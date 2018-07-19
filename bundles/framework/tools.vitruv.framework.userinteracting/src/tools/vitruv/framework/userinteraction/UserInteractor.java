package tools.vitruv.framework.userinteraction;

import tools.vitruv.framework.userinteraction.impl.TextInputDialog.InputValidator;

/**
 * Central interface providing dialog builders to build different types of dialogs to notify the user or get input in
 * different forms.
 * 
 * @author Dominik Klooz
 */
public interface UserInteractor {
	
	/**
	 * @return a {@link NotificationDialogBuilder} used to configure, build and show notification dialogs to inform the
	 * user about something.
	 */
	NotificationDialogBuilder getNotificationDialogBuilder();
	
	/**
	 * @return a {@link ConfirmationDialogBuilder} used to configure, build and show confirmation dialogs to prompt the
	 * user to answer a question in the positive or negative.
	 */
	ConfirmationDialogBuilder getConfirmationDialogBuilder();
	
	/**
	 * @return a {@link TextDialogBuilder} used to configure, build and show text input dialogs to prompt the user to
	 * input free text optionally restricted by a {@link InputValidator}. Can be configured to take single- or
	 * multi-line input.
	 */
	TextInputDialogBuilder getTextInputDialogBuilder();
	
	/**
	 * @return a {@link MultipleChoiceSelectionDialogBuilder} used to configure, build and show multiple choice input
	 * dialogs to prompt the user to choose from a list of choices. This one allows the user to select one single item.
	 */
	MultipleChoiceSingleSelectionDialogBuilder getSingleSelectionDialogBuilder();
	
	/**
	 * @return a {@link MultipleChoiceSelectionDialogBuilder} used to configure, build and show multiple choice input
	 * dialogs to prompt the user to choose from a list of choices. This one allows the user to select multiple items.
	 */
	MultipleChoiceMultiSelectionDialogBuilder getMultiSelectionDialogBuilder();
}
