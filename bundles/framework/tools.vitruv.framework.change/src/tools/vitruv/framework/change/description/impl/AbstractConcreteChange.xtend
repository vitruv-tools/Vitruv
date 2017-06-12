package tools.vitruv.framework.change.description.impl

import java.util.ArrayList
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.change.description.ConcreteChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.datatypes.VURI

abstract class AbstractConcreteChange implements ConcreteChange {
	static val logger = Logger::getLogger(AbstractConcreteChange)

	protected EChange eChange
	val VURI vuri

	new(VURI vuri) {
		this.vuri = vuri
	}

	override containsConcreteChange() {
		true
	}

	override validate() {
		containsConcreteChange && URI !== null
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
		logger.warn("The applyBackward method is not implemented for " + class.simpleName + " yet.")
	}

	override applyForward() {
		logger.warn("The applyForward method is not implemented for " + class.simpleName + " yet.")
	}

	override resolveBeforeAndApplyForward(ResourceSet resourceSet) {
		logger.warn("The resolveBeforeAndapplyForward method is not implemented for " + class.simpleName + " yet.")
	}

	override applyBackwardIfLegacy() {
// Do nothing
	}
}
