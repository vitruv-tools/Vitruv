package tools.vitruv.dsls.mappings.generator.reactions

import org.eclipse.emf.ecore.EReference
import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

import static tools.vitruv.dsls.mappings.generator.XExpressionParser.*

class InsertedReactionGenerator extends AbstractReactionTypeGenerator {

	private MetaclassFeatureReference insertTarget = null

	new(MetaclassReference element) {
		super(element.metaclass)
	}

	new(MetaclassReference element, MetaclassFeatureReference insertedIn) {
		this(element)
		this.insertTarget = insertedIn
	}

	override generateTrigger(ReactionGeneratorContext context) {
		if (insertTarget !== null) {
			return context.create.reaction('''On«metaclass.parameterName»InsertedIn«insertTarget.parameterName»''').
				afterElement(metaclass).insertedIn(insertTarget.feature as EReference)
		} else {
			return context.create.reaction('''On«metaclass.parameterName»InsertedAsRoot''').afterElement(metaclass).
				created
		}
	}

	override generateCorrespondenceMatches(UndecidedMatcherStatementBuilder builder) {
		iterateParameters [ reactionParameter, correspondingParameter |
			builder.requireAbsenceOf(correspondingParameter.metaclass).correspondingTo.affectedEObject.taggedWith(
				reactionParameter, correspondingParameter)
		]
	}

	override generateCorrespondenceActions(ActionStatementBuilder builder) {
		// create corresponding elements
		correspondingParameters.forEach[
			val newElement = it.newElementName
			builder.vall(newElement).create(it.metaclass)			
		]
		iterateParameters [ reactionParameter, correspondingParameter |
			val newElement = correspondingParameter.newElementName
			builder.addCorrespondenceBetween(newElement).and.affectedEObject.taggedWith(reactionParameter,
				correspondingParameter)
		]
	}

	override equals(Object obj) {
		if (obj instanceof InsertedReactionGenerator) {
			if (metaclass == obj.metaclass) {
				if (insertTarget === null) {
					return obj.insertTarget === null
				} else {
					if (obj.insertTarget !== null) {
						return insertTarget.feature == obj.insertTarget.feature
					}
				}
			}
		}
		false
	}

}
