package tools.vitruv.framework.vsum.branch.data;

import lombok.Getter;

import java.util.List;
import java.util.Objects;

/**
 * Holds the result of a merge operation performed by
 * {@link tools.vitruv.framework.vsum.branch.MergeManager}.
 *
 * <p>The status field indicates whether the merge succeeded cleanly,
 * was fast-forwarded, resulted in conflicts requiring manual resolution,
 * or failed with an unexpected error.
 *
 * <p>When status is {@link MergeStatus#CONFLICTING}, the merge has been
 * applied to the working directory but no merge commit has been created.
 * The developer must resolve the conflicts manually and commit the resolution.
 * The conflict resolution UI (MG-8) and automatic resolvers (MG-8) are
 * planned for a future iteration.
 *
 * <p>REST API: POST /merges (planned, implemented in Vitruv-Server in a future iteration)
 */
@Getter
public class ModelMergeResult {

    /**
     * Represents the outcome of a merge operation.
     */
    public enum MergeStatus {
        /** Clean merge: a merge commit was created successfully. */
        SUCCESS,
        /** Fast-forward merge: HEAD was moved forward, no merge commit created. */
        FAST_FORWARD,
        /**
         * Conflicts detected: the working directory contains conflict markers.
         * The developer must resolve conflicts manually and commit the resolution.
         * TODO: automatic and semi-automatic resolvers (MG-8) planned for next iteration.
         */
        CONFLICTING,
        /** Unexpected error : the merge could not be attempted or completed. */
        FAILED
    }

    /** The outcome of the merge operation. */
    private final MergeStatus status;

    /** The branch that was merged in (source). */
    private final String sourceBranch;

    /** The branch that received the merge (target). */
    private final String targetBranch;

    /**
     * The SHA of the merge commit created by Git.
     * Null for FAST_FORWARD (no merge commit) and CONFLICTING (no commit yet).
     */
    private final String mergeCommitSha;

    /** Whether the merge was a fast-forward (no diverging history). */
    private final boolean fastForward;

    /**
     * Relative paths of files that have conflicts.
     * Empty for SUCCESS and FAST_FORWARD.
     * TODO: used by conflict resolution UI (MG-3, MG-8) in next iteration.
     */
    private final List<String> conflictingFiles;

    /** Human-readable summary of the merge outcome. */
    private final String message;

    private ModelMergeResult(MergeStatus status, String sourceBranch, String targetBranch, String mergeCommitSha, boolean fastForward, List<String> conflictingFiles, String message) {
        this.status = Objects.requireNonNull(status);
        this.sourceBranch = Objects.requireNonNull(sourceBranch);
        this.targetBranch = Objects.requireNonNull(targetBranch);
        this.mergeCommitSha = mergeCommitSha; // nullable
        this.fastForward = fastForward;
        this.conflictingFiles = List.copyOf(conflictingFiles);
        this.message = Objects.requireNonNull(message);
    }

    public static ModelMergeResult success(String sourceBranch, String targetBranch, String mergeCommitSha) {
        return new ModelMergeResult(MergeStatus.SUCCESS, sourceBranch, targetBranch, mergeCommitSha, false, List.of(), "Merged '" + sourceBranch + "' into '" + targetBranch + "' successfully");
    }

    public static ModelMergeResult fastForward(String sourceBranch, String targetBranch, String newHeadSha) {
        return new ModelMergeResult(MergeStatus.FAST_FORWARD, sourceBranch, targetBranch, newHeadSha, true, List.of(), "Fast-forward merge of '" + sourceBranch + "' into '" + targetBranch + "'");
    }

    public static ModelMergeResult conflicting(String sourceBranch, String targetBranch, List<String> conflictingFiles) {
        return new ModelMergeResult(MergeStatus.CONFLICTING, sourceBranch, targetBranch, null, false, conflictingFiles,
                "Merge of '" + sourceBranch + "' into '" + targetBranch + "' resulted in " + conflictingFiles.size() + " conflict(s). " + "Resolve conflicts manually and commit the resolution.");
    }

    public static ModelMergeResult failed(String sourceBranch, String targetBranch, String reason) {
        return new ModelMergeResult(MergeStatus.FAILED, sourceBranch, targetBranch,
                null, false, List.of(), "Merge of '" + sourceBranch + "' into '" + targetBranch + "' failed: " + reason);
    }

    public boolean isSuccessful() {
        return status == MergeStatus.SUCCESS || status == MergeStatus.FAST_FORWARD;
    }

    @Override
    public String toString() {
        return "ModelMergeResult{status=" + status
                + ", source='" + sourceBranch + "'"
                + ", target='" + targetBranch + "'"
                + (mergeCommitSha != null
                ? ", sha='" + mergeCommitSha.substring(0, Math.min(7, mergeCommitSha.length())) + "'"
                : "")
                + ", conflicts=" + conflictingFiles.size()
                + ", message='" + message + "'"
                + "}";
    }
}