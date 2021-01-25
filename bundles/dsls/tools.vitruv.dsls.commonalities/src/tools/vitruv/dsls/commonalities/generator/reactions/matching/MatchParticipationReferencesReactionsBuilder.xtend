package tools.vitruv.dsls.commonalities.generator.reactions.matching

import com.google.inject.Inject
import tools.vitruv.dsls.commonalities.generator.reactions.ReactionsSubGenerator
import tools.vitruv.dsls.commonalities.generator.util.guice.InjectingFactoryBase
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.dsls.commonalities.participation.ParticipationContextHelper.*

/**
 * Generates the matching reaction and routines for participations referenced
 * in commonality reference mappings.
 */
class MatchParticipationReferencesReactionsBuilder extends ReactionsSubGenerator {

	static class Factory extends InjectingFactoryBase {
		def createFor(Participation participation) {
			return new MatchParticipationReferencesReactionsBuilder(participation).injectMembers
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
		val extension matchingReactionsBuilder = participationMatchingReactionsBuilderProvider.getFor(segment)
		// TODO We do not take the 'read' flag of reference mappings into account currently. The semantics and use
		// cases for this flag are not clear currently. Remove it?
		commonality.references.flatMap[referenceParticipationContexts].filter [
			it.participation.domainName == participation.domainName
		].forEach [
			generateMatchingReactions
		]
	}
}
