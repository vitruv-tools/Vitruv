package edu.kit.ipd.sdq.vitruvius.applications.jmljava;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import edu.kit.ipd.sdq.vitruvius.applications.jmljava.changesynchronizer.ChangeSynchronizerRegistry;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "edu.kit.ipd.sdq.vitruvius.casestudies.jmljava"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		ChangeSynchronizerRegistry.init();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

}
