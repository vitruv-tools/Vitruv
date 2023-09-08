package tools.vitruv.framework.remote.common.util.constants;

public final class JsonFieldName {
    public static final String CHANGE_TYPE = "changeType";
    public static final String E_CHANGES = "eChanges";
    public static final String V_CHANGES = "vChanges";
    public static final String U_INTERACTIONS = "uInteractions";
    public static final String TEMP_VALUE = "temp";
    public static final String CONTENT = "content";
    public static final String URI = "uri";

    private JsonFieldName() throws InstantiationException {
        throw new InstantiationException("Cannot be instantiated");
    }
}
