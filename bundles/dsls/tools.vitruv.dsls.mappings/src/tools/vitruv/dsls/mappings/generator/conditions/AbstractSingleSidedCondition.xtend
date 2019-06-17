package tools.vitruv.dsls.mappings.generator.conditions

import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder
import org.eclipse.emf.ecore.EObject

abstract class AbstractSingleSidedCondition<T extends EObject> {
	
	protected T condition
	
	new(T condition){
		this.condition = condition	
	}
	
	abstract def void generate(UndecidedMatcherStatementBuilder builder)
	
}