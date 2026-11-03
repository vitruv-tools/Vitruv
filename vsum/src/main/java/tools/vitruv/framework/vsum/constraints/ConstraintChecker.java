package tools.vitruv.framework.vsum.constraints;

import java.util.Collection;
import java.util.List;

/**
 * Checks constraints on the models of a virtual model. Implementations provide constraints of a
 * specific constraint language and are registered via {@link
 * tools.vitruv.framework.vsum.VirtualModelBuilder#withConstraintChecker(ConstraintChecker)}.
 *
 * <p>{@link #check} is called while the virtual model is locked, so change propagation waits until
 * it returns. Implementations should therefore be fast and must not access the virtual model from
 * other threads during a check.
 */
public interface ConstraintChecker {

  /**
   * Gets the constraints provided by this checker. Their ids must be unique within a virtual
   * model. This method is called whenever the constraints of the virtual model change, so it must
   * be cheap and reflect the current constraints; checkers that are no {@link
   * EditableConstraintChecker} must always return the same constraints.
   *
   * @return the constraints provided by this checker
   */
  List<ConstraintDescriptor> getConstraints();

  /**
   * Checks the given constraints of this checker against the given virtual model state. Must
   * return a result for each requested constraint; requested constraints without a result are
   * reported as {@link ConstraintStatus#ERROR}.
   *
   * @param context read access to the virtual model
   * @param constraintIds the ids of the constraints to check, all of which are provided by this
   *     checker
   * @return the results of the checked constraints
   */
  List<ConstraintResult> check(ConstraintCheckContext context, Collection<String> constraintIds);
}
