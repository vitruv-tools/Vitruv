package tools.vitruv.testutils.domains

import edu.kit.ipd.sdq.activextendannotations.Utility
import pcm_mockup.Pcm_mockupFactory
import tools.vitruv.testutils.util.DomainUtil

@Utility
class PcmMockupCreators {
	static def pcm_mockup(String modelName) {
		DomainUtil.getModelFileName(modelName, new PcmMockupDomainProvider)
	}

	static def newRepository() {
		Pcm_mockupFactory.eINSTANCE.createRepository
	}

	static def newComponent() {
		Pcm_mockupFactory.eINSTANCE.createComponent
	}
}
