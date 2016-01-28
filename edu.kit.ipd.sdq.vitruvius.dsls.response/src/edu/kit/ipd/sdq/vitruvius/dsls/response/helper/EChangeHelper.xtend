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
import org.eclipse.emf.ecore.EModelElement
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ConcreteModelElementChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ConcreteModelElementUpdate
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ModelChange
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ConcreteModelElementCreate
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ConcreteModelElementDelete
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.CreateRootEObject
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.DeleteRootEObject
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ReplaceRootEObject

final class EChangeHelper {
	
	static def String getGenericTypeParameterFQNOfChange(EClass changeEClass, FeatureOfElement foe) {
		val changeClass = changeEClass.instanceClass;
		var String className = "";
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
	
	private static def String convertPrimitiveTypeToClassName(String className) {
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
	
	static def dispatch EClass generateEChange(ModelChange modelChange) {
		return ChangePackageImpl.eINSTANCE.EChange;
	}
	
	static def dispatch EClass generateEChange(ConcreteModelElementChange elementChange) {
		generateEChange(elementChange, elementChange.changedObject.feature?:elementChange.changedObject.element);
	}
	
	private static def dispatch EClass generateEChange(ConcreteModelElementChange elementUpdate, EModelElement element) {
		throw new UnsupportedOperationException("This type of change or element is currently not supported.");
	}
	
	private static def dispatch EClass generateEChange(ConcreteModelElementUpdate elementUpdate, EAttribute feature) {
		if (feature.upperBound > 1 || feature.upperBound == -1) {
			return AttributePackageImpl.eINSTANCE.replaceEAttributeValue;
		} else {
			return AttributePackageImpl.eINSTANCE.updateSingleValuedEAttribute;
		}
	}	
	
	private static def dispatch EClass generateEChange(ConcreteModelElementUpdate elementUpdate, EReference feature) {
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
	
	private static def dispatch EClass generateEChange(ConcreteModelElementUpdate elementUpdate, EClass element) {
		return ObjectPackageImpl.eINSTANCE.replaceRootEObject;
	}
	
	private static def dispatch EClass generateEChange(ConcreteModelElementCreate elementCreate, EAttribute feature) {
		if (feature.upperBound > 1 || feature.upperBound == -1) {
			return AttributePackageImpl.eINSTANCE.insertEAttributeValue;
		} else {
			return AttributePackageImpl.eINSTANCE.updateSingleValuedEAttribute
		}
	}
	
	private static def dispatch EClass generateEChange(ConcreteModelElementCreate elementCreate, EReference feature) {
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
	
	private static def dispatch EClass generateEChange(ConcreteModelElementCreate elementCreate, EClass element) {
		return ObjectPackageImpl.eINSTANCE.createRootEObject;
	}	
	
	private static def dispatch EClass generateEChange(ConcreteModelElementDelete elementDelete, EModelElement changedElement) {
		throw new UnsupportedOperationException("The given element is not supported.");
	} 
	
	private static def dispatch EClass generateEChange(ConcreteModelElementDelete elementDelete, EAttribute feature) {
		if (feature.upperBound > 1 || feature.upperBound == -1) {
			return AttributePackageImpl.eINSTANCE.removeEAttributeValue
		} else {
			return FeaturePackageImpl.eINSTANCE.unsetEAttribute;
		}
	}
	
	private static def dispatch EClass generateEChange(ConcreteModelElementDelete elementDelete, EReference feature) {
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
	
	private static def dispatch EClass generateEChange(ConcreteModelElementDelete elementDelete, EClass element) {
		return ObjectPackageImpl.eINSTANCE.deleteRootEObject;
	}	

	private static def dispatch EClass generateEChange(ConcreteModelElementChange elementChange, EAttribute feature) {
		if (feature.upperBound > 1 || feature.upperBound == -1) {
			return AttributePackageImpl.eINSTANCE.updateEAttribute;
		} else {
			return AttributePackageImpl.eINSTANCE.updateSingleValuedEAttribute;
		}
	}	
	
	private static def dispatch EClass generateEChange(ConcreteModelElementChange elementChange, EReference feature) {
		if (feature.upperBound > 1 || feature.upperBound == -1) {
			if (feature.containment) {
				return ContainmentPackageImpl.eINSTANCE.updateContainmentEReference;
			} else {
				return ReferencePackageImpl.eINSTANCE.updateNonContainmentEReference;
			}
		} else {
			if (feature.containment) {
				return ContainmentPackageImpl.eINSTANCE.updateSingleValuedContainmentEReference;
			} else {
				return ReferencePackageImpl.eINSTANCE.updateSingleValuedNonContainmentEReference;
			}
		}
	}
	
	private static def dispatch EClass generateEChange(ConcreteModelElementChange elementChange, EClass element) {
		return ObjectPackageImpl.eINSTANCE.EObjectChange;
	}
	
	public static def String getEChangeFeatureNameOfChangedObject(EClass change) {
		if (EFeatureChange.isAssignableFrom(change.instanceClass)) {
			// TODO HK This is not correct
			return "oldAffectedEObject";
		} else if (CreateRootEObject.isAssignableFrom(change.instanceClass)) {
			return "newValue"
		} else if (DeleteRootEObject.isAssignableFrom(change.instanceClass)
			|| ReplaceRootEObject.isAssignableFrom(change.instanceClass)) {
			return "oldValue"
		} else {
			throw new IllegalStateException();
		}
	}
}
