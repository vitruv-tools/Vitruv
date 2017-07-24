package tools.vitruv.dsls.commonalities.language.elements

import org.eclipse.emf.ecore.EReference
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

@FinalFieldsConstructor
package class EReferenceAdapter implements Reference, Wrapper<EReference> {

	val EReference wrappedEReference

	override getName() {
		wrappedEReference.name
	}

	override getType() {
		wrappedEReference.EType
	}

	override getWrapped() {
		wrappedEReference
	}

}
