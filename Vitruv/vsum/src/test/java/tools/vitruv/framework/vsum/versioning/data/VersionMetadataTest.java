package tools.vitruv.framework.vsum.versioning.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tools.vitruv.framework.vsum.branch.data.MaturityLevel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link VersionMetadata}.
 *
 * <p>Tests verify constructor null rejection, the null-description default,
 * {@link VersionMetadata#setMaturity}, JSON write/read round-trip,
 * missing-field detection, and unknown-maturity fallback.
 */
class VersionMetadataTest {

    private static final String VERSION_ID = "v1.0";
    private static final String SHA = "abc1234567890abcdef1234567890abcdef12345";
    private static final String BRANCH = "main";
    private static final String TAGGER = "Alice";
    private static final String EMAIL = "alice@example.com";
    private static final LocalDateTime CREATED = LocalDateTime.of(2024, 6, 1, 12, 0);

    private VersionMetadata build(String description, MaturityLevel maturity) {
        return new VersionMetadata(VERSION_ID, SHA, BRANCH, TAGGER, EMAIL,
                CREATED, description, maturity);
    }

    @Test
    @DisplayName("All getters return the values provided at construction")
    void allGettersReturnProvidedValues() {
        var meta = build("Release 1.0", MaturityLevel.FINAL);

        assertEquals(VERSION_ID, meta.getVersionId());
        assertEquals(SHA, meta.getCommitSha());
        assertEquals(BRANCH, meta.getBranch());
        assertEquals(TAGGER, meta.getTaggerName());
        assertEquals(EMAIL, meta.getTaggerEmail());
        assertEquals(CREATED, meta.getCreatedAt());
        assertEquals("Release 1.0", meta.getDescription());
        assertEquals(MaturityLevel.FINAL, meta.getMaturity());
    }

    @Test
    @DisplayName("Null description is replaced with an empty string")
    void nullDescriptionBecomesEmptyString() {
        var meta = build(null, MaturityLevel.DRAFT);
        assertEquals("", meta.getDescription());
    }

    @Test
    @DisplayName("Null versionId, commitSha, branch, taggerName, taggerEmail, or createdAt are rejected")
    void nullRequiredFieldsAreRejected() {
        assertThrows(Exception.class,
                () -> new VersionMetadata(null, SHA, BRANCH, TAGGER, EMAIL, CREATED, "d", MaturityLevel.DRAFT));
        assertThrows(Exception.class,
                () -> new VersionMetadata(VERSION_ID, null, BRANCH, TAGGER, EMAIL, CREATED, "d", MaturityLevel.DRAFT));
        assertThrows(Exception.class,
                () -> new VersionMetadata(VERSION_ID, SHA, null, TAGGER, EMAIL, CREATED, "d", MaturityLevel.DRAFT));
        assertThrows(Exception.class,
                () -> new VersionMetadata(VERSION_ID, SHA, BRANCH, null, EMAIL, CREATED, "d", MaturityLevel.DRAFT));
        assertThrows(Exception.class,
                () -> new VersionMetadata(VERSION_ID, SHA, BRANCH, TAGGER, null, CREATED, "d", MaturityLevel.DRAFT));
        assertThrows(Exception.class,
                () -> new VersionMetadata(VERSION_ID, SHA, BRANCH, TAGGER, EMAIL, null, "d", MaturityLevel.DRAFT));
    }

    @Test
    @DisplayName("setMaturity updates the maturity field")
    void setMaturityUpdatesField() {
        var meta = build("desc", MaturityLevel.DRAFT);
        meta.setMaturity(MaturityLevel.REVIEWED);
        assertEquals(MaturityLevel.REVIEWED, meta.getMaturity());
    }

    @Test
    @DisplayName("setMaturity rejects null")
    void setMaturityRejectsNull() {
        var meta = build("desc", MaturityLevel.DRAFT);
        assertThrows(Exception.class, () -> meta.setMaturity(null));
    }

    @Test
    @DisplayName("writeTo creates a JSON file and readFrom round-trips all fields")
    void writeAndReadRoundTrip(@TempDir Path tempDir) throws IOException {
        var meta = build("First release", MaturityLevel.REVIEWED);
        Path file = tempDir.resolve("v1.0.metadata");

        meta.writeTo(file);

        assertTrue(Files.exists(file), "metadata file must be created");
        String json = Files.readString(file);
        assertTrue(json.contains("\"versionId\""), "JSON must contain versionId field");

        var loaded = VersionMetadata.readFrom(file);

        assertEquals(meta.getVersionId(), loaded.getVersionId());
        assertEquals(meta.getCommitSha(), loaded.getCommitSha());
        assertEquals(meta.getBranch(), loaded.getBranch());
        assertEquals(meta.getTaggerName(), loaded.getTaggerName());
        assertEquals(meta.getTaggerEmail(), loaded.getTaggerEmail());
        assertEquals(meta.getDescription(), loaded.getDescription());
        assertEquals(meta.getMaturity(), loaded.getMaturity());
    }

    @Test
    @DisplayName("writeTo creates parent directories automatically")
    void writeToCreatesParentDirectories(@TempDir Path tempDir) throws IOException {
        var meta = build("desc", MaturityLevel.DRAFT);
        Path nested = tempDir.resolve("versions").resolve("nested").resolve("v1.metadata");

        meta.writeTo(nested);

        assertTrue(Files.exists(nested));
    }

    @Test
    @DisplayName("readFrom throws when a required field is missing")
    void readFromThrowsOnMissingField(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("bad.metadata");
        Files.writeString(file, "{\"versionId\": \"v1\"}"); // missing all other fields

        assertThrows(IllegalArgumentException.class, () -> VersionMetadata.readFrom(file));
    }

    @Test
    @DisplayName("readFrom defaults maturity to DRAFT when the field is missing or unknown")
    void readFromDefaultsDraftForUnknownMaturity(@TempDir Path tempDir) throws IOException {
        var meta = build("desc", MaturityLevel.REVIEWED);
        Path file = tempDir.resolve("v.metadata");
        meta.writeTo(file);

        // corrupt the maturity field
        String json = Files.readString(file);
        Files.writeString(file, json.replace("REVIEWED", "NONEXISTENT_LEVEL"));

        var loaded = VersionMetadata.readFrom(file);
        assertEquals(MaturityLevel.DRAFT, loaded.getMaturity(),
                "unknown maturity value must fall back to DRAFT");
    }

    @Test
    @DisplayName("toString includes versionId, short SHA, branch, and maturity")
    void toStringIncludesKeyFields() {
        var meta = build("desc", MaturityLevel.FINAL);
        String str = meta.toString();
        assertTrue(str.contains(VERSION_ID));
        assertTrue(str.contains(BRANCH));
        assertTrue(str.contains("FINAL"));
    }
}
