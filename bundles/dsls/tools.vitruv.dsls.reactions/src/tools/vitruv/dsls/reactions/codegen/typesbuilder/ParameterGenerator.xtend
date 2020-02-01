package tools.vitruv.dsls.reactions.codegen.typesbuilder

import org.eclipse.xtext.xbase.jvmmodel.JvmTypeReferenceBuilder
import org.eclipse.xtext.common.types.JvmFormalParameter
import org.eclipse.emf.ecore.EObject
import java.util.ArrayList
import org.eclipse.xtext.common.types.JvmTypeReference
import tools.vitruv.framework.userinteraction.UserInteractor
import tools.vitruv.dsls.mirbase.mirBase.NamedJavaElement
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState
import org.eclipse.emf.ecore.EClass
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.InputTypesPackage
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import static extension tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageHelper.*;
import static extension tools.vitruv.dsls.reactions.codegen.helper.ClassNamesGenerators.*;
import tools.vitruv.dsls.mirbase.mirBase.NamedMetaclassReference
import tools.vitruv.dsls.reactions.codegen.helper.AccessibleElement
import static tools.vitruv.dsls.reactions.codegen.ReactionsLanguageConstants.*;

class ParameterGenerator {
	protected final extension JvmTypeReferenceBuilder _typeReferenceBuilder;
	protected final extension JvmTypesBuilderWithoutAssociations _typesBuilder;	
	
	new (JvmTypeReferenceBuilder typeReferenceBuilder, JvmTypesBuilderWithoutAssociations typesBuilder) {
		_typeReferenceBuilder = typeReferenceBuilder;
		_typesBuilder = typesBuilder;
	}
	
	public def JvmFormalParameter generateModelElementParameter(EObject parameterContext, MetaclassReference metaclassReference, String elementName) {
		if (metaclassReference?.metaclass !== null) {
			return parameterContext.generateParameter(elementName, typeRef(metaclassReference.javaClassName))
		}	
		return null;
	}
	
	public def JvmFormalParameter generateRoutinesFacadeParameter(EObject parameterContext, ReactionsSegment reactionsSegment) {
		return generateParameter(parameterContext, ROUTINES_FACADE_PARAMETER_NAME, typeRef(reactionsSegment.routinesFacadeClassNameGenerator.qualifiedName));
	}
	
	public def JvmFormalParameter generateReactionExecutionStateParameter(EObject parameterContext) {
		return generateParameter(parameterContext, REACTION_EXECUTION_STATE_PARAMETER_NAME, ReactionExecutionState);
	}
	
	public def JvmFormalParameter generateUserInteractorParameter(EObject parameterContext) {
		return generateParameter(parameterContext, USER_INTERACTING_PARAMETER_NAME, UserInteractor);
	}
	
	public def generateParameter(EObject context, String parameterName, JvmTypeReference parameterType) {
		if (parameterType === null) {
			return null;
		}
		return context.toParameter(parameterName, parameterType);
	}

		
	public def generateParameterFromClasses(EObject context, String parameterName, Class<?> parameterClass, Iterable<String> typeParameterClasses) {
		return generateParameter(context, parameterName, parameterClass, typeParameterClasses);
	}
	
	public def generateParameter(EObject context, String parameterName, Class<?> parameterClass, String... typeParameterClassNames) {
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
	
	public def Iterable<AccessibleElement> getInputElements(EObject contextObject, Iterable<NamedMetaclassReference> metaclassReferences, Iterable<NamedJavaElement> javaElements) {
		return metaclassReferences.map[new AccessibleElement(it.name, it.metaclass.mappedInstanceClassCanonicalName)]
			+ javaElements.map[new AccessibleElement(it.name, it.type.qualifiedName)];
	}
	
	public def Iterable<JvmFormalParameter> generateMethodInputParameters(EObject contextObject, Iterable<NamedMetaclassReference> metaclassReferences, Iterable<NamedJavaElement> javaElements) {
		return contextObject.generateMethodInputParameters(contextObject.getInputElements(metaclassReferences, javaElements));
	}
	
	public def Iterable<JvmFormalParameter> generateMethodInputParameters(EObject contextObject, Iterable<AccessibleElement> elements) {
		elements.map[toParameter(contextObject, it.name, it.generateTypeRef(_typeReferenceBuilder, _typesBuilder))]
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
	
	public def JvmFormalParameter generateUntypedChangeParameter(EObject parameterContext) {
		return parameterContext.generateParameter(CHANGE_PARAMETER_NAME, EChange);
	}
	
}
							