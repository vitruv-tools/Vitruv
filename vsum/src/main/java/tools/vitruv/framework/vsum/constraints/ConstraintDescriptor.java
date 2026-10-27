package tools.vitruv.framework.vsum.constraints;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Describes a constraint provided by a {@link ConstraintChecker}, independent of the language it
 * is written in.
 *
 * @param id the identifier of the constraint, unique within a virtual model
 * @param name a human-readable name, defaults to the id
 * @param contextType the type the constraint is defined for (e.g. {@code model::Component}), may
 *     be {@code null} if the constraint has no single context type
 * @param kind the kind of the constraint, defaults to {@link ConstraintKind#INVARIANT}
 * @param severity the default severity of violations, defaults to {@link
 *     ConstraintSeverity#WARNING}
 * @param language the language the constraint is written in (e.g. {@code vitruvocl}), may be
 *     {@code null}
 * @param source the textual source of the constraint, may be {@code null}
 */
public record ConstraintDescriptor(
    String id,
    String name,
    String contextType,
    ConstraintKind kind,
    ConstraintSeverity severity,
    String language,
    String source) {

  /** Validates the id and applies the defaults for name, kind and severity. */
  public ConstraintDescriptor {
    checkArgument(id != null && !id.isBlank(), "constraint id must not be blank");
    name = name == null || name.isBlank() ? id : name;
    kind = kind == null ? ConstraintKind.INVARIANT : kind;
    severity = severity == null ? ConstraintSeverity.WARNING : severity;
  }
}
