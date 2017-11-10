package tools.vitruv.dsls.commonalities.generator

import java.util.Collections
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EReference
import tools.vitruv.dsls.commonalities.language.CommonalityAttributeMapping
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.elements.EClassAdapter
import tools.vitruv.dsls.commonalities.language.elements.EDataTypeAdapter
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

import static com.google.common.base.Preconditions.*

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import static extension tools.vitruv.dsls.commonalities.generator.EmfAccessExpressions.*
import static extension tools.vitruv.dsls.commonalities.generator.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class ParticipationAttributeChangeReactionsBuilder 
	extends ReactionsSubGenerator<ParticipationAttributeChangeReactionsBuilder> {
	Participation targetParticipation

	def package forParticipation(Participation targetParticipation) {
		this.targetParticipation = targetParticipation
		this
	}

	def package Iterable<FluentReactionBuilder> getReactions() {
		checkState(targetParticipation !== null, "No participation to create reactions for was set!")
		checkState(generationContext !== null, "No generation context was set!")

		commonality.attributes.flatMap[mappings].filter [
			isRead && attribute.participationClass.participation == targetParticipation
		].flatMap[reactionsForAttributeMappingRightChange]
	}

	def private reactionsForAttributeMappingRightChange(CommonalityAttributeMapping mapping) {
		val attributeType = mapping.declaringAttribute.type
		switch (attributeType) {
			EDataTypeAdapter case !mapping.declaringAttribute.isMultiValued:
				Collections.singleton(singleAttributeSetReaction(mapping))
				
			EDataTypeAdapter case mapping.declaringAttribute.isMultiValued:
				#[multiAttributeAddReaction(mapping), multiAttributeRemoveReaction(mapping)]
				
			EClassAdapter case !mapping.attribute.isMultiValued:
				Collections.singleton(singleReferenceSetReaction(mapping))
				
			EClassAdapter case mapping.attribute.isMultiValued:
				#[multiReferenceAddReaction(mapping), multiReferenceRemoveReaction(mapping)]
		}
	}

	def private singleAttributeSetReaction(CommonalityAttributeMapping mapping) {
		create.reaction('''«mapping.participationAttributeReactionName»Change''')
			.afterAttributeReplacedAt(mapping.participationAttributeChangeClass, mapping.participationEAttribute)
			.call [
				input [newValue]
				.match [
					retrieveIntermediate()
				]
				.action [
					update('intermediate') [
						eSetFeature(variable('intermediate'), mapping.declaringAttribute.name, newValue)
					]
				]
			]
	}

	def private multiAttributeAddReaction(CommonalityAttributeMapping mapping) {
		create.reaction('''«mapping.participationAttributeReactionName»Insert''')
			.afterAttributeInsertIn(mapping.participationAttributeChangeClass, mapping.participationEAttribute)
			.call [
				input [newValue].match [
					retrieveIntermediate()
				].action [
					update('intermediate') [
						eAddToFeatureList(variable('intermediate'), mapping.declaringAttribute.name, newValue)
					]
				]
			]
	}

	def private multiAttributeRemoveReaction(CommonalityAttributeMapping mapping) {
		create.reaction('''«mapping.participationAttributeReactionName»Remove''')
			.afterAttributeRemoveFrom(mapping.participationAttributeChangeClass, mapping.participationEAttribute)
			.call [
				input [oldValue]
				.match [
					retrieveIntermediate()
				]
				.action [
					update('intermediate') [
						eRemoveFromFeatureList(variable('intermediate'), mapping.declaringAttribute.name, oldValue)
					]
				]
			]
	}

	def private singleReferenceSetReaction(CommonalityAttributeMapping mapping) {
		create.reaction('''«mapping.participationAttributeReactionName»Change''')
			.afterElement.replacedAt(mapping.participationAttributeChangeClass, mapping.participationEReference)
			.call [
				input [newValue]
				.match [
					retrieveIntermediate()
				]
				.action [
					update('intermediate') [
						eSetFeature(variable('intermediate'), mapping.declaringAttribute.name, newValue)
					]
				]
			]
	}

	def private multiReferenceAddReaction(CommonalityAttributeMapping mapping) {
		create.reaction('''«mapping.participation.name»«mapping.attribute.name.toFirstUpper»Insert''')
			.afterElement.insertedIn(mapping.participationAttributeChangeClass, mapping.participationEReference)
			.call [
				input [newValue]
				.match [
					retrieveIntermediate()
				]
				.action [
					update('intermediate') [
						eAddToFeatureList(variable('intermediate'), mapping.declaringAttribute.name, newValue)
					]
				]
			]
	}

	def private multiReferenceRemoveReaction(CommonalityAttributeMapping mapping) {
		create.reaction('''«mapping.participationAttributeReactionName»Remove''').
			afterElement.removedFrom(mapping.participationAttributeChangeClass, mapping.participationEReference)
			.call [
				input [oldValue]
				.match [
					retrieveIntermediate()
				]
				.action [
					update('intermediate') [
						eRemoveFromFeatureList(variable('intermediate'), mapping.declaringAttribute.name, oldValue)
					]
				]
			]
	}
	
	def private getParticipationAttributeChangeClass(CommonalityAttributeMapping mapping) {
		mapping.attribute.participationClass.changeClass
	}
	
	def private getParticipationEReference(CommonalityAttributeMapping mapping) {
		mapping.attribute.EFeatureToReference as EReference
	}
	
	def private getParticipationEAttribute(CommonalityAttributeMapping mapping) {
		mapping.attribute.EFeatureToReference as EAttribute
	}

	def private retrieveIntermediate(extension UndecidedMatcherStatementBuilder builder) {
		vall('intermediate').retrieveAsserted(commonalityFile.changeClass).correspondingTo.affectedEObject
	}
}
