package tools.vitruv.framework.tests.domains

import org.junit.jupiter.api.Test
import static tools.vitruv.testutils.metamodels.UmlMockupCreators.uml

class UmlStateChangeTest extends StateChangePropagationTest {
	@Test
	def void testRenameTypes() {
		umlRoot.classes.get(0) => [name = "RenamedClass"]
		umlRoot.interfaces.get(0) => [name = "RenamedInterface"]
		compareChanges(umlModel, umlCheckpoint)
	}

	@Test
	def void testNewAttributes() {
		umlRoot.classes.get(0) => [
			attributes += uml.Attribute => [attributeName = "NewlyAddedAttribute"]
		]
		compareChanges(umlModel, umlCheckpoint)
	}

	@Test
	def void testNewMethod() {
		umlRoot.interfaces.get(0) => [
			methods += uml.Method => [name = "NewlyAddedMethod"]
		]
		compareChanges(umlModel, umlCheckpoint)
	}

	@Test
	def void testNewClass() {
		umlRoot.classes += uml.Class => [name = "NewlyAddedClass"]
		compareChanges(umlModel, umlCheckpoint)
	}

	@Test
	def void testReplaceClass() {
		umlRoot.classes.remove(0)
		umlRoot.classes += uml.Class => [name = "NewlyAddedClass"]
		compareChanges(umlModel, umlCheckpoint)
	}

	@Test
	def void testDeleteClass() {
		umlRoot.classes.remove(0)
		compareChanges(umlModel, umlCheckpoint)
	}

	@Test
	def void testNewInterface() {
		umlRoot.interfaces += uml.Interface => [name = "NewlyAddedInterface"]
		compareChanges(umlModel, umlCheckpoint)
	}
}
