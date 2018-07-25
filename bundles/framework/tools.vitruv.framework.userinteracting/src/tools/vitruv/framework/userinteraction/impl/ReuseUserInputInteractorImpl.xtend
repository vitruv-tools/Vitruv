package tools.vitruv.framework.userinteraction.impl

import tools.vitruv.framework.change.interaction.UserInteractionBase
import tools.vitruv.framework.userinteraction.UserInteractor

/**
 * @author Heiko Klare
 */
class ReuseUserInputInteractorImpl extends PredefinedUserInputInteractorImpl {
	
	new(Iterable<UserInteractionBase> userInputs, UserInteractor fallbackInteractor) {
		super(fallbackInteractor)
		if (fallbackInteractor === null) {
			throw new IllegalArgumentException("Fallback interactor must not be null");
		}
		if (userInputs === null) {
			throw new IllegalArgumentException("Inputs must not be null");
		}
		addUserInteraction(userInputs);
	}
	
}