package tools.vitruv.dsls.commonalities.language.elements

import org.eclipse.emf.ecore.EClass
import tools.vitruv.dsls.commonalities.language.elements.impl.EClassMetaclassImpl

import static com.google.common.base.Preconditions.*

package class EClassAdapter extends EClassMetaclassImpl implements Wrapper<EClass> {

	EClass wrappedEClass
	var attributesInitialized = false
	var referencesInitialized = false
	
	override wrapEClass(EClass eClass) {
		checkState(wrappedEClass === null, "This object already has an EClass!")
		wrappedEClass = checkNotNull(eClass)
	}

	def private checkWrappedEClassIsSet() {
		checkState(wrappedEClass !== null, "No EClass was set on this object!");
	}

	override getName() {
		if (eIsProxy) return null
		checkWrappedEClassIsSet()
		wrappedEClass.name
	}
	
	override getAttributes() {
		val attributes = super.getAttributes()
		if (!attributesInitialized && !eIsProxy) {
			attributes.addAll(loadAttributes())
			attributesInitialized = true
		}
		attributes
	}
	
	override getReferences() {
		val references = super.getReferences()
		if (!referencesInitialized && !eIsProxy) {
			references.addAll(loadReferences())
			referencesInitialized = true
		}
		references
	}
	
	def private loadAttributes() {
		checkWrappedEClassIsSet()

		wrappedEClass.EAllAttributes.map [new EAttributeAdapter(it)]
	}

	def private loadReferences() {
		checkWrappedEClassIsSet()

		wrappedEClass.EAllReferences.map [new EReferenceAdapter(it)]
	}

	override getWrapped() {
		wrappedEClass
	}
	
	override basicGetPackageLikeContainer() {
		domain
	}
	
	override basicGetChangeClass() {
		wrappedEClass
	}

}
