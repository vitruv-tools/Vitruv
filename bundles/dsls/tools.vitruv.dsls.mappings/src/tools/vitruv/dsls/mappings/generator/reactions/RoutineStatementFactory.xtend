package tools.vitruv.dsls.mappings.generator.reactions

import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

class RoutineStatementFactory {
	
	private AbstractReactionTriggerGenerator generator
	
	new(AbstractReactionTriggerGenerator generator){
		this.generator = generator	
	}
	
	def constructMatcher(UndecidedMatcherStatementBuilder builder){
		if(generator.reactionParameters.size>1){
			constructMatcherMulti(builder)
		}
		else{
			constructMatcherSingle(builder)
		}
	}
	
	def constructActions(ActionStatementBuilder builder){
		if(generator.reactionParameters.size>1){
			constructActionsMulti(builder)
		}
		else{
			constructActionsSingle(builder)
		}
	}
	
	private def constructMatcherSingle(UndecidedMatcherStatementBuilder builder){
		
	}
	
	private def constructMatcherMulti(UndecidedMatcherStatementBuilder builder){
		
	}
	
	private def constructActionsSingle(ActionStatementBuilder builder){
		
	}
	
	private def constructActionsMulti(ActionStatementBuilder builder){
		
	}
	
}