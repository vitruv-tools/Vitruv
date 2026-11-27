package tools.vitruv.framework.vsum.internal;

import java.util.Collection;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import tools.vitruv.change.propagation.ChangeRecordingModelRepository;
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout;

/** Repository for model instances in the VSUM framework. */
public interface ModelRepository extends ChangeRecordingModelRepository {
  /**
   * Returns the model at the given {@link URI} if it was already loaded to or created in the
   * repository. Returns <code>null</code> otherwise.
   */
  ModelInstance getModel(URI modelUri);

  /** Loads existing models from the file system into the repository. */
  void loadExistingModels();

  /** Reloads all models from the file system into the repository.
   * It will unload currently loaded models and reload from disk
   * to reflect the current state of the working dir
   */
  void reload();

  /** Binds this repository to a different branch's storage directory, discarding all
   * in-memory state and reloading from the new layout's files on disk.
   */
  void reinitialize(VsumFileSystemLayout newLayout);

  /** Saves or deletes models in the repository based on their state. */
  void saveOrDeleteModels();

  /**
   * Returns all model resources managed by this repository.
   *
   * @return the model resources
   */
  Collection<Resource> getModelResources();
}
