package tools.vitruv.framework.tests.echange.command

import org.eclipse.emf.common.command.Command
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertFalse

/**
 * Abstract base class of the command tests.
 */
abstract class CommandTest {
	/**
	 * Command is prepareable (can be executed)
	 */
	def protected static void assertIsPreparable(Command command) {
		assertTrue(command.canExecute)
	}
	
	/**
	 * Command is not preparable (cannot be executed)
	 */
	def protected static void assertIsNotPreparable(Command command) {
		assertFalse(command.canExecute)
	}	
	
	/** 
	 * Checks if a command can be executed and executes it.
	 */
	def protected static void assertExecuteCommand(Command command) {
		assertTrue(command.canExecute)
		command.execute
	}	
		
	/**
	 * Command is instance of a specific class.
	 */
	def protected static <T> T assertIsInstanceOf(Command command, Class<T> type) {
		assertTrue(type.isInstance(command))
		return type.cast(command)
	}
}