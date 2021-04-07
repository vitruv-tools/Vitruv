package tools.vitruv.framework.propagation.impl

import tools.vitruv.framework.propagation.impl.CompositeChangePropagationSpecification
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.propagation.ResourceAccess
import tools.vitruv.framework.change.description.CompositeTransactionalChange
import org.apache.log4j.Logger

/**
 * This {@link ChangePropagationSpecification} acts just like the generic {@link CompositeChangePropagationSpecification}
 * but disassembles {@link CompositeTransactionalChanges} into their inner changes and applies the main change processors to each of them
 * instead of applying the main change processors to the complete {@link CompositeTransactionalChange}.
 * 
 */
class CompositeDecomposingChangePropagationSpecification extends CompositeChangePropagationSpecification {
	static val logger = Logger.getLogger(CompositeDecomposingChangePropagationSpecification);

	new(VitruvDomain sourceDomain, VitruvDomain targetDomain) {
		super(sourceDomain, targetDomain)
	}

	override propagateChangeViaMainprocessors(TransactionalChange change, CorrespondenceModel correspondenceModel,
		ResourceAccess resourceAccess) {
		propagateDecomposedChange(change, correspondenceModel, resourceAccess);
	}

	private def dispatch void propagateDecomposedChange(TransactionalChange change,
		CorrespondenceModel correspondenceModel, ResourceAccess resourceAccess) {
		for (changeProcessor : changeMainprocessors) {
			logger.trace('''Calling change mainprocessor «changeProcessor» for change event «change»''');
			changeProcessor.propagateChange(change, correspondenceModel, resourceAccess);
		}
	}

	private def dispatch void propagateDecomposedChange(CompositeTransactionalChange change,
		CorrespondenceModel correspondenceModel, ResourceAccess resourceAccess) {
		for (innerChange : change.changes) {
			propagateDecomposedChange(innerChange, correspondenceModel, resourceAccess);
		}
	}
}
