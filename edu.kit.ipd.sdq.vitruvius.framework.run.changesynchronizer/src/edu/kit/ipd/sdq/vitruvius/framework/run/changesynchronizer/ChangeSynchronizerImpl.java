package edu.kit.ipd.sdq.vitruvius.framework.run.changesynchronizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.apache.log4j.Logger;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.URIHaving;
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
        this.blackboardHistory = new Stack<Blackboard>();
    }

    @Override
    public synchronized void synchronizeChange(final Change change) {
        synchronizeChanges(Arrays.asList(change));
    }

    @Override
    public synchronized List<List<Change>> synchronizeChanges(final List<Change> changes) {
        if (null == changes || 0 == changes.size()) {
            logger.warn("The change list does not contain any changes to synchronize." + changes);
            return Collections.emptyList();
        }
        for (SynchronisationListener syncListener : this.synchronisationListeners) {
            syncListener.syncStarted();
        }

        VURI sourceModelVURI = getSourceModelVURI(changes);
        Set<InternalCorrespondenceInstance> correspondenceInstances = this.correspondenceProviding
                .getOrCreateAllCorrespondenceInstances(sourceModelVURI);
        List<List<Change>> commandExecutionChanges = new ArrayList<List<Change>>(correspondenceInstances.size());
        for (InternalCorrespondenceInstance correspondenceInstance : correspondenceInstances) {
            VURI mmURI1 = correspondenceInstance.getMapping().getMetamodelA().getURI();
            VURI mmURI2 = correspondenceInstance.getMapping().getMetamodelB().getURI();
            Change2CommandTransforming change2CommandTransforming = this.change2CommandTransformingProviding
                    .getChange2CommandTransforming(mmURI1, mmURI2);
            Blackboard blackboard = new BlackboardImpl(correspondenceInstance, this.modelProviding);
            blackboard.pushChanges(changes);
            this.changePreparing.prepareAllChanges(blackboard);
            this.blackboardHistory.push(blackboard);
            change2CommandTransforming.transformChanges2Commands(blackboard);
            commandExecutionChanges.add(this.commandExecuting.executeCommands(blackboard));
        }

        // TODO: check invariants and execute undo if necessary

        for (SynchronisationListener syncListener : this.synchronisationListeners) {
            syncListener.syncFinished();
        }
        return commandExecutionChanges;
    }

    private VURI getSourceModelVURI(final List<Change> changesForTransformation) {
        if (null != changesForTransformation && 0 < changesForTransformation.size()) {
            Change firstChange = changesForTransformation.get(0);
            if (firstChange instanceof URIHaving) {
                return ((URIHaving) firstChange).getURI();
            }
            if (firstChange instanceof CompositeChange) {
                return getSourceModelVURI(((CompositeChange) firstChange).getChanges());
            }

        }
        return null;
    }

}
