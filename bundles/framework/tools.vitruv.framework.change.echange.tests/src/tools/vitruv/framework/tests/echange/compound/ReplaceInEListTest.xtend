package tools.vitruv.framework.tests.echange.compound

import allElementTypes.Root
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EAttribute
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory
import tools.vitruv.framework.change.echange.TypeInferringCompoundEChangeFactory
import tools.vitruv.framework.change.echange.compound.ReplaceInEList
import tools.vitruv.framework.change.echange.feature.attribute.InsertEAttributeValue
import tools.vitruv.framework.change.echange.feature.attribute.RemoveEAttributeValue
import tools.vitruv.framework.tests.echange.feature.attribute.InsertRemoveEAttributeTest
import org.junit.Assert

/**
 * Test class for the concrete {@link ReplaceInEList} EChange,
 * which replaces a value in a multi valued attribute or reference.
 */
class ReplaceInEListTest extends InsertRemoveEAttributeTest {
	protected var EList<Integer> attributeContent = null
	
	protected static val Integer DEFAULT_OLD_VALUE = 123
	protected static val Integer DEFAULT_OLD_VALUE_2 = 456
	
	protected static val Integer DEFAULT_INDEX = 0
	protected static val Integer DEFAULT_INDEX_2 = 1
	
	/**
	 * Calls setup of super class and sets the list of the affected 
	 * feature for the tests.
	 */
	@Before
	override public void beforeTest() {
		super.beforeTest
		attributeContent = defaultAffectedEObject.eGet(defaultAffectedFeature) as EList<Integer>
	}
	
	/**
	 * Sets the attribute to the state before the change.
	 */
	def private void prepareStateBefore() {
		attributeContent.clear
		attributeContent.add(DEFAULT_OLD_VALUE)
		attributeContent.add(DEFAULT_OLD_VALUE_2)		
	}
	
	/**
	 * Sets the attribute to the state after the change.
	 */
	def private void prepareStateAfter() {
		attributeContent.clear
		attributeContent.add(DEFAULT_NEW_VALUE)
		attributeContent.add(DEFAULT_NEW_VALUE_2)		
	}
	
	def private ReplaceInEList<Root, EAttribute, Integer, RemoveEAttributeValue<Root, Integer>, 
		InsertEAttributeValue<Root, Integer>> createChange(int index, int oldValue, int newValue) {
		val removeChange = TypeInferringAtomicEChangeFactory.<Root, Integer>createRemoveAttributeChange
			(defaultAffectedEObject, defaultAffectedFeature, index, oldValue, true)
		val insertChange = TypeInferringAtomicEChangeFactory.<Root, Integer>createInsertAttributeChange
			(defaultAffectedEObject, defaultAffectedFeature, index, newValue, true)
		val change = TypeInferringCompoundEChangeFactory.<Root, EAttribute, Integer, 
			RemoveEAttributeValue<Root, Integer>, InsertEAttributeValue<Root, Integer>>
			createReplaceInEListChange(removeChange, insertChange)
		return change
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
		val oldSize = attributeContent.size
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX), DEFAULT_OLD_VALUE)	
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX_2), DEFAULT_OLD_VALUE_2)	
		
		// Create change
		val unresolvedChange = createChange(DEFAULT_INDEX, DEFAULT_OLD_VALUE, DEFAULT_NEW_VALUE)
		
		Assert.assertFalse(unresolvedChange.isResolved)
		Assert.assertFalse(unresolvedChange.removeChange.affectedEObject == defaultAffectedEObject)
		Assert.assertFalse(unresolvedChange.insertChange.affectedEObject == defaultAffectedEObject)
		
		// Resolve
		val resolvedChange = unresolvedChange.copyAndResolveBefore(resourceSet1)
			as ReplaceInEList<Root, EAttribute, Integer, RemoveEAttributeValue<Root, Integer>, 
			InsertEAttributeValue<Root, Integer>>
			
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange != resolvedChange)	
		Assert.assertTrue(resolvedChange.removeChange.affectedEObject == defaultAffectedEObject)
		Assert.assertTrue(resolvedChange.insertChange.affectedEObject == defaultAffectedEObject)
		
		// Resolving applies all changes and reverts them, so the model should be unaffected.
		Assert.assertEquals(attributeContent.size, oldSize)
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX), DEFAULT_OLD_VALUE)	
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX_2), DEFAULT_OLD_VALUE_2)	
	}
	
	/**
	 * Resolves a {@link ReplaceInEList} EChange. The model is 
	 * in the state after the change, so the new values are in 
	 * the multi valued attribute.
	 */
	@Test
	def public void resolveReplaceInEListTest2() {
		// Set state before
		prepareStateAfter
		val oldSize = attributeContent.size
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX), DEFAULT_NEW_VALUE)	
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX_2), DEFAULT_NEW_VALUE_2)	
		
		// Create change
		val unresolvedChange = createChange(DEFAULT_INDEX, DEFAULT_OLD_VALUE, DEFAULT_NEW_VALUE)
		
		Assert.assertFalse(unresolvedChange.isResolved)
		Assert.assertFalse(unresolvedChange.removeChange.affectedEObject == defaultAffectedEObject)
		Assert.assertFalse(unresolvedChange.insertChange.affectedEObject == defaultAffectedEObject)
		
		// Resolve
		val resolvedChange = unresolvedChange.copyAndResolveAfter(resourceSet1)
			as ReplaceInEList<Root, EAttribute, Integer, RemoveEAttributeValue<Root, Integer>, 
			InsertEAttributeValue<Root, Integer>>
			
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange != resolvedChange)	
		Assert.assertTrue(resolvedChange.removeChange.affectedEObject == defaultAffectedEObject)
		Assert.assertTrue(resolvedChange.insertChange.affectedEObject == defaultAffectedEObject)
		
		// Resolving applies all changes and reverts them, so the model should be unaffected.
		Assert.assertEquals(attributeContent.size, oldSize)
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX), DEFAULT_NEW_VALUE)	
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX_2), DEFAULT_NEW_VALUE_2)	
	}
	
	/**
	 * Tests whether resolving the {@link ReplaceInEList} EChange
	 * returns the same class.
	 */	
	@Test
	def public void resolveToCorrectType() {
		// Set state before
		prepareStateBefore
		
		// Create change
		val unresolvedChange = createChange(DEFAULT_INDEX, DEFAULT_OLD_VALUE, DEFAULT_NEW_VALUE)
		
		// Resolve
		val resolvedChange = unresolvedChange.copyAndResolveBefore(resourceSet1)
				
		assertDifferentChangeSameClass(unresolvedChange, resolvedChange)		
	}
	
	/**
	 * Tests the {@link ReplaceInEList} EChange by applying it forward.
	 */
	@Test
	def public void replaceInEListApplyForwardTest() {
		// Set state before	
		prepareStateBefore
		val oldSize = attributeContent.size
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX), DEFAULT_OLD_VALUE)	
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX_2), DEFAULT_OLD_VALUE_2)	
		
		// Create change 1
		val resolvedChange = createChange(DEFAULT_INDEX, DEFAULT_OLD_VALUE, DEFAULT_NEW_VALUE).
			copyAndResolveBefore(resourceSet1)
			
		// Apply forward change 1
		Assert.assertTrue(resolvedChange.applyForward)
		
		Assert.assertEquals(attributeContent.size, oldSize)
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX), DEFAULT_NEW_VALUE)	
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX_2), DEFAULT_OLD_VALUE_2)		
		
		// Create change 2
		val resolvedChange2 = createChange(DEFAULT_INDEX_2, DEFAULT_OLD_VALUE_2, DEFAULT_NEW_VALUE_2).
			copyAndResolveBefore(resourceSet1)
			
		// Apply forward change 2
		Assert.assertTrue(resolvedChange2.applyForward)			

		Assert.assertEquals(attributeContent.size, oldSize)
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX), DEFAULT_NEW_VALUE)	
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX_2), DEFAULT_NEW_VALUE_2)		
	}
	
	/**
	 * Tests the {@link ReplaceInEList} EChange by applying it backward.
	 */
	@Test
	def public void replaceInEListApplyBackwardTest() {
		// Set state before	
		prepareStateBefore
		val oldSize = attributeContent.size
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX), DEFAULT_OLD_VALUE)	
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX_2), DEFAULT_OLD_VALUE_2)	
		
		// Create change 1
		val resolvedChange = createChange(DEFAULT_INDEX, DEFAULT_OLD_VALUE, DEFAULT_NEW_VALUE).
			copyAndResolveBefore(resourceSet1)
			
		// Apply forward change 1
		Assert.assertTrue(resolvedChange.applyForward)	
		
		// Create change 2
		val resolvedChange2 = createChange(DEFAULT_INDEX_2, DEFAULT_OLD_VALUE_2, DEFAULT_NEW_VALUE_2).
			copyAndResolveBefore(resourceSet1)
			
		// Apply forward change 2
		Assert.assertTrue(resolvedChange2.applyForward)			

		Assert.assertEquals(attributeContent.size, oldSize)
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX), DEFAULT_NEW_VALUE)	
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX_2), DEFAULT_NEW_VALUE_2)	
		
		// Apply backward change 2
		Assert.assertTrue(resolvedChange2.applyBackward)
		
		Assert.assertEquals(attributeContent.size, oldSize)
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX), DEFAULT_NEW_VALUE)	
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX_2), DEFAULT_OLD_VALUE_2)	
		
		// Apply backward change 1
		Assert.assertTrue(resolvedChange.applyBackward)	
		
		Assert.assertEquals(attributeContent.size, oldSize)			
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX), DEFAULT_OLD_VALUE)	
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX_2), DEFAULT_OLD_VALUE_2)				
	}
}