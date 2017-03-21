package tools.vitruv.framework.tests.echange.compound

import allElementTypes.Root
import java.util.ArrayList
import java.util.List
import org.eclipse.emf.common.util.EList
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.compound.CompoundSubtraction
import tools.vitruv.framework.change.echange.feature.attribute.RemoveEAttributeValue
import tools.vitruv.framework.tests.echange.feature.attribute.InsertRemoveEAttributeTest

/**
 * Test class for the concrete {@link CompoundSubtraction} EChange,
 * which removes several attributes or references from a multi valued feature.
 */
class CompoundSubtractionTest extends InsertRemoveEAttributeTest {
	protected var EList<Integer> attributeContent = null
	
	protected static val Integer DEFAULT_INDEX = 0
	
	/**
	 * Calls setup of super class and sets list of the affected feature
	 * for the tests and sets state before.
	 */
	@Before
	override public void beforeTest() {
		super.beforeTest
		attributeContent = affectedEObject.eGet(affectedFeature) as EList<Integer>
		prepareStateBefore
	}
	
	/**
	 * Resolves a {@link CompoundSubtraction} EChange. The model is in the state
	 * before the change, so the multi valued attribute is filled with values.
	 */
	@Test
	def public void resolveCompoundSubtractionTest() {
		// State before
		assertIsStateBefore
		
		// Create change
		val unresolvedChange = createUnresolvedChange()
		unresolvedChange.assertIsNotResolved(affectedEObject)	
			
		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(resourceSet) 
			as CompoundSubtraction<Integer, RemoveEAttributeValue<Root, Integer>>
		resolvedChange.assertIsResolved(affectedEObject)

		// Resolving applies all changes and reverts them, so the model should be unaffected.	
		assertIsStateBefore
	}
	
	/**
	 * Resolves a {@link CompoundSubtraction} EChange. The model is in the state
	 * after the change, so the multi valued attribute is empty.
	 */
	@Test
	def public void resolveCompoundSubtractionTest2() {
		// State before
		assertIsStateBefore
		
		// Create change
		val unresolvedChange = createUnresolvedChange()
		unresolvedChange.assertIsNotResolved(affectedEObject)	
			
		// Set state after change
		prepareStateAfter
		assertIsStateAfter
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(resourceSet) 
			as CompoundSubtraction<Integer, RemoveEAttributeValue<Root, Integer>>
		resolvedChange.assertIsResolved(affectedEObject)

		// Resolving applies all changes and reverts them, so the model should be unaffected.	
		assertIsStateAfter
	} 
	
	/**
	 * Tests whether resolving the {@link CompoundSubtraction} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange()
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(resourceSet) 
			as CompoundSubtraction<Integer, RemoveEAttributeValue<Root, Integer>>
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)					
	}
	
	/**
	 * Tests the {@link CompoundSubtraction} EChange by applying it forward.
	 */
	@Test
	def public void compoundSubtractionApplyForwardTest() {
		// State before
		assertIsStateBefore
		
		// Create change
		val resolvedChange = createUnresolvedChange().resolveBefore(resourceSet) 
			as CompoundSubtraction<Integer, RemoveEAttributeValue<Root, Integer>>
			
		// Apply forward
		Assert.assertTrue(resolvedChange.applyForward)
		
		// State after
		assertIsStateAfter
	}
	
	/**
	 * Tests the {@link CompoundSubtraction} EChange by applying it backward.
	 */
	@Test
	def public void compoundSubtractionApplyBackwardTest() {
		// State before
		assertIsStateBefore
		
		// Create change
		val resolvedChange = createUnresolvedChange().resolveBefore(resourceSet) 
			as CompoundSubtraction<Integer, RemoveEAttributeValue<Root, Integer>>
			
		// Set state after change
		prepareStateAfter
		assertIsStateAfter
			
		// Apply backward
		Assert.assertTrue(resolvedChange.applyBackward)
		
		// State before
		assertIsStateBefore						
	}
	
	/**
	 * Sets the state before the change.
	 */
	def private void prepareStateBefore() {
		attributeContent.add(NEW_VALUE)
		attributeContent.add(NEW_VALUE_2)
		attributeContent.add(NEW_VALUE_3)
	}
	
	/**
	 * Sets the state after the change.
	 */
	def private void prepareStateAfter() {
		attributeContent.clear
	}
	
	/**
	 * Model is in state before the change.
	 */
	def private void assertIsStateBefore() {
		Assert.assertEquals(attributeContent.size, 3)
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX), NEW_VALUE)
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX + 1), NEW_VALUE_2)
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX + 2), NEW_VALUE_3)
	}
	
	/**
	 * Model is in state after the change.
	 */
	def private void assertIsStateAfter() {
		Assert.assertTrue(attributeContent.empty)
	}
	
	/**
	 * Change is not resolved.
	 */
	def private static void assertIsNotResolved(CompoundSubtraction<Integer, RemoveEAttributeValue<Root, Integer>> change, 
		Root affectedObject) {
		Assert.assertFalse(change.isResolved)
		for (c : change.subtractiveChanges) {
			Assert.assertFalse(c.isResolved)
			Assert.assertFalse(c.affectedEObject == affectedObject)
		}
	}
	
	/**
	 * Change is resolved with state before change.
	 */
	def private static void assertIsResolved(CompoundSubtraction<Integer, RemoveEAttributeValue<Root, Integer>> change, 
		Root affectedObject) {
		Assert.assertTrue(change.isResolved)
		for (c : change.subtractiveChanges) {
			Assert.assertTrue(c.affectedEObject == affectedObject)
		}
	}	
	
	/**
	 * Creates the atomic changes for the compound change in the tests.
	 */
	def private List<RemoveEAttributeValue<Root, Integer>> getSubtractiveChanges() {
		var List<RemoveEAttributeValue<Root, Integer>> changes = new ArrayList<RemoveEAttributeValue<Root, Integer>>()
		changes.add(atomicFactory.<Root, Integer>createRemoveAttributeChange(
			affectedEObject, affectedFeature, DEFAULT_INDEX + 2, NEW_VALUE_3))
		changes.add(atomicFactory.<Root, Integer>createRemoveAttributeChange(
			affectedEObject, affectedFeature, DEFAULT_INDEX + 1, NEW_VALUE_2))
		changes.add(atomicFactory.<Root, Integer>createRemoveAttributeChange(
			affectedEObject, affectedFeature, DEFAULT_INDEX, NEW_VALUE))		
		return changes
	}	
	
	/**
	 * Creates new unresolved change.
	 */
	def private CompoundSubtraction<Integer, RemoveEAttributeValue<Root, Integer>> createUnresolvedChange() {
		return compoundFactory.<Integer, RemoveEAttributeValue<Root, Integer>>
			createCompoundSubtractionChange(getSubtractiveChanges())
	}	
}