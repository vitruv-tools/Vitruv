package tools.vitruv.change.interaction

import tools.vitruv.change.interaction.impl.UserInteractorImpl
import tools.vitruv.change.interaction.UserInteractionBase
import tools.vitruv.change.interaction.impl.PredefinedInteractionResultProviderImpl
import tools.vitruv.change.interaction.impl.ThinktimeSimulatingInteractionResultProvider

/**
 * Factory for {@link UserInteractor}s and {@link InteractionResultProvider}.
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
	 * Creates a {@link InteractionResultProvider} which simulates waiting for the user. This can, for 
	 * example, be used for performance evaluations. The given {@code delegate} is used to actually provide results.
	 * 
	 * @param delegate - the provider to be used if no input was predefined
	 * @param minWaittime - the minimum time to wait in milliseconds
	 * @param maxWaittime - the maximum time to wait in milliseconds
	 */
	def InteractionResultProvider createThinktimeSimulatingInteractionResultProvider(
		InteractionResultProvider delegate, int minWaittime, int maxWaittime) {
		return new ThinktimeSimulatingInteractionResultProvider(delegate, minWaittime,
			maxWaittime);
	}
}
