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
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeType;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangelogManager;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static tools.vitruv.framework.vsum.branch.GitTestHelper.commitFile;
import static tools.vitruv.framework.vsum.branch.GitTestHelper.initRepo;

/**
 * Unit tests for {@link ChangeReplayer}.
 *
 * <p>Changelogs are written as real JSON files committed to the test repo so that
 * the TreeWalk-based reading path is exercised end-to-end.
 */
class ChangeReplayerTest {

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
        int position;
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
    @DisplayName("replayBranches - no conflicts")
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

                // Switch back to master for replay
                git.checkout().setName("master").call();

                var replayer = new ChangeReplayer(repoDir);
                ReplayResult result = replayer.replayBranches("master", "feature-x");

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

                ReplayResult result = new ChangeReplayer(repoDir).replayBranches("master", "feature-x");

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

                ReplayResult result = new ChangeReplayer(repoDir).replayBranches("master", "feature-x");

                assertFalse(result.hasConflicts());
                assertTrue(result.getChangesOnA().isEmpty());
                assertTrue(result.getChangesOnB().isEmpty());
            }
        }
    }


    @Nested
    @DisplayName("replayBranches - conflict detection")
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

                ReplayResult result = new ChangeReplayer(repoDir).replayBranches("master", "feature-x");

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

                ReplayResult result = new ChangeReplayer(repoDir).replayBranches("master", "feature-x");

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

                // Both branches deleted uuid-A → same outcome, auto-resolvable
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

                ReplayResult result = new ChangeReplayer(repoDir).replayBranches("master", "feature-x");

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

                ReplayResult result = new ChangeReplayer(repoDir).replayBranches("master", "feature-x");

                assertTrue(result.hasConflicts());
                assertEquals(1, result.highSeverityCount());
                assertEquals(1, result.mediumSeverityCount());
            }
        }
    }


    @Nested
    @DisplayName("replayBranches - edge cases")
    class EdgeCases {

        @Test
        @DisplayName("throws when branch does not exist")
        void throwsForMissingBranch(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var replayer = new ChangeReplayer(repoDir);
                assertThrows(BranchOperationException.class,
                        () -> replayer.replayBranches("master", "nonexistent"));
            }
        }

        @Test
        @DisplayName("throws for null parameters")
        void throwsForNullParameters(@TempDir Path repoDir) throws Exception {
            try (var ignored = initRepo(repoDir)) {
                var replayer = new ChangeReplayer(repoDir);
                assertThrows(NullPointerException.class, () -> replayer.replayBranches(null, "master"));
                assertThrows(NullPointerException.class, () -> replayer.replayBranches("master", null));
            }
        }

        @Test
        @DisplayName("handles two branches with a shared HEAD correctly (no commits since ancestor)")
        void noCommitsSinceAncestorWhenBranchesAtSameCommit(@TempDir Path repoDir) throws Exception {
            try (Git git = initRepo(repoDir)) {
                // Create feature-x from master HEAD without any new commits
                git.branchCreate().setName("feature-x").call();

                ReplayResult result = new ChangeReplayer(repoDir).replayBranches("master", "feature-x");

                assertFalse(result.hasConflicts());
                assertTrue(result.getCommitShasOnA().isEmpty(), "no diverging commits on master");
                assertTrue(result.getCommitShasOnB().isEmpty(), "no diverging commits on feature-x");
            }
        }
    }
}
