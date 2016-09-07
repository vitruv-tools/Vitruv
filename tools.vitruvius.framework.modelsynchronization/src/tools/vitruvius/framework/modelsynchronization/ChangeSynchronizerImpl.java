package tools.vitruvius.framework.modelsynchronization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.change.FeatureChange;

import com.google.common.collect.EvictingQueue;

import tools.vitruvius.framework.change.description.CompositeChange;
import tools.vitruvius.framework.change.description.EMFModelChange;
import tools.vitruvius.framework.change.description.GeneralChange;
import tools.vitruvius.framework.change.description.VitruviusChange;
import tools.vitruvius.framework.change.preparation.ChangePreparing;
import tools.vitruvius.framework.change.preparation.ChangePreparingImpl;
import tools.vitruvius.framework.change.processing.Change2CommandTransforming;
import tools.vitruvius.framework.change.processing.Change2CommandTransformingProviding;
import tools.vitruvius.framework.correspondence.CorrespondenceModel;
import tools.vitruvius.framework.correspondence.CorrespondenceProviding;
import tools.vitruvius.framework.metamodel.Metamodel;
import tools.vitruvius.framework.metamodel.ModelProviding;
import tools.vitruvius.framework.modelsynchronization.blackboard.Blackboard;
import tools.vitruvius.framework.modelsynchronization.blackboard.impl.BlackboardImpl;
import tools.vitruvius.framework.modelsynchronization.commandexecution.CommandExecuting;
import tools.vitruvius.framework.modelsynchronization.commandexecution.CommandExecutingImpl;
import tools.vitruvius.framework.tuid.TUID;
import tools.vitruvius.framework.util.datatypes.VURI;

public class ChangeSynchronizerImpl implements ChangeSynchronizing {

    private static final int BLACKBOARD_HITORY_SIZE = 2;

    private static Logger logger = Logger.getLogger(ChangeSynchronizerImpl.class.getSimpleName());

    private final ModelProviding modelProviding;
    private final Change2CommandTransformingProviding change2CommandTransformingProviding;
    private final CorrespondenceProviding correspondenceProviding;
    private final ChangePreparing changePreparing;
    private final CommandExecuting commandExecuting;

    private Set<SynchronisationListener> synchronisationListeners;
    private Queue<Blackboard> blackboardHistory;

    public ChangeSynchronizerImpl(final ModelProviding modelProviding,
            final Change2CommandTransformingProviding change2CommandTransformingProviding,
            final CorrespondenceProviding correspondenceProviding,
            final SynchronisationListener synchronisationListener) {
        this.modelProviding = modelProviding;
        this.change2CommandTransformingProviding = change2CommandTransformingProviding;
        this.correspondenceProviding = correspondenceProviding;
        this.changePreparing = new ChangePreparingImpl();
        this.synchronisationListeners = new HashSet<SynchronisationListener>();
        if (null != synchronisationListener) {
            this.synchronisationListeners.add(synchronisationListener);
        }
        this.commandExecuting = new CommandExecutingImpl();
        this.blackboardHistory = EvictingQueue.create(BLACKBOARD_HITORY_SIZE);
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
        Set<CorrespondenceModel> correspondenceModels = this.correspondenceProviding
                .getOrCreateAllCorrespondenceModels(sourceModelVURI);

        rollbackChange(change);
        List<List<VitruviusChange>> commandExecutionChanges = new ArrayList<List<VitruviusChange>>();
        synchronizeSingleChange(change, correspondenceModels, commandExecutionChanges);

        // TODO: check invariants and execute undo if necessary

        for (SynchronisationListener syncListener : this.synchronisationListeners) {
            syncListener.syncFinished();
        }
        return commandExecutionChanges;
    }

    private void synchronizeSingleChange(final VitruviusChange change,
            final Set<CorrespondenceModel> correspondenceModels,
            final List<List<VitruviusChange>> commandExecutionChanges) {
        if (change instanceof CompositeChange) {
            for (VitruviusChange innerChange : ((CompositeChange) change).getChanges()) {
                synchronizeSingleChange(innerChange, correspondenceModels, commandExecutionChanges);
            }
        } else {
            Map<EObject, TUID> tuidMap = new HashMap<>();
            getOldObjectTUIDs(change, correspondenceModels.iterator().next(), tuidMap);
            change.prepare(this.changePreparing);
            if (change instanceof EMFModelChange) {
                ((EMFModelChange) change).getChangeDescription().applyAndReverse();
            }
            updateTUIDs(tuidMap, correspondenceModels.iterator().next());
            for (CorrespondenceModel correspondenceModel : correspondenceModels) {
                Metamodel mmA = correspondenceModel.getMapping().getMetamodelA();
                Metamodel mmB = correspondenceModel.getMapping().getMetamodelB();
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
                Blackboard blackboard = new BlackboardImpl(correspondenceModel, this.modelProviding,
                        this.correspondenceProviding);
                // TODO HK: Clone the changes for each synchronization! Should even be cloned for
                // each response that uses it,
                // or: make them read only, i.e. give them a read-only interface!
                blackboard.pushChanges(Collections.singletonList(change));
                this.blackboardHistory.add(blackboard);
                blackboard.pushCommands(change2CommandTransforming.transformChange2Commands(
                        blackboard.getAndArchiveChangesForTransformation().get(0), correspondenceModel));
                commandExecutionChanges.add(this.commandExecuting.executeCommands(blackboard));
            }
        }
    }

    private void getOldObjectTUIDs(final VitruviusChange recordedChange, final CorrespondenceModel correspondenceModel,
            final Map<EObject, TUID> tuidMap) {
        if (recordedChange instanceof EMFModelChange) {
            EMFModelChange change = (EMFModelChange) recordedChange;
            List<EObject> objects = new ArrayList<EObject>();
            objects.addAll(change.getChangeDescription().getObjectChanges().keySet());
            objects.addAll(change.getChangeDescription().getObjectsToDetach());
            for (EObject object : change.getChangeDescription().getObjectChanges().keySet()) {
                TUID tuid = correspondenceModel.calculateTUIDFromEObject(object);
                if (tuid != null) {
                    tuidMap.put(object, tuid);
                    for (FeatureChange featureChange : change.getChangeDescription().getObjectChanges().get(object)) {
                        tuidMap.put(featureChange.getReferenceValue(),
                                correspondenceModel.calculateTUIDFromEObject(featureChange.getReferenceValue()));
                    }
                }
            }
            for (EObject object : change.getChangeDescription().getObjectsToDetach()) {
                TUID tuid = correspondenceModel.calculateTUIDFromEObject(object);
                if (tuid != null) {
                    tuidMap.put(object, tuid);
                }
            }
        } else if (recordedChange instanceof GeneralChange) {
            // for (EChange eChange : recordedChange.getEChanges()) {
            // if (eChange instanceof JavaFeatureEChange<?, ?>) {
            // if (((JavaFeatureEChange<?, ?>) eChange).getOldAffectedEObject() != null) {
            // JavaFeatureEChange<?, ?> javaFeatureEChange = (JavaFeatureEChange<?, ?>) eChange;
            // TUID tuid = correspondenceModel
            // .calculateTUIDFromEObject(javaFeatureEChange.getOldAffectedEObject());
            // if (tuid != null && javaFeatureEChange.getAffectedEObject() != null) {
            // tuidMap.put(javaFeatureEChange.getAffectedEObject(), tuid);
            // }
            // }
            // }
            // }
        } else if (recordedChange instanceof CompositeChange) {
            CompositeChange change = (CompositeChange) recordedChange;
            for (VitruviusChange innerChange : change.getChanges()) {
                getOldObjectTUIDs(innerChange, correspondenceModel, tuidMap);
            }
        }
    }

    protected void updateTUIDs(final Map<EObject, TUID> tuidMap, final CorrespondenceModel correspondenceModel) {
        // TODO HK There is something wrong with transactions if we have to start a transaction to
        // update the TUID here.
        // Possibilities:
        // 1. There should not be an active transaction when this method is called
        // 2. The TUID mechanism is refactored so that only the TUID object is modified and no other
        // resources
        for (final EObject object : tuidMap.keySet()) { // TODO HK add filter null in Xtend
            final TUID newTUID = correspondenceModel.calculateTUIDFromEObject(object);
            if (newTUID != null) {
                this.modelProviding.createRecordingCommandAndExecuteCommandOnTransactionalDomain(new Callable<Void>() {
                    @Override
                    public Void call() {
                        correspondenceModel.updateTUID(tuidMap.get(object), newTUID);
                        return null;
                    }
                });
            }
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

}
