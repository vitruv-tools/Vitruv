package tools.vitruv.change.atomic.feature.attribute

import allElementTypes.AllElementTypesPackage
import allElementTypes.Root
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tools.vitruv.change.atomic.EChange
import tools.vitruv.change.atomic.EChangeTest
import tools.vitruv.change.atomic.uuid.Uuid

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertThrows
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.*

/**
 * Test class for the concrete {@link ReplaceSingleValuedEAttribute} EChange, 
 * which replaces the value of an attribute with a new one.
 */
class ReplaceSingleValuedEAttributeTest extends EChangeTest {
	var Root affectedEObject
	var EAttribute affectedFeature
	var String oldValue
	var String newValue

	static val DEFAULT_ROOT_NAME = "Root Element"
	static val DEFAULT_SINGLE_VALUED_EATTRIBUTE_VALUE = 123

	@BeforeEach
	def void beforeTest() {
		affectedEObject = rootObject
		affectedFeature = AllElementTypesPackage.Literals.IDENTIFIED__ID
		oldValue = DEFAULT_ROOT_NAME
		newValue = "New Root ID"
		prepareStateBefore
	}

	/**
	 * Tests whether resolving the {@link ReplaceSingleValuedEAttribute} EChange returns 
	 * the same class.
	 */
	@Test
	def void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange()

		// Resolve
		val resolvedChange = unresolvedChange.applyForwardAndResolve
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}

	/**
	 * Tests applying a {@link ReplaceSingleValuedEAttribute} EChange forward
	 * by replacing a single value in a root element with a new value.
	 */
	@Test
	def void applyForwardTest() {
		// Create change
		val unresolvedChange = createUnresolvedChange()
		unresolvedChange.applyForwardAndResolve

		// State after
		assertIsStateAfter
	}

	/**
	 * Tests applying a {@link ReplaceSingleValuedEAttribute} EChange backward
	 * by replacing a single value in a root element with the old value.
	 */
	@Test
	def void applyBackwardTest() {
		// Create change
		val resolvedChange = createUnresolvedChange().applyForwardAndResolve

		// Set state after
		prepareStateAfter

		// Apply backward
		resolvedChange.applyBackward

		// State before
		assertIsStateBefore
	}

	/**
	 * Tests an affected object which has no such attribute.
	 */
	@Test
	def void invalidAttributeTest() {
		// NonRoot element has no int attribute.
		val affectedNonRootEObject = aet.NonRoot
		resource.contents.add(affectedNonRootEObject)
		val affectedRootFeature = AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_EATTRIBUTE
		val oldIntValue = DEFAULT_SINGLE_VALUED_EATTRIBUTE_VALUE
		val newIntValue = 500

		// Resolving the change will be tested in EFeatureChange
		val resolvedChange = atomicFactory.<EObject, Integer>createReplaceSingleAttributeChange(affectedNonRootEObject,
			affectedRootFeature, oldIntValue, newIntValue)

		// NonRoot has no such feature
		assertEquals(affectedNonRootEObject.eClass.getFeatureID(affectedRootFeature), -1)

		// Apply
		assertThrows(IllegalStateException) [resolvedChange.applyBackward]
	}

	/**
	 * Tests a {@link ReplaceSingleValuedEAttribue} EChange with the wrong value type.
	 */
	@Test
	def void invalidValueTest() {
		val oldIntValue = DEFAULT_SINGLE_VALUED_EATTRIBUTE_VALUE // values are Integer, attribute value type is String
		val newIntValue = 500

		// Create and resolve change
		val resolvedChange = atomicFactory.<EObject, Integer>createReplaceSingleAttributeChange(affectedEObject,
			affectedFeature, oldIntValue, newIntValue)

		// Type of attribute is String not Integer
		assertEquals(affectedFeature.EAttributeType.name, "EString")

		// Apply
		assertThrows(IllegalStateException) [resolvedChange.applyBackward]
	}

	/**
	 * Set state before the change
	 */
	def private void prepareStateBefore() {
		rootObject.eSet(affectedFeature, oldValue)
		assertIsStateBefore
	}

	/**
	 * Set state after the change
	 */
	def private void prepareStateAfter() {
		rootObject.setId(newValue)
		assertIsStateAfter
	}

	/** 
	 * Model is in state before the change.
	 */
	def private void assertIsStateBefore() {
		assertEquals(affectedEObject.eGet(affectedFeature), oldValue)
	}

	/**
	 * Model is in state after the change.
	 */
	def private void assertIsStateAfter() {
		assertEquals(affectedEObject.eGet(affectedFeature), newValue)
	}

	/**
	 * Creates new unresolved change.
	 */
	def private EChange<Uuid> createUnresolvedChange() {
		// The concrete change type ReplaceSingleEAttributeChange will be used for the tests.
		return atomicFactory.createReplaceSingleAttributeChange(affectedEObject, affectedFeature, oldValue, newValue).unresolve
	}
}
