package tools.vitruv.dsls.commonalities.generator

import com.google.inject.Inject
import tools.vitruv.dsls.commonalities.language.Commonality
import tools.vitruv.dsls.commonalities.language.CommonalityAttributeMapping
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder
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

	@Inject extension AttributeChangeReactionsHelper attributeChangeReactionsHelper

	val Participation participation
	val Commonality commonality

	private new(Participation participation) {
		checkNotNull(participation, "participation is null")
		this.participation = participation
		this.commonality = participation.containingCommonality
	}

	// Dummy constructor for Guice
	package new() {
		this.participation = null
		this.commonality = null
		throw new IllegalStateException("Use the Factory to create instances of this class!")
	}

	def private getRelevantMappings() {
		return commonality.attributes.flatMap[mappings].filter [
			isRead && it.participation == participation
		]
	}

	def package void generateReactions(FluentReactionsSegmentBuilder segment) {
		segment += relevantMappings.flatMap[reactionsForAttributeMappingRightChange]
	}

	def private reactionsForAttributeMappingRightChange(CommonalityAttributeMapping mapping) {
		return mapping.attribute.getAttributeChangeReactions [ changeType , it |
			call [
				switch (changeType) {
					case VALUE_REPLACED: {
						buildSingleFeatureSetRoutine(mapping)
					}
					case VALUE_INSERTED: {
						buildMultiFeatureAddRoutine(mapping)
					}
					case VALUE_REMOVED: {
						buildMultiFeatureRemoveRoutine(mapping)
					}
				}
			]
		]
	}

	def private buildSingleFeatureSetRoutine(extension RoutineStartBuilder routineBuilder,
		CommonalityAttributeMapping mapping) {
		input [newValue]
		.match [
			retrieveIntermediate(mapping)
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
			retrieveIntermediate(mapping)
		]
		.action [
			update(INTERMEDIATE) [
				addListFeatureValue(variable(INTERMEDIATE), mapping.commonalityEFeature, newValue)
			]
		]
	}

	def private buildMultiFeatureRemoveRoutine(extension RoutineStartBuilder routineBuilder,
		CommonalityAttributeMapping mapping) {
		input [oldValue]
		.match [
			retrieveIntermediate(mapping)
		]
		.action [
			update(INTERMEDIATE) [
				removeListFeatureValue(variable(INTERMEDIATE), mapping.commonalityEFeature, oldValue)
			]
		]
	}

	def private retrieveIntermediate(extension UndecidedMatcherStatementBuilder builder,
		CommonalityAttributeMapping mapping) {
		// assert: mapping.participation == participation && mapping.containingCommonality == commonality
		val participationClass = mapping.attribute.participationClass
		vall(INTERMEDIATE).retrieve(commonality.changeClass)
			.correspondingTo.affectedEObject
			.taggedWith(participationClass.correspondenceTag)
	}
}
