package tools.vitruv.dsls.reactions.codegen.classgenerators

import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.common.types.JvmVisibility
import org.eclipse.xtext.common.types.JvmOperation
import java.io.IOException
import org.eclipse.xtend2.lib.StringConcatenationClient
import org.eclipse.xtext.common.types.JvmFormalParameter
import tools.vitruv.framework.util.bridges.CollectionBridge
import static extension tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageHelper.*;
import org.eclipse.xtext.common.types.JvmMember
import org.eclipse.xtext.common.types.JvmConstructor
import static tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageConstants.*;
import tools.vitruv.dsls.reactions.codegen.helper.ClassNamesGenerators.ClassNameGenerator
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving
import tools.vitruv.dsls.reactions.reactionsLanguage.CreateCorrespondence
import java.util.List
import tools.vitruv.dsls.mirbase.mirBase.NamedJavaElement
import tools.vitruv.dsls.reactions.reactionsLanguage.Routine
import tools.vitruv.dsls.reactions.reactionsLanguage.RemoveCorrespondence
import java.util.ArrayList
import tools.vitruv.dsls.reactions.reactionsLanguage.RoutineCallStatement
import tools.vitruv.dsls.reactions.reactionsLanguage.MatcherCheckStatement
import tools.vitruv.dsls.reactions.reactionsLanguage.MatcherStatement
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization
import tools.vitruv.dsls.reactions.reactionsLanguage.CreateModelElement
import tools.vitruv.dsls.reactions.reactionsLanguage.RetrieveModelElement
import tools.vitruv.dsls.reactions.reactionsLanguage.DeleteModelElement
import tools.vitruv.dsls.reactions.reactionsLanguage.UpdateModelElement
import tools.vitruv.dsls.reactions.reactionsLanguage.ActionStatement
import tools.vitruv.dsls.mirbase.mirBase.NamedMetaclassReference
import static extension tools.vitruv.dsls.reactions.codegen.helper.ClassNamesGenerators.*
import tools.vitruv.dsls.reactions.codegen.helper.AccessibleElement
import tools.vitruv.dsls.reactions.codegen.typesbuilder.TypesBuilderExtensionProvider

class RoutineClassGenerator extends ClassGenerator {
	protected final Routine routine;
	protected extension ReactionElementsCompletionChecker _completionChecker;
	private final String generalUserExecutionClassQualifiedName;
	private final ClassNameGenerator routineClassNameGenerator;
	private final ClassNameGenerator routinesFacadeClassNameGenerator;
	private final List<NamedMetaclassReference> modelInputElements;
	private final List<NamedJavaElement> javaInputElements;
	private extension final UserExecutionClassGenerator userExecutionClassGenerator;
	private final List<AccessibleElement> currentlyAccessibleElements;
	private static val USER_EXECUTION_FIELD_NAME = "userExecution";
	private int elementUpdateCounter;
	
	public new(Routine routine, TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(typesBuilderExtensionProvider);
		this.routine = routine;
		this._completionChecker = new ReactionElementsCompletionChecker();
		this.routineClassNameGenerator = routine.routineClassNameGenerator;
		this.routinesFacadeClassNameGenerator = routine.reactionsSegment.routinesFacadeClassNameGenerator;
		this.generalUserExecutionClassQualifiedName = routineClassNameGenerator.qualifiedName + "." + EFFECT_USER_EXECUTION_SIMPLE_NAME;
		this.userExecutionClassGenerator = new UserExecutionClassGenerator(typesBuilderExtensionProvider, routine, 
			routineClassNameGenerator.qualifiedName + "." + EFFECT_USER_EXECUTION_SIMPLE_NAME);
		this.modelInputElements = routine.input.modelInputElements;
		this.javaInputElements = routine.input.javaInputElements;
		this.elementUpdateCounter = 0;
		this.currentlyAccessibleElements = new ArrayList();
		this.currentlyAccessibleElements += routine.generateInputParameters().map[new AccessibleElement(it.name, it.parameterType.qualifiedName)];
	}
	
	private def Iterable<JvmFormalParameter> generateInputParameters(EObject contextObject) {
		return generateMethodInputParameters(contextObject, modelInputElements, javaInputElements);
	}
	
	protected def generateCurrentlyAccessibleElementsParameters(EObject sourceObject) {
		this.currentlyAccessibleElements.map[sourceObject.toParameter(name, typeRef(fullyQualifiedType))];
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
		val userExecutionClass = userExecutionClassGenerator.generateClass;
		
		routine.toClass(routineClassNameGenerator.qualifiedName) [
			visibility = JvmVisibility.PUBLIC;
			superTypes += typeRef(AbstractRepairRoutineRealization);
			members += toField(EFFECT_FACADE_FIELD_NAME,  typeRef(routinesFacadeClassNameGenerator.qualifiedName));
			members += toField(USER_EXECUTION_FIELD_NAME, typeRef(userExecutionClass));
			members += userExecutionClass;
			members += generateConstructor();
			members += generateInputFields();
			members += executeMethod;
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
			val executionStateParameter = generateReactionExecutionStateParameter();
			val calledByParameter = generateParameter("calledBy", typeRef(CallHierarchyHaving));
			val inputParameters = routine.generateInputParameters();
			parameters += executionStateParameter;
			parameters += calledByParameter;
			parameters += inputParameters;
			body = '''
				super(«executionStateParameter.name», «calledByParameter.name»);
				this.«USER_EXECUTION_FIELD_NAME» = new «generalUserExecutionClassQualifiedName»(getExecutionState(), this);
				this.«EFFECT_FACADE_FIELD_NAME» = new «routinesFacadeClassNameGenerator.qualifiedName»(getExecutionState(), this);
				«FOR inputParameter : inputParameters»this.«inputParameter.name» = «inputParameter.name»;«ENDFOR»'''
		]
	}
	
	
	private def dispatch StringConcatenationClient createStatements(CreateModelElement createElement) {
		this.currentlyAccessibleElements += new AccessibleElement(createElement.name, createElement.javaClass)
		val initializeMethod = if (createElement.initializationBlock != null) 
			generateUpdateElementMethod(createElement.name, createElement.initializationBlock, currentlyAccessibleElements);
		val initializeMethodCall = if (initializeMethod != null) initializeMethod.userExecutionMethodCallString;
		return '''
			«getElementCreationCode(createElement)»
			«IF initializeMethod != null»
				«initializeMethodCall»;
			«ENDIF»
		'''
	}
	
	private def dispatch StringConcatenationClient createStatements(RetrieveModelElement retrieveElement) {
		val retrieveStatement = getGetCorrespondingElementStatement(retrieveElement);
		val affectedElementClass = retrieveElement.metaclass;
		val StringConcatenationClient statements = '''
			«IF !retrieveElement.name.nullOrEmpty»
				«affectedElementClass.javaClass» «retrieveElement.name» = «retrieveStatement»;
				«IF !retrieveElement.optional && !retrieveElement.abscence»
					if («retrieveElement.name» == null) {
						return;
					}
				«ENDIF»
				initializeRetrieveElementState(«retrieveElement.name»);
			«ELSEIF retrieveElement.abscence»
				if («retrieveStatement» != null) {
					return;
				}
			«ELSE»
				if («retrieveStatement» == null) {
					return;
				} else {
					initializeRetrieveElementState(«retrieveStatement»);
				}
			«ENDIF»
		'''	
		if (!retrieveElement.abscence) {
			currentlyAccessibleElements += new AccessibleElement(retrieveElement.name, retrieveElement.javaClass);
		}
		return statements;
	}
	
	private def dispatch StringConcatenationClient createStatements(MatcherCheckStatement checkStatement) {
		val checkMethod = generateMethodMatcherPrecondition(checkStatement, currentlyAccessibleElements);
		val checkMethodCall = checkMethod.userExecutionMethodCallString;
		return '''
		if (!«checkMethodCall») {
			return;
		}''';
	}
	
	private def dispatch StringConcatenationClient createStatements(DeleteModelElement deleteElement) {
		val getElementMethod = generateMethodGetElement(deleteElement.element, currentlyAccessibleElements);
		val getElementMethodCall = getElementMethod.userExecutionMethodCallString;
		return '''
			deleteObject(«getElementMethodCall»);
		'''
	}
	
	private def dispatch StringConcatenationClient createStatements(UpdateModelElement updateElement) {
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
	
	private def dispatch StringConcatenationClient createStatements(RoutineCallStatement routineCall) {
		val callRoutineMethod = generateMethodCallRoutine(routineCall, currentlyAccessibleElements, typeRef(routinesFacadeClassNameGenerator.qualifiedName));
		val StringConcatenationClient callRoutineMethodCall = '''«USER_EXECUTION_FIELD_NAME».«callRoutineMethod.simpleName»(«
			routineCall.generateCurrentlyAccessibleElementsParameters.generateMethodParameterCallList», «EFFECT_FACADE_FIELD_NAME»);''';
		return callRoutineMethodCall;
	}
	
	
	protected def generateMethodExecuteEffect() {
		val methodName = "executeRoutine";
		val inputParameters = routine.generateInputParameters();
		val matcherStatements = if (routine.matcher != null) routine.matcher.matcherStatements else #[];
		val effectStatements = routine.action.actionStatements;
		val matcherStatementsMap = <MatcherStatement, StringConcatenationClient>newHashMap();
		CollectionBridge.mapFixed(matcherStatements, [matcherStatementsMap.put(it, createStatements(it))]);
		val effectStatementsMap = <ActionStatement, StringConcatenationClient>newHashMap();
		CollectionBridge.mapFixed(effectStatements, [effectStatementsMap.put(it, createStatements(it))]);
		return generateUnassociatedMethod(methodName, typeRef(Void.TYPE)) [
			visibility = JvmVisibility.PROTECTED;
			exceptions += typeRef(IOException);
			body = '''
				getLogger().debug("Called routine «routineClassNameGenerator.simpleName» with input:");
				«FOR inputParameter : inputParameters»
					getLogger().debug("   «inputParameter.parameterType.type.simpleName»: " + this.«inputParameter.name»);
				«ENDFOR»
				
				«FOR matcherStatement : matcherStatements»
					«matcherStatementsMap.get(matcherStatement)»
				«ENDFOR»
				«FOR effectStatement : effectStatements»
					«effectStatementsMap.get(effectStatement)»
					
				«ENDFOR»
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
		val affectedElementClass = retrieveElement.javaClass;
		if (retrieveElement.precondition == null) {
			return '''(«affectedElementClass» _element) -> true''';
		}
		val preconditionMethod = generateMethodCorrespondencePrecondition(retrieveElement, currentlyAccessibleElements);
		return '''(«affectedElementClass» _element) -> «USER_EXECUTION_FIELD_NAME».«preconditionMethod.simpleName»(«
			preconditionMethod.generateMethodParameterCallList.toString.replace(retrieveElement.name, "_element")»)'''	
	}
	
	private def StringConcatenationClient getGetCorrespondingElementStatement(RetrieveModelElement retrieveElement) {
		val affectedElementClass = retrieveElement.javaClass;
		val correspondingElementPreconditionChecker = getPreconditionChecker(retrieveElement);
		val correspondenceSourceMethod = generateMethodGetCorrespondenceSource(retrieveElement, currentlyAccessibleElements);
		val correspondenceSourceMethodCall = correspondenceSourceMethod.userExecutionMethodCallString;
		val tagString = getTagString(retrieveElement);
		return '''
			getCorrespondingElement(
				«correspondenceSourceMethodCall», // correspondence source supplier
				«affectedElementClass».class,
				«correspondingElementPreconditionChecker», // correspondence precondition checker
				«tagString»)'''	
	}
	
	private def StringConcatenationClient getElementCreationCode(CreateModelElement elementCreate) {
		val affectedElementClass = elementCreate.metaclass;
		val createdClassFactory = affectedElementClass.EPackage.EFactoryInstance.class;
		return '''
			«affectedElementClass.javaClass» «elementCreate.name» = «createdClassFactory».eINSTANCE.create«affectedElementClass.name»();
			initializeCreateElementState(«elementCreate.name»);'''
	}
	
}