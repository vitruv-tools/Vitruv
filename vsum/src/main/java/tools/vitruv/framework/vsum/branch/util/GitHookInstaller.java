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
 * This class installs Git hook scripts into a repository.
 * The hooks ensure that VSUM is reloaded when developers switch branches using Git commands such as {@code git checkout}.
 * The hook scripts are copied from application resources into the repository's {@code .git/hooks} directory.
 * If a hook already exists, it is backed up before being replaced.
 * Currently, the following hooks are supported:
 * <ul>
 *   <li>{@code post-checkout}: triggered after a branch switch</li>
 *   <li>{@code pre-commit}: triggered before a commit to validate V-SUM consistency</li>
 *   <li>{@code post-merge}: (TODO) triggered after a merge</li>
 * </ul>
 *
 * This is intended for Unix-like systems. On Windows, git bash is required to execute the hook scripts.
 */
public class GitHookInstaller {
    private static final Logger LOGGER = LogManager.getLogger(GitHookInstaller.class);
    private static final String HOOKS_RESOURCE_PATH = "/git-hooks/";
    private static final String POST_CHECKOUT_HOOK = "post-checkout";
    private static final String PRE_COMMIT_HOOK = "pre-commit";

    /**
     * Returns the hooks directory for this repository.
     */
    @Getter
    private final Path hooksDirectory;

    /**
     * Creates a new hook installer for the given repository.
     *
     * @param repositoryRoot The root directory of the Git repository
     * @throws IllegalArgumentException if the directory is not a Git repository
     */
    public GitHookInstaller(Path repositoryRoot) {
        this.hooksDirectory = repositoryRoot.resolve(".git/hooks");
        if (!Files.isDirectory(hooksDirectory)) {
            throw new IllegalArgumentException("Not a Git repository (.git/hooks directory): " + repositoryRoot);
        }
    }

    /**
     * Installs the post-checkout hook that triggers VSUM reload after branch switches.
     *
     * <p>If a post-checkout hook already exists, it will be backed up with a
     * {@code .backup} suffix before being replaced.</p>
     *
     * @throws IOException if the hook cannot be installed
     */
    public void installPostCheckoutHook() throws IOException {
        Path hookPath = hooksDirectory.resolve(POST_CHECKOUT_HOOK);
        // backup if existing hook is present
        if (Files.exists(hookPath)) {
            Path backupPath = hooksDirectory.resolve(POST_CHECKOUT_HOOK + ".backup");
            Files.move(hookPath, backupPath, StandardCopyOption.REPLACE_EXISTING);
            LOGGER.info("Post-checkout hook has been backed up");
        }
        // copy hook template from resources to .git/hooks
        String resourcePath = HOOKS_RESOURCE_PATH + POST_CHECKOUT_HOOK;
        try (InputStream hookTemplate = getClass().getResourceAsStream(resourcePath)) {
            if (hookTemplate == null) {
                throw new IOException("Hook template not found in resources: " + resourcePath);
            }
            Files.copy(hookTemplate, hookPath, StandardCopyOption.REPLACE_EXISTING);
            LOGGER.debug("Copied hook template to: {}", hookPath);
        }
        // make hook executable
        makeExecutable(hookPath);
        LOGGER.info("Installed post-checkout hook at: {}", hookPath);
    }

    /**
     * Installs the pre-commit hook that validates V-SUM before allowing commits.
     *
     * <p>If a pre-commit hook already exists, it will be backed up with a
     * {@code .backup} suffix before being replaced.</p>
     *
     * @throws IOException if the hook cannot be installed
     */
    public void installPreCommitHook() throws IOException {
        Path hookPath = hooksDirectory.resolve(PRE_COMMIT_HOOK);

        // backup if existing hook is present
        if (Files.exists(hookPath)) {
            Path backupPath = hooksDirectory.resolve(PRE_COMMIT_HOOK + ".backup");
            Files.move(hookPath, backupPath, StandardCopyOption.REPLACE_EXISTING);
            LOGGER.info("Pre-commit hook backed up");
        }

        // copy hook template from resources to .git/hooks
        String resourcePath = HOOKS_RESOURCE_PATH + PRE_COMMIT_HOOK;
        try (InputStream hookTemplate = getClass().getResourceAsStream(resourcePath)) {
            if (hookTemplate == null) {
                throw new IOException("Hook template not found in resources: " + resourcePath);
            }
            Files.copy(hookTemplate, hookPath, StandardCopyOption.REPLACE_EXISTING);
            LOGGER.debug("Copied pre-commit hook template to: {}", hookPath);
        }

        // make hook executable
        makeExecutable(hookPath);
        LOGGER.info("Installed pre-commit hook at: {}", hookPath);
    }

    /**
     * Installs all available Git hooks.
     * Currently, installs post-checkout and pre-commit hooks.
     *
     * @throws IOException if any hook installation fails
     */
    public void installAllHooks() throws IOException {
        installPostCheckoutHook();
        installPreCommitHook();

        // TODO: Add installPostMergeHook()
        LOGGER.info("Installed all Git hooks (post-checkout, pre-commit)");
    }

    /**
     * Removes the post-checkout hook.
     * If a backup exists, it will be restored.
     *
     * @throws IOException if the hook cannot be uninstalled
     */
    public void uninstallPostCheckoutHook() throws IOException {
        Path hookPath = hooksDirectory.resolve(POST_CHECKOUT_HOOK);
        Path backupPath = hooksDirectory.resolve(POST_CHECKOUT_HOOK + ".backup");
        if (Files.exists(hookPath)) {
            Files.delete(hookPath);
            LOGGER.info("Removed post-checkout hook");
        }
        // restore backup if it exists
        if (Files.exists(backupPath)) {
            Files.move(backupPath, hookPath, StandardCopyOption.REPLACE_EXISTING);
            LOGGER.info("Restored backup post-checkout hook");
        }
    }

    /**
     * Removes the pre-commit hook.
     * If a backup exists, it will be restored.
     *
     * @throws IOException if the hook cannot be uninstalled
     */
    public void uninstallPreCommitHook() throws IOException {
        Path hookPath = hooksDirectory.resolve(PRE_COMMIT_HOOK);
        Path backupPath = hooksDirectory.resolve(PRE_COMMIT_HOOK + ".backup");

        if (Files.exists(hookPath)) {
            Files.delete(hookPath);
            LOGGER.info("Removed pre-commit hook");
        }

        // restore backup if it exists
        if (Files.exists(backupPath)) {
            Files.move(backupPath, hookPath, StandardCopyOption.REPLACE_EXISTING);
            LOGGER.info("Restored backup pre-commit hook");
        }
    }

    /**
     * Checks if the post-checkout hook is currently installed.
     *
     * @return true if the hook exists, false otherwise
     */
    public boolean isPostCheckoutHookInstalled() {
        return Files.exists(hooksDirectory.resolve(POST_CHECKOUT_HOOK));
    }

    /**
     * Checks if the pre-commit hook is currently installed.
     *
     * @return true if the hook exists, false otherwise
     */
    public boolean isPreCommitHookInstalled() {
        return Files.exists(hooksDirectory.resolve(PRE_COMMIT_HOOK));
    }

    /**
     * Makes a file executable on Unix-like systems.
     * On Windows, git bash will handle the executability.
     *
     * @param path The file to make executable
     */
    private void makeExecutable(Path path) {
        try {
            // mark the hook as executable for unix-like system
            Set<PosixFilePermission> perms = Files.getPosixFilePermissions(path);
            perms.add(PosixFilePermission.OWNER_EXECUTE);
            perms.add(PosixFilePermission.GROUP_EXECUTE);
            perms.add(PosixFilePermission.OTHERS_EXECUTE);
            Files.setPosixFilePermissions(path, perms);
            LOGGER.debug("Made hook executable: {}", path);
        } catch (UnsupportedOperationException | IOException e) {
            //for windows it would throw exception, but git hook will still be handled as long as git bash is installed
            LOGGER.debug("Could not set POSIX permissions on Windows: {}", e.getMessage());
        }
    }
}