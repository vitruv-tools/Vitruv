package tools.vitruv.dsls.commonalities.generator

import java.util.List
import org.eclipse.emf.ecore.EReference
import tools.vitruv.dsls.commonalities.language.CommonalityReference
import tools.vitruv.dsls.commonalities.language.CommonalityReferenceMapping
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.dsls.commonalities.generator.EmfAccessExpressions.*
import static extension tools.vitruv.dsls.commonalities.generator.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class CommonalityReferenceChangeReactionsBuilder extends ReactionsSubGenerator {

	static class Factory extends InjectingFactoryBase {
		def createFor(CommonalityReference reference, Participation targetParticipation) {
			return new CommonalityReferenceChangeReactionsBuilder(reference, targetParticipation).injectMembers
		}
	}

	val CommonalityReference reference
	val Participation targetParticipation
	List<CommonalityReferenceMapping> relevantMappings

	private new(CommonalityReference reference, Participation targetParticipation) {
		checkNotNull(reference, "reference is null")
		checkNotNull(targetParticipation, "targetParticipation is null")
		this.reference = reference
		this.targetParticipation = targetParticipation
	}

	// Dummy constructor for Guice
	package new() {
		this.reference = null
		this.targetParticipation = null
		throw new IllegalStateException("Use the Factory to create instances of this class!")
	}

	def package void generateReactions(FluentReactionsSegmentBuilder segment) {
		relevantMappings = reference.mappings.filter [
			isWrite && participation == targetParticipation
		].toList
		if (relevantMappings.size === 0) return;

		if (reference.isMultiValued) {
			segment += #[multiReferenceAddReaction, multiReferenceRemoveReaction]
		} else {
			segment += singleReferenceSetReaction
		}
	}

	def private singleReferenceSetReaction() {
		create.reaction('''«reference.commonalityAttributeReactionName»Change''')
			.afterElement.replacedAt(reference.correspondingEFeature as EReference)
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
							setFeatureValue(
								variable(correspondingElement),
								mapping.participationEFeature,
								variable(correspondingValue)
							)
						]
					}
				]
			]
	}

	def private multiReferenceAddReaction() {
		create.reaction('''«reference.commonalityAttributeReactionName»Insert''')
			.afterElement.insertedIn(reference.correspondingEFeature as EReference)
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
							addToListFeatureValue(
								variable(correspondingElement),
								mapping.participationEFeature,
								variable(correspondingValue)
							)
						]
					}
				]
			]
	}

	def private multiReferenceRemoveReaction() {
		create.reaction('''«reference.commonalityAttributeReactionName»Remove''')
			.afterElement.removedFrom(reference.correspondingEFeature as EReference)
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
							removeFromListFeatureValue(
								variable(correspondingElement),
								mapping.participationEFeature,
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
