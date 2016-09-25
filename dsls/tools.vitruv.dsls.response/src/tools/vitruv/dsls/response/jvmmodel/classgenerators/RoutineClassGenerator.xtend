package tools.vitruv.dsls.response.jvmmodel.classgenerators

import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.common.types.JvmVisibility
import org.eclipse.xtext.common.types.JvmOperation
import tools.vitruv.dsls.response.environment.SimpleTextXBlockExpression
import java.io.IOException
import org.eclipse.xtend2.lib.StringConcatenationClient
import org.eclipse.xtext.common.types.JvmFormalParameter
import tools.vitruv.framework.util.bridges.CollectionBridge
import static extension tools.vitruv.dsls.response.helper.ResponseLanguageHelper.*;
import org.eclipse.xtext.common.types.JvmMember
import org.eclipse.xtext.common.types.JvmConstructor
import static tools.vitruv.dsls.response.helper.ResponseLanguageConstants.*;
import tools.vitruv.dsls.response.helper.ResponseClassNamesGenerator.ClassNameGenerator
import static extension tools.vitruv.dsls.response.helper.ResponseClassNamesGenerator.*;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving
import tools.vitruv.dsls.response.responseLanguage.CreateCorrespondence
import tools.vitruv.dsls.response.responseLanguage.RetrieveModelElement
import tools.vitruv.dsls.response.responseLanguage.CreateElement
import tools.vitruv.dsls.response.responseLanguage.DeleteElement
import tools.vitruv.dsls.response.responseLanguage.RoutineCallBlock
import java.util.List
import tools.vitruv.dsls.mirbase.mirBase.ModelElement
import tools.vitruv.dsls.mirbase.mirBase.NamedJavaElement
import tools.vitruv.dsls.response.responseLanguage.Routine
import tools.vitruv.dsls.response.responseLanguage.UpdateElement
import tools.vitruv.dsls.response.responseLanguage.RemoveCorrespondence
import tools.vitruv.dsls.response.responseLanguage.EffectStatement
import java.util.ArrayList
import tools.vitruv.dsls.response.jvmmodel.classgenerators.UserExecutionClassGenerator.AccessibleElement

class RoutineClassGenerator extends ClassGenerator {
	protected final Routine routine;
	protected final boolean hasRoutineCallBlock;
	protected extension ResponseElementsCompletionChecker _completionChecker;
	protected final Iterable<RetrieveModelElement> retrievedElements;
	protected final Iterable<CreateElement> createElements;
	protected final Iterable<DeleteElement> deleteElements;
	private final String generalUserExecutionClassQualifiedName;
	private final ClassNameGenerator routineClassNameGenerator;
	private final CallRoutineClassGenerator callRoutineClassGenerator;
	private final ClassNameGenerator routinesFacadeClassNameGenerator;
	private final List<ModelElement> modelInputElements;
	private final List<NamedJavaElement> javaInputElements;
	private extension final UserExecutionClassGenerator userExecutionClassGenerator;
	private final List<AccessibleElement> currentlyAccessibleElements;
	private static val USER_EXECUTION_FIELD_NAME = "userExecution";
	private int elementUpdateCounter;
	
	public new(Routine routine, TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(typesBuilderExtensionProvider);
		this.routine = routine;
		this.hasRoutineCallBlock = routine.effect.callRoutine!= null;
		this._completionChecker = new ResponseElementsCompletionChecker();
		this.retrievedElements = routine.matching?.retrievedElements?.filter[complete]?:newArrayList();
		this.createElements = routine.effect.effectStatement.filter(CreateElement);
		this.deleteElements = routine.effect.effectStatement.filter(DeleteElement);
		this.routineClassNameGenerator = routine.routineClassNameGenerator;
		this.routinesFacadeClassNameGenerator = routine.responsesSegment.routinesFacadeClassNameGenerator;
		this.generalUserExecutionClassQualifiedName = routineClassNameGenerator.qualifiedName + "." + EFFECT_USER_EXECUTION_SIMPLE_NAME;
		this.callRoutineClassGenerator = new CallRoutineClassGenerator(typesBuilderExtensionProvider, routine, 
			routineClassNameGenerator.qualifiedName + "." + EFFECT_CALL_ROUTINES_USER_EXECUTION_SIMPLE_NAME, routinesFacadeClassNameGenerator);
		this.userExecutionClassGenerator = new UserExecutionClassGenerator(typesBuilderExtensionProvider, routine, 
			routineClassNameGenerator.qualifiedName + "." + EFFECT_USER_EXECUTION_SIMPLE_NAME);
		this.modelInputElements = routine.input.modelInputElements;
		this.javaInputElements = routine.input.javaInputElements;
		this.elementUpdateCounter = 0;
		this.currentlyAccessibleElements = new ArrayList();
		this.currentlyAccessibleElements += routine.generateInputParameters().map[new AccessibleElement(it.name, it.parameterType)];
	}
	
	private def Iterable<JvmFormalParameter> generateInputParameters(EObject contextObject) {
		return generateMethodInputParameters(contextObject, modelInputElements, javaInputElements);
	}
	
	def private getInputParameterNames() {
		return modelInputElements.map[name]+ javaInputElements.map[name];
	}
	
	protected def Iterable<String> getInputAndRetrievedElementsParameterNames() {
		return inputParameterNames + retrievedElements.map[it.element.name].filterNull;
	}
	
	protected def getParameterCallListWithModelInputAndRetrievedElements(String... parameterStrings) {
		getParameterCallList(inputAndRetrievedElementsParameterNames + parameterStrings);
	}
	
	protected def generateCurrentlyAccessibleElementsParameters(EObject sourceObject) {
		this.currentlyAccessibleElements.map[sourceObject.toParameter(name, type)];
	}
	
	protected def generateMethodParameterCallList(JvmOperation method) {
		return method.parameters.generateMethodParameterCallList;
	}
		
	protected def generateMethodParameterCallList(Iterable<JvmFormalParameter> parameters) '''
		«FOR parameter : parameters.filterNull SEPARATOR ', '»«parameter.name»«ENDFOR»'''
	
	protected def getParameterCallList(String... parameterStrings) '''
		«FOR parameterName : parameterStrings SEPARATOR ', '»«parameterName»«ENDFOR»'''
		
	public override JvmGenericType generateClass() {
		if (routine == null) {
			return null;
		}
		
		val executeMethod = generateMethodExecuteEffect();
		if (hasRoutineCallBlock) {
			callRoutineClassGenerator.addMethod(generateMethodCallRoutines(routine.effect.callRoutine))
		}
		val userExecutionClass = userExecutionClassGenerator.generateClass;
		routine.toClass(routineClassNameGenerator.qualifiedName) [
			visibility = JvmVisibility.PUBLIC;
			superTypes += typeRef(AbstractEffectRealization);
			members += userExecutionClass;
			members += toField(USER_EXECUTION_FIELD_NAME, typeRef(userExecutionClass));
			members += generateConstructor();
			members += generateInputFields();
			members += executeMethod;
			members += callRoutineClassGenerator.generateClass();
		];
	}
	
		
	protected def generateMethodCallRoutines(RoutineCallBlock routineCallBlock) {
		if (!hasRoutineCallBlock) {
			return null;
		}
		
		val methodName = EFFECT_USER_EXECUTION_EXECUTE_METHOD_NAME;
		return routineCallBlock.toMethod(methodName, typeRef(Void.TYPE)) [
			parameters += routine.generateInputParameters;
			parameters += retrievedElements.map[routine.generateModelElementParameter(element)];
			parameters += createElements.map[routine.generateModelElementParameter(it.element)];
			val code = routineCallBlock.code;
			if (code instanceof SimpleTextXBlockExpression) {
				body = code.text;
			} else {
				body = code;
			}
		];	
		
	}	
	
	private def String getUserExecutionMethodCallString(JvmOperation method) '''
		«USER_EXECUTION_FIELD_NAME».«method.simpleName»(«method.generateMethodParameterCallList»)'''
	
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
				this.«USER_EXECUTION_FIELD_NAME» = new «generalUserExecutionClassQualifiedName»(getExecutionState(), this);
				«FOR inputParameter : inputParameters»this.«inputParameter.name» = «inputParameter.name»;«ENDFOR»'''
		]
	}
	
	private def dispatch StringConcatenationClient createStatements(CreateElement createElement) {
		this.currentlyAccessibleElements += new AccessibleElement(createElement.element.name, typeRef(createElement.element.element.instanceClass))
		val initializeMethod = if (createElement.initializationBlock != null) 
			generateUpdateElementMethod(createElement.element.name, createElement.initializationBlock, currentlyAccessibleElements);
		val initializeMethodCall = if (initializeMethod != null) initializeMethod.userExecutionMethodCallString;
		return '''
			«getElementCreationCode(createElement)»
			«IF initializeMethod != null»
				«initializeMethodCall»;
			«ENDIF»
		'''
	}
	
	private def dispatch StringConcatenationClient createStatements(DeleteElement deleteElement) {
		val getElementMethod = generateMethodGetElement(deleteElement.element, currentlyAccessibleElements);
		val getElementMethodCall = getElementMethod.userExecutionMethodCallString;
		return '''
			deleteObject(«getElementMethodCall»);
		'''
	}
	
	private def dispatch StringConcatenationClient createStatements(UpdateElement updateElement) {
		val getElementMethod = generateMethodGetElement(updateElement.element, currentlyAccessibleElements);
		val getElementMethodCall = getElementMethod.userExecutionMethodCallString;
		val updateMethod = generateUpdateElementMethod("" + elementUpdateCounter++, updateElement.updateBlock, currentlyAccessibleElements);
		val updateMethodCall = updateMethod.userExecutionMethodCallString;
		return '''
			// val updatedElement «getElementMethodCall»;
			«updateMethodCall»;
		'''
	}
	
	private def dispatch StringConcatenationClient createStatements(CreateCorrespondence createCorrespondence) {
		val getFirstElementMethod = generateMethodGetElement(createCorrespondence.firstElement, currentlyAccessibleElements);
		val getFirstElementMethodCall = getFirstElementMethod.userExecutionMethodCallString;
		val getSecondElementMethod = generateMethodGetElement(createCorrespondence.secondElement, currentlyAccessibleElements);
		val getSecondElementMethodCall = getSecondElementMethod.userExecutionMethodCallString;
		val tagMethod = if (createCorrespondence.tag != null) generateMethodGetCreateTag(createCorrespondence, currentlyAccessibleElements);
		val tagMethodCall = if (tagMethod != null) tagMethod.userExecutionMethodCallString else '''""''';
		return '''
			addCorrespondenceBetween(«getFirstElementMethodCall», «getSecondElementMethodCall», «tagMethodCall»);
		'''
	}
	
	private def dispatch StringConcatenationClient createStatements(RemoveCorrespondence removeCorrespondence) {
		val getFirstElementMethod = generateMethodGetElement(removeCorrespondence.firstElement, currentlyAccessibleElements);
		val getFirstElementMethodCall = getFirstElementMethod.userExecutionMethodCallString
		val getSecondElementMethod = generateMethodGetElement(removeCorrespondence.secondElement, currentlyAccessibleElements);
		val getSecondElementMethodCall = getSecondElementMethod.userExecutionMethodCallString;
		return '''
			removeCorrespondenceBetween(«getFirstElementMethodCall», «getSecondElementMethodCall»);
		'''
	}
	
	
	protected def generateMethodExecuteEffect() {
		val methodName = "executeRoutine";
		val inputParameters = routine.generateInputParameters();
		val modelElementInitialization = <RetrieveModelElement, StringConcatenationClient>newHashMap();
		CollectionBridge.mapFixed(retrievedElements, 
				[modelElementInitialization.put(it, getInitializationCode(it)); 
					currentlyAccessibleElements += new AccessibleElement(element.name, typeRef(element.element.instanceClass))]);
		
		val matcherMethodPrecondition = generateMethodMatcherPrecondition(routine.matching, currentlyAccessibleElements);
//		val effectStatements = routine.effect.effectStatement;
//		val elementReferences = <EObject, String>newHashMap();
//		CollectionBridge.mapFixed((effectStatements.filter(CreateCorrespondence).map[firstElement] + effectStatements.filter(CreateCorrespondence).map[secondElement]
//			+ effectStatements.filter(RemoveCorrespondence).map[firstElement] + effectStatements.filter(RemoveCorrespondence).map[secondElement]).filter(ExistingElementReference),
//			[elementReferences.put(it, generateMethodGetElement(it).simpleName + '''(«getParameterCallListWithModelInputAndAccesibleElements()»)''')]);
//		CollectionBridge.mapFixed(effectStatements.filter(DeleteElement).map[element],
//			[elementReferences.put(it, generateMethodGetElement(it).simpleName + '''(«getParameterCallListWithModelInputAndAccesibleElements()»)''')]);
//			
//		val tagMethodCalls = <CreateCorrespondence, String>newHashMap();
//		CollectionBridge.mapFixed(effectStatements.filter(CreateCorrespondence),
//			[tagMethodCalls.put(it, if (it.tag != null) {generateMethodGetCreateTag(it).simpleName + '''(«getParameterCallListWithModelInputAndAccesibleElements()»)'''} else {'''""'''})]);

		val effectStatementsMap = <EffectStatement, StringConcatenationClient>newHashMap();
		CollectionBridge.mapFixed(routine.effect.effectStatement,
			[effectStatementsMap.put(it, createStatements(it))]);

		return generateUnassociatedMethod(methodName, typeRef(Void.TYPE)) [
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
					if (!«USER_EXECUTION_FIELD_NAME».«matcherMethodPrecondition.simpleName»(«getParameterCallListWithModelInputAndRetrievedElements()»)) {
						return;
					}
				«ENDIF»
				«FOR effectStatement : routine.effect.effectStatement»
					«effectStatementsMap.get(effectStatement)»
					
				«ENDFOR»
«««				«FOR createElement : effectStatements.filter(CreateElement)»
«««					«getElementCreationCode(createElement)»
«««				«ENDFOR»
«««				«FOR deleteElement : effectStatements.filter(DeleteElement)»
«««					deleteObject(«elementReferences.get(deleteElement.element)»);
«««				«ENDFOR»
«««
«««				«FOR correspondenceCreate : effectStatements.filter(CreateCorrespondence)»
«««					addCorrespondenceBetween(«elementReferences.get(correspondenceCreate.firstElement)», «
«««						elementReferences.get(correspondenceCreate.secondElement)», «tagMethodCalls.get(correspondenceCreate)»);
«««				«ENDFOR»
«««				«FOR correspondenceDelete : effectStatements.filter(RemoveCorrespondence)»
«««					removeCorrespondenceBetween(«elementReferences.get(correspondenceDelete.firstElement)», «
«««						elementReferences.get(correspondenceDelete.secondElement)»);
«««				«ENDFOR»				
				preprocessElementStates();
				«IF hasRoutineCallBlock»
					new «callRoutineClassGenerator.qualifiedClassName»(getExecutionState(), this).«EFFECT_USER_EXECUTION_EXECUTE_METHOD_NAME»(
						«routine.generateCurrentlyAccessibleElementsParameters.generateMethodParameterCallList»);
				«ENDIF»
				postprocessElementStates();
				'''
		];	
	}
		
	private def StringConcatenationClient getTagString(RetrieveModelElement retrieveElement) {
		if (retrieveElement.tag != null) {
			val tagMethod = generateMethodGetRetrieveTag(retrieveElement, currentlyAccessibleElements);
			return '''«tagMethod.userExecutionMethodCallString»'''
		} else {
			return '''null'''
		}
	}
	
	private def StringConcatenationClient getPreconditionChecker(RetrieveModelElement retrieveElement) {
		val affectedElementClass = retrieveElement.element.element.javaClass;
		if (retrieveElement.precondition == null) {
			return '''(«affectedElementClass» _element) -> true''';
		}
		val preconditionMethod = generateMethodCorrespondencePrecondition(retrieveElement, currentlyAccessibleElements);
		return '''(«affectedElementClass» _element) -> «USER_EXECUTION_FIELD_NAME».«preconditionMethod.simpleName»(«
			preconditionMethod.generateMethodParameterCallList.toString.replace(retrieveElement.element.name, "_element")»)'''	
	}
	
	private def StringConcatenationClient getInitializationCode(RetrieveModelElement retrieveElement) {
		val retrieveStatement = getGetCorrespondingElementStatement(retrieveElement);
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
	
	private def StringConcatenationClient getGetCorrespondingElementStatement(RetrieveModelElement retrieveElement) {
		val affectedElementClass = retrieveElement.element.element;
		val correspondingElementPreconditionChecker = getPreconditionChecker(retrieveElement);
		val correspondenceSourceMethod = generateMethodGetCorrespondenceSource(retrieveElement, currentlyAccessibleElements);
		val correspondenceSourceMethodCall = correspondenceSourceMethod.userExecutionMethodCallString;
		val tagString = getTagString(retrieveElement);
		return '''
			getCorrespondingElement(
				«correspondenceSourceMethodCall», // correspondence source supplier
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