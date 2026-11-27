package tools.vitruv.change.composite.description;

import org.eclipse.emf.ecore.EObject;

/** Interface for resolving and assigning Ids to changes. */
public interface VitruviusChangeResolver<Id> {
  /**
   * Resolves the change and applies it forward so that the model is in the state after the change
   * afterwards. It has to be ensured that the model is in a state the change can be applied to
   * before calling this method. Returns the resolved change.
   *
   * @throws IllegalStateException if the change cannot be resolved or applied.
   */
  public VitruviusChange<EObject> resolveAndApply(VitruviusChange<Id> change);

  /**
   * Unresolves the change and assigns Ids to it. It has to be ensured that the model is in the
   * state after the change has been applied. Returns the Id-assigned change.
   */
  public VitruviusChange<Id> assignIds(VitruviusChange<EObject> change);
}
