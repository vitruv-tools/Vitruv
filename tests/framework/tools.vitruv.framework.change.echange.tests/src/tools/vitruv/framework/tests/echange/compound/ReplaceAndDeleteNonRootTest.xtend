package tools.vitruv.framework.tests.echange.compound

import allElementTypes.AllElementTypesPackage
import allElementTypes.NonRoot
import allElementTypes.Root
import org.eclipse.emf.ecore.EReference
import org.junit.Before
import org.junit.Assert
import org.junit.Test
import tools.vitruv.framework.change.echange.compound.ReplaceAndDeleteNonRoot
import tools.vitruv.framework.tests.echange.EChangeTest
import allElementTypes.AllElementTypesFactory

import static extension tools.vitruv.framework.tests.echange.util.EChangeAssertHelper.*
import static extension tools.vitruv.framework.change.echange.EChangeResolverAndApplicator.*;

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
		unresolvedChange.assertIsNotResolved(affectedEObject, oldNonRootObject)
		
		// Set state after
		prepareStateAfter
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(uuidGeneratorAndResolver)
			as ReplaceAndDeleteNonRoot<Root, NonRoot>
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
		unresolvedChange.assertIsNotResolved(affectedEObject, oldNonRootObject)	

		// Set state after
		prepareStateAfter
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(uuidGeneratorAndResolver)
			as ReplaceAndDeleteNonRoot<Root, NonRoot>
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
			as ReplaceAndDeleteNonRoot<Root, NonRoot>
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
			as ReplaceAndDeleteNonRoot<Root, NonRoot>
			
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
	def private static void assertIsNotResolved(ReplaceAndDeleteNonRoot<Root, NonRoot> change, Root affectedEObject, NonRoot oldValue) {
		Assert.assertFalse(change.isResolved)
		Assert.assertFalse(change.deleteChange.isResolved)
		Assert.assertFalse(change.removeChange.isResolved)
		Assert.assertNull(change.deleteChange.affectedEObject)
		Assert.assertNull(change.removeChange.oldValue)
		Assert.assertNull(change.removeChange.affectedEObject);
		Assert.assertEquals(change.removeChange.oldValueID, change.deleteChange.affectedEObjectID)
	}
	
	/**
	 * Change is resolved.
	 */
	def private static void assertIsResolved(ReplaceAndDeleteNonRoot<Root, NonRoot> change, Root affectedEObject, NonRoot oldValue) {
		Assert.assertTrue(change.isResolved)	
		change.removeChange.newValue.assertEqualsOrCopy(oldValue)
		change.deleteChange.affectedEObject.assertEqualsOrCopy(oldValue)
		Assert.assertSame(change.removeChange.affectedEObject, affectedEObject)	
	}
	
	/**
	 * Creates new unresolved change.
	 */
	def private ReplaceAndDeleteNonRoot<Root, NonRoot> createUnresolvedChange(NonRoot oldNonRoot) {
		return compoundFactory.createReplaceAndDeleteNonRootChange(affectedEObject, affectedFeature, oldNonRoot)
	}
}