package tools.vitruv.dsls.mappings.generator.reactions

import org.eclipse.emf.ecore.EReference
import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

class RemovedReactionGenerator extends AbstractReactionTypeGenerator {

	private MetaclassFeatureReference removeTarget = null

	new(MetaclassReference element) {
		super(element.metaclass)
	}

	new(MetaclassReference element, MetaclassFeatureReference insertedIn) {
		this(element)
		this.removeTarget = insertedIn
	}

	override generateTrigger(ReactionGeneratorContext context) {
		if (removeTarget !== null) {
			return context.create.reaction('''On«metaclass.parameterName»RemovedFrom«removeTarget.parameterName»''').
				afterElement(metaclass).removedFrom(removeTarget.feature as EReference)
		} else {
			return context.create.reaction('''On«metaclass.parameterName»RemovedAsRoot''').afterElement(metaclass).
				deleted // todo: is it the same?
		}
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
		if (obj instanceof RemovedReactionGenerator) {
			if (metaclass == obj.metaclass) {
				if (removeTarget === null) {
					return obj.removeTarget === null
				} else {
					if (obj.removeTarget !== null) {
						return removeTarget.feature == obj.removeTarget.feature
					}
				}
			}
		}
		false
	}

}
