package tools.vitruv.dsls.reactions.tests.versioning

import allElementTypes.AllElementTypesFactory
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.junit.Ignore
import org.junit.Test
import tools.vitruv.dsls.reactions.tests.AbstractVersioningTest
import tools.vitruv.framework.util.datatypes.VURI

import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat
import tools.vitruv.framework.versioning.extensions.VirtualModelExtension

class SourceTargetRecorderTest extends AbstractVersioningTest {
	static protected extension VirtualModelExtension = VirtualModelExtension::instance
	static extension Logger = Logger::getLogger(SourceTargetRecorderTest)

	static protected val nonRootObjectContainerName = "NonRootObjectContainer"
	protected VURI sourceVURI

	override setup() {
		super.setup
		level = Level::DEBUG
		sourceVURI = VURI::getInstance(rootElement.eResource)
	}

	override cleanup() {
		super.cleanup
	}

	override unresolveChanges() {
		true
	}

	@Test
	def void testRecordOriginalAndCorrespondentChanges() {

		// Create container and synchronize 
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = nonRootObjectContainerName
		rootElement.nonRootObjectContainerHelper = container
		rootElement.saveAndSynchronizeChanges
		assertThat(virtualModel.getChangeMatches(sourceVURI).length, is(2))

		// Create and add non roots
		NON_CONTAINMENT_NON_ROOT_IDS.forEach [
			createAndAddNonRoot(container)
			rootElement.saveAndSynchronizeChanges
			assertModelsEqual
		]
		assertThat(virtualModel.getChangeMatches(sourceVURI).length, is(5))
		assertThat(virtualModel.getChangeMatches(sourceVURI).forall[sourceVURI == originalChange.URI], is(true))
	}

	@Test
	def void testRecordOriginalAndCorrespondentChangesSingleSaveAndSynchronize() {
		// Create container and synchronize 
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = nonRootObjectContainerName
		rootElement.nonRootObjectContainerHelper = container
		rootElement.saveAndSynchronizeChanges

		assertThat(virtualModel.getChangeMatches(sourceVURI).length, is(2))

		// Create and add non roots
		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(container)]
		rootElement.saveAndSynchronizeChanges
		assertModelsEqual
		assertThat(virtualModel.getChangeMatches(sourceVURI).length, is(5))
		assertThat(virtualModel.getChangeMatches(sourceVURI).forall[sourceVURI == originalChange.URI], is(true))
	}

	@Test
	def void echangesShouldBeUnresolved() {
		// Create container and synchronize 
		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
		container.id = nonRootObjectContainerName
		rootElement.nonRootObjectContainerHelper = container
		rootElement.saveAndSynchronizeChanges

		assertThat(virtualModel.getChangeMatches(sourceVURI).length, is(2))

		// Create and add non roots
		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(container)]
		rootElement.saveAndSynchronizeChanges
		assertModelsEqual
		assertThat(virtualModel.getChangeMatches(sourceVURI).length, is(5))
		virtualModel.getChangeMatches(sourceVURI).forEach [
			assertThat(originalChange.EChanges.forall[!resolved], is(true))
		]

	}

	@Ignore
	@Test
	def void testSezializeChangeMatches() {
//		val container = AllElementTypesFactory::eINSTANCE.createNonRootObjectContainerHelper
//		container.id = nonRootObjectContainerName
//		rootElement.nonRootObjectContainerHelper = container
//		rootElement.saveAndSynchronizeChanges
//		assertThat(stRecorder.getChangeMatches(sourceVURI).length, is(1))
//
//		// Create and add non roots
//		NON_CONTAINMENT_NON_ROOT_IDS.forEach[createAndAddNonRoot(container)]
//		rootElement.saveAndSynchronizeChanges
//		assertModelsEqual
//		val changeMatches = stRecorder.getChangeMatches(sourceVURI)
//		assertThat(changeMatches.length, is(4))
//
//		// Serialize change matches 
////		val serializationPath = '''changeMatches.ser'''
////		val yourFile = tempFolder.newFile(serializationPath)
//		val fileOut = new FileOutputStream(yourFile, false)
//		val out = new ObjectOutputStream(fileOut)
//		out.writeObject(changeMatches)
//		out.close
//		fileOut.close
//
//		val fileIn = new FileInputStream(yourFile)
//		val in = new ObjectInputStream(fileIn)
//		val deserializedChangeMatches = in.readObject as List<ChangeMatch>
//		in.close
//		fileIn.close
//
//		assertThat(deserializedChangeMatches.length, is(4))
//		// TODO PS Fix VURI 
//		assertThat(deserializedChangeMatches.forall[sourceVURI == originalVURI], is(true))
//	// assertThat(deserializedChangeMatches.forall[null !== targetToCorrespondentChanges.get(targetVURI)], is(true))
//	// assertThat(deserializedChangeMatches.forall[1 == targetToCorrespondentChanges.size], is(true))
	}

}
