package tools.vitruv.dsls.commonalities.generator

import java.util.Collections
import java.util.List
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EReference
import tools.vitruv.dsls.commonalities.language.CommonalityAttribute
import tools.vitruv.dsls.commonalities.language.CommonalityAttributeMapping
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.elements.EClassAdapter
import tools.vitruv.dsls.commonalities.language.elements.EDataTypeAdapter
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.dsls.commonalities.generator.EmfAccessExpressions.*
import static extension tools.vitruv.dsls.commonalities.generator.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class CommonalityAttributeChangeReactionsBuilder 
	extends ReactionsSubGenerator<CommonalityAttributeChangeReactionsBuilder> {
	CommonalityAttribute attribute
	Participation targetParticipation
	List<CommonalityAttributeMapping> relevantMappings

	def package forAttribute(CommonalityAttribute attribute) {
		this.attribute = attribute
		this
	}

	def package regardingParticipation(Participation targetParticipation) {
		this.targetParticipation = targetParticipation
		this
	}

	def package Iterable<FluentReactionBuilder> getReactions() {
		checkState(attribute !== null, "No attribute to create reactions for was set!")
		checkState(targetParticipation !== null, "No participation to create reactions for was set!")
		checkState(generationContext !== null, "No generation context was set!")

		relevantMappings = attribute.mappings.filter [
			isWrite && participation == targetParticipation
		].toList

		if (relevantMappings.size === 0) return Collections.emptyList

		switch attribute.type {
			EDataTypeAdapter case !attribute.isMultiValued:
				Collections.singleton(singleAttributeSetReaction)
			EDataTypeAdapter case attribute.isMultiValued:
				#[multiAttributeAddReaction, multiAttributeRemoveReaction]
			EClassAdapter case !attribute.isMultiValued:
				Collections.singleton(singleReferenceSetReaction)
			EClassAdapter case attribute.isMultiValued:
				#[multiReferenceAddReaction, multiReferenceRemoveReaction]
		}
	}

	def private singleAttributeSetReaction() {
		create.reaction('''«attribute.commonalityAttributeReactionName»Change''')
			.afterAttributeReplacedAt(attribute.EFeatureToReference as EAttribute)
			.call [
				input [newValue]
				.match [
					retrieveRelevantCorrespondences()
				]
				.action [
					for (mapping : relevantMappings) {
						val corresponding = mapping.correspondingVariableName
						update(corresponding) [
							setFeature(variable(corresponding), mapping.attribute.EFeatureToReference, newValue)
						]
					}
				]
		]
	}

	def private multiAttributeAddReaction() {
		create.reaction('''«attribute.commonalityAttributeReactionName»Insert''')
			.afterAttributeInsertIn(attribute.EFeatureToReference as EAttribute)
			.call [
				input [newValue]
				.match [
					retrieveRelevantCorrespondences()
				]
				.action [
					for (mapping : relevantMappings) {
						val corresponding = mapping.correspondingVariableName
						update(corresponding) [
							addToFeatureList(variable(corresponding), mapping.attribute.EFeatureToReference, newValue)
						]
					}
				]
			]
	}

	def private multiAttributeRemoveReaction() {
		create.reaction('''«attribute.commonalityAttributeReactionName»Remove''')
			.afterAttributeRemoveFrom(attribute.EFeatureToReference as EAttribute)
			.call [
				input [oldValue]
				.match [
					retrieveRelevantCorrespondences()
				]
				.action [
					for (mapping : relevantMappings) {
						val corresponding = mapping.correspondingVariableName
						update(corresponding) [
							removeFromFeatureList(variable(corresponding), mapping.attribute.EFeatureToReference, oldValue)
						]
					}
				]
			]
	}

	def private singleReferenceSetReaction() {
		create.reaction('''«attribute.commonalityAttributeReactionName»Change''')
			.afterElement.replacedAt(attribute.EFeatureToReference as EReference)
			.call [
				input [newValue]
				.match [
					retrieveRelevantCorrespondences()
				]
				.action [
					for (mapping : relevantMappings) {
						val corresponding = mapping.correspondingVariableName
						update(corresponding) [
							setFeature(variable(corresponding), mapping.attribute.EFeatureToReference, newValue)
						]
					}
				]
			]
	}

	def private multiReferenceAddReaction() {
		create.reaction('''«attribute.commonalityAttributeReactionName»ElementInsert''')
			.afterElement.insertedIn(attribute.EFeatureToReference as EReference)
			.call [
				input [newValue]
				.match [
					retrieveRelevantCorrespondences()
				]
				.action [
					for (mapping : relevantMappings) {
						val corresponding = mapping.correspondingVariableName
						update(corresponding) [
							addToFeatureList(variable(corresponding), mapping.attribute.EFeatureToReference, newValue)
						]
					}
				]
			]
	}

	def private multiReferenceRemoveReaction() {
		create.reaction('''«attribute.commonalityAttributeReactionName»ElementRemove''')
			.afterElement.removedFrom(attribute.EFeatureToReference as EReference)
			.call [
				input [oldValue]
				.match [
					retrieveRelevantCorrespondences()
				]
				.action [
					for (mapping : relevantMappings) {
						val corresponding = mapping.correspondingVariableName
						update(corresponding) [
							removeFromFeatureList(variable(corresponding), mapping.attribute.EFeatureToReference, oldValue)
						]
					}
				]
			]
	}

	def private retrieveRelevantCorrespondences(extension UndecidedMatcherStatementBuilder matcherBuilder) {
		for (mapping : relevantMappings) {
			val participationClass = mapping.attribute.participationClass
			vall(participationClass.correspondingVariableName).retrieveAsserted(participationClass.changeClass)
				.correspondingTo.affectedEObject
				.taggedWith(participationClass.correspondenceTag)
		}
	}

	def private correspondingVariableName(CommonalityAttributeMapping mappingSpecification) {
		mappingSpecification.attribute.participationClass.correspondingVariableName
	}
}