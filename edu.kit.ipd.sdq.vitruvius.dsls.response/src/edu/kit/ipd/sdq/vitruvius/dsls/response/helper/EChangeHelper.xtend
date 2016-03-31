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
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.impl.ReferencePackageImpl
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl.ContainmentPackageImpl
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.impl.ObjectPackageImpl
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ConcreteModelElementChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ModelChange
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl.ChangePackageImpl
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.CreateRootEObject
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.DeleteRootEObject
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ReplaceRootEObject
import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.AtomicFeatureChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.AtomicRootObjectChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.MultiValuedFeatureInsertChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.MultiValuedFeaturePermuteChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.MultiValuedFeatureRemoveChange
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.AttributePackage
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.InsertRootChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.RemoveRootChange
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.ChangePackage
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.SingleValuedFeatureReplace
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.SingleValuedFeatureCreate
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.SingleValuedFeatureDelete
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Trigger
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.dsls.mirbase.mirBase.FeatureOfElement

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
	
	static def dispatch Class<?> getGenericTypeParameterOfChange(Trigger change) {
		throw new UnsupportedOperationException();
	}
	
	static def dispatch Class<?> getGenericTypeParameterOfChange(AtomicFeatureChange featureChange) {
		return featureChange.changedFeature.feature.EType.instanceClass;
	}
	
	static def dispatch Class<?> getGenericTypeParameterOfChange(AtomicRootObjectChange elementChange) {
		return elementChange.changedElement.element.instanceClass;
	}
	
	static def dispatch String getGenericTypeParameterFQNOfChange(ConcreteModelElementChange change) {
		throw new UnsupportedOperationException();
	}
	
	static def dispatch String getGenericTypeParameterFQNOfChange(AtomicFeatureChange featureChange) {
		return featureChange.changedFeature?.feature?.EType?.instanceClassName?.convertPrimitiveTypeToClassName;
	}
	
	static def dispatch String getGenericTypeParameterFQNOfChange(AtomicRootObjectChange elementChange) {
		return elementChange.changedElement?.element?.instanceClassName?.convertPrimitiveTypeToClassName;
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
	
	static def Class<? extends EChange> generateEChangeInstanceClass(Trigger trigger) {
		trigger.generateEChange().instanceClass as Class<? extends EChange>;
	}
	
	static def dispatch EClass generateEChange(Trigger trigger) {
		throw new UnsupportedOperationException();
	}
	
	static def dispatch EClass generateEChange(ModelChange modelChange) {
		return ChangePackageImpl.eINSTANCE.EChange;
	}
	
	static def dispatch EClass generateEChange(AtomicFeatureChange elementChange) {
		generateEChange(elementChange, elementChange.changedFeature.feature);
	}
	
	static def dispatch EClass generateEChange(AtomicRootObjectChange elementChange) {
		generateEChange(elementChange, elementChange.changedElement.element);
	}
	
	private static def dispatch EClass generateEChange(ConcreteModelElementChange elementUpdate, EObject element) {
		return ChangePackage.Literals.ECHANGE;
	}

	private static def dispatch EClass generateEChange(MultiValuedFeaturePermuteChange elementUpdate, EAttribute feature) {
		return AttributePackage.Literals.PERMUTE_EATTRIBUTE_VALUES;
	}	
	
	private static def dispatch EClass generateEChange(MultiValuedFeaturePermuteChange elementUpdate, EReference feature) {
		if (feature.containment) {
			return ContainmentPackageImpl.eINSTANCE.permuteContainmentEReferenceValues;
		} else {
			return ReferencePackageImpl.eINSTANCE.permuteNonContainmentEReferenceValues;
		}
	}
	
	
	private static def dispatch EClass generateEChange(MultiValuedFeatureInsertChange elementUpdate, EAttribute feature) {
		return AttributePackage.Literals.INSERT_EATTRIBUTE_VALUE;
	}	
	
	private static def dispatch EClass generateEChange(MultiValuedFeatureInsertChange elementUpdate, EReference feature) {
		if (feature.containment) {
			return ContainmentPackageImpl.eINSTANCE.createNonRootEObjectInList;
		} else {
			return ReferencePackageImpl.eINSTANCE.insertNonContainmentEReference;
		}
	}
	
	private static def dispatch EClass generateEChange(MultiValuedFeatureRemoveChange elementUpdate, EAttribute feature) {
		return AttributePackage.Literals.REMOVE_EATTRIBUTE_VALUE;
	}	
	
	private static def dispatch EClass generateEChange(MultiValuedFeatureRemoveChange elementUpdate, EReference feature) {
		if (feature.containment) {
			return ContainmentPackageImpl.eINSTANCE.deleteNonRootEObjectInList;
		} else {
			return ReferencePackageImpl.eINSTANCE.removeNonContainmentEReference;
		}
	}
	
	private static def dispatch EClass generateEChange(SingleValuedFeatureCreate elementUpdate, EAttribute feature) {
		return AttributePackage.Literals.UPDATE_SINGLE_VALUED_EATTRIBUTE;
	}
	
	private static def dispatch EClass generateEChange(SingleValuedFeatureCreate elementUpdate, EReference feature) {
		if (feature.containment) {
			return ContainmentPackageImpl.eINSTANCE.createNonRootEObjectSingle;
		} else {
			return ReferencePackageImpl.eINSTANCE.updateSingleValuedNonContainmentEReference;
		}
	}
	
	private static def dispatch EClass generateEChange(SingleValuedFeatureDelete elementUpdate, EAttribute feature) {
		return AttributePackage.Literals.UPDATE_SINGLE_VALUED_EATTRIBUTE;
	}
	
	private static def dispatch EClass generateEChange(SingleValuedFeatureDelete elementUpdate, EReference feature) {
		if (feature.containment) {
			return ContainmentPackageImpl.eINSTANCE.deleteNonRootEObjectSingle;
		} else {
			return ReferencePackageImpl.eINSTANCE.updateSingleValuedNonContainmentEReference;
		}
	}
	
	private static def dispatch EClass generateEChange(SingleValuedFeatureReplace elementUpdate, EAttribute feature) {
		return AttributePackage.Literals.UPDATE_SINGLE_VALUED_EATTRIBUTE;
	}
	
	private static def dispatch EClass generateEChange(SingleValuedFeatureReplace elementUpdate, EReference feature) {
		if (feature.containment) {
			return ContainmentPackageImpl.eINSTANCE.replaceNonRootEObjectSingle;
		} else {
			return ReferencePackageImpl.eINSTANCE.updateSingleValuedNonContainmentEReference;
		}
	}
	
	private static def dispatch EClass generateEChange(InsertRootChange elementCreate, EClass element) {
		ObjectPackageImpl.eINSTANCE.createRootEObject;
	}
	
	private static def dispatch EClass generateEChange(RemoveRootChange elementCreate, EClass element) {
		ObjectPackageImpl.eINSTANCE.deleteRootEObject;
	}
	
	private static def dispatch EClass generateEChange(ConcreteModelElementChange elementChange, EClass element) {
		return ObjectPackageImpl.eINSTANCE.EObjectChange;
	}
	
	public static def String getEChangeFeatureNameOfChangedObject(Trigger change) {
		val eChange = change.generateEChange()
		if (EFeatureChange.isAssignableFrom(eChange.instanceClass)) {
			// TODO HK (Change MM) This is not correct
			return "oldAffectedEObject";
		} else if (CreateRootEObject.isAssignableFrom(eChange.instanceClass)) {
			return "newValue"
		} else if (DeleteRootEObject.isAssignableFrom(eChange.instanceClass)
			|| ReplaceRootEObject.isAssignableFrom(eChange.instanceClass)) {
			return "oldValue"
		} else {
			throw new IllegalStateException();
		}
	}
	
}
