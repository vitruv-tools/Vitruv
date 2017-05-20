package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.description.TransactionalChange
import org.eclipse.emf.ecore.resource.ResourceSet

class EmptyChangeImpl implements TransactionalChange {
	val VURI vuri

	new(VURI vuri) {
		this.vuri = vuri
	}

	override containsConcreteChange() {
		true
	}

	override validate() {
		true
	}

	override getEChanges() {
		#[]
	}

	override getURI() {
		vuri
	}

	override applyBackward() throws IllegalStateException {
		// Nothing to be done
	}

	override applyForward() throws IllegalStateException {
		// Nothing to be done
	}

	override resolveBeforeAndApplyForward(ResourceSet resourceSet) {
	}

	override applyBackwardIfLegacy() {
		// Do nothing
	}
}
