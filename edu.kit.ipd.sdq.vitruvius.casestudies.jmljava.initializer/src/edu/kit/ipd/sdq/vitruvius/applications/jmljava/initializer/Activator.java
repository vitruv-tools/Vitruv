package edu.kit.ipd.sdq.vitruvius.applications.jmljava.initializer;

import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * Activator for the initializer plugin.
 * 
 * @author Stephan Seifermann
 *
 */
public class Activator extends AbstractUIPlugin {

    /**
     * Constructor.
     */
    public Activator() {
        LoggingSettings.setup();
    }

}
