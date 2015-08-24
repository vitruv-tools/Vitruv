package edu.kit.ipd.sdq.vitruvius.framework.run.changesynchronizer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.apache.log4j.Logger;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransformingProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangePreparing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CommandExecuting;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CorrespondenceProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.InvariantProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.SynchronisationListener;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Validating;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.internal.BlackboardImpl;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.internal.InternalCorrespondenceInstance;

public class ChangeSynchronizerImpl implements ChangeSynchronizing {

    private static Logger logger = Logger.getLogger(ChangeSynchronizerImpl.class.getSimpleName());

    private final ModelProviding modelProviding;
    private final Change2CommandTransformingProviding change2CommandTransformingProviding;
    private final CorrespondenceProviding correspondenceProviding;
    private final InvariantProviding invariantProviding;
    private final Validating validating;
    private final VitruviusResourceManipulatingJob vitruviusResourceManipulatingJob;
    private final ChangePreparing changePreparing;
    private final CommandExecuting commandExecuting;

    private Set<SynchronisationListener> synchronisationListeners;
    private Stack<Blackboard> blackboardHistory;

    public ChangeSynchronizerImpl(final ModelProviding modelProviding,
            final Change2CommandTransformingProviding change2CommandTransformingProviding,
            final CorrespondenceProviding correspondenceProviding, final InvariantProviding invariantProviding,
            final Validating validating, final SynchronisationListener synchronisationListener,
            final ChangePreparing changePreparing, final CommandExecuting commandExecuting) {
        this.modelProviding = modelProviding;
        this.change2CommandTransformingProviding = change2CommandTransformingProviding;
        this.correspondenceProviding = correspondenceProviding;
        this.changePreparing = changePreparing;
        this.synchronisationListeners = new HashSet<SynchronisationListener>();
        if (null != synchronisationListener) {
            this.synchronisationListeners.add(synchronisationListener);
        }
        this.invariantProviding = invariantProviding;
        this.validating = validating;
        this.commandExecuting = commandExecuting;
        this.vitruviusResourceManipulatingJob = new VitruviusResourceManipulatingJob(this.modelProviding,
                this.synchronisationListeners);
    }

    @Override
    public synchronized void synchronizeChange(final Change change) {
        synchronizeChanges(Arrays.asList(change));
    }

    @Override
    public synchronized void synchronizeChanges(final List<Change> changes) {
        if (null == changes || 0 == changes.size()) {
            logger.warn("The change list does not contain any changes to synchronize." + changes);
            return;
        }
        for (SynchronisationListener syncListener : this.synchronisationListeners) {
            syncListener.syncStarted();
        }

        VURI sourceModelVURI = getSourceModelVURI(changes);
        Set<InternalCorrespondenceInstance> correspondenceInstances = this.correspondenceProviding
                .getAllCorrespondenceInstances(sourceModelVURI);
        for (InternalCorrespondenceInstance correspondenceInstance : correspondenceInstances) {
            VURI mmURI1 = correspondenceInstance.getMapping().getMetamodelA().getURI();
            VURI mmURI2 = correspondenceInstance.getMapping().getMetamodelB().getURI();
            Change2CommandTransforming change2CommandTransforming = this.change2CommandTransformingProviding
                    .getChange2CommandTransforming(mmURI1, mmURI2);
            Blackboard blackboard = new BlackboardImpl(correspondenceInstance, this.modelProviding);
            this.changePreparing.prepareAllChanges(blackboard);
            this.blackboardHistory.push(blackboard);
            blackboard.pushChanges(changes);
            List<Change> changesForTransformation = blackboard.getAndArchiveChangesForTransformation();
            validateChanges(changesForTransformation);
            change2CommandTransforming.transformChanges2Commands(blackboard);
            this.commandExecuting.executeCommands(blackboard);
        }

        // TODO: check invariants and execute undo if necessary

        for (SynchronisationListener syncListener : this.synchronisationListeners) {
            syncListener.syncFinished();
        }
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

    private void validateChanges(final List<Change> changesForTransformation) {
        VURI sourceModelURI = null;
        for (Change change : changesForTransformation) {
            if (!(change instanceof EMFModelChange)) {
                throw new RuntimeException(
                        "The change " + change + " is not an instanceof EMFModelChange. Can not transform change");
            }
            EMFModelChange emfModelChange = (EMFModelChange) change;
            if (null == sourceModelURI) {
                sourceModelURI = emfModelChange.getURI();
            } else {
                if (!sourceModelURI.equals(emfModelChange.getURI())) {
                    throw new RuntimeException(
                            "The change " + change + " has a different URI (" + emfModelChange.getURI()
                                    + ") as expected (" + sourceModelURI + "). Can not transform change");
                }
            }
        }

    }

    private VURI getSourceModelVURI(final List<Change> changesForTransformation) {
        if (null != changesForTransformation && 0 < changesForTransformation.size()) {
            EMFModelChange modelChange = (EMFModelChange) changesForTransformation.get(0);
            return modelChange.getURI();
        }
        return null;
    }

}
