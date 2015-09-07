package edu.kit.ipd.sdq.vitruvius.integration.invariantcheckers;

 
/**
 * Helper class for building dynamic Invariant enforcer. Does not use Builder pattern!
 *
 * @author usr
 *
 */
public class InvariantEnforcerFacadeBuilder {

    /**
     * Builds the InvariantEnforcer, takes care of the correct logger placement.
     * 
     * @param strategy
     *            the strategy
     * @param enforcer
     *            the enforcer
     * @return the dynamic invariant enforcer
     */
    public static InvariantEnforcerFacade buildInvariantEnforcerFacade(final PCMElementSelector strategy,
            final PCMtoJaMoPPRenameInvariantEnforcer enforcer) {
        final InvariantEnforcerFacade dEnforcer = new InvariantEnforcerFacade(strategy, enforcer);
        strategy.setLogger(dEnforcer.logger);
        strategy.setParentEnforcer(dEnforcer);
        strategy.setEnforcer(enforcer);
        return dEnforcer;
    }

}
