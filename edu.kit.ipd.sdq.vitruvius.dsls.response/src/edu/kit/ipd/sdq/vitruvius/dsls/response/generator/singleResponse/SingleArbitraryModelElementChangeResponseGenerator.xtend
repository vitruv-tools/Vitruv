package edu.kit.ipd.sdq.vitruvius.dsls.response.generator.singleResponse

import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response

import static edu.kit.ipd.sdq.vitruvius.dsls.response.generator.ResponseLanguageGeneratorConstants.*;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
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
		return super.getGeneratedMethods();
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
	protected override generateMethodApplyChange() '''
		«val blackboardName = "blackboard"»
		«val typedChangeName = "typedChange"»
		public override «RESPONSE_APPLY_METHOD_NAME»(«ih.typeRef(EChange)» «CHANGE_PARAMETER_NAME», «
			ih.typeRef(Blackboard)» «blackboardName») {
			LOGGER.debug("Called response " + this.class.name + " with event " + «CHANGE_PARAMETER_NAME»);
			
			// Check if the event matches the trigger of the response
			if (!checkChangeType(«CHANGE_PARAMETER_NAME»)) {
				return new «ih.typeRef(TransformationResult)»();
			}
			val «typedChangeName» = «CHANGE_PARAMETER_NAME» as «changeEventTypeString»;
			«IF hasPreconditionBlock»
			if (!checkPrecondition(«typedChangeName»)) {
				return new «ih.typeRef(TransformationResult)»();
			}«ENDIF»
			LOGGER.debug("Passed precondition check of response " + this.class.name);
			
			executeResponse(«typedChangeName», «blackboardName»);
			return new «ih.typeRef(TransformationResult)»();
		}
	'''	
	
	
	protected override getChangeEventTypeString() '''
		«ih.typeRef(EChange)»'''
	
}
