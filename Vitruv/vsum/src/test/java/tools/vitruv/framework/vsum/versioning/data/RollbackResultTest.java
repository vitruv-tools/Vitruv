package tools.vitruv.framework.vsum.versioning.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tools.vitruv.framework.vsum.branch.data.MaturityLevel;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link RollbackResult} factory methods and {@code isSuccessful()}.
 */
class RollbackResultTest {

    private static VersionMetadata meta(String versionId) {
        return new VersionMetadata(versionId, "abc1234567890abcdef1234567890abcdef12345",
                "main", "Alice", "alice@example.com",
                LocalDateTime.of(2024, 6, 1, 12, 0), "desc", MaturityLevel.DRAFT);
    }

    @Test
    @DisplayName("success() produces SUCCESS status and isSuccessful=true")
    void successFactory() {
        var version = meta("v1.0");
        var result = RollbackResult.success(version, "newHead123");

        assertEquals(RollbackResult.RollbackStatus.SUCCESS, result.getStatus());
        assertTrue(result.isSuccessful());
        assertSame(version, result.getTargetVersion());
        assertEquals("newHead123", result.getNewHeadSha());
        assertTrue(result.getMessage().contains("v1.0"));
    }

    @Test
    @DisplayName("successReloadFailed() produces SUCCESS_RELOAD_FAILED and isSuccessful=true")
    void successReloadFailedFactory() {
        var result = RollbackResult.successReloadFailed(meta("v2.0"), "sha456", "heap OOM");

        assertEquals(RollbackResult.RollbackStatus.SUCCESS_RELOAD_FAILED, result.getStatus());
        assertTrue(result.isSuccessful());
        assertEquals("sha456", result.getNewHeadSha());
        assertTrue(result.getMessage().contains("heap OOM"),
                "reload error reason must appear in the message");
    }

    @Test
    @DisplayName("failed() produces FAILED status, isSuccessful=false, and null newHeadSha")
    void failedFactory() {
        var result = RollbackResult.failed(meta("v3.0"), "ref not found");

        assertEquals(RollbackResult.RollbackStatus.FAILED, result.getStatus());
        assertFalse(result.isSuccessful());
        assertNull(result.getNewHeadSha());
        assertTrue(result.getMessage().contains("ref not found"),
                "failure reason must appear in the message");
    }
}
