package tools.vitruv.framework.tests.domains

import java.io.File
import java.util.ArrayList
import java.util.Collections
import java.util.List
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.After
import org.junit.Before
import pcm_mockup.Pcm_mockupFactory
import pcm_mockup.Pcm_mockupPackage
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder
import tools.vitruv.framework.domains.AbstractTuidAwareVitruvDomain
import tools.vitruv.framework.domains.DefaultStateChangePropagationStrategy
import tools.vitruv.framework.domains.StateChangePropagationStrategy
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.domains.VitruviusProjectBuilderApplicator
import tools.vitruv.framework.tuid.AttributeTuidCalculatorAndResolver
import tools.vitruv.framework.userinteraction.UserInteractionFactory
import tools.vitruv.framework.util.bridges.EMFBridge
import tools.vitruv.framework.util.bridges.EcoreResourceBridge
import tools.vitruv.framework.util.datatypes.ModelInstance
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.vsum.InternalVirtualModel
import tools.vitruv.testutils.VitruviusTest
import tools.vitruv.testutils.util.TestUtil
import uml_mockup.Uml_mockupFactory
import uml_mockup.Uml_mockupPackage

import static org.junit.Assert.*

abstract class StateChangePropagationTest extends VitruviusTest {
	protected static final String VSUM_NAME = "DomainProject"
	protected static final String PCM_FILE_EXT = "pcm_mockup"
	protected static final String UML_FILE_EXT = "uml_mockup"
	protected var StateChangePropagationStrategy strategyToTest
	protected var InternalVirtualModel vsum
	protected var Resource umlCheckpoint
	protected var Resource pcmCheckpoint
	protected var AtomicEmfChangeRecorder changeRecorder

	static final VitruvDomain UmlDomain = new AbstractTuidAwareVitruvDomain("UML", Uml_mockupPackage.eINSTANCE,
		new AttributeTuidCalculatorAndResolver(Uml_mockupPackage.eINSTANCE.nsURI, "id"), UML_FILE_EXT) {
		override VitruviusProjectBuilderApplicator getBuilderApplicator() {
			return null
		}

		override boolean supportsUuids() {
			return false
		}
	}

	static final VitruvDomain PcmDomain = new AbstractTuidAwareVitruvDomain("PCM", Pcm_mockupPackage.eINSTANCE,
		new AttributeTuidCalculatorAndResolver(Pcm_mockupPackage.eINSTANCE.nsURI, "id"), PCM_FILE_EXT) {
		override VitruviusProjectBuilderApplicator getBuilderApplicator() {
			return null
		}

		override boolean supportsUuids() {
			return false
		}
	}

	/**
	 * Creates the strategy, sets up the test model and prepares everything for detemining changes.
	 */
	@Before
	def void setup() {
		strategyToTest = new DefaultStateChangePropagationStrategy
		vsum = createVirtualModel(VSUM_NAME)
		changeRecorder = new AtomicEmfChangeRecorder(vsum.uuidGeneratorAndResolver)
		umlCheckpoint = umlModelInstance.resource.createCheckpoint
		pcmCheckpoint = pcmModelInstance.resource.createCheckpoint
		createPcmMockupModel() // TODO (HIGH) why are there multiple roots?
		createUmlMockupModel()
		umlCheckpoint = umlModelInstance.firstRootEObject.eResource.createCheckpoint
		pcmCheckpoint = pcmModelInstance.firstRootEObject.eResource.createCheckpoint
		umlModelInstance.firstRootEObject.startRecording
		pcmModelInstance.firstRootEObject.startRecording
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
	protected def void compareRecordedChanges(ModelInstance model) {
		val stateBasedChange = model.changeFromComparisonWithCheckpoint
		val deltaBasedChange = model.recordedChanges
		System.err.println("State-based " + stateBasedChange) // TODO TS (HIGH) remove this
		System.err.println("Delta-based " + deltaBasedChange) // TODO TS (HIGH) remove this
		assertFalse("Composite change is empty!", stateBasedChange.EChanges.empty)
		assertTrue(stateBasedChange.changedEObjectEquals(deltaBasedChange)) // TODO TS (HIGH) readable outputs for the assertions
	}

	/**
	 * Returns the change resolved by the state delta based strategy (the "reconstructed" changes) for a specific model instance.
	 */
	protected def VitruviusChange getChangeFromComparisonWithCheckpoint(ModelInstance model) {
		vsum.save
		val root = model.firstRootEObject.eResource
		val checkpoint = model.correlatingCheckpoint
		val change = strategyToTest.getChangeSequences(root, checkpoint, vsum.uuidGeneratorAndResolver)
		assertNotNull(change)
		return change
	}

	/**
	 * Returns the recorded change sequences (the "original" changes) for a specific model instance.
	 */
	protected def VitruviusChange getRecordedChanges(ModelInstance model) {
		EcoreResourceBridge.saveResource(model.resource)
		changeRecorder.endRecording
		return VitruviusChangeFactory.instance.createCompositeChange(changeRecorder.changes)
	}

	/**
	 * Grants access to the UMl mockup model instance via the VSUM.
	 */
	protected def ModelInstance getUmlModelInstance() {
		vsum.getModelInstance(getModelVURI("My.uml_mockup"))
	}

	/**
	 * Grants access to the PCM mockup model instance via the VSUM.
	 */
	protected def ModelInstance getPcmModelInstance() {
		vsum.getModelInstance(getModelVURI("My.pcm_mockup"))
	}

	// Methods regarding model creation:
	def private InternalVirtualModel createVirtualModel(String vsumName) {
		var List<VitruvDomain> vitruvDomains = new ArrayList<VitruvDomain>()
		vitruvDomains.add(UmlDomain)
		vitruvDomains.add(PcmDomain)
		return TestUtil.createVirtualModel(vsumName, true, vitruvDomains, Collections.emptyList(),
			UserInteractionFactory.instance.createDummyUserInteractor())
	}

	def private void createPcmMockupModel() {
		startRecording(pcmCheckpoint)
		vsum.executeCommand [
			val contents = pcmCheckpoint.contents
			val repository = Pcm_mockupFactory.eINSTANCE.createRepository
			repository.interfaces.add(Pcm_mockupFactory.eINSTANCE.createPInterface)
			repository.components.add(Pcm_mockupFactory.eINSTANCE.createComponent)
			contents.add(repository)
			return null
		]
		propagateRecordedChanges(pcmModelInstance)
	}

	def private void createUmlMockupModel() {
		startRecording(umlCheckpoint)
		vsum.executeCommand [
			var contents = umlCheckpoint.contents
			var uPackage = Uml_mockupFactory.eINSTANCE.createUPackage
			uPackage.interfaces.add(Uml_mockupFactory.eINSTANCE.createUInterface)
			uPackage.classes.add(Uml_mockupFactory.eINSTANCE.createUClass)
			contents.add(uPackage)
			return null
		]
		propagateRecordedChanges(umlModelInstance)
	}

	// Methods regarding recording and propagating changes:
	def private void propagateRecordedChanges(ModelInstance model) {
		val compositeChange = model.recordedChanges
		vsum.propagateChange(compositeChange)
		startRecording(model.resource)
	}

	private def void startRecording(Notifier notifier) {
		changeRecorder.addToRecording(notifier)
		if (!changeRecorder.isRecording) {
			changeRecorder.beginRecording
		}
	}

	// Methods regarding creating and accessing checkpoints:
	private def getCorrelatingCheckpoint(ModelInstance model) {
		if (model.URI.fileExtension == umlModelInstance.URI.fileExtension) umlCheckpoint else pcmCheckpoint
	}

	private def Resource createCheckpoint(Resource original) {
		val resourceSet = new ResourceSetImpl
		val copy = resourceSet.createResource(original.URI)
		copy.contents.addAll(EcoreUtil.copyAll(original.contents))
		return copy
	}

	// Utility method regarding model folders:
	def private VURI getModelVURI(String fileName) {
		val modelFolder = new File(getCurrentTestProjectFolder(), "model")
		val file = new File(modelFolder, fileName)
		return VURI.getInstance(EMFBridge.getEmfFileUriForFile(file))
	}
}
