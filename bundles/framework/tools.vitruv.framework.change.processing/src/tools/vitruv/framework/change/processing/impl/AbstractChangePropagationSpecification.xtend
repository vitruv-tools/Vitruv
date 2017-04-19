package tools.vitruv.framework.change.processing.impl

import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.framework.domains.VitruvDomain

abstract class AbstractChangePropagationSpecification implements ChangePropagationSpecification {
	private var UserInteracting userInteracting;
	private var VitruvDomain sourceDomain;
	private var VitruvDomain targetDomain;
	
	new(UserInteracting userInteracting) {
		setUserInteracting(userInteracting);
	}
	
	protected def UserInteracting getUserInteracting() {
		return userInteracting;
	}
	
	protected def setVitruvDomains(VitruvDomain sourceDomain, VitruvDomain targetDomain) {
		this.sourceDomain = sourceDomain;
		this.targetDomain = targetDomain;
	}
	
	override getSourceDomain() {
		return sourceDomain;
	}
	
	override getTargetDomain() {
		return targetDomain;
	}
	
	override setUserInteracting(UserInteracting userInteracting) {
		if (userInteracting == null) {
			throw new IllegalArgumentException("UserInteracting must not be null");
		}
		this.userInteracting = userInteracting;
	}
	
}
