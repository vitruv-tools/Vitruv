package tools.vitruv.framework.userinteraction.builder.impl

import tools.vitruv.framework.userinteraction.UserInteractionOptions.InputValidator
import java.util.function.Function
import tools.vitruv.framework.userinteraction.UserInteractionOptions.InputFieldType
import tools.vitruv.framework.userinteraction.builder.TextInputInteractionBuilder.OptionalSteps
import tools.vitruv.framework.userinteraction.UserInteractionListener
import tools.vitruv.framework.userinteraction.builder.TextInputInteractionBuilder
import tools.vitruv.framework.userinteraction.types.TextInputInteraction
import tools.vitruv.framework.userinteraction.types.InteractionFactory

/**
 * Builder class for {@link TextInputInteraction}s. Use the add/set... methods to specify details and then call
 * createAndShow() to display and get a reference to the configured dialog.
 * Creates a dialog with a text input field (configurable to accept single or multi-line input). A {@link InputValidator}
 * can also be specified which limits the input to strings conforming to its
 * {@link InputValidator#isInputValid(String) isInputValid} method (the default validator accepts all input).<br>
 * <br>
 * For further info on the rationale behind the ...InteractionBuilder implementation, see the {@link InteractionBuilder} javadoc.
 * @see TextInputInteractionBuilder
 * 
 * @author Dominik Klooz
 * @author Heiko Klare
 */
class TextInputInteractionBuilderImpl extends BaseInteractionBuilder<String, TextInputInteraction, OptionalSteps> implements TextInputInteractionBuilder, OptionalSteps {
	new(InteractionFactory interactionFactory, Iterable<UserInteractionListener> userInteractionListener) {
		super(interactionFactory, userInteractionListener)
	}

	override message(String message) {
		setMessage(message)
		return this;
	}

	override inputValidator(InputValidator inputValidator) {
		if (inputValidator !== null) {
			interactionToBuild.inputValidator = inputValidator
		}
		return this
	}

	override inputValidator(Function<String, Boolean> validatorFunction, String invalidInputMessage) {
		if (validatorFunction !== null && invalidInputMessage !== null) {
			interactionToBuild.inputValidator = new InputValidator() {
				override getInvalidInputMessage(String input) { invalidInputMessage }

				override isInputValid(String input) { validatorFunction.apply(input) }
			}
		}
		return this
	}

	override inputFieldType(InputFieldType inputFieldType) {
		if (inputFieldType !== null) {
			interactionToBuild.inputFieldType = inputFieldType
		}
		return this
	}

	override startInteraction() {
		val result = interactionToBuild.startInteraction();
		notifyUserInputReceived(result);
		return result.text;
	}

	override createUserInteraction() {
		interactionFactory.createTextInputInteraction();
	}

	override protected getSelf() {
		return this;
	}

}
