package tools.vitruv.framework.vsum.branch;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.PersonIdent;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Shared test utilities for Git-based unit tests.
 * Provides repository initialization and model file helpers used across
 * CommitManagerTest, MergeManagerTest, and VersioningServiceTest.
 */
public class GitTestHelper {

    /**
     * Initializes a Git repository with a single initial commit on master.
     * Git config is set so JGit has a valid author identity for commits and tags.
     */
    public static Git initRepo(Path repoDir) throws Exception {
        var git = Git.init()
                .setDirectory(repoDir.toFile())
                .setInitialBranch("master")
                .call();

        // Set author identity so JGit can create commits and annotated tags
        git.getRepository().getConfig().setString("user", null, "name", "Test User");
        git.getRepository().getConfig().setString("user", null, "email", "test@example.com");
        git.getRepository().getConfig().save();

        // Initial commit so HEAD exists
        var file = repoDir.resolve("init.txt");
        Files.writeString(file, "initial commit");
        git.add().addFilepattern("init.txt").call();
        git.commit().setMessage("Initial commit").call();

        return git;
    }

    /**
     * Creates a model file with the given extension under the repo root and returns its path.
     */
    static Path createModelFile(Path repoDir, String name, String content) throws Exception {
        var file = repoDir.resolve(name);
        Files.writeString(file, content);
        return file;
    }

    /**
     * Stages and commits a file directly via JGit, bypassing CommitManager.
     * Used to set up repository state before testing specific scenarios.
     */
    public static String commitFile(Git git, Path repoDir, String filename, String content,
                                    String message) throws Exception {
        var file = repoDir.resolve(filename);
        Files.writeString(file, content);
        git.add().addFilepattern(filename).call();
        var commit = git.commit().setMessage(message).call();
        return commit.getName();
    }
}