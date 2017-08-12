package tools.vitruv.dsls.commonalities.generator

import tools.vitruv.dsls.commonalities.language.elements.Participation
import tools.vitruv.dsls.commonalities.language.AttributeMappingSpecifiation
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import tools.vitruv.dsls.commonalities.language.elements.ParticipationClass
import tools.vitruv.dsls.reactions.api.generator.IReactionsGenerator
import com.google.inject.Inject
import com.google.inject.Provider
import tools.vitruv.framework.domains.VitruvDomainProviderRegistry
import java.util.function.Supplier
import tools.vitruv.dsls.reactions.builder.FluentReactionsLanguageBuilder

package class CommonalityReactionsGenerator extends CommonalityFileGenerator {

	static val DEBUG_WRITE_REACTIONS = true

	Supplier<IReactionsGenerator> reactionsGeneratorProvider
	@Inject FluentReactionsLanguageBuilder create

	@Inject
	new(Provider<IReactionsGenerator> reactionsGeneratorProvider) {
		this.reactionsGeneratorProvider = [
			reactionsGeneratorProvider.get() => [
				useResourceSet(commonalityFile.eResource.resourceSet)
			]
		]
	}

	def private reactionForCommonalityDelete(ParticipationClass participationClass) {
		create.reaction('''«commonality.name»Delete''')
			.afterElement(commonalityFile.changeClass).deleted
			.call [
				match [
					vall("danglingCorrespondence").retrieve(participationClass.changeClass).correspondingTo.affectedEObject
				].action [
					delete("danglingCorrespondence")
				]
		]
	}

	def private reactionForParticipationDelete(ParticipationClass participationClass) {
		create.reaction('''«participationClass.name»Delete''')
			.afterElement(participationClass.changeClass).deleted
			.call [
				match [
					vall("danglingCorrespondence").retrieve(commonalityFile.changeClass).correspondingTo.affectedEObject
				].action [
					delete("danglingCorrespondence")
				]
			]
	}

	def private reactionForCommonalityCreate(ParticipationClass participationClass) {
		create.reaction('''«commonality.name»Create''')
			.afterElement(commonalityFile.changeClass).created
			.call [
				action [
					vall("newCorrespondence").create(participationClass.changeClass)
					addCorrespondenceBetween.affectedEObject.and("newCorrespondence")
				]
		]
	}

	def private reactionsForParticipationCreate(ParticipationClass participationClass) {
		create.reaction('''«participationClass.name»Create''')
			.afterElement(participationClass.changeClass).created
			.call [
				action [
					vall("newCorrespondence").create(commonalityFile.changeClass)
					addCorrespondenceBetween.affectedEObject.and("newCorrespondence")
				]
			]
	}
	
	def private getChangeClass(ParticipationClass participationClass) {
		participationClass.superMetaclass.changeClass
	}

	def private commonalityChangeReactions(Participation participation) {
		participation.classes.flatMap[#[reactionForCommonalityDelete, reactionForCommonalityCreate]]
	}

	def private participationChangeReactions(Participation participation) {
		participation.classes.flatMap[#[reactionForParticipationDelete, reactionsForParticipationCreate]]
	}

	override generate() {
		val generator = reactionsGeneratorProvider.get()

		val reactionFile = create.reactionsFile(commonality.name)
		for (participation : commonalityFile.commonality.participations) {
			reactionFile +=
				create.reactionsSegment('''«commonality.name»To«participation.name»''')
					.inReactionToChangesIn(commonalityFile.conceptDomain)
					.executeActionsIn(participation.domain.vitruvDomain) +=
						commonalityChangeReactions(participation)

			reactionFile +=
				create.reactionsSegment('''«commonality.name»From«participation.name»''')
					.inReactionToChangesIn(participation.domain.vitruvDomain)
					.executeActionsIn(commonalityFile.concept.name) +=
						participationChangeReactions(participation)

		}

		VitruvDomainProviderRegistry.registerDomainProvider(commonalityFile.concept.name,
			commonalityFile.conceptDomain.provider)
		// TODO participation domains
		try {
			generator.addReactionsFile(reactionFile)
			generator.generate(fsa)

			if (DEBUG_WRITE_REACTIONS) {
				generator.writeReactions(fsa)
			}
		} finally {
			VitruvDomainProviderRegistry.unregisterDomainProvider(commonalityFile.concept.name)
		}

	}
}
