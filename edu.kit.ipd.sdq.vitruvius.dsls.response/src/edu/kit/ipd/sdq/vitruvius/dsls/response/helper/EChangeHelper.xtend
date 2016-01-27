package edu.kit.ipd.sdq.vitruvius.dsls.response.helper

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetEAttribute
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetEReference
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateEAttribute
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateEReference
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.EObjectChange
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EClass
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.AttributePackageImpl
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.ReferencePackageImpl
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ContainmentPackageImpl
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.FeaturePackageImpl
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.ObjectPackageImpl
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.FeatureOfElement
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.change.impl.ChangePackageImpl
import org.eclipse.emf.ecore.EModelElement

final class EChangeHelper {
	
	static def String getGenericTypeParameterFQNOfChange(EClass changeEClass, FeatureOfElement foe) {
		val changeClass = changeEClass.instanceClass;
		var String className;
		if (UpdateEReference.isAssignableFrom(changeClass)
			|| UpdateEAttribute.isAssignableFrom(changeClass)
			|| UnsetEReference.isAssignableFrom(changeClass)
			|| UnsetEAttribute.isAssignableFrom(changeClass)) {
			className = foe.feature.EType.instanceClassName
		} else if (EObjectChange.isAssignableFrom(changeClass)) {
			className = foe.element.instanceClassName;
		} else if (EFeatureChange.isAssignableFrom(changeClass)) {
			className = foe.feature.class.name
		}
		
		className = className?.convertPrimitiveTypeToClassName;
		return className;
	}
	
	static def String convertPrimitiveTypeToClassName(String className) {
		switch (className) {
			case "short": return Short.name
			case "int": return Integer.name
			case "long": return Long.name
			case "double": return Double.name
			case "float": return Float.name
			case "boolean": return Boolean.name
			case "char": return Character.name
			case "byte": return Byte.name
			case "void": return Void.name
			default: return className
		}
	}
	
	static def EClass generateEChange(EClass responseChange, EModelElement changedObject) {
		if (responseChange.equals(ChangePackageImpl.eINSTANCE.update)) {
			generateUpdateEChange(changedObject);
		} else if (responseChange.equals(ChangePackageImpl.eINSTANCE.create)) {
			generateCreateEChange(changedObject);
		} else if (responseChange.equals(ChangePackageImpl.eINSTANCE.delete)) {
			generateDeleteEChange(changedObject);
		} else {
			return null;
		}
	}
	
	static def dispatch EClass generateUpdateEChange(EModelElement changedElement) {
		throw new UnsupportedOperationException("The given element is not supported.");
	} 
		
	static def dispatch EClass generateUpdateEChange(EAttribute feature) {
		if (feature.upperBound > 1 || feature.upperBound == -1) {
			return AttributePackageImpl.eINSTANCE.replaceEAttributeValue;
		} else {
			return AttributePackageImpl.eINSTANCE.updateSingleValuedEAttribute;
		}
	}	
	
	static def dispatch EClass generateUpdateEChange(EReference feature) {
		if (feature.upperBound > 1 || feature.upperBound == -1) {
			if (feature.containment) {
				return ContainmentPackageImpl.eINSTANCE.replaceNonRootEObjectInList;
			} else {
				return ReferencePackageImpl.eINSTANCE.replaceNonContainmentEReference;
			}
		} else {
			if (feature.containment) {
				return ContainmentPackageImpl.eINSTANCE.replaceNonRootEObjectSingle;
			} else {
				return ReferencePackageImpl.eINSTANCE.updateSingleValuedNonContainmentEReference;
			}
		}
	}
	
	static def dispatch EClass generateUpdateEChange(EClass element) {
		return ObjectPackageImpl.eINSTANCE.replaceRootEObject;
	}
	
	static def dispatch EClass generateCreateEChange(EModelElement changedElement) {
		throw new UnsupportedOperationException("The given element is not supported.");
	} 
	
	static def dispatch EClass generateCreateEChange(EAttribute feature) {
		if (feature.upperBound > 1 || feature.upperBound == -1) {
			return AttributePackageImpl.eINSTANCE.insertEAttributeValue;
		} else {
			return AttributePackageImpl.eINSTANCE.updateSingleValuedEAttribute
		}
	}
	
	static def dispatch EClass generateCreateEChange(EReference feature) {
		if (feature.upperBound > 1 || feature.upperBound == -1) {
			if (feature.containment) {
				// TODO HK could also be InsertNonRootEObjectInContainmentList if object is moved from somewhere else 
				return ContainmentPackageImpl.eINSTANCE.createNonRootEObjectInList;
			} else {
				return ReferencePackageImpl.eINSTANCE.insertNonContainmentEReference;
			}
		} else {
			if (feature.containment) {
				return ContainmentPackageImpl.eINSTANCE.createNonRootEObjectSingle;
			} else {
				return ReferencePackageImpl.eINSTANCE.updateSingleValuedNonContainmentEReference;
			}
		}
	}
	
	static def dispatch EClass generateCreateEChange(EClass element) {
		return ObjectPackageImpl.eINSTANCE.createRootEObject;
	}	
	
	static def dispatch EClass generateDeleteEChange(EModelElement changedElement) {
		throw new UnsupportedOperationException("The given element is not supported.");
	} 
	
	static def dispatch EClass generateDeleteEChange(EAttribute feature) {
		if (feature.upperBound > 1 || feature.upperBound == -1) {
			return AttributePackageImpl.eINSTANCE.removeEAttributeValue
		} else {
			return FeaturePackageImpl.eINSTANCE.unsetEAttribute;
		}
	}
	
	static def dispatch EClass generateDeleteEChange(EReference feature) {
		if (feature.upperBound > 1 || feature.upperBound == -1) {
			if (feature.containment) {
				// TODO HK could also be RemoveNonRootEObjectFromContainmentList if object is moved to somewhere else
				return ContainmentPackageImpl.eINSTANCE.deleteNonRootEObjectInList
			} else {
				return ReferencePackageImpl.eINSTANCE.removeNonContainmentEReference;
			}
		} else {
			if (feature.containment) {
				// TODO HK could also be FeaturePackageImpl.eINSTANCE.unsetContainmentEReference
				return ContainmentPackageImpl.eINSTANCE.deleteNonRootEObjectSingle;
			} else {
				return FeaturePackageImpl.eINSTANCE.unsetNonContainmentEReference;
			}
		}
	}
	
	static def dispatch EClass generateDeleteEChange(EClass element) {
		return ObjectPackageImpl.eINSTANCE.deleteRootEObject;
	}	
	
}
