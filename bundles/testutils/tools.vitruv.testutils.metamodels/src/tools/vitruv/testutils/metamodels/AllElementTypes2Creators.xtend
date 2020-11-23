package tools.vitruv.testutils.metamodels

import edu.kit.ipd.sdq.activextendannotations.Utility
import allElementTypes2.AllElementTypes2Factory

@Utility
class AllElementTypes2Creators {
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
