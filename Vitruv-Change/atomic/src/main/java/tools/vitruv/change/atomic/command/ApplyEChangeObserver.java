package tools.vitruv.change.atomic.command;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.command.internal.ApplyEChangeSwitch;

/**
 * An observer for {@link ApplyEChangeSwitch#applyEChange}.
 */
public interface ApplyEChangeObserver {
  /**
   * Informs the observer that change application has started.
   *
   * @param change - {@link EChange}
   * @param applyForward - boolean
   */
  void startToApplyEChange(EChange<EObject> change, boolean applyForward);

  /**
   * Informs the observer that change application has finished,
   * and resulted in applying the execution of <code>commands</code>.
   *
   * @param commands - {@link Iterable}
   */
  void endApplyEChange(Iterable<Command> commands);
}
