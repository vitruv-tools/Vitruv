package tools.vitruv.framework.vsum.internal;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.framework.vsum.constraints.ConstraintCheckContext;
import tools.vitruv.framework.vsum.constraints.ConstraintChecker;
import tools.vitruv.framework.vsum.constraints.ConstraintDescriptor;
import tools.vitruv.framework.vsum.constraints.ConstraintResult;
import tools.vitruv.framework.vsum.constraints.ConstraintValidationReport;
import tools.vitruv.framework.vsum.constraints.ConstraintValidationResult;
import tools.vitruv.framework.vsum.constraints.ConstraintViolation;
import tools.vitruv.framework.vsum.constraints.ResolvedConstraintViolation;
import tools.vitruv.framework.vsum.internal.messages.ErrorMessages;

/**
 * Manages the {@link ConstraintChecker}s of a virtual model and runs validations. The constraints
 * of the checkers are determined once on construction.
 */
public class ConstraintCheckerRegistry {
  private static final Logger LOGGER = LogManager.getLogger(ConstraintCheckerRegistry.class);

  private final Map<String, ConstraintDescriptor> descriptors = new LinkedHashMap<>();
  private final Map<String, ConstraintChecker> checkersByConstraintId = new LinkedHashMap<>();

  /**
   * Creates a registry for the given checkers.
   *
   * @param checkers the constraint checkers
   * @throws IllegalArgumentException if two constraints share the same id
   */
  public ConstraintCheckerRegistry(Collection<ConstraintChecker> checkers) {
    for (ConstraintChecker checker : checkers) {
      List<ConstraintDescriptor> constraints =
          checkNotNull(checker.getConstraints(), ErrorMessages.CHECKER_CONSTRAINTS_NULL, checker);
      for (ConstraintDescriptor descriptor : constraints) {
        checkArgument(
            !descriptors.containsKey(descriptor.id()),
            ErrorMessages.DUPLICATE_CONSTRAINT_ID,
            descriptor.id());
        descriptors.put(descriptor.id(), descriptor);
        checkersByConstraintId.put(descriptor.id(), checker);
      }
    }
  }

  /**
   * Gets the constraints of all registered checkers, in registration order.
   *
   * @return the constraints
   */
  public List<ConstraintDescriptor> getConstraints() {
    return List.copyOf(descriptors.values());
  }

  /**
   * Validates the given constraints. Checkers are called once with all of their requested
   * constraints. Failures of a checker are reported as errors of its constraints.
   *
   * @param context read access to the virtual model
   * @param uuidResolver the resolver used to identify violating elements
   * @param constraintIds the ids of the constraints to validate; duplicates are validated once
   * @return the validation report, in the order of the requested ids
   * @throws IllegalArgumentException if an id does not belong to a registered constraint
   */
  public ConstraintValidationReport validate(
      ConstraintCheckContext context, UuidResolver uuidResolver, Collection<String> constraintIds) {
    List<String> requestedIds = List.copyOf(new LinkedHashSet<>(constraintIds));
    for (String id : requestedIds) {
      checkArgument(descriptors.containsKey(id), ErrorMessages.UNKNOWN_CONSTRAINT_ID, id);
    }

    Map<ConstraintChecker, List<String>> idsByChecker = new LinkedHashMap<>();
    for (String id : requestedIds) {
      idsByChecker.computeIfAbsent(checkersByConstraintId.get(id), c -> new ArrayList<>()).add(id);
    }

    Map<String, ConstraintResult> resultsById = new LinkedHashMap<>();
    idsByChecker.forEach((checker, ids) -> resultsById.putAll(runChecker(checker, context, ids)));

    List<ConstraintValidationResult> results =
        requestedIds.stream().map(id -> resolve(id, resultsById.get(id), uuidResolver)).toList();
    return new ConstraintValidationReport(results);
  }

  private Map<String, ConstraintResult> runChecker(
      ConstraintChecker checker, ConstraintCheckContext context, List<String> ids) {
    Map<String, ConstraintResult> results = new LinkedHashMap<>();
    try {
      List<ConstraintResult> checkerResults = checker.check(context, ids);
      if (checkerResults != null) {
        for (ConstraintResult result : checkerResults) {
          if (result != null && ids.contains(result.descriptor().id())) {
            results.put(result.descriptor().id(), result);
          }
        }
      }
    } catch (RuntimeException | StackOverflowError exception) {
      LOGGER.warn("Constraint checker {} failed", checker, exception);
      String reason =
          exception.getMessage() != null
              ? exception.getMessage()
              : exception.getClass().getSimpleName();
      List<String> errors = List.of(String.format(ErrorMessages.CHECKER_FAILED, reason));
      ids.forEach(id -> results.put(id, ConstraintResult.error(descriptors.get(id), errors)));
      return results;
    }
    for (String id : ids) {
      results.computeIfAbsent(
          id,
          missing ->
              ConstraintResult.error(
                  descriptors.get(missing), List.of(ErrorMessages.CHECKER_RETURNED_NO_RESULT)));
    }
    return results;
  }

  private ConstraintValidationResult resolve(
      String id, ConstraintResult result, UuidResolver uuidResolver) {
    List<ResolvedConstraintViolation> violations =
        result.violations().stream().map(v -> resolve(v, uuidResolver)).toList();
    // use the registered descriptor so that reports are consistent with getConstraints()
    return new ConstraintValidationResult(
        descriptors.get(id), result.status(), violations, result.errors());
  }

  private static ResolvedConstraintViolation resolve(
      ConstraintViolation violation, UuidResolver uuidResolver) {
    EObject element = violation.element();
    Resource resource = element.eResource();
    return new ResolvedConstraintViolation(
        element,
        uuidResolver.hasUuid(element) ? uuidResolver.getUuid(element) : null,
        resource == null ? null : resource.getURI(),
        violation.severity(),
        violation.message());
  }
}
