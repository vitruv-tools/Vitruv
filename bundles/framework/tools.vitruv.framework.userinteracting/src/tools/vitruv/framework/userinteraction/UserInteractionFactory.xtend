package tools.vitruv.framework.userinteraction

import tools.vitruv.framework.userinteraction.impl.UserInteractorImpl
import tools.vitruv.framework.change.interaction.UserInteractionBase
import tools.vitruv.framework.userinteraction.impl.DialogInteractionResultProviderImpl
import tools.vitruv.framework.userinteraction.impl.PredefinedInteractionResultProviderImpl
import tools.vitruv.framework.userinteraction.impl.PredefinedThinktimeSimulatingInteractionResultProviderImpl

/**
 * Factory for {@link UserInteraction}s and {@link InteractionResultProvider}.
 * 
 * @author Heiko Klare
 */
class UserInteractionFactory {
	public static val instance = new UserInteractionFactory();

	private new() {
	}

	/**
	 * Creates a {@link InternalUserInteractor} with the given {@link InteractionResultProvider}.
	 * 
	 * @param interactionResultProvider - the provider for results of an interaction
	 */
	def InternalUserInteractor createUserInteractor(InteractionResultProvider interactionResultProvider) {
		return new UserInteractorImpl(interactionResultProvider);
	}

	/**
	 * Creates a {@link InternalUserInteractor} with the a result provider based on UI dialogs.
	 */
	def InternalUserInteractor createDialogUserInteractor() {
		return new UserInteractorImpl(createDialogInteractionResultProvider());
	}

	/**
	 * Creates a {@link InteralResultProvider} based on UI dialogs.
	 */
	def InteractionResultProvider createDialogInteractionResultProvider() {
		return new DialogInteractionResultProviderImpl();
	}

	/**
	 * Creates a {@link PredefinedInteractionResultProvider} on which used inputs can be predefined. The given {@link InteractionResultProvider}
	 * is used as a fallback if not appropriate result is predefined.
	 * 
	 * @param fallbackResultProvider - the provider to be used if no input was predefined
	 */
	def PredefinedInteractionResultProvider createPredefinedInteractionResultProvider(
		InteractionResultProvider fallbackResultProvider) {
		return new PredefinedInteractionResultProviderImpl(fallbackResultProvider);
	}

	/**
	 * Creates a {@link PredefinedInteractionResultProvider} on which the given {@link UserInteractionBase}s are used as predefined inputs. 
	 * The given {@link InteractionResultProvider} is used as a fallback if not appropriate result is predefined.
	 * 
	 * @param fallbackResultProvider - the provider to be used if no input was predefined
	 */
	def PredefinedInteractionResultProvider createPredefinedInteractionResultProvider(
		InteractionResultProvider fallbackResultProvider, UserInteractionBase... predefinedUserInputs) {
		val result = createPredefinedInteractionResultProvider(fallbackResultProvider);
		result.addUserInteractions(predefinedUserInputs);
		return result;
	}

	/**
	 * Creates a {@link PredefinedInteractionResultProvider} on which used inputs can be predefined and which simulates a think time everytime an 
	 * interaction is performed. This can, for example, be performed for performance evaluations. The given {@link InteractionResultProvider}
	 * is used as a fallback if not appropriate result is predefined.
	 * 
	 * @param fallbackResultProvider - the provider to be used if no input was predefined
	 * @param minWaittime - the minimum time to wait in milliseconds
	 * @param maxWaittime - the maximum time to wait in milliseconds
	 */
	def PredefinedInteractionResultProvider createPredefinedThinktimeSimulatingInteractionResultProvider(
		InteractionResultProvider fallbackResultProvider, int minWaittime, int maxWaittime) {
		return new PredefinedThinktimeSimulatingInteractionResultProviderImpl(fallbackResultProvider, minWaittime,
			maxWaittime);
	}
}
