package tools.vitruv.dsls.reactions.tests.simpleChangesTests

import allElementTypes.AllElementTypesFactory

import org.junit.Assert
import org.junit.Test

import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.VersioningFacade
import tools.vitruv.framework.versioning.impl.VersioningFacadeImpl

import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat

class VersioningFacadeTest extends AbstractVersioningTest {
	var VersioningFacade facade

	override setup() {
		super.setup
		// Setup facade 
		facade = new VersioningFacadeImpl(virtualModel)
		facade.registerObserver
	}

	override cleanup() {
		super.cleanup
		facade = null
	}

	@Test
	def testFacadeAddPathToRecorded() {
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = "NonRootObjectContainer"
		rootElement.nonRootObjectContainerHelper = container
		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(container)]

		val resourcePlatformPath = '''«currentTestProject.name»/«TEST_TARGET_MODEL_NAME.projectModelPath»'''
		val resourceVuri = VURI::getInstance(resourcePlatformPath)
		(facade as VersioningFacadeImpl).addPathToRecorded(resourceVuri)

		rootElement.saveAndSynchronizeChanges

		assertModelsEqual
		val changes = (facade as VersioningFacadeImpl).getChanges(resourceVuri)
		Assert::assertEquals(4, changes.length)
	}

	@Test
	def testSingleChangeSynchronization() {
		val resourcePlatformPath = '''«currentTestProject.name»/«TEST_TARGET_MODEL_NAME.projectModelPath»'''
		val resourceVuri = VURI::getInstance(resourcePlatformPath)
		(facade as VersioningFacadeImpl).addPathToRecorded(resourceVuri)

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
