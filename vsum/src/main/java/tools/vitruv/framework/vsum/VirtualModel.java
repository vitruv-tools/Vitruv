package tools.vitruv.framework.vsum;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import tools.vitruv.change.composite.propagation.ChangeableModelRepository;
import tools.vitruv.change.propagation.ChangePropagationMode;
import tools.vitruv.framework.views.ViewProvider;
import tools.vitruv.framework.views.ViewTypeProvider;
import tools.vitruv.framework.vsum.constraints.ConstraintChecker;
import tools.vitruv.framework.vsum.constraints.ConstraintDescriptor;
import tools.vitruv.framework.vsum.constraints.ConstraintValidationReport;

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
   * Gets the constraints of all {@link ConstraintChecker}s registered for this virtual model. The
   * constraints are determined once when the virtual model is built.
   *
   * @return the constraints in registration order, empty if no checker is registered
   */
  default List<ConstraintDescriptor> getConstraints() {
    return List.of();
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
   * @throws IllegalArgumentException if an id does not belong to a constraint of this virtual
   *     model
   */
  default ConstraintValidationReport validateConstraints(Collection<String> constraintIds) {
    if (constraintIds == null) {
      throw new NullPointerException("constraintIds");
    }
    if (!constraintIds.isEmpty()) {
      throw new IllegalArgumentException("There is no constraint with the ids " + constraintIds);
    }
    return new ConstraintValidationReport(List.of());
  }
}
