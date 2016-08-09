package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime

import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import org.apache.log4j.Logger
import java.util.List
import org.eclipse.emf.common.command.Command
import java.util.ArrayList
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.userinteractor.UserInteractor
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.CompositeChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.EMFModelChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.ConcreteChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VitruviusChange

abstract class AbstractResponseChange2CommandTransforming implements Change2CommandTransforming {
	private final static val LOGGER = Logger.getLogger(AbstractResponseChange2CommandTransforming);
	
	private List<AbstractResponseExecutor> responseExecutors;

	protected UserInteracting userInteracting;
	
	private List<Change2CommandTransformingPreprocessor> preprocessors;
	
	new() {
		this.responseExecutors = new ArrayList<AbstractResponseExecutor>();
		this.preprocessors = new ArrayList<Change2CommandTransformingPreprocessor>();
		setUserInteracting(new UserInteractor());
	}

	protected def void addResponseExecutor(AbstractResponseExecutor executor) {
		this.responseExecutors.add(executor);
	}
	
	public def void addPreprocessor(Change2CommandTransformingPreprocessor preprocessor) {
		this.preprocessors += preprocessor;
	}
	
	override transformChanges2Commands(Blackboard blackboard) {
		val changes = blackboard.getAndArchiveChangesForTransformation();
		val commands = new ArrayList<Command>();

		for (VitruviusChange change : changes) {
			commands.addAll(this.processChange(change, blackboard));
		}

		blackboard.pushCommands(commands);
	}

	private def List<Command> processChange(VitruviusChange change, Blackboard blackboard) {
		val result = new ArrayList<Command>();
		for (preprocessor : preprocessors) {
			if (preprocessor.doesProcess(change)) {
				result.addAll(preprocessor.processChange(change, userInteracting, blackboard));	
			}
		}
		result.addAll(change.handleChange(blackboard));
		return result;
	}
	
	private def dispatch List<Command> handleChange(VitruviusChange change, Blackboard blackboard) {
		throw new IllegalArgumentException("Change subtype " + change.getClass().getName() + " not handled");
	}
	
	private def dispatch List<Command> handleChange(CompositeChange change, Blackboard blackboard) {
		val result = new ArrayList<Command>();
		for (VitruviusChange c : change.getChanges()) {
			result.addAll(this.processChange(c, blackboard));
		}
		return result
	}
	
	private def dispatch List<Command> handleChange(ConcreteChange change, Blackboard blackboard) {
		val result = new ArrayList<Command>();
		for (eChange : change.getEChanges()) {
			for (executor : responseExecutors) {
				LOGGER.debug('''Calling executor «executor» for change event «change»''');
				result += executor.generateCommandsForEvent(eChange, blackboard.correspondenceInstance);	
			}
		}
		return result
	}
	
	override setUserInteracting(UserInteracting userInteracting) {
		this.userInteracting = userInteracting;
		this.cleanAndSetup();
	}

	private def void cleanAndSetup() {
		this.responseExecutors.clear();
		setup();
	}
	
	protected abstract def void setup();
	
}