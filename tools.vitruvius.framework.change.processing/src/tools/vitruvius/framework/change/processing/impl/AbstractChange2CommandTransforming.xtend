package tools.vitruvius.framework.change.processing.impl

import tools.vitruvius.framework.userinteraction.UserInteracting
import java.util.ArrayList
import tools.vitruvius.framework.change.description.VitruviusChange
import java.util.List
import tools.vitruvius.framework.change.description.CompositeChange
import tools.vitruvius.framework.change.description.ConcreteChange
import org.apache.log4j.Logger
import tools.vitruvius.framework.correspondence.CorrespondenceModel
import tools.vitruvius.framework.change.processing.ChangeProcessor
import tools.vitruvius.framework.util.datatypes.MetamodelPair
import tools.vitruvius.framework.util.datatypes.VURI
import tools.vitruvius.framework.util.command.VitruviusRecordingCommand
import tools.vitruvius.framework.change.processing.Change2CommandTransforming

abstract class AbstractChange2CommandTransforming implements Change2CommandTransforming {
	private final static val LOGGER = Logger.getLogger(AbstractChange2CommandTransforming);
	
	private UserInteracting userInteracting;
	private val MetamodelPair metamodelPair;
	private val List<tools.vitruvius.framework.change.processing.ChangeProcessor> changeProcessors;
	
	new(VURI fromMetamodel, VURI toMetamodel) {
		this.metamodelPair = new MetamodelPair(fromMetamodel, toMetamodel);
		this.changeProcessors = new ArrayList<ChangeProcessor>();
	}
	 
	protected def UserInteracting getUserInteracting() {
		return userInteracting;
	}
	
	override MetamodelPair getTransformableMetamodels() {
		return metamodelPair;
	}
	
	protected def addChangeProcessor(ChangeProcessor changeProcessor) {
		this.changeProcessors += changeProcessor;
	}
	
	override List<VitruviusRecordingCommand> transformChange2Commands(VitruviusChange change, CorrespondenceModel correspondenceModel) {
		val commands = new ArrayList<VitruviusRecordingCommand>();
		this.processChange(change, correspondenceModel, commands);
		return commands;
	}

	private def dispatch void processChange(VitruviusChange change, CorrespondenceModel correspondenceModel, List<VitruviusRecordingCommand> commandList) {
		throw new IllegalArgumentException("Change subtype " + change.getClass().getName() + " not handled");
	}

	private def dispatch void processChange(CompositeChange change, CorrespondenceModel correspondenceModel, List<VitruviusRecordingCommand> commandList) {
		for (containedChange : change.changes) {
			processChange(containedChange, correspondenceModel, commandList);
		}
	}

	private def dispatch void processChange(ConcreteChange change, CorrespondenceModel correspondenceModel, List<VitruviusRecordingCommand> commandList) {
		var currentChange = change;
		for (changeProcessor : changeProcessors) {
			LOGGER.debug('''Calling change processor «changeProcessor» for change event «change»''');
			val processingResult = changeProcessor.transformChange(currentChange, correspondenceModel);
			currentChange = processingResult.resultingChange;
			commandList += processingResult.generatedCommands;
		}
	}
	
	override setUserInteracting(UserInteracting userInteracting) {
		this.userInteracting = userInteracting;
		this.cleanAndSetup();
	}

	private def void cleanAndSetup() {
		this.changeProcessors.clear();
		setup();
	}
	
	/**
	 * Adds the {@link ChangePreprocessor}s in order in which they are to be executed.
	 */
	protected abstract def void setup();
	
}
