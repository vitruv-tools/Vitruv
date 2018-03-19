package tools.vitruv.framework.userinteraction;

import org.eclipse.emf.common.util.URI;

import tools.vitruv.framework.change.interaction.ConfirmationUserInput;

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
	
	ConfirmationUserInput getUserConfirmation(String title, String message, WindowModality windowModality);
	
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
