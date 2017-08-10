package tools.vitruv.dsls.reactions.codegen.changetyperepresentation

import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtend2.lib.StringConcatenationClient
import tools.vitruv.dsls.reactions.codegen.helper.AccessibleElement
import static tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageConstants.*

public class AtomicChangeTypeRepresentation extends ChangeTypeRepresentation {
	public static final String changeName = "change";

	protected final Class<?> changeType;
	protected final String affectedElementClassCanonicalName
	protected final String affectedValueClassCanonicalName
	protected final boolean hasOldValue;
	protected final boolean hasNewValue;
	protected final EStructuralFeature affectedFeature;

	protected new(Class<?> changeType, String affectedElementClassCanonicalName, String affectedValueClassCanonicalName, boolean hasOldValue,
		boolean hasNewValue, EStructuralFeature affectedFeature) {
		this.changeType = changeType
		this.affectedElementClassCanonicalName = affectedElementClassCanonicalName.mapToNonPrimitiveType
		this.affectedValueClassCanonicalName = affectedValueClassCanonicalName.mapToNonPrimitiveType
		this.affectedFeature = affectedFeature
		this.hasOldValue = hasOldValue
		this.hasNewValue = hasNewValue
	}

	public override Class<?> getChangeType() {
		return changeType;
	}

	def getAffectedElementClass() {
		affectedElementClassCanonicalName;
	}

	def getAffectedValueClass() {
		affectedValueClassCanonicalName
	}

	public def EStructuralFeature getAffectedFeature() {
		return affectedFeature;
	}

	public def boolean hasAffectedElement() {
		return affectedElementClass !== null;
	}
	
	public def boolean hasAffectedFeature() {
		return affectedFeature !== null;
	}
	
	public def boolean hasOldValue() {
		return hasOldValue;
	}

	public def boolean hasNewValue() {
		return hasNewValue;
	}

	public override getGenericTypeParameters() {
		#[affectedElementClassCanonicalName, affectedValueClassCanonicalName].filterNull
	}

	public override StringConcatenationClient getUntypedChangeTypeRepresentation() {
		return '''«changeType»'''
	}

	override getRelevantAtomicChangeTypeRepresentation() {
		return this;
	}

	public def Iterable<AccessibleElement> generatePropertiesParameterList() {
		val result = <AccessibleElement>newArrayList();
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
		if (result.empty) {
			result.add(new AccessibleElement(changeName, changeType));
		}
		return result;
	}

	public def StringConcatenationClient generatePropertiesAssignmentCode(String typedChangeVariableName) {
		'''
			«IF affectedElementClass !== null»
				«affectedElementClass» «CHANGE_AFFECTED_ELEMENT_ATTRIBUTE» = «typedChangeVariableName».get«CHANGE_AFFECTED_ELEMENT_ATTRIBUTE.toFirstUpper»();
			«ENDIF»
			«IF affectedFeature !== null»
				«affectedFeature.eClass.instanceClass» «CHANGE_AFFECTED_FEATURE_ATTRIBUTE» = «typedChangeVariableName».get«CHANGE_AFFECTED_FEATURE_ATTRIBUTE.toFirstUpper»();
			«ENDIF»
			«IF hasOldValue»
				«affectedValueClass» «CHANGE_OLD_VALUE_ATTRIBUTE» = «typedChangeVariableName».get«CHANGE_OLD_VALUE_ATTRIBUTE.toFirstUpper»();
			«ENDIF»
			«IF hasNewValue»
				«affectedValueClass» «CHANGE_NEW_VALUE_ATTRIBUTE» = «typedChangeVariableName».get«CHANGE_NEW_VALUE_ATTRIBUTE.toFirstUpper»();
			«ENDIF»
		'''
	}

	public override StringConcatenationClient getRelevantChangeAssignmentCode(String untypedChangeVariableName,
			String typedChangeVariableName) {
		val typedRelevantChangeString = relevantAtomicChangeTypeRepresentation.
			typedChangeTypeRepresentation;
		return '''
			«typedRelevantChangeString» «typedChangeVariableName» = («typedChangeTypeRepresentation»)«untypedChangeVariableName»;
		'''
	}

}
