package edu.kit.ipd.sdq.vitruvius.commandexecuter

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CommandExecuting
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFCommandBridge
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.VitruviusTransformationRecordingCommand
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair
import java.util.ArrayList
import java.util.Collections
import java.util.HashSet
import java.util.List
import java.util.Set
import java.util.stream.Collectors
import org.apache.log4j.Logger
import org.eclipse.emf.common.command.Command
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.transaction.TransactionalEditingDomain

import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge.*
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondences

class CommandExecutingImpl implements CommandExecuting {
	static final Logger logger = Logger::getLogger(typeof(CommandExecutingImpl).getSimpleName())

	override List<Change> executeCommands(Blackboard blackboard) {
		val ModelProviding modelProviding = blackboard.getModelProviding()
		val TransactionalEditingDomain domain = modelProviding.getTransactionalEditingDomain()
		val ArrayList<Object> affectedObjects = new ArrayList()
		val ArrayList<TransformationResult> transformationResults = new ArrayList()
		for (Command command : blackboard.getAndArchiveCommandsForExecution()) {
			if (command instanceof VitruviusTransformationRecordingCommand) {
				val TransformationResult transformationResult = EMFCommandBridge::
					executeVitruviusRecordingCommand(modelProviding, command as VitruviusTransformationRecordingCommand)
				transformationResults.add(transformationResult)
			} else {
				domain.getCommandStack().execute(command)
			}
			affectedObjects.addAll(command.getAffectedObjects().filter [ eObj |
				!(eObj instanceof Correspondence) && !(eObj instanceof Correspondences)])
		}
		this.executeTransformationResults(transformationResults, blackboard)
		this.saveAffectedEObjects(affectedObjects, blackboard.getModelProviding())
		modelProviding.detachTransactionalEditingDomain() // FIXME
		return Collections::emptyList()
	}

	/** 
	 * undo all commands on the blackboard
	 */
	override void rollbackCommands(Blackboard blackboard) {
		val Pair<List<Change>, List<Command>> commandAndChangePairs = blackboard.getArchivedChangesAndCommandsForUndo()
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
				val TUID oldTUID = blackboard.getCorrespondenceInstance().calculateTUIDsFromEObjects(
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
					vurisToSave.add(VURI::getInstance(eObject.eResource()))
				}

			}

		}
		for (VURI vuri : vurisToSave) {
			modelProviding.saveExistingModelInstanceOriginal(vuri)
		}

	}

}
