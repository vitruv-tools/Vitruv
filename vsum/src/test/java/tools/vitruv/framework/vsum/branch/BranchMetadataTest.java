package tools.vitruv.framework.vsum.branch;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link BranchMetadata}.
 */
class BranchMetadataTest {

    @Nested
    @DisplayName("construction")
    class Construction {
        @Test
        @DisplayName("creates metadata with all fields")
        void createsWithAllFields() {
            var now = LocalDateTime.now();
            var metadata = new BranchMetadata("feature-test", "a1b2c3d", BranchState.ACTIVE, "main", now, now);
            assertEquals("feature-test", metadata.getName());
            assertEquals("a1b2c3d", metadata.getUid());
            assertEquals(BranchState.ACTIVE, metadata.getState());
            assertEquals("main", metadata.getParent());
            assertEquals(now, metadata.getCreatedAt());
            assertEquals(now, metadata.getUpdatedAt());
        }

        @Test
        @DisplayName("rejects null name")
        void rejectsNullName() {
            var now = LocalDateTime.now();
            assertThrows(NullPointerException.class, () -> new BranchMetadata(null, "uid", BranchState.ACTIVE, "main", now, now));
        }

        @Test
        @DisplayName("rejects null uid")
        void rejectsNullUid() {
            var now = LocalDateTime.now();
            assertThrows(NullPointerException.class, () -> new BranchMetadata("name", null, BranchState.ACTIVE, "main", now, now));
        }

        @Test
        @DisplayName("rejects null state")
        void rejectsNullState() {
            var now = LocalDateTime.now();
            assertThrows(NullPointerException.class, () -> new BranchMetadata("name", "uid", null, "main", now, now));
        }
    }

    @Nested
    @DisplayName("setState")
    class SetState {
        @Test
        @DisplayName("updates state and timestamp")
        void updatesStateAndTimestamp() throws InterruptedException {
            var createdAt = LocalDateTime.now();
            var metadata = new BranchMetadata("feature-test", "a1b2c3d", BranchState.ACTIVE, "main", createdAt, createdAt);

            // sleep to have different timestamp for last updated
            Thread.sleep(10);
            var beforeUpdate = LocalDateTime.now();
            metadata.setState(BranchState.DELETED);
            var afterUpdate = LocalDateTime.now();
            assertEquals(BranchState.DELETED, metadata.getState());
            assertTrue(metadata.getUpdatedAt().isAfter(createdAt), "Updated timestamp should be after creation timestamp");
            assertFalse(metadata.getUpdatedAt().isBefore(beforeUpdate), "Updated timestamp should be at or after setState call");
            assertFalse(metadata.getUpdatedAt().isAfter(afterUpdate), "Updated timestamp should be at or before setState return");
        }

        @Test
        @DisplayName("rejects null state")
        void rejectsNullState() {
            var now = LocalDateTime.now();
            var metadata = new BranchMetadata("feature-test", "a1b2c3d", BranchState.ACTIVE, "main", now, now);
            assertThrows(NullPointerException.class, () -> metadata.setState(null));
        }
    }

    @Nested
    @DisplayName("file serialization")
    class FileSerialization {
        @Test
        @DisplayName("writes metadata to file")
        void writesMetadataToFile(@TempDir Path tempDir) throws Exception {
            var metadataPath = tempDir.resolve("test.metadata");
            var now = LocalDateTime.now();

            var metadata = new BranchMetadata("feature-test", "a1b2c3d", BranchState.ACTIVE, "main", now, now);
            metadata.writeTo(metadataPath);
            assertTrue(Files.exists(metadataPath), "Metadata file should exist");

            var lines = Files.readAllLines(metadataPath);
            assertTrue(lines.stream().anyMatch(l -> l.startsWith("name=feature-test")));
            assertTrue(lines.stream().anyMatch(l -> l.startsWith("uid=a1b2c3d")));
            assertTrue(lines.stream().anyMatch(l -> l.startsWith("state=ACTIVE")));
            assertTrue(lines.stream().anyMatch(l -> l.startsWith("parent=main")));
            assertTrue(lines.stream().anyMatch(l -> l.startsWith("createdAt=")));
            assertTrue(lines.stream().anyMatch(l -> l.startsWith("updatedAt=")));
        }

        @Test
        @DisplayName("creates parent directories if needed")
        void createsParentDirectories(@TempDir Path tempDir) throws Exception {
            var metadataPath = tempDir.resolve("nested/dir/test.metadata");
            var now = LocalDateTime.now();
            var metadata = new BranchMetadata("feature-test", "a1b2c3d", BranchState.ACTIVE, "main", now, now);
            metadata.writeTo(metadataPath);
            assertTrue(Files.exists(metadataPath), "Metadata file should exist");
            assertTrue(Files.isDirectory(metadataPath.getParent()), "Parent directory should exist");
        }

        @Test
        @DisplayName("reads metadata from file")
        void readsMetadataFromFile(@TempDir Path tempDir) throws Exception {
            var metadataPath = tempDir.resolve("test.metadata");

            // Write metadata
            var now = LocalDateTime.now();
            var original = new BranchMetadata("feature-test", "a1b2c3d", BranchState.ACTIVE, "main", now, now);
            original.writeTo(metadataPath);

            // Read it back
            var loaded = BranchMetadata.readFrom(metadataPath);

            assertEquals(original.getName(), loaded.getName());
            assertEquals(original.getUid(), loaded.getUid());
            assertEquals(original.getState(), loaded.getState());
            assertEquals(original.getParent(), loaded.getParent());
            assertEquals(original.getCreatedAt(), loaded.getCreatedAt());
            assertEquals(original.getUpdatedAt(), loaded.getUpdatedAt());
        }

        @Test
        @DisplayName("round-trip preserves all data")
        void roundTripPreservesData(@TempDir Path tempDir) throws Exception {
            var metadataPath = tempDir.resolve("test.metadata");
            var createdAt = LocalDateTime.of(2026, 1, 31, 10, 30, 0);
            var updatedAt = LocalDateTime.of(2026, 2, 1, 14, 15, 30);
            var original = new BranchMetadata("feature-authentication", "9f3a2b1", BranchState.MERGED, "develop", createdAt, updatedAt);

            // Write and read
            original.writeTo(metadataPath);
            var loaded = BranchMetadata.readFrom(metadataPath);

            assertEquals("feature-authentication", loaded.getName());
            assertEquals("9f3a2b1", loaded.getUid());
            assertEquals(BranchState.MERGED, loaded.getState());
            assertEquals("develop", loaded.getParent());
            assertEquals(createdAt, loaded.getCreatedAt());
            assertEquals(updatedAt, loaded.getUpdatedAt());
        }

        @Test
        @DisplayName("handles values containing equals sign")
        void handlesEqualsInValues(@TempDir Path tempDir) throws Exception {
            var metadataPath = tempDir.resolve("test.metadata");
            var now = LocalDateTime.now();
            // Branch name with equals sign (unusual but valid)
            var original = new BranchMetadata("fix=issue", "a1b2c3d", BranchState.ACTIVE, "main", now, now);
            original.writeTo(metadataPath);
            var loaded = BranchMetadata.readFrom(metadataPath);
            assertEquals("fix=issue", loaded.getName());
        }

        @Test
        @DisplayName("ignores unknown keys for forward compatibility")
        void ignoresUnknownKeys(@TempDir Path tempDir) throws Exception {
            var metadataPath = tempDir.resolve("test.metadata");
            var now = LocalDateTime.now();

            // Write standard metadata
            var metadata = new BranchMetadata("feature-test", "a1b2c3d", BranchState.ACTIVE, "main", now, now);
            metadata.writeTo(metadataPath);

            // Add a future field manually
            var lines = Files.readAllLines(metadataPath);
            lines.add("futureField=someValue");
            Files.write(metadataPath, lines);

            // Should still load successfully (ignoring unknown key)
            var loaded = BranchMetadata.readFrom(metadataPath);
            assertEquals("feature-test", loaded.getName());
        }

        @Test
        @DisplayName("throws exception when required key is missing")
        void throwsWhenKeyMissing(@TempDir Path tempDir) throws Exception {
            var metadataPath = tempDir.resolve("test.metadata");
            // Write incomplete metadata (missing 'parent')
            var lines = java.util.List.of("name=feature-test", "uid=a1b2c3d", "state=ACTIVE", "createdAt=2026-02-01T10:30:00", "updatedAt=2026-02-01T10:30:00");
            Files.write(metadataPath, lines);
            var exception = assertThrows(IllegalArgumentException.class, () -> BranchMetadata.readFrom(metadataPath));
            assertTrue(exception.getMessage().contains("parent"));
        }

        @Test
        @DisplayName("throws exception when file does not exist")
        void throwsWhenFileNotFound(@TempDir Path tempDir) {
            var nonExistentPath = tempDir.resolve("nonexistent.metadata");
            assertThrows(java.io.IOException.class, () -> BranchMetadata.readFrom(nonExistentPath));
        }

        @Test
        @DisplayName("throws exception when state value is invalid")
        void throwsWhenStateInvalid(@TempDir Path tempDir) throws Exception {
            var metadataPath = tempDir.resolve("test.metadata");
            var lines = java.util.List.of("name=feature-test", "uid=a1b2c3d", "state=INVALID_STATE", "parent=main", "createdAt=2026-02-01T10:30:00", "updatedAt=2026-02-01T10:30:00");
            Files.write(metadataPath, lines);
            assertThrows(IllegalArgumentException.class, () -> BranchMetadata.readFrom(metadataPath));
        }

        @Test
        @DisplayName("throws exception when timestamp format is invalid")
        void throwsWhenTimestampInvalid(@TempDir Path tempDir) throws Exception {
            var metadataPath = tempDir.resolve("test.metadata");
            var lines = java.util.List.of("name=feature-test", "uid=a1b2c3d", "state=ACTIVE", "parent=main", "createdAt=invalid-timestamp", "updatedAt=2026-02-01T10:30:00");
            Files.write(metadataPath, lines);
            assertThrows(java.time.format.DateTimeParseException.class, () -> BranchMetadata.readFrom(metadataPath));
        }
    }
}