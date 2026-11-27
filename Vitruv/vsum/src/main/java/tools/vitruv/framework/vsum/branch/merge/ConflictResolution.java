package tools.vitruv.framework.vsum.branch.merge;

/**
 * Represents the user's resolution choice for a single merge conflict.
 */
public record ConflictResolution(String elementUuid, Choice choice) {

    public enum Choice {
        /** Keep the value from the target branch ("ours"). */
        OURS,
        /** Accept the value from the source branch ("theirs"). */
        THEIRS
    }
}
