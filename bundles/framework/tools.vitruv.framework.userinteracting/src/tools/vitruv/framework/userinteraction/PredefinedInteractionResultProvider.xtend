package tools.vitruv.framework.userinteraction

import tools.vitruv.framework.userinteraction.DecoratingInteractionResultProvider
import tools.vitruv.framework.change.interaction.UserInteractionBase

/**
 * Implementation of the {@link DecoratingInteractionResultProvider}, which allows to predefine the performed inputs.
 * 
 * @author Heiko Klare
 */
abstract class PredefinedInteractionResultProvider extends DecoratingInteractionResultProvider {
	new(InteractionResultProvider fallbackResultProvider) {
		super(fallbackResultProvider)
	}

	/**
	 * Adds user interaction results to be used whenever an interaction is requested.
	 * 
	 * @param interactions - the interaction results to use
	 */
	public abstract def void addUserInteractions(UserInteractionBase... interactions);
}
