package tools.vitruv.framework.tests.echange.feature.attribute

import allElementTypes.AllElementTypesFactory
import allElementTypes.NonRoot
import allElementTypes.Root
import org.junit.Assert
import org.junit.Test
import tools.vitruv.framework.change.echange.feature.attribute.InsertEAttributeValue

/**
 * Test class for the concrete {@link InsertEAttributeValue} EChange,
 * which inserts a value in a multivalued attribute.
 */
public class InsertEAttributeValueTest extends InsertRemoveEAttributeTest {
	/**
	 * Tests whether resolving the {@link InsertEAttributeValue} EChange returns the same class.
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
	 * Tests applying two {@link InsertEAttributeValue} EChanges forward by 
	 * inserting new values in a multivalued attribute.
	 */
	@Test
	def public void insertEAttributeValueApplyForwardTest() {
		// State before
		val oldSize = attributeContent.size
		
		// Create change and resolve
		val resolvedChange = createUnresolvedChange(NEW_VALUE).resolveBefore(resourceSet)
			as InsertEAttributeValue<Root, Integer>
	 	
	 	// Apply forward
	 	Assert.assertTrue(resolvedChange.applyForward)
	 	
	 	Assert.assertEquals(attributeContent.size, oldSize + 1)
	 	Assert.assertEquals(attributeContent.get(DEFAULT_INDEX), NEW_VALUE)

		// Create change and resolve 2
		val resolvedChange2 = createUnresolvedChange(NEW_VALUE_2).resolveBefore(resourceSet)
			as InsertEAttributeValue<Root, Integer>
	 	
	 	// Apply forward 2
	 	Assert.assertTrue(resolvedChange2.applyForward)
	 	
	 	Assert.assertEquals(attributeContent.size, oldSize + 2)
	 	Assert.assertEquals(attributeContent.get(DEFAULT_INDEX), NEW_VALUE_2)
	 	Assert.assertEquals(attributeContent.get(DEFAULT_INDEX + 1), NEW_VALUE)
	 }
	 
	 /**
	  * Applies two {@link InsertEAttributeValue} EChanges backward.
	  */
	 @Test
	 def public void insertEAttributeValueApplyBackwardTest() {
		// Create change and resolve and apply forward
		val resolvedChange = createUnresolvedChange(NEW_VALUE).resolveBefore(resourceSet)
			as InsertEAttributeValue<Root, Integer>
	 	Assert.assertTrue(resolvedChange.applyForward)
	 	
		// Create change and resolve and apply forward 2
		val resolvedChange2 = createUnresolvedChange(NEW_VALUE_2).resolveBefore(resourceSet)
			as InsertEAttributeValue<Root, Integer>
	 	Assert.assertTrue(resolvedChange2.applyForward)			
					
	 	// State after
	 	val oldSize = attributeContent.size	
	 	Assert.assertEquals(attributeContent.get(DEFAULT_INDEX), NEW_VALUE_2)
	 	Assert.assertEquals(attributeContent.get(DEFAULT_INDEX + 1), NEW_VALUE)
	 		 	
	 	// Apply backward 2
	 	Assert.assertTrue(resolvedChange2.applyBackward)	
	 	
		Assert.assertEquals(attributeContent.size, oldSize - 1)
	 	Assert.assertEquals(attributeContent.get(DEFAULT_INDEX), NEW_VALUE)
	 	 	
	 	// Apply backward 1
	 	Assert.assertTrue(resolvedChange.applyBackward)	 
	 	
	 	Assert.assertEquals(attributeContent.size, oldSize - 2)	 	
	 }
	 
	 /**
	  * Tests the {@link InsertEAttributeValue} EChange with invalid index.
	  */
	 @Test
	 def public void insertEAttributeValueInvalidIndexTest() {
	 	index = 5 // Valid index in empty list is only 0
	 	Assert.assertTrue(attributeContent.empty) 
	 	
		// Create change and resolve
		val resolvedChange = createUnresolvedChange(NEW_VALUE).resolveBefore(resourceSet)
			as InsertEAttributeValue<Root, Integer>	 	
	 	Assert.assertTrue(resolvedChange.isResolved)
	 	
	 	// Apply
	 	Assert.assertFalse(resolvedChange.applyForward)
	 	Assert.assertFalse(resolvedChange.applyBackward)
	 }
	 
	 /**
	  * Tests an {@link InsertEAttributeValue} with an affected object which has no such attribute.
	  */
	 @Test
	 def public void insertEAttributeValueInvalidAttribute() {
	 	// NonRoot has no multivalued int attribute
	 	val affectedNonRootEObject = AllElementTypesFactory.eINSTANCE.createNonRoot()
	 	resource.contents.add(affectedNonRootEObject)
	 	
	 	// Resolving the change will be tested in EFeatureChange
	 	val resolvedChange = atomicFactory.<NonRoot, Integer>createInsertAttributeChange
	 		(affectedNonRootEObject, affectedFeature, DEFAULT_INDEX, NEW_VALUE).
	 		resolveBefore(resourceSet)
	 	
	 	// NonRoot has no such feature
	 	Assert.assertEquals(affectedNonRootEObject.eClass.getFeatureID(affectedFeature), -1)	
	 	
	 	Assert.assertFalse(resolvedChange.applyForward)
	 	Assert.assertFalse(resolvedChange.applyBackward)
	 }
	 
	 /**
	  * Tests an {@link InsertEAttributeValue} EChange with the wrong value type.
	  */
	 @Test
	 def public void insertEAttributeValueInvalidValue() {
	 	val newInvalidValue = "New String Value" // values are String, attribute value type is Integer
	 	
	 	// Resolving the change will be tested in EFeatureChange
	 	val resolvedChange = atomicFactory.<Root, String>createInsertAttributeChange
	 		(affectedEObject, affectedFeature, DEFAULT_INDEX, newInvalidValue).
	 		resolveBefore(resourceSet)
	 		
	 	// Type of attribute is Integer not String
	 	Assert.assertEquals(affectedFeature.EAttributeType.name, "EIntegerObject")
	 	
	 	Assert.assertFalse(resolvedChange.applyForward)	 	
	 	Assert.assertFalse(resolvedChange.applyBackward)
	 }
	 
	/**
	 * Creates new unresolved change.
	 */
	def private InsertEAttributeValue<Root, Integer> createUnresolvedChange(int newValue) {
		// The concrete change type ReplaceSingleEAttributeChange will be used for the tests.
		return atomicFactory.<Root, Integer>createInsertAttributeChange
		(affectedEObject, affectedFeature, index, newValue)	
	}
}