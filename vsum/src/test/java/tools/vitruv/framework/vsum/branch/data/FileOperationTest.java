package tools.vitruv.framework.vsum.branch.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link FileOperation}.
 */
class FileOperationTest {

    @Test
    @DisplayName("verify whether all operations on file are represented.")
    void testAllOperationsExist() {
        assertEquals(5, FileOperation.values().length);
        assertNotNull(FileOperation.ADDED);
        assertNotNull(FileOperation.MODIFIED);
        assertNotNull(FileOperation.DELETED);
        assertNotNull(FileOperation.RENAMED);
        assertNotNull(FileOperation.COPIED);
    }

    @Test
    void testToString() {
        assertEquals("ADDED", FileOperation.ADDED.toString());
        assertEquals("MODIFIED", FileOperation.MODIFIED.toString());
        assertEquals("DELETED", FileOperation.DELETED.toString());
        assertEquals("RENAMED", FileOperation.RENAMED.toString());
        assertEquals("COPIED", FileOperation.COPIED.toString());
    }

    @Test
    void testValueOf() {
        assertEquals(FileOperation.ADDED, FileOperation.valueOf("ADDED"));
        assertEquals(FileOperation.MODIFIED, FileOperation.valueOf("MODIFIED"));
        assertEquals(FileOperation.DELETED, FileOperation.valueOf("DELETED"));
        assertEquals(FileOperation.RENAMED, FileOperation.valueOf("RENAMED"));
        assertEquals(FileOperation.COPIED, FileOperation.valueOf("COPIED"));
    }

    @Test
    void testInvalidValueOf() {
        assertThrows(IllegalArgumentException.class, () -> {FileOperation.valueOf("INVALID");});
    }
}