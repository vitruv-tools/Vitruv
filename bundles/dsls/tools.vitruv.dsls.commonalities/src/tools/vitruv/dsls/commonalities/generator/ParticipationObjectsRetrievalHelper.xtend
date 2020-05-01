package tools.vitruv.dsls.commonalities.generator

import java.util.function.Function
import org.eclipse.xtext.xbase.XExpression
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder
import tools.vitruv.dsls.reactions.builder.TypeProvider

import static extension tools.vitruv.dsls.commonalities.generator.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.ParticipationContextHelper.*

@GenerationScoped
package class ParticipationObjectsRetrievalHelper extends ReactionsGenerationHelper {

	package new() {
	}

	def retrieveParticipationObject(extension UndecidedMatcherStatementBuilder matcherBuilder,
		ParticipationClass participationClass, Function<TypeProvider, XExpression> correspondenceSource) {
		if (participationClass.isRootClass) {
			// Note: Depending on the context in which the participation
			// exists, the participation's root object(s) may not exist.
			vall(participationClass.correspondingVariableName).retrieveOptional(participationClass.changeClass)
				.correspondingTo(correspondenceSource)
				.taggedWith(participationClass.correspondenceTag)
		} else {
			vall(participationClass.correspondingVariableName).retrieveAsserted(participationClass.changeClass)
				.correspondingTo(correspondenceSource)
				.taggedWith(participationClass.correspondenceTag)
		}
	}
}
