package tools.vitruv.framework.vsum.constraints;

import java.util.List;
import java.util.stream.Collectors;

/** Thrown if the source of a constraint is not valid, e.g. because it cannot be parsed. */
public class InvalidConstraintException extends IllegalArgumentException {
  private static final long serialVersionUID = 1L;

  /** The problems in the source. */
  private final transient List<ConstraintDiagnostic> diagnostics;

  /**
   * Creates an exception for the given problems.
   *
   * @param diagnostics the problems in the source, must not be empty
   */
  public InvalidConstraintException(List<ConstraintDiagnostic> diagnostics) {
    super(describe(diagnostics));
    this.diagnostics = List.copyOf(diagnostics);
  }

  private static String describe(List<ConstraintDiagnostic> diagnostics) {
    if (diagnostics == null || diagnostics.isEmpty()) {
      throw new IllegalArgumentException("An invalid constraint needs at least one diagnostic");
    }
    return "Invalid constraint: "
        + diagnostics.stream().map(ConstraintDiagnostic::toString).collect(Collectors.joining("; "));
  }

  /**
   * Gets the problems in the source.
   *
   * @return the problems with their positions
   */
  public List<ConstraintDiagnostic> getDiagnostics() {
    return diagnostics;
  }
}
