package tools.vitruv.framework.vsum.constraints;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

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
 * @param metamodels the names of the metamodels the constraint reads, i.e. the names of their root
 *     packages as used in qualified type names (e.g. {@code model} for {@code model::Component}).
 *     Always contains the metamodel of a qualified context type, which comes first; {@code null}
 *     means that only the metamodel of the context type is known. Constraints only read models,
 *     so these are all metamodels whose models the constraint touches.
 */
public record ConstraintDescriptor(
    String id,
    String name,
    String contextType,
    ConstraintKind kind,
    ConstraintSeverity severity,
    String language,
    String source,
    Set<String> metamodels) {

  private static final String QUALIFIER_SEPARATOR = "::";

  /**
   * Validates the id, applies the defaults for name, kind and severity, and adds the metamodel of
   * the context type to the metamodels.
   */
  public ConstraintDescriptor {
    checkArgument(id != null && !id.isBlank(), "constraint id must not be blank");
    name = name == null || name.isBlank() ? id : name;
    kind = kind == null ? ConstraintKind.INVARIANT : kind;
    severity = severity == null ? ConstraintSeverity.WARNING : severity;
    metamodels = withContextMetamodel(contextType, metamodels);
  }

  /**
   * Creates a descriptor whose metamodels are only the metamodel of its context type.
   *
   * @param id the identifier of the constraint, unique within a virtual model
   * @param name a human-readable name, defaults to the id
   * @param contextType the type the constraint is defined for, may be {@code null}
   * @param kind the kind of the constraint, defaults to {@link ConstraintKind#INVARIANT}
   * @param severity the default severity of violations, defaults to {@link
   *     ConstraintSeverity#WARNING}
   * @param language the language the constraint is written in, may be {@code null}
   * @param source the textual source of the constraint, may be {@code null}
   */
  public ConstraintDescriptor(
      String id,
      String name,
      String contextType,
      ConstraintKind kind,
      ConstraintSeverity severity,
      String language,
      String source) {
    this(id, name, contextType, kind, severity, language, source, null);
  }

  /**
   * Returns the metamodel of a qualified type name such as {@code model::Component}.
   *
   * @param type the type name, may be {@code null}
   * @return the part before the first {@code ::}, or {@code null} if the type is not qualified
   */
  public static String metamodelOf(String type) {
    if (type == null) {
      return null;
    }
    int separator = type.indexOf(QUALIFIER_SEPARATOR);
    return separator > 0 ? type.substring(0, separator).strip() : null;
  }

  private static Set<String> withContextMetamodel(String contextType, Set<String> metamodels) {
    Set<String> result = new LinkedHashSet<>();
    String contextMetamodel = metamodelOf(contextType);
    if (contextMetamodel != null && !contextMetamodel.isEmpty()) {
      result.add(contextMetamodel);
    }
    if (metamodels != null) {
      for (String metamodel : metamodels) {
        if (metamodel != null && !metamodel.isBlank()) {
          result.add(metamodel.strip());
        }
      }
    }
    return Collections.unmodifiableSet(result);
  }
}
