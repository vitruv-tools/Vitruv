package tools.vitruv.framework.tests.domains

import java.io.IOException
import java.util.List
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.change.description.CompositeContainerChange
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.util.bridges.EcoreResourceBridge
import tools.vitruv.framework.util.datatypes.ModelInstance
import tools.vitruv.framework.util.datatypes.VURI

import static org.junit.Assert.*
import tools.vitruv.framework.domains.StateChangePropagationStrategy
import tools.vitruv.framework.vsum.InternalVirtualModel
import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder
import tools.vitruv.framework.domains.DefaultStateChangePropagationStrategy
import org.junit.Before
import org.junit.After

abstract class StateChangePropagationTest extends DomainTest {
	protected var StateChangePropagationStrategy strategyToTest
	protected var InternalVirtualModel vsum
	protected var Resource checkpoint
	protected var AtomicEmfChangeRecorder changeRecorder

	@Before
	def void setup() {
		strategyToTest = new DefaultStateChangePropagationStrategy
		vsum = createVirtualModelAndModelInstances
		// fillVsum(vsum) // TODO TS remove!
		// createMockupModelsWithDefaultUris(vsum) // TODO TS remove!
		checkpoint = umlModelInstance.createCheckpoint
		changeRecorder = new AtomicEmfChangeRecorder(vsum.uuidGeneratorAndResolver)
		umlModelInstance.resource.startRecordingChanges
	}

	@After
	def void teardown() {
		if (changeRecorder.isRecording) {
			changeRecorder.stopRecording
		}
	}

	protected def void compareWithRecordedChanges(ModelInstance model) {
		val change = changeFromComparisonWithCheckpoint
		assertFalse("Composite change is empty!", change.EChanges.empty)
		assertEquals(change, model.recordedChanges)
	}

	protected def VitruviusChange getChangeFromComparisonWithCheckpoint() {
		vsum.save
		val change = strategyToTest.getChangeSequences(umlModelInstance.resource, checkpoint, vsum.uuidGeneratorAndResolver)
		assertNotNull(change)
		return change
	}

	protected def ModelInstance getUmlModelInstance() {
		vsum.getModelInstance(VURI.getInstance(defaultUMLInstanceURI))
	}

	protected def ModelInstance getPcmModelInstance() {
		vsum.getModelInstance(VURI.getInstance(defaultPcmInstanceURI))
	}

	protected def void startRecordingChanges(Resource resource) {
		changeRecorder.addToRecording(resource)
		if (!changeRecorder.isRecording) {
			changeRecorder.beginRecording
		}
	}

	private def CompositeContainerChange getRecordedChanges(ModelInstance model) throws IOException {
		// val resource = model.getUniqueRootEObjectIfCorrectlyTyped(UPackage).eResource // TODO TS
		val resource = model.resource
		EcoreResourceBridge.saveResource(resource)
		changeRecorder.endRecording
		val List<TransactionalChange> changes = changeRecorder.changes
		val CompositeContainerChange compositeChange = VitruviusChangeFactory.instance.createCompositeChange(changes)
		return compositeChange
	}

	private def Resource createCheckpoint(ModelInstance model) { // TODO TS model resource or root resource?
		val resource = model.resource
		val resourceSet = new ResourceSetImpl
		val copy = resourceSet.createResource(resource.URI)
		copy.contents.addAll(EcoreUtil.copyAll(resource.contents))
		return copy
	}
}
