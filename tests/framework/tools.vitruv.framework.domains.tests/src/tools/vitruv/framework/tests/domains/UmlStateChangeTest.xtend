package tools.vitruv.framework.tests.domains

import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.junit.Test
import tools.vitruv.framework.uuid.UuidGeneratorAndResolverImpl
import uml_mockup.UPackage
import uml_mockup.Uml_mockupFactory

import static org.junit.Assert.*

class UmlStateChangeTest extends StateChangePropagationTest {

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
}
