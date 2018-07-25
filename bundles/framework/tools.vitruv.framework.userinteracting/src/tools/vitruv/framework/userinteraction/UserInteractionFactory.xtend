package tools.vitruv.framework.userinteraction

import tools.vitruv.framework.userinteraction.resultprovider.DialogInteractionProvider
import tools.vitruv.framework.userinteraction.resultprovider.PredefinedThinktimeSimulatingInteractionProvider
import tools.vitruv.framework.userinteraction.impl.UserInteractorImpl
import tools.vitruv.framework.userinteraction.resultprovider.PredefinedInteractionResultProviderImpl
import tools.vitruv.framework.change.interaction.UserInteractionBase

class UserInteractionFactory {
	public static val instance = new UserInteractionFactory();
	
	private new() {}
	
	def InternalUserInteractor createUserInteractor(InteractionResultProvider interactionResultProvider) {
		return new UserInteractorImpl(interactionResultProvider);
	}
	
	def InternalUserInteractor createDialogUserInteractor() {
		return new UserInteractorImpl(createDialogInteractionResultProvider());
	}
	
	def InternalUserInteractor createDummyUserInteractor() {
		return new UserInteractorImpl(null);
	}
	
	def InteractionResultProvider createDialogInteractionResultProvider() {
		return new DialogInteractionProvider();
	}
	
	def PredefinedInteractionResultProvider createPredefinedInteractionResultProvider(InteractionResultProvider fallbackResultProvider) {
		return new PredefinedInteractionResultProviderImpl(fallbackResultProvider);
	}
	
	def PredefinedInteractionResultProvider createPredefinedInteractionResultProvider(InteractionResultProvider fallbackResultProvider, UserInteractionBase... predefinedUserInputs) {
		val result = createPredefinedInteractionResultProvider(fallbackResultProvider);
		result.addUserInteractions(predefinedUserInputs);
		return result;
	}
	
	def PredefinedInteractionResultProvider createPredefinedThinktimeSimulatingInteractionResultProvider(InteractionResultProvider fallbackResultProvider, int minWaittime, int maxWaittime) {
		return new PredefinedThinktimeSimulatingInteractionProvider(fallbackResultProvider, minWaittime, maxWaittime);
	}
}