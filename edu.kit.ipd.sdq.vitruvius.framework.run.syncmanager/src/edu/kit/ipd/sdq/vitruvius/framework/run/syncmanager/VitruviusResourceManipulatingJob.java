package edu.kit.ipd.sdq.vitruvius.framework.run.syncmanager;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Quadruple;

/**
 * The VitruviusResourceManipulatingJob is an eclipse job. Its task is to save A own job is used
 * since it is not always possible to save or manipulate elements using the same thread that calls
 * {@link SyncManagerImpl}::synchronizeChange (e.g. when the manipulation is caused during a build).
 *
 */
class VitruviusResourceManipulatingJob extends Job {

    private ConcurrentLinkedQueue<EMFChangeResult> emfChangeResultQueue;
    private ModelProviding modelProviding;
    private boolean resourceManipulatingInProgress;

    public VitruviusResourceManipulatingJob(final ModelProviding modelProviding) {
        super(VitruviusResourceManipulatingJob.class.getSimpleName());

        this.modelProviding = modelProviding;
        this.emfChangeResultQueue = new ConcurrentLinkedQueue<EMFChangeResult>();
        this.resourceManipulatingInProgress = false;
    }

    @Override
    protected IStatus run(final IProgressMonitor monitor) {
        setPriority(SHORT);
        this.resourceManipulatingInProgress = true;
        try {
            while (!this.emfChangeResultQueue.isEmpty()) {
                EMFChangeResult emfChangeResult = this.emfChangeResultQueue.poll();

                // TODO: Check wheather we need a deleteModelInstanceOriginal in VSUM.
                // Here we usually do not need it because we usually delete JaMoPP resource that are
                // renamed
                // Hence we do not need to remove the correspondence models etc.However the question
                // is
                // what
                // happens if we delete, e.g. a PCM instance.
                for (VURI vuriToDelete : emfChangeResult.getExistingObjectsToDelete()) {
                    ModelInstance mi = this.modelProviding.getAndLoadModelInstanceOriginal(vuriToDelete);
                    Resource resource = mi.getResource();
                    try {
                        resource.delete(null);
                    } catch (IOException e) {
                        throw new RuntimeException("Could not delete VURI: " + vuriToDelete + ". Exception: " + e);
                    }
                }

                for (VURI changedVURI : emfChangeResult.getExistingObjectsToSave()) {
                    this.modelProviding.saveModelInstanceOriginal(changedVURI);
                }

                for (Pair<EObject, VURI> createdEObjectVURIPair : emfChangeResult.getNewRootObjectsToSave()) {
                    ModelInstance mi = this.modelProviding.getAndLoadModelInstanceOriginal(createdEObjectVURIPair
                            .getSecond());
                    Resource resource = mi.getResource();
                    // clear the resource first
                    resource.getContents().clear();
                    resource.getContents().add(createdEObjectVURIPair.getFirst());
                    this.modelProviding.saveModelInstanceOriginal(mi.getURI());
                }

                // update correspondenceInstances
                removeOldCorrespondences(emfChangeResult.getCorrespondencesToDelete());
                addNewCorrespondences(emfChangeResult.getNewCorrespondences());
                updateExistingCorrespondence(emfChangeResult.getCorrespondencesToUpdate());
            }
        } finally {
            this.resourceManipulatingInProgress = false;
        }
        return Status.OK_STATUS;
    }

    private void removeOldCorrespondences(final Set<Pair<CorrespondenceInstance, TUID>> correspondencesToDelete) {
        for (final Pair<CorrespondenceInstance, TUID> pair : correspondencesToDelete) {
            CorrespondenceInstance correspondenceInstance = pair.getFirst();
            correspondenceInstance.removeCorrespondenceAndAllDependentCorrespondences(pair.getSecond());
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
            final Set<Quadruple<CorrespondenceInstance, TUID, EObject, Correspondence>> correspondencesToUpdate) {
        for (final Quadruple<CorrespondenceInstance, TUID, EObject, Correspondence> quadruple : correspondencesToUpdate) {
            CorrespondenceInstance correspondenceInstance = quadruple.getFirst();
            correspondenceInstance.update(quadruple.getSecond(), quadruple.getThird());
        }
    }

    public void addEMFChangeResult(final EMFChangeResult emfChangeResult) {
        this.emfChangeResultQueue.add(emfChangeResult);
    }

    public boolean isResourceManipulatingInProgress() {
        return this.resourceManipulatingInProgress;
    }
}
