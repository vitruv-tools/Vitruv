package tools.vitruv.dsls.commonalities.generator.reactions.matching

import com.google.inject.Inject
import java.util.HashMap
import java.util.Map
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.generator.helper.ContainmentHelper
import tools.vitruv.dsls.commonalities.generator.reactions.helper.ReactionsGenerationHelper
import tools.vitruv.dsls.commonalities.generator.reactions.intermediatemodel.CreateIntermediateRoutineBuilder
import tools.vitruv.dsls.commonalities.generator.reactions.intermediatemodel.InsertIntermediateRoutineBuilder
import tools.vitruv.dsls.commonalities.generator.reactions.intermediatemodel.InsertReferencedIntermediateRoutineBuilder
import tools.vitruv.dsls.commonalities.generator.reactions.util.ReactionsHelper.RoutineCallContext
import tools.vitruv.dsls.commonalities.generator.reactions.util.ReactionsSegmentScopedProvider
import tools.vitruv.dsls.commonalities.participation.OperatorContainment
import tools.vitruv.dsls.commonalities.participation.ParticipationContext
import tools.vitruv.dsls.commonalities.participation.ParticipationContextHelper
import tools.vitruv.dsls.commonalities.participation.ReferenceContainment
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder.PreconditionOrRoutineCallBuilder
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder.RoutineCallBuilder
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineCallParameter
import tools.vitruv.extensions.dslruntime.commonalities.BooleanResult
import tools.vitruv.extensions.dslruntime.commonalities.matching.ParticipationMatcher

import static com.google.common.base.Preconditions.*
import static tools.vitruv.framework.util.XtendAssertHelper.*

import static extension tools.vitruv.dsls.commonalities.generator.reactions.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.ReactionsHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.XbaseHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

/**
 * Generates the reactions and routines that match participations in given
 * contexts. These reactions are responsible for detecting if a participation
 * exists, according to the specified structural and non-structural conditions,
 * and then instantiate the corresponding Commonality.
 * <p>
 * The contexts are those specified by participations themselves or contexts
 * for participations that are referenced in commonality reference mappings.
 * See also {@link ParticipationContextHelper} and {@link ParticipationMatcher}
 * regarding the different contexts in which a participation may exist and
 * therefore need to be matched.
 * <p>
 * One reactions segment may contain the matching reactions and routines for
 * multiple participation contexts that involve the same participation (for
 * example when a commonality defines multiple references to the same other
 * commonality). To not generate common routines multiple times, we keep track
 * of the already generated shared routines for that segment. This requires
 * that there only exists one instance of this class per reactions segment.
 * However, we also expect that one reactions segment contains the matching
 * reactions and routines for at most one participation's own context.
 * <p>
 * Rationale for taking the containment context into account:</br>
 * The objects specified by participation classes may have different roles
 * depending on the context in which they exist. For instance, a Java class may
 * represent a PCM component, PCM system or PCM datatype, depending on the Java
 * package in which it resides. Even within a single Commonality, a metaclass
 * may occur multiple times and therefore require aliasing of the respective
 * participation classes that represent the metaclass in those different roles.
 * <p>
 * Instead of reacting to individual object creations and then having to ask
 * the user up-front about the role in which the newly created object is going
 * to be used, we defer the participation instantiation until all the
 * structural and non-structural conditions specified by the participation are
 * fulfilled.
 * <p>
 * The structural conditions are derived from the participation's containment
 * relations and conditions (<code>'in'</code>) and commonality reference
 * mappings. These form one or multiple trees of containment relationships
 * between the participation's objects and the context's root objects. The
 * non-structural conditions are all other conditions. Those may for example
 * represent requirements on the attributes of the involved objects.
 * <p>
 * Since every object inside a model is at some point either contained within
 * another object (its container) or a resource, the matching of a
 * participation always involves at least one containment relationship (even if
 * it specifies only a single participation class). It is therefore sufficient
 * to only react to inserts and removes in order to detect when a
 * participation's structural pattern has been established or decomposed again.
 * It is not required to react to individual object creations and deletions.
 * <p>
 * Matching a participation consists of the following two steps:
 * <ol>
 * <li>On every structural change (insert into a containment reference) that
 * may involve an object of the participation that we try to match: Check if we
 * find a set of objects that matches the participations structural conditions.
 * See {@link ParticipationMatcher} for the details about the actual matching
 * procedure. The result of this are candidate matches of model objects
 * according to the participation's containment context.
 * <p>
 * TODO: Currently we only supports participations with a single Resource root
 * class. A workaround for participations with multiple Resource roots could be
 * to split the commonality into separate child commonalities and one parent
 * commonality that uses those as participations.
 * <li>For every found candidate match, we check if it also fulfills the
 * non-structural (i.e. attribute) conditions. The first found candidate match
 * that fulfills also fulfills those is used to establish the participation.
 * </ol>
 * <p>
 * TODO: One consequence of our contextual matching is that we need to deal
 * with cases in which objects get moved from one container to another (Eg. a
 * Java package gets moved from one parent package to another). Currently we
 * will destroy the participation, including the corresponding Commonality
 * instance and any other corresponding participation instances, when the
 * original containment relation is removed. And we then re-establish the
 * participation and rebuild the commonality afterwards during the insertion
 * into the new container. This is prone to information loss in the commonality
 * instance and any corresponding participations.</br>
 * One solution to this could be to defer the actual deletion of the
 * commonality instance and match and re-attach it again during a later
 * insertion. But this hasn't been implemented yet.
 * <p>
 * A similar consequence is that any model change that may break any of the
 * structural or non-structural conditions will lead to the deletion of the
 * commonality instance and the deletion of all corresponding participations.
 * This is prone to errors in a user's model editing leading to unexpected
 * deletions of parts in other models. It is therefore currently recommended to
 * limit any checked conditions to those that are absolutely necessary for the
 * participation's existence.</br>
 * One solution to this could be to prompt the user before performing any
 * deletions of other participations.
 * <p>
 * Other limitations / Possible TODOs:
 * <ul>
 * <li>We might not properly support commonalities that define multiple
 * participations for the same domain currently.
 * <li>Matching needs to happen for various contexts (own context and external
 * reference mappings) and on various occasions (containment reference changes
 * and attribute changes). Currently we generate very similar matching
 * reactions and routines for each of these contexts and occasions, which leads
 * to a lot of duplication in the generated reactions code. Ideally one could
 * generate reactions and routines for the common aspects only once (i.e.
 * anything affecting the non-root portion of the matching, as well as the
 * commonality creation and setup once a participation has been matched) and
 * then invoke those in the various contexts.
 * </ul>
 */
class ParticipationMatchingReactionsBuilder extends ReactionsGenerationHelper {

	static val Logger logger = Logger.getLogger(ParticipationMatchingReactionsBuilder)

	static class Provider extends ReactionsSegmentScopedProvider<ParticipationMatchingReactionsBuilder> {
		protected override createFor(FluentReactionsSegmentBuilder segment) {
			return new ParticipationMatchingReactionsBuilder(segment).injectMembers
		}
	}

	@Inject extension ContainmentHelper containmentHelper
	@Inject extension MatchParticipationRoutineBuilder.Provider matchParticipationRoutineBuilderProvider
	@Inject extension CreateIntermediateRoutineBuilder.Provider createIntermediateRoutineBuilderProvider
	@Inject extension InsertIntermediateRoutineBuilder.Provider insertIntermediateRoutineBuilderProvider
	@Inject extension InsertReferencedIntermediateRoutineBuilder.Provider insertReferencedIntermediateRoutineBuilderProvider
	@Inject extension AttributeReferenceMatchingReactionsBuilder.Provider attributeReferenceMatchingReactionsBuilderProvider
	@Inject extension ParticipationConditionMatchingReactionsBuilder.Provider participationConditionMatchingReactionsBuilderProvider

	val FluentReactionsSegmentBuilder segment

	val Map<ParticipationContext, FluentRoutineBuilder> matchManyParticipationsRoutines = new HashMap
	val Map<ParticipationContext, FluentRoutineBuilder> matchSubParticipationsRoutines = new HashMap

	private new(FluentReactionsSegmentBuilder segment) {
		checkNotNull(segment, "segment is null")
		this.segment = segment
	}

	// Dummy constructor for Guice
	package new() {
		this.segment = null
		throw new IllegalStateException("Use the Provider to get instances of this class!")
	}

	/**
	 * Generates the reactions and routines for matching participations in the
	 * given context.
	 */
	package def void generateMatchingReactions(ParticipationContext participationContext) {
		logger.debug(participationContext.logMessage)
		participationContext.generateRoutines()
		participationContext.generateReactions()
	}

	private def String getLogMessage(ParticipationContext participationContext) {
		if (participationContext.forReferenceMapping) {
			val participation = participationContext.participation
			val reference = participationContext.declaringReference
			val commonality = participationContext.referencingCommonality
			return '''Commonality «commonality»: Generating matching reactions for participation '«participation
				»' and reference '«reference.name»'.'''
		} else {
			val participation = participationContext.participation
			val commonality = participation.containingCommonality
			return '''Commonality «commonality»: Generating matching reactions for participation '«participation»'.'''
		}
	}

	private def generateRoutines(ParticipationContext participationContext) {
		val participation = participationContext.participation
		val commonality = participation.containingCommonality

		segment += segment.getMatchParticipationRoutine(participationContext)
		segment += segment.getCreateIntermediateRoutine(participationContext)

		if (participationContext.isRootContext) {
			segment += segment.getInsertIntermediateRoutine(commonality.concept)
		}

		if (participationContext.forReferenceMapping) {
			val reference = participationContext.declaringReference
			segment += segment.getInsertReferencedIntermediateRoutine(reference)
			segment += participationContext.matchManyParticipationsRoutine
		}
	}

	private def generateReactions(ParticipationContext participationContext) {
		// Reactions which match the participation according to its context:
		if (participationContext.isForAttributeReferenceMapping) {
			segment.generateAttributeReferenceMatchingReactions(participationContext)
		} else {
			participationContext.generateContainmentReferenceMatchingReactions()
		}

		// Reactions for attribute changes which might affect the checked participation conditions:
		segment.generateParticipationConditionReactions(participationContext)
	}

	private def generateContainmentReferenceMatchingReactions(ParticipationContext participationContext) {
		assertTrue(!participationContext.isForAttributeReferenceMapping)
		// Note: For different participation contexts involving the same participation, we generate multiple
		// reactions with the same trigger. The difference between them is which matching routine they invoke.
		participationContext.containments.forEach [ extension contextContainment |
			val containment = containment
			if (containment instanceof ReferenceContainment) {
				segment += participationContext.reactionForParticipationClassInsert(containment)
				segment += participationContext.reactionForParticipationClassRemove(containment)
			} else if (containment instanceof OperatorContainment) {
				val operatorMapping = containment.mapping
				if (operatorMapping.operator.isAttributeReference) {
					throw new IllegalStateException('''Not expecting attribute reference containments for «
						»non-attribute-reference participation context''')
				} else {
					throw new UnsupportedOperationException('''Operator reference mappings for non-attribute «
						»references are not supported yet''')
				}
			}
		]

		// Note: We do not need to react to the deletion of participation
		// objects, because we also receive events for the removal from their
		// previous container.
	}

	/**
	 * When one of the containment relationships between potential
	 * participation objects is established, check if we can match the
	 * participation in the given context.
	 */
	private def FluentReactionBuilder reactionForParticipationClassInsert(ParticipationContext participationContext,
		ReferenceContainment containment) {
		val participation = participationContext.participation
		val containerClass = containment.container
		val containedClass = containment.contained
		var RoutineCallBuilder reaction
		if (containerClass.isForResource) {
			reaction = create.reaction('''«participation.name»_«containedClass.name»_insertedAtRoot«
				participationContext.reactionNameSuffix»''')
				.afterElementInsertedAsRoot(containedClass.changeClass)
		} else {
			val containmentEReference = containment.EReference
			reaction = create.reaction('''«participation.name»_«containedClass.name»_insertedAt_«containerClass.name»_«
				containmentEReference.name»«participationContext.reactionNameSuffix»''')
				.afterElement(containedClass.changeClass).insertedIn(containerClass.changeClass, containmentEReference)
		}
		return reaction.callMatchingRoutine(participationContext, new RoutineCallParameter[newValue], false)
	}

	private def callMatchingRoutine(RoutineCallBuilder reaction, ParticipationContext participationContext,
		RoutineCallParameter startObject, boolean followAttributeReferences) {
		return reaction.call(segment.getMatchParticipationRoutine(participationContext), startObject,
			new RoutineCallParameter[booleanLiteral(followAttributeReferences)],
			new RoutineCallParameter[findDeclaredType(BooleanResult).noArgsConstructorCall])
	}

	/**
	 * When one of the the containment relationships between potential
	 * participation objects is removed, delete the corresponding commonality
	 * instance (if there is one).
	 */
	private def reactionForParticipationClassRemove(ParticipationContext participationContext,
		ReferenceContainment containment) {
		val participation = participationContext.participation
		val commonality = participation.containingCommonality
		val containerClass = containment.container
		val containedClass = containment.contained
		var PreconditionOrRoutineCallBuilder reaction
		if (containerClass.isForResource) {
			reaction = create.reaction('''«participation.name»_«containedClass.name»_removedFromRoot«
				participationContext.reactionNameSuffix»''')
				.afterElementRemovedAsRoot(containedClass.changeClass)
		} else {
			val containmentEReference = containment.EReference
			reaction = create.reaction('''«participation.name»_«containedClass.name»_removedFrom_«
				containerClass.name»_«containmentEReference.name»«participationContext.reactionNameSuffix»''')
				.afterElement(containedClass.changeClass)
				.removedFrom(containerClass.changeClass, containmentEReference)
		}
		return reaction.call [
			match [
				vall(INTERMEDIATE).retrieve(commonality.changeClass).correspondingTo.oldValue
			]
			action [
				delete(INTERMEDIATE)
				// Note: The deletion of the intermediate will also remove it
				// from any parent intermediate container (if there is one).
			]
		]
	}

	/**
	 * Invokes the matching routine as often as new participation matches are
	 * found.
	 * <p>
	 * This only really makes sense if the matching is invoked for the root
	 * object of a reference mapping participation context.
	 */
	private def getMatchManyParticipationsRoutine(ParticipationContext participationContext) {
		return matchManyParticipationsRoutines.computeIfAbsent(participationContext) [
			val participation = participationContext.participation
			val extension routineCallContext = new RoutineCallContext
			create.routine('''matchManyParticipations_«participation.name»«reactionNameSuffix»''')
				.input [
					model(EcorePackage.eINSTANCE.EObject, REFERENCE_ROOT)
				].action [
					execute [ extension typeProvider |
						XbaseFactory.eINSTANCE.createXBlockExpression => [
							// Result variable (initially 'false'):
							val resultVar = XbaseFactory.eINSTANCE.createXVariableDeclaration => [
								type = jvmTypeReferenceBuilder.typeRef(BooleanResult)
								name = FOUND_MATCH_RESULT
								right = typeProvider.findDeclaredType(BooleanResult).imported.noArgsConstructorCall()
							]
							expressions += resultVar

							// Repeatedly invoke the matching routine until no more matches are found:
							expressions += XbaseFactory.eINSTANCE.createXDoWhileExpression => [
								predicate = resultVar.featureCall.memberFeatureCall => [
									feature = typeProvider.findDeclaredType(BooleanResult).findMethod('getValue')
									explicitOperationCall = true
								]
								body = XbaseFactory.eINSTANCE.createXBlockExpression => [
									// Reset the result variable:
									expressions += resultVar.featureCall.memberFeatureCall => [
										feature = typeProvider.findDeclaredType(BooleanResult).findMethod('setValue')
										memberCallArguments += booleanLiteral(false)
										explicitOperationCall = true
									]

									// Call the matching routine:
									// Note: This follows attribute references.
									expressions += routineCallContext.createRoutineCall(typeProvider,
										segment.getMatchParticipationRoutine(participationContext),
										variable(REFERENCE_ROOT), booleanLiteral(true), resultVar.featureCall)
								]
							]
						]
					].setCallerContext
				].setCaller
		]
	}

	/**
	 * Generates the routines for matching sub-participations for the given
	 * commonality reference mapping.
	 * <p>
	 * Returns the routine that needs to be called on commonality insert in
	 * order to invoke the matching.
	 */
	def getMatchSubParticipationsRoutine(ParticipationContext participationContext) {
		return matchSubParticipationsRoutines.computeIfAbsent(participationContext) [
			assertTrue(participationContext.isForReferenceMapping)
			val referencingCommonality = participationContext.referencingCommonality

			// Generate the required routines:
			participationContext.generateRoutines()

			return create.routine('''matchSubParticipations_«participationContext.reactionNameSuffix»''')
				.input [
					model(referencingCommonality.changeClass, INTERMEDIATE)
				]
				.match [
					// Passing one of the reference root classes suffices for the matching procedure:
					val referenceRootClass = participationContext.referenceRootClasses.head.participationClass
					vall(REFERENCE_ROOT).retrieve(referenceRootClass.changeClass)
						.correspondingTo(INTERMEDIATE)
						.taggedWith(referenceRootClass.correspondenceTag)
				]
				.action [
					// TODO: Avoid generating the same matching routines twice. These routines already exists inside
					// P -> C segment. Import the segment and call the matching routine there.
					if (participationContext.isForAttributeReferenceMapping) {
						call(segment.getMatchAttributeReferenceElementsRoutine(participationContext),
							new RoutineCallParameter[variable(REFERENCE_ROOT)])
					} else {
						call(participationContext.matchManyParticipationsRoutine,
							new RoutineCallParameter[variable(REFERENCE_ROOT)])
					}
				]
		]
	}
}
