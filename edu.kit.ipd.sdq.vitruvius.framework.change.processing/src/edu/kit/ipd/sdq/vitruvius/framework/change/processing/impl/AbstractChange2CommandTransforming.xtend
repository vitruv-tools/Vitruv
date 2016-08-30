package edu.kit.ipd.sdq.vitruvius.framework.change.processing.impl

import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import java.util.ArrayList
import org.eclipse.emf.common.command.Command
import edu.kit.ipd.sdq.vitruvius.framework.change.description.VitruviusChange
import java.util.List
import edu.kit.ipd.sdq.vitruvius.framework.change.description.CompositeChange
import edu.kit.ipd.sdq.vitruvius.framework.change.description.ConcreteChange
import org.apache.log4j.Logger
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel
import edu.kit.ipd.sdq.vitruvius.framework.change.processing.ChangeProcessor
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.MetamodelPair
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI

abstract class AbstractChange2CommandTransforming implements edu.kit.ipd.sdq.vitruvius.framework.change.processing.Change2CommandTransforming {
	private final static val LOGGER = Logger.getLogger(AbstractChange2CommandTransforming);
	
	private UserInteracting userInteracting;
	private val MetamodelPair metamodelPair;
	private val List<edu.kit.ipd.sdq.vitruvius.framework.change.processing.ChangeProcessor> changeProcessors;
	
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
	
	override List<Command> transformChange2Commands(VitruviusChange change, CorrespondenceModel correspondenceModel) {
		val commands = new ArrayList<Command>();
		this.processChange(change, correspondenceModel, commands);
		return commands;
	}

	private def dispatch void processChange(VitruviusChange change, CorrespondenceModel correspondenceModel, List<Command> commandList) {
		throw new IllegalArgumentException("Change subtype " + change.getClass().getName() + " not handled");
	}

	private def dispatch void processChange(CompositeChange change, CorrespondenceModel correspondenceModel, List<Command> commandList) {
		for (containedChange : change.changes) {
			processChange(containedChange, correspondenceModel, commandList);
		}
	}

	private def dispatch void processChange(ConcreteChange change, CorrespondenceModel correspondenceModel, List<Command> commandList) {
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
