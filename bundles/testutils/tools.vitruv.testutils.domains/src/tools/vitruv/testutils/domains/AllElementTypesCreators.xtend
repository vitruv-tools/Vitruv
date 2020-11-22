package tools.vitruv.testutils.domains

import edu.kit.ipd.sdq.activextendannotations.Utility
import allElementTypes.AllElementTypesFactory
import tools.vitruv.testutils.util.DomainUtil

@Utility
class AllElementTypesCreators {
	static def allElementTypes(String modelName) {
		DomainUtil.getModelFileName(modelName, new AllElementTypesDomainProvider)
	}

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
