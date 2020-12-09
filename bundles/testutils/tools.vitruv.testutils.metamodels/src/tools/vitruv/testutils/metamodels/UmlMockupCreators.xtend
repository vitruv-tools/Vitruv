package tools.vitruv.testutils.metamodels

import uml_mockup.Uml_mockupFactory
import tools.vitruv.testutils.activeannotations.ModelCreators

@ModelCreators(factory=Uml_mockupFactory, stripPrefix = "U")
final class UmlMockupCreators {
	public static val uml = new UmlMockupCreators

	private new() {
	}
}
