package tools.vitruv.framework.vsum.constraints;

import allElementTypes.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * An editable checker for tests. A constraint has the source {@code <id>:<prefix>} and is violated
 * by every {@link Root} whose id starts with the prefix.
 */
class PrefixConstraintChecker implements EditableConstraintChecker {
  static final String LANGUAGE = "prefix";

  private final Map<String, ConstraintDescriptor> constraints = new LinkedHashMap<>();
  private final String language;

  /**
   * Creates a checker with the given built-in constraints.
   *
   * @param builtInSources the sources of the built-in constraints
   */
  PrefixConstraintChecker(String... builtInSources) {
    this(LANGUAGE, builtInSources, true);
  }

  private PrefixConstraintChecker(String language, String[] builtInSources, boolean unused) {
    this.language = language;
    for (String source : builtInSources) {
      create(source);
    }
  }

  /**
   * Creates a checker for another language with the given built-in constraints.
   *
   * @param language the language of the checker
   * @param builtInSources the sources of the built-in constraints
   * @return the checker
   */
  static PrefixConstraintChecker forLanguage(String language, String... builtInSources) {
    return new PrefixConstraintChecker(language, builtInSources, true);
  }

  @Override
  public String getLanguage() {
    return language;
  }

  @Override
  public List<ConstraintDescriptor> getConstraints() {
    return List.copyOf(constraints.values());
  }

  @Override
  public ConstraintDescriptor create(String source) {
    ConstraintDescriptor descriptor = parse(source);
    if (constraints.containsKey(descriptor.id())) {
      throw new DuplicateConstraintException(descriptor.id());
    }
    constraints.put(descriptor.id(), descriptor);
    return descriptor;
  }

  @Override
  public ConstraintDescriptor update(String id, String source) {
    if (!constraints.containsKey(id)) {
      throw new UnknownConstraintException(id);
    }
    ConstraintDescriptor descriptor = parse(source);
    if (!descriptor.id().equals(id)) {
      throw new InvalidConstraintException(
          List.of(new ConstraintDiagnostic(1, 0, "The id of a constraint cannot be changed")));
    }
    constraints.put(id, descriptor);
    return descriptor;
  }

  @Override
  public void delete(String id) {
    if (constraints.remove(id) == null) {
      throw new UnknownConstraintException(id);
    }
  }

  @Override
  public List<ConstraintResult> check(
      ConstraintCheckContext context, Collection<String> constraintIds) {
    List<ConstraintResult> results = new ArrayList<>();
    for (String id : constraintIds) {
      ConstraintDescriptor descriptor = constraints.get(id);
      String prefix = descriptor.source().substring(descriptor.source().indexOf(':') + 1);
      List<ConstraintViolation> violations = new ArrayList<>();
      for (Resource resource : context.getModelResources()) {
        resource
            .getAllContents()
            .forEachRemaining(
                object -> {
                  if (object instanceof Root root
                      && root.getId() != null
                      && root.getId().startsWith(prefix)) {
                    violations.add(
                        new ConstraintViolation(root, ConstraintSeverity.WARNING, prefix));
                  }
                });
      }
      results.add(ConstraintResult.of(descriptor, violations));
    }
    return results;
  }

  private ConstraintDescriptor parse(String source) {
    int separator = source == null ? -1 : source.indexOf(':');
    if (separator <= 0) {
      throw new InvalidConstraintException(
          List.of(new ConstraintDiagnostic(1, 0, "Expected <id>:<prefix>")));
    }
    String id = source.substring(0, separator);
    return new ConstraintDescriptor(
        id, id, "allElementTypes::Root", ConstraintKind.INVARIANT, null, language, source);
  }
}
