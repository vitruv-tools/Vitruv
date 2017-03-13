package tools.vitruv.framework.tests.echange.compound

import allElementTypes.Root
import java.util.ArrayList
import java.util.List
import org.eclipse.emf.common.util.EList
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory
import tools.vitruv.framework.change.echange.TypeInferringCompoundEChangeFactory
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
	 * for the tests.
	 */
	@Before
	override public void beforeTest() {
		super.beforeTest
		attributeContent = defaultAffectedEObject.eGet(defaultAffectedFeature) as EList<Integer>
	}
	
	/**
	 * Creates the atomic changes for the compound change in the tests.
	 */
	def private List<InsertEAttributeValue<Root, Integer>> getAdditiveChanges() {
		var List<InsertEAttributeValue<Root, Integer>> changes = new ArrayList<InsertEAttributeValue<Root, Integer>>()
		changes.add(TypeInferringAtomicEChangeFactory.<Root, Integer>createInsertAttributeChange(
			defaultAffectedEObject, defaultAffectedFeature, DEFAULT_INDEX, DEFAULT_NEW_VALUE, true))
		changes.add(TypeInferringAtomicEChangeFactory.<Root, Integer>createInsertAttributeChange(
			defaultAffectedEObject, defaultAffectedFeature, DEFAULT_INDEX + 1, DEFAULT_NEW_VALUE_2, true))
		changes.add(TypeInferringAtomicEChangeFactory.<Root, Integer>createInsertAttributeChange(
			defaultAffectedEObject, defaultAffectedFeature, DEFAULT_INDEX + 2, DEFAULT_NEW_VALUE_3, true))		
		return changes
	}
	
	/**
	 * Fills the attribute of the tests with values.
	 */
	def private void prepareAttribute() {
		attributeContent.add(DEFAULT_NEW_VALUE)
		attributeContent.add(DEFAULT_NEW_VALUE_2)
		attributeContent.add(DEFAULT_NEW_VALUE_3)
	}
	
	/**
	 * Resolves a {@link CompoundAddition} EChange. The model is in the state
	 * before the change, so the multi valued attribute is empty.
	 */
	@Test
	def public void resolveCompoundAdditionTest() {
		val oldSize = attributeContent.size
		Assert.assertTrue(attributeContent.empty)
		
		// Create change
		val unresolvedChange = TypeInferringCompoundEChangeFactory.<Integer, InsertEAttributeValue<Root, Integer>>
			createCompoundAdditionChange(getAdditiveChanges())
			
		Assert.assertFalse(unresolvedChange.isResolved)
		for (change : unresolvedChange.atomicChanges) {
			val c = change as InsertEAttributeValue<Root, Integer>
			Assert.assertTrue(c.affectedEObject != defaultAffectedEObject)
		}
		
		// Resolve
		val resolvedChange = unresolvedChange.copyAndResolveBefore(resourceSet1) 
			as CompoundAddition<Integer, InsertEAttributeValue<Root, Integer>>
			
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange != resolvedChange)	
		for (change : resolvedChange.atomicChanges) {
			val c = change as InsertEAttributeValue<Root, Integer>
			Assert.assertTrue(c.affectedEObject == defaultAffectedEObject)
		}
					
		// Resolving applies all changes and reverts them, so the model should be unaffected.	
		Assert.assertEquals(attributeContent.size, oldSize)
	}
	
	/**
	 * Resolves a {@link CompoundAddition} EChange. The model is in the state
	 * after the change, so the multi valued attribute is filled with values.
	 */
	@Test
	def public void resolveCompoundAdditionTest2() {
		// Set state before change
		attributeContent.clear
		Assert.assertTrue(attributeContent.empty)
		
		// Create change
		val unresolvedChange = TypeInferringCompoundEChangeFactory.<Integer, InsertEAttributeValue<Root, Integer>>
			createCompoundAdditionChange(getAdditiveChanges())
			
		// Set state after change
		prepareAttribute
		val oldSize = attributeContent.size
		
		Assert.assertFalse(unresolvedChange.isResolved)
		for (change : unresolvedChange.atomicChanges) {
			val c = change as InsertEAttributeValue<Root, Integer>
			Assert.assertTrue(c.affectedEObject != defaultAffectedEObject)
		}
		
		// Resolve
		val resolvedChange = unresolvedChange.copyAndResolveAfter(resourceSet1) 
			as CompoundAddition<Integer, InsertEAttributeValue<Root, Integer>>
			
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange != resolvedChange)	
		for (change : resolvedChange.atomicChanges) {
			val c = change as InsertEAttributeValue<Root, Integer>
			Assert.assertTrue(c.affectedEObject == defaultAffectedEObject)
		}
					
		// Resolving applies all changes and reverts them, so the model should be unaffected.	
		Assert.assertEquals(attributeContent.size, oldSize)				
	}
	
	/**
	 * Tests whether resolving the {@link CompoundAddition} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		// Create change
		val unresolvedChange = TypeInferringCompoundEChangeFactory.<Integer, InsertEAttributeValue<Root, Integer>>
			createCompoundAdditionChange(getAdditiveChanges())	
			
		// Resolve
		val resolvedChange = unresolvedChange.copyAndResolveBefore(resourceSet1) 
			as CompoundAddition<Integer, InsertEAttributeValue<Root, Integer>>	
			
		assertDifferentChangeSameClass(unresolvedChange, resolvedChange)
	}
	
	/**
	 * Tests the {@link CompoundAddition} EChange by applying it forward.
	 */
	@Test
	def public void compoundAdditionApplyForwardTest() {
		// Set state before
		Assert.assertTrue(attributeContent.empty)
		val oldSize = attributeContent.size
		
		// Create change
		val resolvedChange = TypeInferringCompoundEChangeFactory.<Integer, InsertEAttributeValue<Root, Integer>>
			createCompoundAdditionChange(getAdditiveChanges()).copyAndResolveBefore(resourceSet1)
		
		// Apply forward
		Assert.assertTrue(resolvedChange.applyForward)
		
		Assert.assertEquals(attributeContent.size, oldSize + 3)
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX), DEFAULT_NEW_VALUE)
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX + 1), DEFAULT_NEW_VALUE_2)
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX + 2), DEFAULT_NEW_VALUE_3)
	}
	
	/**
	 * Test the {@link CompoundAddition} EChange by applying it backward.
	 */
	@Test
	def public void compoundAdditionApplyBackwardTest() {
		// Set state before
		Assert.assertTrue(attributeContent.empty)
		
		// Create change
		val unresolvedChange = TypeInferringCompoundEChangeFactory.<Integer, InsertEAttributeValue<Root, Integer>>
			createCompoundAdditionChange(getAdditiveChanges())
			
		// Set state after
		prepareAttribute
		val oldSize = attributeContent.size
		
		// Resolve
		val resolvedChange = unresolvedChange.copyAndResolveAfter(resourceSet1)
		
		// Apply backward
		Assert.assertTrue(resolvedChange.applyBackward)
		Assert.assertEquals(attributeContent.size, oldSize - 3)
	}
}