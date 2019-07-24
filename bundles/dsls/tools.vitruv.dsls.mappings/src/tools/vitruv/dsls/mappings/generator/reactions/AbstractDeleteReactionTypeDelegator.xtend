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

	override generateCorrespondenceMatches(UndecidedMatcherStatementBuilder builder) {
		// just take the first element to retrieve all the corresponding elements
		val taggingParameter = reactionParameters.get(0)
		correspondingParameters.forEach [ correspondingParameter |
			val element = correspondingParameter.removeElementName
			builder.vall(element).retrieve(correspondingParameter.value.metaclass).correspondingTo.matchingElement.
				taggedWith(taggingParameter, correspondingParameter)
		]
	}

	override generateCorrespondenceActions(ActionStatementBuilder builder) {
		// 1) remove all correspondences
		iterateParameters([ reactionParameter, correspondingParameter |
			val element = correspondingParameter.removeElementName
			builder.removeCorrespondenceBetween(element).and.matchingElement.taggedWith(reactionParameter,
				correspondingParameter)
		])
		// 2) delete all corresponding elements
		correspondingParameters.forEach [ correspondingParameter |
			val element = correspondingParameter.removeElementName
			builder.delete(element)
		]
	}

	override generateTrigger(ReactionGeneratorContext context) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

}
