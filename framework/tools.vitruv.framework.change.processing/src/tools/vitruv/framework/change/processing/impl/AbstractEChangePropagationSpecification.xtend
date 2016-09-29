package tools.vitruv.framework.change.processing.impl

import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.change.echange.EChange
import org.apache.log4j.Logger
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.util.command.TransformationResult

abstract class AbstractEChangePropagationSpecification extends AbstractChangePropagationSpecification {
	private final static val LOGGER = Logger.getLogger(AbstractEChangePropagationSpecification);
	
	new(UserInteracting userInteracting) {
		super(userInteracting);
	}
	
	override doesHandleChange(TransactionalChange change, CorrespondenceModel correspondenceModel) {
		for (eChange : change.getEChanges) {
			if (doesHandleChange(eChange, correspondenceModel)) {
				return true;
			}
		}
		return false;
	}
	
	override propagateChange(TransactionalChange change, CorrespondenceModel correspondenceModel) {
		val propagationResult = new TransformationResult();
		for (eChange : change.getEChanges) {
			LOGGER.debug('''Transforming eChange  «eChange» of change «change»''');
			val currentPropagationResult = propagateChange(eChange, correspondenceModel);
			propagationResult.integrateTransformationResult(currentPropagationResult);
		}
		
		return propagationResult;
	}
	
	protected def boolean doesHandleChange(EChange change, CorrespondenceModel correspondenceModel);
	protected def TransformationResult propagateChange(EChange change, CorrespondenceModel correspondenceModel);
	
}
			