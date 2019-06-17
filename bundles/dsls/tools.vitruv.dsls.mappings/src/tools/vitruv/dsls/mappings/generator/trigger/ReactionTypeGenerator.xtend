package tools.vitruv.dsls.mappings.generator.trigger

import java.util.HashMap
import java.util.Map
import tools.vitruv.dsls.mappings.mappingsLanguage.SingleSidedCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.ReactionTrigger
import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mappings.mappingsLanguage.InsertedInTrigger
import tools.vitruv.dsls.mappings.mappingsLanguage.RemovedFromTrigger
import tools.vitruv.dsls.mappings.mappingsLanguage.DeletedTrigger

class ReactionTypeGenerator {
	
	private Map<Class, AbstractReactionTypeGenerator> generators = new HashMap<Class, AbstractReactionTypeGenerator>();	
	private ReactionTrigger trigger;
	
	new(ReactionTrigger trigger){
		this.trigger = trigger;
		init(InsertedInTrigger, new InsertedInReactionGenerator)
		init(RemovedFromTrigger, new RemovedFromReactionGenerator)
		init(DeletedTrigger, new DeletedReactionGenerator)
	}
	
	private def void init(Class clazz, AbstractReactionTypeGenerator generator){
		this.generators.put(clazz, generator)
	}
	
	def generate(ReactionGeneratorContext context){
		generators.get(trigger.type).generate(context, trigger.parameter, trigger.type)
	}
	
}
