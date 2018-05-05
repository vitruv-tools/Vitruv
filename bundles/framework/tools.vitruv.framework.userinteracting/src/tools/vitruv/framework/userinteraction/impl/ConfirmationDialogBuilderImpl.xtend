package tools.vitruv.framework.userinteraction.impl

import tools.vitruv.framework.userinteraction.ConfirmationDialogBuilder
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Display
import tools.vitruv.framework.change.interaction.impl.InteractionFactoryImpl
import tools.vitruv.framework.userinteraction.ConfirmationDialogBuilder.OptionalSteps
import tools.vitruv.framework.userinteraction.InternalConfirmationDialogBuilder
import tools.vitruv.framework.userinteraction.UserInputListener

/**
 * Builder class for {@link ConfirmationDialog}s.
 * Creates a dialog with a question and buttons to give a positive or negative answer.<br>
 * <br>
 * For further info on the rationale behind the ...DialogBuilder implementation, see the {@link DialogBuilder} javadoc.
 * 
 * @see ConfirmationDialogBuilder
 * 
 * @author Dominik Klooz
 */
class ConfirmationDialogBuilderImpl extends BaseDialogBuilder<Boolean, OptionalSteps>
        implements InternalConfirmationDialogBuilder, OptionalSteps {
    private ConfirmationDialog dialog
    
    public static final String STANDARD_TITLE = "Please Confirm"
    
    new(Shell shell, Display display, UserInputListener inputListener) {
        super(shell, display, inputListener)
        title = STANDARD_TITLE
    }
    
    override message(String message) {
        this.message = message
        return this
    }

    override def Boolean startInteraction() {
        dialog = new ConfirmationDialog(shell, windowModality, title, message)
        openDialog()
        var userInput = InteractionFactoryImpl.eINSTANCE.createConfirmationUserInput()
        userInput.message = message
        userInput.confirmed = dialog.getConfirmed()
        notifyUserInputReceived(userInput)
        return dialog.getConfirmed()
    }
    
    override getIntermediateDialog() {
        return dialog
    }
    
}
