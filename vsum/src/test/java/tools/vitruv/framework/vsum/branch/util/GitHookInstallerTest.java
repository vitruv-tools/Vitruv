package tools.vitruv.framework.vsum.branch.util;

import org.eclipse.jgit.api.Git;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link GitHookInstaller}.
 *
 * <p>Each test initializes a real temporary Git repository using JGit so that the hooks directory exists and file system operations can be verified end to end.
 * No mocking is used because the installer's behavior is entirely about file system state.
 *
 * <p>Tests that read hook script content (for example checking for the shebang line or the trigger file reference) 
 * depend on the hook resource files being present on the classpath under {@code /git-hooks/}. 
 * If those resources are missing the installation will throw an {@link java.io.IOException} and the test will fail with a clear message.
 */
class GitHookInstallerTest {

    /**
     * Verifies that constructing an installer with a directory that is not a Git repository raises an {@link IllegalArgumentException}.
     * Without a {@code .git/hooks} directory there is no valid target for hook installation.
     */
    @Test
    @DisplayName("throws an exception when the directory is not a Git repository")
    void throwsExceptionWhenNotGitRepo(@TempDir Path tempDir) {
        // tempDir has no .git subdirectory, so it does not qualify as a Git repository.
        assertThrows(IllegalArgumentException.class, () -> new GitHookInstaller(tempDir));
    }

    /**
     * Verifies that a valid Git repository (one with a {@code .git/hooks} directory) is accepted without throwing.
     * This is the minimal precondition for all other operations.
     */
    @Test
    @DisplayName("accepts a valid Git repository without throwing")
    void acceptsValidGitRepo(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            assertDoesNotThrow(() -> new GitHookInstaller(tempDir));
        }
    }

    /**
     * Verifies that the hooks directory path exposed by the getter is exactly {@code <repositoryRoot>/.git/hooks}.
     * Callers rely on this path to verify or inspect installed hooks outside the installer.
     */
    @Test
    @DisplayName("exposes the correct hooks directory path")
    void getHooksDirectoryReturnsCorrectPath(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);
            assertEquals(tempDir.resolve(".git/hooks"), installer.getHooksDirectory());
        }
    }

    /**
     * Verifies that installing the post-checkout hook writes the hook file to disk and that the installer's own predicate reflects the new state.
     */
    @Test
    @DisplayName("installs the post-checkout hook and reports it as installed")
    void installsPostCheckoutHook(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);

            // the hook must not exist before installation.
            assertFalse(installer.isPostCheckoutHookInstalled());

            installer.installPostCheckoutHook();

            assertTrue(installer.isPostCheckoutHookInstalled(), "hook must be reported as installed after installPostCheckoutHook()");
        }
    }

    /**
     * Verifies that installing the pre-commit hook writes the hook file to disk and that the installer's own predicate reflects the new state.
     */
    @Test
    @DisplayName("installs the pre-commit hook and reports it as installed")
    void installsPreCommitHook(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);

            assertFalse(installer.isPreCommitHookInstalled());

            installer.installPreCommitHook();

            assertTrue(installer.isPreCommitHookInstalled(), "hook must be reported as installed after installPreCommitHook()");
        }
    }

    /**
     * Verifies that the installed post-checkout script is a valid bash script that references the Vitruvius reload trigger file.
     * This confirms that the correct resource was copied from the classpath and that the file content was not corrupted during the copy.
     */
    @Test
    @DisplayName("post-checkout hook script contains the expected shebang and trigger reference")
    void postCheckoutHookScriptContainsExpectedContent(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);
            installer.installPostCheckoutHook();

            var content = Files.readString(tempDir.resolve(".git/hooks/post-checkout"));

            // the shebang line is required for Git to invoke the script via the correct shell.
            assertTrue(content.startsWith("#!/bin/bash"), "hook script must start with a bash shebang line");
            // the reload trigger file reference confirms this is the Vitruvius hook and not
            // some other script that happened to be installed.
            assertTrue(content.contains("reload-trigger"), "hook script must reference the reload-trigger file");
            assertTrue(content.contains(".vitruvius"), "hook script must reference the .vitruvius directory");
        }
    }

    /**
     * Verifies that when a hook already exists it is moved to a {@code .backup} file before
     * the new Vitruvius hook is written. the backup must contain the original content so that
     * it can be restored on uninstall.
     */
    @Test
    @DisplayName("backs up an existing hook before installing the Vitruvius hook")
    void backsUpExistingHookBeforeInstalling(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);
            var hookPath = tempDir.resolve(".git/hooks/post-checkout");
            var backupPath = tempDir.resolve(".git/hooks/post-checkout.backup");

            // write a pre-existing hook to simulate a developer who had their own hook set up.
            var originalContent = "#!/bin/bash\necho 'developer hook'";
            Files.writeString(hookPath, originalContent);

            installer.installPostCheckoutHook();

            // the backup must exist and contain the original content so it can be restored later.
            assertTrue(Files.exists(backupPath), "backup file must be created from the existing hook");
            assertEquals(originalContent, Files.readString(backupPath), "backup must contain the original hook content");

            // the installed hook must be the Vitruvius hook, not the original.
            var newContent = Files.readString(hookPath);
            assertTrue(newContent.contains("reload-trigger"), "the installed hook must be the Vitruvius post-checkout hook");
        }
    }

    /**
     * Verifies that {@link GitHookInstaller#installAllHooks()} installs both the post-checkout and the pre-commit hooks in a single call.
     * Each hook is checked independently because both must be present for the full Vitruvius workflow to work.
     */
    @Test
    @DisplayName("installAllHooks installs both post-checkout and pre-commit hooks")
    void installAllHooksInstallsBothHooks(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);

            installer.installAllHooks();

            assertTrue(installer.isPostCheckoutHookInstalled(), "post-checkout hook must be installed by installAllHooks()");
            assertTrue(installer.isPreCommitHookInstalled(), "pre-commit hook must be installed by installAllHooks()");
        }
    }

    /**
     * Verifies that uninstalling a hook removes it from the hooks directory so that Git will no longer invoke it on branch switches.
     */
    @Test
    @DisplayName("uninstalls the post-checkout hook and reports it as not installed")
    void uninstallRemovesPostCheckoutHook(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);
            installer.installPostCheckoutHook();
            assertTrue(installer.isPostCheckoutHookInstalled());

            installer.uninstallPostCheckoutHook();

            assertFalse(installer.isPostCheckoutHookInstalled(), "hook must not be reported as installed after uninstall");
        }
    }

    /**
     * Verifies that when a backup was created during installation, uninstalling the Vitruvius hook restores the developer's original hook.
     */
    @Test
    @DisplayName("uninstall restores the original hook from the backup")
    void uninstallRestoresBackupHook(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);
            var hookPath = tempDir.resolve(".git/hooks/post-checkout");

            // set up an original hook that will be backed up during installation.
            var originalContent = "#!/bin/bash\necho 'original hook'";
            Files.writeString(hookPath, originalContent);

            // install creates the backup and replaces the hook with the Vitruvius version.
            installer.installPostCheckoutHook();
            assertTrue(Files.readString(hookPath).contains("reload-trigger"), "Vitruvius hook must be active after installation");

            // uninstall must remove the Vitruvius hook and move the backup back.
            installer.uninstallPostCheckoutHook();

            assertTrue(Files.exists(hookPath), "the original hook file must exist after uninstall");
            assertEquals(originalContent, Files.readString(hookPath), "the restored hook must match the original content");
        }
    }

    /**
     * Verifies that calling uninstall when no hook is present does not throw an exception.
     */
    @Test
    @DisplayName("uninstalling a hook that is not installed completes without throwing")
    void uninstallNonExistentHookIsSafe(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);
            // no hook has been installed, so there is nothing to remove. the call must
            // complete silently rather than throwing a file-not-found exception.
            assertDoesNotThrow(installer::uninstallPostCheckoutHook);
            assertDoesNotThrow(installer::uninstallPreCommitHook);
        }
    }
}