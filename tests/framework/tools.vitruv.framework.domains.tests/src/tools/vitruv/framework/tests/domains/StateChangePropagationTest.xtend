package tools.vitruv.framework.tests.domains

import java.io.File
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.junit.After
import org.junit.Before
import pcm_mockup.Pcm_mockupFactory
import pcm_mockup.Repository
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder
import tools.vitruv.framework.util.bridges.EMFBridge
import tools.vitruv.framework.util.bridges.EcoreResourceBridge
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
import tools.vitruv.framework.uuid.UuidGeneratorAndResolverImpl
import tools.vitruv.testutils.VitruviusTest
import uml_mockup.UPackage
import uml_mockup.Uml_mockupFactory

import static org.junit.Assert.*
import tools.vitruv.framework.domains.StateBasedChangeResolutionStrategy
import tools.vitruv.framework.domains.DefaultStateBasedChangeResolutionStrategy

abstract class StateChangePropagationTest extends VitruviusTest {
	protected static final String PCM_FILE_EXT = "pcm_mockup"
	protected static final String UML_FILE_EXT = "uml_mockup"
	protected var StateBasedChangeResolutionStrategy strategyToTest
	protected var Resource umlCheckpoint
	protected var Resource pcmCheckpoint
	protected var Resource umlModel
	protected var Resource pcmModel
	protected var Repository pcmRoot
	protected var UPackage umlRoot
	protected var AtomicEmfChangeRecorder changeRecorder
	protected var UuidGeneratorAndResolver setupResolver
	protected var UuidGeneratorAndResolver checkpointResolver
	protected var ResourceSet resourceSet
	protected var ResourceSet checkpointResourceSet

	/**
	 * Creates the strategy, sets up the test model and prepares everything for detemining changes.
	 */
	@Before
	def setup() {
		// Setup:
		strategyToTest = new DefaultStateBasedChangeResolutionStrategy
		resourceSet = new ResourceSetImpl
		checkpointResourceSet = new ResourceSetImpl
		setupResolver = new UuidGeneratorAndResolverImpl(resourceSet, true)
		changeRecorder = new AtomicEmfChangeRecorder(setupResolver)
		// Create mockup models:
		resourceSet.startRecording
		createPcmMockupModel()
		createUmlMockupModel()
		endRecording
		// change to new recorder with test resolver, create model checkpoints and start recording:
		checkpointResolver = new UuidGeneratorAndResolverImpl(setupResolver, checkpointResourceSet, true)
		umlCheckpoint = umlModel.createCheckpoint
		pcmCheckpoint = pcmModel.createCheckpoint
		umlModel.startRecording
		pcmModel.startRecording
	}

	/**
	 * Stops recording in case the test does not call getRecordedChanges() or getChangeFromComparisonWithCheckpoint().
	 */
	@After
	def cleanup() {
		if (changeRecorder.isRecording) {
			changeRecorder.stopRecording
		}
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
		assertTrue(message, stateBasedChange.changedEObjectEquals(deltaBasedChange))
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
		pcmModel = resourceSet.createResource(getModelVURI("My.pcm_mockup"))
		pcmRoot = Pcm_mockupFactory.eINSTANCE.createRepository
		pcmRoot.name = "RootRepository"
		pcmRoot.interfaces.add(Pcm_mockupFactory.eINSTANCE.createPInterface)
		pcmRoot.components.add(Pcm_mockupFactory.eINSTANCE.createComponent)
		pcmModel.contents.add(pcmRoot)
		EcoreResourceBridge.saveResource(pcmModel)
	}

	private def createUmlMockupModel() {
		umlModel = resourceSet.createResource(getModelVURI("My.uml_mockup"))
		umlRoot = Uml_mockupFactory.eINSTANCE.createUPackage
		umlRoot.name = "RootPackage"
		umlRoot.interfaces.add(Uml_mockupFactory.eINSTANCE.createUInterface)
		umlRoot.classes.add(Uml_mockupFactory.eINSTANCE.createUClass)
		umlModel.contents.add(umlRoot)
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
		val modelFolder = new File(getCurrentTestProjectFolder(), "model")
		val file = new File(modelFolder, fileName)
		return EMFBridge.getEmfFileUriForFile(file)
	}
}
