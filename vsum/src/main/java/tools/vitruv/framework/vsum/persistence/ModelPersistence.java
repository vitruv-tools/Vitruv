package tools.vitruv.framework.vsum.persistence;

import java.util.List;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.change.composite.description.TransactionalChange;
import tools.vitruv.change.composite.description.VitruviusChange;

/**
 * Strategy that decides how a virtual model stores its models, UUIDs and correspondences, chosen
 * with {@link tools.vitruv.framework.vsum.VirtualModelBuilder#withModelPersistence}.
 *
 * <p>A virtual model calls the methods in this order: {@link #load} once when it is built; for
 * each propagation the change hooks in the order the changes are applied to the models ({@link
 * #changeApplied} for the propagated change, {@link #changeRecorded} for each consequential change
 * recorded while change propagation specifications run), then {@link #propagationFinished} and
 * {@link #save}; finally {@link #close} when the virtual model is disposed. The change hooks are
 * notifications only: the models already contain the change when a hook is called.
 *
 * <p>Implementations must not keep state that belongs to one virtual model unless they are only
 * used for that virtual model; the context is passed to every call.
 */
public interface ModelPersistence {

  /**
   * Loads the existing models, their UUIDs and the correspondences into the context's resource set.
   * Called once, before any change is propagated. Does nothing if nothing was stored yet.
   *
   * @param context the models of the virtual model
   */
  void load(ModelPersistenceContext context);

  /**
   * Called after a change with UUIDs was resolved and applied to the models.
   *
   * @param context the models of the virtual model
   * @param change the applied change
   * @param appliedChange the same change, referencing the model elements, as returned by {@code
   *     resolveAndApply}
   */
  default void changeApplied(
      ModelPersistenceContext context,
      VitruviusChange<Uuid> change,
      VitruviusChange<EObject> appliedChange) {}

  /**
   * Called after a consequential change was recorded and UUIDs were assigned to it.
   *
   * @param context the models of the virtual model
   * @param recordedChange the recorded change, referencing the model elements
   * @param changeWithIds the same change with UUIDs, as returned by {@code assignIds}
   */
  default void changeRecorded(
      ModelPersistenceContext context,
      TransactionalChange<EObject> recordedChange,
      VitruviusChange<Uuid> changeWithIds) {}

  /**
   * Called when a propagation is finished, before {@link #save}.
   *
   * @param context the models of the virtual model
   * @param propagatedChanges the changes of the propagation, as returned by {@code
   *     propagateChange}
   */
  default void propagationFinished(
      ModelPersistenceContext context, List<PropagatedChange> propagatedChanges) {}

  /**
   * Persists the state after a propagation.
   *
   * @param context the models of the virtual model
   */
  void save(ModelPersistenceContext context);

  /**
   * Called when the virtual model is disposed, before its models are unloaded.
   *
   * @param context the models of the virtual model
   */
  default void close(ModelPersistenceContext context) {}
}
