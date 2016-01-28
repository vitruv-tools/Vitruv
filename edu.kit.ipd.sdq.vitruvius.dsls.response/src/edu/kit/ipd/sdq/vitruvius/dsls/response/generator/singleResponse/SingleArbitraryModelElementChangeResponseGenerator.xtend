package edu.kit.ipd.sdq.vitruvius.dsls.response.generator.singleResponse

import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response

import static edu.kit.ipd.sdq.vitruvius.dsls.response.generator.ResponseLanguageGeneratorConstants.*;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ConcreteModelRootUpdate
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ConcreteModelRootCreate
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ConcreteModelRootChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ArbitraryModelElementChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.MetamodelImport

class SingleArbitraryModelElementChangeResponseGenerator extends SingleModelChangeResponseGenerator {
	private final ArbitraryModelElementChange changeEvent;
	private final MetamodelImport changedMetamodel;
	
	protected new(Response response) {
		super(response);
		if (!(response.trigger instanceof ArbitraryModelElementChange)) {
			throw new IllegalArgumentException("Response must be triggered by a change event")
		}
		this.changeEvent = response.trigger as ArbitraryModelElementChange;
		this.changedMetamodel = changeEvent.changedModel.model;
	}
	
	protected override Iterable<CharSequence> getGeneratedMethods() {
		val methods = <CharSequence>newArrayList();
		
		methods += generateMethodCheckChangeType();
		
		if (hasPreconditionBlock) {
			methods += generateMethodCheckPrecondition()
		}
		if (hasTargetChange) {
			if (response.effects.targetChange instanceof ConcreteModelRootUpdate) {
				methods += generateMethodDetermineTargetModels(response.effects.targetChange as ConcreteModelRootUpdate);	
				methods += generateMethodExecuteResponse(response.effects.targetChange as ConcreteModelRootUpdate);
			} else if (response.effects.targetChange instanceof ConcreteModelRootCreate) {
				methods += generateMethodGenerateTargetModel(response.effects.targetChange as ConcreteModelRootCreate);
				methods += generateMethodExecuteResponse(response.effects.targetChange as ConcreteModelRootCreate);
			} else {
				methods += generateMethodExecuteResponse();
			}
		} else {
			methods += generateMethodExecuteResponse();
		}
		
		methods += generateMethodApplyChange();
		
		if (hasExecutionBlock) {
			if (response.effects.targetChange instanceof ConcreteModelRootChange) {
				methods += generateMethodPerformResponse((response.effects.targetChange as ConcreteModelRootChange).rootModelElement.modelElement);
			} else {
				methods += generateMethodPerformResponse();
			}
		}
			
		return methods;
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
	protected def generateMethodApplyChange() '''
		«val blackboardName = "blackboard"»
		«val typedChangeName = "typedChange"»
		public override «RESPONSE_APPLY_METHOD_NAME»(«ih.typeRef(EChange)» «CHANGE_PARAMETER_NAME», «
			ih.typeRef(Blackboard)» «blackboardName») {
			LOGGER.debug("Called response " + this.class.name + " with event " + «CHANGE_PARAMETER_NAME»);
			
			// Check if the event matches the trigger of the response
			if (!checkChangeType(«CHANGE_PARAMETER_NAME»)«IF hasPreconditionBlock»
				|| !checkPrecondition(«CHANGE_PARAMETER_NAME»)«ENDIF») {
				return new «ih.typeRef(TransformationResult)»();
			}
			LOGGER.debug("Passed precondition check of response " + this.class.name);
			
			val «typedChangeName» = «CHANGE_PARAMETER_NAME» as «changeEventTypeString»;
			executeResponse(«typedChangeName», «blackboardName»);
			return new «ih.typeRef(TransformationResult)»();
		}
	'''	
	
	
	protected override getChangeEventTypeString() '''
		«ih.typeRef(EChange)»'''
	
}
