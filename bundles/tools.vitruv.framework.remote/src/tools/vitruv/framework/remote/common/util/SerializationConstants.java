package tools.vitruv.framework.remote.common.util;

public class SerializationConstants {
    public static final String CHANGE_TYPE = "changeType";
    public static final String E_CHANGES = "eChanges";
    public static final String V_CHANGES = "vChanges";
    public static final String TEMP = "temp";
    public static final String CONTENT = "content";
    public static final String URI = "uri";

    private SerializationConstants() throws InstantiationException {
        throw new InstantiationException("Cannot be instantiated");
    }
}
