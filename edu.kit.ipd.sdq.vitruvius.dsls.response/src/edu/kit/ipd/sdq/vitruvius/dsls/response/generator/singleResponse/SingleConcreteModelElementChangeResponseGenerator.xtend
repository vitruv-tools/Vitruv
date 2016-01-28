package edu.kit.ipd.sdq.vitruvius.dsls.response.generator.singleResponse

import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response

import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.helper.EChangeHelper.*;
import static edu.kit.ipd.sdq.vitruvius.dsls.response.generator.ResponseLanguageGeneratorConstants.*;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.FeatureOfElement
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ConcreteModelElementChange

class SingleConcreteModelElementChangeResponseGenerator extends SingleModelChangeResponseGenerator {
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
		var methods = <CharSequence>newArrayList();
		methods += super.getGeneratedMethods();
		methods += generateMethodCheckChangedObject();
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
	protected override generateMethodApplyChange() '''
		«val blackboardName = "blackboard"»
		«val typedChangeName = "typedChange"»
		public override «RESPONSE_APPLY_METHOD_NAME»(«ih.typeRef(EChange)» «CHANGE_PARAMETER_NAME», «
			ih.typeRef(Blackboard)» «blackboardName») {
			LOGGER.debug("Called response " + this.class.name + " with event " + «CHANGE_PARAMETER_NAME»);
			
			// Check if the event matches the trigger of the response
			if (!checkChangeType(«CHANGE_PARAMETER_NAME») 
				|| !checkChangedObject(«CHANGE_PARAMETER_NAME»)) {
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
		«val typeParameter = ih.typeRef(change.getGenericTypeParameterFQNOfChange(changeEvent.changedObject))»
		«ih.typeRef(change)»«IF !typeParameter.nullOrEmpty»<«typeParameter»>«ENDIF»'''
	
}
