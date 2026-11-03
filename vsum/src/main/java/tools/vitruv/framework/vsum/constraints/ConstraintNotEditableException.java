package tools.vitruv.framework.vsum.constraints;

/**
 * Thrown if a constraint is updated or deleted that belongs to a {@link ConstraintChecker} which is
 * no {@link EditableConstraintChecker}.
 */
public class ConstraintNotEditableException extends UnsupportedOperationException {
  private static final long serialVersionUID = 1L;

  /** The id of the constraint. */
  private final String constraintId;

  /**
   * Creates an exception for the given constraint id.
   *
   * @param constraintId the id of the constraint that cannot be changed
   */
  public ConstraintNotEditableException(String constraintId) {
    super(
        "The constraint "
            + constraintId
            + " belongs to a constraint checker that does not support changes");
    this.constraintId = constraintId;
  }

  /**
   * Gets the id of the constraint that cannot be changed.
   *
   * @return the constraint id
   */
  public String getConstraintId() {
    return constraintId;
  }
}
