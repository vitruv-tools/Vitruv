package tools.vitruv.dsls.mappings.generator.reactions

import org.eclipse.emf.ecore.EClass
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.dsls.mappings.generator.AbstractMappingEntityGenerator
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder.PreconditionOrRoutineCallBuilder
import tools.vitruv.dsls.mappings.generator.MappingGeneratorContext

abstract class AbstractReactionTriggerGenerator extends AbstractMappingEntityGenerator {

	@Accessors(PUBLIC_GETTER)
	protected EClass metaclass
	@Accessors(#[PUBLIC_GETTER, PUBLIC_SETTER])
	private ConflictingTriggerCheck conflictingTriggerCheck
	@Accessors(PUBLIC_GETTER)
	protected boolean usesNewValue = false
	@Accessors(PUBLIC_GETTER)
	private ReactionTriggerType triggerType
	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	private boolean derivedFromBidirectionalCondition

	protected String reactionName

	new(EClass metaclass, ReactionTriggerType triggerType) {
		this.metaclass = metaclass
		this.triggerType = triggerType
	}

	def public String reactionName() '''
	On«mappingName.toFirstUpper»«reactionName»«IF derivedFromBidirectionalCondition»Bidirectional«ENDIF»'''

	def boolean isSubordinateToTrigger(AbstractReactionTriggerGenerator generator) {
		if (generator.metaclass == metaclass && conflictingTriggerCheck !== null) {
			return conflictingTriggerCheck.isSubordinateTrigger(generator)
		}
		false
	}

	def abstract PreconditionOrRoutineCallBuilder generateTrigger(MappingGeneratorContext context)

}
