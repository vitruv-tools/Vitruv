package tools.vitruv.testutils.metamodels

import edu.kit.ipd.sdq.activextendannotations.Utility
import allElementTypes.AllElementTypesFactory

@Utility
class AllElementTypesCreators {
	static def newRoot() {
		AllElementTypesFactory.eINSTANCE.createRoot
	}

	static def newNonRoot() {
		AllElementTypesFactory.eINSTANCE.createNonRoot
	}

	static def newNonRootObjectContainerHelper() {
		AllElementTypesFactory.eINSTANCE.createNonRootObjectContainerHelper
	}
}
