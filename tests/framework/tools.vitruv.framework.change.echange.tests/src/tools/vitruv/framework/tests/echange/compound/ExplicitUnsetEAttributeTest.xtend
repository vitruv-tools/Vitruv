package tools.vitruv.framework.tests.echange.compound

import allElementTypes.AllElementTypesPackage
import allElementTypes.Root
import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EAttribute
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange
import tools.vitruv.framework.tests.echange.EChangeTest

import static extension tools.vitruv.framework.tests.echange.util.EChangeAssertHelper.*
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.feature.UnsetFeature

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
	def public void resolveBeforeSingleValuedAttributeTest() {
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
	def public void resolveAfterSingleValuedAttributeTest() {
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
 		val resolvedChange = unresolvedChange.resolveBefore(uuidGeneratorAndResolver)
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)	
	}
		
	/**
	 * Tests a {@link ExplicitUnsetEAttribute} EChange by applying it forward.
	 * Unsets a single valued unsettable attribute.
	 */
	@Test
	def public void applyForwardUnsetSingleValuedAttributeTest() {
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
	def public void applyForwardUnsetMulitValuedAttributeTest() {
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
	def public void applyBackwardUnsetSingleValuedAttributeTest() {
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
	def public void applyBackwardUnsetMulitValuedAttributeTest() {
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
		assertIsStateAfter
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
	def protected static assertIsNotResolved(List<? extends EChange> changes) {
		EChangeTest.assertIsNotResolved(changes);
	}
	
	/**
	 * Change is resolved.
	 */
	def private static void assertIsResolved(List<? extends EChange> changes, Root affectedRootObject) {
		assertIsResolved(changes);
		assertType(changes.get(changes.size - 2), SubtractiveAttributeEChange);
		assertType(changes.last, UnsetFeature);
	}
	
	/**
	 * Creates new unresolved change.
	 */
	def private List<? extends EChange> createUnresolvedChange() {
		var List<EChange> subtractiveChanges = newArrayList()
		if (!affectedFeature.many) {
			subtractiveChanges.add(atomicFactory.createReplaceSingleAttributeChange
				(affectedEObject, affectedFeature, OLD_VALUE, affectedFeature.defaultValue as Integer))			
		} else {
			subtractiveChanges.add(atomicFactory.createRemoveAttributeChange
				(affectedEObject, affectedFeature, 2, OLD_VALUE_3))
			subtractiveChanges.add(atomicFactory.createRemoveAttributeChange
				(affectedEObject, affectedFeature, 1, OLD_VALUE_2))
			subtractiveChanges.add(atomicFactory.createRemoveAttributeChange
				(affectedEObject, affectedFeature, 0, OLD_VALUE))		
		}
		return (subtractiveChanges + #[atomicFactory.createUnsetFeatureChange(affectedEObject, affectedFeature)]).toList;
	}	
	
	/**
	 * Starts a test with resolving a change before the change is applied.
	 */
	def private void resolveBeforeTest() {
		// State before
		assertIsStateBefore
		
		// Create change
		val unresolvedChange = createUnresolvedChange()
		unresolvedChange.assertIsNotResolved
		
		// Resolve 1
		var resolvedChange = unresolvedChange.resolveBefore(uuidGeneratorAndResolver)
		resolvedChange.assertIsResolved(affectedEObject)
		
		// Model should be unaffected.
		assertIsStateBefore
				
		// Resolve 2
		var resolvedAndAppliedChange = unresolvedChange.resolveBefore(uuidGeneratorAndResolver)
		resolvedAndAppliedChange.applyForward
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
		unresolvedChange.assertIsNotResolved
		
		// Set state after
		prepareStateAfter
		
		// Resolve 1
		var resolvedChange = unresolvedChange.resolveAfter(uuidGeneratorAndResolver)
		resolvedChange.assertIsResolved(affectedEObject)
		
		// Model should be unaffected.
		assertIsStateAfter	
		
		// Resolve 2
		var resolvedAndAppliedChange = unresolvedChange.resolveAfter(uuidGeneratorAndResolver)
		resolvedAndAppliedChange.applyBackward
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
		val resolvedChange = createUnresolvedChange().resolveBefore(uuidGeneratorAndResolver)
			
		// Apply forward
		resolvedChange.assertApplyForward
		
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
		
		// Apply forward
		resolvedChange.assertApplyBackward
		
		// State before
		assertIsStateBefore		
	}
}