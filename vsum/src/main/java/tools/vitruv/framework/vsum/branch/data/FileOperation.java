package tools.vitruv.framework.vsum.branch.data;

/**
 * Represents the type of operation performed on a file in a Git commit.
 *
 * <p>Each constant maps to one of Git's file status codes so that changelogs produced by
 * Vitruvius use the same terminology as the underlying version control system.
 * <ul>
 *   <li>{@link #ADDED} - Git status code {@code A}: new file created.</li>
 *   <li>{@link #MODIFIED} - Git status code {@code M}: existing file content changed.</li>
 *   <li>{@link #DELETED} - Git status code {@code D}: file removed.</li>
 *   <li>{@link #RENAMED} - Git status code {@code R}: file moved to a new location.</li>
 *   <li>{@link #COPIED} - Git status code {@code C}: file duplicated to a new location.</li>
 * </ul>
 */
public enum FileOperation {

    /**
     * A new file was created in this commit.
     * Corresponds to Git status code {@code A}.
     */
    ADDED,

    /**
     * The content of an existing file was changed in this commit.
     * Corresponds to Git status code {@code M}.
     */
    MODIFIED,

    /**
     * A file was removed in this commit.
     * Corresponds to Git status code {@code D}.
     */
    DELETED,

    /**
     * A file was moved to a new location. The previous path is captured in the
     * {@code oldPath} field of the changelog entry.
     * Corresponds to Git status code {@code R}.
     */
    RENAMED,

    /**
     * A file was duplicated to a new location.
     * Corresponds to Git status code {@code C}.
     */
    COPIED
}