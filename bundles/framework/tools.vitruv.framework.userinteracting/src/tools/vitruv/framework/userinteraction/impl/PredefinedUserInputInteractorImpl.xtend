package tools.vitruv.framework.userinteraction.impl

import tools.vitruv.framework.userinteraction.impl.AbstractUserInteractor
import tools.vitruv.framework.change.interaction.UserInteractionBase
import tools.vitruv.framework.userinteraction.types.InteractionFactory
import tools.vitruv.framework.userinteraction.types.InteractionFactoryImpl
import tools.vitruv.framework.userinteraction.UserInteractor
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.userinteraction.resultprovider.InteractionResultProvider
import tools.vitruv.framework.userinteraction.resultprovider.PredefinedInteractionProvider

/**
 * @author Heiko Klare
 */
class PredefinedUserInputInteractorImpl extends AbstractUserInteractor<PredefinedInteractionProvider> {
	private InteractionFactory interactionFactory;
	@Accessors(PROTECTED_GETTER)
	private final InteractionResultProvider fallbackInteractionResultProvider;
	
	new(UserInteractor fallbackInteractor) {
		// TODO HK This is really ugly. We should only provide one user interactor implementation and make only the input provider replaceable
		this.fallbackInteractionResultProvider = if (fallbackInteractor instanceof AbstractUserInteractor<?>) {
			fallbackInteractor.interactionResultProvider
		} else if (fallbackInteractor !== null) {
			throw new IllegalStateException;
		}
	}
	
	public def void addUserInteraction(UserInteractionBase... interactions) {
		interactionResultProvider.addUserInteractions(interactions);
	}
	
	override protected getInteractionFactory() {
		if (interactionFactory === null) {
			this.interactionFactory = new InteractionFactoryImpl(interactionResultProvider, null);
		}
		return interactionFactory;
	}
	
	override createInteractionResultProvider() {
		return new PredefinedInteractionProvider(fallbackInteractionResultProvider);
	}

}
