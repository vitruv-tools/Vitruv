package tools.vitruv.framework.vsum.branch;

import com.google.gson.Gson;
import org.eclipse.jgit.api.Git;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tools.vitruv.framework.vsum.branch.data.ConflictSeverity;
import tools.vitruv.framework.vsum.branch.data.ReplayResult;
import tools.vitruv.framework.vsum.branch.data.SemanticConflict;
import tools.vitruv.framework.vsum.branch.exception.BranchOperationException;
import tools.vitruv.framework.vsum.branch.merge.SemanticMergeEngine;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeType;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangelogManager;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static tools.vitruv.framework.vsum.branch.GitTestHelper.commitFile;
import static tools.vitruv.framework.vsum.branch.GitTestHelper.initRepo;

/**
 * Unit tests for {@link SemanticConflictDetector}.
 *
 * <p>Changelogs are written as real JSON files committed to the test repo so that
 * the TreeWalk-based reading path is exercised end-to-end.
 */
class SemanticConflictDetectorTest {

    private static final Gson GSON = new Gson();

    /**
     * Builds a minimal changelog JSON string for a given commit on a branch.
     */
    private static String changelogJson(String sha, String branch,
                                        List<SemanticChangelogManager.ChangelogDocument.FileChangeInfo> fileChanges) {
        SemanticChangelogManager.ChangelogDocument doc = new SemanticChangelogManager.ChangelogDocument();
        doc.formatVersion = "1.0";
        SemanticChangelogManager.ChangelogDocument.CommitInfo ci = new SemanticChangelogManager.ChangelogDocument.CommitInfo();
        ci.sha = sha;
        ci.shortSha = sha.substring(0, Math.min(7, sha.length()));
        ci.branch = branch;
        ci.message = "test commit";
        ci.parentShas = List.of();
        doc.commit = ci;

        doc.fileChanges = fileChanges;

        SemanticChangelogManager.ChangelogDocument.Summary s = new SemanticChangelogManager.ChangelogDocument.Summary();
        s.totalFileChanges = fileChanges.size();
        s.affectedElementUuids = List.of();
        doc.summary = s;

        return GSON.toJson(doc);
    }

    /**
     * Builds a FileChangeInfo with a single semantic change entry (JSON-compatible).
     */
    private static SemanticChangelogManager.ChangelogDocument.FileChangeInfo fileChange(
            String path, String elementUuid, String feature, SemanticChangeType changeType, String from, String to) {
        SemanticChangelogManager.ChangelogDocument.FileChangeInfo info =
                new SemanticChangelogManager.ChangelogDocument.FileChangeInfo();
        info.path = path;
        info.operation = "MODIFIED";

        // Build the SemanticChangeEntry as a plain map/object for Gson round-trip
        ChangeEntryJson entry = new ChangeEntryJson();
        entry.index = 0;
        entry.changeType = changeType.name();
        entry.changeDescription = changeType.getDescription();
        entry.emfType = "Test";
        entry.elementUuid = elementUuid;
        entry.feature = feature;
        entry.from = from;
        entry.to = to;
        entry.position = -1;

        // Serialize to JSON and parse back as the correct type
        String entryJson = GSON.toJson(entry);
        info.semanticChanges = GSON.fromJson("[" + entryJson + "]",
                new com.google.gson.reflect.TypeToken<List<tools.vitruv.framework.vsum.branch.storage.SemanticChangeEntry>>() {
        }.getType());
        return info;
    }

    /**
     * Plain POJO for building SemanticChangeEntry-compatible JSON without depending on its builder.
     */
    private static class ChangeEntryJson {
        int index;
        String changeType;
        String changeDescription;
        String emfType;
        String elementUuid;
        String eClass;
        String feature;
        String from;
        String to;
        String referencedElementUuid;
        String containerUuid;
        java.util.List<String> cascadeDeletedUuids;
        int position;
        String changeOrigin;
    }

    /**
     * Same as {@link #fileChange} but also sets the {@code changeOrigin} field
     * (e.g. {@code "original"} or {@code "consequential"}).
     */
    private static SemanticChangelogManager.ChangelogDocument.FileChangeInfo fileChange(
            String path, String elementUuid, String feature, SemanticChangeType changeType,
            String from, String to, String changeOrigin) {
        SemanticChangelogManager.ChangelogDocument.FileChangeInfo info = fileChange(
                path, elementUuid, feature, changeType, from, to);
        // Patch the changeOrigin into the already-deserialized SemanticChangeEntry list
        // by re-serialising through ChangeEntryJson so the field is present in JSON.
        ChangeEntryJson entry = new ChangeEntryJson();
        entry.index = 0;
        entry.changeType = changeType.name();
        entry.changeDescription = changeType.getDescription();
        entry.emfType = "Test";
        entry.elementUuid = elementUuid;
        entry.feature = feature;
        entry.from = from;
        entry.to = to;
        entry.position = -1;
        entry.changeOrigin = changeOrigin;
        String entryJson = GSON.toJson(entry);
        info.semanticChanges = GSON.fromJson("[" + entryJson + "]",
                new com.google.gson.reflect.TypeToken<List<tools.vitruv.framework.vsum.branch.storage.SemanticChangeEntry>>() {
                }.getType());
        return info;
    }

    /**
     * Same as {@link #fileChange(String, String, String, SemanticChangeType, String, String)}
     * but also sets the {@code containerUuid} field (UUID of the parent element at change time).
     * Used to test tombstone conflict detection.
     */
    private static SemanticChangelogManager.ChangelogDocument.FileChangeInfo fileChangeWithContainer(
            String path, String elementUuid, String feature, SemanticChangeType changeType,
            String from, String to, String containerUuid) {
        ChangeEntryJson entry = new ChangeEntryJson();
        entry.index = 0;
        entry.changeType = changeType.name();
        entry.changeDescription = changeType.getDescription();
        entry.emfType = "Test";
        entry.elementUuid = elementUuid;
        entry.feature = feature;
        entry.from = from;
        entry.to = to;
        entry.position = -1;
        entry.containerUuid = containerUuid;

        SemanticChangelogManager.ChangelogDocument.FileChangeInfo info =
                new SemanticChangelogManager.ChangelogDocument.FileChangeInfo();
        info.path = path;
        info.operation = "MODIFIED";
        String entryJson = GSON.toJson(entry);
        info.semanticChanges = GSON.fromJson("[" + entryJson + "]",
                new com.google.gson.reflect.TypeToken<List<tools.vitruv.framework.vsum.branch.storage.SemanticChangeEntry>>() {
                }.getType());
        return info;
    }

    /**
     * Builds a FileChangeInfo for an ELEMENT_DELETED entry that carries a list of cascade-deleted
     * descendant UUIDs. Used to test cascade DELETE_MODIFY and MODIFY_DELETE detection.
     */
    private static SemanticChangelogManager.ChangelogDocument.FileChangeInfo fileChangeWithCascade(
            String path, String elementUuid, List<String> cascadeDeletedUuids) {
        ChangeEntryJson entry = new ChangeEntryJson();
        entry.index = 0;
        entry.changeType = SemanticChangeType.ELEMENT_DELETED.name();
        entry.changeDescription = SemanticChangeType.ELEMENT_DELETED.getDescription();
        entry.emfType = "DeleteEObject";
        entry.elementUuid = elementUuid;
        entry.position = -1;
        entry.cascadeDeletedUuids = cascadeDeletedUuids;

        SemanticChangelogManager.ChangelogDocument.FileChangeInfo info =
                new SemanticChangelogManager.ChangelogDocument.FileChangeInfo();
        info.path = path;
        info.operation = "MODIFIED";
        String entryJson = GSON.toJson(entry);
        info.semanticChanges = GSON.fromJson("[" + entryJson + "]",
                new com.google.gson.reflect.TypeToken<List<tools.vitruv.framework.vsum.branch.storage.SemanticChangeEntry>>() {
                }.getType());
        return info;
    }

    /**
     * Commits a changelog JSON file at the correct path for the given branch and short SHA.
     * Returns the short SHA of the changelog commit.
     */
    private static void commitChangelog(Git git, Path repoDir, String branch, String targetShortSha, String jsonContent)
            throws Exception {
        Path logFile = repoDir.resolve(".vitruvius/changelogs/" + branch + "/json/" + targetShortSha + ".json");
        Files.createDirectories(logFile.getParent());
        Files.writeString(logFile, jsonContent);
        git.add().addFilepattern(".vitruvius/changelogs").call();
        var commit = git.commit().setMessage("changelog for " + targetShortSha).call();
    }


    @Nested
    @DisplayName("analyzeBranches - no conflicts")
    class NoConflicts {

        @Test
        @DisplayName("returns empty conflicts when branches changed different elements")
        void noConflictWhenDifferentElements(@TempDir Path repoDir) throws Exception {
            try (Git git = initRepo(repoDir)) {
                String ancestorSha = git.getRepository().resolve("HEAD").getName().substring(0, 7);

                // Create feature-x branch from master
                git.branchCreate().setName("feature-x").call();

                // Add a commit + changelog on master changing element-A
                String masterSha = commitFile(git, repoDir, "a.xmi", "<A/>", "change A on master");
                String masterShort = masterSha.substring(0, 7);
                commitChangelog(git, repoDir, "master", masterShort,
                        changelogJson(masterSha, "master", List.of(fileChange("a.xmi", "uuid-A",
                                "name", SemanticChangeType.ATTRIBUTE_CHANGED, "Old", "New"))));

                // Switch to feature-x, add a commit changing element-B
                git.checkout().setName("feature-x").call();
                String featureSha = commitFile(git, repoDir, "b.xmi", "<B/>",
                        "change B on feature-x");
                String featureShort = featureSha.substring(0, 7);
                commitChangelog(git, repoDir, "feature-x", featureShort, changelogJson(featureSha,
                        "feature-x", List.of(fileChange("b.xmi", "uuid-B", "name",
                                SemanticChangeType.ATTRIBUTE_CHANGED, "Old", "Xyz"))));

                // Switch back to master for analysis
                git.checkout().setName("master").call();

                var detector = new SemanticConflictDetector(repoDir);
                ReplayResult result = detector.analyzeBranches("master", "feature-x");

                assertFalse(result.hasConflicts(), "different elements should not conflict");
                // model-change commit + changelog commit = 2 diverging commits per branch
                assertEquals(2, result.getCommitShasOnA().size());
                assertEquals(2, result.getCommitShasOnB().size());
                assertNotNull(result.getAncestorSha());
            }
        }

        @Test
        @DisplayName("returns empty conflicts when both branches made the same change (auto-resolvable)")
        void noConflictForIdenticalChanges(@TempDir Path repoDir) throws Exception {
            try (Git git = initRepo(repoDir)) {
                git.branchCreate().setName("feature-x").call();

                // Both branches set the same attribute to the same value
                String masterSha = commitFile(git, repoDir, "a.xmi", "<A/>", "master commit");
                String masterShort = masterSha.substring(0, 7);
                commitChangelog(git, repoDir, "master", masterShort, changelogJson(masterSha, "master",
                        List.of(fileChange("a.xmi", "uuid-A", "name",
                                SemanticChangeType.ATTRIBUTE_CHANGED, "Old", "SameValue"))));

                git.checkout().setName("feature-x").call();
                String featureSha = commitFile(git, repoDir, "a.xmi", "<A/>", "feature commit");
                String featureShort = featureSha.substring(0, 7);
                commitChangelog(git, repoDir, "feature-x", featureShort, changelogJson(featureSha,
                        "feature-x", List.of(fileChange("a.xmi", "uuid-A", "name",
                                SemanticChangeType.ATTRIBUTE_CHANGED, "Old", "SameValue"))));

                git.checkout().setName("master").call();

                ReplayResult result = new SemanticConflictDetector(repoDir).analyzeBranches("master", "feature-x");

                assertFalse(result.hasConflicts(), "identical changes are not a conflict");
            }
        }

        @Test
        @DisplayName("returns empty conflicts when no changelogs exist yet")
        void noConflictWhenNoChangelogs(@TempDir Path repoDir) throws Exception {
            try (Git git = initRepo(repoDir)) {
                git.branchCreate().setName("feature-x").call();
                commitFile(git, repoDir, "a.xmi", "<A/>", "commit on master");
                git.checkout().setName("feature-x").call();
                commitFile(git, repoDir, "b.xmi", "<B/>", "commit on feature-x");
                git.checkout().setName("master").call();

                ReplayResult result = new SemanticConflictDetector(repoDir).analyzeBranches("master", "feature-x");

                assertFalse(result.hasConflicts());
                assertTrue(result.getChangesOnA().isEmpty());
                assertTrue(result.getChangesOnB().isEmpty());
            }
        }
    }


    @Nested
    @DisplayName("analyzeBranches - conflict detection")
    class ConflictDetection {

        @Test
        @DisplayName("detects MEDIUM conflict when both branches changed the same attribute to different values")
        void detectsMediumConflictForDivergingAttribute(@TempDir Path repoDir) throws Exception {
            try (Git git = initRepo(repoDir)) {
                git.branchCreate().setName("feature-x").call();

                // master: changed uuid-A.name from "Old" to "Bar"
                String masterSha = commitFile(git, repoDir, "a.xmi", "<A/>",
                        "master changes A.name");
                String masterShort = masterSha.substring(0, 7);
                commitChangelog(git, repoDir, "master", masterShort, changelogJson(masterSha,
                        "master", List.of(fileChange("a.xmi", "uuid-A", "name",
                                SemanticChangeType.ATTRIBUTE_CHANGED, "Old", "Bar"))));

                // feature-x: changed uuid-A.name from "Old" to "Baz"  <- CONFLICT
                git.checkout().setName("feature-x").call();
                String featureSha = commitFile(git, repoDir, "a.xmi", "<A/>",
                        "feature changes A.name");
                String featureShort = featureSha.substring(0, 7);
                commitChangelog(git, repoDir, "feature-x", featureShort, changelogJson(featureSha,
                        "feature-x", List.of(fileChange("a.xmi", "uuid-A", "name",
                                SemanticChangeType.ATTRIBUTE_CHANGED, "Old", "Baz"))));

                git.checkout().setName("master").call();

                ReplayResult result = new SemanticConflictDetector(repoDir).analyzeBranches("master", "feature-x");

                assertTrue(result.hasConflicts());
                assertEquals(1, result.getConflicts().size());

                SemanticConflict conflict = result.getConflicts().get(0);
                assertEquals("uuid-A", conflict.getElementUuid());
                assertEquals("name", conflict.getFeature());
                assertEquals(ConflictSeverity.MEDIUM, conflict.getSeverity());
                assertEquals(SemanticChangeType.ATTRIBUTE_CHANGED, conflict.getChangeOnBranchA().getChangeType());
                assertEquals(SemanticChangeType.ATTRIBUTE_CHANGED, conflict.getChangeOnBranchB().getChangeType());
            }
        }

        @Test
        @DisplayName("detects HIGH conflict when one branch deletes an element modified by the other")
        void detectsHighConflictForDeleteVsModify(@TempDir Path repoDir) throws Exception {
            try (Git git = initRepo(repoDir)) {
                git.branchCreate().setName("feature-x").call();

                // master: deleted uuid-A
                String masterSha = commitFile(git, repoDir, "a.xmi", "<A/>", "master deletes A");
                String masterShort = masterSha.substring(0, 7);
                commitChangelog(git, repoDir, "master", masterShort, changelogJson(masterSha, "master",
                        List.of(fileChange("a.xmi", "uuid-A", null, SemanticChangeType.ELEMENT_DELETED,
                                null, null))));

                // feature-x: modified uuid-A.name  <- HIGH CONFLICT (A was deleted on master)
                git.checkout().setName("feature-x").call();
                String featureSha = commitFile(git, repoDir, "a.xmi", "<A/>", "feature modifies A");
                String featureShort = featureSha.substring(0, 7);
                commitChangelog(git, repoDir, "feature-x", featureShort, changelogJson(featureSha,
                        "feature-x", List.of(fileChange("a.xmi", "uuid-A", "name",
                                SemanticChangeType.ATTRIBUTE_CHANGED, "Old", "New"))));

                git.checkout().setName("master").call();

                ReplayResult result = new SemanticConflictDetector(repoDir).analyzeBranches("master", "feature-x");

                assertTrue(result.hasConflicts());
                assertEquals(ConflictSeverity.HIGH, result.getConflicts().get(0).getSeverity());
                assertEquals(1, result.highSeverityCount());
            }
        }

        @Test
        @DisplayName("does not conflict when both branches deleted the same element")
        void noConflictWhenBothDeleteSameElement(@TempDir Path repoDir) throws Exception {
            try (Git git = initRepo(repoDir)) {
                git.branchCreate().setName("feature-x").call();

                // Both branches deleted uuid-A -> same outcome, auto-resolvable
                String masterSha = commitFile(git, repoDir, "a.xmi", "", "master deletes A");
                commitChangelog(git, repoDir, "master", masterSha.substring(0, 7), changelogJson(masterSha,
                        "master", List.of(fileChange("a.xmi", "uuid-A", null,
                                SemanticChangeType.ELEMENT_DELETED, null, null))));

                git.checkout().setName("feature-x").call();
                String featureSha = commitFile(git, repoDir, "a.xmi", "", "feature-x deletes A");
                commitChangelog(git, repoDir, "feature-x", featureSha.substring(0, 7), changelogJson(featureSha,
                        "feature-x", List.of(fileChange("a.xmi", "uuid-A", null,
                                SemanticChangeType.ELEMENT_DELETED, null, null))));

                git.checkout().setName("master").call();

                ReplayResult result = new SemanticConflictDetector(repoDir).analyzeBranches("master", "feature-x");

                assertFalse(result.hasConflicts(), "both branches deleting the same element is not a conflict");
            }
        }

        @Test
        @DisplayName("reports correct counts via highSeverityCount and mediumSeverityCount")
        void countsConflictsBySeverity(@TempDir Path repoDir) throws Exception {
            try (Git git = initRepo(repoDir)) {
                git.branchCreate().setName("feature-x").call();

                // master: delete uuid-A (HIGH), change uuid-B.name to "Bar" (MEDIUM)
                String masterSha = commitFile(git, repoDir, "model.xmi", "<M/>", "master changes");
                String masterShort = masterSha.substring(0, 7);
                commitChangelog(git, repoDir, "master", masterShort, changelogJson(masterSha, "master",
                        List.of(fileChange("model.xmi", "uuid-A", null,
                                SemanticChangeType.ELEMENT_DELETED, null, null),
                                fileChange("model.xmi", "uuid-B", "name",
                                SemanticChangeType.ATTRIBUTE_CHANGED, "X", "Bar"))));

                // feature-x: modify uuid-A.desc (HIGH: deleted on master), change uuid-B.name to "Baz" (MEDIUM)
                git.checkout().setName("feature-x").call();
                String featureSha = commitFile(git, repoDir, "model.xmi", "<M/>", "feature changes");
                String featureShort = featureSha.substring(0, 7);
                commitChangelog(git, repoDir, "feature-x", featureShort, changelogJson(featureSha,
                        "feature-x", List.of(fileChange("model.xmi", "uuid-A", "desc",
                                SemanticChangeType.ATTRIBUTE_CHANGED, "X", "Y"),
                                fileChange("model.xmi", "uuid-B", "name",
                                        SemanticChangeType.ATTRIBUTE_CHANGED, "X", "Baz"))));

                git.checkout().setName("master").call();

                ReplayResult result = new SemanticConflictDetector(repoDir).analyzeBranches("master", "feature-x");

                assertTrue(result.hasConflicts());
                assertEquals(1, result.highSeverityCount());
                assertEquals(1, result.mediumSeverityCount());
            }
        }
    }


    @Nested
    @DisplayName("analyzeBranches - edge cases")
    class EdgeCases {

        @Test
        @DisplayName("throws when branch does not exist")
        void throwsForMissingBranch(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var detector = new SemanticConflictDetector(repoDir);
                assertThrows(BranchOperationException.class,
                        () -> detector.analyzeBranches("master", "nonexistent"));
            }
        }

        @Test
        @DisplayName("throws for null parameters")
        void throwsForNullParameters(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var detector = new SemanticConflictDetector(repoDir);
                assertThrows(NullPointerException.class, () -> detector.analyzeBranches(null, "master"));
                assertThrows(NullPointerException.class, () -> detector.analyzeBranches("master", null));
            }
        }

        @Test
        @DisplayName("handles two branches with a shared HEAD correctly (no commits since ancestor)")
        void noCommitsSinceAncestorWhenBranchesAtSameCommit(@TempDir Path repoDir) throws Exception {
            try (Git git = initRepo(repoDir)) {
                // Create feature-x from master HEAD without any new commits
                git.branchCreate().setName("feature-x").call();

                ReplayResult result = new SemanticConflictDetector(repoDir).analyzeBranches("master", "feature-x");

                assertFalse(result.hasConflicts());
                assertTrue(result.getCommitShasOnA().isEmpty(), "no diverging commits on master");
                assertTrue(result.getCommitShasOnB().isEmpty(), "no diverging commits on feature-x");
            }
        }
    }


    @Nested
    @DisplayName("resolveDirectConflicts - ChangeOrigin auto-resolution")
    class ConflictResolution {

        @Test
        @DisplayName("auto-resolves conflict when one side is ORIGINAL and the other is CONSEQUENTIAL")
        void autoResolvesOriginalVsConsequential(@TempDir Path repoDir) throws Exception {
            try (Git git = initRepo(repoDir)) {
                git.branchCreate().setName("feature-x").call();

                // master: ORIGINAL change to uuid-A.name (human-made)
                String masterSha = commitFile(git, repoDir, "a.xmi", "<A/>", "master changes A.name");
                String masterShort = masterSha.substring(0, 7);
                commitChangelog(git, repoDir, "master", masterShort, changelogJson(masterSha, "master",
                        List.of(fileChange("a.xmi", "uuid-A", "name",
                                SemanticChangeType.ATTRIBUTE_CHANGED, "Old", "Bar", "original"))));

                // feature-x: CONSEQUENTIAL change to the same uuid-A.name (Vitruvius reaction)
                git.checkout().setName("feature-x").call();
                String featureSha = commitFile(git, repoDir, "a.xmi", "<A/>", "feature reaction updates A.name");
                String featureShort = featureSha.substring(0, 7);
                commitChangelog(git, repoDir, "feature-x", featureShort, changelogJson(featureSha, "feature-x",
                        List.of(fileChange("a.xmi", "uuid-A", "name",
                                SemanticChangeType.ATTRIBUTE_CHANGED, "Old", "Baz", "consequential"))));

                git.checkout().setName("master").call();

                ReplayResult result = new SemanticConflictDetector(repoDir).analyzeBranches("master", "feature-x");

                assertFalse(result.hasConflicts(),
                        "ORIGINAL vs CONSEQUENTIAL on same element+feature must be auto-resolved");
            }
        }

        @Test
        @DisplayName("does NOT auto-resolve conflict when both sides are ORIGINAL")
        void doesNotAutoResolveOriginalVsOriginal(@TempDir Path repoDir) throws Exception {
            try (Git git = initRepo(repoDir)) {
                git.branchCreate().setName("feature-x").call();

                // master: ORIGINAL change to uuid-A.name
                String masterSha = commitFile(git, repoDir, "a.xmi", "<A/>", "master changes A.name");
                String masterShort = masterSha.substring(0, 7);
                commitChangelog(git, repoDir, "master", masterShort, changelogJson(masterSha, "master",
                        List.of(fileChange("a.xmi", "uuid-A", "name",
                                SemanticChangeType.ATTRIBUTE_CHANGED, "Old", "Bar", "original"))));

                // feature-x: also ORIGINAL change to uuid-A.name (another human made a different choice)
                git.checkout().setName("feature-x").call();
                String featureSha = commitFile(git, repoDir, "a.xmi", "<A/>", "feature also changes A.name");
                String featureShort = featureSha.substring(0, 7);
                commitChangelog(git, repoDir, "feature-x", featureShort, changelogJson(featureSha, "feature-x",
                        List.of(fileChange("a.xmi", "uuid-A", "name",
                                SemanticChangeType.ATTRIBUTE_CHANGED, "Old", "Baz", "original"))));

                git.checkout().setName("master").call();

                ReplayResult result = new SemanticConflictDetector(repoDir).analyzeBranches("master", "feature-x");

                assertTrue(result.hasConflicts(),
                        "ORIGINAL vs ORIGINAL requires human resolution and must remain unresolved");
                assertEquals(1, result.getConflicts().size());
                assertEquals(ConflictSeverity.MEDIUM, result.getConflicts().get(0).getSeverity());
            }
        }

        @Test
        @DisplayName("does NOT auto-resolve conflict when both sides have UNKNOWN origin")
        void doesNotAutoResolveUnknownVsUnknown(@TempDir Path repoDir) throws Exception {
            try (Git git = initRepo(repoDir)) {
                git.branchCreate().setName("feature-x").call();

                // Both sides: changeOrigin absent (null -> UNKNOWN)
                String masterSha = commitFile(git, repoDir, "a.xmi", "<A/>", "master changes A.name");
                commitChangelog(git, repoDir, "master", masterSha.substring(0, 7), changelogJson(masterSha,
                        "master", List.of(fileChange("a.xmi", "uuid-A", "name",
                                SemanticChangeType.ATTRIBUTE_CHANGED, "Old", "Bar"))));

                git.checkout().setName("feature-x").call();
                String featureSha = commitFile(git, repoDir, "a.xmi", "<A/>", "feature changes A.name");
                commitChangelog(git, repoDir, "feature-x", featureSha.substring(0, 7), changelogJson(featureSha,
                        "feature-x", List.of(fileChange("a.xmi", "uuid-A", "name",
                                SemanticChangeType.ATTRIBUTE_CHANGED, "Old", "Baz"))));

                git.checkout().setName("master").call();

                ReplayResult result = new SemanticConflictDetector(repoDir).analyzeBranches("master", "feature-x");

                assertTrue(result.hasConflicts(),
                        "UNKNOWN vs UNKNOWN must remain unresolved -- no origin information to decide");
            }
        }
    }


    @Nested
    @DisplayName("analyzeBranches - tombstone conflict detection")
    class TombstoneConflicts {

        @Test
        @DisplayName("detects HIGH conflict when branch A deletes a container and branch B modifies a child")
        void detectsTombstoneWhenADeletesContainerAndBModifiesChild(@TempDir Path repoDir) throws Exception {
            try (Git git = initRepo(repoDir)) {
                git.branchCreate().setName("feature-x").call();

                // master: deletes the parent element "uuid-parent"
                String masterSha = commitFile(git, repoDir, "model.xmi", "", "delete parent on master");
                commitChangelog(git, repoDir, "master", masterSha.substring(0, 7),
                        changelogJson(masterSha, "master", List.of(
                                fileChange("model.xmi", "uuid-parent", null,
                                        SemanticChangeType.ELEMENT_DELETED, null, null))));

                // feature-x: modifies a child element whose containerUuid == "uuid-parent"
                git.checkout().setName("feature-x").call();
                String featureSha = commitFile(git, repoDir, "model.xmi", "<model/>", "modify child on feature-x");
                commitChangelog(git, repoDir, "feature-x", featureSha.substring(0, 7),
                        changelogJson(featureSha, "feature-x", List.of(
                                fileChangeWithContainer("model.xmi", "uuid-child", "name",
                                        SemanticChangeType.ATTRIBUTE_CHANGED, "Old", "New", "uuid-parent"))));

                git.checkout().setName("master").call();

                ReplayResult result = new SemanticConflictDetector(repoDir).analyzeBranches("master", "feature-x");

                assertTrue(result.hasConflicts(), "orphaned child change must produce a conflict");
                assertEquals(1, result.getConflicts().size());
                SemanticConflict conflict = result.getConflicts().get(0);
                assertEquals(ConflictSeverity.HIGH, conflict.getSeverity());
                assertEquals("uuid-parent", conflict.getElementUuid(),
                        "conflict must reference the deleted container UUID");
                assertNull(conflict.getFeature(), "tombstone conflicts have no feature (lifecycle level)");
                assertEquals(SemanticChangeType.ELEMENT_DELETED,
                        conflict.getChangeOnBranchA().getChangeType(),
                        "branch A entry must be the ELEMENT_DELETED record");
            }
        }

        @Test
        @DisplayName("detects HIGH conflict when branch B deletes a container and branch A modifies a child")
        void detectsTombstoneWhenBDeletesContainerAndAModifiesChild(@TempDir Path repoDir) throws Exception {
            try (Git git = initRepo(repoDir)) {
                git.branchCreate().setName("feature-x").call();

                // master: modifies a child element whose containerUuid == "uuid-parent"
                String masterSha = commitFile(git, repoDir, "model.xmi", "<model/>", "modify child on master");
                commitChangelog(git, repoDir, "master", masterSha.substring(0, 7),
                        changelogJson(masterSha, "master", List.of(
                                fileChangeWithContainer("model.xmi", "uuid-child", "name",
                                        SemanticChangeType.ATTRIBUTE_CHANGED, "Old", "New", "uuid-parent"))));

                // feature-x: deletes the parent element "uuid-parent"
                git.checkout().setName("feature-x").call();
                String featureSha = commitFile(git, repoDir, "model.xmi", "", "delete parent on feature-x");
                commitChangelog(git, repoDir, "feature-x", featureSha.substring(0, 7),
                        changelogJson(featureSha, "feature-x", List.of(
                                fileChange("model.xmi", "uuid-parent", null,
                                        SemanticChangeType.ELEMENT_DELETED, null, null))));

                git.checkout().setName("master").call();

                ReplayResult result = new SemanticConflictDetector(repoDir).analyzeBranches("master", "feature-x");

                assertTrue(result.hasConflicts(), "orphaned child change must produce a conflict");
                assertEquals(ConflictSeverity.HIGH, result.getConflicts().get(0).getSeverity());
                assertEquals("uuid-parent", result.getConflicts().get(0).getElementUuid());
                assertEquals(SemanticChangeType.ELEMENT_DELETED,
                        result.getConflicts().get(0).getChangeOnBranchB().getChangeType(),
                        "branch B entry must be the ELEMENT_DELETED record");
            }
        }

        @Test
        @DisplayName("reports only one conflict when multiple children of the same deleted container are modified")
        void deduplicatesMultipleChildrenOfSameDeletedContainer(@TempDir Path repoDir) throws Exception {
            try (Git git = initRepo(repoDir)) {
                git.branchCreate().setName("feature-x").call();

                // master: deletes "uuid-parent"
                String masterSha = commitFile(git, repoDir, "model.xmi", "", "delete parent on master");
                commitChangelog(git, repoDir, "master", masterSha.substring(0, 7),
                        changelogJson(masterSha, "master", List.of(
                                fileChange("model.xmi", "uuid-parent", null,
                                        SemanticChangeType.ELEMENT_DELETED, null, null))));

                // feature-x: modifies two separate children of "uuid-parent"
                git.checkout().setName("feature-x").call();
                String featureSha = commitFile(git, repoDir, "model.xmi", "<model/>", "modify two children on feature-x");
                commitChangelog(git, repoDir, "feature-x", featureSha.substring(0, 7),
                        changelogJson(featureSha, "feature-x", List.of(
                                fileChangeWithContainer("model.xmi", "uuid-child1", "name",
                                        SemanticChangeType.ATTRIBUTE_CHANGED, "Old", "A", "uuid-parent"),
                                fileChangeWithContainer("model.xmi", "uuid-child2", "name",
                                        SemanticChangeType.ATTRIBUTE_CHANGED, "Old", "B", "uuid-parent"))));

                git.checkout().setName("master").call();

                ReplayResult result = new SemanticConflictDetector(repoDir).analyzeBranches("master", "feature-x");

                assertTrue(result.hasConflicts());
                assertEquals(1, result.getConflicts().size(),
                        "multiple orphaned children of the same deleted container must produce exactly one conflict");
                assertEquals("uuid-parent", result.getConflicts().get(0).getElementUuid());
            }
        }

        @Test
        @DisplayName("does not produce a tombstone conflict when containerUuid does not match any deletion")
        void noFalsePositiveWhenContainerUuidDoesNotMatchDeletion(@TempDir Path repoDir) throws Exception {
            try (Git git = initRepo(repoDir)) {
                git.branchCreate().setName("feature-x").call();

                // master: plain attribute change, no deletions
                String masterSha = commitFile(git, repoDir, "model.xmi", "<model/>", "master changes");
                commitChangelog(git, repoDir, "master", masterSha.substring(0, 7),
                        changelogJson(masterSha, "master", List.of(
                                fileChange("model.xmi", "uuid-A", "name",
                                        SemanticChangeType.ATTRIBUTE_CHANGED, "Old", "New"))));

                // feature-x: change with a containerUuid that was never deleted
                git.checkout().setName("feature-x").call();
                String featureSha = commitFile(git, repoDir, "model.xmi", "<model/>", "feature changes");
                commitChangelog(git, repoDir, "feature-x", featureSha.substring(0, 7),
                        changelogJson(featureSha, "feature-x", List.of(
                                fileChangeWithContainer("model.xmi", "uuid-B", "name",
                                        SemanticChangeType.ATTRIBUTE_CHANGED, "Old", "New", "uuid-unrelated-container"))));

                git.checkout().setName("master").call();

                ReplayResult result = new SemanticConflictDetector(repoDir).analyzeBranches("master", "feature-x");

                assertFalse(result.hasConflicts(),
                        "containerUuid that does not match any deletion must not produce a tombstone conflict");
            }
        }
    }


    @Nested
    @DisplayName("analyzeBranches - cascade DELETE_MODIFY / MODIFY_DELETE detection")
    class CascadeConflicts {

        @Test
        @DisplayName("detects HIGH conflict when A deletes parent (cascadeDeletedUuids) and B modifies the child")
        void detectsCascadeDeleteModifyWhenADeletesParentAndBModifiesChild(@TempDir Path repoDir) throws Exception {
            try (Git git = initRepo(repoDir)) {
                git.branchCreate().setName("feature-x").call();

                // master: deletes "uuid-parent"; its child "uuid-child" is recorded in cascadeDeletedUuids
                String masterSha = commitFile(git, repoDir, "model.xmi", "", "master deletes parent");
                commitChangelog(git, repoDir, "master", masterSha.substring(0, 7),
                        changelogJson(masterSha, "master", List.of(
                                fileChangeWithCascade("model.xmi", "uuid-parent",
                                        List.of("uuid-child")))));

                // feature-x: modifies "uuid-child" (the deleted descendant)
                git.checkout().setName("feature-x").call();
                String featureSha = commitFile(git, repoDir, "model.xmi", "<model/>", "feature modifies child");
                commitChangelog(git, repoDir, "feature-x", featureSha.substring(0, 7),
                        changelogJson(featureSha, "feature-x", List.of(
                                fileChange("model.xmi", "uuid-child", "name",
                                        SemanticChangeType.ATTRIBUTE_CHANGED, "Old", "New"))));

                git.checkout().setName("master").call();

                ReplayResult result = new SemanticConflictDetector(repoDir).analyzeBranches("master", "feature-x");

                assertTrue(result.hasConflicts(), "cascade-deleted descendant modified on other branch must conflict");
                assertEquals(1, result.getConflicts().size());
                SemanticConflict conflict = result.getConflicts().get(0);
                assertEquals(ConflictSeverity.HIGH, conflict.getSeverity());
                assertEquals("uuid-child", conflict.getElementUuid(),
                        "conflict must reference the modified cascade-deleted child UUID");
                assertNull(conflict.getFeature(), "cascade conflicts are at lifecycle level -- no feature");
                assertEquals(SemanticChangeType.ELEMENT_DELETED,
                        conflict.getChangeOnBranchA().getChangeType(),
                        "branch A entry must be the ELEMENT_DELETED record");
                assertEquals(SemanticChangeType.ATTRIBUTE_CHANGED,
                        conflict.getChangeOnBranchB().getChangeType(),
                        "branch B entry must be the modifying ATTRIBUTE_CHANGED record");
            }
        }

        @Test
        @DisplayName("detects HIGH conflict when B deletes parent (cascadeDeletedUuids) and A modifies the child")
        void detectsCascadeModifyDeleteWhenBDeletesParentAndAModifiesChild(@TempDir Path repoDir) throws Exception {
            try (Git git = initRepo(repoDir)) {
                git.branchCreate().setName("feature-x").call();

                // master: modifies "uuid-child"
                String masterSha = commitFile(git, repoDir, "model.xmi", "<model/>", "master modifies child");
                commitChangelog(git, repoDir, "master", masterSha.substring(0, 7),
                        changelogJson(masterSha, "master", List.of(
                                fileChange("model.xmi", "uuid-child", "name",
                                        SemanticChangeType.ATTRIBUTE_CHANGED, "Old", "New"))));

                // feature-x: deletes "uuid-parent"; "uuid-child" is a cascade descendant
                git.checkout().setName("feature-x").call();
                String featureSha = commitFile(git, repoDir, "model.xmi", "", "feature deletes parent");
                commitChangelog(git, repoDir, "feature-x", featureSha.substring(0, 7),
                        changelogJson(featureSha, "feature-x", List.of(
                                fileChangeWithCascade("model.xmi", "uuid-parent",
                                        List.of("uuid-child")))));

                git.checkout().setName("master").call();

                ReplayResult result = new SemanticConflictDetector(repoDir).analyzeBranches("master", "feature-x");

                assertTrue(result.hasConflicts(), "cascade MODIFY_DELETE must be detected");
                assertEquals(1, result.getConflicts().size());
                SemanticConflict conflict = result.getConflicts().get(0);
                assertEquals(ConflictSeverity.HIGH, conflict.getSeverity());
                assertEquals("uuid-child", conflict.getElementUuid());
                assertEquals(SemanticChangeType.ATTRIBUTE_CHANGED,
                        conflict.getChangeOnBranchA().getChangeType(),
                        "branch A entry must be the modifying record");
                assertEquals(SemanticChangeType.ELEMENT_DELETED,
                        conflict.getChangeOnBranchB().getChangeType(),
                        "branch B entry must be the ELEMENT_DELETED record");
            }
        }

        @Test
        @DisplayName("no false positive when cascadeDeletedUuids do not overlap with modified UUIDs")
        void noCascadeFalsePositiveWhenUuidsDoNotOverlap(@TempDir Path repoDir) throws Exception {
            try (Git git = initRepo(repoDir)) {
                git.branchCreate().setName("feature-x").call();

                // master: deletes "uuid-parent" with cascadeDeletedUuids = ["uuid-unrelated"]
                String masterSha = commitFile(git, repoDir, "model.xmi", "", "master deletes parent");
                commitChangelog(git, repoDir, "master", masterSha.substring(0, 7),
                        changelogJson(masterSha, "master", List.of(
                                fileChangeWithCascade("model.xmi", "uuid-parent",
                                        List.of("uuid-unrelated")))));

                // feature-x: modifies "uuid-different" (not in cascade list)
                git.checkout().setName("feature-x").call();
                String featureSha = commitFile(git, repoDir, "model.xmi", "<model/>", "feature modifies different element");
                commitChangelog(git, repoDir, "feature-x", featureSha.substring(0, 7),
                        changelogJson(featureSha, "feature-x", List.of(
                                fileChange("model.xmi", "uuid-different", "name",
                                        SemanticChangeType.ATTRIBUTE_CHANGED, "Old", "New"))));

                git.checkout().setName("master").call();

                ReplayResult result = new SemanticConflictDetector(repoDir).analyzeBranches("master", "feature-x");

                assertFalse(result.hasConflicts(),
                        "non-overlapping cascade UUIDs must not produce a false conflict");
            }
        }
    }


    @Nested
    @DisplayName("merge engine wiring")
    class MergeEngineWiring {

        /**
         * Verifies that when a SemanticMergeEngine is attached and steps 1-3 find no direct
         * conflicts, the engine path is entered and its result is returned.
         *
         * Setup: master has no changelog (no source changes for the engine to replay).
         * The engine's fast-path fires -- "theirsDtos empty -> trivially SUCCESS" -- without
         * loading any V-SUM, so the test works without a real model setup.
         *
         * This confirms: (a) the engine wiring is entered when mergeEngine != null,
         * and (b) the engine result (SUCCESS with no conflicts) is correctly converted
         * to a ReplayResult with no conflicts.
         */
        @Test
        @DisplayName("engine path returns no conflicts when source branch has no changelog to replay")
        void enginePathSucceedsWhenNothingToReplay(@TempDir Path repoDir) throws Exception {
            try (Git git = initRepo(repoDir)) {
                git.branchCreate().setName("feature-x").call();

                // master: plain model commit, NO changelog (no source changes for engine to replay)
                commitFile(git, repoDir, "a.xmi", "<A/>", "master adds a.xmi");

                // feature-x: commit with a changelog for a different element
                git.checkout().setName("feature-x").call();
                String featureSha = commitFile(git, repoDir, "b.xmi", "<B/>", "feature adds b.xmi");
                String featureShort = featureSha.substring(0, 7);
                commitChangelog(git, repoDir, "feature-x", featureShort, changelogJson(featureSha,
                        "feature-x", List.of(fileChange("b.xmi", "uuid-B", "name",
                                SemanticChangeType.ATTRIBUTE_CHANGED, "Old", "Xyz", "original"))));

                git.checkout().setName("master").call();

                // Attach a real SemanticMergeEngine (empty specs, no interaction provider).
                // The engine takes the fast-path (no source changelogs to replay) and
                // returns SUCCESS without loading a V-SUM.
                SemanticMergeEngine engine = new SemanticMergeEngine(repoDir, List.of(), null);
                SemanticConflictDetector detector = new SemanticConflictDetector(repoDir, engine);

                ReplayResult result = detector.analyzeBranches("master", "feature-x");

                assertFalse(result.hasConflicts(),
                        "engine reported SUCCESS (nothing to replay) -- result must have no conflicts");
            }
        }

        @Test
        @DisplayName("detector falls back to stub pipeline when engine throws")
        void fallsBackToStubWhenEngineFails(@TempDir Path repoDir) throws Exception {
            try (Git git = initRepo(repoDir)) {
                git.branchCreate().setName("feature-x").call();

                // Both branches: non-overlapping changelogs so steps 1-3 find no direct conflicts.
                String masterSha = commitFile(git, repoDir, "a.xmi", "<A/>", "master changes A");
                commitChangelog(git, repoDir, "master", masterSha.substring(0, 7), changelogJson(masterSha,
                        "master", List.of(fileChange("a.xmi", "uuid-A", "name",
                                SemanticChangeType.ATTRIBUTE_CHANGED, "Old", "Bar", "original"))));

                git.checkout().setName("feature-x").call();
                String featureSha = commitFile(git, repoDir, "b.xmi", "<B/>", "feature changes B");
                commitChangelog(git, repoDir, "feature-x", featureSha.substring(0, 7), changelogJson(featureSha,
                        "feature-x", List.of(fileChange("b.xmi", "uuid-B", "name",
                                SemanticChangeType.ATTRIBUTE_CHANGED, "Old", "Xyz", "original"))));

                git.checkout().setName("master").call();

                // Attach a real engine. Here both branches have changelogs, so the engine
                // will try to load a V-SUM with empty specs and fail. The catch block in
                // analyzeBranches() catches RuntimeException and falls back to the stub pipeline.
                SemanticMergeEngine engine = new SemanticMergeEngine(repoDir, List.of(), null);
                SemanticConflictDetector detector = new SemanticConflictDetector(repoDir, engine);

                // Should not throw -- the fallback guarantees a valid result.
                ReplayResult result = detector.analyzeBranches("master", "feature-x");

                assertFalse(result.hasConflicts(),
                        "stub pipeline found no direct conflicts -- result must have no conflicts");
            }
        }
    }
}
