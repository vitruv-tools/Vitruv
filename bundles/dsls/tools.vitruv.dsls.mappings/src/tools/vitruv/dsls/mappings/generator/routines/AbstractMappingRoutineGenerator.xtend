package tools.vitruv.dsls.mappings.generator.routines

import java.util.List
import java.util.function.Consumer
import java.util.function.Function
import org.eclipse.emf.ecore.EcoreFactory
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.dsls.mappings.generator.AbstractMappingEntityGenerator
import tools.vitruv.dsls.mappings.generator.conditions.AbstractBidirectionalCondition
import tools.vitruv.dsls.mappings.generator.conditions.AbstractSingleSidedCondition
import tools.vitruv.dsls.reactions.builder.FluentReactionsLanguageBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.InputBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.MatcherOrActionBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.mappings.generator.conditions.FeatureConditionGenerator
import org.eclipse.xtext.xbase.XbaseFactory

abstract class AbstractMappingRoutineGenerator extends AbstractMappingEntityGenerator {

	private String name
	@Accessors(PUBLIC_GETTER)
	private FluentRoutineBuilder routine
	protected List<AbstractSingleSidedCondition> singleSidedConditions
	protected List<AbstractBidirectionalCondition> bidirectionConditions
	protected MappingRoutineStorage routineStorage


	new(String name) {
		this.name = name
	}

	def void prepareGenerator(List<AbstractSingleSidedCondition> singleSidedConditions,
		List<AbstractBidirectionalCondition> bidirectionConditions, MappingRoutineStorage routineStorage) {
		this.singleSidedConditions = singleSidedConditions
		this.bidirectionConditions = bidirectionConditions
		this.routineStorage = routineStorage
	}

	def generate(FluentReactionsLanguageBuilder create) {
		routine = create.routine('''«mappingName»_«name»'''.toString.toFirstLower).input(generateInput).generate		
	}

	protected def Consumer<InputBuilder> generateSingleEObjectInput() {
		[ builder |
			builder.affectedEObject.apply(EcoreFactory.eINSTANCE.ecorePackage.EObject)
		]
	}

	protected def Consumer<InputBuilder> generateMappingParameterInput() {
		[ builder |
			reactionParameters.forEach [
				builder.model(it.value.metaclass, it.parameterName)
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

	protected def getFeatureConditions() {
		singleSidedConditions.filter[it instanceof FeatureConditionGenerator].map[it as FeatureConditionGenerator]
	}

	def abstract Consumer<InputBuilder> generateInput()

	def abstract FluentRoutineBuilder generate(MatcherOrActionBuilder builder)

}
