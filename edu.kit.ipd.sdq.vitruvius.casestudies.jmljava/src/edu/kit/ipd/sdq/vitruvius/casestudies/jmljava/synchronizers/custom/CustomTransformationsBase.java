package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.custom;

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.helper.java.shadowcopy.ShadowCopyFactory;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.CustomTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.SynchronisationAbortedListener;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EObjectMappingTransformation;

/**
 * Base class for custom transformations, which are not inherited from
 * {@link EObjectMappingTransformation}. It performs the necessary VURI operations.
 *
 * @author Stephan Seifermann
 *
 */
public abstract class CustomTransformationsBase implements CustomTransformation {

    protected final ShadowCopyFactory shadowCopyFactory;
    protected SynchronisationAbortedListener abortListener;

    /**
     * Constructor.
     *
     * @param shadowCopyFactory
     *            A shadow copy factory.
     */
    protected CustomTransformationsBase(final ShadowCopyFactory shadowCopyFactory) {
        this.shadowCopyFactory = shadowCopyFactory;
    }

    @Override
    public TransformationResult execute(final Blackboard blackboard, final UserInteracting userInteracting,
            final SynchronisationAbortedListener abortListener) {
        this.abortListener = abortListener;
        return this.executeInternal(blackboard, userInteracting);
    }

    /**
     * Executes the transformation.
     *
     * @param ci
     *            The matching correspondence instance.
     * @param userInteracting
     *            A user interacting.
     * @return The transformationresult
     */
    protected abstract TransformationResult executeInternal(Blackboard ci, UserInteracting userInteracting);
}
