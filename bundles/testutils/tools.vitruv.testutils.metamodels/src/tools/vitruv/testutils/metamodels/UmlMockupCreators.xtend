package tools.vitruv.testutils.metamodels

import uml_mockup.Uml_mockupFactory
import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility
class UmlMockupCreators {
	static def newUmlPackage() {
		Uml_mockupFactory.eINSTANCE.createUPackage
	}
	
	static def newUmlInterface() {
		Uml_mockupFactory.eINSTANCE.createUInterface
	}

	static def newUmlClass() {
		Uml_mockupFactory.eINSTANCE.createUClass
	}
	
	static def newUmlMethod() {
		Uml_mockupFactory.eINSTANCE.createUMethod
	}
	
	static def newUmlAttribute() {
		Uml_mockupFactory.eINSTANCE.createUAttribute
	}
}
