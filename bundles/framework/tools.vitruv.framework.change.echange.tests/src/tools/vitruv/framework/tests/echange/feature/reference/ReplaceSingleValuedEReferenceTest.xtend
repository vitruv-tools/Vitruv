package tools.vitruv.framework.tests.echange.feature.reference

import allElementTypes.AllElementTypesFactory
import allElementTypes.AllElementTypesPackage
import allElementTypes.NonRoot
import allElementTypes.Root
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference

/**
 * Test class for the concrete {@link ReplaceSingleValuedEReference} EChange,
 * which replaces the value of a reference with a new one.
 */
public class ReplaceSingleValuedEReferenceTest extends ReferenceEChangeTest {	
	protected var NonRoot oldValue = null	
	protected var EReference affectedFeature = null

	@Before
	override public void beforeTest() {
		super.beforeTest()
		oldValue = AllElementTypesFactory.eINSTANCE.createNonRoot()	
	}

	/**
	 * Test resolves a {@link ReplaceSingleValuedEReference} EChange with correct parameters.
	 * The model is in the state before the change.
	 * The reference is a non containment reference so the new object is in
	 * the resource.
	 */
	@Test
	def public void resolveReplaceSingleValuedEReferenceTest() {
		// Set state before
		isNonContainmentTest
		
		// Create change
		val unresolvedChange = createUnresolvedChange()
		unresolvedChange.assertIsNotResolved(affectedEObject, oldValue, newValue)
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(resourceSet) as ReplaceSingleValuedEReference<Root, NonRoot>	
		resolvedChange.assertIsResolved(affectedEObject, oldValue, newValue)
	}
	
	/**
	 * Test resolves a {@link ReplaceSingleValuedEReference} EChange with correct parameters.
	 * The model is in the state before the change.
	 * The reference is a containment reference, so the new object is in the staging area
	 */
	@Test
	def public void resolveReplaceSingleValuedEReferenceTest2() {
		// Set state before
		isContainmentTest
		
		// Create change
		val unresolvedChange = createUnresolvedChange()
		unresolvedChange.assertIsNotResolved(affectedEObject, oldValue, newValue)
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(resourceSet) as ReplaceSingleValuedEReference<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, oldValue, newValue)
	}
	
	/**
	 * Test resolves a {@link ReplaceSingleValuedEReference} EChange with correct parameters.
	 * The model is in the state after the change.
	 * The reference is a non containment reference, so the old value is in the resource.
	 */
	@Test
	def public void resolveReplaceSingleValuedEReferenceTest3() {
		// Set state before
		isNonContainmentTest
		
		// Create change
		val unresolvedChange = createUnresolvedChange()
		unresolvedChange.assertIsNotResolved(affectedEObject, oldValue, newValue)
							
		// Set state after
		prepareReference(newValue)
		
		// Resolve	
		val resolvedChange = unresolvedChange.resolveAfter(resourceSet) as ReplaceSingleValuedEReference<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, oldValue, newValue)	
	}
	
	/**
	 * Test resolves a {@link ReplaceSingleValuedEReference} EChange with correct parameters.
	 * The model is in the state after the change.
	 * The reference is a containment reference, so the old value is in the staging area.
	 */
	 @Test
	 def public void resolveReplaceSingleValuedEReferenceTest4() {
		// Set state before
		isContainmentTest
		
		// Create change
		val unresolvedChange = createUnresolvedChange()
		unresolvedChange.assertIsNotResolved(affectedEObject, oldValue, newValue)
		
		// Set state after
		prepareReference(newValue)
		prepareStagingArea(oldValue)
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(resourceSet) as ReplaceSingleValuedEReference<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, oldValue, newValue)		 	
	 }

	/**
	 * Tests whether resolving the {@link ReplaceSingleValuedEReference} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		// Set state before
		isNonContainmentTest
		
		// Create change
		val unresolvedChange = createUnresolvedChange()
			
		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(resourceSet)
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}
	
	/**
	 * Tests applying the {@link ReplaceSingleValuedEReference} EChange forward 
	 * by replacing a single value non containment reference in the root element.
	 */
	@Test
	def public void replaceSingleValuedEReferenceApplyForwardTest() {
		// Set state before
		isNonContainmentTest
				
		// Create change (resolved)
		val resolvedChange = createUnresolvedChange().resolveBefore(resourceSet)
			as ReplaceSingleValuedEReference<Root, NonRoot>
			
		resolvedChange.assertIsResolved(affectedEObject, oldValue, newValue)
		Assert.assertTrue(oldValue != newValue)
		Assert.assertTrue(affectedEObject.eGet(affectedFeature) == oldValue)
		Assert.assertEquals(stagingArea.contents.size, 0)
		
		// Apply forward
		Assert.assertTrue(resolvedChange.applyForward)
		
		Assert.assertTrue(affectedEObject.eGet(affectedFeature) == newValue)
		Assert.assertEquals(stagingArea.contents.size, 0) // The staging area must be unaffected
	}
	
	/**
	 * Tests applying the {@link ReplaceSingleValuedEReference} EChange forward
	 * by replacing a single value containment reference in the root element.
	 */
	@Test
	def public void replaceSingleValuedEReferenceApplyForwardTest2() {
		// Set state before
		isContainmentTest
		
		// Create change (resolved)
		val resolvedChange = createUnresolvedChange().resolveBefore(resourceSet)
			as ReplaceSingleValuedEReference<Root, NonRoot>
			
		resolvedChange.assertIsResolved(affectedEObject, oldValue, newValue)
		Assert.assertTrue(oldValue != newValue)
		Assert.assertTrue(affectedEObject.eGet(affectedFeature) == oldValue)
		Assert.assertEquals(stagingArea.contents.size, 1)	
		Assert.assertTrue(stagingArea.contents.contains(newValue))
		
		// Apply forward
		Assert.assertTrue(resolvedChange.applyForward)
		
		Assert.assertTrue(affectedEObject.eGet(affectedFeature) == newValue)
		Assert.assertEquals(stagingArea.contents.size, 1) 
		Assert.assertTrue(stagingArea.contents.contains(oldValue))
	}
	
	/** 
	 * Tests applying the {@link ReplaceSingleValuedEReference} EChange forward
	 * by replacing a single value containment reference in the root element.
	 * The new value is null.
	 */
	@Test
	def public void replaceSingleValuedEReferenceApplyForwardNewValueNullTest() {
		// Set state before
		newValue = null
		isContainmentTest
		
		// Create change (resolved)
		val resolvedChange = createUnresolvedChange().resolveBefore(resourceSet)
			as ReplaceSingleValuedEReference<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, oldValue, newValue)
			
		// Apply forward
		Assert.assertTrue(resolvedChange.applyForward)
		
		Assert.assertTrue(affectedEObject.eGet(affectedFeature) == newValue)
		Assert.assertEquals(stagingArea.contents.size, 1) 
		Assert.assertTrue(stagingArea.contents.contains(oldValue))		
	}
	
	/** 
	 * Tests applying the {@link ReplaceSingleValuedEReference} EChange forward
	 * by replacing a single value containment reference in the root element.
	 * The old value is null.
	 */
	@Test
	def public void replaceSingleValuedEReferenceApplyForwardOldValueNullTest() {
		// Set state before
		oldValue = null
		isContainmentTest
		
		// Create change (resolved)
		val resolvedChange = createUnresolvedChange().resolveBefore(resourceSet)
			as ReplaceSingleValuedEReference<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, oldValue, newValue)
			
		// Apply forward
		Assert.assertTrue(resolvedChange.applyForward)
		
		Assert.assertTrue(affectedEObject.eGet(affectedFeature) == newValue)
		Assert.assertEquals(stagingArea.contents.size, 0) // Null isn't added to the staging area	
	}
		
	/**
	 * Tests applying a {@link ReplaceSingleValuedEReference} EChange backward
	 * by replacing a single value non containment reference with its old value.
	 */
	@Test
	def public void replaceSingleValuedEReferenceApplyBackwardTest() {
		// Set state before
		isNonContainmentTest
		
		// Create change (resolved)
		val resolvedChange = createUnresolvedChange().resolveBefore(resourceSet)
			as ReplaceSingleValuedEReference<Root, NonRoot>	
			
		// Set state after
		prepareReference(newValue)
		
		resolvedChange.assertIsResolved(affectedEObject, oldValue, newValue)
		Assert.assertTrue(oldValue != newValue)
		Assert.assertTrue(affectedEObject.eGet(affectedFeature) == newValue)
		Assert.assertEquals(stagingArea.contents.size, 0)
		
		// Apply backward
		Assert.assertTrue(resolvedChange.applyBackward)
		
		Assert.assertTrue(affectedEObject.eGet(affectedFeature) == oldValue)
		Assert.assertEquals(stagingArea.contents.size, 0) // The staging area must be unaffected		
	}
	
	/**
	 * Tests applying a {@link ReplaceSingleValuedEReference} EChange backward
	 * by replacing a single value containment reference with its old value.
	 */
	@Test
	def public void replaceSingleValuedEReferenceApplyBackwardTest2() {
		// Set state before
		isContainmentTest
		
		// Create change (resolved)
		val resolvedChange = createUnresolvedChange().resolveBefore(resourceSet)
			as ReplaceSingleValuedEReference<Root, NonRoot>	
		
		// Set state after
		prepareReference(newValue)
		prepareStagingArea(oldValue)
				
		resolvedChange.assertIsResolved(affectedEObject, oldValue, newValue)
		Assert.assertTrue(oldValue != newValue)	
		Assert.assertTrue(affectedEObject.eGet(affectedFeature) == newValue)
		Assert.assertEquals(stagingArea.contents.size, 1)
		Assert.assertTrue(stagingArea.contents.contains(oldValue))
		
		// Apply backward
		Assert.assertTrue(resolvedChange.applyBackward)
		
		Assert.assertTrue(affectedEObject.eGet(affectedFeature) == oldValue)
		Assert.assertEquals(stagingArea.contents.size, 1)
		Assert.assertTrue(stagingArea.contents.contains(newValue))
	}
	
	/**
	 * Tests applying a {@link ReplaceSingleValuedEReference} EChange backward
	 * by replacing a single value containment reference with its old value.
	 * The new value was null.
	 */
	@Test
	def public void replaceSingleValuedEReferenceApplyBackwardNewValueNullTest() {
		// Set state before
		newValue = null
		isContainmentTest
		
		// Create change (resolved)
		val resolvedChange = createUnresolvedChange().resolveBefore(resourceSet)
			as ReplaceSingleValuedEReference<Root, NonRoot>	
		
		// Set state after
		prepareReference(newValue)
		prepareStagingArea(oldValue)
				
		resolvedChange.assertIsResolved(affectedEObject, oldValue, newValue)
		Assert.assertTrue(oldValue != newValue)	
		Assert.assertTrue(affectedEObject.eGet(affectedFeature) == newValue)
		Assert.assertEquals(stagingArea.contents.size, 1)
		Assert.assertTrue(stagingArea.contents.contains(oldValue))
		
		// Apply backward
		Assert.assertTrue(resolvedChange.applyBackward)
		
		Assert.assertTrue(affectedEObject.eGet(affectedFeature) == oldValue)
		Assert.assertEquals(stagingArea.contents.size, 0) // Null isn't added to the staging area
	}

	/**
	 * Tests applying a {@link ReplaceSingleValuedEReference} EChange backward
	 * by replacing a single value containment reference with its old value.
	 * The old value was null.
	 */
	@Test
	def public void replaceSingleValuedEReferenceApplyBackwardOldValueNullTest() {
		// Set state before
		oldValue = null
		isContainmentTest
		
		// Create change (resolved)
		val resolvedChange = createUnresolvedChange().resolveBefore(resourceSet)
			as ReplaceSingleValuedEReference<Root, NonRoot>	
		
		// Set state after
		prepareReference(newValue)
				
		resolvedChange.assertIsResolved(affectedEObject, oldValue, newValue)
		Assert.assertTrue(oldValue != newValue)	
		Assert.assertTrue(affectedEObject.eGet(affectedFeature) == newValue)
		
		// Apply backward
		Assert.assertTrue(resolvedChange.applyBackward)
		
		Assert.assertTrue(affectedEObject.eGet(affectedFeature) == oldValue)
		Assert.assertEquals(stagingArea.contents.size, 1)
		Assert.assertTrue(stagingArea.contents.contains(newValue))
	}
		
	/**
	 * Starts a test with a containment feature and sets state before.
	 */
	def private void isContainmentTest() {
		affectedFeature = AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE
		prepareReference(oldValue)
		prepareStagingArea(newValue)
	}
	
	/**
	 * Starts a test with a non containment feature and sets state before.
	 */
	def private void isNonContainmentTest() {
		affectedFeature = AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE
		prepareReference(oldValue)
		prepareResource	
	}
	
	/**
	 * Sets the value of the affected feature.
	 * @param object The new value of the affected feature.
	 */
	def private void prepareReference(EObject object) {
		affectedEObject.eSet(affectedFeature, object)
	}
	
	/**
	 * Prepares all new and old values and stores them in a resource.
	 */
	override protected void prepareResource() {
		super.prepareResource
		resource.contents.add(oldValue)		
	}
	
	/**
	 * Change is not resolved.
	 */
	def private static void assertIsNotResolved(ReplaceSingleValuedEReference<Root, NonRoot> change, 
		Root affectedEObject, NonRoot oldValue, NonRoot newValue) {
		Assert.assertFalse(change.isResolved)
		Assert.assertTrue(change.affectedEObject != affectedEObject)
		Assert.assertTrue(change.oldValue != oldValue)
		Assert.assertTrue(change.newValue != newValue)
	}
	
	/**
	 * Change is resolved.
	 */
	def private static void assertIsResolved(ReplaceSingleValuedEReference<Root, NonRoot> change, 
		Root affectedEObject, NonRoot oldValue, NonRoot newValue) {
		Assert.assertTrue(change.isResolved)
		Assert.assertTrue(change.affectedEObject == affectedEObject)
		Assert.assertTrue(change.oldValue == oldValue)
		Assert.assertTrue(change.newValue == newValue)	
	}
	
	/**
	 * Creates new unresolved change.
	 */
	def private ReplaceSingleValuedEReference<Root, NonRoot> createUnresolvedChange() {
		return atomicFactory.<Root, NonRoot>createReplaceSingleReferenceChange
		(affectedEObject, affectedFeature, oldValue, newValue)	
	}
}