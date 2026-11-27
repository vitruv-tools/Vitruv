package tools.vitruv.change.composite.propagation;

import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.change.composite.description.VitruviusChange;

/** Listener for the change propagation status. */
public interface ChangePropagationListener {

  /**
   * Called before the change propagation is started.
   *
   * @param changeToPropagate the change to be propagated, must not be <code>null</code>
   */
  void startedChangePropagation(VitruviusChange<Uuid> changeToPropagate);

  /**
   * Called after the change propagation is finished.
   *
   * @param propagatedChanges the changes that have been propagated, must not be <code>null</code>
   */
  void finishedChangePropagation(Iterable<PropagatedChange> propagatedChanges);
}
