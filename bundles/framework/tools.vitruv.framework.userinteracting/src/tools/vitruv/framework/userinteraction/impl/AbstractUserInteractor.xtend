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
import org.eclipse.xtend.lib.annotations.Accessors
import java.util.ArrayList
import tools.vitruv.framework.userinteraction.resultprovider.InteractionResultProvider

/**
 * @author Heiko Klare
 */
abstract class AbstractUserInteractor<T extends InteractionResultProvider> implements InternalUserInteractor {
	@Accessors(PROTECTED_GETTER)
	private final List<UserInteractionListener> userInteractionListener;
	@Accessors(PROTECTED_GETTER)
	private T interactionResultProvider;
	
	new () {
		this.userInteractionListener = new ArrayList<UserInteractionListener>();
	}
	
	public override NotificationInteractionBuilder getNotificationDialogBuilder() {
		return new NotificationInteractionBuilderImpl(interactionFactory, userInteractionListener);
	}

	public override ConfirmationInteractionBuilder getConfirmationDialogBuilder() {
		return new ConfirmationInteractionBuilderImpl(interactionFactory, userInteractionListener);
	}

	public override TextInputInteractionBuilder getTextInputDialogBuilder() {
		return new TextInputInteractionBuilderImpl(interactionFactory, userInteractionListener);
	}

	public override MultipleChoiceSingleSelectionInteractionBuilder getSingleSelectionDialogBuilder() {
		return new MultipleChoiceSingleSelectionInteractionBuilderImpl(interactionFactory, userInteractionListener);
	}
	
	public override MultipleChoiceMultiSelectionInteractionBuilder getMultiSelectionDialogBuilder() {
		return new MultipleChoiceMultiSelectionInteractionBuilderImpl(interactionFactory, userInteractionListener);
	}
	
	override registerUserInputListener(UserInteractionListener listener) {
		this.userInteractionListener += listener;
	}
	
	protected abstract def InteractionFactory getInteractionFactory();
	protected abstract def T createInteractionResultProvider();
	
	public def final T getInteractionResultProvider() {
		if (interactionResultProvider === null) {
			interactionResultProvider = createInteractionResultProvider();
		}
		return interactionResultProvider;
	} 
}