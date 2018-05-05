package tools.vitruv.framework.userinteraction.impl

import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Shell
import tools.vitruv.framework.change.interaction.impl.InteractionFactoryImpl
import tools.vitruv.framework.userinteraction.DialogBuilder
import tools.vitruv.framework.userinteraction.SelectionType
import tools.vitruv.framework.userinteraction.MultipleChoiceSingleSelectionDialogBuilder
import tools.vitruv.framework.userinteraction.UserInputListener

/**
 * Builder class for {@link MultipleChoiceSelectionDialog}s.
 * Creates a dialog with check boxes or radio buttons to pick multiple or a single entry from a list of choices.<br>
 * <br>
 * For further info on the rationale behind the ...DialogBuilder implementation, see the {@link DialogBuilder} javadoc.
 * @see MultipleChoiceSelectionDialogBuilder
 * 
 * @author Dominik Klooz
 */
class MultipleChoiceSingleSelectionDialogBuilderImpl extends MultipleChoiceSelectionDialogBuilderBaseImpl<Integer>
        implements MultipleChoiceSingleSelectionDialogBuilder {
    private MultipleChoiceSelectionDialog dialog;
    
    new(Shell shell, Display display, UserInputListener inputListener) {
        super(shell, display, inputListener)
    }
    
    override startInteraction() {
        dialog = new MultipleChoiceSelectionDialog(shell, windowModality, title, message, choices, SelectionType.SINGLE_SELECT)
        openDialog()
        var userInput = InteractionFactoryImpl.eINSTANCE.createMultipleChoiceSingleSelectionUserInput()
        userInput.message = message
        userInput.choices.addAll(choices)
        userInput.selectedIndex = dialog.selectedChoices.head
        notifyUserInputReceived(userInput)
        return userInput.selectedIndex
    }
}
