package tools.vitruv.dsls.commonalities.generator

import com.google.inject.Inject
import tools.vitruv.dsls.commonalities.language.Commonality
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.ParticipationContextHelper.*

/**
 * Generates the matching reaction and routines for participations referenced
 * in commonality reference mappings.
 */
package class MatchParticipationReferencesReactionsBuilder extends ReactionsSubGenerator {

	static class Factory extends InjectingFactoryBase {
		def createFor(Participation participation) {
			return new MatchParticipationReferencesReactionsBuilder(participation).injectMembers
		}
	}

	@Inject ParticipationMatchingReactionsBuilder.Provider participationMatchingReactionsBuilderProvider

	// Note: May be a commonality participation.
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

	def package void generateReactions(FluentReactionsSegmentBuilder segment) {
		val extension matchingReactionsBuilder = participationMatchingReactionsBuilderProvider.getFor(segment)
		relevantReferenceMappings.map[referenceParticipationContext].filter[present].map[get].forEach [
			generateReactions
		]
	}

	def private getRelevantReferenceMappings() {
		return commonality.references.flatMap[mappings].filter [
			isRead && it.participation == participation
		]
	}
}
