package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.CommonalityReference

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalityReferenceMappingExtension.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.ParticipationExtension.*

@Utility
package class CommonalityReferenceExtension {

	static def getMappings(CommonalityReference reference, String domainName) {
		return reference.mappings.filter[it.participation.domainName == domainName].toList
	}
}
