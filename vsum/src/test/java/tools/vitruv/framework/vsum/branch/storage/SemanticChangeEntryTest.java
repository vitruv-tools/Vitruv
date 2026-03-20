package tools.vitruv.framework.vsum.branch.storage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link SemanticChangeEntry} and its {@link SemanticChangeEntry.Builder}.
 *
 * <p>Tests cover the builder pattern, the auto-population of {@code changeDescription} from the
 * {@link SemanticChangeType}, the default value for {@code position}, the immutability contract,
 * and the {@code equals}/{@code hashCode}/{@code toString} contracts.
 */
class SemanticChangeEntryTest {

    /**
     * Verifies that the builder stores all explicitly set fields and that the {@code changeDescription}
     * is automatically derived from the {@code changeType} without the caller having to set it.
     * This is the central contract of the builder: callers must never set {@code changeDescription}
     * manually - it is always consistent with {@code changeType}.
     */
    @Test
    @DisplayName("Builder stores all set fields and auto-populates changeDescription from changeType")
    void builderStoresFieldsAndAutoPopulatesDescription() {
        var entry = SemanticChangeEntry.builder()
                .index(3)
                .changeType(SemanticChangeType.ATTRIBUTE_CHANGED)
                .emfType("ReplaceSingleValuedEAttribute")
                .elementUuid("uuid-abc-123")
                .eClass("entities::Entity")
                .feature("name")
                .from("oldName")
                .to("newName")
                .build();

        assertEquals(3, entry.getIndex());
        assertEquals(SemanticChangeType.ATTRIBUTE_CHANGED, entry.getChangeType());
        // changeDescription must be sourced from SemanticChangeType.getDescription(), not set manually
        assertEquals(SemanticChangeType.ATTRIBUTE_CHANGED.getDescription(), entry.getChangeDescription(), "changeDescription must be auto-populated from the changeType's description");
        assertEquals("ReplaceSingleValuedEAttribute", entry.getEmfType());
        assertEquals("uuid-abc-123", entry.getElementUuid());
        assertEquals("entities::Entity", entry.getEClass());
        assertEquals("name", entry.getFeature());
        assertEquals("oldName", entry.getFrom());
        assertEquals("newName", entry.getTo());
    }

    /**
     * Verifies that the {@code position} field defaults to {@code -1} when not set via the builder.
     * The value {@code -1} is the agreed sentinel meaning "not applicable" - it must never be
     * a valid list index, which is always non-negative.
     */
    @Test
    @DisplayName("position defaults to -1 when not set by the caller")
    void positionDefaultsToMinusOne() {
        var entry = SemanticChangeEntry.builder()
                .index(0)
                .changeType(SemanticChangeType.ELEMENT_CREATED)
                .emfType("CreateEObject")
                .build();
        assertEquals(-1, entry.getPosition(), "position must default to -1 for changes that do not involve a list index");
    }

    /**
     * Verifies that a valid position is stored when the caller sets it explicitly.
     * Positions are used for multi-valued attribute and reference changes to indicate where in
     * the list the value was inserted or removed.
     */
    @Test
    @DisplayName("Explicitly set position is stored correctly")
    void explicitPositionIsStored() {
        var entry = SemanticChangeEntry.builder()
                .index(0)
                .changeType(SemanticChangeType.ATTRIBUTE_VALUE_INSERTED)
                .emfType("InsertEAttributeValue")
                .position(2)
                .build();
        assertEquals(2, entry.getPosition());
    }

    /**
     * Verifies that optional fields ({@code elementUuid}, {@code eClass}, {@code feature},
     * {@code from}, {@code to}, {@code referencedElementUuid}) default to {@code null} when not
     * set. These fields are null for lifecycle changes such as element creation and deletion.
     */
    @Test
    @DisplayName("Optional fields are null when not set by the builder")
    void optionalFieldsDefaultToNull() {
        var entry = SemanticChangeEntry.builder()
                .index(0)
                .changeType(SemanticChangeType.ELEMENT_CREATED)
                .emfType("CreateEObject")
                .build();

        assertNull(entry.getElementUuid(), "elementUuid must default to null");
        assertNull(entry.getEClass(), "eClass must default to null");
        assertNull(entry.getFeature(), "feature must default to null for lifecycle changes");
        assertNull(entry.getFrom(), "from must default to null");
        assertNull(entry.getTo(), "to must default to null");
        assertNull(entry.getReferencedElementUuid(), "referencedElementUuid must default to null");
    }

    /**
     * Verifies that the builder rejects a null {@code changeType}, since the type is required to
     * auto-populate {@code changeDescription} and is the primary key for conflict classification.
     */
    @Test
    @DisplayName("Builder throws when changeType is null")
    void builderThrowsOnNullChangeType() {
        assertThrows(NullPointerException.class, () ->
                SemanticChangeEntry.builder()
                        .index(0)
                        .changeType(null)
                        .emfType("SomeType")
                        .build(), "null changeType must be rejected because changeDescription cannot be derived");
    }

    /**
     * Verifies that the builder rejects a null {@code emfType}, which is required for debugging
     * and for tooling that works directly with the Vitruv change model.
     */
    @Test
    @DisplayName("Builder throws when emfType is null")
    void builderThrowsOnNullEmfType() {
        assertThrows(NullPointerException.class, () ->
                SemanticChangeEntry.builder()
                        .index(0)
                        .changeType(SemanticChangeType.ELEMENT_CREATED)
                        .emfType(null)
                        .build(), "null emfType must be rejected");
    }

    /**
     * Verifies that the {@code changeDescription} is always consistent with the {@code changeType}
     * across all 13 change categories. This is a bulk verification: if a new constant is added to
     * {@link SemanticChangeType} without a corresponding description, this test catches it.
     */
    @Test
    @DisplayName("changeDescription matches changeType.getDescription() for all 13 categories")
    void changeDescriptionMatchesTypeDescriptionForAllCategories() {
        for (SemanticChangeType type : SemanticChangeType.values()) {
            var entry = SemanticChangeEntry.builder()
                    .index(0)
                    .changeType(type)
                    .emfType("SomeEMFType")
                    .build();

            assertEquals(type.getDescription(), entry.getChangeDescription(), "changeDescription must equal changeType.getDescription() for " + type.name());
        }
    }

    /**
     * Verifies that two entries built with identical fields are equal and produce the same hash code.
     * Entries that differ in any field must not be equal.
     * This contract is required because entries are compared during conflict detection: two entries
     * from different branches are compared by value to detect identical or conflicting changes.
     */
    @Test
    @DisplayName("Equal entries compare as equal and produce the same hash code")
    void equalsAndHashCodeContract() {
        var entry1 = SemanticChangeEntry.builder()
                .index(0)
                .changeType(SemanticChangeType.ATTRIBUTE_CHANGED)
                .emfType("ReplaceSingleValuedEAttribute")
                .elementUuid("uuid-001")
                .feature("name")
                .from("A")
                .to("B")
                .build();

        var entry2 = SemanticChangeEntry.builder()
                .index(0)
                .changeType(SemanticChangeType.ATTRIBUTE_CHANGED)
                .emfType("ReplaceSingleValuedEAttribute")
                .elementUuid("uuid-001")
                .feature("name")
                .from("A")
                .to("B")
                .build();

        var different = SemanticChangeEntry.builder()
                .index(0)
                .changeType(SemanticChangeType.ATTRIBUTE_CHANGED)
                .emfType("ReplaceSingleValuedEAttribute")
                .elementUuid("uuid-002")   // different UUID
                .feature("name")
                .from("A")
                .to("B")
                .build();

        assertEquals(entry1, entry2, "entries with identical fields must be equal");
        assertNotEquals(entry1, different, "entries with different elementUuid must not be equal");
        assertEquals(entry1.hashCode(), entry2.hashCode(), "equal entries must produce the same hash code");
    }

    /**
     * Verifies that entries differing only in {@code feature} are not equal, since the conflict-
     * detection key is the {@code (elementUuid, feature)} pair and two changes to different
     * features on the same element must be treated as independent.
     */
    @Test
    @DisplayName("Entries differing only in feature are not equal")
    void entriesDifferingInFeatureAreNotEqual() {
        var entry1 = SemanticChangeEntry.builder()
                .index(0).changeType(SemanticChangeType.ATTRIBUTE_CHANGED)
                .emfType("ReplaceSingleValuedEAttribute")
                .elementUuid("uuid-001").feature("name").build();

        var entry2 = SemanticChangeEntry.builder()
                .index(0).changeType(SemanticChangeType.ATTRIBUTE_CHANGED)
                .emfType("ReplaceSingleValuedEAttribute")
                .elementUuid("uuid-001").feature("value").build();

        assertNotEquals(entry1, entry2, "entries with different features must not be equal even when elementUuid matches");
    }

    /**
     * Verifies that {@link SemanticChangeEntry#toString()} includes the fields most useful for
     * debugging: the index, changeType, elementUuid, feature, from, and to values.
     */
    @Test
    @DisplayName("toString includes index, changeType, elementUuid, feature, from, and to")
    void toStringIncludesKeyFields() {
        var entry = SemanticChangeEntry.builder()
                .index(5)
                .changeType(SemanticChangeType.ATTRIBUTE_CHANGED)
                .emfType("ReplaceSingleValuedEAttribute")
                .elementUuid("uuid-xyz")
                .feature("status")
                .from("DRAFT")
                .to("PUBLISHED")
                .build();

        String str = entry.toString();
        assertTrue(str.contains("5"), "toString must include the index");
        assertTrue(str.contains("ATTRIBUTE_CHANGED"), "toString must include the changeType name");
        assertTrue(str.contains("uuid-xyz"), "toString must include the elementUuid");
        assertTrue(str.contains("status"), "toString must include the feature name");
        assertTrue(str.contains("DRAFT"), "toString must include the from value");
        assertTrue(str.contains("PUBLISHED"), "toString must include the to value");
    }
}
