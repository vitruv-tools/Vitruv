package tools.vitruv.framework.change.processing.impl

import tools.vitruv.framework.change.processing.ChangeProcessor
import tools.vitruv.framework.userinteraction.UserInteracting

abstract class AbstractChangeProcessor implements ChangeProcessor {
	private val UserInteracting userInteracting;
	
	new(UserInteracting userInteracting) {
		if (userInteracting == null) {
			throw new IllegalArgumentException("UserInteracting must not be null");
		}
		this.userInteracting = userInteracting;
	}
	
	protected def UserInteracting getUserInteracting() {
		return userInteracting;
	}
	
}
