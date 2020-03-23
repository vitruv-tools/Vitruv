package tools.vitruv.dsls.commonalities.generator

import java.util.List
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EReference
import tools.vitruv.dsls.commonalities.language.CommonalityAttribute
import tools.vitruv.dsls.commonalities.language.CommonalityAttributeMapping
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.elements.EClassAdapter
import tools.vitruv.dsls.commonalities.language.elements.EDataTypeAdapter
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.dsls.commonalities.generator.EmfAccessExpressions.*
import static extension tools.vitruv.dsls.commonalities.generator.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class CommonalityAttributeChangeReactionsBuilder extends ReactionsSubGenerator {

	static class Factory extends InjectingFactoryBase {
		def createFor(CommonalityAttribute attribute, Participation targetParticipation) {
			return new CommonalityAttributeChangeReactionsBuilder(attribute, targetParticipation).injectMembers
		}
	}

	val CommonalityAttribute attribute
	val Participation targetParticipation
	List<CommonalityAttributeMapping> relevantMappings

	private new(CommonalityAttribute attribute, Participation targetParticipation) {
		checkNotNull(attribute, "attribute is null")
		checkNotNull(targetParticipation, "targetParticipation is null")
		this.attribute = attribute
		this.targetParticipation = targetParticipation
	}

	// Dummy constructor for Guice
	package new() {
		this.attribute = null
		this.targetParticipation = null
		throw new IllegalStateException("Use the Factory to create instances of this class!")
	}

	def package void generateReactions(FluentReactionsSegmentBuilder segment) {
		relevantMappings = attribute.mappings.filter [
			isWrite && participation == targetParticipation
		].toList
		if (relevantMappings.size === 0) return;

		switch attribute.type {
			EDataTypeAdapter case !attribute.isMultiValued:
				segment += singleAttributeSetReaction
			EDataTypeAdapter case attribute.isMultiValued:
				segment += #[multiAttributeAddReaction, multiAttributeRemoveReaction]
			EClassAdapter case !attribute.isMultiValued:
				segment += singleReferenceSetReaction
			EClassAdapter case attribute.isMultiValued:
				segment += #[multiReferenceAddReaction, multiReferenceRemoveReaction]
		}
	}

	def private singleAttributeSetReaction() {
		create.reaction('''«attribute.commonalityAttributeReactionName»Change''')
			.afterAttributeReplacedAt(attribute.correspondingEFeature as EAttribute)
			.call [
				input [newValue]
				.match [
					retrieveRelevantCorrespondences()
				]
				.action [
					for (mapping : relevantMappings) {
						val corresponding = mapping.correspondingVariableName
						update(corresponding) [
							setFeatureValue(variable(corresponding), mapping.participationEFeature, newValue)
						]
					}
				]
		]
	}

	def private multiAttributeAddReaction() {
		create.reaction('''«attribute.commonalityAttributeReactionName»Insert''')
			.afterAttributeInsertIn(attribute.correspondingEFeature as EAttribute)
			.call [
				input [newValue]
				.match [
					retrieveRelevantCorrespondences()
				]
				.action [
					for (mapping : relevantMappings) {
						val corresponding = mapping.correspondingVariableName
						update(corresponding) [
							addToListFeatureValue(variable(corresponding), mapping.participationEFeature, newValue)
						]
					}
				]
			]
	}

	def private multiAttributeRemoveReaction() {
		create.reaction('''«attribute.commonalityAttributeReactionName»Remove''')
			.afterAttributeRemoveFrom(attribute.correspondingEFeature as EAttribute)
			.call [
				input [oldValue]
				.match [
					retrieveRelevantCorrespondences()
				]
				.action [
					for (mapping : relevantMappings) {
						val corresponding = mapping.correspondingVariableName
						update(corresponding) [
							removeFromListFeatureValue(variable(corresponding), mapping.participationEFeature, oldValue)
						]
					}
				]
			]
	}

	def private singleReferenceSetReaction() {
		create.reaction('''«attribute.commonalityAttributeReactionName»Change''')
			.afterElement.replacedAt(attribute.correspondingEFeature as EReference)
			.call [
				input [newValue]
				.match [
					retrieveRelevantCorrespondences()
				]
				.action [
					for (mapping : relevantMappings) {
						val corresponding = mapping.correspondingVariableName
						update(corresponding) [
							setFeatureValue(variable(corresponding), mapping.participationEFeature, newValue)
						]
					}
				]
			]
	}

	def private multiReferenceAddReaction() {
		create.reaction('''«attribute.commonalityAttributeReactionName»ElementInsert''')
			.afterElement.insertedIn(attribute.correspondingEFeature as EReference)
			.call [
				input [newValue]
				.match [
					retrieveRelevantCorrespondences()
				]
				.action [
					for (mapping : relevantMappings) {
						val corresponding = mapping.correspondingVariableName
						update(corresponding) [
							addToListFeatureValue(variable(corresponding), mapping.participationEFeature, newValue)
						]
					}
				]
			]
	}

	def private multiReferenceRemoveReaction() {
		create.reaction('''«attribute.commonalityAttributeReactionName»ElementRemove''')
			.afterElement.removedFrom(attribute.correspondingEFeature as EReference)
			.call [
				input [oldValue]
				.match [
					retrieveRelevantCorrespondences()
				]
				.action [
					for (mapping : relevantMappings) {
						val corresponding = mapping.correspondingVariableName
						update(corresponding) [
							removeFromListFeatureValue(variable(corresponding), mapping.participationEFeature, oldValue)
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
