package tools.vitruv.framework.tests.domains

import java.io.IOException
import java.util.List
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.After
import org.junit.Before
import org.junit.Test
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
import tools.vitruv.framework.uuid.UuidGeneratorAndResolverImpl
import tools.vitruv.framework.vsum.InternalVirtualModel
import uml_mockup.UPackage
import uml_mockup.Uml_mockupFactory

import static org.junit.Assert.*

class StateChangePropagationStrategyTest extends DomainTest {
	var StateChangePropagationStrategy strategyToTest
	var InternalVirtualModel vsum
	var Resource checkpoint
	var AtomicEmfChangeRecorder changeRecorder

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

	@Test
	def void testNoChange() {
		val change = changeFromComparisonWithCheckpoint
		assertTrue("Composite change contains children!", change.EChanges.empty)
		assertEquals(change, recordedChanges)
	}

	@Test
	def void testRenameClass() {
		vsum.executeCommand([
			var uPackage = umlModelInstance.getUniqueRootEObjectIfCorrectlyTyped(UPackage)
			var uClass = uPackage.classes.get(0)
			uClass.name = "RenamedClass"
			return null
		])
		val original = recordedChanges
		val change = changeFromComparisonWithCheckpoint
		assertFalse("Composite change is empty!", change.EChanges.empty)
		assertEquals(change, original)
	}

	@Test
	def void testNewMethod() {
		vsum.executeCommand([
			var uPackage = umlModelInstance.getUniqueRootEObjectIfCorrectlyTyped(UPackage)
			var uClass = uPackage.classes.get(0)
			var uAttribute = Uml_mockupFactory.eINSTANCE.createUAttribute
			uAttribute.attributeName = "NewlyAddedAttribute"
			uClass.attributes.add(uAttribute)
			return null
		])
		val original = recordedChanges
		val change = changeFromComparisonWithCheckpoint
		assertFalse("Composite change is empty!", change.EChanges.empty)
		assertEquals(change, original)
	}

	@Test
	def void testNewClass() {
		vsum.executeCommand([
			var uPackage = umlModelInstance.getUniqueRootEObjectIfCorrectlyTyped(UPackage)
			val uClass = Uml_mockupFactory.eINSTANCE.createUClass
			uClass.name = "NewlyAddedInterface"
			uClass.id = "NewlyAddedInterface"
			uPackage.classes.add(uClass)
			return null
		])
		val original = recordedChanges
		val change = changeFromComparisonWithCheckpoint
		assertFalse("Composite change is empty!", change.EChanges.empty)
		assertEquals(change, original)
	}

	@Test
	def void testNewInterface() {
		vsum.executeCommand([
			var uPackage = umlModelInstance.getUniqueRootEObjectIfCorrectlyTyped(UPackage)
			val uInterface = Uml_mockupFactory.eINSTANCE.createUInterface
			uInterface.name = "NewlyAddedInterface"
			uInterface.id = "NewlyAddedInterface"
			uPackage.interfaces.add(uInterface)
			return null
		])
		val change = changeFromComparisonWithCheckpoint
		assertFalse("Composite change is empty!", change.EChanges.empty)
		assertEquals(change, recordedChanges) // TODO TS reduce code duplication
	}

	@Test
	def void testNullResources() {
		val resourceSet = new ResourceSetImpl
		val resolver = new UuidGeneratorAndResolverImpl(resourceSet, false)
		val change = strategyToTest.getChangeSequences(null, null, resolver)
		assertTrue("Composite change contains children!", change.EChanges.empty)
	}

	@Test(expected=IllegalArgumentException)
	def void testNullResolver() {
		strategyToTest.getChangeSequences(null, null, null)
	}

	private def VitruviusChange getChangeFromComparisonWithCheckpoint() {
		vsum.save
		val change = strategyToTest.getChangeSequences(umlModelInstance.resource, checkpoint, vsum.uuidGeneratorAndResolver)
		assertNotNull(change)
		return change
	}

	def private Resource createCheckpoint(ModelInstance model) { // TODO TS model resource or root resource?
		val resource = model.resource
		val resourceSet = new ResourceSetImpl
		val copy = resourceSet.createResource(resource.URI)
		copy.contents.addAll(EcoreUtil.copyAll(resource.contents))
		return copy
	}

	private def ModelInstance getUmlModelInstance() {
		vsum.getModelInstance(VURI.getInstance(defaultUMLInstanceURI))
	}

	def private CompositeContainerChange getRecordedChanges() throws IOException {
		// val resource = umlModelInstance.getUniqueRootEObjectIfCorrectlyTyped(UPackage).eResource // TODO TS
		val resource = umlModelInstance.resource
		EcoreResourceBridge.saveResource(resource)
		changeRecorder.endRecording
		val List<TransactionalChange> changes = changeRecorder.changes
		val CompositeContainerChange compositeChange = VitruviusChangeFactory.instance.createCompositeChange(changes)
		return compositeChange
	}

	private def void startRecordingChanges(Resource resource) {
		changeRecorder.addToRecording(resource)
		if (!changeRecorder.isRecording) {
			changeRecorder.beginRecording
		}
	}
}
