package tools.vitruv.framework.userinteraction

import tools.vitruv.framework.userinteraction.DecoratingInteractionResultProvider
import tools.vitruv.framework.change.interaction.UserInteractionBase

abstract class PredefinedInteractionResultProvider extends DecoratingInteractionResultProvider {
	new (InteractionResultProvider fallbackResultProvider) {
		super(fallbackResultProvider)
	}
	
	public abstract def void addUserInteractions(UserInteractionBase... interactions);
}