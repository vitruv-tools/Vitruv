package tools.vitruv.framework.vsum.internal;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.getOrCreateResource;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;
import static tools.vitruv.change.correspondence.model.CorrespondenceModelFactory.createPersistableCorrespondenceModel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.xtext.xbase.lib.Exceptions;

import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.composite.description.TransactionalChange;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.description.VitruviusChangeResolver;
import tools.vitruv.change.composite.description.VitruviusChangeResolverFactory;
import tools.vitruv.change.composite.recording.ChangeRecorder;
import tools.vitruv.change.correspondence.Correspondence;
import tools.vitruv.change.correspondence.model.PersistableCorrespondenceModel;
import tools.vitruv.change.correspondence.view.CorrespondenceModelViewFactory;
import tools.vitruv.change.correspondence.view.EditableCorrespondenceModelView;
import tools.vitruv.change.propagation.impl.ResourceRegistrationAdapter;
import tools.vitruv.change.utils.ResourcePersistenceObserver;
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout;
import tools.vitruv.framework.vsum.internal.messages.InfoMessages;

class ResourceRepositoryImpl implements ModelRepository {
  private static final Logger LOGGER = LogManager.getLogger(ResourceRepositoryImpl.class);

  private final Collection<ResourcePersistenceObserver> persistenceObservers = new ArrayList<>();
  private final ResourceSet modelsResourceSet = withGlobalFactories(new ResourceSetImpl());
  private final Map<URI, ModelInstance> modelInstances = new HashMap<>();
  private final PersistableCorrespondenceModel correspondenceModel;
  private UuidResolver uuidResolver = UuidResolver.create(modelsResourceSet);
  private final ChangeRecorder changeRecorder = new ChangeRecorder(modelsResourceSet);
  private final VitruviusChangeResolver<Uuid> changeResolver =
      VitruviusChangeResolverFactory.forUuids(uuidResolver);

  private final VsumFileSystemLayout fileSystemLayout;

  private boolean isRecording = false;
  private boolean isLoading = false;

  ResourceRepositoryImpl(VsumFileSystemLayout fileSystemLayout) {
    this.fileSystemLayout = fileSystemLayout;
    this.correspondenceModel =
        createPersistableCorrespondenceModel(fileSystemLayout.getCorrespondencesURI());
    modelsResourceSet
        .eAdapters()
        .add(
            new ResourceRegistrationAdapter(
                resource -> getCreateOrLoadModelUnlessLoading(resource.getURI())));
  }

  @Override
  public void loadExistingModels() {
    isLoading = true;
    try {
      readModelsFile();
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
    correspondenceModel.loadSerializedCorrespondences(modelsResourceSet);
    isLoading = false;
  }

  private void writeModelsFile() throws IOException {
    Files.write(
        fileSystemLayout.getModelsNamesFilesPath(),
        modelsResourceSet.getResources().stream()
            .map(Resource::getURI)
            .map(URI::toString)
            .toList());
  }

  private void readModelsFile() throws IOException {
    List<URI> modelUris;
    try {
      modelUris =
          Files.readAllLines(fileSystemLayout.getModelsNamesFilesPath()).stream()
              .map(URI::createURI)
              .toList();

    } catch (NoSuchFileException e) {
      // There are no existing models, so don't do anything
      return;
    }
    // TODO Explicitly load resource.
    modelUris.forEach(uri -> loadOrCreateResource(modelsResourceSet, uri));
    uuidResolver.loadFromUri(fileSystemLayout.getUuidsURI());
    modelUris.forEach(this::createOrLoadModel);
  }

  @Override
  public EditableCorrespondenceModelView<Correspondence> getCorrespondenceModel() {
    return CorrespondenceModelViewFactory.createEditableCorrespondenceModelView(
        correspondenceModel);
  }

  @Override
  public ModelInstance getModel(URI modelUri) {
    return modelInstances.get(modelUri);
  }

  @Override
  public UuidResolver getUuidResolver() {
    return uuidResolver;
  }

  private ModelInstance getCreateOrLoadModelUnlessLoading(URI modelUri) {
    if (isLoading) {
      return null;
    }
    return getCreateOrLoadModel(modelUri);
  }

  private ModelInstance getCreateOrLoadModel(URI modelUri) {
    ModelInstance instance = getModel(modelUri);
    if (instance != null) {
      return instance;
    }
    // TODO Explicitly load resource.
    return createOrLoadModel(modelUri);
  }

  private ModelInstance createOrLoadModel(URI modelUri) {
    Resource resource;
    if (modelUri.isFile() || modelUri.isPlatform()) {
      resource = getOrCreateResource(modelsResourceSet, modelUri);
    } else {
      // TODO Explicitly load resource.
      resource = loadOrCreateResource(modelsResourceSet, modelUri);
    }
    ModelInstance modelInstance = new ModelInstance(resource);
    modelInstances.put(modelUri, modelInstance);
    registerRecorder(modelInstance);
    return modelInstance;
  }

  private void registerRecorder(ModelInstance modelInstance) {
    // Only monitor modifiable models (file / platform URIs, not pathmap URIs)
    if (modelInstance.getURI().isFile() || modelInstance.getURI().isPlatform()) {
      changeRecorder.addToRecording(modelInstance.getResource());
      if (isRecording && !changeRecorder.isRecording()) {
        changeRecorder.beginRecording();
      }
    }
  }

  @Override
  public void persistAsRoot(EObject rootObject, URI uri) {
    getCreateOrLoadModel(uri).addRoot(rootObject);
  }

  @Override
  public void saveOrDeleteModels() {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(InfoMessages.DEBUG_SAVING_ALL_MODELS , fileSystemLayout);
    }
    Iterator<Entry<URI, ModelInstance>> modelInstancesIterator =
        modelInstances.entrySet().iterator();
    while (modelInstancesIterator.hasNext()) {
      ModelInstance modelInstance = modelInstancesIterator.next().getValue();
      final var underlyingResource = modelInstance.getResource();

      if (modelInstance.isEmpty()) {        
        this.persistenceObservers.forEach(p -> p.startDeletingResource(underlyingResource));
        modelInstance.delete();
        modelInstancesIterator.remove();
        this.persistenceObservers.forEach(p -> p.finishDeletingResource(underlyingResource));
      } else {
        this.persistenceObservers.forEach(p -> p.startSavingResource(underlyingResource));
        modelInstance.save();
        this.persistenceObservers.forEach(p -> p.finishSavingResource(underlyingResource));
      }
    }
    correspondenceModel.save();
    try {
      writeModelsFile();
      uuidResolver.storeAtUri(fileSystemLayout.getUuidsURI());
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public Iterable<TransactionalChange<EObject>> recordChanges(Runnable changeApplicator) {
    changeRecorder.beginRecording();
    isRecording = true;
    LOGGER.debug(InfoMessages.DEBUG_START_RECORDING_VIRTUAL_MODEL);
    changeApplicator.run();
    LOGGER.debug(InfoMessages.DEBUG_END_RECORDING_VIRTUAL_MODEL);
    isRecording = false;
    changeRecorder.endRecording();
    TransactionalChange<EObject> change = changeRecorder.getChange();
    changeResolver.assignIds(change);
    return change.containsConcreteChange() ? List.of(change) : List.of();
  }

  @Override
  public VitruviusChange<EObject> applyChange(VitruviusChange<Uuid> change) {
    return changeResolver.resolveAndApply(change);
  }

  @Override
  public URI getMetadataModelURI(String... metadataKey) {
    return fileSystemLayout.getConsistencyMetadataModelURI(metadataKey);
  }

  @Override
  public Resource getModelResource(URI uri) {
    return getCreateOrLoadModel(uri).getResource();
  }

  @Override
  public Collection<Resource> getModelResources() {
    return modelsResourceSet.getResources();
  }

  @Override
  public void close() {
    changeRecorder.close();
    modelsResourceSet.getResources().forEach(Resource::unload);
    modelsResourceSet.getResources().clear();
    uuidResolver = null;
  }

  @Override
  public void registerModelPersistanceObserver(ResourcePersistenceObserver observer) {
    this.persistenceObservers.add(observer);
  }

  @Override
  public void deregisterModelPersistanceObserver(ResourcePersistenceObserver observer) {
    this.persistenceObservers.remove(observer);
  }

    /**
   * Returns a {@link Resource} that is either loaded from the given {@link URI} if some model
   * is persisted at that {@link URI}, or creates a new {@link Resource} if it does not exist yet.
   * 
   * @param resourceSet -
   * 			the {@link ResourceSet} to load the {@link Resource} into
   * @param uri -
   * 			the {@link URI} of the {@link Resource} to load
   * @return a {@link Resource} created for or loaded from the given {@link URI}
   * @throws RuntimeException if some exception occurred during loading the file
   */
  Resource loadOrCreateResource(final ResourceSet resourceSet, final URI uri) throws RuntimeException {
    try {
      var resource = resourceSet.getResource(uri, false);
      if (resource == null) {
        resource = resourceSet.createResource(uri);
      }
      if (!resource.isLoaded()) {
        this.persistenceObservers.forEach(o -> o.startLoadingResource(uri));
        resource.load(null);
        final var loadedResource = resource;
        this.persistenceObservers.forEach(o -> o.finishLoadingResource(loadedResource));
      }
      return resource;
    } catch (final Throwable _t) {
      if (_t instanceof RuntimeException) {
        final RuntimeException e = (RuntimeException)_t;
        if (((e.getCause() instanceof FileNotFoundException) || e.getMessage().contains("not exist"))) {
          return resourceSet.getResource(uri, false);
        } else {
          throw e;
        }
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
}
