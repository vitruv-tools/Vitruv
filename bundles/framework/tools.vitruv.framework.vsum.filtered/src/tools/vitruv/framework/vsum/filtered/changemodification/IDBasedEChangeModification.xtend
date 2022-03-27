package tools.vitruv.framework.vsum.filtered.changemodification

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import tools.vitruv.framework.change.echange.feature.FeatureEChange

@FinalFieldsConstructor
class IDBasedEChangeModification<T extends IDChange> implements EChangeModification<String>{
	val FeatureEChange<? extends EObject, ? extends EStructuralFeature> echange
	val String oldValue
	val String newValue
	val T applicationFunction
	
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
		return EChangeModificationType.ID_BASED_CHANGE
	}
	
	override apply() {
		applicationFunction.changeID(echange, newValue)
	}
	
	override reverse() {
		applicationFunction.changeID(echange, oldValue)
	}
	
}