package tools.vitruv.dsls.commonalities.names

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.xtext.naming.QualifiedName

@Utility
class QualifiedNameHelper {
	public static val DOMAIN_METACLASS_SEPARATOR = ":"
	public static val METACLASS_ATTRIBUTE_SEPARATOR = "."
	public static val DOMAIN_METACLASS_SEPARATOR_SEGMENT = ":"

	static def getQualifiedDomainName(String domainName) {
		return QualifiedName.create(domainName, DOMAIN_METACLASS_SEPARATOR_SEGMENT)
	}

	static def hasDomainName(QualifiedName name) {
		return (name.segmentCount > 1 && name.getSegment(1) == DOMAIN_METACLASS_SEPARATOR_SEGMENT)
	}

	static def getDomainName(QualifiedName name) {
		return (name.hasDomainName) ? name.getSegment(0) : null
	}

	static def getClassName(QualifiedName name) {
		return (name.segmentCount > 2 && name.hasDomainName) ? name.getSegment(2) : null
	}

	static def getMemberName(QualifiedName name) {
		return (name.segmentCount > 3 && name.hasDomainName) ? name.getSegment(3) : null
	}
}
