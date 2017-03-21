package tools.vitruv.framework.tests.echange.compound

import allElementTypes.Root
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EAttribute
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.compound.ReplaceInEList
import tools.vitruv.framework.change.echange.feature.attribute.InsertEAttributeValue
import tools.vitruv.framework.change.echange.feature.attribute.RemoveEAttributeValue
import tools.vitruv.framework.tests.echange.feature.attribute.InsertRemoveEAttributeTest

/**
 * Test class for the concrete {@link ReplaceInEList} EChange,
 * which replaces a value in a multi valued attribute or reference.
 */
class ReplaceInEListTest extends InsertRemoveEAttributeTest {
	protected var EList<Integer> attributeContent = null
	
	protected static val Integer OLD_VALUE = 123
	protected static val Integer OLD_VALUE_2 = 456
	
	protected static val Integer DEFAULT_INDEX = 0
	protected static val Integer DEFAULT_INDEX_2 = 1
	
	/**
	 * Calls setup of super class and sets the list of the affected 
	 * feature for the tests.
	 */
	@Before
	override public void beforeTest() {
		super.beforeTest
		attributeContent = affectedEObject.eGet(affectedFeature) as EList<Integer>
		prepareStateBefore
	}
	
	/**
	 * Resolves a {@link ReplaceInEList} EChange. The model is 
	 * in the state before the change, so the old values are in 
	 * the multi valued attribute.
	 */
	@Test
	def public void resolveReplaceInEListTest() {
		// Set state before
		prepareStateBefore
		assertIsStateBefore
		
		// Create change
		val unresolvedChange = createUnresolvedChange(DEFAULT_INDEX, OLD_VALUE, NEW_VALUE)
		unresolvedChange.assertIsNotResolved(affectedEObject)

		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(resourceSet)
			as ReplaceInEList<Root, EAttribute, Integer, RemoveEAttributeValue<Root, Integer>, 
			InsertEAttributeValue<Root, Integer>>
		resolvedChange.assertIsResolved(affectedEObject)			
		
		// Resolving applies all changes and reverts them, so the model should be unaffected.
		assertIsStateBefore
	}
	
	/**
	 * Resolves a {@link ReplaceInEList} EChange. The model is 
	 * in the state after the change, so the new values are in 
	 * the multi valued attribute.
	 */
	@Test
	def public void resolveReplaceInEListTest2() {
		// Create change
		val unresolvedChange = createUnresolvedChange(DEFAULT_INDEX, OLD_VALUE, NEW_VALUE)
		unresolvedChange.assertIsNotResolved(affectedEObject)
		
		// Set state after
		prepareStateAfter
		assertIsStateAfter
						
		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(resourceSet)
			as ReplaceInEList<Root, EAttribute, Integer, RemoveEAttributeValue<Root, Integer>, 
			InsertEAttributeValue<Root, Integer>>
		resolvedChange.assertIsResolved(affectedEObject)
		
		// Resolving applies all changes and reverts them, so the model should be unaffected.
		assertIsStateAfter
	}
	
	/**
	 * Tests whether resolving the {@link ReplaceInEList} EChange
	 * returns the same class.
	 */	
	@Test
	def public void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange(DEFAULT_INDEX, OLD_VALUE, NEW_VALUE)
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(resourceSet)
			as ReplaceInEList<Root, EAttribute, Integer, RemoveEAttributeValue<Root, Integer>, 
			InsertEAttributeValue<Root, Integer>>	
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)		
	}
	
	/**
	 * Tests the {@link ReplaceInEList} EChange by applying it forward.
	 */
	@Test
	def public void replaceInEListApplyForwardTest() {
		// State before	
		assertIsStateBefore
		
		// Create change 1
		val resolvedChange = createUnresolvedChange(DEFAULT_INDEX, OLD_VALUE, NEW_VALUE).
			resolveBefore(resourceSet)
			
		// Apply forward change 1
		Assert.assertTrue(resolvedChange.applyForward)
		
		Assert.assertEquals(attributeContent.size, 2)
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX), NEW_VALUE)	
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX_2), OLD_VALUE_2)		
		
		// Create change 2
		val resolvedChange2 = createUnresolvedChange(DEFAULT_INDEX_2, OLD_VALUE_2, NEW_VALUE_2).
			resolveBefore(resourceSet)
			
		// Apply forward change 2
		Assert.assertTrue(resolvedChange2.applyForward)			

		// State after
		assertIsStateAfter	
	}
	
	/**
	 * Tests the {@link ReplaceInEList} EChange by applying it backward.
	 */
	@Test
	def public void replaceInEListApplyBackwardTest() {
		// State before	
		assertIsStateBefore
		
		// Create and resolve and apply change 1
		val resolvedChange = createUnresolvedChange(DEFAULT_INDEX, OLD_VALUE, NEW_VALUE).
			resolveBefore(resourceSet)
		Assert.assertTrue(resolvedChange.applyForward)	
		
		// Create and resolve and apply change 2
		val resolvedChange2 = createUnresolvedChange(DEFAULT_INDEX_2, OLD_VALUE_2, NEW_VALUE_2).
			resolveBefore(resourceSet)
		Assert.assertTrue(resolvedChange2.applyForward)			
		
		// State after
		assertIsStateAfter	
		
		// Apply backward change 2
		Assert.assertTrue(resolvedChange2.applyBackward)
		
		Assert.assertEquals(attributeContent.size, 2)
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX), NEW_VALUE)	
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX_2), OLD_VALUE_2)	
		
		// Apply backward change 1
		Assert.assertTrue(resolvedChange.applyBackward)	
		
		// State before
		assertIsStateBefore			
	}
	
	/**
	 * Sets the attribute to the state before the change.
	 */
	def private void prepareStateBefore() {
		attributeContent.clear
		attributeContent.add(OLD_VALUE)
		attributeContent.add(OLD_VALUE_2)		
	}
		
	/**
	 * Sets the attribute to the state after the change.
	 */
	def private void prepareStateAfter() {
		attributeContent.clear
		attributeContent.add(NEW_VALUE)
		attributeContent.add(NEW_VALUE_2)		
	}
	
	/**
	 * Model is in state before the change.
	 */
	def private void assertIsStateBefore() {
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX), OLD_VALUE)
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX_2), OLD_VALUE_2)	
		Assert.assertEquals(attributeContent.size, 2)	
	}
	
	/**
	 * Model is in state after the change.
	 */
	def private void assertIsStateAfter() {
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX), NEW_VALUE)
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX_2), NEW_VALUE_2)
		Assert.assertEquals(attributeContent.size, 2)	
	}
		
	/**
	 * Change is not resolved.
	 */
	def private static void assertIsNotResolved(ReplaceInEList<Root, EAttribute, Integer, RemoveEAttributeValue<Root, Integer>, 
		InsertEAttributeValue<Root, Integer>> change, Root affectedRootObject) {
		Assert.assertFalse(change.isResolved)
		Assert.assertFalse(change.removeChange.isResolved)
		Assert.assertFalse(change.insertChange.isResolved)	
		Assert.assertFalse(change.removeChange.affectedEObject == affectedRootObject)
		Assert.assertFalse(change.insertChange.affectedEObject == affectedRootObject)
	}
	
	/**
	 * Change is resolved with state before change.
	 */
	def private static void assertIsResolved(ReplaceInEList<Root, EAttribute, Integer, RemoveEAttributeValue<Root, Integer>, 
		InsertEAttributeValue<Root, Integer>> change, Root affectedRootObject) {
		Assert.assertTrue(change.isResolved)
		Assert.assertTrue(change.removeChange.affectedEObject == affectedRootObject)
		Assert.assertTrue(change.insertChange.affectedEObject == affectedRootObject)
	}
	
	/**
	 * Creates new unresolved change.
	 */	
	def private ReplaceInEList<Root, EAttribute, Integer, RemoveEAttributeValue<Root, Integer>, 
		InsertEAttributeValue<Root, Integer>> createUnresolvedChange(int index, int oldValue, int newValue) {
		val removeChange = atomicFactory.<Root, Integer>createRemoveAttributeChange
			(affectedEObject, affectedFeature, index, oldValue)
		val insertChange = atomicFactory.<Root, Integer>createInsertAttributeChange
			(affectedEObject, affectedFeature, index, newValue)
		val change = compoundFactory.<Root, EAttribute, Integer, 
			RemoveEAttributeValue<Root, Integer>, InsertEAttributeValue<Root, Integer>>
			createReplaceInEListChange(removeChange, insertChange)
		return change
	}
}