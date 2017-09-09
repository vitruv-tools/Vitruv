package tools.vitruv.framework.tests.echange.compound

import allElementTypes.AllElementTypesPackage
import allElementTypes.NonRoot
import allElementTypes.Root
import org.eclipse.emf.ecore.EReference
import org.junit.Before
import org.junit.Assert
import org.junit.Test
import tools.vitruv.framework.tests.echange.EChangeTest
import allElementTypes.AllElementTypesFactory

import static extension tools.vitruv.framework.tests.echange.util.EChangeAssertHelper.*
import tools.vitruv.framework.change.echange.EChange
import java.util.List
import tools.vitruv.framework.change.echange.eobject.DeleteEObject
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference

/**
 * Test class for the concrete {@link ReplaceAndDeleteNonRoot} EChange,
 * which removes a non root EObject from a single valued containment reference
 * and delets it.
 */
class ReplaceAndDeleteNonRootTest extends EChangeTest {
	protected var Root affectedEObject = null
	protected var EReference affectedFeature = null
	protected var NonRoot oldNonRootObject = null
	
	@Before
	override public void beforeTest() {
		super.beforeTest
		affectedEObject = rootObject
		affectedFeature = AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE
		oldNonRootObject = AllElementTypesFactory.eINSTANCE.createNonRoot
		prepareStateBefore
	}
	
	/**
	 * Resolves a {@link ReplaceAndDeleteNonRoot} EChange. The model is in state
	 * before the change, so the old non root element is still in the single valued
	 * containment reference.
	 */
	@Test
	def public void resolveBeforeTest() {
		// Create change
		val unresolvedChange = createUnresolvedChange(oldNonRootObject)
		unresolvedChange.assertIsNotResolved
		
		// Set state after
		prepareStateAfter
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(uuidGeneratorAndResolver)
		resolvedChange.assertIsResolved(affectedEObject, oldNonRootObject)
		
		// Resolving applies all changes and reverts them, so the model should be unaffected.
		assertIsStateAfter		
	}
	
	/**
	 * Resolves a {@link ReplaceAndDeleteNonRoot} EChange. The model is in state
	 * after the change, so the old non root element is deleted and the single valued
	 * containment reference is null.
	 */
	@Test
	def public void resolveAfterTest() {
		// Create change
		val unresolvedChange = createUnresolvedChange(oldNonRootObject)
		unresolvedChange.assertIsNotResolved	

		// Set state after
		prepareStateAfter
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(uuidGeneratorAndResolver)
		resolvedChange.assertIsResolved(affectedEObject, oldNonRootObject)
		
		// Resolving applies all changes and reverts them, so the model should be unaffected.
		assertIsStateAfter		
	}
	
	/**
	 * Tests whether resolving the {@link ReplaceAndDeleteNonRoot} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange(oldNonRootObject)
		
		// Resolve		
 		val resolvedChange = unresolvedChange.resolveBefore(uuidGeneratorAndResolver)
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}
	
	/**
	 * Tests the {@link ReplaceAndDeleteNonRoot} EChange by applying it forward.
	 * Removes a non root object from a single valued containment reference and deletes it.
	 */
	@Test
	def public void applyForwardTest() {
		// Create and resolve and apply
		val resolvedChange = createUnresolvedChange(oldNonRootObject).resolveBefore(uuidGeneratorAndResolver)
		resolvedChange.assertApplyForward
		
		// State after
		assertIsStateAfter
	}
	
	/**
	 * Tests the {@link ReplaceAndDeleteNonRoot} EChange by applying it backward.
	 * Creates and inserts an old non root object into a single valued containment
	 * reference.
	 */
	@Test
	def public void applyBackwardTest() {
		// Create and resolve
		val resolvedChange = createUnresolvedChange(oldNonRootObject).resolveBefore(uuidGeneratorAndResolver)
			
		// Set state after
		prepareStateAfter
		
		// Apply
		resolvedChange.assertApplyBackward
		
		// State before
		assertIsStateBefore
	}
		
	/**
	 * Sets the state before the change.
	 */
	def private void prepareStateBefore() {
		affectedEObject.eSet(affectedFeature, oldNonRootObject)
		assertIsStateBefore
	}
	
	/**
	 * Sets the state after the change.
	 */
	def private void prepareStateAfter() {
		affectedEObject.eSet(affectedFeature, null)
		assertIsStateAfter
	}
	
	/**
	 * Model is in state before the change.
	 */
	def private void assertIsStateBefore() {
		oldNonRootObject.assertEqualsOrCopy(affectedEObject.eGet(affectedFeature) as NonRoot)
	}
	
	/**
	 * Model is in state after the change.
	 */
	def private void assertIsStateAfter() {
		Assert.assertNull(affectedEObject.eGet(affectedFeature))
	}
	
	/**
	 * Change is not resolved.
	 */
	def protected static void assertIsNotResolved(List<? extends EChange> changes) {
		EChangeTest.assertIsNotResolved(changes);
		Assert.assertEquals(2, changes.size);
		val replaceChange = assertType(changes.get(0), ReplaceSingleValuedEReference);
		val deleteChange = assertType(changes.get(1), DeleteEObject);
		Assert.assertEquals(replaceChange.oldValueID, deleteChange.affectedEObjectID)
	}
	
	/**
	 * Change is resolved.
	 */
	def private static void assertIsResolved(List<EChange> changes, Root affectedEObject, NonRoot oldValue) {
		changes.assertIsResolved;
		Assert.assertEquals(2, changes.size);
		val replaceChange = assertType(changes.get(0), ReplaceSingleValuedEReference);
		val deleteChange = assertType(changes.get(1), DeleteEObject);
		replaceChange.oldValue.assertEqualsOrCopy(oldValue)
		deleteChange.affectedEObject.assertEqualsOrCopy(oldValue)
		replaceChange.affectedEObject.assertEqualsOrCopy(affectedEObject)	
	}
	
	/**
	 * Creates new unresolved change.
	 */
	def private List<EChange> createUnresolvedChange(NonRoot oldNonRoot) {
		return compoundFactory.createReplaceAndDeleteNonRootChange(affectedEObject, affectedFeature, oldNonRoot)
	}
}