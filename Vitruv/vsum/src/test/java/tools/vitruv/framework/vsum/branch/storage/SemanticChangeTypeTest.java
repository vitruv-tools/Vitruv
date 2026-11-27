package tools.vitruv.framework.vsum.branch.storage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link SemanticChangeType}.
 *
 * <p>Verifies the completeness of the enum (all 15 categories must be present), that every
 * constant carries a non-empty human-readable description, and that the description is different
 * from the enum name so that readers unfamiliar with the EMF change model receive genuinely
 * helpful text rather than a raw technical identifier.
 */
class SemanticChangeTypeTest {

    /**
     * Verifies that all 15 expected change categories are present.
     * Adding or removing a constant without updating this test is intentional - it forces
     * reviewers to confirm that the conflict-detection logic is still consistent with the enum.
     */
    @Test
    @DisplayName("Enum defines exactly 15 change categories")
    void enumDefinesExactly15Categories() {
        assertEquals(15, SemanticChangeType.values().length,
                "The number of SemanticChangeType constants must be 15. " +
                        "If you added or removed a constant, update this test and review ConflictClassifier.");
    }

    /**
     * Verifies that all expected constant names are present so that renaming a constant is
     * caught immediately rather than silently breaking changelog readers or conflict detection.
     */
    @Test
    @DisplayName("All expected constant names are present")
    void allExpectedConstantNamesPresent() {
        Set<String> names = Arrays.stream(SemanticChangeType.values())
                .map(Enum::name)
                .collect(Collectors.toSet());

        assertTrue(names.contains("ELEMENT_CREATED"));
        assertTrue(names.contains("ELEMENT_DELETED"));
        assertTrue(names.contains("ATTRIBUTE_SET"));
        assertTrue(names.contains("ATTRIBUTE_CHANGED"));
        assertTrue(names.contains("ATTRIBUTE_CLEARED"));
        assertTrue(names.contains("ATTRIBUTE_VALUE_INSERTED"));
        assertTrue(names.contains("ATTRIBUTE_VALUE_REMOVED"));
        assertTrue(names.contains("REFERENCE_SET"));
        assertTrue(names.contains("REFERENCE_CHANGED"));
        assertTrue(names.contains("REFERENCE_CLEARED"));
        assertTrue(names.contains("REFERENCE_VALUE_INSERTED"));
        assertTrue(names.contains("REFERENCE_VALUE_REMOVED"));
        assertTrue(names.contains("ELEMENT_REORDERED"));
        assertTrue(names.contains("ROOT_INSERTED"));
        assertTrue(names.contains("ROOT_REMOVED"));
    }

    /**
     * Verifies that every constant has a non-null, non-blank description.
     * The description is written to the JSON changelog for human readers, so an empty or null
     * description would degrade the usefulness of the changelog.
     */
    @Test
    @DisplayName("Every constant has a non-null non-blank description")
    void everyConstantHasNonBlankDescription() {
        for (SemanticChangeType type : SemanticChangeType.values()) {
            assertNotNull(type.getDescription(),
                    type.name() + " must have a non-null description");
            assertFalse(type.getDescription().isBlank(),
                    type.name() + " must have a non-blank description");
        }
    }

    /**
     * Verifies that the description text is actually human-readable by checking that it is not
     * just the enum name repeated in a different case. The description should explain the change
     * to someone unfamiliar with the EMF change model.
     */
    @Test
    @DisplayName("Descriptions are human-readable and differ from the enum name")
    void descriptionsAreDifferentFromEnumName() {
        for (SemanticChangeType type : SemanticChangeType.values()) {
            assertNotEquals(
                    type.name().toLowerCase().replace("_", " "),
                    type.getDescription().toLowerCase(),
                    type.name() + ": description must be more informative than the enum name");
        }
    }

    /**
     * Verifies that descriptions are unique across all constants, since duplicate descriptions
     * would make it impossible for a reader to distinguish between change categories.
     */
    @Test
    @DisplayName("All descriptions are unique")
    void allDescriptionsAreUnique() {
        long distinctCount = Arrays.stream(SemanticChangeType.values())
                .map(SemanticChangeType::getDescription)
                .distinct()
                .count();
        assertEquals(SemanticChangeType.values().length, distinctCount,
                "Every SemanticChangeType constant must have a unique description");
    }

    /**
     * Verifies that {@code valueOf} round-trips correctly for each constant, confirming that
     * the constants are accessible by name (which is how they are deserialized from JSON).
     */
    @Test
    @DisplayName("valueOf round-trips for every constant")
    void valueOfRoundTripsForEveryConstant() {
        for (SemanticChangeType type : SemanticChangeType.values()) {
            assertSame(type, SemanticChangeType.valueOf(type.name()),
                    "valueOf must return the same constant for name '" + type.name() + "'");
        }
    }
}
