package tools.vitruv.dsls.commonalities.generator.reactions.attribute

import com.google.inject.Inject
import java.util.HashMap
import java.util.Map
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.generator.reactions.attribute.AttributeMappingOperatorHelper.AttributeMappingOperatorContext
import tools.vitruv.dsls.commonalities.generator.reactions.helper.ReactionsGenerationHelper
import tools.vitruv.dsls.commonalities.generator.reactions.participation.ParticipationObjectsHelper
import tools.vitruv.dsls.commonalities.generator.reactions.util.ReactionsSegmentScopedProvider
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder
import tools.vitruv.extensions.dslruntime.commonalities.matching.ParticipationObjects

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.dsls.commonalities.generator.reactions.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

class ApplyParticipationAttributesRoutineBuilder extends ReactionsGenerationHelper {

	static class Provider extends ReactionsSegmentScopedProvider<ApplyParticipationAttributesRoutineBuilder> {

		protected override createFor(FluentReactionsSegmentBuilder segment) {
			return new ApplyParticipationAttributesRoutineBuilder(segment).injectMembers
		}

		def getApplyParticipationAttributesRoutine(FluentReactionsSegmentBuilder segment, Participation participation) {
			return this.getFor(segment).getApplyAttributesRoutine(participation)
		}
	}

	@Inject extension AttributeMappingHelper attributeMappingHelper
	@Inject extension ParticipationObjectsHelper participationObjectsHelper

	val Map<Participation, FluentRoutineBuilder> applyParticipationAttributesRoutines = new HashMap

	private new(FluentReactionsSegmentBuilder segment) {
		checkNotNull(segment, "segment is null")
		// Note: The reactions segment is unused here. But having the provider
		// require it ensures that we only create one instance of this class
		// per reactions segment.
	}

	// Dummy constructor for Guice
	package new() {
		throw new IllegalStateException("Use the Provider to get instances of this class!")
	}

	def getApplyAttributesRoutine(Participation participation) {
		checkNotNull(participation, "participation is null")
		return applyParticipationAttributesRoutines.computeIfAbsent(participation) [
			val commonality = participation.declaringCommonality
			create.routine('''applyParticipationAttributes_«participation.reactionName»''')
				.input [
					model(commonality.changeClass, INTERMEDIATE)
					plain(ParticipationObjects, PARTICIPATION_OBJECTS)
				]
				.action [
					update(INTERMEDIATE) [ extension typeProvider |
						XbaseFactory.eINSTANCE.createXBlockExpression => [
							val participationObjectVars = participation.getParticipationObjectVars(
								variable(PARTICIPATION_OBJECTS), typeProvider)
							expressions += participationObjectVars.values
							val participationClassToObject = participationObjectVars.participationClassToNullableObject
							val operatorContext = new AttributeMappingOperatorContext(typeProvider,
								[variable(INTERMEDIATE)], participationClassToObject)
							participation.relevantReadMappings.forEach [ mapping |
								expressions += mapping.applyReadMapping(operatorContext)
							]
						]
					]
				]
		]
	}
}
