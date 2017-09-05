package tools.vitruv.dsls.commonalities.generator

import com.google.inject.Inject
import org.eclipse.emf.ecore.EReference
import tools.vitruv.dsls.commonalities.language.CommonalityReference
import tools.vitruv.dsls.commonalities.language.CommonalityReferenceMapping
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder
import tools.vitruv.dsls.reactions.builder.FluentReactionsLanguageBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

import static com.google.common.base.Preconditions.*

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import static extension tools.vitruv.dsls.commonalities.generator.EmfAccessExpressions.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

class ParticipationReferenceChangeReactionsBuilder {
	@Inject FluentReactionsLanguageBuilder create
	extension GenerationContext generationContext
	Participation targetParticipation

	def package forParticipation(Participation targetParticipation) {
		this.targetParticipation = targetParticipation
		this
	}

	def package withGenerationContext(GenerationContext context) {
		this.generationContext = context
		this
	}	
	
	def package Iterable<FluentReactionBuilder> getReactions() {
		checkState(targetParticipation !== null, "No participation to create reactions for was set!")
		checkState(generationContext !== null, "No generation context was set!")

		commonality.references.flatMap[mappings].filter [
			isRead && participation == targetParticipation
		].flatMap[reactionsForReferenceMappingRightChange]
	}
	
	def reactionsForReferenceMappingRightChange(CommonalityReferenceMapping mapping) {
		if (mapping.reference.isMultiValued) {
			#[singleReferenceSetReaction(mapping)]
		} else {
			#[multiReferenceAddReaction(mapping), multiReferenceRemoveReaction(mapping)]
		}
	}
	
	def private singleReferenceSetReaction(CommonalityReferenceMapping mapping) {
		create.reaction('''«mapping.participation.name»«mapping.reference.name.toFirstUpper»Change''')
			.afterElement.replacedAt(mapping.participationReferenceChangeClass, mapping.participationEReference)
			.call [
				match [
					retrieveIntermediate()
					retrieveReferencedIntermediate(mapping.declaringReference)
					
				]
				.action [
					update('intermediate') [
						eSetFeature(variable('intermediate'), mapping.declaringReference.name, referencedIntermediate)
					]
				]
			]
	}

	def private multiReferenceAddReaction(CommonalityReferenceMapping mapping) {
		create.reaction('''«mapping.participation.name»«mapping.reference.name.toFirstUpper»ElementInsert''')
			.afterElement.insertedIn(mapping.participationReferenceChangeClass, mapping.participationEReference)
			.call [
				match [
					retrieveIntermediate()
					retrieveReferencedIntermediate(mapping.declaringReference)
					
				]
				.action [
					update('intermediate') [
						eAddToFeatureList(variable('intermediate'), mapping.declaringReference.name, referencedIntermediate)
					]
				]
			]
	}

	def private multiReferenceRemoveReaction(CommonalityReferenceMapping mapping) {
		create.reaction('''«mapping.participation.name»«mapping.reference.name.toFirstUpper»ElementRemove''').
			afterElement.removedFrom(mapping.participationReferenceChangeClass, mapping.participationEReference)
			.call [
				match [
					retrieveIntermediate()
					retrieveReferencedIntermediate(mapping.declaringReference)
				]
				.action [
					update('intermediate') [
						eRemoveFromFeatureList(variable('intermediate'), mapping.declaringReference.name, referencedIntermediate)
					]
				]
			]
	}
	
	def private getReferencedIntermediate(extension RoutineTypeProvider builder) {
		variable('referencedIntermediate')
	}
	
	def private getParticipationReferenceChangeClass(CommonalityReferenceMapping mapping) {
		mapping.reference.participationClass.changeClass
	}
	
	def private retrieveIntermediate(extension UndecidedMatcherStatementBuilder builder) {
		vall('intermediate').retrieve(commonalityFile.changeClass).correspondingTo.affectedEObject
	}
	
	def private retrieveReferencedIntermediate(extension UndecidedMatcherStatementBuilder builder, CommonalityReference reference) {
		vall('referencedIntermediate').retrieve(reference.referenceType.changeClass).correspondingTo.newValue
	}
	
	def private getParticipationEReference(CommonalityReferenceMapping mapping) {
		mapping.reference.EFeatureToReference as EReference
	}
}