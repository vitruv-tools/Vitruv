package tools.vitruv.dsls.commonalities.generator.reactions.matching

import com.google.inject.Inject
import java.util.HashMap
import java.util.Map
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.xtext.common.types.TypesFactory
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.generator.helper.ContainmentHelper
import tools.vitruv.dsls.commonalities.generator.reactions.helper.ReactionsGenerationHelper
import tools.vitruv.dsls.commonalities.generator.reactions.intermediatemodel.CreateIntermediateRoutineBuilder
import tools.vitruv.dsls.commonalities.generator.reactions.reference.ReferenceMappingOperatorHelper
import tools.vitruv.dsls.commonalities.generator.reactions.reference.ReferenceMappingOperatorHelper.ReferenceMappingOperatorContext
import tools.vitruv.dsls.commonalities.generator.reactions.util.ReactionsHelper.RoutineCallContext
import tools.vitruv.dsls.commonalities.generator.reactions.util.ReactionsSegmentScopedProvider
import tools.vitruv.dsls.commonalities.participation.OperatorContainment
import tools.vitruv.dsls.commonalities.participation.ParticipationContext
import tools.vitruv.dsls.commonalities.participation.ReferenceContainment
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.BooleanResult
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.IntermediateModelBasePackage
import tools.vitruv.extensions.dslruntime.commonalities.matching.ContainmentTree
import tools.vitruv.extensions.dslruntime.commonalities.matching.ParticipationMatcher
import tools.vitruv.extensions.dslruntime.commonalities.matching.ParticipationObjects

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.dsls.commonalities.generator.reactions.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.ReactionsHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.XbaseHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*
import static extension tools.vitruv.dsls.commonalities.participation.ParticipationContextHelper.*

package class MatchParticipationRoutineBuilder extends ReactionsGenerationHelper {

	static class Provider extends ReactionsSegmentScopedProvider<MatchParticipationRoutineBuilder> {

		protected override createFor(FluentReactionsSegmentBuilder segment) {
			return new MatchParticipationRoutineBuilder(segment).injectMembers
		}

		def getMatchParticipationRoutine(FluentReactionsSegmentBuilder segment,
			ParticipationContext participationContext) {
			return getFor(segment).getMatchParticipationRoutine(participationContext)
		}
	}

	@Inject extension ContainmentHelper containmentHelper
	@Inject extension ReferenceMappingOperatorHelper referenceMappingOperatorHelper
	@Inject extension CreateIntermediateRoutineBuilder.Provider createIntermediateRoutineBuilderProvider

	val FluentReactionsSegmentBuilder segment
	val Map<ParticipationContext, FluentRoutineBuilder> matchParticipationRoutines = new HashMap

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
	def getMatchParticipationRoutine(ParticipationContext participationContext) {
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

	/**
	 * Returns a block expressions which sets up a ContainmentTree for the
	 * participation context, invokes the ParticipationMatcher and then creates
	 * a new intermediate for the first found candidate match which also
	 * fulfills the non-structural participation conditions.
	 */
	private def matchParticipation(ParticipationContext participationContext, RoutineCallContext routineCallContext,
		extension TypeProvider typeProvider) {
		return XbaseFactory.eINSTANCE.createXBlockExpression => [
			// Create new ContainmentTree:
			val containmentTreeBuilder = setupContainmentTree(participationContext, typeProvider)
			val containmentTreeVar = containmentTreeBuilder.containmentTreeVar
			join(containmentTreeBuilder.resultExpressions)

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
								segment.getCreateIntermediateRoutine(participationContext), participationObjectsVar.featureCall)

							// Abort checking other candidate matches:
							expressions += XbaseFactory.eINSTANCE.createXReturnExpression
						]
					]
				]
			]
		]
	}

	private def isRootCommonalityParticipation(ParticipationContext participationContext) {
		return (participationContext.isRootContext && participationContext.participation.isCommonalityParticipation)
	}

	// TODO Matching of participation contexts for attribute references is no longer required (at least for now) and
	// could therefore be removed.
	private def ContainmentTreeBuilder setupContainmentTree(ParticipationContext participationContext,
		extension TypeProvider typeProvider) {
		val participation = participationContext.participation
		val isRootCommonalityParticipation = participationContext.isRootCommonalityParticipation

		val containmentTreeBuilder = new ContainmentTreeBuilder(typeProvider)
		containmentTreeBuilder.newContainmentTree('containmentTree')

		// TODO Find relevant EPackages up front and then reuse them for EClass lookup?
		// Same applies to EClasses that are reused by later calls.

		// Specify the root Intermediate type in case we try to match the
		// participation in the context of a commonality reference mapping:
		if (participationContext.forReferenceMapping) {
			val rootIntermediateType = participationContext.referencingCommonality.changeClass
			containmentTreeBuilder.setRootIntermediateType(rootIntermediateType)
		}

		// Setup nodes:
		if (isRootCommonalityParticipation) {
			// Add the node for the implicit intermediate root:
			val intermediateRootEClass = participation.participationConcept.intermediateMetamodelRootClass
			containmentTreeBuilder.addNode(INTERMEDIATE_ROOT, intermediateRootEClass)
		}
		participationContext.classes.forEach [ contextClass |
			val participationClass = contextClass.participationClass
			containmentTreeBuilder.addNode(contextClass.name, participationClass.changeClass)
		]
		if (participationContext.isForAttributeReferenceMapping) {
			// Add the node for the attribute reference root:
			val attributeReferenceRoot = participationContext.attributeReferenceRoot
			val attributeReferenceRootClass = attributeReferenceRoot.participationClass
			containmentTreeBuilder.setAttributeReferenceRootNode(attributeReferenceRoot.name,
				attributeReferenceRootClass.changeClass)
		}

		// Setup containment edges:
		if (isRootCommonalityParticipation) {
			// Add edges for the implicit containments of the intermediate root:
			val containerName = INTERMEDIATE_ROOT
			val containmentEReference = IntermediateModelBasePackage.Literals.ROOT__INTERMEDIATES
			participation.nonRootBoundaryClasses.forEach [ contained |
				containmentTreeBuilder.addReferenceEdge(containerName, contained.name, containmentEReference)
			]
		}
		participationContext.containments.forEach [ extension contextContainment |
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
		return containmentTreeBuilder
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
}
