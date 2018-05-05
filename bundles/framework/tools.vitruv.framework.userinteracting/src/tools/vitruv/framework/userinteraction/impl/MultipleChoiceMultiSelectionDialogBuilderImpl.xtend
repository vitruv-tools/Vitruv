package tools.vitruv.framework.userinteraction.impl

import java.util.Collection
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Display
import tools.vitruv.framework.userinteraction.SelectionType
import tools.vitruv.framework.change.interaction.impl.InteractionFactoryImpl
import tools.vitruv.framework.userinteraction.MultipleChoiceMultiSelectionDialogBuilder
import tools.vitruv.framework.userinteraction.UserInputListener

/**
 * Implementation of a dialog builder for multiple choice dialogs that allow multiple items to be selected.
 * 
 * @author Dominik Klooz
 */
class MultipleChoiceMultiSelectionDialogBuilderImpl extends MultipleChoiceSelectionDialogBuilderBaseImpl<Collection<Integer>>
        implements MultipleChoiceMultiSelectionDialogBuilder {
    private MultipleChoiceSelectionDialog dialog;
    
    new(Shell shell, Display display, UserInputListener inputListener) {
        super(shell, display, inputListener)
    }
    
    override startInteraction() {
        dialog = new MultipleChoiceSelectionDialog(shell, windowModality, title, message, choices, SelectionType.SINGLE_SELECT)
        openDialog()
        var userInput = InteractionFactoryImpl.eINSTANCE.createMultipleChoiceMultiSelectionUserInput()
        userInput.message = message
        userInput.choices.addAll(choices)
        userInput.selectedIndices.addAll(dialog.selectedChoices)
        notifyUserInputReceived(userInput)
        return userInput.selectedIndices
    }
}
