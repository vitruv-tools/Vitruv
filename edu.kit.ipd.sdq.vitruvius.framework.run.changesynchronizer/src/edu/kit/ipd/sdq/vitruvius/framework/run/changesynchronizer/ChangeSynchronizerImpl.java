package edu.kit.ipd.sdq.vitruvius.framework.run.changesynchronizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.FeatureChange;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.GenericCompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VitruviusChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstanceDecorator;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID;
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
    public synchronized List<List<VitruviusChange>> synchronizeChange(final VitruviusChange change) {
        if (change == null || !change.containsConcreteChange()) {
            logger.warn("The change does not contain any changes to synchronize." + change);
            return Collections.emptyList();
        }
        if (!change.validate()) {
            logger.error("Change contains changes from different models." + change);
            return Collections.emptyList();
        }
        for (SynchronisationListener syncListener : this.synchronisationListeners) {
            syncListener.syncStarted();
        }

        VURI sourceModelVURI = change.getURI();

        // FIXME HK: This is all strange: the sourceModelVURI is taken from the first change,
        // although they can be from different ones. Why not make it for each change independently?
        Set<CorrespondenceInstanceDecorator> correspondenceInstances = this.correspondenceProviding
                .getOrCreateAllCorrespondenceInstances(sourceModelVURI);

        rollbackChange(change);
        List<List<VitruviusChange>> commandExecutionChanges = new ArrayList<List<VitruviusChange>>();
        synchronizeSingleChange(change, correspondenceInstances, commandExecutionChanges);

        // TODO: check invariants and execute undo if necessary

        for (SynchronisationListener syncListener : this.synchronisationListeners) {
            syncListener.syncFinished();
        }
        return commandExecutionChanges;
    }

    private void synchronizeSingleChange(final VitruviusChange change,
            final Set<CorrespondenceInstanceDecorator> correspondenceInstances,
            final List<List<VitruviusChange>> commandExecutionChanges) {
        if (change instanceof CompositeChange) {
            for (VitruviusChange innerChange : ((CompositeChange) change).getChanges()) {
                synchronizeSingleChange(innerChange, correspondenceInstances, commandExecutionChanges);
            }
        } else {
            Map<EObject, TUID> tuidMap = new HashMap<>();
            getOldObjectTUIDs(change, correspondenceInstances.iterator().next(), tuidMap);
            change.prepare(this.changePreparing);
            if (change instanceof EMFModelChange) {
                ((EMFModelChange) change).getChangeDescription().applyAndReverse();
            }
            updateTUIDs(tuidMap, correspondenceInstances.iterator().next());
            for (CorrespondenceInstanceDecorator correspondenceInstance : correspondenceInstances) {
                Metamodel mmA = correspondenceInstance.getMapping().getMetamodelA();
                Metamodel mmB = correspondenceInstance.getMapping().getMetamodelB();
                // assume mmaA is source metamodel
                VURI sourceMMURI = mmA.getURI();
                VURI targetMMURI = mmB.getURI();
                if (!Arrays.asList(mmA.getFileExtensions()).contains(change.getURI().getFileExtension())) {
                    VURI tmpURI = sourceMMURI;
                    sourceMMURI = targetMMURI;
                    targetMMURI = tmpURI;
                }
                Change2CommandTransforming change2CommandTransforming = this.change2CommandTransformingProviding
                        .getChange2CommandTransforming(sourceMMURI, targetMMURI);
                Blackboard blackboard = new BlackboardImpl(correspondenceInstance, this.modelProviding,
                        this.correspondenceProviding);
                // TODO HK: Clone the changes for each synchronization! Should even be cloned for
                // each response that uses it,
                // or: make them read only, i.e. give them a read-only interface!
                blackboard.pushChanges(Collections.singletonList(change));
                this.blackboardHistory.push(blackboard);
                change2CommandTransforming.transformChanges2Commands(blackboard);
                commandExecutionChanges.add(this.commandExecuting.executeCommands(blackboard));
            }
        }
    }

    private void getOldObjectTUIDs(final VitruviusChange recordedChange,
            final CorrespondenceInstanceDecorator correspondenceInstance, final Map<EObject, TUID> tuidMap) {
        if (recordedChange instanceof EMFModelChange) {
            EMFModelChange change = (EMFModelChange) recordedChange;
            for (EObject object : change.getChangeDescription().getObjectChanges().keySet()) {
                TUID tuid = correspondenceInstance.calculateTUIDFromEObject(object);
                if (tuid != null) {
                    tuidMap.put(object, tuid);
                    for (FeatureChange featureChange : change.getChangeDescription().getObjectChanges().get(object)) {
                        tuidMap.put(featureChange.getReferenceValue(),
                                correspondenceInstance.calculateTUIDFromEObject(featureChange.getReferenceValue()));
                    }
                }
            }
        } else if (recordedChange instanceof CompositeChange) {
            CompositeChange change = (CompositeChange) recordedChange;
            for (VitruviusChange innerChange : change.getChanges()) {
                getOldObjectTUIDs(innerChange, correspondenceInstance, tuidMap);
            }
        }
    }

    private void updateTUIDs(final Map<EObject, TUID> tuidMap,
            final CorrespondenceInstanceDecorator correspondenceInstance) {
        for (EObject object : tuidMap.keySet()) { // TODO HK add filter null in Xtend
            correspondenceInstance.updateTUID(tuidMap.get(object), object);
        }
    }

    private void rollbackChange(final VitruviusChange change) {
        if (change instanceof CompositeChange) {
            List<VitruviusChange> innerChanges = ((CompositeChange) change).getChanges();
            for (int i = innerChanges.size() - 1; i >= 0; i--) {
                rollbackChange(innerChanges.get(i));
            }
        } else if (change instanceof EMFModelChange) {
            ((EMFModelChange) change).getChangeDescription().applyAndReverse();
        }
    }

    private void applyChanges(final List<VitruviusChange> changes) {
        for (VitruviusChange change : changes) {
            if (change instanceof EMFModelChange) {
                ChangeDescription descr = ((EMFModelChange) change).getChangeDescription();
                if (descr != null)
                    descr.applyAndReverse();
            } else if (change instanceof GenericCompositeChange) {
                applyChanges(((GenericCompositeChange) change).getChanges());
            }
        }
    }

}
