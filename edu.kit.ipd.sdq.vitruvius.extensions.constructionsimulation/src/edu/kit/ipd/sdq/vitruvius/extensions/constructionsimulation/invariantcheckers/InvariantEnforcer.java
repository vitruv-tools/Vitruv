package edu.kit.ipd.sdq.vitruvius.extensions.constructionsimulation.invariantcheckers;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * Base class for all invariant enforcers.
 *
 * @author
 */
public abstract class InvariantEnforcer {

    public Logger logger = LogManager.getRootLogger();
    protected Resource model;

    /**
     * Gets the model.
     *
     * @return the model
     */
    public Resource getModel() {
        return this.model;
    }

    /**
     * Sets the model.
     *
     * @param model
     *            the new model
     */
    public void setModel(final Resource model) {
        this.model = model;
    }

    /**
     * Instantiates a new invariant enforcer.
     */
    public InvariantEnforcer() {

        /*
         * final ConsoleAppender ca = new ConsoleAppender(); ca.setName("cAppender");
         * ca.setWriter(new OutputStreamWriter(System.out)); ca.setLayout(new PatternLayout(
         * "%-5p [%t]: %m%n")); if (this.logger.getAppender("cAppender") == null) {
         * this.logger.addAppender(ca); // TODO: find better way to prevent duplicate output }
         */

    }

    /**
     * Load enforce return.
     *
     * @param model
     *            the model
     * @return the resource
     */
    public Resource loadEnforceReturn(final Resource model) {
        this.loadModelRoot(model);
        this.enforceInvariant();
        return this.returnModel();
    }

    /**
     * Load model root.
     *
     * @param model
     *            the model
     */
    public abstract void loadModelRoot(Resource model);

    /**
     * Enforce invariant.
     */
    public abstract void enforceInvariant();

    /**
     * Return model.
     *
     * @return the resource
     */
    public abstract Resource returnModel();
}
