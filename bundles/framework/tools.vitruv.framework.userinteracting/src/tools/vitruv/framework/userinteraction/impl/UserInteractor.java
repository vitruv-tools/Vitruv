package tools.vitruv.framework.userinteraction.impl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import org.eclipse.emf.common.util.URI;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import tools.vitruv.framework.change.interaction.UserInputBase;
import tools.vitruv.framework.userinteraction.ConfirmationDialogBuilder;
import tools.vitruv.framework.userinteraction.InternalUserInteracting;
import tools.vitruv.framework.userinteraction.MultipleChoiceMultiSelectionDialogBuilder;
import tools.vitruv.framework.userinteraction.MultipleChoiceSingleSelectionDialogBuilder;
import tools.vitruv.framework.userinteraction.NotificationDialogBuilder;
import tools.vitruv.framework.userinteraction.TextInputDialogBuilder;
import tools.vitruv.framework.userinteraction.UserInteracting;
import tools.vitruv.framework.util.bridges.EclipseUIBridge;

/**
 * Implementation of the {@link InternalUserInteracting} interface providing dialog builders for different cases of user
 * interaction. Through the {@link UserInteracting.UserInputListener}, this class keeps track of user input entered
 * through any of the dialogs constructed from here to be retrieved for bookkeeping/reuse.
 * 
 * @author Dominik Klooz
 */
public class UserInteractor implements InternalUserInteracting, UserInteracting.UserInputListener {
    protected Display display;
    protected Shell shell;
    private Queue<UserInputBase> userInput = new LinkedList<>();

    public UserInteractor() {
        this.init();
    }

    public void init() {
        this.display = PlatformUI.getWorkbench().getDisplay();
        this.shell = null; /* TODO DK: setting the shell to null makes dialogs have their own task bar entry, but
        prevents the window modality flags from having any effect. */
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
	public MultipleChoiceSingleSelectionDialogBuilder getSingleSelectionDialogBuilder() {
		return new MultipleChoiceSingleSelectionDialogBuilderImpl(shell, display, this);
	}
	
	@Override
	public MultipleChoiceMultiSelectionDialogBuilder getMultiSelectionDialogBuilder() {
		return new MultipleChoiceMultiSelectionDialogBuilderImpl(shell, display, this);
	}

	// TODO DK: still needed/used?
	@Override
	public URI selectURI(String message) {
		return EclipseUIBridge.askForNewResource(message);
	}
	
	@Override
	public void onUserInputReceived(UserInputBase input) {
		userInput.add(input);
	}

	@Override
	public Collection<UserInputBase> getUserInputs() {
		return userInput;
	}
	
	@Override
	public void resetUserInputs() {
		userInput.clear();
	}
}
