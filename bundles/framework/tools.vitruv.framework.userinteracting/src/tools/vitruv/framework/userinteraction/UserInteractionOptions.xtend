package tools.vitruv.framework.userinteraction

/**
 * Enumerations and interfaces for options that can be made during user interaction.
 * 
 * @author Heiko Klare
 */
public final class UserInteractionOptions {

	private new() {
	}

	/**
	 * Represents the different ways a window can behave (whether it requires interaction blocking other windows behind it
	 * or it can be minimized).
	 * 
	 * @author Dominik Klooz
	 */
	enum WindowModality {
		MODAL,
		MODELESS
	/* TODO DK: MODAL doesn't work currently. In order for it to work, the shell passed to UserInteractor would have to be
	 the one used by the workbench */
	}

	/**
	 * Enum for the levels of severity for notification messages (specifies the icon used in {@link NotificationInteraction}s).
	 * @see NotificationInteraction
	 * 
	 * @uthor Dominik Klooz
	 */
	enum NotificationType {
		INFORMATION,
		WARNING,
		ERROR
	}

	/**
	 * Enum to specify whether input lines provide single or multi line input.
	 * 
	 * @author Dominik Klooz
	 */
	enum InputFieldType {
		SINGLE_LINE,
		MULTI_LINE
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
