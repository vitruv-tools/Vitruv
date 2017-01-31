package tools.vitruv.dsls.reactions.codegen.helper

import org.eclipse.emf.ecore.EClass
import tools.vitruv.dsls.reactions.reactionsLanguage.Trigger
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.feature.reference.ReferencePackage
import tools.vitruv.framework.change.echange.root.RootPackage
import java.util.List
import tools.vitruv.dsls.reactions.reactionsLanguage.ModelElementChange
import tools.vitruv.framework.change.echange.eobject.EobjectPackage
import tools.vitruv.framework.change.echange.compound.CompoundPackage
import tools.vitruv.dsls.reactions.reactionsLanguage.ModelAttributeChange
import org.eclipse.xtend2.lib.StringConcatenationClient
import java.util.Map
import tools.vitruv.framework.change.echange.feature.attribute.AttributePackage
import tools.vitruv.dsls.reactions.reactionsLanguage.ModelAttributeRemovedChange
import tools.vitruv.dsls.reactions.reactionsLanguage.ModelAttributeReplacedChange
import tools.vitruv.dsls.reactions.reactionsLanguage.ModelAttributeInsertedChange
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
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
import tools.vitruv.framework.change.echange.compound.CreateAndInsertEObject
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteEObject
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceAndDeleteNonRoot

final class ChangeTypeRepresentationExtractor {
	private static final String affectedEObjectAttribute = "affectedEObject";
	private static final String affectedFeatureAttribute = "affectedFeature";
	private static final String oldValueAttribute = "oldValue";
	private static final String newValueAttribute = "newValue";
	
	private static def boolean isPrimitiveType(Class<?> type) {
		return primitveTypeNamesMap.containsKey(type.name);
	}
	
	private static Map<String, String> primitveTypeNamesMap = #{
		"short" -> Short.name,
		"int" -> Integer.name,
		"long" -> Long.name,
		"double" -> Double.name,
		"float" -> Float.name,
		"boolean" -> Boolean.name,
		"char" -> Character.name,
		"byte" -> Byte.name,
		"void" -> Void.name
	}
		
	public static abstract class ChangeTypeRepresentation {
		public def Class<?> getChangeType();
		public def List<Class<?>> getGenericTypeParameters();
		
		public def StringConcatenationClient getUntypedChangeTypeRepresentation() {
			return '''«changeType»'''
		}
		
		/**
		 * Returns the relevant change type representation that is used by the consistency preservation.
		 * Is one of the atomic changes if dealing with a compound type representation 
		 */
		public def AtomicChangeTypeRepresentation getRelevantChangeTypeRepresentation();
		
		public def StringConcatenationClient getTypedChangeTypeRepresentation() {
			return '''«changeType»«FOR param : genericTypeParameters BEFORE "<" SEPARATOR ", " AFTER ">"»«
				IF param.isPrimitiveType»«primitveTypeNamesMap.get(param)»«ELSE»«param»«ENDIF»«
				ENDFOR»'''
		}	
		
		public def StringConcatenationClient getRelevantChangeAssignmentCode(String originalChangeVariableName, boolean isOriginalVariableUntyped, String typedChangeVariableName);
	}
	
	public static class CompoundChangeTypRepresentation extends ChangeTypeRepresentation {
		private Class<?> changeType;
		private AtomicChangeTypeRepresentation existenceChange;
		private AtomicChangeTypeRepresentation usageChange;
		
		protected new(Class<?> changeType, AtomicChangeTypeRepresentation existenceChange, AtomicChangeTypeRepresentation usageChange) {
			this.changeType = changeType;
			this.existenceChange = existenceChange;
			this.usageChange = usageChange;
		}
		
		override getChangeType() {
			return changeType;
		}
		
		override getGenericTypeParameters() {
			return #[usageChange.affectedElementClass, usageChange.affectedValueClass].filterNull.toList;
		}
		
		def getExistenceChange() {
			return existenceChange;
		}
		
		def getUsageChange() {
			return usageChange;
		}
		
		override getRelevantChangeTypeRepresentation() {
			return usageChange;
		}
		
		public override StringConcatenationClient getRelevantChangeAssignmentCode(String originalChangeVariableName, boolean isOriginalVariableUntyped, String typedChangeVariableName) {
			val typedRelevantChangeString = relevantChangeTypeRepresentation.typedChangeTypeRepresentation;
			return '''
				«typedRelevantChangeString» «typedChangeVariableName» = «
					»(«IF isOriginalVariableUntyped»(«typedChangeTypeRepresentation»)«ENDIF»«originalChangeVariableName»).«
					»«IF CreateAndInsertEObject.isAssignableFrom(changeType)»getInsertChange()«
					ELSEIF RemoveAndDeleteEObject.isAssignableFrom(changeType)»getRemoveChange()«
					ELSEIF CreateAndReplaceAndDeleteNonRoot.isAssignableFrom(changeType)»getReplaceChange()«ENDIF»;
				'''			
		}
	}
	
	public static class AtomicChangeTypeRepresentation extends ChangeTypeRepresentation {
		protected Class<?> changeType;
		protected Class<?> affectedElementClass;
		protected Class<?> affectedValueClass;
		protected boolean hasOldValue;
		protected boolean hasNewValue;
		protected EStructuralFeature affectedFeature;
		
		protected new(Class<?> changeType, Class<?> affectedElementClass, Class<?> affectedValueClass, boolean hasOldValue, boolean hasNewValue, EStructuralFeature affectedFeature) {
			this.changeType = changeType;
			this.affectedElementClass = affectedElementClass;
			this.affectedValueClass = affectedValueClass;
			this.affectedFeature = affectedFeature;
			this.hasOldValue = hasOldValue;
			this.hasNewValue = hasNewValue;
		}
		
		public override Class<?> getChangeType() {
			return changeType;
		}
		
		public def Class<?> getAffectedElementClass() {
			return affectedElementClass;
		}
		
		public def Class<?> getAffectedValueClass() {
			return affectedValueClass;
		}
		
		public def EStructuralFeature getAffectedFeature() {
			return affectedFeature;
		}
		
		public def boolean hasOldValue() {
			return hasOldValue;
		}
		
		public def boolean hasNewValue() {
			return hasNewValue;
		}
		
		public override List<Class<?>> getGenericTypeParameters() {
			return #[affectedElementClass, affectedValueClass].filterNull.toList;
		}
		
		public override StringConcatenationClient getUntypedChangeTypeRepresentation() {
			return '''«changeType»'''
		}
		
		public override StringConcatenationClient getTypedChangeTypeRepresentation() {
			return '''«changeType»«FOR param : genericTypeParameters BEFORE "<" SEPARATOR ", " AFTER ">"»«
				IF param.isPrimitiveType»«primitveTypeNamesMap.get(param)»«ELSE»«param»«ENDIF»«
				ENDFOR»'''
		}
		
		override getRelevantChangeTypeRepresentation() {
			return this;
		}
		
		public def Iterable<AccessibleElement> generatePropertiesParameterList() {
			val result = <AccessibleElement>newArrayList();
			if (affectedElementClass != null) {
				result.add(new AccessibleElement(affectedEObjectAttribute, affectedElementClass));
			}
			if (affectedFeature != null) {
				result.add(new AccessibleElement(affectedFeatureAttribute, affectedFeature.eClass.instanceClass));
			}
			if (hasOldValue) {
				result.add(new AccessibleElement(oldValueAttribute, affectedValueClass));
			}
			if (hasNewValue) {
				result.add(new AccessibleElement(newValueAttribute, affectedValueClass));
			}	
			return result;
		}
		
		public def StringConcatenationClient generatePropertiesAssignmentCode(String typedChangeVariableName) {
			'''
			«IF affectedElementClass != null»
				«affectedElementClass» «affectedEObjectAttribute» = «typedChangeVariableName».get«affectedEObjectAttribute.toFirstUpper»();
			«ENDIF»
			«IF affectedFeature != null»
				«affectedFeature.eClass.instanceClass» «affectedFeatureAttribute» = «typedChangeVariableName».get«affectedFeatureAttribute.toFirstUpper»();
			«ENDIF»
			«IF hasOldValue»
				«affectedValueClass» «oldValueAttribute» = «typedChangeVariableName».get«oldValueAttribute.toFirstUpper»();
			«ENDIF»
			«IF hasNewValue»
				«affectedValueClass» «newValueAttribute» = «typedChangeVariableName».get«newValueAttribute.toFirstUpper»();
			«ENDIF»
			'''
		}
		
		public override StringConcatenationClient getRelevantChangeAssignmentCode(String originalChangeVariableName, boolean isOriginalVariableUntyped, String typedChangeVariableName) {
			val typedRelevantChangeString = relevantChangeTypeRepresentation.typedChangeTypeRepresentation;
			return '''
				«typedRelevantChangeString» «typedChangeVariableName» = «
					IF isOriginalVariableUntyped»(«typedChangeTypeRepresentation»)«ENDIF»«originalChangeVariableName»;
				'''
		}
		
	}
	

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
	
	private static def dispatch CompoundChangeTypRepresentation generateChangeTypeRepresentation(ElementCreationAndInsertionChangeType modelElementChange, EClass elementClass) {
		val clazz = if (modelElementChange.insertChange instanceof ElementInsertionAsRootChangeType) {
				CompoundPackage.Literals.CREATE_AND_INSERT_ROOT
			} else {
				CompoundPackage.Literals.CREATE_AND_INSERT_NON_ROOT
			}
		val existenceChange = ChangeTypeRepresentationExtractor.generateChangeTypeRepresentation(modelElementChange.createChange, elementClass) as AtomicChangeTypeRepresentation;
		val usageChange = ChangeTypeRepresentationExtractor.generateChangeTypeRepresentation(modelElementChange.insertChange, elementClass) as AtomicChangeTypeRepresentation;
		return new CompoundChangeTypRepresentation(clazz.instanceClass, existenceChange, usageChange);
	}
	
	private static def dispatch CompoundChangeTypRepresentation generateChangeTypeRepresentation(ElementDeletionAndRemovalChangeType modelElementChange, EClass elementClass) {
		val clazz = if (modelElementChange.removeChange instanceof ElementRemovalAsRootChangeType) {
				CompoundPackage.Literals.REMOVE_AND_DELETE_ROOT
			} else {
				CompoundPackage.Literals.REMOVE_AND_DELETE_NON_ROOT
			}
		val existenceChange = ChangeTypeRepresentationExtractor.generateChangeTypeRepresentation(modelElementChange.deleteChange, elementClass) as AtomicChangeTypeRepresentation;
		val usageChange = ChangeTypeRepresentationExtractor.generateChangeTypeRepresentation(modelElementChange.removeChange, elementClass) as AtomicChangeTypeRepresentation;
		return new CompoundChangeTypRepresentation(clazz.instanceClass, existenceChange, usageChange);
	}

	private static def dispatch CompoundChangeTypRepresentation generateChangeTypeRepresentation(ElementDeletionAndCreationAndReplacementChangeType modelElementChange, EClass elementClass) {
		val clazz = CompoundPackage.Literals.CREATE_AND_REPLACE_AND_DELETE_NON_ROOT;
		val existenceChange = ChangeTypeRepresentationExtractor.generateChangeTypeRepresentation(modelElementChange.createChange, elementClass) as AtomicChangeTypeRepresentation;
		val usageChange = ChangeTypeRepresentationExtractor.generateChangeTypeRepresentation(modelElementChange.replacedChange, elementClass) as AtomicChangeTypeRepresentation;
		return new CompoundChangeTypRepresentation(clazz.instanceClass, existenceChange, usageChange);
	}
	
}
