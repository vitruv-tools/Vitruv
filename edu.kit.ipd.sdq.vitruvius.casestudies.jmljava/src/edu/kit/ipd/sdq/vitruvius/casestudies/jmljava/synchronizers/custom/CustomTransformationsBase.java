package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.custom;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import com.google.common.collect.Sets;

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.helper.java.shadowcopy.ShadowCopyFactory;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.CustomTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.SynchronisationAbortedListener;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

/**
 * Base class for custom transformations, which are not inherited from
 * {@link EObjectMappingTransformation}. It performs the necessary VURI operations.
 * 
 * @author Stephan Seifermann
 *
 */
public abstract class CustomTransformationsBase implements CustomTransformation {

    protected final ShadowCopyFactory shadowCopyFactory;

    /**
     * Constructor.
     * @param shadowCopyFactory A shadow copy factory.
     */
    protected CustomTransformationsBase(ShadowCopyFactory shadowCopyFactory) {
        this.shadowCopyFactory = shadowCopyFactory;
    }

    @Override
    public EMFChangeResult execute(CorrespondenceInstance ci, UserInteracting userInteracting,
            SynchronisationAbortedListener abortListener) {

        TransformationChangeResult result = executeInternal(ci, userInteracting);
        if (result.isTransformationAborted()) {
            abortListener.synchronisationAborted(this);
        }

        Set<VURI> vurisToSave = new HashSet<VURI>();
        for (EObject objectToSave : result.getExistingObjectsToSave()) {
            vurisToSave.add(VURI.getInstance(objectToSave.eResource()));
        }

        // TODO vurisToAdd is not supported because no URI affecting changes are supported atm

        return new EMFChangeResult(vurisToSave, new HashSet<Pair<EObject, VURI>>(), new HashSet<VURI>());
    }

    /**
     * Executes the transformation.
     * @param ci The matching correspondence instance.
     * @param userInteracting A user interacting.
     * @return The set of changed, deleted and added EObjects.
     */
    protected abstract TransformationChangeResult executeInternal(CorrespondenceInstance ci,
            UserInteracting userInteracting);

    /**
     * Creates a transformation result for changed EObjects.
     * @param changedEObjects The changed EObjects.
     * @return The transformation result.
     */
    protected TransformationChangeResult createTransformationChangeResult(Iterable<EObject> changedEObjects) {
        Set<EObject> changedEObjectsSet = Sets.newHashSet(changedEObjects);
        return new TransformationChangeResult(changedEObjectsSet, new HashSet<EObject>(), new HashSet<VURI>());
    }

    /**
     * Creates a transformation result for an aborted transformation.
     * @return The transformation result.
     */
    protected TransformationChangeResult createAbortTransformationChangeResult() {
        return new TransformationChangeResult(new HashSet<EObject>(), new HashSet<EObject>(), new HashSet<VURI>(), true);
    }

}
