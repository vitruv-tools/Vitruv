package tools.vitruv.framework.tests.echange.feature.reference

import allElementTypes.AllElementTypesPackage
import allElementTypes.NonRoot
import allElementTypes.Root
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference

/**
 * Test class for the concrete {@link InsertEReferenceValue} EChange,
 * which inserts a reference in a multivalued attribute.
 */
public class InsertEReferenceTest extends ReferenceEChangeTest {
	protected var EReference affectedFeature = null
	protected var EList<EObject> resourceContent = null
	protected var EList<NonRoot> referenceContent = null
	protected var int index = DEFAULT_INDEX
	
	protected static val Integer DEFAULT_INDEX = 0
	
	@Before
	override public void beforeTest() {
		super.beforeTest()
		resourceContent = resource.contents
	}
			
	/**
	 * Test resolves a {@link InsertEReference} EChange with correct parameters on
	 * a model which is in state before the change.
	 * The affected feature is a non containment reference, so the new reference
	 * is in the resource already.
	 */
	@Test
	def public void resolveInsertEReferenceTest() {
		// Set state before
		isNonContainmentTest
		
		// Create change
		val unresolvedChange = createUnresolvedChange(newValue)
		unresolvedChange.assertIsNotResolved(affectedEObject, newValue)
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(resourceSet) 
			as InsertEReference<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, newValue)
	}
	
	/**
	 * Test resolves a {@link InsertEReference} EChange with correct parameters on
	 * a model which is in state before the change.
	 * The affected feature is a containment reference, so the new reference 
	 * is in the staging area.
	 */
	@Test
	def public void resolveInsertEReferenceTest2() {
		// Set state before
		isContainmentTest
		
		// Create change
		val unresolvedChange = createUnresolvedChange(newValue)
		unresolvedChange.assertIsNotResolved(affectedEObject, newValue)
			
		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(resourceSet) as InsertEReference<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, newValue)				
	}
	
	/**
	 * Test resolves a {@link InsertEReference} EChange with correct parameters on 
	 * a model which is in state after the change.
	 * The affected feature is a non containment reference, so the inserted
	 * reference is already a root element.
	 */
	@Test
	def public void resolveInsertEReferenceTest3() {
		// Set state before
		isNonContainmentTest
		
		// Create change
		val unresolvedChange = createUnresolvedChange(newValue)
		unresolvedChange.assertIsNotResolved(affectedEObject, newValue)
			
		// Set state after
		prepareReference(newValue)
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(resourceSet) as InsertEReference<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, newValue)			
	}
	
	/**
	 * Test resolves a {@link InsertEReference} EChange with correct parameters on
	 * a model which is in the state after the change.
	 * The affected feature is a containment reference, so the inserted 
	 * reference is in the resource after the change.
	 */
	@Test
	def public void resolveInsertEReferenceTest4() {
		// Set state before
		isContainmentTest
		
		// Create change
		val unresolvedChange = createUnresolvedChange(newValue)
		unresolvedChange.assertIsNotResolved(affectedEObject, newValue)		
		
		// Set state after
		prepareReference(newValue)
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(resourceSet) as InsertEReference<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, newValue)						
	}
	
	/**
	 * Tests whether resolving the {@link InsertEReference} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		// Set state before
		isNonContainmentTest
		
		// Create change
		val unresolvedChange = createUnresolvedChange(newValue)
			
		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(resourceSet)
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}
	
	 /**
	  * Tests applying the {@link InsertEReference} EChange forward by 
	  * inserting new values in a multivalued reference.
	  * The affected feature is a non containment reference, so the 
	  * new value is already in the resource.
	  */
	@Test
	def public void insertEReferenceApplyForwardTest() {
		// Set state before
		isNonContainmentTest
		val oldSize = referenceContent.size
		
		// Create change (resolved)
		val resolvedChange = createUnresolvedChange(newValue).resolveBefore(resourceSet)
	 	
	 	// Apply forward
	 	Assert.assertTrue(resolvedChange.applyForward)
	 	Assert.assertEquals(referenceContent.size, oldSize + 1)
	 	Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == newValue)

	 	// Create change 2 (resolved)
		val resolvedChange2 = createUnresolvedChange(newValue2).resolveBefore(resourceSet)
	 	
	 	// Apply forward 2
	 	Assert.assertTrue(resolvedChange2.applyForward)
	 	
	 	Assert.assertEquals(referenceContent.size, oldSize + 2)
	 	Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == newValue2)
	 	Assert.assertTrue(referenceContent.get(DEFAULT_INDEX + 1) == newValue)
	}
	
	/**
	 * Tests applying the {@link InsertEReference} EChange forward by 
	 * inserting new values from a multivalued reference.
	 * The affected feature is a containment reference, so the
	 * new value is from the staging area.
	 */
	@Test
	def public void insertEReferenceApplyForwardTest2() {
		// Set state before
		isContainmentTest
		val oldSize = referenceContent.size
		
		// Create change (resolved)		
		val resolvedChange = createUnresolvedChange(newValue).resolveBefore(resourceSet)
			
		Assert.assertEquals(stagingArea.contents.size, 1)
		
		// Apply forward
	 	Assert.assertTrue(resolvedChange.applyForward)
	 	
		Assert.assertEquals(stagingArea.contents.size, 0)
		Assert.assertEquals(referenceContent.size, oldSize + 1)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == newValue)
		
		// Prepare and create change 2
		prepareStagingArea(newValue2) // another change would fill the staging area.
		val resolvedChange2 = createUnresolvedChange(newValue2).resolveBefore(resourceSet)
	 	
	 	// Apply forward 2
	 	Assert.assertTrue(resolvedChange2.applyForward)
	 	
		Assert.assertEquals(stagingArea.contents.size, 0)	 	
	 	Assert.assertEquals(referenceContent.size, oldSize + 2)
	 	Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == newValue2)
	 	Assert.assertTrue(referenceContent.get(DEFAULT_INDEX + 1) == newValue)
	}
	
	/**
	 * Tests applying two {@link InsertEReference} EChanges backward by
	 * removing new added values from a multivalued reference.
	 * The affected feature is a non containment reference, so the
	 * removed values are already in the resource.
	 */
	@Test
	def public void insertEReferenceApplyBackwardTest() {
		// Set state before
		isNonContainmentTest
		
		// Create change and apply forward
		val resolvedChange = createUnresolvedChange(newValue).resolveBefore(resourceSet)
	 	Assert.assertTrue(resolvedChange.applyForward)
	 	
	 	// Create change 2 and apply forward			
		val resolvedChange2 = createUnresolvedChange(newValue2).resolveBefore(resourceSet)
	 	Assert.assertTrue(resolvedChange2.applyForward)			
		
		// State after
		val oldSize = referenceContent.size		
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == newValue2)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX + 1) == newValue)	
		
		// Apply backward 2
		Assert.assertTrue(resolvedChange2.applyBackward)
		
		Assert.assertEquals(referenceContent.size, oldSize - 1)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == newValue)
		
		// Apply backward 1
		Assert.assertTrue(resolvedChange.applyBackward)
		
		Assert.assertEquals(referenceContent.size, oldSize - 2)
	}
	
	/**
	 * Tests applying two {@link InsertEReference} EChanges backward by 
	 * removing new added values from a multivalued reference.
	 * The affected feature is a containment reference, so the
	 * removed values will be placed in the staging area after removing them.
	 */
	@Test
	def public void insertEReferenceApplyBackwardTest2() {
		// Set state before
		isContainmentTest

		// Create change and apply forward
		val resolvedChange = createUnresolvedChange(newValue).resolveBefore(resourceSet)
	 	Assert.assertTrue(resolvedChange.applyForward)
	 	
	 	// Create change 2 and apply forward			
		prepareStagingArea(newValue2)	
		val resolvedChange2 = createUnresolvedChange(newValue2).resolveBefore(resourceSet)
	 	Assert.assertTrue(resolvedChange2.applyForward)	
	 	
	 	// State after
	 	val oldSize = referenceContent.size
		Assert.assertEquals(stagingArea.contents.size, 0)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == newValue2)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX + 1) == newValue)	
		
		// Apply backward 2
		Assert.assertTrue(resolvedChange2.applyBackward)
		Assert.assertEquals(stagingArea.contents.size, 1)
		Assert.assertEquals(referenceContent.size, oldSize - 1)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == newValue)		
		// Now another change would delete the element in the staging area (or reinsert)
		stagingArea.contents.clear
		
		// Apply backward 1
		Assert.assertTrue(resolvedChange.applyBackward)
		
		Assert.assertEquals(stagingArea.contents.size, 1)
		Assert.assertEquals(referenceContent.size, oldSize - 2)		
	}
	
	/**
	 * Tests the {@link InsertEReference} EChange with invalid index.
	 */
	@Test
	def public void insertEReferenceInvalidIndexTest() {
		// Set state before
		isNonContainmentTest
		index = 5 // Valid index in empty list is only 0
		Assert.assertEquals(referenceContent.size, 0)
		
		// Create and resolve
		val resolvedChange = createUnresolvedChange(newValue).resolveBefore(resourceSet)
			as InsertEReference<Root, NonRoot>		
		resolvedChange.assertIsResolved(affectedEObject, newValue)	
		
		// Apply	
		Assert.assertFalse(resolvedChange.applyForward)
		Assert.assertFalse(resolvedChange.applyBackward)
	}
	
	/**
	 * Tests an affected object which has no such reference.
	 */
	@Test
	def public void insertEReferenceInvalidAttribute() {
		// Set state before
		isNonContainmentTest
		val invalidAffectedEObject = newValue2 // NonRoot element

		// Create and resolve change
		val resolvedChange = atomicFactory.<NonRoot, NonRoot>createInsertReferenceChange
			(invalidAffectedEObject, affectedFeature, newValue, DEFAULT_INDEX).
			resolveBefore(resourceSet)
		Assert.assertTrue(resolvedChange.isResolved)
		
		// NonRoot has no such feature
		Assert.assertTrue(invalidAffectedEObject.eClass.getFeatureID(affectedFeature) == -1)
		
		Assert.assertFalse(resolvedChange.applyForward)
		Assert.assertFalse(resolvedChange.applyBackward)
	}
	
	/**
	 * Starts a test with a containment feature and sets state before.
	 */	
	def private void isContainmentTest() {
		affectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE
		referenceContent = affectedEObject.eGet(affectedFeature) as EList<NonRoot>
		prepareStagingArea(newValue)
	}
	
	/**
	 * Starts a test with a non containment feature and sets state before.
	 */	
	def private void isNonContainmentTest() {
		affectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE	
		referenceContent = affectedEObject.eGet(affectedFeature) as EList<NonRoot>
		prepareResource	
	}
	
	/**
	 * Prepares the multivalued reference used in the tests 
	 * and fills it with a new value.
	 */
	def private void prepareReference(NonRoot object) {
		referenceContent.add(object)
	}
	
	/**
	 * Change is not resolved.
	 */
	def private static void assertIsNotResolved(InsertEReference<Root, NonRoot> change, 
		Root affectedEObject, NonRoot newValue) {
		Assert.assertFalse(change.isResolved)
		Assert.assertTrue(change.affectedEObject != affectedEObject)
		Assert.assertTrue(change.newValue != newValue)			
	}

	/**
	 * Change is resolved.
	 */
	def private static void assertIsResolved(InsertEReference<Root, NonRoot> change, 
		Root affectedEObject, NonRoot newValue) {
		Assert.assertTrue(change.isResolved)
		Assert.assertTrue(change.affectedEObject == affectedEObject)
		Assert.assertTrue(change.newValue == newValue)			
	}

	/**
	 * Creates new unresolved change.
	 */
	def private InsertEReference<Root, NonRoot> createUnresolvedChange(NonRoot newValue) {
		return atomicFactory.<Root, NonRoot>createInsertReferenceChange
		(affectedEObject, affectedFeature, newValue, index)	
	}
}