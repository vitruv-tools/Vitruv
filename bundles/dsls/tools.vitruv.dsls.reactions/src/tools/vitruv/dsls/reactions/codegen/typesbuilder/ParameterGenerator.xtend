package tools.vitruv.dsls.reactions.codegen.typesbuilder

import org.eclipse.xtext.xbase.jvmmodel.JvmTypeReferenceBuilder
import org.eclipse.xtext.common.types.JvmFormalParameter
import org.eclipse.emf.ecore.EObject
import java.util.ArrayList
import org.eclipse.xtext.common.types.JvmTypeReference
import tools.vitruv.framework.userinteraction.UserInteractor
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState
import org.eclipse.emf.ecore.EClass
import tools.vitruv.dsls.reactions.language.toplevelelements.ReactionsSegment
import tools.vitruv.dsls.reactions.language.inputTypes.InputTypesPackage
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.dsls.common.elements.MetaclassReference
import static extension tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageHelper.*;
import static extension tools.vitruv.dsls.reactions.codegen.helper.ClassNamesGenerators.*;
import tools.vitruv.dsls.reactions.codegen.helper.AccessibleElement
import static tools.vitruv.dsls.reactions.codegen.ReactionsLanguageConstants.*;
import tools.vitruv.dsls.reactions.language.toplevelelements.NamedJavaElementReference
import tools.vitruv.dsls.common.elements.NamedMetaclassReference

class ParameterGenerator {
	static val MISSING_PARAMETER_NAME = "/* Name missing */"
	
	protected final extension JvmTypeReferenceBuilder _typeReferenceBuilder;
	protected final extension JvmTypesBuilderWithoutAssociations _typesBuilder;	
	
	new (JvmTypeReferenceBuilder typeReferenceBuilder, JvmTypesBuilderWithoutAssociations typesBuilder) {
		_typeReferenceBuilder = typeReferenceBuilder;
		_typesBuilder = typesBuilder;
	}
	
	def JvmFormalParameter generateModelElementParameter(EObject parameterContext, MetaclassReference metaclassReference, String elementName) {
		if (metaclassReference?.metaclass !== null) {
			return parameterContext.generateParameter(elementName, typeRef(metaclassReference.javaClassName))
		}	
		return null;
	}
	
	def JvmFormalParameter generateRoutinesFacadeParameter(EObject parameterContext, ReactionsSegment reactionsSegment) {
		return generateParameter(parameterContext, ROUTINES_FACADE_PARAMETER_NAME, typeRef(reactionsSegment.routinesFacadeClassNameGenerator.qualifiedName));
	}
	
	def JvmFormalParameter generateReactionExecutionStateParameter(EObject parameterContext) {
		return generateParameter(parameterContext, REACTION_EXECUTION_STATE_PARAMETER_NAME, ReactionExecutionState);
	}
	
	def JvmFormalParameter generateUserInteractorParameter(EObject parameterContext) {
		return generateParameter(parameterContext, USER_INTERACTING_PARAMETER_NAME, UserInteractor);
	}
	
	def generateParameter(EObject context, String parameterName, JvmTypeReference parameterType) {
		if (parameterType === null) {
			return null;
		}
		return context.toParameter(parameterName, parameterType);
	}

		
	def generateParameterFromClasses(EObject context, String parameterName, Class<?> parameterClass, Iterable<String> typeParameterClasses) {
		return generateParameter(context, parameterName, parameterClass, typeParameterClasses);
	}
	
	def generateParameter(EObject context, String parameterName, Class<?> parameterClass, String... typeParameterClassNames) {
		if (parameterClass === null) {
			return null;
		}
		val typeParameters = new ArrayList<JvmTypeReference>(typeParameterClassNames.size);
		for (typeParameterClassName : typeParameterClassNames.filter[!nullOrEmpty]) {
			typeParameters.add(typeRef(typeParameterClassName));
		}		
		val changeType = typeRef(parameterClass, typeParameters);
		return context.toParameter(parameterName, changeType);
	}
	
	def Iterable<AccessibleElement> getInputElements(EObject contextObject, Iterable<NamedMetaclassReference> metaclassReferences, Iterable<NamedJavaElementReference> javaElements) {
		return metaclassReferences.map[new AccessibleElement(it.name ?: MISSING_PARAMETER_NAME, it.metaclass?.mappedInstanceClassCanonicalName)]
			+ javaElements.map[new AccessibleElement(it.name ?: MISSING_PARAMETER_NAME, it.type?.qualifiedName)];
	}
	
	def Iterable<JvmFormalParameter> generateMethodInputParameters(EObject contextObject, Iterable<NamedMetaclassReference> metaclassReferences, Iterable<NamedJavaElementReference> javaElements) {
		return contextObject.generateMethodInputParameters(contextObject.getInputElements(metaclassReferences, javaElements));
	}
	
	def Iterable<JvmFormalParameter> generateMethodInputParameters(EObject contextObject, Iterable<AccessibleElement> elements) {
		elements.map[toParameter(contextObject, it.name, it.generateTypeRef(_typeReferenceBuilder))]
	}
	
	private def getMappedInstanceClassCanonicalName(EClass eClass) {
		switch eClass {
			case InputTypesPackage.Literals.STRING: String.name
			case InputTypesPackage.Literals.INTEGER: Integer.name
			case InputTypesPackage.Literals.LONG: Long.name
			case InputTypesPackage.Literals.SHORT: Short.name
			case InputTypesPackage.Literals.BOOLEAN: Boolean.name
			case InputTypesPackage.Literals.CHARACTER: Character.name
			case InputTypesPackage.Literals.BYTE: Byte.name
			case InputTypesPackage.Literals.FLOAT: Float.name
			case InputTypesPackage.Literals.DOUBLE: Double.name
			default: eClass.javaClassName
		}
	}
	
	def JvmFormalParameter generateUntypedChangeParameter(EObject parameterContext) {
		return parameterContext.generateParameter(CHANGE_PARAMETER_NAME, EChange);
	}
	
}
							