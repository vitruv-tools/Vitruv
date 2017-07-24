package tools.vitruv.dsls.commonalities.language.elements.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.elements.Metaclass
import org.eclipse.xtext.naming.QualifiedName

@Utility
package class MetaclassExtension {

	def static describedBy(Metaclass metaclass, QualifiedName name) {
		name.segmentCount == 2 && metaclass.domain.name == name.getSegment(0) && metaclass.name == name.getSegment(1)
	}
}
