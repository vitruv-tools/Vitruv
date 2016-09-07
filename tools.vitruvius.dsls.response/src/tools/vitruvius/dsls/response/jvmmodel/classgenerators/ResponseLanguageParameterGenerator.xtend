package tools.vitruvius.dsls.response.jvmmodel.classgenerators

import org.eclipse.xtext.xbase.jvmmodel.JvmTypeReferenceBuilder
import org.eclipse.xtext.common.types.JvmFormalParameter
import org.eclipse.emf.ecore.EObject
import java.util.ArrayList
import org.eclipse.xtext.common.types.JvmTypeReference
import tools.vitruvius.framework.userinteraction.UserInteracting
import tools.vitruvius.framework.util.command.TransformationResult
import tools.vitruvius.dsls.mirbase.mirBase.NamedJavaElement
import tools.vitruvius.dsls.response.responseLanguage.Trigger
import tools.vitruvius.dsls.response.responseLanguage.ConcreteModelElementChange
import static extension tools.vitruvius.dsls.response.helper.EChangeHelper.*;
import java.util.List
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState
import org.eclipse.emf.ecore.EClass
import tools.vitruvius.dsls.response.responseLanguage.inputTypes.InputTypesPackage
import tools.vitruvius.dsls.mirbase.mirBase.ModelElement
import tools.vitruvius.dsls.response.jvmmodel.JvmTypesBuilderWithoutAssociations
import tools.vitruvius.framework.change.echange.EChange

class ResponseLanguageParameterGenerator {
	package static val CHANGE_PARAMETER_NAME = "change";
	package static val BLACKBOARD_PARAMETER_NAME = "blackboard";
	package static val TRANSFORMATION_RESULT_PARAMETER_NAME = "transformationResult";
	package static val USER_INTERACTING_PARAMETER_NAME = "userInteracting";
	package static val RESPONSE_EXECUTION_STATE_PARAMETER_NAME = "responseExecutionState";
	
	protected final extension JvmTypeReferenceBuilder _typeReferenceBuilder;
	protected final extension JvmTypesBuilderWithoutAssociations _typesBuilder;	
	
	new (JvmTypeReferenceBuilder typeReferenceBuilder, tools.vitruvius.dsls.response.jvmmodel.JvmTypesBuilderWithoutAssociations typesBuilder) {
		_typeReferenceBuilder = typeReferenceBuilder;
		_typesBuilder = typesBuilder;
	}
	
	protected def JvmFormalParameter generateModelElementParameter(EObject parameterContext, ModelElement element) {
		if (element?.element != null) {
			return parameterContext.generateParameter(element.name, element.element.instanceClass);
		}	
		return null;
	}
	
	protected def JvmFormalParameter generateResponseExecutionStateParameter(EObject parameterContext) {
		return generateParameter(parameterContext, RESPONSE_EXECUTION_STATE_PARAMETER_NAME, ResponseExecutionState);
	}
	
	protected def JvmFormalParameter generateTransformationResultParameter(EObject parameterContext) {
		return generateParameter(parameterContext, TRANSFORMATION_RESULT_PARAMETER_NAME, TransformationResult);
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
	
	protected def Iterable<JvmFormalParameter> generateMethodInputParameters(EObject contextObject, Iterable<ModelElement> modelElements, Iterable<NamedJavaElement> javaElements) {
		return modelElements.map[
			generateParameter(contextObject, it.name, it.element.mappedInstanceClass)
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
							