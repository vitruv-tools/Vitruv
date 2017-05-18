package tools.vitruv.dsls.reactions.tests.simpleChangesTests

import allElementTypes.AllElementTypesFactory
import org.junit.Test
import org.junit.Assert

class VersioningTests extends SimpleChangesTests {
	
	protected override setup() {
		val root = AllElementTypesFactory::eINSTANCE.createRoot
		root.id = TEST_SOURCE_MODEL_NAME
		createAndSynchronizeModel(TEST_SOURCE_MODEL_NAME.projectModelPath, root)
				val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = "NonRootObjectContainer"
		rootElement.nonRootObjectContainerHelper = container
		nonContainmentNonRootIds.forEach [
			val nonRoot = AllElementTypesFactory::eINSTANCE.createNonRoot
			nonRoot.id = it
			container.nonRootObjectsContainment.add(nonRoot)
		]
		saveAndSynchronizeChanges(rootElement)
		assertModelsEqual
	}
	
	@Test
	def void test1() {
		Assert::assertTrue(true)
	}
}
