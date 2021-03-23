package tools.vitruv.framework.propagation.impl

import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.change.echange.EChange
import org.apache.log4j.Logger
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.propagation.ResourceAccess

abstract class AbstractEChangePropagationSpecification extends AbstractChangePropagationSpecification {
	static val LOGGER = Logger.getLogger(AbstractEChangePropagationSpecification);

	new(VitruvDomain sourceDomain, VitruvDomain targetDomain) {
		super(sourceDomain, targetDomain);
	}

	override doesHandleChange(TransactionalChange change, CorrespondenceModel correspondenceModel) {
		for (eChange : change.getEChanges) {
			if (doesHandleChange(eChange, correspondenceModel)) {
				return true;
			}
		}
		return false;
	}

	override propagateChange(TransactionalChange change, CorrespondenceModel correspondenceModel,
		ResourceAccess resourceAccess) {
		for (eChange : change.getEChanges) {
			LOGGER.trace('''Transforming eChange  «eChange» of change «change»''');
			propagateChange(eChange, correspondenceModel, resourceAccess);
		}
	}

	protected def boolean doesHandleChange(EChange change, CorrespondenceModel correspondenceModel);

	protected def void propagateChange(EChange change, CorrespondenceModel correspondenceModel,
		ResourceAccess resourceAccess);

}
