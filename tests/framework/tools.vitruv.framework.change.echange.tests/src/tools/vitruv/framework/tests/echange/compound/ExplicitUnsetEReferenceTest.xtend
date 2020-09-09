package tools.vitruv.framework.tests.echange.compound

import allElementTypes.AllElementTypesFactory
import allElementTypes.AllElementTypesPackage
import allElementTypes.Identified
import allElementTypes.NonRoot
import allElementTypes.Root
import java.util.ArrayList
import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EReference
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.feature.reference.SubtractiveReferenceEChange
import tools.vitruv.framework.tests.echange.EChangeTest

import static extension tools.vitruv.framework.tests.echange.util.EChangeAssertHelper.*
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.eobject.DeleteEObject
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference

/**
 * Test class for the concrete {@link ExplicitUnsetEReference} EChange,
 * which unsets a single or multi valued reference.
 */
class ExplicitUnsetEReferenceTest extends EChangeTest {
	protected var Root affectedEObject = null
	protected var EReference affectedFeature = null
	protected var EList<NonRoot> referenceContent = null
	
	protected var NonRoot oldValue = null
	protected var NonRoot oldValue2 = null
	protected var NonRoot oldValue3 = null	
		
	@Before
	override void beforeTest() {
		super.beforeTest()
		affectedEObject = rootObject
		oldValue = AllElementTypesFactory.eINSTANCE.createNonRoot
		oldValue2 = AllElementTypesFactory.eINSTANCE.createNonRoot
		oldValue3 = AllElementTypesFactory.eINSTANCE.createNonRoot
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
 		val resolvedChange = unresolvedChange.resolveBefore(uuidGeneratorAndResolver)
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
		Assert.assertTrue(affectedEObject.eIsSet(affectedFeature))
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
				Assert.assertEquals(oldValue, affectedEObject.eGet(affectedFeature))
			} else {
				Assert.assertEquals(oldValue, referenceContent.get(0))
				Assert.assertEquals(oldValue2, referenceContent.get(1))
				Assert.assertEquals(oldValue3, referenceContent.get(2))
			}	
		} else {
			if (!affectedFeature.many) {
				oldValue.assertEqualsOrCopy(affectedEObject.eGet(affectedFeature) as Identified)
			} else {
				oldValue.assertEqualsOrCopy(referenceContent.get(0))
				oldValue2.assertEqualsOrCopy(referenceContent.get(1))
				oldValue3.assertEqualsOrCopy(referenceContent.get(2))
			}			
		}
	}
	
	/**
	 * The resource is in state before the change.
	 */
	def private void assertResourceIsStateBefore() {
		if (!affectedFeature.containment) {
			// Root object at index 0
			oldValue.assertEqualsOrCopy(resource.contents.get(1) as Identified)
			oldValue2.assertEqualsOrCopy(resource.contents.get(2) as Identified)
			oldValue3.assertEqualsOrCopy(resource.contents.get(3) as Identified)			
		} else {
			Assert.assertEquals(resource.contents.size, 1)	
		}
	}
	
	/**
	 * Model is in state after the change.
	 */
	def private void assertIsStateAfter() {
		Assert.assertFalse(affectedEObject.eIsSet(affectedFeature))
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
		EChangeTest.assertIsNotResolved(changes);
		if (!affectedFeature.containment) {
			if (!affectedFeature.many) {
				Assert.assertNotSame((changes.get(0) as SubtractiveReferenceEChange<Root, NonRoot>).affectedEObject, oldValue)
			} else {
				Assert.assertNotSame((changes.get(0) as SubtractiveReferenceEChange<Root, NonRoot>).affectedEObject, oldValue3)
				Assert.assertNotSame((changes.get(1) as SubtractiveReferenceEChange<Root, NonRoot>).affectedEObject, oldValue2)
				Assert.assertNotSame((changes.get(2) as SubtractiveReferenceEChange<Root, NonRoot>).affectedEObject, oldValue)
			}
		} else {
			if (!affectedFeature.many) {
				assertUnresolvedRemoveAndDelete(changes.get(0), true, changes.get(1));
			} else {
				assertUnresolvedRemoveAndDelete(changes.get(0), false, changes.get(1));
				assertUnresolvedRemoveAndDelete(changes.get(2), false, changes.get(3));
				assertUnresolvedRemoveAndDelete(changes.get(4), false, changes.get(5));
			}
		} 
	}
	
	private def assertUnresolvedRemoveAndDelete(EChange removeChange, boolean replace, 
		EChange deleteChange
	) {
		assertIsNotResolved(#[removeChange, deleteChange]);
		val typedRemoveChange = if (replace) {
			assertType(removeChange, ReplaceSingleValuedEReference);	
		} else {
			assertType(removeChange, RemoveEReference);
		}
				
		val typedDeleteChange = assertType(deleteChange, DeleteEObject);
		Assert.assertEquals(typedRemoveChange.oldValueID, typedDeleteChange.affectedEObjectID);
	}
	
	/**
	 * Change is resolved.
	 */
	def protected void localAssertIsResolved(List<EChange> changes) {
		EChangeTest.assertIsResolved(changes);
		if (!affectedFeature.containment) {
			if (!affectedFeature.many) {
				Assert.assertSame((changes.get(0) as SubtractiveReferenceEChange<Root, NonRoot>).oldValue, oldValue)
			} else {
				Assert.assertSame((changes.get(0) as SubtractiveReferenceEChange<Root, NonRoot>).oldValue, oldValue3)
				Assert.assertSame((changes.get(1) as SubtractiveReferenceEChange<Root, NonRoot>).oldValue, oldValue2)
				Assert.assertSame((changes.get(2) as SubtractiveReferenceEChange<Root, NonRoot>).oldValue, oldValue)				
			}
		} else {
			if (!affectedFeature.many) {
				assertResolvedRemoveAndDelete(changes.get(0), true, changes.get(1), oldValue);
			} else {
				assertResolvedRemoveAndDelete(changes.get(0), false, changes.get(1), oldValue3);
				assertResolvedRemoveAndDelete(changes.get(2), false, changes.get(3), oldValue2);
				assertResolvedRemoveAndDelete(changes.get(4), false, changes.get(5), oldValue);
			}
		}
	}
	
	private def assertResolvedRemoveAndDelete(EChange removeChange, boolean replace,
		EChange deleteChange, EObject oldValue
	) {
		assertIsResolved(#[removeChange, deleteChange]);
		val typedRemoveChange = if (replace) {
			assertType(removeChange, ReplaceSingleValuedEReference);	
		} else {
			assertType(removeChange, RemoveEReference);
		}
		val typedDeleteChange = assertType(deleteChange, DeleteEObject);
		oldValue.assertEqualsOrCopy(typedRemoveChange.oldValue)
		oldValue.assertEqualsOrCopy(typedDeleteChange.affectedEObject)
	}
	
	/**
	 * Creates new unresolved change.
	 */
	def private List<? extends EChange> createUnresolvedChange() {
		var List<EChange> changes = new ArrayList<EChange>
		if (!affectedFeature.containment) {
			if (!affectedFeature.many) {
				val change = atomicFactory.createReplaceSingleReferenceChange(affectedEObject, affectedFeature, oldValue, null)
				change.setIsUnset = true;
				changes.add(change)
			} else {
				changes.add(atomicFactory.createRemoveReferenceChange(affectedEObject, affectedFeature, oldValue3, 2))
				changes.add(atomicFactory.createRemoveReferenceChange(affectedEObject, affectedFeature, oldValue2, 1))
				changes.add(atomicFactory.createRemoveReferenceChange(affectedEObject, affectedFeature, oldValue, 0))
				changes.add(atomicFactory.createUnsetFeatureChange(affectedEObject, affectedFeature))
			}
		} else {
			if (!affectedFeature.many) {
				val change = compoundFactory.createReplaceAndDeleteNonRootChange(affectedEObject, affectedFeature, oldValue)
				(change.get(0) as ReplaceSingleValuedEReference<?,?>).isUnset = true;
				changes.addAll(change)
			} else {
				changes.addAll(compoundFactory.createRemoveAndDeleteNonRootChange(affectedEObject, affectedFeature, oldValue3, 2))
				changes.addAll(compoundFactory.createRemoveAndDeleteNonRootChange(affectedEObject, affectedFeature, oldValue2, 1))
				changes.addAll(compoundFactory.createRemoveAndDeleteNonRootChange(affectedEObject, affectedFeature, oldValue, 0))
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
		val resolvedChange = unresolvedChange.resolveBefore(uuidGeneratorAndResolver)
		resolvedChange.localAssertIsResolved()
		
		// Model should be unaffected
		assertIsStateBefore
		
		// Resolve 2
		var resolvedAndAppliedChange = unresolvedChange.resolveBefore(uuidGeneratorAndResolver)
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
		
		// Resolve 1
		var resolvedChange = unresolvedChange.resolveAfter(uuidGeneratorAndResolver)
		resolvedChange.localAssertIsResolved
		
		// Model should be unaffected.
		assertIsStateAfter	
		
		// Resolve 2
		var resolvedAndAppliedChange = unresolvedChange.resolveAfter(uuidGeneratorAndResolver)
		resolvedAndAppliedChange.applyBackward
		resolvedAndAppliedChange.localAssertIsResolved
		
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
		val resolvedChange = createUnresolvedChange().resolveBefore(uuidGeneratorAndResolver)
			
		// Apply forward
		Assert.assertTrue(resolvedChange.applyForward)
		
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
		val resolvedChange = createUnresolvedChange().resolveBefore(uuidGeneratorAndResolver)
			
		// Set state after
		prepareStateAfter
		assertIsStateAfter
		
		// Apply forward
		Assert.assertTrue(resolvedChange.applyBackward)
		
		// State before
		assertIsStateBefore			
	}
}