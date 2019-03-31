package tools.vitruv.framework.tests.domains

import org.junit.Test
import pcm_mockup.Pcm_mockupFactory

class PcmStateChangeTest extends StateChangePropagationTest {

	@Test
	def void testAddComponent() {
		val component = Pcm_mockupFactory.eINSTANCE.createComponent
		component.name = "NewlyAddedComponent"
		pcmRoot.components.add(component)
		compareChanges(pcmModel, pcmCheckpoint)
	}

	@Test
	def void testRenameComponent() {
		val component = pcmRoot.components.get(0)
		component.name = "RenamedComponent"
		compareChanges(pcmModel, pcmCheckpoint)
	}

	// @Ignore // TS (HIGH) Object has no UUID exception when replaying
	@Test
	def void testDeleteComponent() {
		pcmRoot.components.remove(0)
		compareChanges(pcmModel, pcmCheckpoint)
	}

	@Test
	def void testAddProvidedInterface() {
		val pInterface = Pcm_mockupFactory.eINSTANCE.createPInterface
		pInterface.name = "NewlyAddedInterface"
		pcmRoot.interfaces.add(pInterface)
		val pMethod = Pcm_mockupFactory.eINSTANCE.createPMethod
		pMethod.name = "newMethod"
		pInterface.methods.add(pMethod)
		val component = pcmRoot.components.get(0)
		component.providedInterface = pInterface
		compareChanges(pcmModel, pcmCheckpoint)
	}

	@Test
	def void testInterfaceWithMultipleMethods() {
		val pInterface = Pcm_mockupFactory.eINSTANCE.createPInterface
		pInterface.name = "NewlyAddedInterface"
		pcmRoot.interfaces.add(pInterface)
		for (i : 0 .. 5) {
			val pMethod = Pcm_mockupFactory.eINSTANCE.createPMethod
			pMethod.name = "newMethod" + i
			pInterface.methods.add(pMethod)
		}
		val component = pcmRoot.components.get(0)
		component.providedInterface = pInterface
		compareChanges(pcmModel, pcmCheckpoint)
	}

	@Test
	def void testAddDifferentProvidedInterface() { // TODO TS (HIGH) breaks comparison
		val pInterface = Pcm_mockupFactory.eINSTANCE.createPInterface
		pInterface.name = "NewlyAddedInterface"
		pcmRoot.interfaces.add(pInterface)
		val component = pcmRoot.components.get(0)
		component.providedInterface = pInterface
		val pInterface2 = Pcm_mockupFactory.eINSTANCE.createPInterface
		pInterface2.name = "NewlyAddedInterface2"
		pcmRoot.interfaces.add(pInterface2)
		component.providedInterface = pInterface2
		compareChanges(pcmModel, pcmCheckpoint)
	}

	@Test
	def void testAddMultipleInterfaces() {
		// TODO (TS) remove duplication
		val pInterface1 = Pcm_mockupFactory.eINSTANCE.createPInterface
		pInterface1.name = "NewlyAddedInterface"
		pcmRoot.interfaces.add(pInterface1)
		val pMethod1 = Pcm_mockupFactory.eINSTANCE.createPMethod
		pMethod1.name = "newMethod"
		pInterface1.methods.add(pMethod1)
		val pInterface2 = Pcm_mockupFactory.eINSTANCE.createPInterface
		pInterface2.name = "NewlyAddedInterface"
		pcmRoot.interfaces.add(pInterface2)
		val pMethod2 = Pcm_mockupFactory.eINSTANCE.createPMethod
		pMethod2.name = "newMethod"
		pInterface2.methods.add(pMethod2)
		val pMethod3 = Pcm_mockupFactory.eINSTANCE.createPMethod
		pMethod3.name = "newMethod"
		pInterface2.methods.add(pMethod3)
		compareChanges(pcmModel, pcmCheckpoint)
	}

}
