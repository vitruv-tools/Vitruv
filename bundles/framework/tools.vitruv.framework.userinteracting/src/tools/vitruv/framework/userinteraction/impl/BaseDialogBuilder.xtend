package tools.vitruv.framework.userinteraction.impl

import tools.vitruv.framework.userinteraction.DialogBuilder
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Display
import tools.vitruv.framework.userinteraction.WindowModality
import tools.vitruv.framework.change.interaction.UserInteractionBase
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.userinteraction.UserInteractionListener

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
 * @param <T> type parameter for the return type of methods declared in {@link DialogBuilder}. In subclasses, this is to
 *          be set to the specific builder's {@code OptionalSteps} interface (the interface that extends
 *          {@link DialogBuilder} and contains methods whose execution is optional when building a dialog).
 * 
 * @author Dominik Klooz
 */
@Accessors abstract class BaseDialogBuilder<V, T extends DialogBuilder<V, T>> implements DialogBuilder<V, T> {
    private BaseDialog dialog
    private Shell shell
    private Display display
    private String title = "Unspecified Title"
    private String message = "No message specified."
    private WindowModality windowModality = WindowModality.MODELESS
    private String positiveButtonText = "Yes"
    private String negativeButtonText = "No"
    private String cancelButtonText = "Cancel"
    @Accessors(NONE) UserInteractionListener userInputListener;
    
    new(Shell shell, Display display, UserInteractionListener inputListener) {
        this.shell = shell
        this.display = display
        userInputListener = inputListener
    }
    
    /**
     * @inheritDoc
     * Implementations should call {@link #openDialog()} to make sure displaying the dialog is run on the UI thread.
     */
    override abstract V startInteraction()
    
    def notifyUserInputReceived(UserInteractionBase input) {
        userInputListener.onUserInteractionReceived(input)
    }
    
    def setMessage(String message) {
        if (message === null) {
            throw new IllegalArgumentException("Message is null!")
        }
        this.message = message
    }
    
    protected def void openDialog() {
        display.syncExec(new Runnable() {
            override void run() {
                dialog.show();
            }
        });
    }
    
    override T title(String title) {
        if (title !== null) {
            this.title = title
        }
        return this as T
    }
    
    override T windowModality(WindowModality windowModality) {
        if (windowModality !== null) {
            this.windowModality = windowModality
        }
        return this as T
    }
    
    override T positiveButtonText(String text) {
        if (text !== null) {
            this.positiveButtonText = text
        }
        return this as T
    }
    
    override T negativeButtonText(String text) {
        if (text !== null) {
            this.negativeButtonText = text
        }
        return this as T
    }
    
    override T cancelButtonText(String text) {
        if (text !== null) {
            this.cancelButtonText = text
        }
        return this as T
    }
}
