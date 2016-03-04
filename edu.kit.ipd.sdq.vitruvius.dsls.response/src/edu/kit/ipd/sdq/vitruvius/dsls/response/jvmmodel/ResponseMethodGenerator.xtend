package edu.kit.ipd.sdq.vitruvius.dsls.response.jvmmodel

import edu.kit.ipd.sdq.vitruvius.dsls.response.jvmmodel.JvmTypesBuilderWithoutAssociations
import org.eclipse.xtext.xbase.jvmmodel.JvmTypeReferenceBuilder
import org.eclipse.xtext.common.types.JvmVisibility
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange
import org.eclipse.xtext.common.types.JvmOperation
import static edu.kit.ipd.sdq.vitruvius.dsls.response.api.generator.ResponseLanguageGeneratorConstants.*;
import org.eclipse.emf.ecore.EObject
import java.util.ArrayList
import org.eclipse.xtext.common.types.JvmFormalParameter
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ConcreteModelElementChange
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.helper.EChangeHelper.*;
import java.util.List
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.runtime.ResponseRuntimeHelper
import java.util.Map
import java.util.HashMap
import org.eclipse.emf.common.util.URI
import java.util.Collections
import edu.kit.ipd.sdq.vitruvius.dsls.response.generator.impl.SimpleTextXBlockExpression
import java.io.IOException
import org.eclipse.xtend2.lib.StringConcatenationClient
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.helper.EChangeHelper.*;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.AtomicFeatureChange
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.helper.ResponseLanguageHelper.*;
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ConcreteTargetModelChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.CorrespondingModelElementSpecification
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge
import org.eclipse.emf.ecore.change.ChangeDescription
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.DeleteRootEObject
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.CorrespondingModelElementDelete
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ModelPathCodeBlock
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.PreconditionCodeBlock
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ExecutionCodeBlock
import org.eclipse.xtext.common.types.JvmGenericType
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.CorrespondingModelElementRetrieve
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.CorrespondingModelElementRetrieveOrDelete
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.CorrespondingModelElementCreate
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID

class ResponseMethodGenerator {
	@Extension protected static JvmTypeReferenceBuilder _typeReferenceBuilder;
	protected extension JvmTypesBuilderWithoutAssociations _typesBuilder;
	
	protected final Response response;
	protected final boolean hasTargetChange;
	protected final boolean hasConcreteTargetChange;
	protected final boolean hasPreconditionBlock;
	protected final boolean hasExecutionBlock;
	protected final boolean isBlackboardAvailable;
	protected final Class<? extends EChange> change;
	
	protected Map<String, JvmOperation> methodMap;
	
	private extension IJvmOperationRegistry _operationRegistry;
	private extension ResponseParameterGenerator _parameterGenerator;
	private extension final ResponseElementsCompletionChecker _completionChecker; 
	
	new(Response response, IJvmOperationRegistry registry, JvmTypeReferenceBuilder typeReferenceBuilder, JvmTypesBuilderWithoutAssociations jvmTypesBuilder) {
		this.response = response;
		this.hasTargetChange = response.effects.targetChange != null;
		this.hasConcreteTargetChange = this.hasTargetChange && response.effects.targetChange instanceof ConcreteTargetModelChange;
		this.hasPreconditionBlock = response.trigger.precondition != null;
		this.hasExecutionBlock = response.effects.codeBlock != null;
		this.isBlackboardAvailable = !hasConcreteTargetChange;
		this.change = response.trigger.generateEChangeInstanceClass();
		this.methodMap = new HashMap<String, JvmOperation>();
		_operationRegistry = registry;
		_parameterGenerator = new ResponseParameterGenerator(response, typeReferenceBuilder, jvmTypesBuilder);
		_completionChecker = new ResponseElementsCompletionChecker();
		_typesBuilder = jvmTypesBuilder;
		_typeReferenceBuilder = typeReferenceBuilder;
	}
	
	protected def generateConstructor(JvmGenericType clazz) {
		clazz.toConstructor [
			parameters += generateParameter("userInteracting", UserInteracting);
			visibility = JvmVisibility.PUBLIC;
			body = '''super(userInteracting);'''
		]
	}
	
	/**
	 * Generates method: applyChange
	 * 
	 * <p>Applies the given change to the specified response. Executes the response if all preconditions are fulfilled.
	 * 
	 * <p>Method parameters are:
	 * <li>1. change: the change event ({@link EChange})
	 * <li>2. blackboard: the {@link Blackboard} containing the {@link CorrespondenceInstance} 
	 * 
	 */
	protected def generateMethodApplyChange() {
		val methodName = RESPONSE_APPLY_METHOD_NAME;

		val checkPreconditionMethod = generateInterfacePreconditionMethod();
		val executeResponseMethod = generateMethodExecuteResponse();		
		return getOrGenerateMethod(methodName, typeRef(TransformationResult)) [
			visibility = JvmVisibility.PUBLIC;
			val changeParameter = generateUntypedChangeParameter();
			val blackboardParameter = generateBlackboardParameter();
			parameters += changeParameter;
			parameters += blackboardParameter;
			val typedChangeName = "typedChange";
			body = '''
				LOGGER.debug("Called response " + this.getClass().getName() + " with event " + «changeParameter.name»);
				
				// Check if the event matches the trigger of the response
				if (!«checkPreconditionMethod.simpleName»(«changeParameter.name»)) {
					return new «TransformationResult»();
				}
				
				«typedChangeString» «typedChangeName» = («typedChangeString»)«changeParameter.name»;
				«TransformationResult» transformationResult = new «TransformationResult»();
				try {
					«executeResponseMethod.simpleName»(«typedChangeName», «blackboardParameter.name», transformationResult);
				} catch («Exception» exception) {
					// If an error occured during execution, avoid an application shutdown and print the error.
					LOGGER.error(exception.getClass().getSimpleName() + " (" + exception.getMessage() + ") during execution of response: " + this.getClass().getName());
				}
				
				return transformationResult;'''
		];
	}
	
	private def StringConcatenationClient getTypedChangeString() '''
		«val trigger = response.trigger
		»«change»«IF trigger instanceof ConcreteModelElementChange»<«getGenericTypeParameterOfChange(trigger)»>«ENDIF»'''
		
	
		
	protected def generateMethodGetTrigger() {
		val methodName = "getTrigger";
		return getOrGenerateMethod(methodName, typeRef(Class, wildcardExtends(typeRef(EChange)))) [
			static = true;
			body = '''return «change».class;'''
		];
	} 
	
	/** 
	 * Generated method: checkPrecondition : boolean
	 * 
	 * <p>Evaluates the precondition specified in the response
	 * 
	 * <p>Methods parameters are: 
	 * 	<li>1. change: the change event ({@link EChange})</li>
	 * 
	 * <p>Precondition: precondition code block must exist.
	 */
	protected def generateInterfacePreconditionMethod() {
		val methodName = PRECONDITION_METHOD_NAME;
		val operationsToCallBeforeCast = getPreconditionMethodsBeforeCast();
		val operationsToCallAfterCast = getPreconditionMethodsAfterCast();
		return getOrGenerateMethod(methodName, typeRef(Boolean.TYPE)) [
			val changeParameter = generateUntypedChangeParameter(response);
			val typedChangeClass = change;
			visibility = JvmVisibility.PUBLIC;
			parameters += changeParameter
			body = '''
				«FOR operation : operationsToCallBeforeCast»
					if (!«operation.simpleName»(«changeParameter.name»)) {
						return false;
					}
				«ENDFOR»
				«typedChangeClass» typedChange = («typedChangeClass»)«changeParameter.name»;
				«FOR operation : operationsToCallAfterCast»
					if (!«operation.simpleName»(typedChange)) {
						return false;
					}
				«ENDFOR»
				LOGGER.debug("Passed precondition check of response " + this.getClass().getName());
				return true;
				'''
		];
	}
	
	protected def generateMethodCheckChangeType() {
		val methodName = "checkChangeType";
		return getOrGenerateMethod(methodName, typeRef(Boolean.TYPE)) [
			val changeParameter = generateUntypedChangeParameter;
			visibility = JvmVisibility.PRIVATE;
			parameters += changeParameter;
			val changeType = if (!change.equals(EChange)) {
				typeRef(change, wildcard);
			} else {
				typeRef(change);
			}
			body = '''return «changeParameter.name» instanceof «changeType»;''';
		];	
	}
	
	/** 
	 * Generated method: checkPrecondition : boolean
	 * 
	 * <p>Evaluates the precondition specified in the response
	 * 
	 * <p>Methods parameters are: 
	 * 	<li>1. change: the change event ({@link EChange})</li>
	 * 
	 * <p>Precondition: precondition code block must exist.
	 */
	protected def JvmOperation generateUserDefinedPreconditionMethod(PreconditionCodeBlock preconditionBlock) {
		val methodName = TRIGGER_PRECONDITION_METHOD_NAME;
		
		return preconditionBlock.getOrGenerateMethod(methodName, typeRef(Boolean.TYPE)) [
			visibility = JvmVisibility.PRIVATE;
			parameters += generateChangeParameter(preconditionBlock);
			body = preconditionBlock.code;
		];		
	}
	
	protected def JvmOperation generateMethodGetPathFromSource(ModelPathCodeBlock modelPathCodeBlock) {
		val methodName = "getModelPath";
		
		return modelPathCodeBlock.getOrGenerateMethod(methodName, typeRef(String)) [
			visibility = JvmVisibility.PRIVATE;
			parameters += generateChangeParameter(modelPathCodeBlock);
			body = modelPathCodeBlock.code;
		];		
	}
	
	protected def generateMethodCorrespondencePrecondition(CorrespondingModelElementRetrieveOrDelete correspondingModelElementSpecification) {
		val methodName = "getCorrespondingModelElementsPrecondition" + correspondingModelElementSpecification.name.toFirstUpper;
		return correspondingModelElementSpecification.precondition.getOrGenerateMethod(methodName, typeRef(Boolean.TYPE)) [
			visibility = JvmVisibility.PRIVATE;
			val changeParameter = generateChangeParameter();	
			val elementParameter = generateModelElementParameter(correspondingModelElementSpecification);
			parameters += changeParameter;
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
		
		val affectedElementClass = correspondingModelElement?.elementType.javaClass;
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
			val changeParameter = generateChangeParameter();	
			val blackboardParameter = generateBlackboardParameter();
			parameters += changeParameter;
			parameters += blackboardParameter;
			body = '''
				«List»<«affectedElementClass»> _targetModels = new «ArrayList»<«affectedElementClass»>();
				«EObject» _sourceElement = «correspondenceSourceMethod.simpleName»(«changeParameter.name»);
				«EObject» _sourceParent = null;
				LOGGER.debug("Retrieving corresponding " + «affectedElementClass».class.getSimpleName() + " element for: " + _sourceElement);
				«/* TODO HK Fix this after the new change MM is implemented:
				 * Delete objects (except root one) are removed from the model and now contained in the ChangeDescription,
				 * so correspondences cannot be resolved
				 */»
				«IF !(response.trigger instanceof ConcreteModelElementChange && DeleteRootEObject.equals(change))»
				if (_sourceElement.eContainer() instanceof «ChangeDescription») {
					_sourceParent = «changeParameter.name».getOldAffectedEObject();
				}
				«ENDIF»
				«Iterable»<«affectedElementClass»> _correspondingObjects = «ResponseRuntimeHelper».getCorrespondingObjectsOfType(
						«blackboardParameter.name».getCorrespondenceInstance(), _sourceElement, _sourceParent, «affectedElementClass».class);
				
				for («affectedElementClass» _potentialTargetElement : _correspondingObjects) {
					«IF preconditionMethod != null»
					if (_potentialTargetElement != null && «preconditionMethod.simpleName»(«changeParameter.name», _potentialTargetElement)) {
						_targetModels.add(_potentialTargetElement);
					}
					«ELSE»
					_targetModels.add(_potentialTargetElement);
					«ENDIF»
				}
				
				«IF correspondingModelElement instanceof CorrespondingModelElementDelete»
				for («EObject» _targetModel : _targetModels) {
					«ResponseRuntimeHelper».removeCorrespondence(«blackboardParameter.name».getCorrespondenceInstance(), _sourceElement, _sourceParent, _targetModel, null);
				}
				«ENDIF»
				
				«/*TODO HK This would be a good place for user interaction*/»					
				if (_targetModels.size() != 1) {
					throw new «IllegalArgumentException»("There has to be exacty one corresponding element.");
				}
				
				return _targetModels.get(0);
			'''
		];
	}
	
	protected def generateMethodGetCorrespondenceSource(CorrespondingModelElementSpecification modelElementSpecification) {
		val methodName = "getCorrepondenceSource" + modelElementSpecification.name.toFirstUpper;
		
		return modelElementSpecification.correspondenceSource.getOrGenerateMethod(methodName, typeRef(EObject)) [
			visibility = JvmVisibility.PRIVATE;
			parameters += generateChangeParameter(modelElementSpecification);
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
			val changeParameter = generateChangeParameter();
			val blackboardParameter = generateBlackboardParameter();
			val rootParameter = generateModelElementParameter(createdElement);
			val transformationResultParameter = generateParameter("transformationResult", TransformationResult);
			parameters += changeParameter;
			parameters += blackboardParameter;
			parameters += rootParameter;
			parameters += transformationResultParameter;
			exceptions += typeRef(IOException);
			val correspondenceSourceMethod = generateMethodGetCorrespondenceSource(createdElement);
			val pathFromSourceMethod = generateMethodGetPathFromSource(createdElement.persistAsRoot.modelPath);
			body = '''
				«EObject» sourceElement = «correspondenceSourceMethod.simpleName»(«changeParameter.name»);
				«String» relativePath = «pathFromSourceMethod.simpleName»(«changeParameter.name»);
				«URI» resourceURI = «ResponseRuntimeHelper».«
				IF (createdElement.persistAsRoot.useRelativeToSource)»getURIFromSourceResourceFolder«
				ELSEIF (createdElement.persistAsRoot.useRelativeToProject)»getURIFromSourceProjectFolder«
				ENDIF»(sourceElement, relativePath, «blackboardParameter.name»);
				«transformationResultParameter.name».addRootEObjectToSave(«rootParameter.name», «VURI».getInstance(resourceURI));
				//«blackboardParameter.name».getModelProviding().saveModelInstanceOriginalWithEObjectAsOnlyContent(«VURI».getInstance(resourceURI), «rootParameter.name», null);
				//«blackboardParameter.name».getCorrespondenceInstance().createAndAddCorrespondence(«Collections».singletonList(sourceElement), «Collections».singletonList(«rootParameter.name»));
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
	
	protected def StringConcatenationClient generateCodeAddModelElementCorrespondence(CorrespondingModelElementSpecification elementSpecification, JvmFormalParameter changeParameter, JvmFormalParameter blackboardParameter) {
		val correspondenceSourceMethod = generateMethodGetCorrespondenceSource(elementSpecification);
		return ''' 
			«ResponseRuntimeHelper».addCorrespondence(«blackboardParameter.name».getCorrespondenceInstance(), 
				«correspondenceSourceMethod.simpleName»(«changeParameter.name»), «elementSpecification.name»);'''
	}

	protected def Iterable<JvmOperation> getPreconditionMethodsBeforeCast() {
		val methods = <JvmOperation>newArrayList();
		methods += generateMethodCheckChangeType();
		if (response.trigger instanceof ConcreteModelElementChange) {
			methods += generateMethodCheckChangedObject();
		}
		return methods;
	}
	
	protected def Iterable<JvmOperation> getPreconditionMethodsAfterCast() {
		val methods = <JvmOperation>newArrayList();
		if (hasPreconditionBlock) {
			methods += generateUserDefinedPreconditionMethod(response.trigger.precondition);
		}
		return methods;
	}
	
	/**
	 * Generates method: checkChangedObject : boolean
	 * 
	 * <p>Checks if the currently changed object equals the one specified in the response.
	 * 
	 * <p>Methods parameters are:
	 * 	<li>1. change: the change event ({@link EChange})</li>
	 */
	protected def generateMethodCheckChangedObject() {
		val methodName = "checkChangedObject";
		
		if (!(response.trigger instanceof ConcreteModelElementChange)) {
			throw new IllegalStateException();
		}
		
		val changeEvent = response.trigger;
		val changedElement = response.trigger.changedModelElementClass;
		return getOrGenerateMethod(methodName, typeRef(Boolean.TYPE)) [
			visibility = JvmVisibility.PRIVATE;
			val changeParameter = generateUntypedChangeParameter();
			parameters += changeParameter;
			val typedChangeName = "typedChange";
			val typedChangeClassGenericString = if (!change.equals(EChange)) "<?>" else ""
			body = '''
				«change»«typedChangeClassGenericString» «typedChangeName» = («change»«typedChangeClassGenericString»)«changeParameter.name»;
				«EObject» changedElement = «typedChangeName».get«changeEvent.EChangeFeatureNameOfChangedObject.toFirstUpper»();
				«IF changeEvent instanceof AtomicFeatureChange»
					«/* TODO HK We could compare something more safe like <MM>PackageImpl.eINSTANCE.<ELEMENT>_<FEATURE>.*/»
					if (!«typedChangeName».getAffectedFeature().getName().equals("«changeEvent.changedFeature.feature.name»")) {
						return false;
					}
				«ENDIF»
				if (changedElement instanceof «changedElement.instanceClass») {
					return true;
				}
				return false;
			'''
		];
	}
	
	protected def generateMethodPerformResponse(ExecutionCodeBlock executionBlock, CorrespondingModelElementSpecification... modelElements) {
		if (!hasExecutionBlock) {
			return null;
		}
		val methodName = "performResponseTo";
		return executionBlock.getOrGenerateMethod(methodName, typeRef(Void.TYPE)) [
			visibility = JvmVisibility.PRIVATE;
			parameters += generateChangeParameter;
			val generatedMethod = it;
			parameters += modelElements.map[generateModelElementParameter(generatedMethod, it)];
			if (isBlackboardAvailable) {
				parameters += generateBlackboardParameter;
				parameters += generateParameter("transformationResult", TransformationResult);
			}
			val code = executionBlock.code;
			if (code instanceof SimpleTextXBlockExpression) {
				body = code.text;
			} else {
				body = code;
			}
		];	
	}
	
	protected def generateMethodExecuteResponse() {
		val methodName = "executeResponse";
		val changeParameter = response.generateChangeParameter();
		val blackboardParameter = response.generateBlackboardParameter();
		val transformationResultParameter = response.generateParameter("_transformationResult", TransformationResult);
		val methodBody = if (hasConcreteTargetChange) {
			val modelRootChange = response.effects.targetChange as ConcreteTargetModelChange;
			generateMethodExecuteResponseBody(modelRootChange, changeParameter, blackboardParameter, transformationResultParameter);
		} else {
			generateMethodExecuteResponseBody(changeParameter, blackboardParameter, transformationResultParameter);
		}
		return getOrGenerateMethod(methodName, typeRef(Void.TYPE)) [
			visibility = JvmVisibility.PRIVATE;
			exceptions += typeRef(IOException);
			parameters += changeParameter;
			parameters += blackboardParameter;
			parameters += transformationResultParameter;
			body = methodBody;
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
		JvmFormalParameter changeParameter, JvmFormalParameter blackboardParameter, JvmFormalParameter transformationResultParameter) {
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
				[addCorrespondenceMethodMap.put(it, generateCodeAddModelElementCorrespondence(it, changeParameter, blackboardParameter))]);
		
		val modelElementList = createElements + retrieveElements + deleteElements;
		val performResponseMethod = generateMethodPerformResponse(response.effects.codeBlock, modelElementList);

		val saveAsRootMethodMap = <CorrespondingModelElementCreate, JvmOperation>newHashMap();
		CollectionBridge.mapFixed(createElements.filter[persistAsRoot != null], 
				[saveAsRootMethodMap.put(it, generateMethodPersistModelElementAsRoot(it))]);
		val renameModelMethodMap = <CorrespondingModelElementRetrieve, JvmOperation>newHashMap();
		CollectionBridge.mapFixed(retrieveElements.filter[renamedModelFileName != null], 
				[renameModelMethodMap.put(it, generateMethodGetNewModelPath(it.renamedModelFileName, it.name))]);
		
		val getCorrespondenceSourceMethodMap = <CorrespondingModelElementRetrieve, JvmOperation>newHashMap();
		CollectionBridge.mapFixed(retrieveElements.filter[renamedModelFileName != null],
			[getCorrespondenceSourceMethodMap.put(it, generateMethodGetCorrespondenceSource(it))]);
			
		return '''
				LOGGER.debug("Execute response " + this.getClass().getName());
				«FOR element : createElements»
					«generateCodeGenerateModelElement(element)»
				«ENDFOR»
				«FOR element : retrieveElements + deleteElements»
					«val method = retrieveElementsMethodMap.get(element)»
					«method.returnType» «element.name» = «method.simpleName»(«
						changeParameter.name», «blackboardParameter.name»«/*identifyingElement.name*/»);
				«ENDFOR»
				«FOR element : retrieveElements»
					«TUID» «element.name»_oldTUID = «blackboardParameter.name».getCorrespondenceInstance().calculateTUIDFromEObject(«element.name»);
				«ENDFOR»
				«IF hasExecutionBlock»
					«performResponseMethod.simpleName»(«changeParameter.name»«
						FOR modelElement : modelElementList BEFORE ', ' SEPARATOR ', '»«modelElement.name»«ENDFOR»);
				«ENDIF»
				«FOR element : createElements»
					«addCorrespondenceMethodMap.get(element)»
					«IF element.persistAsRoot != null»
						«saveAsRootMethodMap.get(element).simpleName»(«changeParameter.name», «blackboardParameter.name», «element.name», «transformationResultParameter.name»);
					«ENDIF»
				«ENDFOR»
				«FOR element : deleteElements»
					deleteElement(«element.name»);
				«ENDFOR»
				«FOR element: renameModelMethodMap.keySet»
					«EObject» «element.name»_sourceElement = «getCorrespondenceSourceMethodMap.get(element).simpleName»(«changeParameter.name»);
					String «element.name»_newModelPath = «renameModelMethodMap.get(element).simpleName»(«changeParameter.name»);
					«/* TODO HK The third parameter was the identifying element before since it is persisted when renaming a basic component and so does not throw an exception */»
					LOGGER.debug("Move model with element " + «element.name»_sourceElement + " to " + «element.name»_newModelPath);
					«ResponseRuntimeHelper».renameModel(«blackboardParameter.name», «element.name»_sourceElement, «element.name», «element.name»_newModelPath, «transformationResultParameter.name»);
				«ENDFOR»
				«FOR element : retrieveElements»
					«blackboardParameter.name».getCorrespondenceInstance().updateTUID(«element.name»_oldTUID, «element.name»);
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
			parameters += generateChangeParameter();
			body = pathBlock.code;
		];
	}
	
	/**
	 * Generates: executeResponse
	 * 
	 * <p>Calls the response execution after checking preconditions for creating the target model.
	 * 
	 * <p>Methods parameters are:
	 * <li>1. change: the change event ({@link EChange})
	 * <li>2. blackboard: the {@link Blackboard} containing the {@link CorrespondenceInstance}
	 */
	/*private def dispatch StringConcatenationClient generateMethodExecuteResponseBody(ConcreteTargetModelCreate modelRootCreate, 
		JvmFormalParameter changeParameter, JvmFormalParameter blackboardParameter, JvmFormalParameter transformationResultParameter) {
		if (!modelRootCreate.rootElement.complete) {
			return '''''';
		}
		val rootElement = modelRootCreate.rootElement;
		val createElements = modelRootCreate.createElements;
		val generateTargetModelMethod = generateMethodGenerateTargetModel(modelRootCreate);
		val addCorrespondenceMethodMap = <CorrespondingModelElementSpecification, StringConcatenationClient>newHashMap();
		addCorrespondenceMethodMap.put(rootElement, generateCodeAddModelElementCorrespondence(rootElement, changeParameter, blackboardParameter));
		CollectionBridge.mapFixed(createElements, 
				[addCorrespondenceMethodMap.put(it, generateCodeAddModelElementCorrespondence(it, changeParameter, blackboardParameter))]);
		
		val modelElementList = #[rootElement] + createElements;
		val performResponseMethod = generateMethodPerformResponse(response.effects.codeBlock, modelElementList);
		return '''
			«generateCodeGenerateModelElement(modelRootCreate.rootElement)»
			«FOR element : createElements»
				«generateCodeGenerateModelElement(element)»
			«ENDFOR»
			«IF hasExecutionBlock»
				«performResponseMethod.simpleName»(«changeParameter.name»«
					FOR modelElement : modelElementList BEFORE ', ' SEPARATOR ', '»«modelElement.name»«ENDFOR»);
			«ENDIF»
			«FOR element : createElements»
				«addCorrespondenceMethodMap.get(element)»
			«ENDFOR»
			«generateTargetModelMethod.simpleName»(«changeParameter.name», «blackboardParameter.name», «modelRootCreate.rootElement.name», «transformationResultParameter.name»);
		'''
	}	*/
	
	
	/**
	 * Generates: executeResponse
	 * 
	 * <p>Calls the response execution after checking preconditions without a target model.
	 * 
	 * <p>Methods parameters are:
	 * <li>1. change: the change event ({@link EChange})
	 */
	private def StringConcatenationClient generateMethodExecuteResponseBody(JvmFormalParameter changeParameter, JvmFormalParameter blackboardParameter, JvmFormalParameter transformationResultParameter) {
		val JvmOperation performResponseMethod = if (hasExecutionBlock) {
			generateMethodPerformResponse(response.effects.codeBlock, #[]);
		} else {
			null;
		}
		return '''
			LOGGER.debug("Execute response " + this.getClass().getName() + " with no affected model");
			«IF hasExecutionBlock»
				«performResponseMethod.simpleName»(«changeParameter.name», «blackboardParameter.name», «transformationResultParameter.name»);
			«ENDIF»
		'''
	}
	
}