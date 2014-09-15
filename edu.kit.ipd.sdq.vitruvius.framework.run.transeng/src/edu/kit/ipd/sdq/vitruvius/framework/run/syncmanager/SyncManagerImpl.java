package edu.kit.ipd.sdq.vitruvius.framework.run.syncmanager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
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
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Quadruple;

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
        this.changeSynchonizerMap.put(CompositeChange.class, new CompositeChangeSynchronizer(modelProviding,
                this.changePropagating, this.correspondenceProviding));
        this.invariantProviding = invariantProviding;
        this.validating = validating;
    }

    @Override
    public void synchronizeChanges(final List<Change> changes) {
        for (Change change : changes) {
            synchronizeChange(change);
        }
    }

    @Override
    public void synchronizeChange(final Change change) {
        if (!this.changeSynchonizerMap.containsKey(change.getClass())) {
            logger.warn("Could not find ChangeSynchronizer for change " + change.getClass().getSimpleName()
                    + ". Can not synchronize change " + change);
        }
        EMFChangeResult emfChangeResult = (EMFChangeResult) this.changeSynchonizerMap.get(change.getClass())
                .synchronizeChange(change);
        // TODO: Check invariants:
        // Get invariants from Invariant providing
        // Validate models with Validating
        // TODO: Execute responses for violated invariants --> Classified response actions

        for (VURI changedVURI : emfChangeResult.getExistingObjectsToSave()) {
            this.modelProviding.saveModelInstanceOriginal(changedVURI);
        }

        for (Pair<EObject, VURI> createdEObjectVURIPair : emfChangeResult.getNewRootObjectsToSave()) {
            ModelInstance mi = this.modelProviding.getAndLoadModelInstanceOriginal(createdEObjectVURIPair.getSecond());
            Resource resource = mi.getResource();
            // clear the resource first
            resource.getContents().clear();
            resource.getContents().add(createdEObjectVURIPair.getFirst());
            this.modelProviding.saveModelInstanceOriginal(mi.getURI());
        }

        // TODO: Check wheather we need a deleteModelInstanceOriginal in VSUM.
        // Here we usually do not need it because we usually delete JaMoPP resource that are renamed
        // Hence we do not need to remove the correspondence models etc.However the question is what
        // happens if we delete, e.g. a PCM instance.
        for (VURI vuriToDelete : emfChangeResult.getExistingObjectsToDelete()) {
            ModelInstance mi = getModelProviding().getAndLoadModelInstanceOriginal(vuriToDelete);
            Resource resource = mi.getResource();
            try {
                resource.delete(null);
            } catch (IOException e) {
                throw new RuntimeException("Could not delete VURI: " + vuriToDelete + ". Exception: " + e);
            }
        }

        // update correspondenceInstances
        removeOldCorrespondences(emfChangeResult.getCorrespondencesToDelete());
        addNewCorrespondences(emfChangeResult.getNewCorrespondences());
        updateExistingCorrespondence(emfChangeResult.getCorrespondencesToUpdate());
    }

    private void removeOldCorrespondences(
            final Set<Quadruple<CorrespondenceInstance, EObject, EObject, Correspondence>> correspondencesToDelete) {
        for (final Quadruple<CorrespondenceInstance, EObject, EObject, Correspondence> quadruple : correspondencesToDelete) {
            CorrespondenceInstance correspondenceInstance = quadruple.getFirst();
            correspondenceInstance.removeCorrespondenceAndAllDependentCorrespondences(quadruple.getFourth());
        }
    }

    private void addNewCorrespondences(
            final Set<Quadruple<CorrespondenceInstance, EObject, EObject, Correspondence>> newCorrespondences) {
        for (final Quadruple<CorrespondenceInstance, EObject, EObject, Correspondence> quadruple : newCorrespondences) {
            CorrespondenceInstance correspondenceInstance = quadruple.getFirst();
            correspondenceInstance.createAndAddEObjectCorrespondence(quadruple.getSecond(), quadruple.getThird(),
                    quadruple.getFourth());
        }
    }

    private void updateExistingCorrespondence(
            final Set<Quadruple<CorrespondenceInstance, EObject, EObject, Correspondence>> correspondencesToUpdate) {
        for (final Quadruple<CorrespondenceInstance, EObject, EObject, Correspondence> quadruple : correspondencesToUpdate) {
            CorrespondenceInstance correspondenceInstance = quadruple.getFirst();
            correspondenceInstance.update(quadruple.getSecond(), quadruple.getThird());
        }
    }

    public ModelProviding getModelProviding() {
        return this.modelProviding;
    }
}
