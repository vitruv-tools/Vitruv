package tools.vitruv.framework.change.description

/**
 * Interface for recorded original changes with correspondent changes.
 */
interface PropagatedChange {
	def String getId()

	def VitruviusChange getConsequentialChanges()

	def VitruviusChange getOriginalChange()

	def boolean isResolved()

	def void applyBackward()

	def void applyForward()
}
