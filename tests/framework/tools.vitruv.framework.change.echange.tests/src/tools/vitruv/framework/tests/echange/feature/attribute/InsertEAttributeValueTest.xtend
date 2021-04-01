package tools.vitruv.framework.tests.echange.feature.attribute

import allElementTypes.NonRoot
import allElementTypes.Root
import tools.vitruv.framework.change.echange.feature.attribute.InsertEAttributeValue

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertThrows
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.*
import static extension tools.vitruv.framework.change.echange.resolve.EChangeResolverAndApplicator.*

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
		val resolvedChange = unresolvedChange.resolveBefore
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}

	/**
	 * Tests applying two {@link InsertEAttributeValue} EChanges forward by 
	 * inserting new values in a multivalued attribute.
	 */
	@Test
	def void applyForwardTest() {
		// Create change and resolve
		val resolvedChange = createUnresolvedChange(NEW_VALUE, 0).resolveBefore as InsertEAttributeValue<Root, Integer>

		// Apply forward
		resolvedChange.assertApplyForward

		assertEquals(attributeContent.size, 1)
		assertEquals(attributeContent.get(0), NEW_VALUE)

		// Create change and resolve 2
		val resolvedChange2 = createUnresolvedChange(NEW_VALUE_2, 1).
			resolveBefore as InsertEAttributeValue<Root, Integer>

		// Apply forward 2
		resolvedChange2.assertApplyForward

		// State after
		assertIsStateAfter
	}

	/**
	 * Applies two {@link InsertEAttributeValue} EChanges backward.
	 */
	@Test
	def void applyBackwardTest() {
		// Create change and resolve and apply forward
		val resolvedChange = createUnresolvedChange(NEW_VALUE, 0).resolveBefore as InsertEAttributeValue<Root, Integer>
		resolvedChange.assertApplyForward

		// Create change and resolve and apply forward 2
		val resolvedChange2 = createUnresolvedChange(NEW_VALUE_2, 1).
			resolveBefore as InsertEAttributeValue<Root, Integer>
		resolvedChange2.assertApplyForward

		// State after
		assertIsStateAfter

		// Apply backward 2
		resolvedChange2.assertApplyBackward

		assertEquals(attributeContent.size, 1)
		assertEquals(attributeContent.get(0), NEW_VALUE)

		// Apply backward 1
		resolvedChange.assertApplyBackward

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
		val resolvedChange = createUnresolvedChange(NEW_VALUE, index).
			resolveBefore as InsertEAttributeValue<Root, Integer>
		assertTrue(resolvedChange.isResolved)

		// Apply
		assertThrows(IllegalStateException) [resolvedChange.applyForward]
		// assertFalse(resolvedChange.applyForward)
		assertThrows(IllegalStateException) [resolvedChange.applyBackward]
		// assertFalse(resolvedChange.applyBackward)
	}

	/**
	 * Tests an {@link InsertEAttributeValue} with an affected object which has no such attribute.
	 */
	@Test
	def void invalidAttributeTest() {
		// NonRoot has no multi-valued int attribute
		val affectedNonRootEObject = aet.NonRoot.withUuid.registerAsPreexisting
		resource.contents.add(affectedNonRootEObject)

		// Resolving the change will be tested in EFeatureChange
		val resolvedChange = atomicFactory.<NonRoot, Integer>createInsertAttributeChange(affectedNonRootEObject,
			affectedFeature, 0, NEW_VALUE).resolveBefore

		// NonRoot has no such feature
		assertEquals(affectedNonRootEObject.eClass.getFeatureID(affectedFeature), -1)

		assertThrows(IllegalStateException) [resolvedChange.applyForward]
		assertThrows(IllegalStateException) [resolvedChange.applyBackward]
	}

	/**
	 * Tests an {@link InsertEAttributeValue} EChange with the wrong value type.
	 */
	@Test
	def void invalidValueTest() {
		val newInvalidValue = "New String Value" // values are String, attribute value type is Integer
		// Resolving the change will be tested in EFeatureChange
		val resolvedChange = atomicFactory.createInsertAttributeChange(affectedEObject, affectedFeature, 0,
			newInvalidValue).resolveBefore

		// Type of attribute is Integer not String
		assertEquals(affectedFeature.EAttributeType.name, "EIntegerObject")

		assertThrows(IllegalStateException) [resolvedChange.applyForward]
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
	def private InsertEAttributeValue<Root, Integer> createUnresolvedChange(int newValue, int index) {
		// The concrete change type ReplaceSingleEAttributeChange will be used for the tests.
		return atomicFactory.createInsertAttributeChange(affectedEObject, affectedFeature, index, newValue)
	}
}
