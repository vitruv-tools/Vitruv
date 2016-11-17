package tools.vitruv.domains.sysml.tuid

import tools.vitruv.framework.tuid.AttributeTUIDCalculatorAndResolver
import org.eclipse.emf.ecore.EObject
import org.eclipse.uml2.uml.UMLPackage
import tools.vitruv.domains.sysml.SysMlMetamodel

class SysMlTuidCalculatorAndResolver extends AttributeTUIDCalculatorAndResolver {
	private val extension SysMlToUmlResolver sysMlToUmlResolver;
	//private val UmlMetamodel umlMetamodel;
	
	new(String nsPrefix) {
		super(nsPrefix, UMLPackage.Literals.NAMED_ELEMENT__NAME.name)
		sysMlToUmlResolver = SysMlToUmlResolver.instance;
		//umlMetamodel = UmlMetamodel.instance;
	}
	
	override calculateTUIDFromEObject(EObject eObject) {
		//if (SysMlMetamodel.NAMESPACE_URIS.contains(eObject.eClass.EPackage.nsURI)) {
			super.calculateTUIDFromEObject(eObject.stereotypedObject);	
//		} else {
//			return umlMetamodel.calculateTUIDFromEObject(eObject);
//		}
	}
	
	override calculateTUIDFromEObject(EObject eObject, EObject virtualRootObject, String prefix) {
		if (SysMlMetamodel.NAMESPACE_URIS.contains(eObject.eClass.getEPackage.nsURI)) {
			super.calculateTUIDFromEObject(eObject.stereotypedObject, virtualRootObject, prefix);
		} else {
			super.calculateTUIDFromEObject(eObject, virtualRootObject, prefix);
//			return umlMetamodel.calculateTUIDFromEObject(eObject);
		}
	}
	
	override hasTUID(EObject eObject) {
		if (eObject.stereotypedObject != null) {
			return super.hasTUID(eObject.stereotypedObject);
		}
		return false;
	}
	
}
