package tools.vitruv.framework.tests.echange.feature.attribute

import allElementTypes.AllElementTypesFactory
import allElementTypes.NonRoot
import allElementTypes.Root
import org.junit.Assert
import org.junit.Test
import tools.vitruv.framework.change.echange.feature.attribute.RemoveEAttributeValue

/**
 * Test class for the concrete {@link RemoveEAttributeValue} EChange,
 * which removes a value in a multivalued attribute.
 */
public class RemoveEAttributeValueTest extends InsertRemoveEAttributeTest {	
	/**
	 * Tests whether resolving the {@link RemoveEAttributeValue} EChange
	 * returns the same class. 
	 */
	@Test
	def public void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange(NEW_VALUE)
		
		// Resolve		
 		val resolvedChange = unresolvedChange.resolveBefore(resourceSet)
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}
	
	/**
	 * Tests applying the {@link RemoveEAttributeValue} EChange forward
	 * by removing two values from an multivalued attribute.
	 */
	@Test
	def public void removeEAttributeValueApplyForwardTest() {
		// Set state before
		prepareAttribute
		val  oldSize = attributeContent.size
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX), NEW_VALUE)
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX + 1), NEW_VALUE_2)
		
		// Create change and resolve
		val resolvedChange = createUnresolvedChange(NEW_VALUE).resolveBefore(resourceSet)
			as RemoveEAttributeValue<Root, String>
			
		// Apply forward
		Assert.assertTrue(resolvedChange.applyForward)
			
		Assert.assertEquals(attributeContent.size, oldSize - 1)
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX), NEW_VALUE_2)
		
		// Create change and resolve 2
		val resolvedChange2 = createUnresolvedChange(NEW_VALUE_2).resolveBefore(resourceSet)
			as RemoveEAttributeValue<Root, String>
		
		// Apply forward 2	
		Assert.assertTrue(resolvedChange2.applyForward)
		
		Assert.assertEquals(attributeContent.size, oldSize - 2)
	}
	
	/**
	 * Tests applying the {@link RemoveEAttributeValue} EChange backward
	 * by inserting two removed values from an multivalued attribute.
	 */
	@Test
	def public void removeEAttributeValueApplyBackwardTest() {
		// Set state before
		prepareAttribute
		
		// Create change and resolve and apply
		val resolvedChange = createUnresolvedChange(NEW_VALUE).resolveBefore(resourceSet)
			as RemoveEAttributeValue<Root, String>
		Assert.assertTrue(resolvedChange.applyForward)
		
		// Create change and resolve and apply 2
		val resolvedChange2 = createUnresolvedChange(NEW_VALUE_2).resolveBefore(resourceSet)
			as RemoveEAttributeValue<Root, String>		
		Assert.assertTrue(resolvedChange2.applyForward)	
		
		// State after	
		val oldSize = attributeContent.size
		
		// Apply backward 2
		Assert.assertTrue(resolvedChange2.applyBackward)
		
		Assert.assertEquals(attributeContent.size, oldSize + 1)
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX), NEW_VALUE_2)
				
		// Apply backward 1
		Assert.assertTrue(resolvedChange.applyBackward)	
				
		Assert.assertEquals(attributeContent.size, oldSize + 2)
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX), NEW_VALUE)
		Assert.assertEquals(attributeContent.get(DEFAULT_INDEX + 1), NEW_VALUE_2)
	}
	
	/**
	 * Tests a {@link RemoveEAttributeValue} EChange with invalid index.
	 */
	@Test
	def public void removeEAttributeValueInvalidIndexTest() {
		// Set state before
		prepareAttribute
		index = 5 // > 2
		Assert.assertEquals(attributeContent.size, 2)
		
		// Create change and resolve
		val resolvedChange = createUnresolvedChange(NEW_VALUE).resolveBefore(resourceSet)
			as RemoveEAttributeValue<Root, String>	
		Assert.assertTrue(resolvedChange.isResolved)
	
		// Apply
		Assert.assertFalse(resolvedChange.applyForward)
		Assert.assertFalse(resolvedChange.applyBackward)
	}
	
	/**
	 * Tests an affected object which has no such attribute.
	 */
	@Test
	def public void removeEAttributeValueInvalidAttribute() {
		prepareAttribute
		val affectedNonRootEObject = AllElementTypesFactory.eINSTANCE.createNonRoot()
	 	resource.contents.add(affectedNonRootEObject)
		
		// Create change and resolve
		val resolvedChange = atomicFactory.<NonRoot, Integer>createRemoveAttributeChange
			(affectedNonRootEObject, affectedFeature, DEFAULT_INDEX, NEW_VALUE).
			resolveBefore(resourceSet)
		Assert.assertTrue(resolvedChange.isResolved)	
			
		// NonRoot has no such feature
	 	Assert.assertEquals(affectedNonRootEObject.eClass.getFeatureID(affectedFeature), -1)	
	 	
	 	Assert.assertFalse(resolvedChange.applyForward)
	 	Assert.assertFalse(resolvedChange.applyBackward)
	}
	
	/**
	 * Tests a {@link RemoveEAttributeValue} EChange with the wrong value type.
	 */
	@Test
	def public void removeEAttributeValueInvalidValue() {
		prepareAttribute
		val newInvalidValue = "New String Value" // values are Strings, attribute value type is Integer
		
		// Create change and resolve
		val resolvedChange = atomicFactory.<Root, String>createRemoveAttributeChange
			(affectedEObject, affectedFeature, DEFAULT_INDEX, newInvalidValue).
			resolveBefore(resourceSet)
		Assert.assertTrue(resolvedChange.isResolved)
		
		// Type of attribute is Integer not String
	 	Assert.assertEquals(affectedFeature.EAttributeType.name, "EIntegerObject")
	 	
	 	Assert.assertFalse(resolvedChange.applyForward)	 	
	 	Assert.assertFalse(resolvedChange.applyBackward)
	}	
	
	/**
	 * Prepares the affected attribute for the tests.
	 */
	def private void prepareAttribute() {
		attributeContent.add(NEW_VALUE)
		attributeContent.add(NEW_VALUE_2)
	}
	
	/**
	 * Creates new unresolved change.
	 */
	def private RemoveEAttributeValue<Root, Integer> createUnresolvedChange(int newValue) {
		// The concrete change type ReplaceSingleEAttributeChange will be used for the tests.
		return atomicFactory.<Root, Integer>createRemoveAttributeChange
		(affectedEObject, affectedFeature, index, newValue)	
	}
}