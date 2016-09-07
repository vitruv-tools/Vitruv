package tools.vitruvius.dsls.response.jvmmodel.classgenerators

import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.common.types.JvmVisibility
import org.eclipse.xtext.common.types.JvmOperation
import tools.vitruvius.dsls.response.environment.SimpleTextXBlockExpression
import java.io.IOException
import org.eclipse.xtend2.lib.StringConcatenationClient
import tools.vitruvius.dsls.response.responseLanguage.ExecutionCodeBlock
import org.eclipse.xtext.common.types.JvmFormalParameter
import tools.vitruvius.framework.util.bridges.CollectionBridge
import static extension tools.vitruvius.dsls.response.helper.ResponseLanguageHelper.*;
import org.eclipse.xtext.common.types.JvmMember
import org.eclipse.xtext.common.types.JvmConstructor
import static tools.vitruvius.dsls.response.helper.ResponseLanguageConstants.*;
import tools.vitruvius.dsls.response.helper.ResponseClassNamesGenerator.ClassNameGenerator
import static extension tools.vitruvius.dsls.response.helper.ResponseClassNamesGenerator.*;
import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving
import tools.vitruvius.dsls.response.responseLanguage.ExistingElementReference
import tools.vitruvius.dsls.response.responseLanguage.CreateCorrespondence
import tools.vitruvius.dsls.response.responseLanguage.Taggable
import tools.vitruvius.dsls.response.responseLanguage.Routine
import tools.vitruvius.dsls.response.responseLanguage.RetrieveModelElement
import tools.vitruvius.dsls.response.responseLanguage.CreateElement
import tools.vitruvius.dsls.response.responseLanguage.DeleteElement
import tools.vitruvius.dsls.response.responseLanguage.Matching

abstract class RoutineClassGenerator extends ClassGenerator {
	protected final Routine routine;
	protected final boolean hasExecutionBlock;
	protected extension ResponseElementsCompletionChecker _completionChecker;
	protected final Iterable<RetrieveModelElement> retrievedElements;
	protected final Iterable<CreateElement> createElements;
	protected final Iterable<DeleteElement> deleteElements;
	private final String routineUserExecutionQualifiedClassName;
	private final ClassNameGenerator routineClassNameGenerator;
	private final ClassNameGenerator routinesFacadeClassNameGenerator;
	private int elementMethodCounter;
	private int tagMethodCounter;
	
	public new(Routine routine, TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(typesBuilderExtensionProvider);
		this.routine = routine;
		this.hasExecutionBlock = routine.effect.codeBlock != null;
		this._completionChecker = new ResponseElementsCompletionChecker();
		this.retrievedElements = routine.matching?.retrievedElements?.filter[complete]?:newArrayList();
		this.createElements = routine.effect.elementCreation;
		this.deleteElements = routine.effect.elementDeletion;
		this.routineClassNameGenerator = routine.routineClassNameGenerator;
		this.routinesFacadeClassNameGenerator = routine.responsesSegment.routinesFacadeClassNameGenerator;
		this.routineUserExecutionQualifiedClassName = routineClassNameGenerator.qualifiedName + "." + EFFECT_USER_EXECUTION_SIMPLE_NAME;
		this.elementMethodCounter = 0;
		this.tagMethodCounter = 0;
	}
	
	protected abstract def Iterable<JvmFormalParameter> generateInputParameters(EObject sourceObject);
	
	protected def Iterable<JvmFormalParameter> generateInputAndRetrievedElementsParameters(EObject sourceObject) {
		return generateInputParameters(sourceObject) + retrievedElements.map[if (it.element != null) sourceObject.generateModelElementParameter(it.element) else null].filterNull;
	}
	
	protected def Iterable<JvmFormalParameter> generateAccessibleElementsParameters(EObject sourceObject) {
		return generateInputAndRetrievedElementsParameters(sourceObject) + createElements.map[sourceObject.generateModelElementParameter(it.element)];
	}
	
	protected def Iterable<String> getInputAndRetrievedElementsParameterNames() {
		return inputParameterNames + retrievedElements.map[it.element.name].filterNull;
	}
	
	protected def Iterable<String> getAccessibleElementsParameterNames() {
		return inputAndRetrievedElementsParameterNames + createElements.map[it.element.name];
	}
	
	protected abstract def Iterable<String> getInputParameterNames();
	
	protected def getParameterCallListWithModelInputAndAccesibleElements(String... parameterStrings) {
		getParameterCallList(accessibleElementsParameterNames + parameterStrings);
	}
	
	protected def getParameterCallListWithModelInputAndRetrievedElements(String... parameterStrings) {
		getParameterCallList(inputAndRetrievedElementsParameterNames + parameterStrings);
	}
	
	protected def getParameterCallListWithModelInput(String... parameterStrings) {
		getParameterCallList(inputParameterNames + parameterStrings);
	}
	
	protected def getParameterCallList(String... parameterStrings) '''
		«FOR parameterName : parameterStrings SEPARATOR ', '»«parameterName»«ENDFOR»'''
		
	public override JvmGenericType generateClass() {
		if (routine == null) {
			return null;
		}
		
		generateMethodExecuteEffect();
		routine.toClass(routineClassNameGenerator.qualifiedName) [
			visibility = JvmVisibility.PUBLIC;
			superTypes += typeRef(AbstractEffectRealization);
			members += generateConstructor();
			members += generateInputFields();
			members += generatedMethods;
			members += generateEffectUserExecutionClass();
		];
	}
	
	private def JvmGenericType generateEffectUserExecutionClass() {
		return routine.toClass(routineUserExecutionQualifiedClassName) [
			visibility = JvmVisibility.PRIVATE;
			static = true;
			superTypes += typeRef(AbstractEffectRealization.UserExecution);
			val routinesFacadeField = toField(EFFECT_FACADE_FIELD_NAME,  typeRef(routinesFacadeClassNameGenerator.qualifiedName)) [
					annotations += annotationRef(Extension);
				]
			members += #[routinesFacadeField];
			members += toConstructor() [
				val responseExecutionStateParameter = generateResponseExecutionStateParameter();
				val calledByParameter = generateParameter("calledBy", typeRef(CallHierarchyHaving));
				parameters += responseExecutionStateParameter;
				parameters += calledByParameter;
				body = '''
					super(«responseExecutionStateParameter.name»);
					this.«routinesFacadeField.simpleName» = new «routinesFacadeClassNameGenerator.qualifiedName»(«responseExecutionStateParameter.name», «calledByParameter.name»);
					'''
			]
			if (hasExecutionBlock) {
				members += generateMethodExecuteUserOperations(routine.effect.codeBlock, retrievedElements);
			}
		]
	}
	
	protected def generateMethodExecuteUserOperations(ExecutionCodeBlock executionBlock, RetrieveModelElement... retrieveElements) {
		if (!hasExecutionBlock) {
			return null;
		}
		
		val methodName = EFFECT_USER_EXECUTION_EXECUTE_METHOD_NAME;
		return executionBlock.toMethod(methodName, typeRef(Void.TYPE)) [
			visibility = JvmVisibility.PRIVATE;
			parameters += routine.generateInputParameters;
			parameters += retrieveElements.map[routine.generateModelElementParameter(element)];
			parameters += createElements.map[routine.generateModelElementParameter(it.element)];
			val code = executionBlock.code;
			if (code instanceof SimpleTextXBlockExpression) {
				body = code.text;
			} else {
				body = code;
			}
		];	
		
	}	
	
	protected def Iterable<JvmMember> generateInputFields() {
		val inputParameters = routine.generateInputParameters()
		return inputParameters.map[toField(name, parameterType)]
	}
	
	protected def JvmConstructor generateConstructor(JvmGenericType clazz) {
		return clazz.toConstructor [
			visibility = JvmVisibility.PUBLIC;
			val executionStateParameter = generateResponseExecutionStateParameter();
			val calledByParameter = generateParameter("calledBy", typeRef(CallHierarchyHaving));
			val inputParameters = routine.generateInputParameters();
			parameters += executionStateParameter;
			parameters += calledByParameter;
			parameters += inputParameters;
			body = '''super(«executionStateParameter.name», «calledByParameter.name»);
				«FOR inputParameter : inputParameters»this.«inputParameter.name» = «inputParameter.name»;«ENDFOR»'''
		]
	}
	
	var retrieveTagCounter = 0;
	
	protected def JvmOperation generateMethodGetRetrieveTag(Taggable taggable) {
		val methodName = "getRetrieveTag" + retrieveTagCounter++;
		
		return taggable.tag.getOrGenerateMethod(methodName, typeRef(String)) [
			visibility = JvmVisibility.PRIVATE;
			parameters += generateInputParameters();
			body = taggable.tag.code;
		];		
	}
	
	protected def JvmOperation generateMethodGetCreateTag(Taggable taggable) {
		val methodName = "getTag" + tagMethodCounter++;
		
		return taggable.tag.getOrGenerateMethod(methodName, typeRef(String)) [
			visibility = JvmVisibility.PRIVATE;
			parameters += generateAccessibleElementsParameters();
			body = taggable.tag.code;
		];		
	}
	
	protected def generateMethodCorrespondencePrecondition(RetrieveModelElement elementRetrieve) {
		val methodName = "getCorrespondingModelElementsPrecondition" + elementRetrieve.element.name.toFirstUpper;
		return elementRetrieve.precondition.getOrGenerateMethod(methodName, typeRef(Boolean.TYPE)) [
			visibility = JvmVisibility.PRIVATE;
			val inputParameters = generateInputParameters();
			val elementParameter = generateModelElementParameter(elementRetrieve.element);
			parameters += inputParameters;
			parameters += elementParameter;
			body = elementRetrieve.precondition.code;
		];
	}
	
	protected def generateMethodGetCorrespondenceSource(RetrieveModelElement elementRetrieve) {
		val methodName = "getCorrepondenceSource" + elementRetrieve.element.name.toFirstUpper;
		
		return elementRetrieve.correspondenceSource.getOrGenerateMethod(methodName, typeRef(EObject)) [
			visibility = JvmVisibility.PRIVATE;
			parameters += generateInputParameters();
			val correspondenceSourceBlock = elementRetrieve.correspondenceSource?.code;
			if (correspondenceSourceBlock instanceof SimpleTextXBlockExpression) {
				body = correspondenceSourceBlock.text;
			} else {
				body = correspondenceSourceBlock;
			}
		];
	}
	
	protected def generateMethodGetElement(ExistingElementReference reference) {
		val methodName = "getElement" + elementMethodCounter++;
		
		return reference.code.getOrGenerateMethod(methodName, typeRef(EObject)) [
			visibility = JvmVisibility.PRIVATE;
			parameters += generateAccessibleElementsParameters();
			val correspondenceSourceBlock = reference?.code;
			if (correspondenceSourceBlock instanceof SimpleTextXBlockExpression) {
				body = correspondenceSourceBlock.text;
			} else {
				body = correspondenceSourceBlock;
			}
		];
	}
	
	protected def generateMethodMatcherPrecondition(Matching matching) {
		if (matching?.condition == null) {
			return null;
		}
		val methodName = "checkMatcherPrecondition";
		return matching.condition.getOrGenerateMethod(methodName, typeRef(Boolean.TYPE)) [
			visibility = JvmVisibility.PRIVATE;
			parameters += generateInputAndRetrievedElementsParameters();
			body = matching.condition.code;
		];
	}
	
	
	protected def generateMethodExecuteEffect() {
		val methodName = "executeRoutine";
		val inputParameters = routine.generateInputParameters();
		val modelElementInitialization = <RetrieveModelElement, StringConcatenationClient>newHashMap();
		CollectionBridge.mapFixed(retrievedElements, 
				[modelElementInitialization.put(it, getInitializationCode(it, getParameterCallListWithModelInput()))]);
		
		val matcherMethodPrecondition = generateMethodMatcherPrecondition(routine.matching);
		
		val elementReferences = <EObject, String>newHashMap();
		CollectionBridge.mapFixed((routine.effect.correspondenceCreation.map[firstElement] + routine.effect.correspondenceCreation.map[secondElement]
			+ routine.effect.correspondenceDeletion.map[firstElement] + routine.effect.correspondenceDeletion.map[secondElement]).filter(ExistingElementReference),
			[elementReferences.put(it, generateMethodGetElement(it).simpleName + '''(«getParameterCallListWithModelInputAndAccesibleElements()»)''')]);
		CollectionBridge.mapFixed(routine.effect.elementDeletion.map[element],
			[elementReferences.put(it, generateMethodGetElement(it).simpleName + '''(«getParameterCallListWithModelInputAndAccesibleElements()»)''')]);
			
		val tagMethodCalls = <CreateCorrespondence, String>newHashMap();
		CollectionBridge.mapFixed(routine.effect.correspondenceCreation,
			[tagMethodCalls.put(it, if (it.tag != null) {generateMethodGetCreateTag(it).simpleName + '''(«getParameterCallListWithModelInputAndAccesibleElements()»)'''} else {'''""'''})]);

		return getOrGenerateMethod(methodName, typeRef(Void.TYPE)) [
			visibility = JvmVisibility.PROTECTED;
			exceptions += typeRef(IOException);
			body = '''
				getLogger().debug("Called routine «routineClassNameGenerator.simpleName» with input:");
				«FOR inputParameter : inputParameters»
					getLogger().debug("   «inputParameter.parameterType.type.simpleName»: " + this.«inputParameter.name»);
				«ENDFOR»
				
				«FOR element : retrievedElements»
					«modelElementInitialization.get(element)»
				«ENDFOR»
				«IF matcherMethodPrecondition != null»
					if (!«matcherMethodPrecondition.simpleName»(«getParameterCallListWithModelInputAndRetrievedElements()»)) {
						return;
					}
				«ENDIF»
				«FOR createElement : createElements»
					«getElementCreationCode(createElement)»
				«ENDFOR»
				«FOR deleteElement : deleteElements»
					deleteObject(«elementReferences.get(deleteElement.element)»);
				«ENDFOR»

				«FOR correspondenceCreate : routine.effect.correspondenceCreation»
					addCorrespondenceBetween(«elementReferences.get(correspondenceCreate.firstElement)», «
						elementReferences.get(correspondenceCreate.secondElement)», «tagMethodCalls.get(correspondenceCreate)»);
				«ENDFOR»
				«FOR correspondenceDelete : routine.effect.correspondenceDeletion»
					removeCorrespondenceBetween(«elementReferences.get(correspondenceDelete.firstElement)», «
						elementReferences.get(correspondenceDelete.secondElement)»);
				«ENDFOR»				
				preprocessElementStates();
				«IF hasExecutionBlock»
					new «routineUserExecutionQualifiedClassName»(getExecutionState(), this).«EFFECT_USER_EXECUTION_EXECUTE_METHOD_NAME»(
						«getParameterCallListWithModelInputAndAccesibleElements()»);
				«ENDIF»
				postprocessElementStates();
				'''
		];	
	}
		
	private def StringConcatenationClient getTagString(RetrieveModelElement retrieveElement) {
		if (retrieveElement.tag != null) {
			val tagMethod = generateMethodGetRetrieveTag(retrieveElement);
			return '''«tagMethod.simpleName»(«getParameterCallListWithModelInput()»)'''
		} else {
			return '''null'''
		}
	}
	
	private def StringConcatenationClient getPreconditionChecker(RetrieveModelElement retrieveElement) {
		val affectedElementClass = retrieveElement.element.element.javaClass;
		if (retrieveElement.precondition == null) {
			return '''(«affectedElementClass» _element) -> true''';
		}
		val preconditionMethod = generateMethodCorrespondencePrecondition(retrieveElement);
		return '''(«affectedElementClass» _element) -> «preconditionMethod.simpleName»(«getParameterCallListWithModelInput("_element")»)'''	
	}
	
	private def StringConcatenationClient getInitializationCode(RetrieveModelElement retrieveElement, CharSequence parameterCallList) {
		val retrieveStatement = getGetCorrespondingElementStatement(retrieveElement, parameterCallList);
		val affectedElementClass = retrieveElement.element.element;
		return '''
			«IF !retrieveElement.element.name.nullOrEmpty»
				«affectedElementClass.javaClass» «retrieveElement.element.name» = «retrieveStatement»;
				«IF retrieveElement.required»
					if («retrieveElement.element.name» == null) {
						return;
					}
				«ENDIF»
				initializeRetrieveElementState(«retrieveElement.element.name»);
			«ELSEIF retrieveElement.abscence»
				if («retrieveStatement» != null) {
					return;
				}
			«ELSEIF retrieveElement.required || retrieveElement.optional»
				if («retrieveStatement» == null) {
					return;
				} else {
					initializeRetrieveElementState(«retrieveStatement»);
				}
			«ENDIF»
		'''	
	}
	
	private def StringConcatenationClient getGetCorrespondingElementStatement(RetrieveModelElement retrieveElement, CharSequence parameterCallList) {
		val affectedElementClass = retrieveElement.element.element;
		val correspondingElementPreconditionChecker = getPreconditionChecker(retrieveElement);
		val correspondenceSourceMethod = generateMethodGetCorrespondenceSource(retrieveElement);
		val tagString = getTagString(retrieveElement);
		return '''
			getCorrespondingElement(
				«correspondenceSourceMethod.simpleName»(«getParameterCallListWithModelInput()»), // correspondence source supplier
				«affectedElementClass.javaClass».class,
				«correspondingElementPreconditionChecker», // correspondence precondition checker
				«tagString»)'''	
	}
	
	private def StringConcatenationClient getElementCreationCode(CreateElement elementCreate) {
		val affectedElementClass = elementCreate.element.element;
		val createdClassFactory = affectedElementClass.EPackage.EFactoryInstance.class;
		return '''
			«affectedElementClass.instanceClass» «elementCreate.element.name» = «createdClassFactory».eINSTANCE.create«affectedElementClass.name»();
			initializeCreateElementState(«elementCreate.element.name»);'''
	}
	
}