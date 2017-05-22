package tools.vitruv.dsls.reactions.tests.simpleChangesTests

import allElementTypes.AllElementTypesFactory
import allElementTypes.Root
import java.util.Collections
import mir.reactions.AbstractChangePropagationSpecificationAllElementTypesToAllElementTypes
import org.junit.Assert
import org.junit.Test
import tools.vitruv.dsls.reactions.tests.AbstractAllElementTypesReactionsTests
import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder
import tools.vitruv.framework.util.datatypes.VURI
import org.junit.Ignore

class VersioningTests extends AbstractAllElementTypesReactionsTests {
	static val TEST_SOURCE_MODEL_NAME = "EachTestModelSource"
	static val TEST_TARGET_MODEL_NAME = "EachTestModelTarget"
	val nonContainmentNonRootIds = #["NonRootHelper0", "NonRootHelper1", "NonRootHelper2"]

	private def String getProjectModelPath(String modelName) {
		'''model/«modelName».«MODEL_FILE_EXTENSION»'''
	}

	override protected createChangePropagationSpecifications() {
		#[new AbstractChangePropagationSpecificationAllElementTypesToAllElementTypes {
		}]
	}

	private def Root getRootElement() {
		TEST_SOURCE_MODEL_NAME.projectModelPath.firstRootElement as Root
	}

	private def assertModelsEqual() {
		assertPersistedModelsEqual(TEST_SOURCE_MODEL_NAME.projectModelPath, TEST_TARGET_MODEL_NAME.projectModelPath)
	}

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
		val recorder = new AtomicEmfChangeRecorder
		val resource = getModelResource(TEST_TARGET_MODEL_NAME.projectModelPath)
		val vuri = VURI::getInstance(resource)
		recorder.beginRecording(vuri, Collections::singleton(resource))
		// Changes matchen ?
		// SaveAndSynchonize aufspalten => 
		// bisher alles im CompositeChange 
		//
		saveAndSynchronizeChanges(rootElement)
		assertModelsEqual
		val changes = recorder.endRecording
		
		Assert::assertNotEquals(0, changes.length)
	}

	override protected cleanup() {
		// Do nothing
	}
	
	@Ignore
	@Test
	def void test1() {
		Assert::assertTrue(true)
	}

}
