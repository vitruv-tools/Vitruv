package tools.vitruv.testutils.domains

import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility
class DomainModelCreators {
	static def allElementTypes2(String modelName) {
		DomainUtil.getModelFileName(modelName, new AllElementTypes2DomainProvider)
	}

	static def allElementTypes(String modelName) {
		DomainUtil.getModelFileName(modelName, new AllElementTypesDomainProvider)
	}

	static def pcm_mockup(String modelName) {
		DomainUtil.getModelFileName(modelName, new PcmMockupDomainProvider)
	}

	static def uml_mockup(String modelName) {
		DomainUtil.getModelFileName(modelName, new UmlMockupDomainProvider)
	}
}
