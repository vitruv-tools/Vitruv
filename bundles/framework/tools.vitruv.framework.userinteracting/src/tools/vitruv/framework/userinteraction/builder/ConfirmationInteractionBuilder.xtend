package tools.vitruv.framework.userinteraction.builder

import tools.vitruv.framework.userinteraction.types.ConfirmationInteraction

/**
 * Defines one single entry point to the build process of a {@link ConfirmationDialog} thus ensuring that mandatory
 * information has to be provided before continuing. The top-level method represents the first and only mandatory step
 * returning the nested interface which includes optional steps as well as the build method
 * ({@link ConfirmationDialogBuilder.OptionalSteps} extends {@link DialogBuilder} to provide access to build step
 * methods common to all types of dialogs).
 * <br>
 * <br>
 * For further info on the rationale behind the ...DialogBuilder implementation, see the {@link DialogBuilder} javadoc.
 * 
 * @author Dominik Klooz
 */
interface ConfirmationInteractionBuilder {
    
    /**
     * Specifies the message of the dialog.<br><br>
     * Calling this method is mandatory, it is thus the only method available in the {@link ConfirmationDialogBuilder}
     * interface handed out for user interaction and returns a {@link DialogBuilder} implementation to allow for further
     * adjustments and building the adjusted dialog ({@link ConfirmationDialog}s don't provide any adjustments that
     * aren't already defined for all types of dialogs in the {@link DialogBuilder} interface).<br>
     * This is a form of implementation of the Step Builder pattern.
     * 
     * @param message   The message to be set, if {@code null}, an {@link IllegalArgumentException} is thrown.
     */
    def OptionalSteps message(String message)
    
    /**
     * Interface for optional build steps (none needed), build steps common to all types of dialogs and the method to
     * create and use the dialog as declared in {@link DialogBuilder}. This represents the final step of this dialog's
     * build process.
     */
    interface OptionalSteps extends InteractionBuilder<Boolean, OptionalSteps> { }
}

interface InternalConfirmationDialogBuilder extends ConfirmationInteractionBuilder {
    
    /**
     * Provides access to the dialog being built using this builder before it is displayed for behind-the-scenes use
     * (e.g. for user interaction in reuse scenarios).
     */
    def ConfirmationInteraction getIntermediateDialog()
}
