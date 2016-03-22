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
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.runtime.ResponseExecutionState
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.generator.EffectsGeneratorUtils.*;
import org.eclipse.xtext.common.types.JvmMember
import org.eclipse.xtext.common.types.JvmConstructor
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment.effects.AbstractEffectRealization

abstract class EffectClassGenerator extends ClassGenerator {
	protected final Effect effect;
	protected final boolean hasExecutionBlock;
	protected final boolean hasEffectsFacade;
	protected extension ResponseElementsCompletionChecker _completionChecker;
	
	public new(Effect effect, TypesBuilderExtensionProvider typesBuilderExtensionProvider, boolean hasEffectsFacade) {
		super(typesBuilderExtensionProvider);
		this.effect = effect;
		this.hasExecutionBlock = effect.codeBlock != null;
		this._completionChecker = new ResponseElementsCompletionChecker();
		this.hasEffectsFacade = hasEffectsFacade;
	}
	
	protected abstract def Iterable<JvmFormalParameter> generateInputParameters(EObject sourceObject); 
	
	protected def getParameterCallList(JvmFormalParameter... parameters) '''
		«FOR parameter : parameters SEPARATOR ', '»«parameter.name»«ENDFOR»'''
		
	public override JvmGenericType generateClass() {
		if (effect == null) {
			return null;
		}
		
		generateMethodExecuteEffect();
		generateMethodAllParametersSet();
		effect.toClass(effect.qualifiedClassName) [
			visibility = JvmVisibility.PUBLIC;
			superTypes += typeRef(AbstractEffectRealization);
			members += generateConstructor();
			members += generateInputFieldsAndSetterMethods();
			members += generateFacadeClassField;
			members += generatedMethods;
		];
	}
	
	private def JvmMember generateFacadeClassField() {
		if (!hasEffectsFacade) {
			return null;
		}
		effect.toField("_effectsFacade", typeRef(effect.qualifiedEffectsFacadeClassName)) [
			annotations += annotationRef(Extension);
			initializer = '''new «typeRef(effect.qualifiedEffectsFacadeClassName)»(_executionState)'''
		]
	}
	
	protected def void generateMethodAllParametersSet() {
		effect.getOrGenerateMethod("allParametersSet", typeRef(Boolean.TYPE)) [
			visibility = JvmVisibility.PUBLIC
			body = '''
				return «FOR inputParameter : effect.generateInputParameters() SEPARATOR '&&'
					»is«inputParameter.name.toFirstUpper»Set«ENDFOR»;'''		
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
			val executionStateParameter = generateParameter("executionState", ResponseExecutionState);
			parameters += executionStateParameter;
			visibility = JvmVisibility.PUBLIC;
			body = '''super(«executionStateParameter.name»);'''
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
	
	protected def generateMethodPerformResponse(ExecutionCodeBlock executionBlock, CorrespondingModelElementSpecification... modelElements) {
		if (!hasExecutionBlock) {
			return null;
		}
		val methodName = "performResponseTo";
		return executionBlock.getOrGenerateMethod(methodName, typeRef(Void.TYPE)) [
			visibility = JvmVisibility.PRIVATE;
			parameters += generateInputParameters;
			val generatedMethod = it;
			parameters += modelElements.map[generateModelElementParameter(generatedMethod, it)];
			val code = executionBlock.code;
			if (code instanceof SimpleTextXBlockExpression) {
				body = code.text;
			} else {
				body = code;
			}
		];	
	}
	
	protected def generateMethodExecuteEffect() {
		val methodName = "executeEffect";
		val inputParameters = effect.generateInputParameters();
		val modelElements = (effect.createElements + effect.retrieveElements + effect.deleteElements).filter[complete]
		val modelElementInitialization = <CorrespondingModelElementSpecification, StringConcatenationClient>newHashMap();
		CollectionBridge.mapFixed(modelElements, 
				[modelElementInitialization.put(it, getInitializationCode(it, getParameterCallList(generateInputParameters)))]);
		
		val performResponseMethod = generateMethodPerformResponse(effect.codeBlock, modelElements);
		return getOrGenerateMethod(methodName, typeRef(Void.TYPE)) [
			visibility = JvmVisibility.PROTECTED;
			exceptions += typeRef(IOException);
			//parameters += inputParameters;
			body = '''
				getLogger().debug("Called effect «effect.simpleClassName» with input:");
				«FOR inputParameter : inputParameters»
					getLogger().debug("   «inputParameter.parameterType.type.simpleName»: " + «inputParameter.name»);
				«ENDFOR»
				
				«FOR element : modelElements»
					«modelElementInitialization.get(element)»
				«ENDFOR»
				preProcessElements();
				«IF hasExecutionBlock»
					«performResponseMethod.simpleName»(«getParameterCallList(inputParameters)»«
						FOR modelElement : modelElements BEFORE ', ' SEPARATOR ', '»«modelElement.name»«ENDFOR»);
				«ENDIF»
				postProcessElements();
				'''
		];	
	}
		
	private def StringConcatenationClient getCorrespondenceSourceSupplier(CorrespondingModelElementSpecification elementSpecification) {
		val correspondenceSourceMethod = generateMethodGetCorrespondenceSource(elementSpecification);
		return '''() -> «correspondenceSourceMethod.simpleName»(«getParameterCallList(elementSpecification.generateInputParameters())»)'''	
	}
	
	private def StringConcatenationClient getPersistencePathSupplier(CorrespondingModelElementSpecification elementSpecification) {
		val pathFromSourceMethod = generateMethodGetPathFromSource(elementSpecification);
		if (pathFromSourceMethod == null) {
			return null;
		}
		return '''() -> «pathFromSourceMethod.simpleName»(«getParameterCallList(elementSpecification.generateInputParameters())», «elementSpecification.name»)'''	
	}
	
	private def StringConcatenationClient getPreconditionChecker(CorrespondingModelElementRetrieveOrDelete elementSpecification) {
		val affectedElementClass = elementSpecification.elementType.element.javaClass;
		if (elementSpecification.precondition == null) {
			return '''(«affectedElementClass» _element) -> true''';
		}
		val preconditionMethod = generateMethodCorrespondencePrecondition(elementSpecification);
		return '''(«affectedElementClass» _element) -> «preconditionMethod.simpleName»(«getParameterCallList(elementSpecification.generateInputParameters())», _element)'''	
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