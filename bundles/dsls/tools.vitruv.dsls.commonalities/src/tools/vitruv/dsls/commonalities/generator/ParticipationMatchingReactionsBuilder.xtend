package tools.vitruv.dsls.commonalities.generator

import com.google.inject.Inject
import java.util.HashMap
import java.util.Map
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.xtext.common.types.TypesFactory
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.generator.ReactionsHelper.RoutineCallContext
import tools.vitruv.dsls.commonalities.language.CommonalityReference
import tools.vitruv.dsls.commonalities.language.CommonalityReferenceMapping
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.extensions.Containment
import tools.vitruv.dsls.commonalities.language.extensions.ParticipationContext
import tools.vitruv.dsls.commonalities.language.extensions.ParticipationContextHelper
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder.PreconditionOrRoutineCallBuilder
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineCallParameter
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.BooleanResult
import tools.vitruv.extensions.dslruntime.commonalities.ParticipationMatcher
import tools.vitruv.extensions.dslruntime.commonalities.ParticipationMatcher.ContainmentTree
import tools.vitruv.extensions.dslruntime.commonalities.ParticipationMatcher.ParticipationObjects
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.IntermediateModelBasePackage

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
	static class Provider extends InjectingFactoryBase {

		val Map<FluentReactionsSegmentBuilder, ParticipationMatchingReactionsBuilder> bySegment = new HashMap

		def getFor(FluentReactionsSegmentBuilder segment) {
			return bySegment.computeIfAbsent(segment) [
				createFor(segment)
			]
		}

		def private createFor(FluentReactionsSegmentBuilder segment) {
			return new ParticipationMatchingReactionsBuilder(segment).injectMembers
		}
	}

	@Inject extension ContainmentHelper containmentHelper
	@Inject extension ResourceBridgeHelper resourceBridgeHelper
	@Inject extension ParticipationObjectsHelper participationObjectsHelper

	@Inject InsertIntermediateRoutineBuilder.Provider insertIntermediateRoutineBuilderProvider
	@Inject ApplyParticipationAttributesRoutineBuilder.Factory applyParticipationAttributesRoutineBuilderFactory

	val FluentReactionsSegmentBuilder segment

	val Map<ParticipationContext, FluentRoutineBuilder> matchParticipationRoutines = new HashMap
	val Map<ParticipationContext, FluentRoutineBuilder> createIntermediateRoutines = new HashMap
	val Map<ParticipationClass, FluentRoutineBuilder> insertResourceBridgeRoutines = new HashMap
	val Map<CommonalityReference, FluentRoutineBuilder> insertReferencedIntermediateRoutines = new HashMap
	val Map<Participation, FluentRoutineBuilder> applyParticipationAttributesRoutines = new HashMap
	val Map<CommonalityReferenceMapping, FluentRoutineBuilder> matchCommonalityReferenceMappingRoutines = new HashMap

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
			val commonality = participationContext.referencingCommonality
			logger.debug('''Commonality «commonality»: Generating matching reactions for reference «
				mapping.declaringReference.name» and mapping of participation «mapping.participation.name»''')
		} else {
			val participation = participationContext.participation
			val commonality = participation.containingCommonality
			logger.debug('''Commonality «commonality»: Generating matching reactions for participation «participation.name»''')
		}

		participationContext.generateRoutines
		participationContext.allContainments.forEach [ containment |
			// Note: For different participation contexts involving the same
			// participation, we generate multiple reactions with the same
			// trigger. The difference between them is which matching routine
			// they invoke.
			segment += participationContext.reactionForParticipationClassInsert(containment)
			segment += participationContext.reactionForParticipationClassRemove(containment)
			// Note: We do not need to react to the deletion of participation
			// objects, because we also receive events for the removal from
			// their previous container.
		]

		// TODO reactions to react to attribute changes that might fulfill the non-structural conditions
		// TODO also need to react to them in order to delete the participation again once a checked
		// condition is no longer fulfilled (conditions that get only enforced are ignored)
	}

	def private generateRoutines(ParticipationContext participationContext) {
		segment += participationContext.matchParticipationRoutine
		segment += participationContext.createIntermediateRoutine
		if (participationContext.forReferenceMapping) {
			segment += participationContext.referenceMapping.declaringReference.insertReferencedIntermediateRoutine
		}
	}

	/**
	 * When one of the containment relationships between potential
	 * participation objects is established, check if we can match the
	 * participation in the given context.
	 */
	def private reactionForParticipationClassInsert(ParticipationContext participationContext,
		extension Containment containment) {
		val participation = participationContext.participation
		var PreconditionOrRoutineCallBuilder reaction
		if (container.isForResource) {
			reaction = create.reaction('''«participation.name»_«contained.name»_insertedAtRoot«
				participationContext.reactionNameSuffix»''')
				.afterElementInsertedAsRoot(contained.changeClass)
		} else {
			reaction = create.reaction('''«participation.name»_«contained.name»_insertedAt_«container.name»_«
				containment.EReference.name»«participationContext.reactionNameSuffix»''')
				.afterElement(contained.changeClass).insertedIn(container.changeClass, containment.EReference)
		}
		return reaction.call(participationContext.matchParticipationRoutine, new RoutineCallParameter[newValue],
			new RoutineCallParameter[findDeclaredType(BooleanResult).noArgsConstructorCall])
	}

	/**
	 * When one of the the containment relationships between potential
	 * participation objects is removed, delete the corresponding commonality
	 * instance (if there is one).
	 */
	def private reactionForParticipationClassRemove(ParticipationContext participationContext,
		extension Containment containment) {
		val participation = participationContext.participation
		val commonality = participation.containingCommonality
		var PreconditionOrRoutineCallBuilder reaction
		if (container.isForResource) {
			reaction = create.reaction('''«participation.name»_«contained.name»_removedFromRoot«
				participationContext.reactionNameSuffix»''')
				.afterElementRemovedAsRoot(contained.changeClass)
		} else {
			reaction = create.reaction('''«participation.name»_«contained.name»_removedFrom_«
				container.name»_«containment.EReference.name»«participationContext.reactionNameSuffix»''')
				.afterElement(contained.changeClass).removedFrom(container.changeClass, containment.EReference)
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
	def private getMatchParticipationRoutine(ParticipationContext participationContext) {
		val participation = participationContext.participation
		return matchParticipationRoutines.computeIfAbsent(participationContext) [
			val extension routineCallContext = new RoutineCallContext
			create.routine('''matchParticipation_«participation.name»«reactionNameSuffix»''')
				.input [
					model(EcorePackage.eINSTANCE.EObject, START_OBJECT)
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
		val isRootCommonalityParticipation = (!participationContext.forReferenceMapping && participation.isCommonalityParticipation)
		return XbaseFactory.eINSTANCE.createXBlockExpression => [
			// Create new ContainmentTree:
			val containmentTreeType = typeProvider.findDeclaredType(ContainmentTree).imported
			val containmentTreeVar = XbaseFactory.eINSTANCE.createXVariableDeclaration => [
				name = "containmentTree"
				writeable = false
				type = jvmTypeReferenceBuilder.typeRef(containmentTreeType)
				right = XbaseFactory.eINSTANCE.createXConstructorCall => [
					it.constructor = containmentTreeType.findNoArgsConstructor
				]
			]
			expressions += containmentTreeVar
	
			// TODO Find relevant EPackages up front and then reuse them for EClass lookup?
			// Same applies to EClasses that are reused by later calls.
	
			// Specify the root Intermediate type in case we try to match the
			// participation in the context of a commonality reference mapping:
			if (participationContext.forReferenceMapping) {
				val setRootIntermediateTypeMethod = containmentTreeType.findMethod("setRootIntermediateType", EClass)
				expressions += containmentTreeVar.featureCall.memberFeatureCall(setRootIntermediateTypeMethod) => [
					explicitOperationCall = true
					val rootIntermediateType = participationContext.referencingCommonality.changeClass
					memberCallArguments += getEClass(typeProvider, rootIntermediateType)
				]
			}
	
			// Setup nodes:
			val addNodeMethod = containmentTreeType.findMethod("addNode", String, EClass)
			if (isRootCommonalityParticipation) {
				// Add the node for the implicit intermediate root:
				val intermediateRootEClass = participation.participationConcept.intermediateModelRootClass
				expressions += containmentTreeVar.featureCall.memberFeatureCall(addNodeMethod) => [
					memberCallArguments += expressions(
						stringLiteral(INTERMEDIATE_ROOT),
						getEClass(typeProvider, intermediateRootEClass)
					)
				]
			}
			expressions += participationContext.participationClasses.map [ participationClass |
				containmentTreeVar.featureCall.memberFeatureCall(addNodeMethod) => [
					memberCallArguments += expressions(
						stringLiteral(participationClass.name),
						getEClass(typeProvider, participationClass.changeClass)
					)
				]
			]
	
			// Setup containment edges:
			val addEdgeMethod = containmentTreeType.findMethod("addEdge", String, String, EReference)
			if (isRootCommonalityParticipation) {
				// Add edges for the implicit containments of the intermediate root:
				val containerName = INTERMEDIATE_ROOT
				val containmentEReference = IntermediateModelBasePackage.Literals.ROOT__INTERMEDIATES
				expressions += participation.nonRootBoundaryClasses.map [ contained |
					containmentTreeVar.featureCall.memberFeatureCall(addEdgeMethod) => [
						memberCallArguments += expressions(
							stringLiteral(contained.name),
							stringLiteral(containerName),
							getEReference(typeProvider, containmentEReference)
						)
					]
				]
			}
			expressions += participationContext.allContainments.map [ extension containment |
				containmentTreeVar.featureCall.memberFeatureCall(addEdgeMethod) => [
					memberCallArguments += expressions(
						stringLiteral(contained.name),
						stringLiteral(container.name),
						getEReference(typeProvider, containment.EReference)
					)
				]
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
					it.expressions += XbaseFactory.eINSTANCE.createXIfExpression => [
						it.^if = participationContext.checkNonStructuralConditions(participationObjectsVar.featureCall,
							typeProvider)
						it.then = XbaseFactory.eINSTANCE.createXBlockExpression => [
							// Set result flag:
							it.expressions += variable(FOUND_MATCH_RESULT).memberFeatureCall => [
								feature = typeProvider.findDeclaredType(BooleanResult).findMethod('setValue')
								memberCallArguments += booleanLiteral(true)
								explicitOperationCall = true
							]

							// Create intermediate:
							it.expressions += routineCallContext.createRoutineCall(typeProvider,
								participationContext.createIntermediateRoutine, participationObjectsVar.featureCall)

							// Abort checking other candidate matches:
							it.expressions += XbaseFactory.eINSTANCE.createXReturnExpression
						]
					]
				]
			]
		]
	}

	def private XExpression checkNonStructuralConditions(ParticipationContext participationContext,
		XFeatureCall participationObjects, extension TypeProvider typeProvider) {
		// TODO Implement this: This needs to check the (checked) non-containment participation relations and
		// conditions
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
					participationContext.participationClasses
						// Exclude the participation context's root container class, which is either handled
						// specifically (in the case of a Resource root container), or already corresponds to and is
						// managed by another Intermediate (in the context of an external reference mapping).
						.filter[!participationContext.isRootContainerClass(it)]
						.filter[!isInSingletonRoot]
						.forEach [ participationClass |
							addCorrespondenceBetween(INTERMEDIATE).and [ extension typeProvider |
								// TODO Ideally don't use a block expression here (results in more compact reactions code)
								// But: This is not yet supported by the fluent reactions builder.
								// Possible alternative: Call a routine which adds the correspondence between the
								// passed objects
								participationClass.getParticipationObject(variable(PARTICIPATION_OBJECTS), typeProvider)
							].taggedWith(participationClass.getCorrespondenceTag(commonality))
						]

					if (!participationContext.forReferenceMapping) {
						if (participation.hasSingletonClass) {
							// Setup the singleton if it hasn't been setup yet:
							val singletonClass = participation.singletonClass
							call(singletonClass.setupSingletonRoutine, new RoutineCallParameter [ extension typeProvider |
								singletonClass.getParticipationObject(variable(PARTICIPATION_OBJECTS), typeProvider)
							], new RoutineCallParameter(PARTICIPATION_OBJECTS))
						} else if (participation.hasResourceClass) {
							// If we have a Resource as root container, setup and insert the corresponding
							// ResourceBridge:
							insertResourceBridge(participation)
						}
					}

					// For commonality references: Insert the new intermediate into the referencing intermediate
					if (participationContext.forReferenceMapping) {
						val mapping = participationContext.referenceMapping
						val reference = mapping.declaringReference
						val mappingParticipationClass = mapping.reference.participationClass
						call(reference.insertReferencedIntermediateRoutine, new RoutineCallParameter(INTERMEDIATE),
							new RoutineCallParameter [ extension typeProvider |
								mappingParticipationClass.getParticipationObject(variable(PARTICIPATION_OBJECTS), typeProvider)
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
		val singletonEClass = singletonClass.changeClass
		return create.routine('''setupSingleton_«participation.name»_«singletonClass.name»''')
			.input [
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

				// Setup and insert a ResourceBridge for the resource root:
				// TODO This sets a commonality and participation specific
				// correspondence, which is not required here.
				insertResourceBridge(participation)
			]
	}

	def private insertResourceBridge(extension ActionStatementBuilder it, Participation participation) {
		// TODO The participation matcher already returns a new ResourceBridge
		// object. Use that instead? (still requires some setup though)
		val resourceClass = participation.resourceClass
		// assert: resourceClass !== null
		// assert: resourceClass === participationContext.rootContainerClass
		// We use any of the contained objects for the setup:
		val classInResource = resourceClass.containedClasses.head
		// assert: classInResource !== null
		// Note: We do not need to add a participation context specific suffix
		// to the insertResourceBridge routine, because this routine is only
		// required for a participation's own context and we expect that we
		// match at most one of these per reactions segment.
		call(resourceClass.insertResourceBridgeRoutine, new RoutineCallParameter [ extension typeProvider |
			classInResource.getParticipationObject(variable(PARTICIPATION_OBJECTS), typeProvider)
		])
	}

	def private getInsertResourceBridgeRoutine(ParticipationClass resourceClass) {
		// assert: There is only a single resource class per participation
		// assert: Inside a single reactions segment we handle at most one participation with a resource class
		insertResourceBridgeRoutines.computeIfAbsent(resourceClass) [
			resourceClass.generateInsertResourceBridgeRoutine
		]
	}

	def private getInsertReferencedIntermediateRoutine(CommonalityReference reference) {
		val referencedCommonality = reference.referenceType
		val referencingCommonality = reference.containingCommonality
		return insertReferencedIntermediateRoutines.computeIfAbsent(reference) [
			create.routine('''insertReferencedIntermediate_«reference.commonalityAttributeReactionName»''')
				.input [
					model(referencedCommonality.changeClass, REFERENCED_INTERMEDIATE)
					model(EcorePackage.eINSTANCE.EObject, PARTICIPATION_CONTEXT_ROOT)
				].match [
					vall(INTERMEDIATE).retrieveAsserted(referencingCommonality.changeClass)
						.correspondingTo(PARTICIPATION_CONTEXT_ROOT)
				].action [
					update(INTERMEDIATE) [
						if (reference.isMultiValued) {
							addToListFeatureValue(variable(INTERMEDIATE), reference.commonalityEReference, variable(REFERENCED_INTERMEDIATE))
						} else {
							setFeatureValue(variable(INTERMEDIATE), reference.commonalityEReference, variable(REFERENCED_INTERMEDIATE))
						}
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
	 * Generates the routines that are required to match the given commonality
	 * reference mapping and returns the routine that needs to be called in
	 * order to invoke the matching.
	 * <p>
	 * Called on commonality insert.
	 * <p>
	 * Optional: Empty if there is no valid ParticipationContext for the given
	 * mapping.
	 */
	def package getMatchCommonalityReferenceMappingRoutine(CommonalityReferenceMapping mapping) {
		val participation = mapping.participation
		val commonality = participation.containingCommonality
		return matchCommonalityReferenceMappingRoutines.computeIfAbsent(mapping) [
			val participationContext = mapping.referenceParticipationContext

			// Generate the required routines:
			participationContext.generateRoutines

			val mappingParticipationClass = mapping.reference.participationClass
			val extension routineCallContext = new RoutineCallContext
			return create.routine('''matchCommonalityReferenceMapping_«mapping.reactionName»''')
				.input [
					model(commonality.changeClass, INTERMEDIATE)
				]
				.match [
					vall(PARTICIPATION_CONTEXT_ROOT).retrieve(mappingParticipationClass.changeClass)
						.correspondingTo(INTERMEDIATE)
						.taggedWith(mappingParticipationClass.correspondenceTag)
				]
				.action [
					// TODO: Avoid generating the same matching twice. This routine already exists inside P -> C segment.
					// Import the segment and call the matching routine there.
					execute [ extension typeProvider |
						XbaseFactory.eINSTANCE.createXBlockExpression => [
							// Result variable (initially 'false'):
							val resultVar = XbaseFactory.eINSTANCE.createXVariableDeclaration => [
								type = jvmTypeReferenceBuilder.typeRef(BooleanResult)
								name = FOUND_MATCH_RESULT
								right = typeProvider.findDeclaredType(BooleanResult).imported.noArgsConstructorCall()
							]
							expressions += resultVar

							// Repeatedly invoke the matching procedure until no more matches are found:
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

									// Invoke the matching:
									expressions += routineCallContext.createRoutineCall(typeProvider,
										participationContext.matchParticipationRoutine,
										variable(PARTICIPATION_CONTEXT_ROOT), resultVar.featureCall)
								]
							]
						]
					].setCallerContext
				].setCaller
		]
	}
}