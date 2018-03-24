package tools.vitruv.framework.userinteraction.impl;

import java.util.Arrays;

import javax.swing.JOptionPane;

import org.eclipse.emf.common.util.URI;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import tools.vitruv.framework.change.interaction.ConfirmationUserInput;
import tools.vitruv.framework.change.interaction.FreeTextUserInput;
import tools.vitruv.framework.change.interaction.InteractionFactory;
import tools.vitruv.framework.change.interaction.MultipleChoiceMultiSelectionUserInput;
import tools.vitruv.framework.change.interaction.MultipleChoiceSingleSelectionUserInput;
import tools.vitruv.framework.userinteraction.NotificationType;
import tools.vitruv.framework.userinteraction.UserInteracting;
import tools.vitruv.framework.userinteraction.UserInteractionType;
import tools.vitruv.framework.userinteraction.WindowModality;
import tools.vitruv.framework.userinteraction.impl.MultipleChoiceSelectionDialog.SelectionType;
import tools.vitruv.framework.util.bridges.EclipseUIBridge;

/**
 * @author messinger
 *
 *         Implements {@link UserInteracting} and decouples {@link UserInteractorDialog} from
 *         Platform.
 *
 */
public class UserInteractor implements UserInteracting {

    private static final String VITRUVIUS_TEXT_INPUT_DIALOG = "Vitruvius Text Input Dialog";
    protected Display display;
    protected Shell shell;

    public UserInteractor() {
        this.init();
    }

    public void init() {
        this.display = PlatformUI.getWorkbench().getDisplay();
        this.shell = null;
    }
    
    @Override
	public void showNotification(String title, String message, NotificationType notificationType,
			WindowModality windowModality) {
    	this.display.syncExec(new Runnable() {
            @Override
            public void run() {
                NotificationDialog dialog = new NotificationDialog(shell, windowModality, notificationType, title,
                		message);
                dialog.show();
            }
        });
	}
    
    @Override
	public boolean getUserConfirmation(String title, String message, WindowModality windowModality) {
    	ConfirmationUserInput input = InteractionFactory.eINSTANCE.createConfirmationUserInput();
    	this.display.syncExec(new Runnable() {
            @Override
            public void run() {
                ConfirmationDialog dialog = new ConfirmationDialog(shell, windowModality, title, message);
                dialog.show();
                input.setConfirmed(dialog.getResult());
            }
        });
		return input.isConfirmed();
	}
    
    @Override
	public String getTextInput(String title, String message, WindowModality windowModality) {
    	FreeTextUserInput input = InteractionFactory.eINSTANCE.createFreeTextUserInput();
    	this.display.syncExec(new Runnable() {
            @Override
            public void run() {
                TextInputDialog dialog = new TextInputDialog(shell, windowModality, title, message);
                dialog.show();
                input.setText(dialog.getInput());
            }
        });
		return input.getText();
	}
    
    @Override
	public int selectSingle(String title, String message,
			String[] selectionDescriptions, WindowModality windowModality) {
    	MultipleChoiceSingleSelectionUserInput input =
    			InteractionFactory.eINSTANCE.createMultipleChoiceSingleSelectionUserInput();
    	this.display.syncExec(new Runnable() {
            @Override
            public void run() {
                MultipleChoiceSelectionDialog dialog = new MultipleChoiceSelectionDialog(shell, windowModality, title,
                		message, selectionDescriptions, SelectionType.SINGLE_SELECT);
                dialog.show();
                input.setSelectedIndex(dialog.getSelectedChoices()[0]);
            }
        });
		return input.getSelectedIndex();
	}
    
    @Override
	public int[] selectMulti(String title, String message,
			String[] selectionDescriptions, WindowModality windowModality) {
    	MultipleChoiceMultiSelectionUserInput input =
    			InteractionFactory.eINSTANCE.createMultipleChoiceMultiSelectionUserInput();
    	this.display.syncExec(new Runnable() {
            @Override
            public void run() {
                MultipleChoiceSelectionDialog dialog = new MultipleChoiceSelectionDialog(shell, windowModality, title,
                		message, selectionDescriptions, SelectionType.MULTI_SELECT);
                dialog.show();
                for (int index : dialog.getSelectedChoices()) { //TODO
                	input.getSelectedIndices().add(index);
                }
            }
        });
		return input.getSelectedIndices().stream().mapToInt(i -> i).toArray();
	}

    @Override
    public void showMessage(final UserInteractionType type, final String message) {
        this.display.syncExec(new Runnable() {
            @Override
            public void run() {
                switch (type) {
                case MODAL:
                case MODAL_POSTPONABLE:
                    // showMessage is not postponable
                    UserInteractorDialog.showMessage(UserInteractor.this.shell, true, message);
                    break;
                case MODELESS:
                    UserInteractorDialog.showMessage(UserInteractor.this.shell, false, message);
                    break;
                default:
                    break;
                }
            }
        });
    }

    @Override
    public int selectFromMessage(final UserInteractionType type, final String message,
            final String... selectionDescriptions) {
        final int[] result = new int[1];
        this.display.syncExec(new Runnable() {

            @Override
            public void run() {
                switch (type) {
                case MODAL:
                    result[0] = UserInteractorDialog.selectFromMessage(UserInteractor.this.shell, true, false, message,
                            selectionDescriptions);
                    break;
                case MODAL_POSTPONABLE:
                    result[0] = UserInteractorDialog.selectFromMessage(UserInteractor.this.shell, true, true, message,
                            selectionDescriptions);
                    break;
                case MODELESS:
                    result[0] = UserInteractorDialog.selectFromMessage(UserInteractor.this.shell, false, false, message,
                            selectionDescriptions);
                    break;
                default:
                    result[0] = -1;
                    break;
                }
            }
        });
        return result[0];
    }

    @Override
    public String getTextInput(final String msg) {
        final String textInput = JOptionPane.showInputDialog(null, msg, VITRUVIUS_TEXT_INPUT_DIALOG,
                JOptionPane.OK_OPTION);
        return textInput;
    }

	@Override
	public URI selectURI(String message) {
		return EclipseUIBridge.askForNewResource(message);
	}

}
