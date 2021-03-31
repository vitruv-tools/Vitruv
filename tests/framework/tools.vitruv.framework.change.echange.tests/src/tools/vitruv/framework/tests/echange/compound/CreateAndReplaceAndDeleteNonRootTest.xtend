package tools.vitruv.framework.tests.echange.compound

import allElementTypes.AllElementTypesPackage
import allElementTypes.NonRoot
import allElementTypes.Root
import org.eclipse.emf.ecore.EReference
import tools.vitruv.framework.tests.echange.feature.reference.ReferenceEChangeTest

import static extension tools.vitruv.framework.tests.echange.util.EChangeAssertHelper.*
import tools.vitruv.framework.change.echange.EChange
import java.util.List
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.change.echange.eobject.DeleteEObject
import tools.vitruv.framework.tests.echange.EChangeTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertEquals
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.*
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.equalsDeeply

/**
 * Test class for the concrete {@link CreateAndReplaceAndDeleteNonRoot} EChange,
 * which creates a new non root EObject and replaces an existing non root object
 * in a single value containment reference. The existing one will be deleted.
 */
class CreateAndReplaceAndDeleteNonRootTest extends ReferenceEChangeTest {
	var NonRoot oldValue
	var EReference affectedFeature

	@BeforeEach
	def void prepareState() {
		oldValue = aet.NonRoot.withUuid
		affectedFeature = AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE
		prepareStateBefore
	}

	/**
	 * Resolves a {@link CreateAndReplaceAndDeleteNonRoot} EChange. The 
	 * model is in state before the change, so new value doesn't exist and 
	 * the old value is in the containment reference.
	 */
	@Test
	def void resolveBeforeTest() {
		// Create change
		val unresolvedChange = createUnresolvedChange(newValue)
		unresolvedChange.assertIsNotResolved()

		// Resolve
		oldValue.registerAsPreexisting
		val resolvedChange = unresolvedChange.resolveBefore
		resolvedChange.assertIsResolved(affectedEObject, oldValue, newValue)

		// Resolving applies all changes and reverts them, so the model should be unaffected.			
		assertIsStateBefore
	}

	/**
	 * Resolves a {@link CreateAndReplaceAndDeleteNonRoot} EChange. The 
	 * model is in state after the change, so old value doesn't exist and 
	 * the new value is in the containment reference.
	 */
	@Test
	def void resolveAfterTest() {
		// Create change
		val unresolvedChange = createUnresolvedChange(newValue)
		unresolvedChange.assertIsNotResolved

		// Set state after change	
		prepareStateAfter

		// Resolve
		newValue.registerAsPreexisting
		val resolvedChange = unresolvedChange.resolveAfter
		resolvedChange.assertIsResolved(affectedEObject, oldValue, newValue)

		// Resolving applies all changes and reverts them, so the model should be unaffected.			
		assertIsStateAfter
	}

	/**
	 * Tests whether resolving the {@link CreateAndReplaceAndDeleteNonRoot} EChange
	 * returns the same class.
	 */
	@Test
	def void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange(newValue)

		// Resolve
		newValue.registerAsPreexisting
		val resolvedChange = unresolvedChange.resolveAfter
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}

	/**
	 * Tests applying the {@link CreateAndReplaceAndDeleteNonRoot} EChange forward
	 * by creating a new non root and replacing an existing one.
	 */
	@Test
	def void applyForwardTest() {
		// Create and resolve 1
		oldValue.registerAsPreexisting
		val resolvedChange = createUnresolvedChange(newValue).resolveBefore

		// Apply change 1 forward
		resolvedChange.assertApplyForward

		// State after
		assertIsStateAfter

		// Create and resolve 2
		val resolvedChange2 = createUnresolvedChange(newValue2).resolveBefore

		// Apply change 2 forward
		resolvedChange2.assertApplyForward

		var NonRoot valueAfterChange2 = affectedEObject.eGet(affectedFeature) as NonRoot
		assertThat(valueAfterChange2, equalsDeeply(newValue2))
	}

	/**
	 * Tests applying the {@link CreateAndReplaceAndDeleteNonRoot} EChange backward
	 * by replacing a single value containment reference with its old value.
	 */
	@Test
	def void applyBackwardTest() {
		// Create change 
		oldValue.registerAsPreexisting
		val resolvedChange = createUnresolvedChange(newValue).resolveBefore

		// Set state after change 
		prepareStateAfter

		// Apply backward
		resolvedChange.applyBackward

		// State before
		assertIsStateBefore
	}

	def private void prepareStateBefore() {
		affectedEObject.eSet(affectedFeature, oldValue)
		assertIsStateBefore
	}

	/**
	 * Sets the model after the change
	 */
	def private void prepareStateAfter() {
		affectedEObject.eSet(affectedFeature, newValue)
		assertIsStateAfter
	}

	/**
	 * Model is in state before the changes.
	 */
	def private void assertIsStateBefore() {
		assertThat(oldValue, equalsDeeply(affectedEObject.eGet(affectedFeature) as NonRoot))
	}

	/**
	 * Model is in state after the changes.
	 */
	def private void assertIsStateAfter() {
		assertThat(newValue, equalsDeeply(affectedEObject.eGet(affectedFeature) as NonRoot))
	}

	/**
	 * Change is not resolved.
	 */
	def protected static void assertIsNotResolved(List<? extends EChange> changes) {
		EChangeTest.assertIsNotResolved(changes)
		assertEquals(3, changes.size)
		val createChange = assertType(changes.get(0), CreateEObject)
		val replaceChange = assertType(changes.get(1), ReplaceSingleValuedEReference)
		val deleteChange = assertType(changes.get(2), DeleteEObject)
		assertEquals(replaceChange.newValueID, createChange.affectedEObjectID)
		assertEquals(replaceChange.oldValueID, deleteChange.affectedEObjectID)
	}

	/**
	 * Change is resolved.
	 */
	def private static void assertIsResolved(List<EChange> changes, Root affectedRootObject, NonRoot oldNonRootObject,
		NonRoot newNonRootObject) {
		changes.assertIsResolved
		assertEquals(3, changes.size)
		val createChange = assertType(changes.get(0), CreateEObject)
		val ReplaceSingleValuedEReference<?,?> replaceChange = assertType(changes.get(1), ReplaceSingleValuedEReference)
		val deleteChange = assertType(changes.get(2), DeleteEObject)

		assertThat(createChange.affectedEObject, equalsDeeply(newNonRootObject))
		assertThat(replaceChange.oldValue, equalsDeeply(oldNonRootObject))
		assertThat(replaceChange.newValue, equalsDeeply(newNonRootObject))
		assertThat(deleteChange.affectedEObject, equalsDeeply(oldNonRootObject))
		assertThat(replaceChange.affectedEObject, equalsDeeply(affectedRootObject))
	}

	/**
	 * Creates new unresolved change.
	 */
	def private List<EChange> createUnresolvedChange(NonRoot newNonRootValue) {
		return compoundFactory.createCreateAndReplaceAndDeleteNonRootChange(affectedEObject, affectedFeature, oldValue,
			newNonRootValue)
	}
}
