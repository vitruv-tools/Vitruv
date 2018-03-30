package tools.vitruv.framework.userinteraction;

import org.eclipse.emf.common.util.URI;

public interface UserInteracting {
	
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
	 * dialogs to prompt the user to choose from a list of choices. Can be configured to either provide single-select or
	 * multi-select controls.
	 */
	MultipleChoiceSelectionDialogBuilder getMultipleChoiceSelectionDialogBuilder();

    /**
     * Ask for a URI.
     *
     * @param message
     *            the message to display to the user.
     * @return the URI of the resource.
     * @author Dominik Werle
     */
    URI selectURI(String message);
}
