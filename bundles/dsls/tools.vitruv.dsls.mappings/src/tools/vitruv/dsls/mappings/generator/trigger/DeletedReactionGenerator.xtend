package tools.vitruv.dsls.mappings.generator.trigger

import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.mappings.mappingsLanguage.DeletedTrigger

class DeletedReactionGenerator extends AbstractReactionTypeGenerator<DeletedTrigger>{
	
	override generate(ReactionGeneratorContext context, MetaclassReference parameter, DeletedTrigger trigger) {
		 context.create.reaction('''on«parameter.parameterName»Deleted''').afterElement(parameter.metaclass).deleted
	}
	
}
