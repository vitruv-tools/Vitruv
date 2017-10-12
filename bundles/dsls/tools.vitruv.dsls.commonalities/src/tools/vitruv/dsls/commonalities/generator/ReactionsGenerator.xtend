package tools.vitruv.dsls.commonalities.generator

import com.google.inject.Inject
import com.google.inject.Provider
import java.util.function.Supplier
import org.eclipse.emf.ecore.EClass
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.ParticipationRelation
import tools.vitruv.dsls.reactions.api.generator.IReactionsGenerator
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder.ReactionTypeProvider
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.IntermediateModelManagement
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.IntermediateModelBasePackage
import tools.vitruv.framework.domains.VitruvDomainProviderRegistry

import static tools.vitruv.dsls.commonalities.generator.ParticipationRelationUtil.*

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import static extension tools.vitruv.dsls.commonalities.generator.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class ReactionsGenerator extends SubGenerator {

	val Supplier<IReactionsGenerator> reactionsGeneratorProvider
	val Supplier<CommonalityAttributeChangeReactionsBuilder> commonalityAttributeChangeReactionsBuilder
	val Supplier<ParticipationAttributeChangeReactionsBuilder> participationAttributeChangeReactionsBuilder
	val Supplier<ParticipationClassSpecialInitializationBuilder> participationClassSpecialInitializationBuilder
	val Supplier<ParticipationReferenceChangeReactionsBuilder> participationReferenceChangeReactionsBuilder
	val Supplier<CommonalityReferenceChangeReactionsBuilder> commonalityReferenceChangeReactionsBuilder
	@Inject Provider<ReactionsGenerationContext> reactionsGeneratorContextProvider
	@Inject CommonalitiesGenerationSettings generationSettings
	extension ReactionsGenerationContext reactionsGenerationContext

	@Inject
	new(
		Provider<IReactionsGenerator> reactionsGeneratorProvider,
		Provider<CommonalityAttributeChangeReactionsBuilder> commonalityAttributeChangeReactionsBuilderProvider,
		Provider<ParticipationAttributeChangeReactionsBuilder> participationAttributeChangeReactionsBuilderProvider,
		Provider<ParticipationClassSpecialInitializationBuilder> participationClassSpecialInitBuilderProvider,
		Provider<ParticipationReferenceChangeReactionsBuilder> participationReferenceChangeReactionsBuilderProvider,
		Provider<CommonalityReferenceChangeReactionsBuilder> commonalityReferenceChangeReactionsBuilderProvider
	) {
		this.reactionsGeneratorProvider = [
			reactionsGeneratorProvider.get() => [
				useResourceSet(commonalityFile.eResource.resourceSet)
			]
		]
		this.commonalityAttributeChangeReactionsBuilder = [
			commonalityAttributeChangeReactionsBuilderProvider.get.withGenerationContext(reactionsGenerationContext)
		]
		this.participationAttributeChangeReactionsBuilder = [
			participationAttributeChangeReactionsBuilderProvider.get.withGenerationContext(reactionsGenerationContext)
		]
		this.participationClassSpecialInitializationBuilder = [
			participationClassSpecialInitBuilderProvider.get.withGenerationContext(reactionsGenerationContext)
		]
		this.participationReferenceChangeReactionsBuilder = [
			participationReferenceChangeReactionsBuilderProvider.get.withGenerationContext(reactionsGenerationContext)
		]
		this.commonalityReferenceChangeReactionsBuilder = [
			commonalityReferenceChangeReactionsBuilderProvider.get.withGenerationContext(reactionsGenerationContext)
		]
	}
	
	override generate() {
		if (commonality.participations.length + commonality.allMembers.length == 0) {
			// nothing to generate
			return;
		}
		
		val generator = reactionsGeneratorProvider.get()
		reactionsGenerationContext = reactionsGeneratorContextProvider.get.wrappingContext(generationContext)

		val reactionFile = create.reactionsFile(commonality.name)
		for (participation : commonalityFile.commonality.participations) {
			reactionFile +=
				create.reactionsSegment('''«commonality.name»To«participation.name»''').
					inReactionToChangesIn(commonalityFile.concept.vitruvDomain)
					.executeActionsIn(participation.domain.vitruvDomain) +=
						commonalityChangeReactions(participation)

			reactionFile +=
				create.reactionsSegment('''«commonality.name»From«participation.name»''')
					.inReactionToChangesIn(participation.domain.vitruvDomain)
					.executeActionsIn(commonalityFile.concept.name) +=
						participationChangeReactions(participation)

		}

		VitruvDomainProviderRegistry.registerDomainProvider(commonalityFile.concept.name,
			commonalityFile.concept.vitruvDomain.provider)
		// TODO participation domains
		try {
			generator.addReactionsFile(reactionFile)
			generator.generate(fsa)

			if (generationSettings.createReactionFiles) {
				generator.writeReactions(fsa)
			}
		} finally {
			VitruvDomainProviderRegistry.unregisterDomainProvider(commonalityFile.concept.name)
		}
	}

	def private commonalityChangeReactions(Participation participation) {
		(
			#[
				reactionForCommonalityCreate(participation),
				reactionForCommonalityDelete(participation),
				reactionForCommonalityInsert(participation)
			] 
			+ participation.reactionsForCommonalityAttributeChange
			+ participation.reactionsForCommonalityReferenceChange
		).filterNull
	}

	def private participationChangeReactions(Participation participation) {
		(
			participation.classes.flatMap [#[
				reactionForParticipationClassCreate,
				reactionForParticipationClassDelete,  
				reactionForParticipationRootInsert
			]]
			+ participation.reactionsForParticipationAttributeChange
			+ participation.reactionsForParticipationReferenceChange
		).filterNull
	}

	def private reactionForCommonalityDelete(Participation participation) {
		create.reaction('''«commonality.name»Delete''')
			.afterElement(commonalityFile.changeClass).deleted
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

	def private reactionForParticipationClassDelete(ParticipationClass participationClass) {
		create.reaction('''«participationClass.name»Delete''')
			.afterElement(participationClass.changeClass).deleted
			.call [
				match [
					vall('corresponding_intermediate').retrieveAsserted(commonalityFile.changeClass)
						.correspondingTo.affectedEObject
				]
				action [
					delete('corresponding_intermediate')
				]
			]
	}



	def private reactionForCommonalityCreate(Participation participation) {
		create.reaction('''«commonality.name»Create''')
			.afterElement(commonalityFile.changeClass).created
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
						val specialInitBuilder = participationClassSpecialInitializationBuilder.get.forParticipationClass(participationClass)
						vall(corresponding).create(participationClass.changeClass) => [
							if (specialInitBuilder.hasSpecialInitialization) {
								andInitialize [ typeProvider |
									specialInitBuilder.getSpecialInitializer(typeProvider, [
										typeProvider.variable(correspondingVariableName)
									])
								]
							}
						]
						addCorrespondenceBetween.affectedEObject.and(corresponding)
							.taggedWith(participationClass.correspondenceTag)
					}
				]
			]
	}

	def private reactionForParticipationClassCreate(ParticipationClass participationClass) {
		create.reaction('''«participationClass.name»Create''')
			.afterElement(participationClass.changeClass).created
			.call [
				action [
					vall('newIndermediate').create(commonalityFile.changeClass).andInitialize [
						assignStagingId(variable('newIndermediate'))
					]
					addCorrespondenceBetween('newIndermediate').and.affectedEObject
						.taggedWith(participationClass.correspondenceTag)
				]
			]
	}

	def private reactionForParticipationRootInsert(ParticipationClass participationClass) {
		create.reaction('''«participationClass.name»RootInsert''')
			.afterElement(participationClass.changeClass).insertedAsRoot
			.call(#[
				participationClass.intermediateResourceBridgeRoutine,
				participationClass.participation.insertRoutine
			].filterNull)
	}
	
	def private reactionForCommonalityInsert(Participation participation) {
		val relations = newHashMap(participation.classes.map [optionalParticipationRelation]
			.filterNull
			.toSet
			.map [it -> operator.findOptionalImplementedMethod('afterInserted')]
			.filter[value !== null])
		
		if (relations.size > 0) {
			create.reaction('''«commonality.name»Insert''')
				.afterElement(commonalityFile.changeClass).insertedIn(IntermediateModelBasePackage.eINSTANCE.root_Intermediates)
				.call [
					match [
						for (partClass : relations.keySet.flatMap [participationClasses]) {
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
	}

	def private reactionsForCommonalityAttributeChange(Participation participation) {
		commonality.attributes.flatMap [ attribute |
			commonalityAttributeChangeReactionsBuilder.get.forAttribute(attribute).regardingParticipation(participation).reactions
		]
	}

	def private reactionsForCommonalityReferenceChange(Participation participation) {
		commonality.references.flatMap [ reference |
			commonalityReferenceChangeReactionsBuilder.get.forReference(reference).regardingParticipation(participation).reactions
		]
	}

	def private reactionsForParticipationAttributeChange(Participation participation) {
		participationAttributeChangeReactionsBuilder.get.forParticipation(participation).reactions
	}
	
	def private reactionsForParticipationReferenceChange(Participation participation) {
		participationReferenceChangeReactionsBuilder.get.forParticipation(participation).reactions
	}

	def hasResource(extension ReactionTypeProvider typeProvider, XFeatureCall element) {
		XbaseFactory.eINSTANCE.createXBinaryOperation => [
			leftOperand = XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
				memberCallTarget = element
				feature = typeProvider.findMethod(EClass, 'eResource')
			]
			feature = typeProvider.findMethod(ObjectExtensions, 'operator_tripleNotEquals')
			rightOperand = XbaseFactory.eINSTANCE.createXNullLiteral
		]
	}

	def private assignStagingId(extension RoutineTypeProvider typeProvider, XFeatureCall element) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = element
			feature = typeProvider.findMethod(IntermediateModelManagement, 'claimStagingId').staticExtensionWildcardImported
			explicitOperationCall = true
		]
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
