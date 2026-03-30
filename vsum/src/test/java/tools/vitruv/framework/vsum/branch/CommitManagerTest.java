package tools.vitruv.framework.vsum.branch;

import org.eclipse.jgit.api.Git;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tools.vitruv.framework.vsum.branch.data.BranchMetadata;
import tools.vitruv.framework.vsum.branch.data.CommitOptions;
import tools.vitruv.framework.vsum.branch.exception.BranchOperationException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static tools.vitruv.framework.vsum.branch.GitTestHelper.createModelFile;
import static tools.vitruv.framework.vsum.branch.GitTestHelper.initRepo;

/**
 * Unit tests for {@link CommitManager}.
 *
 * <p>Tests operate on a real temporary Git repository to exercise JGit
 * interactions and file staging end to end. The post-commit trigger file
 * is written to the same temp directory and verified on disk.
 */
class CommitManagerTest {

    @Nested
    @DisplayName("commit - basic behavior")
    class BasicCommit {

        @Test
        @DisplayName("commits modified model files and returns correct result")
        void commitsModelFilesAndReturnsResult(@TempDir Path repoDir) throws Exception {
            try (var git = initRepo(repoDir)) {
                createModelFile(repoDir, "system.model", "<System/>");
                var manager = new CommitManager(repoDir);

                var result = manager.commit("Add system model");

                assertNotNull(result.getCommitSha(), "commit SHA must not be null");
                assertEquals(40, result.getCommitSha().length(), "commit SHA must be full 40-char SHA");
                assertEquals("master", result.getBranch());
                assertTrue(result.getStagedFiles().stream()
                                .anyMatch(f -> f.contains("system.model")),
                        "system.model must appear in staged files");
                assertTrue(result.isHasModelChanges(), "result must report model changes");
            }
        }

        @Test
        @DisplayName("commits multiple model file extensions")
        void commitsMultipleModelExtensions(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                createModelFile(repoDir, "metamodel.ecore", "<ecore:EPackage/>");
                createModelFile(repoDir, "instance.xmi", "<root/>");
                createModelFile(repoDir, "generated.genmodel", "<genmodel/>");
                createModelFile(repoDir, "README.txt", "not a model");
                var manager = new CommitManager(repoDir);

                var result = manager.commit("Add model files");

                var staged = result.getStagedFiles();
                assertTrue(staged.stream().anyMatch(f -> f.endsWith(".ecore")));
                assertTrue(staged.stream().anyMatch(f -> f.endsWith(".xmi")));
                assertTrue(staged.stream().anyMatch(f -> f.endsWith(".genmodel")));
                // non-model file must not be staged automatically
                assertFalse(staged.stream().anyMatch(f -> f.endsWith(".txt")),
                        "non-model files must not be auto-staged");
            }
        }

        @Test
        @DisplayName("matches .model and .model2 and .model10 extensions")
        void matchesDynamicModelExtensions(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                createModelFile(repoDir, "a.model", "<root/>");
                createModelFile(repoDir, "b.model2", "<root/>");
                createModelFile(repoDir, "c.model10", "<root/>");
                var manager = new CommitManager(repoDir);

                var result = manager.commit("Add dynamic model files");

                var staged = result.getStagedFiles();
                assertTrue(staged.stream().anyMatch(f -> f.endsWith(".model")));
                assertTrue(staged.stream().anyMatch(f -> f.endsWith(".model2")));
                assertTrue(staged.stream().anyMatch(f -> f.endsWith(".model10")));
            }
        }

        @Test
        @DisplayName("throws when there are no modified model files to commit")
        void throwsWhenNothingToCommit(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var manager = new CommitManager(repoDir);
                assertThrows(BranchOperationException.class,
                        () -> manager.commit("Empty commit"));
            }
        }

        @Test
        @DisplayName("throws when commit message is blank")
        void throwsOnBlankMessage(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                createModelFile(repoDir, "system.model", "<System/>");
                var manager = new CommitManager(repoDir);
                assertThrows(IllegalArgumentException.class,
                        () -> manager.commit("   "));
            }
        }

        @Test
        @DisplayName("sets hasModelChanges to false when only non-model files staged via options")
        void hasModelChangesIsFalseForNonModelFiles(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var extraFile = repoDir.resolve("notes.txt");
                Files.writeString(extraFile, "notes");
                var manager = new CommitManager(repoDir);
                var options = CommitOptions.builder()
                        .addFile(extraFile)
                        .build();

                var result = manager.commit("Add notes", options);

                assertFalse(result.isHasModelChanges(),
                        "no model file extensions means hasModelChanges must be false");
            }
        }
    }

    @Nested
    @DisplayName("commit - CommitOptions")
    class CommitOptionsTests {

        @Test
        @DisplayName("stages additional files specified in options")
        void stagesAdditionalFiles(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                createModelFile(repoDir, "system.model", "<System/>");
                var extra = repoDir.resolve("config.properties");
                Files.writeString(extra, "key=value");
                var manager = new CommitManager(repoDir);
                var options = CommitOptions.builder().addFile(extra).build();

                var result = manager.commit("Add model and config", options);

                var staged = result.getStagedFiles();
                assertTrue(staged.stream().anyMatch(f -> f.contains("system.model")));
                assertTrue(staged.stream().anyMatch(f -> f.contains("config.properties")));
            }
        }

        @Test
        @DisplayName("excludes model files specified in exclude list")
        void excludesSpecifiedModelFiles(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                createModelFile(repoDir, "system.model", "<System/>");
                createModelFile(repoDir, "component.model", "<Component/>");
                var manager = new CommitManager(repoDir);
                var excluded = repoDir.resolve("component.model");
                var options = CommitOptions.builder().excludeFile(excluded).build();

                var result = manager.commit("Add system only", options);

                var staged = result.getStagedFiles();
                assertTrue(staged.stream().anyMatch(f -> f.contains("system.model")));
                assertFalse(staged.stream().anyMatch(f -> f.contains("component.model")),
                        "excluded file must not be staged");
            }
        }
    }

    @Nested
    @DisplayName("commit - branch metadata update")
    class BranchMetadataUpdate {

        @Test
        @DisplayName("updates branch metadata lastModified and stages it with the commit")
        void updatesBranchMetadataLastModified(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                // Write branch metadata with an old lastModified timestamp
                var branchManager = new BranchManager(repoDir);
                branchManager.createBranch("feature", "master");
                // Switch to feature so CommitManager picks up feature.metadata
                try (var git = Git.open(repoDir.toFile())) {
                    git.checkout().setName("feature").call();
                }

                var beforeCommit = LocalDateTime.now();
                Thread.sleep(50); // ensure timestamp advances

                createModelFile(repoDir, "system.model", "<System/>");
                var manager = new CommitManager(repoDir);
                manager.commit("Add model");

                Thread.sleep(50);
                var afterCommit = LocalDateTime.now();

                // Read metadata back and verify lastModified was updated
                var metadataFile = repoDir.resolve(".vitruvius/branches/feature.metadata");
                var metadata = BranchMetadata.readFrom(metadataFile);
                assertTrue(metadata.getLastModified().isAfter(beforeCommit),
                        "lastModified must be after commit start");
                assertTrue(metadata.getLastModified().isBefore(afterCommit),
                        "lastModified must be before commit end");
            }
        }

        @Test
        @DisplayName("succeeds even when no branch metadata file exists")
        void succeedsWithoutMetadataFile(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                // master has no metadata file - external branch scenario
                createModelFile(repoDir, "system.model", "<System/>");
                var manager = new CommitManager(repoDir);

                // must not throw even though no metadata file exists for master
                assertDoesNotThrow(() -> manager.commit("Add model"));
            }
        }
    }

    @Nested
    @DisplayName("commit - post-commit trigger")
    class PostCommitTrigger {

        @Test
        @DisplayName("writes post-commit trigger file when model files are committed")
        void writesPostCommitTrigger(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                createModelFile(repoDir, "system.model", "<System/>");
                var manager = new CommitManager(repoDir);

                manager.commit("Add model");

                var triggerFile = repoDir.resolve(".vitruvius/post-commit-trigger");
                assertTrue(Files.exists(triggerFile),
                        "post-commit trigger must be written for model file commits");
                var content = Files.readString(triggerFile);
                assertTrue(content.contains("master"),
                        "trigger must contain the branch name");
                assertEquals(4, content.split("\\|").length,
                        "trigger must have 4 pipe-delimited fields");
            }
        }

        @Test
        @DisplayName("does not write post-commit trigger when no model files changed")
        void doesNotWriteTriggerForNonModelFiles(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var extra = repoDir.resolve("notes.txt");
                Files.writeString(extra, "notes");
                var manager = new CommitManager(repoDir);
                var options = CommitOptions.builder().addFile(extra).build();

                manager.commit("Add notes", options);

                var triggerFile = repoDir.resolve(".vitruvius/post-commit-trigger");
                assertFalse(Files.exists(triggerFile),
                        "post-commit trigger must not be written when no model files changed");
            }
        }
    }
}