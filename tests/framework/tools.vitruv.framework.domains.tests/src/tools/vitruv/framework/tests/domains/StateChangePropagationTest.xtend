package tools.vitruv.framework.tests.domains

import java.io.File
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.After
import org.junit.Before
import pcm_mockup.Pcm_mockupFactory
import pcm_mockup.Repository
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder
import tools.vitruv.framework.domains.DefaultStateChangePropagationStrategy
import tools.vitruv.framework.domains.StateChangePropagationStrategy
import tools.vitruv.framework.util.bridges.EMFBridge
import tools.vitruv.framework.util.bridges.EcoreResourceBridge
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
import tools.vitruv.framework.uuid.UuidGeneratorAndResolverImpl
import tools.vitruv.testutils.VitruviusTest
import uml_mockup.UPackage
import uml_mockup.Uml_mockupFactory

import static org.junit.Assert.*

abstract class StateChangePropagationTest extends VitruviusTest {
	protected static final String PCM_FILE_EXT = "pcm_mockup"
	protected static final String UML_FILE_EXT = "uml_mockup"
	protected var StateChangePropagationStrategy strategyToTest
	protected var Resource umlCheckpoint
	protected var Resource pcmCheckpoint
	protected var Resource umlModel
	protected var Resource pcmModel
	protected var Repository pcmRoot
	protected var UPackage umlRoot
	protected var AtomicEmfChangeRecorder changeRecorder
	protected var UuidGeneratorAndResolver setupResolver
	protected var UuidGeneratorAndResolver testResolver
	protected var ResourceSet resourceSet

	/**
	 * Creates the strategy, sets up the test model and prepares everything for detemining changes.
	 */
	@Before
	def void setup() {
		// setup models:
		strategyToTest = new DefaultStateChangePropagationStrategy
		resourceSet = new ResourceSetImpl
		setupResolver = new UuidGeneratorAndResolverImpl(resourceSet, true)
		changeRecorder = new AtomicEmfChangeRecorder(setupResolver)
		resourceSet.startRecording
		createPcmMockupModel()
		createUmlMockupModel()
		System.err.println(endRecording)
		// change to new recorder with test resolver:
		var resourceSet2 = new ResourceSetImpl
		testResolver = new UuidGeneratorAndResolverImpl(setupResolver, resourceSet2, true) // TODO TS (HIGH) new resource set or existing one?
		changeRecorder = new AtomicEmfChangeRecorder(testResolver);
		// create model checkpoints and start recording:
		umlCheckpoint = umlModel.createCheckpoint
		pcmCheckpoint = pcmModel.createCheckpoint
		umlModel.startRecording
		pcmModel.startRecording
	}

	/**
	 * Stops recording in case the test does not call getRecordedChanges() or getChangeFromComparisonWithCheckpoint().
	 */
	@After
	def void cleanup() {
		if (changeRecorder.isRecording) {
			changeRecorder.stopRecording
		}
	}

	/**
	 * USE THIS METHOD TO COMPARE RESULTS!
	 * Compares two changes: The recorded change sequence and the resolved changes by the state delta based strategy.
	 */
	protected def void compareChanges(Resource model, Resource checkpoint) {
		EcoreResourceBridge.saveResource(model)
		val deltaBasedChange = endRecording
		val stateBasedChange = strategyToTest.getChangeSequences(model, checkpoint, setupResolver)
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

	def private void createPcmMockupModel() {
		pcmModel = resourceSet.createResource(getModelVURI("My.pcm_mockup"))
		pcmRoot = Pcm_mockupFactory.eINSTANCE.createRepository
		pcmRoot.name = "RootRepository"
		pcmRoot.interfaces.add(Pcm_mockupFactory.eINSTANCE.createPInterface)
		pcmRoot.components.add(Pcm_mockupFactory.eINSTANCE.createComponent)
		pcmModel.contents.add(pcmRoot)
		EcoreResourceBridge.saveResource(pcmModel)
	}

	def private void createUmlMockupModel() {
		umlModel = resourceSet.createResource(getModelVURI("My.uml_mockup"))
		umlRoot = Uml_mockupFactory.eINSTANCE.createUPackage
		umlRoot.name = "RootPackage"
		umlRoot.interfaces.add(Uml_mockupFactory.eINSTANCE.createUInterface)
		umlRoot.classes.add(Uml_mockupFactory.eINSTANCE.createUClass)
		umlModel.contents.add(umlRoot)
		EcoreResourceBridge.saveResource(umlModel)
	}

	private def void startRecording(Notifier notifier) {
		changeRecorder.addToRecording(notifier)
		if (!changeRecorder.isRecording) {
			changeRecorder.beginRecording
		}
	}

	private def Resource createCheckpoint(Resource original) {
		val resourceSet = new ResourceSetImpl // TODO TS (HIGH) new resource set for checkpoint?
		val copy = resourceSet.createResource(original.URI)
		copy.contents.addAll(EcoreUtil.copyAll(original.contents))
		return copy
	}

	// Utility method regarding model folders:
	def private URI getModelVURI(String fileName) {
		val modelFolder = new File(getCurrentTestProjectFolder(), "model")
		val file = new File(modelFolder, fileName)
		return EMFBridge.getEmfFileUriForFile(file)
	}
}
