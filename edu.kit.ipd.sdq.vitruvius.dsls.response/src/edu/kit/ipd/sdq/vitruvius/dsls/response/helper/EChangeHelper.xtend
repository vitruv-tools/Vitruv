package edu.kit.ipd.sdq.vitruvius.dsls.response.helper

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetEAttribute
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetEReference
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateEAttribute
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateEReference
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.EObjectChange
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.EAttribute
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.change.Create
import org.eclipse.emf.ecore.EReference
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.change.Delete
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.change.Update
import org.eclipse.emf.ecore.EClass
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.impl.AttributePackageImpl
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.ReferencePackageImpl
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ContainmentPackageImpl
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.impl.FeaturePackageImpl
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.ObjectPackageImpl
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.FeatureOfElement
import org.eclipse.emf.ecore.EObject

final class EChangeHelper {
	
	static def String getGenericTypeParameterFQNOfChange(EClass changeEClass, FeatureOfElement foe) {
		val changeClass = changeEClass.instanceClass;
		 if (UpdateEReference.isAssignableFrom(changeClass)
			|| UpdateEAttribute.isAssignableFrom(changeClass)
			|| UnsetEReference.isAssignableFrom(changeClass)
			|| UnsetEAttribute.isAssignableFrom(changeClass)) {
			return foe.feature.EType.instanceClassName
		} else if (EObjectChange.isAssignableFrom(changeClass)) {
			return foe.element.instanceClassName;
		} else if (EFeatureChange.isAssignableFrom(changeClass)) {
			return foe.feature.class.name
		} 
		return null;
	}
	
	static def dispatch EClass generateEChange(EClass responseChange, EObject changedObject) {
		throw new UnsupportedOperationException("This object type is not supported.");
	}
	
	static def dispatch EClass generateEChange(EClass responseChange, EStructuralFeature feature) {
		// TODO HK Find a better way for type checking...
		if (responseChange.instanceTypeName.equals(Update.typeName)) {
			generateUpdateEChange(feature);
		} else if (responseChange.instanceTypeName.equals(Create.typeName)) {
			generateCreateEChange(feature);
		} else if (responseChange.instanceTypeName.equals(Delete.typeName)) {
			generateDeleteEChange(feature);
		} else {
			return null;
		}
	}
	
	static def dispatch EClass generateEChange(EClass responseChange, EClass element) {
		// TODO HK Find a better way for type checking...
		if (responseChange.instanceTypeName.equals(Update.typeName)) {
			generateUpdateEChange(element);
		} else if (responseChange.instanceTypeName.equals(Create.typeName)) {
			generateCreateEChange(element);
		} else if (responseChange.instanceTypeName.equals(Delete.typeName)) {
			generateDeleteEChange(element);
		} else {
			return null;
		}
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
				return ReferencePackageImpl.eINSTANCE.updateNonContainmentEReference;
			}
		}
	}
	
	static def dispatch EClass generateCreateEChange(EClass element) {
		return ObjectPackageImpl.eINSTANCE.createRootEObject;
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
