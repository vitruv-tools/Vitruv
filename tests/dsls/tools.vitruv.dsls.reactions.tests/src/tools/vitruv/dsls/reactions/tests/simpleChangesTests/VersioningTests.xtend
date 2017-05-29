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
import static org.junit.Assert.assertThat
import static org.hamcrest.CoreMatchers.is

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
		val resourcePlatformPath = '''«currentTestProject.name»/«TEST_TARGET_MODEL_NAME.projectModelPath»'''
		val resourceVuri = VURI::getInstance(resourcePlatformPath)
		facade.addPathToRecorded(resourceVuri)

		saveAndSynchronizeChanges(rootElement)

		assertModelsEqual
		val changes = facade.getChanges(resourceVuri)
		Assert::assertEquals(4, changes.length)
	}

	@Test
	def synchronizeTest() {
		// Create model 
		val root = AllElementTypesFactory::eINSTANCE.createRoot
		root.id = TEST_SOURCE_MODEL_NAME
		createAndSynchronizeModel(TEST_SOURCE_MODEL_NAME.projectModelPath, root)

		// Setup facade 
		val facade = new VersioningFacade(virtualModel)
		registerObserver(facade)
		val resourcePlatformPath = '''«currentTestProject.name»/«TEST_TARGET_MODEL_NAME.projectModelPath»'''
		val resourceVuri = VURI::getInstance(resourcePlatformPath)
		facade.addPathToRecorded(resourceVuri)

		// Create container and synchronize 
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = "NonRootObjectContainer"
		rootElement.nonRootObjectContainerHelper = container
		saveAndSynchronizeChanges(rootElement)

		nonContainmentNonRootIds.forEach [
			createAndAddNonRoot(it, container)
			saveAndSynchronizeChanges(root)
			assertModelsEqual
		]
	}

	@Test
	def void recordOriginalAndCorrespondentChanges() {
		// Create model 
		val targetPath = '''«currentTestProject.name»/«TEST_TARGET_MODEL_NAME.projectModelPath»'''
		val sourcePath = TEST_SOURCE_MODEL_NAME.projectModelPath
		val root = AllElementTypesFactory::eINSTANCE.createRoot
		root.id = TEST_SOURCE_MODEL_NAME
		createAndSynchronizeModel(sourcePath, root)

		// Setup facade 
		val facade = new VersioningFacade(virtualModel)
		registerObserver(facade)
		val targetVURI = VURI::getInstance(targetPath)
		val sourceVURI = VURI::getInstance(sourcePath)
		facade.recordOriginalAndCorrespondentChanges(sourceVURI, #[targetVURI])

		// Create container and synchronize 
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = "NonRootObjectContainer"
		rootElement.nonRootObjectContainerHelper = container
		saveAndSynchronizeChanges(rootElement)

		// Create and add non roots
		nonContainmentNonRootIds.forEach [
			createAndAddNonRoot(it, container)
			saveAndSynchronizeChanges(root)
			assertModelsEqual
		]
		assertThat(facade.changesMatches.length, is(4))
		assertThat(facade.changesMatches.forall[originalVURI == sourceVURI], is(true))
		assertThat(facade.changesMatches.forall[null !== targetToCorrespondentChanges.get(targetVURI)], is(true))
		assertThat(facade.changesMatches.forall[targetToCorrespondentChanges.get(targetVURI).length == 1], is(true))
	}

}
