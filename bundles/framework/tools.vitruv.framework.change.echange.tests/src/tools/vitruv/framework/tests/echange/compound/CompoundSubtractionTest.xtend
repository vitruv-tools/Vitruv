package tools.vitruv.framework.tests.echange.compound

import allElementTypes.Root
import java.util.ArrayList
import java.util.List
import org.eclipse.emf.common.util.EList
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory
import tools.vitruv.framework.change.echange.compound.CompoundSubtraction
import tools.vitruv.framework.change.echange.feature.attribute.RemoveEAttributeValue
import tools.vitruv.framework.tests.echange.feature.attribute.InsertRemoveEAttributeTest
import org.junit.Assert
import tools.vitruv.framework.change.echange.TypeInferringCompoundEChangeFactory

/**
 * Test class for the concrete {@link CompoundSubtraction} EChange,
 * which removes several attributes or references from a multi valued feature.
 */
class CompoundSubtractionTest extends InsertRemoveEAttributeTest {
	protected var EList<Integer> attributeContent = null
	
	protected static val Integer DEFAULT_INDEX = 0
	
	/**
	 * Calls setup of super class and sets list of the affected feature
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
	def private List<RemoveEAttributeValue<Root, Integer>> getSubtractiveChanges() {
		var List<RemoveEAttributeValue<Root, Integer>> changes = new ArrayList<RemoveEAttributeValue<Root, Integer>>()
		changes.add(TypeInferringAtomicEChangeFactory.<Root, Integer>createRemoveAttributeChange(
			defaultAffectedEObject, defaultAffectedFeature, DEFAULT_INDEX + 2, DEFAULT_NEW_VALUE_3, true))
		changes.add(TypeInferringAtomicEChangeFactory.<Root, Integer>createRemoveAttributeChange(
			defaultAffectedEObject, defaultAffectedFeature, DEFAULT_INDEX + 1, DEFAULT_NEW_VALUE_2, true))
		changes.add(TypeInferringAtomicEChangeFactory.<Root, Integer>createRemoveAttributeChange(
			defaultAffectedEObject, defaultAffectedFeature, DEFAULT_INDEX, DEFAULT_NEW_VALUE, true))		
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
	 * Resolves a {@link CompoundSubtraction} EChange. The model is in the state
	 * before the change, so the multi valued attribute is filled with values.
	 */
	@Test
	def public void resolveCompoundSubtractionTest() {
		// Set state before change
		prepareAttribute
		val oldSize = attributeContent.size
		Assert.assertFalse(attributeContent.empty)
		
		// Create change
		val unresolvedChange = TypeInferringCompoundEChangeFactory.<Integer, RemoveEAttributeValue<Root, Integer>>
			createCompoundSubtractionChange(getSubtractiveChanges())
			
		Assert.assertFalse(unresolvedChange.isResolved)
		for (change : unresolvedChange.atomicChanges) {
			val c = change as RemoveEAttributeValue<Root, Integer>
			Assert.assertTrue(c.affectedEObject != defaultAffectedEObject)
		}
		
		// Resolve
		val resolvedChange = unresolvedChange.copyAndResolveBefore(resourceSet1) 
			as CompoundSubtraction<Integer, RemoveEAttributeValue<Root, Integer>>
			
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange != resolvedChange)
		for (change : resolvedChange.atomicChanges) {
			val c = change as RemoveEAttributeValue<Root, Integer>
			Assert.assertTrue(c.affectedEObject == defaultAffectedEObject)
		}	

		// Resolving applies all changes and reverts them, so the model should be unaffected.	
		Assert.assertEquals(attributeContent.size, oldSize)
	}
	
	/**
	 * Resolves a {@link CompoundSubtraction} EChange. The model is in the state
	 * after the change, so the multi valued attribute is empty.
	 */
	@Test
	def public void resolveCompoundSubtractionTest2() {
		// Set state before change
		prepareAttribute
		Assert.assertFalse(attributeContent.empty)
		
		// Create change
		val unresolvedChange = TypeInferringCompoundEChangeFactory.<Integer, RemoveEAttributeValue<Root, Integer>>
			createCompoundSubtractionChange(getSubtractiveChanges())	
			
		// Set state after change
		attributeContent.clear	
		val oldSize = attributeContent.size
		Assert.assertTrue(attributeContent.empty)
		
		Assert.assertFalse(unresolvedChange.isResolved)
		for (change : unresolvedChange.atomicChanges) {
			val c = change as RemoveEAttributeValue<Root, Integer>
			Assert.assertTrue(c.affectedEObject != defaultAffectedEObject)
		}
		
		// Resolve
		val resolvedChange = unresolvedChange.copyAndResolveAfter(resourceSet1) 
			as CompoundSubtraction<Integer, RemoveEAttributeValue<Root, Integer>>
			
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange != resolvedChange)
		for (change : resolvedChange.atomicChanges) {
			val c = change as RemoveEAttributeValue<Root, Integer>
			Assert.assertTrue(c.affectedEObject == defaultAffectedEObject)
		}	

		// Resolving applies all changes and reverts them, so the model should be unaffected.	
		Assert.assertEquals(attributeContent.size, oldSize)
	} 
	
	/**
	 * Tests whether resolving the {@link CompoundSubtraction} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		// Set state before change
		prepareAttribute
		
		// Create change
		val unresolvedChange = TypeInferringCompoundEChangeFactory.<Integer, RemoveEAttributeValue<Root, Integer>>
			createCompoundSubtractionChange(getSubtractiveChanges())	
			
		// Resolve
		val resolvedChange = unresolvedChange.copyAndResolveBefore(resourceSet1) 
			as CompoundSubtraction<Integer, RemoveEAttributeValue<Root, Integer>>
			
		assertDifferentChangeSameClass(unresolvedChange, resolvedChange)					
	}
	
	/**
	 * Tests the {@link CompoundSubtraction} EChange by applying it forward.
	 */
	@Test
	def public void compoundSubtractionApplyForwardTest() {
		// Set state before
		prepareAttribute
		val oldSize = attributeContent.size
		
		// Create change
		val resolvedChange = TypeInferringCompoundEChangeFactory.<Integer, RemoveEAttributeValue<Root, Integer>>
			createCompoundSubtractionChange(getSubtractiveChanges()).copyAndResolveBefore(resourceSet1)
			
		// Apply forward
		Assert.assertTrue(resolvedChange.applyForward)
		Assert.assertEquals(attributeContent.size, oldSize - 3)
	}
	
	/**
	 * Tests the {@link CompoundSubtraction} EChange by applying it backward.
	 */
	@Test
	def public void compoundSubtractionApplyBackwardTest() {
		// Set state before
		prepareAttribute	
		
		// Create change
		val unresolvedChange = TypeInferringCompoundEChangeFactory.<Integer, RemoveEAttributeValue<Root, Integer>>
			createCompoundSubtractionChange(getSubtractiveChanges())
			
		// Set state after change
		attributeContent.clear	
		val oldSize = attributeContent.size
		Assert.assertTrue(attributeContent.empty)
		
		// Resolve
		val resolvedChange = unresolvedChange.copyAndResolveAfter(resourceSet1) 
			as CompoundSubtraction<Integer, RemoveEAttributeValue<Root, Integer>>
			
		// Apply backward
		Assert.assertTrue(resolvedChange.applyBackward)
		
		Assert.assertEquals(attributeContent.size, oldSize + 3)
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX), DEFAULT_NEW_VALUE)
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX + 1), DEFAULT_NEW_VALUE_2)
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX + 2), DEFAULT_NEW_VALUE_3)						
	}
}