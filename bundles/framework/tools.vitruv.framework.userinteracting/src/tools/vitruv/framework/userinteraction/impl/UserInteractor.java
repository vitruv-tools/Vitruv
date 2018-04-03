package tools.vitruv.framework.userinteraction.impl;

import java.util.LinkedList;
import java.util.Queue;

import org.eclipse.emf.common.util.URI;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import tools.vitruv.framework.change.interaction.UserInputBase;
import tools.vitruv.framework.userinteraction.ConfirmationDialogBuilder;
import tools.vitruv.framework.userinteraction.MultipleChoiceSelectionDialogBuilder;
import tools.vitruv.framework.userinteraction.NotificationDialogBuilder;
import tools.vitruv.framework.userinteraction.TextInputDialogBuilder;
import tools.vitruv.framework.userinteraction.UserInteracting;
import tools.vitruv.framework.util.bridges.EclipseUIBridge;

/**
 * @author messinger
 *
 *         Implements {@link UserInteracting} and decouples {@link UserInteractorDialog} from
 *         Platform.
 *
 */
public class UserInteractor implements UserInteracting, UserInteracting.UserInputListener {
    protected Display display;
    protected Shell shell;
    private Queue<UserInputBase> userInput = new LinkedList<>();

    public UserInteractor() {
        this.init();
    }

    public void init() {
        this.display = PlatformUI.getWorkbench().getDisplay();
        this.shell = null; /* TODO: setting the shell to null makes dialogs have their own task bar entry, but prevents
        the window modality flags from having any effect. */
    }
    
    @Override
	public NotificationDialogBuilder getNotificationDialogBuilder() {
		return new NotificationDialogBuilderImpl(shell, display);
	}

	@Override
	public ConfirmationDialogBuilder getConfirmationDialogBuilder() {
		return new ConfirmationDialogBuilderImpl(shell, display, this);
	}

	@Override
	public TextInputDialogBuilder getTextInputDialogBuilder() {
		return new TextInputDialogBuilderImpl(shell, display, this);
	}

	@Override
	public MultipleChoiceSelectionDialogBuilder getMultipleChoiceSelectionDialogBuilder() {
		return new MultipleChoiceSelectionDialogBuilderImpl(shell, display, this);
	}

	@Override
	public URI selectURI(String message) {
		return EclipseUIBridge.askForNewResource(message);
	}
	
	@Override
	public void onUserInputReceived(UserInputBase input) {
		userInput.add(input);
	}
}
