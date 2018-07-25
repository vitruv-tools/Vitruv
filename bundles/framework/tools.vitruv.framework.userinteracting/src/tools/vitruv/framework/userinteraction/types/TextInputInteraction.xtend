package tools.vitruv.framework.userinteraction.types

import java.util.function.Function
import tools.vitruv.framework.userinteraction.WindowModality
import tools.vitruv.framework.userinteraction.types.InputFieldType
import tools.vitruv.framework.change.interaction.FreeTextUserInteraction
import tools.vitruv.framework.change.interaction.impl.InteractionFactoryImpl
import tools.vitruv.framework.userinteraction.InteractionResultProvider

/**
 * An interaction providing a single- or multi-line text input field, optionally restricted by a {@link InputValidator}.
 * 
 * @author Dominik Klooz
 * @author Heiko Klare
 */
public class TextInputInteraction extends BaseInteraction<FreeTextUserInteraction> {
	private static val DEFAULT_TITLE = "Input Text";
	private static val DEFAULT_MESSAGE = "";
	private InputValidator inputValidator
	private InputFieldType inputFieldType
	
	public static final InputValidator NUMBERS_ONLY_INPUT_VALIDATOR = new InputValidator() {
        override getInvalidInputMessage(String input) { "Only numbers are allowed as input" }
        override isInputValid(String input) { input.matches("[0-9]*") }
    }
    
    public static final InputValidator ACCEPT_ALL_INPUT_VALIDATOR = new InputValidator() {
        override getInvalidInputMessage(String input) { "" }
        override isInputValid(String input) { true }
    }
	
	protected new(InteractionResultProvider interactionResultProvider, WindowModality windowModality, InputFieldType fieldType, InputValidator inputValidator) {
		super(interactionResultProvider, windowModality, DEFAULT_TITLE, DEFAULT_MESSAGE)
		this.inputFieldType = fieldType
		this.inputValidator = inputValidator
	}
	
	protected new(InteractionResultProvider interactionResultProvider, WindowModality windowModality, InputFieldType fieldType, Function<String, Boolean> inputValidator, String invalidInputMessage) {
		this(interactionResultProvider, windowModality, fieldType, new InputValidator() {
			override getInvalidInputMessage(String input) { "" }
			override isInputValid(String input) { inputValidator.apply(input) }
		})
	}
	
	protected new(InteractionResultProvider interactionResultProvider, WindowModality windowModality) {
		this(interactionResultProvider, windowModality, InputFieldType.SINGLE_LINE, [ text | true ], "")
	}
	
	def InputValidator getInputValidator() { inputValidator }
    def void setInputValidator(InputValidator newInputValidator) { inputValidator = newInputValidator }
    
    def InputFieldType getInputFieldType() { inputFieldType }
    def void setInputFieldType(InputFieldType newInputFieldType) { inputFieldType = newInputFieldType }
	
	override startInteraction() {
		val result = interactionResultProvider.getTextInputInteractionResult(windowModality, title, message, positiveButtonText, cancelButtonText, inputValidator);
		val userInput = InteractionFactoryImpl.eINSTANCE.createFreeTextUserInteraction()
        userInput.message = message
        userInput.text = result
        return userInput;
	}
	
	
	/**
	 * Interface for input validators used in {@link TextInputDialog}s. The {@link #isInputValid(String) isInputValid}
	 * method is used to accept or deny input changes, the message constructed in
	 * {@link #getInvalidInputMessage(String) getInvalidInputMessage} is displayed whenever the user tries to enter
	 * illegal characters as determined by {@link #isInputValid(String) isInputValid}.
	 */
	interface InputValidator {
		
		/**
		 * Get a warning message to be displayed when the user tries to add illegal characters (as determined by 
		 * {@link isInputValid(String)}).
		 */
		def String getInvalidInputMessage(String input)
		
		/**
		 * Determines whether or not the current input is to be considered valid. If not, the illegal characters will
		 * not be added to the input.
		 */
		def boolean isInputValid(String input)
	}
}
