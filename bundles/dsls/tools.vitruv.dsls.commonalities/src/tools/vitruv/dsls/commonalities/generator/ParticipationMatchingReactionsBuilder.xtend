package tools.vitruv.dsls.commonalities.generator

import com.google.inject.Inject
import java.util.Collections
import java.util.HashMap
import java.util.HashSet
import java.util.Map
import java.util.Set
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.xtext.common.types.TypesFactory
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.generator.ReactionsHelper.RoutineCallContext
import tools.vitruv.dsls.commonalities.language.CommonalityReference
import tools.vitruv.dsls.commonalities.language.CommonalityReferenceMapping
import tools.vitruv.dsls.commonalities.language.OperatorReferenceMapping
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationAttributeOperand
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.ReferenceMappingOperand
import tools.vitruv.dsls.commonalities.language.ReferencedParticipationAttributeOperand
import tools.vitruv.dsls.commonalities.language.extensions.OperatorContainment
import tools.vitruv.dsls.commonalities.language.extensions.ParticipationContext
import tools.vitruv.dsls.commonalities.language.extensions.ParticipationContextHelper
import tools.vitruv.dsls.commonalities.language.extensions.ReferenceContainment
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder.PreconditionOrRoutineCallBuilder
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder.RoutineCallBuilder
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineCallParameter
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.BooleanResult
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.IntermediateModelBasePackage
import tools.vitruv.extensions.dslruntime.commonalities.matching.ContainmentTree
import tools.vitruv.extensions.dslruntime.commonalities.matching.ParticipationMatcher
import tools.vitruv.extensions.dslruntime.commonalities.matching.ParticipationObjects

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.dsls.commonalities.generator.EmfAccessExpressions.*
import static extension tools.vitruv.dsls.commonalities.generator.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.generator.ReactionsHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.XbaseHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.ParticipationContextHelper.*

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
 * The structural conditions are derived from the participation's
 * <code>'in'</code> relations. These form a tree of containment relationships
 * between the participation's objects. The non-structural conditions are all
 * other conditions. Those may for example represent requirements on the
 * attributes of the involved objects.
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
 * procedure. The result of this are candidate trees of model objects according
 * to the participation context's containment hierarchy.
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
package class ParticipationMatchingReactionsBuilder extends ReactionsGenerationHelper {

	private static val Logger logger = Logger.getLogger(ReactionsGenerator) => [level = Level.TRACE]

	@GenerationScoped
	static class Provider extends ReactionsSegmentScopedProvider<ParticipationMatchingReactionsBuilder> {
		protected override createFor(FluentReactionsSegmentBuilder segment) {
			return new ParticipationMatchingReactionsBuilder(segment).injectMembers
		}
	}

	@Inject extension ContainmentHelper containmentHelper
	@Inject extension ResourceBridgeHelper resourceBridgeHelper
	@Inject extension ParticipationObjectsHelper participationObjectsHelper
	@Inject extension ReferenceMappingOperatorHelper referenceMappingOperatorHelper
	@Inject extension AttributeChangeReactionsHelper attributeChangeReactionsHelper
	@Inject extension IntermediateContainmentReactionsHelper intermediateContainmentReactionsHelper
	@Inject InsertIntermediateRoutineBuilder.Provider insertIntermediateRoutineBuilderProvider
	@Inject ApplyParticipationAttributesRoutineBuilder.Factory applyParticipationAttributesRoutineBuilderFactory

	val FluentReactionsSegmentBuilder segment
	var alreadyPerformedCommonSetup = false
	val Set<ParticipationContext> alreadyGeneratedRoutines = new HashSet

	var FluentRoutineBuilder _deleteObjectRoutine = null

	val Map<ParticipationContext, FluentRoutineBuilder> checkAttributeReferenceRoutines = new HashMap
	val Map<ParticipationContext, FluentRoutineBuilder> checkAttributeReferenceElementsRemovedRoutines = new HashMap
	val Map<ParticipationContext, FluentRoutineBuilder> checkAttributeReferenceElementRemovedRoutines = new HashMap
	val Map<ParticipationContext, FluentRoutineBuilder> matchAttributeReferenceElementsRoutines = new HashMap
	val Map<ParticipationContext, FluentRoutineBuilder> matchAttributeReferenceContainerRoutines = new HashMap

	val Map<ParticipationContext, FluentRoutineBuilder> matchParticipationRoutines = new HashMap
	val Map<ParticipationContext, FluentRoutineBuilder> createIntermediateRoutines = new HashMap
	val Map<ParticipationClass, FluentRoutineBuilder> setupAndInsertResourceBridgeRoutines = new HashMap
	val Map<CommonalityReference, FluentRoutineBuilder> insertReferencedIntermediateRoutines = new HashMap
	val Map<Participation, FluentRoutineBuilder> applyParticipationAttributesRoutines = new HashMap
	val Map<ParticipationContext, FluentRoutineBuilder> matchManyParticipationsRoutines = new HashMap

	val Map<CommonalityReferenceMapping, FluentRoutineBuilder> matchSubParticipationsRoutines = new HashMap
	val Map<ParticipationContext, FluentRoutineBuilder> matchAttributeReferenceContainerForIntermediateRoutines = new HashMap

	private new(FluentReactionsSegmentBuilder segment) {
		checkNotNull(segment, "segment is null")
		this.segment = segment
	}

	// Dummy constructor for Guice
	package new() {
		this.segment = null
		throw new IllegalStateException("Use the Provider to get instances of this class!")
	}

	def package void generateReactions(ParticipationContext participationContext) {
		if (participationContext.forReferenceMapping) {
			val mapping = participationContext.referenceMapping
			val reference = mapping.declaringReference
			val commonality = participationContext.referencingCommonality
			logger.debug('''Commonality «commonality»: Generating matching reactions for reference «reference.name
				» and mapping of participation «mapping.participation»''')
		} else {
			val participation = participationContext.participation
			val commonality = participation.containingCommonality
			logger.debug('''Commonality «commonality»: Generating matching reactions for participation «
				participation.name»''')
		}

		performCommonSetup()
		participationContext.generateRoutines()

		if (participationContext.isForAttributeReferenceMapping) {
			participationContext.attributeReferenceContainments.forEach [ extension contextContainment |
				val containment = containment
				// assert: containment.operator.isAttributeReference
				segment += participationContext.reactionsForAttributeReferenceChange(containment)
			]
		} else {
			// Note: For different participation contexts involving the same participation, we generate multiple
			// reactions with the same trigger. The difference between them is which matching routine they invoke.
			participationContext.containments.forEach [ extension contextContainment |
				val containment = containment
				if (containment instanceof ReferenceContainment) {
					segment += participationContext.reactionForParticipationClassInsert(containment)
					segment += participationContext.reactionForParticipationClassRemove(containment)
				} else if (containment instanceof OperatorContainment) {
					if (containment.operator.isAttributeReference) {
						throw new IllegalStateException('''Not expecting attribute reference containments for «
							»non-attribute-reference participation context''')
					} else {
						throw new UnsupportedOperationException('''Operator reference mappings for non-attribute «
							»references are not supported yet''')
					}
				}
			]
		}

		// Note: We do not need to react to the deletion of participation
		// objects, because we also receive events for the removal from their
		// previous container.

		// TODO reactions to react to attribute changes that might fulfill the non-structural conditions
		// TODO also need to react to them in order to delete the participation again once a checked
		// condition is no longer fulfilled (conditions that get only enforced are ignored)
	}

	/**
	 * This is run once for the reactions segment when generating the reactions
	 * and routines for the first participation context that gets matched (even
	 * if the segment contains the matching reactions and routines for several
	 * participation contexts).
	 */
	def private performCommonSetup() {
		if (alreadyPerformedCommonSetup) return;
		alreadyPerformedCommonSetup = true
		generateCommonRoutines()
	}

	def private generateCommonRoutines() {
		segment += deleteObjectRoutine
	}

	def private generateRoutines(ParticipationContext participationContext) {
		if (!alreadyGeneratedRoutines.add(participationContext)) {
			return;
		}
		val participation = participationContext.participation
		val commonality = participation.containingCommonality

		segment += participationContext.matchParticipationRoutine
		segment += participationContext.createIntermediateRoutine

		if (participationContext.isRootContext) {
			segment += insertIntermediateRoutineBuilderProvider.getFor(segment).getRoutine(commonality)
		}

		if (participationContext.forReferenceMapping) {
			segment += participationContext.referenceMapping.declaringReference.insertReferencedIntermediateRoutine
			segment += participationContext.matchManyParticipationsRoutine
			if (participationContext.isForAttributeReferenceMapping) {
				segment += participationContext.checkAttributeReferenceRoutine
				segment += participationContext.checkAttributeReferenceElementsRemovedRoutine
				segment += participationContext.checkAttributeReferenceElementRemovedRoutine
				segment += participationContext.matchAttributeReferenceElementsRoutine
				segment += participationContext.matchAttributeReferenceContainerRoutine
			}
		}
	}

	/**
	 * It is currently not possible to invoke reaction actions (such as the
	 * delete action) from within execute blocks. This is for example required
	 * when conditionally deleting an object. As workaround, this routine can
	 * be called which then simply invokes the delete action for the passed
	 * object.
	 */
	def private getDeleteObjectRoutine() {
		if (_deleteObjectRoutine === null) {
			_deleteObjectRoutine = create.routine('''deleteObject''')
				.input [
					model(EcorePackage.Literals.EOBJECT, "object")
				].action [
					delete("object")
				]
		}
		return _deleteObjectRoutine
	}

	/**
	 * When one of the containment relationships between potential
	 * participation objects is established, check if we can match the
	 * participation in the given context.
	 */
	def private FluentReactionBuilder reactionForParticipationClassInsert(ParticipationContext participationContext,
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

	def private callMatchingRoutine(RoutineCallBuilder reaction, ParticipationContext participationContext,
		RoutineCallParameter startObject, boolean followAttributeReferences) {
		return reaction.call(participationContext.matchParticipationRoutine, startObject,
			new RoutineCallParameter[booleanLiteral(followAttributeReferences)],
			new RoutineCallParameter[findDeclaredType(BooleanResult).noArgsConstructorCall])
	}

	/**
	 * When one of the the containment relationships between potential
	 * participation objects is removed, delete the corresponding commonality
	 * instance (if there is one).
	 */
	def private reactionForParticipationClassRemove(ParticipationContext participationContext,
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
				.afterElement(containedClass.changeClass).removedFrom(containerClass.changeClass, containmentEReference)
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

	def private reactionsForAttributeReferenceChange(ParticipationContext participationContext,
		OperatorContainment containment) {
		// assert: participationContext.isForAttributeReferenceMapping
		return containment.operands.flatMap [
			participationContext.reactionsForAttributeReferenceChange(containment, it)
		]
	}

	// In reaction to an attribute change inside the referencing participation:
	def private dispatch reactionsForAttributeReferenceChange(ParticipationContext participationContext,
		OperatorContainment containment, ParticipationAttributeOperand operand) {
		val attribute = operand.participationAttribute
		val reactionNameSuffix = participationContext.reactionNameSuffix
		return attribute.getAttributeChangeReactions(reactionNameSuffix) [ changeType , it |
			// Check if any of the previous attribute references got removed:
			call(participationContext.checkAttributeReferenceElementsRemovedRoutine,
				new RoutineCallParameter[affectedEObject])

			// Check if there are new attribute references:
			call(participationContext.matchAttributeReferenceElementsRoutine, new RoutineCallParameter[affectedEObject])
		]
	}

	// In reaction to an attribute change inside the referenced participation:
	def private dispatch reactionsForAttributeReferenceChange(ParticipationContext participationContext,
		OperatorContainment containment, ReferencedParticipationAttributeOperand operand) {
		val attribute = operand.participationAttribute
		val reactionNameSuffix = participationContext.reactionNameSuffix + "_element"
		return attribute.getAttributeChangeReactions(reactionNameSuffix) [ changeType , it |
			// Check if any previous attribute reference is no longer fulfilled:
			call(participationContext.checkAttributeReferenceElementRemovedRoutine,
				new RoutineCallParameter[affectedEObject])

			// Check if there is a new attribute reference container:
			call(participationContext.matchAttributeReferenceContainerRoutine,
				new RoutineCallParameter[affectedEObject])
		]
	}

	def private dispatch reactionsForAttributeReferenceChange(ParticipationContext participationContext,
		OperatorContainment containment, ReferenceMappingOperand operand) {
		return Collections.emptyList // for any other type of operand (eg. literal operands)
	}

	/**
	 * Checks if any attribute references for the given container participation object are no longer fulfilled.
	 */
	private def getCheckAttributeReferenceElementsRemovedRoutine(ParticipationContext participationContext) {
		return checkAttributeReferenceElementsRemovedRoutines.computeIfAbsent(participationContext) [
			// assert: participationContext.isForAttributeReferenceMapping
			val commonalityReference = participationContext.referenceMapping.declaringReference
			val referencingCommonality = participationContext.referencingCommonality
			val referencedCommonality = participationContext.referencedCommonality

			val extension routineCallContext = new RoutineCallContext
			create.routine('''checkAttributeReferenceElementsRemoved«participationContext.reactionNameSuffix»''')
				.input [
					// Some object of the referencing participation (not necessarily the attribute reference root):
					model(EcorePackage.Literals.EOBJECT, PARTICIPATION_OBJECT)
				].match [
					vall(REFERENCING_INTERMEDIATE).retrieve(referencingCommonality.changeClass)
						.correspondingTo(PARTICIPATION_OBJECT)
				].action [
					execute [extension typeProvider |
						val extension jvmTypeReferenceBuilder = jvmTypeReferenceBuilder
						XbaseFactory.eINSTANCE.createXBlockExpression => [
							if (commonalityReference.isMultiValued) {
								// Get all referenced intermediates:
								val referencedIntermediatesVar = XbaseFactory.eINSTANCE.createXVariableDeclaration => [
									name = REFERENCED_INTERMEDIATES
									type = typeRef(Iterable, typeRef(referencedCommonality.changeClass.javaClassName))
									writeable = false
									right = getListFeatureValue(typeProvider, variable(REFERENCING_INTERMEDIATE),
										commonalityReference.correspondingEReference)
								]
								expressions += referencedIntermediatesVar

								// For each referenced intermediate, check if the attribute reference still holds:
								val referencedIntermediateVar = TypesFactory.eINSTANCE.createJvmFormalParameter => [
									parameterType = typeRef(referencedCommonality.changeClass.javaClassName)
									name = REFERENCED_INTERMEDIATE
								]
								expressions += XbaseFactory.eINSTANCE.createXForLoopExpression => [
									declaredParam = referencedIntermediateVar
									forExpression = referencedIntermediatesVar.featureCall
									eachExpression = XbaseFactory.eINSTANCE.createXBlockExpression => [
										expressions += routineCallContext.createRoutineCall(typeProvider,
											participationContext.checkAttributeReferenceRoutine,
											variable(REFERENCING_INTERMEDIATE), referencedIntermediateVar.featureCall)
									]
								]
							} else {
								// Get the referenced intermediate:
								val referencedIntermediateVar = XbaseFactory.eINSTANCE.createXVariableDeclaration => [
									name = REFERENCED_INTERMEDIATE
									type = typeRef(referencedCommonality.changeClass.javaClassName)
									writeable = false
									right = getFeatureValue(typeProvider, variable(REFERENCING_INTERMEDIATE),
										commonalityReference.correspondingEReference)
								]
								expressions += referencedIntermediateVar

								// Check if the attribute reference still holds:
								expressions += routineCallContext.createRoutineCall(typeProvider,
									participationContext.checkAttributeReferenceRoutine,
									variable(REFERENCING_INTERMEDIATE), referencedIntermediateVar.featureCall)
							}
						]
					].setCallerContext
				].setCaller
		]
	}

	/**
	 * Checks if any attribute reference for the given contained participation object is no longer fulfilled.
	 */
	private def getCheckAttributeReferenceElementRemovedRoutine(ParticipationContext participationContext) {
		return checkAttributeReferenceElementRemovedRoutines.computeIfAbsent(participationContext) [
			// assert: participationContext.isForAttributeReferenceMapping
			val commonalityReference = participationContext.referenceMapping.declaringReference
			val referencedCommonality = participationContext.referencedCommonality

			val extension routineCallContext = new RoutineCallContext
			create.routine('''checkAttributeReferenceElementRemoved«participationContext.reactionNameSuffix»''')
				.input [
					model(EcorePackage.Literals.EOBJECT, PARTICIPATION_OBJECT)
				].match [
					vall(REFERENCED_INTERMEDIATE).retrieve(referencedCommonality.changeClass)
						.correspondingTo(PARTICIPATION_OBJECT)
				].action [
					execute [extension typeProvider |
						XbaseFactory.eINSTANCE.createXBlockExpression => [
							// Get the container intermediate, if there is one for the specific commonality reference:
							val referencingIntermediateVar = XbaseFactory.eINSTANCE.createXVariableDeclaration => [
								name = REFERENCING_INTERMEDIATE
								writeable = false
								right = getIntermediateContainer(typeProvider, variable(REFERENCED_INTERMEDIATE),
									commonalityReference)
							]
							expressions += referencingIntermediateVar

							// If the container intermediate is not null:
							expressions += XbaseFactory.eINSTANCE.createXIfExpression => [
								^if = referencingIntermediateVar.featureCall.notEqualsNull(typeProvider)
								// Then check if the attribute reference still holds:
								then = routineCallContext.createRoutineCall(typeProvider,
									participationContext.checkAttributeReferenceRoutine,
									referencingIntermediateVar.featureCall, variable(REFERENCED_INTERMEDIATE))
							]
						]
					].setCallerContext
				].setCaller
		]
	}

	// Checks for new attribute references for a given container participation object:
	private def getMatchAttributeReferenceElementsRoutine(ParticipationContext participationContext) {
		return matchAttributeReferenceElementsRoutines.computeIfAbsent(participationContext) [
			// assert: participationContext.isForAttributeReferenceMapping
			val mapping = participationContext.referenceMapping as OperatorReferenceMapping
			val referencingCommonality = participationContext.referencingCommonality
			val referencedCommonality = participationContext.referencedCommonality
			val attributeReferenceRoot = participationContext.attributeReferenceRoot
			val attributeReferenceRootClass = attributeReferenceRoot.participationClass

			val extension routineCallContext = new RoutineCallContext
			create.routine('''matchAttributeReferenceElements«participationContext.reactionNameSuffix»''')
				.input [
					// Some object of the referencing participation (not necessarily the attribute reference root):
					model(EcorePackage.Literals.EOBJECT, PARTICIPATION_OBJECT)
				].match [
					vall(REFERENCING_INTERMEDIATE).retrieve(referencingCommonality.changeClass)
						.correspondingTo(PARTICIPATION_OBJECT)
					vall(REFERENCE_ROOT).retrieveAsserted(attributeReferenceRootClass.changeClass)
						.correspondingTo(REFERENCING_INTERMEDIATE)
						.taggedWith(attributeReferenceRootClass.correspondenceTag)
				].action [
					execute [extension typeProvider |
						val extension jvmTypeReferenceBuilder = jvmTypeReferenceBuilder
						XbaseFactory.eINSTANCE.createXBlockExpression => [
							// Query the operator for all potentially contained intermediates:
							val operator = mapping.operator
							val operands = mapping.operands
							val referencedIntermediatesVar = XbaseFactory.eINSTANCE.createXVariableDeclaration => [
								name = REFERENCED_INTERMEDIATES
								type = typeRef(Iterable, typeRef(referencedCommonality.changeClass.javaClassName))
								writeable = false
								right = operator.callGetPotentiallyContainedIntermediates(operands,
									variable(REFERENCE_ROOT), referencedCommonality.changeClass, typeProvider)
							]
							expressions += referencedIntermediatesVar

							// For each potentially referenced intermediate, check if the attribute reference is fulfilled:
							val referencedIntermediateVar = TypesFactory.eINSTANCE.createJvmFormalParameter => [
								parameterType = typeRef(referencedCommonality.changeClass.javaClassName)
								name = REFERENCED_INTERMEDIATE
							]
							expressions += XbaseFactory.eINSTANCE.createXForLoopExpression => [
								declaredParam = referencedIntermediateVar
								forExpression = referencedIntermediatesVar.featureCall
								eachExpression = XbaseFactory.eINSTANCE.createXBlockExpression => [
									expressions += routineCallContext.createRoutineCall(typeProvider,
										participationContext.checkAttributeReferenceRoutine,
										variable(REFERENCING_INTERMEDIATE), referencedIntermediateVar.featureCall)
								]
							]
						]
					].setCallerContext
				].setCaller
		]
	}

	// Checks for a new attribute reference container for a given potentially contained participation object:
	private def getMatchAttributeReferenceContainerRoutine(ParticipationContext participationContext) {
		return matchAttributeReferenceContainerRoutines.computeIfAbsent(participationContext) [
			// assert: participationContext.isForAttributeReferenceMapping
			val mapping = participationContext.referenceMapping as OperatorReferenceMapping
			val referencingCommonality = participationContext.referencingCommonality
			val referencedCommonality = participationContext.referencedCommonality

			val extension routineCallContext = new RoutineCallContext
			create.routine('''matchAttributeReferenceContainer«participationContext.reactionNameSuffix»''')
				.input [
					// Some object of the potentially referenced participation:
					model(EcorePackage.Literals.EOBJECT, PARTICIPATION_OBJECT)
				].match [
					vall(REFERENCED_INTERMEDIATE).retrieve(referencedCommonality.changeClass)
						.correspondingTo(PARTICIPATION_OBJECT)
				].action [
					execute [extension typeProvider |
						val extension jvmTypeReferenceBuilder = jvmTypeReferenceBuilder
						XbaseFactory.eINSTANCE.createXBlockExpression => [
							// Query the operator for the potential container intermediate:
							val operator = mapping.operator
							val operands = mapping.operands
							val referencingIntermediateVar = XbaseFactory.eINSTANCE.createXVariableDeclaration => [
								name = REFERENCING_INTERMEDIATE
								type = typeRef(referencingCommonality.changeClass.javaClassName)
								writeable = false
								right = operator.callGetPotentialContainerIntermediate(operands,
									variable(PARTICIPATION_OBJECT), referencingCommonality.changeClass, typeProvider)
							]
							expressions += referencingIntermediateVar

							// If the candidate container intermediate is not null:
							expressions += XbaseFactory.eINSTANCE.createXIfExpression => [
								^if = referencingIntermediateVar.featureCall.notEqualsNull(typeProvider)
								// Then check if the attribute reference is fulfilled:
								then = routineCallContext.createRoutineCall(typeProvider,
									participationContext.checkAttributeReferenceRoutine,
									referencingIntermediateVar.featureCall, variable(REFERENCED_INTERMEDIATE))
							]
						]
					].setCallerContext
				].setCaller
		]
	}

	/**
	 * Checks if the participation corresponding to the given 'referenced
	 * intermediate' is, according to the attribute reference operator,
	 * contained by the participation corresponding to the given 'referencing
	 * intermediate'.
	 * <p>
	 * If the attribute reference is fulfilled, but the referenced intermediate
	 * is not actually referenced yet, we insert it into the respective
	 * reference of the referencing intermediate.
	 * <p>
	 * If the attribute reference is not fulfilled, but the referenced
	 * intermediate is currently referenced by the referencing intermediate, we
	 * move it into the intermediate model root.
	 */
	private def getCheckAttributeReferenceRoutine(ParticipationContext participationContext) {
		return checkAttributeReferenceRoutines.computeIfAbsent(participationContext) [
			// assert: participationContext.isForAttributeReferenceMapping
			val commonalityReference = participationContext.referenceMapping.declaringReference
			val referencingCommonality = participationContext.referencingCommonality
			val referencedCommonality = participationContext.referencedCommonality
			val attributeReferenceRoot = participationContext.attributeReferenceRoot
			val attributeReferenceRootClass = attributeReferenceRoot.participationClass

			val extension routineCallContext = new RoutineCallContext
			create.routine('''checkAttributeReference«participationContext.reactionNameSuffix»''')
				.input [
					model(referencingCommonality.changeClass, REFERENCING_INTERMEDIATE)
					model(referencedCommonality.changeClass, REFERENCED_INTERMEDIATE)
				].match [
					vall(REFERENCE_ROOT).retrieveAsserted(attributeReferenceRootClass.changeClass)
						.correspondingTo(REFERENCING_INTERMEDIATE)
						.taggedWith(attributeReferenceRootClass.correspondenceTag)

					// assert: !participationContext.attributeReferenceContainments.empty
					participationContext.attributeReferenceContainments.forEach [ extension contextContainment |
						// assert: !contained.isExternal (no conflicts between variable names expected)
						val containedClass = contained.participationClass
						vall(containedClass.correspondingVariableName)
							.retrieveAsserted(containedClass.changeClass)
							.correspondingTo(REFERENCED_INTERMEDIATE)
							.taggedWith(containedClass.correspondenceTag)
					]
				].action [
					execute [extension typeProvider |
						XbaseFactory.eINSTANCE.createXBlockExpression => [
							// Check each attribute reference containment:
							participationContext.attributeReferenceContainments.forEach [ extension contextContainment |
								join(checkAttributeReferenceContainment(participationContext, containment,
									routineCallContext, typeProvider))
							]

							// If we did not return yet, it means that all attribute reference containments are
							// fulfilled.
							// If the referenced intermediate is not yet contained by the referencing intermediate:
							expressions += XbaseFactory.eINSTANCE.createXIfExpression => [
								^if = negated(isIntermediateContainerMatching(typeProvider,
									variable(REFERENCED_INTERMEDIATE), variable(REFERENCING_INTERMEDIATE),
									commonalityReference), typeProvider)
								// Then insert it into the referencing intermediate:
								val commonalityEReference = commonalityReference.correspondingEReference
								then = insertFeatureValue(typeProvider, variable(REFERENCING_INTERMEDIATE),
									commonalityEReference, variable(REFERENCED_INTERMEDIATE))
							]
						]
					].setCallerContext
				].setCaller
		]
	}

	/**
	 * Checks if the given attribute reference containment is fulfilled.
	 * <p>
	 * If it is not, but the referenced intermediate is currently contained by the referencing intermediate, we move it
	 * into the root of its intermediate model.
	 */
	private def checkAttributeReferenceContainment(ParticipationContext participationContext,
		OperatorContainment containment, extension RoutineCallContext routineCallContext,
		extension TypeProvider typeProvider) {
		val commonalityReference = participationContext.referenceMapping.declaringReference
		val referencedCommonality = participationContext.referencedCommonality

		val operator = containment.operator
		val operands = containment.operands
		val containedClass = containment.contained

		return XbaseFactory.eINSTANCE.createXBlockExpression => [
			// If, according to the attribute reference operator, the reference root object no longer contains the
			// referenced object:
			expressions += XbaseFactory.eINSTANCE.createXIfExpression => [
				^if = negated(operator.callIsContained(operands, variable(REFERENCE_ROOT),
					variable(containedClass.correspondingVariableName), typeProvider), typeProvider)
				then = XbaseFactory.eINSTANCE.createXBlockExpression => [
					// But if the referencing intermediate still contains the referenced intermediate:
					expressions += XbaseFactory.eINSTANCE.createXIfExpression => [
						^if = isIntermediateContainerMatching(typeProvider, variable(REFERENCED_INTERMEDIATE),
							variable(REFERENCING_INTERMEDIATE), commonalityReference)
						// Then move the referenced intermediate to the intermediate model root:
						val insertIntermediateRoutine = insertIntermediateRoutineBuilderProvider.getFor(segment)
							.getRoutine(referencedCommonality)
						then = routineCallContext.createRoutineCall(typeProvider, insertIntermediateRoutine,
							variable(REFERENCED_INTERMEDIATE))
					]
					// In either case: Return from the routine.
					expressions += XbaseFactory.eINSTANCE.createXReturnExpression
				]
			]
		]
	}

	/**
	 * Sets up the {@link ContainmentTree} for the given
	 * {@link ParticipationContext}, invokes the {@link ParticipationMatcher}
	 * and instantiates the corresponding Commonality if a match is found.
	 * <p>
	 * If there are multiple candidates for a structural match, the first found
	 * candidate that also fulfills the non-structural conditions is used to
	 * establish the participation.
	 * <p>
	 * Note: We don't check if there already is a corresponding Intermediate
	 * for the give start object. If the participation context is for a
	 * commonality reference mapping, the start object is the containment
	 * tree's root object specified by that mapping, which already corresponds
	 * to an Intermediate (possibly even the same type of Intermediate that the
	 * participation will correspond to in case we find a match). Otherwise, if
	 * the participation context is not for a commonality reference mapping,
	 * the {@link ParticipationMatcher} will already verify that the
	 * participation objects (including the passed start object) do not already
	 * correspond to an Intermediate.
	 */
	def private getMatchParticipationRoutine(ParticipationContext participationContext) {
		return matchParticipationRoutines.computeIfAbsent(participationContext) [
			val participation = participationContext.participation
			val extension routineCallContext = new RoutineCallContext
			create.routine('''matchParticipation_«participation.name»«reactionNameSuffix»''')
				.input [
					model(EcorePackage.eINSTANCE.EObject, START_OBJECT)
					plain(Boolean, FOLLOW_ATTRIBUTE_REFERENCES)
					plain(BooleanResult, FOUND_MATCH_RESULT)
				].action [
					execute [ extension typeProvider |
						participationContext.matchParticipation(routineCallContext, typeProvider)
					].setCallerContext
				].setCaller
		]
	}

	def private matchParticipation(ParticipationContext participationContext, RoutineCallContext routineCallContext,
		extension TypeProvider typeProvider) {
		val participation = participationContext.participation
		val isRootCommonalityParticipation = (participationContext.isRootContext && participation.isCommonalityParticipation)
		return XbaseFactory.eINSTANCE.createXBlockExpression => [
			// Create new ContainmentTree:
			val containmentTreeBuilder = new ContainmentTreeExpressionBuilder(typeProvider)
			val containmentTreeVar = containmentTreeBuilder.newContainmentTree("containmentTree")
			expressions += containmentTreeVar
	
			// TODO Find relevant EPackages up front and then reuse them for EClass lookup?
			// Same applies to EClasses that are reused by later calls.
	
			// Specify the root Intermediate type in case we try to match the
			// participation in the context of a commonality reference mapping:
			if (participationContext.forReferenceMapping) {
				val rootIntermediateType = participationContext.referencingCommonality.changeClass
				expressions += containmentTreeBuilder.setRootIntermediateType(rootIntermediateType)
			}
	
			// Setup nodes:
			if (isRootCommonalityParticipation) {
				// Add the node for the implicit intermediate root:
				val intermediateRootEClass = participation.participationConcept.intermediateModelRootClass
				expressions += containmentTreeBuilder.addNode(INTERMEDIATE_ROOT, intermediateRootEClass)
			}
			expressions += participationContext.classes.map [ contextClass |
				val participationClass = contextClass.participationClass
				containmentTreeBuilder.addNode(contextClass.name, participationClass.changeClass)
			]
			if (participationContext.isForAttributeReferenceMapping) {
				// Add the node for the attribute reference root:
				val attributeReferenceRoot = participationContext.attributeReferenceRoot
				val attributeReferenceRootClass = attributeReferenceRoot.participationClass
				expressions += containmentTreeBuilder.setAttributeReferenceRootNode(attributeReferenceRoot.name,
					attributeReferenceRootClass.changeClass)
			}
	
			// Setup containment edges:
			if (isRootCommonalityParticipation) {
				// Add edges for the implicit containments of the intermediate root:
				val containerName = INTERMEDIATE_ROOT
				val containmentEReference = IntermediateModelBasePackage.Literals.ROOT__INTERMEDIATES
				expressions += participation.nonRootBoundaryClasses.map [ contained |
					containmentTreeBuilder.addReferenceEdge(containerName, contained.name, containmentEReference)
				]
			}
			expressions += participationContext.containments.map [ extension contextContainment |
				val containment = containment
				if (containment instanceof ReferenceContainment) {
					containmentTreeBuilder.addReferenceEdge(container.name, contained.name, containment.EReference)
				} else if (containment instanceof OperatorContainment) {
					val operator = containment.operator
					val operands = containment.operands
					val operatorInstance = operator.callConstructor(operands, typeProvider)
					if (operator.isAttributeReference) {
						containmentTreeBuilder.addAttributeReferenceEdge(contained.name, operatorInstance)
					} else {
						containmentTreeBuilder.addOperatorEdge(container.name, contained.name, operatorInstance)
					}
				} else {
					throw new IllegalStateException("Unexpected containment type: " + containment.class.name)
				}
			]
	
			// Match participation objects:
			val participationMatcherType = typeProvider.findDeclaredType(ParticipationMatcher).imported
			val matchObjectsMethod = participationMatcherType.findMethod("matchObjects")
			val candidateMatchesVar = XbaseFactory.eINSTANCE.createXVariableDeclaration => [
				name = "candidateMatches"
				type = jvmTypeReferenceBuilder.typeRef(Iterable, jvmTypeReferenceBuilder.typeRef(ParticipationObjects))
				right = participationMatcherType.memberFeatureCall(matchObjectsMethod) => [
					staticWithDeclaringType = true
					memberCallArguments += expressions(
						containmentTreeVar.featureCall,
						variable(START_OBJECT),
						variable(FOLLOW_ATTRIBUTE_REFERENCES),
						correspondenceModel
					)
				]
			]
			expressions += candidateMatchesVar
	
			// For each candidate match:
			val participationObjectsVar = TypesFactory.eINSTANCE.createJvmFormalParameter => [
				parameterType = jvmTypeReferenceBuilder.typeRef(ParticipationObjects)
				name = PARTICIPATION_OBJECTS
			]
			expressions += XbaseFactory.eINSTANCE.createXForLoopExpression => [
				declaredParam = participationObjectsVar
				forExpression = candidateMatchesVar.featureCall
				eachExpression = XbaseFactory.eINSTANCE.createXBlockExpression => [
					// Check non-structural participation conditions:
					expressions += XbaseFactory.eINSTANCE.createXIfExpression => [
						^if = participationContext.checkNonStructuralConditions(participationObjectsVar.featureCall,
							typeProvider)
						then = XbaseFactory.eINSTANCE.createXBlockExpression => [
							// Set result flag:
							expressions += variable(FOUND_MATCH_RESULT).memberFeatureCall => [
								feature = typeProvider.findDeclaredType(BooleanResult).findMethod('setValue')
								memberCallArguments += booleanLiteral(true)
								explicitOperationCall = true
							]

							// Create intermediate:
							expressions += routineCallContext.createRoutineCall(typeProvider,
								participationContext.createIntermediateRoutine, participationObjectsVar.featureCall)

							// Abort checking other candidate matches:
							expressions += XbaseFactory.eINSTANCE.createXReturnExpression
						]
					]
				]
			]
		]
	}

	def private XExpression checkNonStructuralConditions(ParticipationContext participationContext,
		XFeatureCall participationObjects, extension TypeProvider typeProvider) {
		// TODO Implement this: This needs to check the (checked) non-containment participation relations and
		// conditions.
		// TODO If the participation context is for an attribute reference, we need to filter any conditions for
		// attributes which are also affected by the attribute reference.
		return XbaseFactory.eINSTANCE.createXBooleanLiteral => [
			isTrue = true
		]
	}

	def private getCreateIntermediateRoutine(ParticipationContext participationContext) {
		val participation = participationContext.participation
		val commonality = participation.containingCommonality
		return createIntermediateRoutines.computeIfAbsent(participationContext) [
			create.routine('''createIntermediate_«commonality.name»«reactionNameSuffix»''')
				.input [
					plain(ParticipationObjects, PARTICIPATION_OBJECTS)
				]
				.action [ extension it |
					vall(INTERMEDIATE).create(commonality.changeClass).andInitialize [
						claimIntermediateId(variable(INTERMEDIATE))
					]

					// Add correspondences with participation objects:
					participationContext.managedClasses
						// Any resource root container class is handled specifically:
						.filter[!participationClass.isForResource]
						.forEach [ contextClass |
							val participationClass = contextClass.participationClass
							addCorrespondenceBetween(INTERMEDIATE).and [ extension typeProvider |
								// TODO Ideally don't use a block expression here (results in more compact reactions code)
								// But: This is not yet supported by the fluent reactions builder.
								// Possible alternative: Call a routine which adds the correspondence between the
								// passed objects
								contextClass.getParticipationObject(variable(PARTICIPATION_OBJECTS), typeProvider)
							].taggedWith(participationClass.getCorrespondenceTag(commonality))
						]

					if (participationContext.isRootContext) {
						if (participation.hasSingletonClass) {
							// Setup the singleton if it hasn't been setup yet:
							val singletonClass = participation.singletonClass
							call(singletonClass.setupSingletonRoutine, new RoutineCallParameter [ extension typeProvider |
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

					// For commonality references: Insert the new intermediate into the referencing intermediate
					if (participationContext.forReferenceMapping) {
						val mapping = participationContext.referenceMapping
						val reference = mapping.declaringReference
						val referenceRootClass = participationContext.referenceRootClass
						call(reference.insertReferencedIntermediateRoutine, new RoutineCallParameter(INTERMEDIATE),
							new RoutineCallParameter [ extension typeProvider |
								referenceRootClass.getParticipationObject(variable(PARTICIPATION_OBJECTS), typeProvider)
							]
						)
					} else {
						// Insert new intermediate into the intermediate model root:
						call(insertIntermediateRoutineBuilderProvider.getFor(segment).getRoutine(commonality),
							new RoutineCallParameter(INTERMEDIATE))
					}

					// Apply all attribute mappings:
					// TODO trigger this on commonality creation instead, similar to sub-participation matching?
					// This would have the benefit that external participation matching would not need to generate and
					// invoke this routine, since it is handled by the referenced participation itself already.
					call(participation.applyParticipationAttributesRoutine, new RoutineCallParameter(INTERMEDIATE),
						new RoutineCallParameter(PARTICIPATION_OBJECTS))
				]
		]
	}

	def private getSetupSingletonRoutine(ParticipationClass singletonClass) {
		val participation = singletonClass.participation
		val commonality = participation.containingCommonality
		val singletonEClass = singletonClass.changeClass
		return create.routine('''setupSingleton_«participation.name»_«singletonClass.name»''')
			.input [
				model(singletonEClass, SINGLETON)
				model(commonality.changeClass, INTERMEDIATE)
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

				// Setup and insert a ResourceBridge for the resource root:
				// Note: This sets a commonality and participation specific
				// correspondence for the first participation the singleton
				// gets matched for. The ResourceBridge uses this for some of
				// its tasks.
				setupAndInsertResourceBridge(participation)
			]
	}

	/**
	 * Performs remaining setup and inserts the ResourceBridge returned by the
	 * ParticipationMatcher into the intermediate model.
	 */
	def private setupAndInsertResourceBridge(extension ActionStatementBuilder it, Participation participation) {
		val resourceClass = participation.resourceClass
		// assert: resourceClass !== null
		call(resourceClass.setupAndInsertResourceBridgeRoutine, new RoutineCallParameter [ extension typeProvider |
			resourceClass.getParticipationObject(variable(PARTICIPATION_OBJECTS), typeProvider)
		], new RoutineCallParameter(INTERMEDIATE))
	}

	def private getSetupAndInsertResourceBridgeRoutine(ParticipationClass resourceClass) {
		setupAndInsertResourceBridgeRoutines.computeIfAbsent(resourceClass) [
			resourceClass.generateSetupAndInsertResourceBridgeRoutine
		]
	}

	def private getInsertReferencedIntermediateRoutine(CommonalityReference reference) {
		val referencedCommonality = reference.referenceType
		val referencingCommonality = reference.containingCommonality
		return insertReferencedIntermediateRoutines.computeIfAbsent(reference) [
			create.routine('''insertReferencedIntermediate_«reference.reactionName»''')
				.input [
					model(referencedCommonality.changeClass, REFERENCED_INTERMEDIATE)
					model(EcorePackage.eINSTANCE.EObject, REFERENCE_ROOT)
				].match [
					vall(REFERENCING_INTERMEDIATE).retrieveAsserted(referencingCommonality.changeClass)
						.correspondingTo(REFERENCE_ROOT)
				].action [
					update(REFERENCING_INTERMEDIATE) [
						insertFeatureValue(variable(REFERENCING_INTERMEDIATE), reference.correspondingEReference,
							variable(REFERENCED_INTERMEDIATE))
					]
				]
		]
	}

	// TODO Generate this routine only once for every participation and then
	// for commonality reference mapping participation contexts import and call
	// it from external reaction segments?
	def private getApplyParticipationAttributesRoutine(Participation participation) {
		return applyParticipationAttributesRoutines.computeIfAbsent(participation) [
			applyParticipationAttributesRoutineBuilderFactory.createFor(participation).routine
		]
	}

	/**
	 * Invokes the matching routine as often as new participation matches are
	 * found.
	 * <p>
	 * This only really makes sense if the matching is invoked for the root
	 * object of a reference mapping participation context.
	 */
	def private getMatchManyParticipationsRoutine(ParticipationContext participationContext) {
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
									feature = typeProvider.findDeclaredType(BooleanResult).findMethod("getValue")
									explicitOperationCall = true
								]
								body = XbaseFactory.eINSTANCE.createXBlockExpression => [
									// Reset the result variable:
									expressions += resultVar.featureCall.memberFeatureCall => [
										feature = typeProvider.findDeclaredType(BooleanResult).findMethod("setValue")
										memberCallArguments += booleanLiteral(false)
										explicitOperationCall = true
									]

									// Call the matching routine:
									// Note: This follows attribute references.
									expressions += routineCallContext.createRoutineCall(typeProvider,
										participationContext.matchParticipationRoutine, variable(REFERENCE_ROOT),
										booleanLiteral(true), resultVar.featureCall)
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
	def package getMatchSubParticipationsRoutine(CommonalityReferenceMapping mapping) {
		return matchSubParticipationsRoutines.computeIfAbsent(mapping) [
			val participation = mapping.participation
			val commonality = participation.containingCommonality
			val participationContext = mapping.referenceParticipationContext

			// Generate the required routines:
			performCommonSetup()
			participationContext.generateRoutines()

			val mappingParticipationClass = mapping.participationClass
			return create.routine('''matchSubParticipations_«mapping.reactionName»''')
				.input [
					model(commonality.changeClass, INTERMEDIATE)
				]
				.match [
					vall(REFERENCE_ROOT).retrieve(mappingParticipationClass.changeClass)
						.correspondingTo(INTERMEDIATE)
						.taggedWith(mappingParticipationClass.correspondenceTag)
				]
				.action [
					// TODO: Avoid generating the same matching routines twice. These routines already exists inside
					// P -> C segment. Import the segment and call the matching routine there.
					if (participationContext.isForAttributeReferenceMapping) {
						call(participationContext.matchAttributeReferenceElementsRoutine,
							new RoutineCallParameter[variable(REFERENCE_ROOT)])
					} else {
						call(participationContext.matchManyParticipationsRoutine,
							new RoutineCallParameter[variable(REFERENCE_ROOT)])
					}
				]
		]
	}

	def package getMatchAttributeReferenceContainerForIntermediateRoutine(ParticipationContext participationContext) {
		return matchAttributeReferenceContainerForIntermediateRoutines.computeIfAbsent(participationContext) [
			// assert: participationContext.isForAttributeReferenceMapping
			val referencedCommonality = participationContext.referencedCommonality

			// Generate the required routines:
			performCommonSetup()
			participationContext.generateRoutines()

			return create.routine('''matchAttributeReferenceContainerForIntermediate«
				participationContext.reactionNameSuffix»''')
				.input [
					model(referencedCommonality.changeClass, REFERENCED_INTERMEDIATE)
				]
				.match [
					// We treat this like an attribute change for one of referenced participation objects:
					val attributeReferenceContainment = participationContext.attributeReferenceContainments.head
					// assert: attributeReferenceContainment !== null
					val referencedClass = attributeReferenceContainment.contained.participationClass
					vall(PARTICIPATION_OBJECT).retrieve(referencedClass.changeClass)
						.correspondingTo(REFERENCED_INTERMEDIATE)
						.taggedWith(referencedClass.correspondenceTag)
				]
				.action [
					call(participationContext.matchAttributeReferenceContainerRoutine,
						new RoutineCallParameter[variable(PARTICIPATION_OBJECT)])
				]
		]
	}
}
