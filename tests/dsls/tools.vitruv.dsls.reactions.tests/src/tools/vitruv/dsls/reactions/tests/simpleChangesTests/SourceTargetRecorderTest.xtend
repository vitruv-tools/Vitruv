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
import tools.vitruv.framework.versioning.SourceTargetRecorder
import tools.vitruv.framework.versioning.VersioningXtendFactory
import tools.vitruv.framework.versioning.impl.SourceTargetRecorderImpl

import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat
import tools.vitruv.framework.change.description.impl.EMFModelChangeImpl
import tools.vitruv.framework.change.description.VitruviusChangeFactory

//import tools.vitruv.framework.change.description.impl.EMFModelChangeImpl
//import tools.vitruv.framework.change.description.VitruviusChangeFactory
class SourceTargetRecorderTest extends AbstractVersioningTest {
	var SourceTargetRecorder stRecorder

	@Rule
	public val tempFolder = new TemporaryFolder

	override setup() {
		super.setup
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
		container.id = "NonRootObjectContainer"
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
	def void testRecordOriginalAndCorrespondentChanges() {
		// Paths and VURIs
		val sourcePath = '''«currentTestProject.name»/«TEST_SOURCE_MODEL_NAME.projectModelPath»'''
		val targetPath = '''«currentTestProject.name»/«TEST_TARGET_MODEL_NAME.projectModelPath»'''
		val targetVURI = VURI::getInstance(targetPath)
		val sourceVURI = VURI::getInstance(sourcePath)

		stRecorder.recordOriginalAndCorrespondentChanges(sourceVURI, #[targetVURI])

		// Create container and synchronize 
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = "NonRootObjectContainer"
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
		val sourcePath = '''«currentTestProject.name»/«TEST_SOURCE_MODEL_NAME.projectModelPath»'''
		val targetPath = '''«currentTestProject.name»/«TEST_TARGET_MODEL_NAME.projectModelPath»'''
		val targetVURI = VURI::getInstance(targetPath)
		val sourceVURI = VURI::getInstance(sourcePath)

		stRecorder.recordOriginalAndCorrespondentChanges(sourceVURI, #[targetVURI])

		// Create container and synchronize 
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = "NonRootObjectContainer"
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
	def void testReapply() {
		// Paths and VURIs
		val sourcePath = '''«currentTestProject.name»/«TEST_SOURCE_MODEL_NAME.projectModelPath»'''
		val targetPath = '''«currentTestProject.name»/«TEST_TARGET_MODEL_NAME.projectModelPath»'''
		val targetVURI = VURI::getInstance(targetPath)
		val sourceVURI = VURI::getInstance(sourcePath)

		stRecorder.recordOriginalAndCorrespondentChanges(sourceVURI, #[targetVURI])

		// Create container and synchronize 
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = "NonRootObjectContainer"
		rootElement.nonRootObjectContainerHelper = container
		rootElement.saveAndSynchronizeChanges
		assertThat(stRecorder.getChangeMatches(sourceVURI).length, is(1))

		// Create and add non roots
		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(container)]
		rootElement.saveAndSynchronizeChanges
		assertModelsEqual
		assertThat(stRecorder.getChangeMatches(sourceVURI).length, is(4))

		// Create new source
		val newTestSourceModelName = "EachTestModelSource2"
//		val newTestTargetModelName = "EachTestModelTarget2"
		val newSourcePath = '''«currentTestProject.name»/«newTestSourceModelName.projectModelPath»'''
//		val newTargetPath = '''«currentTestProject.name»/«newTestTargetModelName.projectModelPath»'''
//		val newTargetVURI = VURI::getInstance(newTargetPath)
		val newSourceVURI = VURI::getInstance(newSourcePath)
		val newRoot = AllElementTypesFactory::eINSTANCE.createRoot
		newRoot.id = newTestSourceModelName
		newTestSourceModelName.projectModelPath.createAndSynchronizeModel(newRoot)

		val changeMatches = stRecorder.getChangeMatches(sourceVURI)
		assertThat(changeMatches.length, is(4))
		changeMatches.
		val copiedChanges = changeMatches.map[originalChange].filter[it instanceof EMFModelChangeImpl].map [
			VitruviusChangeFactory::instance.createEMFModelChange(it as EMFModelChangeImpl, newSourceVURI)
		]
		assertThat(copiedChanges.length, is(4))
		// EcoreUtil::copy => koennte resolven 
		// Resource problematisch: Proxy URI ersetzten 
		// Fur Test ersetzen 
		copiedChanges.forEach[virtualModel.propagateChange(it)]
		assertThat(copiedChanges.length, is(4))
	}

	@Test
	def void testSezializeChangeMatches() {
		// Paths and VURIs
		val sourcePath = '''«currentTestProject.name»/«TEST_SOURCE_MODEL_NAME.projectModelPath»'''
		val targetPath = '''«currentTestProject.name»/«TEST_TARGET_MODEL_NAME.projectModelPath»'''
		val targetVURI = VURI::getInstance(targetPath)
		val sourceVURI = VURI::getInstance(sourcePath)

		stRecorder.recordOriginalAndCorrespondentChanges(sourceVURI, #[targetVURI])

		// Create container and synchronize 
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = "NonRootObjectContainer"
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
