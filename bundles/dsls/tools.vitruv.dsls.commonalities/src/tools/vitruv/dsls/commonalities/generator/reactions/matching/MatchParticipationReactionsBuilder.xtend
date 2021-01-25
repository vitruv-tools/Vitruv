package tools.vitruv.dsls.commonalities.generator.reactions.matching

import com.google.inject.Inject
import org.apache.log4j.Logger
import tools.vitruv.dsls.commonalities.generator.reactions.ReactionsSubGenerator
import tools.vitruv.dsls.commonalities.generator.util.guice.InjectingFactoryBase
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.dsls.commonalities.participation.ParticipationContextHelper.*

/**
 * Generates the matching reaction and routines for a participation, in its own
 * specified context.
 */
class MatchParticipationReactionsBuilder extends ReactionsSubGenerator {

	static val Logger logger = Logger.getLogger(MatchParticipationReactionsBuilder)

	static class Factory extends InjectingFactoryBase {
		def createFor(Participation participation) {
			return new MatchParticipationReactionsBuilder(participation).injectMembers
		}
	}

	@Inject ParticipationMatchingReactionsBuilder.Provider participationMatchingReactionsBuilderProvider

	// Note: May be a commonality participation.
	val Participation participation

	private new(Participation participation) {
		checkNotNull(participation, "participation is null")
		this.participation = participation
	}

	// Dummy constructor for Guice
	package new() {
		this.participation = null
		throw new IllegalStateException("Use the Factory to create instances of this class!")
	}

	def void generateReactions(FluentReactionsSegmentBuilder segment) {
		// If the commonality is marked as 'referenced', we ignore any potentially existing own participation contexts:
		if (commonality.referenced) {
			return;
		}
		val participationContext = participation.participationContext
		if (!participationContext.isPresent) {
			logger.debug('''Commonality «commonality»: Found no own participation context for participation «
				participation.name»''')
			return;
		}

		val extension matchingReactionsBuilder = participationMatchingReactionsBuilderProvider.getFor(segment)
		participationContext.get.generateMatchingReactions
	}
}
