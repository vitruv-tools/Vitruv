package tools.vitruv.dsls.mappings.generator.conditions

import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EObject
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder
import tools.vitruv.dsls.mappings.generator.reactions.AbstractReactionTriggerGenerator
import org.eclipse.xtend.lib.annotations.Accessors

abstract class AbstractSingleSidedCondition<T extends EObject> {
	
	@Accessors(PROTECTED_GETTER)
	var T condition

	new(T condition) {
		this.condition = condition
	}

	abstract protected def void constructReactionTriggers(List<AbstractReactionTriggerGenerator> triggers)

	def List<AbstractReactionTriggerGenerator> constructReactionTriggers() {
		val triggers = new ArrayList<AbstractReactionTriggerGenerator>()
		constructReactionTriggers(triggers)
		triggers
	}

	abstract def void generate(UndecidedMatcherStatementBuilder builder)

}
