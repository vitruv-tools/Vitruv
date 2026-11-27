package tools.vitruv.framework.vsum.branch.util;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Stateless Git naming rules shared by branch and version name validation.
 */
public final class GitNameValidator {

  private GitNameValidator() {}

  /**
   * Validates a proposed Git branch or tag name against structural naming rules.
   *
   * <p>The following rules are enforced:
   * <ul>
   *   <li>name must not be blank.</li>
   *   <li>name must not contain {@code ..}.</li>
   *   <li>name must not end with {@code .lock}.</li>
   *   <li>name must not contain any of {@code space ~ ^ : ? * [ \}.</li>
   * </ul>
   *
   * @param name the name to validate; must not be null.
   * @throws IllegalArgumentException if the name violates any rule.
   */
  public static void validateFormat(String name) {
    checkNotNull(name, "name must not be null");
    if (name.isBlank()) {
      throw new IllegalArgumentException("Name must not be blank");
    }
    if (name.contains("..")) {
      throw new IllegalArgumentException("Name must not contain '..': " + name);
    }
    if (name.endsWith(".lock")) {
      throw new IllegalArgumentException("Name must not end with '.lock': " + name);
    }
    if (name.chars().anyMatch(c -> " ~^:?*[\\".indexOf(c) >= 0)) {
      throw new IllegalArgumentException("Name contains illegal characters: " + name);
    }
  }

  /**
   * Validates a proposed Git tag name. Applies all rules from {@link #validateFormat} and
   * additionally disallows {@code @{} (used in Git reflog syntax).
   *
   * @param name the tag name to validate; must not be null.
   * @throws IllegalArgumentException if the name violates any rule.
   */
  public static void validateTagFormat(String name) {
    validateFormat(name);
    if (name.contains("@{")) {
      throw new IllegalArgumentException("Tag name must not contain '@{': " + name);
    }
  }
}
