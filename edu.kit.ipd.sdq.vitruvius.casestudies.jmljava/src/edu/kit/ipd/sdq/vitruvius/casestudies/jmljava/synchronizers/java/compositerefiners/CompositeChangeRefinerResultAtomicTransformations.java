package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.java.compositerefiners;

import java.util.List;

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.SynchronisationAbortedListener;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;

/**
 * Composite change refiner result, which contains atomic changes only. The atomic changes can be
 * processed in isolation.
 *
 * @author Stephan Seifermann
 *
 */
public class CompositeChangeRefinerResultAtomicTransformations implements CompositeChangeRefinerResult {

    private final List<EMFModelChange> changes;

    /**
     * Constructor.
     *
     * @param changes
     *            The atomic changes.
     */
    public CompositeChangeRefinerResultAtomicTransformations(final List<EMFModelChange> changes) {
        this.changes = changes;
    }

    @Override
    public void apply(final Change2CommandTransforming transformationExecuting, final Blackboard blackboard,
            final UserInteracting ui, final SynchronisationAbortedListener abortListener) {
        transformationExecuting.transformChanges2Commands(blackboard);
    }
}
