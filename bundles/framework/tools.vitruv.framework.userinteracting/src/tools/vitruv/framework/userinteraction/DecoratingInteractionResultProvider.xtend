package tools.vitruv.framework.userinteraction

import org.eclipse.xtend.lib.annotations.Accessors

abstract class DecoratingInteractionResultProvider implements InteractionResultProvider {
	@Accessors(PUBLIC_GETTER)
	private final InteractionResultProvider decoratedInteractionResultProvider;
	
	new(InteractionResultProvider decoratedInteractionResultProvider) {
		this.decoratedInteractionResultProvider = decoratedInteractionResultProvider;
	}
	
}