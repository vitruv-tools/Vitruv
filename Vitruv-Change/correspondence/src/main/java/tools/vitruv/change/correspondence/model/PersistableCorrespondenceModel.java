package tools.vitruv.change.correspondence.model;

import org.eclipse.emf.ecore.resource.ResourceSet;

/**
 * An persistable representation of a {@link CorrespondenceModel}.
 *
 * @author Heiko Klare
 */
public interface PersistableCorrespondenceModel extends CorrespondenceModel {
  /**
   * Loads the correspondence model from its persistence if existing.
   *
   * @param resolveIn the {@link ResourceSet} to resolve the corresponding objects in
   */
  public void loadSerializedCorrespondences(ResourceSet resolveIn);

  /** Saves this correspondence model instance. */
  public void save();
}
