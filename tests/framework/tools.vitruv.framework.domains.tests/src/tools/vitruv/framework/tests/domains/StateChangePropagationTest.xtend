package tools.vitruv.framework.tests.domains

import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import pcm_mockup.Repository
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.util.bridges.EMFBridge
import tools.vitruv.framework.util.bridges.EcoreResourceBridge
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
import uml_mockup.UPackage
import tools.vitruv.framework.domains.StateBasedChangeResolutionStrategy
import tools.vitruv.framework.domains.DefaultStateBasedChangeResolutionStrategy
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.testutils.TestLogging
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.AfterEach
import tools.vitruv.testutils.TestProject
import java.nio.file.Path

import static org.junit.jupiter.api.Assertions.*
import static tools.vitruv.testutils.metamodels.PcmMockupCreators.pcm
import static tools.vitruv.testutils.metamodels.UmlMockupCreators.uml
import tools.vitruv.testutils.TestProjectManager
import tools.vitruv.testutils.RegisterMetamodelsInStandalone
import tools.vitruv.testutils.domains.TestDomainsRepository
import static extension tools.vitruv.framework.util.ResourceSetUtil.withGlobalFactories
import static extension tools.vitruv.framework.domains.repository.DomainAwareResourceSet.awareOfDomains
import tools.vitruv.framework.change.recording.ChangeRecorder
import org.eclipse.emf.ecore.util.EcoreUtil
import static tools.vitruv.framework.uuid.UuidGeneratorAndResolverFactory.createUuidGeneratorAndResolver

@ExtendWith(TestProjectManager, TestLogging, RegisterMetamodelsInStandalone)
abstract class StateChangePropagationTest {
	protected static final String PCM_FILE_EXT = "pcm_mockup"
	protected static final String UML_FILE_EXT = "uml_mockup"
	protected var Path testProjectFolder
	protected var StateBasedChangeResolutionStrategy strategyToTest
	protected var Resource umlCheckpoint
	protected var Resource pcmCheckpoint
	protected var Resource umlModel
	protected var Resource pcmModel
	protected var Repository pcmRoot
	protected var UPackage umlRoot
	protected var ChangeRecorder changeRecorder
	protected var UuidGeneratorAndResolver setupResolver
	protected var UuidGeneratorAndResolver checkpointResolver
	protected var ResourceSet resourceSet
	protected var ResourceSet checkpointResourceSet

	/**
	 * Creates the strategy, sets up the test model and prepares everything for detemining changes.
	 */
	@BeforeEach
	def void setup(@TestProject Path testProjectFolder) {
		this.testProjectFolder = testProjectFolder
		// Setup:
		strategyToTest = new DefaultStateBasedChangeResolutionStrategy
		resourceSet = new ResourceSetImpl().withGlobalFactories().awareOfDomains(TestDomainsRepository.INSTANCE)
		checkpointResourceSet = new ResourceSetImpl().withGlobalFactories().awareOfDomains(TestDomainsRepository.INSTANCE)
		setupResolver = createUuidGeneratorAndResolver(resourceSet)
		changeRecorder = new ChangeRecorder(setupResolver)
		// Create mockup models:
		resourceSet.startRecording
		createPcmMockupModel()
		createUmlMockupModel()
		endRecording
		// change to new recorder with test resolver, create model checkpoints and start recording:
		checkpointResolver = createUuidGeneratorAndResolver(setupResolver, checkpointResourceSet)
		umlCheckpoint = umlModel.createCheckpoint
		pcmCheckpoint = pcmModel.createCheckpoint
		umlModel.startRecording
		pcmModel.startRecording
	}
	
	/**
	 * Stops recording in case the test does not call getRecordedChanges() or getChangeFromComparisonWithCheckpoint().
	 */
	@AfterEach
	def stopRecording() {
		changeRecorder.close()
	}

	/**
	 * USE THIS METHOD TO COMPARE RESULTS!
	 * Compares two changes: The recorded change sequence and the resolved changes by the state delta based strategy.
	 */
	protected def compareChanges(Resource model, Resource checkpoint) {
		EcoreResourceBridge.saveResource(model)
		val deltaBasedChange = endRecording
		val stateBasedChange = strategyToTest.getChangeSequences(model, checkpoint, checkpointResolver)
		assertNotNull(stateBasedChange)
		val message = getTextualRepresentation(stateBasedChange, deltaBasedChange)
		val stateBasedChangedObjects = stateBasedChange.affectedAndReferencedEObjects
		val deltaBasedChangedObjects = deltaBasedChange.affectedAndReferencedEObjects
		assertEquals(stateBasedChangedObjects.size, deltaBasedChangedObjects.size, '''
			Got a different number of changed objects:
			«message»''')
		stateBasedChangedObjects.forEach [ stateBasedChangedObject |
			assertTrue(deltaBasedChangedObjects.exists [EcoreUtil.equals(it, stateBasedChangedObject)], '''
				Could not find this changed object in the delta based change:
				«stateBasedChangedObject»
				
				«message»''')
		]
	}

	/**
	 * Returns the recorded change sequences (the "original" changes) for a specific model instance.
	 */
	private def VitruviusChange endRecording() {
		changeRecorder.endRecording
		return VitruviusChangeFactory.instance.createCompositeChange(changeRecorder.changes)
	}

	private def String getTextualRepresentation(VitruviusChange stateBasedChange, VitruviusChange deltaBasedChange) '''
		State-based «stateBasedChange»
		Delta-based «deltaBasedChange»
	'''

	private def createPcmMockupModel() {
		pcmModel = resourceSet.createResource(getModelVURI("My.pcm_mockup")) => [
			contents += (pcmRoot = pcm.Repository => [
				name = "RootRepository"
				interfaces += pcm.Interface
				components += pcm.Component
			])
		]
		EcoreResourceBridge.saveResource(pcmModel)
	}

	private def createUmlMockupModel() {
		umlModel = resourceSet.createResource(getModelVURI("My.uml_mockup")) => [
			contents += (umlRoot = uml.Package => [
				name = "RootPackage"
				interfaces += uml.Interface
				classes += uml.Class
			])
		]
		EcoreResourceBridge.saveResource(umlModel)
	}

	private def startRecording(Notifier notifier) {
		changeRecorder.addToRecording(notifier)
		if (!changeRecorder.isRecording) {
			changeRecorder.beginRecording
		}
	}

	private def Resource createCheckpoint(Resource original) {
		return checkpointResourceSet.getResource(original.URI, true)
	}

	private def URI getModelVURI(String fileName) {
		return EMFBridge.getEmfFileUriForFile(testProjectFolder.resolve("model").resolve(fileName).toFile())
	}
}
