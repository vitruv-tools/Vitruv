package tools.vitruv.framework.userinteraction

import tools.vitruv.framework.userinteraction.impl.UserInteractorImpl
import tools.vitruv.framework.change.interaction.UserInteractionBase
import tools.vitruv.framework.userinteraction.impl.DialogInteractionResultProviderImpl
import tools.vitruv.framework.userinteraction.impl.PredefinedInteractionResultProviderImpl
import tools.vitruv.framework.userinteraction.impl.PredefinedThinktimeSimulatingInteractionResultProviderImpl

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
		return new DialogInteractionResultProviderImpl();
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
		return new PredefinedThinktimeSimulatingInteractionResultProviderImpl(fallbackResultProvider, minWaittime, maxWaittime);
	}
}