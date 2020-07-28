package tools.vitruv.dsls.commonalities.generator.reactions.participation

import java.util.function.Function
import org.eclipse.xtext.xbase.XExpression
import tools.vitruv.dsls.commonalities.generator.reactions.helper.ReactionsGenerationHelper
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder
import tools.vitruv.dsls.reactions.builder.TypeProvider

import static extension tools.vitruv.dsls.commonalities.generator.reactions.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.participation.ParticipationContextHelper.*

class ParticipationObjectsRetrievalHelper extends ReactionsGenerationHelper {

	package new() {
	}

	def retrieveUnassertedParticipationObject(extension UndecidedMatcherStatementBuilder matcherBuilder,
		ParticipationClass participationClass, Function<TypeProvider, XExpression> correspondenceSource) {
		matcherBuilder.retrieveParticipationObject(participationClass, false, correspondenceSource)
	}

	def retrieveAssertedParticipationObject(extension UndecidedMatcherStatementBuilder matcherBuilder,
		ParticipationClass participationClass, Function<TypeProvider, XExpression> correspondenceSource) {
		matcherBuilder.retrieveParticipationObject(participationClass, true, correspondenceSource)
	}

	private def retrieveParticipationObject(extension UndecidedMatcherStatementBuilder matcherBuilder,
		ParticipationClass participationClass, boolean asserted,
		Function<TypeProvider, XExpression> correspondenceSource) {
		if (participationClass.isRootClass) {
			// Note: Depending on the participation's context, the participation's root object(s) may not exist.
			vall(participationClass.correspondingVariableName).retrieveOptional(participationClass.changeClass)
				.correspondingTo(correspondenceSource)
				.taggedWith(participationClass.correspondenceTag)
		} else {
			val vall = vall(participationClass.correspondingVariableName)
			var retrieval = if (asserted) {
				vall.retrieveAsserted(participationClass.changeClass)
			} else {
				vall.retrieve(participationClass.changeClass)
			}
			retrieval.correspondingTo(correspondenceSource)
				.taggedWith(participationClass.correspondenceTag)
		}
	}
}
