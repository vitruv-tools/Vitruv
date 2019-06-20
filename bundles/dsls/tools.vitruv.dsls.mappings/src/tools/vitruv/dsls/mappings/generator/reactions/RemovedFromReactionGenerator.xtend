package tools.vitruv.dsls.mappings.generator.reactions

import org.eclipse.emf.ecore.EReference
import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

class RemovedFromReactionGenerator extends AbstractReactionTypeGenerator {

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
			return context.create.reaction('''on«metaclass.parameterName»RemovedFrom«removeTarget.parameterName»''').
				afterElement(metaclass).removedFrom(removeTarget.feature as EReference)
		} else {
			return context.create.reaction('''on«metaclass.parameterName»RemovedAsRoot''').afterElement(metaclass).
				deleted // todo: is it the same?
		}
	}

	override generateCorrespondenceMatches(UndecidedMatcherStatementBuilder builder) {
		correspondingParameters.forEach [
			builder.vall(it.parameterName).retrieve(it.metaclass).correspondingTo.affectedEObject.taggedWith(
				reactionParameters.get(0), it)
		]
	}

	override generateCorrespondenceActions(ActionStatementBuilder builder) {
		// delete 
		correspondingParameters.forEach [
			builder.delete(it.parameterName)
		]
	}

	override equals(Object obj) {
		if (obj instanceof RemovedFromReactionGenerator) {
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
