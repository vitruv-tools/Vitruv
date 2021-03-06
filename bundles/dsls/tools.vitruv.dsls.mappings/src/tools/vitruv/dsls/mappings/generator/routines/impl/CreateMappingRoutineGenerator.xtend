package tools.vitruv.dsls.mappings.generator.routines.impl

import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.mappings.generator.routines.AbstractMappingRoutineGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.ExistingMappingCorrespondence
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.InputBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.MatcherOrActionBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

class CreateMappingRoutineGenerator extends AbstractMappingRoutineGenerator {

	new() {
		super('CreateMapping')
	}

	override generateInput(InputBuilder builder) {
		builder.generateMappingParameterInput
	}

	override generate(MatcherOrActionBuilder builder) {
		builder.match [ matchBuilder |
			matchBuilder.generateMatch
		]
		builder.action [ actionBuilder |
			actionBuilder.generateAction
		]
	}

	private def generateMatch(UndecidedMatcherStatementBuilder builder) {
		// just take the first element to require absence of all corresponding elements
		val taggingParameter = reactionParameters.get(0)
		correspondingParameters.forEach [ correspondingParameter |
			builder.requireAbsenceOf(correspondingParameter.value.metaclass).correspondingTo(
				taggingParameter.parameterName).taggedWith(taggingParameter, correspondingParameter)
		]
		// retrieve all existing mapping parameters
		correspondingParameters.filterExistingMappingParameters.forEach [
			val reactionParameter = it as ExistingMappingCorrespondence
			val correspondingParameter = reactionParameter.correspondence
			val tag = reactionParameter.tag
			val retrieval = builder.vall(reactionParameter.parameterName).retrieve(reactionParameter.value.metaclass).
				correspondingTo(correspondingParameter.parameterName)
			if (tag !== null) {
				retrieval.taggedWith(tag)
			}
		]
	}

	private def generateAction(ActionStatementBuilder builder) {
		// 1) create corresponding elements
		builder.createCorrespondingElements
		// 2) add all the correspondences
		builder.addCorrespondences
		// 3) init corresponding elements with featurecondtions
		builder.initWithFeatureConditions
		// 4) perform bidirectional conditions to further init the created models values
		builder.initWithBidirectionalConditions
	}

	private def createCorrespondingElements(ActionStatementBuilder builder) {
		// only create actual parameters
		correspondingParameters.filterNonExistingMappingParameters.forEach [ correspondingParameter |
			val newElement = correspondingParameter.parameterName
			var createMetaclass = getMappingParameterInstantiationMetaclass(correspondingParameter)
			builder.vall(newElement).create(createMetaclass)
		]
	}

	private def addCorrespondences(ActionStatementBuilder builder) {
		iterateParameters([ reactionParameter, correspondingParameter |
			val newElement = correspondingParameter.parameterName
			builder.addCorrespondenceBetween(newElement).and(reactionParameter.parameterName).taggedWith(
				reactionParameter, correspondingParameter)
		])
	}

	private def initWithFeatureConditions(ActionStatementBuilder builder) {
		val intializations = correspondingFeatureConditions.filter[it.hasCorrespondenceInitialization]
		if (!intializations.empty) {
			builder.execute([ provider |
				XbaseFactory.eINSTANCE.createXBlockExpression => [
					expressions += intializations.map[it.generateCorrespondenceInitialization(provider)]
				]
			])
		}
	}

	private def initWithBidirectionalConditions(ActionStatementBuilder builder) {
		// just call the bidirectional update routine
		builder.call(UpdateMappingRoutineGenerator.routine)
	}

}
