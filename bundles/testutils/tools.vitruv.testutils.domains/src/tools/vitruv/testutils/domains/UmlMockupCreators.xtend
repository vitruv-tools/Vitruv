package tools.vitruv.testutils.domains

import uml_mockup.Uml_mockupFactory
import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.testutils.util.DomainUtil

@Utility
class UmlMockupCreators {
	static def uml_mockup(String modelName) {
		DomainUtil.getModelFileName(modelName, new UmlMockupDomainProvider)
	}

	static def newUMLPackage() {
		Uml_mockupFactory.eINSTANCE.createUPackage
	}

	static def newUMLClass() {
		Uml_mockupFactory.eINSTANCE.createUClass
	}
}
