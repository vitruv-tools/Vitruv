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
import tools.vitruv.dsls.commonalities.generator.reactions.condition.CheckedParticipationConditionsHelper
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
import tools.vitruv.extensions.dslruntime.commonalities.matching.ContainmentContext
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
	@Inject extension CheckedParticipationConditionsHelper checkedParticipationConditionsHelper

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
	 * Sets up the {@link ContainmentContext} for the given
	 * {@link ParticipationContext}, invokes the {@link ParticipationMatcher}
	 * and instantiates the corresponding Commonality if a match is found.
	 * <p>
	 * If there are multiple candidates for a structural match, the first found
	 * candidate that also fulfills the non-structural conditions is used to
	 * establish the participation.
	 * <p>
	 * Note: We don't check if there already is a corresponding Intermediate
	 * for the given start object. If the participation context is for a
	 * commonality reference, the start object may be a containment context
	 * root object specified by the mappings, which already corresponds to an
	 * Intermediate (possibly even the same type of Intermediate that the
	 * participation will correspond to in case we find a match). Otherwise, if
	 * the participation context is not for a commonality reference, the
	 * {@link ParticipationMatcher} will already verify that the participation
	 * objects (including the passed start object) do not already correspond to
	 * an Intermediate.
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
	 * Returns a block expressions which sets up a ContainmentContext for the
	 * participation context, invokes the ParticipationMatcher and then creates
	 * a new intermediate for the first found candidate match which also
	 * fulfills the non-structural participation conditions.
	 */
	private def matchParticipation(ParticipationContext participationContext, RoutineCallContext routineCallContext,
		extension TypeProvider typeProvider) {
		return XbaseFactory.eINSTANCE.createXBlockExpression => [
			// Create new ContainmentContext:
			val containmentContextBuilder = setupContainmentContext(participationContext, typeProvider)
			val containmentContextVar = containmentContextBuilder.containmentContextVar
			join(containmentContextBuilder.resultExpressions)

			// Create participation matcher:
			val participationMatcherType = typeProvider.findDeclaredType(ParticipationMatcher).imported
			val participationMatcherVar = XbaseFactory.eINSTANCE.createXVariableDeclaration => [
				name = 'participationMatcher'
				right = XbaseFactory.eINSTANCE.createXConstructorCall => [
					constructor = participationMatcherType.findConstructor()
					explicitConstructorCall = true
					arguments += expressions(
						containmentContextVar.featureCall,
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

	private def ContainmentContextBuilder setupContainmentContext(ParticipationContext participationContext,
		extension TypeProvider typeProvider) {
		val participation = participationContext.participation
		val isRootCommonalityParticipation = participationContext.isRootCommonalityParticipation

		val containmentContextBuilder = new ContainmentContextBuilder(typeProvider)
		containmentContextBuilder.newContainmentContext('containmentContext')

		// TODO Find relevant EPackages up front and then reuse them for EClass lookup?
		// Same applies to EClasses that are reused by later calls.

		// Specify the root Intermediate type in case we try to match the
		// participation in the context of a commonality reference mapping:
		if (participationContext.forReferenceMapping) {
			val rootIntermediateType = participationContext.referencingCommonality.changeClass
			containmentContextBuilder.setRootIntermediateType(rootIntermediateType)
		}

		// Setup nodes:
		if (isRootCommonalityParticipation) {
			// Add the node for the implicit intermediate root:
			val intermediateRootEClass = participation.participationConcept.intermediateMetamodelRootClass
			containmentContextBuilder.addNode(INTERMEDIATE_ROOT, intermediateRootEClass, null)
		}
		participationContext.classes.forEach [ contextClass |
			val participationClass = contextClass.participationClass
			containmentContextBuilder.addNode(contextClass.name, participationClass.changeClass,
				participationClass.correspondenceTag)
		]
		if (participationContext.isForAttributeReferenceMapping) {
			// Add the node for the attribute reference root:
			val attributeReferenceRoot = participationContext.attributeReferenceRoot
			val attributeReferenceRootClass = attributeReferenceRoot.participationClass
			containmentContextBuilder.setAttributeReferenceRootNode(attributeReferenceRoot.name,
				attributeReferenceRootClass.changeClass, attributeReferenceRootClass.correspondenceTag)
		}

		// Setup containment edges:
		if (isRootCommonalityParticipation) {
			// Add edges for the implicit containments of the intermediate root:
			val containerName = INTERMEDIATE_ROOT
			val containmentEReference = IntermediateModelBasePackage.Literals.ROOT__INTERMEDIATES
			participation.nonRootBoundaryClasses.forEach [ contained |
				containmentContextBuilder.addReferenceEdge(containerName, contained.name, containmentEReference)
			]
		}
		participationContext.containments.forEach [ extension contextContainment |
			val containment = containment
			if (containment instanceof ReferenceContainment) {
				containmentContextBuilder.addReferenceEdge(container.name, contained.name, containment.EReference)
			} else if (containment instanceof OperatorContainment) {
				val operatorMapping = containment.mapping
				val operatorContext = new ReferenceMappingOperatorContext(typeProvider)
				val operatorInstance = operatorMapping.constructOperator(operatorContext)
				if (operatorMapping.operator.isAttributeReference) {
					containmentContextBuilder.addAttributeReferenceEdge(contained.name, operatorInstance)
				} else {
					containmentContextBuilder.addOperatorEdge(container.name, contained.name, operatorInstance)
				}
			} else {
				throw new IllegalStateException("Unexpected containment type: " + containment.class.name)
			}
		]
		return containmentContextBuilder
	}

	private def XExpression checkNonStructuralConditions(ParticipationContext participationContext,
		XFeatureCall participationObjects, extension TypeProvider typeProvider) {
		// Check if the relevant checked participation conditions are fulfilled:
		return participationContext.checkParticipationConditions(typeProvider, participationObjects)
	}
}
