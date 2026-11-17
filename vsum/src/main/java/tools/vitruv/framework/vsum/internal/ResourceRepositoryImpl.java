package tools.vitruv.framework.vsum.internal;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.getOrCreateResource;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.loadOrCreateResource;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;
import static tools.vitruv.change.correspondence.model.CorrespondenceModelFactory.createPersistableCorrespondenceModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
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
import tools.vitruv.change.propagation.ModelRepositorySnapshot;
import tools.vitruv.change.propagation.impl.DefaultModelRepositorySnapshot;
import tools.vitruv.change.propagation.impl.ResourceRegistrationAdapter;
import tools.vitruv.framework.views.util.RobustMappedResourceLocator;
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout;
import tools.vitruv.framework.vsum.internal.messages.InfoMessages;

/**
 * The repository of the models of a virtual model.
 *
 * <p>Models can be created, loaded and looked up from several threads. Creating and loading
 * models, including the demand-loading by proxy resolution, as well as iterating the models, holds
 * the monitor of the {@link SynchronizedResourceSet models resource set}, which serves as the
 * repository lock; {@link #getModel} does not lock.
 */
class ResourceRepositoryImpl implements ModelRepository {
  private static final Logger LOGGER = LogManager.getLogger(ResourceRepositoryImpl.class);

  private final ResourceSet modelsResourceSet = createModelsResourceSet();
  private final Map<URI, ModelInstance> modelInstances = new ConcurrentHashMap<>();
  private final PersistableCorrespondenceModel correspondenceModel;
  private UuidResolver uuidResolver = UuidResolver.create(modelsResourceSet);
  private final ChangeRecorder changeRecorder = new ChangeRecorder(modelsResourceSet);
  private final VitruviusChangeResolver<Uuid> changeResolver =
      VitruviusChangeResolverFactory.forUuids(uuidResolver);

  private final VsumFileSystemLayout fileSystemLayout;

  /** Whether the current thread records changes in {@link #recordChanges}. */
  private final ThreadLocal<Boolean> isRecording = ThreadLocal.withInitial(() -> false);

  private volatile boolean isLoading = false;

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

  private static ResourceSet createModelsResourceSet() {
    ResourceSetImpl resourceSet = new SynchronizedResourceSet();
    // index the resources by URI, so that resolving IDs does not scan all resources
    new RobustMappedResourceLocator(resourceSet);
    return withGlobalFactories(resourceSet);
  }

  @Override
  public void loadExistingModels() {
    synchronized (modelsResourceSet) {
      isLoading = true;
      try {
        readModelsFile();
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }
      withIntrinsicIdIndex(
          () -> correspondenceModel.loadSerializedCorrespondences(modelsResourceSet));
      isLoading = false;
    }
  }

  /**
   * Runs the given action while the model resources index their elements by their ID attributes.
   * The correspondences refer to elements with an ID attribute by that ID, and without an index EMF
   * searches the whole resource for each of them. The index is removed afterwards, because EMF does
   * not update it when the ID of an element changes.
   */
  private void withIntrinsicIdIndex(Runnable action) {
    List<ResourceImpl> indexedResources = new ArrayList<>();
    for (Resource resource : modelsResourceSet.getResources()) {
      if (resource instanceof ResourceImpl resourceImpl
          && resourceImpl.getIntrinsicIDToEObjectMap() == null) {
        resourceImpl.setIntrinsicIDToEObjectMap(new HashMap<>());
        indexedResources.add(resourceImpl);
      }
    }
    try {
      action.run();
    } finally {
      indexedResources.forEach(resource -> resource.setIntrinsicIDToEObjectMap(null));
    }
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
    synchronized (modelsResourceSet) {
      instance = getModel(modelUri);
      if (instance != null) {
        return instance;
      }
      return createOrLoadModel(modelUri);
    }
  }

  private ModelInstance createOrLoadModel(URI modelUri) {
    synchronized (modelsResourceSet) {
      Resource resource;
      if (modelUri.isFile() || modelUri.isPlatform()) {
        resource = getOrCreateResource(modelsResourceSet, modelUri);
      } else {
        resource = loadOrCreateResource(modelsResourceSet, modelUri);
      }
      // creating the resource registers it through the ResourceRegistrationAdapter
      ModelInstance existing = getModel(modelUri);
      if (existing != null) {
        return existing;
      }
      ModelInstance modelInstance = new ModelInstance(resource);
      modelInstances.put(modelUri, modelInstance);
      registerRecorder(modelInstance);
      return modelInstance;
    }
  }

  private void registerRecorder(ModelInstance modelInstance) {
    // Only monitor modifiable models (file / platform URIs, not pathmap URIs)
    if (modelInstance.getURI().isFile() || modelInstance.getURI().isPlatform()) {
      changeRecorder.addToRecording(modelInstance.getResource());
      if (isRecording.get() && !changeRecorder.isRecording()) {
        changeRecorder.beginRecording();
      }
    }
  }

  @Override
  public void persistAsRoot(EObject rootObject, URI uri) {
    ModelInstance model = getCreateOrLoadModel(uri);
    synchronized (modelsResourceSet) {
      model.addRoot(rootObject);
    }
  }

  @Override
  public void saveOrDeleteModels() {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(InfoMessages.DEBUG_SAVING_ALL_MODELS, fileSystemLayout);
    }
    synchronized (modelsResourceSet) {
      Iterator<Entry<URI, ModelInstance>> modelInstancesIterator =
          modelInstances.entrySet().iterator();
      while (modelInstancesIterator.hasNext()) {
        ModelInstance modelInstance = modelInstancesIterator.next().getValue();
        if (modelInstance.isEmpty()) {
          modelInstance.delete();
          modelInstancesIterator.remove();
        } else {
          modelInstance.save();
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
  }

  @Override
  public Iterable<TransactionalChange<EObject>> recordChanges(Runnable changeApplicator) {
    changeRecorder.beginRecording();
    isRecording.set(true);
    LOGGER.debug(InfoMessages.DEBUG_START_RECORDING_VIRTUAL_MODEL);
    try {
      changeApplicator.run();
    } finally {
      isRecording.remove();
    }
    LOGGER.debug(InfoMessages.DEBUG_END_RECORDING_VIRTUAL_MODEL);
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
  public ModelRepositorySnapshot createSnapshot() {
    synchronized (modelsResourceSet) {
      return DefaultModelRepositorySnapshot.copyOf(
          modelsResourceSet, correspondenceModel, this::getMetadataModelURI);
    }
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
    synchronized (modelsResourceSet) {
      return List.copyOf(modelsResourceSet.getResources());
    }
  }

  @Override
  public void close() {
    changeRecorder.close();
    synchronized (modelsResourceSet) {
      modelsResourceSet.getResources().forEach(Resource::unload);
      modelsResourceSet.getResources().clear();
    }
    uuidResolver = null;
  }
}
