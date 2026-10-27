package tools.vitruv.framework.vsum.constraints;

/** The outcome of checking a single constraint. */
public enum ConstraintStatus {
  /** The constraint holds for all checked elements. */
  SATISFIED,
  /** The constraint does not hold for at least one element. */
  VIOLATED,
  /** The constraint could not be checked, e.g., because it is malformed or evaluation failed. */
  ERROR
}
