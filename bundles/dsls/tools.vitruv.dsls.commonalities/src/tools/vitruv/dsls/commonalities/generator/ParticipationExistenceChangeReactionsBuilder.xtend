package tools.vitruv.dsls.commonalities.generator

import tools.vitruv.dsls.commonalities.language.Commonality
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.dsls.commonalities.generator.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.generator.ReactionsHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class ParticipationExistenceChangeReactionsBuilder extends ReactionsSubGenerator {

	static class Factory extends InjectingFactoryBase {
		def createFor(Participation participation) {
			return new ParticipationExistenceChangeReactionsBuilder(participation).injectMembers
		}
	}

	@Inject extension ResourceBridgeHelper resourceBridgeHelper

	// note: may be a commonality participation
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

	}

	def package void generateReactions(FluentReactionsSegmentBuilder segment) {

		participation.classes
			.filter[!isForResource]
			.forEach [
				segment += reactionForParticipationClassCreate
				segment += reactionForParticipationClassDelete
				segment += reactionForParticipationRootInsert
			]
	}

	def private reactionForParticipationClassCreate(ParticipationClass participationClass) {
		create.reaction('''«participationClass.participation.name»_«participationClass.name»Create''')
			.afterElement(participationClass.changeClass).created
			.call [
				match [
					requireAbsenceOf(commonality.changeClass).correspondingTo.affectedEObject
						.taggedWith(participationClass.correspondenceTag)
				]
				.action [
					vall(INTERMEDIATE).create(commonality.changeClass).andInitialize [
						assignStagingId(variable(INTERMEDIATE))
					]
					addCorrespondenceBetween(INTERMEDIATE).and.affectedEObject
						.taggedWith(participationClass.correspondenceTag)
				]
			]
	}

	def private reactionForParticipationClassDelete(ParticipationClass participationClass) {
		create.reaction('''«participationClass.participation.name»_«participationClass.name»Delete''')
			.afterElement(participationClass.changeClass).deleted
			.call [
				match [
					vall(INTERMEDIATE).retrieveAsserted(commonality.changeClass).correspondingTo.affectedEObject
				]
				action [
					delete(INTERMEDIATE)
				]
			]
	}

	def private reactionForParticipationRootInsert(ParticipationClass participationClass) {
		create.reaction('''«participationClass.participation.name»_«participationClass.name»RootInsert''')
			// note: may be a commonality participation
			.afterElementInsertedAsRoot(participationClass.changeClass)
			.call(#[
				participation.insertResourceBridgeRoutine,
				getInsertRoutine(participationClass.participation, commonality)
			].filterNull)
	}
}
