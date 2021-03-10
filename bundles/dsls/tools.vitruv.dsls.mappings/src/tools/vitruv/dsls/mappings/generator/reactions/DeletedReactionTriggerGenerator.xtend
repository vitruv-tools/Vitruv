package tools.vitruv.dsls.mappings.generator.reactions

import tools.vitruv.dsls.mappings.generator.MappingGeneratorContext
import tools.vitruv.dsls.mappings.generator.MappingScenarioType
import tools.vitruv.dsls.common.elements.MetaclassReference

class DeletedReactionTriggerGenerator extends AbstractReactionTriggerGenerator {

	new(MetaclassReference element) {
		super(element.metaclass, MappingScenarioType.DELETE)
	}

	override generateTrigger(MappingGeneratorContext context) {
		this.reactionName = '''«metaclass.parameterName»Deleted'''
		context.create.reaction(reactionName()).afterElement(metaclass).deleted
	}

	override toString() '''
	«metaclass.parameterName» deleted'''

	override equals(Object obj) {
		if (obj instanceof DeletedReactionTriggerGenerator) {
			return metaclass == obj.metaclass
		}
		false
	}

}
