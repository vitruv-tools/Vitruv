package tools.vitruv.framework.change.description

import org.eclipse.xtend.lib.annotations.Data

@Data
class PropagatedChange {
	VitruviusChange originalChange
	VitruviusChange consequentialChanges

	override toString() '''
		Original change:
			«originalChange»
		Consequential change: «consequentialChanges»
	'''

	def applyBackward() {
		consequentialChanges.applyBackward
		originalChange.applyBackward
	}

	def applyForward() {
		originalChange.applyForward
		consequentialChanges.applyForward
	}
}
