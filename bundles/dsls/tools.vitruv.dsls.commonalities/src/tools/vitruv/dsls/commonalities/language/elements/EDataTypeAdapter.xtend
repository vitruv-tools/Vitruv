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

	private def checkEDataTypeSet() {
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
		this.wrappedDataType.instanceClass.isAssignableFrom(dataTypeAdapter.wrappedDataType.instanceClass)
	}

	def dispatch isSuperTypeOf(MostSpecificType mostSpecificType) {
		true
	}

	def dispatch isSuperTypeOf(LeastSpecificType leastSpecificType) {
		false
	}

	override getName() {
		wrappedDataType.name
	}

	override toString() {
		'''{{«wrappedDataType?.name»}}'''
	}
}
