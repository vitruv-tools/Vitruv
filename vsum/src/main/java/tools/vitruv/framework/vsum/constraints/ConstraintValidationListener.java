package tools.vitruv.framework.vsum.constraints;

/**
 * Is notified whenever all constraints of a virtual model were validated, see {@link
 * tools.vitruv.framework.vsum.VirtualModel#addConstraintValidationListener}.
 */
@FunctionalInterface
public interface ConstraintValidationListener {

  /** What caused a validation of all constraints. */
  enum Trigger {
    /** A change was propagated and validation after propagation is enabled. */
    PROPAGATION,
    /** A constraint was created, updated or deleted and validation after propagation is enabled. */
    CONSTRAINTS_CHANGED,
    /** All constraints were validated on request. */
    REQUEST
  }

  /**
   * Called after all constraints were validated, while the virtual model does not change, so the
   * report belongs to its current state. Must not modify the virtual model and should return
   * quickly; exceptions are logged and do not affect the virtual model.
   *
   * @param report the validation report of all constraints
   * @param trigger what caused the validation
   */
  void constraintsValidated(ConstraintValidationReport report, Trigger trigger);
}
