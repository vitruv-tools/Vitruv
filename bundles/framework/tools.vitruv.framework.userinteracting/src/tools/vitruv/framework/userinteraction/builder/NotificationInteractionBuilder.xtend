package tools.vitruv.framework.userinteraction.builder

import tools.vitruv.framework.userinteraction.NotificationType

/**
 * Defines one single entry point to the build process of a {@link NotificationDialog} thus ensuring that mandatory
 * information has to be provided before continuing. The top-level method represents the first and only mandatory step
 * returning the nested interface which includes optional steps as well as the build method
 * ({@link NotificationDialogBuilder.OptionalSteps} extends {@link DialogBuilder} to provide access to build step
 * methods common to all types of dialogs).<br>
 * <br>
 * For further info on the rationale behind the ...DialogBuilder implementation, see the {@link DialogBuilder} javadoc.
 * 
 * @author Dominik Klooz
 */
interface NotificationInteractionBuilder {
    
    /**
     * Specifies the message of the dialog.<br><br>
     * Calling this method is mandatory, it is thus the only method available in the {@link NotificationDialogBuilder}
     * interface handed out for user interaction and returns a {@link NotificationDialogBuilder.OptionalSteps}
     * implementation to allow for further adjustments and building the adjusted dialog. This is a form of
     * implementation of the Step Builder pattern.
     * 
     * @param message   The message to be set, if {@code null}, an {@link IllegalArgumentException} is thrown.
     */
    def OptionalSteps message(String message)
    
    /**
     * Interface for optional build steps (mostly common to all DialogBuilders as defined by {@link DialogBuilder})
     */
    interface OptionalSteps extends InteractionBuilder<Void, OptionalSteps> {
        
        /**
         * Sets the severity of the notification, depicted as an icon in the dialog content area, window title bar and
         * task bar entry. Can be one of Information, Warning or Error, defaults to Information.<br>
         * For a question dialog, see
         * {@link tools.vitruv.framework.userinteraction.impl.ConfirmationDialog ConfirmationDialog}.
         * 
         * @param type   The notification type to be set, if {@code null}, nothing happens.
         */
        def OptionalSteps notificationType(NotificationType type)
    }
}