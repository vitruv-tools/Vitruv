package tools.vitruv.dsls.commonalities.generator.reactions.attribute

import com.google.inject.Inject
import java.util.HashMap
import java.util.Map
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.generator.reactions.attribute.AttributeMappingOperatorHelper.AttributeMappingOperatorContext
import tools.vitruv.dsls.commonalities.generator.reactions.helper.ReactionsGenerationHelper
import tools.vitruv.dsls.commonalities.generator.reactions.participation.ParticipationObjectsRetrievalHelper
import tools.vitruv.dsls.commonalities.generator.reactions.util.ReactionsSegmentScopedProvider
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.dsls.commonalities.generator.reactions.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

class ApplyCommonalityAttributesRoutineBuilder extends ReactionsGenerationHelper {
	static class Provider extends ReactionsSegmentScopedProvider<ApplyCommonalityAttributesRoutineBuilder> {
		protected override createFor(FluentReactionsSegmentBuilder segment) {
			return new ApplyCommonalityAttributesRoutineBuilder(segment).injectMembers
		}

		def getApplyAttributesRoutine(FluentReactionsSegmentBuilder segment, Participation participation) {
			return getFor(segment).getApplyAttributesRoutine(participation)
		}
	}

	@Inject extension AttributeMappingHelper attributeMappingHelper
	@Inject extension ParticipationObjectsRetrievalHelper participationObjectsRetrievalHelper

	val Map<Participation, FluentRoutineBuilder> routines = new HashMap

	private new(FluentReactionsSegmentBuilder segment) {
		checkNotNull(segment, "segment is null")
		// Note: The reactions segment is unused here. But having the provider
		// require it ensures that we only create one instance of this class
		// per reactions segment.
	}

	// Dummy constructor for Guice
	package new() {
		throw new IllegalStateException("Use the Factory to create instances of this class!")
	}

	def getApplyAttributesRoutine(Participation participation) {
		return routines.computeIfAbsent(participation) [
			val commonality = participation.declaringCommonality
			create.routine('''applyCommonalityAttributes_«participation.reactionName»''')
				.input [
					model(commonality.changeClass, INTERMEDIATE)
				]
				.match [
					participation.allClasses.forEach [ participationClass |
						retrieveAssertedParticipationObject(participationClass) [
							variable(INTERMEDIATE) // correspondence source
						]
					]
				]
				.action [
					execute [ extension typeProvider |
						XbaseFactory.eINSTANCE.createXBlockExpression => [
							val participationClassToObject = typeProvider.participationClassToOptionalObject
							val operatorContext = new AttributeMappingOperatorContext(typeProvider,
								[variable(INTERMEDIATE)], participationClassToObject)
							participation.relevantWriteMappings.forEach [ mapping |
								expressions += mapping.applyWriteMapping(operatorContext)
							]
						]
					]
				]
		]
	}
}
