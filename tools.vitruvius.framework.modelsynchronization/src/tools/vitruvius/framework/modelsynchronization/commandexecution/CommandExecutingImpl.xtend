package tools.vitruvius.framework.modelsynchronization.commandexecution

import tools.vitruvius.framework.util.command.TransformationResult
import tools.vitruvius.framework.util.datatypes.VURI
import tools.vitruvius.framework.metamodel.ModelProviding
import tools.vitruvius.framework.util.command.VitruviusTransformationRecordingCommand
import tools.vitruvius.framework.util.datatypes.Pair
import java.util.ArrayList
import java.util.Collections
import java.util.HashSet
import java.util.List
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.common.command.Command
import org.eclipse.emf.ecore.EObject

import static extension tools.vitruvius.framework.util.bridges.CollectionBridge.*
import tools.vitruvius.framework.util.VitruviusConstants
import tools.vitruvius.framework.correspondence.Correspondences
import tools.vitruvius.framework.change.description.VitruviusChange
import tools.vitruvius.framework.correspondence.Correspondence
import tools.vitruvius.framework.tuid.TUID
import tools.vitruvius.framework.modelsynchronization.blackboard.Blackboard
import tools.vitruvius.framework.util.command.VitruviusRecordingCommand

class CommandExecutingImpl implements CommandExecuting {
	static final Logger logger = Logger::getLogger(typeof(CommandExecutingImpl).getSimpleName())

	override List<VitruviusChange> executeCommands(Blackboard blackboard) {
		val ModelProviding modelProviding = blackboard.getModelProviding()
		val ArrayList<Object> affectedObjects = new ArrayList()
		val ArrayList<TransformationResult> transformationResults = new ArrayList()
		for (VitruviusRecordingCommand command : blackboard.getAndArchiveCommandsForExecution()) {
			modelProviding.executeRecordingCommandOnTransactionalDomain(command);
			if (command instanceof VitruviusTransformationRecordingCommand) {
				transformationResults.add(command.transformationResult)
			}
			affectedObjects.addAll(command.getAffectedObjects().filter [ eObj |
				!(eObj instanceof Correspondence) && !(eObj instanceof Correspondences)])
		}
		this.executeTransformationResults(transformationResults, blackboard)
		this.saveAffectedEObjects(affectedObjects, blackboard.getModelProviding())
		modelProviding.detachTransactionalEditingDomain() // FIXME
		blackboard.correspondenceModel.saveModel()
		return Collections::emptyList()
	}

	/** 
	 * undo all commands on the blackboard
	 */
	override void rollbackCommands(Blackboard blackboard) {
		val Pair<List<VitruviusChange>, List<VitruviusRecordingCommand>> commandAndChangePairs = blackboard.getArchivedChangesAndCommandsForUndo()
		val ArrayList<Object> affectedObjects = new ArrayList()
		for (Command command : commandAndChangePairs.getSecond()) {
			command.undo()
			affectedObjects.addAll(command.getAffectedObjects())
		}
		this.saveAffectedEObjects(affectedObjects, blackboard.getModelProviding())
	}

	def private void executeTransformationResults(ArrayList<TransformationResult> transformationResults,
		Blackboard blackboard) {
		if (null === transformationResults) {
			return;
		}
		for (TransformationResult transformationResult : transformationResults) {
			if (null === transformationResult) {
				logger.info("Current TransformationResult is null. Can not save new root EObjects or delete VURIs.")
				return;
			}
			for (VURI vuriToDelete : transformationResult.getVUIRsToDelete()) {
				blackboard.getModelProviding().deleteModelInstanceOriginal(vuriToDelete)
			}
			for (Pair<EObject, VURI> createdEObjectVURIPair : transformationResult.getRootEObjectsToSave()) {
				val TUID oldTUID = blackboard.getCorrespondenceModel().calculateTUIDsFromEObjects(
					createdEObjectVURIPair.getFirst().toList).claimOne
				blackboard.getModelProviding().
					saveModelInstanceOriginalWithEObjectAsOnlyContent(createdEObjectVURIPair.getSecond(),
						createdEObjectVURIPair.getFirst(), oldTUID)
			}
 
		}

	}

	def private void saveAffectedEObjects(ArrayList<Object> affectedObjects, ModelProviding modelProviding) {
		val Set<VURI> vurisToSave = new HashSet<VURI>()
		for (Object object : affectedObjects) {
			if (object instanceof EObject) {
				val EObject eObject = object as EObject
				if (null !== eObject.eResource()) {
					val vuri = VURI::getInstance(eObject.eResource());
					if (!vuri.toString.startsWith(VitruviusConstants.getPathmapPrefix()))
						vurisToSave.add(vuri)
				}

			}

		}
		for (VURI vuri : vurisToSave) {
			modelProviding.saveExistingModelInstanceOriginal(vuri)
		}

	}
	
}
