package tools.vitruv.dsls.mappings.generator.reactions

import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

class DeletedReactionGenerator extends AbstractReactionTypeGenerator {

	new(MetaclassReference element) {
		super(element.metaclass)
	}

	override generateTrigger(ReactionGeneratorContext context) {
		this.reactionName = '''«metaclass.parameterName»Deleted'''
		context.create.reaction(reactionName()).afterElement(metaclass).deleted
	}

	override toString() '''
	«metaclass.parameterName» deleted'''

	override generateCorrespondenceMatches(UndecidedMatcherStatementBuilder builder, MappingParameter parameter) {
		iterateParameters [ reactionParameter, correspondingParameter |
			val element = getParameterName(reactionParameter, correspondingParameter)
			builder.vall(element).retrieve(correspondingParameter.value.metaclass).correspondingTo.affectedEObject.
				taggedWith(reactionParameter, correspondingParameter)
		]
	}

	override generateCorrespondenceActions(ActionStatementBuilder builder, MappingParameter parameter) {
		// delete 
		correspondingParameters.forEach [
//			val element = it.removeElementName
			// builder.delete(element)
		]
		iterateParameters [ reactionParameter, correspondingParameter |
			val element = getParameterName(reactionParameter, correspondingParameter)
			builder.removeCorrespondenceBetween(element).and.affectedEObject.taggedWith(reactionParameter,
				correspondingParameter)
			builder.delete(element)
		]
	}

	override equals(Object obj) {
		if (obj instanceof DeletedReactionGenerator) {
			return metaclass == obj.metaclass
		}
		false
	}

}
