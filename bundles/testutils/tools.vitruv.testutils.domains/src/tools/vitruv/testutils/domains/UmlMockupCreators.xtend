package tools.vitruv.testutils.domains

import uml_mockup.Uml_mockupFactory
import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility
class UmlMockupCreators {
	static def newUMLPackage() {
		Uml_mockupFactory.eINSTANCE.createUPackage
	}

	static def newUMLClass() {
		Uml_mockupFactory.eINSTANCE.createUClass
	}
}
