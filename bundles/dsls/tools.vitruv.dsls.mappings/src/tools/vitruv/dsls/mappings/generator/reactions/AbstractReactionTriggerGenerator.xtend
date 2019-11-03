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
	protected EClass metaclass
	@Accessors(#[PUBLIC_GETTER, PUBLIC_SETTER])
	private ConflictingTriggerCheck conflictingTriggerCheck
	@Accessors(PUBLIC_GETTER)
	protected boolean usesNewValue = false
	@Accessors(PUBLIC_GETTER)
	private MappingScenarioType scenarioType
	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	private ObserveChange sourceObserveChange

	protected String reactionName

	new(EClass metaclass, MappingScenarioType scenarioType) {
		this.metaclass = metaclass
		this.scenarioType = scenarioType
	}
	
	//should only be called for triggers derived from bidirectional conditions
	def public overwriteScenarioType(MappingScenarioType scenarioType){
		this.scenarioType = scenarioType
	}
	
	def public String reactionName() '''
	On«mappingName.toFirstUpper»«reactionName»«IF scenarioType==MappingScenarioType.UPDATE»Bidirectional«ENDIF»'''

	def boolean isSubordinateToTrigger(AbstractReactionTriggerGenerator generator) {
		if (generator.metaclass == metaclass && conflictingTriggerCheck !== null) {
			return conflictingTriggerCheck.isSubordinateTrigger(generator)
		}
		false
	}

	def abstract PreconditionOrRoutineCallBuilder generateTrigger(MappingGeneratorContext context)

}
