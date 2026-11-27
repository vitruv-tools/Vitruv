package tools.vitruv.framework.vsum.branch.storage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ChangeOrigin}.
 *
 * <p>Tests verify the priority ordering (ORIGINAL > CONSEQUENTIAL > UNKNOWN),
 * that every constant has a non-empty description, and that all three constants are distinct.
 */
class ChangeOriginTest {

    @Test
    @DisplayName("ORIGINAL has the highest conflict-resolution priority")
    void originalHasHighestPriority() {
        assertTrue(ChangeOrigin.ORIGINAL.getPriority() > ChangeOrigin.CONSEQUENTIAL.getPriority(),
                "ORIGINAL must outrank CONSEQUENTIAL");
        assertTrue(ChangeOrigin.ORIGINAL.getPriority() > ChangeOrigin.UNKNOWN.getPriority(),
                "ORIGINAL must outrank UNKNOWN");
    }

    @Test
    @DisplayName("CONSEQUENTIAL has higher priority than UNKNOWN")
    void consequentialOutranksUnknown() {
        assertTrue(ChangeOrigin.CONSEQUENTIAL.getPriority() > ChangeOrigin.UNKNOWN.getPriority());
    }

    @Test
    @DisplayName("Each ChangeOrigin has a non-null, non-empty description")
    void allOriginsHaveNonEmptyDescription() {
        for (ChangeOrigin origin : ChangeOrigin.values()) {
            assertNotNull(origin.getDescription(),
                    origin + " must have a non-null description");
            assertFalse(origin.getDescription().isBlank(),
                    origin + " must have a non-empty description");
        }
    }

    @Test
    @DisplayName("All three ChangeOrigin constants are distinct")
    void allThreeConstantsAreDistinct() {
        assertEquals(3, ChangeOrigin.values().length, "exactly three origins must exist");
        assertNotEquals(ChangeOrigin.ORIGINAL, ChangeOrigin.CONSEQUENTIAL);
        assertNotEquals(ChangeOrigin.CONSEQUENTIAL, ChangeOrigin.UNKNOWN);
        assertNotEquals(ChangeOrigin.ORIGINAL, ChangeOrigin.UNKNOWN);
    }

    @Test
    @DisplayName("Priority values are positive integers")
    void prioritiesArePositive() {
        for (ChangeOrigin origin : ChangeOrigin.values()) {
            assertTrue(origin.getPriority() > 0,
                    origin + " must have a positive priority");
        }
    }
}
