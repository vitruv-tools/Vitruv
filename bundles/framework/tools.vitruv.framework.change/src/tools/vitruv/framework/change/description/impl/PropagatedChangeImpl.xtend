package tools.vitruv.framework.change.description.impl

import org.eclipse.xtend.lib.annotations.Data
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChange

@Data
class PropagatedChangeImpl implements PropagatedChange {
	String id
	VitruviusChange originalChange
	VitruviusChange consequentialChanges

	override toString() '''
		Original change:
			«originalChange»
		Consequential change: «consequentialChanges»
	'''

	override applyBackward() {
		consequentialChanges.applyBackward
		originalChange.applyBackward
	}

	override applyForward() {
		originalChange.applyForward
		consequentialChanges.applyForward
	}

	override isResolved() {
		val allResolved = originalChange.EChanges.forall[resolved]
		val allTriggeredResolved = consequentialChanges.EChanges.forall[resolved]
		if (allResolved && allTriggeredResolved) return true
		val noneResolved = originalChange.EChanges.forall[!resolved]
		val noneTriggeredResolved = consequentialChanges.EChanges.forall[!resolved]
		if (noneResolved && noneTriggeredResolved) return false
		throw new IllegalStateException('''
			original all resolved «allResolved»
			triggerd all resolved «allTriggeredResolved»
			original all unresolved «noneResolved»
			triggerd all unresolved «noneTriggeredResolved»
		''')
	}

}
