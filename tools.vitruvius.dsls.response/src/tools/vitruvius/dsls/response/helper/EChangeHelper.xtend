package tools.vitruvius.dsls.response.helper

import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EClass
import tools.vitruvius.dsls.response.responseLanguage.ConcreteModelElementChange
import tools.vitruvius.dsls.response.responseLanguage.ModelChange
import org.eclipse.emf.ecore.EObject
import tools.vitruvius.dsls.response.responseLanguage.AtomicFeatureChange
import tools.vitruvius.dsls.response.responseLanguage.AtomicRootObjectChange
import tools.vitruvius.dsls.response.responseLanguage.MultiValuedFeatureInsertChange
import tools.vitruvius.dsls.response.responseLanguage.MultiValuedFeaturePermuteChange
import tools.vitruvius.dsls.response.responseLanguage.MultiValuedFeatureRemoveChange
import tools.vitruvius.dsls.response.responseLanguage.InsertRootChange
import tools.vitruvius.dsls.response.responseLanguage.RemoveRootChange
import tools.vitruvius.dsls.response.responseLanguage.SingleValuedFeatureReplace
import tools.vitruvius.dsls.response.responseLanguage.Trigger
import tools.vitruvius.dsls.mirbase.mirBase.FeatureOfElement
import tools.vitruvius.framework.change.echange.EChange
import tools.vitruvius.framework.change.echange.ChangePackage
import tools.vitruvius.framework.change.echange.feature.attribute.AttributePackage
import tools.vitruvius.framework.change.echange.feature.reference.ReferencePackage
import tools.vitruvius.framework.change.echange.root.RootPackage
import tools.vitruvius.framework.change.echange.root.InsertRootEObject
import tools.vitruvius.framework.change.echange.root.RemoveRootEObject
import java.util.List
import tools.vitruvius.framework.change.echange.feature.FeatureEChange
import tools.vitruvius.framework.change.echange.root.RootEChange

final class EChangeHelper {
	
	static def String getGenericTypeParameterFQNOfChange(EClass changeEClass, FeatureOfElement foe) {
		val changeClass = changeEClass.instanceClass;
		var String className = "";
		if (FeatureEChange.isAssignableFrom(changeClass)
//			|| UpdateEAttribute.isAssignableFrom(changeClass)) {
//			|| UnsetEReference.isAssignableFrom(changeClass)
//			|| UnsetEAttribute.isAssignableFrom(changeClass)
		) {
			className = foe.feature.EType.instanceClassName
		} else if (RootEChange.isAssignableFrom(changeClass)) {
			className = foe.element.instanceClassName;
		}/* else if (EFeatureChange.isAssignableFrom(changeClass)) {
			className = foe.feature.class.name
		}*/
		
		className = className?.convertPrimitiveTypeToClassName;
		return className;
	}
	
	static def dispatch List<Class<?>> getGenericTypeParametersOfChange(Trigger change) {
		throw new UnsupportedOperationException();
	}
	
	static def dispatch List<Class<?>> getGenericTypeParametersOfChange(AtomicFeatureChange featureChange) {
		return #[featureChange.changedFeature.element.instanceClass, featureChange.changedFeature.feature.EType.instanceClass];
	}
	
	static def dispatch List<Class<?>> getGenericTypeParametersOfChange(AtomicRootObjectChange elementChange) {
		return #[elementChange.changedElement.element.instanceClass];
	}
	
	static def List<String> getGenericTypeParameterFQNsOfChange(ConcreteModelElementChange change) {
		return change.genericTypeParametersOfChange.map[it.name.convertPrimitiveTypeToClassName];
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
		return ChangePackage.Literals.ECHANGE;
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
		return ReferencePackage.Literals.PERMUTE_EREFERENCES;
	}
	
	
	private static def dispatch EClass generateEChange(MultiValuedFeatureInsertChange elementUpdate, EAttribute feature) {
		return AttributePackage.Literals.INSERT_EATTRIBUTE_VALUE;
	}	
	
	private static def dispatch EClass generateEChange(MultiValuedFeatureInsertChange elementUpdate, EReference feature) {
		return ReferencePackage.Literals.INSERT_EREFERENCE;
	}
	
	private static def dispatch EClass generateEChange(MultiValuedFeatureRemoveChange elementUpdate, EAttribute feature) {
		return AttributePackage.Literals.REMOVE_EATTRIBUTE_VALUE;
	}	
	
	private static def dispatch EClass generateEChange(MultiValuedFeatureRemoveChange elementUpdate, EReference feature) {
		return ReferencePackage.Literals.REMOVE_EREFERENCE;
	}
	
//	private static def dispatch EClass generateEChange(SingleValuedFeatureCreate elementUpdate, EAttribute feature) {
//		return AttributePackage.Literals.REPLACE_SINGLE_VALUED_EATTRIBUTE;
//	}
//	
//	private static def dispatch EClass generateEChange(SingleValuedFeatureCreate elementUpdate, EReference feature) {
//		return ReferencePackage.Literals.REPLACE_SINGLE_VALUED_EREFERENCE;
//	}
//	
//	private static def dispatch EClass generateEChange(SingleValuedFeatureDelete elementUpdate, EAttribute feature) {
//		return AttributePackage.Literals.REPLACE_SINGLE_VALUED_EATTRIBUTE;
//	}
//	
//	private static def dispatch EClass generateEChange(SingleValuedFeatureDelete elementUpdate, EReference feature) {
//		return ReferencePackage.Literals.REPLACE_SINGLE_VALUED_EREFERENCE;
//	}
//	
	private static def dispatch EClass generateEChange(SingleValuedFeatureReplace elementUpdate, EAttribute feature) {
		return AttributePackage.Literals.REPLACE_SINGLE_VALUED_EATTRIBUTE;
	}
	
	private static def dispatch EClass generateEChange(SingleValuedFeatureReplace elementUpdate, EReference feature) {
		return ReferencePackage.Literals.REPLACE_SINGLE_VALUED_EREFERENCE;
	}
	
	private static def dispatch EClass generateEChange(InsertRootChange elementCreate, EClass element) {
		RootPackage.Literals.INSERT_ROOT_EOBJECT;
	}
	
	private static def dispatch EClass generateEChange(RemoveRootChange elementCreate, EClass element) {
		RootPackage.Literals.REMOVE_ROOT_EOBJECT;
	}
	
	private static def dispatch EClass generateEChange(ConcreteModelElementChange elementChange, EClass element) {
		return ChangePackage.Literals.ECHANGE;
	}
	
	public static def String getEChangeFeatureNameOfChangedObject(Trigger change) {
		val eChange = change.generateEChange()
		if (FeatureEChange.isAssignableFrom(eChange.instanceClass)) {
			// TODO HK (Change MM) This is not correct
			return "affectedEObject";
		} else if (InsertRootEObject.isAssignableFrom(eChange.instanceClass)) {
			return "newValue"
		} else if (RemoveRootEObject.isAssignableFrom(eChange.instanceClass)) {
			//|| ReplaceRootEObject.isAssignableFrom(eChange.instanceClass)) {
			return "oldValue"
		} else {
			throw new IllegalStateException();
		}
	}
	
}
