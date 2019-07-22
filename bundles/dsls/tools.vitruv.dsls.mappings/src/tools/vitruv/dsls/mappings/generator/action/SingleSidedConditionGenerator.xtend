package tools.vitruv.dsls.mappings.generator.action

import java.util.ArrayList
import java.util.List
import java.util.function.Consumer
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.dsls.mappings.generator.conditions.AbstractSingleSidedCondition
import tools.vitruv.dsls.mappings.generator.conditions.FeatureConditionGenerator
import tools.vitruv.dsls.mappings.generator.conditions.SingleSidedConditionFactory
import tools.vitruv.dsls.mappings.generator.reactions.AbstractReactionTypeGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.SingleSidedCondition
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

class SingleSidedConditionGenerator implements Consumer<UndecidedMatcherStatementBuilder> {

	private List<SingleSidedCondition> conditions;
	private AbstractReactionTypeGenerator reactionTypeGenerator
	private List<AbstractSingleSidedCondition> generators
	@Accessors(PUBLIC_GETTER)
	private UndecidedMatcherStatementBuilder builder
	@Accessors(PUBLIC_SETTER)
	private ReactionRoutineContentGenerator contentGenerator

	new(AbstractReactionTypeGenerator reactionTypeGenerator, List<SingleSidedCondition> conditions) {
		this.reactionTypeGenerator = reactionTypeGenerator
		this.conditions = conditions;
		generators = constructConditionGenerators();
	}

	public def constructConditionGenerators() {
		val generators = new ArrayList
		conditions.forEach [ condition |
			val generator = SingleSidedConditionFactory.construct(condition)
			if (generator === null) {
				System.err.println('''No single sided condition generator found for condition «condition»''')
			} else {
				// check if single side condition is meant for this reaction type generator
				if (generator.feasibleForGenerator(reactionTypeGenerator)) {
					generators += generator
				}
			}
		]
		generators
	}

	override accept(UndecidedMatcherStatementBuilder builder) {
		this.builder = builder
		generators.forEach[generate(builder)]
		if (!contentGenerator.usesSubRoutines) {
			// directly integrate logic in this routine, without creating and calling subroutines
			reactionTypeGenerator.reactionParameters.forEach [
				reactionTypeGenerator.generateCorrespondenceMatches(
					builder,
					it
				)
			]
		}
	}

	def constructFeatureConditions() {
		val featureGenerators = generators.filter[it instanceof FeatureConditionGenerator].map [
			it as FeatureConditionGenerator
		]
		new ReactionFeatureConditionsGenerator(reactionTypeGenerator, featureGenerators.toList)
	}

}
