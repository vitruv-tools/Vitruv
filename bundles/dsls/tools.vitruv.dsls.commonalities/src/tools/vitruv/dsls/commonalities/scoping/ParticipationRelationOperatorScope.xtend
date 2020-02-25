package tools.vitruv.dsls.commonalities.scoping

import com.google.inject.Singleton
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.relation.ContainmentRelation

@Singleton
class ParticipationRelationOperatorScope extends AbstractExternalOperatorScope {

	static val WELL_KNOWN_OPERATORS = #[
		ContainmentRelation.name
	]

	override getWellKnownOperators() {
		return WELL_KNOWN_OPERATORS
	}
}
