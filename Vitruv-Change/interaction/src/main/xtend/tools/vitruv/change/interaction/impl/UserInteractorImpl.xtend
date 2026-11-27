package tools.vitruv.change.interaction.impl

import tools.vitruv.change.interaction.InternalUserInteractor
import tools.vitruv.change.interaction.builder.impl.ConfirmationInteractionBuilderImpl
import tools.vitruv.change.interaction.builder.impl.TextInputInteractionBuilderImpl
import tools.vitruv.change.interaction.builder.NotificationInteractionBuilder
import tools.vitruv.change.interaction.builder.TextInputInteractionBuilder
import tools.vitruv.change.interaction.builder.impl.MultipleChoiceMultiSelectionInteractionBuilderImpl
import tools.vitruv.change.interaction.builder.MultipleChoiceSingleSelectionInteractionBuilder
import tools.vitruv.change.interaction.builder.impl.MultipleChoiceSingleSelectionInteractionBuilderImpl
import tools.vitruv.change.interaction.builder.ConfirmationInteractionBuilder
import tools.vitruv.change.interaction.builder.impl.NotificationInteractionBuilderImpl
import tools.vitruv.change.interaction.builder.MultipleChoiceMultiSelectionInteractionBuilder
import tools.vitruv.change.interaction.types.InteractionFactory
import tools.vitruv.change.interaction.UserInteractionListener
import java.util.List
import java.util.ArrayList
import tools.vitruv.change.interaction.types.InteractionFactoryImpl
import tools.vitruv.change.interaction.UserInteractionOptions.WindowModality
import tools.vitruv.change.interaction.InteractionResultProvider
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
