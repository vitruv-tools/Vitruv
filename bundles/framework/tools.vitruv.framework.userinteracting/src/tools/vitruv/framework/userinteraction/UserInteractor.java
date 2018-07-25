package tools.vitruv.framework.userinteraction;

import tools.vitruv.framework.userinteraction.builder.ConfirmationInteractionBuilder;
import tools.vitruv.framework.userinteraction.builder.MultipleChoiceMultiSelectionInteractionBuilder;
import tools.vitruv.framework.userinteraction.builder.MultipleChoiceSingleSelectionInteractionBuilder;
import tools.vitruv.framework.userinteraction.builder.NotificationInteractionBuilder;
import tools.vitruv.framework.userinteraction.builder.TextInputInteractionBuilder;

/**
 * Central interface providing dialog builders to build different types of
 * dialogs to notify the user or get input in different forms.
 * 
 * @author Dominik Klooz
 * @author Heiko Klare
 */
// TODO Rename all "dialog" to "Interaction" -> also adapt application projects
public interface UserInteractor {

	/**
	 * @return a {@link NotificationInteractionBuilder} used to configure, build and
	 *         show notification dialogs to inform the user about something.
	 */
	NotificationInteractionBuilder getNotificationDialogBuilder();

	/**
	 * @return a {@link ConfirmationInteractionBuilder} used to configure, build and
	 *         show confirmation dialogs to prompt the user to answer a question in
	 *         the positive or negative.
	 */
	ConfirmationInteractionBuilder getConfirmationDialogBuilder();

	/**
	 * @return a {@link TextInputInteractionBuilder} used to configure, build and
	 *         show text input dialogs to prompt the user to input free text
	 *         optionally restricted by a {@link InputValidator}. Can be configured
	 *         to take single- or multi-line input.
	 */
	TextInputInteractionBuilder getTextInputDialogBuilder();

	/**
	 * @return a {@link MultipleChoiceMultiSelectionInteractionBuilder} used to
	 *         configure, build and show multiple choice input dialogs to prompt the
	 *         user to choose from a list of choices. This one allows the user to
	 *         select one single item.
	 */
	MultipleChoiceSingleSelectionInteractionBuilder getSingleSelectionDialogBuilder();

	/**
	 * @return a {@link MultipleChoiceMultiSelectionInteractionBuilder} used to
	 *         configure, build and show multiple choice input dialogs to prompt the
	 *         user to choose from a list of choices. This one allows the user to
	 *         select multiple items.
	 */
	MultipleChoiceMultiSelectionInteractionBuilder getMultiSelectionDialogBuilder();
}
