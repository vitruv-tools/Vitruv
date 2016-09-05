package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.reconstructionintegration.invariantcheckers;

import org.eclipse.emf.ecore.resource.Resource;

import edu.kit.ipd.sdq.vitruvius.extensions.constructionsimulation.invariantcheckers.InvariantEnforcer;

/**
 * The Class DynamicInvariantEnforcer.
 */
public class InvariantEnforcerFacade extends InvariantEnforcer {

    private PCMElementSelector<?> traversalStrategy; // TODO: make more abstract
    private PCMtoJaMoPPRenameInvariantEnforcer enforcer; // TODO: same here

    /**
     * Instantiates a new dynamic invariant enforcer.
     *
     * @param strategy
     *            the strategy
     * @param enforcer
     *            the enforcer
     */
    public InvariantEnforcerFacade(final PCMElementSelector<?> strategy,
            final PCMtoJaMoPPRenameInvariantEnforcer enforcer) {
        this.setTraversalStrategy(strategy);
        this.setEnforcer(enforcer);

    }

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
     * Gets the traversal strategy.
     *
     * @return the traversal strategy
     */
    public PCMElementSelector<?>getTraversalStrategy() {
        return this.traversalStrategy;
    }

    /**
     * Sets the traversal strategy.
     *
     * @param traversalStrategy
     *            the new traversal strategy
     */
    public void setTraversalStrategy(final PCMElementSelector<?> traversalStrategy) {
        this.traversalStrategy = traversalStrategy;
        traversalStrategy.setParentEnforcer(this);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * edu.kit.ipd.sdq.vitruvius.integration.invariantChecker.InvariantEnforcer#loadModelRoot(org
     * .eclipse.emf.ecore.resource.Resource)
     */
    @Override
    public void loadModelRoot(final Resource model) {
        this.model = model;
        this.traversalStrategy.loadModelRoot();

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * edu.kit.ipd.sdq.vitruvius.integration.invariantChecker.InvariantEnforcer#enforceInvariant()
     */
    @Override
    public void enforceInvariant() {
        this.traversalStrategy.traverseModelAndSolveConflics();

    }

    /*
     * (non-Javadoc)
     *
     * @see edu.kit.ipd.sdq.vitruvius.integration.invariantChecker.InvariantEnforcer#returnModel()
     */
    @Override
    public Resource returnModel() {
        return this.model;
    }

}
