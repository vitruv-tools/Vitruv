package tools.vitruv.framework.tests.echange.compound

import allElementTypes.Root
import java.util.ArrayList
import java.util.List
import org.eclipse.emf.common.util.EList
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.compound.CompoundAddition
import tools.vitruv.framework.change.echange.feature.attribute.InsertEAttributeValue
import tools.vitruv.framework.tests.echange.feature.attribute.InsertRemoveEAttributeTest

/**
 * Test class for the concrete {@link CompoundAddition} EChange,
 * which inserts several values into a multi valued feature.
 */
class CompoundAdditionTest extends InsertRemoveEAttributeTest {
	protected var EList<Integer> attributeContent = null
	
	protected static val Integer DEFAULT_INDEX = 0
	
	/**
	 * Calls setup of super class and sets the list of the affected feature
	 * for the tests and sets state before.
	 */
	@Before
	override public void beforeTest() {
		super.beforeTest
		attributeContent = affectedEObject.eGet(affectedFeature) as EList<Integer>
	}
	 
	/**
	 * Sets the state after the change and fills the attribute of the tests with values.
	 */
	def private void prepareStateAfter() {
		attributeContent.add(NEW_VALUE)
		attributeContent.add(NEW_VALUE_2)
		attributeContent.add(NEW_VALUE_3)
	}
	
	/**
	 * Model is in state before the change.
	 */
	def private void assertIsStateBefore() {
		Assert.assertTrue(attributeContent.empty)
	}
	
	/** 
	 * Model is in state after the change.
	 */
	def private void assertIsStateAfter() {
		Assert.assertEquals(attributeContent.size, 3)
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX), NEW_VALUE)
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX + 1), NEW_VALUE_2)
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX + 2), NEW_VALUE_3)
	}
	
	/**
	 * Change is not resolved.
	 */
	def private static void assertIsNotResolved(CompoundAddition<Integer, InsertEAttributeValue<Root, Integer>> change, 
		Root affectedObject) {
		Assert.assertFalse(change.isResolved)
		for (c : change.additiveChanges) {
			Assert.assertFalse(c.isResolved)
			Assert.assertFalse(c.affectedEObject == affectedObject)
		}
	}
	
	/**
	 * Change is resolved with state before change.
	 */
	def private static void assertIsResolved(CompoundAddition<Integer, InsertEAttributeValue<Root, Integer>> change, 
		Root affectedObject) {
		Assert.assertTrue(change.isResolved)
		for (c : change.additiveChanges) {
			Assert.assertTrue(c.affectedEObject == affectedObject)
		}
	}
	
	/**
	 * Creates the atomic changes for the compound change in the tests.
	 */
	def private List<InsertEAttributeValue<Root, Integer>> getAdditiveChanges() {
		var List<InsertEAttributeValue<Root, Integer>> changes = new ArrayList<InsertEAttributeValue<Root, Integer>>()
		changes.add(atomicFactory.<Root, Integer>createInsertAttributeChange(
			affectedEObject, affectedFeature, DEFAULT_INDEX, NEW_VALUE))
		changes.add(atomicFactory.<Root, Integer>createInsertAttributeChange(
			affectedEObject, affectedFeature, DEFAULT_INDEX + 1, NEW_VALUE_2))
		changes.add(atomicFactory.<Root, Integer>createInsertAttributeChange(
			affectedEObject, affectedFeature, DEFAULT_INDEX + 2, NEW_VALUE_3))		
		return changes
	}
	
	/**
	 * Creates new unresolved change.
	 */
	def private CompoundAddition<Integer, InsertEAttributeValue<Root, Integer>> createUnresolvedChange() {
		return compoundFactory.<Integer, InsertEAttributeValue<Root, Integer>>
			createCompoundAdditionChange(getAdditiveChanges())
	}	
	
	/**
	 * Resolves a {@link CompoundAddition} EChange. The model is in the state
	 * before the change, so the multi valued attribute is empty.
	 */
	@Test
	def public void resolveCompoundAdditionTest() {
		// State before
		assertIsStateBefore
		
		// Create change
		val unresolvedChange = createUnresolvedChange()
		unresolvedChange.assertIsNotResolved(affectedEObject)	
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(resourceSet) 
			as CompoundAddition<Integer, InsertEAttributeValue<Root, Integer>>
		resolvedChange.assertIsResolved(affectedEObject)
					
		// Resolving applies all changes and reverts them, so the model should be unaffected.	
		assertIsStateBefore
	}
	
	/**
	 * Resolves a {@link CompoundAddition} EChange. The model is in the state
	 * after the change, so the multi valued attribute is filled with values.
	 */
	@Test
	def public void resolveCompoundAdditionTest2() {
		// Set state before change
		assertIsStateBefore
		
		// Create change
		val unresolvedChange = createUnresolvedChange()
		unresolvedChange.assertIsNotResolved(affectedEObject)	
			
		// Set state after change
		prepareStateAfter
		assertIsStateAfter
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(resourceSet) 
			as CompoundAddition<Integer, InsertEAttributeValue<Root, Integer>>
		resolvedChange.assertIsResolved(affectedEObject)
					
		// Resolving applies all changes and reverts them, so the model should be unaffected.	
		assertIsStateAfter			
	}
	
	/**
	 * Tests whether resolving the {@link CompoundAddition} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange()
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(resourceSet) 
			as CompoundAddition<Integer, InsertEAttributeValue<Root, Integer>>
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)	
	}
	
	/**
	 * Tests the {@link CompoundAddition} EChange by applying it forward.
	 */
	@Test
	def public void compoundAdditionApplyForwardTest() {
		// State before
		assertIsStateBefore
		
		// Create change
		val resolvedChange = createUnresolvedChange().resolveBefore(resourceSet) 
			as CompoundAddition<Integer, InsertEAttributeValue<Root, Integer>>
		
		// Apply forward
		Assert.assertTrue(resolvedChange.applyForward)
		
		// State after
		assertIsStateAfter
	}
	
	/**
	 * Test the {@link CompoundAddition} EChange by applying it backward.
	 */
	@Test
	def public void compoundAdditionApplyBackwardTest() {
		// State before
		assertIsStateBefore
		
		// Create and resolve and apply change
		val resolvedChange = createUnresolvedChange().resolveBefore(resourceSet) 
			as CompoundAddition<Integer, InsertEAttributeValue<Root, Integer>>
		Assert.assertTrue(resolvedChange.applyForward)
		
		// State after
		assertIsStateAfter
		
		// Apply backward
		Assert.assertTrue(resolvedChange.applyBackward)
		
		// State before
		assertIsStateBefore
	}
}