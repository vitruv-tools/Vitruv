package tools.vitruv.applications.jmljava.synchronizers;

import tools.vitruv.framework.util.command.TransformationResult;
import tools.vitruv.framework.userinteraction.UserInteracting;
import tools.vitruv.framework.correspondence.CorrespondenceModel;
import tools.vitruv.framework.modelsynchronization.TransformationAbortCause;

/**
 * Base interface for custom transformations, which are not part of the usual transformation
 * handling.
 *
 * @author Stephan Seifermann
 *
 */
public interface CustomTransformation extends TransformationAbortCause {

    /**
     * Executes the transformation.
     *
     * @param blackboard
     *            The correspondence instance, which is relevant for this transformation.
     * @param userInteracting
     *            A user interacting, which can be used to communicate with the user.
     * @param abortListener
     *            The listener, which has to be notified on aborted transformations.
     * @return TODO
     * @return The transformation result represented by URIs.
     */
    public TransformationResult execute(CorrespondenceModel correspondenceModel, UserInteracting userInteracting,
            SynchronisationAbortedListener abortListener);

}
