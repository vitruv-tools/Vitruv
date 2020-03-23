package tools.vitruv.dsls.commonalities.generator

import com.google.inject.Inject
import com.google.inject.Provider
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.resource.IGlobalServiceProvider
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.reactions.api.generator.IReactionsGenerator
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder
import tools.vitruv.framework.domains.VitruvDomainProviderRegistry

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class ReactionsGenerator extends SubGenerator {

	private static val Logger logger = Logger.getLogger(ReactionsGenerator)

	@Inject IGlobalServiceProvider globalServiceProvider
	@Inject Provider<IReactionsGenerator> reactionsGeneratorProvider

	@Inject CommonalitiesGenerationSettings generationSettings
	@Inject extension ReactionsGenerationContext reactionsGenerationContext

	@Inject CommonalityExistenceChangeReactionsBuilder.Factory commonalityExistenceChangeReactionsBuilder
	@Inject CommonalityAttributeChangeReactionsBuilder.Factory commonalityAttributeChangeReactionsBuilder
	@Inject CommonalityReferenceChangeReactionsBuilder.Factory commonalityReferenceChangeReactionsBuilder

	@Inject ParticipationExistenceChangeReactionsBuilder.Factory participationExistenceChangeReactionsBuilder
	@Inject ParticipationAttributeChangeReactionsBuilder.Factory participationAttributeChangeReactionsBuilder
	@Inject ParticipationReferenceChangeReactionsBuilder.Factory participationReferenceChangeReactionsBuilder

	override generate() {
		logger.debug('''Generating reactions for commonality «commonalityFile.concept.name»::«commonality.name»''')
		if (commonality.participations.length + commonality.allMembers.length == 0) {
			// nothing to generate
			logger.debug("  Ignoring empty commonality")
			return;
		}

		// This has the side-effect of initializing the Reactions language's injector if that hasn't happened yet:
		globalServiceProvider.findService(URI.createFileURI("fake.reactions"), IReactionsGenerator)

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

	def private void generateCommonalityChangeReactions(Participation participation,
		FluentReactionsSegmentBuilder segment) {
		participation.generateReactionsForCommonalityExistenceChange(segment)
		participation.generateReactionsForCommonalityAttributeChange(segment)
		participation.generateReactionsForCommonalityReferenceChange(segment)
	}

	def private void generateParticipationChangeReactions(Participation participation,
		FluentReactionsSegmentBuilder segment) {
		participation.generateReactionsForParticipationExistenceChange(segment)
		participation.generateReactionsForParticipationAttributeChange(segment)
		participation.generateReactionsForParticipationReferenceChange(segment)
	}

	def private void generateReactionsForCommonalityExistenceChange(Participation targetParticipation,
		FluentReactionsSegmentBuilder segment) {
		commonalityExistenceChangeReactionsBuilder.createFor(targetParticipation).generateReactions(segment)
	}

	def private void generateReactionsForCommonalityAttributeChange(Participation targetParticipation,
		FluentReactionsSegmentBuilder segment) {
		commonality.attributes.forEach [ attribute |
			commonalityAttributeChangeReactionsBuilder.createFor(attribute, targetParticipation)
				.generateReactions(segment)
		]
	}

	def private void generateReactionsForCommonalityReferenceChange(Participation targetParticipation,
		FluentReactionsSegmentBuilder segment) {
		commonality.references.forEach [ reference |
			commonalityReferenceChangeReactionsBuilder.createFor(reference, targetParticipation)
				.generateReactions(segment)
		]
	}

	def private void generateReactionsForParticipationExistenceChange(Participation participation,
		FluentReactionsSegmentBuilder segment) {
		participationExistenceChangeReactionsBuilder.createFor(participation).generateReactions(segment)
	}

	def private void generateReactionsForParticipationAttributeChange(Participation participation,
		FluentReactionsSegmentBuilder segment) {
		participationAttributeChangeReactionsBuilder.createFor(participation).generateReactions(segment)
	}

	def private void generateReactionsForParticipationReferenceChange(Participation participation,
		FluentReactionsSegmentBuilder segment) {
		participationReferenceChangeReactionsBuilder.createFor(participation).generateReactions(segment)
	}
}
