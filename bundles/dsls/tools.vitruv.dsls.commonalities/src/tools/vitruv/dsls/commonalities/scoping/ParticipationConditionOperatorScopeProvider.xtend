package tools.vitruv.dsls.commonalities.scoping

import com.google.inject.Singleton
import java.util.List
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.ParticipationConditionOperator

@Singleton
class ParticipationConditionOperatorScopeProvider extends AbstractOperatorScopeProvider {

	static List<String> DEFAULT_OPERATOR_IMPORTS = #[
		'tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.*'
	]

	override protected getOperatorTypeName() {
		return 'condition'
	}

	override protected getDefaultOperatorImports() {
		return DEFAULT_OPERATOR_IMPORTS
	}

	override protected getOperatorBaseType() {
		return ParticipationConditionOperator
	}
}
