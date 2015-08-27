package edu.kit.ipd.sdq.vitruvius.commandexecuter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CommandExecuting;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.VitruviusRecordingCommand;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

public class CommandExecutingImpl implements CommandExecuting {
    @Override
    public void executeCommands(final Blackboard blackboard) {
        final ModelProviding modelProviding = blackboard.getModelProviding();
        modelProviding.attachTransactionalEditingDomain();
        final TransactionalEditingDomain domain = modelProviding.getTransactionalEditingDomain();

        final ArrayList<Object> affectedObjects = new ArrayList<>();
        final ArrayList<TransformationResult> transformationResults = new ArrayList<>();
        for (final Command command : blackboard.getAndArchiveCommandsForExecution()) {
            if (command instanceof VitruviusRecordingCommand) {
                ((VitruviusRecordingCommand) command).setTransactionDomain(domain);
            }
            domain.getCommandStack().execute(command);
            if (command instanceof VitruviusRecordingCommand) {
                transformationResults.add(((VitruviusRecordingCommand) command).getTransformationResult());
            }
            affectedObjects.addAll(command.getAffectedObjects());
            this.executeTransformationResults(transformationResults, blackboard.getModelProviding());
        }

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
            final ModelProviding modelProviding) {
        for (final TransformationResult transformationResult : transformationResults) {
            for (final VURI vuriToDelete : transformationResult.getVUIRsToDelete()) {
                // TODO: Check wheather we need a deleteModelInstanceOriginal in VSUM.
                // Here we usually do not need it because we usually delete JaMoPP resource that are
                // renamed. Hence we do not need to remove the correspondence models etc.However the
                // question is what happens if we delete, e.g. a PCM instance
                final ModelInstance mi = modelProviding.getAndLoadModelInstanceOriginal(vuriToDelete);
                final Resource resource = mi.getResource();
                try {
                    resource.delete(null);
                } catch (final IOException e) {
                    throw new RuntimeException("Could not delete VURI: " + vuriToDelete + ". Exception: " + e);
                }
            }
            for (final Pair<EObject, VURI> createdEObjectVURIPair : transformationResult.getRootEObjectsToSave()) {
                final ModelInstance mi = modelProviding
                        .getAndLoadModelInstanceOriginal(createdEObjectVURIPair.getSecond());
                final Resource resource = mi.getResource();
                // clear the resource first
                resource.getContents().clear();
                resource.getContents().add(createdEObjectVURIPair.getFirst());
                // get old tuid
                modelProviding.saveModelInstanceOriginal(mi.getURI());
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
            modelProviding.saveModelInstanceOriginal(vuri);
        }
    }

}
