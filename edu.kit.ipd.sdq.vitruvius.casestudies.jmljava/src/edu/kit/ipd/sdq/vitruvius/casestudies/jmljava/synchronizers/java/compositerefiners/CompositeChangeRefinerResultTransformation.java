package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.java.compositerefiners;

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.CustomTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.SynchronisationAbortedListener;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;

/**
 * Base class for composite change refiner results, which are custom transformations.
 *
 * @author Stephan Seifermann
 *
 */
public class CompositeChangeRefinerResultTransformation implements CompositeChangeRefinerResult {

    private final CustomTransformation transformation;

    /**
     * Constructor.
     *
     * @param transformation
     *            The transformation, which shall be executed.
     */
    public CompositeChangeRefinerResultTransformation(final CustomTransformation transformation) {
        this.transformation = transformation;
    }

    @Override
    public void apply(final Change2CommandTransforming transformationExecuting, final Blackboard blackboard,
            final UserInteracting ui, final SynchronisationAbortedListener abortListener) {
        this.transformation.execute(blackboard, ui, abortListener);
    }

}
