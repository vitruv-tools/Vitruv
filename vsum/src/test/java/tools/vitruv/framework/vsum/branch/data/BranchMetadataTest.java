package tools.vitruv.framework.vsum.branch.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link BranchMetadata}.
 *
 * <p>Tests are organized into three groups: construction validation, state mutation, and file serialization (write and read). 
 * The serialization tests are the most critical because metadata file is the only persistence mechanism for branch lifecycle information across Vitruvius sessions.
 */
class BranchMetadataTest {
    
    @Nested
    @DisplayName("construction")
    class Construction {

        /**
         * Verifies that a correctly constructed instance exposes all provided field values through its getters.
         */
        @Test
        @DisplayName("stores all provided fields and exposes through getters")
        void createsWithAllFields() {
            var now = LocalDateTime.now();

            var metadata = new BranchMetadata("feature-vcs", "a1b2c3d", BranchState.ACTIVE, "main", now, now);

            assertEquals("feature-vcs", metadata.getName());
            assertEquals("a1b2c3d", metadata.getUid());
            assertEquals(BranchState.ACTIVE, metadata.getState());
            assertEquals("main", metadata.getParent());
            assertEquals(now, metadata.getCreatedAt());
            assertEquals(now, metadata.getLastModified());
        }

        /**
         * Verifies that the constructor rejects null values for all required fields. Passing null for any field would produce an incomplete metadata object
         * that cannot be reliably persisted or compared, so a {@link NullPointerException} is expected in each case.
         */
        @Test
        @DisplayName("rejects null values for all required constructor fields")
        void rejectsNullFields() {
            var now = LocalDateTime.now();

            assertThrows(NullPointerException.class, () -> new BranchMetadata(null, "uid", BranchState.ACTIVE, "main", now, now), "null name must be rejected");
            assertThrows(NullPointerException.class, () -> new BranchMetadata("name", null, BranchState.ACTIVE, "main", now, now), "null unique identifier must be rejected");
            assertThrows(NullPointerException.class, () -> new BranchMetadata("name", "uid", null, "main", now, now), "null state must be rejected");
            assertThrows(NullPointerException.class, () -> new BranchMetadata("name", "uid", BranchState.ACTIVE, null, now, now), "null parent must be rejected");
            assertThrows(NullPointerException.class, () -> new BranchMetadata("name", "uid", BranchState.ACTIVE, "main", null, now), "null creation timestamp must be rejected");
            assertThrows(NullPointerException.class, () -> new BranchMetadata("name", "uid", BranchState.ACTIVE, "main", now, null), "null last modified timestamp must be rejected");
        }
    }

    @Nested
    @DisplayName("setState")
    class SetState {

        /**
         * Verifies that calling {@link BranchMetadata#setState} updates the state field and also refreshes the {@code lastModified} timestamp to a time after the creation time.
         */
        @Test
        @DisplayName("updates the state and advances the last modified timestamp")
        void updatesStateAndTimestamp() throws InterruptedException {
            var createdAt = LocalDateTime.now();
            var metadata = new BranchMetadata("feature-vcs", "a1b2c3d", BranchState.ACTIVE, "main", createdAt, createdAt);

            // a small sleep ensures the clock advances so that the updated timestamp is measurably later than the creation timestamp on all platforms.
            Thread.sleep(10);
            var beforeUpdate = LocalDateTime.now();
            metadata.setState(BranchState.DELETED);
            var afterUpdate = LocalDateTime.now();

            assertEquals(BranchState.DELETED, metadata.getState(), "state must be updated to the new value");
            assertTrue(metadata.getLastModified().isAfter(createdAt), "last modified timestamp must be later than the creation timestamp");
            assertFalse(metadata.getLastModified().isBefore(beforeUpdate), "last modified timestamp must not be before the setState call");
            assertFalse(metadata.getLastModified().isAfter(afterUpdate), "last modified timestamp must not be after the setState return");
        }

        /**
         * Verifies that passing null to {@link BranchMetadata#setState} throws a {@link NullPointerException}.
         * State must always be a known enum value so that the metadata file can be parsed back without errors.
         */
        @Test
        @DisplayName("rejects null state")
        void rejectsNullState() {
            var now = LocalDateTime.now();
            var metadata = new BranchMetadata("feature-vcs", "a1b2c3d", BranchState.ACTIVE, "main", now, now);

            assertThrows(NullPointerException.class, () -> metadata.setState(null));
        }
    }

    @Nested
    @DisplayName("file serialization")
    class FileSerialization {

        /**
         * Verifies that writing metadata to a file produces a valid key-value file containing all expected fields.
         */
        @Test
        @DisplayName("writes all metadata fields to a key-value file")
        void writesMetadataToFile(@TempDir Path tempDir) throws Exception {
            var metadataPath = tempDir.resolve("feature-vcs.metadata");
            var now = LocalDateTime.now();
            var metadata = new BranchMetadata("feature-vcs", "a1b2c3d", BranchState.ACTIVE, "main", now, now);

            metadata.writeTo(metadataPath);

            assertTrue(Files.exists(metadataPath), "metadata file must be created");
            var lines = Files.readAllLines(metadataPath);
            // verify that every required field is present with the expected value.
            assertTrue(lines.stream().anyMatch(l -> l.startsWith("name=feature-vcs")));
            assertTrue(lines.stream().anyMatch(l -> l.startsWith("uid=a1b2c3d")));
            assertTrue(lines.stream().anyMatch(l -> l.startsWith("state=ACTIVE")));
            assertTrue(lines.stream().anyMatch(l -> l.startsWith("parent=main")));
            assertTrue(lines.stream().anyMatch(l -> l.startsWith("createdAt=")));
            assertTrue(lines.stream().anyMatch(l -> l.startsWith("updatedAt=")));
        }

        /**
         * Verifies that writing metadata and then reading it back produces an object that is field-for-field identical to the original.
         */
        @Test
        @DisplayName("round-trip write and read preserves all field values exactly")
        void roundTripPreservesAllFields(@TempDir Path tempDir) throws Exception {
            var metadataPath = tempDir.resolve("feature-vcs.metadata");
            var now = LocalDateTime.now();
            var original = new BranchMetadata("feature-vcs", "a1b2c3d", BranchState.ACTIVE, "main", now, now);

            original.writeTo(metadataPath);
            var loaded = BranchMetadata.readFrom(metadataPath);

            assertEquals(original.getName(), loaded.getName());
            assertEquals(original.getUid(), loaded.getUid());
            assertEquals(original.getState(), loaded.getState());
            assertEquals(original.getParent(), loaded.getParent());
            assertEquals(original.getCreatedAt().withNano(0), loaded.getCreatedAt().withNano(0));
            assertEquals(original.getLastModified().withNano(0), loaded.getLastModified().withNano(0));
        }

        /**
         * Verifies that {@link BranchMetadata#writeTo} creates the parent directory hierarchy automatically if it does not exist.
         * This is important because the metadata directory {@code .vitruvius/branches/} is not guaranteed to exist before the first branch is created.
         */
        @Test
        @DisplayName("creates parent directories automatically if they do not exist")
        void createsParentDirectories(@TempDir Path tempDir) throws Exception {
            // use a nested path that does not yet exist on disk.
            var metadataPath = tempDir.resolve("nested/directory/feature-vcs.metadata");
            var now = LocalDateTime.now();
            var metadata = new BranchMetadata("feature-vcs", "a1b2c3d", BranchState.ACTIVE, "main", now, now);

            // writeTo must succeed without the caller creating the parent directories first.
            assertDoesNotThrow(() -> metadata.writeTo(metadataPath));
            assertTrue(Files.exists(metadataPath), "metadata file must exist after write with missing parent directories");
        }

        /**
         * Verifies that reading from a file with a missing required key raises an {@link IllegalArgumentException} and that the error message identifies the missing key.
         */
        @Test
        @DisplayName("throws an exception identifying the missing key when a required field is absent")
        void throwsWhenKeyMissing(@TempDir Path tempDir) throws Exception {
            var metadataPath = tempDir.resolve("feature-vcs.metadata");
            // write a file that is missing the 'parent' field to simulate a corrupted or
            // manually edited metadata file.
            Files.write(metadataPath, List.of("name=feature-vcs", "uid=a1b2c3d", "state=ACTIVE", "createdAt=2026-02-01T10:30:00", "updatedAt=2026-02-01T10:30:00"));

            var exception = assertThrows(IllegalArgumentException.class, () -> BranchMetadata.readFrom(metadataPath));
            assertTrue(exception.getMessage().contains("parent"), "the error message must name the missing key so the developer can locate it");
        }

        /**
         * Verifies that attempting to read from a file that does not exist throws an {@link java.io.IOException}.
         * Callers must check file existence before calling {@link BranchMetadata#readFrom}.
         */
        @Test
        @DisplayName("throws an exception when the metadata file does not exist")
        void throwsWhenFileNotFound(@TempDir Path tempDir) {
            var nonExistentPath = tempDir.resolve("nonexistent.metadata");

            assertThrows(java.io.IOException.class, () -> BranchMetadata.readFrom(nonExistentPath));
        }

        /**
         * Verifies that a file containing an unrecognized state value causes an {@link IllegalArgumentException} during parsing.
         */
        @Test
        @DisplayName("throws an exception when the state value is not a recognized enum constant")
        void throwsWhenStateInvalid(@TempDir Path tempDir) throws Exception {
            var metadataPath = tempDir.resolve("feature-vcs.metadata");
            Files.write(metadataPath, List.of("name=feature-vcs", "uid=a1b2c3d", "state=UNKNOWN_STATE", "parent=main", "createdAt=2026-02-01T10:30:00", "updatedAt=2026-02-01T10:30:00"));

            assertThrows(IllegalArgumentException.class, () -> BranchMetadata.readFrom(metadataPath));
        }

        /**
         * Verifies that a file containing a malformed timestamp value causes a {@link java.time.format.DateTimeParseException} during parsing.
         * Timestamp must conform to the ISO local date-time format used by {@link BranchMetadata#writeTo(Path)}.
         */
        @Test
        @DisplayName("throws an exception when a timestamp field cannot be parsed")
        void throwsWhenTimestampInvalid(@TempDir Path tempDir) throws Exception {
            var metadataPath = tempDir.resolve("feature-vcs.metadata");
            Files.write(metadataPath, List.of("name=feature-vcs", "uid=a1b2c3d", "state=ACTIVE", "parent=main", "createdAt=not-a-valid-timestamp", "updatedAt=2026-02-01T10:30:00"));

            assertThrows(java.time.format.DateTimeParseException.class, () -> BranchMetadata.readFrom(metadataPath));
        }
    }
}