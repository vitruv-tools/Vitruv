package tools.vitruv.change.atomic.feature.attribute

import org.eclipse.emf.ecore.EObject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tools.vitruv.change.atomic.EChange
import tools.vitruv.change.atomic.uuid.Uuid

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertThrows
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.*

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
		val resolvedChange = unresolvedChange.applyForwardAndResolve
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}

	/**
	 * Tests applying the {@link RemoveEAttributeValue} EChange forward
	 * by removing two values from an multivalued attribute.
	 */
	@Test
	def void applyForwardTest() {
		// Create change and resolve
		val unresolvedChange = createUnresolvedChange(NEW_VALUE, 0)
		unresolvedChange.applyForwardAndResolve

		assertEquals(attributeContent.size, 1)
		assertEquals(attributeContent.get(0), NEW_VALUE_2)

		// Create change and resolve 2
		val unresolvedChange2 = createUnresolvedChange(NEW_VALUE_2, 0)
		unresolvedChange2.applyForwardAndResolve

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
		val resolvedChange = createUnresolvedChange(NEW_VALUE, 0).applyForwardAndResolve

		// Create change and resolve and apply 2
		val resolvedChange2 = createUnresolvedChange(NEW_VALUE_2, 0).applyForwardAndResolve

		// State after	
		assertIsStateAfter

		// Apply backward 2
		resolvedChange2.applyBackward

		assertEquals(attributeContent.size, 1)
		assertEquals(attributeContent.get(0), NEW_VALUE_2)

		// Apply backward 1
		resolvedChange.applyBackward

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
		val unresolvedChange = createUnresolvedChange(NEW_VALUE, index)

		// Apply
		assertThrows(IllegalStateException) [unresolvedChange.applyForwardAndResolve]
	}

	/**
	 * Tests an affected object which has no such attribute.
	 */
	@Test
	def void invalidAttributeTest() {
		val affectedNonRootEObject = aet.NonRoot
		resource.contents.add(affectedNonRootEObject)

		// Create change and resolve
		val resolvedChange = atomicFactory.<EObject, Integer>createRemoveAttributeChange(affectedNonRootEObject,
			affectedFeature, 0, NEW_VALUE)

		// NonRoot has no such feature
		assertEquals(affectedNonRootEObject.eClass.getFeatureID(affectedFeature), -1)

		// Apply
		assertThrows(IllegalStateException) [resolvedChange.applyBackward]
	}

	/**
	 * Tests a {@link RemoveEAttributeValue} EChange with the wrong value type.
	 */
	@Test
	def void invalidValueTest() {
		val newInvalidValue = "New String Value" // values are Strings, attribute value type is Integer
		// Create change and resolve
		val resolvedChange = atomicFactory.<EObject, String>createRemoveAttributeChange(affectedEObject, affectedFeature,
			0, newInvalidValue)

		// Type of attribute is Integer not String
		assertEquals(affectedFeature.EAttributeType.name, "EIntegerObject")

		// Apply
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
	def private EChange<Uuid> createUnresolvedChange(int newValue, int index) {
		// The concrete change type ReplaceSingleEAttributeChange will be used for the tests.
		return atomicFactory.createRemoveAttributeChange(affectedEObject, affectedFeature, index, newValue).unresolve
	}
}
