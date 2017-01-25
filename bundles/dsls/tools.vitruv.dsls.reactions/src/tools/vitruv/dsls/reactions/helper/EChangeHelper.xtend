package tools.vitruv.dsls.reactions.helper

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

final class EChangeHelper {
	
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
		
		public def StringConcatenationClient getTypedChangeTypeRepresentation() {
			return '''«changeType»«FOR param : genericTypeParameters BEFORE "<" SEPARATOR ", " AFTER ">"»«
				IF param.isPrimitiveType»«primitveTypeNamesMap.get(param)»«ELSE»«param»«ENDIF»«
				ENDFOR»'''
		}	
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
	}
	
	public static class AtomicChangeTypeRepresentation extends ChangeTypeRepresentation {
		protected Class<?> changeType;
		protected Class<?> affectedElementClass;
		protected Class<?> affectedValueClass;
		protected EStructuralFeature affectedFeature;
		
		protected new(Class<?> changeType, Class<?> affectedElementClass, Class<?> affectedValueClass, EStructuralFeature affectedFeature) {
			this.changeType = changeType;
			this.affectedElementClass = affectedElementClass;
			this.affectedValueClass = affectedValueClass;
			this.affectedFeature = affectedFeature;
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
	}
	

	public static def dispatch ChangeTypeRepresentation generateEChange(Trigger trigger) {
		return new AtomicChangeTypeRepresentation(EChange, null, null, null);
	}
	
	public static def dispatch ChangeTypeRepresentation generateEChange(ModelAttributeChange modelAttributeChange) {
		val clazz = switch (modelAttributeChange) {
			ModelAttributeInsertedChange: 
				AttributePackage.Literals.INSERT_EATTRIBUTE_VALUE
			ModelAttributeRemovedChange:
				AttributePackage.Literals.REMOVE_EATTRIBUTE_VALUE
			ModelAttributeReplacedChange:
				AttributePackage.Literals.REPLACE_SINGLE_VALUED_EATTRIBUTE
		}
		val affectedEObject = modelAttributeChange.feature.metaclass.instanceClass;
		val affectedValue = modelAttributeChange.feature.feature.EType.instanceClass;
		val affectedFeature = modelAttributeChange.feature.feature;
		return new AtomicChangeTypeRepresentation(clazz.instanceClass, affectedEObject, affectedValue, affectedFeature);
	}
			
	public static def dispatch ChangeTypeRepresentation generateEChange(ModelElementChange modelElementChange) {
		if (modelElementChange?.changeType == null) {
			return new AtomicChangeTypeRepresentation(EChange, null, null, null);
		}
		return generateEChange(modelElementChange.changeType, modelElementChange.elementType?.metaclass)
	}	
	
	public static def dispatch AtomicChangeTypeRepresentation generateEChange(ElementRootChangeType modelElementChange, EClass elementClass) {
		val clazz = switch (modelElementChange) {
			ElementInsertionAsRootChangeType: 
				RootPackage.Literals.INSERT_ROOT_EOBJECT
			ElementRemovalAsRootChangeType:
				RootPackage.Literals.REMOVE_ROOT_EOBJECT
		} 
		val affectedEObject = null;
		val affectedValue = if (elementClass != null) elementClass.instanceClass else EObject;
		return new AtomicChangeTypeRepresentation(clazz.instanceClass, affectedEObject, affectedValue, null);
	}
	
	public static def dispatch AtomicChangeTypeRepresentation generateEChange(ElementFeatureChangeType modelElementChange, EClass elementClass) {
		val clazz = switch (modelElementChange) {
			ElementInsertionInListChangeType:
				ReferencePackage.Literals.INSERT_EREFERENCE
			ElementRemovalFromListChangeType:
				ReferencePackage.Literals.REMOVE_EREFERENCE
			ElementReplacementChangeType:
				ReferencePackage.Literals.REPLACE_SINGLE_VALUED_EREFERENCE
		}
		val affectedEObject = modelElementChange.feature.metaclass.instanceClass;
		val affectedValue = if (elementClass != null) elementClass.instanceClass else modelElementChange.feature.feature.EType.instanceClass;
		val affectedFeature = modelElementChange.feature.feature; 
		return new AtomicChangeTypeRepresentation(clazz.instanceClass, affectedEObject, affectedValue, affectedFeature);
	}
	
	public static def dispatch AtomicChangeTypeRepresentation generateEChange(ElementExistenceChangeType modelElementChange, EClass elementClass) {
		val clazz = switch (modelElementChange) {
			ElementCreationChangeType: 
				EobjectPackage.Literals.CREATE_EOBJECT
			ElementDeletionChangeType:
				EobjectPackage.Literals.DELETE_EOBJECT
		}
		val affectedEObject = if (elementClass != null) elementClass.instanceClass else EObject;
		val affectedValue = null; 
		return new AtomicChangeTypeRepresentation(clazz.instanceClass, affectedEObject, affectedValue, null);
	}
	
	public static def dispatch CompoundChangeTypRepresentation generateEChange(ElementCreationAndInsertionChangeType modelElementChange, EClass elementClass) {
		val clazz = if (modelElementChange.insertChange instanceof ElementInsertionAsRootChangeType) {
				CompoundPackage.Literals.CREATE_AND_INSERT_ROOT
			} else {
				CompoundPackage.Literals.CREATE_AND_INSERT_NON_ROOT
			}
		val existenceChange = generateEChange(modelElementChange.createChange, elementClass) as AtomicChangeTypeRepresentation;
		val usageChange = generateEChange(modelElementChange.insertChange, elementClass) as AtomicChangeTypeRepresentation;
		return new CompoundChangeTypRepresentation(clazz.instanceClass, existenceChange, usageChange);
	}
	
	public static def dispatch CompoundChangeTypRepresentation generateEChange(ElementDeletionAndRemovalChangeType modelElementChange, EClass elementClass) {
		val clazz = if (modelElementChange.removeChange instanceof ElementRemovalAsRootChangeType) {
				CompoundPackage.Literals.REMOVE_AND_DELETE_ROOT
			} else {
				CompoundPackage.Literals.REMOVE_AND_DELETE_NON_ROOT
			}
		val existenceChange = generateEChange(modelElementChange.deleteChange, elementClass) as AtomicChangeTypeRepresentation;
		val usageChange = generateEChange(modelElementChange.removeChange, elementClass) as AtomicChangeTypeRepresentation;
		return new CompoundChangeTypRepresentation(clazz.instanceClass, existenceChange, usageChange);
	}

	public static def dispatch CompoundChangeTypRepresentation generateEChange(ElementDeletionAndCreationAndReplacementChangeType modelElementChange, EClass elementClass) {
		val clazz = CompoundPackage.Literals.CREATE_AND_REPLACE_AND_DELETE_NON_ROOT;
		val existenceChange = generateEChange(modelElementChange.createChange, elementClass) as AtomicChangeTypeRepresentation;
		val usageChange = generateEChange(modelElementChange.replacedChange, elementClass) as AtomicChangeTypeRepresentation;
		return new CompoundChangeTypRepresentation(clazz.instanceClass, existenceChange, usageChange);
	}
	
}
