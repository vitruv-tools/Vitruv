package tools.vitruv.dsls.reactions.tests.simpleChangesTests

import allElementTypes.AllElementTypesFactory
import allElementTypes.NonRootObjectContainerHelper
import allElementTypes.Root

import java.util.ArrayList
import java.util.Collections
import java.util.List

import mir.reactions.AbstractChangePropagationSpecificationAllElementTypesToAllElementTypes

import org.junit.Assert
import org.junit.Test

import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat

import tools.vitruv.dsls.reactions.tests.AbstractAllElementTypesReactionsTests
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.impl.VersioningFacadeImpl
import tools.vitruv.framework.versioning.VersioningFacade

class VersioningTests extends AbstractAllElementTypesReactionsTests {
	static val TEST_SOURCE_MODEL_NAME = "EachTestModelSource"
	static val TEST_TARGET_MODEL_NAME = "EachTestModelTarget"
	static val NON_CONTAINMENT_NON_ROOT_IDS = #["NonRootHelper0", "NonRootHelper1", "NonRootHelper2"]

	@Test
	def testRecordingWithExecuteCommand() {
		val root = AllElementTypesFactory::eINSTANCE.createRoot
		root.id = TEST_SOURCE_MODEL_NAME
		createAndSynchronizeModel(TEST_SOURCE_MODEL_NAME.projectModelPath, root)
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = "NonRootObjectContainer"
		rootElement.nonRootObjectContainerHelper = container
		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(it, container)]
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
	def testFacadeAddPathToRecorded() {
		val root = AllElementTypesFactory::eINSTANCE.createRoot
		root.id = TEST_SOURCE_MODEL_NAME
		createAndSynchronizeModel(TEST_SOURCE_MODEL_NAME.projectModelPath, root)
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = "NonRootObjectContainer"
		rootElement.nonRootObjectContainerHelper = container
		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(it, container)]
		val facade = new VersioningFacadeImpl(virtualModel)
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
	def testSingleChangeSynchronization() {
		// Create model 
		val root = AllElementTypesFactory::eINSTANCE.createRoot
		root.id = TEST_SOURCE_MODEL_NAME
		createAndSynchronizeModel(TEST_SOURCE_MODEL_NAME.projectModelPath, root)

		// Setup facade 
		val facade = new VersioningFacadeImpl(virtualModel)
		registerObserver(facade)
		val resourcePlatformPath = '''«currentTestProject.name»/«TEST_TARGET_MODEL_NAME.projectModelPath»'''
		val resourceVuri = VURI::getInstance(resourcePlatformPath)
		facade.addPathToRecorded(resourceVuri)

		// Create container and synchronize 
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = "NonRootObjectContainer"
		rootElement.nonRootObjectContainerHelper = container
		saveAndSynchronizeChanges(rootElement)

		NON_CONTAINMENT_NON_ROOT_IDS.forEach [
			createAndAddNonRoot(it, container)
			saveAndSynchronizeChanges(root)
			assertModelsEqual
		]
	}

	@Test
	def void testFacadeRecordOriginalAndCorrespondentChanges() {
		// Paths and VURIs
		val sourcePath = '''«currentTestProject.name»/«TEST_SOURCE_MODEL_NAME.projectModelPath»'''
		val targetPath = '''«currentTestProject.name»/«TEST_TARGET_MODEL_NAME.projectModelPath»'''
		val targetVURI = VURI::getInstance(targetPath)
		val sourceVURI = VURI::getInstance(sourcePath)

		// Create model 
		val root = AllElementTypesFactory::eINSTANCE.createRoot
		root.id = TEST_SOURCE_MODEL_NAME
		createAndSynchronizeModel(TEST_SOURCE_MODEL_NAME.projectModelPath, root)

		// Setup facade 
		val VersioningFacade facade = new VersioningFacadeImpl(virtualModel)
		facade.registerObserver

		facade.recordOriginalAndCorrespondentChanges(sourceVURI, #[targetVURI])

		// Create container and synchronize 
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = "NonRootObjectContainer"
		rootElement.nonRootObjectContainerHelper = container
		saveAndSynchronizeChanges(rootElement)
		assertThat(facade.changesMatches.length, is(1))

		// Create and add non roots
		NON_CONTAINMENT_NON_ROOT_IDS.forEach [
			createAndAddNonRoot(it, container)
			saveAndSynchronizeChanges(root)
			assertModelsEqual
		]
		assertThat(facade.changesMatches.length, is(4))
		assertThat(facade.changesMatches.forall[originalVURI == sourceVURI], is(true))
		assertThat(facade.changesMatches.forall[null !== targetToCorrespondentChanges.get(targetVURI)], is(true))
		assertThat(facade.changesMatches.forall[targetToCorrespondentChanges.size == 1], is(true))
	}

	override protected setup() {
		// Do nothing 
	}

	override protected cleanup() {
		// Do nothing
	}

	override protected createChangePropagationSpecifications() {
		#[new AbstractChangePropagationSpecificationAllElementTypesToAllElementTypes {
		}]
	}

	private def String getProjectModelPath(String modelName) {
		'''model/«modelName».«MODEL_FILE_EXTENSION»'''
	}

	private def getRootElement() {
		TEST_SOURCE_MODEL_NAME.projectModelPath.firstRootElement as Root
	}

	private def assertModelsEqual() {
		assertPersistedModelsEqual(TEST_SOURCE_MODEL_NAME.projectModelPath, TEST_TARGET_MODEL_NAME.projectModelPath)
	}

	private def void createAndAddNonRoot(String id, NonRootObjectContainerHelper container) {
		val nonRoot = AllElementTypesFactory::eINSTANCE.createNonRoot
		nonRoot.id = id
		container.nonRootObjectsContainment.add(nonRoot)
	}
}
