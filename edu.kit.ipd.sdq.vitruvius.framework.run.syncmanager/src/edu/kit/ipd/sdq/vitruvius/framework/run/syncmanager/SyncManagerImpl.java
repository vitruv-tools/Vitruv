package edu.kit.ipd.sdq.vitruvius.framework.run.syncmanager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.SynchronisationListener;
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

    private Set<SynchronisationListener> synchronisationListeners;

    public SyncManagerImpl(final ModelProviding modelProviding, final ChangePropagating changePropagating,
            final CorrespondenceProviding correspondenceProviding, final InvariantProviding invariantProviding,
            final Validating validating, final SynchronisationListener synchronisationListener) {
        this.modelProviding = modelProviding;
        this.changePropagating = changePropagating;
        this.correspondenceProviding = correspondenceProviding;
        this.synchronisationListeners = new HashSet<SynchronisationListener>();
        if (null != synchronisationListener) {
            this.synchronisationListeners.add(synchronisationListener);
        }
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
        this.vitruviusResourceManipulatingJob = new VitruviusResourceManipulatingJob(this.modelProviding,
                this.synchronisationListeners);
    }

    @Override
    public synchronized void synchronizeChanges(final List<Change> changes) {
        boolean isListSynchronization = true;
        for (Change change : changes) {
            boolean isFirstSynchronizationInList = changes.indexOf(change) == 0;
            boolean isLastSynchronizationInList = changes.indexOf(change) == changes.size() - 1;
            synchronizeChange(change, isListSynchronization, isFirstSynchronizationInList, isLastSynchronizationInList);
        }
    }

    @Override
    public synchronized void synchronizeChange(final Change change) {
        synchronizeChange(change, false, true, true);
    }

    public void synchronizeChange(final Change change, final boolean isListSynchronization,
            final boolean isFirstSynchronisationInList, final boolean isLastSynchroisationInList) {
        if (!isListSynchronization || (isListSynchronization && isFirstSynchronisationInList)) {
            for (SynchronisationListener syncListener : this.synchronisationListeners) {
                syncListener.syncStarted();
            }
        }
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
        boolean isLastChangeResultInList = (!isListSynchronization || (isListSynchronization && isLastSynchroisationInList));
        emfChangeResult.setLastChangeResultInList(isLastChangeResultInList);
        this.vitruviusResourceManipulatingJob.addEMFChangeResult(emfChangeResult);
        this.vitruviusResourceManipulatingJob.runSynchron();
    }

    /**
     * check whether the change is caused by the resource manipulating job itself. This is true if
     * the current thread is the same thread as the resource manipulating thread.
     *
     * @return
     */
    private boolean changeCausedByResourceManipultingJob() {
        return this.vitruviusResourceManipulatingJob.getThread() == Thread.currentThread();
    }

}
