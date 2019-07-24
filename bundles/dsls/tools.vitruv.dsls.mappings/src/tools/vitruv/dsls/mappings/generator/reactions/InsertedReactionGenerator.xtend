package tools.vitruv.dsls.mappings.generator.reactions

import org.eclipse.emf.ecore.EReference
import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

class InsertedReactionGenerator extends AbstractContainingReactionTypeGenerator {

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
			this.usesNewValue = true
			this.reactionName = '''«metaclass.parameterName»InsertedIn«insertTarget.parameterName»'''
			return context.create.reaction(reactionName()).afterElement(metaclass).insertedIn(
				insertTarget.feature as EReference)
		} else {
			this.reactionName = '''«metaclass.parameterName»InsertedAsRoot'''
			return context.create.reaction(reactionName()).afterElement(metaclass).created
		}
	}

	override toString() '''
	«metaclass.parameterName» inserted «IF insertTarget!==null»in «insertTarget.parameterName»«ELSE»as root«ENDIF»'''

	override generateCorrespondenceMatches(UndecidedMatcherStatementBuilder builder) {
		// just take the first element to require absence of all corresponding elements
		val taggingParameter = reactionParameters.get(0)
		correspondingParameters.forEach [ correspondingParameter |
			builder.requireAbsenceOf(correspondingParameter.value.metaclass).correspondingTo.matchingElement.taggedWith(
				taggingParameter, correspondingParameter)
		]
	}

	override generateCorrespondenceActions(ActionStatementBuilder builder) {
		// 1) create corresponding elements
		correspondingParameters.forEach [ correspondingParameter |
			val newElement = correspondingParameter.newElementName
			builder.vall(newElement).create(correspondingParameter.value.metaclass)
		]
		// 2) create all the correspondences
		iterateParameters([ reactionParameter, correspondingParameter |
			val newElement = correspondingParameter.newElementName
			builder.addCorrespondenceBetween(newElement).and.matchingElement.taggedWith(reactionParameter,
				correspondingParameter)
		])
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
