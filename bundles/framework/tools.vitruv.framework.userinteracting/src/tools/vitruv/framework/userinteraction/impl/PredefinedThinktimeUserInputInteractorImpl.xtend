package tools.vitruv.framework.userinteraction.impl

import tools.vitruv.framework.userinteraction.UserInteractor
import tools.vitruv.framework.userinteraction.resultprovider.PredefinedThinktimeSimulatingInteractionProvider

/**
 * @author Heiko Klare
 */
class PredefinedThinktimeUserInputInteractorImpl extends PredefinedUserInputInteractorImpl {
	private final int minWaittime;
	private final int maxWaittime;
	
	/** 
	 * Waittime in milliseconds. 
	 */
	new(UserInteractor fallbackInteractor, int minWaittime, int maxWaittime) {
		super(fallbackInteractor)
		this.minWaittime = minWaittime;
		this.maxWaittime = maxWaittime;
	}
	
	override createInteractionResultProvider() {
		return new PredefinedThinktimeSimulatingInteractionProvider(fallbackInteractionResultProvider, minWaittime, maxWaittime);
	}
	
}