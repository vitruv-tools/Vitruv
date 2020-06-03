package tools.vitruv.dsls.commonalities.generator.reactions

import com.google.inject.Inject
import com.google.inject.Provider
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.resource.IGlobalServiceProvider
import tools.vitruv.dsls.commonalities.generator.CommonalitiesGenerationSettings
import tools.vitruv.dsls.commonalities.generator.SubGenerator
import tools.vitruv.dsls.commonalities.generator.reactions.attribute.CommonalityAttributeChangeReactionsBuilder
import tools.vitruv.dsls.commonalities.generator.reactions.attribute.ParticipationAttributeChangeReactionsBuilder
import tools.vitruv.dsls.commonalities.generator.reactions.intermediatemodel.CommonalityInsertReactionsBuilder
import tools.vitruv.dsls.commonalities.generator.reactions.matching.MatchParticipationReactionsBuilder
import tools.vitruv.dsls.commonalities.generator.reactions.matching.MatchParticipationReferencesReactionsBuilder
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.reactions.api.generator.IReactionsGenerator
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder
import tools.vitruv.framework.domains.VitruvDomainProviderRegistry

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

class ReactionsGenerator extends SubGenerator {

	private static val Logger logger = Logger.getLogger(ReactionsGenerator)

	@Inject IGlobalServiceProvider globalServiceProvider
	@Inject Provider<IReactionsGenerator> reactionsGeneratorProvider

	@Inject CommonalitiesGenerationSettings generationSettings
	@Inject extension ReactionsGenerationContext reactionsGenerationContext

	@Inject CommonalityInsertReactionsBuilder.Factory commonalityInsertReactionsBuilder
	@Inject CommonalityAttributeChangeReactionsBuilder.Factory commonalityAttributeChangeReactionsBuilder

	@Inject MatchParticipationReactionsBuilder.Factory matchParticipationReactionsBuilder
	@Inject MatchParticipationReferencesReactionsBuilder.Factory matchParticipationReferencesReactionsBuilder
	@Inject ParticipationAttributeChangeReactionsBuilder.Factory participationAttributeChangeReactionsBuilder

	override generate() {
		logger.debug('''Generating reactions for commonality «commonality»''')
		if (commonality.participations.length + commonality.allMembers.length == 0) {
			// nothing to generate
			logger.debug("  Ignoring empty commonality")
			return;
		}

		// This has the side-effect of initializing the Reactions language's injector if that hasn't happened yet:
		globalServiceProvider.findService(URI.createFileURI('fake.reactions'), IReactionsGenerator)

		val reactionsGenerator = reactionsGeneratorProvider.get() => [
			useResourceSet(resourceSet)
		]

		val reactionFile = create.reactionsFile(commonality.name)
		for (participation : commonalityFile.commonality.participations) {
			val participationToCommonalitySegment = create
				.reactionsSegment('''«commonality.name»From«participation.name»''')
				.inReactionToChangesIn(participation.domain.vitruvDomain)
				.executeActionsIn(commonalityFile.concept.name)
			participation.generateParticipationChangeReactions(participationToCommonalitySegment)
			reactionFile += participationToCommonalitySegment

			val commonalityToParticipationSegment = create
				.reactionsSegment('''«commonality.name»To«participation.name»''')
				.inReactionToChangesIn(commonalityFile.concept.vitruvDomain)
				.executeActionsIn(participation.domain.vitruvDomain)
			participation.generateCommonalityChangeReactions(commonalityToParticipationSegment)
			reactionFile += commonalityToParticipationSegment
		}

		logger.debug('''Temporarily registering concept domains: «generatedConcepts»''')
		for (String conceptName : generatedConcepts) {
			VitruvDomainProviderRegistry.registerDomainProvider(conceptName, conceptName.vitruvDomain.provider)
		}

		try {
			reactionsGenerator.addReactionsFile(reactionFile)
			reactionsGenerator.generate(fsa)

			if (generationSettings.createReactionFiles) {
				reactionsGenerator.writeReactions(fsa)
			}
		} finally {
			logger.debug('''Unregistering concept domains again: «generatedConcepts»''')
			for (String conceptName : generatedConcepts) {
				VitruvDomainProviderRegistry.unregisterDomainProvider(conceptName)
			}
		}
	}

	private def void generateCommonalityChangeReactions(Participation participation,
		FluentReactionsSegmentBuilder segment) {
		participation.generateCommonalityInsertReactions(segment)
		participation.generateReactionsForCommonalityAttributeChange(segment)
	}

	private def void generateParticipationChangeReactions(Participation participation,
		FluentReactionsSegmentBuilder segment) {
		participation.generateMatchParticipationReactions(segment)
		participation.generateMatchParticipationReferencesReactions(segment)
		participation.generateReactionsForParticipationAttributeChange(segment)
	}

	private def void generateCommonalityInsertReactions(Participation targetParticipation,
		FluentReactionsSegmentBuilder segment) {
		commonalityInsertReactionsBuilder.createFor(targetParticipation).generateReactions(segment)
	}

	private def void generateReactionsForCommonalityAttributeChange(Participation targetParticipation,
		FluentReactionsSegmentBuilder segment) {
		commonality.attributes.forEach [ attribute |
			commonalityAttributeChangeReactionsBuilder.createFor(attribute, targetParticipation)
				.generateReactions(segment)
		]
	}

	private def void generateMatchParticipationReactions(Participation participation,
		FluentReactionsSegmentBuilder segment) {
		matchParticipationReactionsBuilder.createFor(participation).generateReactions(segment)
	}

	private def void generateMatchParticipationReferencesReactions(Participation participation,
		FluentReactionsSegmentBuilder segment) {
		matchParticipationReferencesReactionsBuilder.createFor(participation).generateReactions(segment)
	}

	private def void generateReactionsForParticipationAttributeChange(Participation participation,
		FluentReactionsSegmentBuilder segment) {
		participationAttributeChangeReactionsBuilder.createFor(participation).generateReactions(segment)
	}
}
