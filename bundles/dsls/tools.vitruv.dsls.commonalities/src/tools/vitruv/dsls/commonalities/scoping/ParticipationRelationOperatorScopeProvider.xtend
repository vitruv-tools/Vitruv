package tools.vitruv.dsls.commonalities.scoping

import com.google.inject.Singleton
import java.util.List
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.relation.ParticipationRelationOperator

@Singleton
class ParticipationRelationOperatorScopeProvider extends AbstractOperatorScopeProvider {

	static List<String> DEFAULT_OPERATOR_IMPORTS = #[
		'tools.vitruv.extensions.dslruntime.commonalities.operators.participation.relation.*'
	]

	override protected getOperatorTypeName() {
		return 'relation'
	}

	override protected getDefaultOperatorImports() {
		return DEFAULT_OPERATOR_IMPORTS
	}

	override protected getOperatorBaseType() {
		return ParticipationRelationOperator
	}
}
