package tools.vitruv.framework.vsum.filtered.changemodification

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import tools.vitruv.framework.change.echange.feature.list.UpdateSingleListEntryEChange
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference

@FinalFieldsConstructor
class IndexBasedEChangeModification implements EChangeModification<Integer>{
	
	val UpdateSingleListEntryEChange<EObject, EReference> echange
	val Integer oldValue
	val Integer newValue
	
	override getEChange() {
		return echange
	}
	
	override getOldValue() {
		return oldValue
	}
	
	override getNewValue() {
		return newValue
	}
	
	override getModificationType() {
		return EChangeModificationType.INDEX_BASED_CHANGE
	}
	
	override apply() {
		echange.index = newValue.intValue
	}
	
	override reverse() {
		echange.index = oldValue.intValue
	}
	
}