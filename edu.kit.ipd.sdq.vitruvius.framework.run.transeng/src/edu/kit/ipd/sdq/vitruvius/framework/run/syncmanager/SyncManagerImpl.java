package edu.kit.ipd.sdq.vitruvius.framework.run.syncmanager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FileChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangePropagating;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CorrespondenceProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.InvariantProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Validating;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.Pair;

public class SyncManagerImpl implements ChangeSynchronizing {

    private static Logger logger = Logger.getLogger(SyncManagerImpl.class.getSimpleName());

    private final ModelProviding modelProviding;
    private final ChangePropagating changePropagating;
    private final CorrespondenceProviding correspondenceProviding;
    private final InvariantProviding invariantProviding;
    private final Validating validating;

    private Map<Class<?>, ConcreteChangeSynchronizer> changeSynchonizerMap;

    public SyncManagerImpl(final ModelProviding modelProviding, final ChangePropagating changePropagating,
            final CorrespondenceProviding correspondenceProviding, final InvariantProviding invariantProviding,
            final Validating validating) {
        this.modelProviding = modelProviding;
        this.changePropagating = changePropagating;
        this.correspondenceProviding = correspondenceProviding;
        this.changeSynchonizerMap = new HashMap<Class<?>, ConcreteChangeSynchronizer>();
        EMFModelSynchronizer emfModelSynchronizer = new EMFModelSynchronizer(modelProviding, this,
                this.changePropagating, this.correspondenceProviding);
        this.changeSynchonizerMap.put(EMFModelChange.class, emfModelSynchronizer);
        this.changeSynchonizerMap.put(FileChange.class,
                new FileChangeSynchronizer(modelProviding, emfModelSynchronizer));
        this.invariantProviding = invariantProviding;
        this.validating = validating;
    }

    @Override
    public void synchronizeChanges(final List<Change> changes, final VURI sourceModelURI) {
        for (Change change : changes) {
            synchronizeChange(change, sourceModelURI);
        }
    }

    @Override
    public void synchronizeChange(final Change change, final VURI sourceModelURI) {
        if (!this.changeSynchonizerMap.containsKey(change.getClass())) {
            logger.warn("Could not find ChangeSynchronizer for change " + change.getClass().getSimpleName()
                    + ". Can not synchronize change in source model " + sourceModelURI.toString() + " not synchroized.");
        }
        EMFChangeResult emfChangeResult = (EMFChangeResult) this.changeSynchonizerMap.get(change.getClass())
                .synchronizeChange(change, sourceModelURI);
        // TODO: Check invariants:
        // Get invariants from Invariant providing
        // Validate models with Validating
        // TODO: Execute responses for violated invariants --> Classified response actions

        for (VURI changedVURI : emfChangeResult.getExistingVURIsToSave()) {
            this.modelProviding.saveModelInstanceOriginal(changedVURI);
        }

        for (Pair<EObject, VURI> createdEObjectVURIPair : emfChangeResult.getNewRootEObjectsToSave()) {
            ModelInstance mi = this.modelProviding.getModelInstanceOriginal(createdEObjectVURIPair.getSecond());
            Resource resource = mi.getResource();
            // clear the resource first
            resource.getContents().clear();
            resource.getContents().add(createdEObjectVURIPair.getFirst());
            this.modelProviding.saveModelInstanceOriginal(mi.getURI());
        }
    }

    public ModelProviding getModelProviding() {
        return this.modelProviding;
    }
}
