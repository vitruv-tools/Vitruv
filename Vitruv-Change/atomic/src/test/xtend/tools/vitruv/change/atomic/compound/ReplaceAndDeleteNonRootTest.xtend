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

import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.jupiter.api.Assertions.assertNull
import static tools.vitruv.change.testutils.matchers.ModelMatchers.equalsDeeply
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.*

/**
 * Test class for the concrete {@link ReplaceAndDeleteNonRoot} EChange,
 * which removes a non root EObject from a single valued containment reference
 * and delets it.
 */
class ReplaceAndDeleteNonRootTest extends EChangeTest {
	var Root affectedEObject
	var EReference affectedFeature
	var NonRoot oldNonRootObject

	@BeforeEach
	def void beforeTest() {
		affectedEObject = rootObject
		affectedFeature = AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE
		oldNonRootObject = aet.NonRoot.withUuid
		prepareStateBefore
	}

	/**
	 * Tests whether resolving the {@link ReplaceAndDeleteNonRoot} EChange
	 * returns the same class.
	 */
	@Test
	def void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange(oldNonRootObject)

		// Resolve		
		val resolvedChange = unresolvedChange.applyForwardAndResolve
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}

	/**
	 * Tests the {@link ReplaceAndDeleteNonRoot} EChange by applying it forward.
	 * Removes a non root object from a single valued containment reference and deletes it.
	 */
	@Test
	def void applyForwardTest() {
		// Create and resolve and apply
		val unresolvedChange = createUnresolvedChange(oldNonRootObject)
		unresolvedChange.applyForwardAndResolve

		// State after
		assertIsStateAfter
	}

	/**
	 * Tests the {@link ReplaceAndDeleteNonRoot} EChange by applying it backward.
	 * Creates and inserts an old non root object into a single valued containment
	 * reference.
	 */
	@Test
	def void applyBackwardTest() {
		// Create and resolve
		val resolvedChange = createUnresolvedChange(oldNonRootObject).applyForwardAndResolve

		// Set state after
		prepareStateAfter

		// Apply
		resolvedChange.applyBackward

		// State before
		assertIsStateBefore
	}

	/**
	 * Sets the state before the change.
	 */
	def private void prepareStateBefore() {
		affectedEObject.eSet(affectedFeature, oldNonRootObject)
		assertIsStateBefore
	}

	/**
	 * Sets the state after the change.
	 */
	def private void prepareStateAfter() {
		affectedEObject.eSet(affectedFeature, null)
		assertIsStateAfter
	}

	/**
	 * Model is in state before the change.
	 */
	def private void assertIsStateBefore() {
		assertThat(oldNonRootObject, equalsDeeply(affectedEObject.eGet(affectedFeature) as NonRoot))
	}

	/**
	 * Model is in state after the change.
	 */
	def private void assertIsStateAfter() {
		assertNull(affectedEObject.eGet(affectedFeature))
	}

	/**
	 * Creates new unresolved change.
	 */
	def private List<EChange<Uuid>> createUnresolvedChange(NonRoot oldNonRoot) {
		return compoundFactory.createReplaceAndDeleteNonRootChange(affectedEObject, affectedFeature, oldNonRoot).unresolve
	}
}
