package tools.vitruv.framework.tests.echange.feature.attribute

import allElementTypes.NonRoot
import allElementTypes.Root
import tools.vitruv.framework.change.echange.feature.attribute.RemoveEAttributeValue

import static extension tools.vitruv.framework.tests.echange.util.EChangeAssertHelper.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNull
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.*
import static org.junit.jupiter.api.Assertions.assertThrows
import static extension tools.vitruv.framework.change.echange.resolve.EChangeResolverAndApplicator.*

/**
 * Test class for the concrete {@link RemoveEAttributeValue} EChange,
 * which removes a value in a multivalued attribute.
 */
class RemoveEAttributeValueTest extends InsertRemoveEAttributeTest {

	@BeforeEach
	def void prepareState() {
		prepareStateBefore
	}

	/**
	 * Tests whether resolving the {@link RemoveEAttributeValue} EChange
	 * returns the same class. 
	 */
	@Test
	def void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange(NEW_VALUE, 0)

		// Resolve		
		val resolvedChange = unresolvedChange.resolveBefore
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}

	/**
	 * Tests applying the {@link RemoveEAttributeValue} EChange forward
	 * by removing two values from an multivalued attribute.
	 */
	@Test
	def void applyForwardTest() {
		// Create change and resolve
		val resolvedChange = createUnresolvedChange(NEW_VALUE, 0).resolveBefore as RemoveEAttributeValue<Root, String>

		// Apply forward
		resolvedChange.assertApplyForward

		assertEquals(attributeContent.size, 1)
		assertEquals(attributeContent.get(0), NEW_VALUE_2)

		// Create change and resolve 2
		val resolvedChange2 = createUnresolvedChange(NEW_VALUE_2, 0).
			resolveBefore as RemoveEAttributeValue<Root, String>

		// Apply forward 2	
		resolvedChange2.assertApplyForward

		// State after
		assertIsStateAfter
	}

	/**
	 * Tests applying the {@link RemoveEAttributeValue} EChange backward
	 * by inserting two removed values from an multivalued attribute.
	 */
	@Test
	def void applyBackwardTest() {
		// Create change and resolve and apply
		val resolvedChange = createUnresolvedChange(NEW_VALUE, 0).resolveBefore as RemoveEAttributeValue<Root, String>
		resolvedChange.assertApplyForward

		// Create change and resolve and apply 2
		val resolvedChange2 = createUnresolvedChange(NEW_VALUE_2, 0).
			resolveBefore as RemoveEAttributeValue<Root, String>
		resolvedChange2.assertApplyForward

		// State after	
		assertIsStateAfter

		// Apply backward 2
		resolvedChange2.assertApplyBackward

		assertEquals(attributeContent.size, 1)
		assertEquals(attributeContent.get(0), NEW_VALUE_2)

		// Apply backward 1
		resolvedChange.assertApplyBackward

		// State before
		assertIsStateBefore
	}

	/**
	 * Tests a {@link RemoveEAttributeValue} EChange with invalid index.
	 */
	@Test
	def void invalidIndexTest() {
		var index = 5 // > 2
		// Create change and resolve
		val resolvedChange = createUnresolvedChange(NEW_VALUE, index).
			resolveBefore as RemoveEAttributeValue<Root, String>
		assertTrue(resolvedChange.isResolved)

		// Apply
		assertThrows(IllegalStateException) [resolvedChange.applyForward]
		assertThrows(IllegalStateException) [resolvedChange.applyBackward]
	}

	/**
	 * Tests an affected object which has no such attribute.
	 */
	@Test
	def void invalidAttributeTest() {
		val affectedNonRootEObject = aet.NonRoot.withUuid.registerAsPreexisting
		resource.contents.add(affectedNonRootEObject)

		// Create change and resolve
		val resolvedChange = atomicFactory.<NonRoot, Integer>createRemoveAttributeChange(affectedNonRootEObject,
			affectedFeature, 0, NEW_VALUE).resolveBefore
		assertTrue(resolvedChange.isResolved)

		// NonRoot has no such feature
		assertEquals(affectedNonRootEObject.eClass.getFeatureID(affectedFeature), -1)

		// Apply
		assertThrows(IllegalStateException) [resolvedChange.applyForward]
		assertThrows(IllegalStateException) [resolvedChange.applyBackward]
	}

	/**
	 * Tests a {@link RemoveEAttributeValue} EChange with the wrong value type.
	 */
	@Test
	def void invalidValueTest() {
		val newInvalidValue = "New String Value" // values are Strings, attribute value type is Integer
		// Create change and resolve
		val resolvedChange = atomicFactory.<Root, String>createRemoveAttributeChange(affectedEObject, affectedFeature,
			0, newInvalidValue).resolveBefore
		assertTrue(resolvedChange.isResolved)

		// Type of attribute is Integer not String
		assertEquals(affectedFeature.EAttributeType.name, "EIntegerObject")

		// Apply
		assertThrows(IllegalStateException) [resolvedChange.applyForward]
		assertThrows(IllegalStateException) [resolvedChange.applyBackward]
	}

	/**
	 * Sets the state of the model before the changes.
	 */
	def private void prepareStateBefore() {
		attributeContent.add(NEW_VALUE)
		attributeContent.add(NEW_VALUE_2)
		assertIsStateBefore
	}

	/** 
	 * Model is in state before the changes. 
	 */
	def private void assertIsStateBefore() {
		assertEquals(attributeContent.size, 2)
		assertEquals(attributeContent.get(0), NEW_VALUE)
		assertEquals(attributeContent.get(1), NEW_VALUE_2)
	}

	/**
	 * Model is in state after the changes.
	 */
	def private void assertIsStateAfter() {
		assertEquals(attributeContent.size, 0)
	}

	/**
	 * Creates new unresolved change.
	 */
	def private RemoveEAttributeValue<Root, Integer> createUnresolvedChange(int newValue, int index) {
		// The concrete change type ReplaceSingleEAttributeChange will be used for the tests.
		return atomicFactory.createRemoveAttributeChange(affectedEObject, affectedFeature, index, newValue)
	}
}
