package edu.kit.ipd.sdq.vitruvius.framework.changes.changeprocessor

import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationMetamodelPair
import java.util.ArrayList
import org.eclipse.emf.common.command.Command
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VitruviusChange
import java.util.List
import edu.kit.ipd.sdq.vitruvius.framework.changes.changedescription.CompositeChange
import edu.kit.ipd.sdq.vitruvius.framework.changes.changedescription.ConcreteChange
import org.apache.log4j.Logger

abstract class AbstractChange2CommandTransforming implements Change2CommandTransforming {
	private final static val LOGGER = Logger.getLogger(AbstractChange2CommandTransforming);
	
	private UserInteracting userInteracting;
	private val TransformationMetamodelPair transformationMetamodelPair;
	private val List<ChangeProcessor> changeProcessors;
	
	new(TransformationMetamodelPair metamodelPair) {
		this.transformationMetamodelPair = metamodelPair;
		this.changeProcessors = new ArrayList<ChangeProcessor>();
	}
	 
	protected def UserInteracting getUserInteracting() {
		return userInteracting;
	}
	
	override TransformationMetamodelPair getTransformableMetamodels() {
		return transformationMetamodelPair;
	}
	
	protected def addChangeProcessor(ChangeProcessor changeProcessor) {
		this.changeProcessors += changeProcessor;
	}
	
	override transformChanges2Commands(Blackboard blackboard) {
		val changes = blackboard.getAndArchiveChangesForTransformation();
		val commands = new ArrayList<Command>();

		for (VitruviusChange change : changes) {
			this.processChange(change, blackboard, commands);
		}

		blackboard.pushCommands(commands);
	}

	private def dispatch void processChange(VitruviusChange change, Blackboard blackboard, List<Command> commandList) {
		throw new IllegalArgumentException("Change subtype " + change.getClass().getName() + " not handled");
	}

	private def dispatch void processChange(CompositeChange change, Blackboard blackboard, List<Command> commandList) {
		for (containedChange : change.changes) {
			processChange(containedChange, blackboard, commandList);
		}
	}

	private def dispatch void processChange(ConcreteChange change, Blackboard blackboard, List<Command> commandList) {
		var currentChange = change;
		for (changeProcessor : changeProcessors) {
			LOGGER.debug('''Calling change processor «changeProcessor» for change event «change»''');
			val processingResult = changeProcessor.transformChange(currentChange, blackboard.correspondenceModel);
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