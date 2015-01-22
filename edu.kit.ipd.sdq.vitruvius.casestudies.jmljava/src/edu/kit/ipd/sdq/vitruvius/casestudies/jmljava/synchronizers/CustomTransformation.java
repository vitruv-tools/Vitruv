package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers;

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.changesynchronizer.TransformationAbortCause;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;

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
     * @param ci
     *            The correspondence instance, which is relevant for this transformation.
     * @param userInteracting
     *            A user interacting, which can be used to communicate with the user.
     * @param abortListener
     *            The listener, which has to be notified on aborted transformations.
     * @return The transformation result represented by URIs.
     */
    public EMFChangeResult execute(CorrespondenceInstance ci, UserInteracting userInteracting,
            SynchronisationAbortedListener abortListener);

}
