package tools.vitruv.framework.vsum.branch.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ConflictSeverity}.
 *
 * <p>Tests verify the {@link ConflictSeverity#fromLostUpdateCount(int)} thresholds
 * and that all four enum constants are distinct.
 */
class ConflictSeverityTest {

    @Test
    @DisplayName("fromLostUpdateCount returns LOW when no updates would be lost")
    void zeroLostUpdatesIsLow() {
        assertEquals(ConflictSeverity.LOW, ConflictSeverity.fromLostUpdateCount(0));
    }

    @Test
    @DisplayName("fromLostUpdateCount returns MEDIUM for 1 and 2 lost updates")
    void oneOrTwoLostUpdatesIsMedium() {
        assertEquals(ConflictSeverity.MEDIUM, ConflictSeverity.fromLostUpdateCount(1));
        assertEquals(ConflictSeverity.MEDIUM, ConflictSeverity.fromLostUpdateCount(2));
    }

    @Test
    @DisplayName("fromLostUpdateCount returns HIGH for 3 through 9 lost updates")
    void threeToNineLostUpdatesIsHigh() {
        assertEquals(ConflictSeverity.HIGH, ConflictSeverity.fromLostUpdateCount(3));
        assertEquals(ConflictSeverity.HIGH, ConflictSeverity.fromLostUpdateCount(9));
    }

    @Test
    @DisplayName("fromLostUpdateCount returns CRITICAL at and above 10 lost updates")
    void tenOrMoreLostUpdatesIsCritical() {
        assertEquals(ConflictSeverity.CRITICAL, ConflictSeverity.fromLostUpdateCount(10));
        assertEquals(ConflictSeverity.CRITICAL, ConflictSeverity.fromLostUpdateCount(100));
    }

    @Test
    @DisplayName("All four severity levels are distinct enum constants")
    void allFourLevelsAreDistinct() {
        var levels = ConflictSeverity.values();
        assertEquals(4, levels.length, "exactly four severity levels must exist");
        assertNotEquals(ConflictSeverity.LOW, ConflictSeverity.MEDIUM);
        assertNotEquals(ConflictSeverity.MEDIUM, ConflictSeverity.HIGH);
        assertNotEquals(ConflictSeverity.HIGH, ConflictSeverity.CRITICAL);
    }
}
