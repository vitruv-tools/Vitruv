package tools.vitruv.framework.remote.common.util.constants;

public final class ContentTypes {
	public static final String APPLICATION_JSON = "application/json";
	public static final String TEXT_PLAIN = "text/plain";
	
	private ContentTypes() throws InstantiationException {
        throw new InstantiationException("Cannot be instantiated");
    }
}
