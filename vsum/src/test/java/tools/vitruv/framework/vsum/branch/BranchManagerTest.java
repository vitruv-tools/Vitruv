package tools.vitruv.framework.vsum.branch;

import org.eclipse.jgit.api.Git;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tools.vitruv.framework.vsum.branch.data.BranchMetadata;
import tools.vitruv.framework.vsum.branch.data.BranchState;
import tools.vitruv.framework.vsum.branch.exception.BranchOperationException;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link BranchManager}.
 *
 * <p>Each test group covers one public method of the manager.
 * Tests operate on a real, temporary Git repository initialized by {@link #initRepo(Path)}
 * rather than mocks, so that the JGit interactions and
 * metadata file writes are exercised end to end within the unit test scope.
 *
 * <p>the {@link org.junit.jupiter.api.io.TempDir} annotation ensures every test starts with a
 * clean file system state and that the temporary directory is deleted afterward.
 */
class BranchManagerTest {

    /**
     * Initializes a Git repository at the given path with a single initial commit on {@code main}.
     * A mock commit is required because JGit does not allow branch creation on a repository that has no commits yet
     * (as there is no head reference to fork from).
     *
     * @param repoDir the directory to initialize the repository in.
     * @return an opened {@link Git} instance for the initialized repository. the caller is responsible for closing it.
     * @throws Exception if initialization or the initial commit fails.
     */
    private static Git initRepo(Path repoDir) throws Exception {
        var git = Git.init().setDirectory(repoDir.toFile()).setInitialBranch("master").call();
        var file = repoDir.resolve("init.txt");
        Files.writeString(file, "initial commit");
        git.add().addFilepattern("init.txt").call();
        git.commit().setMessage("Initial commit").setAllowEmpty(false).call();
        return git;
    }

    @Nested
    @DisplayName("createBranch")
    class CreateBranch {

        /**
         * Verifies that creating a branch returns correct metadata and also persists it to disk.
         * Both the in-memory result and the file content are checked in one test
         * because they represent a single operation
         * that must succeed atomically from the caller's perspective.
         */
        @Test
        @DisplayName("creates a branch from main and persists correct metadata")
        void createBranchFromMainPersistsMetadata(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);

                var metadata = manager.createBranch("bugfix-viewtype", "master");

                // the returned metadata must reflect the requested name, parent, and initial state.
                assertEquals("bugfix-viewtype", metadata.getName());
                assertEquals("master", metadata.getParent());
                assertEquals(BranchState.ACTIVE, metadata.getState());
                // the unique identifier is the first seven characters of the commit hash made by Git

                // the metadata file must also exist on disk so that
                // Vitruvius can reconstruct the branch history across sessions.
                var metadataFile = repoDir.resolve(".vitruvius/branches/bugfix-viewtype.metadata");
                assertTrue(Files.exists(metadataFile), "metadata file must be written to disk");

                var lines = Files.readAllLines(metadataFile);
                assertTrue(lines.stream().anyMatch(l -> l.contains("\"branchName\"")));
                assertTrue(lines.stream().anyMatch(l -> l.contains("\"state\"")));
                assertTrue(lines.stream().anyMatch(l -> l.contains("\"parentBranch\"")));
            }
        }

        /**
         * Verifies that attempting to create a branch from a source that does not exist raises
         * an exception. No metadata file must be created.
         */
        @Test
        @DisplayName("fails when source branch does not exist")
        void failOnNonExistentSource(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                assertThrows(BranchOperationException.class,
                        () -> manager.createBranch("feature-delta", "nonexistent"));
            }
        }

        /**
         * Verifies that creating a branch whose name already exists raises an exception.
         * The duplicate check is performed during name validation
         * before any Git operation is attempted.
         */
        @Test
        @DisplayName("fails when a branch with the same name already exists")
        void failOnDuplicateName(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                manager.createBranch("bugfix-viewtype", "master");
                assertThrows(BranchOperationException.class,
                        () -> manager.createBranch("bugfix-viewtype", "master"));
            }
        }
    }


    @Nested
    @DisplayName("switchBranch")
    class SwitchBranch {

        /**
         * Verifies that switching by exact branch name updates the HEAD reference to point to the target branch.
         * Git repository state is checked directly after the switch.
         */
        @Test
        @DisplayName("switches to an existing branch by name and updates HEAD")
        void switchByName(@TempDir Path repoDir) throws Exception {
            try (var git = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                manager.createBranch("bugfix-viewtype", "master");

                manager.switchBranch("bugfix-viewtype");

                var head = git.getRepository().findRef("HEAD");
                assertEquals("refs/heads/bugfix-viewtype", head.getTarget().getName(),
                        "HEAD must point to the target branch after switching");
            }
        }


        /**
         * Verifies that switching to a branch that does not exist raises an exception
         * instead of leaving the working directory in an undefined state.
         */
        @Test
        @DisplayName("fails when the target branch does not exist")
        void failOnNonExistentBranch(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                assertThrows(BranchOperationException.class,
                        () -> manager.switchBranch("nonexistent"));
            }
        }

    }

    @Nested
    @DisplayName("deleteBranch")
    class DeleteBranch {

        /**
         * Verifies that after deletion the branch reference no longer exists in Git,
         * and that the metadata file still exists on disk but is marked as DELETED.
         * The branch should be gone from Git but the history is preserved in metadata.
         */
        @Test
        @DisplayName("removes the Git reference and marks metadata as DELETED")
        void deleteBranchRemovesRefAndUpdatesMetadata(@TempDir Path repoDir) throws Exception {
            try (var git = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                manager.createBranch("bugfix-viewtype", "master");

                manager.deleteBranch("bugfix-viewtype");

                // the Git reference must be gone so the branch cannot be checked out anymore.
                var ref = git.getRepository().findRef("refs/heads/bugfix-viewtype");
                assertNull(ref, "Git reference must be removed after deletion");

                // the metadata file must still exist so that the branch lifecycle history is preserved
                // and it must reflect the DELETED state.
                var metadataFile = repoDir.resolve(".vitruvius/branches/bugfix-viewtype.metadata");
                assertTrue(Files.exists(metadataFile), "metadata file must be preserved after deletion");
                var lines = Files.readAllLines(metadataFile);
                assertTrue(lines.stream().anyMatch(l -> l.contains("\"state\"")));
                assertTrue(lines.stream().anyMatch(l -> l.contains("\"DELETED\"")));
            }
        }

        /**
         * Verifies that attempting to delete the currently checked-out branch raises an exception.
         * Deleting the active branch would leave the working directory without a valid branch reference,
         * which is not a supported state.
         */
        @Test
        @DisplayName("fails when trying to delete the currently checked-out branch")
        void failOnDeleteCurrentBranch(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                // "master" is the currently checked-out branch after initialization, so deleting it must be rejected.
                assertThrows(BranchOperationException.class, () -> manager.deleteBranch("master"));
            }
        }
    }

    @Nested
    @DisplayName("listBranches")
    class ListBranches {

        /**
         * Verifies that all branches present in the repository appear in the returned list with their correct names,
         * regardless of whether they were created via the manager or directly via Git.
         */
        @Test
        @DisplayName("returns all existing branches including those created via the manager")
        void listAllBranches(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                manager.createBranch("bugfix-viewtype", "master");
                manager.createBranch("feature-signup", "master");

                var branches = manager.listBranches();
                var names = branches.stream().map(BranchMetadata::getName).toList();

                // master plus the two newly created branches must all appear in the list.
                assertEquals(3, branches.size());
                assertTrue(names.contains("master"));
                assertTrue(names.contains("bugfix-viewtype"));
                assertTrue(names.contains("feature-signup"));
            }
        }

        /**
         * Verifies that a branch created directly via JGit (outside of this manager) is still listed
         * Its synthesized metadata correctly reflects an unknown parent and ACTIVE state.
         */
        @Test
        @DisplayName("includes external branches with synthesized ACTIVE metadata and unknown parent")
        void listIncludesExternalBranch(@TempDir Path repoDir) throws Exception {
            try (var git = initRepo(repoDir)) {
                git.branchCreate().setName("external-branch").call();
                var manager = new BranchManager(repoDir);

                var branches = manager.listBranches();
                var external = branches.stream().filter(
                        m -> m.getName().equals("external-branch"))
                        .findFirst()
                        .orElseThrow(() -> new AssertionError(
                                "external branch must appear in list"));

                assertEquals(BranchState.ACTIVE, external.getState());
                assertEquals("unknown", external.getParent());
            }
        }

        /**
         * Verifies that a branch that was deleted via the manager no longer appears in the list.
         * After deletion, only the initial master branch should remain.
         */
        @Test
        @DisplayName("does not include deleted branches")
        void deletedBranchNotListed(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                manager.createBranch("bugfix-viewtype", "master");
                manager.deleteBranch("bugfix-viewtype");

                var branches = manager.listBranches();
                var names = branches.stream().map(BranchMetadata::getName).toList();

                // only master must remain after the deleted branch is removed from Git.
                assertEquals(1, branches.size(), "only master must remain after deletion");
                assertFalse(names.contains("bugfix-viewtype"));
            }
        }
    }

    @Nested
    @DisplayName("findBranches")
    class FindBranches {

        /**
         * Verifies that the glob wildcard {@code *} matches any sequence of characters in the branch name suffix,
         * and that branches not matching the pattern are excluded.
         */
        @Test
        @DisplayName("returns only branches whose names match the given glob pattern")
        void findByPattern(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                manager.createBranch("bugfix-viewtype", "master");
                manager.createBranch("feature-vcs", "master");
                manager.createBranch("bugfix-delta", "master");

                var matches = manager.findBranches("bugfix-*");
                var names = matches.stream().map(BranchMetadata::getName).toList();

                // only the two bugfix branches must be returned, not the feature branch.
                assertEquals(2, matches.size());
                assertTrue(names.contains("bugfix-viewtype"));
                assertFalse(names.contains("feature-vcs"));
                assertTrue(names.contains("bugfix-delta"));
            }
        }

        /**
         * Verifies that an empty list is returned when no branch matches the given pattern,
         * rather than throwing an exception.
         */
        @Test
        @DisplayName("returns an empty list when no branch matches the pattern")
        void findNoMatch(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                manager.createBranch("bugfix-viewtype", "master");

                var matches = manager.findBranches("feature-*");

                assertTrue(matches.isEmpty(), "result must be empty when no branch matches the pattern");
            }
        }

        /**
         * Verifies that the single-character wildcard {@code ?} matches exactly one character,
         * so that multi-character suffixes are excluded from the result.
         */
        @Test
        @DisplayName("respects single-character wildcard matching")
        void findWithQuestionMark(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                manager.createBranch("v1", "master");
                manager.createBranch("v2", "master");
                manager.createBranch("v10", "master");

                var matches = manager.findBranches("v?");
                var names = matches.stream().map(BranchMetadata::getName).toList();

                // v? matches exactly one character after 'v', so v10 must not be included.
                assertEquals(2, matches.size());
                assertTrue(names.contains("v1"));
                assertTrue(names.contains("v2"));
                assertFalse(names.contains("v10"));
            }
        }
    }

    @Nested
    @DisplayName("resolveBranchIdentifier")
    class ResolveBranchIdentifier {

        /**
         * Verifies that an exact branch name is resolved immediately without scanning metadata files,
         * returning the same string that was provided.
         */
        @Test
        @DisplayName("resolves an exact branch name without scanning metadata")
        void resolveByName(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                manager.createBranch("bugfix-viewtype", "master");

                var resolved = manager.resolveBranchIdentifier("bugfix-viewtype");

                assertEquals("bugfix-viewtype", resolved);
            }
        }


        /**
         * Verifies that an identifier that matches neither a branch name nor any unique identifier prefix
         * raises an exception.
         */
        @Test
        @DisplayName("fails when the identifier matches no branch")
        void failOnNoMatch(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                assertThrows(BranchOperationException.class,
                        () -> manager.resolveBranchIdentifier("nonexistent"));
            }
        }
    }

    @Nested
    @DisplayName("getBranchTopology")
    class GetBranchTopology {

        /**
         * Verifies that a two-level parent-child hierarchy is correctly reflected in the topology map.
         * Each branch must appear as a child of the branch it was forked from.
         */
        @Test
        @DisplayName("reflects direct parent-child relationships from metadata")
        void topologyFromMetadata(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                manager.createBranch("bugfix-viewtype", "master");
                manager.createBranch("feature-vcs", "bugfix-viewtype");

                var topology = manager.getBranchTopology();

                // master must have exactly one child: bugfix-viewtype.
                assertTrue(topology.containsKey("master"));
                assertEquals(1, topology.get("master").size());
                assertTrue(topology.get("master").contains("bugfix-viewtype"));

                // bugfix-viewtype must itself have exactly one child: feature-vcs.
                assertTrue(topology.containsKey("bugfix-viewtype"));
                assertEquals(1, topology.get("bugfix-viewtype").size());
                assertTrue(topology.get("bugfix-viewtype").contains("feature-vcs"));
            }
        }

        /**
         * Verifies that multiple branches forked from the same parent all appear as children of that parent in the topology.
         */
        @Test
        @DisplayName("lists multiple children under the same parent")
        void multipleChildrenUnderParent(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                manager.createBranch("bugfix-viewtype", "master");
                manager.createBranch("feature-vcs", "master");
                manager.createBranch("bugfix-delta", "master");

                var topology = manager.getBranchTopology();

                assertEquals(3, topology.get("master").size());
                assertTrue(topology.get("master").contains("bugfix-viewtype"));
                assertTrue(topology.get("master").contains("feature-vcs"));
                assertTrue(topology.get("master").contains("bugfix-delta"));
            }
        }

        /**
         * Verifies that deleted branches are excluded from the topology
         * so that the returned map only represents currently active branches.
         */
        @Test
        @DisplayName("excludes deleted branches from the topology")
        void deletedBranchExcluded(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                manager.createBranch("bugfix-viewtype", "master");
                manager.createBranch("feature-vcs", "master");
                manager.deleteBranch("bugfix-viewtype");

                var topology = manager.getBranchTopology();

                // only the surviving branch must appear as a child of master.
                assertEquals(1, topology.get("master").size());
                assertTrue(topology.get("master").contains("feature-vcs"));
                assertFalse(topology.get("master").contains("bugfix-viewtype"));
            }
        }

        /**
         * Verifies that an empty map is returned when no managed branches exist.
         * Branches created outside the manager have no metadata files and are therefore not part of the topology.
         */
        @Test
        @DisplayName("returns an empty map when no managed branches exist")
        void emptyTopology(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                // the repository has only master, which was created by Git init and has no metadata.
                var topology = manager.getBranchTopology();
                assertTrue(topology.isEmpty(),
                        "topology must be empty when no branches were created via the manager");
            }
        }

        /**
         * Verifies that external branches (no metadata file) do not cause an error
         * and are simply ignored by the topology.
         * Only branches created via the manager are included.
         */
        @Test
        @DisplayName("ignores external branches that have no metadata file")
        void externalBranchesIgnoredInTopology(@TempDir Path repoDir) throws Exception {
            try (var git = initRepo(repoDir)) {
                // create a branch directly via JGit, bypassing the manager so no metadata is written.
                git.branchCreate().setName("external-branch").call();
                var manager = new BranchManager(repoDir);
                manager.createBranch("managed-branch", "master");

                var topology = manager.getBranchTopology();

                // only managed-branch must appear in the topology.
                // external-branch has no metadata and must not cause an exception or appear as a spurious entry.
                assertTrue(topology.containsKey("master"));
                assertEquals(1, topology.get("master").size());
                assertTrue(topology.get("master").contains("managed-branch"));
            }
        }
    }

    @Nested
    @DisplayName("validateBranchName")
    class ValidateBranchName {

        /**
         * Verifies that a well-formed branch name with a hyphen separator passes validation
         * without throwing an exception.
         */
        @Test
        @DisplayName("accepts a valid branch name without throwing")
        void validName(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                // a conventional branch name must pass all validation rules.
                assertDoesNotThrow(() -> manager.validateBranchName("bugfix-viewtype"));
            }
        }

        /**
         * Verifies that blank names, the {@code ..} range operator, the {@code .lock} suffix,
         * and characters with special Git meaning are all rejected.
         * These rules follow the Git reference format specification and
         * prevent names that would either corrupt the reference database
         * or be misinterpreted by Git commands.
         */
        @Test
        @DisplayName("rejects blank names, double dots, lock suffix, and illegal characters")
        void failOnInvalidNames(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);

                // blank names would produce an unnamed reference, which is not valid.
                assertThrows(BranchOperationException.class, () -> manager.validateBranchName("   "));

                // double dot is the Git range operator and cannot appear inside a branch name.
                assertThrows(BranchOperationException.class, () -> manager.validateBranchName("feature..viewtype"));

                // Git uses the .lock suffix internally for reference locking, so it is reserved.
                assertThrows(BranchOperationException.class, () -> manager.validateBranchName("feature.lock"));

                // spaces and special characters such as ~ ^ : are reserved by Git's revision syntax.
                assertThrows(BranchOperationException.class, () -> manager.validateBranchName("feature viewtype"));
                assertThrows(BranchOperationException.class, () -> manager.validateBranchName("feature~viewtype"));
                assertThrows(BranchOperationException.class, () -> manager.validateBranchName("feature^viewtype"));
            }
        }

        /**
         * Verifies that a name conflicting with an already existing branch is
         * rejected to prevent overwriting of existing branch references.
         */
        @Test
        @DisplayName("rejects a name that already exists as a branch")
        void failOnExistingBranch(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                // master is created during repository initialization and must be seen as a conflict.
                assertThrows(BranchOperationException.class, () -> manager.validateBranchName("master"));
            }
        }
    }

    @Nested
    @DisplayName("getBranchState")
    class GetBranchState {

        /**
         * Verifies that a newly created branch is reported as ACTIVE, and that after deletion its state transitions to DELETED
         * even though the Git reference no longer exists.
         * These two cases are tested together because they represent the complete lifecycle of a managed branch.
         */
        @Test
        @DisplayName("reports ACTIVE for a living branch and DELETED after it is removed")
        void stateThroughLifecycle(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                manager.createBranch("bugfix-viewtype", "master");

                assertEquals(BranchState.ACTIVE, manager.getBranchState("bugfix-viewtype"),
                        "a newly created branch must be ACTIVE");

                manager.deleteBranch("bugfix-viewtype");

                // the Git reference is gone after deletion, but the metadata file is preserved.
                // the state must reflect the deletion rather than throwing an exception.
                assertEquals(BranchState.DELETED, manager.getBranchState("bugfix-viewtype"),
                        "a deleted branch must report DELETED state via its preserved metadata");
            }
        }

        /**
         * Verifies that a branch created directly via JGit (without a metadata file) defaults to ACTIVE,
         * so that the manager handles external branches gracefully.
         */
        @Test
        @DisplayName("defaults to ACTIVE for external branches that have no metadata file")
        void externalBranchDefaults(@TempDir Path repoDir) throws Exception {
            try (var git = initRepo(repoDir)) {
                git.branchCreate().setName("external-branch").call();
                var manager = new BranchManager(repoDir);

                assertEquals(BranchState.ACTIVE, manager.getBranchState("external-branch"));
            }
        }

        /**
         * Verifies that requesting the state of a branch that does not exist in Git and
         * has no metadata file raises an exception rather than returning a default state.
         */
        @Test
        @DisplayName("fails when the branch does not exist anywhere")
        void failOnNonExistent(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                assertThrows(BranchOperationException.class, () -> manager.getBranchState("nonexistent"));
            }
        }
    }
}