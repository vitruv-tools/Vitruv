package tools.vitruv.dsls.mappings.generator.trigger

import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mappings.mappingsLanguage.InsertedInTrigger
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.mappings.mappingsLanguage.InsertedInCollectionTrigger
import tools.vitruv.dsls.mappings.mappingsLanguage.AsRootTrigger

class InsertedInReactionGenerator extends AbstractReactionTypeGenerator<InsertedInTrigger>{
	
	override generate(ReactionGeneratorContext context, MetaclassReference parameter, InsertedInTrigger inserterdIn) {
		val trigger = inserterdIn.trigger;
		if(trigger instanceof InsertedInCollectionTrigger){
			val insertedIn = trigger.collection
			return context.create.reaction('''on«parameter.parameterName»InsertedIn«insertedIn.parameterName»''')
			.afterElement(parameter.metaclass).insertedIn(null) //todo		
		}
		else if(trigger instanceof AsRootTrigger){
			return context.create.reaction('''on«parameter.parameterName»InsertedAsRoot''')
			.afterElement(parameter.metaclass).insertedAsRoot
		}
		null
	}
	

}
