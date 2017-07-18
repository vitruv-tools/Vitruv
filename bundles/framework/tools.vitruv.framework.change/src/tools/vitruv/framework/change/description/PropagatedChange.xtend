package tools.vitruv.framework.change.description

interface PropagatedChange {
	def String getId()

	def VitruviusChange getOriginalChange()

	def VitruviusChange getConsequentialChanges()

	def boolean isResolved()

	def void applyBackward()

	def void applyForward()

}
