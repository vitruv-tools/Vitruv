package tools.vitruv.dsls.commonalities.language.elements

import org.eclipse.emf.ecore.EDataType
import tools.vitruv.dsls.commonalities.language.elements.impl.EDataTypeClassifierImpl

import static com.google.common.base.Preconditions.*

class EDataTypeAdapter extends EDataTypeClassifierImpl implements Wrapper<EDataType> {
	
	EDataType wrappedDataType
	
	override forEDataType(EDataType eDataType) {
		wrappedDataType = checkNotNull(eDataType)
		return this
	}
	
	def private checkEDataTypeSet() {
		checkState(wrappedDataType !== null, "No EDataType was set on this adapter!")
	}
	
	override getWrapped() {
		wrappedDataType
	}
	
	def dispatch isSuperTypeOf(Classifier subType) {
		false
	}
	
	def dispatch isSuperTypeOf(EDataTypeAdapter dataTypeAdapter) {
		checkEDataTypeSet()
		if (dataTypeAdapter === this) return true
		if (dataTypeAdapter === WellKnownClassifiers.MOST_SPECIFIC_TYPE) return false
		this.wrappedDataType.instanceClass.isAssignableFrom(dataTypeAdapter.wrappedDataType.instanceClass)
	}
	
	def dispatch isSuperTypeOf(MostSpecificType mostSpecificType) {
		true
	}
	
	override getName() {
		wrappedDataType.name
	}
	
	override toString() {
		'''{{«wrappedDataType?.name»}}'''
	}
	
}