package tools.vitruv.change.interaction

import tools.vitruv.change.interaction.UserInteractionBase

/**
 * A {@link InteractionResultProvider} which allows to predefine the performed inputs.
 * 
 * @author Heiko Klare
 */
interface PredefinedInteractionResultProvider extends InteractionResultProvider {
	/**
	 * Adds user interaction results to be used whenever an interaction is requested.
	 * 
	 * @param interactions - the interaction results to use
	 */
	abstract def void addUserInteractions(UserInteractionBase... interactions);
}
