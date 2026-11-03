package tools.vitruv.framework.vsum.constraints;

/**
 * A problem in the source of a constraint.
 *
 * @param line the line of the problem, starting at 1
 * @param column the column of the problem, starting at 0
 * @param message a human-readable description of the problem
 */
public record ConstraintDiagnostic(int line, int column, String message) {

  @Override
  public String toString() {
    return line + ":" + column + " " + message;
  }
}
