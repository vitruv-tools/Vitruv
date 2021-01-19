package tools.vitruv.extensions.dslruntime.commonalities.operators

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.Map

import static java.util.stream.Collectors.toMap
import static com.google.common.base.Preconditions.checkArgument

@Utility
class CommonalitiesOperatorConventions {
	static val OPERATOR_TYPES_PACKAGE_NAME = "cl_operators__"
	static val TYPE_NAME_TO_LANGUAGE_IDENTIFIER = Map.of(
		"equals_", "=",
		"in_", "in"
	)
	static val LANGUAGE_IDENTIFIER_TO_TYPE_NAME = TYPE_NAME_TO_LANGUAGE_IDENTIFIER.entrySet.stream
		.collect(toMap([value], [key]))
	
	def static toOperatorLanguageName(String typeName) {
		if (typeName === null) null
		else TYPE_NAME_TO_LANGUAGE_IDENTIFIER.getOrDefault(typeName, typeName)
	}
	
	// type names *must* start with an upper case letter because of a wrong
	// heuristic implemented in Xtext’s InMemoryJavaCompiler
	def static toOperatorTypeName(String languageName) {
		if (languageName === null) null
		else LANGUAGE_IDENTIFIER_TO_TYPE_NAME.getOrDefault(languageName, languageName)
	}
	
	def static toOperatorTypeQualifiedName(String languageQualifiedName) {
		if (languageQualifiedName === null) null
		else {
			val lastDot = languageQualifiedName.lastIndexOf('.')
			checkArgument(lastDot != -1, '''‹«languageQualifiedName»› is not a fully qualified name!''')
			toOperatorTypeQualifiedName(
				languageQualifiedName.substring(0, lastDot),
				languageQualifiedName.substring(lastDot + 1)
			)
		}
	}
	
	def static toOperatorTypeQualifiedName(String languagePackage, String languageName) {
		languagePackage + '.' + OPERATOR_TYPES_PACKAGE_NAME + '.' + toOperatorTypeName(languageName)
	}
	
	def static toOperatorLanguageQualifiedName(String typeQualifiedName) {
		if (typeQualifiedName === null) null
		else {
			val lastDot = typeQualifiedName.lastIndexOf('.')
			checkArgument(lastDot != -1, '''‹«typeQualifiedName»› is not a fully qualified name!''')
			toOperatorLanguageQualifiedName(
				typeQualifiedName.substring(0, lastDot),
				typeQualifiedName.substring(lastDot + 1)
			)
		}
	}
	
	def static toOperatorLanguageQualifiedName(String typePackage, String typeName) {
		val packageEnd = typePackage.lastIndexOf('.' + OPERATOR_TYPES_PACKAGE_NAME)
		checkArgument(packageEnd != -1, '''The provided package does not contain «OPERATOR_TYPES_PACKAGE_NAME»: ‹«typePackage»›''')
		typePackage.substring(0, packageEnd - 1) + toOperatorLanguageName(typeName)		
	}
}