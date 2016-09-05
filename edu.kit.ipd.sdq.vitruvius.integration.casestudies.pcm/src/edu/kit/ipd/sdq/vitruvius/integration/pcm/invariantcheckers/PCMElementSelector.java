package edu.kit.ipd.sdq.vitruvius.integration.pcm.invariantcheckers;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.extensions.constructionsimulation.invariantcheckers.IMModelImplExtractor;

/**
 * Base class for traversal of different pcm model instances. (e.g. Repository, System) Is mainly
 * used as traversal strategy for rename invariant enforcers. Is ALWAYS used within a
 * invariantenforcer. Allows the reuse of rename enforcers with different types of models.
 *
 * do not confuse with traversal strategies for the integration step
 *
 * * Is always used in a "dynamic invariant enforcer" setting, so reference to this "parent"
 *
 * @author johannes hoor
 *
 * @param <T>
 *            the "base" or root object (model element) of the model type
 */
public abstract class PCMElementSelector<T extends EObject> {
    protected IMModelImplExtractor<T> extractor;
    protected Logger logger; // this should be the logger of the underlying
    // "dynamicInvariantEnforcer" class
    protected T root;
    protected InvariantEnforcerFacade parentEnforcer; // parent class == facade
    protected PCMtoJaMoPPRenameInvariantEnforcer enforcer; // the actual enforcer that is used

    /**
     * Gets the enforcer.
     *
     * @return the enforcer
     */
    public PCMtoJaMoPPRenameInvariantEnforcer getEnforcer() {
        return this.enforcer;
    }

    /**
     * Sets the enforcer.
     *
     * @param enforcer
     *            the new enforcer
     */
    public void setEnforcer(final PCMtoJaMoPPRenameInvariantEnforcer enforcer) {
        this.enforcer = enforcer;
    }

    /**
     * Gets the parent enforcer.
     *
     * @return the parent enforcer
     */
    public InvariantEnforcerFacade getParentEnforcer() {
        return this.parentEnforcer;
    }

    /**
     * Sets the parent enforcer.
     *
     * @param parentEnforcer
     *            the new parent enforcer
     */
    public void setParentEnforcer(final InvariantEnforcerFacade parentEnforcer) {
        this.parentEnforcer = parentEnforcer;
    }

    /**
     * Load model root.
     */
    public abstract void loadModelRoot();

    /**
     * Sets the logger.
     *
     * @param logger
     *            the new logger
     */
    public void setLogger(final Logger logger) {
        this.logger = logger;
    }

    /**
     * traverse model. Delegate solving to solving methods in sub-classes.
     */
    public abstract void traverseModelAndSolveConflics();

    /**
     * delegation method Is a conflicting to the rule/invariant?.
     *
     * @param a
     *            input String
     * @return t/f
     */
    protected boolean checkForNameConflict(final String a) {
        return this.enforcer.checkForNameConflict(a);
    }

    /**
     * delegation method Rename Strings with specific settings (unique rename).
     *
     * @param element
     *            String to be renamed
     * @return renamed String
     */
    protected String renameElement(final String element) {
        return this.enforcer.renameElement(element);
    }

}
