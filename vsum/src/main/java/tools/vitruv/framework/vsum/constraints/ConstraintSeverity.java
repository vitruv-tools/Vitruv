package tools.vitruv.framework.vsum.constraints;

/**
 * The severity of a constraint or of a single constraint violation, in ascending order. The levels
 * match the {@code @severity} annotations of VitruvOCL.
 */
public enum ConstraintSeverity {
  INFO,
  WARNING,
  MINOR,
  MAJOR,
  CRITICAL
}
