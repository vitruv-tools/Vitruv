package tools.vitruv.framework.userinteraction.impl

import tools.vitruv.framework.userinteraction.ConfirmationDialogBuilder
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Display

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
class ConfirmationDialogBuilderImpl extends BaseDialogBuilder<Boolean> implements ConfirmationDialogBuilder {
    private ConfirmationDialog dialog
    
    public static final String STANDARD_TITLE = "Please Confirm"
    
    new(Shell shell, Display display) {
        super(shell, display)
        title = STANDARD_TITLE
    }
    
    override message(String message) {
        this.message = message
        return this
    }

    override def Boolean showDialogAndGetUserInput() {
        dialog = new ConfirmationDialog(shell, windowModality, title, message)
        openDialog()
        return dialog.getResult()
    }
}
