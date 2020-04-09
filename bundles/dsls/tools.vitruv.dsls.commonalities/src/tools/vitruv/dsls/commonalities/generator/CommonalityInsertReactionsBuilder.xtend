package tools.vitruv.dsls.commonalities.generator

import com.google.inject.Inject
import java.util.List
import java.util.Optional
import java.util.function.Consumer
import java.util.function.Function
import org.eclipse.xtext.util.Wrapper
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.generator.ParticipationContextHelper.ParticipationContext
import tools.vitruv.dsls.commonalities.language.Commonality
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.extensions.Containment
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder.PreconditionOrRoutineCallBuilder
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineCallParameter
import tools.vitruv.dsls.reactions.builder.TypeProvider

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
	 * <p>
	 * This does not include singleton classes or any of their containers.
	 */
	def private getManagedClasses(ParticipationContext participationContext) {
		if (participationContext.forReferenceMapping) {
			// Note: Cannot contain singleton classes because singletons
			// require a Resource root container.
			return participationContext.participationClasses.filter [
				!participationContext.isRootContainerClass(it)
			]
		} else {
			return participationContext.participationClasses.filter[!isInSingletonRoot]
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

		// If the participation declares a singleton, ensure that it exists:
		val singletonClass = participation.singletonClass
		if (singletonClass !== null) {
			reaction.call(singletonClass.createSingletonRoutine(segment))
		}

		// Instantiate the corresponding participation (if it doesn't exist yet):
		var List<RoutineCallParameter> parameters
		if (participationContext.isForReferenceMapping) {
			parameters = #[new RoutineCallParameter[newValue], new RoutineCallParameter[affectedEObject]]
		} else {
			parameters = #[new RoutineCallParameter[newValue]]
		}
		return reaction.call(participationContext.createParticipationRoutine(segment), parameters)
	}

	def private createParticipationRoutine(ParticipationContext participationContext,
		FluentReactionsSegmentBuilder segment) {
		val participation = participationContext.participation
		val commonality = participation.containingCommonality
		val managedClasses = participationContext.managedClasses.toList

		return create.routine('''createParticipation_«participation.name»«participationContext.reactionNameSuffix»''')
			.input [
				model(commonality.changeClass, INTERMEDIATE)
				if (participationContext.isForReferenceMapping) {
					val referencingCommonality = participationContext.referencingCommonality
					model(referencingCommonality.changeClass, REFERENCING_INTERMEDIATE)
				}
			]
			.match [
				// Singletons and their containers are expected to already exist:
				val singletonClass = participation.singletonClass
				if (singletonClass !== null) {
					// We don't retrieve any of the singleton's containers, because only the singleton object itself
					// can contain other, non-root participation objects.
					val singletonEClass = singletonClass.changeClass
					vall(singletonClass.correspondingVariableName).retrieveAsserted(singletonEClass)
						.correspondingTo[getEClass(singletonEClass)]
					// TODO Allow limiting the scope of the 'singleton' (eg. have one participation in one commonality
					// use a different 'singleton' instance than a participation in a different commonality)? This
					// could be implemented by using different correspondence tags.
				}

				managedClasses.forEach [ participationClass|
					requireAbsenceOf(participationClass.changeClass).correspondingTo(INTERMEDIATE)
						.taggedWith(participationClass.getCorrespondenceTag(commonality))
				]

				if (participationContext.forReferenceMapping) {
					val referencingCommonality = participationContext.referencingCommonality
					val rootContainerClass = participationContext.rootContainerClass
					vall(PARTICIPATION_CONTEXT_ROOT).retrieveAsserted(rootContainerClass.changeClass)
						.correspondingTo(REFERENCING_INTERMEDIATE)
						.taggedWith(rootContainerClass.getCorrespondenceTag(referencingCommonality))
				}
			].action [
				// Create and initialize the (non-singleton) participation objects:
				val classes = managedClasses
				val containments = participationContext.allContainments.filter[!contained.isInSingletonRoot]
				val isRootContext = !participationContext.forReferenceMapping
				val Function<ParticipationClass, String> variableNameFunction = [
					participationContext.getVariableName(it)
				]
				val Consumer<ActionStatementBuilder> correspondenceSetup = [
					// Setup correspondences:
					managedClasses.forEach [ participationClass |
						addCorrespondence(commonality, participationClass)
					]
				]
				createParticipationObjects(segment, classes, containments, isRootContext, variableNameFunction,
					correspondenceSetup)
			]
	}

	def private createSingletonRoutine(ParticipationClass singletonMarkerClass,
		FluentReactionsSegmentBuilder segment) {
		val participation = singletonMarkerClass.participation
		val singletonMarkerEClass = singletonMarkerClass.changeClass
		return create.routine('''createSingleton_«participation.name»_«singletonMarkerClass.name»''')
			.match [
				requireAbsenceOf(singletonMarkerEClass).correspondingTo [
					getEClass(singletonMarkerEClass)
				]
			].action [
				// Instantiates the singleton object and all of its containers:
				val classes = participation.singletonRootClasses
				val containments = participation.containments.filter[contained.isInSingletonRoot]
				val isRootContext = true
				val Function<ParticipationClass, String> variableNameFunction = [correspondingVariableName]
				val Consumer<ActionStatementBuilder> correspondenceSetup = [
					// Add singleton correspondence:
					// Note: We don't add correspondences for the containers of the singleton object, since we do not
					// require those currently. Also note that these container objects may not necessarily be
					// singletons themselves (i.e. there can be multiple resources even though the singleton object is
					// contained in one).
					addCorrespondenceBetween(singletonMarkerClass.correspondingVariableName).and [
						getEClass(singletonMarkerEClass)
					]
				]
				createParticipationObjects(segment, classes, containments, isRootContext, variableNameFunction,
					correspondenceSetup)
			]
	}

	def private createParticipationObjects(extension ActionStatementBuilder it, FluentReactionsSegmentBuilder segment,
		Iterable<ParticipationClass> classes, Iterable<Containment> containments, boolean rootContext,
		Function<ParticipationClass, String> variableNameFunction,
		Consumer<ActionStatementBuilder> correspondenceSetup) {
		// Create and initialize the participation objects:
		classes.forEach [ participationClass |
			createParticipationObject(participationClass)
		]

		// Setup correspondences:
		correspondenceSetup.accept(it)

		// Establish containment relationships:
		execute [
			setupContainments(containments, variableNameFunction)
		]

		// Each participating commonality instance is implicitly contained
		// inside the root of its intermediate model:
		if (rootContext) {
			// Note: If we are in a root context, all participation classes are
			// expected to come from the same participation.
			val participation = classes.head.participation
			if (participation.isCommonalityParticipation) {
				insertCommonalityParticipationClasses(participation, segment)
			}
		}

		// Any initialization that needs to happen after all objects were created:
		executePostInitializers(classes)
	}

	def private insertCommonalityParticipationClasses(extension ActionStatementBuilder it,
		Participation participation, FluentReactionsSegmentBuilder segment) {
		// assert participation.isCommonalityParticipation
		for (participationClass : participation.rootContainerClasses) {
			val participatingCommonality = participationClass.participatingCommonality // assert: not null
			call(insertIntermediateRoutineBuilderProvider.getFor(segment).getRoutine(participatingCommonality),
				new RoutineCallParameter(participationClass.correspondingVariableName))
		}
	}

	def private createParticipationObject(extension ActionStatementBuilder actionBuilder,
		ParticipationClass participationClass) {
		val corresponding = participationClass.correspondingVariableName
		vall(corresponding).create(participationClass.changeClass) => [
			val initializers = participationClass.initializers
			if (!initializers.empty) {
				andInitialize [ typeProvider |
					initializers.toBlockExpression(typeProvider)
				]
			}
		]
	}

	def private addCorrespondence(extension ActionStatementBuilder actionBuilder, Commonality commonality,
		ParticipationClass participationClass) {
		val corresponding = participationClass.correspondingVariableName
		addCorrespondenceBetween(INTERMEDIATE).and(corresponding)
			.taggedWith(participationClass.getCorrespondenceTag(commonality))
	}

	def setupContainments(extension TypeProvider typeProvider, Iterable<Containment> containments,
		Function<ParticipationClass, String> variableNameFunction) {
		return XbaseFactory.eINSTANCE.createXBlockExpression => [
			containments.forEach [ extension containment |
				val containerVar = variable(variableNameFunction.apply(container))
				val containedVar = variable(variableNameFunction.apply(contained))
				val containmentReference = containment.EReference
				if (containmentReference.many) {
					expressions += typeProvider.addToListFeatureValue(containerVar, containmentReference, containedVar)
				} else {
					expressions += typeProvider.setFeatureValue(containerVar, containmentReference, containedVar)
				}
			]
		]
	}

	def executePostInitializers(extension ActionStatementBuilder actionBuilder,
		Iterable<ParticipationClass> participationClasses) {
		participationClasses.forEach [ participationClass |
			val postInitializers = participationClass.postInitializers
			if (!postInitializers.empty) {
				update(participationClass.correspondingVariableName, [ typeProvider |
					postInitializers.toBlockExpression(typeProvider)
				])
			}
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
