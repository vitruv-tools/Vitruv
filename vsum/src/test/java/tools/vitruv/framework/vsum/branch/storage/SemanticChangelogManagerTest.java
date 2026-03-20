package tools.vitruv.framework.vsum.branch.storage;

import com.google.gson.Gson;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.eobject.CreateEObject;
import tools.vitruv.change.atomic.uuid.UuidResolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link SemanticChangelogManager}.
 *
 * <p>Tests verify the branch-scoped directory structure, the JSON schema produced by
 * {@link SemanticChangelogManager#write}, and the round-trip behaviour of
 * {@link SemanticChangelogManager#read}.
 */
@SuppressWarnings({"rawtypes", "unchecked"})
class SemanticChangelogManagerTest {

    @TempDir Path repoRoot;

    private UuidResolver uuidResolver;
    private CreateEObject createChange;
    private EObject element;

    private SemanticChangelogManager manager;

    private static final String COMMIT_SHA  = "abc1234567890abcdef1234567890abcdef12345";
    private static final String SHORT_SHA   = "abc1234";
    private static final String BRANCH      = "feature-x";
    private static final String AUTHOR      = "Alice";
    private static final LocalDateTime DATE = LocalDateTime.of(2026, 3, 19, 10, 0, 0);
    private static final String MESSAGE     = "Add entity model";

    @BeforeEach
    void setUp() {
        uuidResolver = mock(UuidResolver.class);
        createChange = mock(CreateEObject.class);
        element      = mock(EObject.class);
        manager      = new SemanticChangelogManager(repoRoot);
    }

    @Nested
    @DisplayName("Directory and file layout")
    class FileLayout {

        /**
         * Verifies that the JSON changelog is created at the branch-scoped path:
         * {@code .vitruvius/changelogs/<branch>/json/<shortSha>.json}.
         */
        @Test
        @DisplayName("write creates JSON at branch-scoped path")
        void writeCreatesJsonAtBranchScopedPath() throws IOException {
            var written = manager.write(COMMIT_SHA, BRANCH, AUTHOR, DATE, MESSAGE, List.of(), changesByResource(), null, uuidResolver);

            assertEquals(1, written.size(), "only JSON returned when activeResources is null");
            Path expected = repoRoot.resolve(".vitruvius").resolve("changelogs").resolve(BRANCH).resolve("json").resolve(SHORT_SHA + ".json");
            assertEquals(expected, written.get(0));
            assertTrue(Files.exists(expected));
        }

        /**
         * Verifies that parent directories are created automatically.
         */
        @Test
        @DisplayName("write creates parent directories automatically")
        void writeCreatesParentDirectories() throws IOException {
            Path jsonDir = repoRoot.resolve(".vitruvius/changelogs/" + BRANCH + "/json");
            assertFalse(Files.exists(jsonDir));

            manager.write(COMMIT_SHA, BRANCH, AUTHOR, DATE, MESSAGE, List.of(), changesByResource(), null, uuidResolver);

            assertTrue(Files.isDirectory(jsonDir));
        }

        /**
         * Verifies that two commits on different branches produce separate directory trees.
         */
        @Test
        @DisplayName("Different branches produce files in separate directories")
        void differentBranchesProduceFilesInSeparateDirectories() throws IOException {
            manager.write(COMMIT_SHA, "main",      AUTHOR, DATE, MESSAGE, List.of(), changesByResource(), null, uuidResolver);
            manager.write(COMMIT_SHA, "feature-x", AUTHOR, DATE, MESSAGE, List.of(), changesByResource(), null, uuidResolver);

            assertTrue(Files.exists(repoRoot.resolve(".vitruvius/changelogs/main/json/" + SHORT_SHA + ".json")));
            assertTrue(Files.exists(repoRoot.resolve(".vitruvius/changelogs/feature-x/json/" + SHORT_SHA + ".json")));
        }

        /**
         * Verifies that the file name prefix is the first 7 characters of the full SHA.
         */
        @Test
        @DisplayName("File name prefix is the first 7 characters of the full SHA")
        void fileNamePrefixIsFirst7Characters() throws IOException {
            manager.write(COMMIT_SHA, BRANCH, AUTHOR, DATE, MESSAGE, List.of(), changesByResource(), null, uuidResolver);

            assertTrue(Files.exists(repoRoot.resolve(".vitruvius/changelogs/" + BRANCH + "/json/abc1234.json")));
        }
    }

    @Nested
    @DisplayName("JSON content")
    class JsonContent {

        /**
         * Verifies that the JSON commit block contains all mandatory metadata fields and
         * the full list of parent SHAs (needed for three-way merge).
         */
        @Test
        @DisplayName("JSON commit block contains all metadata fields including parent SHAs")
        void jsonContainsAllCommitMetadataFields() throws IOException {
            List<String> parentShas = List.of("parent111", "parent222");
            manager.write(COMMIT_SHA, BRANCH, AUTHOR, DATE, MESSAGE, parentShas, changesByResource(), null, uuidResolver);

            var doc = readDocument();
            assertEquals("1.0", doc.formatVersion);
            assertEquals(COMMIT_SHA, doc.commit.sha);
            assertEquals(SHORT_SHA, doc.commit.shortSha);
            assertEquals(BRANCH, doc.commit.branch);
            assertEquals(MESSAGE, doc.commit.message);
            assertEquals(AUTHOR, doc.commit.author.name);
            assertEquals(2, doc.commit.parentShas.size());
            assertTrue(doc.commit.parentShas.containsAll(List.of("parent111", "parent222")));
        }

        /**
         * Verifies that summary totals match the number of file changes and semantic changes.
         */
        @Test
        @DisplayName("JSON summary reflects total file and semantic change counts")
        void jsonSummaryReflectsTotals() throws IOException {
            manager.write(COMMIT_SHA, BRANCH, AUTHOR, DATE, MESSAGE, List.of(), changesByResource(), null, uuidResolver);

            var doc = readDocument();
            assertEquals(1, doc.summary.totalFileChanges);
            assertEquals(1, doc.summary.totalSemanticChanges);
        }

        /**
         * Verifies that an empty changesByResource map produces a valid JSON with zero totals.
         */
        @Test
        @DisplayName("Empty changesByResource produces valid JSON with zero totals")
        void emptyChangesProducesValidJson() throws IOException {
            manager.write(COMMIT_SHA, BRANCH, AUTHOR, DATE, MESSAGE, List.of(), Map.of(), null, uuidResolver);

            var doc = readDocument();
            assertNotNull(doc);
            assertEquals(0, doc.summary.totalFileChanges);
            assertEquals(0, doc.summary.totalSemanticChanges);
        }

        /**
         * Verifies that two resources in the changes map produce two entries in fileChanges.
         */
        @Test
        @DisplayName("Two changed resources produce two fileChanges entries")
        void twoChangedResourcesProduceTwoFileChangeEntries() throws IOException {
            EObject elementB = mock(EObject.class);
            when(elementB.eClass()).thenReturn(null);
            CreateEObject changeB = mock(CreateEObject.class);
            when(changeB.getAffectedElement()).thenReturn(elementB);
            when(uuidResolver.hasUuid(elementB)).thenReturn(false);

            Map<String, List<EChange<EObject>>> changes = Map.of("file:///models/A.xmi", List.of(createChange), "file:///models/B.xmi", List.of(changeB)
            );
            // Need to set up the first change too
            when(createChange.getAffectedElement()).thenReturn(element);
            when(element.eClass()).thenReturn(null);
            when(uuidResolver.hasUuid(element)).thenReturn(false);

            manager.write(COMMIT_SHA, BRANCH, AUTHOR, DATE, MESSAGE, List.of(), changes, null, uuidResolver);

            var doc = readDocument();
            assertEquals(2, doc.fileChanges.size());
        }
    }

    @Nested
    @DisplayName("read")
    class ReadTests {

        /**
         * Verifies that {@code read} returns the document written by {@code write} (round-trip).
         */
        @Test
        @DisplayName("read returns the document written by write (round-trip)")
        void readReturnsDocumentWrittenByWrite() throws IOException {
            manager.write(COMMIT_SHA, BRANCH, AUTHOR, DATE, MESSAGE, List.of(), changesByResource(), null, uuidResolver);

            var doc = manager.read(BRANCH, SHORT_SHA);

            assertNotNull(doc);
            assertEquals(COMMIT_SHA, doc.commit.sha);
            assertEquals(BRANCH, doc.commit.branch);
        }

        /**
         * Verifies that {@code read} returns null when no file exists.
         */
        @Test
        @DisplayName("read returns null when no file exists for the given branch and SHA")
        void readReturnsNullForMissingFile() throws IOException {
            assertNull(manager.read(BRANCH, "0000000"));
        }

        /**
         * Verifies that {@code read} returns null when the branch directory does not contain
         * the file (even if the same SHA exists on another branch).
         */
        @Test
        @DisplayName("read returns null when SHA exists on a different branch")
        void readReturnsNullForWrongBranch() throws IOException {
            manager.write(COMMIT_SHA, "main", AUTHOR, DATE, MESSAGE, List.of(), changesByResource(), null, uuidResolver);

            assertNull(manager.read("feature-x", SHORT_SHA));
        }
    }

    @Nested
    @DisplayName("XMI snapshot writing")
    class XmiWriting {

        /**
         * Verifies that null activeResources skips XMI writing and returns only the JSON path.
         */
        @Test
        @DisplayName("null activeResources skips XMI writing and returns only the JSON path")
        void nullActiveResourcesSkipsXmi() throws IOException {
            var written = manager.write(COMMIT_SHA, BRANCH, AUTHOR, DATE, MESSAGE, List.of(), changesByResource(), null, uuidResolver);

            assertEquals(1, written.size());
        }

        /**
         * Verifies that an empty resource list skips XMI writing.
         */
        @Test
        @DisplayName("Empty activeResources list skips XMI writing")
        void emptyActiveResourcesSkipsXmi() throws IOException {
            var written = manager.write(COMMIT_SHA, BRANCH, AUTHOR, DATE, MESSAGE, List.of(), changesByResource(), List.of(), uuidResolver);

            assertEquals(1, written.size());
        }

        /**
         * Verifies that a resource whose URI does not match any key in changesByResource
         * produces no XMI file.
         */
        @Test
        @DisplayName("Non-matching active resource produces no XMI file")
        void nonMatchingResourceProducesNoXmiFile() throws IOException {
            Resource resource = mock(Resource.class);
            when(resource.getURI()).thenReturn(URI.createURI("file:///unrelated/path.xmi"));

            var written = manager.write(COMMIT_SHA, BRANCH, AUTHOR, DATE, MESSAGE, List.of(), changesByResource(), List.of(resource), uuidResolver);

            assertEquals(1, written.size(), "only JSON must be returned when no resource matches");
        }
    }

    /**
     * Verifies that null required parameters are rejected immediately.
     */
    @Test
    @DisplayName("write throws NullPointerException for null required parameters")
    void writeThrowsForNullRequiredParameters() {
        assertThrows(NullPointerException.class, () -> manager.write(null, BRANCH, AUTHOR, DATE, MESSAGE, List.of(), Map.of(), null, uuidResolver));
        assertThrows(NullPointerException.class, () -> manager.write(COMMIT_SHA, null, AUTHOR, DATE, MESSAGE, List.of(), Map.of(), null, uuidResolver));
        assertThrows(NullPointerException.class, () -> manager.write(COMMIT_SHA, BRANCH, AUTHOR, DATE, MESSAGE, List.of(), null, null, uuidResolver));
        assertThrows(NullPointerException.class, () -> manager.write(COMMIT_SHA, BRANCH, AUTHOR, DATE, MESSAGE, List.of(), Map.of(), null, null));
    }

    @SuppressWarnings("unchecked")
    private Map<String, List<EChange<EObject>>> changesByResource() {
        when(createChange.getAffectedElement()).thenReturn(element);
        when(element.eClass()).thenReturn(null);
        when(uuidResolver.hasUuid(element)).thenReturn(false);
        return Map.of("file:///models/Test.xmi", List.of(createChange));
    }

    private SemanticChangelogManager.ChangelogDocument readDocument() throws IOException {
        Path file = repoRoot.resolve(".vitruvius/changelogs/" + SemanticChangelogManagerTest.BRANCH + "/json/" + SemanticChangelogManagerTest.SHORT_SHA + ".json");
        assertTrue(Files.exists(file), "changelog file must exist: " + file);
        return new Gson().fromJson(Files.readString(file), SemanticChangelogManager.ChangelogDocument.class);
    }
}
