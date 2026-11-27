package tools.vitruv.framework.remote.common.json;

/** Field names used in JSON serialization and deserialization. */
public final class JsonFieldName {
  /** The field name for the change type. */
  public static final String CHANGE_TYPE = "changeType";

  /** The field name for eChanges. */
  public static final String E_CHANGES = "eChanges";

  /** The field name for vChanges. */
  public static final String V_CHANGES = "vChanges";

  /** The field name for user interactions. */
  public static final String U_INTERACTIONS = "uInteractions";

  /** The field name for temporary values. */
  public static final String TEMP_VALUE = "temp";

  /** The field name for content. */
  public static final String CONTENT = "content";

  /** The field name for URI. */
  public static final String URI = "uri";

  private JsonFieldName() throws InstantiationException {
    throw new InstantiationException("Cannot be instantiated");
  }
}
