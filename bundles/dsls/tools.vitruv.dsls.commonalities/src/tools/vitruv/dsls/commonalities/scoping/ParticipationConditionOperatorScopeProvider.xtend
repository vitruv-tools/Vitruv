package tools.vitruv.dsls.commonalities.scoping

import com.google.inject.Singleton
import java.util.List
import org.eclipse.xtext.common.types.JvmDeclaredType
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.IParticipationConditionOperator

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

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
		return IParticipationConditionOperator
	}

	override protected getOperatorName(JvmDeclaredType operatorType) {
		return operatorType.participationConditionOperatorName
	}
}
