package tools.vitruv.framework.tests.domains

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Disabled
import static tools.vitruv.testutils.metamodels.PcmMockupCreators.newPcmComponent
import static tools.vitruv.testutils.metamodels.PcmMockupCreators.newPcmInterface
import static tools.vitruv.testutils.metamodels.PcmMockupCreators.newPcmMethod

class PcmStateChangeTest extends StateChangePropagationTest {
	@Test
	def void testAddComponent() {
		pcmRoot.components += newPcmComponent => [name = "NewlyAddedComponent"]
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
		val newInterface = newPcmInterface => [name = "NewlyAddedInterface"]
		pcmRoot.interfaces += newInterface
		newInterface.methods += newPcmMethod => [name = "newMethod"]
		pcmRoot.components.get(0).providedInterface = newInterface
		compareChanges(pcmModel, pcmCheckpoint)
	}

	@Test
	def void testInterfaceWithMultipleMethods() {
		val newInterface = newPcmInterface => [
			name = "NewlyAddedInterface"
		]
		pcmRoot.interfaces += newInterface
		newInterface.methods += (0 .. 5).map [ index |
			newPcmMethod => [name = '''newMethod«index»''']
		]
		pcmRoot.components.get(0).providedInterface = newInterface
		compareChanges(pcmModel, pcmCheckpoint)
	}

	@Disabled("Example for a test case that will NOT pass since the state-based diff looses some information")
	@Test
	def void testAddDifferentProvidedInterface() {
		val firstInterface = newPcmInterface => [name = "NewlyAddedInterface"]
		val secondInterface = newPcmInterface => [name = "NewlyAddedInterface2"]
		pcmRoot.interfaces += #[firstInterface, secondInterface]
		pcmRoot.components.get(0).providedInterface = firstInterface
		pcmRoot.components.get(0).providedInterface = secondInterface
		compareChanges(pcmModel, pcmCheckpoint)
	}

	@Test
	def void testAddMultipleInterfaces() {
		pcmRoot.interfaces += (1 .. 3).map [ index |
			newPcmInterface => [name = '''NewlyAddedInterface«index»''']
		]
		pcmRoot.interfaces.forEach[methods += newPcmMethod => [name = "newMethod"]]
		compareChanges(pcmModel, pcmCheckpoint)
	}
}
