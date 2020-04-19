package tools.vitruv.dsls.commonalities.generator

import com.google.inject.Inject
import java.util.List
import java.util.Optional
import java.util.function.Consumer
import java.util.function.Function
import org.eclipse.xtext.util.Wrapper
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.language.Commonality
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.extensions.ParticipationContext
import tools.vitruv.dsls.commonalities.language.extensions.ParticipationContext.ContextClass
import tools.vitruv.dsls.commonalities.language.extensions.ParticipationContext.ContextContainment
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
import static extension tools.vitruv.dsls.commonalities.language.extensions.ParticipationContextHelper.*

package class CommonalityInsertReactionsBuilder extends ReactionsSubGenerator {

	static class Factory extends InjectingFactoryBase {
		def createFor(Participation targetParticipation) {
			return new CommonalityInsertReactionsBuilder(targetParticipation).injectMembers
		}
	}

	@Inject extension ContainmentHelper containmentHelper
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
		].map[referenceParticipationContext].forEach [
			segment += reactionForCommonalityInsert(segment)
			segment += reactionForCommonalityRemove
		]

		// Matching of existing sub-participations:
		reactionForCommonalityCreateSetup(segment).ifPresent [segment += it]
	}

	def private getVariableName(extension ParticipationContext participationContext,
		ContextClass contextClass) {
		if (forReferenceMapping && isRootContainerClass(contextClass)) {
			return PARTICIPATION_CONTEXT_ROOT
		} else {
			return contextClass.participationClass.correspondingVariableName
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

		// If the participation context is for a singleton root, ensure that the singleton root exists:
		if (participationContext.isForSingletonRoot) {
			reaction.call(participationContext.createSingletonRoutine(segment))
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
					val participationClass = contextClass.participationClass
					requireAbsenceOf(participationClass.changeClass).correspondingTo(INTERMEDIATE)
						.taggedWith(participationClass.getCorrespondenceTag(commonality))
				]

				if (participationContext.forReferenceMapping) {
					val referencingCommonality = participationContext.referencingCommonality
					val rootContainerClass = participationContext.rootContainerClass.participationClass
					vall(PARTICIPATION_CONTEXT_ROOT).retrieveAsserted(rootContainerClass.changeClass)
						.correspondingTo(REFERENCING_INTERMEDIATE)
						.taggedWith(rootContainerClass.getCorrespondenceTag(referencingCommonality))
				}
			].action [
				// Create and initialize the (non-singleton) participation objects:
				val classes = managedClasses
				val containments = participationContext.managedContainments
				val isRootContext = participationContext.isRootContext
				val Function<ContextClass, String> variableNameFunction = [
					participationContext.getVariableName(it)
				]
				val Consumer<ActionStatementBuilder> correspondenceSetup = [
					// Setup correspondences:
					managedClasses.forEach [ contextClass |
						addCorrespondence(commonality, contextClass.participationClass)
					]
				]
				createParticipationObjects(segment, participation, classes, containments, isRootContext,
					variableNameFunction, correspondenceSetup)
			]
	}

	def private createSingletonRoutine(ParticipationContext participationContext,
		FluentReactionsSegmentBuilder segment) {
		// assert: participationContext.isForSingletonRoot
		val participation = participationContext.participation
		val singletonClass = participation.singletonClass
		val singletonEClass = singletonClass.changeClass
		return create.routine('''createSingleton_«participation.name»_«singletonClass.name»''')
			.match [
				requireAbsenceOf(singletonEClass).correspondingTo [
					getEClass(singletonEClass)
				]
			].action [
				// Instantiates the singleton object and all of its containers:
				val classes = participationContext.rootClasses // singleton root classes
				val containments = participationContext.rootContainments
				val isRootContext = true
				val Function<ContextClass, String> variableNameFunction = [
					participationClass.correspondingVariableName
				]
				val Consumer<ActionStatementBuilder> correspondenceSetup = [
					// Add singleton correspondence:
					// Note: We don't add correspondences for the containers of the singleton object, since we do not
					// require those currently. Also note that these container objects may not necessarily be
					// singletons themselves (i.e. there can be multiple resources even though the singleton object is
					// contained in one).
					addCorrespondenceBetween(singletonClass.correspondingVariableName).and [
						getEClass(singletonEClass)
					]
				]
				createParticipationObjects(segment, participation, classes, containments, isRootContext,
					variableNameFunction, correspondenceSetup)
			]
	}

	def private createParticipationObjects(extension ActionStatementBuilder it, FluentReactionsSegmentBuilder segment,
		Participation participation, Iterable<ContextClass> classes, Iterable<ContextContainment> containments,
		boolean rootContext, Function<ContextClass, String> variableNameFunction,
		Consumer<ActionStatementBuilder> correspondenceSetup) {
		// Create and initialize the participation objects:
		classes.forEach [ contextClass |
			createParticipationObject(contextClass.participationClass)
		]

		// Setup correspondences:
		correspondenceSetup.accept(it)

		// Establish containment relationships:
		execute [
			setupContainments(containments, variableNameFunction)
		]

		// Each participating commonality instance is implicitly contained
		// inside the root of its intermediate model:
		if (rootContext && participation.isCommonalityParticipation) {
			insertCommonalityParticipationClasses(participation, segment)
		}

		// Any initialization that needs to happen after all objects were created:
		executePostInitializers(classes.map[participationClass])
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

	def setupContainments(extension TypeProvider typeProvider, Iterable<ContextContainment> containments,
		Function<ContextClass, String> variableNameFunction) {
		return XbaseFactory.eINSTANCE.createXBlockExpression => [
			containments.forEach [ extension contextContainment |
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
				participationContext.managedClasses.forEach [ contextClass |
					val participationClass = contextClass.participationClass
					vall(participationClass.correspondingVariableName)
						.retrieveAsserted(participationClass.changeClass)
						.correspondingTo.oldValue
						.taggedWith(participationClass.getCorrespondenceTag(commonality))
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
