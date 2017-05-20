package tools.vitruv.framework.change.processing.impl

import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.framework.domains.VitruvDomain

abstract class AbstractChangePropagationSpecification implements ChangePropagationSpecification {
	var UserInteracting userInteracting
	var VitruvDomain sourceDomain
	var VitruvDomain targetDomain

	new(UserInteracting userInteracting, VitruvDomain sourceDomain, VitruvDomain targetDomain) {
		setUserInteracting(userInteracting)
		setVitruvDomains(sourceDomain, targetDomain)
	}

	protected def UserInteracting getUserInteracting() {
		userInteracting
	}

	private def setVitruvDomains(VitruvDomain sourceDomain, VitruvDomain targetDomain) {
		this.sourceDomain = sourceDomain
		this.targetDomain = targetDomain
	}

	override getSourceDomain() {
		sourceDomain
	}

	override getTargetDomain() {
		targetDomain
	}

	override setUserInteracting(UserInteracting userInteracting) {
		if (userInteracting === null) {
			throw new IllegalArgumentException("UserInteracting must not be null")
		}
		this.userInteracting = userInteracting
	}
}
