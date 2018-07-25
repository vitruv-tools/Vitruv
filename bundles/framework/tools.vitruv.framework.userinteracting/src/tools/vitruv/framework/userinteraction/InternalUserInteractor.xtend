package tools.vitruv.framework.userinteraction

import java.util.function.Function

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
     * Decorates the contained {@link InteractionResultProvider} with the one provided by the given function.
     * 
     * @param decoratingInteractionResultProviderProducer - function that gets the contained {@link InteractionResultProvider} and returns a new one decorating the old
     */
    def void decorateUserInteractionResultProvider(Function<InteractionResultProvider, DecoratingInteractionResultProvider> decoratingInteractionResultProviderProducer);
    
    /**
     * Removes the topmost {@link DecoratingInteractionResultProvider}. If none was added, nothing is done.
     */
    def void removeDecoratingUserInteractionResultProvider();
}