package tools.vitruv.change.atomic.compound

import allElementTypes.AllElementTypesPackage
import allElementTypes.NonRoot
import allElementTypes.Root
import java.util.List
import org.eclipse.emf.ecore.EReference
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tools.vitruv.change.atomic.EChange
import tools.vitruv.change.atomic.EChangeTest
import tools.vitruv.change.atomic.uuid.Uuid

import static org.hamcrest.CoreMatchers.instanceOf
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.jupiter.api.Assertions.assertNull
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.aet

/**
 * Test class for the concrete {@link CreateAndReplaceNonRoot} EChange,
 * which creates a new non root EObject and replaces a null value
 * in a single valued containment reference.
 */
class CreateAndReplaceNonRootTest extends EChangeTest {
	var Root affectedEObject
	var EReference affectedFeature

	@BeforeEach
	def void beforeTest() {
		affectedEObject = rootObject
		affectedFeature = AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE
		prepareStateBefore
	}

	/**
	 * Tests whether resolving the {@link CreateAndReplaceNonRoot} EChange
	 * returns the same class.
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
	 * Tests the {@link CreateAndReplaceNonRoot} EChange by applying it forward.
	 * Creates and inserts a new non root object into a single valued containment
	 * reference.
	 */
	@Test
	def void applyForwardTest() {
		// Create and resolve and apply
		val unresolvedChange = createUnresolvedChange()
		unresolvedChange.applyForwardAndResolve

		// State after
		assertIsStateAfter
	}

	/**
	 * Tests the {@link CreateAndReplaceNonRoot} EChange by applying it backward.
	 * Removes and deletes an existing non root object from a single valued containment
	 * reference.
	 */
	@Test
	def void applyBackwardTest() {
		// Create and resolve
		val resolvedChange = createUnresolvedChange().applyForwardAndResolve

		// Set state after
		assertIsStateAfter

		// Apply
		resolvedChange.applyBackward

		// State before
		assertIsStateBefore
	}

	/**
	 * Sets state before
	 */
	def private void prepareStateBefore() {
		affectedEObject.eSet(affectedFeature, null)
		assertIsStateBefore
	}

	/**
	 * Model is in state before the change.
	 */
	def private void assertIsStateBefore() {
		assertNull(affectedEObject.eGet(affectedFeature))
	}

	/**
	 * Model is in state after the change.
	 */
	def private void assertIsStateAfter() {
		assertThat(affectedEObject.eGet(affectedFeature), instanceOf(NonRoot))
	}

	/**
	 * Creates new unresolved change.
	 */
	def private List<EChange<Uuid>> createUnresolvedChange() {
		return compoundFactory.createCreateAndReplaceNonRootChange(affectedEObject, affectedFeature, aet.NonRoot).unresolve
	}
}
