package tools.vitruv.change.interaction

/**
 * Internal version of the {@link UserInteractor} interface used to separate methods for internal "bookkeeping" from
 * methods for actual user interaction.
 * 
 * @author Dominik Klooz
 * @author Heiko Klare
 */
interface InternalUserInteractor extends UserInteractor {
	/**
	 * Registers the given {@link UserInteractionListener} to get notified about interactions.
	 */
	def void registerUserInputListener(UserInteractionListener listener)
	
	/**
	 * Removes the given {@link UserInteractionListener} from the list of listeners that get notified about interactions.
	 */
	def void deregisterUserInputListener(UserInteractionListener listener)
	
	/**
	 * Replaces the current {@link InteractionResultProvider} with the one provided by the given {@code replacementProvider}.
	 * 
	 * @param replacementProvider - function that gets the current {@link InteractionResultProvider} and returns the new one.
	 * @return an {@link AutoCloseable} that will revert the change when being closed.
	 */
	def AutoCloseable replaceUserInteractionResultProvider(
		(InteractionResultProvider) => InteractionResultProvider replacementProvider
	)
}
