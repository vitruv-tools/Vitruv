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

import static extension tools.vitruv.framework.tests.echange.util.EChangeAssertHelper.*

/**
 * Test class for the concrete {@link CreateAndReplaceAndDeleteNonRoot} EChange,
 * which creates a new non root EObject and replaces an existing non root object
 * in a single value containment reference. The existing one will be deleted.
 */
class CreateAndReplaceAndDeleteNonRootTest extends ReferenceEChangeTest {
	protected var NonRoot oldValue = null	
	protected var EReference affectedFeature = null
	
	@Before
	override public void beforeTest() {
		super.beforeTest()
		oldValue = AllElementTypesFactory.eINSTANCE.createNonRoot()
		affectedFeature = AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE
		prepareStateBefore
	}
		
	/**
	 * Resolves a {@link CreateAndReplaceAndDeleteNonRoot} EChange. The 
	 * model is in state before the change, so new value doesn't exist and 
	 * the old value is in the containment reference.
	 */
	@Test
	def public void resolveBeforeTest() {
		// Create change
		val unresolvedChange = createUnresolvedChange(newValue)
		unresolvedChange.assertIsNotResolved(affectedEObject, oldValue, newValue)

		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(resourceSet) 
			as CreateAndReplaceAndDeleteNonRoot<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, oldValue, newValue)
							
		// Resolving applies all changes and reverts them, so the model should be unaffected.			
		assertIsStateBefore
	}
	
	/**
	 * Resolves a {@link CreateAndReplaceAndDeleteNonRoot} EChange. The 
	 * model is in state after the change, so old value doesn't exist and 
	 * the new value is in the containment reference.
	 */
	@Test
	def public void resolveAfterTest() {
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
		assertIsStateAfter
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
	def public void applyForwardTest() {		
		// Create and resolve 1
		val resolvedChange = createUnresolvedChange(newValue).resolveBefore(resourceSet)
			as CreateAndReplaceAndDeleteNonRoot<Root, NonRoot>
			
		// Apply change 1 forward
		resolvedChange.assertApplyForward
				
		// State after
		assertIsStateAfter
				
		// Create and resolve 2
		val resolvedChange2 = createUnresolvedChange(newValue2).resolveBefore(resourceSet)
			as CreateAndReplaceAndDeleteNonRoot<Root, NonRoot>
			
		// Apply change 2 forward
		resolvedChange2.assertApplyForward
		
		var NonRoot valueAfterChange2 = affectedEObject.eGet(affectedFeature) as NonRoot
		valueAfterChange2.assertEqualsOrCopy(newValue2)
		Assert.assertTrue(stagingArea.empty)			
	}
	
	/**
	 * Tests applying the {@link CreateAndReplaceAndDeleteNonRoot} EChange backward
	 * by replacing a single value containment reference with its old value.
	 */
	@Test
	def public void applyBackwardTest() {
		// Create change 
		val resolvedChange = createUnresolvedChange(newValue).resolveBefore(resourceSet)
			as CreateAndReplaceAndDeleteNonRoot<Root, NonRoot>
			
		// Set state after change 
		prepareStateAfter
				
		// Apply backward
		Assert.assertTrue(resolvedChange.applyBackward)
		
		// State before
		assertIsStateBefore		
	}

	def private void prepareStateBefore() {
		affectedEObject.eSet(affectedFeature, oldValue)
		assertIsStateBefore
	}
	
	/**
	 * Sets the model after the change
	 */
	def private void prepareStateAfter() {
		affectedEObject.eSet(affectedFeature, newValue)
		assertIsStateAfter
	}
	
	/**
	 * Model is in state before the changes.
	 */
	def private void assertIsStateBefore() {
		Assert.assertTrue(stagingArea.empty)
		oldValue.assertEqualsOrCopy(affectedEObject.eGet(affectedFeature) as NonRoot)		
	}
	
	/**
	 * Model is in state after the changes.
	 */
	def private void assertIsStateAfter() {
		Assert.assertTrue(stagingArea.empty)
		newValue.assertEqualsOrCopy(affectedEObject.eGet(affectedFeature) as NonRoot)
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
		
		Assert.assertNotSame(change.createChange.affectedEObject, newNonRootObject)
		Assert.assertNotSame(change.replaceChange.newValue, newNonRootObject)
		Assert.assertNotSame(change.replaceChange.oldValue, oldNonRootObject)
		Assert.assertNotSame(change.deleteChange.affectedEObject, oldNonRootObject)
		
		Assert.assertNotSame(change.replaceChange.affectedEObject, affectedRootObject)
		
		Assert.assertNotSame(change.replaceChange.oldValue, change.deleteChange.affectedEObject)
		Assert.assertNotSame(change.replaceChange.newValue, change.createChange.affectedEObject)
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
		Assert.assertSame(change.replaceChange.affectedEObject, affectedRootObject)
	}	
	
	/**
	 * Creates new unresolved change.
	 */
	def private CreateAndReplaceAndDeleteNonRoot<Root, NonRoot> createUnresolvedChange(NonRoot newNonRootValue) {
		return compoundFactory.createCreateAndReplaceAndDeleteNonRootChange(affectedEObject, affectedFeature, oldValue, newNonRootValue, null, null)	
	}
}