package tools.vitruv.dsls.mappings.generator.action

import java.util.List
import java.util.function.Consumer
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.xbase.XFeatureCall
import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mappings.generator.conditions.AbstractBidirectionalCondition
import tools.vitruv.dsls.mappings.generator.reactions.AbstractReactionTypeGenerator
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.mappings.generator.reactions.InsertedReactionGenerator

class ReactionRoutineContentGenerator implements Consumer<ActionStatementBuilder>, FeatureRoutineCall {

	private AbstractReactionTypeGenerator generator
	private List<AbstractBidirectionalCondition> conditions
	private ReactionGeneratorContext context
	private ReactionFeatureConditionsGenerator featureGenerator
	@Accessors(PUBLIC_SETTER)
	private FluentRoutineBuilder currentRoutine
	private FluentRoutineBuilder subRoutine

	new(AbstractReactionTypeGenerator generator, List<AbstractBidirectionalCondition> conditions,
		ReactionFeatureConditionsGenerator featureGenerator, ReactionGeneratorContext context) {
		this.generator = generator
		this.conditions = conditions
		this.featureGenerator = featureGenerator
		this.context = context
	}

	private def getParameters() {
		generator.reactionParameters
	}

	public def void generateSubRoutine() {
		val name = '''«generator.reactionName»RepairSub'''.toString.toFirstLower
		subRoutine = context.create.routine(name).input [ builder |
			parameters.forEach [ parameter |
				builder.model(parameter.value.metaclass, parameter.value.name)
			]
		].match([ builder |
			generator.generateCorrespondenceMatches(builder)
		], true).action [ builder |
			generator.generateCorrespondenceActions(builder)
			if (generator instanceof InsertedReactionGenerator) {
				// do initialization of feature conditions
				featureGenerator.generateCorrespondenceInitialization(builder)
			}
			// enforce bidirectional consistency rules
			conditions.forEach[generate(context, generator, builder)]
		]
		context.segmentBuilder += subRoutine
	}

	override accept(ActionStatementBuilder builder) {
		featureGenerator.generate(generator, builder, currentRoutine, this)
	}

	override connectRoutineCall(FluentRoutineBuilder callingRoutine, XFeatureCall call) {
		// connect the routine call
		call.implicitReceiver = callingRoutine.jvmOperationRoutineFacade
		call.feature = subRoutine.jvmOperation
	}

}
