package tools.vitruv.framework.vsum.constraints;

/** Thrown if a requested constraint does not exist. */
public class UnknownConstraintException extends IllegalArgumentException {
  private static final long serialVersionUID = 1L;

  /** The id of the requested constraint. */
  private final String constraintId;

  /**
   * Creates an exception for the given constraint id.
   *
   * @param constraintId the id of the requested constraint
   */
  public UnknownConstraintException(String constraintId) {
    super("There is no constraint with the id " + constraintId);
    this.constraintId = constraintId;
  }

  /**
   * Gets the id of the requested constraint.
   *
   * @return the constraint id
   */
  public String getConstraintId() {
    return constraintId;
  }
}
