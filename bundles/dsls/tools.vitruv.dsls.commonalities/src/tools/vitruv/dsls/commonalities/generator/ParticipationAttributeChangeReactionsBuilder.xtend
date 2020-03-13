package tools.vitruv.dsls.commonalities.generator

import java.util.Collections
import tools.vitruv.dsls.commonalities.language.CommonalityAttributeMapping
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.elements.EClassAdapter
import tools.vitruv.dsls.commonalities.language.elements.EDataTypeAdapter
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineStartBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.dsls.commonalities.generator.EmfAccessExpressions.*
import static extension tools.vitruv.dsls.commonalities.generator.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class ParticipationAttributeChangeReactionsBuilder extends ReactionsSubGenerator {

	static class Factory extends InjectingFactoryBase {
		def createFor(Participation participation) {
			return new ParticipationAttributeChangeReactionsBuilder(participation).injectMembers
		}
	}

	val Participation participation

	private new(Participation participation) {
		checkNotNull(participation, "participation is null")
		this.participation = participation
	}

	// Dummy constructor for Guice
	package new() {
		this.participation = null
		throw new IllegalStateException("Use the Factory to create instances of this class!")
	}

	def package Iterable<FluentReactionBuilder> getReactions() {
		return commonality.attributes.flatMap[mappings].filter [
			isRead && it.participation == participation
		].flatMap[reactionsForAttributeMappingRightChange]
	}

	def private reactionsForAttributeMappingRightChange(CommonalityAttributeMapping mapping) {
		switch (mapping.attribute.type) {
			EDataTypeAdapter case !mapping.attribute.isMultiValued:
				Collections.singleton(singleAttributeSetReaction(mapping))

			EDataTypeAdapter case mapping.attribute.isMultiValued:
				#[multiAttributeAddReaction(mapping), multiAttributeRemoveReaction(mapping)]

			EClassAdapter case !mapping.attribute.isMultiValued:
				Collections.singleton(singleReferenceSetReaction(mapping))

			EClassAdapter case mapping.attribute.isMultiValued:
				#[multiReferenceAddReaction(mapping), multiReferenceRemoveReaction(mapping)]
		}
	}

	def private singleAttributeSetReaction(CommonalityAttributeMapping mapping) {
		create.reaction('''«mapping.participationAttributeReactionName»Change''')
			.afterAttributeReplacedAt(mapping.participationChangeClass, mapping.participationEAttribute)
			.call [
				buildSingleFeatureSetRoutine(mapping)
			]
	}

	def private multiAttributeAddReaction(CommonalityAttributeMapping mapping) {
		create.reaction('''«mapping.participationAttributeReactionName»Insert''')
			.afterAttributeInsertIn(mapping.participationChangeClass, mapping.participationEAttribute)
			.call [
				buildMultiFeatureAddRoutine(mapping)
			]
	}

	def private multiAttributeRemoveReaction(CommonalityAttributeMapping mapping) {
		create.reaction('''«mapping.participationAttributeReactionName»Remove''')
			.afterAttributeRemoveFrom(mapping.participationChangeClass, mapping.participationEAttribute)
			.call [
				buildMultiFeatureRemoveRoutine(mapping)
			]
	}

	def private singleReferenceSetReaction(CommonalityAttributeMapping mapping) {
		create.reaction('''«mapping.participationAttributeReactionName»Change''')
			.afterElement.replacedAt(mapping.participationChangeClass, mapping.participationEReference)
			.call [
				buildSingleFeatureSetRoutine(mapping)
			]
	}

	def private multiReferenceAddReaction(CommonalityAttributeMapping mapping) {
		create.reaction('''«mapping.participation.name»«mapping.attribute.name.toFirstUpper»Insert''')
			.afterElement.insertedIn(mapping.participationChangeClass, mapping.participationEReference)
			.call [
				buildMultiFeatureAddRoutine(mapping)
			]
	}

	def private multiReferenceRemoveReaction(CommonalityAttributeMapping mapping) {
		create.reaction('''«mapping.participationAttributeReactionName»Remove''').
			afterElement.removedFrom(mapping.participationChangeClass, mapping.participationEReference)
			.call [
				buildMultiFeatureRemoveRoutine(mapping)
			]
	}

	def private buildSingleFeatureSetRoutine(extension RoutineStartBuilder routineBuilder,
		CommonalityAttributeMapping mapping) {
		input [newValue]
		.match [
			retrieveIntermediate()
		]
		.action [
			update(INTERMEDIATE) [
				setFeatureValue(variable(INTERMEDIATE), mapping.commonalityEFeature, newValue)
			]
		]
	}

	def private buildMultiFeatureAddRoutine(extension RoutineStartBuilder routineBuilder,
		CommonalityAttributeMapping mapping) {
		input [newValue]
		.match [
			retrieveIntermediate()
		]
		.action [
			update(INTERMEDIATE) [
				addToListFeatureValue(variable(INTERMEDIATE), mapping.commonalityEFeature, newValue)
			]
		]
	}

	def private buildMultiFeatureRemoveRoutine(extension RoutineStartBuilder routineBuilder,
		CommonalityAttributeMapping mapping) {
		input [oldValue]
		.match [
			retrieveIntermediate()
		]
		.action [
			update(INTERMEDIATE) [
				removeFromListFeatureValue(variable(INTERMEDIATE), mapping.commonalityEFeature, oldValue)
			]
		]
	}

	def private retrieveIntermediate(extension UndecidedMatcherStatementBuilder builder) {
		vall(INTERMEDIATE).retrieveAsserted(commonality.changeClass).correspondingTo.affectedEObject
	}
}
