package tools.vitruv.framework.vsum.branch.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Installs and uninstalls Git hook scripts into a repository's {@code .git/hooks} directory.
 * The hooks enable Vitruvius to react to Git operations without requiring developers to run
 * any manual setup steps: The installer copies the hook scripts from the application's
 * bundled resources and makes them executable.
 *
 * <p>The following hooks are currently supported:
 * <ul>
 *   <li>{@code post-checkout}: triggered after a branch switch via {@code git checkout}
 *       or {@code git switch}. Writes a reload trigger file so that the Vitruvius background
 *       watcher can refresh the VirtualModel state.</li>
 *   <li>{@code pre-commit}: triggered before each commit. Requests a VirtualModel validation
 *       and blocks the commit if consistency errors are detected.</li>
 *   <li>{@code post-merge}: triggered after {@code git merge} or {@code git pull} completes.
 *       Writes a merge trigger file so that the Vitruvius background watcher can validate the
 *       merged model state and write a permanent merge metadata record.</li>
 *   <li>{@code post-commit}: triggered after each successful commit. Signals the background
 *       watcher to generate a semantic changelog entry for the new commit.</li>
 * </ul>
 *
 * <p>If a hook file already exists when installing, it is renamed to
 * {@code <hookName>.backup} before the new file is written. Uninstalling removes the
 * Vitruvius hook and restores the backup if one is present to preserve any hook that the
 * developer had configured before Vitruvius was set up.
 *
 * <p>This installer is designed for Unix-like systems where POSIX file permissions are
 * available. On Windows, the executable bit cannot be set via the Java POSIX permission
 * interface, but Git hooks will still run correctly when Git for Windows (Git Bash) is
 * installed, because Git Bash interprets the shebang line directly.
 */
public class GitHookInstaller {

  private static final Logger LOGGER = LogManager.getLogger(GitHookInstaller.class);

  /**
   * Classpath prefix under which the hook script templates are stored as resources.
   */
  private static final String HOOKS_RESOURCE_PATH = "/git-hooks/";

  private static final String POST_CHECKOUT_HOOK = "post-checkout";
  private static final String PRE_COMMIT_HOOK = "pre-commit";
  private static final String POST_MERGE_HOOK = "post-merge";
  private static final String POST_COMMIT_HOOK = "post-commit";

  /**
   * Classpath location of the .gitignore template for Vitruvius files.
   */
  private static final String GITIGNORE_TEMPLATE_PATH = "/vitruvius/.gitignore.template";

  /**
   * Guard comment that marks the Vitruvius-managed section in {@code .gitattributes}.
   * Used both when writing the section and when detecting or removing it.
   */
  private static final String GITATTRIBUTES_GUARD = "# Vitruvius merge driver";

  /**
   * The {@code .git/hooks} directory of the target repository.
   * All hook files are written to and read from this directory.
   */
  @Getter
  private final Path hooksDirectory;

  private final Path repositoryRoot;

  /**
   * Creates a new installer targeting the given repository root.
   *
   * @param repositoryRoot the root directory of the Git repository. Must contain a
   *     {@code .git/hooks} subdirectory.
   * @throws IllegalArgumentException if the directory is not a Git repository or the
   *     {@code .git/hooks} directory is missing.
   */
  public GitHookInstaller(Path repositoryRoot) {
    this.repositoryRoot = repositoryRoot;
    this.hooksDirectory = repositoryRoot.resolve(".git/hooks");
    if (!Files.isDirectory(hooksDirectory)) {
      throw new IllegalArgumentException(
          "not a Git repository (missing .git/hooks directory): " + repositoryRoot);
    }
  }

  /**
   * Installs the {@code post-checkout} hook that triggers a Vitruvius VirtualModel reload
   * after each branch switch. If a hook file already exists, it is backed up before being
   * replaced.
   *
   * @throws IOException if the hook resource cannot be found, the backup cannot be created,
   *     or the hook file cannot be written.
   */
  public void installPostCheckoutHook() throws IOException {
    installHook(POST_CHECKOUT_HOOK);
  }

  /**
   * Installs the {@code pre-commit} hook that validates VirtualModel consistency before
   * allowing a commit to proceed. If a hook file already exists, it is backed up before
   * being replaced.
   *
   * @throws IOException if the hook resource cannot be found, the backup cannot be created,
   *     or the hook file cannot be written.
   */
  public void installPreCommitHook() throws IOException {
    installHook(PRE_COMMIT_HOOK);
  }

  /**
   * Installs the {@code post-merge} hook that triggers post-merge VSUM validation after
   * {@code git merge} or {@code git pull} completes. Writes a merge trigger file so that
   * the Vitruvius background watcher can validate the merged model state and write a
   * permanent merge metadata record. If a hook file already exists, it is backed up before
   * being replaced.
   *
   * @throws IOException if the hook resource cannot be found, the backup cannot be created,
   *     or the hook file cannot be written.
   */
  public void installPostMergeHook() throws IOException {
    installHook(POST_MERGE_HOOK);
  }

  /**
   * Installs the {@code post-commit} hook that triggers semantic changelog generation after
   * each commit. If a hook file already exists, it is backed up before being replaced.
   *
   * @throws IOException if the hook resource cannot be found, the backup cannot be created,
   *     or the hook file cannot be written.
   */
  public void installPostCommitHook() throws IOException {
    installHook(POST_COMMIT_HOOK);
  }

  /**
   * Installs all currently supported Git hooks ({@code post-checkout}, {@code pre-commit},
   * {@code post-merge}, and {@code post-commit}) and appends Vitruvius entries to
   * {@code .gitignore}. Does NOT configure the merge driver; call
   * {@link #installAll(List, String, List)} for a full project setup.
   *
   * @throws IOException if any hook cannot be installed.
   */
  public void installAllHooks() throws IOException {
    installPostCheckoutHook();
    installPreCommitHook();
    installPostMergeHook();
    installPostCommitHook();
    installGitignore();
    LOGGER.info("installed all Git hooks ({}, {}, {}, {})",
        POST_CHECKOUT_HOOK, PRE_COMMIT_HOOK, POST_MERGE_HOOK, POST_COMMIT_HOOK);
  }

  /**
   * Full project setup: installs all Git hooks, appends {@code .gitignore} entries,
   * registers model file extensions in {@code .gitattributes}, and configures the
   * Vitruvius custom merge driver in {@code .git/config} and
   * {@code .vitruvius/merge-driver.properties}.
   *
   * <p>This is the single call that replaces a manual {@code setup-merge-driver.sh}
   * script. It is idempotent: repeated calls are safe because each step checks for
   * existing content before writing.
   *
   * @param fileExtensions model file extensions to route through the merge driver
   *     (e.g. {@code List.of("brakesystem", "cad", "safety")}).
   * @param driverCommand  the Git driver command string with {@code %O %A %B %L %P}
   *     placeholders (e.g. {@code "java -jar /path/to/vitruvius-merge.jar %O %A %B %L %P"}).
   * @param specClasses    fully-qualified class names of the
   *     {@code ChangePropagationSpecification} implementations to load at merge time.
   * @throws IOException if any step cannot write its output file.
   */
  public void installAll(List<String> fileExtensions, String driverCommand,
      List<String> specClasses) throws IOException {
    installAllHooks();
    installGitAttributes(fileExtensions);
    installMergeDriverConfig(driverCommand, specClasses);
    LOGGER.info("full Vitruvius project setup complete ({} extension(s), {} spec(s))",
        fileExtensions.size(), specClasses.size());
  }

  /**
   * Appends Vitruvius entries to the repository's {@code .gitignore} file,
   * or creates the file if it does not exist yet.
   *
   * <p>If the file already contains the Vitruvius section (detected by the
   * presence of the guard comment), this method does nothing so that repeated
   * calls during re-initialization are safe.
   *
   * @throws IOException if the template cannot be read or the file cannot be written.
   */
  public void installGitignore() throws IOException {
    Path gitignoreFile = repositoryRoot.resolve(".gitignore");

    String templateContent;
    try (InputStream template = getClass().getResourceAsStream(GITIGNORE_TEMPLATE_PATH)) {
      if (template == null) {
        throw new IOException(
            "could not find .gitignore template at: " + GITIGNORE_TEMPLATE_PATH);
      }
      templateContent = new String(template.readAllBytes());
    }

    if (!Files.exists(gitignoreFile)) {
      // No .gitignore yet, create it from the template
      Files.writeString(gitignoreFile, templateContent);
      LOGGER.info("created .gitignore with Vitruvius entries");
      return;
    }

    // .gitignore exists, only append if Vitruvius section not already present
    String existingContent = Files.readString(gitignoreFile);
    if (existingContent.contains("# This section is managed by Vitruvius")) {
      return;
    }

    // Append with a blank line separator to avoid merging with existing content
    String separator = existingContent.endsWith("\n") ? "\n" : "\n\n";
    Files.writeString(gitignoreFile, existingContent + separator + templateContent,
        StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
    LOGGER.info("appended Vitruvius entries to existing .gitignore");
  }

  /**
   * Writes a {@code .gitattributes} file that assigns the Vitruvius custom merge driver
   * to the specified model file extensions. If the file already contains the Vitruvius
   * section (detected by a guard comment) this method is a no-op so repeated calls are safe.
   *
   * <p>Extensions may be provided with or without a leading {@code *.} prefix -- both
   * {@code "brakesystem"} and {@code "*.brakesystem"} produce the same output line.
   *
   * @param fileExtensions model file extensions to route through the Vitruvius merge driver.
   * @throws IOException if the file cannot be written.
   */
  public void installGitAttributes(List<String> fileExtensions) throws IOException {
    Path gitattributes = repositoryRoot.resolve(".gitattributes");
    String existing = Files.exists(gitattributes) ? Files.readString(gitattributes) : null;

    if (existing != null && existing.contains(GITATTRIBUTES_GUARD)) {
      return;
    }

    var sb = new StringBuilder();
    if (existing != null) {
      sb.append(existing);
      if (!existing.endsWith("\n")) {
        sb.append("\n");
      }
      sb.append("\n");
    }

    sb.append(GITATTRIBUTES_GUARD).append("\n");
    for (String ext : fileExtensions) {
      String pattern = ext.startsWith("*.") ? ext : "*." + ext;
      sb.append(pattern).append(" merge=vitruvius\n");
    }

    Files.writeString(gitattributes, sb.toString());
    LOGGER.info("written .gitattributes with {} pattern(s)", fileExtensions.size());
  }

  /**
   * Configures the Vitruvius custom merge driver for this repository by writing two items:
   * <ol>
   *   <li>A {@code [merge "vitruvius"]} section in {@code .git/config} containing the
   *       driver command. Git invokes this command once per conflicting model file during
   *       {@code git merge}.</li>
   *   <li>{@code .vitruvius/merge-driver.properties} listing the fully-qualified
   *       {@code ChangePropagationSpecification} class names that the driver loads via
   *       reflection at runtime.</li>
   * </ol>
   *
   * <p>The {@code driverCommand} should be the full invocation string that Git will use,
   * with the standard Git merge driver placeholders {@code %O %A %B %L %P}. Example:
   * <pre>
   *   java -jar /path/to/vitruvius-merge-driver.jar %O %A %B %L %P
   * </pre>
   *
   * @param driverCommand the Git driver command string (with %O %A %B %L %P placeholders).
   * @param specClasses   fully-qualified class names of ChangePropagationSpecifications.
   * @throws IOException if any file cannot be written.
   */
  public void installMergeDriverConfig(String driverCommand, List<String> specClasses)
      throws IOException {

    // Write [merge "vitruvius"] to .git/config via JGit StoredConfig
    try (org.eclipse.jgit.api.Git git =
             org.eclipse.jgit.api.Git.open(repositoryRoot.toFile())) {
      org.eclipse.jgit.lib.StoredConfig config = git.getRepository().getConfig();
      config.setString("merge", "vitruvius", "name", "Vitruvius semantic merge");
      config.setString("merge", "vitruvius", "driver", driverCommand);
      config.save();
      LOGGER.info("wrote [merge \"vitruvius\"] driver entry to .git/config");
    }

    // Write .vitruvius/merge-driver.properties for GitMergeDriver bootstrap
    Path vitruviusDir = repositoryRoot.resolve(".vitruvius");
    Files.createDirectories(vitruviusDir);
    Path propsFile = vitruviusDir.resolve("merge-driver.properties");
    Files.writeString(propsFile,
        "specifications=" + String.join(",", specClasses) + "\n");
    LOGGER.info("wrote merge-driver.properties with {} spec class(es)", specClasses.size());
  }

  /**
   * Removes the {@code post-checkout} hook. If a backup exists from a previous installation,
   * the backup is restored so that the developer's original hook is active again.
   *
   * @throws IOException if the hook file cannot be deleted or the backup cannot be restored.
   */
  public void uninstallPostCheckoutHook() throws IOException {
    uninstallHook(POST_CHECKOUT_HOOK);
  }

  /**
   * Removes the {@code pre-commit} hook. If a backup exists from a previous installation,
   * the backup is restored so that the developer's original hook is active again.
   *
   * @throws IOException if the hook file cannot be deleted or the backup cannot be restored.
   */
  public void uninstallPreCommitHook() throws IOException {
    uninstallHook(PRE_COMMIT_HOOK);
  }

  /**
   * Removes the {@code post-merge} hook. If a backup exists from a previous installation,
   * the backup is restored so that the developer's original hook is active again.
   *
   * @throws IOException if the hook file cannot be deleted or the backup cannot be restored.
   */
  public void uninstallPostMergeHook() throws IOException {
    uninstallHook(POST_MERGE_HOOK);
  }

  /**
   * Removes the {@code post-commit} hook. If a backup exists from a previous installation,
   * the backup is restored so that the developer's original hook is active again.
   *
   * @throws IOException if the hook file cannot be deleted or the backup cannot be restored.
   */
  public void uninstallPostCommitHook() throws IOException {
    uninstallHook(POST_COMMIT_HOOK);
  }

  /**
   * Removes the Vitruvius section from the repository's {@code .gitattributes} file.
   * The section is identified by the {@code # Vitruvius merge driver} guard comment that
   * {@link #installGitAttributes(List)} writes. If no such section exists this method
   * does nothing. The rest of the file is preserved unchanged.
   *
   * @throws IOException if the file cannot be read or written.
   */
  public void uninstallGitAttributes() throws IOException {
    Path gitattributes = repositoryRoot.resolve(".gitattributes");
    if (!Files.exists(gitattributes)) {
      return;
    }
    String content = Files.readString(gitattributes);
    if (!content.contains(GITATTRIBUTES_GUARD)) {
      return;
    }

    List<String> lines = new ArrayList<>(List.of(content.split("\n", -1)));
    int guardIndex = -1;
    for (int i = 0; i < lines.size(); i++) {
      if (lines.get(i).stripTrailing().equals(GITATTRIBUTES_GUARD)) {
        guardIndex = i;
        break;
      }
    }
    if (guardIndex < 0) {
      return;
    }

    lines.remove(guardIndex);
    while (guardIndex < lines.size() && lines.get(guardIndex).contains("merge=vitruvius")) {
      lines.remove(guardIndex);
    }
    // Strip trailing blank lines left by the separator that installGitAttributes() prepended
    while (!lines.isEmpty() && lines.get(lines.size() - 1).isBlank()) {
      lines.remove(lines.size() - 1);
    }

    String result = String.join("\n", lines);
    Files.writeString(gitattributes, result.isEmpty() ? "" : result + "\n");
    LOGGER.info("removed Vitruvius section from .gitattributes");
  }

  /**
   * Removes the Vitruvius merge driver configuration: deletes the
   * {@code [merge "vitruvius"]} section from {@code .git/config} and deletes
   * {@code .vitruvius/merge-driver.properties} if it exists. If the section is
   * not present this method does nothing.
   *
   * @throws IOException if the config cannot be saved or the properties file cannot be deleted.
   */
  public void uninstallMergeDriverConfig() throws IOException {
    try (org.eclipse.jgit.api.Git git =
             org.eclipse.jgit.api.Git.open(repositoryRoot.toFile())) {
      org.eclipse.jgit.lib.StoredConfig config = git.getRepository().getConfig();
      if (config.getSections().contains("merge")
          && config.getSubsections("merge").contains("vitruvius")) {
        config.unsetSection("merge", "vitruvius");
        config.save();
        LOGGER.info("removed [merge \"vitruvius\"] from .git/config");
      } else {
        LOGGER.debug("no [merge \"vitruvius\"] section in .git/config, nothing to remove");
      }
    }

    Path propsFile = repositoryRoot.resolve(".vitruvius/merge-driver.properties");
    if (Files.deleteIfExists(propsFile)) {
      LOGGER.info("deleted merge-driver.properties");
    } else {
      LOGGER.debug("merge-driver.properties did not exist, nothing to delete");
    }
  }

  /**
   * Full project teardown: uninstalls all Git hooks and removes the merge driver
   * configuration ({@code .git/config} entry, {@code .vitruvius/merge-driver.properties},
   * and the Vitruvius section from {@code .gitattributes}). The inverse of
   * {@link #installAll(List, String, List)}.
   *
   * @throws IOException if any step cannot remove its target file.
   */
  public void uninstallAll() throws IOException {
    uninstallPostCheckoutHook();
    uninstallPreCommitHook();
    uninstallPostMergeHook();
    uninstallPostCommitHook();
    uninstallGitAttributes();
    uninstallMergeDriverConfig();
    LOGGER.info("full Vitruvius project teardown complete");
  }

  /**
   * Returns true if the {@code post-checkout} hook file is currently present in the
   * hooks' directory. This does not verify whether the file is the Vitruvius-managed version.
   */
  public boolean isPostCheckoutHookInstalled() {
    return Files.exists(hooksDirectory.resolve(POST_CHECKOUT_HOOK));
  }

  /**
   * Returns true if the {@code pre-commit} hook file is currently present in the
   * hooks' directory. This does not verify whether the file is the Vitruvius-managed version.
   */
  public boolean isPreCommitHookInstalled() {
    return Files.exists(hooksDirectory.resolve(PRE_COMMIT_HOOK));
  }

  /**
   * Returns true if the {@code post-merge} hook file is currently present in the
   * hooks directory. This does not verify whether the file is the Vitruvius-managed version.
   */
  public boolean isPostMergeHookInstalled() {
    return Files.exists(hooksDirectory.resolve(POST_MERGE_HOOK));
  }

  /**
   * Returns true if the {@code post-commit} hook file is currently present in the
   * hooks directory. This does not verify whether the file is the Vitruvius-managed version.
   */
  public boolean isPostCommitHookInstalled() {
    return Files.exists(hooksDirectory.resolve(POST_COMMIT_HOOK));
  }

  /**
   * Returns true if all four Vitruvius hooks are currently installed.
   * Used by ManualTest to skip reinstallation on subsequent runs.
   */
  public boolean areAllHooksInstalled() {
    return isPostCheckoutHookInstalled()
        && isPreCommitHookInstalled()
        && isPostMergeHookInstalled()
        && isPostCommitHookInstalled();
  }

  /**
   * Performs the actual installation of a single hook by name.
   *
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

    // back up any pre-existing hook so it can be restored on uninstallation
    // to preserve hooks that the developer configured before Vitruvius was set up.
    if (Files.exists(hookPath)) {
      var backupPath = hooksDirectory.resolve(hookName + ".backup");
      Files.move(hookPath, backupPath, StandardCopyOption.REPLACE_EXISTING);
      LOGGER.info("backed up existing {} hook", hookName);
    }

    var resourcePath = HOOKS_RESOURCE_PATH + hookName;
    try (InputStream hookTemplate = getClass().getResourceAsStream(resourcePath)) {
      if (hookTemplate == null) {
        throw new IOException("hook template not found in resources: " + resourcePath);
      }
      Files.copy(hookTemplate, hookPath, StandardCopyOption.REPLACE_EXISTING);
    }

    makeExecutable(hookPath);
    LOGGER.info("installed {} hook at: {}", hookName, hookPath);
  }

  /**
   * Performs the actual uninstallation of a single hook by name. This shared helper is used
   * by all uninstall methods.
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
    } catch (UnsupportedOperationException | IOException e) {
      // POSIX permissions are not available on Windows. The hook will still be executed
      // correctly by Git Bash, which interprets the shebang line without requiring the
      // executable bit to be set on the file system.
      LOGGER.debug("could not set POSIX permissions (likely running on Windows): {}",
          e.getMessage());
    }
  }
}
