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
import java.util.function.Function
import tools.vitruv.framework.userinteraction.InteractionResultProvider
import tools.vitruv.framework.userinteraction.DecoratingInteractionResultProvider

/**
 * The default implementation of the {@link InternalUserInteractor}.
 * 
 * @author Heiko Klare
 */
class UserInteractorImpl implements InternalUserInteractor {
	WindowModality defaultWindowModality = WindowModality.MODELESS;
	final List<UserInteractionListener> userInteractionListener;
	InteractionResultProvider interactionResultProvider;
	InteractionFactory interactionFactory;

	new(InteractionResultProvider interactionResultProvider) {
		if (interactionResultProvider === null) {
			throw new IllegalArgumentException("Interaction result provider must not be null");
		}
		this.interactionResultProvider = interactionResultProvider;
		this.userInteractionListener = new ArrayList<UserInteractionListener>();
		updateInteractionFactory();
	}

	new(InteractionResultProvider interactionResultProvider, WindowModality defaultWindowModality) {
		this(interactionResultProvider);
		this.defaultWindowModality = defaultWindowModality;
	}

	override NotificationInteractionBuilder getNotificationDialogBuilder() {
		return new NotificationInteractionBuilderImpl(interactionFactory, userInteractionListener);
	}

	override ConfirmationInteractionBuilder getConfirmationDialogBuilder() {
		return new ConfirmationInteractionBuilderImpl(interactionFactory, userInteractionListener);
	}

	override TextInputInteractionBuilder getTextInputDialogBuilder() {
		return new TextInputInteractionBuilderImpl(interactionFactory, userInteractionListener);
	}

	override MultipleChoiceSingleSelectionInteractionBuilder getSingleSelectionDialogBuilder() {
		return new MultipleChoiceSingleSelectionInteractionBuilderImpl(interactionFactory, userInteractionListener);
	}

	override MultipleChoiceMultiSelectionInteractionBuilder getMultiSelectionDialogBuilder() {
		return new MultipleChoiceMultiSelectionInteractionBuilderImpl(interactionFactory, userInteractionListener);
	}

	override registerUserInputListener(UserInteractionListener listener) {
		this.userInteractionListener += listener;
	}

	override decorateUserInteractionResultProvider(
		Function<InteractionResultProvider, DecoratingInteractionResultProvider> decoratingInteractionResultProviderProducer) {
		if (interactionResultProvider === null) {
			throw new IllegalArgumentException("Interaction result provider must not be null");
		}
		this.interactionResultProvider = decoratingInteractionResultProviderProducer.apply(interactionResultProvider);
		updateInteractionFactory();
	}

	override removeDecoratingUserInteractionResultProvider() {
		if (this.interactionResultProvider.decoratedInteractionResultProvider !== null) {
			this.interactionResultProvider = this.interactionResultProvider.decoratedInteractionResultProvider;
			updateInteractionFactory();
		}
	}

	private def updateInteractionFactory() {
		this.interactionFactory = new InteractionFactoryImpl(interactionResultProvider, defaultWindowModality);
	}

}
