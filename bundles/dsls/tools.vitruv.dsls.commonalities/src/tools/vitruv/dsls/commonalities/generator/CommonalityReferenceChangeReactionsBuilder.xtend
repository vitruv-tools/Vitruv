package tools.vitruv.dsls.commonalities.generator

import java.util.Collections
import java.util.List
import org.eclipse.emf.ecore.EReference
import tools.vitruv.dsls.commonalities.language.CommonalityReference
import tools.vitruv.dsls.commonalities.language.CommonalityReferenceMapping
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.dsls.commonalities.generator.EmfAccessExpressions.*
import static extension tools.vitruv.dsls.commonalities.generator.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

class CommonalityReferenceChangeReactionsBuilder
	extends ReactionsSubGenerator<CommonalityReferenceChangeReactionsBuilder> {
		
	CommonalityReference reference
	Participation targetParticipation
	List<CommonalityReferenceMapping> relevantMappings

	def package forReference(CommonalityReference reference) {
		this.reference = reference
		this
	}

	def package regardingParticipation(Participation targetParticipation) {
		this.targetParticipation = targetParticipation
		this
	}
	
	def package Iterable<FluentReactionBuilder> getReactions() {
		checkState(reference !== null, "No reference to create reactions for was set!")
		checkState(targetParticipation !== null, "No participation to create reactions for was set!")
		checkState(generationContext !== null, "No generation context was set!")

		relevantMappings = reference.mappings.filter [
			isWrite && participation == targetParticipation
		].toList

		if (relevantMappings.size === 0) return Collections.emptyList

		if (reference.isMultiValued) {
			#[multiReferenceAddReaction, multiReferenceRemoveReaction]
		} else {
			Collections.singleton(singleReferenceSetReaction)
		}
	}
	def private singleReferenceSetReaction() {
		create.reaction('''«reference.commonalityAttributeReactionName»Change''')
			.afterElement.replacedAt(reference.EFeatureToReference as EReference)
			.call [
				input [newValue]
				.match [
					for (mapping : relevantMappings) {
						retrieveCorrespondingAffectedObjectOf(mapping)
						retrieveReferencedValueOf(mapping).correspondingTo.newValue
					}
				]
				.action [
					for (mapping : relevantMappings) {
						val correspondingElement = mapping.correspondingParticipationClassVariable
						val correspondingValue = mapping.referencedParticipationClassVariable
						update(correspondingElement) [
							setFeature(
								variable(correspondingElement),
								mapping.reference.EFeatureToReference,
								variable(correspondingValue)
							)
						]
					}
				]
			]
	}

	def private multiReferenceAddReaction() {
		create.reaction('''«reference.commonalityAttributeReactionName»Insert''')
			.afterElement.insertedIn(reference.EFeatureToReference as EReference)
			.call [
				input [newValue]
				.match [
					for (mapping : relevantMappings) {
						retrieveCorrespondingAffectedObjectOf(mapping)
						retrieveReferencedValueOf(mapping).correspondingTo.newValue
					}
				]
				.action [
					for (mapping : relevantMappings) {
						val correspondingElement = mapping.correspondingParticipationClassVariable
						val correspondingValue = mapping.referencedParticipationClassVariable
						update(correspondingElement) [
							addToFeatureList(
								variable(correspondingElement),
								mapping.reference.EFeatureToReference,
								variable(correspondingValue)
							)
						]
					}
				]
			]
	}

	def private multiReferenceRemoveReaction() {
		create.reaction('''«reference.commonalityAttributeReactionName»Remove''')
			.afterElement.removedFrom(reference.EFeatureToReference as EReference)
			.call [
				input [oldValue]
				.match [
					for (mapping : relevantMappings) {
						retrieveCorrespondingAffectedObjectOf(mapping)
						retrieveReferencedValueOf(mapping).correspondingTo.oldValue
					}
				]
				.action [
					for (mapping : relevantMappings) {
						val correspondingElement = mapping.correspondingParticipationClassVariable
						val correspondingValue = mapping.referencedParticipationClassVariable
						update(correspondingElement) [
							removeFromFeatureList(
								variable(correspondingElement),
								mapping.reference.EFeatureToReference,
								variable(correspondingValue)
							)
						]
					}
				]
			]
	}
	
	def private retrieveCorrespondingAffectedObjectOf(
		extension UndecidedMatcherStatementBuilder matcherBuilder,
		CommonalityReferenceMapping mapping
	) {
		val participationClass = mapping.reference.participationClass
		vall(participationClass.correspondingVariableName).retrieveAsserted(participationClass.changeClass)
			.correspondingTo.affectedEObject
			.taggedWith(participationClass.correspondenceTag)
	}
	
	def private getCorrespondingParticipationClassVariable(CommonalityReferenceMapping mapping) {
		mapping.reference.participationClass.correspondingVariableName
	}
	
	def private getReferencedParticipationClassVariable(CommonalityReferenceMapping mapping) {
		mapping.matchingReferencedParticipations.head.changedVariableName
	}
	
	def private retrieveReferencedValueOf(
		extension UndecidedMatcherStatementBuilder matcherBuilder,
		CommonalityReferenceMapping mapping
	) {
		val referencedParticipationClass = mapping.matchingReferencedParticipations.head
		vall(referencedParticipationClass.changedVariableName).retrieveAsserted(referencedParticipationClass.changeClass)
	}
	
	def private static String getChangedVariableName(ParticipationClass participationClass) {
		'''changed_«participationClass.name»'''
	}
}