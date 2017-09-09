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
import tools.vitruv.framework.change.echange.EChange
import java.util.List
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.change.echange.eobject.DeleteEObject
import tools.vitruv.framework.tests.echange.EChangeTest

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
		unresolvedChange.assertIsNotResolved()

		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(uuidGeneratorAndResolver) 
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
		unresolvedChange.assertIsNotResolved
	
		// Set state after change	
		prepareStateAfter
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(uuidGeneratorAndResolver) 
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
		val resolvedChange = unresolvedChange.resolveAfter(uuidGeneratorAndResolver) 
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)				
	}
	
	/**
	 * Tests applying the {@link CreateAndReplaceAndDeleteNonRoot} EChange forward
	 * by creating a new non root and replacing an existing one.
	 */
	@Test
	def public void applyForwardTest() {		
		// Create and resolve 1
		val resolvedChange = createUnresolvedChange(newValue).resolveBefore(uuidGeneratorAndResolver)
			
		// Apply change 1 forward
		resolvedChange.assertApplyForward
				
		// State after
		assertIsStateAfter
				
		// Create and resolve 2
		val resolvedChange2 = createUnresolvedChange(newValue2).resolveBefore(uuidGeneratorAndResolver)
			
		// Apply change 2 forward
		resolvedChange2.assertApplyForward
		
		var NonRoot valueAfterChange2 = affectedEObject.eGet(affectedFeature) as NonRoot
		valueAfterChange2.assertEqualsOrCopy(newValue2)
	}
	
	/**
	 * Tests applying the {@link CreateAndReplaceAndDeleteNonRoot} EChange backward
	 * by replacing a single value containment reference with its old value.
	 */
	@Test
	def public void applyBackwardTest() {
		// Create change 
		val resolvedChange = createUnresolvedChange(newValue).resolveBefore(uuidGeneratorAndResolver)
			
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
		oldValue.assertEqualsOrCopy(affectedEObject.eGet(affectedFeature) as NonRoot)		
	}
	
	/**
	 * Model is in state after the changes.
	 */
	def private void assertIsStateAfter() {
		newValue.assertEqualsOrCopy(affectedEObject.eGet(affectedFeature) as NonRoot)
	}
	
	/**
	 * Change is not resolved.
	 */
	 def protected static void assertIsNotResolved(List<EChange> changes) {
	 	EChangeTest.assertIsNotResolved(changes)
		Assert.assertEquals(3, changes.size);
		val createChange = assertType(changes.get(0), CreateEObject);
		val replaceChange = assertType(changes.get(1), ReplaceSingleValuedEReference);
		val deleteChange = assertType(changes.get(2), DeleteEObject);
		Assert.assertEquals(replaceChange.newValueID, createChange.affectedEObjectID)
		Assert.assertEquals(replaceChange.oldValueID, deleteChange.affectedEObjectID)
	}

	/**
	 * Change is resolved.
	 */
	def private static void assertIsResolved(List<EChange> changes, Root affectedRootObject,
		NonRoot oldNonRootObject, NonRoot newNonRootObject) {
		changes.assertIsResolved;
		Assert.assertEquals(3, changes.size);
		val createChange = assertType(changes.get(0), CreateEObject);
		val replaceChange = assertType(changes.get(1), ReplaceSingleValuedEReference);
		val deleteChange = assertType(changes.get(2), DeleteEObject);

		createChange.affectedEObject.assertEqualsOrCopy(newNonRootObject)
		replaceChange.oldValue.assertEqualsOrCopy(oldNonRootObject)
		replaceChange.newValue.assertEqualsOrCopy(newNonRootObject)
		deleteChange.affectedEObject.assertEqualsOrCopy(oldNonRootObject)
		replaceChange.affectedEObject.assertEqualsOrCopy(affectedRootObject)
	}	
	
	/**
	 * Creates new unresolved change.
	 */
	def private List<EChange> createUnresolvedChange(NonRoot newNonRootValue) {
		return compoundFactory.createCreateAndReplaceAndDeleteNonRootChange(affectedEObject, affectedFeature, oldValue, newNonRootValue)	
	}
}