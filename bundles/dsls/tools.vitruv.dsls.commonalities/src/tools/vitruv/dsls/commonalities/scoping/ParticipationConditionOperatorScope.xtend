package tools.vitruv.dsls.commonalities.scoping

import com.google.inject.Singleton
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.EqualsOperator

@Singleton
class ParticipationConditionOperatorScope extends AbstractExternalOperatorScope {

	static val WELL_KNOWN_OPERATORS = #[
		EqualsOperator.name
	]

	override getWellKnownOperators() {
		return WELL_KNOWN_OPERATORS
	}
}
