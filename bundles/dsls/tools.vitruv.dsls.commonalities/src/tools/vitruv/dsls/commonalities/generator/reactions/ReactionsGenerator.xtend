package tools.vitruv.dsls.commonalities.generator.reactions

import com.google.inject.Inject
import com.google.inject.Provider
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.resource.IGlobalServiceProvider
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

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*
import tools.vitruv.dsls.commonalities.generator.GenerationContext
import static extension tools.vitruv.dsls.commonalities.generator.reactions.ReactionsGeneratorConventions.*

/**
 * Generates reactions in the Reactions language and the corresponding Java
 * code to transform changes between a commonality and its participations.
 */
class ReactionsGenerator implements SubGenerator {
	static val Logger logger = Logger.getLogger(ReactionsGenerator)

	@Inject IGlobalServiceProvider globalServiceProvider
	@Inject Provider<IReactionsGenerator> reactionsGeneratorProvider
	@Inject extension GenerationContext

	@Inject extension ReactionsGenerationContext reactionsGenerationContext

	@Inject CommonalityInsertReactionsBuilder.Factory commonalityInsertReactionsBuilder
	@Inject CommonalityAttributeChangeReactionsBuilder.Factory commonalityAttributeChangeReactionsBuilder

	@Inject MatchParticipationReactionsBuilder.Factory matchParticipationReactionsBuilder
	@Inject MatchParticipationReferencesReactionsBuilder.Factory matchParticipationReferencesReactionsBuilder
	@Inject ParticipationAttributeChangeReactionsBuilder.Factory participationAttributeChangeReactionsBuilder

	override beforeGenerate() {
		// Prepare the Reactions language:
		if (isNewResourceSet) {
			// This has the side-effect of initializing the Reactions language's injector if that hasn't happened yet:
			globalServiceProvider.findService(URI.createFileURI('fake.reactions'), IReactionsGenerator)
		}
	}

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

		// Generate reactions:
		val reactionsFile = generateReactions()

		// Generate reactions code:
		if (reactionsFile.willGenerateCode) {
			generateReactionsCode(reactionsFile)
		} else {
			logger.debug("  Ignoring empty commonality")
		}
	}

	private def FluentReactionsFileBuilder generateReactions() {
		val reactionsFile = create.reactionsFile(commonality.name)
		for (participation : commonalityFile.commonality.participations) {
			val fromSegment = generateCommonalityFromParticipationSegment(participation)
			reactionsFile += fromSegment
			val toSegment = generateCommonalityToParticipationSegment(participation)
			reactionsFile += toSegment
		}
		return reactionsFile
	}

	private def generateReactionsCode(FluentReactionsFileBuilder reactionsFile) {
		// Get and setup the code generator of the Reactions language:
		val reactionsGenerator = reactionsGeneratorProvider.get() => [
			useResourceSet(resourceSet)
		]
		
		// Generate the Java code for the given reactions:
		reactionsGenerator.addReactionsFile(reactionsFile)
		reactionsGenerator.generate(fsa)

		// Optionally: Also persist the reactions in the Reactions language itself.
		if (settings.createReactionFiles) {
			reactionsGenerator.writeReactions(fsa)
		}
	}

	private def generateCommonalityFromParticipationSegment(Participation participation) {
		val segment = create.reactionsSegment(
			getReactionsSegmentFromParticipationToCommonalityName(commonality, participation)) //
		.inReactionToChangesIn(participation.domain.metamodelRootPackage) //
		.executeActionsIn(commonalityFile.concept.metamodelRootPackage)
		participation.generateParticipationChangeReactions(segment)
		return segment
	}

	private def generateCommonalityToParticipationSegment(Participation participation) {
		val segment = create.reactionsSegment(
			getReactionsSegmentFromCommonalityToParticipationName(commonality, participation)) //
		.inReactionToChangesIn(commonalityFile.concept.metamodelRootPackage) //
		.executeActionsIn(participation.domain.metamodelRootPackage)
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
			commonalityAttributeChangeReactionsBuilder.createFor(attribute, targetParticipation).
				generateReactions(segment)
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
