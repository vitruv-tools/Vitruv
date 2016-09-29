package tools.vitruv.framework.change.processing.impl

import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.change.processing.ChangePropagationSpecification

abstract class AbstractChangePropagationSpecification implements ChangePropagationSpecification {
	private var UserInteracting userInteracting;
	
	new(UserInteracting userInteracting) {
		setUserInteracting(userInteracting);
	}
	
	protected def UserInteracting getUserInteracting() {
		return userInteracting;
	}
	
	override setUserInteracting(UserInteracting userInteracting) {
		if (userInteracting == null) {
			throw new IllegalArgumentException("UserInteracting must not be null");
		}
		this.userInteracting = userInteracting;
	}
	
}
