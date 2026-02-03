package tools.vitruv.framework.vsum.branch;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link ReloadTriggerFile}.
 */
class ReloadTriggerFileTest {

    @Test
    @DisplayName("creates trigger file in .vitruvius directory")
    void createsTriggerFile(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        triggerFile.createTrigger();
        Path expectedPath = tempDir.resolve(".vitruvius/reload-trigger");
        assertTrue(Files.exists(expectedPath), "Trigger file should exist");
    }

    @Test
    @DisplayName("creates parent directory if needed")
    void createsParentDirectory(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        assertFalse(Files.exists(tempDir.resolve(".vitruvius")));
        triggerFile.createTrigger();
        assertTrue(Files.isDirectory(tempDir.resolve(".vitruvius")));
    }

    @Test
    @DisplayName("writes timestamp to trigger file")
    void writesTimestamp(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        triggerFile.createTrigger();
        Path filePath = tempDir.resolve(".vitruvius/reload-trigger");
        String content = Files.readString(filePath);
        assertTrue(content.contains("Reload requested at:"),"File should contain timestamp message");
    }

    @Test
    @DisplayName("checkAndClearTrigger returns false when file doesn't exist")
    void checkAndClearReturnsFalseWhenNotExists(@TempDir Path tempDir) {
        var triggerFile = new ReloadTriggerFile(tempDir);
        boolean result = triggerFile.checkAndClearTrigger();
        assertFalse(result, "Should return false when trigger doesn't exist");
    }

    @Test
    @DisplayName("checkAndClearTrigger returns true and deletes file when it exists")
    void checkAndClearReturnsTrueAndDeletes(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        triggerFile.createTrigger();
        Path filePath = tempDir.resolve(".vitruvius/reload-trigger");
        assertTrue(Files.exists(filePath), "File should exist before check");
        boolean result = triggerFile.checkAndClearTrigger();
        assertTrue(result, "Should return true when trigger exists");
        assertFalse(Files.exists(filePath), "File should be deleted after check");
    }

    @Test
    @DisplayName("multiple checks return false after first clear")
    void multipleChecksReturnFalse(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        triggerFile.createTrigger();
        assertTrue(triggerFile.checkAndClearTrigger());
        assertFalse(triggerFile.checkAndClearTrigger());
        assertFalse(triggerFile.checkAndClearTrigger());
    }

    @Test
    @DisplayName("exists returns true when file exists")
    void existsReturnsTrueWhenFileExists(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        assertFalse(triggerFile.exists(), "Should not exist initially");
        triggerFile.createTrigger();
        assertTrue(triggerFile.exists(), "Should exist after creation");
    }

    @Test
    @DisplayName("getTriggerPath returns correct path")
    void getTriggerPathReturnsCorrectPath(@TempDir Path tempDir) {
        var triggerFile = new ReloadTriggerFile(tempDir);
        Path expectedPath = tempDir.resolve(".vitruvius/reload-trigger");
        assertEquals(expectedPath, triggerFile.getTriggerPath());
    }

    @Test
    @DisplayName("recreating trigger after clear works")
    void recreatingTriggerWorks(@TempDir Path tempDir) throws Exception {
        var triggerFile = new ReloadTriggerFile(tempDir);
        triggerFile.createTrigger();
        assertTrue(triggerFile.checkAndClearTrigger());
        triggerFile.createTrigger();
        assertTrue(triggerFile.exists());
    }
}