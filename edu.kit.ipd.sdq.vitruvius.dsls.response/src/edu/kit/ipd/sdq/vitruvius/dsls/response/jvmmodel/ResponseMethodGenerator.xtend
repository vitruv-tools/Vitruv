package edu.kit.ipd.sdq.vitruvius.dsls.response.jvmmodel

import edu.kit.ipd.sdq.vitruvius.dsls.response.jvmmodel.JvmTypesBuilderWithoutAssociations
import org.eclipse.xtext.xbase.jvmmodel.JvmTypeReferenceBuilder
import org.eclipse.xtext.common.types.JvmVisibility
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange
import org.eclipse.xtext.common.types.JvmOperation
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ConcreteTargetModelRootUpdate
import static edu.kit.ipd.sdq.vitruvius.dsls.response.api.generator.ResponseLanguageGeneratorConstants.*;
import org.eclipse.emf.ecore.EObject
import java.util.ArrayList
import org.eclipse.xtext.common.types.JvmFormalParameter
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ConcreteTargetModelRootChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ConcreteModelElementChange
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.helper.EChangeHelper.*;
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.PreconditionBlock
import java.util.List
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.runtime.ResponseRuntimeHelper
import java.util.Map
import java.util.HashMap
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ConcreteTargetModelRootCreate
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge
import java.util.Collections
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ConcreteTargetModelRootDelete
import edu.kit.ipd.sdq.vitruvius.dsls.response.generator.impl.SimpleTextXBlockExpression
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence
import java.io.IOException
import org.eclipse.xtend2.lib.StringConcatenationClient
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ExecutionBlock
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.helper.EChangeHelper.*;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import org.apache.log4j.Level
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.AtomicFeatureChange
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.helper.ResponseLanguageHelper.*;

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
	
	new(Response response, IJvmOperationRegistry registry, JvmTypeReferenceBuilder typeReferenceBuilder, JvmTypesBuilderWithoutAssociations jvmTypesBuilder) {
		this.response = response;
		this.hasTargetChange = response.effects.targetChange != null;
		this.hasConcreteTargetChange = this.hasTargetChange && response.effects.targetChange instanceof ConcreteTargetModelRootChange;
		this.hasPreconditionBlock = response.trigger.precondition != null;
		this.hasExecutionBlock = response.effects.codeBlock != null;
		this.isBlackboardAvailable = !hasConcreteTargetChange;
		this.change = response.trigger.generateEChangeInstanceClass();
		this.methodMap = new HashMap<String, JvmOperation>();
		_operationRegistry = registry;
		_parameterGenerator = new ResponseParameterGenerator(response, typeReferenceBuilder, jvmTypesBuilder);
		_typesBuilder = jvmTypesBuilder;
		_typeReferenceBuilder = typeReferenceBuilder;
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
		
		return getOrGenerateMethod(methodName, typeRef(TransformationResult)) [
			visibility = JvmVisibility.PUBLIC;
			val changeParameter = generateUntypedChangeParameter();
			val blackboardParameter = generateBlackboardParameter();
			parameters += changeParameter;
			parameters += blackboardParameter;
			val typedChangeName = "typedChange";
			val checkPreconditionMethod = generateInterfacePreconditionMethod();
			val executeResponseMethod = generateMethodExecuteResponse();
			body = '''
				LOGGER.setLevel(«Level».DEBUG);
				LOGGER.debug("Called response " + this.getClass().getName() + " with event " + «changeParameter.name»);
				
				// Check if the event matches the trigger of the response
				if (!«checkPreconditionMethod.simpleName»(«changeParameter.name»)) {
					return new «TransformationResult»();
				}
				
				«typedChangeString» «typedChangeName» = («typedChangeString»)«changeParameter.name»;
				try {
					«executeResponseMethod.simpleName»(«typedChangeName», «blackboardParameter.name»);
				} catch («IOException» exception) {
					LOGGER.error("IOException during execution of response: " + this.getClass().getName());
				}
				
				return new «TransformationResult»();'''
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
	protected def JvmOperation generateUserDefinedPreconditionMethod(PreconditionBlock preconditionBlock) {
		val methodName = TRIGGER_PRECONDITION_METHOD_NAME;
		
		return preconditionBlock.getOrGenerateMethod(methodName, typeRef(Boolean.TYPE)) [
			visibility = JvmVisibility.PRIVATE;
			parameters += generateChangeParameter(preconditionBlock);
			body = preconditionBlock.code;
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
	protected def generateMethodDetermineTargetModels(ConcreteTargetModelRootUpdate updatedModel) {
		val methodName = "determineTargetModels";
		
		val affectedElementClass = updatedModel.rootModelElement.element;
		return getOrGenerateMethod(methodName, typeRef(Iterable, typeRef(affectedElementClass.instanceClass))) [
			visibility = JvmVisibility.PRIVATE;
			val changeParameter = generateChangeParameter(); 
			val blackboardParameter = generateBlackboardParameter();
			parameters += changeParameter;
			parameters += blackboardParameter;
			val correspondenceSourceMethod = generateMethodGetCorrespondenceSource(updatedModel)
			body = '''
				«List»<«affectedElementClass.instanceClass»> targetModels = new «ArrayList»<«affectedElementClass.instanceClass»>();
				EObject objectToGetCorrespondencesFor = «correspondenceSourceMethod.simpleName»(«changeParameter.name»);
				«Iterable»<«affectedElementClass.instanceClass»> correspondingObjects = «ResponseRuntimeHelper».getCorrespondingObjectsOfType(
					«blackboardParameter.name».getCorrespondenceInstance(), objectToGetCorrespondencesFor, «affectedElementClass.instanceClass».class);
				for («affectedElementClass.instanceClass» object : correspondingObjects) {
					targetModels.add(object);
				}
				return targetModels;
			'''
		];
	}
	
	protected def generateMethodGetCorrespondenceSource(ConcreteTargetModelRootChange changedModel) {
		val methodName = "getCorrepondenceSource";
		
		return  changedModel.getOrGenerateMethod(methodName, typeRef(EObject)) [
			visibility = JvmVisibility.PRIVATE;
			parameters += generateChangeParameter(changedModel);
			val correspondenceSourceBlock = changedModel.correspondenceSource.code;
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
	protected def generateMethodGenerateTargetModel(ConcreteTargetModelRootCreate createdModel) {
		val methodName = "generateTargetModel";
		val affectedElementClass = createdModel.rootModelElement.element;
		
		return getOrGenerateMethod(methodName, typeRef(affectedElementClass.instanceClass)) [ 
			visibility = JvmVisibility.PRIVATE;
			val changeParameter = generateChangeParameter(createdModel);
			val blackboardParameter = generateBlackboardParameter(createdModel);
			parameters += changeParameter;
			parameters += blackboardParameter;
			exceptions += typeRef(IOException);
			val correspondenceSourceMethod = generateMethodGetCorrespondenceSource(createdModel);
			/* old sourceElement = «changeParameter.name».«change.EChangeFeatureNameOfChangedObject»*/
			body = '''
				«val createdClassFactory = affectedElementClass.EPackage.EFactoryInstance.class»
				«affectedElementClass.instanceClass» newRoot = «createdClassFactory».eINSTANCE.create«affectedElementClass.name»();
				«EObject» sourceElement = «correspondenceSourceMethod.simpleName»(«changeParameter.name»);
				«String»[] newModelFileSegments = "«createdModel.relativeToSourcePath»".split("/");
				«val newModelFileSegments = createdModel.relativeToSourcePath.split("/")»
				«IF !newModelFileSegments.last.contains(".")»
					// No file extension was specified, add the first one that is the valid for the metamodel
					newModelFileSegments[newModelFileSegments.length - 1] = newModelFileSegments[newModelFileSegments.length - 1] 
						+ "." + «blackboardParameter.name».getCorrespondenceInstance().getMapping().getMetamodelB().getFileExtensions()[0];
				«ENDIF»
				«URI» newResourceURI = sourceElement.eResource().getURI().trimSegments(1).appendSegments(newModelFileSegments);
				«Resource» newModelResource = new «ResourceSetImpl»().createResource(newResourceURI);
				«/* TODO HK Replace with correct id definition resp. let user declare id in response */»
				newRoot.setId(newModelFileSegments[newModelFileSegments.length - 1].split("\\.")[0]);
				newModelResource.getContents().add(newRoot);
				«blackboardParameter.name».getCorrespondenceInstance().createAndAddCorrespondence(«Collections».singletonList(sourceElement), «Collections».singletonList(newRoot));
				«EcoreResourceBridge».saveResource(newModelResource);
				return newRoot;
			'''
		];
	}
	
	/**
	 * Generates method: deleteTargetModel
	 * 
	 * <p>Delete the target model as specified in the response
	 * 
	 * <p>Method parameters are:
	 * 	<li>1. change: the change event ({@link EChange})</li>
	 *  <li>2. blackboard: the blackboard ({@link Blackboard})</li>
	 */	
	protected def generateMethodDeleteTargetModels(ConcreteTargetModelRootDelete deletedModel) {
		val methodName = "deleteTargetModels";
		val affectedElementClass = deletedModel.rootModelElement.element;
		
		return getOrGenerateMethod(methodName, typeRef(Void.TYPE)) [
			visibility = JvmVisibility.PRIVATE;
			val changeParameter = generateChangeParameter(deletedModel);
			val blackboardParameter = generateBlackboardParameter(deletedModel);
			parameters += changeParameter;
			parameters += blackboardParameter;
			exceptions += typeRef(IOException);
			val correspondenceSourceMethod = generateMethodGetCorrespondenceSource(deletedModel);
			/* old sourceElement = «changeParameter.name».«change.EChangeFeatureNameOfChangedObject»*/
			body = '''
				«EObject» objectToGetCorrespondencesFor =«correspondenceSourceMethod.simpleName»(«changeParameter.name»);
				«Iterable»<«Correspondence»> correspondences = «ResponseRuntimeHelper».getCorrespondencesWithTargetType(«blackboardParameter.name».getCorrespondenceInstance(),
					objectToGetCorrespondencesFor, «affectedElementClass.instanceClass».class);
				for («Correspondence» correspondence : correspondences) {
					«Iterable»<«affectedElementClass.instanceClass»> targetModels = «ResponseRuntimeHelper».getCorrespondingObjectsOfTypeInCorrespondence(correspondence, objectToGetCorrespondencesFor, «affectedElementClass.instanceClass».class);
					«blackboardParameter.name».getCorrespondenceInstance().removeCorrespondencesAndDependendCorrespondences(correspondence);
					for («EObject» targetModel : targetModels) {
						targetModel.eResource().delete(«Collections».EMPTY_MAP);
					}
				}
			'''
		];
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
	
	protected def generateMethodPerformResponse(ExecutionBlock executionBlock) {
		val methodName = "performResponseTo";
		return executionBlock.getOrGenerateMethod(methodName, typeRef(Void.TYPE)) [
			visibility = JvmVisibility.PRIVATE;
			parameters += generateChangeParameter;
			if (hasTargetChange) {
				parameters += generateTargetModelParameter;
			}
			if (isBlackboardAvailable) {
				parameters += generateBlackboardParameter;
			}
			val code = executionBlock.code;
			if (code instanceof SimpleTextXBlockExpression) {
				body = code.text;
			} else {
				body = code;
			}
		];	
	}
	
	
	/**
	 * Generates method: performResponseTo : void
	 * 
	 * <p>Executes the response logic.
	 * 
	 * <p>Method parameters are:
	 * 	<li>1. change: the change event ({@link EChange})</li>
	 *  <li>2. targetModel: the target model root element if specified and if it is an update operation</li>
	 * 
	 * <p>Precondition: Executed codeBlock must exist.
	 */
	/*protected def generateMethodPerformResponse(EClass targetModelElement) '''
		private def performResponseTo(«changeEventTypeString» «CHANGE_PARAMETER_NAME»«
			IF hasTargetChange», «ih.typeRef(targetModelElement)
			» «TARGET_MODEL_PARAMETER_NAME»«ENDIF»)«response.effects.codeBlock.code.XBlockExpressionText»
	'''*/
	
	/**
	 * Generates method: performResponseTo : void
	 * 
	 * <p>Executes the response logic.
	 * 
	 * <p>Method parameters are:
	 * 	<li>1. change: the change event ({@link EChange})</li>
	 *  <li>2. blackboard: the {@link Blackboard} containing the {@link CorrespondenceInstance}</li>
	 * 
	 * <p>Precondition: Executed codeBlock must exist.
	 */
	/*protected def generateMethodPerformResponse() '''
		private def performResponseTo(«changeEventTypeString» «CHANGE_PARAMETER_NAME», «ih.typeRef(Blackboard)
			» blackboard)«response.effects.codeBlock.code.XBlockExpressionText»
	'''*/
	
	/**
	 * Generates method: checkChangeType : boolean
	 * 
	 * <p>Checks if the change type is correct.
	 * 
	 * <p>Method parameters are:
	 *  <li>1. change: the change event ({@link EChange})</li>
	 */
	/*protected def generateMethodCheckChangeType() '''
		private def boolean checkChangeType(«ih.typeRef(EChange)» «CHANGE_PARAMETER_NAME») { 
			return «CHANGE_PARAMETER_NAME» instanceof «ih.typeRef(change)»«IF !change.instanceClass.equals(EChange)»<?>«ENDIF»;
		}
	'''*/
	
	protected def generateMethodExecuteResponse() {
		val methodName = "executeResponse";
		return getOrGenerateMethod(methodName, typeRef(Void.TYPE)) [
			visibility = JvmVisibility.PRIVATE;
			exceptions += typeRef(IOException);
			val changeParameter = generateChangeParameter();
			val blackboardParameter = generateBlackboardParameter();
			parameters += changeParameter;
			parameters += blackboardParameter;
			if (hasConcreteTargetChange) {
				val modelRootChange = response.effects.targetChange as ConcreteTargetModelRootChange;
				body = generateMethodExecuteResponseBody(modelRootChange, changeParameter, blackboardParameter);
			} else {
				body = generateMethodExecuteResponseBody(changeParameter, blackboardParameter);
			}
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
	private def dispatch StringConcatenationClient generateMethodExecuteResponseBody(ConcreteTargetModelRootUpdate modelRootUpdate, JvmFormalParameter changeParameter, JvmFormalParameter blackboardParameter) {
		val determineTargetModelsMethod = generateMethodDetermineTargetModels(modelRootUpdate);
		val JvmOperation performResponseMethod = if (hasExecutionBlock) {
			generateMethodPerformResponse(response.effects.codeBlock);
		} else {
			null;
		}
		return '''
			«val targetModelElementClass = modelRootUpdate.rootModelElement?.element?.instanceClass»
			«Iterable»<«targetModelElementClass»> targetModels = «determineTargetModelsMethod.simpleName»(«changeParameter.name», «blackboardParameter.name»);
			for («targetModelElementClass» targetModel : targetModels) {
				LOGGER.debug("Execute response " + this.getClass().getName() + " for model " + targetModel);
				«IF hasExecutionBlock»
					«performResponseMethod.simpleName»(«changeParameter.name», targetModel);
				«ENDIF»
			}
		'''
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
	private def dispatch StringConcatenationClient generateMethodExecuteResponseBody(ConcreteTargetModelRootCreate modelRootCreate, JvmFormalParameter changeParameter, JvmFormalParameter blackboardParameter) {
		val generateTargetModelMethod = generateMethodGenerateTargetModel(modelRootCreate);
		val JvmOperation performResponseMethod = if (hasExecutionBlock) {
			generateMethodPerformResponse(response.effects.codeBlock);
		} else {
			null;
		}
		return '''
			«val targetModelElementClass = modelRootCreate.rootModelElement?.element?.instanceClass»
			«targetModelElementClass» targetModel = «generateTargetModelMethod.simpleName»(«changeParameter.name», «blackboardParameter.name»);
			LOGGER.debug("Execute response " + this.getClass().getName() + " for model " + targetModel);
			«IF hasExecutionBlock»
				«performResponseMethod.simpleName»(«changeParameter.name», targetModel);
			«ENDIF»
		'''
	}	
	
	/**
	 * Generates: executeResponse
	 * 
	 * <p>Calls the response execution after deleting the target model.
	 * 
	 * <p>Methods parameters are:
	 * <li>1. change: the change event ({@link EChange})
	 * <li>2. blackboard: the {@link Blackboard} containing the {@link CorrespondenceInstance}
	 */
	private def dispatch StringConcatenationClient generateMethodExecuteResponseBody(ConcreteTargetModelRootDelete modelRootDelete, JvmFormalParameter changeParameter, JvmFormalParameter blackboardParameter) {
		val deleteTargetModelsMethod = generateMethodDeleteTargetModels(modelRootDelete);
		val JvmOperation performResponseMethod = if (hasExecutionBlock) {
			generateMethodPerformResponse(response.effects.codeBlock);
		} else {
			null;
		}
		return '''
			«deleteTargetModelsMethod.simpleName»(«changeParameter.name», «blackboardParameter.name»);
			LOGGER.debug("Execute response " + this.getClass().getName() + " with no affected model");
			«IF hasExecutionBlock»
				«performResponseMethod.simpleName»(«changeParameter.name», targetModel);
			«ENDIF»
		'''	
	}
	
	/**
	 * Generates: executeResponse
	 * 
	 * <p>Calls the response execution after checking preconditions without a target model.
	 * 
	 * <p>Methods parameters are:
	 * <li>1. change: the change event ({@link EChange})
	 */
	private def StringConcatenationClient generateMethodExecuteResponseBody(JvmFormalParameter changeParameter, JvmFormalParameter blackboardParameter) '''
		LOGGER.debug("Execute response " + this.class.name + " with no affected model");
		«IF hasExecutionBlock»
			«generateMethodPerformResponse(response.effects.codeBlock).simpleName»(«changeParameter.name», targetModel);
		«ENDIF»
		'''
	
}