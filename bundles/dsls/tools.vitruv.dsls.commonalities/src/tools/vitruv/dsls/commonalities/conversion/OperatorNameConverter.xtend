package tools.vitruv.dsls.commonalities.conversion

import org.eclipse.xtext.conversion.IValueConverter
import org.eclipse.xtext.conversion.ValueConverterException
import org.eclipse.xtext.nodemodel.INode
import tools.vitruv.extensions.dslruntime.commonalities.operators.CommonalitiesOperatorConventions

/**
 * Converts operator names from their language notation (like ‘=’) to their type names 
 * (like ‘equals_’). Doing this so early provides a consistent representation in the
 * AST: operators will always be represented by the expected type name. The language 
 * notation can only be found in the concrete syntax.
 */
class OperatorNameConverter implements IValueConverter<String> {
	override toString(String typeName) throws ValueConverterException {
		// this will not only be called for existing models, but also
		// for candidates. Thus, don’t break on invalid names!
		if (typeName.contains('.')) {
			if (CommonalitiesOperatorConventions.isPotentialOperator(typeName)) {
				CommonalitiesOperatorConventions.toOperatorLanguageQualifiedName(typeName)
			} else {
				typeName
			} 
		} else {
			CommonalitiesOperatorConventions.toOperatorLanguageName(typeName)
		}
	}
	
	override toValue(String languageNotation, INode node) throws ValueConverterException {
		if (languageNotation.contains('.')) {
			CommonalitiesOperatorConventions.toOperatorTypeQualifiedName(languageNotation)
		} else {
			CommonalitiesOperatorConventions.toOperatorTypeName(languageNotation)
		}
	}
}