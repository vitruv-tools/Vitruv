package tools.vitruv.framework.vsum.persistence;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.correspondence.model.PersistableCorrespondenceModel;
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout;

/** The models of a virtual model, as a {@link ModelPersistence} sees them. */
public interface ModelPersistenceContext {

  /**
   * Returns the layout of the virtual model's folder.
   *
   * @return the file system layout
   */
  VsumFileSystemLayout getFileSystemLayout();

  /**
   * Returns the resource set that holds the models.
   *
   * @return the models' resource set
   */
  ResourceSet getModelsResourceSet();

  /**
   * Returns the UUID resolver of the models.
   *
   * @return the UUID resolver
   */
  UuidResolver getUuidResolver();

  /**
   * Returns the correspondence model.
   *
   * @return the correspondence model
   */
  PersistableCorrespondenceModel getCorrespondenceModel();

  /**
   * Loads the serialized correspondences and resolves them against the models in the resource set.
   * Elements with an ID attribute are looked up through a temporary ID index while loading.
   */
  void loadCorrespondences();

  /**
   * Registers the model at the given URI with the virtual model, so that its changes are recorded.
   * Loads or creates its resource if necessary.
   *
   * @param modelUri the URI of the model
   */
  void registerModel(URI modelUri);

  /**
   * Saves every registered model that has content and deletes and unregisters every empty one.
   */
  void saveOrDeleteModels();
}
