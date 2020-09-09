package tools.vitruv.dsls.commonalities.generator.reactions.intermediatemodel

import com.google.inject.Inject
import java.util.HashMap
import java.util.List
import java.util.Map
import java.util.Optional
import java.util.function.Consumer
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.generator.helper.ContainmentHelper
import tools.vitruv.dsls.commonalities.generator.reactions.ReactionsSubGenerator
import tools.vitruv.dsls.commonalities.generator.reactions.attribute.ApplyCommonalityAttributesRoutineBuilder
import tools.vitruv.dsls.commonalities.generator.reactions.matching.AttributeReferenceMatchingReactionsBuilder
import tools.vitruv.dsls.commonalities.generator.reactions.matching.ParticipationMatchingReactionsBuilder
import tools.vitruv.dsls.commonalities.generator.reactions.participation.ParticipationObjectInitializationHelper
import tools.vitruv.dsls.commonalities.generator.reactions.reference.ReferenceMappingOperatorHelper
import tools.vitruv.dsls.commonalities.generator.reactions.reference.ReferenceMappingOperatorHelper.ReferenceMappingOperatorContext
import tools.vitruv.dsls.commonalities.generator.reactions.resource.InsertResourceBridgeRoutineBuilder
import tools.vitruv.dsls.commonalities.generator.reactions.resource.ResourceBridgeHelper
import tools.vitruv.dsls.commonalities.generator.util.guice.InjectingFactoryBase
import tools.vitruv.dsls.commonalities.language.Commonality
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.participation.OperatorContainment
import tools.vitruv.dsls.commonalities.participation.ParticipationContext
import tools.vitruv.dsls.commonalities.participation.ParticipationContext.ContextClass
import tools.vitruv.dsls.commonalities.participation.ParticipationContext.ContextContainment
import tools.vitruv.dsls.commonalities.participation.ReferenceContainment
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder.PreconditionOrRoutineCallBuilder
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder.RoutineCallBuilder
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineCallParameter
import tools.vitruv.dsls.reactions.builder.TypeProvider

import static com.google.common.base.Preconditions.*
import static tools.vitruv.framework.util.XtendAssertHelper.*

import static extension tools.vitruv.dsls.commonalities.generator.reactions.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.EmfAccessExpressions.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.ReactionsHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*
import static extension tools.vitruv.dsls.commonalities.participation.ParticipationContextHelper.*

class CommonalityInsertReactionsBuilder extends ReactionsSubGenerator {

	static class Factory extends InjectingFactoryBase {
		def createFor(Participation targetParticipation) {
			return new CommonalityInsertReactionsBuilder(targetParticipation).injectMembers
		}
	}

	@Inject extension ResourceBridgeHelper resourceBridgeHelper
	@Inject extension ContainmentHelper containmentHelper
	@Inject extension ParticipationObjectInitializationHelper participationObjectInitializationHelper
	@Inject extension ReferenceMappingOperatorHelper referenceMappingOperatorHelper
	@Inject extension IntermediateContainmentReactionsHelper intermediateContainmentReactionsHelper
	@Inject extension InsertResourceBridgeRoutineBuilder.Provider insertResourceBridgeRoutineBuilderProvider
	@Inject ParticipationMatchingReactionsBuilder.Provider participationMatchingReactionsBuilderProvider
	@Inject extension InsertIntermediateRoutineBuilder.Provider insertIntermediateRoutineBuilderProvider
	@Inject extension ApplyCommonalityAttributesRoutineBuilder.Provider applyCommonalityAttributesRoutineBuilderProvider
	@Inject extension AttributeReferenceMatchingReactionsBuilder.Provider attributeReferenceMatchingReactionsBuilderProvider

	val Commonality commonality
	val Participation targetParticipation

	// Assumption: We use this reactions builder only for a single reactions segment.
	// Note: The participations used as keys may be both local and external participations.
	val Map<Participation, Optional<FluentRoutineBuilder>> matchSubParticipationsRoutines = new HashMap

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

	def void generateReactions(FluentReactionsSegmentBuilder segment) {
		// If the participation defines an own participation context, we instantiate it once the commonality instance
		// has been created and inserted into the intermediate model root:
		val participationContext = targetParticipation.participationContext
		participationContext.ifPresent [
			segment += reactionForCommonalityInsert(segment)
			segment += reactionForCommonalityRemove
		]

		// For every commonality reference, if an instance of the referenced commonality is inserted, we instantiate
		// the corresponding referenced participations according to the commonality reference's mappings:
		// TODO We do not take the 'write' flag of reference mappings into account currently. The semantics and use
		// cases for this flag are not clear currently. Remove it?
		commonality.references.flatMap[referenceParticipationContexts].filter [
			it.participation.domainName == targetParticipation.domainName
		].forEach [
			segment += reactionForCommonalityInsert(segment)
			segment += reactionForCommonalityRemove
			reactionForCommonalityCreate(segment).ifPresent [segment += it]
		]
	}

	private def reactionForCommonalityInsert(ParticipationContext participationContext,
		FluentReactionsSegmentBuilder segment) {
		val participation = participationContext.participation
		val commonality = participation.containingCommonality

		// Note: The intermediate might have been moved again in the meantime (due to attribute reference matching
		// during intermediate creation), so we check the reaction's trigger condition again before execution.
		var RoutineCallBuilder reaction
		if (participationContext.forReferenceMapping) {
			val reference = participationContext.declaringReference
			reaction = create.reaction('''«commonality.reactionName»_insertedAt_«reference.shortReactionName»«
				participationContext.reactionNameSuffix»''')
				.afterElement(commonality.changeClass).insertedIn(reference.correspondingEReference)
				.with [
					isIntermediateContainerMatching(newValue, affectedEObject, reference)
				]
		} else {
			reaction = create.reaction('''«commonality.reactionName»_insertedAtRoot«
				participationContext.reactionNameSuffix»''')
				.afterElementInsertedAsRoot(commonality.changeClass)
				.with [
					isIntermediateContainedAtRoot(newValue)
				]
		}

		// Match existing sub-participations if the participation already exists:
		val matchSubParticipationsRoutine = participationContext.getMatchSubParticipationsRoutine(segment)
		if (matchSubParticipationsRoutine.present) {
			reaction.call(matchSubParticipationsRoutine.get, new RoutineCallParameter[newValue])
		}

		// If the participation context is for a singleton root, ensure that the singleton root exists:
		if (participationContext.isForSingletonRoot) {
			reaction.call(participationContext.createSingletonRoutine(segment), new RoutineCallParameter[newValue])
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

	private def createParticipationRoutine(ParticipationContext participationContext,
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
				if (participationContext.isForSingletonRoot) {
					// Singletons and their containers are expected to already exist.
					// Note: We don't retrieve any of the singleton's containers, because only the singleton object
					// itself can contain other, non-root participation objects.
					val singletonClass = participation.singletonClass
					val singletonEClass = singletonClass.changeClass
					vall(singletonClass.correspondingVariableName).retrieveAsserted(singletonEClass)
						.correspondingTo[getEClass(singletonEClass)]
					// TODO Allow limiting the scope of the 'singleton' (eg. have one participation in one commonality
					// use a different 'singleton' instance than a participation in a different commonality)? This
					// could be implemented by using different correspondence tags.
				}

				managedClasses.forEach [ contextClass |
					assertTrue(!contextClass.isExternal)
					val participationClass = contextClass.participationClass
					requireAbsenceOf(participationClass.changeClass).correspondingTo(INTERMEDIATE)
						.taggedWith(participationClass.correspondenceTag)
				]

				if (participationContext.forReferenceMapping) {
					// Get all external root classes:
					participationContext.referenceRootClasses.forEach [ contextClass |
						assertTrue(contextClass.isExternal)
						val externalParticipationClass = contextClass.participationClass
						vall(contextClass.correspondingVariableName)
							.retrieveAsserted(externalParticipationClass.changeClass)
							.correspondingTo(REFERENCING_INTERMEDIATE)
							.taggedWith(externalParticipationClass.correspondenceTag)
					]
				}
			].action [
				// Create and initialize the (non-singleton) participation objects:
				val classes = managedClasses
				val containments = participationContext.managedContainments
				val Consumer<ActionStatementBuilder> correspondenceSetup = [
					// Correspondences between participation objects and intermediate:
					managedClasses.forEach [ contextClass |
						assertTrue(!contextClass.isExternal)
						addIntermediateCorrespondence(contextClass.participationClass)
					]
				]
				val applyAttributes = true
				createParticipationObjects(segment, participationContext, classes, containments, applyAttributes,
					correspondenceSetup)
			]
	}

	private def createSingletonRoutine(ParticipationContext participationContext,
		FluentReactionsSegmentBuilder segment) {
		assertTrue(participationContext.isForSingletonRoot)
		val participation = participationContext.participation
		val commonality = participation.containingCommonality
		val singletonClass = participation.singletonClass
		val singletonEClass = singletonClass.changeClass
		return create.routine('''createSingleton_«participation.name»_«singletonClass.name»''')
			.input [
				model(commonality.changeClass, INTERMEDIATE)
			].match [
				requireAbsenceOf(singletonEClass).correspondingTo [
					getEClass(singletonEClass)
				]
			].action [
				// Instantiates the singleton object and all of its containers:
				val classes = participationContext.rootClasses // Singleton root classes
				val containments = participationContext.rootContainments
				val Consumer<ActionStatementBuilder> correspondenceSetup = [
					// The ResourceBridge requires a correspondence with an Intermediate for some of its tasks. For
					// this purpose we add a correspondence with the first intermediate for which the singleton gets
					// created for:
					val resourceClass = participationContext.resourceClass
					assertTrue(resourceClass !== null)
					assertTrue(!resourceClass.isExternal)
					addIntermediateCorrespondence(resourceClass.participationClass)

					// Add singleton correspondence:
					// Note: We don't add correspondences for the containers of the singleton object (except for the
					// ResourceBridge), since we do not require those currently. Also note that these container objects
					// may not necessarily be singletons themselves (eg. there can be multiple resources other than the
					// one the singleton object is contained in).
					addCorrespondenceBetween(singletonClass.correspondingVariableName).and [
						getEClass(singletonEClass)
					]
				]
				// We don't need to apply intermediate attributes to the singleton objects, because singleton objects
				// should not be used inside mappings:
				val applyAttributes = false
				createParticipationObjects(segment, participationContext, classes, containments, applyAttributes,
					correspondenceSetup)
			]
	}

	private def createParticipationObjects(extension ActionStatementBuilder it, FluentReactionsSegmentBuilder segment,
		ParticipationContext participationContext, Iterable<ContextClass> classes,
		Iterable<ContextContainment<?>> containments, boolean applyAttributes,
		Consumer<ActionStatementBuilder> correspondenceSetup) {
		val participation = participationContext.participation

		// Create and initialize the participation objects:
		classes.forEach [ contextClass |
			createParticipationObject(contextClass.participationClass)
		]

		// Setup correspondences:
		correspondenceSetup.accept(it)
		// We always also setup correspondences for resource bridges and their contained objects:
		setupResourceBridgeCorrespondences(containments)

		// Any initialization that needs to happen after all objects were created:
		executePostInitializers(participationContext, classes)

		// Apply attribute mappings:
		// This is required so that attribute reference operators are able to
		// access the attributes when establishing the containments.
		if (applyAttributes) {
			call(segment.getApplyAttributesRoutine(participation), new RoutineCallParameter(INTERMEDIATE))
		}

		// Any ResourceBridge is implicitly contained inside the intermediate model:
		classes.filter[participationClass.isForResource].forEach [
			val resourceClass = participationClass
			call(segment.getInsertResourceBridgeRoutine(resourceClass),
				new RoutineCallParameter(resourceClass.correspondingVariableName),
				new RoutineCallParameter(INTERMEDIATE))
		]

		// Each participating commonality instance is implicitly contained
		// inside the root of its intermediate model:
		if (participationContext.rootContext && participation.isCommonalityParticipation) {
			insertCommonalityParticipationClasses(participation, segment)
		}

		// Establish containment relationships:
		execute [
			setupContainments(containments)
		]

		// Enable persistence again for resource bridges:
		val resourceClasses = classes.map[participationClass].filter[isForResource].toList
		if (!resourceClasses.empty) {
			execute [ extension typeProvider |
				XbaseFactory.eINSTANCE.createXBlockExpression => [
					resourceClasses.forEach [ resourceClass |
						val resourceBridge = variable(resourceClass.correspondingVariableName)
						expressions += setIsPersistenceEnabled(typeProvider, resourceBridge, true)
					]
				]
			]
		}
	}

	private def insertCommonalityParticipationClasses(extension ActionStatementBuilder it,
		Participation participation, FluentReactionsSegmentBuilder segment) {
		assertTrue(participation.isCommonalityParticipation)
		for (participationClass : participation.rootContainerClasses) {
			val participatingCommonality = participationClass.participatingCommonality
			assertTrue(participatingCommonality !== null)
			call(segment.getInsertIntermediateRoutine(participatingCommonality.concept),
				new RoutineCallParameter(participationClass.correspondingVariableName))
		}
	}

	private def createParticipationObject(extension ActionStatementBuilder actionBuilder,
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

	private def addIntermediateCorrespondence(extension ActionStatementBuilder actionBuilder,
		ParticipationClass participationClass) {
		addCorrespondenceBetween(INTERMEDIATE).and(participationClass.correspondingVariableName)
			.taggedWith(participationClass.correspondenceTag)
	}

	private def setupResourceBridgeCorrespondences(extension ActionStatementBuilder actionBuilder,
		Iterable<ContextContainment<?>> containments) {
		containments.filter[container.participationClass.isForResource].forEach [
			assertTrue(!container.isExternal && !contained.isExternal)
			val resourceClass = container.participationClass
			val containedClass = contained.participationClass
			actionBuilder.addResourceBridgeCorrespondence(resourceClass, containedClass)
		]
	}

	private def addResourceBridgeCorrespondence(extension ActionStatementBuilder actionBuilder,
		ParticipationClass resourceClass, ParticipationClass containedClass) {
		val resourceBridge = resourceClass.correspondingVariableName
		val containedObject = containedClass.correspondingVariableName
		addCorrespondenceBetween(resourceBridge).and(containedObject)
			.taggedWith(resourceClass.getResourceCorrespondenceTag(containedClass))
	}

	private def setupContainments(extension TypeProvider typeProvider, Iterable<ContextContainment<?>> containments) {
		return XbaseFactory.eINSTANCE.createXBlockExpression => [
			containments.forEach [ extension contextContainment |
				val containerVar = variable(container.correspondingVariableName)
				val containedVar = variable(contained.correspondingVariableName)
				val containment = containment
				if (containment instanceof ReferenceContainment) {
					val containmentReference = containment.EReference
					if (containmentReference.many) {
						expressions += typeProvider.addListFeatureValue(containerVar, containmentReference, containedVar)
					} else {
						expressions += typeProvider.setFeatureValue(containerVar, containmentReference, containedVar)
					}
				} else if (containment instanceof OperatorContainment) {
					val operatorMapping = containment.mapping
					val operatorContext = new ReferenceMappingOperatorContext(typeProvider)
					expressions += operatorMapping.callInsert(containerVar, containedVar, operatorContext)
				}
			]
		]
	}

	private def executePostInitializers(extension ActionStatementBuilder actionBuilder,
		ParticipationContext participationContext, Iterable<ContextClass> contextClasses) {
		contextClasses.forEach [ contextClass |
			val postInitializers = participationContext.getPostInitializers(contextClass)
			if (!postInitializers.empty) {
				val participationClass = contextClass.participationClass
				update(participationClass.correspondingVariableName, [ typeProvider |
					postInitializers.toBlockExpression(typeProvider)
				])
			}
		]
	}

	private def reactionForCommonalityRemove(ParticipationContext participationContext) {
		val participation = participationContext.participation
		val commonality = participation.containingCommonality

		var PreconditionOrRoutineCallBuilder reaction
		if (participationContext.forReferenceMapping) {
			val reference = participationContext.declaringReference
			val referencingCommonality = reference.containingCommonality
			reaction = create.reaction('''«commonality.concept.name»_«commonality.name»_removedFrom«
				referencingCommonality.name»_«reference.name»«participationContext.reactionNameSuffix»''')
				.afterElement(commonality.changeClass).removedFrom(reference.correspondingEReference)
		} else {
			reaction = create.reaction('''«commonality.concept.name»_«commonality.name»_removedFromRoot«
				participationContext.reactionNameSuffix»''')
				.afterElementRemovedAsRoot(commonality.changeClass)
		}
		return reaction.call [
			match [
				// If the intermediate has been moved during creation (due to attribute reference matching), the
				// corresponding participations might not have been created yet. We therefore do not assert the
				// existence of the participation objects.
				participationContext.managedClasses.forEach [ contextClass |
					assertTrue(!contextClass.isExternal)
					val participationClass = contextClass.participationClass
					vall(participationClass.correspondingVariableName)
						.retrieve(participationClass.changeClass)
						.correspondingTo.oldValue
						.taggedWith(participationClass.correspondenceTag)
				]
			].action [
				participationContext.managedClasses.forEach [ contextClass |
					val participationClass = contextClass.participationClass
					delete(participationClass.correspondingVariableName)
				]
			]
		]
	}

	/**
	 * Matches sub-participations for an existing parent participation.
	 * <p>
	 * Optional: Empty if there are no sub-participations to match.
	 */
	// TODO: It would be sufficient to generate this routine once for every participation and then import it when
	// called for external reference mappings.
	private def getMatchSubParticipationsRoutine(ParticipationContext participationContext,
		FluentReactionsSegmentBuilder segment) {
		val participation = participationContext.participation
		return matchSubParticipationsRoutines.computeIfAbsent(participation) [
			val extension matchingReactionsBuilder = participationMatchingReactionsBuilderProvider.getFor(segment)
			val commonality = participation.containingCommonality
			// TODO We do not take the 'read' flag of reference mappings into account currently. The semantics and use
			// cases for this flag are not clear currently. Remove it?
			val matchingRoutines = commonality.references.flatMap[referenceParticipationContexts].filter [
				it.participation.domainName == participation.domainName
			].map[matchSubParticipationsRoutine].toList
			if (matchingRoutines.empty) {
				return Optional.empty
			}

			// We use the ParticipationContext to generate unique routine names, since otherwise local participation
			// names may clash with external participation names.
			return Optional.of(create.routine('''matchSubParticipations_«participation»«
				participationContext.reactionNameSuffix»''')
				.input[
					model(commonality.changeClass, INTERMEDIATE)
				]
				.action [
					matchingRoutines.forEach [ matchingRoutine |
						call(matchingRoutine, new RoutineCallParameter(INTERMEDIATE))
					]
				])
		]
	}

	/**
	 * Optional: Empty if there is no reaction to be created.
	 */
	private def reactionForCommonalityCreate(ParticipationContext participationContext,
		FluentReactionsSegmentBuilder segment) {
		if (!participationContext.isForAttributeReferenceMapping) {
			return Optional.empty
		}

		val referencedCommonality = participationContext.referencedCommonality
		return Optional.of(create.reaction('''«referencedCommonality.reactionName»Create«
			participationContext.reactionNameSuffix»''')
			.afterElement(referencedCommonality.changeClass).created
			.call(segment.getMatchAttributeReferenceContainerForIntermediateRoutine(participationContext),
				new RoutineCallParameter[affectedEObject])
		)
	}
}
