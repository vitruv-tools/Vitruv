package tools.vitruv.framework.change.description

interface PropagatedChange {
	def VitruviusChange getOriginalChange()

	def VitruviusChange getConsequentialChanges()

	def void applyBackward()

	def void applyForward()
}
