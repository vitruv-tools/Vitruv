package tools.vitruv.dsls.mappings.generator.routines

import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.lib.Functions.Function0
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.InputBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.MatcherOrActionBuilder
import tools.vitruv.dsls.reactions.builder.TypeProvider

abstract class AbstractRetrievalCheckRoutineGenerator extends AbstractMappingRoutineGenerator {

	new(String name) {
		super(name)
	}

	override generateInput(InputBuilder builder) {
		builder.generateSingleEObjectInput
	}

	override generate(MatcherOrActionBuilder builder) {
		builder.action [ actionBuilder |
			actionBuilder.generateAction
		]
	}

	protected def generateAction(ActionStatementBuilder builder) {
		val generator = new MappingParameterRetrievalGenerator(this)
		builder.call [ provider |
			generator.generate(provider, provider.onSuccessfullyRetrievingParameters,
				provider.onFailedToRetrieveParameters)
		].initCallingContext
	}

	abstract def Function0<XExpression> onSuccessfullyRetrievingParameters(TypeProvider provider)

	abstract def Function0<XExpression> onFailedToRetrieveParameters(TypeProvider provider)
}
