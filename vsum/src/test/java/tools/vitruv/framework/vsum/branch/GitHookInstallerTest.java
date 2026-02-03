package tools.vitruv.framework.vsum.branch;

import org.eclipse.jgit.api.Git;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link GitHookInstaller}.
 */
class GitHookInstallerTest {

    @Test
    @DisplayName("throws exception when not a Git repository")
    void throwsExceptionWhenNotGitRepo(@TempDir Path tempDir) {
        // tempDir is not a Git repo (no .git directory)
        assertThrows(IllegalArgumentException.class, () -> new GitHookInstaller(tempDir));
    }

    @Test
    @DisplayName("accepts valid Git repository")
    void acceptsValidGitRepo(@TempDir Path tempDir) throws Exception {
        // Initialize Git repo
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            assertDoesNotThrow(() -> {new GitHookInstaller(tempDir);});
        }
    }

    @Test
    @DisplayName("installs post-checkout hook")
    void installsPostCheckoutHook(@TempDir Path tempDir) throws Exception {
        // initialize Git repo
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);
            // Hook should not exist yet
            assertFalse(installer.isPostCheckoutHookInstalled());
            // Install hook
            installer.installPostCheckoutHook();
            // Hook should now exist
            assertTrue(installer.isPostCheckoutHookInstalled());
            // Check actual file exists
            Path hookPath = tempDir.resolve(".git/hooks/post-checkout");
            assertTrue(Files.exists(hookPath), "Hook file should exist");
        }
    }

    @Test
    @DisplayName("hook script contains expected content")
    void hookScriptContainsExpectedContent(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);
            installer.installPostCheckoutHook();
            Path hookPath = tempDir.resolve(".git/hooks/post-checkout");
            String content = Files.readString(hookPath);
            // verify it a bash script
            assertTrue(content.startsWith("#!/bin/bash"), "Should be a bash script");
            // verify it creates the trigger file
            assertTrue(content.contains("reload-trigger"), "Should reference reload-trigger file");
            assertTrue(content.contains(".vitruvius"), "Should reference .vitruvius directory");
        }
    }

    @Test
    @DisplayName("backs up existing hook before installing")
    void backsUpExistingHook(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);
            // Create an existing hook
            Path hookPath = tempDir.resolve(".git/hooks/post-checkout");
            String existingContent = "#!/bin/bash\necho 'old hook'";
            Files.writeString(hookPath, existingContent);
            // Install new hook
            installer.installPostCheckoutHook();
            // Backup should exist
            Path backupPath = tempDir.resolve(".git/hooks/post-checkout.backup");
            assertTrue(Files.exists(backupPath), "Backup should exist");
            // Backup should have old content
            String backupContent = Files.readString(backupPath);
            assertEquals(existingContent, backupContent, "Backup should contain old hook content");
            // new hook should be installed
            String newContent = Files.readString(hookPath);
            assertNotEquals(existingContent, newContent, "Hook should have new content");
            assertTrue(newContent.contains("reload-trigger"), "New hook should be Vitruvius hook");
        }
    }

    @Test
    @DisplayName("uninstallPostCheckoutHook removes hook")
    void uninstallRemovesHook(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);
            // Install hook
            installer.installPostCheckoutHook();
            assertTrue(installer.isPostCheckoutHookInstalled());
            // Uninstall hook
            installer.uninstallPostCheckoutHook();
            // Hook should be gone
            assertFalse(installer.isPostCheckoutHookInstalled());
        }
    }

    @Test
    @DisplayName("uninstall restores backup if present")
    void uninstallRestoresBackup(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);
            // Create existing hook
            Path hookPath = tempDir.resolve(".git/hooks/post-checkout");
            String originalContent = "#!/bin/bash\necho 'original hook'";
            Files.writeString(hookPath, originalContent);
            // Install Vitruvius hook (backs up original)
            installer.installPostCheckoutHook();
            // Verify Vitruvius hook is installed
            String vitruviusContent = Files.readString(hookPath);
            assertTrue(vitruviusContent.contains("reload-trigger"));
            // Uninstall (should restore backup)
            installer.uninstallPostCheckoutHook();
            // Original hook should be restored
            assertTrue(Files.exists(hookPath), "Original hook should be restored");
            String restoredContent = Files.readString(hookPath);
            assertEquals(originalContent, restoredContent, "Should restore original hook content");
        }
    }

    @Test
    @DisplayName("getHooksDirectory returns correct path")
    void getHooksDirectoryReturnsCorrectPath(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);
            Path expectedPath = tempDir.resolve(".git/hooks");
            assertEquals(expectedPath, installer.getHooksDirectory());
        }
    }

    @Test
    @DisplayName("installAllHooks installs post-checkout hook")
    void installAllHooksInstallsPostCheckout(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);
            installer.installAllHooks();
            assertTrue(installer.isPostCheckoutHookInstalled());
        }
    }

    @Test
    @DisplayName("uninstall on non-existent hook is safe")
    void uninstallNonExistentHookIsSafe(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);
            assertDoesNotThrow(installer::uninstallPostCheckoutHook);
        }
    }
}