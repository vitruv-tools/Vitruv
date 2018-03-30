package tools.vitruv.framework.userinteraction.impl

import java.util.Collection
import tools.vitruv.framework.userinteraction.MultipleChoiceSelectionDialogBuilder
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Display
import tools.vitruv.framework.userinteraction.SelectionType

/**
 * Builder class for {@link MultipleChoiceSelectionDialog}s.
 * Creates a dialog with check boxes or radio buttons to pick multiple or one entry from a list of choices.<br>
 * <br>
 * For further info on the rationale behind the ...DialogBuilder implementation, see the {@link DialogBuilder} javadoc.
 * @see MultipleChoiceSelectionDialogBuilder
 * 
 * @author Dominik Klooz
 */
class MultipleChoiceSelectionDialogBuilderImpl extends BaseDialogBuilder<Collection<Integer>>
        implements MultipleChoiceSelectionDialogBuilder, MultipleChoiceSelectionDialogBuilder.ChoicesStep,
        MultipleChoiceSelectionDialogBuilder.OptionalSteps {
    private MultipleChoiceSelectionDialog dialog
    private String[] choices = #["unspecified"]
    private SelectionType selectionType = SelectionType.SINGLE_SELECT
    
    new(Shell shell, Display display) {
        super(shell, display)
        title = "Please Select..."
    }
    
    override message(String message) {
        this.message = message
        return this
    }
    
    override choices(String[] choices) {
        if (choices.length < 2) {
            throw new IllegalArgumentException("Provide at least two choices to pick from.")
        }
        if (choices.length == 2) {
            // TODO: log hint: consider using ConfirmationDialog for dialogs with two possible outcomes
        }
        this.choices = choices
        return this
    }
    
    override selectionType(SelectionType selectionType) {
        this.selectionType = selectionType
        return this
    }
    
    override showDialogAndGetUserInput() {
        dialog = new MultipleChoiceSelectionDialog(shell, windowModality, title, message, choices, selectionType)
        openDialog()
        return dialog.selectedChoices
    }
}
