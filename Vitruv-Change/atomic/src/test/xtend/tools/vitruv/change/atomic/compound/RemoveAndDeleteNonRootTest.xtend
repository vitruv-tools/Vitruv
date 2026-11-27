package tools.vitruv.change.atomic.compound

import allElementTypes.AllElementTypesPackage
import allElementTypes.NonRoot
import allElementTypes.Root
import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EReference
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tools.vitruv.change.atomic.EChange
import tools.vitruv.change.atomic.feature.reference.ReferenceEChangeTest
import tools.vitruv.change.atomic.uuid.Uuid

import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertTrue
import static tools.vitruv.change.testutils.matchers.ModelMatchers.equalsDeeply

/**
 * Test class for the concrete {@link RemoveAndDeleteNonRoot} EChange,
 * which removes a non root element reference from a containment reference 
 * list and deletes it.
 */
class RemoveAndDeleteNonRootTest extends ReferenceEChangeTest {
	var EReference affectedFeature
	var EList<NonRoot> referenceContent

	@BeforeEach
	def void prepareState() {
		affectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE
		referenceContent = affectedEObject.eGet(affectedFeature) as EList<NonRoot>
		prepareStateBefore
	}

	/**
	 * Tests whether resolving the {@link RemoveAndDeleteNonRoot} EChange
	 * returns the same class.
	 */
	@Test
	def void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange(affectedEObject, newValue, 0)

		// Resolve		
		val resolvedChange = unresolvedChange.applyForwardAndResolve
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}

	/**
	 * Tests the {@link RemoveAndDeleteNonRoot} EChange by applying it forward.
	 * Removes and deletes a non root element from a containment reference.
	 */
	@Test
	def void applyForwardTest() {
		// Create and resolve change 1
		val unresolvedChange = createUnresolvedChange(affectedEObject, newValue, 0)
		unresolvedChange.applyForwardAndResolve

		assertEquals(referenceContent.size, 1)
		assertFalse(referenceContent.contains(newValue))
		assertTrue(referenceContent.contains(newValue2))

		// Create and resolve change 2
		val unresolvedChange2 = createUnresolvedChange(affectedEObject, newValue2, 0)
		unresolvedChange2.applyForwardAndResolve

		// State after
		assertIsStateAfter
	}

	/**
	 * Tests the {@link RemoveAndDeleteNonRoot} EChange by applying it backward.
	 * Creates and reinserts the removed object.
	 */
	@Test
	def void applyBackwardTest() {
		// Create and resolve and apply change 1
		val resolvedChange = createUnresolvedChange(affectedEObject, newValue, 0).applyForwardAndResolve

		// Create and resolve and apply change 2
		val resolvedChange2 = createUnresolvedChange(affectedEObject, newValue2, 0).applyForwardAndResolve

		// State after change
		assertIsStateAfter

		// Apply backward 2
		resolvedChange2.applyBackward

		assertEquals(referenceContent.size, 1)
		assertTrue(referenceContent.contains(newValue2))

		// Apply backward 1
		resolvedChange.applyBackward

		// State before
		assertIsStateBefore
	}

	/**
	 * Sets the state of the model before the changes.
	 */
	def private void prepareStateBefore() {
		referenceContent.add(newValue)
		referenceContent.add(newValue2)
		assertIsStateBefore
	}

	/**
	 * Model is in state before the changes.
	 */
	def private void assertIsStateBefore() {
		assertEquals(referenceContent.size, 2)
		assertThat(newValue, equalsDeeply(referenceContent.get(0)))
		assertThat(newValue2, equalsDeeply(referenceContent.get(1)))
	}

	/**
	 * Model is in state after the changes.
	 */
	def private void assertIsStateAfter() {
		assertEquals(referenceContent.size, 0)
	}

	/**
	 * Creates new unresolved change.
	 */
	def private List<EChange<Uuid>> createUnresolvedChange(Root affectedRootObject, NonRoot newNonRoot, int index) {
		return compoundFactory.createRemoveAndDeleteNonRootChange(affectedRootObject, affectedFeature, newNonRoot,
			index).unresolve
	}

}
