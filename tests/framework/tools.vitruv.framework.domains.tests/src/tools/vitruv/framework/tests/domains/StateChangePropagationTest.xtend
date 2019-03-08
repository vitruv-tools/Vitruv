package tools.vitruv.framework.tests.domains

import java.io.IOException
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
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
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.common.notify.Notifier

abstract class StateChangePropagationTest extends DomainTest {
	protected var StateChangePropagationStrategy strategyToTest
	protected var InternalVirtualModel vsum
	protected var Resource umlCheckpoint
	protected var Resource pcmCheckpoint
	protected var AtomicEmfChangeRecorder changeRecorder

	@Before
	def void setup() {
		strategyToTest = new DefaultStateChangePropagationStrategy
		vsum = createVirtualModelAndModelInstances
		// fillVsum(vsum) // TODO TS remove!
		// createMockupModelsWithDefaultUris(vsum) // TODO TS remove!
		umlCheckpoint = umlModelInstance.createCheckpoint
		pcmCheckpoint = pcmModelInstance.createCheckpoint
		changeRecorder = new AtomicEmfChangeRecorder(vsum.uuidGeneratorAndResolver)
		umlModelInstance.firstRootEObject.startRecordingChanges // TODO resource? object?
		pcmModelInstance.firstRootEObject.startRecordingChanges
	}

	@After
	def void teardown() {
		if (changeRecorder.isRecording) {
			changeRecorder.stopRecording
		}
	}

	protected def void compareRecordedChanges(ModelInstance model) {
		val change = model.changeFromComparisonWithCheckpoint
		assertFalse("Composite change is empty!", change.EChanges.empty)
		assertEquals(change, model.recordedChanges)
	}

	protected def VitruviusChange getChangeFromComparisonWithCheckpoint(ModelInstance model) {
		vsum.save
		val change = strategyToTest.getChangeSequences(model.resource, model.correlatingCheckpoint, vsum.uuidGeneratorAndResolver) // TODO TS model resource? root object?
		assertNotNull(change)
		return change
	}

	protected def ModelInstance getUmlModelInstance() {
		vsum.getModelInstance(VURI.getInstance(defaultUMLInstanceURI))
	}

	protected def ModelInstance getPcmModelInstance() {
		vsum.getModelInstance(VURI.getInstance(defaultPcmInstanceURI))
	}

	protected def CompositeContainerChange getRecordedChanges(ModelInstance model) throws IOException {
		val resource = model.resource
		EcoreResourceBridge.saveResource(resource)
		changeRecorder.endRecording
		val List<TransactionalChange> changes = changeRecorder.changes
		val CompositeContainerChange compositeChange = VitruviusChangeFactory.instance.createCompositeChange(changes)
		return compositeChange
	}

	protected def void startRecordingChanges(Notifier notifier) {
		changeRecorder.addToRecording(notifier)
		if (!changeRecorder.isRecording) {
			changeRecorder.beginRecording
		}
	}

	private def getCorrelatingCheckpoint(ModelInstance model) {
		if (model.URI.fileExtension == umlModelInstance.URI.fileExtension) umlCheckpoint else pcmCheckpoint
	}

	private def Resource createCheckpoint(ModelInstance model) { // TODO TS model resource or root resource?
	// return EcoreUtil.copy(model.firstRootEObject)
		val resourceSet = new ResourceSetImpl
		val copy = resourceSet.createResource(model.resource.URI)
		copy.contents.addAll(EcoreUtil.copyAll(model.resource.contents))
		return copy
	}
}
