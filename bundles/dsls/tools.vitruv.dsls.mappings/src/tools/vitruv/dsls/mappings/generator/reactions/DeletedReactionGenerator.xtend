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

	override equals(Object obj) {
		if (obj instanceof DeletedReactionGenerator) {
			return metaclass == obj.metaclass
		}
		false
	}
	
	override generateCorrespondenceMatches(UndecidedMatcherStatementBuilder builder) {
		new AbstractDeleteReactionTypeDelegator(this).generateCorrespondenceMatches(builder)
	}
	
	override generateCorrespondenceActions(ActionStatementBuilder builder) {
		new AbstractDeleteReactionTypeDelegator(this).generateCorrespondenceActions(builder)
	}

}
