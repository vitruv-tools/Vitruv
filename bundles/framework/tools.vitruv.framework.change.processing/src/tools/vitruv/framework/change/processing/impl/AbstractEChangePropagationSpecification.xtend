package tools.vitruv.framework.change.processing.impl

import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.change.echange.EChange
import org.apache.log4j.Logger
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.util.command.ChangePropagationResult
import tools.vitruv.framework.domains.VitruvDomain

abstract class AbstractEChangePropagationSpecification extends AbstractChangePropagationSpecification {
	static val LOGGER = Logger::getLogger(AbstractEChangePropagationSpecification)

	new(UserInteracting userInteracting, VitruvDomain sourceDomain, VitruvDomain targetDomain) {
		super(userInteracting, sourceDomain, targetDomain)
	}

	override doesHandleChange(TransactionalChange change, CorrespondenceModel correspondenceModel) {
		for (eChange : change.getEChanges) {
			if (doesHandleChange(eChange, correspondenceModel)) {
				return true
			}
		}
		false
	}

	override propagateChange(TransactionalChange change, CorrespondenceModel correspondenceModel) {
		val propagationResult = new ChangePropagationResult
		change.EChanges.forEach [
			LOGGER.debug('''Transforming eChange  «it» of change «change»''')
			val currentPropagationResult = propagateChange(it, correspondenceModel)
			propagationResult.integrateResult(currentPropagationResult)
		]
		propagationResult
	}

	protected def boolean doesHandleChange(EChange change, CorrespondenceModel correspondenceModel)

	protected def ChangePropagationResult propagateChange(EChange change, CorrespondenceModel correspondenceModel)

}
