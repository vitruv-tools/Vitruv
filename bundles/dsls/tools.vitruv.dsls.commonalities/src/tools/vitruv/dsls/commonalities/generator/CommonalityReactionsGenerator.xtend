package tools.vitruv.dsls.commonalities.generator

import static tools.vitruv.dsls.reactions.builder.FluentReactionBuilder.*
import static tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder.*
import static tools.vitruv.dsls.reactions.builder.FluentReactionsFileBuilder.*
import tools.vitruv.dsls.commonalities.language.elements.Participation
import java.util.HashMap
import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import tools.vitruv.dsls.reactions.api.generator.ReactionBuilderFactory
import tools.vitruv.dsls.commonalities.language.AttributeMappingSpecifiation
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import tools.vitruv.dsls.commonalities.language.elements.ParticipationClass
import tools.vitruv.dsls.reactions.api.generator.IReactionsGenerator
import com.google.inject.Inject
import com.google.inject.Provider
import java.nio.file.Paths
import java.nio.file.Files

package class CommonalityReactionsGenerator extends CommonalityFileGenerator {

	static val DEBUG_WRITE_REACTIONS = true
	ReactionBuilderFactory reactionBuilderProvider = new ReactionBuilderFactory()

	val participationReactions = new HashMap<Participation, Reaction>
	
	@Inject Provider<IReactionsGenerator> reactionsGeneratorProvider

	// @Inject IReactionsEnvironmentGenerator reactionsEnvironmentGenerator;
	def private reaction(Participation participation) {
	}

	def private reactionForCommonalityDelete(ParticipationClass participationClass) {
		reaction('''«participationClass.name»Delete''')
			.afterElement(participationClass.superMetaclass.changeClass).deleted
			.call [
				match [
					vall("toDelete").retrieve(commonality.changeClass).correspondingTo.oldValue()
				].action [
					delete("toDelete")
				]
			]
	}

	def private reactionForParticipationDelete(ParticipationClass participationClass) {
		reaction('''«commonality.name»Delete''')
			.afterElement(commonality.changeClass).deleted
			.call [
				match [
					vall("toDelete").retrieve(participationClass.superMetaclass.changeClass).correspondingTo.oldValue()
				].action [
					delete("toDelete")
				]
		]	
	}

	def private reactionForCommonalityCreate(ParticipationClass participationClass) {
		reaction('''«participationClass.name»Create''')
			.afterElement(participationClass.superMetaclass.changeClass).deleted
			.call [
				match [
					vall("toDelete").retrieve(commonality.changeClass).correspondingTo.oldValue()
				].action [
					delete("toDelete")
					addCorrespondenceBetween.oldValue.and("toDelete")
				]
			]
	}

	def private reactionsForParticipationCreate(ParticipationClass participationClass) {
		reaction('''«commonality.name»Create''')
			.afterElement(commonality.changeClass).deleted
			.call [
				action [
					vall("created").create(commonality.changeClass)
					addCorrespondenceBetween.newValue.and("created")
				]
			]	
	}

	def private attributeReactions(AttributeMappingSpecifiation mappingSpecification) {

	}
	
	def private commonalityChangeReactions(Participation participation) {
		participation.classes.flatMap [#[reactionForCommonalityDelete, reactionForCommonalityCreate]]
	}
	
	def private participationChangeReactions(Participation participation) {
		participation.classes.flatMap [#[reactionForParticipationDelete, reactionsForParticipationCreate]]
	}

	override generate() {
		val generator = reactionsGeneratorProvider.get()
		
		val reactionFile = reactionFile(commonality.name)
		for (participation : commonalityFile.commonality.participations) {
			reactionFile += 
				reactionsSegment('''«commonality.name»To«participation.name»''')
					.inReactionToChangesIn(commonality.name) // TODO
					.executeActionsIn(participation.domain.vitruvDomain)
						+= commonalityChangeReactions(participation)
					
			reactionFile +=
				reactionsSegment('''«commonality.name»From«participation.name»''')
					.inReactionToChangesIn(participation.domain.vitruvDomain)
					.executeActionsIn(commonality.name) // TODO
						+= participationChangeReactions(participation)
				
		}
	
		generator.addReaction(reactionFile)
		
		if (DEBUG_WRITE_REACTIONS) {
			val outputUri = fsa.getURI('reactions')
			generator.writeReactionsTo(outputUri)		
		}
	}
}
