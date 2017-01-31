package tools.vitruv.dsls.reactions.codegen.changetyperepresentation

import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtend2.lib.StringConcatenationClient
import java.util.List
import tools.vitruv.dsls.reactions.codegen.helper.AccessibleElement

public class AtomicChangeTypeRepresentation extends ChangeTypeRepresentation {
	private static final String affectedEObjectAttribute = "affectedEObject";
	private static final String affectedFeatureAttribute = "affectedFeature";
	private static final String oldValueAttribute = "oldValue";
	private static final String newValueAttribute = "newValue";

	protected Class<?> changeType;
	protected Class<?> affectedElementClass;
	protected Class<?> affectedValueClass;
	protected boolean hasOldValue;
	protected boolean hasNewValue;
	protected EStructuralFeature affectedFeature;

	protected new(Class<?> changeType, Class<?> affectedElementClass, Class<?> affectedValueClass, boolean hasOldValue,
		boolean hasNewValue, EStructuralFeature affectedFeature) {
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
				IF param.isPrimitiveType»«param.getPrimitiveType»«ELSE»«param»«ENDIF»«ENDFOR»'''
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

	public override StringConcatenationClient getRelevantChangeAssignmentCode(String originalChangeVariableName,
		boolean isOriginalVariableUntyped, String typedChangeVariableName) {
		val typedRelevantChangeString = relevantChangeTypeRepresentation.
			typedChangeTypeRepresentation;
		return '''
			«typedRelevantChangeString» «typedChangeVariableName» = «IF isOriginalVariableUntyped»(«typedChangeTypeRepresentation»)«ENDIF»«originalChangeVariableName»;
		'''
	}

}
