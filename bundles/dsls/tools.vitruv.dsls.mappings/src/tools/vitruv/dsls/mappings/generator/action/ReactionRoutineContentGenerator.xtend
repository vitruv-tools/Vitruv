package tools.vitruv.dsls.mappings.generator.action

import java.util.ArrayList
import java.util.HashMap
import java.util.List
import java.util.Map
import java.util.function.Consumer
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.xbase.XFeatureCall
import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mappings.generator.conditions.AbstractBidirectionalCondition
import tools.vitruv.dsls.mappings.generator.reactions.AbstractReactionTypeGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineCallParameter
import tools.vitruv.dsls.reactions.codegen.ReactionsLanguageConstants

class ReactionRoutineContentGenerator implements Consumer<ActionStatementBuilder>, FeatureRoutineCall {

	private AbstractReactionTypeGenerator generator
	private List<AbstractBidirectionalCondition> conditions
	private ReactionGeneratorContext context
	private ReactionFeatureConditionsGenerator featureGenerator
	private Map<MappingParameter, FluentRoutineBuilder> subRoutines = new HashMap
	@Accessors(PUBLIC_GETTER)
	private boolean usesSubRoutines = false
	@Accessors(PUBLIC_SETTER)
	private FluentRoutineBuilder currentRoutine

	new(AbstractReactionTypeGenerator generator, List<AbstractBidirectionalCondition> conditions,
		ReactionFeatureConditionsGenerator featureGenerator, ReactionGeneratorContext context) {
		this.generator = generator
		this.conditions = conditions
		this.featureGenerator = featureGenerator
		this.context = context
	}

	public def generateSubRoutines() {
		// create sub routines
		if (featureGenerator.hasAnyMatchingFeatureConditions) {
			usesSubRoutines = true
			generator.reactionParameters.forEach [
				createSubRoutine
			]
		}
	}

	override accept(ActionStatementBuilder builder) {
		if (usesSubRoutines) {
			featureGenerator.generate(builder, currentRoutine, this)
			// find all parameters for which no feature conditions exist and just call the sub routine directly
			generator.reactionParameters.forEach [
				if (!featureGenerator.hasFeatureConditionFor(it)) {
					val routine = subRoutines.get(it)
					val routineCallParameter = new ArrayList
					routineCallParameter +=
						new RoutineCallParameter(ReactionsLanguageConstants.CHANGE_AFFECTED_ELEMENT_ATTRIBUTE)
					if (generator.usesNewValue) {
						routineCallParameter +=
							new RoutineCallParameter(ReactionsLanguageConstants.CHANGE_NEW_VALUE_ATTRIBUTE)
					}
					builder.call(
						routine,
						routineCallParameter
					)
				}
			]
		} else {
			// create actions directly in routine
			generator.reactionParameters.forEach [
				builder.createRoutineAction(it)
			]
		}
	}

	private def createRoutineAction(ActionStatementBuilder builder, MappingParameter parameter) {
		generator.generateCorrespondenceActions(builder, parameter)
		// enforce bidirectional consistency rules
		conditions.forEach[generate(context, generator, builder, parameter)]
	}

	private def createSubRoutine(MappingParameter parameter) {
		val name = '''«generator.reactionName»Repair«parameter.value.name.toFirstUpper»'''.toString.toFirstLower
		val routine = context.create.routine(name).input [ builder |
			builder.affectedEObject.apply(generator.metaclass)
			if (generator.usesNewValue) {
				builder.newValue.apply(generator.metaclass)
			}
		].match([ builder |
			generator.generateCorrespondenceMatches(builder, parameter)
		], true).action [ builder |
			builder.createRoutineAction(parameter)
		]
		subRoutines.put(parameter, routine)
		context.segmentBuilder += routine
	}

	override connectRoutineCall(MappingParameter parameter, FluentRoutineBuilder callingRoutine, XFeatureCall call) {
		// connect the routine call
		val routine = subRoutines.get(parameter)
		call.implicitReceiver = callingRoutine.jvmOperationRoutineFacade
		call.feature = routine.jvmOperation
	}

}