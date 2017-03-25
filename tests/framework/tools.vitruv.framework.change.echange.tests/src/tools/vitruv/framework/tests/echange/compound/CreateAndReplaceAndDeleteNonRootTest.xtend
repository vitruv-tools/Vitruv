package tools.vitruv.framework.tests.echange.compound

import allElementTypes.AllElementTypesFactory
import allElementTypes.AllElementTypesPackage
import allElementTypes.NonRoot
import allElementTypes.Root
import org.eclipse.emf.ecore.EReference
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceAndDeleteNonRoot
import tools.vitruv.framework.tests.echange.feature.reference.ReferenceEChangeTest

/**
 * Test class for the concrete {@link CreateAndReplaceAndDeleteNonRoot} EChange,
 * which creates a new non root EObject and replaces an existing non root object
 * in a single value containment reference. The existing one will be deleted.
 */
class CreateAndReplaceAndDeleteNonRootTest extends ReferenceEChangeTest {
	protected var NonRoot oldValue = null	
	protected var EReference affectedFeature = null
	protected var index = DEFAULT_INDEX
	
	protected static val Integer DEFAULT_INDEX = 0
	protected static val String DEFAULT_OLD_VALUE_ID = "Old Non Root Element"
	
	/**
	 * Calls setup of super class and sets the feature for the tests.
	 */
	@Before
	override public void beforeTest() {
		super.beforeTest()
		oldValue = AllElementTypesFactory.eINSTANCE.createNonRoot()
		oldValue.id = DEFAULT_OLD_VALUE_ID
		affectedFeature = AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE
		affectedEObject.eSet(affectedFeature, oldValue)
	}
		
	/**
	 * Resolves a {@link CreateAndReplaceAndDeleteNonRoot} EChange. The 
	 * model is in state before the change, so new value doesn't exist and 
	 * the old value is in the containment reference.
	 */
	@Test
	def public void resolveCreateAndReplaceAndDeleteNonRootTest() {
		// State before
		Assert.assertTrue(stagingArea.contents.empty)
		Assert.assertTrue(affectedEObject.eGet(affectedFeature) == oldValue)
		
		// Create change
		val unresolvedChange = createUnresolvedChange(newValue)
		unresolvedChange.assertIsNotResolved(affectedEObject, oldValue, newValue)

		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(resourceSet) 
			as CreateAndReplaceAndDeleteNonRoot<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, oldValue, newValue)
							
		// Resolving applies all changes and reverts them, so the model should be unaffected.			
		Assert.assertTrue(affectedEObject.eGet(affectedFeature) == oldValue)
		Assert.assertTrue(stagingArea.contents.empty)
	}
	
	/**
	 * Resolves a {@link CreateAndReplaceAndDeleteNonRoot} EChange. The 
	 * model is in state after the change, so old value doesn't exist and 
	 * the new value is in the containment reference.
	 */
	@Test
	def public void resolveCreateAndReplaceAndDeleteNonRootTest2() {
		// State before change
		Assert.assertTrue(stagingArea.contents.empty)
		Assert.assertTrue(affectedEObject.eGet(affectedFeature) == oldValue)
		
		// Create change
		val unresolvedChange = createUnresolvedChange(newValue)
		unresolvedChange.assertIsNotResolved(affectedEObject, oldValue, newValue)
	
		// Set state after change	
		prepareStateAfter
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(resourceSet) 
			as CreateAndReplaceAndDeleteNonRoot<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, oldValue, newValue)
				
		// Resolving applies all changes and reverts them, so the model should be unaffected.			
		Assert.assertTrue(affectedEObject.eGet(affectedFeature) == newValue)
		Assert.assertTrue(stagingArea.contents.empty)
	}
	
	/**
	 * Tests whether resolving the {@link CreateAndReplaceAndDeleteNonRoot} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange(newValue)
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(resourceSet) 
			as CreateAndReplaceAndDeleteNonRoot<Root, NonRoot>
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)				
	}
	
	/**
	 * Tests applying the {@link CreateAndReplaceAndDeleteNonRoot} EChange forward
	 * by creating a new non root and replacing an existing one.
	 */
	@Test
	def public void createAndReplaceAndDeleteNonRootApplyForwardTest() {
		// State before
		Assert.assertTrue(stagingArea.contents.empty)
		Assert.assertTrue(affectedEObject.eGet(affectedFeature) == oldValue)
				
		// Create and resolve 1
		val resolvedChange = createUnresolvedChange(newValue).resolveBefore(resourceSet)
			as CreateAndReplaceAndDeleteNonRoot<Root, NonRoot>
			
		// Apply change 1 forward
		Assert.assertTrue(resolvedChange.applyForward)
				
		var NonRoot valueAfterChange1 = affectedEObject.eGet(affectedFeature) as NonRoot
		valueAfterChange1.assertIsCopy(newValue)
		Assert.assertTrue(stagingArea.contents.empty)
				
		// Create and resolve 2
		val resolvedChange2 = createUnresolvedChange(newValue2).resolveBefore(resourceSet)
			as CreateAndReplaceAndDeleteNonRoot<Root, NonRoot>
			
		// Apply change 2 forward
		Assert.assertTrue(resolvedChange2.applyForward)
		
		var NonRoot valueAfterChange2 = affectedEObject.eGet(affectedFeature) as NonRoot
		valueAfterChange2.assertIsCopy(newValue2)
		Assert.assertTrue(stagingArea.contents.empty)			
	}
	
	/**
	 * Tests applying the {@link CreateAndReplaceAndDeleteNonRoot} EChange backward
	 * by replacing a single value containment reference with its old value.
	 */
	@Test
	def public void createAndReplaceAndDeleteNonRootApplyBackwardTest() {
		// State before change.
		Assert.assertTrue(stagingArea.contents.empty)
		Assert.assertTrue(affectedEObject.eGet(affectedFeature) == oldValue)
		
		// Create change 
		val resolvedChange = createUnresolvedChange(newValue).resolveBefore(resourceSet)
			as CreateAndReplaceAndDeleteNonRoot<Root, NonRoot>
			
		// Set state after change 
		Assert.assertTrue(resolvedChange.applyForward)
		newValue.assertIsCopy(affectedEObject.eGet(affectedFeature) as NonRoot)
		Assert.assertTrue(stagingArea.contents.empty)
				
		// Apply backward
		Assert.assertTrue(resolvedChange.applyBackward)
		
		Assert.assertTrue(affectedEObject.eGet(affectedFeature) == oldValue)
		Assert.assertTrue(stagingArea.contents.empty)			
	}
	

	/**
	 * Sets the model after the change
	 * (Replaces the {@code oldValue} with {@code newValue})
	 */
	def private void prepareStateAfter() {
		affectedEObject.eSet(affectedFeature, newValue)
	}
	
	/**
	 * Change is not resolved.
	 */
	def private static void assertIsNotResolved(CreateAndReplaceAndDeleteNonRoot<Root, NonRoot> change, Root affectedRootObject,
		NonRoot oldNonRootObject, NonRoot newNonRootObject) {
		Assert.assertFalse(change.isResolved)
		
		Assert.assertFalse(change.createChange.isResolved)
		Assert.assertFalse(change.replaceChange.isResolved)
		Assert.assertFalse(change.deleteChange.isResolved)
		
		Assert.assertTrue(change.createChange.affectedEObject != newNonRootObject)
		Assert.assertTrue(change.replaceChange.newValue != newNonRootObject)
		Assert.assertTrue(change.replaceChange.oldValue != oldNonRootObject)
		Assert.assertTrue(change.deleteChange.affectedEObject != oldNonRootObject)
		
		Assert.assertTrue(change.replaceChange.affectedEObject != affectedRootObject)
		
		Assert.assertTrue(change.replaceChange.oldValue != change.deleteChange.affectedEObject)
		Assert.assertTrue(change.replaceChange.newValue != change.createChange.affectedEObject)
	}
	
	/**
	 * Change is resolved.
	 */
	def private static void assertIsResolved(CreateAndReplaceAndDeleteNonRoot<Root, NonRoot> change, Root affectedRootObject,
		NonRoot oldNonRootObject, NonRoot newNonRootObject) {
		Assert.assertTrue(change.isResolved)
		change.createChange.affectedEObject.assertEqualsOrCopy(newNonRootObject)
		change.replaceChange.oldValue.assertEqualsOrCopy(oldNonRootObject)
		change.replaceChange.newValue.assertEqualsOrCopy(newNonRootObject)
		change.deleteChange.affectedEObject.assertEqualsOrCopy(oldNonRootObject)
		Assert.assertTrue(change.replaceChange.affectedEObject == affectedRootObject)
	}	
	
	/**
	 * Creates new unresolved change.
	 */
	def private CreateAndReplaceAndDeleteNonRoot<Root, NonRoot> createUnresolvedChange(NonRoot newNonRootValue) {
		return compoundFactory.<Root, NonRoot>createCreateAndReplaceAndDeleteNonRootChange(affectedEObject, affectedFeature, oldValue, newNonRootValue)	
	}
}