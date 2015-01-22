package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.java.compositerefiners;

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.CustomTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.SynchronisationAbortedListener;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.EMFModelTransformationExecuting;
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
    public CompositeChangeRefinerResultTransformation(CustomTransformation transformation) {
        this.transformation = transformation;
    }

    @Override
    public EMFChangeResult apply(EMFModelTransformationExecuting transformationExecuting, CorrespondenceInstance ci,
            UserInteracting ui, SynchronisationAbortedListener abortListener) {
        return transformation.execute(ci, ui, abortListener);
    }

}
