package tools.vitruv.framework.change.echange.util

import tools.vitruv.framework.change.echange.EChange

/**
 * Utility class for applying an EChanges:: The xcore-model doesn't provide
 * private methods and to provide a clean EChange interface, the apply
 * method for applying forward and backward is placed in this utility class.
 */
class ApplyEChangeSwitch {
	private new() {
	}

	/**
	 * Applies a given {@link EChange}. The change needs to be resolved.
	 * @param change					The {@link EChange} which will be applied.
	 * @param applyForward				If {@code true} the change will be applied forward,
	 * 									otherwise backward.
	 * @returns							The change was successfully applied.
	 * @throws IllegalStateException	The change is already resolved.
	 * @throws RunTimeException			The change could not be applied.
	 */
	static def boolean applyEChange(EChange change, boolean applyForward) {
		if (!change.resolved)
			throw new IllegalStateException('''The EChange «change»could not be applied. ''')

		val commands = if (applyForward)
				ApplyForwardCommandSwitch::getCommands(change)
			else
				ApplyBackwardCommandSwitch::getCommands(change)

		if (commands === null)
			throw new RuntimeException("EChange could not be applied.")
		else {
			commands.forEach [
				if (canExecute)
					execute
				else
					throw new RuntimeException('''EChange «it»could not be applied. ''')
			]
			return true
		}
	}
}
