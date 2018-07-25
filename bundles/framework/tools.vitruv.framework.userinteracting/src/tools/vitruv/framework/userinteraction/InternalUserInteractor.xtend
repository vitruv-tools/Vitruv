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
    def void registerUserInputListener(UserInteractionListener listener)
    def void decorateUserInteractionResultProvider(Function<InteractionResultProvider, DecoratingInteractionResultProvider> decoratingInteractionResultProviderProducer);
    def void removeDecoratingUserInteractionResultProvider();
}