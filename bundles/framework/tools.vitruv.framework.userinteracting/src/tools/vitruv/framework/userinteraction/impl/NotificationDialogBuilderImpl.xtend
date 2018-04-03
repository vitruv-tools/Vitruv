package tools.vitruv.framework.userinteraction.impl

import tools.vitruv.framework.userinteraction.NotificationDialogBuilder
import tools.vitruv.framework.userinteraction.NotificationType
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Shell

/**
 * Builder class for {@link NotificationDialog}s.
 * Creates a dialog with a notification message and an icon depicting the severity.<br>
 * <br>
 * For further info on the rationale behind the ...DialogBuilder implementation, see the {@link DialogBuilder} javadoc.
 * @see NotificationDialogBuilder
 * 
 * @author Dominik Klooz
 */
class NotificationDialogBuilderImpl extends BaseDialogBuilder<Void> implements NotificationDialogBuilder,
        NotificationDialogBuilder.OptionalSteps {
    private NotificationDialog dialog
    private NotificationType notificationType = NotificationType.INFORMATION
    
    new(Shell shell, Display display) {
        super(shell, display, null) // notifications take no input, so there is no need for a UserInputListener
        title = "Notification"
    }
    
    override notificationType(NotificationType notificationType) {
        this.notificationType = notificationType
        return this
    }

    override def showDialogAndGetUserInput() {
        dialog = new NotificationDialog(shell, windowModality, notificationType, title, message)
        openDialog()
        return null // notifications don't have any form of user input
    }
    
    override message(String message) {
        this.message = message
        return this
    }
}
