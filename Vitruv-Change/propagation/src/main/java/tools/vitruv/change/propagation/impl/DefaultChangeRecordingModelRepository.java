package tools.vitruv.change.propagation.impl;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.getOrCreateResource;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.loadOrCreateResource;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;
import static tools.vitruv.change.correspondence.model.CorrespondenceModelFactory.createPersistableCorrespondenceModel;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
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
import tools.vitruv.change.propagation.PersistableChangeRecordingModelRepository;

/**
 * A default implementation of a {@link PersistableChangeRecordingModelRepository}. It manages a
 * {@link ResourceSet} for the models, which are automatically monitored for changes. It loads
 * existing models calling {@link #loadExistingModels()} by loading the correspondence model at the
 * URI specified in the constructor and resolving all models references in the correspondence model.
 * If no such URI is specified, loading will have no effect.
 */
public class DefaultChangeRecordingModelRepository
    implements PersistableChangeRecordingModelRepository {
  private static final Logger LOGGER =
      LogManager.getLogger(DefaultChangeRecordingModelRepository.class);
  private static final String METADATA_KEY_FRAGMENT_SEPARATOR = "_";

  private final ResourceSet modelsResourceSet;
  private final PersistableCorrespondenceModel correspondenceModel;
  private final ChangeRecorder changeRecorder;
  private final Path consistencyMetadataFolder;
  private UuidResolver uuidResolver;
  private final VitruviusChangeResolver<Uuid> changeResolver;

  private boolean isLoading = false;

  /**
   * Creates a repository with the defined URI for the correspondence model and using the specified
   * path for storing metadata. The URI must be resolvable when performing load or save operations,
   * or can be {@code null} is no persistence of the correspondence model shall be managed.
   *
   * @param correspondencesURI the URI of the correspondence model to use for saving and loading it,
   *     must be {@code null} or valid
   * @param consistencyMetadataFolder the folder to store consistency metadata in, must not be
   *     {@code null}
   */
  public DefaultChangeRecordingModelRepository(
      URI correspondencesURI, Path consistencyMetadataFolder) {
    this.consistencyMetadataFolder = consistencyMetadataFolder;
    this.modelsResourceSet = withGlobalFactories(new ResourceSetImpl());
    this.uuidResolver = UuidResolver.create(modelsResourceSet);
    this.changeResolver = VitruviusChangeResolverFactory.forUuids(uuidResolver);
    this.correspondenceModel = createPersistableCorrespondenceModel(correspondencesURI);
    this.modelsResourceSet
        .eAdapters()
        .add(
            new ResourceRegistrationAdapter(
                resource -> {
                  if (!isLoading) {
                    getCreateOrLoadModel(resource.getURI());
                  }
                }));
    this.changeRecorder = new ChangeRecorder(modelsResourceSet);
  }

  @Override
  public void loadExistingModels() {
    isLoading = true;
    correspondenceModel.loadSerializedCorrespondences(modelsResourceSet);
    isLoading = false;
  }

  @Override
  public EditableCorrespondenceModelView<Correspondence> getCorrespondenceModel() {
    return CorrespondenceModelViewFactory.createEditableCorrespondenceModelView(
        correspondenceModel);
  }

  @Override
  public URI getMetadataModelURI(String... metadataKey) {
    Path metadataPath =
        consistencyMetadataFolder.resolve(
            String.join(METADATA_KEY_FRAGMENT_SEPARATOR, metadataKey));
    return URI.createFileURI(metadataPath.toString());
  }

  @Override
  public Resource getModelResource(URI uri) {
    return getCreateOrLoadModel(uri);
  }

  private Resource getCreateOrLoadModel(URI modelResourceURI) {
    Resource resource;
    if ((modelResourceURI.isFile() || modelResourceURI.isPlatform())) {
      resource = getOrCreateResource(modelsResourceSet, modelResourceURI);
      // Only monitor modifiable models (file / platform URIs, not pathmap URIs)
      changeRecorder.addToRecording(resource);
    } else {
      resource = loadOrCreateResource(modelsResourceSet, modelResourceURI);
    }
    return resource;
  }

  @Override
  public void persistAsRoot(EObject rootEObject, URI uri) {
    addRoot(getCreateOrLoadModel(uri), rootEObject);
  }

  private void addRoot(Resource resource, EObject root) {
    resource.getContents().add(root);
    resource.setModified(true);
    LOGGER.debug("Add root to resource: {}", resource);
  }

  @Override
  public void saveOrDeleteModels() {
    Set<Resource> resourcesToDelete = new HashSet<>();
    for (Resource modelResource : modelsResourceSet.getResources()) {
      if (modelResource.getContents().isEmpty()) {
        resourcesToDelete.add(modelResource);
      } else {
        save(modelResource);
      }
    }
    resourcesToDelete.stream().forEach(this::delete);
    correspondenceModel.save();
  }

  private void delete(Resource resource) {
    try {
      resource.delete(null);
    } catch (IOException e) {
      LOGGER.error("Deletion of resource {} did not work.", resource, e);
      throw new IllegalStateException("Could not delete resource with URI " + resource.getURI(), e);
    }
  }

  private void save(Resource resource) {
    if (!resource.isModified()) {
      return;
    }
    LOGGER.debug("Attempt to save resource: {}", resource);
    try {
      resource.save(null);
      resource.setModified(false);
    } catch (IOException e) {
      LOGGER.error("Model could not be saved: {}", resource.getURI(), e);
      throw new IllegalStateException("Could not save resource with URI " + resource.getURI(), e);
    }
  }

  @Override
  public Iterable<TransactionalChange<EObject>> recordChanges(Runnable changeApplicator) {
    changeRecorder.beginRecording();
    LOGGER.debug("Start recording changes");
    changeApplicator.run();
    LOGGER.debug("End recording changes");
    changeRecorder.endRecording();
    TransactionalChange<EObject> recordedChange = changeRecorder.getChange();
    changeResolver.assignIds(recordedChange);
    return List.of(recordedChange);
  }

  @Override
  public VitruviusChange<EObject> applyChange(VitruviusChange<Uuid> change) {
    return changeResolver.resolveAndApply(change);
  }

  @Override
  public void close() throws Exception {
    changeRecorder.close();
    modelsResourceSet.getResources().forEach(Resource::unload);
    modelsResourceSet.getResources().clear();
    correspondenceModel.close();
    uuidResolver = null;
  }

  @Override
  public UuidResolver getUuidResolver() {
    return uuidResolver;
  }
}
