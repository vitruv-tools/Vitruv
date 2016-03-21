package edu.kit.ipd.sdq.vitruvius.dsls.response.jvmmodel.classgenerators

import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Effect
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ConcreteTargetModelChange
import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.emf.ecore.EObject
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.generator.ResponseLanguageGeneratorUtils.*;
import org.eclipse.xtext.common.types.JvmVisibility
import org.eclipse.xtext.common.types.JvmOperation
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ModelPathCodeBlock
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.CorrespondingModelElementRetrieveOrDelete
import java.util.ArrayList
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.runtime.ResponseRuntimeHelper
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
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.TargetChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID
import java.util.List
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.helper.ResponseLanguageHelper.*;
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment.AbstractEffectRealization
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.runtime.ResponseExecutionState
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.generator.EffectsGeneratorUtils.*;
import org.eclipse.xtext.common.types.JvmField
import org.eclipse.xtext.common.types.JvmMember
import org.eclipse.xtext.common.types.JvmConstructor
import edu.kit.ipd.sdq.vitruvius.dsls.mirbase.mirBase.ModelElement

abstract class EffectClassGenerator extends ClassGenerator {
	protected final Effect effect;
	protected final boolean hasTargetChange;
	protected final boolean hasConcreteTargetChange;
	protected final boolean hasExecutionBlock;
	protected final boolean isBlackboardAvailable;
	protected extension ResponseElementsCompletionChecker _completionChecker;
	
	public new(Effect effect, TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(typesBuilderExtensionProvider);
		this.effect = effect;
		this.hasTargetChange = effect.targetChange != null;
		this.hasConcreteTargetChange = this.hasTargetChange && effect.targetChange instanceof ConcreteTargetModelChange;
		this.hasExecutionBlock = effect.codeBlock != null;
		this.isBlackboardAvailable = !hasConcreteTargetChange;
		this._completionChecker = new ResponseElementsCompletionChecker();
	}
	
	protected abstract def Iterable<JvmFormalParameter> generateInputParameters(EObject sourceObject); 
	
	protected def getParameterCallList(JvmFormalParameter... parameters) '''
		«FOR parameter : parameters SEPARATOR ','»«parameter.name»«ENDFOR»'''
		
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
			members += generateInputFieldsAndSetterSetterMethods();
			members += generateFacadeClassField;
			members += generatedMethods;
		];
	}
	
	private def JvmMember generateFacadeClassField() {
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
	
	protected def Iterable<JvmMember> generateInputFieldsAndSetterSetterMethods() {
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
	
	/**
	 * Generates method: determineTargetModels
	 * 
	 * <p>Checks the CorrespondenceInstance in the specified blackboard for corresponding objects
	 * according to the type and constraints specified in the response.
	 * 
	 * <p>Method parameters are:
	 * 	<li>1. change: the change event ({@link EChange})</li>
	 *  <li>2. blackboard: the blackboard ({@link Blackboard})</li>
	 * 
	 * <p>Precondition: a metamodel element for the target models is specified in the response
	 */	
	protected def generateMethodGetCorrespondingModelElement(CorrespondingModelElementRetrieveOrDelete correspondingModelElement) {
		val methodName = "getCorrespondingModelElement" + correspondingModelElement.name.toFirstUpper;
		val correspondenceSourceMethod = generateMethodGetCorrespondenceSource(correspondingModelElement)
		
		val affectedElementClass = correspondingModelElement?.elementType.element.javaClass;
		if (affectedElementClass == null) {
			return null;
		}
		
		var returnType = typeRef(affectedElementClass);
		val preconditionMethod = if (correspondingModelElement instanceof CorrespondingModelElementRetrieveOrDelete) {
			if (correspondingModelElement.precondition != null) {
				generateMethodCorrespondencePrecondition(correspondingModelElement);
			}
		}
		return getOrGenerateMethod(methodName, returnType) [
			visibility = JvmVisibility.PRIVATE;
			val inputParameters = effect.generateInputParameters();	
			val blackboardParameter = effect.generateBlackboardParameter();
			parameters += inputParameters;
			parameters += blackboardParameter;
			body = '''
				«List»<«affectedElementClass»> _targetModels = new «ArrayList»<«affectedElementClass»>();
				«EObject» _sourceElement = «correspondenceSourceMethod.simpleName»(«getParameterCallList(inputParameters)»);
				getLogger().debug("Retrieving corresponding " + «affectedElementClass».class.getSimpleName() + " element for: " + _sourceElement);
				«Iterable»<«affectedElementClass»> _correspondingObjects = new «ArrayList»<«affectedElementClass»>();
				try {
					_correspondingObjects = «ResponseRuntimeHelper».getCorrespondingObjectsOfType(
							«blackboardParameter.name».getCorrespondenceInstance(), _sourceElement, «affectedElementClass».class);
				} catch(«RuntimeException» ex) {
					«IF !correspondingModelElement.optional»
						throw new «RuntimeException»("An error occured when retrieved the corresponding elements of: " + _sourceElement);
					«ELSE»
						// The element is optional so there can occur errors when trying to retrieve the corresponding element. Just catch it and go on.
					«ENDIF»
				}
				
				for («affectedElementClass» _potentialTargetElement : _correspondingObjects) {
					«IF preconditionMethod != null»
					if (_potentialTargetElement != null && «preconditionMethod.simpleName»(«getParameterCallList(inputParameters)», _potentialTargetElement)) {
						_targetModels.add(_potentialTargetElement);
					}
					«ELSE»
					_targetModels.add(_potentialTargetElement);
					«ENDIF»
				}
				
				«IF correspondingModelElement instanceof CorrespondingModelElementDelete»
				for («EObject» _targetModel : _targetModels) {
					«ResponseRuntimeHelper».removeCorrespondence(«blackboardParameter.name».getCorrespondenceInstance(), _sourceElement, null, _targetModel, null);
				}
				«ENDIF»
				
				«/*TODO HK This would be a good place for user interaction*/»					
				«IF correspondingModelElement.optional»
				if (_targetModels.size() > 1) {
					throw new «IllegalArgumentException»("There has to be at most one corresponding element of type " + «affectedElementClass».class.getSimpleName() + " for: " + _sourceElement);
				}
				«ELSE»
				if (_targetModels.size() != 1) {
					throw new «IllegalArgumentException»("There has to be exacty one corresponding element of type " + «affectedElementClass».class.getSimpleName() + " for: " + _sourceElement);
				}
				«ENDIF»
				
				return _targetModels.isEmpty() ? null : _targetModels.get(0);
			'''
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
	
	/**
	 * Generates method: generateTargetModel
	 * 
	 * <p>Generates a new target model as specified in the response
	 * 
	 * <p>Method parameters are:
	 * 	<li>1. change: the change event ({@link EChange})</li>
	 *  <li>2. blackboard: the blackboard ({@link Blackboard})</li>
	 * 
	 * <p>Precondition: a metamodel element to be the root of the new model is specified in the response
	 */	
	protected def generateMethodPersistModelElementAsRoot(CorrespondingModelElementCreate createdElement) {
		if (createdElement.persistAsRoot == null) {
			throw new IllegalArgumentException("Given element is not to be persisted as root:");
		}
		val methodName = "persistModelElementAsRoot" + createdElement.name.toFirstUpper;
		
		return getOrGenerateMethod(methodName, typeRef(Void.TYPE)) [ 
			visibility = JvmVisibility.PRIVATE;
			val inputParameters = effect.generateInputParameters();
			val rootParameter = effect.generateModelElementParameter(createdElement);
			parameters += inputParameters;
			parameters += rootParameter;
			exceptions += typeRef(IOException);
			val correspondenceSourceMethod = generateMethodGetCorrespondenceSource(createdElement);
			val pathFromSourceMethod = generateMethodGetPathFromSource(createdElement.persistAsRoot.modelPath);
			body = '''
				«EObject» _sourceElement = «correspondenceSourceMethod.simpleName»(«getParameterCallList(inputParameters)»);
				«String» _relativePath = «pathFromSourceMethod.simpleName»(«getParameterCallList(inputParameters + #[rootParameter])»);
				this.persistModel(_sourceElement, «rootParameter.name», _relativePath, «IF createdElement.persistAsRoot.useRelativeToSource»true«ELSE»false«ENDIF»);
			'''
		];
	}
	
	protected def generateMethodMoveModelOfElement(CorrespondingModelElementRetrieve retrievedElement) {
		if (retrievedElement.moveRoot == null) {
			throw new IllegalArgumentException("Given element is not to be persisted as root:");
		}
		val methodName = "moveModel" + retrievedElement.name.toFirstUpper;
		
		return getOrGenerateMethod(methodName, typeRef(Void.TYPE)) [ 
			visibility = JvmVisibility.PRIVATE;
			val inputParameters = effect.generateInputParameters();
			val rootParameter = effect.generateModelElementParameter(retrievedElement);
			parameters += inputParameters;
			parameters += rootParameter;
			exceptions += typeRef(IOException);
			val correspondenceSourceMethod = generateMethodGetCorrespondenceSource(retrievedElement);
			val pathFromSourceMethod = generateMethodGetPathFromSource(retrievedElement.moveRoot.modelPath);
			body = '''
				«EObject» _sourceElement = «correspondenceSourceMethod.simpleName»(«getParameterCallList(inputParameters)»);
				«String» _relativePath = «pathFromSourceMethod.simpleName»(«getParameterCallList(inputParameters + #[rootParameter])»);
				this.renameModel(_sourceElement, «rootParameter.name», _relativePath, «IF retrievedElement.moveRoot.useRelativeToSource»true«ELSE»false«ENDIF»);
			'''
		];
	}
	
	
	/**
	 * Generates method: generateTargetModel
	 * 
	 * <p>Generates a new target model as specified in the response
	 * 
	 * <p>Method parameters are:
	 * 	<li>1. change: the change event ({@link EChange})</li>
	 *  <li>2. blackboard: the blackboard ({@link Blackboard})</li>
	 * 
	 * <p>Precondition: a metamodel element to be the root of the new model is specified in the response
	 */	
	protected def StringConcatenationClient generateCodeGenerateModelElement(CorrespondingModelElementSpecification elementSpecification) {
		if (!elementSpecification.complete) {
			return '''''';
		}
		val affectedElementClass = elementSpecification.elementType.element;
		val createdClassFactory = affectedElementClass.EPackage.EFactoryInstance.class;
		return '''«affectedElementClass.instanceClass» «elementSpecification.name» = «createdClassFactory».eINSTANCE.create«affectedElementClass.name»();'''
	}
	
	protected def StringConcatenationClient generateCodeAddModelElementCorrespondence(CorrespondingModelElementSpecification elementSpecification, Iterable<JvmFormalParameter> inputParameters) {
		val correspondenceSourceMethod = generateMethodGetCorrespondenceSource(elementSpecification);
		return ''' 
			«ResponseRuntimeHelper».addCorrespondence(this._executionState.getBlackboard().getCorrespondenceInstance(), 
				«correspondenceSourceMethod.simpleName»(«getParameterCallList(inputParameters)»), «elementSpecification.name»);'''
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
		val methodBody = generateMethodExecuteResponseBody(effect.targetChange, inputParameters);
		return getOrGenerateMethod(methodName, typeRef(Void.TYPE)) [
			visibility = JvmVisibility.PROTECTED;
			exceptions += typeRef(IOException);
			//parameters += inputParameters;
			body = '''
				getLogger().debug("Called effect " + this.getClass().getSimpleName() + " with input:");
				«FOR inputParameter : inputParameters»
					getLogger().debug("   " + «inputParameter.parameterType.type.qualifiedName».class.getSimpleName() + ": " + «inputParameter.name»);
				«ENDFOR»
				«methodBody»
				'''
		];	
	}
		
	
	/**
	 * Generates: executeResponse
	 * 
	 * <p>Calls the response execution after checking preconditions for updating the determined target models.
	 * 
	 * <p>Methods parameters are:
	 * <li>1. change: the change event ({@link EChange})
	 * <li>2. blackboard: the {@link Blackboard} containing the {@link CorrespondenceInstance}
	 */
	private def dispatch StringConcatenationClient generateMethodExecuteResponseBody(ConcreteTargetModelChange targetModelChange, 
		Iterable<JvmFormalParameter> inputParameters) {
		val createElements = targetModelChange.createElements.filter[complete];
		val retrieveElements = targetModelChange.retrieveElements.filter[complete];
		val deleteElements = targetModelChange.deleteElements.filter[complete];
		
		val retrieveElementsMethodMap = <CorrespondingModelElementRetrieveOrDelete, JvmOperation>newHashMap();
		CollectionBridge.mapFixed(retrieveElements + deleteElements, 
				[retrieveElementsMethodMap.put(it, generateMethodGetCorrespondingModelElement(it))]);
		CollectionBridge.mapFixed(deleteElements, 
				[retrieveElementsMethodMap.put(it, generateMethodGetCorrespondingModelElement(it))]);
		
		val addCorrespondenceMethodMap = <CorrespondingModelElementSpecification, StringConcatenationClient>newHashMap();
		CollectionBridge.mapFixed(createElements, 
				[addCorrespondenceMethodMap.put(it, generateCodeAddModelElementCorrespondence(it, inputParameters))]);
		
		val modelElementList = createElements + retrieveElements + deleteElements;
		val performResponseMethod = generateMethodPerformResponse(effect.codeBlock, modelElementList);

		val saveAsRootMethodMap = <CorrespondingModelElementCreate, JvmOperation>newHashMap();
		CollectionBridge.mapFixed(createElements.filter[persistAsRoot != null], 
				[saveAsRootMethodMap.put(it, generateMethodPersistModelElementAsRoot(it))]);
		val renameModelMethodMap = <CorrespondingModelElementRetrieve, JvmOperation>newHashMap();
		CollectionBridge.mapFixed(retrieveElements.filter[moveRoot != null], 
				[renameModelMethodMap.put(it, generateMethodMoveModelOfElement(it))]);
		
		val getCorrespondenceSourceMethodMap = <CorrespondingModelElementRetrieve, JvmOperation>newHashMap();
		CollectionBridge.mapFixed(retrieveElements.filter[moveRoot != null],
			[getCorrespondenceSourceMethodMap.put(it, generateMethodGetCorrespondenceSource(it))]);
			
		return '''
				getLogger().debug("Execute response " + this.getClass().getName());
				«FOR element : createElements»
					«generateCodeGenerateModelElement(element)»
				«ENDFOR»
				«FOR element : retrieveElements + deleteElements»
					«val method = retrieveElementsMethodMap.get(element)»
					«method.returnType» «element.name» = «method.simpleName»(«getParameterCallList(inputParameters)
						», _executionState.getBlackboard()«/*identifyingElement.name*/»);
				«ENDFOR»
				«FOR element : retrieveElements»
					«TUID» «element.name»_oldTUID = _executionState.getBlackboard().getCorrespondenceInstance().calculateTUIDFromEObject(«element.name»);
				«ENDFOR»
				«FOR element : deleteElements»
					deleteElement(«element.name»);
				«ENDFOR»
				«IF hasExecutionBlock»
					«performResponseMethod.simpleName»(«getParameterCallList(inputParameters)»«
						FOR modelElement : modelElementList BEFORE ', ' SEPARATOR ', '»«modelElement.name»«ENDFOR»);
				«ENDIF»
				«FOR element : createElements»
					«addCorrespondenceMethodMap.get(element)»
					«IF element.persistAsRoot != null»
						«saveAsRootMethodMap.get(element).simpleName»(«getParameterCallList(inputParameters)», «element.name»);
					«ENDIF»
				«ENDFOR»
				«FOR element: renameModelMethodMap.keySet»
					if («element.name» != null) {
						«renameModelMethodMap.get(element).simpleName»(«getParameterCallList(inputParameters)», «element.name»);
					}
				«ENDFOR»
				«FOR element : retrieveElements»
					if («element.name» != null) {
						_executionState.getBlackboard().getCorrespondenceInstance().updateTUID(«element.name»_oldTUID, «element.name»);
					}
				«ENDFOR»
		'''
	}
	
	
	/*protected def JvmOperation generateMethodMoveModelWithElement(CorrespondingModelElementRetrieve elementRetrieve) {
		if(elementRetrieve.renamedModelFileName == null) {
			throw new IllegalArgumentException("This model element is not to be moved.");
		}
		val methodName = "moveModelWithElement" + elementRetrieve.name.toFirstUpper;
		val newModelPathMethod = generateMethodGetNewModelPath(elementRetrieve.renamedModelFileName, elementRetrieve.name);
		return elementRetrieve.renamedModelFileName.getOrGenerateMethod(methodName, typeRef(String)) [
			visibility = JvmVisibility.PRIVATE;
			parameters += generateChangeParameter();
			body = '''
				
			'''
		];
	}*/
	
	protected def JvmOperation generateMethodGetNewModelPath(ModelPathCodeBlock pathBlock, String modelElementName) {
		val methodName = "getNewModelName" + modelElementName.toFirstUpper;
		
		return pathBlock.getOrGenerateMethod(methodName, typeRef(String)) [
			visibility = JvmVisibility.PRIVATE;
			parameters += generateInputParameters();
			body = pathBlock.code;
		];
	}
	
	/**
	 * Generates: executeResponse
	 * 
	 * <p>Calls the response execution after checking preconditions without a target model.
	 * 
	 * <p>Methods parameters are:
	 * <li>1. change: the change event ({@link EChange})
	 */
	private def dispatch StringConcatenationClient generateMethodExecuteResponseBody(TargetChange targetChange, Iterable<JvmFormalParameter> inputParameters) {
		val JvmOperation performResponseMethod = if (hasExecutionBlock) {
			generateMethodPerformResponse(effect.codeBlock, #[]);
		} else {
			null;
		}
		return '''
			getLogger().debug("Execute response " + this.getClass().getName() + " with no affected model");
			«IF hasExecutionBlock»
				«performResponseMethod.simpleName»(«getParameterCallList(inputParameters)»);
			«ENDIF»
		'''
	}
	
}