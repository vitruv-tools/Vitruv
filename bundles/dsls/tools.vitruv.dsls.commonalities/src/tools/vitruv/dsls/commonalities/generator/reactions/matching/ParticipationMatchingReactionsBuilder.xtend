package tools.vitruv.dsls.commonalities.generator.reactions.matching

import com.google.inject.Inject
import java.util.HashMap
import java.util.Map
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.xtext.common.types.TypesFactory
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.generator.helper.ContainmentHelper
import tools.vitruv.dsls.commonalities.generator.reactions.attribute.ApplyParticipationAttributesRoutineBuilder
import tools.vitruv.dsls.commonalities.generator.reactions.helper.ReactionsGenerationHelper
import tools.vitruv.dsls.commonalities.generator.reactions.intermediatemodel.InsertIntermediateRoutineBuilder
import tools.vitruv.dsls.commonalities.generator.reactions.participation.ParticipationObjectsHelper
import tools.vitruv.dsls.commonalities.generator.reactions.reference.ReferenceMappingOperatorHelper
import tools.vitruv.dsls.commonalities.generator.reactions.reference.ReferenceMappingOperatorHelper.ReferenceMappingOperatorContext
import tools.vitruv.dsls.commonalities.generator.reactions.resource.InsertResourceBridgeRoutineBuilder
import tools.vitruv.dsls.commonalities.generator.reactions.resource.SetupResourceBridgeRoutineBuilder
import tools.vitruv.dsls.commonalities.generator.reactions.util.ReactionsHelper.RoutineCallContext
import tools.vitruv.dsls.commonalities.generator.reactions.util.ReactionsSegmentScopedProvider
import tools.vitruv.dsls.commonalities.generator.util.guice.GenerationScoped
import tools.vitruv.dsls.commonalities.language.CommonalityReference
import tools.vitruv.dsls.commonalities.language.CommonalityReferenceMapping
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.participation.OperatorContainment
import tools.vitruv.dsls.commonalities.participation.ParticipationContext
import tools.vitruv.dsls.commonalities.participation.ParticipationContextHelper
import tools.vitruv.dsls.commonalities.participation.ReferenceContainment
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
import static tools.vitruv.framework.util.XtendAssertHelper.*

import static extension tools.vitruv.dsls.commonalities.generator.reactions.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.intermediatemodel.IntermediateModelHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.EmfAccessExpressions.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.ReactionsHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.XbaseHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*
import static extension tools.vitruv.dsls.commonalities.participation.ParticipationContextHelper.*

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
class ParticipationMatchingReactionsBuilder extends ReactionsGenerationHelper {

	private static val Logger logger = Logger.getLogger(ParticipationMatchingReactionsBuilder) => [level = Level.TRACE]

	@GenerationScoped
	static class Provider extends ReactionsSegmentScopedProvider<ParticipationMatchingReactionsBuilder> {
		protected override createFor(FluentReactionsSegmentBuilder segment) {
			return new ParticipationMatchingReactionsBuilder(segment).injectMembers
		}
	}

	@Inject extension ContainmentHelper containmentHelper
	@Inject extension ParticipationObjectsHelper participationObjectsHelper
	@Inject extension ReferenceMappingOperatorHelper referenceMappingOperatorHelper
	@Inject extension SetupResourceBridgeRoutineBuilder.Provider setupResourceBridgeRoutineBuilderProvider
	@Inject extension InsertResourceBridgeRoutineBuilder.Provider insertResourceBridgeRoutineBuilderProvider
	@Inject extension InsertIntermediateRoutineBuilder.Provider insertIntermediateRoutineBuilderProvider
	// TODO It would be possible to generate this routine only once per participation and then import it when called
	// for external reference mapping participation contexts.
	@Inject extension ApplyParticipationAttributesRoutineBuilder.Provider applyParticipationAttributesRoutineBuilderProvider
	@Inject extension AttributeReferenceMatchingReactionsBuilder.Provider attributeReferenceMatchingReactionsBuilderProvider

	val FluentReactionsSegmentBuilder segment

	val Map<ParticipationContext, FluentRoutineBuilder> matchParticipationRoutines = new HashMap
	val Map<ParticipationContext, FluentRoutineBuilder> createIntermediateRoutines = new HashMap
	val Map<CommonalityReference, FluentRoutineBuilder> insertReferencedIntermediateRoutines = new HashMap
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

	package def void generateReactions(ParticipationContext participationContext) {
		if (participationContext.forReferenceMapping) {
			val mapping = participationContext.referenceMapping
			val reference = mapping.declaringReference
			val commonality = participationContext.referencingCommonality
			logger.debug('''Commonality «commonality»: Generating matching reactions for participation '«
				mapping.participation»' and reference '«reference.name»'.''')
		} else {
			val participation = participationContext.participation
			val commonality = participation.containingCommonality
			logger.debug('''Commonality «commonality»: Generating matching reactions for participation '«
				participation»'.''')
		}

		participationContext.generateRoutines()

		if (participationContext.isForAttributeReferenceMapping) {
			segment.generateAttributeReferenceMatchingReactions(participationContext)
		} else {
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
		}

		// Note: We do not need to react to the deletion of participation
		// objects, because we also receive events for the removal from their
		// previous container.

		// TODO reactions to react to attribute changes that might fulfill the non-structural conditions
		// TODO also need to react to them in order to delete the participation again once a checked
		// condition is no longer fulfilled (conditions that get only enforced are ignored)
	}

	private def generateRoutines(ParticipationContext participationContext) {
		val participation = participationContext.participation
		val commonality = participation.containingCommonality

		segment += participationContext.matchParticipationRoutine
		segment += participationContext.createIntermediateRoutine

		if (participationContext.isRootContext) {
			segment += segment.getInsertIntermediateRoutine(commonality.concept)
		}

		if (participationContext.forReferenceMapping) {
			segment += participationContext.referenceMapping.declaringReference.insertReferencedIntermediateRoutine
			segment += participationContext.matchManyParticipationsRoutine
		}
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
		return reaction.call(participationContext.matchParticipationRoutine, startObject,
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
	private def getMatchParticipationRoutine(ParticipationContext participationContext) {
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

	private def isRootCommonalityParticipation(ParticipationContext participationContext) {
		return (participationContext.isRootContext && participationContext.participation.isCommonalityParticipation)
	}

	// TODO Matching of participation contexts for attribute references is no longer required (at least for now) and
	// could therefore be removed.
	private def matchParticipation(ParticipationContext participationContext, RoutineCallContext routineCallContext,
		extension TypeProvider typeProvider) {
		val participation = participationContext.participation
		val isRootCommonalityParticipation = participationContext.isRootCommonalityParticipation
		return XbaseFactory.eINSTANCE.createXBlockExpression => [
			// Create new ContainmentTree:
			val containmentTreeBuilder = new ContainmentTreeExpressionBuilder(typeProvider)
			val containmentTreeVar = containmentTreeBuilder.newContainmentTree('containmentTree')
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
				val intermediateRootEClass = participation.participationConcept.intermediateMetamodelRootClass
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
					val operatorMapping = containment.mapping
					val operatorContext = new ReferenceMappingOperatorContext(typeProvider)
					val operatorInstance = operatorMapping.constructOperator(operatorContext)
					if (operatorMapping.operator.isAttributeReference) {
						containmentTreeBuilder.addAttributeReferenceEdge(contained.name, operatorInstance)
					} else {
						containmentTreeBuilder.addOperatorEdge(container.name, contained.name, operatorInstance)
					}
				} else {
					throw new IllegalStateException("Unexpected containment type: " + containment.class.name)
				}
			]

			// Create participation matcher:
			val participationMatcherType = typeProvider.findDeclaredType(ParticipationMatcher).imported
			val participationMatcherVar = XbaseFactory.eINSTANCE.createXVariableDeclaration => [
				name = 'participationMatcher'
				right = XbaseFactory.eINSTANCE.createXConstructorCall => [
					constructor = participationMatcherType.findConstructor()
					explicitConstructorCall = true
					arguments += expressions(
						containmentTreeVar.featureCall,
						variable(START_OBJECT),
						variable(FOLLOW_ATTRIBUTE_REFERENCES),
						correspondenceModel,
						resourceAccess
					)
				]
			]
			expressions += participationMatcherVar

			// Match participation objects:
			val matchObjectsMethod = participationMatcherType.findMethod('matchObjects')
			val candidateMatchesVar = XbaseFactory.eINSTANCE.createXVariableDeclaration => [
				name = 'candidateMatches'
				type = jvmTypeReferenceBuilder.typeRef(Iterable, jvmTypeReferenceBuilder.typeRef(ParticipationObjects))
				right = participationMatcherVar.featureCall.memberFeatureCall(matchObjectsMethod)
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

	private def XExpression checkNonStructuralConditions(ParticipationContext participationContext,
		XFeatureCall participationObjects, extension TypeProvider typeProvider) {
		// TODO Implement this: This needs to check the (checked) non-containment participation relations and
		// conditions.
		// TODO If the participation context is for an attribute reference, we need to filter any conditions for
		// attributes which are also affected by the attribute reference.
		return XbaseFactory.eINSTANCE.createXBooleanLiteral => [
			isTrue = true
		]
	}

	private def getCreateIntermediateRoutine(ParticipationContext participationContext) {
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
						val participationClass = contextClass.participationClass
						addCorrespondenceBetween(INTERMEDIATE).and [ extension typeProvider |
							// TODO Ideally don't use a block expression here (results in more compact reactions code)
							// But: This is not yet supported by the fluent reactions builder.
							// Possible alternative: Call a routine which adds the correspondence between the
							// passed objects
							contextClass.getParticipationObject(variable(PARTICIPATION_OBJECTS), typeProvider)
						].taggedWith(participationClass.getCorrespondenceTag(commonality))
					]

					// For commonality references: Insert the new intermediate into the referencing intermediate
					if (participationContext.forReferenceMapping) {
						val mapping = participationContext.referenceMapping
						val reference = mapping.declaringReference
						val referenceRootClass = participationContext.referenceRootClass
						call(reference.insertReferencedIntermediateRoutine, new RoutineCallParameter(INTERMEDIATE),
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

	private def getSetupSingletonRoutine(ParticipationClass singletonClass) {
		val participation = singletonClass.participation
		val commonality = participation.containingCommonality
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

	private def getInsertReferencedIntermediateRoutine(CommonalityReference reference) {
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
	def getMatchSubParticipationsRoutine(CommonalityReferenceMapping mapping) {
		return matchSubParticipationsRoutines.computeIfAbsent(mapping) [
			val participation = mapping.participation
			val commonality = participation.containingCommonality
			val participationContext = mapping.referenceParticipationContext

			// Generate the required routines:
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
						call(segment.getMatchAttributeReferenceElementsRoutine(participationContext),
							new RoutineCallParameter[variable(REFERENCE_ROOT)])
					} else {
						call(participationContext.matchManyParticipationsRoutine,
							new RoutineCallParameter[variable(REFERENCE_ROOT)])
					}
				]
		]
	}

	def getMatchAttributeReferenceContainerForIntermediateRoutine(ParticipationContext participationContext) {
		return matchAttributeReferenceContainerForIntermediateRoutines.computeIfAbsent(participationContext) [
			assertTrue(participationContext.isForAttributeReferenceMapping)
			val referencedCommonality = participationContext.referencedCommonality

			// Generate the required routines:
			participationContext.generateRoutines()

			return create.routine('''matchAttributeReferenceContainerForIntermediate«
				participationContext.reactionNameSuffix»''')
				.input [
					model(referencedCommonality.changeClass, REFERENCED_INTERMEDIATE)
				]
				.match [
					// We treat this like an attribute change for one of referenced participation objects:
					val attributeReferenceContainment = participationContext.attributeReferenceContainments.head
					assertTrue(attributeReferenceContainment !== null)
					val referencedClass = attributeReferenceContainment.contained.participationClass
					vall(PARTICIPATION_OBJECT).retrieve(referencedClass.changeClass)
						.correspondingTo(REFERENCED_INTERMEDIATE)
						.taggedWith(referencedClass.correspondenceTag)
				]
				.action [
					call(segment.getMatchAttributeReferenceContainerRoutine(participationContext),
						new RoutineCallParameter[variable(PARTICIPATION_OBJECT)])
				]
		]
	}
}
