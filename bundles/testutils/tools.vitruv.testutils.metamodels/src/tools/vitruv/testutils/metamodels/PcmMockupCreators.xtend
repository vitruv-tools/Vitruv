package tools.vitruv.testutils.metamodels

import edu.kit.ipd.sdq.activextendannotations.Utility
import pcm_mockup.Pcm_mockupFactory

@Utility
class PcmMockupCreators {
	static def newPcmRepository() {
		Pcm_mockupFactory.eINSTANCE.createRepository
	}

	static def newPcmInterface() {
		Pcm_mockupFactory.eINSTANCE.createPInterface
	}

	static def newPcmComponent() {
		Pcm_mockupFactory.eINSTANCE.createComponent
	}

	static def newPcmMethod() {
		Pcm_mockupFactory.eINSTANCE.createPMethod
	}
}
