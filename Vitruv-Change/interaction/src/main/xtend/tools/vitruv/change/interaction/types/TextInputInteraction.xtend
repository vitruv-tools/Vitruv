package tools.vitruv.change.interaction.types

import tools.vitruv.change.interaction.UserInteractionOptions.WindowModality
import tools.vitruv.change.interaction.UserInteractionOptions.InputFieldType
import tools.vitruv.change.interaction.FreeTextUserInteraction
import tools.vitruv.change.interaction.InteractionFactory
import tools.vitruv.change.interaction.InteractionResultProvider
import tools.vitruv.change.interaction.UserInteractionOptions.InputValidator

/**
 * An interaction providing a single- or multi-line text input field, optionally restricted by a {@link InputValidator}.
 * 
 * @author Dominik Klooz
 * @author Heiko Klare
 */
class TextInputInteraction extends BaseInteraction<FreeTextUserInteraction> {
	static val DEFAULT_TITLE = "Input Text";
	static val DEFAULT_MESSAGE = "";
	InputValidator inputValidator
	InputFieldType inputFieldType

	public static final InputValidator NUMBERS_ONLY_INPUT_VALIDATOR = new InputValidator() {
		override getInvalidInputMessage(String input) { "Only numbers are allowed as input" }

		override isInputValid(String input) { input.matches("[0-9]*") }
	}

	public static final InputValidator ACCEPT_ALL_INPUT_VALIDATOR = new InputValidator() {
		override getInvalidInputMessage(String input) { "" }

		override isInputValid(String input) { true }
	}

	protected new(InteractionResultProvider interactionResultProvider, WindowModality windowModality,
		InputFieldType fieldType, InputValidator inputValidator) {
		super(interactionResultProvider, windowModality, DEFAULT_TITLE, DEFAULT_MESSAGE)
		this.inputFieldType = fieldType
		this.inputValidator = inputValidator
	}

	protected new(InteractionResultProvider interactionResultProvider, WindowModality windowModality) {
		this(interactionResultProvider, windowModality, InputFieldType.SINGLE_LINE, ACCEPT_ALL_INPUT_VALIDATOR)
	}

	def InputValidator getInputValidator() { inputValidator }

	def void setInputValidator(InputValidator newInputValidator) { inputValidator = newInputValidator }

	def InputFieldType getInputFieldType() { inputFieldType }

	def void setInputFieldType(InputFieldType newInputFieldType) { inputFieldType = newInputFieldType }

	override startInteraction() {
		val result = interactionResultProvider.getTextInputInteractionResult(windowModality, title, message,
			positiveButtonText, cancelButtonText, inputValidator);
		val userInput = InteractionFactory.eINSTANCE.createFreeTextUserInteraction()
		userInput.message = message
		userInput.text = result
		return userInput;
	}

}
