package edu.kit.ipd.sdq.vitruvius.framework.run.syncmanager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FileChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangePropagating;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CorrespondenceProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.InvariantProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Validating;

public class SyncManagerImpl implements ChangeSynchronizing {

    private static Logger logger = Logger.getLogger(SyncManagerImpl.class.getSimpleName());

    private final ModelProviding modelProviding;
    private final ChangePropagating changePropagating;
    private final CorrespondenceProviding correspondenceProviding;
    private final InvariantProviding invariantProviding;
    private final Validating validating;
    private final VitruviusResourceManipulatingJob vitruviusResourceManipulatingJob;

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
        this.vitruviusResourceManipulatingJob = new VitruviusResourceManipulatingJob(this.modelProviding);
    }

    @Override
    public void synchronizeChanges(final List<Change> changes) {
        for (Change change : changes) {
            synchronizeChange(change);
        }
    }

    @Override
    public void synchronizeChange(final Change change) {
        if (changeCausedByResourceManipultingJob()) {
            logger.info("Change " + change
                    + " not syncrhonized, cause it was caused by the resource manipulating job itself.");
            return;
        }
        if (!this.changeSynchonizerMap.containsKey(change.getClass())) {
            logger.warn("Could not find ChangeSynchronizer for change " + change.getClass().getSimpleName()
                    + ". Can not synchronize change " + change);
        }
        EMFChangeResult emfChangeResult = (EMFChangeResult) this.changeSynchonizerMap.get(change.getClass())
                .synchronizeChange(change);

        this.vitruviusResourceManipulatingJob.addEMFChangeResult(emfChangeResult);
        this.vitruviusResourceManipulatingJob.schedule();
    }

    private boolean changeCausedByResourceManipultingJob() {
        return this.vitruviusResourceManipulatingJob.isResourceManipulatingInProgress();
        // if (Job.RUNNING == this.vitruisviusResourceManipulatingJob.getState()) {
        // if (this.vitruviusResourceManipulatingJob.getThread() == Thread.currentThread()) {
        // return true;
        // }
        // }
        // return false;
    }

}
