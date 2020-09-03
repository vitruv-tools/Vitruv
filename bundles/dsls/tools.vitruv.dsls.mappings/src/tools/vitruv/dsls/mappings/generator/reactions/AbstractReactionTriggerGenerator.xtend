package tools.vitruv.dsls.mappings.generator.reactions

import org.eclipse.emf.ecore.EClass
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.dsls.mappings.generator.AbstractMappingEntityGenerator
import tools.vitruv.dsls.mappings.generator.MappingGeneratorContext
import tools.vitruv.dsls.mappings.generator.MappingScenarioType
import tools.vitruv.dsls.mappings.mappingsLanguage.ObserveChange
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder.PreconditionOrRoutineCallBuilder

abstract class AbstractReactionTriggerGenerator extends AbstractMappingEntityGenerator {

	@Accessors(PUBLIC_GETTER)
	var EClass metaclass
	@Accessors(#[PUBLIC_GETTER, PUBLIC_SETTER])
	var ConflictingTriggerCheck conflictingTriggerCheck
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER)
	var boolean usesNewValue = false
	@Accessors(PUBLIC_GETTER)
	var MappingScenarioType scenarioType
	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	var ObserveChange sourceObserveChange
	@Accessors(PROTECTED_SETTER)	
	var String reactionName

	new(EClass metaclass, MappingScenarioType scenarioType) {
		this.metaclass = metaclass
		this.scenarioType = scenarioType
	}
	
	//should only be called for triggers derived from bidirectional conditions
	def overwriteScenarioType(MappingScenarioType scenarioType){
		this.scenarioType = scenarioType
	}
	
	/**
	 * Returns the name of the reaction to generate
	 */
	def String reactionName() '''
	On«mappingName.toFirstUpper»«reactionName»«IF scenarioType==MappingScenarioType.UPDATE»Bidirectional«ENDIF»'''
	
	/**
	 * Checks if this trigger generator is subordinate to another generator
	 */
	def boolean isSubordinateToTrigger(AbstractReactionTriggerGenerator generator) {
		if (generator.metaclass == metaclass && conflictingTriggerCheck !== null) {
			return conflictingTriggerCheck.isSubordinateTrigger(generator)
		}
		false
	}

	def abstract PreconditionOrRoutineCallBuilder generateTrigger(MappingGeneratorContext context)

}
