package tools.vitruv.framework.modelsynchronization.commandexecution

import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.util.datatypes.Pair
import java.util.ArrayList
import java.util.Collections
import java.util.List
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject

import tools.vitruv.framework.correspondence.Correspondences
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.correspondence.Correspondence
import tools.vitruv.framework.modelsynchronization.blackboard.Blackboard
import tools.vitruv.framework.util.command.VitruviusRecordingCommand
import tools.vitruv.framework.metamodel.ModelRepository
import tools.vitruv.framework.util.command.VitruviusTransformationRecordingCommand
import tools.vitruv.framework.util.VitruviusConstants
import tools.vitruv.framework.util.command.ChangePropagationResult

class CommandExecutingImpl implements CommandExecuting {
	static final Logger logger = Logger::getLogger(typeof(CommandExecutingImpl).getSimpleName())

	override List<VitruviusChange> executeCommands(Blackboard blackboard) {
		val ModelRepository modelProviding = blackboard.getModelProviding()
		val ArrayList<Object> affectedObjects = new ArrayList()
		val ArrayList<ChangePropagationResult> transformationResults = new ArrayList()
		for (VitruviusRecordingCommand command : blackboard.getAndArchiveCommandsForExecution()) {
			modelProviding.executeRecordingCommandOnTransactionalDomain(command);
			if (command instanceof VitruviusTransformationRecordingCommand) {
				transformationResults.add(command.transformationResult)
			}
			affectedObjects.addAll(command.getAffectedObjects().filter [ eObj |
				!(eObj instanceof Correspondence) && !(eObj instanceof Correspondences)])
//			modelProviding.executeRecordingCommandOnTransactionalDomain(EMFCommandBridge.createVitruviusTransformationRecordingCommand([|
//				command.undo
//				for (affectedObject : affectedObjects.filter(EObject)) {
//					TuidManager.instance.registerObjectUnderModification(affectedObject);
//				}
//				command.redo();
//				for (affectedObject : affectedObjects.filter(EObject)) {
//					TuidManager.instance.updateTuidsOfRegisteredObjects;
//					TuidManager.instance.flushRegisteredObjectsUnderModification;
//				}
//				return null;
//			]));
		}
		this.executeTransformationResults(transformationResults, affectedObjects.filter(EObject), blackboard)
		return Collections::emptyList()
	}

	def private void saveChangedModels(Iterable<EObject> affectedObjects, ModelRepository modelRepository) {
		for (EObject eObject : affectedObjects) {
			if (null !== eObject.eResource()) {
				val vuri = VURI::getInstance(eObject.eResource());
				// FIXME HK We do not save Java files at the moment, because AST changes are not correctly
				// reflected in the JaMoPP models, so that saving JaMoPP models overwrites AST changes
				if (!vuri.toString.startsWith(VitruviusConstants.getPathmapPrefix()) && !vuri.fileExtension.endsWith("java")) {
					eObject.eResource.modified = true;
				}
			}
		}
		// FIXME This should be done by the VSUM, not by the command executor«
		modelRepository.saveAllModels();
	}

	def private void executeTransformationResults(ArrayList<ChangePropagationResult> transformationResults, Iterable<EObject> modifiedEObjects,
		Blackboard blackboard) {
		if (null === transformationResults) {
			return;
		}
		for (ChangePropagationResult transformationResult : transformationResults) {
			if (null === transformationResult) {
				logger.info("Current TransformationResult is null. Can not save new root EObjects or delete VURIs.")
				return;
			}
			for (VURI vuriToDelete : transformationResult.getVurisToDelete()) {
				blackboard.getModelProviding().deleteModel(vuriToDelete)
			}
			saveChangedModels(modifiedEObjects, blackboard.modelProviding)
			for (Pair<EObject, VURI> createdEObjectVURIPair : transformationResult.getRootEObjectsToSave()) {
				blackboard.getModelProviding().
					createModel(createdEObjectVURIPair.getSecond(),
						createdEObjectVURIPair.getFirst())
			}
		}
	}

}
