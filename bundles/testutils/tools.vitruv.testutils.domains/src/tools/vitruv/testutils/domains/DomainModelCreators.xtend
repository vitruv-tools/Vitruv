package tools.vitruv.testutils.domains

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.testutils.metamodels.AllElementTypesCreators
import tools.vitruv.testutils.metamodels.AllElementTypes2Creators
import tools.vitruv.testutils.metamodels.PcmMockupCreators
import tools.vitruv.testutils.metamodels.UmlMockupCreators

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
	
	static def getDomain(AllElementTypesCreators creators) {
		new AllElementTypesDomainProvider().domain
	}
	
	static def getDomain(AllElementTypes2Creators creators) {
		new AllElementTypes2DomainProvider().domain
	}
	
	static def getDomain(PcmMockupCreators creators) {
		new PcmMockupDomainProvider().domain
	}
	
	static def getDomain(UmlMockupCreators creators) {
		new UmlMockupDomainProvider().domain
	}
}
