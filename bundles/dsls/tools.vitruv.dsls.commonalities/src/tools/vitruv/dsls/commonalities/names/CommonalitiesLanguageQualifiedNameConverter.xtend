package tools.vitruv.dsls.commonalities.names

import com.google.inject.Singleton
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.naming.QualifiedName

@Singleton
class CommonalitiesLanguageQualifiedNameConverter implements IQualifiedNameConverter {

	public static val DOMAIN_METACLASS_SEPARATOR = ":";
	public static val METACLASS_ATTRIBUTE_SEPARATOR = ".";
	static val NOT_FOUND = -1

	/*
	 * 'a:b.c' -> #['a', 'b', 'c']
	 * 'a:b' -> #['a', 'b']
	 * 'b.c' -> #['', 'b', 'c']
	 * 'a' -> #['a']
	 */
	override toQualifiedName(String qualifiedNameAsText) {
		var String[] parts
		var classPartToSplit = qualifiedNameAsText
		var domainPart = ''

		val classSplitIndex = qualifiedNameAsText.indexOf(DOMAIN_METACLASS_SEPARATOR)
		if (classSplitIndex !== NOT_FOUND) {
			domainPart = qualifiedNameAsText.substring(0, classSplitIndex)
			classPartToSplit = qualifiedNameAsText.substring(classSplitIndex + 1)
		}

		val attributeSplitIndex = classPartToSplit.indexOf(METACLASS_ATTRIBUTE_SEPARATOR)
		if (attributeSplitIndex !== NOT_FOUND) {
			var classPart = classPartToSplit.substring(0, attributeSplitIndex)
			val attributePart = classPartToSplit.substring(attributeSplitIndex + 1)
			parts = #[domainPart, classPart, attributePart]
		} else if (classSplitIndex !== NOT_FOUND) {
			parts = #[domainPart, classPartToSplit]
		} else {
			parts = #[qualifiedNameAsText]
		}
		QualifiedName.create(parts)
	}

	override toString(QualifiedName name) {
		var result = name.firstSegment
		if (name.segmentCount > 1) result += DOMAIN_METACLASS_SEPARATOR + name.getSegment(1)
		if (name.segmentCount > 2) result += METACLASS_ATTRIBUTE_SEPARATOR + name.getSegment(2)
		result
	}
}
