package tools.vitruv.dsls.commonalities.names

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.xtext.naming.QualifiedName

@Utility
class QualifiedNameHelper {
	public static val METAMODEL_METACLASS_SEPARATOR = ":"
	public static val METACLASS_ATTRIBUTE_SEPARATOR = "."
	public static val METAMODEL_METACLASS_SEPARATOR_SEGMENT = ":"

	static def getQualifiedDomainName(String domainName) {
		return QualifiedName.create(domainName, tools.vitruv.dsls.commonalities.names.QualifiedNameHelper.METAMODEL_METACLASS_SEPARATOR_SEGMENT)
	}

	static def hasMetamodelName(QualifiedName name) {
		return (name.segmentCount > 1 && name.getSegment(1) == tools.vitruv.dsls.commonalities.names.QualifiedNameHelper.METAMODEL_METACLASS_SEPARATOR_SEGMENT)
	}

	static def getMetamodelName(QualifiedName name) {
		return (name.hasMetamodelName) ? name.getSegment(0) : null
	}

	static def getClassName(QualifiedName name) {
		return (name.segmentCount > 2 && name.hasMetamodelName) ? name.getSegment(2) : null
	}

	static def getMemberName(QualifiedName name) {
		return (name.segmentCount > 3 && name.hasMetamodelName) ? name.getSegment(3) : null
	}
}
