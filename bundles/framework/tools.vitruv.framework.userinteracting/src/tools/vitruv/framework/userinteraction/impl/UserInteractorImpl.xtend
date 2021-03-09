package tools.vitruv.framework.userinteraction.impl

import tools.vitruv.framework.userinteraction.InternalUserInteractor
import tools.vitruv.framework.userinteraction.builder.impl.ConfirmationInteractionBuilderImpl
import tools.vitruv.framework.userinteraction.builder.impl.TextInputInteractionBuilderImpl
import tools.vitruv.framework.userinteraction.builder.NotificationInteractionBuilder
import tools.vitruv.framework.userinteraction.builder.TextInputInteractionBuilder
import tools.vitruv.framework.userinteraction.builder.impl.MultipleChoiceMultiSelectionInteractionBuilderImpl
import tools.vitruv.framework.userinteraction.builder.MultipleChoiceSingleSelectionInteractionBuilder
import tools.vitruv.framework.userinteraction.builder.impl.MultipleChoiceSingleSelectionInteractionBuilderImpl
import tools.vitruv.framework.userinteraction.builder.ConfirmationInteractionBuilder
import tools.vitruv.framework.userinteraction.builder.impl.NotificationInteractionBuilderImpl
import tools.vitruv.framework.userinteraction.builder.MultipleChoiceMultiSelectionInteractionBuilder
import tools.vitruv.framework.userinteraction.types.InteractionFactory
import tools.vitruv.framework.userinteraction.UserInteractionListener
import java.util.List
import java.util.ArrayList
import tools.vitruv.framework.userinteraction.types.InteractionFactoryImpl
import tools.vitruv.framework.userinteraction.UserInteractionOptions.WindowModality
import tools.vitruv.framework.userinteraction.InteractionResultProvider
import static com.google.common.base.Preconditions.checkNotNull

/**
 * The default implementation of the {@link InternalUserInteractor}.
 * 
 * @author Heiko Klare
 */
class UserInteractorImpl implements InternalUserInteractor {
	WindowModality defaultWindowModality = WindowModality.MODELESS
	val List<UserInteractionListener> userInteractionListeners = new ArrayList
	InteractionResultProvider interactionResultProvider
	InteractionFactory interactionFactory

	new(InteractionResultProvider interactionResultProvider) {
		if (interactionResultProvider === null) {
			throw new IllegalArgumentException("Interaction result provider must not be null");
		}
		this.interactionResultProvider = interactionResultProvider;
		updateInteractionFactory();
	}

	new(InteractionResultProvider interactionResultProvider, WindowModality defaultWindowModality) {
		this(interactionResultProvider);
		this.defaultWindowModality = defaultWindowModality;
	}

	override NotificationInteractionBuilder getNotificationDialogBuilder() {
		return new NotificationInteractionBuilderImpl(interactionFactory, userInteractionListeners);
	}

	override ConfirmationInteractionBuilder getConfirmationDialogBuilder() {
		return new ConfirmationInteractionBuilderImpl(interactionFactory, userInteractionListeners);
	}

	override TextInputInteractionBuilder getTextInputDialogBuilder() {
		return new TextInputInteractionBuilderImpl(interactionFactory, userInteractionListeners);
	}

	override MultipleChoiceSingleSelectionInteractionBuilder getSingleSelectionDialogBuilder() {
		return new MultipleChoiceSingleSelectionInteractionBuilderImpl(interactionFactory, userInteractionListeners);
	}

	override MultipleChoiceMultiSelectionInteractionBuilder getMultiSelectionDialogBuilder() {
		return new MultipleChoiceMultiSelectionInteractionBuilderImpl(interactionFactory, userInteractionListeners);
	}

	override registerUserInputListener(UserInteractionListener listener) {
		this.userInteractionListeners += listener;
	}
	
	override deregisterUserInputListener(UserInteractionListener listener) {
		this.userInteractionListeners -= listener
	}

	override replaceUserInteractionResultProvider(
		(InteractionResultProvider) => InteractionResultProvider decoratingInteractionResultProviderProducer
	) {
		val oldProvider = checkNotNull(interactionResultProvider, "No user interaction result provider is set!")
		this.interactionResultProvider = decoratingInteractionResultProviderProducer.apply(interactionResultProvider)
		updateInteractionFactory()
		return [
			this.interactionResultProvider = oldProvider
			updateInteractionFactory()
		]
	}

	private def updateInteractionFactory() {
		this.interactionFactory = new InteractionFactoryImpl(interactionResultProvider, defaultWindowModality);
	}
}
