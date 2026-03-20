package tools.vitruv.framework.vsum.branch.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeEntry;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link FileChange}.
 *
 * <p>The tests cover all three constructor overloads, the validation rules enforced at construction time,
 * the predicate methods {@link FileChange#isRenamed()} and {@link FileChange#hasElementChanges()}, and the {@code equals}/{@code hashCode}/{@code toString}
 * contracts that are relied upon when file changes are collected into sets or maps.
 */
class FileChangeTest {

    /**
     * Verifies that the two-argument constructor (file path and operation) correctly sets all fields and leaves optional fields at their default values.
     * This is the most commonly used constructor for non-rename operations.
     */
    @Test
    @DisplayName("Two-argument constructor sets file path and operation, leaves optional fields empty")
    void simpleConstructorSetsExpectedFields() {
        var change = new FileChange("models/User.java", FileOperation.MODIFIED);

        assertEquals("models/User.java", change.getFilePath());
        assertEquals(FileOperation.MODIFIED, change.getOperation());
        // oldPath and elementChanges must be absent for a simple non-rename change.
        assertNull(change.getOldPath());
        assertTrue(change.getElementChanges().isEmpty());
    }

    /**
     * Verifies that the three-argument constructor (file path, operation, old path) correctly tores the previous path,
     * which is needed to reconstruct the rename history.
     */
    @Test
    @DisplayName("Three-argument constructor stores the old path for rename operations")
    void renameConstructorStoresOldPath() {
        var change = new FileChange("models/NewUser.java", FileOperation.RENAMED, "models/User.java");

        assertEquals("models/NewUser.java", change.getFilePath());
        assertEquals(FileOperation.RENAMED, change.getOperation());
        assertEquals("models/User.java", change.getOldPath());
    }

    /**
     * Verifies that the full four-argument constructor correctly stores element-level changes and exposes them through the getter.
     * This constructor will be the primary entry point once element-level tracking is implemented.
     */
    @Test
    @DisplayName("Full constructor stores element-level changes and exposes them via getter")
    void fullConstructorStoresElementChanges() {
        var elementChanges = List.of(
                SemanticChangeEntry.builder().index(0).changeType(SemanticChangeType.ELEMENT_CREATED).emfType("CreateEObject").build(),
                SemanticChangeEntry.builder().index(1).changeType(SemanticChangeType.ATTRIBUTE_SET).emfType("ReplaceSingleValuedEAttribute").build());
        var change = new FileChange("models/User.java", FileOperation.MODIFIED, null, elementChanges);
        assertEquals(2, change.getElementChanges().size(), "all provided element changes must be stored");
        assertTrue(change.hasElementChanges());
    }

    /**
     * Verifies that the element changes list returned by the getter is unmodifiable, so that external code cannot mutate the internal state of the file change.
     */
    @Test
    @DisplayName("Element changes list is unmodifiable after construction")
    void elementChangesListIsUnmodifiable() {
        var elementChanges = List.of(SemanticChangeEntry.builder().index(0).changeType(SemanticChangeType.ELEMENT_CREATED).emfType("CreateEObject").build());
        var change = new FileChange("models/User.java", FileOperation.MODIFIED, null, elementChanges);

        assertThrows(UnsupportedOperationException.class, () -> change.getElementChanges().add(null), "the returned list must not allow external modification");
    }

    /**
     * Verifies that the validation rules enforced at construction time reject invalid combinations of operation and old path.
     * Specifically: providing an old path for a non-rename operation must fail, and omitting the old path for a rename operation must also fail.
     * These two rules together ensure that the presence of {@code oldPath} always matches the operation type exactly.
     */
    @Test
    @DisplayName("Rejects old path for non-rename operations and missing old path for rename operations")
    void validatesOldPathAndOperationConsistency() {
        // providing an old path for a non-rename operation is semantically wrong.
        assertThrows(IllegalArgumentException.class, () -> new FileChange("models/User.java", FileOperation.MODIFIED, "models/Old.java"), "old path must be rejected for non-RENAMED operations");

        // a rename without a source path cannot be used to reconstruct the file history.
        assertThrows(IllegalArgumentException.class, () -> new FileChange("models/User.java", FileOperation.RENAMED, null), "RENAMED operation without old path must be rejected");
    }

    /**
     * Verifies that {@link FileChange#isRenamed()} returns true only for rename operations and false for all other operation types.
     */
    @Test
    @DisplayName("isRenamed returns true only for RENAMED operations")
    void isRenamedReturnsTrueOnlyForRenameOperation() {
        var renamed = new FileChange("models/NewUser.java", FileOperation.RENAMED, "models/User.java");
        assertTrue(renamed.isRenamed(), "RENAMED operation must be identified as a rename");

        var modified = new FileChange("models/User.java", FileOperation.MODIFIED);
        assertFalse(modified.isRenamed(), "MODIFIED operation must not be identified as a rename");
    }

    /**
     * Verifies that {@link FileChange#hasElementChanges()} returns true when element-level changes are present and
     * false when the list is empty (which is currently always the case for changes created without explicit element changes).
     */
    @Test
    @DisplayName("hasElementChanges reflects whether element-level changes are present")
    void hasElementChangesReflectsPresenceOfChanges() {
        var withChanges = new FileChange("models/User.java", FileOperation.MODIFIED, null, List.of(SemanticChangeEntry.builder().index(0).changeType(SemanticChangeType.ELEMENT_CREATED).emfType("CreateEObject").build()));
        assertTrue(withChanges.hasElementChanges(), "a change with element details must report hasElementChanges() as true");

        var withoutChanges = new FileChange("models/User.java", FileOperation.MODIFIED);
        assertFalse(withoutChanges.hasElementChanges(), "a change without element details must report hasElementChanges() as false");
    }

    /**
     * Verifies the equals and hashCode: two file changes with identical fields must be equal and must produce the same hash code.
     * Two file changes that differ in any field must not be equal.
     * Equal objects must always have equal hash codes (the reverse is not required but tested here for simple value objects where collisions are unexpected).
     */
    @Test
    @DisplayName("Equal file changes compare as equal and produce the same hash code")
    void equalsAndHashCodeContract() {
        var change1 = new FileChange("models/User.java", FileOperation.MODIFIED);
        var change2 = new FileChange("models/User.java", FileOperation.MODIFIED);
        var different = new FileChange("models/Other.java", FileOperation.MODIFIED);

        // two instances with identical fields must be considered equal.
        assertEquals(change1, change2, "file changes with identical fields must be equal");
        assertNotEquals(change1, different, "file changes with different paths must not be equal");

        // equal objects must produce the same hash code as required by the equals contract.
        assertEquals(change1.hashCode(), change2.hashCode(), "equal file changes must have the same hash code");
        // for this simple value object, unequal objects are expected to produce different hash codes (collisions are theoretically possible but unlikely with distinct paths).
        assertNotEquals(change1.hashCode(), different.hashCode(), "file changes with different paths should produce different hash codes");
    }

    /**
     * Verifies that {@link FileChange#toString()} produces a human-readable representation that includes the operation name and the file path.
     * For rename operations, the old path must also appear so the full rename is visible in logs and changelogs.
     */
    @Test
    @DisplayName("toString includes the operation and file path, and old path for renames")
    void toStringIncludesOperationAndPaths() {
        var modified = new FileChange("models/User.java", FileOperation.MODIFIED);
        assertEquals("MODIFIED: models/User.java", modified.toString(), "toString must include the operation name and file path");

        var renamed = new FileChange("models/NewUser.java", FileOperation.RENAMED, "models/User.java");
        // for a rename, both the new and old path must appear so the rename is traceable.
        assertTrue(renamed.toString().contains("RENAMED"));
        assertTrue(renamed.toString().contains("models/NewUser.java"));
        assertTrue(renamed.toString().contains("models/User.java"), "old path must appear in toString for RENAMED operations");
    }
}