package tools.vitruv.framework.userinteraction;

import org.eclipse.emf.common.util.URI;

import tools.vitruv.framework.change.interaction.ConfirmationUserInput;
import tools.vitruv.framework.change.interaction.FreeTextUserInput;
import tools.vitruv.framework.change.interaction.MultipleChoiceMultiSelectionUserInput;
import tools.vitruv.framework.change.interaction.MultipleChoiceSingleSelectionUserInput;
import tools.vitruv.framework.userinteraction.impl.ConfirmationDialogBuilder;
import tools.vitruv.framework.userinteraction.impl.DialogBuilder;
import tools.vitruv.framework.userinteraction.impl.MultipleChoiceSelectionDialogBuilder;
import tools.vitruv.framework.userinteraction.impl.NotificationDialogBuilder;
import tools.vitruv.framework.userinteraction.impl.TextInputDialogBuilder;

public interface UserInteracting {
	
	/**
	 * @return a {@link DialogBuilder} used to configure and build notification dialogs to inform the user about something.
	 */
	NotificationDialogBuilder getNotificationDialogBuilder();
	
	/**
	 * @return a {@link DialogBuilder} used to configure and build confirmation dialogs to prompt the user to answer a
	 * question in the positive or negative.
	 */
	ConfirmationDialogBuilder getConfirmationDialogBuilder();
	
	/**
	 * @return a {@link DialogBuilder} used to configure and build text input dialogs to prompt the user to input free
	 * text optionally restricted by a {@link InputValidator}. Can be configured to take single- or multi-line input.
	 */
	TextInputDialogBuilder getTextInputDialogBuilder();
	
	/**
	 * @return a {@link DialogBuilder} used to configure and build multiple choice input dialogs to prompt the user to
	 * choose from a list of choices. Can be configured to either provide single-select or multi-select controls.
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
