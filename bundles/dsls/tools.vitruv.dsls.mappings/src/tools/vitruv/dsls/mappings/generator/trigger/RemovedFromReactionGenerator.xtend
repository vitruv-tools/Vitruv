package tools.vitruv.dsls.mappings.generator.trigger

import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mappings.mappingsLanguage.AsRootTrigger
import tools.vitruv.dsls.mappings.mappingsLanguage.RemovedFromCollectionTrigger
import tools.vitruv.dsls.mappings.mappingsLanguage.RemovedFromTrigger
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference

class RemovedFromReactionGenerator extends AbstractReactionTypeGenerator<RemovedFromTrigger>{
	
	override generate(ReactionGeneratorContext context, MetaclassReference parameter, RemovedFromTrigger removedFrom) {
		val trigger = removedFrom.trigger;
		if(trigger instanceof RemovedFromCollectionTrigger){
			val target = trigger.collection
			return context.create.reaction('''on«parameter.parameterName»RemovedFrom«target.parameterName»''')
			.afterElement(parameter.metaclass).removedFrom(null) //todo
		}
		else if(trigger instanceof AsRootTrigger){
			return context.create.reaction('''on«parameter.parameterName»RemovedAsRoot''')
			.afterElement(parameter.metaclass).deleted //todo no removed from root
		}
		null
	}
	
}
