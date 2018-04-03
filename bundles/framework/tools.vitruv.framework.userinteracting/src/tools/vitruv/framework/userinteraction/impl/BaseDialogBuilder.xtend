package tools.vitruv.framework.userinteraction.impl

import tools.vitruv.framework.userinteraction.DialogBuilder
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Display
import tools.vitruv.framework.userinteraction.WindowModality
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.change.interaction.UserInputBase

/**
 * Abstract base class for dialog builder objects. The dialog to be built is created and returned in createAndShow, the
 * other methods are to be used beforehand to specify adjustments to the dialogs contents / behavior. Standard values
 * for properties not specified using the respective methods are set here or in the constructor for subclasses and
 * subclass-specific properties.<br>
 * <br>
 * For further info on the rationale behind the ...DialogBuilder implementation, see the {@link DialogBuilder} javadoc.
 *
 * @param <V> type parameter for the return type of {@link #getResult() getResult()}, which returns the user input from
 *          the dialog.
 * 
 * @author Dominik Klooz
 */
abstract class BaseDialogBuilder<V> implements DialogBuilder<V> {
    protected BaseDialog dialog
    protected Shell shell
    protected Display display
    protected String title = "Unspecified Title"
    protected String message = "No message specified."
    protected WindowModality windowModality = WindowModality.MODELESS
    protected String positiveButtonText = "Yes"
    protected String negativeButtonText = "No"
    protected String cancelButtonText = "Cancel"
    private UserInteracting.UserInputListener userInputListener;
    
    new(Shell shell, Display display, UserInteracting.UserInputListener inputListener) {
        this.shell = shell
        this.display = display
        userInputListener = inputListener
    }
    
    /**
     * @inheritDoc
     * Implementations should call {@link #openDialog()} to make sure displaying the dialog is run on the UI thread.
     */
    override abstract V showDialogAndGetUserInput()
    
    def notifyUserInputReceived(UserInputBase input) {
        userInputListener.onUserInputReceived(input)
    }
    
    protected def void openDialog() {
        display.syncExec(new Runnable() {
            override void run() {
                dialog.show();
            }
        });
    }
    
    override DialogBuilder<V> title(String title) {
        this.title = title
        return this
    }
    
    override DialogBuilder<V> windowModality(WindowModality windowModality) {
        this.windowModality = windowModality
        return this
    }
    
    override DialogBuilder<V> positiveButtonText(String text) {
        this.positiveButtonText = text
        return this
    }
    
    override DialogBuilder<V> negativeButtonText(String text) {
        this.negativeButtonText = text
        return this
    }
    
    override DialogBuilder<V> cancelButtonText(String text) {
        this.cancelButtonText = text
        return this
    }
}
