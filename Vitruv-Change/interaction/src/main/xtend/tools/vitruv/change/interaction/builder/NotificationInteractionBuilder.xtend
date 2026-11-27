package tools.vitruv.change.interaction.builder

import tools.vitruv.change.interaction.UserInteractionOptions.NotificationType
import tools.vitruv.change.interaction.types.ConfirmationInteraction

/**
 * Defines one single entry point to the build process of a {@link tools.vitruv.change.interaction.types.NotificationInteraction} thus ensuring that mandatory
 * information has to be provided before continuing. The top-level method represents the first and only mandatory step
 * returning the nested interface which includes optional steps as well as the build method
 * ({@link NotificationInteractionBuilder.OptionalSteps} extends {@link InteractionBuilder} to provide access to build step
 * methods common to all types of interactions).<br>
 * <br>
 * For further info on the rationale behind the ...InteractionBuilder implementation, see the {@link InteractionBuilder} javadoc.
 * 
 * @author Dominik Klooz
 */
interface NotificationInteractionBuilder {

	/**
	 * Specifies the message of the interaction.<br><br>
	 * Calling this method is mandatory, it is thus the only method available in the {@link NotificationInteractionBuilder}
	 * interface handed out for user interaction and returns a {@link NotificationInteractionBuilder.OptionalSteps}
	 * implementation to allow for further adjustments and building the adjusted interaction. This is a form of
	 * implementation of the Step Builder pattern.
	 * 
	 * @param message   The message to be set, if {@code null}, an {@link IllegalArgumentException} is thrown.
	 */
	def OptionalSteps message(String message)

	/**
	 * Interface for optional build steps (mostly common to all InteractionBuilders as defined by {@link InteractionBuilder})
	 */
	interface OptionalSteps extends InteractionBuilder<Void, OptionalSteps> {

		/**
		 * Sets the severity of the notification, depicted as an icon in the interaction content area, window title bar and
		 * task bar entry. Can be one of Information, Warning or Error, defaults to Information.<br>
		 * For a question interaction, see
		 * {@link ConfirmationInteraction}.
		 * 
		 * @param type   The notification type to be set, if {@code null}, nothing happens.
		 */
		def OptionalSteps notificationType(NotificationType type)
	}
}
