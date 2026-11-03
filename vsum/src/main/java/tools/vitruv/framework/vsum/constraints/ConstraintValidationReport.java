package tools.vitruv.framework.vsum.constraints;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.eclipse.emf.common.util.URI;

/**
 * The results of validating constraints against a virtual model.
 *
 * @param results the results, one per validated constraint
 */
public record ConstraintValidationReport(List<ConstraintValidationResult> results) {

  /** Makes the result list immutable. */
  public ConstraintValidationReport {
    results = List.copyOf(results);
  }

  /**
   * Checks whether all validated constraints are satisfied.
   *
   * @return {@code true} if no constraint is violated or could not be checked
   */
  public boolean isSatisfied() {
    return results.stream().allMatch(result -> result.status() == ConstraintStatus.SATISFIED);
  }

  /**
   * Gets the results of all constraints that are not satisfied.
   *
   * @return the results with status {@link ConstraintStatus#VIOLATED} or {@link
   *     ConstraintStatus#ERROR}
   */
  public List<ConstraintValidationResult> getUnsatisfiedResults() {
    return results.stream()
        .filter(result -> result.status() != ConstraintStatus.SATISFIED)
        .toList();
  }

  /**
   * Gets the URIs of the resources that contained at least one violating element at validation
   * time.
   *
   * @return the URIs of the affected resources, in the order of their first violation
   */
  public Set<URI> affectedResourceUris() {
    return results.stream()
        .flatMap(result -> result.violations().stream())
        .map(ResolvedConstraintViolation::resourceUri)
        .filter(Objects::nonNull)
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }
}
