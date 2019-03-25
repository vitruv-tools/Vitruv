package tools.vitruv.framework.tests.domains

import org.junit.Test
import pcm_mockup.Repository
import pcm_mockup.Pcm_mockupFactory
import org.junit.Ignore

class PcmStateChangeTest extends StateChangePropagationTest {

	@Test
	def void testAddComponent() {
		vsum.executeCommand [
			var repository = pcmModelInstance.getUniqueRootEObjectIfCorrectlyTyped(Repository)
			val component = Pcm_mockupFactory.eINSTANCE.createComponent
			component.name = "NewlyAddedComponent"
			repository.components.add(component)
			return null
		]
		pcmModelInstance.compareRecordedChanges
	}

	@Test
	def void testRenameComponent() {
		vsum.executeCommand [
			var repository = pcmModelInstance.getUniqueRootEObjectIfCorrectlyTyped(Repository)
			val component = repository.components.get(0)
			component.name = "RenamedComponent"
			return null
		]
		pcmModelInstance.compareRecordedChanges
	}

	@Ignore // TS (HIGH) Object has no UUID exception when replaying
	@Test
	def void testDeleteComponent() {
		vsum.executeCommand [
			var repository = pcmModelInstance.getUniqueRootEObjectIfCorrectlyTyped(Repository)
			repository.components.remove(0)
			return null
		]
		pcmModelInstance.compareRecordedChanges
	}

	@Test
	def void testAddProvidedInterface() {
		vsum.executeCommand [
			var repository = pcmModelInstance.getUniqueRootEObjectIfCorrectlyTyped(Repository)
			val pInterface = Pcm_mockupFactory.eINSTANCE.createPInterface
			pInterface.name = "NewlyAddedInterface"
			repository.interfaces.add(pInterface)
			val pMethod = Pcm_mockupFactory.eINSTANCE.createPMethod
			pMethod.name = "newMethod"
			pInterface.methods.add(pMethod)
			val component = repository.components.get(0)
			component.providedInterface = pInterface
			return null
		]
		pcmModelInstance.compareRecordedChanges
	}

	@Test
	def void testAddMultipleInterfaces() {
		vsum.executeCommand [ // TODO (TS) remove duplication
			var repository = pcmModelInstance.getUniqueRootEObjectIfCorrectlyTyped(Repository)
			val pInterface1 = Pcm_mockupFactory.eINSTANCE.createPInterface
			pInterface1.name = "NewlyAddedInterface"
			repository.interfaces.add(pInterface1)
			val pMethod1 = Pcm_mockupFactory.eINSTANCE.createPMethod
			pMethod1.name = "newMethod"
			pInterface1.methods.add(pMethod1)
			val pInterface2 = Pcm_mockupFactory.eINSTANCE.createPInterface
			pInterface2.name = "NewlyAddedInterface"
			repository.interfaces.add(pInterface2)
			val pMethod2 = Pcm_mockupFactory.eINSTANCE.createPMethod
			pMethod2.name = "newMethod"
			pInterface2.methods.add(pMethod2)
			val pMethod3 = Pcm_mockupFactory.eINSTANCE.createPMethod
			pMethod3.name = "newMethod"
			pInterface2.methods.add(pMethod3)
			return null
		]
		pcmModelInstance.compareRecordedChanges
	}

}
