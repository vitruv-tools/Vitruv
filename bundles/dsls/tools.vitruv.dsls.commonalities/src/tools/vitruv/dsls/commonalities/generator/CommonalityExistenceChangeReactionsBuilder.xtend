package tools.vitruv.dsls.commonalities.generator

import com.google.inject.Inject
import com.google.inject.Provider
import java.util.List
import java.util.function.Supplier
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import org.eclipse.xtext.xbase.lib.Functions.Function1
import tools.vitruv.dsls.commonalities.language.Commonality
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.ParticipationCondition
import tools.vitruv.dsls.commonalities.language.ParticipationRelation
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder
import tools.vitruv.dsls.reactions.builder.TypeProvider

import static com.google.common.base.Preconditions.*
import static tools.vitruv.dsls.commonalities.generator.EmfAccessExpressions.*
import static tools.vitruv.dsls.commonalities.generator.ParticipationRelationUtil.*
import static tools.vitruv.dsls.commonalities.generator.XbaseHelper.*

import static extension tools.vitruv.dsls.commonalities.generator.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.generator.ReactionsHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

class CommonalityExistenceChangeReactionsBuilder extends ReactionsSubGenerator<CommonalityExistenceChangeReactionsBuilder> {

	val Supplier<ResourceInitializationBuilder> resourceInitializationBuilder
	val Supplier<ParticipationRelationInitializationBuilder> participationRelationInitializationBuilder

	@Inject
	new(
		Provider<ResourceInitializationBuilder> resourceInitializationBuilderProvider,
		Provider<ParticipationRelationInitializationBuilder> participationRelationInitializationBuilderProvider
	) {
		this.resourceInitializationBuilder = [
			resourceInitializationBuilderProvider.get.withGenerationContext(reactionsGenerationContext)
		]
		this.participationRelationInitializationBuilder = [
			participationRelationInitializationBuilderProvider.get.withGenerationContext(reactionsGenerationContext)
		]
	}

	Participation participation
	Commonality commonality

	def package forParticipation(Participation participation) {
		this.participation = participation
		this.commonality = participation.containingCommonality
		return this
	}

	def package Iterable<FluentReactionBuilder> getReactions() {
		checkState(participation !== null, "No participation to create reactions for was set!")
		checkState(generationContext !== null, "No generation context was set!")

		return #[
			reactionForCommonalityCreate,
			reactionForCommonalityDelete,
			reactionForCommonalityRootInsert
		]
	}

	def private reactionForCommonalityCreate() {
		create.reaction('''«commonality.concept.name»_«commonality.name»Create''')
			.afterElement(commonality.changeClass).created
			.call [
				match [
					for (participationClass : participation.classes) {
						requireAbsenceOf(participationClass.changeClass).correspondingTo.affectedEObject
							.taggedWith(participationClass.correspondenceTag)
					}
				]
				.action [
					for (participationClass : participation.classes) {
						val corresponding = participationClass.correspondingVariableName
						vall(corresponding).create(participationClass.changeClass) => [
							val initializers = participationClass.participationClassInitializers
							if (!initializers.empty) {
								andInitialize [ typeProvider |
									initializers.toBlockExpression(typeProvider)
								]
							}
						]
						addCorrespondenceBetween.affectedEObject.and(corresponding)
							.taggedWith(participationClass.correspondenceTag)
					}

					// Any initialization that needs to happen after all model objects were created:
					for (participationClass : participation.classes) {
						val postInitializers = participationClass.participationClassPostInitializers
						if (!postInitializers.empty) {
							update(participationClass.correspondingVariableName, [ typeProvider |
								postInitializers.toBlockExpression(typeProvider)
							])
						}
					}
				]
			]
	}

	def private toBlockExpression(Iterable<? extends Function1<TypeProvider, ? extends XExpression>> expressionBuilders,
			TypeProvider typeProvider) {
		return XbaseFactory.eINSTANCE.createXBlockExpression => [
			expressions += expressionBuilders.map[apply(typeProvider)]
		]
	}

	def private getParticipationClassInitializers(ParticipationClass participationClass) {
		return #[
			participationClass.resourceInitializer,
			participationClass.commonalityParticipationClassInitializer
		].filterNull.toList
	}

	def private getResourceInitializer(ParticipationClass participationClass) {
		extension val resourceInitBuilder = resourceInitializationBuilder.get.forParticipationClass(participationClass)
		if (!resourceInitBuilder.hasInitializer) return null
		return [ TypeProvider typeProvider |
			val resource = typeProvider.variable(participationClass.correspondingVariableName)
			return resource.getInitializer(typeProvider)
		]
	}

	def private getCommonalityParticipationClassInitializer(ParticipationClass participationClass) {
		if (!participationClass.participation.isCommonalityParticipation) return null
		return [ TypeProvider typeProvider |
			assignStagingId(typeProvider, typeProvider.variable(participationClass.correspondingVariableName))
		]
	}

	def private getParticipationClassPostInitializers(ParticipationClass participationClass) {
		// The initialization done by operators might want to reference other
		// model objects, so we run their initializations after all model
		// objects were created
		return (#[
			participationClass.participationRelationInitializer
		] + participationClass.participationClassConditionInitializers).filterNull.toList
	}

	def private getParticipationRelationInitializer(ParticipationClass participationClass) {
		val participationRelationInitBuilder = participationRelationInitializationBuilder.get.forParticipationClass(participationClass)
		if (!participationRelationInitBuilder.hasInitializer) return null
		return [ TypeProvider typeProvider |
			participationRelationInitBuilder.getInitializer(typeProvider, [
				typeProvider.variable(correspondingVariableName)
			])
		]
	}

	def private getParticipationClassConditionInitializers(ParticipationClass participationClass) {
		return participationClass.participation.conditions
			.filter[enforced]
			.filter[attribute.participationClass == participationClass]
			.map[participationConditionInitializer]
	}

	def private getParticipationConditionInitializer(ParticipationCondition participationCondition) {
		return [ extension TypeProvider typeProvider |
			val operator = participationCondition.operator.imported
			val enforceMethod = operator.findOptionalImplementedMethod("enforce")
			return XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
				memberCallTarget = participationCondition.newParticipationConditionOperator(typeProvider)
				feature = enforceMethod
				explicitOperationCall = true
			]
		]
	}

	def package newParticipationConditionOperator(ParticipationCondition participationCondition, extension TypeProvider typeProvider) {
		val attribute = participationCondition.attribute
		val participationClass = attribute.participationClass
		val participationClassInstance = typeProvider.variable(participationClass.correspondingVariableName)
		XbaseFactory.eINSTANCE.createXConstructorCall => [
			constructor = participationCondition.operator.findConstructor(EObject, EStructuralFeature, List)
			explicitConstructorCall = true
			arguments += expressions(
				participationClassInstance,
				getEFeature(typeProvider, participationClassInstance, attribute.name),
				XbaseFactory.eINSTANCE.createXListLiteral => [
					elements += participationCondition.parameters.map[expression]
				]
			)
		]
	}

	def private reactionForCommonalityDelete() {
		create.reaction('''«commonality.concept.name»_«commonality.name»Delete''')
			.afterElement(commonality.changeClass).deleted
			.call [
				match [
					for (participationClass : participation.classes) {
						vall('''corresponding_«participationClass.name»''').retrieveAsserted(participationClass.changeClass)
							.correspondingTo.affectedEObject
							.taggedWith(participationClass.correspondenceTag)
					}
				]
				action [
					for (participationClass : participation.classes) {
						delete('''corresponding_«participationClass.name»''')
					}
				]
			]
	}

	def private reactionForCommonalityRootInsert() {
		// Check for participation relations that trigger on inserts:
		val relations = newHashMap(participation.classes
			.map[optionalParticipationRelation]
			.filterNull
			.toSet
			.map[it -> operator.findOptionalImplementedMethod('afterInserted')]
			.filter[value !== null])

		if (relations.size > 0 || participation.isCommonalityParticipation) {
			// TODO participation domains
			val reactionStart = create.reaction('''«commonality.concept.name»_«commonality.name»RootInsert''')
				.afterElementInsertedAsRoot(commonality.changeClass)

			var FluentReactionBuilder reaction = null;
			if (relations.size > 0) {
				reaction = reactionStart.call [
					match [
						for (partClass : relations.keySet.flatMap[participationClasses]) {
							vall(partClass.correspondingVariableName).retrieveAsserted(partClass.changeClass)
								.correspondingTo.newValue
								.taggedWith(partClass.correspondenceTag)
						}
					]
					.action [
						for (entry : relations.entrySet) {
							val relation = entry.key
							val insertOperation = entry.value
							execute [
								callOperationOnRelation(relation, insertOperation)
							]
						}
					]
				]
			}

			// Each participating commonality is implicitly contained inside the root of its intermediate model.
			// This containment relation is realized when the current commonality instance is inserted into its root.
			// TODO In case relations between commonalities are a thing: Only apply implicit root containment for those
			// commonalities without relation? Or make the root relation explicit (similar to 'in Resource')?
			if (participation.isCommonalityParticipation) {
				for (participationClass : participation.classes) {
					val participatingCommonality = participationClass.participatingCommonality // assert: not null
					reaction = reactionStart.call(getInsertRoutine(participation, participatingCommonality))
				}
			}
			reaction
		}
	}

	def private callOperationOnRelation(extension RoutineTypeProvider typeProvider,
		ParticipationRelation relation, JvmOperation operation) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = createOperatorConstructorCall(relation, typeProvider, [variable(correspondingVariableName)])
			feature = operation
			explicitOperationCall = true
		]
	}
}
