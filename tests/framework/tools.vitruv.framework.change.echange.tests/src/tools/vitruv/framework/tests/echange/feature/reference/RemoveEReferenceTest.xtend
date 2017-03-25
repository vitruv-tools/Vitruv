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
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference

/**
 * Test class for the concrete {@link RemoveEReference} EChange, 
 * which removes a reference from a multivalued attribute.
 */
public class RemoveEReferenceTest extends ReferenceEChangeTest {
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
	 * Test resolves a {@link RemoveEReference} EChange with correct parameters.
	 * The model is in state before the change was applied forward.
	 * The resource that will be removed is in a non containment reference,
	 * so the object needs to be in the resource.
	 */
	@Test
	def public void resolveRemoveEReferenceTest() {
		// Set state before
		isNonContainmentTest
			
		// Create change
		val unresolvedChange = createUnresolvedChange(newValue)
		unresolvedChange.assertIsNotResolved(affectedEObject, newValue)
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(resourceSet) 
			as RemoveEReference<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, newValue)
	}
	
	/**
	 * Test resolves a {@link RemoveEReference} EChange with correct parameters.
	 * The model is in state before the change was applied forward.
	 * The resource that will be removed is in a containment reference,
	 * so the object is not in the resource.
	 */
	@Test
	def public void resolveRemoveEReferenceTest2() {
		// Set state before
		isContainmentTest
		
		// Create change
		val unresolvedChange = createUnresolvedChange(newValue)
		unresolvedChange.assertIsNotResolved(affectedEObject, newValue)
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(resourceSet) 
			as RemoveEReference<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, newValue)
	}
	
	/**
	 * Test resolves a {@link RemoveEReference} EChange with correct parameters.
	 * The model is in state after the change was applied forward.
	 * The value that was removed was in a non containment reference,
	 * so the object is a root object in the resource.
	 */
	@Test
	def public void resolveRemoveEReferenceTest3() {
		// Set state before
		isNonContainmentTest
		
		// Create change
		val unresolvedChange = createUnresolvedChange(newValue)
		unresolvedChange.assertIsNotResolved(affectedEObject, newValue)
		
		// Set state after
		prepareReferenceAfter
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(resourceSet) 
			as RemoveEReference<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, newValue)			
	}
	
	/**
	 * Test resolves a {@link RemoveEReference} EChange with correct parameters.
	 * The model is in the state after the change was applied forward.
	 * The value that was removed was in a containment reference,
	 * so the object is in the staging area.
	 */
	@Test
	def public void resolveRemoveEReferenceTest4() {
		// Set state before
		isContainmentTest
		
		// Create change
		val unresolvedChange = createUnresolvedChange(newValue)
		unresolvedChange.assertIsNotResolved(affectedEObject, newValue)
		
		// Set state after
		prepareStagingArea(newValue)
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(resourceSet) 
			as RemoveEReference<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, newValue)									
	}
	
	/**
	 * Tests whether resolving the {@link RemoveEReference} EChange
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
	 * Tests applying the {@link RemoveEReference} EChange forward by
	 * removing inserted values from a multivalued reference. 
	 * The reference is a non containment reference, so the values has
	 * to be a root object in the resource.
	 */
	@Test
	def public void removeEReferenceApplyForwardTest() {
		// Set state before
		isNonContainmentTest
		val oldSize = referenceContent.size
		
		// Create change (resolved)
		val resolvedChange = createUnresolvedChange(newValue).resolveBefore(resourceSet)
		
	 	// Apply forward
	 	Assert.assertTrue(resolvedChange.applyForward)
			
		Assert.assertEquals(referenceContent.size, oldSize - 1)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == newValue2)
		
		// Create change 2 (resolved)
		val resolvedChange2 = createUnresolvedChange(newValue2).resolveBefore(resourceSet)
			
		// Apply forward 2
		Assert.assertTrue(resolvedChange2.applyForward)
		
		Assert.assertEquals(referenceContent.size, oldSize - 2)
	}
	
	/**
	 * Tests applying the {@link RemoveEReference} EChange forward by 
	 * removing inserted values from a multivalued reference.
	 * The reference is a containment reference, so the values
	 * will be in the staging area after removing them.
	 */
	@Test
	def public void removeEReferenceApplyForwardTest2() {
		// Set state before
		isContainmentTest
		
		val oldSize = referenceContent.size
		
		// Create change (resolved)
		val resolvedChange = createUnresolvedChange(newValue).resolveBefore(resourceSet)
		
	 	// Apply forward
	 	Assert.assertTrue(resolvedChange.applyForward)
			
		Assert.assertEquals(referenceContent.size, oldSize - 1)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == newValue2)
		Assert.assertTrue(stagingArea.contents.contains(newValue))		
		// Now another change would delete the element in the staging area (or reinsert)
		stagingArea.contents.clear
		
		// Create change 2 (resolved)
		val resolvedChange2 = createUnresolvedChange(newValue2).resolveBefore(resourceSet)
			
		// Apply forward 2
		Assert.assertTrue(resolvedChange2.applyForward)
		
		Assert.assertEquals(referenceContent.size, oldSize - 2)
		Assert.assertTrue(stagingArea.contents.contains(newValue2))
	}
	
	/**
	 * Tests applying a {@link RemoveEReference} EChange backward. The reference is
	 * a non containment reference so the values has to be in the resource.
	 */
	@Test
	def public void removeEReferenceApplyBackwardTest() {
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
		Assert.assertFalse(referenceContent.contains(newValue))
		Assert.assertFalse(referenceContent.contains(newValue2))
		
		// apply backward 2
		Assert.assertTrue(resolvedChange2.applyBackward)
		Assert.assertEquals(referenceContent.size, oldSize + 1)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == newValue2)
		
		// apply backward 1
		Assert.assertTrue(resolvedChange.applyBackward)
		Assert.assertEquals(referenceContent.size, oldSize + 2)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == newValue)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX + 1) == newValue2)
	}
	
	/**
	 * Tests applying a {@link RemoveEReference} EChange backward. The reference is
	 * a containment reference so the values has to be in the staging area
	 * before they are reinserted.
	 */
	@Test
	def public void removeEReferenceApplyBackwardTest2() {
		// Set state before
		isContainmentTest
		
		// Create change and apply forward
		val resolvedChange = createUnresolvedChange(newValue).resolveBefore(resourceSet)
	 	Assert.assertTrue(resolvedChange.applyForward)
		stagingArea.contents.clear
		
	 	// Create change 2 and apply forward			
		val resolvedChange2 = createUnresolvedChange(newValue2).resolveBefore(resourceSet)
	 	Assert.assertTrue(resolvedChange2.applyForward)

		// State after
		val oldSize = referenceContent.size	
		Assert.assertFalse(referenceContent.contains(newValue))
		Assert.assertFalse(referenceContent.contains(newValue2))
		Assert.assertTrue(stagingArea.contents.contains(newValue2))
		
		// apply backward 2
		Assert.assertTrue(resolvedChange2.applyBackward)
		
		Assert.assertEquals(referenceContent.size, oldSize + 1)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == newValue2)
		Assert.assertTrue(stagingArea.contents.empty)
		
		// Now another change would fill the staging area for the next object
		prepareStagingArea(newValue)
		
		// apply backward 1
		Assert.assertTrue(resolvedChange.applyBackward)
		
		Assert.assertEquals(referenceContent.size, oldSize + 2)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == newValue)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX + 1) == newValue2)	
		Assert.assertTrue(stagingArea.contents.empty)	
	}
	
	/**
	 * Tests a {@link RemoveEReference} EChange with invalid index.
	 */
	@Test
	def public void removeEReferenceInvalidIndexTest() {
		// Set state before
		isNonContainmentTest
		index = 5 // Valid index is 0 or 1
		Assert.assertEquals(referenceContent.size, 2)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == newValue)
		
		// Create and resolve
		val resolvedChange = createUnresolvedChange(newValue).resolveBefore(resourceSet)
			as RemoveEReference<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, newValue)
		
		// Apply
		Assert.assertFalse(resolvedChange.applyForward)
		Assert.assertFalse(resolvedChange.applyBackward)
	}
	
	/**
	 * Tests a {@link RemoveEReference} EChange with with an affected object which has no 
	 * such reference.
	 */
	@Test
	def public void removeEReferenceInvalidAttribute() {
		// Set state before
		isNonContainmentTest
		val invalidAffectedEObject = newValue2 // NonRoot element
		
		val resolvedChange = atomicFactory.<NonRoot, NonRoot>createRemoveReferenceChange
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
		prepareReference
	}

	/**
	 * Starts a test with a non containment feature and sets state before.
	 */	
	def private void isNonContainmentTest() {
		affectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE	
		referenceContent = affectedEObject.eGet(affectedFeature) as EList<NonRoot>
		prepareReference
		prepareResource	
	}
	
	/**
	 * Prepares the multivalued reference used in the tests 
	 * and fills it with the new values.
	 */
	def private void prepareReference() {
		referenceContent.add(newValue)
		referenceContent.add(newValue2)
	}
	
	/**
	 * Prepares the multivalued reference after the change
	 * was applied forward.
	 */
	def private void prepareReferenceAfter() {
		referenceContent.clear
	}

	/**
	 * Change is not resolved.
	 */
	def private static void assertIsNotResolved(RemoveEReference<Root, NonRoot> change, 
		Root affectedEObject, NonRoot newValue) {
		Assert.assertFalse(change.isResolved)
		Assert.assertTrue(change.affectedEObject != affectedEObject)
		Assert.assertTrue(change.oldValue != newValue)			
	}

	/**
	 * Change is resolved.
	 */
	def private static void assertIsResolved(RemoveEReference<Root, NonRoot> change, 
		Root affectedEObject, NonRoot newValue) {
		Assert.assertTrue(change.isResolved)
		Assert.assertTrue(change.affectedEObject == affectedEObject)
		Assert.assertTrue(change.oldValue == newValue)			
	}
	
	/**
	 * Creates new unresolved change.
	 */
	def private RemoveEReference<Root, NonRoot> createUnresolvedChange(NonRoot oldValue) {
		return atomicFactory.<Root, NonRoot>createRemoveReferenceChange
		(affectedEObject, affectedFeature, oldValue, index)	
	}
}