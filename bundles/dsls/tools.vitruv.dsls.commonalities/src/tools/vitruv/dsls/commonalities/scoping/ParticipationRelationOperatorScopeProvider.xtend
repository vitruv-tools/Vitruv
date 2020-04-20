package tools.vitruv.dsls.commonalities.scoping

import com.google.inject.Singleton
import java.util.List
import org.eclipse.xtext.common.types.JvmDeclaredType
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.relation.IParticipationRelationOperator

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

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
		return IParticipationRelationOperator
	}

	override protected getOperatorName(JvmDeclaredType operatorType) {
		return operatorType.participationRelationOperatorName
	}
}
