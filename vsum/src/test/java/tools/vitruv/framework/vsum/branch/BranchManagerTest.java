package tools.vitruv.framework.vsum.branch;

import java.nio.file.Files;
import java.nio.file.Path;
import org.eclipse.jgit.api.Git;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link BranchManager}.
 */
class BranchManagerTest {
    /**
     * Initializes a Git repository at the given path with a single initial commit on {@code main}.
     * Returns the opened {@link Git} instance (caller is responsible for closing it).
     *
     * @param repoDir The directory to initialize the repository in.
     * @return An opened {@link Git} instance for the initialized repository.
     * @throws Exception If initialization or the initial commit fails.
     */
    private static Git initRepo(Path repoDir) throws Exception {
        var git = Git.init().setDirectory(repoDir.toFile()).setInitialBranch("main").call();
        // Git needs at least one commit before branches can be created
        var file = repoDir.resolve("init.txt");
        Files.writeString(file, "initial commit");
        git.add().addFilepattern("init.txt").call();
        git.commit().setMessage("Initial commit").setAllowEmpty(false).call();
        return git;
    }

    @Nested
    @DisplayName("createBranch")
    class CreateBranch {
        @Test
        @DisplayName("create a branch from main and verify metadata")
        void createBranchFromMain(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                var metadata = manager.createBranch("branching-test", "main");
                assertEquals("branching-test", metadata.getName());
                assertEquals("main", metadata.getParent());
                assertEquals(BranchState.ACTIVE, metadata.getState());
                assertEquals(7, metadata.getUid().length());
            }
        }

        @Test
        @DisplayName("metadata file is written to disk")
        void metadataFileExists(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                manager.createBranch("branching-test", "main");
                var metadataFile = repoDir.resolve(".vitruvius/branches/branching-test.metadata");
                assertTrue(Files.exists(metadataFile));

                var lines = Files.readAllLines(metadataFile);
                assertTrue(lines.stream().anyMatch(l -> l.startsWith("name=branching-test")));
                assertTrue(lines.stream().anyMatch(l -> l.startsWith("state=ACTIVE")));
                assertTrue(lines.stream().anyMatch(l -> l.startsWith("parent=main")));

            }
        }

        @Test
        @DisplayName("fail when source branch does not exist")
        void failOnNonExistentSource(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                assertThrows(BranchOperationException.class, () -> manager.createBranch("feature-x", "nonexistent"));
            }
        }

        @Test
        @DisplayName("fail when branch with same name already exists")
        void failOnDuplicateName(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                manager.createBranch("branching-test", "main");
                assertThrows(BranchOperationException.class, () -> manager.createBranch("branching-test", "main"));
            }
        }
    }

    @Nested
    @DisplayName("switchBranch")
    class SwitchBranch {

        @Test
        @DisplayName("switch to an existing branch by name")
        void switchByName(@TempDir Path repoDir) throws Exception {
            try (var git = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                manager.createBranch("branching-test", "main");

                manager.switchBranch("branching-test");
                var head = git.getRepository().findRef("HEAD");
                assertEquals("refs/heads/branching-test", head.getTarget().getName());
            }
        }

        @Test
        @DisplayName("switch to a branch by uid prefix")
        void switchByUid(@TempDir Path repoDir) throws Exception {
            try (var git = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                var metadata = manager.createBranch("branching-test", "main");
                manager.switchBranch(metadata.getUid());
                var head = git.getRepository().findRef("HEAD");
                assertEquals("refs/heads/branching-test", head.getTarget().getName());
            }
        }

        @Test
        @DisplayName("fail when branch does not exist")
        void failOnNonExistentBranch(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                assertThrows(BranchOperationException.class, () -> manager.switchBranch("nonexistent"));
            }
        }
    }

    @Nested
    @DisplayName("deleteBranch")
    class DeleteBranch {

        @Test
        @DisplayName("delete an existing branch and verify it is gone from Git")
        void deleteBranch(@TempDir Path repoDir) throws Exception {
            try (var git = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                manager.createBranch("branching-test", "main");
                manager.deleteBranch("branching-test");
                var ref = git.getRepository().findRef("refs/heads/branching-test");
                assertNull(ref);
            }
        }

        @Test
        @DisplayName("metadata is marked DELETED after deletion")
        void metadataMarkedDeleted(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                manager.createBranch("branching-test", "main");
                manager.deleteBranch("branching-test");
                var metadataFile = repoDir.resolve(".vitruvius/branches/branching-test.metadata");
                assertTrue(Files.exists(metadataFile));
                var lines = Files.readAllLines(metadataFile);
                assertTrue(lines.stream().anyMatch(l -> l.equals("state=DELETED")));
            }
        }

        @Test
        @DisplayName("fail when trying to delete the currently checked-out branch")
        void failOnDeleteCurrentBranch(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                // We're on main — trying to delete it should fail
                assertThrows(BranchOperationException.class, () -> manager.deleteBranch("main"));
            }
        }
    }

    @Nested
    @DisplayName("listBranches")
    class ListBranches {

        @Test
        @DisplayName("list branches returns all existing branches")
        void listAllBranches(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                manager.createBranch("branching-test", "main");
                manager.createBranch("feature-signup", "main");
                var branches = manager.listBranches();
                var names = branches.stream().map(BranchMetadata::getName).toList();
                assertEquals(3, branches.size()); // main + two feature branches
                assertTrue(names.contains("main"));
                assertTrue(names.contains("branching-test"));
                assertTrue(names.contains("feature-signup"));
            }
        }

        @Test
        @DisplayName("branch created outside BranchManager is listed with synthesized metadata")
        void listIncludesExternalBranch(@TempDir Path repoDir) throws Exception {
            try (var git = initRepo(repoDir)) {
                // Create a branch directly via JGit, bypassing BranchManager
                git.branchCreate().setName("external-branch").call();
                var manager = new BranchManager(repoDir);
                var branches = manager.listBranches();
                var external = branches.stream().filter(m -> m.getName().equals("external-branch")).findFirst().orElseThrow();

                assertEquals(BranchState.ACTIVE, external.getState());
                assertEquals("unknown", external.getParent());
                assertEquals(7, external.getUid().length());
            }
        }

        @Test
        @DisplayName("deleted branches are not listed")
        void deletedBranchNotListed(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                manager.createBranch("branching-test", "main");
                manager.deleteBranch("branching-test");
                var branches = manager.listBranches();
                var names = branches.stream().map(BranchMetadata::getName).toList();
                assertFalse(names.contains("branching-test"));
            }
        }
    }

    @Nested
    @DisplayName("findBranches")
    class FindBranches {

        @Test
        @DisplayName("find branches matching a glob pattern")
        void findByPattern(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                manager.createBranch("feature-branching", "main");
                manager.createBranch("feature-merge", "main");
                manager.createBranch("buggy-vcs", "main");
                var matches = manager.findBranches("feature-*");
                var names = matches.stream().map(BranchMetadata::getName).toList();
                assertEquals(2, matches.size());
                assertTrue(names.contains("feature-branching"));
                assertTrue(names.contains("feature-merge"));
            }
        }

        @Test
        @DisplayName("find branches returns empty list when nothing matches")
        void findNoMatch(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                manager.createBranch("branching-test", "main");
                var matches = manager.findBranches("bugfix-*");
                assertTrue(matches.isEmpty());
            }
        }

        @Test
        @DisplayName("find branches with single-character wildcard")
        void findWithQuestionMark(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                manager.createBranch("v1", "main");
                manager.createBranch("v2", "main");
                manager.createBranch("v10", "main");
                var matches = manager.findBranches("v?");
                var names = matches.stream().map(BranchMetadata::getName).toList();
                // v? matches exactly one character after v — so v1 and v2, not v10
                assertEquals(2, matches.size());
                assertTrue(names.contains("v1"));
                assertTrue(names.contains("v2"));
            }
        }
    }

    @Nested
    @DisplayName("resolveBranchIdentifier")
    class ResolveBranchIdentifier {

        @Test
        @DisplayName("resolve an exact branch name")
        void resolveByName(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                manager.createBranch("branching-test", "main");
                var resolved = manager.resolveBranchIdentifier("branching-test");
                assertEquals("branching-test", resolved);
            }
        }

        @Test
        @DisplayName("resolve a branch by uid prefix")
        void resolveByUid(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                var metadata = manager.createBranch("branching-test", "main");
                var resolved = manager.resolveBranchIdentifier(metadata.getUid());
                assertEquals("branching-test", resolved);
            }
        }

        @Test
        @DisplayName("name takes precedence over uid match")
        void nameOverridesUid(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                // Create a branch whose name looks like an uid
                var metadata = manager.createBranch("branching-test", "main");
                // Now create a branch literally named after that uid
                manager.createBranch(metadata.getUid(), "main");
                // Resolving the uid string should return it as a name match, not search for uid prefix
                var resolved = manager.resolveBranchIdentifier(metadata.getUid());
                assertEquals(metadata.getUid(), resolved);
            }
        }

        @Test
        @DisplayName("fail when identifier matches nothing")
        void failOnNoMatch(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                assertThrows(BranchOperationException.class, () -> manager.resolveBranchIdentifier("nonexistent"));
            }
        }
    }

    @Nested
    @DisplayName("getBranchTopology")
    class GetBranchTopology {

        @Test
        @DisplayName("topology reflects parent-child relationships")
        void topologyFromMetadata(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                manager.createBranch("branching-test", "main");
                manager.createBranch("feature-auth", "branching-test");
                var topology = manager.getBranchTopology();
                // main -> [branching-test]
                assertTrue(topology.containsKey("main"));
                assertEquals(1, topology.get("main").size());
                assertTrue(topology.get("main").contains("branching-test"));
                // branching-test -> [feature-auth]
                assertTrue(topology.containsKey("branching-test"));
                assertEquals(1, topology.get("branching-test").size());
                assertTrue(topology.get("branching-test").contains("feature-auth"));
            }
        }

        @Test
        @DisplayName("multiple children under same parent")
        void multipleChildrenUnderParent(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                manager.createBranch("branching-test", "main");
                manager.createBranch("feature-signup", "main");
                manager.createBranch("bugfix-crash", "main");
                var topology = manager.getBranchTopology();
                assertEquals(3, topology.get("main").size());
                assertTrue(topology.get("main").contains("branching-test"));
                assertTrue(topology.get("main").contains("feature-signup"));
                assertTrue(topology.get("main").contains("bugfix-crash"));
            }
        }

        @Test
        @DisplayName("deleted branches are excluded from topology")
        void deletedBranchExcluded(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                manager.createBranch("branching-test", "main");
                manager.createBranch("feature-signup", "main");
                manager.deleteBranch("branching-test");
                var topology = manager.getBranchTopology();
                // Only feature-signup should remain under main
                assertEquals(1, topology.get("main").size());
                assertTrue(topology.get("main").contains("feature-signup"));
            }
        }

        @Test
        @DisplayName("empty topology when no managed branches exist")
        void emptyTopology(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                // No branches created through BranchManager — no metadata dir exists
                var topology = manager.getBranchTopology();
                assertTrue(topology.isEmpty());
            }
        }
    }

    @Nested
    @DisplayName("validateBranchName")
    class ValidateBranchName {

        @Test
        @DisplayName("valid name passes without exception")
        void validName(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                // Should not throw
                manager.validateBranchName("branching-test");
            }
        }

        @Test
        @DisplayName("fail on blank name")
        void failOnBlank(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                assertThrows(BranchOperationException.class, () -> manager.validateBranchName("   "));
            }
        }

        @Test
        @DisplayName("fail on name containing '..'")
        void failOnDoubleDot(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                assertThrows(BranchOperationException.class, () -> manager.validateBranchName("feature..login"));
            }
        }

        @Test
        @DisplayName("fail on name ending with '.lock'")
        void failOnLockSuffix(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                assertThrows(BranchOperationException.class, () -> manager.validateBranchName("feature.lock"));
            }
        }

        @Test
        @DisplayName("fail on name containing illegal characters")
        void failOnIllegalCharacters(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                assertThrows(BranchOperationException.class, () -> manager.validateBranchName("feature login"));
                assertThrows(BranchOperationException.class, () -> manager.validateBranchName("feature~login"));
                assertThrows(BranchOperationException.class, () -> manager.validateBranchName("feature^login"));
            }
        }

        @Test
        @DisplayName("fail on name that already exists")
        void failOnExistingBranch(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                // "main" already exists from initRepo
                assertThrows(BranchOperationException.class, () -> manager.validateBranchName("main"));
            }
        }
    }

    @Nested
    @DisplayName("getBranchState")
    class GetBranchState {

        @Test
        @DisplayName("active branch returns ACTIVE")
        void activeBranch(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                manager.createBranch("branching-test", "main");
                assertEquals(BranchState.ACTIVE, manager.getBranchState("branching-test"));
            }
        }

        @Test
        @DisplayName("deleted branch returns DELETED")
        void deletedBranch(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                manager.createBranch("branching-test", "main");
                manager.deleteBranch("branching-test");
                assertEquals(BranchState.DELETED, manager.getBranchState("branching-test"));
            }
        }

        @Test
        @DisplayName("branch created outside BranchManager defaults to ACTIVE")
        void externalBranchDefaults(@TempDir Path repoDir) throws Exception {
            try (var git = initRepo(repoDir)) {
                git.branchCreate().setName("external-branch").call();
                var manager = new BranchManager(repoDir);
                assertEquals(BranchState.ACTIVE, manager.getBranchState("external-branch"));
            }
        }

        @Test
        @DisplayName("fail when branch does not exist at all")
        void failOnNonExistent(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new BranchManager(repoDir);
                assertThrows(BranchOperationException.class, () -> manager.getBranchState("nonexistent"));
            }
        }
    }
}