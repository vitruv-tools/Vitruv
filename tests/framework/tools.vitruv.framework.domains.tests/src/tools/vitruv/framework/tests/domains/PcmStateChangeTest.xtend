package tools.vitruv.framework.tests.domains

import org.junit.Test
import pcm_mockup.Repository
import pcm_mockup.Pcm_mockupFactory

class PcmStateChangeTest extends StateChangePropagationTest {

	@Test
	def void testAddComponent() {
		vsum.executeCommand([
			var repository = pcmModelInstance.getUniqueRootEObjectIfCorrectlyTyped(Repository)
			val component = Pcm_mockupFactory.eINSTANCE.createComponent
			component.name = "NewlyAddedComponent"
			repository.components.add(component)
			return null
		])
		pcmModelInstance.compareRecordedChanges
	}

	@Test
	def void testRenameComponent() {
		vsum.executeCommand([
			var repository = pcmModelInstance.getUniqueRootEObjectIfCorrectlyTyped(Repository)
			val component = repository.components.get(0)
			component.name = "RenamedComponent"
			return null
		])
		pcmModelInstance.compareRecordedChanges
	}

	@Test
	def void testDeleteComponent() {
		vsum.executeCommand([
			var repository = pcmModelInstance.getUniqueRootEObjectIfCorrectlyTyped(Repository)
			repository.components.remove(0)
			return null
		])
		pcmModelInstance.compareRecordedChanges
	}

	@Test
	def void testAddInterfaceWithMethod() {
		vsum.executeCommand([
			var repository = pcmModelInstance.getUniqueRootEObjectIfCorrectlyTyped(Repository)
			val component = repository.components.get(0)
			val pInterface = Pcm_mockupFactory.eINSTANCE.createPInterface
			pInterface.name = "NewlyAddedInterface"
			val pMethod = Pcm_mockupFactory.eINSTANCE.createPMethod
			pMethod.name = "newMethod"
			pInterface.methods.add(pMethod)
			repository.interfaces.add(pInterface)
			component.providedInterface = pInterface
			return null
		])
		pcmModelInstance.compareRecordedChanges
	}
}
