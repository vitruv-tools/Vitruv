package tools.vitruv.framework.vsum.constraints;

/**
 * The kind of a constraint. {@link tools.vitruv.framework.vsum.VirtualModel#validateConstraints()}
 * checks the current state of a virtual model; the kind is metadata provided by the checker, e.g.,
 * for constraint languages supporting pre- and postconditions.
 */
public enum ConstraintKind {
  /** A condition that must hold for every instance of the context type at any time. */
  INVARIANT,
  /** A condition that must hold before an operation or change is applied. */
  PRECONDITION,
  /** A condition that must hold after an operation or change was applied. */
  POSTCONDITION
}
