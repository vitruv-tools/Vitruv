package tools.vitruv.dsls.reactions.tests.simpleChangesTests

import allElementTypes.AllElementTypesFactory

import java.util.ArrayList
import java.util.Collections
import java.util.List

import org.junit.Assert
import org.junit.Test

import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.VersioningFacade
import tools.vitruv.framework.versioning.impl.VersioningFacadeImpl

import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat

class VersioningFacadeTest extends AbstractVersioningTest {
	@Test
	def testRecordingWithExecuteCommand() {
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = "NonRootObjectContainer"
		rootElement.nonRootObjectContainerHelper = container
		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(container)]
		val recorder = new AtomicEmfChangeRecorder
		val resourcePlatformPath = '''«currentTestProject.name»/«TEST_TARGET_MODEL_NAME.projectModelPath»'''
		val resourceVuri = VURI::getInstance(resourcePlatformPath)
		val modelInstance = virtualModel.getModelInstance(resourceVuri)
		recorder.beginRecording(resourceVuri, Collections::singleton(modelInstance.resource))

		rootElement.saveAndSynchronizeChanges

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
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = "NonRootObjectContainer"
		rootElement.nonRootObjectContainerHelper = container
		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(container)]
		val facade = new VersioningFacadeImpl(virtualModel)
		facade.registerObserver
		val resourcePlatformPath = '''«currentTestProject.name»/«TEST_TARGET_MODEL_NAME.projectModelPath»'''
		val resourceVuri = VURI::getInstance(resourcePlatformPath)
		facade.addPathToRecorded(resourceVuri)

		rootElement.saveAndSynchronizeChanges

		assertModelsEqual
		val changes = facade.getChanges(resourceVuri)
		Assert::assertEquals(4, changes.length)
	}

	@Test
	def testSingleChangeSynchronization() {
		// Setup facade 
		val facade = new VersioningFacadeImpl(virtualModel)
		facade.registerObserver
		val resourcePlatformPath = '''«currentTestProject.name»/«TEST_TARGET_MODEL_NAME.projectModelPath»'''
		val resourceVuri = VURI::getInstance(resourcePlatformPath)
		facade.addPathToRecorded(resourceVuri)

		// Create container and synchronize 
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = "NonRootObjectContainer"
		rootElement.nonRootObjectContainerHelper = container
		rootElement.saveAndSynchronizeChanges

		NON_CONTAINMENT_NON_ROOT_IDS.forEach [
			createAndAddNonRoot(container)
			rootElement.saveAndSynchronizeChanges
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

		// Setup facade 
		val VersioningFacade facade = new VersioningFacadeImpl(virtualModel)
		facade.registerObserver

		facade.recordOriginalAndCorrespondentChanges(sourceVURI, #[targetVURI])

		// Create container and synchronize 
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = "NonRootObjectContainer"
		rootElement.nonRootObjectContainerHelper = container
		rootElement.saveAndSynchronizeChanges
		assertThat(facade.changesMatches.length, is(1))

		// Create and add non roots
		NON_CONTAINMENT_NON_ROOT_IDS.forEach [
			createAndAddNonRoot(container)
			rootElement.saveAndSynchronizeChanges
			assertModelsEqual
		]
		assertThat(facade.changesMatches.length, is(4))
		assertThat(facade.changesMatches.forall[sourceVURI == originalVURI], is(true))
		assertThat(facade.changesMatches.forall[null !== targetToCorrespondentChanges.get(targetVURI)], is(true))
		assertThat(facade.changesMatches.forall[1 == targetToCorrespondentChanges.size], is(true))
	}

	@Test
	def void testFacadeRecordOriginalAndCorrespondentChangesSingleSaveAndSynchronize() {
		// Paths and VURIs
		val sourcePath = '''«currentTestProject.name»/«TEST_SOURCE_MODEL_NAME.projectModelPath»'''
		val targetPath = '''«currentTestProject.name»/«TEST_TARGET_MODEL_NAME.projectModelPath»'''
		val targetVURI = VURI::getInstance(targetPath)
		val sourceVURI = VURI::getInstance(sourcePath)

		// Setup facade 
		val VersioningFacade facade = new VersioningFacadeImpl(virtualModel)
		facade.registerObserver

		facade.recordOriginalAndCorrespondentChanges(sourceVURI, #[targetVURI])

		// Create container and synchronize 
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = "NonRootObjectContainer"
		rootElement.nonRootObjectContainerHelper = container
		rootElement.saveAndSynchronizeChanges
		assertThat(facade.changesMatches.length, is(1))

		// Create and add non roots
		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(container)]
		rootElement.saveAndSynchronizeChanges
		assertModelsEqual
		assertThat(facade.changesMatches.length, is(4))
		assertThat(facade.changesMatches.forall[sourceVURI == originalVURI], is(true))
		assertThat(facade.changesMatches.forall[null !== targetToCorrespondentChanges.get(targetVURI)], is(true))
		assertThat(facade.changesMatches.forall[1 == targetToCorrespondentChanges.size], is(true))
	}
}
