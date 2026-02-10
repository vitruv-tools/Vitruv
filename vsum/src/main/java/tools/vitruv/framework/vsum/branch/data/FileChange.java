package tools.vitruv.framework.vsum.branch.data;

import lombok.Getter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a single file change within a semantic changelog.
 *
 * <p>Tracks the file path, operation type (added, modified, deleted, renamed, copied),
 * and optionally the old path for rename operations.
 * todo: Element-level changes will be added for fine-grained conflict detection.
 */
@Getter
public class FileChange {

    /**
     * -- GETTER --
     *
     */
    private final String filePath;
    /**
     * -- GETTER --
     *
     */
    private final FileOperation operation;
    /**
     * -- GETTER --
     *
     */
    private final String oldPath;  // nullable and will only be used for RENAMED operations

    /**
     * Detailed element-level changes within this file.
     *
     * <p> todo: Populate with element-level changes such as:
     * <ul>
     *   <li>Method added/modified/deleted</li>
     *   <li>Field added/modified/deleted</li>
     *   <li>Class structure changes</li>
     * </ul>
     *
     * <p> todo: Use for conflict categorization:
     * <ul>
     *   <li>Content conflicts (same method modified by both branches)</li>
     *   <li>Structural conflicts (method added in both branches)</li>
     *   <li>Semantic conflicts (breaking correspondences)</li>
     *   <li>Enables different resolution strategies per conflict type</li>
     * </ul>
     *
     * <p>Currently always empty.
     * -- GETTER --
     *

     */
    private final List<?> elementChanges;

    /**
     * Creates a file change with all details.
     *
     * @param filePath the path of the changed file (must not be null or empty)
     * @param operation the type of operation (must not be null)
     * @param oldPath the old path for RENAMED operations (must be null for non-RENAMED operations)
     * @param elementChanges detailed element-level changes (null for now, populated in Iteration 2)
     * @throws IllegalArgumentException if filePath is null/empty, operation is null,
     *                                  or oldPath is provided for non-RENAMED operations
     */
    public FileChange(String filePath, FileOperation operation, String oldPath, List<?> elementChanges) {
        // Validate required fields
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("filePath must not be null or empty");
        }
        if (operation == null) {
            throw new IllegalArgumentException("operation must not be null");
        }

        // oldPath only valid for RENAMED operations
        if (oldPath != null && operation != FileOperation.RENAMED) {
            throw new IllegalArgumentException("oldPath can only be set for RENAMED operations, but operation is: " + operation);
        }

        // RENAMED operations must have oldPath
        if (operation == FileOperation.RENAMED && oldPath == null) {
            throw new IllegalArgumentException("RENAMED operation requires oldPath to be set");
        }

        this.filePath = filePath;
        this.operation = operation;
        this.oldPath = oldPath;
        this.elementChanges = elementChanges != null ? Collections.unmodifiableList(new ArrayList<>(elementChanges)) : Collections.emptyList();
    }

    /**
     * Creates a simple file change without old path or element details.
     *
     * <p> Used for ADDED, MODIFIED, DELETED, and COPIED operations.
     *
     * @param filePath the path of the changed file
     * @param operation the type of operation
     * @throws IllegalArgumentException if filePath is null or operation is null
     */
    public FileChange(String filePath, FileOperation operation) {
        this(filePath, operation, null, null);
    }

    /**
     * Creates a file change with old path (for renamed files).
     *
     * <p>Typically used for RENAMED operations.
     *
     * @param filePath the new path of the file
     * @param operation the type of operation (must be RENAMED)
     * @param oldPath the previous path of the file
     * @throws IllegalArgumentException if filePath is null, operation is null, or operation is not RENAMED
     */
    public FileChange(String filePath, FileOperation operation, String oldPath) {
        this(filePath, operation, oldPath, null);
    }

    /**
     * @return true if this is a rename operation
     */
    public boolean isRenamed() {
        return operation == FileOperation.RENAMED;
    }

    /**
     * @return true if element-level changes are available
     */
    public boolean hasElementChanges() {
        return !elementChanges.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileChange that = (FileChange) o;
        return Objects.equals(filePath, that.filePath) &&
                operation == that.operation &&
                Objects.equals(oldPath, that.oldPath) &&
                Objects.equals(elementChanges, that.elementChanges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filePath, operation, oldPath, elementChanges);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(operation).append(": ").append(filePath);
        if (oldPath != null) {
            sb.append(" (was: ").append(oldPath).append(")");
        }
        return sb.toString();
    }
}