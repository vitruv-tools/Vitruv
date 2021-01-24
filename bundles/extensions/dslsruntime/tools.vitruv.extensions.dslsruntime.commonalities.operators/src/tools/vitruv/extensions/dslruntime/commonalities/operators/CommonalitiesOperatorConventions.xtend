package tools.vitruv.extensions.dslruntime.commonalities.operators

import edu.kit.ipd.sdq.activextendannotations.Utility

import static com.google.common.base.Preconditions.checkArgument

@Utility
class CommonalitiesOperatorConventions {
	static val OPERATOR_TYPES_PACKAGE_NAME = "cl_operators__"
	static val TYPE_NAME_TO_LANGUAGE_IDENTIFIER = newImmutableMap(
		"equals_" -> "=",
		"in_" -> "in",
		"plusEquals_" -> "+=",
		"minusEquals_" -> "-=",
		"timesEquals_" -> "*=",
		"dividedByEquals_" -> "/=",
		"modEquals_" -> "%=",
		"greaterThanOrEqual_" -> ">=",
		"lessThanOrEqual_" -> "<=",
		"logicalOr_" -> "||",
		"logicalAnd_" -> "&&",
		"doubleEquals_" -> "==",
		"equalsNot_" -> "!=",
		"tripleEquals_" -> "===",
		"notDoubleEquals_" -> "!==",
		"implies_" -> "=>",
		"leftRight_" -> "<>",
		"elvis_" -> "?:",
		"doubleTimes_" -> "**",
		"doubleMinus_" -> "--",
		"doublePlus_" -> "++",
		"doubleColon_" -> "::",
		"not_" -> "!",
		"mod_" -> "%",
		"and_" -> "&",
		"times_" -> "*",
		"plus_" -> "+",
		"minus_" -> "-",
		"dividedBy_" -> "/",
		"lessThan_" -> "<",
		"greaterThan_" -> ">",
		"circumflex_" -> "^",
		"tilde_" -> "~",
		"colon_" -> ":",
		"questionmark_" -> "?"
	)
	static val LANGUAGE_IDENTIFIER_TO_TYPE_NAME = newImmutableMap(TYPE_NAME_TO_LANGUAGE_IDENTIFIER.entrySet.map [value -> key])
	
	def static toOperatorLanguageName(String typeName) {
		if (typeName === null) null
		else TYPE_NAME_TO_LANGUAGE_IDENTIFIER.getOrDefault(typeName, typeName)
	}
	
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
	
	def static isPotentialOperator(String typeQualifiedName) {
		typeQualifiedName.contains(OPERATOR_TYPES_PACKAGE_NAME)
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