package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.CommonalityReference

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageElementExtension.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalityReferenceMappingExtension.*
import tools.vitruv.dsls.commonalities.language.Commonality

@Utility
package class CommonalityReferenceExtension {
	static def getMappings(CommonalityReference reference, String domainName) {
		reference.mappings.filter [participation.domainName == domainName].toList
	}
	
	static def getDeclaringCommonality(CommonalityReference reference) {
		reference.getDirectEContainer(Commonality)
	}
}
