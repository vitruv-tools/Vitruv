package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.java.compositerefiners;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.SynchronisationAbortedListener;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.EMFModelTransformationExecuting;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

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
    public CompositeChangeRefinerResultAtomicTransformations(List<EMFModelChange> changes) {
        this.changes = changes;
    }

    @Override
    public EMFChangeResult apply(EMFModelTransformationExecuting transformationExecuting, CorrespondenceInstance ci,
            UserInteracting ui, SynchronisationAbortedListener abortListener) {
        Set<VURI> vurisToDelete = new HashSet<VURI>();
        Set<VURI> vurisToSave = new HashSet<VURI>();
        Set<Pair<EObject, VURI>> newRootObjectsToSave = new HashSet<Pair<EObject, VURI>>();

        for (EMFModelChange change : changes) {
            EMFChangeResult result = transformationExecuting.executeTransformation(change, ci);
            vurisToDelete.addAll(result.getExistingObjectsToDelete());
            vurisToSave.addAll(result.getExistingObjectsToSave());
            newRootObjectsToSave.addAll(result.getNewRootObjectsToSave());
        }

        return new EMFChangeResult(vurisToSave, newRootObjectsToSave, vurisToDelete);
    }
}
