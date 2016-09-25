package tools.vitruv.dsls.response.jvmmodel.classgenerators

import tools.vitruv.dsls.response.jvmmodel.classgenerators.ClassGenerator
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.common.types.JvmVisibility
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving
import org.eclipse.xtext.common.types.JvmOperation
import tools.vitruv.dsls.response.responseLanguage.CodeBlock
import tools.vitruv.dsls.response.environment.SimpleTextXBlockExpression
import tools.vitruv.dsls.response.responseLanguage.Taggable
import tools.vitruv.dsls.response.responseLanguage.RetrieveModelElement
import tools.vitruv.dsls.response.responseLanguage.ExistingElementReference
import tools.vitruv.dsls.response.responseLanguage.Matching
import org.eclipse.xtext.common.types.JvmTypeReference
import tools.vitruv.dsls.response.responseLanguage.RoutineCallBlock

class UserExecutionClassGenerator extends ClassGenerator {
	private val EObject objectMappedToClass;
	private val String qualifiedClassName;
	private var int counterGetTagMethods;
	private var int counterGetElementMethods;
	private var int counterGetRetrieveTagMethods;
	private var int counterCallRoutineMethods;
	
	protected static class AccessibleElement {
		public val String name;
		public val JvmTypeReference type;
		public new(String name, JvmTypeReference type) {
			this.name = name;
			this.type = type;
		}
	}
	
	new(TypesBuilderExtensionProvider typesBuilderExtensionProvider, EObject objectMappedToClass, String qualifiedClassName) {
		super(typesBuilderExtensionProvider)
		this.objectMappedToClass = objectMappedToClass;
		this.qualifiedClassName = qualifiedClassName;
		this.counterGetTagMethods = 1;
		this.counterGetElementMethods = 1;
		this.counterGetRetrieveTagMethods = 1;
		this.counterCallRoutineMethods = 1;
	}
	
	public def String getQualifiedClassName() {
		return qualifiedClassName;
	}
	
	override generateClass() {
		return objectMappedToClass.toClass(qualifiedClassName) [
			visibility = JvmVisibility.PRIVATE;
			static = true;
			superTypes += typeRef(AbstractEffectRealization.UserExecution);
			members += toConstructor() [
				val responseExecutionStateParameter = generateResponseExecutionStateParameter();
				val calledByParameter = generateParameter("calledBy", typeRef(CallHierarchyHaving));
				parameters += responseExecutionStateParameter;
				parameters += calledByParameter;
				body = '''
					super(«parameters.get(0).name»);
				'''
			]
			members += generatedMethods;
		]
	}
	
	protected def generateAccessibleElementsParameters(EObject sourceObject, Iterable<AccessibleElement> accessibleElements) {
		accessibleElements.map[sourceObject.toParameter(name, type)];
	}

	protected def JvmOperation generateUpdateElementMethod(String elementName, CodeBlock codeBlock, Iterable<AccessibleElement> accessibleElements) {
		return codeBlock.getOrGenerateMethod("update" + elementName.toFirstUpper + "Element", typeRef(Void.TYPE)) [
			parameters += generateAccessibleElementsParameters(accessibleElements);
			val code = codeBlock.code;
			if (code instanceof SimpleTextXBlockExpression) {
				body = code.text;
			} else {
				body = code;
			}
		] 
	}
	
	protected def JvmOperation generateMethodGetRetrieveTag(Taggable taggable, Iterable<AccessibleElement> accessibleElements) {
		val methodName = "getRetrieveTag" + counterGetRetrieveTagMethods++;
		
		return taggable.tag.getOrGenerateMethod(methodName, typeRef(String)) [
			parameters += generateAccessibleElementsParameters(accessibleElements);
			body = taggable.tag.code;
		];		
	}
	
	protected def JvmOperation generateMethodGetCreateTag(Taggable taggable, Iterable<AccessibleElement> accessibleElements) {
		val methodName = "getTag" + counterGetTagMethods++;
		
		return taggable.tag.getOrGenerateMethod(methodName, typeRef(String)) [
			parameters += generateAccessibleElementsParameters(accessibleElements);
			body = taggable.tag.code;
		];		
	}
	
	protected def generateMethodCorrespondencePrecondition(RetrieveModelElement elementRetrieve, Iterable<AccessibleElement> accessibleElements) {
		val methodName = "getCorrespondingModelElementsPrecondition" + elementRetrieve.element.name.toFirstUpper;
		return elementRetrieve.precondition.getOrGenerateMethod(methodName, typeRef(Boolean.TYPE)) [
			val elementParameter = generateModelElementParameter(elementRetrieve.element);
			parameters += generateAccessibleElementsParameters(accessibleElements);
			parameters += elementParameter;
			body = elementRetrieve.precondition.code;
		];
	}
	
	protected def generateMethodGetCorrespondenceSource(RetrieveModelElement elementRetrieve, Iterable<AccessibleElement> accessibleElements) {
		val methodName = "getCorrepondenceSource" + elementRetrieve.element.name.toFirstUpper;
		
		return elementRetrieve.correspondenceSource.getOrGenerateMethod(methodName, typeRef(EObject)) [
			parameters += generateAccessibleElementsParameters(accessibleElements);
			val correspondenceSourceBlock = elementRetrieve.correspondenceSource?.code;
			if (correspondenceSourceBlock instanceof SimpleTextXBlockExpression) {
				body = correspondenceSourceBlock.text;
			} else {
				body = correspondenceSourceBlock;
			}
		];
	}
	
	protected def generateMethodGetElement(ExistingElementReference reference, Iterable<AccessibleElement> accessibleElements) {
		val methodName = "getElement" + counterGetElementMethods++;
		
		return reference.code.getOrGenerateMethod(methodName, typeRef(EObject)) [
			parameters += generateAccessibleElementsParameters(accessibleElements);
			val correspondenceSourceBlock = reference?.code;
			if (correspondenceSourceBlock instanceof SimpleTextXBlockExpression) {
				body = correspondenceSourceBlock.text;
			} else {
				body = correspondenceSourceBlock;
			}
		];
	}
	
	protected def generateMethodMatcherPrecondition(Matching matching, Iterable<AccessibleElement> accessibleElements) {
		if (matching?.condition == null) {
			return null;
		}
		val methodName = "checkMatcherPrecondition";
		return matching.condition.getOrGenerateMethod(methodName, typeRef(Boolean.TYPE)) [
			parameters += generateAccessibleElementsParameters(accessibleElements);
			body = matching.condition.code;
		];
	}
	
	protected def JvmOperation generateMethodCallRoutine(RoutineCallBlock routineCall, Iterable<AccessibleElement> accessibleElements, JvmTypeReference facadeClassTypeReference) {
		if (routineCall.code == null) {
			return null;
		}
		val methodName = "callRoutine" + counterCallRoutineMethods++;
		val codeBlock = routineCall.code;
		return codeBlock.getOrGenerateMethod(methodName, typeRef(Void.TYPE)) [
			parameters += generateAccessibleElementsParameters(accessibleElements);
			val facadeParam = toParameter("_routinesFacade", facadeClassTypeReference);
			facadeParam.annotations += annotationRef(Extension);
			parameters += facadeParam
			if (codeBlock instanceof SimpleTextXBlockExpression) {
				body = codeBlock.text;
			} else {
				body = codeBlock;
			}
		]
	}
}