package tools.vitruv.dsls.commonalities.generator.domain

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.common.GenericClassNameGenerator

import static extension tools.vitruv.dsls.commonalities.generator.intermediatemodel.IntermediateModelConstants.*

@Utility
class ConceptDomainConstants {

	@Pure
	static def getConceptDomainName(String conceptName) {
		conceptName
	}

	@Pure
	static def getConceptDomainProviderClassName(String conceptName) {
		new GenericClassNameGenerator(conceptName.intermediateMetamodelPackageName,
			conceptName.conceptDomainName.toFirstUpper + 'DomainProvider')
	}

}
