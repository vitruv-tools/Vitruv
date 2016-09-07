package tools.vitruvius.framework.monitorededitor.registries;

/**
 * Monitored Editor, which can be registered in a registry.
 * 
 * @author Stephan Seifermann
 *
 */
public interface RegisteredMonitoredEditor {

    /**
     * Disabled the editor, which means no changes are detected anymore and no calls to the
     * synchronizer are made.
     */
    public void shutdown();

}
