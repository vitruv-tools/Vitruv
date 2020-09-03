package tools.vitruv.framework.userinteraction

import org.eclipse.xtend.lib.annotations.Accessors

/**
 * Implementation of the {@link InteractionResultProvider} interface, which allows to decorate existing
 * providers and operate as a proxy for them.
 */
abstract class DecoratingInteractionResultProvider implements InteractionResultProvider {
	@Accessors(PUBLIC_GETTER)
	final InteractionResultProvider decoratedInteractionResultProvider;

	new(InteractionResultProvider decoratedInteractionResultProvider) {
		this.decoratedInteractionResultProvider = decoratedInteractionResultProvider;
	}

}
