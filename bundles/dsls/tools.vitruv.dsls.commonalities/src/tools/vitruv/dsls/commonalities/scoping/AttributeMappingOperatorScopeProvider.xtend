package tools.vitruv.dsls.commonalities.scoping

import com.google.inject.Singleton
import java.util.List
import org.eclipse.xtext.common.types.JvmDeclaredType
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.IAttributeMappingOperator

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

@Singleton
class AttributeMappingOperatorScopeProvider extends AbstractOperatorScopeProvider {

	static List<String> DEFAULT_OPERATOR_IMPORTS = #[
		'tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.*'
	]

	override protected getOperatorTypeName() {
		return 'attribute mapping'
	}

	override protected getDefaultOperatorImports() {
		return DEFAULT_OPERATOR_IMPORTS
	}

	override protected getOperatorBaseType() {
		return IAttributeMappingOperator
	}

	override protected getOperatorName(JvmDeclaredType operatorType) {
		return operatorType.attributeMappingOperatorName
	}
}
