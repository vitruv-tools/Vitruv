package tools.vitruv.framework.tests.echange.compound

import allElementTypes.AllElementTypesPackage
import allElementTypes.Root
import java.util.ArrayList
import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EAttribute
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature
import tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange
import tools.vitruv.framework.tests.echange.EChangeTest
import org.junit.Assert

/**
 * Test class for the concrete {@link ExplicitUnsetEFeature} EChange,
 * which unsets a single or multi valued attribute.
 */
class ExplicitUnsetEFeatureTest extends EChangeTest {
	protected var Root affectedEObject = null
	protected var EAttribute affectedFeature = null
	protected var EList<Integer> attributeContent = null
	
	protected static val Integer OLD_VALUE = 111
	protected static val Integer OLD_VALUE_2 = 222
	protected static val Integer OLD_VALUE_3 = 333
	
	
	@Before
	override public void beforeTest() {
		super.beforeTest()
		affectedEObject = rootObject1	
	}

	/**
	 * Resolves a {@link ExplicitUnsetEFeature} EChange. The feature is a single
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
	 * Resolves a {@link ExplicitUnsetEFeature} EChange. The feature is a single 
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
	 * Resolves a {@link ExplicitUnsetEFeature} EChange. The feature is a multi
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
	 * Resolves a {@link ExplicitUnsetEFeature} EChange. The feature is a single 
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
	 * Tests whether resolving the {@link ExplicitUnsetEFeature} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrecType() {
		// Set state before
		isSingleValuedAttributeTest
		
		// Create change
		val unresolvedChange = createUnresolvedChange()
		
		// Resolve		
 		val resolvedChange = unresolvedChange.resolveBefore(resourceSet1)
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)		
	}
		
	/**
	 * Tests a {@link ExplicitUnsetEFeature} EChange by applying it forward.
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
	 * Tests a {@link ExplicitUnsetEFeature} EChange by applying it forward. 
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
	 * Tests a {@link ExplicitUnsetEFeature} EChange by applying it backward.
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
	 * Tests a {@link ExplicitUnsetEFeature} EChange by applying it backward. 
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
	
	def private void prepareStateBefore() {
		if (!affectedFeature.many) {
			affectedEObject.eSet(affectedFeature, OLD_VALUE)					
		} else {
			attributeContent.add(OLD_VALUE)
			attributeContent.add(OLD_VALUE_2)
			attributeContent.add(OLD_VALUE_3)			
		}
	}
	
	def private void prepareStateAfter() {
		affectedEObject.eUnset(affectedFeature)		
	}
	
	def private void assertIsStateBefore() {
		if (!affectedFeature.many) {
			Assert.assertTrue(affectedEObject.eIsSet(affectedFeature))
			Assert.assertEquals(affectedEObject.eGet(affectedFeature), OLD_VALUE)
		} else {
			Assert.assertTrue(affectedEObject.eIsSet(affectedFeature))
			Assert.assertEquals(attributeContent.get(0), OLD_VALUE)
			Assert.assertEquals(attributeContent.get(1), OLD_VALUE_2)
			Assert.assertEquals(attributeContent.get(2), OLD_VALUE_3)			
		}
	}
	
	def private void assertIsStateAfter() {
		Assert.assertFalse(affectedEObject.eIsSet(affectedFeature))
	}
	
	def private assertIsNotResolved(ExplicitUnsetEFeature<Root, Integer> change, Root affectedRootObject) {
		Assert.assertFalse(change.isResolved)
		for (c : change.atomicChanges) {
			Assert.assertFalse(c.isResolved)
			Assert.assertFalse((c as SubtractiveAttributeEChange<Root, Integer>).affectedEObject == affectedRootObject)
		}
	}
	
	def private assertIsResolved(ExplicitUnsetEFeature<Root, Integer> change, Root affectedRootObject) {
		Assert.assertNotNull(change)
		Assert.assertTrue(change.isResolved)
		for (c : change.atomicChanges) {
			Assert.assertTrue((c as SubtractiveAttributeEChange<Root, Integer>).affectedEObject == affectedRootObject)
		}		
	}
	
	/**
	 * Creates new unresolved change for a single valued attribute.
	 */
	def private ExplicitUnsetEFeature<Root, Integer> createUnresolvedChange() {
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
		return compoundFactory.<Root, Integer>createExplicitUnsetChange(subtractiveChanges)
	}	
	
	def private void resolveBeforeTest() {
		// State before
		assertIsStateBefore
		
		// Create change
		val unresolvedChange = createUnresolvedChange()
		unresolvedChange.assertIsNotResolved(affectedEObject)
		
		// Resolve 1
		var resolvedChange = unresolvedChange.resolveBefore(resourceSet1)
			as ExplicitUnsetEFeature<Root, Integer>
		resolvedChange.assertIsResolved(affectedEObject)
		
		// Model should be unaffected.
		assertIsStateBefore
				
		// Resolve 2
		var resolvedAndAppliedChange = unresolvedChange.resolveBeforeAndApplyForward(resourceSet1)
			as ExplicitUnsetEFeature<Root, Integer>
		resolvedAndAppliedChange.assertIsResolved(affectedEObject)
		
		// State after
		assertIsStateAfter			
	}
	
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
		var resolvedChange = unresolvedChange.resolveAfter(resourceSet1)
			as ExplicitUnsetEFeature<Root, Integer>
		resolvedChange.assertIsResolved(affectedEObject)
		
		// Model should be unaffected.
		assertIsStateAfter	
		
		// Resolve 2
		var resolvedAndAppliedChange = unresolvedChange.resolveAfterAndApplyBackward(resourceSet1)
			as ExplicitUnsetEFeature<Root, Integer>
		resolvedAndAppliedChange.assertIsResolved(affectedEObject)
		
		// State before
		assertIsStateBefore			
	}
	
	def private void applyForwardTest() {
		// State before
		assertIsStateBefore
		
		// Create and resolve change
		val resolvedChange = createUnresolvedChange().resolveBefore(resourceSet1)
			as ExplicitUnsetEFeature<Root, Integer>
			
		// Apply forward
		Assert.assertTrue(resolvedChange.applyForward)
		
		// State after
		assertIsStateAfter		
	}
	
	def private void applyBackwardTest() {
		// State before
		assertIsStateBefore
		
		// Create and resolve change
		val resolvedChange = createUnresolvedChange().resolveBefore(resourceSet1)
			as ExplicitUnsetEFeature<Root, Integer>
			
		// Set state after
		prepareStateAfter
		assertIsStateAfter
		
		// Apply forward
		Assert.assertTrue(resolvedChange.applyBackward)
		
		// State before
		assertIsStateBefore		
	}
}