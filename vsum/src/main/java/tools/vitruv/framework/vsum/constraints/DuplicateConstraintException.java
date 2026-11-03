package tools.vitruv.framework.vsum.constraints;

/** Thrown if a constraint is created with an id that another constraint already has. */
public class DuplicateConstraintException extends IllegalStateException {
  private static final long serialVersionUID = 1L;

  /** The id of the constraint. */
  private final String constraintId;

  /**
   * Creates an exception for the given constraint id.
   *
   * @param constraintId the id that is already used
   */
  public DuplicateConstraintException(String constraintId) {
    super("There is already another constraint with the id " + constraintId);
    this.constraintId = constraintId;
  }

  /**
   * Gets the id that is already used.
   *
   * @return the constraint id
   */
  public String getConstraintId() {
    return constraintId;
  }
}
