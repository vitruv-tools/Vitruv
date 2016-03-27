package edu.kit.ipd.sdq.vitruvius.dsls.response.jvmmodel.classgenerators

import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Effect
import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.common.types.JvmVisibility
import org.eclipse.xtext.common.types.JvmOperation
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ModelPathCodeBlock
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.CorrespondingModelElementRetrieveOrDelete
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.CorrespondingModelElementDelete
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.CorrespondingModelElementSpecification
import edu.kit.ipd.sdq.vitruvius.dsls.response.generator.impl.SimpleTextXBlockExpression
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.CorrespondingModelElementCreate
import java.io.IOException
import org.eclipse.xtend2.lib.StringConcatenationClient
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ExecutionCodeBlock
import org.eclipse.xtext.common.types.JvmFormalParameter
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.CorrespondingModelElementRetrieve
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.helper.ResponseLanguageHelper.*;
import org.eclipse.xtext.common.types.JvmMember
import org.eclipse.xtext.common.types.JvmConstructor
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment.effects.AbstractEffectRealization
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import static edu.kit.ipd.sdq.vitruvius.dsls.response.helper.ResponseLanguageConstants.*;
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment.CallHierarchyHaving
import edu.kit.ipd.sdq.vitruvius.dsls.response.generator.ResponseClassNamesGenerator.EffectsFacadeClassNameGenerator
import edu.kit.ipd.sdq.vitruvius.dsls.response.generator.ResponseClassNamesGenerator.ClassNameGenerator
import edu.kit.ipd.sdq.vitruvius.dsls.response.generator.ResponseClassNamesGenerator.EffectClassNameGenerator

abstract class EffectClassGenerator extends ClassGenerator {
	protected final Effect effect;
	protected final boolean hasExecutionBlock;
	protected extension ResponseElementsCompletionChecker _completionChecker;
	protected final Iterable<CorrespondingModelElementSpecification> modelElements;
	private final String effectUserExecutionQualifiedClassName;
	private final ClassNameGenerator effectClassNameGenerator;
	private final EffectsFacadeClassNameGenerator effectsFacadeClassNameGenerator;
	
	public new(Effect effect, TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(typesBuilderExtensionProvider);
		this.effect = effect;
		this.hasExecutionBlock = effect.codeBlock != null;
		this._completionChecker = new ResponseElementsCompletionChecker();
		this.modelElements = (effect.createElements + effect.retrieveElements + effect.deleteElements).filter[complete];
		this.effectClassNameGenerator = new EffectClassNameGenerator(effect);
		this.effectsFacadeClassNameGenerator = new EffectsFacadeClassNameGenerator(effect.responsesSegment);
		this.effectUserExecutionQualifiedClassName = effectClassNameGenerator.qualifiedName + "." + EFFECT_USER_EXECUTION_SIMPLE_NAME;
	}
	
	protected abstract def Iterable<JvmFormalParameter> generateInputParameters(EObject sourceObject); 
	protected abstract def Iterable<String> getInputParameterNames();
	
	protected def getParameterCallListWithModelInput(String... parameterStrings) {
		getParameterCallList(inputParameterNames + parameterStrings);
	}
	
	protected def getParameterCallList(String... parameterStrings) '''
		«FOR parameterName : parameterStrings SEPARATOR ', '»«parameterName»«ENDFOR»'''
		
	public override JvmGenericType generateClass() {
		if (effect == null) {
			return null;
		}
		
		generateMethodExecuteEffect();
		generateMethodAllParametersSet();
		effect.toClass(effectClassNameGenerator.qualifiedName) [
			visibility = JvmVisibility.PUBLIC;
			superTypes += typeRef(AbstractEffectRealization);
			members += generateConstructor();
			members += generateInputFieldsAndSetterMethods();
			members += generatedMethods;
			members += generateEffectUserExecutionClass();
		];
	}
	
	private def JvmGenericType generateEffectUserExecutionClass() {
		return effect.toClass(effectUserExecutionQualifiedClassName) [
			visibility = JvmVisibility.PRIVATE;
			static = true;
			val blackboardField = toField(BLACKBOARD_FIELD_NAME, typeRef(Blackboard));
			val userInteractingField = toField(USER_INTERACTING_FIELD_NAME, typeRef(UserInteracting));
			val transformationResultField = toField(TRANSFORMATION_RESULT_FIELD_NAME, typeRef(TransformationResult));
			val effectsFacadeField = toField(EFFECT_FACADE_FIELD_NAME,  typeRef(effectsFacadeClassNameGenerator.qualifiedName)) [
					annotations += annotationRef(Extension);
				]
			members += #[blackboardField, userInteractingField, transformationResultField, effectsFacadeField];
			members += toConstructor() [
				val responseExecutionStateParameter = generateResponseExecutionStateParameter();
				val calledByParameter = generateParameter("calledBy", typeRef(CallHierarchyHaving));
				parameters += responseExecutionStateParameter;
				parameters += calledByParameter;
				body = '''
					this.«blackboardField.simpleName» = «responseExecutionStateParameter.name».getBlackboard();
					this.«userInteractingField.simpleName» = «responseExecutionStateParameter.name».getUserInteracting();
					this.«transformationResultField.simpleName» = «responseExecutionStateParameter.name».getTransformationResult();
					this.«effectsFacadeField.simpleName» = new «typeRef(effectsFacadeClassNameGenerator.qualifiedName)»(«responseExecutionStateParameter.name», «calledByParameter.name»);
					'''
			]
			if (hasExecutionBlock) {
				members += generateMethodExecuteUserOperations(effect.codeBlock, modelElements);
			}
		]
	}
	
	protected def generateMethodExecuteUserOperations(ExecutionCodeBlock executionBlock, CorrespondingModelElementSpecification... modelElements) {
		if (!hasExecutionBlock) {
			return null;
		}
		
		val methodName = EFFECT_USER_EXECUTION_EXECUTE_METHOD_NAME;
		return executionBlock.toMethod(methodName, typeRef(Void.TYPE)) [
			visibility = JvmVisibility.PRIVATE;
			parameters += effect.generateInputParameters;
			parameters += modelElements.map[effect.generateModelElementParameter(it)];
			val code = executionBlock.code;
			if (code instanceof SimpleTextXBlockExpression) {
				body = code.text;
			} else {
				body = code;
			}
		];	
		
	}	
	
	protected def void generateMethodAllParametersSet() {
		effect.getOrGenerateMethod("allParametersSet", typeRef(Boolean.TYPE)) [
			visibility = JvmVisibility.PUBLIC
			body = '''
				return «FOR inputParameter : inputParameterNames SEPARATOR '&&'
					»is«inputParameter.toFirstUpper»Set«ENDFOR»;'''		
		]
	}
	
	protected def Iterable<JvmMember> generateInputFieldsAndSetterMethods() {
		val inputParameters = effect.generateInputParameters()
		return inputParameters.map[toField(name, parameterType)]
			+ inputParameters.map[toField("is" + name.toFirstUpper + "Set", typeRef(Boolean.TYPE))]
			+ inputParameters.map[param | param.toMethod("set" + param.name.toFirstUpper, typeRef(Void.TYPE)) [
				visibility = JvmVisibility.PUBLIC;
				parameters += param;
				body = '''
					this.«param.name» = «param.name»;
					this.is«param.name.toFirstUpper»Set = true;''';
			]]
	}
	
	protected def JvmConstructor generateConstructor(JvmGenericType clazz) {
		return clazz.toConstructor [
			visibility = JvmVisibility.PUBLIC;
			val executionStateParameter = generateResponseExecutionStateParameter();
			val calledByParameter = generateParameter("calledBy", typeRef(CallHierarchyHaving));
			parameters += executionStateParameter;
			parameters += calledByParameter;
			body = '''super(«executionStateParameter.name», «calledByParameter.name»);'''
		]
	}
	
	protected def JvmOperation generateMethodGetPathFromSource(ModelPathCodeBlock modelPathCodeBlock, CorrespondingModelElementSpecification... inputElements) {
		val methodName = "getModelPath";
		
		return modelPathCodeBlock.getOrGenerateMethod(methodName, typeRef(String)) [
			visibility = JvmVisibility.PRIVATE;
			parameters += generateInputParameters();
			for (inputElement : inputElements) {
				parameters += generateModelElementParameter(inputElement);
			}
			body = modelPathCodeBlock.code;
		];		
	}
	
	protected def dispatch JvmOperation generateMethodGetPathFromSource(CorrespondingModelElementCreate elementCreate) {
		if (elementCreate.persistence == null) {
			return null;
		}
		return generateMethodGetPathFromSource(elementCreate.persistence.modelPath, elementCreate);		
	}
	
	protected def dispatch JvmOperation generateMethodGetPathFromSource(CorrespondingModelElementDelete elementDelete) {
		return null;
	}
	
	protected def dispatch JvmOperation generateMethodGetPathFromSource(CorrespondingModelElementRetrieve elementRetrieve) {
		if (elementRetrieve.persistence == null) {
			return null;
		}
		return generateMethodGetPathFromSource(elementRetrieve.persistence.modelPath, elementRetrieve);		
	}
	
	protected def generateMethodCorrespondencePrecondition(CorrespondingModelElementRetrieveOrDelete correspondingModelElementSpecification) {
		val methodName = "getCorrespondingModelElementsPrecondition" + correspondingModelElementSpecification.name.toFirstUpper;
		return correspondingModelElementSpecification.precondition.getOrGenerateMethod(methodName, typeRef(Boolean.TYPE)) [
			visibility = JvmVisibility.PRIVATE;
			val inputParameters = generateInputParameters();
			val elementParameter = generateModelElementParameter(correspondingModelElementSpecification);
			parameters += inputParameters;
			parameters += elementParameter;
			body = correspondingModelElementSpecification.precondition.code;
		];
	}
	
	protected def generateMethodGetCorrespondenceSource(CorrespondingModelElementSpecification modelElementSpecification) {
		val methodName = "getCorrepondenceSource" + modelElementSpecification.name.toFirstUpper;
		
		return modelElementSpecification.correspondenceSource.getOrGenerateMethod(methodName, typeRef(EObject)) [
			visibility = JvmVisibility.PRIVATE;
			parameters += generateInputParameters();
			val correspondenceSourceBlock = modelElementSpecification.correspondenceSource?.code;
			if (correspondenceSourceBlock instanceof SimpleTextXBlockExpression) {
				body = correspondenceSourceBlock.text;
			} else {
				body = correspondenceSourceBlock;
			}
		];
	}
	
	
	
	protected def generateMethodExecuteEffect() {
		val methodName = "executeEffect";
		val inputParameters = effect.generateInputParameters();
		val modelElementInitialization = <CorrespondingModelElementSpecification, StringConcatenationClient>newHashMap();
		CollectionBridge.mapFixed(modelElements, 
				[modelElementInitialization.put(it, getInitializationCode(it, getParameterCallListWithModelInput()))]);
		
		return getOrGenerateMethod(methodName, typeRef(Void.TYPE)) [
			visibility = JvmVisibility.PROTECTED;
			exceptions += typeRef(IOException);
			body = '''
				getLogger().debug("Called effect «effectClassNameGenerator.simpleName» with input:");
				«FOR inputParameter : inputParameters»
					getLogger().debug("   «inputParameter.parameterType.type.simpleName»: " + this.«inputParameter.name»);
				«ENDFOR»
				
				«FOR element : modelElements»
					«modelElementInitialization.get(element)»
				«ENDFOR»
				preProcessElements();
				«IF hasExecutionBlock»
					new «effectUserExecutionQualifiedClassName»(getExecutionState(), this).«EFFECT_USER_EXECUTION_EXECUTE_METHOD_NAME»(
						«getParameterCallListWithModelInput(modelElements.map[name])»);
				«ENDIF»
				postProcessElements();
				'''
		];	
	}
		
	private def StringConcatenationClient getCorrespondenceSourceSupplier(CorrespondingModelElementSpecification elementSpecification) {
		val correspondenceSourceMethod = generateMethodGetCorrespondenceSource(elementSpecification);
		return '''() -> «correspondenceSourceMethod.simpleName»(«getParameterCallListWithModelInput()»)'''	
	}
	
	private def StringConcatenationClient getPersistencePathSupplier(CorrespondingModelElementSpecification elementSpecification) {
		val pathFromSourceMethod = generateMethodGetPathFromSource(elementSpecification);
		if (pathFromSourceMethod == null) {
			return null;
		}
		return '''() -> «pathFromSourceMethod.simpleName»(«getParameterCallListWithModelInput(elementSpecification.name)»)'''	
	}
	
	private def StringConcatenationClient getPreconditionChecker(CorrespondingModelElementRetrieveOrDelete elementSpecification) {
		val affectedElementClass = elementSpecification.elementType.element.javaClass;
		if (elementSpecification.precondition == null) {
			return '''(«affectedElementClass» _element) -> true''';
		}
		val preconditionMethod = generateMethodCorrespondencePrecondition(elementSpecification);
		return '''(«affectedElementClass» _element) -> «preconditionMethod.simpleName»(«getParameterCallListWithModelInput("_element")»)'''	
	}
	
	private def dispatch StringConcatenationClient getInitializationCode(CorrespondingModelElementRetrieveOrDelete elementRetrieveOrDelete, CharSequence parameterCallList) {
		val affectedElementClass = elementRetrieveOrDelete.elementType.element;
		val correspondingElementPreconditionChecker = getPreconditionChecker(elementRetrieveOrDelete);
		val correspondenceSourceSupplier = getCorrespondenceSourceSupplier(elementRetrieveOrDelete);
		val persistenceInformationInitializer = getPersistenceInformationInitializer(elementRetrieveOrDelete);
		return '''
			«affectedElementClass.javaClass» «elementRetrieveOrDelete.name» = «
			IF elementRetrieveOrDelete instanceof CorrespondingModelElementRetrieve»initializeRetrieveElementState«
			ELSE»initializeDeleteElementState«ENDIF»(
				«correspondenceSourceSupplier», // correspondence source supplier
				«correspondingElementPreconditionChecker», // correspondence precondition checker
				«affectedElementClass.javaClass».class,	«elementRetrieveOrDelete.optional»);
			«persistenceInformationInitializer»
		'''	
	}
	
	private def dispatch StringConcatenationClient getInitializationCode(CorrespondingModelElementCreate elementCreate, CharSequence parameterCallList) {
		if (!elementCreate.complete) {
			return '''''';
		}
		val affectedElementClass = elementCreate.elementType.element.javaClass;
		val correspondenceSourceSupplier = getCorrespondenceSourceSupplier(elementCreate);
		val persistenceInformationInitializer = getPersistenceInformationInitializer(elementCreate);
		val elementCreationSupplier = getElementCreationSupplier(elementCreate);
		return '''
			«affectedElementClass» «elementCreate.name» = initializeCreateElementState(
				«correspondenceSourceSupplier», // correspondence source supplier
				«elementCreationSupplier», // element creation supplier
				«affectedElementClass».class);
			«persistenceInformationInitializer»
			'''	
	}
	
	private def StringConcatenationClient getPersistenceInformationInitializer(CorrespondingModelElementSpecification elementSpecification) {
		val persistencePathSupplier = getPersistencePathSupplier(elementSpecification);
		if (persistencePathSupplier != null) {
			return '''setPersistenceInformation(«elementSpecification.name», «persistencePathSupplier», «
				IF elementSpecification instanceof CorrespondingModelElementCreate»«elementSpecification.persistence.useRelativeToSource»«
				ELSEIF elementSpecification instanceof CorrespondingModelElementRetrieve»«(elementSpecification as CorrespondingModelElementRetrieve).persistence.useRelativeToSource»«
				ENDIF»);'''
		}
		return '''''';
		
	}
	
	private def StringConcatenationClient getElementCreationSupplier(CorrespondingModelElementCreate elementCreate) {
		val affectedElementClass = elementCreate.elementType.element;
		val createdClassFactory = affectedElementClass.EPackage.EFactoryInstance.class;
		return '''() -> «createdClassFactory».eINSTANCE.create«affectedElementClass.name»()'''
	}
	
}