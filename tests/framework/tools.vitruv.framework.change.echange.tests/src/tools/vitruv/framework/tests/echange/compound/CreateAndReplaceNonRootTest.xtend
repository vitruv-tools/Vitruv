package tools.vitruv.framework.tests.echange.compound

import allElementTypes.AllElementTypesPackage
import allElementTypes.NonRoot
import allElementTypes.Root
import org.eclipse.emf.ecore.EReference
import tools.vitruv.framework.tests.echange.EChangeTest

import static extension tools.vitruv.framework.tests.echange.util.EChangeAssertHelper.*
import tools.vitruv.framework.change.echange.EChange
import java.util.List
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertEquals
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.*
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.equalsDeeply

/**
 * Test class for the concrete {@link CreateAndReplaceNonRoot} EChange,
 * which creates a new non root EObject and replaces a null value
 * in a single valued containment reference.
 */
class CreateAndReplaceNonRootTest extends EChangeTest {
	var Root affectedEObject
	var EReference affectedFeature
	var NonRoot newNonRootObject

	@BeforeEach
	def void beforeTest() {
		affectedEObject = rootObject.withUuid.registerAsPreexisting
		affectedFeature = AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE
		newNonRootObject = aet.NonRoot.withUuid
		prepareStateBefore
	}

	/**
	 * Resolves a {@link CreateAndReplaceNonRoot} EChange. The model is in state
	 * before the change, so the new non root element will be created and inserted
	 * into a single valued containment reference.
	 */
	@Test
	def void resolveBeforeTest() {
		// Create change
		val unresolvedChange = createUnresolvedChange(newNonRootObject)
		unresolvedChange.assertIsNotResolved

		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore
		resolvedChange.assertIsResolved(affectedEObject, newNonRootObject)

		// Resolving applies all changes and reverts them, so the model should be unaffected.
		assertIsStateBefore
	}

	/**
	 * Tests whether resolving the {@link CreateAndReplaceNonRoot} EChange
	 * returns the same class.
	 */
	@Test
	def void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange(newNonRootObject)

		// Resolve		
		val resolvedChange = unresolvedChange.resolveBefore
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
		val resolvedChange = createUnresolvedChange(newNonRootObject).resolveBefore
		resolvedChange.assertApplyForward

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
		val resolvedChange = createUnresolvedChange(newNonRootObject).resolveBefore

		// Set state after
		prepareStateAfter

		// Apply
		resolvedChange.assertApplyBackward

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
	 * Sets state after
	 */
	def private void prepareStateAfter() {
		affectedEObject.eSet(affectedFeature, newNonRootObject)
		assertIsStateAfter
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
		assertThat(newNonRootObject, equalsDeeply(affectedEObject.eGet(affectedFeature) as NonRoot))
	}

	/**
	 * Change is not resolved.
	 */
	def protected static void assertIsNotResolved(List<? extends EChange> changes) {
		EChangeTest.assertIsNotResolved(changes)
		assertEquals(2, changes.size)
		val createChange = assertType(changes.get(0), CreateEObject)
		val replaceChange = assertType(changes.get(1), ReplaceSingleValuedEReference)
		assertEquals(replaceChange.newValueID, createChange.affectedEObjectID)
	}

	/**
	 * Change is resolved.
	 */
	def private static void assertIsResolved(List<EChange> changes, Root affectedEObject, NonRoot newValue) {
		changes.assertIsResolved
		assertEquals(2, changes.size)
		val createChange = assertType(changes.get(0), CreateEObject)
		val ReplaceSingleValuedEReference<?,?> replaceChange = assertType(changes.get(1), ReplaceSingleValuedEReference)
		assertThat(replaceChange.newValue, equalsDeeply(newValue))
		assertThat(createChange.affectedEObject, equalsDeeply(newValue))
		assertThat(replaceChange.affectedEObject, equalsDeeply(affectedEObject))
	}

	/**
	 * Creates new unresolved change.
	 */
	def private List<EChange> createUnresolvedChange(NonRoot newNonRoot) {
		return compoundFactory.createCreateAndReplaceNonRootChange(affectedEObject, affectedFeature, newNonRoot)
	}
}
