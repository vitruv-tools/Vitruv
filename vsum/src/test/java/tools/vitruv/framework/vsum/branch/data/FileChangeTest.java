package tools.vitruv.framework.vsum.branch.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link FileChange}.
 */
class FileChangeTest {

    @Test
    @DisplayName("simple constructor")
    void testSimpleConstructor() {
        FileChange change = new FileChange("models/User.java", FileOperation.MODIFIED);
        assertEquals("models/User.java", change.getFilePath());
        assertEquals(FileOperation.MODIFIED, change.getOperation());
        assertNull(change.getOldPath());
        assertTrue(change.getElementChanges().isEmpty());
    }

    @Test
    @DisplayName("constructor for renamed file")
    void testRenamedConstructor() {
        FileChange change = new FileChange("models/NewUser.java", FileOperation.RENAMED, "models/User.java");
        assertEquals("models/NewUser.java", change.getFilePath());
        assertEquals(FileOperation.RENAMED, change.getOperation());
        assertEquals("models/User.java", change.getOldPath());
        assertTrue(change.isRenamed());
    }

    @Test
    @DisplayName("constructor with all params required")
    void testFullConstructor() {
        List<String> elementChanges = List.of("change1", "change2");
        FileChange change = new FileChange("models/User.java", FileOperation.MODIFIED, null, elementChanges);
        assertEquals("models/User.java", change.getFilePath());
        assertEquals(FileOperation.MODIFIED, change.getOperation());
        assertNull(change.getOldPath());
        assertEquals(2, change.getElementChanges().size());
        assertTrue(change.hasElementChanges());
    }
    @Test
    @DisplayName("oldPath should only be set for RENAMED operation")
    void testValidation_OldPathWithoutRenamed() {
        assertThrows(IllegalArgumentException.class, () -> {new FileChange("models/User.java", FileOperation.MODIFIED, "models/Old.java");});
    }

    @Test
    @DisplayName("RENAMED operation should have oldPath")
    void testValidation_RenamedWithoutOldPath() {
        assertThrows(IllegalArgumentException.class, () -> {new FileChange("models/User.java", FileOperation.RENAMED, null);});
    }

    @Test
    @DisplayName("test whether file was renamed and/or have element changes")
    void isRenamedOrHaveChanges() {
        FileChange renamed = new FileChange("models/NewUser.java", FileOperation.RENAMED, "models/User.java");
        assertTrue(renamed.isRenamed());

        FileChange modified = new FileChange("models/User.java", FileOperation.MODIFIED);
        assertFalse(modified.isRenamed());

        FileChange withChanges = new FileChange("models/User.java", FileOperation.MODIFIED, null, List.of("change1"));
        assertTrue(withChanges.hasElementChanges());

        FileChange withoutChanges = new FileChange("models/User.java", FileOperation.MODIFIED);
        assertFalse(withoutChanges.hasElementChanges());
    }

    @Test
    void testToString() {
        FileChange modified = new FileChange("models/User.java", FileOperation.MODIFIED);
        assertEquals("MODIFIED: models/User.java", modified.toString());
        FileChange renamed = new FileChange("models/NewUser.java", FileOperation.RENAMED, "models/User.java");
        assertTrue(renamed.toString().contains("RENAMED"));
        assertTrue(renamed.toString().contains("models/NewUser.java"));
        assertTrue(renamed.toString().contains("models/User.java"));
    }

    @Test
    void testEquals() {
        FileChange change1 = new FileChange("models/User.java", FileOperation.MODIFIED);
        FileChange change2 = new FileChange("models/User.java", FileOperation.MODIFIED);
        FileChange change3 = new FileChange("models/Other.java", FileOperation.MODIFIED);
        assertEquals(change1, change2);
        assertNotEquals(change1, change3);
    }

    @Test
    void testHashCode() {
        FileChange change1 = new FileChange("models/User.java", FileOperation.MODIFIED);
        FileChange change2 = new FileChange("models/User.java", FileOperation.MODIFIED);
        assertEquals(change1.hashCode(), change2.hashCode());
    }
}