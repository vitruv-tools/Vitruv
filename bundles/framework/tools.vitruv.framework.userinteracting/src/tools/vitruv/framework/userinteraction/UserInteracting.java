package tools.vitruv.framework.userinteraction;

import org.eclipse.emf.common.util.URI;

import tools.vitruv.framework.change.interaction.ConfirmationUserInput;
import tools.vitruv.framework.change.interaction.FreeTextUserInput;
import tools.vitruv.framework.change.interaction.MultipleChoiceMultiSelectionUserInput;
import tools.vitruv.framework.change.interaction.MultipleChoiceSingleSelectionUserInput;

public interface UserInteracting {
	
	/**
	 * Display a notification to the user containing a message, an icon depicting the severity of the message and a
	 * title and modality for the dialog window.
	 * 
	 * @param title
	 * 			the window title.
	 * @param message
	 * 			the notification text.
	 * @param notificationType
	 * 			the severity of the notification content; information, warning or error.
	 * @param windowModality
	 * 			the modality of the window; modal or mode-less.
	 * @author Dominik Klooz
	 */
	void showNotification(String title, String message, NotificationType notificationType, WindowModality windowModality);
	
	/**
	 * Ask the user to confirm or deny some course of action described in {@code message}. 
	 * 
	 * @param title
	 * 			the window title.
	 * @param message
	 * 			the course of action/question the user shall confirm or deny.
	 * @param windowModality
	 * 			the modality of the window; modal or mode-less.
	 * @return a {@code boolean} value that is set to true if the user confirmed or false otherwise.
	 * @author Dominik Klooz
	 */
	boolean getUserConfirmation(String title, String message, WindowModality windowModality);
	
	/**
	 * Get a free text input from the user.
	 * 
	 * @param title
	 * 			the window title.
	 * @param message
	 * 			the message to be shown above the text input line.
	 * @param windowModality
	 * 			the modality of the window; modal or mode-less.
	 * @return a {@code String} value with the text entered by the user or {@code null} if the user cancelled.
	 * @author Dominik Klooz
	 */
	String getTextInput(String title, String message, WindowModality windowModality);
	
	/**
	 * Let the user select a single entry from an array of choices represented by their descriptions given in {@code
	 * selectionDescriptions}. Use TODO to allow the selection of multiple entries.
	 * 
	 * @param title
	 * 			the window title.
	 * @param message
	 * 			the message to be shown above the choices.
	 * @param selectionDescriptions
	 * 			an array of textual descriptions for the selectable choices.
	 * @param windowModality
	 * 			the modality of the window; modal or mode-less.
	 * @return a zero-based {@code int} index of the selected entries from the array of choices passed to
	 * {@code selectionDescriptions}.
	 */
	int selectSingle(String title, String message, String[] selectionDescriptions,
			WindowModality windwoModality);
	
	/**
	 * Let the user select multiple entries from an array of choices represented by their descriptions given in {@code
	 * selectionDescriptions}. Use {@link #selectSingle(String, String, String[], WindowModality) selectSingle} method
	 * to restrict the selection to one single entry.
	 * 
	 * @param title
	 * 			the window title.
	 * @param message
	 * 			the message to be shown above the choices.
	 * @param selectionDescriptions
	 * 			an array of textual descriptions for the selectable choices.
	 * @param windowModality
	 * 			the modality of the window; modal or mode-less.
	 * @return an array of zero-based {@code int} indices of the selected entries from the array of choices passed to
	 * {@code selectionDescriptions}.
	 */
	int[] selectMulti(String title, String message, String[] selectionDescriptions,
			WindowModality windowModality);
	
    void showMessage(UserInteractionType type, String message);

    int selectFromMessage(UserInteractionType type, String message, String... selectionDescriptions);

    String getTextInput(String msg);

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
