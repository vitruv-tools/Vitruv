package tools.vitruv.dsls.mappings.generator.routines.impl

import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.mappings.generator.routines.AbstractMappingRoutineGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.InputBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.MatcherOrActionBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder
import tools.vitruv.dsls.reactions.builder.TypeProvider

import static extension tools.vitruv.dsls.mappings.generator.utils.XBaseMethodUtils.*

class DeletedCheckRoutineGenerator extends AbstractMappingRoutineGenerator {

	new() {
		super('ElementDeletedCheck')
	}
	
	override generateInput(InputBuilder builder) {
		builder.generateSingleEObjectInput
	}

	/**
	 * match:
	 *  - retrieve optional all possible corresponding elements (by all n x m tags so it doesnt 
	 * 		matter which one the affectedEObject actually is, as long as its part of the mapping)
	 * action:
	 * -  if any of the retrieved values is not null, then delete the mapping
	 */
	override generate(MatcherOrActionBuilder builder) {
		builder.match [ matchBuilder |
			matchBuilder.generateMatch
		]
		builder.action [ actionBuilder |
			actionBuilder.generateAction
		]
	}

	private def generateMatch(UndecidedMatcherStatementBuilder builder) {
		iterateParameters([ reactionParameter, correspondingParameter |
			val variable = getParameterName(reactionParameter, correspondingParameter)
			builder.vall(variable).retrieveOptional(correspondingParameter.value.metaclass).correspondingTo.
				affectedEObject.taggedWith(reactionParameter, correspondingParameter)
		])
	}

	private def generateAction(ActionStatementBuilder builder) {
		builder.call [ provider |
			provider.generateCheckForDeletion
		].initCallingContext
	}

	private def generateCheckForDeletion(TypeProvider provider) {
		XbaseFactory.eINSTANCE.createXBlockExpression => [
			expressions += provider.initParametersToRetrieve
			iterateParameters([ reactionParameter, correspondingParameter |
				expressions += provider.checkRetrievedParameter(reactionParameter, correspondingParameter)
			])
			expressions += provider.checkAllParametersExist
		]
	}

	private def initParametersToRetrieve(TypeProvider provider) {
		correspondingParameters.map [ parameter |
			provider.variableDeclaration(parameter)
		]
	}

	/**
	 * check if any of the retrieved exists, and if it does, remember the value in the baseParam
	 */
	private def checkRetrievedParameter(TypeProvider provider, MappingParameter reactionParameter,
		MappingParameter correspondingParameter) {
		val name = getParameterName(reactionParameter, correspondingParameter)
		val target = correspondingParameter
		XbaseFactory.eINSTANCE.createXIfExpression => [
			^if = provider.optionalNotEmpty(provider.variable(name))
			then = XbaseFactory.eINSTANCE.createXAssignment => [
				feature = target.retrieveLocalVariable
				value = provider.optionalGet(provider.variable(name))
			]
		]
	}

	private def checkAllParametersExist(TypeProvider provider) {
		XbaseFactory.eINSTANCE.createXIfExpression => [
			^if = provider.andChain(correspondingParameters.map [ correspondingParameter |
				provider.notNull(correspondingParameter.referenceLocalVariable)
			])
			then = provider.callViaVariables(DeleteMappingRoutineGenerator.routine, correspondingParameters)
		]
	}
}
