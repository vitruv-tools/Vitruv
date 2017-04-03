package tools.vitruv.framework.change.echange.util

import tools.vitruv.framework.change.echange.EChange
import org.eclipse.emf.common.command.Command
import java.util.List

/**
 * Utility class for applying an EChanges. The xcore-model doesn't provide 
 * private methods and to provide a clean EChange interface, the apply 
 * method for applying forward and backward is placed in this utility class.
 */
public class ApplyEChangeSwitch {
	private new() {}
	
	/**
	 * Applies a given {@link EChange}. The change needs to be resolved.
	 * @param change					The {@link EChange} which will be applied.
	 * @param applyForward				If {@code true} the change will be applied forward,
	 * 									otherwise backward.
	 * @returns							The change was successfully applied.
	 * @throws IllegalStateException	The change is already resolved.
	 */
	def public static boolean applyEChange(EChange change, boolean applyForward) {
		if (!change.isResolved) {
			throw new IllegalStateException("The EChange is not resolved.")
		}
		
		var List<Command> commands
		if (applyForward) {
			commands = ApplyForwardCommandSwitch.getCommands(change)
		} else {
			commands = ApplyBackwardCommandSwitch.getCommands(change)
		}

		if (commands != null) {
			for (Command c : commands) {
				if (c.canExecute()) {
					c.execute()
				} else {
					return false
				}
			}
			return true
		}
		return false
	}
}