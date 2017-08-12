package tools.vitruv.dsls.commonalities.names

import com.google.inject.Singleton
import java.util.regex.Pattern
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.naming.QualifiedName

@Singleton
class CommonalitiesLanguageQualifiedNameConverter implements IQualifiedNameConverter {

	public static val DOMAIN_METACLASS_SEPARATOR = ":";
	public static val METACLASS_ATTRIBUTE_SEPARATOR = ".";

	override toQualifiedName(String qualifiedNameAsText) {
		var String[] parts
		if (qualifiedNameAsText.contains(DOMAIN_METACLASS_SEPARATOR)) {
			val domainSplit = qualifiedNameAsText.split(DOMAIN_METACLASS_SEPARATOR)
			if (domainSplit.length == 1) {
				// special case: DOMAIN_METACLASS_SEPARATOR at end
				parts = #[domainSplit.get(0), ""]
			} else if (domainSplit.get(1).contains(METACLASS_ATTRIBUTE_SEPARATOR)) {
				// we have an attribute specification
				
				val metaclassSplit = domainSplit.get(1).split(Pattern.quote(METACLASS_ATTRIBUTE_SEPARATOR))
				if (metaclassSplit.length == 1) {
					// special case: dot at end
					parts = #[domainSplit.get(0), metaclassSplit.get(0), ""]
				} else {
					// domain, metaclass and attribute
					parts = #[domainSplit.get(0)] + metaclassSplit
				}
			} else {
				// domain with metaclass
				parts = domainSplit
			}
		} else {
			// only a domain
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
