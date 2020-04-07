package tools.vitruv.dsls.commonalities.generator

import com.google.inject.Inject
import java.util.Optional
import org.eclipse.xtext.util.Wrapper
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.generator.ParticipationContextHelper.ParticipationContext
import tools.vitruv.dsls.commonalities.language.Commonality
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder.PreconditionOrRoutineCallBuilder
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineCallParameter

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.dsls.commonalities.generator.EmfAccessExpressions.*
import static extension tools.vitruv.dsls.commonalities.generator.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.generator.ReactionsHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class CommonalityInsertReactionsBuilder extends ReactionsSubGenerator {

	static class Factory extends InjectingFactoryBase {
		def createFor(Participation targetParticipation) {
			return new CommonalityInsertReactionsBuilder(targetParticipation).injectMembers
		}
	}

	@Inject extension ContainmentHelper containmentHelper
	@Inject extension ParticipationContextHelper participationContextHelper
	@Inject extension ParticipationObjectInitializationHelper participationObjectInitializationHelper
	@Inject ParticipationMatchingReactionsBuilder.Provider participationMatchingReactionsBuilderProvider
	@Inject InsertIntermediateRoutineBuilder.Provider insertIntermediateRoutineBuilderProvider

	val Commonality commonality
	val Participation targetParticipation

	private new(Participation targetParticipation) {
		checkNotNull(targetParticipation, "targetParticipation is null")
		this.commonality = targetParticipation.containingCommonality
		this.targetParticipation = targetParticipation
	}

	// Dummy constructor for Guice
	package new() {
		this.commonality = null
		this.targetParticipation = null
		throw new IllegalStateException("Use the Factory to create instances of this class!")
	}

	def package void generateReactions(FluentReactionsSegmentBuilder segment) {
		// If the participation defines an own participation context, we instantiate it once the commonality instance
		// has been created and inserted into the intermediate model root:
		val participationContext = targetParticipation.participationContext
		participationContext.ifPresent [
			segment += reactionForCommonalityInsert(segment)
			segment += reactionForCommonalityRemove
		]

		// For every commonality reference, if an instance of the referenced commonality is inserted, we instantiate
		// the corresponding referenced participations according to the commonality reference's mappings:
		commonality.references.flatMap[mappings].filter [
			isWrite && it.participation == targetParticipation
		].map[referenceParticipationContext].filter[present].map[get].forEach [
			segment += reactionForCommonalityInsert(segment)
			segment += reactionForCommonalityRemove
		]

		// Matching of existing sub-participations:
		reactionForCommonalityCreateSetup(segment).ifPresent [segment += it]
	}

	/**
	 * Gets all participation classes of the given participation context that
	 * are managed by the corresponding Intermediate.
	 * <p>
	 * For reference participation contexts this does not include the context's
	 * root container (since that is managed by another Intermediate), but for
	 * non-reference participation contexts it does include the root Resource
	 * container class.
	 */
	def private getManagedClasses(ParticipationContext participationContext) {
		if (participationContext.forReferenceMapping) {
			return participationContext.participationClasses.filter [
				!participationContext.isRootContainerClass(it)
			]
		} else {
			return participationContext.participationClasses
		}
	}

	def private getVariableName(extension ParticipationContext participationContext,
		ParticipationClass participationClass) {
		if (forReferenceMapping && isRootContainerClass(participationClass)) {
			return PARTICIPATION_CONTEXT_ROOT
		} else {
			return participationClass.correspondingVariableName
		}
	}

	def private reactionForCommonalityInsert(ParticipationContext participationContext,
		FluentReactionsSegmentBuilder segment) {
		val participation = participationContext.participation
		val commonality = participation.containingCommonality

		var PreconditionOrRoutineCallBuilder reaction
		if (participationContext.forReferenceMapping) {
			val reference = participationContext.referenceMapping.declaringReference
			val referencingCommonality = reference.containingCommonality
			reaction = create.reaction('''«commonality.concept.name»_«commonality.name»_insertedAt_«
				referencingCommonality.name»_«reference.name»«participationContext.reactionNameSuffix»''')
				.afterElement(commonality.changeClass).insertedIn(reference.commonalityEReference)
		} else {
			reaction = create.reaction('''«commonality.concept.name»_«commonality.name»_insertedAtRoot«
				participationContext.reactionNameSuffix»''')
				.afterElementInsertedAsRoot(commonality.changeClass)
		}
		return reaction.call [
			match [
				participationContext.managedClasses.forEach [ participationClass |
					requireAbsenceOf(participationClass.changeClass).correspondingTo.newValue
						.taggedWith(participationClass.getCorrespondenceTag(commonality))
				]
				if (participationContext.forReferenceMapping) {
					val referencingCommonality = participationContext.referencingCommonality
					val rootContainerClass = participationContext.rootContainerClass
					vall(PARTICIPATION_CONTEXT_ROOT).retrieveAsserted(rootContainerClass.changeClass)
						.correspondingTo.affectedEObject
						.taggedWith(rootContainerClass.getCorrespondenceTag(referencingCommonality))
				}
			].action [
				// Create participation objects, initialize them and setup correspondences:
				participationContext.managedClasses.forEach [ participationClass |
					val corresponding = participationClass.correspondingVariableName
					vall(corresponding).create(participationClass.changeClass) => [
						val initializers = participationClass.initializers
						if (!initializers.empty) {
							andInitialize [ typeProvider |
								initializers.toBlockExpression(typeProvider)
							]
						}
					]
					addCorrespondenceBetween.newValue.and(corresponding)
						.taggedWith(participationClass.getCorrespondenceTag(commonality))
				]

				execute [ extension typeProvider |
					XbaseFactory.eINSTANCE.createXBlockExpression => [
						// Establish all containment relationships:
						participationContext.allContainments.forEach [ extension containment |
							val containerVar = variable(participationContext.getVariableName(container))
							val containedVar = variable(participationContext.getVariableName(contained))
							val containmentReference = containment.EReference
							if (containmentReference.many) {
								expressions += typeProvider.addToListFeatureValue(containerVar, containmentReference, containedVar)
							} else {
								expressions += typeProvider.setFeatureValue(containerVar, containmentReference, containedVar)
							}
						]
					]
				]

				// Each participating commonality instance is implicitly contained inside the root of its intermediate
				// model:
				if (!participationContext.forReferenceMapping && participation.isCommonalityParticipation) {
					for (participationClass : participationContext.root.nonRootBoundaryClasses) {
						val participatingCommonality = participationClass.participatingCommonality // assert: not null
						call(insertIntermediateRoutineBuilderProvider.getFor(segment).getRoutine(participatingCommonality),
							new RoutineCallParameter(participationClass.correspondingVariableName))
					}
				}

				// Any initialization that needs to happen after all model objects were created:
				participationContext.managedClasses.forEach [ participationClass |
					val postInitializers = participationClass.postInitializers
					if (!postInitializers.empty) {
						update(participationClass.correspondingVariableName, [ typeProvider |
							postInitializers.toBlockExpression(typeProvider)
						])
					}
				]
			]
		]
	}

	def private reactionForCommonalityRemove(ParticipationContext participationContext) {
		val participation = participationContext.participation
		val commonality = participation.containingCommonality

		var PreconditionOrRoutineCallBuilder reaction
		if (participationContext.forReferenceMapping) {
			val reference = participationContext.referenceMapping.declaringReference
			val referencingCommonality = reference.containingCommonality
			reaction = create.reaction('''«commonality.concept.name»_«commonality.name»_removedFrom«
				referencingCommonality.name»_«reference.name»«participationContext.reactionNameSuffix»''')
				.afterElement(commonality.changeClass).removedFrom(reference.commonalityEReference)
		} else {
			reaction = create.reaction('''«commonality.concept.name»_«commonality.name»_removedFromRoot«
				participationContext.reactionNameSuffix»''')
				.afterElementRemovedAsRoot(commonality.changeClass)
		}
		return reaction.call [
			match [
				participationContext.managedClasses.forEach [ participationClass |
					vall(participationClass.correspondingVariableName)
						.retrieveAsserted(participationClass.changeClass)
						.correspondingTo.oldValue
						.taggedWith(participationClass.getCorrespondenceTag(commonality))
				]
			].action [
				participationContext.managedClasses.forEach [ participationClass |
					delete(participationClass.correspondingVariableName)
				]
			]
		]
	}

	/**
	 * Reaction that matches existing sub-participations if the corresponding
	 * parent participation already exists.
	 * <p>
	 * Optional: Empty if there is no reaction to be created.
	 */
	def private reactionForCommonalityCreateSetup(FluentReactionsSegmentBuilder segment) {
		val extension matchingReactionsBuilder = participationMatchingReactionsBuilderProvider.getFor(segment)
		val relevantReferenceMappings = commonality.references.flatMap[mappings].filter [
			isRead && it.participation == targetParticipation
		]
		val matchingRoutines = relevantReferenceMappings
			.map[matchCommonalityReferenceMappingRoutine]
			.filter[present].map[get]
			.toList
		if (matchingRoutines.empty) {
			return Optional.empty
		}

		val Wrapper<FluentReactionBuilder> reaction = new Wrapper
		create.reaction('''«commonality.concept.name»_«commonality.name»Create_Setup''')
			.afterElement(commonality.changeClass).created => [
				matchingRoutines.forEach [ matchingRoutine |
					reaction.set(call(matchingRoutine, new RoutineCallParameter[affectedEObject]))
				]
			]
		return Optional.of(reaction.get)
	}
}
