package tools.vitruv.framework.vsum.internal;

import java.util.Collection;
import java.util.List;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.change.propagation.ChangeRecordingModelRepository;

/** Repository for model instances in the VSUM framework. */
public interface ModelRepository extends ChangeRecordingModelRepository {
  /**
   * Returns the model at the given {@link URI} if it was already loaded to or created in the
   * repository. Returns <code>null</code> otherwise.
   */
  ModelInstance getModel(URI modelUri);

  /** Loads existing models from the file system into the repository. */
  void loadExistingModels();

  /**
   * Notifies the repository that a propagation is finished, before {@link #saveOrDeleteModels()}.
   *
   * @param propagatedChanges the changes of the propagation
   */
  void propagationFinished(List<PropagatedChange> propagatedChanges);

  /** Persists the models in the repository with the configured persistence strategy. */
  void saveOrDeleteModels();

  /**
   * Returns all model resources managed by this repository.
   *
   * @return the model resources
   */
  Collection<Resource> getModelResources();
}
