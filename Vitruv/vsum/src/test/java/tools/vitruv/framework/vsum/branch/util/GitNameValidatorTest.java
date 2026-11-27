package tools.vitruv.framework.vsum.branch.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link GitNameValidator}.
 *
 * <p>Tests cover every rule enforced by {@link GitNameValidator#validateFormat} and the
 * additional {@code @{} restriction added by {@link GitNameValidator#validateTagFormat}.
 */
class GitNameValidatorTest {

    @Test
    @DisplayName("Accepts a simple branch name")
    void acceptsSimpleName() {
        assertDoesNotThrow(() -> GitNameValidator.validateFormat("feature-x"));
    }

    @Test
    @DisplayName("Accepts a slash-separated branch name")
    void acceptsSlashSeparatedName() {
        assertDoesNotThrow(() -> GitNameValidator.validateFormat("feature/add-login"));
    }

    @Test
    @DisplayName("Accepts a dotted branch name without double-dot")
    void acceptsDottedName() {
        assertDoesNotThrow(() -> GitNameValidator.validateFormat("release-1.0"));
    }

    @Test
    @DisplayName("validateFormat throws NullPointerException for null")
    void throwsForNull() {
        assertThrows(NullPointerException.class, () -> GitNameValidator.validateFormat(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t"})
    @DisplayName("validateFormat rejects blank names")
    void rejectsBlankName(String name) {
        assertThrows(IllegalArgumentException.class, () -> GitNameValidator.validateFormat(name));
    }

    @Test
    @DisplayName("validateFormat rejects names containing '..'")
    void rejectsDoubleDot() {
        assertThrows(IllegalArgumentException.class,
                () -> GitNameValidator.validateFormat("feat..ure"));
    }

    @Test
    @DisplayName("validateFormat rejects names ending with '.lock'")
    void rejectsLockSuffix() {
        assertThrows(IllegalArgumentException.class,
                () -> GitNameValidator.validateFormat("branch.lock"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"has space", "tilde~", "caret^", "colon:", "question?",
            "star*", "bracket[", "backslash\\"})
    @DisplayName("validateFormat rejects names with illegal characters")
    void rejectsIllegalCharacters(String name) {
        assertThrows(IllegalArgumentException.class,
                () -> GitNameValidator.validateFormat(name));
    }

    @Test
    @DisplayName("validateTagFormat accepts a valid tag name")
    void validateTagFormatAcceptsValidTag() {
        assertDoesNotThrow(() -> GitNameValidator.validateTagFormat("v1.0.0"));
    }

    @Test
    @DisplayName("validateTagFormat rejects names containing '@{'")
    void validateTagFormatRejectsAtBrace() {
        assertThrows(IllegalArgumentException.class,
                () -> GitNameValidator.validateTagFormat("tag@{0}"));
    }

    @Test
    @DisplayName("validateTagFormat also applies all rules from validateFormat")
    void validateTagFormatInheritsFormatRules() {
        assertThrows(IllegalArgumentException.class,
                () -> GitNameValidator.validateTagFormat("v1..0"));
        assertThrows(IllegalArgumentException.class,
                () -> GitNameValidator.validateTagFormat("v1.0.lock"));
        assertThrows(IllegalArgumentException.class,
                () -> GitNameValidator.validateTagFormat("v 1.0"));
    }
}
