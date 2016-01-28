package edu.kit.ipd.sdq.vitruvius.dsls.response.generator.singleResponse

import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response

import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.helper.EChangeHelper.*;
import static edu.kit.ipd.sdq.vitruvius.dsls.response.generator.ResponseLanguageGeneratorConstants.*;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.UpdatedModel
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.CreatedModel
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.FeatureOfElement
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ConcreteModelElementChange

class SingleChangeResponseGenerator extends SingleModelChangeResponseGenerator {
	private final ConcreteModelElementChange changeEvent;
	private final FeatureOfElement changedObject;
	
	protected new(Response response) {
		super(response);
		if (!(response.trigger instanceof ConcreteModelElementChange)) {
			throw new IllegalArgumentException("Response must be triggered by a change event")
		}
		this.changeEvent = response.trigger as ConcreteModelElementChange;
		this.changedObject = changeEvent.changedObject;
	}
	
	protected override Iterable<CharSequence> getGeneratedMethods() {
		val methods = <CharSequence>newArrayList();
		
		methods += generateMethodCheckChangeType();
		methods += generateMethodCheckChangedObject();
		
		if (hasPerModelPreconditionBlock) {
			methods += generateMethodCheckPerModelPrecondition();
		}
		if (hasTargetModel) {
			if (response.effects.targetModel instanceof UpdatedModel) {
				methods += generateMethodDetermineTargetModels(response.effects.targetModel as UpdatedModel);	
				methods += generateMethodExecuteModelUpdate();
			} else if (response.effects.targetModel instanceof CreatedModel) {
				methods += generateMethodGenerateTargetModel(response.effects.targetModel as CreatedModel);
				methods += generateMethodExecuteModelCreate();
			}
		} else {
			methods += generateMethodExecuteWithoutModel();
		}
					
		methods += generateMethodApplyChange();
		
		if (hasExecutionBlock) {
			methods += generateMethodPerformResponse();
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
	protected def generateMethodCheckChangedObject() '''
		private def boolean checkChangedObject(«ih.typeRef(EChange)» «CHANGE_PARAMETER_NAME») {
			val typedChange = «CHANGE_PARAMETER_NAME» as «ih.typeRef(change)»«IF !change.instanceClass.equals(EChange)»<?>«ENDIF»;
			val changedElement = typedChange.«change.EChangeFeatureNameOfChangedObject»;
			«IF EFeatureChange.isAssignableFrom(change.instanceClass)»
				«/* TODO HK We could compare something more safe like <MM>PackageImpl.eINSTANCE.<ELEMENT>_<FEATURE>.*/»
				if (!typedChange.affectedFeature.name.equals("«changedObject.feature.name»")) {
					return false;
				}
			«ENDIF»
			if (changedElement instanceof «ih.typeRef(changedObject.element)») {
				return true;
			}
			return false;
		}
	'''
	
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
	protected def generateMethodApplyChange() '''
		«val blackboardName = "blackboard"»
		«val typedChangeName = "typedChange"»
		public override «RESPONSE_APPLY_METHOD_NAME»(«ih.typeRef(EChange)» «CHANGE_PARAMETER_NAME», «
			ih.typeRef(Blackboard)» «blackboardName») {
			LOGGER.debug("Called response " + this.class.name + " with event " + «CHANGE_PARAMETER_NAME»);
			
			// Check if the event matches the trigger of the response
			if (!checkChangeType(«CHANGE_PARAMETER_NAME») || !checkChangedObject(«CHANGE_PARAMETER_NAME»)) {
				return new «ih.typeRef(TransformationResult)»();
			}
			LOGGER.debug("Passed precondition check of response " + this.class.name);
			
			val «typedChangeName» = «CHANGE_PARAMETER_NAME» as «changeEventTypeString»;
			«IF hasTargetModel»
				«IF response.effects.targetModel instanceof UpdatedModel»
					executeModelUpdate(«typedChangeName», «blackboardName»);
				«ELSE»
					executeModelCreate(«typedChangeName», «blackboardName»);
				«ENDIF»
			«ELSE»
				executeWithoutModel(«typedChangeName»);
			«ENDIF»
			return new «ih.typeRef(TransformationResult)»();
		}
	'''	
	
	/**
	 * Generates: executeModelUpdate
	 * 
	 * <p>Calls the response execution after checking preconditions for updating the determined target models.
	 * 
	 * <p>Methods parameters are:
	 * <li>1. change: the change event ({@link EChange})
	 * <li>2. blackboard: the {@link Blackboard} containing the {@link CorrespondenceInstance}
	 */
	private def generateMethodExecuteModelUpdate() '''
		private def executeModelUpdate(«changeEventTypeString» «CHANGE_PARAMETER_NAME», «ih.typeRef(Blackboard)» blackboard) {
			val targetModels = determineTargetModels(«CHANGE_PARAMETER_NAME», blackboard);
			for (targetModel : targetModels) {
				var preconditionPassed = true;
				«IF hasPerModelPreconditionBlock»
					preconditionPassed = checkPerModelPrecondition(«CHANGE_PARAMETER_NAME», targetModel);
				«ENDIF»
				if (preconditionPassed) {
					LOGGER.debug("Execute response " + this.class.name + " for model " + targetModel);
					«IF hasExecutionBlock»
						performResponseTo(«CHANGE_PARAMETER_NAME», targetModel);
					«ENDIF»
				}
			}
		}
	'''
	
	/**
	 * Generates: executeModelCreate
	 * 
	 * <p>Calls the response execution after checking preconditions for creating the target model.
	 * 
	 * <p>Methods parameters are:
	 * <li>1. change: the change event ({@link EChange})
	 * <li>2. blackboard: the {@link Blackboard} containing the {@link CorrespondenceInstance}
	 */
	private def generateMethodExecuteModelCreate() '''
		private def executeModelCreate(«changeEventTypeString» «CHANGE_PARAMETER_NAME», «ih.typeRef(Blackboard)» blackboard) {
			var preconditionPassed = true;
			«IF hasPerModelPreconditionBlock»
				preconditionPassed = checkPerModelPrecondition(«CHANGE_PARAMETER_NAME»);
			«ENDIF»
			if (preconditionPassed) {
				val targetModel = generateTargetModel(«CHANGE_PARAMETER_NAME», blackboard);
				LOGGER.debug("Execute response " + this.class.name + " for new model " + targetModel);
				«IF hasExecutionBlock»
					performResponseTo(«CHANGE_PARAMETER_NAME», targetModel);
				«ENDIF»
			}
		}
	'''
	
	/**
	 * Generates: executeWithoutModel
	 * 
	 * <p>Calls the response execution after checking preconditions without a target model.
	 * 
	 * <p>Methods parameters are:
	 * <li>1. change: the change event ({@link EChange})
	 */
	private def generateMethodExecuteWithoutModel() '''
		private def executeWithoutModel(«changeEventTypeString» «CHANGE_PARAMETER_NAME») {
			LOGGER.debug("Execute response " + this.class.name + " with no affected model");
			var preconditionPassed = true;
			«IF hasPerModelPreconditionBlock»
				preconditionPassed = checkPerModelPrecondition(«CHANGE_PARAMETER_NAME»);
			«ENDIF»
			«IF hasExecutionBlock»
			if (preconditionPassed) {
				performResponseTo(«CHANGE_PARAMETER_NAME»);
			}
			«ENDIF»
		}
	'''
	
	protected override getChangeEventTypeString() '''
		«ih.typeRef(change)»<«ih.typeRef(change.getGenericTypeParameterFQNOfChange(changeEvent.changedObject))»>'''
	
}
