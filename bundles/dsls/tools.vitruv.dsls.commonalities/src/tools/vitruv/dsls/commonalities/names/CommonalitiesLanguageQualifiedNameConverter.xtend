package tools.vitruv.dsls.commonalities.names

import com.google.inject.Singleton
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.naming.QualifiedName

import static extension tools.vitruv.dsls.commonalities.names.QualifiedNameHelper.*

@Singleton
class CommonalitiesLanguageQualifiedNameConverter extends IQualifiedNameConverter.DefaultImpl {

	static val NOT_FOUND = -1

	/*
	 * In order to retain backwards compatibility with java-like qualified
	 * names and still be able to identify the domain portion of qualified
	 * names, we insert a separator segment into qualified names that include a
	 * domain name.
	 * 
	 * 'a:b.c' -> #['a', ':', 'b', 'c']
	 * 'a:b' -> #['a', ':', 'b']
	 * 'b.c' -> #['b', 'c']
	 * 'a' -> #['a']
	 * If 'a' is a domain name, then the qualified name is #['a', ':']
	 * 'a.b.c.d' -> #['a', 'b', 'c', 'd'] (java-like)
	 */
	override toQualifiedName(String qualifiedNameAsText) {
		val domainSeparatorIndex = qualifiedNameAsText.indexOf(DOMAIN_METACLASS_SEPARATOR)
		if (domainSeparatorIndex === NOT_FOUND) {
			return super.toQualifiedName(qualifiedNameAsText)
		}
		val domainName = qualifiedNameAsText.substring(0, domainSeparatorIndex)
		val classAndAttributePart = qualifiedNameAsText.substring(domainSeparatorIndex + 1)
		return QualifiedName.create(domainName, DOMAIN_METACLASS_SEPARATOR_SEGMENT)
			.append(super.toQualifiedName(classAndAttributePart))
	}

	override toString(QualifiedName name) {
		val domainName = name.domainName
		if (domainName !== null) {
			return domainName + DOMAIN_METACLASS_SEPARATOR + super.toString(name.skipFirst(2))
		} else {
			return super.toString(name)
		}
	}
}
