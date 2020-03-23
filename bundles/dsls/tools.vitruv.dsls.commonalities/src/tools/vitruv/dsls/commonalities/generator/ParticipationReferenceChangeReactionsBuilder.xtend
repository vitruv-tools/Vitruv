package tools.vitruv.dsls.commonalities.generator

import tools.vitruv.dsls.commonalities.language.Commonality
import tools.vitruv.dsls.commonalities.language.CommonalityReferenceMapping
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder
import tools.vitruv.dsls.reactions.builder.TypeProvider

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.dsls.commonalities.generator.EmfAccessExpressions.*
import static extension tools.vitruv.dsls.commonalities.generator.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class ParticipationReferenceChangeReactionsBuilder extends ReactionsSubGenerator {

	static class Factory extends InjectingFactoryBase {
		def createFor(Participation participation) {
			return new ParticipationReferenceChangeReactionsBuilder(participation).injectMembers
		}
	}

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

	def package void generateReactions(FluentReactionsSegmentBuilder segment) {
		segment += commonality.references.flatMap[mappings].filter [
			isRead && it.participation == participation
		].flatMap[reactionsForReferenceMappingRightChange]
	}

	def private reactionsForReferenceMappingRightChange(CommonalityReferenceMapping mapping) {
		if (mapping.reference.isMultiValued) {
			#[multiReferenceAddReaction(mapping), multiReferenceRemoveReaction(mapping)]
		} else {
			#[singleReferenceSetReaction(mapping)]
		}
	}

	def private singleReferenceSetReaction(CommonalityReferenceMapping mapping) {
		create.reaction('''«mapping.participationAttributeReactionName»Change''')
			.afterElement.replacedAt(mapping.participationChangeClass, mapping.participationEReference)
			.call [
				match [
					retrieveIntermediate()
					retrieveReferencedIntermediateOf(mapping).correspondingTo.newValue
				]
				.action [
					update(INTERMEDIATE) [
						setFeatureValue(variable(INTERMEDIATE), mapping.commonalityEFeature, referencedIntermediate)
					]
				]
			] => [
				if (mapping.participationEReference.isContainment) {
					call(getInsertRoutine(participation, mapping.referencedCommonality))
				}
			]
	}

	def private multiReferenceAddReaction(CommonalityReferenceMapping mapping) {
		create.reaction('''«mapping.participationAttributeReactionName»Insert''')
			.afterElement.insertedIn(mapping.participationChangeClass, mapping.participationEReference)
			.call [
				match [
					retrieveIntermediate()
					retrieveReferencedIntermediateOf(mapping).correspondingTo.newValue
				]
				.action [
					update(INTERMEDIATE) [
						addToListFeatureValue(variable(INTERMEDIATE), mapping.commonalityEFeature, referencedIntermediate)
					]
				]
			] => [
				if (mapping.participationEReference.isContainment) {
					call(getInsertRoutine(participation, mapping.referencedCommonality))
				}
			]
	}

	def private multiReferenceRemoveReaction(CommonalityReferenceMapping mapping) {
		create.reaction('''«mapping.participationAttributeReactionName»Remove''').
			afterElement.removedFrom(mapping.participationChangeClass, mapping.participationEReference)
			.call [
				match [
					retrieveIntermediate()
					retrieveReferencedIntermediateOf(mapping).correspondingTo.oldValue
				]
				.action [
					update(INTERMEDIATE) [
						removeFromListFeatureValue(variable(INTERMEDIATE), mapping.commonalityEFeature, referencedIntermediate)
					]
				]
			]
	}

	def private retrieveReferencedIntermediateOf(extension UndecidedMatcherStatementBuilder statementBuilder,
		CommonalityReferenceMapping mapping) {
		vall(REFERENCED_INTERMEDIATE).retrieveAsserted(mapping.referencedCommonality.changeClass)
	}

	def private getReferencedIntermediate(extension TypeProvider builder) {
		variable(REFERENCED_INTERMEDIATE)
	}

	def private retrieveIntermediate(extension UndecidedMatcherStatementBuilder builder) {
		vall(INTERMEDIATE).retrieveAsserted(commonality.changeClass).correspondingTo.affectedEObject
	}
}
