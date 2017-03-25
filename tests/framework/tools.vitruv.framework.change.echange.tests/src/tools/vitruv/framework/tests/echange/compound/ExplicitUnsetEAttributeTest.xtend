package tools.vitruv.framework.tests.echange.compound

import allElementTypes.AllElementTypesPackage
import allElementTypes.Root
import java.util.ArrayList
import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EAttribute
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEAttribute
import tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange
import tools.vitruv.framework.tests.echange.EChangeTest

/**
 * Test class for the concrete {@link ExplicitUnsetEAttribute} EChange,
 * which unsets a single or multi valued attribute.
 */
class ExplicitUnsetEAttributeTest extends EChangeTest {
	protected var Root affectedEObject = null
	protected var EAttribute affectedFeature = null
	protected var EList<Integer> attributeContent = null
	
	protected static val Integer OLD_VALUE = 111
	protected static val Integer OLD_VALUE_2 = 222
	protected static val Integer OLD_VALUE_3 = 333
	
	
	@Before
	override public void beforeTest() {
		super.beforeTest()
		affectedEObject = rootObject	
	}

	/**
	 * Resolves a {@link ExplicitUnsetEAttribute} EChange. The feature is a single
	 * valued attribute and the model is in state before the change.
	 */
	@Test
	def public void resolveBeforeUnsetSingleValuedAttributeTest() {
		// Set state before
		isSingleValuedAttributeTest
		
		// Test
		resolveBeforeTest
	}
	
	/**
	 * Resolves a {@link ExplicitUnsetEAttribute} EChange. The feature is a single 
	 * valued attribute and the model is in state after the change.
	 */
	@Test
	def public void resolveAfterUnsetSingleValuedAttributeTest() {
		// Set state before
		isSingleValuedAttributeTest	
		
		// Test
		resolveAfterTest
	}
	
	/**
	 * Resolves a {@link ExplicitUnsetEAttribute} EChange. The feature is a multi
	 * valued attribute and the model is in state before the change.
	 */
	@Test
	def public void resolveBeforeUnsetMultiValuedAttributeTest() {
		// Set state before
		isMultiValuedAttributeTest
		
		// Test
		resolveBeforeTest
	}	
	
	/**
	 * Resolves a {@link ExplicitUnsetEAttribute} EChange. The feature is a single 
	 * valued attribute and the model is in state after the change.
	 */
	@Test
	def public void resolveAfterUnsetMultiValuedAttributeTest() {
		// Set state before
		isMultiValuedAttributeTest	
		
		// Test
		resolveAfterTest
	}
		
	/**
	 * Tests whether resolving the {@link ExplicitUnsetEAttribute} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		// Set state before
		isSingleValuedAttributeTest
		
		// Create change
		val unresolvedChange = createUnresolvedChange()

		// Resolve		
 		val resolvedChange = unresolvedChange.resolveBefore(resourceSet)
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)	
	}
		
	/**
	 * Tests a {@link ExplicitUnsetEAttribute} EChange by applying it forward.
	 * Unsets a single valued unsettable attribute.
	 */
	@Test
	def public void unsetSingleValuedAttributeApplyForwardTest() {
		// Set state before
		isSingleValuedAttributeTest

		// Test
		applyForwardTest
	}
	
	/**
	 * Tests a {@link ExplicitUnsetEAttribute} EChange by applying it forward. 
	 * Unsets a multi valued unsettable attribute.
	 */
	@Test
	def public void unsetMulitValuedAttributeApplyForwardTest() {
		// Set state before
		isMultiValuedAttributeTest

		// Test
		applyForwardTest
	}
	
	/**
	 * Tests a {@link ExplicitUnsetEAttribute} EChange by applying it backward.
	 * Unsets a single valued unsettable attribute.
	 */
	@Test
	def public void unsetSingleValuedAttributeApplyBackwardTest() {
		// Set state before
		isSingleValuedAttributeTest
		
		// Test
		applyBackwardTest
	}
	
	/**
	 * Tests a {@link ExplicitUnsetEAttribute} EChange by applying it backward. 
	 * Unsets a multi valued unsettable attribute.
	 */
	@Test
	def public void unsetMulitValuedAttributeApplyBackwardTest() {
		// Set state before
		isMultiValuedAttributeTest
		
		// Test
		applyBackwardTest
	}
	
	
	/**
	 * Starts a test with a single valued attribute and sets the state before.
	 */
	def private void isSingleValuedAttributeTest() {
		affectedFeature = AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_UNSETTABLE_EATTRIBUTE
		prepareStateBefore
	}
	
	/**
	 * Starts a test with a multi valued attribute and sets the state before. 
	 */
	def private void isMultiValuedAttributeTest() {
		affectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE	
		attributeContent = affectedEObject.eGet(affectedFeature) as EList<Integer>
		prepareStateBefore
	}
	
	/**
	 * Sets the state before, depending on single or multi valued attribute test.
	 */
	def private void prepareStateBefore() {
		if (!affectedFeature.many) {
			affectedEObject.eSet(affectedFeature, OLD_VALUE)					
		} else {
			attributeContent.add(OLD_VALUE)
			attributeContent.add(OLD_VALUE_2)
			attributeContent.add(OLD_VALUE_3)			
		}
	}
	
	/**
	 * Sets the state after the change.
	 */
	def private void prepareStateAfter() {
		affectedEObject.eUnset(affectedFeature)		
	}
	
	/**
	 * Model is in state before the single or multi valued unset change.
	 */
	def private void assertIsStateBefore() {
		Assert.assertTrue(affectedEObject.eIsSet(affectedFeature))
		if (!affectedFeature.many) {
			Assert.assertEquals(affectedEObject.eGet(affectedFeature), OLD_VALUE)
		} else {
			Assert.assertEquals(attributeContent.get(0), OLD_VALUE)
			Assert.assertEquals(attributeContent.get(1), OLD_VALUE_2)
			Assert.assertEquals(attributeContent.get(2), OLD_VALUE_3)			
		}
	}
	
	/**
	 * Model is in state after the single or multi valued unset change.
	 */
	def private void assertIsStateAfter() {
		Assert.assertFalse(affectedEObject.eIsSet(affectedFeature))
	}
	
	/**
	 * Change is not resolved.
	 */
	def private static assertIsNotResolved(ExplicitUnsetEAttribute<Root, Integer> change, Root affectedRootObject) {
		Assert.assertFalse(change.isResolved)
		Assert.assertFalse(change.affectedEObject == affectedRootObject)
		for (c : change.atomicChanges) {
			Assert.assertFalse(c.isResolved)
			Assert.assertFalse((c as SubtractiveAttributeEChange<Root, Integer>).affectedEObject == affectedRootObject)
		}
	}
	
	/**
	 * Change is resolved.
	 */
	def private static assertIsResolved(ExplicitUnsetEAttribute<Root, Integer> change, Root affectedRootObject) {
		Assert.assertNotNull(change)
		Assert.assertTrue(change.isResolved)
		Assert.assertTrue(change.affectedEObject == affectedRootObject)
		for (c : change.atomicChanges) {
			Assert.assertTrue((c as SubtractiveAttributeEChange<Root, Integer>).affectedEObject == affectedRootObject)
		}		
	}
	
	/**
	 * Creates new unresolved change.
	 */
	def private ExplicitUnsetEAttribute<Root, Integer> createUnresolvedChange() {
		var List<SubtractiveAttributeEChange<Root, Integer>> subtractiveChanges = 
			new ArrayList<SubtractiveAttributeEChange<Root, Integer>>
		if (!affectedFeature.many) {
			subtractiveChanges.add(atomicFactory.<Root, Integer>createReplaceSingleAttributeChange
				(affectedEObject, affectedFeature, OLD_VALUE, affectedFeature.defaultValue as Integer))			
		} else {
			subtractiveChanges.add(atomicFactory.<Root, Integer>createRemoveAttributeChange
				(affectedEObject, affectedFeature, 2, OLD_VALUE_3))
			subtractiveChanges.add(atomicFactory.<Root, Integer>createRemoveAttributeChange
				(affectedEObject, affectedFeature, 1, OLD_VALUE_2))
			subtractiveChanges.add(atomicFactory.<Root, Integer>createRemoveAttributeChange
				(affectedEObject, affectedFeature, 0, OLD_VALUE))				
		}
		return compoundFactory.<Root, Integer>createExplicitUnsetEAttributeChange(affectedEObject, affectedFeature, subtractiveChanges)
	}	
	
	/**
	 * Starts a test with resolving a change before the change is applied.
	 */
	def private void resolveBeforeTest() {
		// State before
		assertIsStateBefore
		
		// Create change
		val unresolvedChange = createUnresolvedChange()
		unresolvedChange.assertIsNotResolved(affectedEObject)
		
		// Resolve 1
		var resolvedChange = unresolvedChange.resolveBefore(resourceSet)
			as ExplicitUnsetEAttribute<Root, Integer>
		resolvedChange.assertIsResolved(affectedEObject)
		
		// Model should be unaffected.
		assertIsStateBefore
				
		// Resolve 2
		var resolvedAndAppliedChange = unresolvedChange.resolveBeforeAndApplyForward(resourceSet)
			as ExplicitUnsetEAttribute<Root, Integer>
		resolvedAndAppliedChange.assertIsResolved(affectedEObject)
		
		// State after
		assertIsStateAfter			
}
	
	/**
	 * Starts a test with resolving the change after the change is applied.
	 */
	def private void resolveAfterTest() {
		// State Before
		assertIsStateBefore	
		
		// Create change
		val unresolvedChange = createUnresolvedChange()
		unresolvedChange.assertIsNotResolved(affectedEObject)
		
		// Set state after
		prepareStateAfter
		assertIsStateAfter
		
		// Resolve 1
		var resolvedChange = unresolvedChange.resolveAfter(resourceSet)
			as ExplicitUnsetEAttribute<Root, Integer>
		resolvedChange.assertIsResolved(affectedEObject)
		
		// Model should be unaffected.
		assertIsStateAfter	
		
		// Resolve 2
		var resolvedAndAppliedChange = unresolvedChange.resolveAfterAndApplyBackward(resourceSet)
			as ExplicitUnsetEAttribute<Root, Integer>
		resolvedAndAppliedChange.assertIsResolved(affectedEObject)
		
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
		val resolvedChange = createUnresolvedChange().resolveBefore(resourceSet)
			as ExplicitUnsetEAttribute<Root, Integer>
			
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
		val resolvedChange = createUnresolvedChange().resolveBefore(resourceSet)
			as ExplicitUnsetEAttribute<Root, Integer>
			
		// Set state after
		prepareStateAfter
		assertIsStateAfter
		
		// Apply forward
		Assert.assertTrue(resolvedChange.applyBackward)
		
		// State before
		assertIsStateBefore		
	}
}