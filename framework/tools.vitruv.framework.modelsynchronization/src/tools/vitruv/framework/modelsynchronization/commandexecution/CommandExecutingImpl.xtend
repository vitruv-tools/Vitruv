package tools.vitruv.framework.modelsynchronization.commandexecution

import tools.vitruv.framework.util.command.TransformationResult
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

class CommandExecutingImpl implements CommandExecuting {
	static final Logger logger = Logger::getLogger(typeof(CommandExecutingImpl).getSimpleName())

	override List<VitruviusChange> executeCommands(Blackboard blackboard) {
		val ModelRepository modelProviding = blackboard.getModelProviding()
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
		return Collections::emptyList()
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
				blackboard.getModelProviding().deleteModel(vuriToDelete)
			}
			for (Pair<EObject, VURI> createdEObjectVURIPair : transformationResult.getRootEObjectsToSave()) {
				blackboard.getModelProviding().
					createModel(createdEObjectVURIPair.getSecond(),
						createdEObjectVURIPair.getFirst())
			}
		}
	}

}
