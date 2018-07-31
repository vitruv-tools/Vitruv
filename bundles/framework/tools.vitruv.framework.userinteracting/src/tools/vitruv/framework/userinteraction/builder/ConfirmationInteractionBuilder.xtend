package tools.vitruv.framework.userinteraction.builder

/**
 * Defines one single entry point to the build process of a {@link ConfirmationInteraction} thus ensuring that mandatory
 * information has to be provided before continuing. The top-level method represents the first and only mandatory step
 * returning the nested interface which includes optional steps as well as the build method
 * ({@link ConfirmationInteractionBuilder.OptionalSteps} extends {@link InteractionBuilder} to provide access to build step
 * methods common to all types of interactions).
 * <br>
 * <br>
 * For further info on the rationale behind the ...InteractionBuilder implementation, see the {@link InteractionBuilder} javadoc.
 * 
 * @author Dominik Klooz
 */
interface ConfirmationInteractionBuilder {

	/**
	 * Specifies the message of the interaction.<br><br>
	 * Calling this method is mandatory, it is thus the only method available in the {@link ConfirmationInteractionBuilder}
	 * interface handed out for user interaction and returns a {@link InteractionBuilder} implementation to allow for further
	 * adjustments and building the adjusted interaction ({@link ConfirmationInteraction}s don't provide any adjustments that
	 * aren't already defined for all types of interactions in the {@link InteractionBuilder} interface).<br>
	 * This is a form of implementation of the Step Builder pattern.
	 * 
	 * @param message   The message to be set, if {@code null}, an {@link IllegalArgumentException} is thrown.
	 */
	def OptionalSteps message(String message)

	/**
	 * Interface for optional build steps (none needed), build steps common to all types of interactions and the method to
	 * create and use the interaction as declared in {@link InteractionBuilder}. This represents the final step of this interaction's
	 * build process.
	 */
	interface OptionalSteps extends InteractionBuilder<Boolean, OptionalSteps> {
	}
}
