package tools.vitruv.framework.tests.echange.feature.attribute

import allElementTypes.AllElementTypesFactory
import allElementTypes.NonRoot
import allElementTypes.Root
import org.junit.Assert
import org.junit.Test
import org.junit.Before
import tools.vitruv.framework.change.echange.feature.attribute.InsertEAttributeValue

import static extension tools.vitruv.framework.tests.echange.util.EChangeAssertHelper.*
import static extension tools.vitruv.framework.change.echange.resolve.EChangeResolverAndApplicator.*

/**
 * Test class for the concrete {@link InsertEAttributeValue} EChange,
 * which inserts a value in a multi valued attribute.
 */
class InsertEAttributeValueTest extends InsertRemoveEAttributeTest {
	@Before
	override void beforeTest() {
		super.beforeTest
		assertIsStateBefore
	}
	/**
	 * Tests whether resolving the {@link InsertEAttributeValue} EChange returns the same class.
	 */
	@Test
	def void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange(NEW_VALUE, 0)
				
		// Resolve		
 		val resolvedChange = unresolvedChange.resolveBefore(uuidGeneratorAndResolver)
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}
	 
	/**
	 * Tests applying two {@link InsertEAttributeValue} EChanges forward by 
	 * inserting new values in a multivalued attribute.
	 */
	@Test
	def void applyForwardTest() {
		// Create change and resolve
		val resolvedChange = createUnresolvedChange(NEW_VALUE, 0).resolveBefore(uuidGeneratorAndResolver)
			as InsertEAttributeValue<Root, Integer>
	 	
	 	// Apply forward
	 	resolvedChange.assertApplyForward
	 	
	 	Assert.assertEquals(attributeContent.size, 1)
	 	Assert.assertEquals(attributeContent.get(0), NEW_VALUE)

		// Create change and resolve 2
		val resolvedChange2 = createUnresolvedChange(NEW_VALUE_2, 1).resolveBefore(uuidGeneratorAndResolver)
			as InsertEAttributeValue<Root, Integer>
	 	
	 	// Apply forward 2
	 	resolvedChange2.assertApplyForward
	 	
	 	// State after
	 	assertIsStateAfter
	}
	 
	/**
	 * Applies two {@link InsertEAttributeValue} EChanges backward.
	 */
	@Test
	def void applyBackwardTest() {
		// Create change and resolve and apply forward
		val resolvedChange = createUnresolvedChange(NEW_VALUE, 0).resolveBefore(uuidGeneratorAndResolver)
			as InsertEAttributeValue<Root, Integer>
	 	resolvedChange.assertApplyForward
	 	
		// Create change and resolve and apply forward 2
		val resolvedChange2 = createUnresolvedChange(NEW_VALUE_2, 1).resolveBefore(uuidGeneratorAndResolver)
			as InsertEAttributeValue<Root, Integer>
	 	resolvedChange2.assertApplyForward			
					
	 	// State after
	 	assertIsStateAfter
	 		 	
	 	// Apply backward 2
	 	resolvedChange2.assertApplyBackward	
	 	
		Assert.assertEquals(attributeContent.size, 1)
	 	Assert.assertEquals(attributeContent.get(0), NEW_VALUE)
	 	 	
		// Apply backward 1
		resolvedChange.assertApplyBackward 
	 	
		// State before
		assertIsStateBefore	 	
	}
	 
	/**
	 * Tests the {@link InsertEAttributeValue} EChange with invalid index.
	 */
	@Test(expected=RuntimeException)
	def void invalidIndexTest() {
	 	var index = 5 // Valid index in empty list is only 0
	 	Assert.assertTrue(attributeContent.empty) 
	 	
		// Create change and resolve
		val resolvedChange = createUnresolvedChange(NEW_VALUE, index).resolveBefore(uuidGeneratorAndResolver)
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
	def void invalidAttributeTest() {
	 	// NonRoot has no multivalued int attribute
	 	val affectedNonRootEObject = AllElementTypesFactory.eINSTANCE.createNonRoot()
	 	resource.contents.add(affectedNonRootEObject)
	 	
	 	// Resolving the change will be tested in EFeatureChange
	 	val resolvedChange = atomicFactory.<NonRoot, Integer>createInsertAttributeChange
	 		(affectedNonRootEObject, affectedFeature, 0, NEW_VALUE).
	 		resolveBefore(uuidGeneratorAndResolver)
	 	
	 	// NonRoot has no such feature
		Assert.assertEquals(affectedNonRootEObject.eClass.getFeatureID(affectedFeature), -1)	
	 	
	 	resolvedChange.assertCannotBeAppliedForward	 	
		resolvedChange.assertCannotBeAppliedBackward
	}
	 
	/**
	 * Tests an {@link InsertEAttributeValue} EChange with the wrong value type.
	 */
	@Test
	def void invalidValueTest() {
	 	val newInvalidValue = "New String Value" // values are String, attribute value type is Integer
	 	
	 	// Resolving the change will be tested in EFeatureChange
	 	val resolvedChange = atomicFactory.createInsertAttributeChange
	 		(affectedEObject, affectedFeature, 0, newInvalidValue).
	 		resolveBefore(uuidGeneratorAndResolver)
	 		
	 	// Type of attribute is Integer not String
	 	Assert.assertEquals(affectedFeature.EAttributeType.name, "EIntegerObject")
	 	
	 	resolvedChange.assertCannotBeAppliedForward	 	
		resolvedChange.assertCannotBeAppliedBackward
	}
	
	/**
	 * Model is in state before the changes.
	 */
	def private void assertIsStateBefore() {
		Assert.assertEquals(attributeContent.size, 0)
	}
	
	/**
	 * Model is in state after the changes.
	 */
	def private void assertIsStateAfter() {
		Assert.assertEquals(attributeContent.size, 2)
		Assert.assertEquals(attributeContent.get(0), NEW_VALUE)
		Assert.assertEquals(attributeContent.get(1), NEW_VALUE_2)
	}
	 
	/**
	 * Creates new unresolved change.
	 */
	def private InsertEAttributeValue<Root, Integer> createUnresolvedChange(int newValue, int index) {
		// The concrete change type ReplaceSingleEAttributeChange will be used for the tests.
		return atomicFactory.createInsertAttributeChange(affectedEObject, affectedFeature, index, newValue)	
	}
}