package tools.vitruv.framework.userinteraction.builder

import java.util.Collection

/**
 * Defines one single entry point to the build process of a {@link MultipleChoiceSelectionInteraction} thus ensuring that
 * mandatory information has to be provided before continuing. The nested interfaces represent another mandatory step
 * ({@link MultipleChoiceSelectionInteractionBuilder.ChoicesStep}) and optional steps as well as the build method
 * ({@link MultipleChoiceSelectionInteractionBuilder.OptionalSteps}, extends {@link InteractionBuilder} to provide access
 * to build step methods common to all types of Interactions).<br>
 * <br>
 * For further info on the rationale behind the ...InteractionBuilder implementation, see the {@link InteractionBuilder} javadoc.
 * 
 * @author Dominik Klooz
 */
interface MultipleChoiceSelectionInteractionBuilder<T> {
    
    /**
     * Specifies the message of the interaction.<br><br>
     * Calling this method is mandatory, it is thus the only method available in
     * the {@link MultipleChoiceSelectionInteractionBuilder} interface handed out for user interaction and returns a
     * {@link MultipleChoiceSelectionInteractionBuilder.ChoicesStep} implementation to ensure that the method defined there
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
     * Interface for optional build steps (mostly common to all InteractionBuilders as defined by {@link InteractionBuilder})
     */
    interface OptionalSteps<T> extends InteractionBuilder<T, OptionalSteps<T>> {
        
    }
}

/**
 * Interface wrapping the generic type for single selection multiple choice interactions.
 */
interface MultipleChoiceSingleSelectionInteractionBuilder extends MultipleChoiceSelectionInteractionBuilder<Integer> { }

/**
 * Interface wrapping the generic type for multiple selection multiple choice interactions.
 */
interface MultipleChoiceMultiSelectionInteractionBuilder extends MultipleChoiceSelectionInteractionBuilder<Collection<Integer>> { }