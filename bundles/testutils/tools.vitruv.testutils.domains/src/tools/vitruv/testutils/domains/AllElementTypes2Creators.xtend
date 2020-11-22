package tools.vitruv.testutils.domains

import edu.kit.ipd.sdq.activextendannotations.Utility
import allElementTypes2.AllElementTypes2Factory
import tools.vitruv.testutils.util.DomainUtil

@Utility
class AllElementTypes2Creators {
	static def allElementTypes2(String modelName) {
		DomainUtil.getModelFileName(modelName, new AllElementTypes2DomainProvider)
	}

	static def newRoot2() {
		AllElementTypes2Factory.eINSTANCE.createRoot2
	}

	static def newNonRoot2() {
		AllElementTypes2Factory.eINSTANCE.createNonRoot2
	}

	static def newNonRootObjectContainerHelper2() {
		AllElementTypes2Factory.eINSTANCE.createNonRootObjectContainerHelper2
	}
}
