package tools.vitruvius.framework.change.processing.impl

import tools.vitruvius.framework.change.processing.ChangeProcessor
import tools.vitruvius.framework.userinteraction.UserInteracting

abstract class AbstractChangeProcessor implements ChangeProcessor {
	private val UserInteracting userInteracting;
	
	new(UserInteracting userInteracting) {
		this.userInteracting = userInteracting;
	}
	
	protected def UserInteracting getUserInteracting() {
		return userInteracting;
	}
}
