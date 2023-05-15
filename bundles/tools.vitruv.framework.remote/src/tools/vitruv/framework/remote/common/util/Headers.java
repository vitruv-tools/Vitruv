package tools.vitruv.framework.remote.common.util;

public final class Headers {
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String VIEW_UUID = "View-UUID";
    public static final String VIEW_TYPE = "View-Type";

    private Headers() throws InstantiationException {
        throw new InstantiationException("Cannot be instantiated");
    }
}
