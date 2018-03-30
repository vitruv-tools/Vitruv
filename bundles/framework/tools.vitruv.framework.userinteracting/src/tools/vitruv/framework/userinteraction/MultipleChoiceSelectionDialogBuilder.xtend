package tools.vitruv.framework.userinteraction

import java.util.Collection
import tools.vitruv.framework.userinteraction.SelectionType

/**
 * Defines one single entry point to the build process of a {@link MultipleChoiceSelectionDialog} thus ensuring that
 * mandatory information has to be provided before continuing. The nested interfaces represent another mandatory step
 * ({@link MultipleChoiceSelectionDialogBuilder.ChoicesStep}) and optional steps as well as the build method
 * ({@link MultipleChoiceSelectionDialogBuilder.OptionalSteps}, extends {@link IDialogBuilder<T>} to provide access
 * to build step methods common to all types of dialogs).<br>
 * <br>
 * For further info on the rationale behind the ...DialogBuilder implementation, see the {@link DialogBuilder} javadoc.
 * 
 * @author Dominik Klooz
 */
interface MultipleChoiceSelectionDialogBuilder {
    
    /**
     * Specifies the message of the dialog.<br><br>
     * Calling this method is mandatory, it is thus the only method available in
     * the {@link MultipleChoiceSelectionDialogBuilder} interface handed out for user interaction and returns a
     * {@link MultipleChoiceSelectionDialogBuilder.ChoicesStep} implementation to ensure that the method defined there
     * is called next, as it is also mandatory. This is a form of implementation of the Step Builder pattern.
     */
    def ChoicesStep message(String message)
    
    interface ChoicesStep {
        /**
         * Sets the textual representations of the choices the user can select.
         */
        def OptionalSteps choices(String[] choices)
    }
    
    /**
     * Interface for optional build steps (mostly common to all DialogBuilders as defined by {@link DialogBuilder})
     */
    interface OptionalSteps extends DialogBuilder<Collection<Integer>> {
        /**
         * Sets the selection type, whether the user can select multiple choices (e.g. via check boxes) or a single one
         * (e.g. via radio buttons). The standard type is the latter, {@link SelectionType#SINGLE_SELECT}.
         */
        def OptionalSteps selectionType(SelectionType selectionType)
    }
}