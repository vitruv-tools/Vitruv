package tools.vitruv.framework.userinteraction

import java.util.Collection

/**
 * Defines one single entry point to the build process of a {@link MultipleChoiceSelectionDialog} thus ensuring that
 * mandatory information has to be provided before continuing. The nested interfaces represent another mandatory step
 * ({@link MultipleChoiceSelectionDialogBuilder.ChoicesStep}) and optional steps as well as the build method
 * ({@link MultipleChoiceSelectionDialogBuilder.OptionalSteps}, extends {@link DialogBuilder} to provide access
 * to build step methods common to all types of dialogs).<br>
 * <br>
 * For further info on the rationale behind the ...DialogBuilder implementation, see the {@link DialogBuilder} javadoc.
 * 
 * @author Dominik Klooz
 */
interface MultipleChoiceSelectionDialogBuilder<T> {
    
    /**
     * Specifies the message of the dialog.<br><br>
     * Calling this method is mandatory, it is thus the only method available in
     * the {@link MultipleChoiceSelectionDialogBuilder} interface handed out for user interaction and returns a
     * {@link MultipleChoiceSelectionDialogBuilder.ChoicesStep} implementation to ensure that the method defined there
     * is called next, as it is also mandatory. This is a form of implementation of the Step Builder pattern.
     * 
     * @param message   The message to be set, if {@code null}, an {@link IllegalArgumentException} is thrown.
     */
    def ChoicesStep<T> message(String message)
    
    interface ChoicesStep<T> {
        /**
         * Sets the textual representations of the choices the user can select.
         * 
         * @param choices   the texts for the choices to be set. If {@code null}, an {@link IllegalArgumentException} is
         * thrown.
         */
        def OptionalSteps<T> choices(String[] choices)
    }
    
    /**
     * Interface for optional build steps (mostly common to all DialogBuilders as defined by {@link DialogBuilder})
     */
    interface OptionalSteps<T> extends DialogBuilder<T, OptionalSteps<T>> {
        
    }
}

/**
 * Interface wrapping the generic type for single selection multiple choice dialogs.
 */
interface MultipleChoiceSingleSelectionDialogBuilder extends MultipleChoiceSelectionDialogBuilder<Integer> { }

/**
 * Interface wrapping the generic type for multiple selection multiple choice dialogs.
 */
interface MultipleChoiceMultiSelectionDialogBuilder extends MultipleChoiceSelectionDialogBuilder<Collection<Integer>> { }