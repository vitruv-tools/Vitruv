package tools.vitruv.change.atomic.feature.attribute

import org.eclipse.emf.ecore.EObject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tools.vitruv.change.atomic.EChange
import tools.vitruv.change.atomic.uuid.Uuid

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertThrows
import static org.junit.jupiter.api.Assertions.assertTrue
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.*

/**
 * Test class for the concrete {@link InsertEAttributeValue} EChange,
 * which inserts a value in a multi valued attribute.
 */
class InsertEAttributeValueTest extends InsertRemoveEAttributeTest {

	@BeforeEach
	def void assertStateBefore() {
		assertIsStateBefore
	}

	/**
	 * Tests whether resolving the {@link InsertEAttributeValue} EChange returns the same class.
	 */
	@Test
	def void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange(NEW_VALUE, 0)

		// Resolve		
		val resolvedChange = unresolvedChange.applyForwardAndResolve
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}

	/**
	 * Tests applying two {@link InsertEAttributeValue} EChanges forward by 
	 * inserting new values in a multivalued attribute.
	 */
	@Test
	def void applyForwardTest() {
		// Create change and resolve
		val unresolvedChange = createUnresolvedChange(NEW_VALUE, 0)
		unresolvedChange.applyForwardAndResolve

		assertEquals(attributeContent.size, 1)
		assertEquals(attributeContent.get(0), NEW_VALUE)

		// Create change and resolve 2
		val unresolvedChange2 = createUnresolvedChange(NEW_VALUE_2, 1)
		unresolvedChange2.applyForwardAndResolve

		// State after
		assertIsStateAfter
	}

	/**
	 * Applies two {@link InsertEAttributeValue} EChanges backward.
	 */
	@Test
	def void applyBackwardTest() {
		// Create change and resolve and apply forward
		val resolvedChange = createUnresolvedChange(NEW_VALUE, 0).applyForwardAndResolve

		// Create change and resolve and apply forward 2
		val resolvedChange2 = createUnresolvedChange(NEW_VALUE_2, 1).applyForwardAndResolve

		// State after
		assertIsStateAfter

		// Apply backward 2
		resolvedChange2.applyBackward

		assertEquals(attributeContent.size, 1)
		assertEquals(attributeContent.get(0), NEW_VALUE)

		// Apply backward 1
		resolvedChange.applyBackward

		// State before
		assertIsStateBefore
	}

	/**
	 * Tests the {@link InsertEAttributeValue} EChange with invalid index.
	 */
	@Test
	def void invalidIndexTest() {
		val index = 5 // Valid index in empty list is only 0
		assertTrue(attributeContent.empty)

		// Create change and resolve
		val unresolvedChange = createUnresolvedChange(NEW_VALUE, index)

		// Apply
		assertThrows(IllegalStateException) [unresolvedChange.applyForwardAndResolve]
	}

	/**
	 * Tests an {@link InsertEAttributeValue} with an affected object which has no such attribute.
	 */
	@Test
	def void invalidAttributeTest() {
		// NonRoot has no multi-valued int attribute
		val affectedNonRootEObject = aet.NonRoot
		resource.contents.add(affectedNonRootEObject)

		// Resolving the change will be tested in EFeatureChange
		val resolvedChange = atomicFactory.<EObject, Integer>createInsertAttributeChange(affectedNonRootEObject,
			affectedFeature, 0, NEW_VALUE)

		// NonRoot has no such feature
		assertEquals(affectedNonRootEObject.eClass.getFeatureID(affectedFeature), -1)

		assertThrows(IllegalStateException) [resolvedChange.applyBackward]
	}

	/**
	 * Tests an {@link InsertEAttributeValue} EChange with the wrong value type.
	 */
	@Test
	def void invalidValueTest() {
		val newInvalidValue = "New String Value" // values are String, attribute value type is Integer
		// Resolving the change will be tested in EFeatureChange
		val resolvedChange = atomicFactory.<EObject, String>createInsertAttributeChange(affectedEObject, affectedFeature, 0,
			newInvalidValue)

		// Type of attribute is Integer not String
		assertEquals(affectedFeature.EAttributeType.name, "EIntegerObject")

		assertThrows(IllegalStateException) [resolvedChange.applyBackward]
	}

	/**
	 * Model is in state before the changes.
	 */
	def private void assertIsStateBefore() {
		assertEquals(attributeContent.size, 0)
	}

	/**
	 * Model is in state after the changes.
	 */
	def private void assertIsStateAfter() {
		assertEquals(attributeContent.size, 2)
		assertEquals(attributeContent.get(0), NEW_VALUE)
		assertEquals(attributeContent.get(1), NEW_VALUE_2)
	}

	/**
	 * Creates new unresolved change.
	 */
	def private EChange<Uuid> createUnresolvedChange(int newValue, int index) {
		// The concrete change type ReplaceSingleEAttributeChange will be used for the tests.
		return atomicFactory.createInsertAttributeChange(affectedEObject, affectedFeature, index, newValue).unresolve
	}
}
