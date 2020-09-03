package tools.vitruv.dsls.reactions.codegen.changetyperepresentation

import org.eclipse.emf.ecore.EClass
import tools.vitruv.dsls.reactions.reactionsLanguage.Trigger
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.feature.reference.ReferencePackage
import tools.vitruv.framework.change.echange.root.RootPackage
import tools.vitruv.dsls.reactions.reactionsLanguage.ModelElementChange
import tools.vitruv.framework.change.echange.eobject.EobjectPackage
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
import tools.vitruv.dsls.reactions.reactionsLanguage.ElementExistenceChangeType
import tools.vitruv.dsls.reactions.reactionsLanguage.ElementReferenceChangeType
import static extension tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageHelper.*
import org.eclipse.emf.ecore.EcorePackage

final class ChangeTypeRepresentationExtractor {
	static val CREATE_CHANGE_NAME = "createChange";
	static val DELET_CHANGE_NAME = "deleteChange";
	static val INSERT_CHANGE_NAME = "insertChange";
	static val REMOVE_CHANGE_NAME = "removeChange";
	static val REPLACE_CHANGE_NAME = "replaceChange";
	static val GENERAL_CHANGE_NAME = "change";
	
	static def dispatch ChangeSequenceRepresentation extractChangeSequenceRepresentation(Trigger trigger) {
		val atomicChange = new AtomicChangeTypeRepresentation(GENERAL_CHANGE_NAME, EChange, null, null, false, false, null, false);
		return new ChangeSequenceRepresentation(#[atomicChange]);
	}
	
	static def dispatch ChangeSequenceRepresentation extractChangeSequenceRepresentation(ModelAttributeChange modelAttributeChange) {
		var hasOldValue = false;
		var hasNewValue = false;
		var hasIndex = false;
		var EClass clazz = null;
		var name = "";
		switch (modelAttributeChange) {
			ModelAttributeInsertedChange: {
				clazz = AttributePackage.Literals.INSERT_EATTRIBUTE_VALUE
				hasNewValue = true
				hasIndex = true
				name = INSERT_CHANGE_NAME
			}
			ModelAttributeRemovedChange: {
				clazz = AttributePackage.Literals.REMOVE_EATTRIBUTE_VALUE
				hasOldValue = true
				hasIndex = true
				name = REMOVE_CHANGE_NAME
			}
			ModelAttributeReplacedChange: {
				clazz = AttributePackage.Literals.REPLACE_SINGLE_VALUED_EATTRIBUTE
				hasOldValue = true;
				hasNewValue = true;
				name = REPLACE_CHANGE_NAME
			}
		}
		val affectedEObject = modelAttributeChange.feature.metaclass.javaClassName
		val affectedValue = modelAttributeChange.feature.feature.EType.javaClassName
		val affectedFeature = modelAttributeChange.feature.feature;
		val atomicChange = new AtomicChangeTypeRepresentation(name, clazz.instanceClass, affectedEObject, affectedValue, hasOldValue, hasNewValue, affectedFeature, hasIndex);
		return new ChangeSequenceRepresentation(#[atomicChange]);
	}
			
	static def dispatch ChangeSequenceRepresentation extractChangeSequenceRepresentation(ModelElementChange modelElementChange) {
		var atomicChanges = newArrayList;
		if (modelElementChange?.changeType === null) {
			atomicChanges += new AtomicChangeTypeRepresentation(GENERAL_CHANGE_NAME, EChange, null, null, false, false, null, false);
		} else {
			atomicChanges += generateChangeTypeRepresentation(modelElementChange.changeType, modelElementChange.elementType?.metaclass)
		}
		return new ChangeSequenceRepresentation(atomicChanges);
	}	
	
	private static def dispatch Iterable<AtomicChangeTypeRepresentation> generateChangeTypeRepresentation(ElementRootChangeType modelElementChange, EClass elementClass) {
		var EClass clazz = null;
		var hasNewValue = false;
		var name = "";
		switch (modelElementChange) {
			ElementInsertionAsRootChangeType: {
				clazz = RootPackage.Literals.INSERT_ROOT_EOBJECT
				hasNewValue = true;
				name = INSERT_CHANGE_NAME
			}
			ElementRemovalAsRootChangeType: {
				clazz = RootPackage.Literals.REMOVE_ROOT_EOBJECT
				name = REMOVE_CHANGE_NAME
			}
		} 
		val affectedEObject = null;
		val affectedValue = if (elementClass !== null) elementClass.javaClassName else EObject.canonicalName
		return #[new AtomicChangeTypeRepresentation(name, clazz.instanceClass, affectedEObject, affectedValue, !hasNewValue, hasNewValue, null, true)];
	}
	
	private static def dispatch Iterable<AtomicChangeTypeRepresentation> generateChangeTypeRepresentation(ElementReferenceChangeType modelElementChange, EClass elementClass) {
		var hasOldValue = false;
		var hasNewValue = false;
		var hasIndex = false;
		var EClass clazz = null;
		var name = "";
		switch (modelElementChange) {
			ElementInsertionInListChangeType: {
				clazz = ReferencePackage.Literals.INSERT_EREFERENCE
				hasNewValue = true
				hasIndex = true
				name = INSERT_CHANGE_NAME
			}
			ElementRemovalFromListChangeType: {
				clazz = ReferencePackage.Literals.REMOVE_EREFERENCE
				hasOldValue = true
				hasIndex = true
				name = REMOVE_CHANGE_NAME
			}
			ElementReplacementChangeType: {
				clazz = ReferencePackage.Literals.REPLACE_SINGLE_VALUED_EREFERENCE
				hasOldValue = true;
				hasNewValue = true;
				name = REPLACE_CHANGE_NAME
			}
		}
		val affectedEObject = modelElementChange.feature.metaclass.javaClassName;
		val affectedValue = if (elementClass !== null) elementClass.javaClassName else modelElementChange.feature.feature.EType.javaClassName;
		val affectedFeature = modelElementChange.feature.feature; 
		return #[new AtomicChangeTypeRepresentation(name, clazz.instanceClass, affectedEObject, affectedValue, hasOldValue, hasNewValue, affectedFeature, hasIndex)];
	}
	
	private static def dispatch Iterable<AtomicChangeTypeRepresentation> generateChangeTypeRepresentation(ElementExistenceChangeType modelElementChange, EClass elementClass) {
		var EClass clazz = null;
		var name = "";
		switch (modelElementChange) {
			ElementCreationChangeType: {
				clazz = EobjectPackage.Literals.CREATE_EOBJECT
				name = CREATE_CHANGE_NAME
			}
			ElementDeletionChangeType: {
				clazz = EobjectPackage.Literals.DELETE_EOBJECT
				name = DELET_CHANGE_NAME
			}
		}
		val affectedEObject = if (elementClass !== null) elementClass.javaClassName else EcorePackage.eINSTANCE.EObject.javaClassName;
		val affectedValue = null; 
		return #[new AtomicChangeTypeRepresentation(name, clazz.instanceClass, affectedEObject, affectedValue, false, false, null, false)];
	}
	
	private static def dispatch Iterable<AtomicChangeTypeRepresentation> generateChangeTypeRepresentation(ElementCreationAndInsertionChangeType modelElementChange, EClass elementClass) {
		val createChanges = generateChangeTypeRepresentation(modelElementChange.createChange, elementClass);
		val insertChanges = generateChangeTypeRepresentation(modelElementChange.insertChange, elementClass);
		return createChanges + insertChanges;
	}
	
	private static def dispatch Iterable<AtomicChangeTypeRepresentation> generateChangeTypeRepresentation(ElementDeletionAndRemovalChangeType modelElementChange, EClass elementClass) {
		val removeChanges = generateChangeTypeRepresentation(modelElementChange.removeChange, elementClass);
		val deleteChanges = generateChangeTypeRepresentation(modelElementChange.deleteChange, elementClass);
		return removeChanges + deleteChanges;
	}

	private static def dispatch Iterable<AtomicChangeTypeRepresentation> generateChangeTypeRepresentation(ElementDeletionAndCreationAndReplacementChangeType modelElementChange, EClass elementClass) {
		val createChanges = generateChangeTypeRepresentation(modelElementChange.createChange, elementClass);
		val replaceChanges = generateChangeTypeRepresentation(modelElementChange.replacedChange, elementClass);
		val deleteChanges = generateChangeTypeRepresentation(modelElementChange.deleteChange, elementClass);
		return createChanges + replaceChanges + deleteChanges
	}
	
}
