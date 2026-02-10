package tools.vitruv.framework.vsum.branch.data;

/**
 * Represents the type of operation performed on a file in a Git commit.
 *
 * <p>Maps to Git's file status codes:
 * <ul>
 *   <li>A (added) - new file created</li>
 *   <li>M (modified) - existing file changed</li>
 *   <li>D (deleted) - file removed</li>
 *   <li>R (renamed) - file moved to new location</li>
 *   <li>C (copied) - file duplicated to new location</li>
 * </ul>
 *
 * <p> todo: Use for conflict categorization and resolution strategies
 * <ul>
 *   <li>Content conflicts: M+M on same file → three-way merge</li>
 *   <li>Structural conflicts: A+A same file, D+M → manual resolution</li>
 *   <li>Rename conflicts: R+R to different names → user choice</li>
 *   <li>Add/delete conflicts: A+D → keep or delete decision</li>
 * </ul>
 */
public enum FileOperation {

    /**
     * File was newly created in this commit.
     * Corresponds to Git status code 'A'.
     */
    ADDED,

    /**
     * File content was changed in this commit.
     * Corresponds to Git status code 'M'.
     */
    MODIFIED,

    /**
     * File was removed in this commit.
     * Corresponds to Git status code 'D'.
     */
    DELETED,

    /**
     * File was moved to a new location.
     * Corresponds to Git status code 'R'.
     *
     * <p> todo: Track old and new paths in FileChange
     */
    RENAMED,

    /**
     * File was copied to a new location.
     * Corresponds to Git status code 'C'.
     *
     * <p> todo: Track source and destination in FileChange
     */
    COPIED
}