package tools.vitruv.dsls.reactions.codegen.classgenerators

import org.eclipse.xtext.common.types.JvmGenericType
import java.util.Map
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.common.types.JvmTypeReference
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1
import java.util.HashMap
import tools.vitruv.dsls.reactions.codegen.helper.AccessibleElement
import tools.vitruv.dsls.reactions.codegen.typesbuilder.TypesBuilderExtensionProvider

/**
 * JVM Model inference should happen in two phases:
 * <ol>
 * <li>Create empty classes so they can be found when linking</li>
 * <li>After linking is done, generate the bodies</li>
 * </ol>
 * Therefore, the {@link ClassGenerator}s provide separated methods 
 * {@link ClassGenerator#generateEmptyClass() generateEmptyClass}
 * and {@link ClassGenerator#generateBody(JvmGenericType) generateBody} for those steps.
 */
abstract class ClassGenerator extends TypesBuilderExtensionProvider implements IJvmOperationRegistry {
	Map<String, JvmOperation> methodMap;

	protected def generateAccessibleElementsParameters(EObject sourceObject,
		Iterable<AccessibleElement> accessibleElements) {
			sourceObject.generateMethodInputParameters(accessibleElements)
	}

	new(TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		typesBuilderExtensionProvider.copyBuildersTo(this);
		this.methodMap = new HashMap<String, JvmOperation>();
	}

	def JvmGenericType generateEmptyClass()

	def JvmGenericType generateBody() {}

	override JvmOperation getOrGenerateMethod(EObject contextObject, String methodName, JvmTypeReference returnType,
		Procedure1<? super JvmOperation> initializer) {
		if (!methodMap.containsKey(methodName)) {
			val operation = contextObject.toMethod(methodName, returnType, initializer);
			methodMap.put(operation.simpleName, operation);
		}

		return methodMap.get(methodName);
	}

	override JvmOperation getOrGenerateMethod(String methodName, JvmTypeReference returnType,
		Procedure1<? super JvmOperation> initializer) {
		if (!methodMap.containsKey(methodName)) {
			val operation = generateUnassociatedMethod(methodName, returnType, initializer);
			methodMap.put(operation.simpleName, operation);
		}

		return methodMap.get(methodName);
	}

	protected def Iterable<JvmOperation> getGeneratedMethods() {
		return methodMap.values;
	}
	
	protected def getCommentWithoutMarkers(String documentation) {
		if (documentation !== null && documentation.length > 4) {
			val withoutMultilineCommentMarkers = documentation.replaceAll("\\n \\* ","\\n")
			return withoutMultilineCommentMarkers.substring(2,withoutMultilineCommentMarkers.length-2)
		} else {
			return documentation
		}
	}
}
