package tools.vitruv.dsls.mappings.generator.reactions

import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

class DeletedReactionGenerator extends AbstractReactionTypeGenerator{
		
	new(MetaclassReference element) {
		super(element.metaclass)
	}
	
	override generateTrigger(ReactionGeneratorContext context) {
		 context.create.reaction('''on«metaclass.parameterName»Deleted''').afterElement(metaclass).deleted
	}
		
	override generateCorrespondenceMatches(UndecidedMatcherStatementBuilder builder) {
		correspondingParameters.forEach[
			builder.vall(it.parameterName).retrieve(it.metaclass).correspondingTo.affectedEObject.taggedWith(reactionParameters.get(0),it)
		]
	}
	
	override generateCorrespondenceActions(ActionStatementBuilder builder) {
		//delete 
		correspondingParameters.forEach[
			builder.delete(it.parameterName)
		]
	}

	override equals(Object obj) {
		if(obj instanceof DeletedReactionGenerator){
			return metaclass == obj.metaclass
		}
		false
	}
	

}
