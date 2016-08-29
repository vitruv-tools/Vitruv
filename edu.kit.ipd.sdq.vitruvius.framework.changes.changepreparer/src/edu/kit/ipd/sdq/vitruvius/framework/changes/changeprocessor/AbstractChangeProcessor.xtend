package edu.kit.ipd.sdq.vitruvius.framework.changes.changeprocessor

import edu.kit.ipd.sdq.vitruvius.framework.changes.changeprocessor.ChangeProcessor
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting

abstract class AbstractChangeProcessor implements ChangeProcessor {
	private val UserInteracting userInteracting;
	
	new(UserInteracting userInteracting) {
		this.userInteracting = userInteracting;
	}
	
	protected def UserInteracting getUserInteracting() {
		return userInteracting;
	}
}