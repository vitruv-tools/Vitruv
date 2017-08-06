package tools.vitruv.dsls.commonalities.language.elements

import org.eclipse.emf.ecore.EDataType

interface Attribute extends MetaclassMember {
	override EDataType getType()
}