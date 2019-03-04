package tools.vitruv.framework.tests.vsum

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.domains.DefaultStateChangePropagationStrategy
import tools.vitruv.framework.domains.StateChangePropagationStrategy
import tools.vitruv.framework.util.datatypes.ModelInstance
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.uuid.UuidGeneratorAndResolverImpl
import tools.vitruv.framework.vsum.InternalVirtualModel
import uml_mockup.UClass
import uml_mockup.UPackage
import uml_mockup.Uml_mockupFactory

import static org.junit.Assert.*

class StateChangePropagationStrategyTest extends DomainTest {
	var StateChangePropagationStrategy strategyToTest
	var InternalVirtualModel vsum
	var Resource checkpoint

	@Before
	def void setup() {
		strategyToTest = new DefaultStateChangePropagationStrategy
		vsum = createVirtualModelAndModelInstances()
		// fillVsum(vsum) // TODO TS remove!
		// createMockupModelsWithDefaultUris(vsum) // TODO TS remove!
		checkpoint = umlModelInstance.createCheckpoint()
	}

	@Test
	def void testNoChange() {
		val change = strategyToTest.getChangeSequences(umlModelInstance.resource, checkpoint, vsum.uuidGeneratorAndResolver)
		assertNotNull(change)
		assertTrue("Composite change contains children!", change.EChanges.empty)
	}

	@Test
	def void testNewClass() {
		vsum.addNewUmlClass()
		val change = strategyToTest.getChangeSequences(umlModelInstance.resource, checkpoint, vsum.uuidGeneratorAndResolver)
		assertNotNull(change)
		assertFalse("Composite change is empty!", change.EChanges.empty) // TODO TS check more than that, compare with real sequences
	}

	@Test
	def void testNullResources() {
		val resourceSet = new ResourceSetImpl
		val resolver = new UuidGeneratorAndResolverImpl(resourceSet, false)
		val change = strategyToTest.getChangeSequences(null, null, resolver)
		assertNotNull(change)
		assertTrue("Composite change contains children!", change.EChanges.empty)
	}

	@Test(expected=IllegalArgumentException)
	def void testNullResolver() {
		strategyToTest.getChangeSequences(null, null, null)
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

	def private void addNewUmlClass(InternalVirtualModel vsum) {
		val modelURI = VURI.getInstance(defaultUMLInstanceURI)
		val ModelInstance model = vsum.getModelInstance(modelURI)
		vsum.executeCommand([
			var UPackage pckg = model.getUniqueRootEObjectIfCorrectlyTyped(UPackage)
			val UClass uClass = Uml_mockupFactory.eINSTANCE.createUClass();
			uClass.name = "newlyAddedClass"
			pckg.getClasses().add(uClass);
			return null
		])
		vsum.save() // (modelURI);
	}
}
