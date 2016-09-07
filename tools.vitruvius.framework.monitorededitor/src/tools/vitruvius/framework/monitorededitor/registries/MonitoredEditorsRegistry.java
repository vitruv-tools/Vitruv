package tools.vitruvius.framework.monitorededitor.registries;

/**
 * Registry for monitored editors, which implements the singleton pattern.
 * 
 * @author Stephan Seifermann
 *
 */
public class MonitoredEditorsRegistry extends RegistryBase<RegisteredMonitoredEditor> {

    private static MonitoredEditorsRegistry instance;

    /**
     * Initializes the singleton.
     */
    public static void init() {
        if (instance == null) {
            instance = new MonitoredEditorsRegistry();
        }
    }

    /**
     * @return The registry instance.
     */
    public static MonitoredEditorsRegistry getInstance() {
        return instance;
    }

    /**
     * Constructor (forbidden).
     */
    protected MonitoredEditorsRegistry() {
    }

    @Override
    public void unregisterElement(final RegisteredMonitoredEditor element) {
        element.shutdown();
        super.unregisterElement(element);
    }
}
