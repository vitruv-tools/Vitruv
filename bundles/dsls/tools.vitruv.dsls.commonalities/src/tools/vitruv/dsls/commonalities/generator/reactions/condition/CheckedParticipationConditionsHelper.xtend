package tools.vitruv.dsls.commonalities.generator.reactions.condition

import com.google.inject.Inject
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.generator.reactions.condition.ParticipationConditionOperatorHelper.ParticipationConditionOperatorContext
import tools.vitruv.dsls.commonalities.generator.reactions.helper.ReactionsGenerationHelper
import tools.vitruv.dsls.commonalities.generator.reactions.participation.ParticipationObjectsHelper
import tools.vitruv.dsls.commonalities.generator.reactions.util.XbaseHelper
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.participation.ParticipationContext
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.matching.ParticipationObjects

import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.XbaseHelper.*
import static extension tools.vitruv.dsls.commonalities.participation.ParticipationConditionHelper.*

class CheckedParticipationConditionsHelper extends ReactionsGenerationHelper {
	@Inject extension ParticipationConditionOperatorHelper participationConditionOperatorHelper
	@Inject extension ParticipationObjectsHelper participationObjectsHelper

	package new() {
	}

	def XExpression checkParticipationConditions(ParticipationContext participationContext,
		extension TypeProvider typeProvider) {
		return checkParticipationConditions(participationContext, typeProvider, null)
	}

	/**
	 * Returns an expression which checks the participation conditions relevant
	 * for the given participation context.
	 * <p>
	 * The expression returns <code>false</code> if there is at least one
	 * unfulfilled condition.
	 * <p>
	 * The <code>participationObjects</code> is optional. If it is
	 * <code>null</code>, the participation objects are retrieved from the
	 * current routine context. Otherwise they are retrieved from the given
	 * {@link ParticipationObjects}.
	 */
	def XExpression checkParticipationConditions(ParticipationContext participationContext,
		extension TypeProvider typeProvider, XFeatureCall participationObjects) {
		val operatorContext = new ParticipationConditionOperatorContext(typeProvider) {
			override getParticipationObject(ParticipationClass participationClass) {
				if (participationObjects !== null) {
					// Retrieve object from ParticipationObjects:
					return participationClass.getParticipationObject(participationObjects.copy, typeProvider)
				} else {
					// Retrieve object from routine context:
					super.getParticipationObject(participationClass)
				}
			}
		}
		val checks = participationContext.checkedParticipationConditions.map [
			check(operatorContext)
		].toList

		if (checks.empty) {
			// There are no conditions to check:
			return XbaseFactory.eINSTANCE.createXBooleanLiteral => [
				isTrue = true
			]
		} else {
			return checks.reduce [check1, check2 |
				XbaseHelper.and(check1, check2, typeProvider)
			]
		}
	}
}
