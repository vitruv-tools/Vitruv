package tools.vitruv.framework.tests.domains

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Disabled
import static tools.vitruv.testutils.metamodels.PcmMockupCreators.pcm

class PcmStateChangeTest extends StateChangePropagationTest {
	@Test
	def void testAddComponent() {
		pcmRoot.components += pcm.Component => [name = "NewlyAddedComponent"]
		compareChanges(pcmModel, pcmCheckpoint)
	}

	@Test
	def void testRenameComponent() {
		pcmRoot.components.get(0).name = "RenamedComponent"
		compareChanges(pcmModel, pcmCheckpoint)
	}

	@Test
	def void testDeleteComponent() {
		pcmRoot.components.remove(0)
		compareChanges(pcmModel, pcmCheckpoint)
	}

	@Test
	def void testAddProvidedInterface() {
		val newInterface = pcm.Interface => [name = "NewlyAddedInterface"]
		pcmRoot.interfaces += pcm.Interface
		newInterface.methods += pcm.Method => [name = "newMethod"]
		pcmRoot.components.get(0).providedInterface = newInterface
		compareChanges(pcmModel, pcmCheckpoint)
	}

	@Test
	def void testInterfaceWithMultipleMethods() {
		val newInterface = pcm.Interface => [
			name = "NewlyAddedInterface"
		]
		pcmRoot.interfaces += newInterface
		newInterface.methods += (0 .. 5).map [ index |
			pcm.Method => [name = '''newMethod«index»''']
		]
		pcmRoot.components.get(0).providedInterface = newInterface
		compareChanges(pcmModel, pcmCheckpoint)
	}

	@Disabled("Example for a test case that will NOT pass since the state-based diff looses some information")
	@Test
	def void testAddDifferentProvidedInterface() {
		val firstInterface = pcm.Interface => [name = "NewlyAddedInterface"]
		val secondInterface = pcm.Interface => [name = "NewlyAddedInterface2"]
		pcmRoot.interfaces += #[firstInterface, secondInterface]
		pcmRoot.components.get(0).providedInterface = firstInterface
		pcmRoot.components.get(0).providedInterface = secondInterface
		compareChanges(pcmModel, pcmCheckpoint)
	}

	@Test
	def void testAddMultipleInterfaces() {
		pcmRoot.interfaces += (1 .. 3).map [ index |
			pcm.Interface => [name = '''NewlyAddedInterface«index»''']
		]
		pcmRoot.interfaces.forEach[methods += pcm.Method => [name = "newMethod"]]
		compareChanges(pcmModel, pcmCheckpoint)
	}
}
