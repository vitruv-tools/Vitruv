package edu.kit.ipd.sdq.vitruvius.applications.jmljava.changesynchronizer;

/**
 * Registry for the change synchronizer. This is used by all monitored editors to access the SAME
 * instance of the synchronizer.
 * 
 * @author Stephan Seifermann
 *
 */
public class ChangeSynchronizerRegistry {

    private static ChangeSynchronizerRegistry instance;
    private ChangeSynchronizer changeSynchronizer;

    /**
     * Initializes the singleton. If called a second time the instance will be replaced with a new
     * one.
     */
    public static void init() {
        if (instance != null) {
            instance = null;
        }
        instance = new ChangeSynchronizerRegistry();
    }

    /**
     * @return The registry instance.
     */
    public static ChangeSynchronizerRegistry getInstance() {
        return instance;
    }

    /**
     * Constructor (forbidden)
     */
    protected ChangeSynchronizerRegistry() {
    }

    /**
     * @return The change synchronizer instance.
     */
    public ChangeSynchronizer getChangeSynchronizer() {
        if (changeSynchronizer == null) {
            changeSynchronizer = new ChangeSynchronizer();
        }
        return changeSynchronizer;
    }
}
