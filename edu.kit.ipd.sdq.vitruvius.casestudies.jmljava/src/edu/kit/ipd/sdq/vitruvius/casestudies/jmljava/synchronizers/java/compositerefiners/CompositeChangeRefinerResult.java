package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.java.compositerefiners;

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.SynchronisationAbortedListener;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;

/**
 * Base interface for all possible composite change refiner results. The only requirement is a
 * method, which can be used to apply the result.
 *
 * @author Stephan Seifermann
 *
 */
public interface CompositeChangeRefinerResult {

    /**
     * Applies the result to the models. Not all parameters might be used for this.
     *
     * @param transformationExecuting
     *            An EMF transformation executing.
     * @param blackboard
     *            The matching correspondence instance. This depends on the processed composite
     *            change.
     * @param ui
     *            A user interacting.
     * @param abortListener
     *            A listener for aborted synchronisations.
     * @return The result of the application process.
     */
    public void apply(Change2CommandTransforming transformationExecuting, Blackboard blackboard, UserInteracting ui,
            SynchronisationAbortedListener abortListener);

}
