package tools.vitruv.framework.userinteraction.impl

import tools.vitruv.framework.userinteraction.TextInputDialogBuilder
import tools.vitruv.framework.userinteraction.impl.TextInputDialog.InputValidator
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Display
import java.util.function.Function
import tools.vitruv.framework.userinteraction.InputFieldType
import tools.vitruv.framework.change.interaction.impl.InteractionFactoryImpl
import tools.vitruv.framework.userinteraction.TextInputDialogBuilder.OptionalSteps
import tools.vitruv.framework.userinteraction.UserInteractionListener

/**
 * Builder class for {@link TextInputDialog}s. Use the add/set... methods to specify details and then call
 * createAndShow() to display and get a reference to the configured dialog.
 * Creates a dialog with a text input field (configurable to accept single or multi-line input). A {@link InputValidator}
 * can also be specified which limits the input to strings conforming to its
 * {@link InputValidator#isInputValid(String) isInputValid} method (the default validator accepts all input).<br>
 * <br>
 * For further info on the rationale behind the ...DialogBuilder implementation, see the {@link DialogBuilder} javadoc.
 * @see TextInputDialogBuilder
 * 
 * @author Dominik Klooz
 */
class TextInputDialogBuilderImpl extends BaseDialogBuilder<String, OptionalSteps> implements TextInputDialogBuilder,
        OptionalSteps {
    private TextInputDialog dialog
    private InputFieldType inputFieldType = InputFieldType.SINGLE_LINE
    private InputValidator inputValidator = TextInputDialog.ACCEPT_ALL_INPUT_VALIDATOR
    
    new(Shell shell, Display display, UserInteractionListener inputListener) {
        super(shell, display, inputListener)
        title = "Input Text..."
    }
    
    override message(String message) {
        setMessage(message)
        return this
    }
    
    override inputValidator(InputValidator inputValidator) {
        if (inputValidator !== null) {
            this.inputValidator = inputValidator
        }
        return this
    }
    
    override inputValidator(Function<String, Boolean> validatorFunction, String invalidInputMessage) {
        if (validatorFunction !== null && invalidInputMessage !== null) {
            this.inputValidator = new InputValidator() {
                override getInvalidInputMessage(String input) { invalidInputMessage }
                override isInputValid(String input) { validatorFunction.apply(input) }
            }
        }
        return this
    }
    
    override inputFieldType(InputFieldType inputFieldType) {
        if (inputFieldType !== null) {
            this.inputFieldType = inputFieldType
        }
        return this
    }

    override def String startInteraction() {
        dialog = new TextInputDialog(shell, windowModality, title, message, inputFieldType, inputValidator)
        openDialog()
        var userInput = InteractionFactoryImpl.eINSTANCE.createFreeTextUserInteraction()
        userInput.message = message
        userInput.text = dialog.input
        notifyUserInputReceived(userInput)
        return dialog.input
    }
    
}
