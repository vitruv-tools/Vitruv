package tools.vitruv.framework.views.changederivation

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

import static tools.vitruv.testutils.metamodels.UmlMockupCreators.uml

class UmlStateChangeTest extends StateChangePropagationTest {
	@ParameterizedTest(name = "{1}")
	@MethodSource("strategiesToTest")
	def void testRenameTypes(StateBasedChangeResolutionStrategy strategyToTest, String strategyName) {
		umlRoot.classes.get(0) => [name = "RenamedClass"]
		umlRoot.interfaces.get(0) => [name = "RenamedInterface"]
		compareChanges(umlModel, umlCheckpoint, strategyToTest)
	}

	@ParameterizedTest(name = "{1}")
	@MethodSource("strategiesToTest")
	def void testNewAttributes(StateBasedChangeResolutionStrategy strategyToTest, String name) {
		umlRoot.classes.get(0) => [
			attributes += uml.Attribute => [attributeName = "NewlyAddedAttribute"]
		]
		compareChanges(umlModel, umlCheckpoint, strategyToTest)
	}

	@ParameterizedTest(name = "{1}")
	@MethodSource("strategiesToTest")
	def void testNewMethod(StateBasedChangeResolutionStrategy strategyToTest, String strategyName) {
		umlRoot.interfaces.get(0) => [
			methods += uml.Method => [name = "NewlyAddedMethod"]
		]
		compareChanges(umlModel, umlCheckpoint, strategyToTest)
	}

	@ParameterizedTest(name = "{1}")
	@MethodSource("strategiesToTest")
	def void testNewClass(StateBasedChangeResolutionStrategy strategyToTest, String strategyName) {
		umlRoot.classes += uml.Class => [name = "NewlyAddedClass"]
		compareChanges(umlModel, umlCheckpoint, strategyToTest)
	}

	@ParameterizedTest(name = "{1}")
	@MethodSource("strategiesToTest")
	def void testReplaceClass(StateBasedChangeResolutionStrategy strategyToTest, String strategyName) {
		umlRoot.classes.remove(0)
		umlRoot.classes += uml.Class => [name = "NewlyAddedClass"]
		compareChanges(umlModel, umlCheckpoint, strategyToTest)
	}

	@ParameterizedTest(name = "{1}")
	@MethodSource("strategiesToTest")
	def void testDeleteClass(StateBasedChangeResolutionStrategy strategyToTest, String name) {
		umlRoot.classes.remove(0)
		compareChanges(umlModel, umlCheckpoint, strategyToTest)
	}

	@ParameterizedTest(name = "{1}")
	@MethodSource("strategiesToTest")
	def void testNewInterface(StateBasedChangeResolutionStrategy strategyToTest, String strategyName) {
		umlRoot.interfaces += uml.Interface => [name = "NewlyAddedInterface"]
		compareChanges(umlModel, umlCheckpoint, strategyToTest)
	}
}
