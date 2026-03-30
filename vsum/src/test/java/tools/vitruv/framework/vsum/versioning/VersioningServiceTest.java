package tools.vitruv.framework.vsum.versioning;

import org.eclipse.jgit.api.Git;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tools.vitruv.framework.vsum.branch.data.BranchMetadata;
import tools.vitruv.framework.vsum.branch.data.BranchState;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;
import tools.vitruv.framework.vsum.versioning.data.RollbackResult;
import tools.vitruv.framework.vsum.versioning.data.VersionMetadata;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static tools.vitruv.framework.vsum.branch.GitTestHelper.commitFile;
import static tools.vitruv.framework.vsum.branch.GitTestHelper.initRepo;

/**
 * Unit tests for {@link VersioningService}.
 *
 * <p>{@link InternalVirtualModel} is mocked via Mockito so that reload()
 * behavior can be verified without a real VSUM instance.
 */
class VersioningServiceTest {

    private static InternalVirtualModel mockVirtualModel() {
        return mock(InternalVirtualModel.class);
    }

    @Nested
    @DisplayName("createVersion")
    class CreateVersion {

        @Test
        @DisplayName("creates annotated Git tag and persists metadata")
        void createsTagAndMetadata(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var service = new VersioningService(repoDir, mockVirtualModel());

                var metadata = service.createVersion("v1.0", "First stable release");

                // Verify returned metadata
                assertEquals("v1.0", metadata.getVersionId());
                assertEquals("master", metadata.getBranch());
                assertEquals("First stable release", metadata.getDescription());
                assertNotNull(metadata.getCommitSha());
                assertEquals(40, metadata.getCommitSha().length());
                assertEquals("Test User", metadata.getTaggerName());

                // Verify metadata file written to disk
                var metadataFile = repoDir.resolve(".vitruvius/versions/v1.0.metadata");
                assertTrue(Files.exists(metadataFile),
                        "version metadata file must be written to disk");

                var onDisk = VersionMetadata.readFrom(metadataFile);
                assertEquals("v1.0", onDisk.getVersionId());
                assertEquals("First stable release", onDisk.getDescription());

                // Verify Git tag exists
                try (var git = Git.open(repoDir.toFile())) {
                    var tag = git.getRepository().findRef("refs/tags/v1.0");
                    assertNotNull(tag, "annotated Git tag must exist");
                }
            }
        }

        @Test
        @DisplayName("creates version with null description without throwing")
        void createsVersionWithNullDescription(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var service = new VersioningService(repoDir, mockVirtualModel());
                assertDoesNotThrow(() -> service.createVersion("v1.0", null));
                var metadata = service.getVersion("v1.0");
                assertEquals("", metadata.getDescription());
            }
        }

        @Test
        @DisplayName("throws when version ID already exists")
        void throwsOnDuplicateVersionId(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var service = new VersioningService(repoDir, mockVirtualModel());
                service.createVersion("v1.0", "First");
                assertThrows(VersioningException.class,
                        () -> service.createVersion("v1.0", "Duplicate"));
            }
        }

        @Test
        @DisplayName("throws when version ID is blank")
        void throwsOnBlankVersionId(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var service = new VersioningService(repoDir, mockVirtualModel());
                assertThrows(IllegalArgumentException.class,
                        () -> service.createVersion("   ", "desc"));
            }
        }
    }

    @Nested
    @DisplayName("listVersions")
    class ListVersions {

        @Test
        @DisplayName("returns empty list when no versions exist")
        void returnsEmptyListWhenNoVersions(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var service = new VersioningService(repoDir, mockVirtualModel());
                assertTrue(service.listVersions().isEmpty());
            }
        }

        @Test
        @DisplayName("returns all created versions sorted newest first")
        void returnsAllVersionsSortedNewestFirst(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var service = new VersioningService(repoDir, mockVirtualModel());
                service.createVersion("v1.0", "First");
                Thread.sleep(50); // ensure timestamps differ
                service.createVersion("v2.0", "Second");

                var versions = service.listVersions();

                assertEquals(2, versions.size());
                // newest first
                assertEquals("v2.0", versions.get(0).getVersionId());
                assertEquals("v1.0", versions.get(1).getVersionId());
            }
        }
    }

    @Nested
    @DisplayName("getVersion")
    class GetVersion {

        @Test
        @DisplayName("returns correct metadata for existing version")
        void returnsMetadataForExistingVersion(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var service = new VersioningService(repoDir, mockVirtualModel());
                service.createVersion("v1.0", "Release");

                var metadata = service.getVersion("v1.0");

                assertEquals("v1.0", metadata.getVersionId());
                assertEquals("Release", metadata.getDescription());
            }
        }

        @Test
        @DisplayName("throws when version does not exist")
        void throwsWhenVersionNotFound(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var service = new VersioningService(repoDir, mockVirtualModel());
                assertThrows(VersioningException.class,
                        () -> service.getVersion("nonexistent"));
            }
        }
    }

    @Nested
    @DisplayName("previewRollback")
    class PreviewRollback {

        @Test
        @DisplayName("preview shows commits to abandon between version and HEAD")
        void previewShowsCommitsToAbandon(@TempDir Path repoDir) throws Exception {
            try (var git = initRepo(repoDir)) {
                var service = new VersioningService(repoDir, mockVirtualModel());
                // Tag current state as v1.0
                service.createVersion("v1.0", "Baseline");

                // Add two more commits after the tag
                commitFile(git, repoDir, "a.model", "<A/>", "Add A");
                commitFile(git, repoDir, "b.model", "<B/>", "Add B");

                var preview = service.previewRollback("v1.0");

                assertEquals("v1.0", preview.getTargetVersion().getVersionId());
                assertEquals("master", preview.getBranch());
                assertEquals(2, preview.getCommitsToAbandon().size(),
                        "two commits after v1.0 must appear in commitsToAbandon");
                assertFalse(preview.isHasUncommittedChanges());
            }
        }

        @Test
        @DisplayName("preview reports uncommitted changes when working directory is dirty")
        void previewReportsUncommittedChanges(@TempDir Path repoDir) throws Exception {
            try (var git = initRepo(repoDir)) {
                var service = new VersioningService(repoDir, mockVirtualModel());
                // First commit a file so it is tracked
                commitFile(git, repoDir, "dirty.model", "<Original/>", "Add dirty");
                service.createVersion("v1.0", "Baseline");

                // Now modify the tracked file without committing
                Files.writeString(repoDir.resolve("dirty.model"), "<Modified/>");

                var preview = service.previewRollback("v1.0");

                assertTrue(preview.isHasUncommittedChanges(),
                        "preview must report uncommitted changes when tracked file is modified");
            }
        }

        @Test
        @DisplayName("preview shows files that will change")
        void previewShowsFilesToChange(@TempDir Path repoDir) throws Exception {
            try (var git = initRepo(repoDir)) {
                var service = new VersioningService(repoDir, mockVirtualModel());
                service.createVersion("v1.0", "Baseline");

                commitFile(git, repoDir, "system.model", "<System/>", "Add system");

                var preview = service.previewRollback("v1.0");

                assertFalse(preview.getFilesToChange().isEmpty(),
                        "files changed after v1.0 must appear in filesToChange");
                assertTrue(preview.getFilesToChange().stream()
                        .anyMatch(f -> f.contains("system.model")));
            }
        }

        @Test
        @DisplayName("throws when version does not exist")
        void throwsWhenVersionNotFound(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var service = new VersioningService(repoDir, mockVirtualModel());
                assertThrows(VersioningException.class,
                        () -> service.previewRollback("nonexistent"));
            }
        }
    }

    @Nested
    @DisplayName("deleteVersion")
    class DeleteVersion {

        @Test
        @DisplayName("removes metadata file and Git tag")
        void removesMetadataAndTag(@TempDir Path repoDir) throws Exception {
            try (var git = initRepo(repoDir)) {
                var service = new VersioningService(repoDir, mockVirtualModel());
                service.createVersion("v1.0", "to delete");

                service.deleteVersion("v1.0");

                assertFalse(Files.exists(repoDir.resolve(".vitruvius/versions/v1.0.metadata")));
                assertNull(git.getRepository().findRef("refs/tags/v1.0"));
            }
        }

        @Test
        @DisplayName("throws when version does not exist")
        void throwsWhenVersionNotFound(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var service = new VersioningService(repoDir, mockVirtualModel());
                assertThrows(VersioningException.class,
                        () -> service.deleteVersion("nonexistent"));
            }
        }
    }

    @Nested
    @DisplayName("createBranchFromVersion")
    class CreateBranchFromVersion {

        @Test
        @DisplayName("creates Git branch pointing to the version's commit")
        void createsGitBranchAtVersionCommit(@TempDir Path repoDir) throws Exception {
            try (var git = initRepo(repoDir)) {
                var service = new VersioningService(repoDir, mockVirtualModel());
                var version = service.createVersion("v1.0", "baseline");

                service.createBranchFromVersion("feature-x", "v1.0");

                var branchRef = git.getRepository().findRef("refs/heads/feature-x");
                assertNotNull(branchRef, "Git branch feature-x must exist");
                assertEquals(version.getCommitSha(), branchRef.getObjectId().getName(),
                        "branch must point to the version's commit");
            }
        }

        @Test
        @DisplayName("extracts V-SUM files from the version's commit into the new branch's directory")
        void extractsVsumFilesFromVersionCommit(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var service = new VersioningService(repoDir, mockVirtualModel());
                service.createVersion("v1.0", "baseline");

                service.createBranchFromVersion("feature-x", "v1.0");

                var newVsumDir = repoDir.resolve(".vitruvius/vsum/feature-x");
                assertTrue(Files.exists(newVsumDir.resolve("uuid.uuid")));
                assertEquals("fake-uuid-content",
                        Files.readString(newVsumDir.resolve("uuid.uuid")));
                assertTrue(Files.exists(newVsumDir.resolve("correspondences.correspondence")));
            }
        }

        @Test
        @DisplayName("writes branch metadata with ACTIVE state and correct parent")
        void writesBranchMetadata(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var service = new VersioningService(repoDir, mockVirtualModel());
                service.createVersion("v1.0", "baseline");

                BranchMetadata metadata = service.createBranchFromVersion("feature-x", "v1.0");

                assertEquals("feature-x", metadata.getName());
                assertEquals(BranchState.ACTIVE, metadata.getState());
                assertEquals("master", metadata.getParent());
                assertTrue(Files.exists(repoDir.resolve(".vitruvius/branches/feature-x.metadata")));
            }
        }

        @Test
        @DisplayName("branch starts from version commit, not current HEAD")
        void branchStartsFromVersionNotCurrentHead(@TempDir Path repoDir) throws Exception {
            try (var git = initRepo(repoDir)) {
                var service = new VersioningService(repoDir, mockVirtualModel());
                var v1 = service.createVersion("v1.0", "baseline");

                commitFile(git, repoDir, "extra.xmi", "<extra/>", "Second commit");

                service.createBranchFromVersion("feature-from-v1", "v1.0");

                var branchRef = git.getRepository().findRef("refs/heads/feature-from-v1");
                assertNotNull(branchRef);
                assertEquals(v1.getCommitSha(), branchRef.getObjectId().getName(),
                        "branch must point to v1.0 commit, not new HEAD");
            }
        }

        @Test
        @DisplayName("throws when version does not exist")
        void throwsWhenVersionNotFound(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var service = new VersioningService(repoDir, mockVirtualModel());
                assertThrows(VersioningException.class,
                        () -> service.createBranchFromVersion("feature-x", "nonexistent"));
            }
        }

        @Test
        @DisplayName("throws when branch name already exists")
        void throwsWhenBranchAlreadyExists(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var service = new VersioningService(repoDir, mockVirtualModel());
                service.createVersion("v1.0", "baseline");
                service.createBranchFromVersion("feature-x", "v1.0");

                assertThrows(VersioningException.class,
                        () -> service.createBranchFromVersion("feature-x", "v1.0"));
            }
        }
    }

    @Nested
    @DisplayName("confirmRollback")
    class ConfirmRollback {

        @Test
        @DisplayName("resets HEAD to target version commit and reloads VSUM")
        void resetsHeadAndReloadsVsum(@TempDir Path repoDir) throws Exception {
            try (var git = initRepo(repoDir)) {
                var virtualModel = mockVirtualModel();
                var service = new VersioningService(repoDir, virtualModel);

                // Tag v1.0 at initial commit
                service.createVersion("v1.0", "Baseline");
                String v1Sha = git.getRepository().resolve("HEAD").getName();

                // Add commit after tag
                commitFile(git, repoDir, "system.model", "<System/>", "After tag");

                // Preview then confirm rollback
                var preview = service.previewRollback("v1.0");
                var result = service.confirmRollback(preview);

                assertTrue(result.isSuccessful());
                assertEquals(RollbackResult.RollbackStatus.SUCCESS, result.getStatus());

                // HEAD must be back at v1.0 commit
                String newHead = git.getRepository().resolve("HEAD").getName();
                assertEquals(v1Sha, newHead,
                        "HEAD must point to the v1.0 commit after rollback");

                // VSUM reload must have been called
                verify(virtualModel, times(1)).reload();
            }
        }

        @Test
        @DisplayName("returns SUCCESS_RELOAD_FAILED when VSUM reload throws")
        void returnsReloadFailedWhenVsumReloadThrows(@TempDir Path repoDir) throws Exception {
            try (var git = initRepo(repoDir)) {
                var virtualModel = mockVirtualModel();
                doThrow(new RuntimeException("reload failed"))
                        .when(virtualModel).reload();
                var service = new VersioningService(repoDir, virtualModel);

                service.createVersion("v1.0", "Baseline");
                commitFile(git, repoDir, "system.model", "<System/>", "After tag");

                var preview = service.previewRollback("v1.0");
                var result = service.confirmRollback(preview);

                assertEquals(RollbackResult.RollbackStatus.SUCCESS_RELOAD_FAILED,
                        result.getStatus(),
                        "status must be SUCCESS_RELOAD_FAILED when reload throws");
                assertTrue(result.getMessage().contains("reload failed"),
                        "message must include the reload error");
                // Git reset still succeeded
                assertTrue(result.isSuccessful());
            }
        }

        @Test
        @DisplayName("rollback to same commit succeeds without abandoning commits")
        void rollbackToCurrentCommitSucceeds(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var service = new VersioningService(repoDir, mockVirtualModel());
                service.createVersion("v1.0", "Current");

                var preview = service.previewRollback("v1.0");
                assertEquals(0, preview.getCommitsToAbandon().size(),
                        "no commits to abandon when rolling back to current HEAD");

                var result = service.confirmRollback(preview);
                assertTrue(result.isSuccessful());
            }
        }
    }
}