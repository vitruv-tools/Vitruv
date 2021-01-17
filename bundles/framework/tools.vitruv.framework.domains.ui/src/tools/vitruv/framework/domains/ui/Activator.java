package tools.vitruv.framework.domains.ui;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Activator for the monitored editor base plugin.
 *
 */
public class Activator implements BundleActivator {

    private static BundleContext context;

    /**
     * @return The bundle context.
     */
    static BundleContext getContext() {
        return context;
    }

    /**
     * Starts the plugin. This is the place where singletons are ensured over various class loaders.
     *
     * @param bundleContext
     *            The bundle context.
     * @throws Exception
     *             in case of an error during the initialization.
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start(final BundleContext bundleContext) throws Exception {
        Activator.context = bundleContext;
    }

    /**
     * Stops the plugin.
     *
     * @param bundleContext
     *            The bundle context.
     * @throws Exception
     *             in case of an error.
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(final BundleContext bundleContext) throws Exception {
        Activator.context = null;
    }

}
