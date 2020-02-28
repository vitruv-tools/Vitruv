package tools.vitruv.dsls.commonalities.generator

import com.google.inject.Inject
import com.google.inject.Provider
import java.util.function.Supplier
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.resource.IGlobalServiceProvider
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.reactions.api.generator.IReactionsGenerator
import tools.vitruv.framework.domains.VitruvDomainProviderRegistry

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class ReactionsGenerator extends SubGenerator {

	private static val Logger logger = Logger.getLogger(ReactionsGenerator)

	@Inject IGlobalServiceProvider globalServiceProvider

	val Supplier<IReactionsGenerator> reactionsGeneratorProvider
	val Supplier<ParticipationExistenceChangeReactionsBuilder> participationExistenceChangeReactionsBuilder
	val Supplier<CommonalityExistenceChangeReactionsBuilder> commonalityExistenceChangeReactionsBuilder
	val Supplier<CommonalityAttributeChangeReactionsBuilder> commonalityAttributeChangeReactionsBuilder
	val Supplier<ParticipationAttributeChangeReactionsBuilder> participationAttributeChangeReactionsBuilder
	val Supplier<ParticipationReferenceChangeReactionsBuilder> participationReferenceChangeReactionsBuilder
	val Supplier<CommonalityReferenceChangeReactionsBuilder> commonalityReferenceChangeReactionsBuilder
	@Inject Provider<ReactionsGenerationContext> reactionsGeneratorContextProvider
	@Inject CommonalitiesGenerationSettings generationSettings
	extension ReactionsGenerationContext reactionsGenerationContext

	@Inject
	new(
		Provider<IReactionsGenerator> reactionsGeneratorProvider,
		Provider<ParticipationExistenceChangeReactionsBuilder> participationExistenceChangeReactionsBuilderProvider,
		Provider<CommonalityExistenceChangeReactionsBuilder> commonalityExistenceChangeReactionsBuilderProvider,
		Provider<CommonalityAttributeChangeReactionsBuilder> commonalityAttributeChangeReactionsBuilderProvider,
		Provider<ParticipationAttributeChangeReactionsBuilder> participationAttributeChangeReactionsBuilderProvider,
		Provider<ParticipationReferenceChangeReactionsBuilder> participationReferenceChangeReactionsBuilderProvider,
		Provider<CommonalityReferenceChangeReactionsBuilder> commonalityReferenceChangeReactionsBuilderProvider
	) {
		this.reactionsGeneratorProvider = [
			reactionsGeneratorProvider.get() => [
				useResourceSet(resourceSet)
			]
		]
		this.participationExistenceChangeReactionsBuilder = [
			participationExistenceChangeReactionsBuilderProvider.get.withGenerationContext(reactionsGenerationContext)
		]
		this.commonalityExistenceChangeReactionsBuilder = [
			commonalityExistenceChangeReactionsBuilderProvider.get.withGenerationContext(reactionsGenerationContext)
		]
		this.commonalityAttributeChangeReactionsBuilder = [
			commonalityAttributeChangeReactionsBuilderProvider.get.withGenerationContext(reactionsGenerationContext)
		]
		this.participationAttributeChangeReactionsBuilder = [
			participationAttributeChangeReactionsBuilderProvider.get.withGenerationContext(reactionsGenerationContext)
		]
		this.participationReferenceChangeReactionsBuilder = [
			participationReferenceChangeReactionsBuilderProvider.get.withGenerationContext(reactionsGenerationContext)
		]
		this.commonalityReferenceChangeReactionsBuilder = [
			commonalityReferenceChangeReactionsBuilderProvider.get.withGenerationContext(reactionsGenerationContext)
		]
	}

	override generate() {
		logger.debug('''Generating reactions for commonality «commonalityFile.concept.name»::«commonality.name»''')
		if (commonality.participations.length + commonality.allMembers.length == 0) {
			// nothing to generate
			logger.debug("  Ignoring empty commonality")
			return;
		}

		// This has the side-effect of initializing the Reactions language's injector if that hasn't happened yet:
		globalServiceProvider.findService(URI.createFileURI("fake.reactions"), IReactionsGenerator)

		val generator = reactionsGeneratorProvider.get()
		reactionsGenerationContext = reactionsGeneratorContextProvider.get.wrappingContext(generationContext)

		val reactionFile = create.reactionsFile(commonality.name)
		for (participation : commonalityFile.commonality.participations) {
			reactionFile +=
				create.reactionsSegment('''«commonality.name»To«participation.name»''').
					inReactionToChangesIn(commonalityFile.concept.vitruvDomain)
					.executeActionsIn(participation.domain.vitruvDomain) +=
						commonalityChangeReactions(participation)

			reactionFile +=
				create.reactionsSegment('''«commonality.name»From«participation.name»''')
					.inReactionToChangesIn(participation.domain.vitruvDomain)
					.executeActionsIn(commonalityFile.concept.name) +=
						participationChangeReactions(participation)

		}

		logger.debug('''Temporarily registering concept domains: «generatedConcepts»''')
		for (String conceptName : generatedConcepts) {
			VitruvDomainProviderRegistry.registerDomainProvider(conceptName, conceptName.vitruvDomain.provider)
		}

		try {
			generator.addReactionsFile(reactionFile)
			generator.generate(fsa)

			if (generationSettings.createReactionFiles) {
				generator.writeReactions(fsa)
			}
		} finally {
			logger.debug('''Unregistering concept domains again: «generatedConcepts»''')
			for (String conceptName : generatedConcepts) {
				VitruvDomainProviderRegistry.unregisterDomainProvider(conceptName)
			}
		}
	}

	def private commonalityChangeReactions(Participation participation) {
		(
			participation.reactionsForCommonalityExistenceChange
			+ participation.reactionsForCommonalityAttributeChange
			+ participation.reactionsForCommonalityReferenceChange
		).filterNull
	}

	def private participationChangeReactions(Participation participation) {
		(
			participation.reactionsForParticipationExistenceChange
			+ participation.reactionsForParticipationAttributeChange
			+ participation.reactionsForParticipationReferenceChange
		).filterNull
	}

	def private reactionsForCommonalityExistenceChange(Participation participation) {
		commonalityExistenceChangeReactionsBuilder.get.forParticipation(participation).reactions
	}

	def private reactionsForCommonalityAttributeChange(Participation participation) {
		commonality.attributes.flatMap [ attribute |
			commonalityAttributeChangeReactionsBuilder.get.forAttribute(attribute).regardingParticipation(participation).reactions
		]
	}

	def private reactionsForCommonalityReferenceChange(Participation participation) {
		commonality.references.flatMap [ reference |
			commonalityReferenceChangeReactionsBuilder.get.forReference(reference).regardingParticipation(participation).reactions
		]
	}

	def private reactionsForParticipationExistenceChange(Participation participation) {
		participationExistenceChangeReactionsBuilder.get.forParticipation(participation).reactions
	}

	def private reactionsForParticipationAttributeChange(Participation participation) {
		participationAttributeChangeReactionsBuilder.get.forParticipation(participation).reactions
	}

	def private reactionsForParticipationReferenceChange(Participation participation) {
		participationReferenceChangeReactionsBuilder.get.forParticipation(participation).reactions
	}
}
