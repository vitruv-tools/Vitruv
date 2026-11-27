package tools.vitruv.change.propagation;

import org.eclipse.emf.ecore.EObject;
import tools.vitruv.change.atomic.EChange;

/**
 * A {@link ChangePropagationObservable} informs {@link ChangePropagationObserver}s about
 * event that occur during change propagation.
 */
public interface ChangePropagationObservable extends ChangePropagationObservableRegistry {
  /**
   * Informs the observers about the creation of <code>createdObject</code>.
   *
   * @param createdObject - {@link EObject}
   */
  void notifyObjectCreated(EObject createdObject);

  /**
   * Informs the observers that <code>specification</code> has started to propagate 
   * <code>change</code>.
   *
   * @param specification - {@link ChangePropagationSpecification}
   * @param change - {@link EChange}
   */
  void notifyChangePropagationStarted(
      ChangePropagationSpecification specification, EChange<EObject> change);

  /**
   * Informs this observer that <code>specification</code> has stopped propagation of
   * <code>change</code>.
   *
   * @param specification - {@link ChangePropagationSpecification}
   * @param change - {@link EChange}
   */
  void notifyChangePropagationStopped(
      ChangePropagationSpecification specification, EChange<EObject> change);
}
