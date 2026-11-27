package tools.vitruv.framework.vsum.branch.storage;

/**
 * Classifies the origin of a model change to support the Vitruvius conflict
 * resolution rule: <em>original changes are preferred to consequential
 * changes.</em>
 * An original change is one that a human developer physically typed
 * into an editor. A consequential change is an automatic update that
 * the Vitruvius consistency engine triggered to maintain cross-view
 * consistency.
 * During merge-conflict resolution, original changes should never be
 * silently overruled by consequential changes. The {@link #priority} value
 * encodes this preference: higher priority wins in a conflict.
 *
 * @see SemanticChangeEntry#getOrigin()
 */
public enum ChangeOrigin {

    /**
     * A change that was physically made by a human developer in an editor.
     * These have the highest conflict-resolution priority.
     */
    ORIGINAL("Human-made change from an editor", 10),

    /**
     * An automatic change triggered by the Vitruvius consistency engine to
     * maintain cross-view consistency. These have lower priority than
     * original changes during conflict resolution.
     */
    CONSEQUENTIAL("Automatic change from the Vitruvius consistency engine", 5),

    /**
     * The origin of the change could not be determined. Used for backward
     * compatibility with changelog entries that were recorded before origin
     * tracking was introduced.
     */
    UNKNOWN("Origin could not be determined", 1);

    private final String description;
    private final int priority;

    ChangeOrigin(String description, int priority) {
        this.description = description;
        this.priority = priority;
    }

    /**
     * Returns a plain-English description of this origin category.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the conflict-resolution priority. Higher values win: an
     * {@link #ORIGINAL} change should not be overruled by a
     * {@link #CONSEQUENTIAL} change.
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Parses a string value to the matching constant. Returns {@link #UNKNOWN}
     * for any value that is not recognized.
     */
    public static ChangeOrigin fromString(String value) {
        if ("consequential".equalsIgnoreCase(value)) return CONSEQUENTIAL;
        if ("original".equalsIgnoreCase(value)) return ORIGINAL;
        return UNKNOWN;
    }
}
