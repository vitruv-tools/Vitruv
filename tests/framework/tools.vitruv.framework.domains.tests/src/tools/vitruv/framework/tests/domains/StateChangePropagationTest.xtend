package tools.vitruv.framework.tests.domains

import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.After
import org.junit.Before
import pcm_mockup.Pcm_mockupFactory
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder
import tools.vitruv.framework.domains.DefaultStateChangePropagationStrategy
import tools.vitruv.framework.domains.StateChangePropagationStrategy
import tools.vitruv.framework.util.bridges.EcoreResourceBridge
import tools.vitruv.framework.util.datatypes.ModelInstance
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.vsum.InternalVirtualModel
import uml_mockup.UPackage
import uml_mockup.Uml_mockupFactory

import static org.junit.Assert.*

abstract class StateChangePropagationTest extends DomainTest {
	protected var StateChangePropagationStrategy strategyToTest
	protected var InternalVirtualModel vsum
	protected var Resource umlCheckpoint
	protected var Resource pcmCheckpoint
	protected var AtomicEmfChangeRecorder changeRecorder

	override protected createChangePropagationSpecifications() {
		return #[]
	}

	override protected getVitruvDomains() {
		return #[]
	}

	/**
	 * Creates the strategy, sets up the test model and prepares everything for detemining changes.
	 */
	@Before
	override void setup() {
		strategyToTest = new DefaultStateChangePropagationStrategy
		changeRecorder = new AtomicEmfChangeRecorder(vsum.uuidGeneratorAndResolver)
		vsum = createVirtualModel(VSUM_NAME)
		createPcmMockupModel()
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
	override void cleanup() {
		if (changeRecorder.isRecording) {
			changeRecorder.stopRecording
		}
	}

	/**
	 * Compares two changes: The recorded change sequence and the resolved changes by the state delta based strategy.
	 */
	protected def void compareRecordedChanges(ModelInstance model) {
		val change = model.changeFromComparisonWithCheckpoint
		assertFalse("Composite change is empty!", change.EChanges.empty)
		assertTrue(change.changedEObjectEquals(model.recordedChanges))
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
		vsum.getModelInstance(VURI.getInstance(defaultUMLInstanceURI))
	}

	/**
	 * Grants access to the PCM mockup model instance via the VSUM.
	 */
	protected def ModelInstance getPcmModelInstance() {
		vsum.getModelInstance(VURI.getInstance(defaultPcmInstanceURI))
	}

	def private void createPcmMockupModel() {
		startRecording(pcmModelInstance.resource)
		val contents = pcmModelInstance.resource.contents
		val repository = Pcm_mockupFactory.eINSTANCE.createRepository()
		repository.interfaces.add(Pcm_mockupFactory.eINSTANCE.createPInterface())
		repository.components.add(Pcm_mockupFactory.eINSTANCE.createComponent())
		contents.add(repository)
		propagateRecordedChanges(pcmModelInstance)
	}

	def private void createUmlMockupModel() {
		startRecording(pcmModelInstance.resource)
		var contents = umlModelInstance.resource.contents
		var UPackage pckg = Uml_mockupFactory.eINSTANCE.createUPackage()
		pckg.interfaces.add(Uml_mockupFactory.eINSTANCE.createUInterface())
		pckg.classes.add(Uml_mockupFactory.eINSTANCE.createUClass())
		contents.add(pckg)
		propagateRecordedChanges(pcmModelInstance)
	}

	def private void propagateRecordedChanges(ModelInstance model) {
		val compositeChange = model.recordedChanges
		vsum.propagateChange(compositeChange);
		startRecording(model.resource)
	}

	private def void startRecording(Notifier notifier) {
		changeRecorder.addToRecording(notifier)
		if (!changeRecorder.isRecording) {
			changeRecorder.beginRecording
		}
	}

	private def getCorrelatingCheckpoint(ModelInstance model) {
		if (model.URI.fileExtension == umlModelInstance.URI.fileExtension) umlCheckpoint else pcmCheckpoint
	}

	private def Resource createCheckpoint(Resource original) {
		val resourceSet = new ResourceSetImpl
		val copy = resourceSet.createResource(original.URI)
		copy.contents.addAll(EcoreUtil.copyAll(original.contents))
		return copy
	}
}
