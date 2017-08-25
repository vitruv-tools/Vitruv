package tools.vitruv.dsls.commonalities.language.elements

import org.eclipse.emf.ecore.EAttribute
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

@FinalFieldsConstructor
class EAttributeAdapter implements Attribute, Wrapper<EAttribute> {

	val EAttribute wrappedEAttribute

	override getName() {
		wrappedEAttribute.name
	}

	override getType() {
		wrappedEAttribute.EAttributeType
	}
	
	override getWrapped() {
		wrappedEAttribute
	}
	
}
