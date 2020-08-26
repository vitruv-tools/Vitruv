package tools.vitruv.dsls.commonalities.generator.reactions.matching

import com.google.inject.Inject
import java.util.HashMap
import java.util.Map
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.generator.reactions.attribute.AttributeChangeReactionsHelper
import tools.vitruv.dsls.commonalities.generator.reactions.condition.CheckedParticipationConditionsHelper
import tools.vitruv.dsls.commonalities.generator.reactions.helper.ReactionsGenerationHelper
import tools.vitruv.dsls.commonalities.generator.reactions.util.ReactionsHelper.RoutineCallContext
import tools.vitruv.dsls.commonalities.generator.reactions.util.ReactionsSegmentScopedProvider
import tools.vitruv.dsls.commonalities.language.ParticipationAttribute
import tools.vitruv.dsls.commonalities.language.ParticipationAttributeOperand
import tools.vitruv.dsls.commonalities.participation.ParticipationContext
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineCallParameter
import tools.vitruv.extensions.dslruntime.commonalities.BooleanResult

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.dsls.commonalities.generator.reactions.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.ReactionsHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.XbaseHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*
import static extension tools.vitruv.dsls.commonalities.participation.ParticipationConditionHelper.*

/**
 * Generates the reactions for attribute changes which might affect the
 * participation's checked conditions.
 * <p>
 * If a change is detected and a corresponding intermediate already exists, we
 * check if the conditions are still fulfilled and otherwise delete the
 * intermediate. If no intermediate exists, we invoke the participation
 * matching.
 */
class ParticipationConditionMatchingReactionsBuilder extends ReactionsGenerationHelper {

	static class Provider extends ReactionsSegmentScopedProvider<ParticipationConditionMatchingReactionsBuilder> {

		protected override createFor(FluentReactionsSegmentBuilder segment) {
			return new ParticipationConditionMatchingReactionsBuilder(segment).injectMembers
		}

		def void generateParticipationConditionReactions(FluentReactionsSegmentBuilder segment,
			ParticipationContext participationContext) {
			getFor(segment).generateReactions(participationContext)
		}
	}

	@Inject extension AttributeChangeReactionsHelper attributeChangeReactionsHelper
	@Inject extension CheckedParticipationConditionsHelper checkedParticipationConditionsHelper
	@Inject extension DeleteObjectRoutineBuilder.Provider deleteObjectRoutineBuilderProvider
	@Inject extension MatchParticipationRoutineBuilder.Provider matchParticipationRoutineBuilderProvider

	val FluentReactionsSegmentBuilder segment

	val Map<ParticipationContext, FluentRoutineBuilder> validateParticipationRoutines = new HashMap

	private new(FluentReactionsSegmentBuilder segment) {
		checkNotNull(segment, "segment is null")
		this.segment = segment
	}

	// Dummy constructor for Guice
	package new() {
		this.segment = null
		throw new IllegalStateException("Use the Provider to get instances of this class!")
	}

	def void generateReactions(ParticipationContext participationContext) {
		// Generate routines:
		segment += participationContext.validateParticipationRoutine
		segment += segment.deleteObjectRoutine
		segment += segment.getMatchParticipationRoutine(participationContext)

		// Generate reactions:
		val contextReactionNameSuffix = participationContext.reactionNameSuffix
		participationContext.checkedParticipationConditions.forEach [ condition, conditionIndex |
			// Left operand:
			val leftOperand = condition.leftOperand
			if (leftOperand instanceof ParticipationAttributeOperand) {
				val reactionNameSuffix = getLeftOperandReactionNameSuffix(conditionIndex) + contextReactionNameSuffix
				val attribute = leftOperand.participationAttribute
				participationContext.reactionsForAttributeChange(attribute, reactionNameSuffix)
			}

			// Right operands:
			condition.rightOperands.filter(ParticipationAttributeOperand).forEach [ operand, operandIndex |
				val reactionNameSuffix = getRightOperandReactionNameSuffix(conditionIndex, operandIndex)
					+ contextReactionNameSuffix
				val attribute = operand.participationAttribute
				participationContext.reactionsForAttributeChange(attribute, reactionNameSuffix)
			]
		]
	}

	private static def String getLeftOperandReactionNameSuffix(int conditionIndex) {
		return '''_forCondition_«conditionIndex»_leftOperand'''
	}

	private static def String getRightOperandReactionNameSuffix(int conditionIndex, int operandIndex) {
		return '''_forCondition_«conditionIndex»_rightOperand_«operandIndex»'''
	}

	private def reactionsForAttributeChange(ParticipationContext participationContext,
		ParticipationAttribute attribute, String reactionNameSuffix) {
		attribute.getAttributeChangeReactions(reactionNameSuffix) [ changeType , it |
			// Check if any previously existing participation is no longer valid:
			call(participationContext.validateParticipationRoutine)

			// Check if the participation can be matched:
			call(segment.getMatchParticipationRoutine(participationContext),
				new RoutineCallParameter[affectedEObject], new RoutineCallParameter(booleanLiteral(true)),
				new RoutineCallParameter[findDeclaredType(BooleanResult).noArgsConstructorCall])
		]
	}

	/**
	 * Checks if there is a corresponding intermediate object for the given
	 * participation object and if the participation's checked conditions are
	 * still fulfilled and otherwise deletes the intermediate.
	 */
	private def getValidateParticipationRoutine(ParticipationContext participationContext) {
		return validateParticipationRoutines.computeIfAbsent(participationContext) [
			val participation = participationContext.participation
			val commonality = participation.containingCommonality
			val extension routineCallContext = new RoutineCallContext
			create.routine('''validateParticipation«participationContext.reactionNameSuffix»''')
				.input [
					model(EcorePackage.Literals.EOBJECT, PARTICIPATION_OBJECT)
				]
				.match [
					// Only executed if we find a corresponding intermediate:
					vall(INTERMEDIATE).retrieve(commonality.changeClass)
						.correspondingTo(PARTICIPATION_OBJECT)
					participationContext.classes.filter[!isExternal].forEach [ extension contextClass |
						vall(participationClass.correspondingVariableName)
							.retrieveAsserted(participationClass.changeClass)
							.correspondingTo(INTERMEDIATE)
							.taggedWith(participationClass.correspondenceTag)
					]
				]
				.action[
					execute [ extension typeProvider |
						// Check all checked participation conditions. If at least one of them is no longer fulfilled,
						// delete the commonality instance.
						XbaseFactory.eINSTANCE.createXIfExpression => [
							^if = participationContext.checkParticipationConditions(typeProvider)
								.negated(typeProvider)
							then = routineCallContext.createRoutineCall(typeProvider, segment.deleteObjectRoutine,
								variable(INTERMEDIATE))
						]
					].setCallerContext
				].setCaller
		]
	}
}
