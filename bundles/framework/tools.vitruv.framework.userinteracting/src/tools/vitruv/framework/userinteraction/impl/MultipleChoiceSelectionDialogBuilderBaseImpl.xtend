package tools.vitruv.framework.userinteraction.impl

import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Display
import tools.vitruv.framework.userinteraction.MultipleChoiceSelectionDialogBuilder
import tools.vitruv.framework.userinteraction.MultipleChoiceSelectionDialogBuilder.ChoicesStep
import tools.vitruv.framework.userinteraction.MultipleChoiceSelectionDialogBuilder.OptionalSteps
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.userinteraction.UserInteractionListener

/**
 * Base implementation of the dialog builder for single- and multi-select {@link MultipleChoiceDialog}s. Implementation
 * of {@link #showDialogAndGetUserInput()} is left up to the two concrete subclasses
 * {@link MultipleChoiceSingleSelectionDialogBuilderImpl} and {@link MultipleChoiceMultiSelectionDialogBuilderImpl} so
 * they can specify the return type while inheriting everything else from here.
 */
abstract class MultipleChoiceSelectionDialogBuilderBaseImpl<T> extends BaseDialogBuilder<T, OptionalSteps<T>>
        implements MultipleChoiceSelectionDialogBuilder<T>, ChoicesStep<T>, OptionalSteps<T> {
    @Accessors private String[] choices = #["unspecified"]
    
    new(Shell shell, Display display, UserInteractionListener inputListener) {
        super(shell, display, inputListener)
        title = "Please Select..."
    }
    
    override abstract T startInteraction()
    
    override message(String message) {
        setMessage(message)
        return this
    }
    
    override choices(String[] choices) {
        if (choices === null || choices.length < 2) {
            throw new IllegalArgumentException("Provide at least two choices to pick from.")
        }
        this.choices = choices
        return this
    }
}