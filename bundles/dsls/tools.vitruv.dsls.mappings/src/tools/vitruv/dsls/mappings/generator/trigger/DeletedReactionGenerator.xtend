package tools.vitruv.dsls.mappings.generator.trigger

import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference

class DeletedReactionGenerator extends AbstractReactionTypeGenerator{
		
	new(MetaclassReference element) {
		super(element.metaclass)
	}
	
	override generateTrigger(ReactionGeneratorContext context) {
		 context.create.reaction('''on«metaclass.parameterName»Deleted''').afterElement(metaclass).deleted
	}
	
	override equals(Object obj) {
		if(obj instanceof DeletedReactionGenerator){
			return metaclass == obj.metaclass
		}
		false
	}
}
