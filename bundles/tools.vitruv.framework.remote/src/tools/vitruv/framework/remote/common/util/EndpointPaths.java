package tools.vitruv.framework.remote.common.util;

public class EndpointPaths {
    public static final String HEALTH = "/health";
    public static final String VIEW_TYPES = "/vsum/view/types";
    public static final String VIEW_SELECTOR = "/vsum/view/selector";
    public static final String VIEW = "/vsum/view";
    public static final String IS_VIEW_CLOSED = "/vsum/view/closed";
    public static final String IS_VIEW_OUTDATED = "/vsum/view/outdated";

    private EndpointPaths() throws InstantiationException {
        throw new InstantiationException("Cannot be instantiated");
    }
}