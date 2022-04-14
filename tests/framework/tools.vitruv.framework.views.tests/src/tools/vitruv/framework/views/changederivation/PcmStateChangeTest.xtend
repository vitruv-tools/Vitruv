package tools.vitruv.framework.views.changederivation

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

import static tools.vitruv.testutils.metamodels.PcmMockupCreators.pcm

class PcmStateChangeTest extends StateChangePropagationTest {
	@ParameterizedTest(name = "{1}")
	@MethodSource("strategiesToTest")
	def void testAddComponent(StateBasedChangeResolutionStrategy strategyToTest, String strategyName) {
		pcmRoot.components += pcm.Component => [name = "NewlyAddedComponent"]
		compareChanges(pcmModel, pcmCheckpoint, strategyToTest)
	}

	@ParameterizedTest(name = "{1}")
	@MethodSource("strategiesToTest")
	def void testRenameComponent(StateBasedChangeResolutionStrategy strategyToTest, String name) {
		pcmRoot.components.get(0).name = "RenamedComponent"
		compareChanges(pcmModel, pcmCheckpoint, strategyToTest)
	}

	@ParameterizedTest(name = "{1}")
	@MethodSource("strategiesToTest")
	def void testDeleteComponent(StateBasedChangeResolutionStrategy strategyToTest, String name) {
		pcmRoot.components.remove(0)
		compareChanges(pcmModel, pcmCheckpoint, strategyToTest)
	}

	@ParameterizedTest(name = "{1}")
	@MethodSource("strategiesToTest")
	def void testAddProvidedInterface(StateBasedChangeResolutionStrategy strategyToTest, String strategyName) {
		val newInterface = pcm.Interface => [name = "NewlyAddedInterface"]
		pcmRoot.interfaces += pcm.Interface
		newInterface.methods += pcm.Method => [name = "newMethod"]
		pcmRoot.components.get(0).providedInterface = newInterface
		compareChanges(pcmModel, pcmCheckpoint, strategyToTest)
	}

	@ParameterizedTest(name = "{1}")
	@MethodSource("strategiesToTest")
	def void testInterfaceWithMultipleMethods(StateBasedChangeResolutionStrategy strategyToTest, String strategyName) {
		val newInterface = pcm.Interface => [
			name = "NewlyAddedInterface"
		]
		pcmRoot.interfaces += newInterface
		newInterface.methods += (0 .. 5).map [ index |
			pcm.Method => [name = '''newMethod«index»''']
		]
		pcmRoot.components.get(0).providedInterface = newInterface
		compareChanges(pcmModel, pcmCheckpoint, strategyToTest)
	}

	@ParameterizedTest(name = "{1}")
	@MethodSource("strategiesToTest")
	def void testAddDifferentProvidedInterface(StateBasedChangeResolutionStrategy strategyToTest, String strategyName) {
		val firstInterface = pcm.Interface => [name = "NewlyAddedInterface"]
		val secondInterface = pcm.Interface => [name = "NewlyAddedInterface2"]
		pcmRoot.interfaces += #[firstInterface, secondInterface]
		pcmRoot.components.get(0).providedInterface = firstInterface
		pcmRoot.components.get(0).providedInterface = secondInterface
		compareChanges(pcmModel, pcmCheckpoint, strategyToTest)
	}

	@ParameterizedTest(name = "{1}")
	@MethodSource("strategiesToTest")
	def void testAddMultipleInterfaces(StateBasedChangeResolutionStrategy strategyToTest, String strategyName) {
		pcmRoot.interfaces += (1 .. 3).map [ index |
			pcm.Interface => [name = '''NewlyAddedInterface«index»''']
		]
		pcmRoot.interfaces.forEach[methods += pcm.Method => [name = "newMethod"]]
		compareChanges(pcmModel, pcmCheckpoint, strategyToTest)
	}
}
