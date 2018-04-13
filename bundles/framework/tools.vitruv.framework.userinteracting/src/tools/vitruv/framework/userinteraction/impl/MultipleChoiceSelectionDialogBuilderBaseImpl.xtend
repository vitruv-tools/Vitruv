package tools.vitruv.framework.userinteraction.impl

import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Display
import tools.vitruv.framework.userinteraction.UserInteracting.UserInputListener
import tools.vitruv.framework.userinteraction.MultipleChoiceSelectionDialogBuilder
import tools.vitruv.framework.userinteraction.MultipleChoiceSelectionDialogBuilder.ChoicesStep
import tools.vitruv.framework.userinteraction.MultipleChoiceSelectionDialogBuilder.OptionalSteps

/**
 * Base implementation of the dialog builder for single- and multi-select {@link MultipleChoiceDialog}s. Implementation
 * of {@link #showDialogAndGetUserInput()} is left up to the two concrete subclasses
 * {@link MultipleChoiceSingleSelectionDialogBuilderImpl} and {@link MultipleChoiceMultiSelectionDialogBuilderImpl} so
 * they can specify the return type while inheriting everything else from here.
 */
abstract class MultipleChoiceSelectionDialogBuilderBaseImpl<T> extends BaseDialogBuilder<T, OptionalSteps<T>>
        implements MultipleChoiceSelectionDialogBuilder<T>, ChoicesStep<T>, OptionalSteps<T> {
    protected String[] choices = #["unspecified"]
    
    new(Shell shell, Display display, UserInputListener inputListener) {
        super(shell, display, inputListener)
        title = "Please Select..."
    }
    
    override abstract T startInteraction()
    
    override message(String message) {
        this.message = message
        return this
    }
    
    override choices(String[] choices) {
        if (choices.length < 2) {
            throw new IllegalArgumentException("Provide at least two choices to pick from.")
        }
        if (choices.length == 2) {
            // TODO DK: log (or something?) hint: consider using ConfirmationDialog for dialogs with two possible outcomes
        }
        this.choices = choices
        return this
    }
}