package tools.vitruv.dsls.reactions.tests.simpleChangesTests

import allElementTypes.AllElementTypesFactory

import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.List

import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.ChangeMatch

import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat
import tools.vitruv.framework.versioning.SourceTargetRecorder
import tools.vitruv.framework.versioning.impl.SourceTargetRecorderImpl
import tools.vitruv.framework.versioning.VersioningXtendFactory

class SourceTargetRecorderTest extends AbstractVersioningTest {
	var SourceTargetRecorder sourceTargetRecorder

	@Rule
	public val tempFolder = new TemporaryFolder

	override setup() {
		super.setup
		// Setup facade 
		sourceTargetRecorder = VersioningXtendFactory::instance.createSourceTargetRecorder(virtualModel)
		sourceTargetRecorder.registerObserver
	}

	override cleanup() {
		super.cleanup
		sourceTargetRecorder = null
	}

	@Test
	def testFacadeAddPathToRecorded() {
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = "NonRootObjectContainer"
		rootElement.nonRootObjectContainerHelper = container
		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(container)]

		val resourcePlatformPath = '''«currentTestProject.name»/«TEST_TARGET_MODEL_NAME.projectModelPath»'''
		val resourceVuri = VURI::getInstance(resourcePlatformPath)
		(sourceTargetRecorder as SourceTargetRecorderImpl).addPathToRecorded(resourceVuri)

		rootElement.saveAndSynchronizeChanges

		assertModelsEqual
		val changes = (sourceTargetRecorder as SourceTargetRecorderImpl).getChanges(resourceVuri)
		Assert::assertEquals(4, changes.length)
	}

	@Test
	def testSingleChangeSynchronization() {
		val resourcePlatformPath = '''«currentTestProject.name»/«TEST_TARGET_MODEL_NAME.projectModelPath»'''
		val resourceVuri = VURI::getInstance(resourcePlatformPath)
		(sourceTargetRecorder as SourceTargetRecorderImpl).addPathToRecorded(resourceVuri)

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

		sourceTargetRecorder.recordOriginalAndCorrespondentChanges(sourceVURI, #[targetVURI])

		// Create container and synchronize 
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = "NonRootObjectContainer"
		rootElement.nonRootObjectContainerHelper = container
		rootElement.saveAndSynchronizeChanges
		assertThat(sourceTargetRecorder.changesMatches.length, is(1))

		// Create and add non roots
		NON_CONTAINMENT_NON_ROOT_IDS.forEach [
			createAndAddNonRoot(container)
			rootElement.saveAndSynchronizeChanges
			assertModelsEqual
		]
		assertThat(sourceTargetRecorder.changesMatches.length, is(4))
		assertThat(sourceTargetRecorder.changesMatches.forall[sourceVURI == originalVURI], is(true))
		assertThat(sourceTargetRecorder.changesMatches.forall[null !== targetToCorrespondentChanges.get(targetVURI)],
			is(true))
		assertThat(sourceTargetRecorder.changesMatches.forall[1 == targetToCorrespondentChanges.size], is(true))
	}

	@Test
	def void testFacadeRecordOriginalAndCorrespondentChangesSingleSaveAndSynchronize() {
		// Paths and VURIs
		val sourcePath = '''«currentTestProject.name»/«TEST_SOURCE_MODEL_NAME.projectModelPath»'''
		val targetPath = '''«currentTestProject.name»/«TEST_TARGET_MODEL_NAME.projectModelPath»'''
		val targetVURI = VURI::getInstance(targetPath)
		val sourceVURI = VURI::getInstance(sourcePath)

		sourceTargetRecorder.recordOriginalAndCorrespondentChanges(sourceVURI, #[targetVURI])

		// Create container and synchronize 
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = "NonRootObjectContainer"
		rootElement.nonRootObjectContainerHelper = container
		rootElement.saveAndSynchronizeChanges
		assertThat(sourceTargetRecorder.changesMatches.length, is(1))

		// Create and add non roots
		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(container)]
		rootElement.saveAndSynchronizeChanges
		assertModelsEqual
		assertThat(sourceTargetRecorder.changesMatches.length, is(4))
		assertThat(sourceTargetRecorder.changesMatches.forall[sourceVURI == originalVURI], is(true))
		assertThat(sourceTargetRecorder.changesMatches.forall[null !== targetToCorrespondentChanges.get(targetVURI)],
			is(true))
		assertThat(sourceTargetRecorder.changesMatches.forall[1 == targetToCorrespondentChanges.size], is(true))
	}

	@Test
	def void testSezializeChangeMatches() {
		// Paths and VURIs
		val sourcePath = '''«currentTestProject.name»/«TEST_SOURCE_MODEL_NAME.projectModelPath»'''
		val targetPath = '''«currentTestProject.name»/«TEST_TARGET_MODEL_NAME.projectModelPath»'''
		val targetVURI = VURI::getInstance(targetPath)
		val sourceVURI = VURI::getInstance(sourcePath)

		sourceTargetRecorder.recordOriginalAndCorrespondentChanges(sourceVURI, #[targetVURI])

		// Create container and synchronize 
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = "NonRootObjectContainer"
		rootElement.nonRootObjectContainerHelper = container
		rootElement.saveAndSynchronizeChanges
		assertThat(sourceTargetRecorder.changesMatches.length, is(1))

		// Create and add non roots
		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(container)]
		rootElement.saveAndSynchronizeChanges
		assertModelsEqual
		val changeMatches = sourceTargetRecorder.changesMatches
		assertThat(changeMatches.length, is(4))

		// Serialize change matches 
		val serializationPath = '''changeMatches.ser'''
		val yourFile = tempFolder.newFile(serializationPath)
		val fileOut = new FileOutputStream(yourFile, false)
		val out = new ObjectOutputStream(fileOut)
		out.writeObject(changeMatches)
		out.close
		fileOut.close

		val fileIn = new FileInputStream(yourFile)
		val in = new ObjectInputStream(fileIn)
		val deserializedChangeMatches = in.readObject as List<ChangeMatch>
		in.close
		fileIn.close

		assertThat(deserializedChangeMatches.length, is(4))
	// TODO PS Fix VURI 
	// assertThat(deserializedChangeMatches.forall[sourceVURI == originalVURI], is(true))
	// assertThat(deserializedChangeMatches.forall[null !== targetToCorrespondentChanges.get(targetVURI)], is(true))
	// assertThat(deserializedChangeMatches.forall[1 == targetToCorrespondentChanges.size], is(true))
	}
}
