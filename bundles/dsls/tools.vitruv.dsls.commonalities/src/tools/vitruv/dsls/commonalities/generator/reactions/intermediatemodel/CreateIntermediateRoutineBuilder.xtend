package tools.vitruv.dsls.commonalities.generator.reactions.intermediatemodel

import com.google.inject.Inject
import java.util.HashMap
import java.util.Map
import tools.vitruv.dsls.commonalities.generator.reactions.attribute.ApplyParticipationAttributesRoutineBuilder
import tools.vitruv.dsls.commonalities.generator.reactions.helper.ReactionsGenerationHelper
import tools.vitruv.dsls.commonalities.generator.reactions.participation.ParticipationObjectsHelper
import tools.vitruv.dsls.commonalities.generator.reactions.resource.InsertResourceBridgeRoutineBuilder
import tools.vitruv.dsls.commonalities.generator.reactions.resource.SetupResourceBridgeRoutineBuilder
import tools.vitruv.dsls.commonalities.generator.reactions.util.ReactionsSegmentScopedProvider
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.participation.ParticipationContext
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineCallParameter
import tools.vitruv.extensions.dslruntime.commonalities.matching.ParticipationObjects

import static com.google.common.base.Preconditions.*
import static tools.vitruv.framework.util.XtendAssertHelper.*

import static extension tools.vitruv.dsls.commonalities.generator.reactions.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.intermediatemodel.IntermediateModelHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.EmfAccessExpressions.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

class CreateIntermediateRoutineBuilder extends ReactionsGenerationHelper {

	static class Provider extends ReactionsSegmentScopedProvider<CreateIntermediateRoutineBuilder> {

		protected override createFor(FluentReactionsSegmentBuilder segment) {
			return new CreateIntermediateRoutineBuilder(segment).injectMembers
		}

		def getCreateIntermediateRoutine(FluentReactionsSegmentBuilder segment,
			ParticipationContext participationContext) {
			return getFor(segment).getCreateIntermediateRoutine(participationContext)
		}
	}

	@Inject extension ParticipationObjectsHelper participationObjectsHelper
	@Inject extension SetupResourceBridgeRoutineBuilder.Provider setupResourceBridgeRoutineBuilderProvider
	@Inject extension InsertResourceBridgeRoutineBuilder.Provider insertResourceBridgeRoutineBuilderProvider
	@Inject extension InsertIntermediateRoutineBuilder.Provider insertIntermediateRoutineBuilderProvider
	@Inject extension InsertReferencedIntermediateRoutineBuilder.Provider insertReferencedIntermediateRoutineBuilderProvider
	// TODO It would be possible to generate this routine only once per participation and then import it when called
	// for external reference mapping participation contexts.
	@Inject extension ApplyParticipationAttributesRoutineBuilder.Provider applyParticipationAttributesRoutineBuilderProvider

	val FluentReactionsSegmentBuilder segment
	val Map<ParticipationContext, FluentRoutineBuilder> createIntermediateRoutines = new HashMap

	private new(FluentReactionsSegmentBuilder segment) {
		checkNotNull(segment, "segment is null")
		this.segment = segment
	}

	// Dummy constructor for Guice
	package new() {
		this.segment = null
		throw new IllegalStateException("Use the Provider to get instances of this class!")
	}

	def getCreateIntermediateRoutine(ParticipationContext participationContext) {
		val participation = participationContext.participation
		val commonality = participation.declaringCommonality
		return createIntermediateRoutines.computeIfAbsent(participationContext) [
			create.routine('''createIntermediate_«commonality.name»«reactionNameSuffix»''')
				.input [
					plain(ParticipationObjects, PARTICIPATION_OBJECTS)
				]
				.action [ extension it |
					vall(INTERMEDIATE).create(commonality.changeClass).andInitialize [
						claimIntermediateId(variable(INTERMEDIATE))
					]

					if (participationContext.isRootContext) {
						if (participation.hasSingletonClass) {
							// Setup the singleton if it hasn't been setup yet:
							val singletonClass = participation.singletonClass
							call(singletonClass.setupSingletonRoutine, new RoutineCallParameter(INTERMEDIATE),
								new RoutineCallParameter [ extension typeProvider |
									singletonClass.getParticipationObject(variable(PARTICIPATION_OBJECTS), typeProvider)
								], new RoutineCallParameter(PARTICIPATION_OBJECTS))
						} else if (participation.hasResourceClass) {
							// Note: We cannot implicitly assume here that the participation has a resource class,
							// because this is not the case for commonality participations.
							// If we have a Resource as root container, setup and insert the corresponding
							// ResourceBridge:
							setupAndInsertResourceBridge(participation)
						}
					}

					// Add correspondences with participation objects:
					participationContext.managedClasses.forEach [ contextClass |
						assertTrue(!contextClass.isExternal)
						val participationClass = contextClass.participationClass
						addCorrespondenceBetween(INTERMEDIATE).and [ extension typeProvider |
							// TODO Ideally don't use a block expression here (results in more compact reactions code)
							// But: This is not yet supported by the fluent reactions builder.
							// Possible alternative: Call a routine which adds the correspondence between the
							// passed objects
							contextClass.getParticipationObject(variable(PARTICIPATION_OBJECTS), typeProvider)
						].taggedWith(participationClass.correspondenceTag)
					]

					// For commonality references: Insert the new intermediate into the referencing intermediate
					if (participationContext.forReferenceMapping) {
						val reference = participationContext.declaringReference
						// We pass one of the reference root objects to find the corresponding referencing intermediate:
						val referenceRootClass = participationContext.referenceRootClasses.head
						call(segment.getInsertReferencedIntermediateRoutine(reference),
							new RoutineCallParameter(INTERMEDIATE),
							new RoutineCallParameter [ extension typeProvider |
								referenceRootClass.getParticipationObject(variable(PARTICIPATION_OBJECTS),
									typeProvider)
							]
						)
					} else {
						// Insert new intermediate into the intermediate model root:
						call(segment.getInsertIntermediateRoutine(commonality.concept),
							new RoutineCallParameter(INTERMEDIATE))
					}

					// Apply all attribute mappings:
					// TODO trigger this on commonality creation instead, similar to sub-participation matching?
					// This would have the benefit that external participation matching would not need to generate and
					// invoke this routine, since it is handled by the referenced participation itself already.
					call(segment.getApplyParticipationAttributesRoutine(participation),
						new RoutineCallParameter(INTERMEDIATE), new RoutineCallParameter(PARTICIPATION_OBJECTS))
				]
		]
	}

	/**
	 * Sets up a matched singleton root if that has not happened yet.
	 */
	private def getSetupSingletonRoutine(ParticipationClass singletonClass) {
		val participation = singletonClass.participation
		val commonality = participation.declaringCommonality
		val singletonEClass = singletonClass.changeClass
		return create.routine('''setupSingleton_«participation.name»_«singletonClass.name»''')
			.input [
				model(commonality.changeClass, INTERMEDIATE)
				model(singletonEClass, SINGLETON)
				plain(ParticipationObjects, PARTICIPATION_OBJECTS)
			]
			.match [
				requireAbsenceOf(singletonEClass).correspondingTo [
					getEClass(singletonEClass)
				]
			].action [
				// Add singleton correspondence:
				addCorrespondenceBetween(SINGLETON).and [
					getEClass(singletonEClass)
				]

				// Setup and then insert the ResourceBridge into the intermediate model:
				setupAndInsertResourceBridge(participation)

				// Add a correspondence for the ResourceBridge:
				// This correspondence is commonality and participation specific and is only set for the first
				// participation the singleton gets matched for. The ResourceBridge uses this for some of its tasks.
				addCorrespondenceBetween [ extension typeProvider |
					val resourceClass = participation.resourceClass
					resourceClass.getParticipationObject(variable(PARTICIPATION_OBJECTS), typeProvider)
				].and(INTERMEDIATE)
			]
	}

	/**
	 * Performs remaining setup and then inserts the ResourceBridge returned by
	 * the ParticipationMatcher into the intermediate model.
	 */
	private def setupAndInsertResourceBridge(extension ActionStatementBuilder it, Participation participation) {
		val resourceClass = participation.resourceClass
		assertTrue(resourceClass !== null)
		call(segment.getSetupResourceBridgeRoutine(resourceClass), new RoutineCallParameter [ extension typeProvider |
			resourceClass.getParticipationObject(variable(PARTICIPATION_OBJECTS), typeProvider)
		])

		// Add correspondences between the resource bridge and its contained objects:
		participation.resourceContainments.map[contained].forEach [ containedClass |
			addCorrespondenceBetween [ extension typeProvider |
				resourceClass.getParticipationObject(variable(PARTICIPATION_OBJECTS), typeProvider)
			].and [ extension typeProvider |
				containedClass.getParticipationObject(variable(PARTICIPATION_OBJECTS), typeProvider)
			].taggedWith(resourceClass.getResourceCorrespondenceTag(containedClass))
		]

		call(segment.getInsertResourceBridgeRoutine(resourceClass), new RoutineCallParameter [ extension typeProvider |
			resourceClass.getParticipationObject(variable(PARTICIPATION_OBJECTS), typeProvider)
		], new RoutineCallParameter(INTERMEDIATE))
	}
}
