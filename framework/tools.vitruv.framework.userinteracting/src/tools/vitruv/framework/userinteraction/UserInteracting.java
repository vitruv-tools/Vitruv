package tools.vitruv.framework.userinteraction;

import org.eclipse.emf.common.util.URI;

public interface UserInteracting {
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
