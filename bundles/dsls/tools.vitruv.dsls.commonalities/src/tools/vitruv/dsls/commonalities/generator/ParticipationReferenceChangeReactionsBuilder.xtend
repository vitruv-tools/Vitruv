package tools.vitruv.dsls.commonalities.generator

import org.eclipse.emf.ecore.EReference
import tools.vitruv.dsls.commonalities.language.CommonalityReferenceMapping
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

import static com.google.common.base.Preconditions.*

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import static extension tools.vitruv.dsls.commonalities.generator.EmfAccessExpressions.*
import static extension tools.vitruv.dsls.commonalities.generator.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

class ParticipationReferenceChangeReactionsBuilder 
	extends ReactionsSubGenerator<ParticipationReferenceChangeReactionsBuilder> {
	Participation targetParticipation

	def package forParticipation(Participation targetParticipation) {
		this.targetParticipation = targetParticipation
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
			#[multiReferenceAddReaction(mapping), multiReferenceRemoveReaction(mapping)]
		} else {
			#[singleReferenceSetReaction(mapping)]
		}
	}
	
	def private singleReferenceSetReaction(CommonalityReferenceMapping mapping) {
		create.reaction('''«mapping.participationAttributeReactionName»Change''')
			.afterElement.replacedAt(mapping.participationReferenceChangeClass, mapping.participationEReference)
			.call [
				match [
					retrieveIntermediate()
					retrieveReferencedIntermediateOf(mapping).correspondingTo.newValue
				]
				.action [
					update('intermediate') [
						eSetFeature(variable('intermediate'), mapping.declaringReference.name, referencedIntermediate)
					]
				]
			] => [
				if (mapping.participationEReference.isContainment) {
					call(targetParticipation.insertRoutine)
				}
			]
	}

	def private multiReferenceAddReaction(CommonalityReferenceMapping mapping) {
		create.reaction('''«mapping.participationAttributeReactionName»Insert''')
			.afterElement.insertedIn(mapping.participationReferenceChangeClass, mapping.participationEReference)
			.call [
				match [
					retrieveIntermediate()
					retrieveReferencedIntermediateOf(mapping).correspondingTo.newValue
				]
				.action [
					update('intermediate') [
						eAddToFeatureList(variable('intermediate'), mapping.declaringReference.name, referencedIntermediate)
					]
				]
			] => [
				if (mapping.participationEReference.isContainment) {
					call(targetParticipation.insertRoutine)
				}
			]
	}

	def private multiReferenceRemoveReaction(CommonalityReferenceMapping mapping) {
		create.reaction('''«mapping.participationAttributeReactionName»Remove''').
			afterElement.removedFrom(mapping.participationReferenceChangeClass, mapping.participationEReference)
			.call [
				match [
					retrieveIntermediate()
					retrieveReferencedIntermediateOf(mapping).correspondingTo.oldValue
				]
				.action [
					update('intermediate') [
						eRemoveFromFeatureList(variable('intermediate'), mapping.declaringReference.name, referencedIntermediate)
					]
				]
			]
	}
	
	def private retrieveReferencedIntermediateOf(
		extension UndecidedMatcherStatementBuilder statementBuilder,
		CommonalityReferenceMapping mapping
	) {
		vall('referencedIntermediate').retrieveAsserted(mapping.referenceTypeChangeClass)
	}
	
	def private getReferencedIntermediate(extension RoutineTypeProvider builder) {
		variable('referencedIntermediate')
	}
	
	def private getParticipationReferenceChangeClass(CommonalityReferenceMapping mapping) {
		mapping.reference.participationClass.changeClass
	}
	
	def private getReferenceTypeChangeClass(CommonalityReferenceMapping mapping) {
		mapping.declaringReference.referenceType.changeClass
	}
	
	def private retrieveIntermediate(extension UndecidedMatcherStatementBuilder builder) {
		vall('intermediate').retrieveAsserted(commonalityFile.changeClass).correspondingTo.affectedEObject
	}
	
	def private getParticipationEReference(CommonalityReferenceMapping mapping) {
		mapping.reference.EFeatureToReference as EReference
	}
}