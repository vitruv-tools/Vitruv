package tools.vitruv.dsls.mappings.generator.routines

import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.MatcherOrActionBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

import static extension tools.vitruv.dsls.mappings.generator.utils.XBaseMethodUtils.*
import org.eclipse.xtext.common.types.TypesFactory
import org.eclipse.xtext.common.types.JvmTypeReference

class DeletedCheckRoutineGenerator extends AbstractMappingRoutineGenerator {

	new() {
		super('ElementDeletedCheck')
	}

	override generateInput() {
		generateSingleEObjectInput
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
			XbaseFactory.eINSTANCE.createXBlockExpression => [
				expressions += provider.initParametersToRetrieve
				iterateParameters([ reactionParameter, correspondingParameter |
					expressions += provider.checkRetrievedParameter(reactionParameter, correspondingParameter)
				])
//				expressions += provider.checkAllParametersExist
				print('')
			]
		]
	}

	private def initParametersToRetrieve(RoutineTypeProvider provider) {
		correspondingParameters.map [ parameter |
			provider.variableDeclaration(parameter)
		]
	}

	/**
	 * check if any of the retrieved exists, and if it does, remember the value in the baseParam
	 */
	private def checkRetrievedParameter(RoutineTypeProvider provider, MappingParameter reactionParameter,
		MappingParameter correspondingParameter) {
		val name = getParameterName(reactionParameter, correspondingParameter)
		val target = correspondingParameter.parameterName
		XbaseFactory.eINSTANCE.createXIfExpression => [
			^if = provider.notNull(name)
			then = XbaseFactory.eINSTANCE.createXAssignment => [				
				feature = target.retrieveVariable
				value = provider.variable(name)
			]
		]
	}

	private def checkAllParametersExist(RoutineTypeProvider provider) {
		XbaseFactory.eINSTANCE.createXIfExpression => [
			^if = provider.andChain(correspondingParameters.map [ correspondingParameter |
				val name = correspondingParameter.parameterName
				provider.notNull(name.retrieveVariableCall)
			])
			then = provider.callViaVariables(DeleteMappingRoutine.routine, correspondingParameters)
		]
	}
}
