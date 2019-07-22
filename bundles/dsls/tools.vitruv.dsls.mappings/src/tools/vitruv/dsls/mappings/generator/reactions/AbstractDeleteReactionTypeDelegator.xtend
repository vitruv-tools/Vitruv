package tools.vitruv.dsls.mappings.generator.reactions

import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

class AbstractDeleteReactionTypeDelegator extends AbstractContainingReactionTypeGenerator {

	new(AbstractReactionTypeGenerator parentGenerator) {
		super(parentGenerator.metaclass)
		initDelegate(parentGenerator)
	}

	override generateCorrespondenceMatches(UndecidedMatcherStatementBuilder builder, MappingParameter parameter) {
		correspondingParameters.forEach [ correspondingParameter |
			val element = correspondingParameter.removeElementName
			builder.vall(element).retrieve(correspondingParameter.value.metaclass).correspondingTo.matchingElement.
				taggedWith(parameter, correspondingParameter)
		]
	}

	override generateCorrespondenceActions(ActionStatementBuilder builder, MappingParameter parameter) {
		correspondingParameters.forEach [ correspondingParameter |
			val element = correspondingParameter.removeElementName
			builder.removeCorrespondenceBetween(element).and.matchingElement.taggedWith(parameter,
				correspondingParameter)
			builder.delete(element)
		]
	}

	override generateTrigger(ReactionGeneratorContext context) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

}
