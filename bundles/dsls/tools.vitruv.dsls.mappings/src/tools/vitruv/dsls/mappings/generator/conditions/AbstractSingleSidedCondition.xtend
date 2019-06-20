package tools.vitruv.dsls.mappings.generator.conditions

import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EObject
import tools.vitruv.dsls.mappings.generator.reactions.AbstractReactionTypeGenerator
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

abstract class AbstractSingleSidedCondition<T extends EObject> {
	
	protected T condition
	
	new(T condition){
		this.condition = condition	
	}
	
	abstract def boolean feasibleForGenerator(AbstractReactionTypeGenerator generator)
	
	abstract protected def void constructReactionTriggers(List<AbstractReactionTypeGenerator> triggers)
	
	def List<AbstractReactionTypeGenerator> constructReactionTriggers(){
		val triggers = new ArrayList<AbstractReactionTypeGenerator> ()
		constructReactionTriggers(triggers)
		triggers
	}
	
	abstract def void generate(UndecidedMatcherStatementBuilder builder)
	
}