package tools.vitruv.framework.userinteraction.builder

import java.util.function.Function
import tools.vitruv.framework.userinteraction.UserInteractionOptions.InputFieldType
import tools.vitruv.framework.userinteraction.UserInteractionOptions.InputValidator

/**
 * Defines one single entry point to the build process of a {@link TextInputInteraction} thus ensuring that mandatory
 * information has to be provided before continuing. The top-level method represents the first and only mandatory step
 * returning the nested interface which includes optional steps as well as the build method
 * ({@link TextInputInteractionBuilder.OptionalSteps} extends {@link InteractionBuilder} to provide access to build step
 * methods common to all types of interactions).<br>
 * <br>
 * For further info on the rationale behind the ...InteractionBuilder implementation, see the {@link InteractionBuilder} javadoc.
 * 
 * @author Dominik Klooz
 */
interface TextInputInteractionBuilder {

	/**
	 * Specifies the message of the interaction.<br><br>
	 * Calling this method is mandatory, it is thus the only method available in the {@link TextInputInteractionBuilder}
	 * interface handed out for user interaction and returns a {@link TextInputInteractionBuilder.OptionalSteps}
	 * implementation to allow for further adjustments and building the adjusted interaction. This is a form of
	 * implementation of the Step Builder pattern.
	 * 
	 * @param message   The message to be set, if {@code null}, an {@link IllegalArgumentException} is thrown.
	 */
	def OptionalSteps message(String message)

	/**
	 * Interface for optional build steps (mostly common to all InteractionBuilders as defined by {@link InteractionBuilder})
	 */
	interface OptionalSteps extends InteractionBuilder<String, OptionalSteps> {

		/**
		 * Adds an input validator used to restrict the input to Strings conforming to the validator's
		 * {@link InputValidator#isInputValid(String) isInputValid} method.<br>
		 * The default input validator accepts all input.
		 * 
		 * @param inputValidator    The input validator to be set, if {@code null}, nothing happens.
		 */
		def OptionalSteps inputValidator(InputValidator inputValidator)

		/**
		 * Convenience method to add an input validator by providing a validation function used to restrict input and a
		 * message displayed when the user tries to input illegal characters.<br>
		 * The default input validator accepts all input.
		 */
		def OptionalSteps inputValidator(Function<String, Boolean> validatorFunction, String invalidInputMessage)

		/**
		 * Sets the input field to be single-line or multi-line. Defaults to {@link InputFieldType#SINGLE_LINE}.
		 * 
		 * @param inputFieldType   The type of input field to be set, if {@code null}, nothing happens.
		 */
		def OptionalSteps inputFieldType(InputFieldType inputFieldType)
	}
}
