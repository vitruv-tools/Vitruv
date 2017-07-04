package tools.vitruv.dsls.reactions.tests.simpleChangesTests

import allElementTypes.AllElementTypesFactory
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.List
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.SourceTargetRecorder
import tools.vitruv.framework.versioning.VersioningXtendFactory
import tools.vitruv.framework.versioning.commit.ChangeMatch
import tools.vitruv.framework.versioning.impl.SourceTargetRecorderImpl

import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat
import tools.vitruv.dsls.reactions.tests.AbstractVersioningTest

class SourceTargetRecorderTest extends AbstractVersioningTest {
	static val logger = Logger::getLogger(SourceTargetRecorderTest)

	static protected val nonRootObjectContainerName = "NonRootObjectContainer"
	protected SourceTargetRecorder stRecorder

	@Rule
	public val tempFolder = new TemporaryFolder

	override setup() {
		super.setup
		logger.level = Level::DEBUG
		// Setup sourceTargetRecorder 
		stRecorder = VersioningXtendFactory::instance.createSourceTargetRecorder(virtualModel)
		stRecorder.registerObserver
	}

	override cleanup() {
		super.cleanup
		stRecorder = null
	}

	override unresolveChanges() {
		true
	}

	@Test
	def testAddPathToRecorded() {
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = nonRootObjectContainerName
		rootElement.nonRootObjectContainerHelper = container
		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(container)]

		val resourcePlatformPath = '''«currentTestProject.name»/«TEST_TARGET_MODEL_NAME.projectModelPath»'''
		val resourceVuri = VURI::getInstance(resourcePlatformPath)
		(stRecorder as SourceTargetRecorderImpl).addPathToRecorded(resourceVuri)

		rootElement.saveAndSynchronizeChanges

		assertModelsEqual
		val changes = (stRecorder as SourceTargetRecorderImpl).getChanges(resourceVuri)
		Assert::assertEquals(4, changes.length)
	}

	@Test
	def testSingleChangeSynchronization() {
		val resourcePlatformPath = '''«currentTestProject.name»/«TEST_TARGET_MODEL_NAME.projectModelPath»'''
		val resourceVuri = VURI::getInstance(resourcePlatformPath)
		(stRecorder as SourceTargetRecorderImpl).addPathToRecorded(resourceVuri)

		// Create container and synchronize 
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = nonRootObjectContainerName
		rootElement.nonRootObjectContainerHelper = container
		rootElement.saveAndSynchronizeChanges

		NON_CONTAINMENT_NON_ROOT_IDS.forEach [
			createAndAddNonRoot(container)
			rootElement.saveAndSynchronizeChanges
			assertModelsEqual
		]
	}

	@Test
	def void testRecordOriginalAndCorrespondentChanges() {
		// Paths and VURIs
		val targetVURI = TEST_TARGET_MODEL_NAME.calculateVURI
		val sourceVURI = TEST_SOURCE_MODEL_NAME.calculateVURI

		stRecorder.recordOriginalAndCorrespondentChanges(sourceVURI, #[targetVURI])

		// Create container and synchronize 
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = nonRootObjectContainerName
		rootElement.nonRootObjectContainerHelper = container
		rootElement.saveAndSynchronizeChanges
		val changesMatches = stRecorder.getChangeMatches(sourceVURI)
		assertThat(changesMatches.length, is(1))

		// Create and add non roots
		NON_CONTAINMENT_NON_ROOT_IDS.forEach [
			createAndAddNonRoot(container)
			rootElement.saveAndSynchronizeChanges
			assertModelsEqual
		]
		assertThat(changesMatches.length, is(4))
		assertThat(changesMatches.forall[sourceVURI == originalVURI], is(true))
		assertThat(changesMatches.forall[null !== targetToCorrespondentChanges.get(targetVURI)], is(true))
		val message = changesMatches.filter[0 == targetToCorrespondentChanges.get(targetVURI).length].map [
			toString
		].reduce[p1, p2|p1 + p2]
		assertThat(message, changesMatches.forall [
			0 < targetToCorrespondentChanges.get(targetVURI).length
		], is(true))
	}

	@Test
	def void testRecordOriginalAndCorrespondentChangesSingleSaveAndSynchronize() {
		// Paths and VURIs
		val targetVURI = TEST_TARGET_MODEL_NAME.calculateVURI
		val sourceVURI = TEST_SOURCE_MODEL_NAME.calculateVURI

		stRecorder.recordOriginalAndCorrespondentChanges(sourceVURI, #[targetVURI])

		// Create container and synchronize 
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = nonRootObjectContainerName
		rootElement.nonRootObjectContainerHelper = container
		rootElement.saveAndSynchronizeChanges

		val changesMatches = stRecorder.getChangeMatches(sourceVURI)
		assertThat(changesMatches.length, is(1))

		// Create and add non roots
		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(container)]
		rootElement.saveAndSynchronizeChanges
		assertModelsEqual
		assertThat(changesMatches.length, is(4))
		assertThat(changesMatches.forall[sourceVURI == originalVURI], is(true))
		assertThat(changesMatches.forall[null !== targetToCorrespondentChanges.get(targetVURI)], is(true))
		val message = changesMatches.filter[0 == targetToCorrespondentChanges.get(targetVURI).length].map [
			toString
		].reduce[p1, p2|p1 + p2]
		assertThat(message, changesMatches.forall [
			0 < targetToCorrespondentChanges.get(targetVURI).length
		], is(true))

	}

	@Test
	def void echangesShouldBeUnresolved() {
		// Paths and VURIs
		val targetVURI = TEST_TARGET_MODEL_NAME.calculateVURI
		val sourceVURI = TEST_SOURCE_MODEL_NAME.calculateVURI

		stRecorder.recordOriginalAndCorrespondentChanges(sourceVURI, #[targetVURI])

		// Create container and synchronize 
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = nonRootObjectContainerName
		rootElement.nonRootObjectContainerHelper = container
		rootElement.saveAndSynchronizeChanges

		val changesMatches = stRecorder.getChangeMatches(sourceVURI)
		assertThat(changesMatches.length, is(1))

		// Create and add non roots
		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(container)]
		rootElement.saveAndSynchronizeChanges
		assertModelsEqual
		assertThat(changesMatches.length, is(4))
		changesMatches.forEach[assertThat(originalChange.EChanges.forall[!resolved], is(true))]

	}

	@Test
	def void testSezializeChangeMatches() {
		// Paths and VURIs
		val targetVURI = TEST_TARGET_MODEL_NAME.calculateVURI
		val sourceVURI = TEST_SOURCE_MODEL_NAME.calculateVURI

		stRecorder.recordOriginalAndCorrespondentChanges(sourceVURI, #[targetVURI])

		// Create container and synchronize 
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = nonRootObjectContainerName
		rootElement.nonRootObjectContainerHelper = container
		rootElement.saveAndSynchronizeChanges
		assertThat(stRecorder.getChangeMatches(sourceVURI).length, is(1))

		// Create and add non roots
		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(container)]
		rootElement.saveAndSynchronizeChanges
		assertModelsEqual
		val changeMatches = stRecorder.getChangeMatches(sourceVURI)
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
		assertThat(deserializedChangeMatches.forall[sourceVURI == originalVURI], is(true))
	// assertThat(deserializedChangeMatches.forall[null !== targetToCorrespondentChanges.get(targetVURI)], is(true))
	// assertThat(deserializedChangeMatches.forall[1 == targetToCorrespondentChanges.size], is(true))
	}

}
