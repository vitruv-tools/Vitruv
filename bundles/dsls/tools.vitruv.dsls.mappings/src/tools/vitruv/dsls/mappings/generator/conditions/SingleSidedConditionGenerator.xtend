package tools.vitruv.dsls.mappings.generator.conditions

import java.util.List
import java.util.function.Consumer
import tools.vitruv.dsls.mappings.generator.trigger.AbstractReactionTypeGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.SingleSidedCondition
import tools.vitruv.dsls.mirbase.mirBase.NamedMetaclassReference
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

class SingleSidedConditionGenerator implements Consumer<UndecidedMatcherStatementBuilder> {

	private List<SingleSidedCondition> conditions;
	private AbstractReactionTypeGenerator reactionTypeGenerator

	new(AbstractReactionTypeGenerator reactionTypeGenerator, List<SingleSidedCondition> conditions) {
		this.reactionTypeGenerator = reactionTypeGenerator
		this.conditions = conditions;
	}
	
	override accept(UndecidedMatcherStatementBuilder builder) {
		conditions.forEach [ condition |
			val generator = SingleSidedConditionFactory.construct(condition)
			if (generator === null) {
				System.err.println('''No single sided condition generator found for condition «condition»''')
			} else {
				//check if single side condition is meant for this reaction type generator
				if(generator.feasibleForGenerator(reactionTypeGenerator)){
					//append condition to reaction
					generator.generate(builder)										
				}				
			}
		]
	}

}
