package tools.vitruv.framework.vsum;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import tools.vitruv.change.composite.propagation.ChangeableModelRepository;
import tools.vitruv.change.propagation.ChangePropagationMode;
import tools.vitruv.framework.views.ViewProvider;
import tools.vitruv.framework.views.ViewTypeProvider;
import tools.vitruv.framework.vsum.constraints.ConstraintChecker;
import tools.vitruv.framework.vsum.constraints.ConstraintDescriptor;
import tools.vitruv.framework.vsum.constraints.ConstraintNotEditableException;
import tools.vitruv.framework.vsum.constraints.ConstraintValidationReport;
import tools.vitruv.framework.vsum.constraints.DuplicateConstraintException;
import tools.vitruv.framework.vsum.constraints.EditableConstraintChecker;
import tools.vitruv.framework.vsum.constraints.InvalidConstraintException;
import tools.vitruv.framework.vsum.constraints.UnknownConstraintException;
import tools.vitruv.framework.vsum.constraints.UnsupportedConstraintLanguageException;

/** A virtual model in the VSUM framework that provides changeable model repositories and views. */
public interface VirtualModel extends ChangeableModelRepository, ViewProvider, ViewTypeProvider {

  /**
   * Gets the folder of the virtual model.
   *
   * @return the folder of the virtual model
   */
  Path getFolder();

  /**
   * Defines how changes are propagated when passed to {@link #propagateChange(VitruviusChange)}. By
   * default, {@link ChangePropagationMode#TRANSITIVE_CYCLIC} is used, i.e., changes are
   * transitively propagated until no further changes are produced.
   */
  void setChangePropagationMode(ChangePropagationMode changePropagationMode);

  /**
   * Gets the constraints of all {@link ConstraintChecker}s registered for this virtual model.
   *
   * @return the constraints in registration order, empty if no checker is registered
   */
  default List<ConstraintDescriptor> getConstraints() {
    return List.of();
  }

  /**
   * Gets the constraint with the given id.
   *
   * @param id the id of the constraint
   * @return the constraint, empty if there is none
   */
  default Optional<ConstraintDescriptor> getConstraint(String id) {
    return getConstraints().stream().filter(constraint -> constraint.id().equals(id)).findFirst();
  }

  /**
   * Creates a constraint with the {@link EditableConstraintChecker} of the given language. The
   * constraint is validated by the next validation and still exists after a restart.
   *
   * @param language the language of the constraint, e.g. {@code vitruvocl}
   * @param source the source of exactly one constraint
   * @return the created constraint
   * @throws UnsupportedConstraintLanguageException if no editable checker supports the language
   * @throws InvalidConstraintException if the source is not a valid constraint
   * @throws DuplicateConstraintException if another constraint has the same id
   */
  default ConstraintDescriptor createConstraint(String language, String source) {
    throw new UnsupportedConstraintLanguageException(language);
  }

  /**
   * Replaces the source of a constraint, including constraints the virtual model was built with.
   * The change still applies after a restart.
   *
   * @param id the id of the constraint
   * @param source the new source, which must define a constraint with the same id
   * @return the updated constraint
   * @throws UnknownConstraintException if there is no constraint with the id
   * @throws ConstraintNotEditableException if the checker of the constraint does not support changes
   * @throws InvalidConstraintException if the source is not a valid constraint with this id
   */
  default ConstraintDescriptor updateConstraint(String id, String source) {
    throw new UnknownConstraintException(id);
  }

  /**
   * Deletes a constraint, including constraints the virtual model was built with. The constraint
   * stays deleted after a restart.
   *
   * @param id the id of the constraint
   * @throws UnknownConstraintException if there is no constraint with the id
   * @throws ConstraintNotEditableException if the checker of the constraint does not support changes
   */
  default void deleteConstraint(String id) {
    throw new UnknownConstraintException(id);
  }

  /**
   * Validates all constraints against the current state of this virtual model.
   *
   * @return the validation report
   */
  default ConstraintValidationReport validateConstraints() {
    return validateConstraints(getConstraints().stream().map(ConstraintDescriptor::id).toList());
  }

  /**
   * Validates the given constraints against the current state of this virtual model. Duplicate
   * ids are validated once.
   *
   * @param constraintIds the ids of the constraints to validate; an empty collection results in an
   *     empty report
   * @return the validation report
   * @throws UnknownConstraintException if an id does not belong to a constraint of this virtual
   *     model
   */
  default ConstraintValidationReport validateConstraints(Collection<String> constraintIds) {
    if (constraintIds == null) {
      throw new NullPointerException("constraintIds");
    }
    if (!constraintIds.isEmpty()) {
      throw new UnknownConstraintException(constraintIds.iterator().next());
    }
    return new ConstraintValidationReport(List.of());
  }
}
