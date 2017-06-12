package tools.vitruv.framework.change.description.impl

import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.change.description.CompositeTransactionalChange
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory

class CompositeTransactionalChangeImpl extends AbstractCompositeChangeImpl<TransactionalChange> implements CompositeTransactionalChange {
	override removeChange(TransactionalChange change) {
		if (change !== null && changes.contains(change)) {
			if (changes.size === 1) {
				val emptyChange = VitruviusChangeFactory::instance.createEmptyChange(change.URI)
				changes += emptyChange
			}
		}
		super.removeChange(change)
	}

	override resolveBeforeAndApplyForward(ResourceSet resourceSet) {
		changes.forEach[resolveBeforeAndApplyForward(resourceSet)]
	}
}
