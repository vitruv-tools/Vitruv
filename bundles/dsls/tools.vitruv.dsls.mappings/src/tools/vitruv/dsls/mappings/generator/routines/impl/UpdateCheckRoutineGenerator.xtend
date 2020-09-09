package tools.vitruv.dsls.mappings.generator.routines.impl

import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.mappings.generator.conditions.impl.BidirectionalMappingRoutineGenerator
import tools.vitruv.dsls.mappings.generator.routines.AbstractRetrievalCheckRoutineGenerator
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.InputBuilder
import tools.vitruv.dsls.reactions.builder.TypeProvider

import static extension tools.vitruv.dsls.mappings.generator.utils.XBaseMethodFinder.*

class UpdateCheckRoutineGenerator extends AbstractRetrievalCheckRoutineGenerator {

	static val ROUTINE_NAME_PARAMETER = 'routineName'

	new() {
		super('BidirectionalCheck')
	}

	override generateInput(InputBuilder builder) {
		builder.generateSingleEObjectInput
		builder.plain(String, ROUTINE_NAME_PARAMETER)
	}

	override onSuccessfullyRetrievingParameters(TypeProvider provider) {
		[
			XbaseFactory.eINSTANCE.createXBlockExpression => [
				//call all workaround bidirectional condition routines
				bidirectionConditions.forEach [ condition |
					if (condition instanceof BidirectionalMappingRoutineGenerator) {
						expressions += provider.createBidirectionalRoutineCall(condition)
					}
				]
			]
		]
	}

	private def createBidirectionalRoutineCall(TypeProvider provider,
		BidirectionalMappingRoutineGenerator condition) {
		val routineName = condition.routine.name
		val routine = condition.targetRoutineBuilder
		XbaseFactory.eINSTANCE.createXIfExpression => [
			^if = XbaseFactory.eINSTANCE.createXBinaryOperation => [
				leftOperand = provider.variable(ROUTINE_NAME_PARAMETER)
				feature = provider.tripleEquals
				rightOperand = XbaseFactory.eINSTANCE.createXStringLiteral => [
					value = routineName
				]
			]
			then = provider.callViaVariables(routine, reactionParameters)
		]
	}

	override onFailedToRetrieveParameters(TypeProvider provider) {
		// nothing to do
		null
	}

}
