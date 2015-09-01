package edu.kit.ipd.sdq.vitruvius.commandexecuter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CommandExecuting;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.VitruviusRecordingCommand;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondences;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

public class CommandExecutingImpl implements CommandExecuting {

    private static final Logger logger = Logger.getLogger(CommandExecutingImpl.class.getSimpleName());

    @Override
    public void executeCommands(final Blackboard blackboard) {
        final ModelProviding modelProviding = blackboard.getModelProviding();
        final TransactionalEditingDomain domain = modelProviding.getTransactionalEditingDomain();

        final ArrayList<Object> affectedObjects = new ArrayList<>();
        final ArrayList<TransformationResult> transformationResults = new ArrayList<>();
        for (final Command command : blackboard.getAndArchiveCommandsForExecution()) {
            if (command instanceof VitruviusRecordingCommand) {
                ((VitruviusRecordingCommand) command).setTransactionDomain(domain);
            }
            domain.getCommandStack().execute(command);
            if (command instanceof VitruviusRecordingCommand) {
                final TransformationResult transformationResult = ((VitruviusRecordingCommand) command).getTransformationResult();
				transformationResults.add(transformationResult);
            }
            affectedObjects.addAll(command.getAffectedObjects().stream().filter(it -> !(it instanceof Correspondence) && !(it instanceof Correspondences)).collect(Collectors.toList()));
        }
        this.executeTransformationResults(transformationResults, blackboard);
        this.saveAffectedEObjects(affectedObjects, blackboard.getModelProviding());
        modelProviding.detachTransactionalEditingDomain();
    }

    /**
     * undo all commands on the blackboard
     */
    @Override
    public void rollbackCommands(final Blackboard blackboard) {
        final Pair<List<Change>, List<Command>> commandAndChangePairs = blackboard
                .getArchivedChangesAndCommandsForUndo();
        final ArrayList<Object> affectedObjects = new ArrayList<>();
        for (final Command command : commandAndChangePairs.getSecond()) {
            command.undo();
            affectedObjects.addAll(command.getAffectedObjects());
        }
        this.saveAffectedEObjects(affectedObjects, blackboard.getModelProviding());
    }

    private void executeTransformationResults(final ArrayList<TransformationResult> transformationResults,
            final Blackboard blackboard) {
        if (null == transformationResults) {
            return;
        }
        for (final TransformationResult transformationResult : transformationResults) {
            if (null == transformationResult) {
                logger.info("Current TransformationResult is null. Can not save new root EObjects or delete VURIs.");
                return;
            }
            for (final VURI vuriToDelete : transformationResult.getVUIRsToDelete()) {
                blackboard.getModelProviding().deleteModelInstanceOriginal(vuriToDelete);
            }
            for (final Pair<EObject, VURI> createdEObjectVURIPair : transformationResult.getRootEObjectsToSave()) {
                final TUID oldTUID = blackboard.getCorrespondenceInstance()
                        .calculateTUIDFromEObject(createdEObjectVURIPair.getFirst());
                blackboard.getModelProviding().saveModelInstanceOriginalWithEObjectAsOnlyContent(
                        createdEObjectVURIPair.getSecond(), createdEObjectVURIPair.getFirst(), oldTUID);
            }
        }
    }

    private void saveAffectedEObjects(final ArrayList<Object> affectedObjects, final ModelProviding modelProviding) {
        final Set<VURI> vurisToSave = new HashSet<VURI>();
        for (final Object object : affectedObjects) {
            if (object instanceof EObject) {
                final EObject eObject = (EObject) object;
                if (null != eObject.eResource()) {
                    vurisToSave.add(VURI.getInstance(eObject.eResource()));
                }
            }
        }
        for (final VURI vuri : vurisToSave) {
            modelProviding.saveExistingModelInstanceOriginal(vuri);
        }
    }

}
