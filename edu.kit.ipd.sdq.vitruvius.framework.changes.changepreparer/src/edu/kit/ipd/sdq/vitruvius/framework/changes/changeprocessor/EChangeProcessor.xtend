package edu.kit.ipd.sdq.vitruvius.framework.changes.changeprocessor

import edu.kit.ipd.sdq.vitruvius.framework.changes.changeprocessor.AbstractChangeProcessor
import edu.kit.ipd.sdq.vitruvius.framework.changes.changedescription.ConcreteChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceModel
import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.EChange
import org.apache.log4j.Logger
import org.eclipse.emf.common.command.Command
import java.util.List
import java.util.ArrayList
import edu.kit.ipd.sdq.vitruvius.framework.changes.changedescription.VitruviusChangeFactory
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting

abstract class EChangeProcessor extends AbstractChangeProcessor {
	private final static val LOGGER = Logger.getLogger(EChangeProcessor);
	
	new(UserInteracting userInteracting) {
		super(userInteracting);
	}
	
	override transformChange(ConcreteChange change, CorrespondenceModel correspondenceModel) {
		val commandList = new ArrayList<Command>();
		for (eChange : change.EChanges) {
			LOGGER.debug('''Transforming eChange  «eChange» of change «change»''');
			commandList += transformChange(eChange, correspondenceModel);
		}
		
		return new ChangeProcessorResult(VitruviusChangeFactory.instance.createEmptyChange(change.URI), commandList);
	}
	
	protected def List<Command> transformChange(EChange change, CorrespondenceModel correspondenceModel);
	
}