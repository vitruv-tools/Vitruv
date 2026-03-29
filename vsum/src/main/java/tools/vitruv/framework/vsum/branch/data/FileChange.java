package tools.vitruv.framework.vsum.branch.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeEntry;

/**
 * Represents a single file-level change captured in a {@link SemanticChangelog}.
 *
 * <p>Each instance records which file was affected, what operation was performed
 * (see {@link FileOperation}), and optionally the previous path for rename operations.
 * An ordered list of element-level changes within the file is also stored, though it is
 * currently always empty.
 * TODO: Fine-grained element tracking to enable more precise conflict categorization
 * during branch merges.
 *
 * <p>Instances are immutable after construction. The {@code elementChanges} list is
 * defensively copied on construction and exposed as an unmodifiable view.
 */
@Getter
public class FileChange {

  /**
   * The path of the changed file relative to the repository root.
   */
  private final String filePath;

  /**
   * The type of operation performed on the file.
   */
  private final FileOperation operation;

  /**
   * The previous path of the file before it was renamed. Only non-null when
   * {@code operation} is {@link FileOperation#RENAMED}, null for all other operations.
   */
  private final String oldPath;

  /**
   * Semantic atomic changes within this file, ordered by recording sequence.
   * Each entry captures one EMF {@code EChange} in a human-readable and
   * machine-queryable form. Populated at commit time via
   * {@link tools.vitruv.framework.vsum.branch.storage.SemanticChangeBuffer}.
   */
  private final List<SemanticChangeEntry> elementChanges;

  /**
   * Creates a file change with all available details.
   *
   * @param filePath       the path of the changed file. must not be null or blank.
   * @param operation      the type of operation performed on the file. must not be null.
   * @param oldPath        the previous path for {@link FileOperation#RENAMED} operations;
   *                       must be null for all other operation types.
   * @param elementChanges semantic atomic changes within the file; may be null, in which
   *                       case an empty list is used.
   * @throws IllegalArgumentException if {@code filePath} is null or blank, if
   *                                  {@code operation} is null, if {@code oldPath} is
   *                                  provided for a non-renamed operation, or if
   *                                  {@code oldPath} is missing for a renamed operation.
   */
  public FileChange(String filePath, FileOperation operation, String oldPath,
      List<SemanticChangeEntry> elementChanges) {
    if (filePath == null || filePath.trim().isEmpty()) {
      throw new IllegalArgumentException("filePath must not be null or empty");
    }
    if (operation == null) {
      throw new IllegalArgumentException("operation must not be null");
    }
    // oldPath is only meaningful for rename operations
    if (oldPath != null && operation != FileOperation.RENAMED) {
      throw new IllegalArgumentException(
          "oldPath can only be set for RENAMED operations, but operation is: " + operation);
    }
    // a rename without a source path cannot be interpreted correctly,
    // so both fields must be present or absent together.
    if (operation == FileOperation.RENAMED && oldPath == null) {
      throw new IllegalArgumentException("RENAMED operation requires oldPath to be set");
    }

    this.filePath = filePath;
    this.operation = operation;
    this.oldPath = oldPath;
    // copy the list so that external mutations do not affect this instance.
    this.elementChanges = elementChanges != null
        ? Collections.unmodifiableList(new ArrayList<>(elementChanges))
        : Collections.emptyList();
  }

  /**
   * Creates a file change without an old path or element-level details. Suitable for
   * {@link FileOperation#ADDED}, {@link FileOperation#MODIFIED}, and
   * {@link FileOperation#DELETED} operations.
   *
   * @param filePath  the path of the changed file, must not be null or blank.
   * @param operation the type of operation, must not be null.
   * @throws IllegalArgumentException if {@code filePath} is null or blank, or if
   *                                  {@code operation} is null.
   */
  public FileChange(String filePath, FileOperation operation) {
    this(filePath, operation, null, null);
  }

  /**
   * Creates a file change with a previous path for rename operations.
   *
   * @param filePath  the new path of the file, must not be null or blank.
   * @param operation the type of operation, must be {@link FileOperation#RENAMED}.
   * @param oldPath   the previous path of the file, must not be null.
   * @throws IllegalArgumentException if any argument is null, if {@code filePath} is
   *                                  blank, or if {@code operation} is not
   *                                  {@link FileOperation#RENAMED}.
   */
  public FileChange(String filePath, FileOperation operation, String oldPath) {
    this(filePath, operation, oldPath, null);
  }

  /**
   * Returns true if this change represents a file rename, i.e., if the operation is
   * {@link FileOperation#RENAMED}. When true, {@link #getOldPath()} returns the previous
   * path.
   */
  public boolean isRenamed() {
    return operation == FileOperation.RENAMED;
  }

  /**
   * Returns true if element-level change details are available for this file change.
   * Currently, always returns false because element-level tracking is not yet implemented.
   */
  public boolean hasElementChanges() {
    return !elementChanges.isEmpty();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FileChange that = (FileChange) o;
    return Objects.equals(filePath, that.filePath)
        && operation == that.operation
        && Objects.equals(oldPath, that.oldPath)
        && Objects.equals(elementChanges, that.elementChanges);
  }

  @Override
  public int hashCode() {
    return Objects.hash(filePath, operation, oldPath, elementChanges);
  }

  @Override
  public String toString() {
    var sb = new StringBuilder();
    sb.append(operation).append(": ").append(filePath);
    if (oldPath != null) {
      sb.append(" (was: ").append(oldPath).append(")");
    }
    return sb.toString();
  }
}
