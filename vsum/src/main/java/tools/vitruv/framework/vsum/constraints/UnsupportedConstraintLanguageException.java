package tools.vitruv.framework.vsum.constraints;

/** Thrown if a constraint is created in a language no {@link EditableConstraintChecker} supports. */
public class UnsupportedConstraintLanguageException extends IllegalArgumentException {
  private static final long serialVersionUID = 1L;

  /** The requested language. */
  private final String language;

  /**
   * Creates an exception for the given language.
   *
   * @param language the requested language
   */
  public UnsupportedConstraintLanguageException(String language) {
    super("There is no constraint checker for the language " + language);
    this.language = language;
  }

  /**
   * Gets the requested language.
   *
   * @return the language
   */
  public String getLanguage() {
    return language;
  }
}
