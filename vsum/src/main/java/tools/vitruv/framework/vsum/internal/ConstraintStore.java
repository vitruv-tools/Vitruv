package tools.vitruv.framework.vsum.internal;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Persists the constraints of the {@link tools.vitruv.framework.vsum.constraints
 * .EditableConstraintChecker}s of a virtual model, so that changes made at runtime survive a
 * restart. The constraints are stored as properties file, one group of keys per constraint.
 */
class ConstraintStore {
  private static final String ID = ".id";
  private static final String LANGUAGE = ".language";
  private static final String ORIGIN = ".origin";
  private static final String DELETED = ".deleted";
  private static final String SOURCE = ".source";
  private static final String BUILT_IN_SOURCE = ".builtInSource";
  private static final String COMMENT = "Constraints of the virtual model, maintained by Vitruvius";

  /** Where a stored constraint comes from. */
  enum Origin {
    /** Provided by a checker when the virtual model was built. */
    BUILT_IN,
    /** Created while the virtual model was running. */
    RUNTIME
  }

  /**
   * The persisted state of a constraint.
   *
   * @param id the id of the constraint
   * @param language the language of the constraint
   * @param origin where the constraint comes from
   * @param deleted whether a built-in constraint was deleted at runtime
   * @param source the current source of the constraint
   * @param builtInSource the source the checker provided, for built-in constraints; {@code null}
   *     for constraints created at runtime
   */
  record StoredConstraint(
      String id,
      String language,
      Origin origin,
      boolean deleted,
      String source,
      String builtInSource) {

    static StoredConstraint builtIn(String id, String language, String source) {
      return new StoredConstraint(id, language, Origin.BUILT_IN, false, source, source);
    }

    static StoredConstraint runtime(String id, String language, String source) {
      return new StoredConstraint(id, language, Origin.RUNTIME, false, source, null);
    }

    /** Whether a built-in constraint was changed at runtime. */
    boolean isModified() {
      return origin == Origin.BUILT_IN && !deleted && !source.equals(builtInSource);
    }

    StoredConstraint withSource(String newSource) {
      return new StoredConstraint(id, language, origin, deleted, newSource, builtInSource);
    }

    StoredConstraint asDeleted() {
      return new StoredConstraint(id, language, origin, true, source, builtInSource);
    }

    StoredConstraint asRuntime() {
      return runtime(id, language, source);
    }
  }

  private final Path file;

  /**
   * Creates a store persisting into the given file.
   *
   * @param file the properties file
   */
  ConstraintStore(Path file) {
    this.file = file;
  }

  /**
   * Loads the stored constraints.
   *
   * @return the constraints in stored order, empty if nothing was stored yet
   * @throws UncheckedIOException if the file cannot be read
   */
  List<StoredConstraint> load() {
    if (!Files.exists(file)) {
      return List.of();
    }
    Properties properties = new Properties();
    try (Reader reader = Files.newBufferedReader(file, UTF_8)) {
      properties.load(reader);
    } catch (IOException e) {
      throw new UncheckedIOException("Could not read the constraints from " + file, e);
    }
    List<StoredConstraint> constraints = new ArrayList<>();
    for (int index = 0; properties.containsKey(index + ID); index++) {
      String prefix = String.valueOf(index);
      constraints.add(
          new StoredConstraint(
              properties.getProperty(prefix + ID),
              properties.getProperty(prefix + LANGUAGE),
              Origin.valueOf(properties.getProperty(prefix + ORIGIN)),
              Boolean.parseBoolean(properties.getProperty(prefix + DELETED)),
              properties.getProperty(prefix + SOURCE),
              properties.getProperty(prefix + BUILT_IN_SOURCE)));
    }
    return constraints;
  }

  /**
   * Replaces the stored constraints. The file is written completely before it replaces the
   * previous one, so a failure never leaves a partially written file.
   *
   * @param constraints the constraints to store
   * @throws UncheckedIOException if the file cannot be written
   */
  void save(List<StoredConstraint> constraints) {
    Properties properties = new Properties();
    for (int index = 0; index < constraints.size(); index++) {
      StoredConstraint constraint = constraints.get(index);
      String prefix = String.valueOf(index);
      properties.setProperty(prefix + ID, constraint.id());
      properties.setProperty(prefix + LANGUAGE, constraint.language());
      properties.setProperty(prefix + ORIGIN, constraint.origin().name());
      properties.setProperty(prefix + DELETED, String.valueOf(constraint.deleted()));
      properties.setProperty(prefix + SOURCE, constraint.source());
      if (constraint.builtInSource() != null) {
        properties.setProperty(prefix + BUILT_IN_SOURCE, constraint.builtInSource());
      }
    }
    try {
      Files.createDirectories(file.toAbsolutePath().getParent());
      Path temporary = file.resolveSibling(file.getFileName() + ".tmp");
      try (Writer writer = Files.newBufferedWriter(temporary, UTF_8)) {
        properties.store(writer, COMMENT);
      }
      Files.move(temporary, file, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      throw new UncheckedIOException("Could not write the constraints to " + file, e);
    }
  }
}
