package tools.vitruv.framework.userinteraction.impl

import tools.vitruv.framework.userinteraction.dialogs.ConfirmationDialogWindow
import tools.vitruv.framework.userinteraction.UserInteractionOptions.WindowModality
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Display
import tools.vitruv.framework.userinteraction.dialogs.BaseDialogWindow
import tools.vitruv.framework.userinteraction.dialogs.NotificationDialogWindow
import tools.vitruv.framework.userinteraction.dialogs.TextInputDialogWindow
import tools.vitruv.framework.userinteraction.UserInteractionOptions.InputValidator
import tools.vitruv.framework.userinteraction.dialogs.MultipleChoiceSelectionDialogWindow
import tools.vitruv.framework.userinteraction.UserInteractionOptions.NotificationType
import org.eclipse.ui.PlatformUI
import tools.vitruv.framework.userinteraction.InteractionResultProvider

/**
 * A result provider based on dialog windows to make the requested input.
 * 
 * @author Heiko Klare
 */
class DialogInteractionResultProviderImpl implements InteractionResultProvider {
	private final Shell parentShell;
	private final Display display;

	new() {
		this.display = if (PlatformUI.isWorkbenchRunning()) PlatformUI.getWorkbench().getDisplay() else PlatformUI.
			createDisplay();
		this.parentShell = display.getActiveShell();
	}

	private def void showDialog(BaseDialogWindow dialog) {
		display.syncExec(new Runnable() {
			override void run() {
				dialog.show();
			}
		});
	}

	override getConfirmationInteractionResult(WindowModality windowModality, String title, String message,
		String positiveDecisionText, String negativeDecisionText, String cancelDecisionText) {
		val dialog = new ConfirmationDialogWindow(parentShell, windowModality, title, message, positiveDecisionText,
			negativeDecisionText, cancelDecisionText);
		dialog.showDialog();
		return dialog.getConfirmed;
	}

	override getNotificationInteractionResult(WindowModality windowModality, String title, String message,
		String positiveDecisionText, NotificationType notificationType) {
		val dialog = new NotificationDialogWindow(parentShell, windowModality, title, message, positiveDecisionText,
			notificationType);
		dialog.showDialog();
	}

	override getTextInputInteractionResult(WindowModality windowModality, String title, String message,
		String positiveDecisionText, String cancelDecisionText, InputValidator inputValidator) {
		val dialog = new TextInputDialogWindow(parentShell, windowModality, title, message, positiveDecisionText,
			cancelDecisionText, inputValidator);
		dialog.showDialog()
		return dialog.inputText;
	}

	override getMultipleChoiceSingleSelectionInteractionResult(WindowModality windowModality, String title,
		String message, String positiveDecisionText, String cancelDecisionText, Iterable<String> choices) {
		val dialog = new MultipleChoiceSelectionDialogWindow(parentShell, windowModality, title, message,
			positiveDecisionText, cancelDecisionText, false, choices);
		dialog.showDialog()
		return dialog.selectedChoices.head;
	}

	override getMultipleChoiceMultipleSelectionInteractionResult(WindowModality windowModality, String title,
		String message, String positiveDecisionText, String cancelDecisionText, Iterable<String> choices) {
		val dialog = new MultipleChoiceSelectionDialogWindow(parentShell, windowModality, title, message,
			positiveDecisionText, cancelDecisionText, true, choices);
		dialog.showDialog()
		return dialog.selectedChoices;
	}

	override getDecoratedInteractionResultProvider() {
		return this;
	}

}
