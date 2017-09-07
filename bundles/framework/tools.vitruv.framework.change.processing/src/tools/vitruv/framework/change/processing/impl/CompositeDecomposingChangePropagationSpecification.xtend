package tools.vitruv.framework.change.processing.impl

import tools.vitruv.framework.change.processing.impl.CompositeChangePropagationSpecification
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.util.command.ResourceAccess
import tools.vitruv.framework.change.description.CompositeTransactionalChange
import org.apache.log4j.Logger
import tools.vitruv.framework.change.echange.AtomicEChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.echange.compound.CompoundEChange
import java.util.Collections

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import tools.vitruv.framework.change.description.ConcreteChange

/**
 * This {@link ChangePropagationSpecification} acts just like the generic {@link CompositeChangePropagationSpecification}
 * but disassembles {@link CompositeTransactionalChanges} into their inner changes and applies the main change processors to each of them
 * instead of applying the main change processors to the complete {@link CompositeTransactionalChange}.
 * 
 */
class CompositeDecomposingChangePropagationSpecification extends CompositeChangePropagationSpecification {
	private static val logger = Logger.getLogger(CompositeDecomposingChangePropagationSpecification);

	new(VitruvDomain sourceDomain, VitruvDomain targetDomain) {
		super(sourceDomain, targetDomain)
	}

	override propagateChangeViaMainprocessors(TransactionalChange change, CorrespondenceModel correspondenceModel,
		ResourceAccess resourceAccess) {
		propagateDecomposedChange(change, correspondenceModel, resourceAccess);
	}

	private def dispatch void propagateDecomposedChange(TransactionalChange change,
		CorrespondenceModel correspondenceModel, ResourceAccess resourceAccess) {
		for (rewrappedChange : change.repackChanges) {
			for (changeProcessor : changeMainprocessors) {
				logger.debug('''Calling change mainprocessor «changeProcessor» for change event «change»''');
				changeProcessor.propagateChange(rewrappedChange, correspondenceModel, resourceAccess);
			}
		}
	}

	private def repackChanges(TransactionalChange transactionalChange) {
		transactionalChange.EChanges.flatMap [wrapInTransactionalChange]
	}

	private def dispatch Iterable<ConcreteChange> wrapInTransactionalChange(AtomicEChange atomicChange) {
		Collections.singleton(VitruviusChangeFactory.instance.createConcreteApplicableChange(atomicChange))
	}

	private def dispatch Iterable<ConcreteChange> wrapInTransactionalChange(CompoundEChange compoundChange) {
		val atomicWrapped = compoundChange.atomicChanges.flatMap [wrapInTransactionalChange]
		val compoundWrapped = Collections.singleton(VitruviusChangeFactory.instance.createConcreteApplicableChange(compoundChange))
		return atomicWrapped + compoundWrapped
	}

	private def dispatch void propagateDecomposedChange(CompositeTransactionalChange change,
		CorrespondenceModel correspondenceModel, ResourceAccess resourceAccess) {
		for (innerChange : change.changes) {
			propagateDecomposedChange(innerChange, correspondenceModel, resourceAccess);
		}
	}
}
