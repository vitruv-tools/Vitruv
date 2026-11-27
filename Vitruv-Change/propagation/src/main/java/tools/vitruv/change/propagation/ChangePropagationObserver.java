package tools.vitruv.change.propagation;

import org.eclipse.emf.ecore.EObject;
import tools.vitruv.change.atomic.EChange;

/**
 * A {@link ChangePropagationObserver} may be informed about events
 * that occur during change propagation.
 */
public interface ChangePropagationObserver {
  /**
   * Informs this observer about the creation of <code>createdObject</code>.
   *
   * @param createdObject - {@link EObject}
   */
  void objectCreated(EObject createdObject);

  /**
   * Informs this observer that <code>specification</code> has started to propagate 
   * <code>change</code>.
   *
   * @param specification - {@link ChangePropagationSpecification}
   * @param change - {@link EChange}
   */
  void changePropagationStarted(
      ChangePropagationSpecification specification, EChange<EObject> change);

  
  /**
   * Informs this observer that <code>specification</code> has stopped propagation of
   * <code>change</code>.
   *
   * @param specification - {@link ChangePropagationSpecification}
   * @param change - {@link EChange}
   */
  void changePropagationStopped(
      ChangePropagationSpecification specification, EChange<EObject> change);
}
