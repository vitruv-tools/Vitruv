package tools.vitruv.dsls.commonalities.language.elements

import org.eclipse.emf.ecore.EClass
import tools.vitruv.dsls.commonalities.language.elements.impl.EClassMetaclassImpl
import static com.google.common.base.Preconditions.*

package class EClassAdapter extends EClassMetaclassImpl implements Wrapper<EClass> {

	EClass wrappedEClass

	override wrapEClass(EClass eClass) {
		checkState(wrappedEClass === null, "This object already has an EClass!")
		wrappedEClass = checkNotNull(eClass)
	}

	def private checkWrappedEClassIsSet() {
		checkState(wrappedEClass !== null, "No EClass was set on this object!");
	}

	override getName() {
		checkWrappedEClassIsSet()
		wrappedEClass.name
	}
	
	override getAttributes() {
		if (attributes === null) {
			super.getAttributes().addAll(loadAttributes())
		}
		attributes
	}
	
	override getReferences() {
		if (references === null) {
			super.getReferences().addAll(loadReferences())
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
