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
		 context.create.reaction('''On«metaclass.parameterName»Deleted''').afterElement(metaclass).deleted
	}
		
	override generateCorrespondenceMatches(UndecidedMatcherStatementBuilder builder) {
		iterateParameters [ reactionParameter, correspondingParameter |
			val newElement = getParameterName(reactionParameter, correspondingParameter)
			builder.vall(newElement).retrieve(correspondingParameter.metaclass).correspondingTo.affectedEObject.taggedWith(
				reactionParameter, correspondingParameter)
		]
	}

	override generateCorrespondenceActions(ActionStatementBuilder builder) {
		// delete 
		iterateParameters [ reactionParameter, correspondingParameter |
			val element = getParameterName(reactionParameter, correspondingParameter)
			builder.removeCorrespondenceBetween(element).and.affectedEObject.taggedWith(reactionParameter,correspondingParameter)
			builder.delete(element)		
		]
	}

	override equals(Object obj) {
		if(obj instanceof DeletedReactionGenerator){
			return metaclass == obj.metaclass
		}
		false
	}
	

}
