package tools.vitruv.framework.tests.domains

import java.util.List
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.After
import org.junit.Before
import tools.vitruv.framework.change.description.CompositeContainerChange
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder
import tools.vitruv.framework.domains.DefaultStateChangePropagationStrategy
import tools.vitruv.framework.domains.StateChangePropagationStrategy
import tools.vitruv.framework.util.bridges.EcoreResourceBridge
import tools.vitruv.framework.util.datatypes.ModelInstance
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.vsum.InternalVirtualModel

import static org.junit.Assert.*

abstract class StateChangePropagationTest extends DomainTest {
	protected var StateChangePropagationStrategy strategyToTest
	protected var InternalVirtualModel vsum
	protected var Resource umlCheckpoint
	protected var Resource pcmCheckpoint
	protected var AtomicEmfChangeRecorder changeRecorder

	/**
	 * Creates the strategy, sets up the test model and prepares everything for detemining changes.
	 */
	@Before
	def void setup() {
		strategyToTest = new DefaultStateChangePropagationStrategy
		vsum = createVirtualModelAndModelInstances
		changeRecorder = new AtomicEmfChangeRecorder(vsum.uuidGeneratorAndResolver)
		umlCheckpoint = umlModelInstance.firstRootEObject.eResource.createCheckpoint
		pcmCheckpoint = pcmModelInstance.firstRootEObject.eResource.createCheckpoint
		umlModelInstance.firstRootEObject.startRecordingChanges
		pcmModelInstance.firstRootEObject.startRecordingChanges
	}

	/**
	 * Stops recording in case the test does not call getRecordedChanges() or getChangeFromComparisonWithCheckpoint().
	 */
	@After
	def void teardown() {
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
		assertEquals(change, model.recordedChanges)
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
		val resource = model.resource
		EcoreResourceBridge.saveResource(resource)
		changeRecorder.endRecording
		val List<TransactionalChange> changes = changeRecorder.changes
		val CompositeContainerChange compositeChange = VitruviusChangeFactory.instance.createCompositeChange(changes)
		return compositeChange
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

	private def void startRecordingChanges(Notifier notifier) {
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
