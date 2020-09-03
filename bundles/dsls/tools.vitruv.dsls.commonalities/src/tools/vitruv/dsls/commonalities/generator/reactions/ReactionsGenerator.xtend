package tools.vitruv.dsls.commonalities.generator.reactions

import com.google.inject.Inject
import com.google.inject.Provider
import java.util.List
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
import tools.vitruv.dsls.reactions.builder.FluentReactionsFileBuilder
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder
import tools.vitruv.framework.domains.VitruvDomainProviderRegistry

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

/**
 * Generates reactions in the Reactions language and the corresponding Java
 * code to transform changes between a commonality and its participations.
 */
class ReactionsGenerator extends SubGenerator {

	static val Logger logger = Logger.getLogger(ReactionsGenerator)

	@Inject IGlobalServiceProvider globalServiceProvider
	@Inject Provider<IReactionsGenerator> reactionsGeneratorProvider

	@Inject CommonalitiesGenerationSettings generationSettings
	@Inject extension ReactionsGenerationContext reactionsGenerationContext

	@Inject CommonalityInsertReactionsBuilder.Factory commonalityInsertReactionsBuilder
	@Inject CommonalityAttributeChangeReactionsBuilder.Factory commonalityAttributeChangeReactionsBuilder

	@Inject MatchParticipationReactionsBuilder.Factory matchParticipationReactionsBuilder
	@Inject MatchParticipationReferencesReactionsBuilder.Factory matchParticipationReferencesReactionsBuilder
	@Inject ParticipationAttributeChangeReactionsBuilder.Factory participationAttributeChangeReactionsBuilder

	/**
	 * Generates the reactions and corresponding Java code to transform changes
	 * between a commonality and its participations.
	 * <p>
	 * We generate two reaction segments for each pair of a commonality and one
	 * of its participations:
	 * <ol>
	 * <li>Commonality from participation: In reaction to changes in the
	 * participation's domain, we perform corresponding actions in the
	 * intermediate model.
	 * <li>Commonality to participation: In reaction to changes regarding the
	 * commonality, we perform corresponding actions with the participation's
	 * objects.
	 * </ol>
	 */
	override generate() {
		logger.debug('''Generating reactions for commonality «commonality»''')
		if (commonality.participations.length + commonality.allMembers.length == 0) {
			// nothing to generate
			logger.debug("  Ignoring empty commonality")
			return;
		}

		// Prepare the Reactions language:
		// This has the side-effect of initializing the Reactions language's injector if that hasn't happened yet:
		globalServiceProvider.findService(URI.createFileURI('fake.reactions'), IReactionsGenerator)

		// Generate reactions:
		val reactionsFiles = generateReactions()

		// Generate reactions code:
		generateReactionsCode(reactionsFiles)
	}

	private def List<FluentReactionsFileBuilder> generateReactions() {
		val reactionsFile = create.reactionsFile(commonality.name)
		for (participation : commonalityFile.commonality.participations) {
			reactionsFile += generateCommonalityFromParticipationSegment(participation)
			reactionsFile += generateCommonalityToParticipationSegment(participation)
		}
		return #[reactionsFile]
	}

	private def generateReactionsCode(FluentReactionsFileBuilder... reactionsFiles) {
		// Get and setup the code generator of the Reactions language:
		val reactionsGenerator = reactionsGeneratorProvider.get() => [
			useResourceSet(resourceSet)
		]

		// Temporarily register dummy domains for our generated concept domains:
		// These are used during the reactions code generation.
		logger.trace('''Temporarily registering concept domains: «generatedConcepts»''')
		for (String conceptName : generatedConcepts) {
			VitruvDomainProviderRegistry.registerDomainProvider(conceptName, conceptName.vitruvDomain.provider)
		}

		try {
			// Generate the Java code for the given reactions:
			reactionsFiles.forEach [reactionsFile |
				reactionsGenerator.addReactionsFile(reactionsFile)
			]
			reactionsGenerator.generate(fsa)

			// Optionally: Also persist the reactions in the Reactions language itself.
			if (generationSettings.createReactionFiles) {
				reactionsGenerator.writeReactions(fsa)
			}
		} finally {
			// Unregister our temporarily registered concept domains again:
			logger.trace('''Unregistering concept domains again: «generatedConcepts»''')
			for (String conceptName : generatedConcepts) {
				VitruvDomainProviderRegistry.unregisterDomainProvider(conceptName)
			}
		}
	}

	private def generateCommonalityFromParticipationSegment(Participation participation) {
		val segment = create.reactionsSegment('''«commonality.name»From«participation.name»''')
			.inReactionToChangesIn(participation.domain.vitruvDomain)
			.executeActionsIn(commonalityFile.concept.name)
		participation.generateParticipationChangeReactions(segment)
		return segment
	}

	private def generateCommonalityToParticipationSegment(Participation participation) {
		val segment = create.reactionsSegment('''«commonality.name»To«participation.name»''')
			.inReactionToChangesIn(commonalityFile.concept.vitruvDomain)
			.executeActionsIn(participation.domain.vitruvDomain)
		participation.generateCommonalityChangeReactions(segment)
		return segment
	}

	private def void generateParticipationChangeReactions(Participation participation,
		FluentReactionsSegmentBuilder segment) {
		participation.generateMatchParticipationReactions(segment)
		participation.generateMatchParticipationReferencesReactions(segment)
		participation.generateReactionsForParticipationAttributeChange(segment)
	}

	private def void generateCommonalityChangeReactions(Participation participation,
		FluentReactionsSegmentBuilder segment) {
		participation.generateCommonalityInsertReactions(segment)
		participation.generateReactionsForCommonalityAttributeChange(segment)
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
