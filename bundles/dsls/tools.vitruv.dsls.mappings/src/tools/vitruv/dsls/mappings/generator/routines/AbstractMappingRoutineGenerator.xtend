package tools.vitruv.dsls.mappings.generator.routines

import java.util.ArrayList
import java.util.List
import java.util.function.Consumer
import org.eclipse.emf.ecore.EcoreFactory
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.mappings.generator.conditions.AbstractBidirectionalCondition
import tools.vitruv.dsls.mappings.generator.conditions.AbstractSingleSidedCondition
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.reactions.builder.FluentReactionsLanguageBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.InputBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.MatcherOrActionBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineCallParameter
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider
import tools.vitruv.dsls.reactions.codegen.ReactionsLanguageConstants
import tools.vitruv.dsls.reactions.reactionsLanguage.ActionStatement
import tools.vitruv.dsls.reactions.reactionsLanguage.ExecuteActionStatement
import tools.vitruv.dsls.reactions.reactionsLanguage.RoutineCallStatement

abstract class AbstractMappingRoutineGenerator extends AbstractRoutineContentGenerator {

	private String name
	@Accessors(PUBLIC_GETTER)
	private FluentRoutineBuilder routine
	protected List<RoutineCallParameter> callParameters = new ArrayList
	@Extension
	protected MappingRoutineStorage routineStorage
	private XExpression currentCallingContext

	new(String name) {
		this.name = name
	}

	override toString() {
		name
	}

	protected def initCallingContext(ActionStatement statement) {
		if (statement instanceof ExecuteActionStatement) {
			currentCallingContext = statement.code
		}
		if (statement instanceof RoutineCallStatement) {
			currentCallingContext = statement.code
		}
	}

	def void prepareGenerator(List<AbstractSingleSidedCondition> singleSidedConditions,
		List<AbstractSingleSidedCondition> correspondingSingleSidedConditions,
		List<AbstractBidirectionalCondition> bidirectionConditions, MappingRoutineStorage routineStorage) {
		this.singleSidedConditions = singleSidedConditions
		this.bidirectionConditions = bidirectionConditions
		this.routineStorage = routineStorage
		this.correspondingFeatureConditions = correspondingSingleSidedConditions.featureConditions.toList
	}

	def call(ActionStatementBuilder builder, AbstractMappingRoutineGenerator routine) {
		builder.call(routine.routine, routine.callParameters)
	}

	private def createCall(RoutineTypeProvider provider, FluentRoutineBuilder routine) {
		XbaseFactory.eINSTANCE.createXFeatureCall => [
			explicitOperationCall = true
			implicitReceiver = this.routine.getJvmOperationRoutineFacade(currentCallingContext)
			feature = routine.jvmOperation
		]
	}

	// call with any expressions
	def call(RoutineTypeProvider provider, FluentRoutineBuilder routine, List<XExpression> arguments) {
		val call = provider.createCall(routine)
		call.featureCallArguments += arguments
		call
	}

	def call(RoutineTypeProvider provider, AbstractMappingRoutineGenerator routine, List<XExpression> arguments) {
		provider.call(routine.routine, arguments)
	}

	// call with the routines parameters
	def call(RoutineTypeProvider provider, FluentRoutineBuilder routine) {
		provider.call(routine, reactionParameters.map[provider.variable(it.parameterName)])
	}

	def call(RoutineTypeProvider provider, AbstractMappingRoutineGenerator routine) {
		provider.call(routine.routine)
	}

	// call with variables from the execution scope
	def callViaVariables(RoutineTypeProvider provider, FluentRoutineBuilder routine,
		List<MappingParameter> parameters) {
		provider.call(routine, parameters.map[referenceLocalVariable])
	}

	def callViaVariables(RoutineTypeProvider provider, AbstractMappingRoutineGenerator routine,
		List<MappingParameter> parameters) {
		provider.callViaVariables(routine.routine, parameters)
	}

	def generate(FluentReactionsLanguageBuilder create) {
		routine = create.routine('''«mappingName»_«name»'''.toString.toFirstLower).input([ builder |
			generateInput(builder)
		]).generate
	}

	protected def generateSingleEObjectInput(InputBuilder builder) {
		builder.affectedEObject.apply(EcoreFactory.eINSTANCE.ecorePackage.EObject)
		callParameters += new RoutineCallParameter(ReactionsLanguageConstants.CHANGE_AFFECTED_ELEMENT_ATTRIBUTE)
	}

	protected def generateMappingParameterInput(InputBuilder builder) {
		generateMappingParameterInput(builder, reactionParameters)
	}

	protected def generateCorrespondingMappingParameterInput(InputBuilder builder) {
		generateMappingParameterInput(builder, correspondingParameters)
	}

	protected def generateMappingParameterInput(InputBuilder builder, List<MappingParameter> parameters) {
		parameters.forEach [
			builder.model(it.value.metaclass, it.parameterName)
			callParameters += new RoutineCallParameter(it.parameterName)
		]
	}

	protected def debugRoutine(MatcherOrActionBuilder builder) {
		builder.action [
			execute[
				XbaseFactory.eINSTANCE.createXReturnExpression
			]
		]
	}

	protected def noAction(ActionStatementBuilder builder) {
		builder.execute [
			XbaseFactory.eINSTANCE.createXReturnExpression
		]
	}

	def abstract void generateInput(InputBuilder builder)

	def abstract FluentRoutineBuilder generate(MatcherOrActionBuilder builder)

}
