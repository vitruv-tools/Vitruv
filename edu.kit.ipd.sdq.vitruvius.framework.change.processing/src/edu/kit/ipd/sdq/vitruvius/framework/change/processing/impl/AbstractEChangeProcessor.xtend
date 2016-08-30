package edu.kit.ipd.sdq.vitruvius.framework.change.processing.impl

import edu.kit.ipd.sdq.vitruvius.framework.change.processing.impl.AbstractChangeProcessor
import edu.kit.ipd.sdq.vitruvius.framework.change.description.ConcreteChange
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.EChange
import org.apache.log4j.Logger
import org.eclipse.emf.common.command.Command
import java.util.List
import java.util.ArrayList
import edu.kit.ipd.sdq.vitruvius.framework.change.description.VitruviusChangeFactory
import edu.kit.ipd.sdq.vitruvius.framework.userinteraction.UserInteracting

abstract class AbstractEChangeProcessor extends AbstractChangeProcessor {
	private final static val LOGGER = Logger.getLogger(AbstractEChangeProcessor);
	
	new(UserInteracting userInteracting) {
		super(userInteracting);
	}
	
	override transformChange(ConcreteChange change, CorrespondenceModel correspondenceModel) {
		val commandList = new ArrayList<Command>();
		for (eChange : change.getEChanges) {
			LOGGER.debug('''Transforming eChange  «eChange» of change «change»''');
			commandList += transformChange(eChange, correspondenceModel);
		}
		
		return new edu.kit.ipd.sdq.vitruvius.framework.change.processing.ChangeProcessorResult(VitruviusChangeFactory.instance.createEmptyChange(change.getURI), commandList);
	}
	
	protected def List<Command> transformChange(EChange change, CorrespondenceModel correspondenceModel);
	
}
			