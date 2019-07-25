package tools.vitruv.dsls.mappings.generator.routines

import java.util.ArrayList
import java.util.HashMap
import java.util.List
import java.util.Map
import java.util.function.Consumer
import org.eclipse.emf.ecore.EcoreFactory
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.common.types.JvmIdentifiableElement
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
	protected Map<String, JvmIdentifiableElement> localVariables = new HashMap

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
		val name = parameter.parameterName
		val declaration = XbaseFactory.eINSTANCE.createXVariableDeclaration => [
			it.name = name
			writeable = true
			type = provider.findTypeReference(parameter)
		]
		localVariables.put(name, declaration)
		declaration
	}

	protected def retrieveLocalVariable(MappingParameter parameter) {
		localVariables.get(parameter.parameterName)
	}

	protected def callLocalVariable(MappingParameter parameter) {
		XbaseFactory.eINSTANCE.createXFeatureCall => [
			// explicitOperationCall = true
			feature = parameter.retrieveLocalVariable
		]
	}

	def call(ActionStatementBuilder builder, AbstractMappingRoutineGenerator routine) {
		builder.call(routine.routine, routine.callParameters)
	}

	private def createCall(RoutineTypeProvider provider, AbstractMappingRoutineGenerator routine) {
		XbaseFactory.eINSTANCE.createXFeatureCall => [
			explicitOperationCall = true
			implicitReceiver = this.routine.jvmOperationRoutineFacade
			feature = routine.routine.jvmOperation
		]
	}

	// call with any expressions
	def call(RoutineTypeProvider provider, AbstractMappingRoutineGenerator routine, List<XExpression> arguments) {
		val call = provider.createCall(routine)
		call.featureCallArguments += arguments
		call
	}

	// call with the routines parameters
	def call(RoutineTypeProvider provider, AbstractMappingRoutineGenerator routine) {
		provider.call(routine, reactionParameters.map[provider.variable(it.parameterName)])
	}

	// call with variables from the execution scope
	def callViaVariables(RoutineTypeProvider provider, AbstractMappingRoutineGenerator routine,
		List<MappingParameter> parameters) {
		provider.call(routine, parameters.map[callLocalVariable])
	}

	def generate(FluentReactionsLanguageBuilder create) {
		routine = create.routine('''«mappingName»_«name»'''.toString.toFirstLower).input(generateInput).generate
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

	protected def getFeatureConditions() {
		singleSidedConditions.featureConditions
	}

	protected def getFeatureConditions(List<AbstractSingleSidedCondition> conditions) {
		conditions.filter[it instanceof FeatureConditionGenerator].map[it as FeatureConditionGenerator]
	}

	def abstract Consumer<InputBuilder> generateInput()

	def abstract FluentRoutineBuilder generate(MatcherOrActionBuilder builder)

}
