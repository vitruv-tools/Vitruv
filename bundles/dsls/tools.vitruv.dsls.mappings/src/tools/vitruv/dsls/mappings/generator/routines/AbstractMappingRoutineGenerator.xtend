package tools.vitruv.dsls.mappings.generator.routines

import java.util.ArrayList
import java.util.List
import java.util.function.Consumer
import org.eclipse.emf.ecore.EcoreFactory
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.common.types.TypesFactory
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.mappings.generator.AbstractMappingEntityGenerator
import tools.vitruv.dsls.mappings.generator.conditions.AbstractBidirectionalCondition
import tools.vitruv.dsls.mappings.generator.conditions.AbstractSingleSidedCondition
import tools.vitruv.dsls.mappings.generator.conditions.FeatureConditionGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.reactions.builder.FluentReactionsLanguageBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.InputBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.MatcherOrActionBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineCallParameter
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider
import tools.vitruv.dsls.reactions.codegen.ReactionsLanguageConstants

import static extension tools.vitruv.dsls.mappings.generator.utils.XBaseMethodUtils.*

abstract class AbstractMappingRoutineGenerator extends AbstractMappingEntityGenerator {

	private String name
	@Accessors(PUBLIC_GETTER)
	private FluentRoutineBuilder routine
	protected List<AbstractSingleSidedCondition> singleSidedConditions
	protected List<AbstractBidirectionalCondition> bidirectionConditions
	protected List<FeatureConditionGenerator> correspondingFeatureConditions
	@Extension
	protected MappingRoutineStorage routineStorage
	protected List<RoutineCallParameter> callParameters = new ArrayList

	new(String name) {
		this.name = name
	}

	def void prepareGenerator(List<AbstractSingleSidedCondition> singleSidedConditions,
		List<AbstractSingleSidedCondition> correspondingSingleSidedConditions,
		List<AbstractBidirectionalCondition> bidirectionConditions, MappingRoutineStorage routineStorage) {
		this.singleSidedConditions = singleSidedConditions
		this.bidirectionConditions = bidirectionConditions
		this.routineStorage = routineStorage
		this.correspondingFeatureConditions = correspondingSingleSidedConditions.featureConditions.toList
	}

	protected def variableDeclaration(RoutineTypeProvider provider, MappingParameter parameter) {
		XbaseFactory.eINSTANCE.createXVariableDeclaration => [
			it.name = parameter.parameterName
			writeable = true
			type = provider.findTypeReference(parameter)
			TypesFactory.eINSTANCE.createJvmFormalParameter => [
				it.
			]
		]
	}

	def call(ActionStatementBuilder builder, AbstractMappingRoutineGenerator routine) {
		builder.call(routine.routine, routine.callParameters)
	}

	private def createCall(RoutineTypeProvider provider, AbstractMappingRoutineGenerator routine) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			implicitFirstArgument = this.routine.jvmOperationRoutineFacade
			feature = routine.routine.jvmOperation
		]
	}

	// call with any expressions
	def call(RoutineTypeProvider provider, AbstractMappingRoutineGenerator routine, List<XExpression> arguments) {
		val call = provider.createCall(routine)
		call.memberCallArguments += arguments
		call
	}

	// call with the routines parameters
	def call(RoutineTypeProvider provider, AbstractMappingRoutineGenerator routine) {
		provider.call(routine, reactionParameters.map[provider.variable(it.parameterName)])
	}

	// call with variables from the execution scope
	def callViaVariables(RoutineTypeProvider provider, AbstractMappingRoutineGenerator routine, List<MappingParameter> parameters) {
		provider.call(routine, parameters.map[it.parameterName.retrieveVariableCall])
	}

	def generate(FluentReactionsLanguageBuilder create) {
		routine = create.routine('''«mappingName»_«name»'''.toString.toFirstLower).input(generateInput).generate
	}

	protected def Consumer<InputBuilder> generateSingleEObjectInput() {
		[ builder |
			builder.affectedEObject.apply(EcoreFactory.eINSTANCE.ecorePackage.EObject)
			callParameters += new RoutineCallParameter(ReactionsLanguageConstants.CHANGE_AFFECTED_ELEMENT_ATTRIBUTE)
		]
	}

	protected def Consumer<InputBuilder> generateMappingParameterInput() {
		[ builder |
			reactionParameters.forEach [
				builder.model(it.value.metaclass, it.parameterName)
				callParameters += new RoutineCallParameter(it.parameterName)
			]
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

	protected def getFeatureConditions() {
		singleSidedConditions.featureConditions
	}

	protected def getFeatureConditions(List<AbstractSingleSidedCondition> conditions) {
		conditions.filter[it instanceof FeatureConditionGenerator].map[it as FeatureConditionGenerator]
	}

	def abstract Consumer<InputBuilder> generateInput()

	def abstract FluentRoutineBuilder generate(MatcherOrActionBuilder builder)

}
