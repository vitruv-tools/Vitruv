package tools.vitruv.framework.vsum.branch.util;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.StoredConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link GitHookInstaller}.
 *
 * <p>Each test initializes a real temporary Git repository using JGit
 * so that the hooks directory exists and file system operations can be verified end to end.
 * No mocking is used because the installer's behavior is entirely about file system state.
 *
 * <p>Tests that read hook script content (for example checking for the shebang line or the trigger file reference) 
 * depend on the hook resource files being present on the classpath under {@code /git-hooks/}. 
 * If those resources are missing the installation will throw an {@link java.io.IOException}
 * and the test will fail with a clear message.
 */
class GitHookInstallerTest {

    /**
     * Verifies that constructing an installer with a directory
     * that is not a Git repository raises an {@link IllegalArgumentException}.
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
     * Verifies that installing the post-checkout hook writes the hook file to disk
     * and that the installer's own predicate reflects the new state.
     */
    @Test
    @DisplayName("installs the post-checkout hook and reports it as installed")
    void installsPostCheckoutHook(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);

            // the hook must not exist before installation.
            assertFalse(installer.isPostCheckoutHookInstalled());

            installer.installPostCheckoutHook();

            assertTrue(installer.isPostCheckoutHookInstalled(),
                    "hook must be reported as installed after installPostCheckoutHook()");
        }
    }

    /**
     * Verifies that installing the pre-commit hook writes the hook file to disk
     * and that the installer's own predicate reflects the new state.
     */
    @Test
    @DisplayName("installs the pre-commit hook and reports it as installed")
    void installsPreCommitHook(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);

            assertFalse(installer.isPreCommitHookInstalled());

            installer.installPreCommitHook();

            assertTrue(installer.isPreCommitHookInstalled(),
                    "hook must be reported as installed after installPreCommitHook()");
        }
    }

    /**
     * Verifies that the installed post-checkout script is a valid bash script
     * that references the Vitruvius reload trigger file.
     * This confirms that the correct resource was copied from the classpath and
     * that the file content was not corrupted during the copy.
     */
    @Test
    @DisplayName("post-checkout hook script contains the expected shebang and trigger reference")
    void postCheckoutHookScriptContainsExpectedContent(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);
            installer.installPostCheckoutHook();

            var content = Files.readString(tempDir.resolve(".git/hooks/post-checkout"));

            // the shebang line is required for Git to invoke the script via the correct shell.
            assertTrue(content.startsWith("#!/bin/bash"),
                    "hook script must start with a bash shebang line");
            // the reload trigger file reference confirms this is the Vitruvius hook and not
            // some other script that happened to be installed.
            assertTrue(content.contains("reload-trigger"),
                    "hook script must reference the reload-trigger file");
            assertTrue(content.contains(".vitruvius"),
                    "hook script must reference the .vitruvius directory");
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
            assertTrue(Files.exists(backupPath),
                    "backup file must be created from the existing hook");
            assertEquals(originalContent, Files.readString(backupPath),
                    "backup must contain the original hook content");

            // the installed hook must be the Vitruvius hook, not the original.
            var newContent = Files.readString(hookPath);
            assertTrue(newContent.contains("reload-trigger"),
                    "the installed hook must be the Vitruvius post-checkout hook");
        }
    }

    /**
     * Verifies that {@link GitHookInstaller#installAllHooks()} installs both the post-checkout
     * and the pre-commit hooks in a single call.
     * Each hook is checked independently because both must be present for the full Vitruvius workflow to work.
     */
    @Test
    @DisplayName("installAllHooks installs both post-checkout and pre-commit hooks")
    void installAllHooksInstallsBothHooks(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);

            installer.installAllHooks();

            assertTrue(installer.isPostCheckoutHookInstalled(),
                    "post-checkout hook must be installed by installAllHooks()");
            assertTrue(installer.isPreCommitHookInstalled(),
                    "pre-commit hook must be installed by installAllHooks()");
        }
    }

    /**
     * Verifies that uninstalling a hook removes it from the hooks directory
     * so that Git will no longer invoke it on branch switches.
     */
    @Test
    @DisplayName("uninstalls the post-checkout hook and reports it as not installed")
    void uninstallRemovesPostCheckoutHook(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);
            installer.installPostCheckoutHook();
            assertTrue(installer.isPostCheckoutHookInstalled());

            installer.uninstallPostCheckoutHook();

            assertFalse(installer.isPostCheckoutHookInstalled(),
                    "hook must not be reported as installed after uninstall");
        }
    }

    /**
     * Verifies that when a backup was created during installation,
     * uninstalling the Vitruvius hook restores the developer's original hook.
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
            assertTrue(Files.readString(hookPath).contains("reload-trigger"),
                    "Vitruvius hook must be active after installation");

            // uninstall must remove the Vitruvius hook and move the backup back.
            installer.uninstallPostCheckoutHook();

            assertTrue(Files.exists(hookPath), "the original hook file must exist after uninstall");
            assertEquals(originalContent, Files.readString(hookPath),
                    "the restored hook must match the original content");
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

    // --- installGitAttributes() ---

    /**
     * Verifies that installGitAttributes writes the guard comment and a merge=vitruvius
     * pattern line for each supplied extension.
     */
    @Test
    @DisplayName("installGitAttributes writes guard and pattern lines for each extension")
    void installGitAttributesWritesCorrectContent(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);

            installer.installGitAttributes(List.of("model", "xmi"));

            String content = Files.readString(tempDir.resolve(".gitattributes"));
            assertTrue(content.contains("# Vitruvius merge driver"),
                    ".gitattributes must contain the guard comment");
            assertTrue(content.contains("*.model merge=vitruvius"),
                    ".gitattributes must map *.model to the vitruvius merge driver");
            assertTrue(content.contains("*.xmi merge=vitruvius"),
                    ".gitattributes must map *.xmi to the vitruvius merge driver");
        }
    }

    /**
     * Verifies that an extension already prefixed with "*." is not double-prefixed.
     */
    @Test
    @DisplayName("installGitAttributes does not double-prefix extensions that start with *.")
    void installGitAttributesNormalizesExtensionPrefix(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);

            installer.installGitAttributes(List.of("*.brakesystem"));

            String content = Files.readString(tempDir.resolve(".gitattributes"));
            assertTrue(content.contains("*.brakesystem merge=vitruvius"),
                    "extension already starting with *. must not be double-prefixed");
            assertFalse(content.contains("*.*.brakesystem"),
                    "double-prefixed pattern must not appear");
        }
    }

    /**
     * Verifies that calling installGitAttributes twice does not add duplicate entries.
     */
    @Test
    @DisplayName("installGitAttributes is idempotent -- second call adds no duplicate lines")
    void installGitAttributesIsIdempotent(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);

            installer.installGitAttributes(List.of("model"));
            installer.installGitAttributes(List.of("model"));

            String content = Files.readString(tempDir.resolve(".gitattributes"));
            long guardCount = content.lines()
                    .filter(l -> l.equals("# Vitruvius merge driver"))
                    .count();
            assertEquals(1, guardCount,
                    "guard comment must appear exactly once after two calls");
        }
    }

    /**
     * Verifies that installGitAttributes appends to an existing .gitattributes
     * without overwriting the pre-existing content.
     */
    @Test
    @DisplayName("installGitAttributes appends to an existing .gitattributes")
    void installGitAttributesAppendsToExistingFile(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);
            Path gitattributes = tempDir.resolve(".gitattributes");
            Files.writeString(gitattributes, "*.txt text=auto\n");

            installer.installGitAttributes(List.of("model"));

            String content = Files.readString(gitattributes);
            assertTrue(content.contains("*.txt text=auto"),
                    "pre-existing content must be preserved");
            assertTrue(content.contains("*.model merge=vitruvius"),
                    "new pattern must be appended");
        }
    }

    // --- installMergeDriverConfig() ---

    /**
     * Verifies that installMergeDriverConfig writes the merge-driver.properties file
     * with the correct specifications= line.
     */
    @Test
    @DisplayName("installMergeDriverConfig writes merge-driver.properties with spec classes")
    void installMergeDriverConfigWritesPropertiesFile(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);

            installer.installMergeDriverConfig(
                    "java -jar vitruvius.jar %O %A %B %L %P",
                    List.of("com.example.AToB", "com.example.BToA"));

            Path propsFile = tempDir.resolve(".vitruvius/merge-driver.properties");
            assertTrue(Files.exists(propsFile),
                    "merge-driver.properties must be created");
            String content = Files.readString(propsFile);
            assertTrue(content.contains("specifications=com.example.AToB,com.example.BToA"),
                    "properties file must list all spec classes separated by commas");
        }
    }

    /**
     * Verifies that installMergeDriverConfig writes the [merge "vitruvius"] section
     * to .git/config with the correct driver command.
     */
    @Test
    @DisplayName("installMergeDriverConfig writes [merge vitruvius] section to .git/config")
    void installMergeDriverConfigWritesGitConfig(@TempDir Path tempDir) throws Exception {
        try (Git git = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);
            String driverCommand = "java -jar vitruvius.jar %O %A %B %L %P";

            installer.installMergeDriverConfig(driverCommand, List.of("com.example.Spec"));

            StoredConfig config = git.getRepository().getConfig();
            config.load();
            assertEquals("Vitruvius semantic merge",
                    config.getString("merge", "vitruvius", "name"),
                    "merge driver name must be 'Vitruvius semantic merge'");
            assertEquals(driverCommand,
                    config.getString("merge", "vitruvius", "driver"),
                    "merge driver command must match what was passed to the installer");
        }
    }

    // --- installAll() (T1) ---

    /**
     * Verifies that installAll installs all four hooks, writes the .gitattributes guard
     * section, registers the merge driver in .git/config, and creates merge-driver.properties.
     */
    @Test
    @DisplayName("installAll installs all hooks and the full merge driver configuration")
    void installAllInstallsEverything(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);

            installer.installAll(
                    List.of("model", "xmi"),
                    "java -jar vitruvius.jar %O %A %B %L %P",
                    List.of("com.example.Spec"));

            assertTrue(installer.areAllHooksInstalled(),
                    "all four hooks must be installed");
            String attrs = Files.readString(tempDir.resolve(".gitattributes"));
            assertTrue(attrs.contains("# Vitruvius merge driver"),
                    ".gitattributes must contain the guard comment");
            assertTrue(attrs.contains("*.model merge=vitruvius"),
                    ".gitattributes must map *.model to the vitruvius driver");
            assertTrue(Files.exists(tempDir.resolve(".vitruvius/merge-driver.properties")),
                    "merge-driver.properties must be created");
        }
    }

    /**
     * Verifies that calling installAll twice does not duplicate the .gitattributes guard
     * section or any other configuration entry.
     */
    @Test
    @DisplayName("installAll is idempotent -- second call adds no duplicate entries")
    void installAllIsIdempotent(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);
            String driverCommand = "java -jar vitruvius.jar %O %A %B %L %P";

            installer.installAll(List.of("model"), driverCommand, List.of("com.example.Spec"));
            installer.installAll(List.of("model"), driverCommand, List.of("com.example.Spec"));

            String attrs = Files.readString(tempDir.resolve(".gitattributes"));
            long guardCount = attrs.lines()
                    .filter(l -> l.equals("# Vitruvius merge driver"))
                    .count();
            assertEquals(1, guardCount,
                    "guard comment must appear exactly once after two installAll calls");
        }
    }

    // --- uninstallGitAttributes() (T2) ---

    /**
     * Verifies that uninstallGitAttributes removes the Vitruvius guard section from
     * .gitattributes while leaving all other content unchanged.
     */
    @Test
    @DisplayName("uninstallGitAttributes removes the Vitruvius section without touching other content")
    void uninstallGitAttributesRemovesVitruviusSectionOnly(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);
            Path gitattributes = tempDir.resolve(".gitattributes");
            Files.writeString(gitattributes, "* text=auto\n");

            installer.installGitAttributes(List.of("model"));
            installer.uninstallGitAttributes();

            String content = Files.readString(gitattributes);
            assertFalse(content.contains("# Vitruvius merge driver"),
                    "guard comment must be removed");
            assertFalse(content.contains("merge=vitruvius"),
                    "merge driver patterns must be removed");
            assertTrue(content.contains("text=auto"),
                    "pre-existing content must be preserved");
        }
    }

    /**
     * Verifies that uninstallGitAttributes does nothing when the guard section is absent,
     * and does not throw an exception.
     */
    @Test
    @DisplayName("uninstallGitAttributes is a no-op when the Vitruvius section is not present")
    void uninstallGitAttributesIsNoOpWhenSectionAbsent(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);
            Path gitattributes = tempDir.resolve(".gitattributes");
            Files.writeString(gitattributes, "* text=auto\n");

            assertDoesNotThrow(installer::uninstallGitAttributes);

            assertEquals("* text=auto\n", Files.readString(gitattributes),
                    "file content must be unchanged when no Vitruvius section exists");
        }
    }

    /**
     * Verifies that calling uninstallGitAttributes twice is safe and does not corrupt the file.
     */
    @Test
    @DisplayName("uninstallGitAttributes is idempotent -- second call does not throw or corrupt the file")
    void uninstallGitAttributesIsIdempotent(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);

            installer.installGitAttributes(List.of("model"));
            installer.uninstallGitAttributes();

            assertDoesNotThrow(installer::uninstallGitAttributes,
                    "second uninstall call must not throw");
        }
    }

    // --- uninstallMergeDriverConfig() (T2) ---

    /**
     * Verifies that uninstallMergeDriverConfig removes the [merge "vitruvius"] section
     * from .git/config so that raw git merge no longer invokes the driver.
     */
    @Test
    @DisplayName("uninstallMergeDriverConfig removes [merge vitruvius] from .git/config")
    void uninstallMergeDriverConfigRemovesGitConfigSection(@TempDir Path tempDir) throws Exception {
        try (Git git = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);

            installer.installMergeDriverConfig(
                    "java -jar vitruvius.jar %O %A %B %L %P",
                    List.of("com.example.Spec"));
            installer.uninstallMergeDriverConfig();

            StoredConfig config = git.getRepository().getConfig();
            config.load();
            assertNull(config.getString("merge", "vitruvius", "driver"),
                    "[merge vitruvius] driver entry must be absent after uninstall");
        }
    }

    /**
     * Verifies that uninstallMergeDriverConfig deletes merge-driver.properties.
     */
    @Test
    @DisplayName("uninstallMergeDriverConfig deletes merge-driver.properties")
    void uninstallMergeDriverConfigDeletesPropertiesFile(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);

            installer.installMergeDriverConfig(
                    "java -jar vitruvius.jar %O %A %B %L %P",
                    List.of("com.example.Spec"));
            installer.uninstallMergeDriverConfig();

            assertFalse(Files.exists(tempDir.resolve(".vitruvius/merge-driver.properties")),
                    "merge-driver.properties must be deleted after uninstall");
        }
    }

    /**
     * Verifies that uninstallMergeDriverConfig does nothing and does not throw when
     * the driver was never configured.
     */
    @Test
    @DisplayName("uninstallMergeDriverConfig is a no-op when the driver was never configured")
    void uninstallMergeDriverConfigIsNoOpWhenNotConfigured(@TempDir Path tempDir) throws Exception {
        try (var ignored = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);
            assertDoesNotThrow(installer::uninstallMergeDriverConfig,
                    "uninstall must not throw when nothing was configured");
        }
    }

    // --- uninstallAll() round-trip (T2) ---

    /**
     * Verifies that uninstallAll completely reverses installAll: all hooks are removed,
     * the .gitattributes Vitruvius section is gone, and the merge driver config is deleted.
     */
    @Test
    @DisplayName("uninstallAll is the exact inverse of installAll")
    void uninstallAllIsFullInverseOfInstallAll(@TempDir Path tempDir) throws Exception {
        try (Git git = Git.init().setDirectory(tempDir.toFile()).call()) {
            var installer = new GitHookInstaller(tempDir);
            String driverCommand = "java -jar vitruvius.jar %O %A %B %L %P";

            installer.installAll(List.of("model"), driverCommand, List.of("com.example.Spec"));
            installer.uninstallAll();

            assertFalse(installer.areAllHooksInstalled(),
                    "no hooks must remain after uninstallAll");
            Path gitattributes = tempDir.resolve(".gitattributes");
            if (Files.exists(gitattributes)) {
                assertFalse(Files.readString(gitattributes).contains("# Vitruvius merge driver"),
                        "Vitruvius .gitattributes section must be removed");
            }
            assertFalse(Files.exists(tempDir.resolve(".vitruvius/merge-driver.properties")),
                    "merge-driver.properties must be deleted");
            StoredConfig config = git.getRepository().getConfig();
            config.load();
            assertNull(config.getString("merge", "vitruvius", "driver"),
                    "[merge vitruvius] entry must be absent from .git/config");
        }
    }
}