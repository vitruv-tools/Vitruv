package tools.vitruv.framework.vsum.constraints;

/**
 * A {@link ConstraintChecker} whose constraints can be created, updated and deleted while the
 * virtual model is running, e.g. via {@link
 * tools.vitruv.framework.vsum.VirtualModel#createConstraint(String, String)}.
 *
 * <p>The virtual model calls these methods while it is locked and persists the resulting set of
 * constraints, so implementations only have to keep their constraints in memory. On every start,
 * the virtual model restores changes made at runtime through these methods.
 */
public interface EditableConstraintChecker extends ConstraintChecker {

  /**
   * Gets the language of the constraints of this checker, e.g. {@code vitruvocl}. Constraints are
   * created by the checker of their language, so it must be unique within a virtual model.
   *
   * @return the language
   */
  String getLanguage();

  /**
   * Creates a constraint from the given source.
   *
   * @param source the source of exactly one constraint
   * @return the created constraint
   * @throws InvalidConstraintException if the source is not a valid constraint
   * @throws DuplicateConstraintException if this checker already has a constraint with the id
   */
  ConstraintDescriptor create(String source);

  /**
   * Replaces the source of a constraint. The id of the constraint does not change.
   *
   * @param id the id of the constraint
   * @param source the new source of the constraint
   * @return the updated constraint
   * @throws UnknownConstraintException if this checker has no constraint with the id
   * @throws InvalidConstraintException if the source is not a valid constraint with this id
   */
  ConstraintDescriptor update(String id, String source);

  /**
   * Deletes a constraint.
   *
   * @param id the id of the constraint
   * @throws UnknownConstraintException if this checker has no constraint with the id
   */
  void delete(String id);
}
