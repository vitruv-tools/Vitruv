package tools.vitruv.framework.vsum.branch.util;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;

/**
 * Installs and uninstalls Git hook scripts into a repository's {@code .git/hooks} directory.
 * The hooks enable Vitruvius to react to Git operations without requiring developers to run any manual setup steps:
 * The installer copies the hook scripts from the application's bundled resources and makes them executable.
 *
 * <p>The following hooks are currently supported:
 * <ul>
 *   <li>{@code post-checkout}: triggered after a branch switch via {@code git checkout} or {@code git switch}.
 *       Writes a reload trigger file so that the Vitruvius background watcher can refresh the VirtualModel state.</li>
 *   <li>{@code pre-commit}: triggered before each commit.
 *       Requests a VirtualModel validation and blocks the commit if consistency errors are detected.</li>
 *   <li>{@code post-merge}: triggered after {@code git merge} or {@code git pull} completes.
 *       Writes a merge trigger file so that the Vitruvius background watcher can validate the merged model state and write a permanent merge metadata record.</li>
 * </ul>
 *
 * <p>If a hook file already exists when installing, it is renamed to {@code <hookName>.backup} before the new file is written.
 * Uninstalling removes the Vitruvius hook and restores the backup if one is present to preserve any hook that the developer had configured before Vitruvius was set up.
 *
 * <p>This installer is designed for Unix-like systems where POSIX file permissions are available.
 * On Windows, the executable bit cannot be set via the Java POSIX permission interface, but Git hooks will still run correctly when Git for Windows (Git Bash) is
 * installed, because Git Bash interprets the shebang line directly.
 */
public class GitHookInstaller {
    private static final Logger LOGGER = LogManager.getLogger(GitHookInstaller.class);

    /**
     * classpath prefix under which the hook script templates are stored as resources.
     */
    private static final String HOOKS_RESOURCE_PATH = "/git-hooks/";

    private static final String POST_CHECKOUT_HOOK = "post-checkout";
    private static final String PRE_COMMIT_HOOK = "pre-commit";
    private static final String POST_MERGE_HOOK = "post-merge";
    private static final String POST_COMMIT_HOOK = "post-commit";

    /**
     * The {@code .git/hooks} directory of the target repository. All hook files are written to and read from this directory.
     */
    @Getter
    private final Path hooksDirectory;

    /**
     * Creates a new installer targeting the given repository root.
     *
     * @param repositoryRoot the root directory of the Git repository. Must contain a {@code .git/hooks} subdirectory.
     * @throws IllegalArgumentException if the directory is not a Git repository or the {@code .git/hooks} directory is missing.
     */
    public GitHookInstaller(Path repositoryRoot) {
        this.hooksDirectory = repositoryRoot.resolve(".git/hooks");
        if (!Files.isDirectory(hooksDirectory)) {
            throw new IllegalArgumentException("not a Git repository (missing .git/hooks directory): " + repositoryRoot);
        }
    }

    /**
     * Installs the {@code post-checkout} hook that triggers a Vitruvius VirtualModel reload after each branch switch.
     * If a hook file already exists, it is backed up before being replaced.
     *
     * @throws IOException if the hook resource cannot be found, the backup cannot be created, or the hook file cannot be written.
     */
    public void installPostCheckoutHook() throws IOException {
        installHook(POST_CHECKOUT_HOOK);
    }

    /**
     * Installs the {@code pre-commit} hook that validates VirtualModel consistency before allowing a commit to proceed.
     * If a hook file already exists, it is backed up before being replaced.
     *
     * @throws IOException if the hook resource cannot be found, the backup cannot be created, or the hook file cannot be written.
     */
    public void installPreCommitHook() throws IOException {
        installHook(PRE_COMMIT_HOOK);
    }

    /**
     * Installs the {@code post-merge} hook that triggers post-merge VSUM validation after {@code git merge} or {@code git pull} completes.
     * Writes a merge trigger file so that the Vitruvius background watcher can validate the merged model state and write a permanent merge metadata record.
     * if a hook file already exists, it is backed up before being replaced.
     *
     * @throws IOException if the hook resource cannot be found, the backup cannot be created, or the hook file cannot be written.
     */
    public void installPostMergeHook() throws IOException {
        installHook(POST_MERGE_HOOK);
    }


    public void installPostCommitHook() throws IOException {
        installHook(POST_COMMIT_HOOK);
    }

    /**
     * Installs all currently supported Git hooks ({@code post-checkout} and {@code pre-commit}).
     * If any installation fails the exception is propagated immediately and subsequent hooks are not installed.
     *
     * @throws IOException if any hook cannot be installed.
     */
    public void installAllHooks() throws IOException {
        installPostCheckoutHook();
        installPreCommitHook();
        installPostMergeHook();
        installPostCommitHook();
        LOGGER.info("installed all Git hooks ({}, {}, {}, {})", POST_CHECKOUT_HOOK, PRE_COMMIT_HOOK, POST_MERGE_HOOK, POST_COMMIT_HOOK);
    }

    /**
     * Removes the {@code post-checkout} hook.
     * If a backup exists from a previous installation, the backup is restored so that the developer's original hook is active again.
     *
     * @throws IOException if the hook file cannot be deleted or the backup cannot be restored.
     */
    public void uninstallPostCheckoutHook() throws IOException {
        uninstallHook(POST_CHECKOUT_HOOK);
    }

    /**
     * Removes the {@code pre-commit} hook.
     * If a backup exists from a previous installation, the backup is restored so that the developer's original hook is active again.
     *
     * @throws IOException if the hook file cannot be deleted or the backup cannot be restored.
     */
    public void uninstallPreCommitHook() throws IOException {
        uninstallHook(PRE_COMMIT_HOOK);
    }

    /**
     * Removes the {@code post-merge} hook.
     * If a backup exists from a previous installation, the backup is restored so that the developer's original hook is active again.
     *
     * @throws IOException if the hook file cannot be deleted or the backup cannot be restored.
     */
    public void uninstallPostMergeHook() throws IOException {
        uninstallHook(POST_MERGE_HOOK);
    }

    public void uninstallPostCommitHook() throws IOException {
        uninstallHook(POST_COMMIT_HOOK);
    }

    /**
     * Returns true if the {@code post-checkout} hook file is currently present in the hooks' directory.
     * This does not verify whether the file is the Vitruvius-managed version.
     */
    public boolean isPostCheckoutHookInstalled() {
        return Files.exists(hooksDirectory.resolve(POST_CHECKOUT_HOOK));
    }

    /**
     * returns true if the {@code pre-commit} hook file is currently present in the hooks'directory.
     * This does not verify whether the file is the Vitruvius-managed version.
     */
    public boolean isPreCommitHookInstalled() {
        return Files.exists(hooksDirectory.resolve(PRE_COMMIT_HOOK));
    }

    /**
     * Returns true if the {@code post-merge} hook file is currently present in the hooks directory.
     * This does not verify whether the file is the Vitruvius-managed version.
     */
    public boolean isPostMergeHookInstalled() {
        return Files.exists(hooksDirectory.resolve(POST_MERGE_HOOK));
    }

    public boolean isPostCommitHookInstalled() {
        return Files.exists(hooksDirectory.resolve(POST_COMMIT_HOOK));
    }

    /**
     * Performs the actual installation of a single hook by name.
     * <p>Steps:
     * <ol>
     *   <li>If a hook file already exists, move it to {@code <hookName>.backup}.</li>
     *   <li>Copy the hook script from the bundled classpath resource to the hooks' directory.</li>
     *   <li>Set the executable permission bits so that Git can invoke the script.</li>
     * </ol>
     *
     * @param hookName the file name of the hook (for example {@code "post-checkout"}).
     * @throws IOException if any of the above steps fail.
     */
    private void installHook(String hookName) throws IOException {
        var hookPath = hooksDirectory.resolve(hookName);

        // back up any pre-existing hook so it can be restored on uninstallation to preserve hooks that the developer configured before Vitruvius was set up.
        if (Files.exists(hookPath)) {
            var backupPath = hooksDirectory.resolve(hookName + ".backup");
            Files.move(hookPath, backupPath, StandardCopyOption.REPLACE_EXISTING);
            LOGGER.info("backed up existing {} hook", hookName);
        }

        // copy the hook script from the application's bundled resources into the hooks directory.
        var resourcePath = HOOKS_RESOURCE_PATH + hookName;
        try (InputStream hookTemplate = getClass().getResourceAsStream(resourcePath)) {
            if (hookTemplate == null) {
                throw new IOException("hook template not found in resources: " + resourcePath);
            }
            Files.copy(hookTemplate, hookPath, StandardCopyOption.REPLACE_EXISTING);
            LOGGER.debug("copied hook template to: {}", hookPath);
        }

        makeExecutable(hookPath);
        LOGGER.info("installed {} hook at: {}", hookName, hookPath);
    }

    /**
     * Performs the actual uninstallation of a single hook by name. This shared helper is used
     * by both {@link #uninstallPostCheckoutHook()} and {@link #uninstallPreCommitHook()}.
     *
     * <p>Calling this method when no hook is installed is safe and does nothing.
     *
     * @param hookName the file name of the hook (for example {@code "pre-commit"}).
     * @throws IOException if the hook file cannot be deleted or the backup cannot be moved.
     */
    private void uninstallHook(String hookName) throws IOException {
        var hookPath = hooksDirectory.resolve(hookName);
        var backupPath = hooksDirectory.resolve(hookName + ".backup");

        if (Files.exists(hookPath)) {
            Files.delete(hookPath);
            LOGGER.info("removed {} hook", hookName);
        }

        // restore the developer's original hook if a backup was created during installation.
        if (Files.exists(backupPath)) {
            Files.move(backupPath, hookPath, StandardCopyOption.REPLACE_EXISTING);
            LOGGER.info("restored backup {} hook", hookName);
        }
    }

    /**
     * Sets the executable permission bits on a file so that Git can invoke it as a hook script.
     * Owner, group, and others execute bits are all set to match the standard Git hook convention.
     *
     * <p>On Windows, the POSIX permission interface is not supported and this method catches the
     * resulting exception silently. Git for Windows (Git Bash) does not require the executable
     * bit to be set because it reads and executes the shebang line directly.
     *
     * @param path the hook file to make executable.
     */
    private void makeExecutable(Path path) {
        try {
            Set<PosixFilePermission> perms = Files.getPosixFilePermissions(path);
            perms.add(PosixFilePermission.OWNER_EXECUTE);
            perms.add(PosixFilePermission.GROUP_EXECUTE);
            perms.add(PosixFilePermission.OTHERS_EXECUTE);
            Files.setPosixFilePermissions(path, perms);
            LOGGER.debug("set executable permissions on hook: {}", path);
        } catch (UnsupportedOperationException | IOException e) {
            // POSIX permissions are not available on Windows. The hook will still be executedcorrectly by Git Bash,
            // which interprets the shebang line without requiring the executable bit to be set on the file system.
            LOGGER.debug("could not set POSIX permissions (likely running on Windows): {}", e.getMessage());
        }
    }
}