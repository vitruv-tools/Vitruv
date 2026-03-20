package tools.vitruv.framework.vsum.versioning.data;

import lombok.Getter;

/**
 * Holds the result of a confirmed rollback operation.
 * Covers DE-8: rollback result after execution.
 */
@Getter
public class RollbackResult {

    public enum RollbackStatus {
        /** Rollback completed successfully and VSUM reloaded. */
        SUCCESS,
        /** Rollback completed but VSUM reload failed - model state may be stale. */
        SUCCESS_RELOAD_FAILED,
        /** Rollback failed - repository state unchanged. */
        FAILED
    }

    private final RollbackStatus status;
    private final VersionMetadata targetVersion;
    private final String newHeadSha;
    private final String message;

    private RollbackResult(RollbackStatus status, VersionMetadata targetVersion,
                           String newHeadSha, String message) {
        this.status = status;
        this.targetVersion = targetVersion;
        this.newHeadSha = newHeadSha;
        this.message = message;
    }

    public static RollbackResult success(VersionMetadata targetVersion, String newHeadSha) {
        return new RollbackResult(RollbackStatus.SUCCESS, targetVersion, newHeadSha,
                "Rollback to '" + targetVersion.getVersionId() + "' completed successfully");
    }

    public static RollbackResult successReloadFailed(VersionMetadata targetVersion,
                                                     String newHeadSha, String reloadError) {
        return new RollbackResult(RollbackStatus.SUCCESS_RELOAD_FAILED, targetVersion, newHeadSha,
                "Rollback to '" + targetVersion.getVersionId()
                        + "' completed but VSUM reload failed: " + reloadError
                        + " - restart the application to restore a consistent state");
    }

    public static RollbackResult failed(VersionMetadata targetVersion, String reason) {
        return new RollbackResult(RollbackStatus.FAILED, targetVersion, null,
                "Rollback to '" + targetVersion.getVersionId() + "' failed: " + reason);
    }

    public boolean isSuccessful() {
        return status == RollbackStatus.SUCCESS
                || status == RollbackStatus.SUCCESS_RELOAD_FAILED;
    }
}