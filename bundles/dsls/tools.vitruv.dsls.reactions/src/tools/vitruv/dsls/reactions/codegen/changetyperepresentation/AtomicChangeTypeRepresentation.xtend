package tools.vitruv.dsls.reactions.codegen.changetyperepresentation

import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtend2.lib.StringConcatenationClient
import tools.vitruv.dsls.reactions.codegen.helper.AccessibleElement
import static tools.vitruv.dsls.reactions.codegen.ReactionsLanguageConstants.*
import tools.vitruv.framework.change.echange.feature.single.ReplaceSingleValuedFeatureEChange
import org.eclipse.xtend.lib.annotations.Accessors

class AtomicChangeTypeRepresentation extends ChangeTypeRepresentation {
	static final String TEMPORARY_TYPED_CHANGE_NAME = "_localTypedChange";
	
	protected final Class<?> changeType;
	protected final String affectedElementClassCanonicalName
	protected final String affectedValueClassCanonicalName
	protected final boolean hasOldValue;
	protected final boolean hasNewValue;
	protected final boolean hasIndex;
	protected final EStructuralFeature affectedFeature;
	@Accessors(PUBLIC_GETTER)
	protected final String name;
	
	protected new(String name, Class<?> changeType, String affectedElementClassCanonicalName, String affectedValueClassCanonicalName, boolean hasOldValue,
		boolean hasNewValue, EStructuralFeature affectedFeature, boolean hasIndex) {
		this.name = name;
		this.changeType = changeType
		this.affectedElementClassCanonicalName = affectedElementClassCanonicalName.mapToNonPrimitiveType
		this.affectedValueClassCanonicalName = affectedValueClassCanonicalName.mapToNonPrimitiveType
		this.affectedFeature = affectedFeature
		this.hasOldValue = hasOldValue
		this.hasNewValue = hasNewValue
		this.hasIndex = hasIndex
	}

	override Class<?> getChangeType() {
		return changeType;
	}

	def getAffectedElementClass() {
		affectedElementClassCanonicalName;
	}

	def getAffectedValueClass() {
		affectedValueClassCanonicalName
	}

	def EStructuralFeature getAffectedFeature() {
		return affectedFeature;
	}

	def boolean hasAffectedElement() {
		return affectedElementClass !== null;
	}
	
	def boolean hasAffectedFeature() {
		return affectedFeature !== null;
	}
	
	def boolean hasOldValue() {
		return hasOldValue;
	}

	def boolean hasNewValue() {
		return hasNewValue;
	}
	
	def boolean hasIndex() {
		return hasIndex;
	}

	override getGenericTypeParameters() {
		#[affectedElementClassCanonicalName, affectedValueClassCanonicalName].filterNull
	}

	override StringConcatenationClient getUntypedChangeTypeRepresentation() {
		return '''«changeType»'''
	}

	def StringConcatenationClient getSetFieldCode(String parameterName) '''
		this.«name» = («typedChangeTypeRepresentation») «parameterName»;
	'''
	
	def StringConcatenationClient getResetFieldCode() '''
		«name» = null;
	'''
	
	def AccessibleElement getAccessibleElement() {
		return new AccessibleElement(name, changeType.name, genericTypeParameters);
	}
	
	def StringConcatenationClient generateCheckMethodBody(String parameterName) '''
		if («parameterName» instanceof «changeTypeRepresentationWithWildcards») {
			«typedChangeTypeRepresentation» «TEMPORARY_TYPED_CHANGE_NAME» = («typedChangeTypeRepresentation») «parameterName»;
			«TEMPORARY_TYPED_CHANGE_NAME.generateElementChecks»
			«parameterName.setFieldCode»
			return true;
		}
		
		return false;
	'''
	
	private def StringConcatenationClient generateElementChecks(String parameterName) '''
		«IF hasAffectedElement»
			if (!(«parameterName».getAffectedEObject() instanceof «affectedElementClass»)) {
				return false;
			}
		«ENDIF»
		«IF hasAffectedFeature»
			if (!«parameterName».getAffectedFeature().getName().equals("«affectedFeature.name»")) {
				return false;
			}
		«ENDIF»
		«IF hasOldValue»
			if («IF ReplaceSingleValuedFeatureEChange.isAssignableFrom(changeType)»«parameterName».isFromNonDefaultValue() && «
				ENDIF»!(«parameterName».getOldValue() instanceof «affectedValueClass»)) {
				return false;
			}
		«ENDIF»
		«IF hasNewValue»
			if («IF ReplaceSingleValuedFeatureEChange.isAssignableFrom(changeType)»«parameterName».isToNonDefaultValue() && «
				ENDIF»!(«parameterName».getNewValue() instanceof «affectedValueClass»)) {
				return false;
			}
		«ENDIF»
	'''

	def Iterable<AccessibleElement> generatePropertiesParameterList() {
		val result = <AccessibleElement>newArrayList();
		result.add(new AccessibleElement(name, changeType));
		if (affectedElementClass !== null) {
			result.add(new AccessibleElement(CHANGE_AFFECTED_ELEMENT_ATTRIBUTE, affectedElementClass));
		}
		if (affectedFeature !== null) {
			result.add(new AccessibleElement(CHANGE_AFFECTED_FEATURE_ATTRIBUTE, affectedFeature.eClass.instanceClass));
		}
		if (hasOldValue) {
			result.add(new AccessibleElement(CHANGE_OLD_VALUE_ATTRIBUTE, affectedValueClass));
		}
		if (hasNewValue) {
			result.add(new AccessibleElement(CHANGE_NEW_VALUE_ATTRIBUTE, affectedValueClass));
		}
		if (hasIndex) {
			result.add(new AccessibleElement(CHANGE_INDEX_ATTRIBUTE, int));
		}
		return result;
	}

	def StringConcatenationClient generatePropertiesAssignmentCode() {
		'''
			«IF affectedElementClass !== null»
				«affectedElementClass» «CHANGE_AFFECTED_ELEMENT_ATTRIBUTE» = «name».get«CHANGE_AFFECTED_ELEMENT_ATTRIBUTE.toFirstUpper»();
			«ENDIF»
			«IF affectedFeature !== null»
				«affectedFeature.eClass.instanceClass» «CHANGE_AFFECTED_FEATURE_ATTRIBUTE» = «name».get«CHANGE_AFFECTED_FEATURE_ATTRIBUTE.toFirstUpper»();
			«ENDIF»
			«IF hasOldValue»
				«affectedValueClass» «CHANGE_OLD_VALUE_ATTRIBUTE» = «name».get«CHANGE_OLD_VALUE_ATTRIBUTE.toFirstUpper»();
			«ENDIF»
			«IF hasNewValue»
				«affectedValueClass» «CHANGE_NEW_VALUE_ATTRIBUTE» = «name».get«CHANGE_NEW_VALUE_ATTRIBUTE.toFirstUpper»();
			«ENDIF»
			«IF hasIndex»
				int «CHANGE_INDEX_ATTRIBUTE» = «name».get«CHANGE_INDEX_ATTRIBUTE.toFirstUpper»();
			«ENDIF»
		'''
	}

}
