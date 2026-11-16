package tools.vitruv.framework.vsum.internal;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.getOrCreateResource;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.loadOrCreateResource;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;
import static tools.vitruv.change.correspondence.model.CorrespondenceModelFactory.createPersistableCorrespondenceModel;

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
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.composite.description.PropagatedChange;
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
import tools.vitruv.framework.vsum.persistence.ModelPersistence;
import tools.vitruv.framework.vsum.persistence.ModelPersistenceContext;

class ResourceRepositoryImpl implements ModelRepository {
  private static final Logger LOGGER = LogManager.getLogger(ResourceRepositoryImpl.class);

  private final ResourceSet modelsResourceSet = createModelsResourceSet();
  private final Map<URI, ModelInstance> modelInstances = new HashMap<>();
  private final PersistableCorrespondenceModel correspondenceModel;
  private UuidResolver uuidResolver = UuidResolver.create(modelsResourceSet);
  private final ChangeRecorder changeRecorder = new ChangeRecorder(modelsResourceSet);
  private final VitruviusChangeResolver<Uuid> changeResolver =
      VitruviusChangeResolverFactory.forUuids(uuidResolver);

  private final VsumFileSystemLayout fileSystemLayout;
  private final ModelPersistence modelPersistence;
  private final ModelPersistenceContext persistenceContext = new PersistenceContext();

  private boolean isRecording = false;
  private boolean isLoading = false;

  ResourceRepositoryImpl(VsumFileSystemLayout fileSystemLayout, ModelPersistence modelPersistence) {
    this.fileSystemLayout = fileSystemLayout;
    this.modelPersistence = modelPersistence;
    this.correspondenceModel =
        createPersistableCorrespondenceModel(fileSystemLayout.getCorrespondencesURI());
    modelsResourceSet
        .eAdapters()
        .add(
            new ResourceRegistrationAdapter(
                resource -> getCreateOrLoadModelUnlessLoading(resource.getURI())));
  }

  private static ResourceSet createModelsResourceSet() {
    ResourceSetImpl resourceSet = new ResourceSetImpl();
    // index the resources by URI, so that resolving IDs does not scan all resources
    new RobustMappedResourceLocator(resourceSet);
    return withGlobalFactories(resourceSet);
  }

  @Override
  public void loadExistingModels() {
    isLoading = true;
    modelPersistence.load(persistenceContext);
    isLoading = false;
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
    return createOrLoadModel(modelUri);
  }

  private ModelInstance createOrLoadModel(URI modelUri) {
    Resource resource;
    if (modelUri.isFile() || modelUri.isPlatform()) {
      resource = getOrCreateResource(modelsResourceSet, modelUri);
    } else {
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
  public void propagationFinished(List<PropagatedChange> propagatedChanges) {
    modelPersistence.propagationFinished(persistenceContext, propagatedChanges);
  }

  @Override
  public void saveOrDeleteModels() {
    modelPersistence.save(persistenceContext);
  }

  private void saveOrDeleteModelResources() {
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
    VitruviusChange<Uuid> changeWithIds = changeResolver.assignIds(change);
    if (!change.containsConcreteChange()) {
      return List.of();
    }
    modelPersistence.changeRecorded(persistenceContext, change, changeWithIds);
    return List.of(change);
  }

  @Override
  public VitruviusChange<EObject> applyChange(VitruviusChange<Uuid> change) {
    VitruviusChange<EObject> appliedChange = changeResolver.resolveAndApply(change);
    modelPersistence.changeApplied(persistenceContext, change, appliedChange);
    return appliedChange;
  }

  @Override
  public ModelRepositorySnapshot createSnapshot() {
    return DefaultModelRepositorySnapshot.copyOf(
        modelsResourceSet, correspondenceModel, this::getMetadataModelURI);
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
    modelPersistence.close(persistenceContext);
    changeRecorder.close();
    modelsResourceSet.getResources().forEach(Resource::unload);
    modelsResourceSet.getResources().clear();
    uuidResolver = null;
  }

  /** Gives the persistence strategy access to the models of this repository. */
  private final class PersistenceContext implements ModelPersistenceContext {
    @Override
    public VsumFileSystemLayout getFileSystemLayout() {
      return fileSystemLayout;
    }

    @Override
    public ResourceSet getModelsResourceSet() {
      return modelsResourceSet;
    }

    @Override
    public UuidResolver getUuidResolver() {
      return uuidResolver;
    }

    @Override
    public PersistableCorrespondenceModel getCorrespondenceModel() {
      return correspondenceModel;
    }

    @Override
    public void loadCorrespondences() {
      withIntrinsicIdIndex(
          () -> correspondenceModel.loadSerializedCorrespondences(modelsResourceSet));
    }

    @Override
    public void registerModel(URI modelUri) {
      createOrLoadModel(modelUri);
    }

    @Override
    public void saveOrDeleteModels() {
      saveOrDeleteModelResources();
    }
  }
}
