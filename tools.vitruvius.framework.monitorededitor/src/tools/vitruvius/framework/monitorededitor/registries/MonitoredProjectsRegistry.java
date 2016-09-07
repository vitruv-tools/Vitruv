package tools.vitruvius.framework.monitorededitor.registries;

/**
 * Registry for monitored projects, which implements the singleton pattern.
 *
 * @author Stephan Seifermann
 *
 */
public class MonitoredProjectsRegistry extends RegistryBase<String> {

    private static MonitoredProjectsRegistry instance;

    /**
     * Initializes the registry singleton.
     */
    public static void init() {
        if (instance == null) {
            instance = new MonitoredProjectsRegistry();
        }
    }

    /**
     * @return The instance of the registry.
     */
    public static MonitoredProjectsRegistry getInstance() {
        return instance;
    }

    /**
     * Constructor (forbidden).
     */
    protected MonitoredProjectsRegistry() {
    }

}
