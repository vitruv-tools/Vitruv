package tools.vitruv.framework.tests.echange.compound

import allElementTypes.AllElementTypesPackage
import allElementTypes.Identified
import allElementTypes.NonRoot
import allElementTypes.Root
import java.util.ArrayList
import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EReference
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.feature.reference.SubtractiveReferenceEChange
import tools.vitruv.framework.tests.echange.EChangeTest

import static extension tools.vitruv.framework.tests.echange.util.EChangeAssertHelper.*
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.eobject.DeleteEObject
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertSame
import static org.junit.jupiter.api.Assertions.assertNotSame
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.*
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.equalsDeeply

/**
 * Test class for the concrete {@link ExplicitUnsetEReference} EChange,
 * which unsets a single or multi valued reference.
 */
class ExplicitUnsetEReferenceTest extends EChangeTest {
	var Root affectedEObject
	var EReference affectedFeature
	var EList<NonRoot> referenceContent

	var NonRoot oldValue
	var NonRoot oldValue2
	var NonRoot oldValue3

	@BeforeEach
	def void beforeTest() {
		affectedEObject = rootObject.withUuid.registerAsPreexisting
		oldValue = aet.NonRoot.withUuid
		oldValue2 = aet.NonRoot.withUuid
		oldValue3 = aet.NonRoot.withUuid
	}
	
	private def registerOldValuesAsPreexisting() {
		oldValue.registerAsPreexisting
		oldValue2.registerAsPreexisting
		oldValue3.registerAsPreexisting
	}

	/**
	 * Resolves a {@link ExplicitUnsetEReference} EChange. The feature is a single 
	 * valued non containment reference and the model is in state before the change.
	 */
	@Test
	def void resolveBeforeSingleValuedNonContainmentReferenceTest() {
		// Set state before
		isSingleValuedNonContainmentTest

		// Test
		resolveBeforeTest
	}

	/**
	 * Resolves a {@link ExplicitUnsetEReference} EChange. The feature is a single 
	 * valued containment reference and the model is in state before the change.
	 */
	@Test
	def void resolveBeforeSingleValuedContainmentReferenceTest() {
		// Set state before
		isSingleValuedContainmentTest

		// Test
		registerOldValuesAsPreexisting()
		resolveBeforeTest
	}

	/**
	 * Resolves a {@link ExplicitUnsetEReference} EChange. The feature is a multi 
	 * valued non containment reference and the model is in state before the change.
	 */
	@Test
	def void resolveBeforeMultiValuedNonContainmentReferenceTest() {
		// Set state before
		isMultiValuedNonContainmentTest

		// Test
		resolveBeforeTest
	}

	/**
	 * Resolves a {@link ExplicitUnsetEReference} EChange. The feature is a multi 
	 * valued containment reference and the model is in state before the change.
	 */
	@Test
	def void resolveBeforeMultiValuedContainmentReferenceTest() {
		// Set state before
		isMultiValuedContainmentTest

		// Test
		registerOldValuesAsPreexisting()
		resolveBeforeTest
	}

	/**
	 * Resolves a {@link ExplicitUnsetEReference} EChange. The feature is a single 
	 * valued non containment reference and the model is in state after the change.
	 */
	@Test
	def void resolveAfterSingleValuedNonContainmentReferenceTest() {
		// Set state before
		isSingleValuedNonContainmentTest

		// Test
		resolveAfterTest
	}

	/**
	 * Resolves a {@link ExplicitUnsetEReference} EChange. The feature is a single 
	 * valued containment reference and the model is in state after the change.
	 */
	@Test
	def void resolveAfterSingleValuedContainmentReferenceTest() {
		// Set state before
		isSingleValuedContainmentTest

		// Test
		resolveAfterTest
	}

	/**
	 * Resolves a {@link ExplicitUnsetEReference} EChange. The feature is a multi 
	 * valued non containment reference and the model is in state after the change.
	 */
	@Test
	def void resolveAfterMultiValuedNonContainmentReferenceTest() {
		// Set state before
		isMultiValuedNonContainmentTest

		// Test
		resolveAfterTest
	}

	/**
	 * Resolves a {@link ExplicitUnsetEReference} EChange. The feature is a multi 
	 * valued containment reference and the model is in state after the change.
	 */
	@Test
	def void resolveAfterMultiValuedContainmentReferenceTest() {
		// Set state before
		isMultiValuedContainmentTest

		// Test
		resolveAfterTest
	}

	/**
	 * Tests whether the {@link ExplicitUnsetEReference} EChange resolves to the correct type.
	 */
	@Test
	def void resolveToCorrectType() {
		// Set state before
		isSingleValuedNonContainmentTest

		// Create change
		val unresolvedChange = createUnresolvedChange()

		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}

	/**
	 * Tests a {@link ExplicitUnsetEReference} EChange by applying it forward.
	 * Unsets a single valued non containment reference.
	 */
	@Test
	def void applyForwardSingleValuedNonContainmentReferenceTest() {
		// Set state before
		isSingleValuedNonContainmentTest

		// Test
		applyForwardTest
	}

	/**
	 * Tests a {@link ExplicitUnsetEReference} EChange by applying it forward.
	 * Unsets a single valued non containment reference.
	 */
	@Test
	def void applyForwardSingleValuedContainmentReferenceTest() {
		// Set state before
		isSingleValuedContainmentTest

		// Test
		registerOldValuesAsPreexisting()
		applyForwardTest
	}

	/**
	 * Tests a {@link ExplicitUnsetEReference} EChange by applying it forward.
	 * Unsets a multi valued non containment reference.
	 */
	@Test
	def void applyForwardMultiValuedNonContainmentReferenceTest() {
		// Set state before
		isMultiValuedContainmentTest

		// Test
		registerOldValuesAsPreexisting()
		applyForwardTest
	}

	/**
	 * Tests a {@link ExplicitUnsetEReference} EChange by applying it forward.
	 * Unsets a multi valued containment reference.
	 */
	@Test
	def void applyForwardMultiValuedNContainmentReferenceTest() {
		// Set state before
		isMultiValuedContainmentTest

		// Test
		registerOldValuesAsPreexisting()
		applyForwardTest
	}

	/**
	 * Tests a {@link ExplicitUnsetEReference} EChange by applying it backward.
	 * Unsets a single valued non containment reference.
	 */
	@Test
	def void applyBackwardSingleValuedNonContainmentReferenceTest() {
		// Set state before
		isSingleValuedNonContainmentTest

		// Test
		applyBackwardTest
	}

	/**
	 * Tests a {@link ExplicitUnsetEReference} EChange by applying it backward.
	 * Unsets a single valued non containment reference.
	 */
	@Test
	def void applyBackwardSingleValuedContainmentReferenceTest() {
		// Set state before
		isSingleValuedContainmentTest

		// Test
		registerOldValuesAsPreexisting()
		applyBackwardTest
	}

	/**
	 * Tests a {@link ExplicitUnsetEReference} EChange by applying it backward.
	 * Unsets a multi valued non containment reference.
	 */
	@Test
	def void applyBackwardMultiValuedNonContainmentReferenceTest() {
		// Set state before
		isMultiValuedNonContainmentTest

		// Test
		applyBackwardTest
	}

	/**
	 * Tests a {@link ExplicitUnsetEReference} EChange by applying it backward.
	 * Unsets a multi valued containment reference.
	 */
	@Test
	def void applyBackwardMultiValuedNContainmentReferenceTest() {
		// Set state before
		isMultiValuedContainmentTest

		// Test
		registerOldValuesAsPreexisting()
		applyBackwardTest
	}

	/**
	 * Starts a test with a single valued non containment reference
	 * and sets the state before.
	 */
	def private void isSingleValuedNonContainmentTest() {
		affectedFeature = AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_UNSETTABLE_NON_CONTAINMENT_EREFERENCE
		prepareStateBefore
	}

	/**
	 * Starts a test with a single valued containment reference
	 * and sets the state before.
	 */
	def private void isSingleValuedContainmentTest() {
		affectedFeature = AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE
		prepareStateBefore
	}

	/**
	 * Starts a test with a multi valued non containment reference
	 * and sets the state before.
	 */
	def private void isMultiValuedNonContainmentTest() {
		affectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_UNSETTABLE_NON_CONTAINMENT_EREFERENCE
		referenceContent = affectedEObject.eGet(affectedFeature) as EList<NonRoot>
		prepareStateBefore
	}

	/**
	 * Starts a test with a multi valued containment reference
	 * and sets the state before.
	 */
	def private void isMultiValuedContainmentTest() {
		affectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE
		referenceContent = affectedEObject.eGet(affectedFeature) as EList<NonRoot>
		prepareStateBefore
	}

	/**
	 * Sets the state before the change, depending on single or multi valued
	 * reference, or containment / non containment.
	 */
	def private void prepareStateBefore() {
		if (!affectedFeature.containment) {
			prepareResource
		}
		prepareReference
	}

	/**
	 * Prepares the resource and puts every value
	 * of the feature in the resource.
	 */
	def private void prepareResource() {
		resource.contents.add(oldValue)
		resource.contents.add(oldValue2)
		resource.contents.add(oldValue3)
		registerOldValuesAsPreexisting()
	}

	/**
	 * Prepares the reference and puts the
	 * affected values into the reference.
	 */
	def private void prepareReference() {
		if (!affectedFeature.many) {
			affectedEObject.eSet(affectedFeature, oldValue)
		} else {
			referenceContent.add(oldValue)
			referenceContent.add(oldValue2)
			referenceContent.add(oldValue3)
		}
	}

	/**
	 * Sets the state after change.
	 */
	def private void prepareStateAfter() {
		affectedEObject.eUnset(affectedFeature)
	}

	/**
	 * The model is in state before the change.
	 */
	def private void assertIsStateBefore() {
		assertTrue(affectedEObject.eIsSet(affectedFeature))
		if (!affectedFeature.isContainment) {
			assertResourceIsStateBefore
		}
		assertReferenceIsStateBefore
	}

	/**
	 * The affected reference is in state before the change.
	 */
	def private void assertReferenceIsStateBefore() {
		if (!affectedFeature.containment) {
			if (!affectedFeature.many) {
				assertEquals(oldValue, affectedEObject.eGet(affectedFeature))
			} else {
				assertEquals(oldValue, referenceContent.get(0))
				assertEquals(oldValue2, referenceContent.get(1))
				assertEquals(oldValue3, referenceContent.get(2))
			}
		} else {
			if (!affectedFeature.many) {
				assertThat(oldValue, equalsDeeply(affectedEObject.eGet(affectedFeature) as Identified))
			} else {
				assertThat(oldValue, equalsDeeply(referenceContent.get(0)))
				assertThat(oldValue2, equalsDeeply(referenceContent.get(1)))
				assertThat(oldValue3, equalsDeeply(referenceContent.get(2)))
			}
		}
	}

	/**
	 * The resource is in state before the change.
	 */
	def private void assertResourceIsStateBefore() {
		if (!affectedFeature.containment) {
			// Root object at index 0
			assertThat(oldValue, equalsDeeply(resource.contents.get(1) as Identified))
			assertThat(oldValue2, equalsDeeply(resource.contents.get(2) as Identified))
			assertThat(oldValue3, equalsDeeply(resource.contents.get(3) as Identified))
		} else {
			assertEquals(resource.contents.size, 1)
		}
	}

	/**
	 * Model is in state after the change.
	 */
	def private void assertIsStateAfter() {
		assertFalse(affectedEObject.eIsSet(affectedFeature))
		assertResourceIsStateAfter
	}

	/**
	 * Resource is in state after the change.
	 */
	def private void assertResourceIsStateAfter() {
		assertResourceIsStateBefore
	}

	/**
	 * Change is not resolved.
	 */
	def protected void localAssertIsNotResolved(List<? extends EChange> changes) {
		EChangeTest.assertIsNotResolved(changes)
		if (!affectedFeature.containment) {
			if (!affectedFeature.many) {
				assertNotSame((changes.get(0) as SubtractiveReferenceEChange<Root, NonRoot>).affectedEObject, oldValue)
			} else {
				assertNotSame((changes.get(0) as SubtractiveReferenceEChange<Root, NonRoot>).affectedEObject, oldValue3)
				assertNotSame((changes.get(1) as SubtractiveReferenceEChange<Root, NonRoot>).affectedEObject, oldValue2)
				assertNotSame((changes.get(2) as SubtractiveReferenceEChange<Root, NonRoot>).affectedEObject, oldValue)
			}
		} else {
			if (!affectedFeature.many) {
				assertUnresolvedRemoveAndDelete(changes.get(0), true, changes.get(1))
			} else {
				assertUnresolvedRemoveAndDelete(changes.get(0), false, changes.get(1))
				assertUnresolvedRemoveAndDelete(changes.get(2), false, changes.get(3))
				assertUnresolvedRemoveAndDelete(changes.get(4), false, changes.get(5))
			}
		}
	}

	private def assertUnresolvedRemoveAndDelete(
		EChange removeChange,
		boolean replace,
		EChange deleteChange
	) {
		assertIsNotResolved(#[removeChange, deleteChange])
		val typedRemoveChange = if (replace) {
				assertType(removeChange, ReplaceSingleValuedEReference)
			} else {
				assertType(removeChange, RemoveEReference)
			}

		val typedDeleteChange = assertType(deleteChange, DeleteEObject)
		assertEquals(typedRemoveChange.oldValueID, typedDeleteChange.affectedEObjectID)
	}

	/**
	 * Change is resolved.
	 */
	def protected void localAssertIsResolved(List<EChange> changes) {
		EChangeTest.assertIsResolved(changes)
		if (!affectedFeature.containment) {
			if (!affectedFeature.many) {
				assertSame((changes.get(0) as SubtractiveReferenceEChange<Root, NonRoot>).oldValue, oldValue)
			} else {
				assertSame((changes.get(0) as SubtractiveReferenceEChange<Root, NonRoot>).oldValue, oldValue3)
				assertSame((changes.get(1) as SubtractiveReferenceEChange<Root, NonRoot>).oldValue, oldValue2)
				assertSame((changes.get(2) as SubtractiveReferenceEChange<Root, NonRoot>).oldValue, oldValue)
			}
		} else {
			if (!affectedFeature.many) {
				assertResolvedRemoveAndDelete(changes.get(0), true, changes.get(1), oldValue)
			} else {
				assertResolvedRemoveAndDelete(changes.get(0), false, changes.get(1), oldValue3)
				assertResolvedRemoveAndDelete(changes.get(2), false, changes.get(3), oldValue2)
				assertResolvedRemoveAndDelete(changes.get(4), false, changes.get(5), oldValue)
			}
		}
	}

	private def assertResolvedRemoveAndDelete(
		EChange removeChange,
		boolean replace,
		EChange deleteChange,
		EObject oldValue
	) {
		assertIsResolved(#[removeChange, deleteChange])
		val typedRemoveChange = if (replace) {
				assertType(removeChange, ReplaceSingleValuedEReference)
			} else {
				assertType(removeChange, RemoveEReference)
			}
		val typedDeleteChange = assertType(deleteChange, DeleteEObject)
		assertThat(oldValue, equalsDeeply(typedRemoveChange.oldValue))
		assertThat(oldValue, equalsDeeply(typedDeleteChange.affectedEObject))
	}

	/**
	 * Creates new unresolved change.
	 */
	def private List<? extends EChange> createUnresolvedChange() {
		var List<EChange> changes = new ArrayList<EChange>
		if (!affectedFeature.containment) {
			if (!affectedFeature.many) {
				val change = atomicFactory.createReplaceSingleReferenceChange(affectedEObject, affectedFeature,
					oldValue, null)
				change.setIsUnset = true
				changes.add(change)
			} else {
				changes.add(atomicFactory.createRemoveReferenceChange(affectedEObject, affectedFeature, oldValue3, 2))
				changes.add(atomicFactory.createRemoveReferenceChange(affectedEObject, affectedFeature, oldValue2, 1))
				changes.add(atomicFactory.createRemoveReferenceChange(affectedEObject, affectedFeature, oldValue, 0))
				changes.add(atomicFactory.createUnsetFeatureChange(affectedEObject, affectedFeature))
			}
		} else {
			if (!affectedFeature.many) {
				val change = compoundFactory.createReplaceAndDeleteNonRootChange(affectedEObject, affectedFeature,
					oldValue)
				(change.get(0) as ReplaceSingleValuedEReference<?, ?>).isUnset = true
				changes.addAll(change)
			} else {
				changes.addAll(
					compoundFactory.createRemoveAndDeleteNonRootChange(affectedEObject, affectedFeature, oldValue3, 2))
				changes.addAll(
					compoundFactory.createRemoveAndDeleteNonRootChange(affectedEObject, affectedFeature, oldValue2, 1))
				changes.addAll(
					compoundFactory.createRemoveAndDeleteNonRootChange(affectedEObject, affectedFeature, oldValue, 0))
				changes.add(atomicFactory.createUnsetFeatureChange(affectedEObject, affectedFeature))
			}
		}

		return changes
	}

	/**
	 * Starts a test with resolving a change before the change is applied.
	 */
	def private void resolveBeforeTest() {
		// State before
		assertIsStateBefore

		// Create change
		val unresolvedChange = createUnresolvedChange()
		unresolvedChange.localAssertIsNotResolved()

		// Resolve 1
		val resolvedChange = unresolvedChange.resolveBefore
		resolvedChange.localAssertIsResolved()

		// Model should be unaffected
		assertIsStateBefore

		// Resolve 2
		var resolvedAndAppliedChange = unresolvedChange.resolveBefore
		resolvedAndAppliedChange.applyForward
		resolvedAndAppliedChange.localAssertIsResolved()

		// State after
		assertIsStateAfter
	}

	/**
	 * Starts a test with resolving the change after the change is applied.
	 */
	def private void resolveAfterTest() {
	
		// State before
		assertIsStateBefore

		// Create change
		val unresolvedChange = createUnresolvedChange()
		unresolvedChange.localAssertIsNotResolved()

		// Set state after
		prepareStateAfter
		assertIsStateAfter

		// Resolve
		var resolvedChange = unresolvedChange.resolveAfter
		resolvedChange.localAssertIsResolved

		// Model should be unaffected.
		assertIsStateAfter

		// Apply Backward
		resolvedChange.applyBackward

		// State before
		assertIsStateBefore
	}

	/**
	 * Starts a test with applying the change forward.
	 */
	def private void applyForwardTest() {
		// State before
		assertIsStateBefore

		// Create and resolve change
		val resolvedChange = createUnresolvedChange().resolveBefore

		// Apply forward
		resolvedChange.applyForward

		// State after
		assertIsStateAfter
	}

	/**
	 * Starts a test with applying the change backward.
	 */
	def private void applyBackwardTest() {
		// State before
		assertIsStateBefore

		// Create and resolve change
		val resolvedChange = createUnresolvedChange().resolveBefore

		// Set state after
		prepareStateAfter
		assertIsStateAfter

		// Apply forward
		resolvedChange.applyBackward

		// State before
		assertIsStateBefore
	}
}
