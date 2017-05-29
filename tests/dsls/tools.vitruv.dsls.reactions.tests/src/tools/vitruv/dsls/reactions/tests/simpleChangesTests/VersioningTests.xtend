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
import java.util.ArrayList
import tools.vitruv.framework.change.description.TransactionalChange
import java.util.List
import tools.vitruv.framework.versioning.VersioningFacade
import allElementTypes.NonRootObjectContainerHelper

class VersioningTests extends AbstractAllElementTypesReactionsTests {
	static val TEST_SOURCE_MODEL_NAME = "EachTestModelSource"
	static val TEST_TARGET_MODEL_NAME = "EachTestModelTarget"
	val nonContainmentNonRootIds = #["NonRootHelper0", "NonRootHelper1", "NonRootHelper2"]

	protected override setup() {
	}

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

	private def createAndAddNonRoot(String id, NonRootObjectContainerHelper container) {
		val nonRoot = AllElementTypesFactory::eINSTANCE.createNonRoot
		nonRoot.id = id
		container.nonRootObjectsContainment.add(nonRoot)
	}

	override protected cleanup() {
		// Do nothing
	}

	@Test
	def test1() {
		val root = AllElementTypesFactory::eINSTANCE.createRoot
		root.id = TEST_SOURCE_MODEL_NAME
		createAndSynchronizeModel(TEST_SOURCE_MODEL_NAME.projectModelPath, root)
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = "NonRootObjectContainer"
		rootElement.nonRootObjectContainerHelper = container
		nonContainmentNonRootIds.forEach[createAndAddNonRoot(it, container)]
		val recorder = new AtomicEmfChangeRecorder
		val resourcePlatformPath = '''«currentTestProject.name»/«TEST_TARGET_MODEL_NAME.projectModelPath»'''
		val resourceVuri = VURI::getInstance(resourcePlatformPath)
		val modelInstance = virtualModel.getModelInstance(resourceVuri)
		recorder.beginRecording(resourceVuri, Collections::singleton(modelInstance.resource))

		saveAndSynchronizeChanges(rootElement)

		assertModelsEqual

		val List<TransactionalChange> changes = new ArrayList<TransactionalChange>

		virtualModel.executeCommand [|
			changes += recorder.endRecording
			null
		]
		Assert::assertEquals(4, changes.length)
	}

	@Test
	def testAddPathToRecorded() {
		val root = AllElementTypesFactory::eINSTANCE.createRoot
		root.id = TEST_SOURCE_MODEL_NAME
		createAndSynchronizeModel(TEST_SOURCE_MODEL_NAME.projectModelPath, root)
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = "NonRootObjectContainer"
		rootElement.nonRootObjectContainerHelper = container
		nonContainmentNonRootIds.forEach[createAndAddNonRoot(it, container)]
		val facade = new VersioningFacade(virtualModel)
		registerObserver(facade)
		Assert::assertEquals(0, facade.changeUpdates.length)
		val resourcePlatformPath = '''«currentTestProject.name»/«TEST_TARGET_MODEL_NAME.projectModelPath»'''
		facade.addPathToRecorded(resourcePlatformPath)

		saveAndSynchronizeChanges(rootElement)
		Assert::assertEquals(1, facade.changeUpdates.length)
		val originalChanges = facade.changeUpdates.get(0)
		Assert::assertEquals(4, originalChanges.length)

		assertModelsEqual
		val changes = facade.getChanges(resourcePlatformPath)
		Assert::assertEquals(4, changes.length)
	}

	@Test
	def repoTest() {
		val root = AllElementTypesFactory::eINSTANCE.createRoot
		root.id = TEST_SOURCE_MODEL_NAME
//		val facade = new VersioningFacade
//		facade.createModel(TEST_SOURCE_MODEL_NAME.projectModelPath,root)
	}

}
