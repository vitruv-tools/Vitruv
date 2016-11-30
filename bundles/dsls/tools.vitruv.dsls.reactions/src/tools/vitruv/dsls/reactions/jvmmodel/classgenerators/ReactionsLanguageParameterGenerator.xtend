package tools.vitruv.dsls.reactions.jvmmodel.classgenerators

import org.eclipse.xtext.xbase.jvmmodel.JvmTypeReferenceBuilder
import org.eclipse.xtext.common.types.JvmFormalParameter
import org.eclipse.emf.ecore.EObject
import java.util.ArrayList
import org.eclipse.xtext.common.types.JvmTypeReference
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.dsls.mirbase.mirBase.NamedJavaElement
import tools.vitruv.dsls.reactions.reactionsLanguage.Trigger
import tools.vitruv.dsls.reactions.reactionsLanguage.ConcreteModelElementChange
import static extension tools.vitruv.dsls.reactions.helper.EChangeHelper.*;
import java.util.List
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState
import org.eclipse.emf.ecore.EClass
import tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.InputTypesPackage
import tools.vitruv.dsls.reactions.jvmmodel.JvmTypesBuilderWithoutAssociations
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.command.ChangePropagationResult
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import static extension tools.vitruv.dsls.reactions.helper.ReactionsLanguageHelper.*;
import tools.vitruv.dsls.mirbase.mirBase.NamedMetaclassReference

class ReactionsLanguageParameterGenerator {
	package static val CHANGE_PARAMETER_NAME = "change";
	package static val BLACKBOARD_PARAMETER_NAME = "blackboard";
	package static val TRANSFORMATION_RESULT_PARAMETER_NAME = "transformationResult";
	package static val USER_INTERACTING_PARAMETER_NAME = "userInteracting";
	package static val REACTION_EXECUTION_STATE_PARAMETER_NAME = "reactionExecutionState";
	
	protected final extension JvmTypeReferenceBuilder _typeReferenceBuilder;
	protected final extension JvmTypesBuilderWithoutAssociations _typesBuilder;	
	
	new (JvmTypeReferenceBuilder typeReferenceBuilder, tools.vitruv.dsls.reactions.jvmmodel.JvmTypesBuilderWithoutAssociations typesBuilder) {
		_typeReferenceBuilder = typeReferenceBuilder;
		_typesBuilder = typesBuilder;
	}
	
	protected def JvmFormalParameter generateModelElementParameter(EObject parameterContext, MetaclassReference metaclassReference, String elementName) {
		if (metaclassReference?.metaclass != null) {
			return parameterContext.generateParameter(elementName, metaclassReference.javaClass);
		}	
		return null;
	}
	
	protected def JvmFormalParameter generateReactionExecutionStateParameter(EObject parameterContext) {
		return generateParameter(parameterContext, REACTION_EXECUTION_STATE_PARAMETER_NAME, ReactionExecutionState);
	}
	
	protected def JvmFormalParameter generateTransformationResultParameter(EObject parameterContext) {
		return generateParameter(parameterContext, TRANSFORMATION_RESULT_PARAMETER_NAME, ChangePropagationResult);
	}
	
	protected def JvmFormalParameter generateUserInteractingParameter(EObject parameterContext) {
		return generateParameter(parameterContext, USER_INTERACTING_PARAMETER_NAME, UserInteracting);
	}
	
	protected def generateParameter(EObject context, String parameterName, JvmTypeReference parameterType) {
		if (parameterType == null) {
			return null;
		}
		return context.toParameter(parameterName, parameterType);
	}
	
	protected def generateParameter(EObject context, String parameterName, Class<?> parameterClass, String... typeParameterClassNames) {
		if (parameterClass == null) {
			return null;
		}
		val typeParameters = new ArrayList<JvmTypeReference>(typeParameterClassNames.size);
		for (typeParameterClassName : typeParameterClassNames.filter[!nullOrEmpty]) {
			typeParameters.add(typeRef(typeParameterClassName));
		}		
		val changeType = typeRef(parameterClass, typeParameters);
		return context.toParameter(parameterName, changeType);
	}
	
	protected def Iterable<JvmFormalParameter> generateMethodInputParameters(EObject contextObject, Iterable<NamedMetaclassReference> metaclassReferences, Iterable<NamedJavaElement> javaElements) {
		return metaclassReferences.map[
			generateParameter(contextObject, it.name, it.metaclass.mappedInstanceClass)
		] + javaElements.map[toParameter(contextObject, it.name, it.type)];
	}
	
	private def getMappedInstanceClass(EClass eClass) {
		if (eClass.instanceClass.equals(InputTypesPackage.Literals.STRING.instanceClass)) {
			return String;
		} else if (eClass.equals(InputTypesPackage.Literals.INT)) {
			return Integer;
		}
		return eClass.instanceClass
	}
	
	protected def JvmFormalParameter generateUntypedChangeParameter(EObject parameterContext) {
		return parameterContext.generateParameter(CHANGE_PARAMETER_NAME, EChange);
	}
	
	protected def JvmFormalParameter generateChangeParameter(EObject parameterContext, Trigger trigger) {
		var List<String> changeTypeParameters = <String>newArrayList;
		if (trigger instanceof ConcreteModelElementChange) {
			changeTypeParameters = getGenericTypeParameterFQNsOfChange(trigger)			
		}
		val changeParameter = parameterContext.generateParameter(CHANGE_PARAMETER_NAME, trigger.generateEChangeInstanceClass(), changeTypeParameters);
		if (changeParameter != null) {
			return changeParameter;
		}
	}
}
							