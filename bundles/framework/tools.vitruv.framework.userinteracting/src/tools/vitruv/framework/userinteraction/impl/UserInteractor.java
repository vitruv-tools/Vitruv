package tools.vitruv.framework.userinteraction.impl;

import org.eclipse.emf.common.util.URI;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import tools.vitruv.framework.userinteraction.UserInteracting;
import tools.vitruv.framework.util.bridges.EclipseUIBridge;

/**
 * @author messinger
 *
 *         Implements {@link UserInteracting} and decouples {@link UserInteractorDialog} from
 *         Platform.
 *
 */
public class UserInteractor implements UserInteracting {
    protected Display display;
    protected Shell shell;

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
		return new NotificationDialogBuilder(shell, display);
	}

	@Override
	public ConfirmationDialogBuilder getConfirmationDialogBuilder() {
		return new ConfirmationDialogBuilder(shell, display);
	}

	@Override
	public TextInputDialogBuilder getTextInputDialogBuilder() {
		return new TextInputDialogBuilder(shell, display);
	}

	@Override
	public MultipleChoiceSelectionDialogBuilder getMultipleChoiceSelectionDialogBuilder() {
		return new MultipleChoiceSelectionDialogBuilder(shell, display);
	}

	@Override
	public URI selectURI(String message) {
		return EclipseUIBridge.askForNewResource(message);
	}
}
