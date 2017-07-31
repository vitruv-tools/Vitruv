package tools.vitruv.framework.change.description.impl

import org.eclipse.xtend.lib.annotations.Data
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChange
import java.util.Collection
import tools.vitruv.framework.change.echange.EChange
import org.apache.log4j.Logger

@Data
class PropagatedChangeImpl implements PropagatedChange {
	static extension Logger = Logger::getLogger(PropagatedChangeImpl)
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
		val mapToBoolean = [Collection<EChange> eChanges|eChanges.map[it -> resolved].toList.immutableCopy]
		val originalResolvedArray = mapToBoolean.apply(originalChange.EChanges)
		val triggeredResolvedArray = mapToBoolean.apply(consequentialChanges.EChanges)
		val allResolved = originalResolvedArray.fold(true, [prev, current|prev && current.value])
		val noneResolved = !originalResolvedArray.fold(false, [prev, current|prev || current.value])
		val allTriggeredResolved = triggeredResolvedArray.fold(true, [prev, current|prev && current.value])
		val noneTriggeredResolved = !triggeredResolvedArray.fold(false, [prev, current|prev || current.value])
		if (allResolved && allTriggeredResolved) return true
		if (noneResolved && noneTriggeredResolved) return false
		error('''
			original all resolved «allResolved»
			triggerd all resolved «allTriggeredResolved»
			original all unresolved «noneResolved»
			triggerd all unresolved «noneTriggeredResolved»
		''')
		return false
	}

}
