package tools.vitruv.framework.userinteraction

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
interface ConfirmationDialogBuilder {
    
    /**
     * Specifies the message of the dialog.<br><br>
     * Calling this method is mandatory, it is thus the only method available in the {@link ConfirmationDialogBuilder}
     * interface handed out for user interaction and returns a {@link DialogBuilder} implementation to allow for further
     * adjustments and building the adjusted dialog ({@link ConfirmationDialog}s don't provide any adjustments that
     * aren't already defined for all types of dialogs in the {@link DialogBuilder} interface).<br>
     * This is a form of implementation of the Step Builder pattern.
     */
    def OptionalSteps message(String message)
    
    interface OptionalSteps extends DialogBuilder<Boolean, OptionalSteps> { }
}
