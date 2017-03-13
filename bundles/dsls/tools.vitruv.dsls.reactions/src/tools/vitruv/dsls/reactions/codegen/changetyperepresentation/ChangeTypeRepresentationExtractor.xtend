package tools.vitruv.dsls.reactions.codegen.changetyperepresentation

import org.eclipse.emf.ecore.EClass
import tools.vitruv.dsls.reactions.reactionsLanguage.Trigger
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.feature.reference.ReferencePackage
import tools.vitruv.framework.change.echange.root.RootPackage
import tools.vitruv.dsls.reactions.reactionsLanguage.ModelElementChange
import tools.vitruv.framework.change.echange.eobject.EobjectPackage
import tools.vitruv.framework.change.echange.compound.CompoundPackage
import tools.vitruv.dsls.reactions.reactionsLanguage.ModelAttributeChange
import tools.vitruv.framework.change.echange.feature.attribute.AttributePackage
import tools.vitruv.dsls.reactions.reactionsLanguage.ModelAttributeRemovedChange
import tools.vitruv.dsls.reactions.reactionsLanguage.ModelAttributeReplacedChange
import tools.vitruv.dsls.reactions.reactionsLanguage.ModelAttributeInsertedChange
import org.eclipse.emf.ecore.EObject
import tools.vitruv.dsls.reactions.reactionsLanguage.ElementCreationChangeType
import tools.vitruv.dsls.reactions.reactionsLanguage.ElementDeletionChangeType
import tools.vitruv.dsls.reactions.reactionsLanguage.ElementInsertionAsRootChangeType
import tools.vitruv.dsls.reactions.reactionsLanguage.ElementRemovalAsRootChangeType
import tools.vitruv.dsls.reactions.reactionsLanguage.ElementInsertionInListChangeType
import tools.vitruv.dsls.reactions.reactionsLanguage.ElementRemovalFromListChangeType
import tools.vitruv.dsls.reactions.reactionsLanguage.ElementReplacementChangeType
import tools.vitruv.dsls.reactions.reactionsLanguage.ElementCreationAndInsertionChangeType
import tools.vitruv.dsls.reactions.reactionsLanguage.ElementDeletionAndRemovalChangeType
import tools.vitruv.dsls.reactions.reactionsLanguage.ElementDeletionAndCreationAndReplacementChangeType
import tools.vitruv.dsls.reactions.reactionsLanguage.ElementRootChangeType
import tools.vitruv.dsls.reactions.reactionsLanguage.ElementFeatureChangeType
import tools.vitruv.dsls.reactions.reactionsLanguage.ElementExistenceChangeType

final class ChangeTypeRepresentationExtractor {

	public static def dispatch ChangeTypeRepresentation extractChangeTypeRepresentation(Trigger trigger) {
		return new AtomicChangeTypeRepresentation(EChange, null, null, false, false, null);
	}
	
	public static def dispatch ChangeTypeRepresentation extractChangeTypeRepresentation(ModelAttributeChange modelAttributeChange) {
		var hasOldValue = false;
		var hasNewValue = false;
		var EClass clazz = null;
		switch (modelAttributeChange) {
			ModelAttributeInsertedChange: {
				clazz = AttributePackage.Literals.INSERT_EATTRIBUTE_VALUE
				hasNewValue = true
			}
			ModelAttributeRemovedChange: {
				clazz = AttributePackage.Literals.REMOVE_EATTRIBUTE_VALUE
				hasOldValue = true
			}
			ModelAttributeReplacedChange: {
				clazz = AttributePackage.Literals.REPLACE_SINGLE_VALUED_EATTRIBUTE
				hasOldValue = true;
				hasNewValue = true;
			}
		}
		val affectedEObject = modelAttributeChange.feature.metaclass.instanceClass;
		val affectedValue = modelAttributeChange.feature.feature.EType.instanceClass;
		val affectedFeature = modelAttributeChange.feature.feature;
		return new AtomicChangeTypeRepresentation(clazz.instanceClass, affectedEObject, affectedValue, hasOldValue, hasNewValue, affectedFeature);
	}
			
	public static def dispatch ChangeTypeRepresentation extractChangeTypeRepresentation(ModelElementChange modelElementChange) {
		if (modelElementChange?.changeType == null) {
			return new AtomicChangeTypeRepresentation(EChange, null, null, false, false, null);
		}
		return generateChangeTypeRepresentation(modelElementChange.changeType, modelElementChange.elementType?.metaclass)
	}	
	
	private static def dispatch AtomicChangeTypeRepresentation generateChangeTypeRepresentation(ElementRootChangeType modelElementChange, EClass elementClass) {
		var EClass clazz = null;
		var hasNewValue = false;
		switch (modelElementChange) {
			ElementInsertionAsRootChangeType: {
				clazz = RootPackage.Literals.INSERT_ROOT_EOBJECT
				hasNewValue = true;
			}
			ElementRemovalAsRootChangeType:
				clazz = RootPackage.Literals.REMOVE_ROOT_EOBJECT
		} 
		val affectedEObject = null;
		val affectedValue = if (elementClass != null) elementClass.instanceClass else EObject;
		return new AtomicChangeTypeRepresentation(clazz.instanceClass, affectedEObject, affectedValue, !hasNewValue, hasNewValue, null);
	}
	
	private static def dispatch AtomicChangeTypeRepresentation generateChangeTypeRepresentation(ElementFeatureChangeType modelElementChange, EClass elementClass) {
		var hasOldValue = false;
		var hasNewValue = false;
		var EClass clazz = null;
		switch (modelElementChange) {
			ElementInsertionInListChangeType: {
				clazz = ReferencePackage.Literals.INSERT_EREFERENCE
				hasNewValue = true
			}
			ElementRemovalFromListChangeType: {
				clazz = ReferencePackage.Literals.REMOVE_EREFERENCE
				hasOldValue = true
			}
			ElementReplacementChangeType: {
				clazz = ReferencePackage.Literals.REPLACE_SINGLE_VALUED_EREFERENCE
				hasOldValue = true;
				hasNewValue = true;
			}
		}
		val affectedEObject = modelElementChange.feature.metaclass.instanceClass;
		val affectedValue = if (elementClass != null) elementClass.instanceClass else modelElementChange.feature.feature.EType.instanceClass;
		val affectedFeature = modelElementChange.feature.feature; 
		return new AtomicChangeTypeRepresentation(clazz.instanceClass, affectedEObject, affectedValue, hasOldValue, hasNewValue, affectedFeature);
	}
	
	private static def dispatch AtomicChangeTypeRepresentation generateChangeTypeRepresentation(ElementExistenceChangeType modelElementChange, EClass elementClass) {
		var EClass clazz = null;
		var hasNewValue = false;
		switch (modelElementChange) {
			ElementCreationChangeType: {
				clazz = EobjectPackage.Literals.CREATE_EOBJECT
				hasNewValue = true;
			}
			ElementDeletionChangeType:
				clazz = EobjectPackage.Literals.DELETE_EOBJECT
		}
		val affectedEObject = if (elementClass != null) elementClass.instanceClass else EObject;
		val affectedValue = null; 
		return new AtomicChangeTypeRepresentation(clazz.instanceClass, affectedEObject, affectedValue, !hasNewValue, hasNewValue, null);
	}
	
	private static def dispatch CompoundChangeTypeRepresentation generateChangeTypeRepresentation(ElementCreationAndInsertionChangeType modelElementChange, EClass elementClass) {
		val clazz = if (modelElementChange.insertChange instanceof ElementInsertionAsRootChangeType) {
				CompoundPackage.Literals.CREATE_AND_INSERT_ROOT
			} else {
				CompoundPackage.Literals.CREATE_AND_INSERT_NON_ROOT
			}
		val existenceChange = generateChangeTypeRepresentation(modelElementChange.createChange, elementClass) as AtomicChangeTypeRepresentation;
		val usageChange = generateChangeTypeRepresentation(modelElementChange.insertChange, elementClass) as AtomicChangeTypeRepresentation;
		return new CompoundChangeTypeRepresentation(clazz.instanceClass, existenceChange, usageChange);
	}
	
	private static def dispatch CompoundChangeTypeRepresentation generateChangeTypeRepresentation(ElementDeletionAndRemovalChangeType modelElementChange, EClass elementClass) {
		val clazz = if (modelElementChange.removeChange instanceof ElementRemovalAsRootChangeType) {
				CompoundPackage.Literals.REMOVE_AND_DELETE_ROOT
			} else {
				CompoundPackage.Literals.REMOVE_AND_DELETE_NON_ROOT
			}
		val existenceChange = generateChangeTypeRepresentation(modelElementChange.deleteChange, elementClass) as AtomicChangeTypeRepresentation;
		val usageChange = generateChangeTypeRepresentation(modelElementChange.removeChange, elementClass) as AtomicChangeTypeRepresentation;
		return new CompoundChangeTypeRepresentation(clazz.instanceClass, existenceChange, usageChange);
	}

	private static def dispatch CompoundChangeTypeRepresentation generateChangeTypeRepresentation(ElementDeletionAndCreationAndReplacementChangeType modelElementChange, EClass elementClass) {
		val clazz = CompoundPackage.Literals.CREATE_AND_REPLACE_AND_DELETE_NON_ROOT;
		val existenceChange = generateChangeTypeRepresentation(modelElementChange.createChange, elementClass) as AtomicChangeTypeRepresentation;
		val usageChange = generateChangeTypeRepresentation(modelElementChange.replacedChange, elementClass) as AtomicChangeTypeRepresentation;
		return new CompoundChangeTypeRepresentation(clazz.instanceClass, existenceChange, usageChange);
	}
	
}
