package tools.vitruv.domains.sysml

import tools.vitruv.framework.tuid.AttributeTUIDCalculatorAndResolver
import org.eclipse.emf.ecore.EObject
import org.eclipse.papyrus.sysml14.blocks.Block
import org.eclipse.uml2.uml.UMLPackage
import org.eclipse.papyrus.sysml14.blocks.ValueType
import tools.vitruv.domains.uml.UmlMetamodel

package class SysMlTuidCalculatorAndResolver extends AttributeTUIDCalculatorAndResolver {
	private val UmlMetamodel umlMetamodel;
	
	new(String nsPrefix) {
		super(nsPrefix, UMLPackage.Literals.NAMED_ELEMENT__NAME.name)
		umlMetamodel = UmlMetamodel.instance;
	}
	
	override calculateTUIDFromEObject(EObject eObject) {
		if (SysMlMetamodel.NAMESPACE_URIS.contains(eObject.eClass.EPackage.nsURI)) {
			super.calculateTUIDFromEObject(eObject);	
		} else {
			return umlMetamodel.calculateTUIDFromEObject(eObject);
		}
	}
	
	override calculateTUIDFromEObject(EObject eObject, EObject virtualRootObject, String prefix) {
		if (SysMlMetamodel.NAMESPACE_URIS.contains(eObject.eClass.EPackage.nsURI)) {
			super.calculateTUIDFromEObject(eObject.stereotypedObject);
		} else {
			return umlMetamodel.calculateTUIDFromEObject(eObject);
		}
	}
	
	def protected dispatch EObject getStereotypedObject(EObject object) throws IllegalArgumentException {
		return object;
	}
	
	def protected dispatch EObject getStereotypedObject(Block block) throws IllegalArgumentException {
		return block.base_Class;
	}
	
	def protected dispatch EObject getStereotypedObject(ValueType valueType) throws IllegalArgumentException {
		return valueType.base_DataType;
	}
	
}