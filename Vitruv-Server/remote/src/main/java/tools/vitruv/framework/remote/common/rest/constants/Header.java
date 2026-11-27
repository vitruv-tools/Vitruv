package tools.vitruv.framework.remote.common.rest.constants;

/** Constants for HTTP headers used in the Vitruv Remote Framework. */
public final class Header {
  /** The Content-Type header key. */
  public static final String CONTENT_TYPE = "Content-Type";

  /** The View-UUID header key. */
  public static final String VIEW_UUID = "View-UUID";

  /** The Selector-UUID header key. */
  public static final String SELECTOR_UUID = "Selector-UUID";

  /** The View-Type header key. */
  public static final String VIEW_TYPE = "View-Type";

  /** The Branch-Name header key. */
  public static final String BRANCH_NAME = "Branch-Name";

  /** The Commit-Sha header key (7-character short SHA). */
  public static final String COMMIT_SHA = "Commit-Sha";

  /** The Version-Id header key. */
  public static final String VERSION_ID = "Version-Id";

  /** The Base-Branch header key (used by the delta endpoint to specify the comparison base). */
  public static final String BASE_BRANCH = "Base-Branch";

  /** The Format header key (values: json, text, mermaid). */
  public static final String FORMAT = "Format";

  private Header() throws InstantiationException {
    throw new InstantiationException("Cannot be instantiated");
  }
}
