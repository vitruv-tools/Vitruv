package tools.vitruv.framework.vsum.branch.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link FileOperation}.
 *
 * <p>Because {@link FileOperation} is a pure enum with no behavior,
 * the tests focus on the contract that other classes depend on:
 * the exact set of declared constants and their string representation.
 */
class FileOperationTest {

    /**
     * Verifies that exactly the five expected operation constants are declared and
     * that each constant can be round-tripped through {@link FileOperation#valueOf(String)}.
     * This protects against accidentally adding or removing a constant,
     * and confirms that the string names used in serialized changelogs
     * and Git status code mapping match the declared enum values.
     */
    @Test
    @DisplayName("All five expected operation constants are declared and round-trip through valueOf")
    void allExpectedConstantsDeclaredAndRoundTrip() {
        // the exact count matters because other classes (such as FileChange validation and changelog serialization)
        // depend on the specific set of operation types.
        assertEquals(5, FileOperation.values().length,
                "exactly five operation constants must be declared");

        // verify each constant by name so that any rename is caught immediately.
        assertAll(
                () -> assertEquals(FileOperation.ADDED,    FileOperation.valueOf("ADDED")),
                () -> assertEquals(FileOperation.MODIFIED, FileOperation.valueOf("MODIFIED")),
                () -> assertEquals(FileOperation.DELETED,  FileOperation.valueOf("DELETED")),
                () -> assertEquals(FileOperation.RENAMED,  FileOperation.valueOf("RENAMED")),
                () -> assertEquals(FileOperation.COPIED,   FileOperation.valueOf("COPIED"))
        );
    }

    /**
     * Verifies that {@link FileOperation#toString()} returns the constant name in upper case.
     * The string representation is used when writing operation names to changelog files,
     * so the exact format must be stable and predictable.
     */
    @Test
    @DisplayName("toString returns the upper-case constant name for all operations")
    void toStringReturnsConstantName() {
        // enum toString() returns name() by default, which is the declared identifier.
        assertEquals("ADDED",    FileOperation.ADDED.toString());
        assertEquals("MODIFIED", FileOperation.MODIFIED.toString());
        assertEquals("DELETED",  FileOperation.DELETED.toString());
        assertEquals("RENAMED",  FileOperation.RENAMED.toString());
        assertEquals("COPIED",   FileOperation.COPIED.toString());
    }

    /**
     * Verifies that {@link FileOperation#valueOf(String)} throws
     * an {@link IllegalArgumentException} for an unrecognized name.
     * This ensures that changelog files containing an unknown operation type are rejected
     * at parse time rather than silently producing a null or default value.
     */
    @Test
    @DisplayName("valueOf throws an exception for an unrecognized operation name")
    void valueOfThrowsForUnknownName() {
        // an unrecognized name such as one that might appear in a manually edited changelog file
        // must be rejected immediately with a clear exception.
        assertThrows(IllegalArgumentException.class, () -> FileOperation.valueOf("INVALID"));
    }
}