package tools.vitruv.framework.vsum.branch;

import org.eclipse.jgit.api.Git;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tools.vitruv.framework.vsum.branch.data.BranchState;
import tools.vitruv.framework.vsum.branch.data.ModelMergeResult;
import tools.vitruv.framework.vsum.branch.exception.BranchOperationException;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static tools.vitruv.framework.vsum.branch.GitTestHelper.*;

/**
 * Unit tests for {@link MergeManager}.
 *
 * <p>Tests use real temporary Git repositories to exercise JGit merge behavior
 * end to end. Conflict scenarios are created by making diverging changes to the
 * same file on two branches.
 */
class MergeManagerTest {

    @Nested
    @DisplayName("merge - fast-forward")
    class FastForward {

        @Test
        @DisplayName("fast-forward merge succeeds when target has no new commits")
        void fastForwardMergeSucceeds(@TempDir Path repoDir) throws Exception {
            try (var git = initRepo(repoDir)) {
                // Create feature branch and add a commit
                git.branchCreate().setName("feature").call();
                git.checkout().setName("feature").call();
                commitFile(git, repoDir, "system.model", "<System/>", "Add system model");

                // Switch back to master - it has no new commits so merge is fast-forward
                git.checkout().setName("master").call();
                var manager = new MergeManager(repoDir);

                var result = manager.merge("feature");

                assertEquals(ModelMergeResult.MergeStatus.FAST_FORWARD, result.getStatus());
                assertEquals("feature", result.getSourceBranch());
                assertEquals("master", result.getTargetBranch());
                assertTrue(result.isSuccessful());
                assertTrue(result.getConflictingFiles().isEmpty());
                assertNotNull(result.getMergeCommitSha(),
                        "fast-forward result must carry the new HEAD SHA");
            }
        }

        @Test
        @DisplayName("fast-forward merge marks source branch as MERGED")
        void fastForwardMarksBranchMerged(@TempDir Path repoDir) throws Exception {
            try (var git = initRepo(repoDir)) {
                var branchManager = new BranchManager(repoDir);
                branchManager.createBranch("feature", "master");
                git.checkout().setName("feature").call();
                commitFile(git, repoDir, "system.model", "<System/>", "Add model");
                git.checkout().setName("master").call();

                new MergeManager(repoDir).merge("feature");

                assertEquals(BranchState.MERGED, branchManager.getBranchState("feature"),
                        "source branch must be marked MERGED after successful merge");
            }
        }
    }

    @Nested
    @DisplayName("merge - non-fast-forward")
    class NonFastForward {

        @Test
        @DisplayName("non-fast-forward merge creates a merge commit")
        void nonFastForwardCreatesMergeCommit(@TempDir Path repoDir) throws Exception {
            try (var git = initRepo(repoDir)) {
                // Create diverging history: feature adds one file, master adds another
                git.branchCreate().setName("feature").call();
                git.checkout().setName("feature").call();
                commitFile(git, repoDir, "component.model", "<Component/>", "Add component");

                git.checkout().setName("master").call();
                commitFile(git, repoDir, "system.model", "<System/>", "Add system");

                var manager = new MergeManager(repoDir);
                var result = manager.merge("feature");

                assertEquals(ModelMergeResult.MergeStatus.SUCCESS, result.getStatus());
                assertTrue(result.isSuccessful());
                assertNotNull(result.getMergeCommitSha(),
                        "merge commit SHA must be set for non-fast-forward merge");
                assertFalse(result.isFastForward());
                assertTrue(result.getConflictingFiles().isEmpty());
            }
        }

        @Test
        @DisplayName("non-fast-forward merge marks source branch as MERGED")
        void nonFastForwardMarksBranchMerged(@TempDir Path repoDir) throws Exception {
            try (var git = initRepo(repoDir)) {
                var branchManager = new BranchManager(repoDir);
                branchManager.createBranch("feature", "master");

                git.checkout().setName("feature").call();
                commitFile(git, repoDir, "component.model", "<Component/>", "Add component");

                git.checkout().setName("master").call();
                commitFile(git, repoDir, "system.model", "<System/>", "Add system");

                new MergeManager(repoDir).merge("feature");

                assertEquals(BranchState.MERGED, branchManager.getBranchState("feature"));
            }
        }
    }

    @Nested
    @DisplayName("merge - conflicts")
    class Conflicts {

        @Test
        @DisplayName("returns CONFLICTING status when same file modified on both branches")
        void detectsConflicts(@TempDir Path repoDir) throws Exception {
            try (var git = initRepo(repoDir)) {
                // Both branches modify the same file differently
                commitFile(git, repoDir, "system.model", "<System v='1'/>", "Base version");

                git.branchCreate().setName("feature").call();
                git.checkout().setName("feature").call();
                commitFile(git, repoDir, "system.model", "<System v='feature'/>",
                        "Feature change");

                git.checkout().setName("master").call();
                commitFile(git, repoDir, "system.model", "<System v='master'/>",
                        "Master change");

                var manager = new MergeManager(repoDir);
                var result = manager.merge("feature");

                assertEquals(ModelMergeResult.MergeStatus.CONFLICTING, result.getStatus());
                assertFalse(result.isSuccessful());
                assertFalse(result.getConflictingFiles().isEmpty(),
                        "conflicting files list must be populated");
                assertTrue(result.getConflictingFiles().contains("system.model"),
                        "system.model must appear in conflicting files");
                assertNull(result.getMergeCommitSha(),
                        "no merge commit SHA for conflicting merge");
            }
        }

        @Test
        @DisplayName("does not mark source branch as MERGED when conflicts detected")
        void doesNotMarkMergedOnConflict(@TempDir Path repoDir) throws Exception {
            try (var git = initRepo(repoDir)) {
                commitFile(git, repoDir, "system.model", "<System v='1'/>", "Base");

                var branchManager = new BranchManager(repoDir);
                branchManager.createBranch("feature", "master");

                git.checkout().setName("feature").call();
                commitFile(git, repoDir, "system.model", "<System v='feature'/>", "Feature");

                git.checkout().setName("master").call();
                commitFile(git, repoDir, "system.model", "<System v='master'/>", "Master");

                new MergeManager(repoDir).merge("feature");

                assertEquals(BranchState.ACTIVE, branchManager.getBranchState("feature"),
                        "source branch must remain ACTIVE when merge has conflicts");
            }
        }
    }

    @Nested
    @DisplayName("merge - auto-delete after merge")
    class AutoDelete {

        @Test
        @DisplayName("deletes source branch after successful merge when flag is set")
        void deletesBranchAfterMerge(@TempDir Path repoDir) throws Exception {
            try (var git = initRepo(repoDir)) {
                git.branchCreate().setName("feature").call();
                git.checkout().setName("feature").call();
                commitFile(git, repoDir, "system.model", "<System/>", "Add model");
                git.checkout().setName("master").call();

                new MergeManager(repoDir).merge("feature", true);

                var ref = git.getRepository().findRef("refs/heads/feature");
                assertNull(ref, "feature branch must be deleted after merge with deleteAfterMerge=true");
            }
        }

        @Test
        @DisplayName("keeps source branch when deleteAfterMerge is false")
        void keepsBranchWhenFlagFalse(@TempDir Path repoDir) throws Exception {
            try (var git = initRepo(repoDir)) {
                git.branchCreate().setName("feature").call();
                git.checkout().setName("feature").call();
                commitFile(git, repoDir, "system.model", "<System/>", "Add model");
                git.checkout().setName("master").call();

                new MergeManager(repoDir).merge("feature", false);

                var ref = git.getRepository().findRef("refs/heads/feature");
                assertNotNull(ref, "feature branch must be kept when deleteAfterMerge=false");
            }
        }
    }

    @Nested
    @DisplayName("merge - error cases")
    class ErrorCases {

        @Test
        @DisplayName("throws when source branch does not exist")
        void throwsOnNonExistentSource(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new MergeManager(repoDir);
                assertThrows(BranchOperationException.class,
                        () -> manager.merge("nonexistent"));
            }
        }

        @Test
        @DisplayName("throws when trying to merge a branch into itself")
        void throwsOnSelfMerge(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new MergeManager(repoDir);
                assertThrows(BranchOperationException.class,
                        () -> manager.merge("master"));
            }
        }
    }

    @Nested
    @DisplayName("merge - trigger file")
    class TriggerFile {

        @Test
        @DisplayName("writes merge trigger file after successful merge")
        void writesMergeTriggerOnSuccess(@TempDir Path repoDir) throws Exception {
            try (var git = initRepo(repoDir)) {
                git.branchCreate().setName("feature").call();
                git.checkout().setName("feature").call();
                commitFile(git, repoDir, "system.model", "<System/>", "Add model");
                git.checkout().setName("master").call();

                new MergeManager(repoDir).merge("feature");

                var triggerFile = repoDir.resolve(".vitruvius/merge-trigger");
                assertTrue(Files.exists(triggerFile),
                        "merge trigger must be written after successful merge");
                var content = Files.readString(triggerFile);
                assertEquals(5, content.split("\\|").length,
                        "merge trigger must have 5 pipe-delimited fields");
                assertTrue(content.contains("feature"),
                        "merge trigger must contain source branch name");
                assertTrue(content.contains("master"),
                        "merge trigger must contain target branch name");
            }
        }

        @Test
        @DisplayName("does not write merge trigger when merge has conflicts")
        void doesNotWriteTriggerOnConflict(@TempDir Path repoDir) throws Exception {
            try (var git = initRepo(repoDir)) {
                commitFile(git, repoDir, "system.model", "<System v='1'/>", "Base");
                git.branchCreate().setName("feature").call();
                git.checkout().setName("feature").call();
                commitFile(git, repoDir, "system.model", "<System v='feature'/>", "Feature");
                git.checkout().setName("master").call();
                commitFile(git, repoDir, "system.model", "<System v='master'/>", "Master");

                new MergeManager(repoDir).merge("feature");

                var triggerFile = repoDir.resolve(".vitruvius/merge-trigger");
                assertFalse(Files.exists(triggerFile),
                        "merge trigger must not be written when merge has conflicts");
            }
        }
    }
}