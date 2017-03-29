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
import tools.vitruv.framework.util.command.ChangePropagationResult

class CommandExecutingImpl implements CommandExecuting {
	static final Logger logger = Logger::getLogger(typeof(CommandExecutingImpl).getSimpleName())

	// FIXME HK The sourceResource is only necessary for avoiding source models to be saved. Because that
	// logic has to be moved to the ChangePropagator, the sourceResource will then not be necessary anymore.
	override List<VitruviusChange> executeCommands(Blackboard blackboard) {
		val ModelRepository modelProviding = blackboard.getModelProviding()
		val ArrayList<EObject> affectedObjects = new ArrayList()
		val ArrayList<ChangePropagationResult> transformationResults = new ArrayList()
		for (VitruviusRecordingCommand command : blackboard.getAndArchiveCommandsForExecution()) {
			modelProviding.executeRecordingCommandOnTransactionalDomain(command);
			if (command instanceof VitruviusTransformationRecordingCommand) {
				transformationResults.add(command.transformationResult)
			}
			
			val changedEObjects = command.getAffectedObjects().filter [ eObj |
				!(eObj instanceof Correspondence) && !(eObj instanceof Correspondences)].filter(EObject)
			// FIXME HK The next filter tries to remove objects that were in a source model (in fact in a model that
			// has the same file extension than the source model). This is especially necessary to avoid overwriting
			// Java files that were modified but whose JaMoPP VSUM model is not up to date because changes were not made
			// to the EMF Model but in the text editor and recorded by the Java AST. Therefore, saving those models would
			// overwrite the changed ones.
			// This will hopefully not be necessary anymore if we replay recorded changes in the VSUM isolated from their
			// recording in editors.
//				.filter[eResource == null || sourceResource == null || 
//					!eResource.URI.fileExtension.equals(sourceResource.URI.fileExtension)
//				];
			changedEObjects.forEach[if (it.eResource != null) blackboard.pushChangedResource(it.eResource)];
		}
		this.executeTransformationResults(transformationResults, affectedObjects.filter(EObject), blackboard)
		return Collections::emptyList()
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
			//saveChangedModels(modifiedEObjects, blackboard.modelProviding)
			for (Pair<EObject, VURI> createdEObjectVURIPair : transformationResult.getRootEObjectsToSave()) {
				blackboard.getModelProviding().
					createModel(createdEObjectVURIPair.getSecond(),
						createdEObjectVURIPair.getFirst())
			}
		}
	}

}
