package tools.vitruv.framework.change.description.impl

import java.util.ArrayList
import tools.vitruv.framework.change.description.ConcreteChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.datatypes.VURI

abstract class AbstractConcreteChange implements ConcreteChange {
	protected EChange eChange
	val VURI vuri

	new(VURI vuri) {
		this.vuri = vuri
	}

	override containsConcreteChange() {
		true
	}

	override validate() {
		containsConcreteChange() && URI !== null
	}

	override getEChanges() {
		new ArrayList<EChange>(#[eChange])
	}

	override getURI() {
		vuri
	}

	override getEChange() {
		eChange
	}

	override applyBackward() {
		eChange.applyBackward
	}

	override applyForward() {
		eChange.applyForward
	}

}
