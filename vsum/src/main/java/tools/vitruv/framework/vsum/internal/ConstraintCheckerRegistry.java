package tools.vitruv.framework.vsum.internal;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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
import tools.vitruv.framework.vsum.constraints.DuplicateConstraintException;
import tools.vitruv.framework.vsum.constraints.EditableConstraintChecker;
import tools.vitruv.framework.vsum.constraints.ResolvedConstraintViolation;
import tools.vitruv.framework.vsum.constraints.UnknownConstraintException;
import tools.vitruv.framework.vsum.constraints.UnsupportedConstraintLanguageException;
import tools.vitruv.framework.vsum.internal.ConstraintStore.Origin;
import tools.vitruv.framework.vsum.internal.ConstraintStore.StoredConstraint;
import tools.vitruv.framework.vsum.internal.messages.ErrorMessages;

/**
 * Manages the {@link ConstraintChecker}s of a virtual model: lists their constraints, runs
 * validations and creates, updates and deletes constraints of {@link EditableConstraintChecker}s.
 *
 * <p>Changing methods must be called while the virtual model is locked. Reading methods may be
 * called concurrently, since the constraints are published as immutable snapshot.
 */
public class ConstraintCheckerRegistry {
  private static final Logger LOGGER = LogManager.getLogger(ConstraintCheckerRegistry.class);

  /** The constraints and their checkers at one point in time. */
  private record Snapshot(
      Map<String, ConstraintDescriptor> descriptors, Map<String, ConstraintChecker> checkers) {}

  private final List<ConstraintChecker> checkers;
  private final Map<String, EditableConstraintChecker> editableCheckers = new LinkedHashMap<>();

  /**
   * The state of the constraints of the editable checkers by id, including deleted built-in
   * constraints and constraints of languages without checker, which are kept for later starts.
   */
  private final Map<String, StoredConstraint> storedConstraints = new LinkedHashMap<>();

  private volatile Snapshot snapshot;
  private ConstraintStore store;

  /**
   * Creates a registry for the given checkers.
   *
   * @param checkers the constraint checkers
   * @throws IllegalArgumentException if two constraints share the same id, or two editable checkers
   *     the same language
   */
  public ConstraintCheckerRegistry(Collection<ConstraintChecker> checkers) {
    this.checkers = List.copyOf(checkers);
    for (ConstraintChecker checker : this.checkers) {
      if (checker instanceof EditableConstraintChecker editable) {
        checkArgument(
            editableCheckers.putIfAbsent(editable.getLanguage(), editable) == null,
            ErrorMessages.DUPLICATE_CONSTRAINT_LANGUAGE,
            editable.getLanguage());
      }
    }
    this.snapshot = createSnapshot();
    editableCheckers.forEach(
        (language, checker) ->
            checker
                .getConstraints()
                .forEach(
                    descriptor ->
                        storedConstraints.put(
                            descriptor.id(),
                            StoredConstraint.builtIn(
                                descriptor.id(), language, sourceOf(descriptor)))));
  }

  /**
   * Gets the constraints of all registered checkers, in registration order.
   *
   * @return the constraints
   */
  public List<ConstraintDescriptor> getConstraints() {
    return List.copyOf(snapshot.descriptors().values());
  }

  /**
   * Gets the constraint with the given id.
   *
   * @param id the id of the constraint
   * @return the constraint, empty if there is none
   */
  public Optional<ConstraintDescriptor> getConstraint(String id) {
    return Optional.ofNullable(snapshot.descriptors().get(id));
  }

  // ---------------------------------------------------------------------------------------------
  // Persistence
  // ---------------------------------------------------------------------------------------------

  /**
   * Restores the changes made at runtime from the given store and persists every later change
   * there. Built-in constraints are reconciled with the stored ones:
   *
   * <ul>
   *   <li>built-in constraints deleted at runtime stay deleted;
   *   <li>built-in constraints changed at runtime keep the changed source;
   *   <li>other built-in constraints take the source the checker provides now;
   *   <li>constraints created at runtime are created again;
   *   <li>built-in constraints the checker no longer provides are dropped unless they were changed;
   *   <li>constraints of languages without checker are kept for later starts.
   * </ul>
   *
   * Constraints that cannot be restored, e.g. because their source became invalid, are logged and
   * kept in the store, so they are not lost.
   *
   * @param constraintStore the store to restore from and persist to
   */
  void restore(ConstraintStore constraintStore) {
    this.store = constraintStore;
    Map<String, StoredConstraint> previous = new LinkedHashMap<>();
    for (StoredConstraint stored : constraintStore.load()) {
      previous.put(stored.id(), stored);
    }

    storedConstraints.clear();
    editableCheckers.forEach(
        (language, checker) -> {
          for (ConstraintDescriptor descriptor : List.copyOf(checker.getConstraints())) {
            StoredConstraint reconciled =
                reconcileBuiltIn(checker, language, descriptor, previous.remove(descriptor.id()));
            storedConstraints.put(descriptor.id(), reconciled);
          }
        });
    this.snapshot = createSnapshot();
    for (StoredConstraint stored : previous.values()) {
      restoreAdditional(stored);
    }
    this.snapshot = createSnapshot();
    persist();
  }

  private StoredConstraint reconcileBuiltIn(
      EditableConstraintChecker checker,
      String language,
      ConstraintDescriptor descriptor,
      StoredConstraint stored) {
    String providedSource = sourceOf(descriptor);
    if (stored == null) {
      return StoredConstraint.builtIn(descriptor.id(), language, providedSource);
    }
    if (stored.deleted()) {
      checker.delete(descriptor.id());
      return stored;
    }
    if (stored.isModified() || stored.origin() == Origin.RUNTIME) {
      if (!providedSource.equals(stored.source())) {
        try {
          checker.update(descriptor.id(), stored.source());
        } catch (RuntimeException e) {
          LOGGER.warn("Could not restore the changed constraint {}", descriptor.id(), e);
        }
      }
      return stored;
    }
    // not changed at runtime, so the checker's current source replaces the stored one
    return StoredConstraint.builtIn(descriptor.id(), language, providedSource);
  }

  private void restoreAdditional(StoredConstraint stored) {
    EditableConstraintChecker checker = editableCheckers.get(stored.language());
    if (checker == null) {
      LOGGER.warn(
          "Keeping constraint {} of language {}, which no constraint checker supports",
          stored.id(),
          stored.language());
      storedConstraints.put(stored.id(), stored);
      return;
    }
    if (stored.deleted() || (stored.origin() == Origin.BUILT_IN && !stored.isModified())) {
      // the checker no longer provides this built-in constraint
      return;
    }
    try {
      createInChecker(checker, stored.source());
      this.snapshot = createSnapshot();
      storedConstraints.put(stored.id(), stored.asRuntime());
    } catch (RuntimeException e) {
      LOGGER.warn("Could not restore constraint {}", stored.id(), e);
      storedConstraints.put(stored.id(), stored);
    }
  }

  private void persist() {
    if (store != null) {
      store.save(new ArrayList<>(storedConstraints.values()));
    }
  }

  // ---------------------------------------------------------------------------------------------
  // Changes at runtime
  // ---------------------------------------------------------------------------------------------

  /**
   * Creates a constraint with the checker of the given language.
   *
   * @param language the language of the constraint
   * @param source the source of the constraint
   * @return the created constraint
   * @throws UnsupportedConstraintLanguageException if no editable checker supports the language
   * @throws tools.vitruv.framework.vsum.constraints.InvalidConstraintException if the source is
   *     invalid
   * @throws DuplicateConstraintException if another constraint has the same id
   */
  public ConstraintDescriptor create(String language, String source) {
    checkNotNull(source, "source");
    EditableConstraintChecker checker = editableCheckers.get(language);
    if (checker == null) {
      throw new UnsupportedConstraintLanguageException(language);
    }
    ConstraintDescriptor created = createInChecker(checker, source);
    StoredConstraint previous = storedConstraints.get(created.id());
    storedConstraints.put(
        created.id(),
        previous != null && previous.origin() == Origin.BUILT_IN
            // a deleted built-in constraint is created again
            ? new StoredConstraint(
                created.id(), language, Origin.BUILT_IN, false, source, previous.builtInSource())
            : StoredConstraint.runtime(created.id(), language, source));
    this.snapshot = createSnapshot();
    persist();
    return created;
  }

  /**
   * Replaces the source of a constraint.
   *
   * @param id the id of the constraint
   * @param source the new source
   * @return the updated constraint
   * @throws UnknownConstraintException if there is no constraint with the id
   * @throws UnsupportedOperationException if the checker of the constraint is not editable
   * @throws tools.vitruv.framework.vsum.constraints.InvalidConstraintException if the source is
   *     invalid
   */
  public ConstraintDescriptor update(String id, String source) {
    checkNotNull(source, "source");
    EditableConstraintChecker checker = editableCheckerOf(id);
    ConstraintDescriptor updated = checker.update(id, source);
    StoredConstraint stored = storedConstraints.get(id);
    storedConstraints.put(
        id,
        stored != null
            ? stored.withSource(source)
            : StoredConstraint.runtime(id, checker.getLanguage(), source));
    this.snapshot = createSnapshot();
    persist();
    return updated;
  }

  /**
   * Deletes a constraint. Built-in constraints stay deleted after a restart.
   *
   * @param id the id of the constraint
   * @throws UnknownConstraintException if there is no constraint with the id
   * @throws UnsupportedOperationException if the checker of the constraint is not editable
   */
  public void delete(String id) {
    EditableConstraintChecker checker = editableCheckerOf(id);
    checker.delete(id);
    StoredConstraint stored = storedConstraints.get(id);
    if (stored != null && stored.origin() == Origin.BUILT_IN) {
      storedConstraints.put(id, stored.asDeleted());
    } else {
      storedConstraints.remove(id);
    }
    this.snapshot = createSnapshot();
    persist();
  }

  private EditableConstraintChecker editableCheckerOf(String id) {
    ConstraintChecker checker = snapshot.checkers().get(id);
    if (checker == null) {
      throw new UnknownConstraintException(id);
    }
    if (!(checker instanceof EditableConstraintChecker editable)) {
      throw new UnsupportedOperationException(
          String.format(ErrorMessages.CONSTRAINT_NOT_EDITABLE, id));
    }
    return editable;
  }

  /** Creates a constraint in the checker, keeping ids unique across all checkers. */
  private ConstraintDescriptor createInChecker(EditableConstraintChecker checker, String source) {
    ConstraintDescriptor created = checker.create(source);
    ConstraintChecker owner = snapshot.checkers().get(created.id());
    if (owner != null && owner != checker) {
      checker.delete(created.id());
      throw new DuplicateConstraintException(created.id());
    }
    return created;
  }

  private Snapshot createSnapshot() {
    Map<String, ConstraintDescriptor> descriptors = new LinkedHashMap<>();
    Map<String, ConstraintChecker> owners = new LinkedHashMap<>();
    for (ConstraintChecker checker : checkers) {
      List<ConstraintDescriptor> constraints =
          checkNotNull(checker.getConstraints(), ErrorMessages.CHECKER_CONSTRAINTS_NULL, checker);
      for (ConstraintDescriptor descriptor : constraints) {
        checkArgument(
            !descriptors.containsKey(descriptor.id()),
            ErrorMessages.DUPLICATE_CONSTRAINT_ID,
            descriptor.id());
        descriptors.put(descriptor.id(), descriptor);
        owners.put(descriptor.id(), checker);
      }
    }
    // unmodifiable views of maps that are not changed later, keeping the registration order
    return new Snapshot(
        Collections.unmodifiableMap(descriptors), Collections.unmodifiableMap(owners));
  }

  private static String sourceOf(ConstraintDescriptor descriptor) {
    return Objects.requireNonNullElse(descriptor.source(), "");
  }

  // ---------------------------------------------------------------------------------------------
  // Validation
  // ---------------------------------------------------------------------------------------------

  /**
   * Validates the given constraints. Checkers are called once with all of their requested
   * constraints. Failures of a checker are reported as errors of its constraints.
   *
   * @param context read access to the virtual model
   * @param uuidResolver the resolver used to identify violating elements
   * @param constraintIds the ids of the constraints to validate; duplicates are validated once
   * @return the validation report, in the order of the requested ids
   * @throws UnknownConstraintException if an id does not belong to a registered constraint
   */
  public ConstraintValidationReport validate(
      ConstraintCheckContext context, UuidResolver uuidResolver, Collection<String> constraintIds) {
    Snapshot current = snapshot;
    List<String> requestedIds = List.copyOf(new LinkedHashSet<>(constraintIds));
    for (String id : requestedIds) {
      if (!current.descriptors().containsKey(id)) {
        throw new UnknownConstraintException(id);
      }
    }

    Map<ConstraintChecker, List<String>> idsByChecker = new LinkedHashMap<>();
    for (String id : requestedIds) {
      idsByChecker.computeIfAbsent(current.checkers().get(id), c -> new ArrayList<>()).add(id);
    }

    Map<String, ConstraintResult> resultsById = new LinkedHashMap<>();
    idsByChecker.forEach(
        (checker, ids) -> resultsById.putAll(runChecker(current, checker, context, ids)));

    List<ConstraintValidationResult> results =
        requestedIds.stream()
            .map(id -> resolve(current, id, resultsById.get(id), uuidResolver))
            .toList();
    return new ConstraintValidationReport(results);
  }

  private static Map<String, ConstraintResult> runChecker(
      Snapshot current,
      ConstraintChecker checker,
      ConstraintCheckContext context,
      List<String> ids) {
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
      ids.forEach(
          id -> results.put(id, ConstraintResult.error(current.descriptors().get(id), errors)));
      return results;
    }
    for (String id : ids) {
      results.computeIfAbsent(
          id,
          missing ->
              ConstraintResult.error(
                  current.descriptors().get(missing),
                  List.of(ErrorMessages.CHECKER_RETURNED_NO_RESULT)));
    }
    return results;
  }

  private static ConstraintValidationResult resolve(
      Snapshot current, String id, ConstraintResult result, UuidResolver uuidResolver) {
    List<ResolvedConstraintViolation> violations =
        result.violations().stream().map(v -> resolve(v, uuidResolver)).toList();
    // use the registered descriptor so that reports are consistent with getConstraints()
    return new ConstraintValidationResult(
        current.descriptors().get(id), result.status(), violations, result.errors());
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
