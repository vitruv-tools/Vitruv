package tools.vitruv.testutils.domains

import edu.kit.ipd.sdq.activextendannotations.Utility
import pcm_mockup.Pcm_mockupFactory

@Utility
class PcmMockupCreators {
	static def newRepository() {
		Pcm_mockupFactory.eINSTANCE.createRepository
	}

	static def newComponent() {
		Pcm_mockupFactory.eINSTANCE.createComponent
	}
}
