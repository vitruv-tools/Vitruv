package tools.vitruv.framework.tests.echange.compound

import allElementTypes.AllElementTypesFactory
import allElementTypes.AllElementTypesPackage
import allElementTypes.NonRoot
import allElementTypes.Root
import org.eclipse.emf.ecore.EReference
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceNonRoot
import tools.vitruv.framework.tests.echange.EChangeTest

import static extension tools.vitruv.framework.tests.echange.util.EChangeAssertHelper.*
import static extension tools.vitruv.framework.change.echange.EChangeResolverAndApplicator.*; 

/**
 * Test class for the concrete {@link CreateAndReplaceNonRoot} EChange,
 * which creates a new non root EObject and replaces a null value
 * in a single valued containment reference.
 */
class CreateAndReplaceNonRootTest extends EChangeTest {
	protected var Root affectedEObject = null
	protected var EReference affectedFeature = null
	protected var NonRoot newNonRootObject = null
	
	@Before
	override public void beforeTest() {
		super.beforeTest
		affectedEObject = rootObject
		affectedFeature = AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE
		newNonRootObject = AllElementTypesFactory.eINSTANCE.createNonRoot
		prepareStateBefore
	}
	
	/**
	 * Resolves a {@link CreateAndReplaceNonRoot} EChange. The model is in state
	 * before the change, so the new non root element will be created and inserted
	 * into a single valued containment reference.
	 */
	@Test
	def public void resolveBeforeTest() {
		// Create change
		val unresolvedChange = createUnresolvedChange(newNonRootObject)
		unresolvedChange.assertIsNotResolved(affectedEObject, newNonRootObject)
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(uuidGeneratorAndResolver)
			as CreateAndReplaceNonRoot<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, newNonRootObject)
		
		// Resolving applies all changes and reverts them, so the model should be unaffected.
		assertIsStateBefore
	}
	
	/**
	 * Resolves a {@link CreateAndReplaceNonRoot} EChange. The model is in state
	 * after the change, so the new non root element is already created and
	 * in the single valued containment reference.
	 */
	@Test
	def public void resolveAfterTest() {
		// Create change
		val unresolvedChange = createUnresolvedChange(newNonRootObject)
		unresolvedChange.assertIsNotResolved(affectedEObject, newNonRootObject)
		
		// Set state after
		prepareStateAfter
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(uuidGeneratorAndResolver)
			as CreateAndReplaceNonRoot<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, newNonRootObject)
		
		// Resolving applies all changes and reverts them, so the model should be unaffected.
		assertIsStateAfter
	}
	
	/**
	 * Tests whether resolving the {@link CreateAndReplaceNonRoot} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange(newNonRootObject)
		
		// Resolve		
 		val resolvedChange = unresolvedChange.resolveBefore(uuidGeneratorAndResolver)
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}
	
	/**
	 * Tests the {@link CreateAndReplaceNonRoot} EChange by applying it forward.
	 * Creates and inserts a new non root object into a single valued containment
	 * reference.
	 */
	@Test
	def public void applyForwardTest() {
		// Create and resolve and apply
		val resolvedChange = createUnresolvedChange(newNonRootObject).resolveBefore(uuidGeneratorAndResolver)
			as CreateAndReplaceNonRoot<Root, NonRoot>
		resolvedChange.assertApplyForward
		
		// State after
		assertIsStateAfter
	}
	
	/**
	 * Tests the {@link CreateAndReplaceNonRoot} EChange by applying it backward.
	 * Removes and deletes an existing non root object from a single valued containment
	 * reference.
	 */
	@Test
	def public void applyBackwardTest() {
		// Create and resolve
		val resolvedChange = createUnresolvedChange(newNonRootObject).resolveBefore(uuidGeneratorAndResolver)
			as CreateAndReplaceNonRoot<Root, NonRoot>
			
		// Set state after
		prepareStateAfter
		
		// Apply
		resolvedChange.assertApplyBackward
		
		// State before
		assertIsStateBefore
	}
	
	/**
	 * Sets state before
	 */
	def private void prepareStateBefore() {
		affectedEObject.eSet(affectedFeature, null)
		assertIsStateBefore
	}
	
	/**
	 * Sets state after
	 */
	def private void prepareStateAfter() {
		affectedEObject.eSet(affectedFeature, newNonRootObject)
		assertIsStateAfter
	}
	
	/**
	 * Model is in state before the change.
	 */
	def private void assertIsStateBefore() {
		Assert.assertNull(affectedEObject.eGet(affectedFeature))
	}
	
	/**
	 * Model is in state after the change.
	 */
	def private void assertIsStateAfter() {
		newNonRootObject.assertEqualsOrCopy(affectedEObject.eGet(affectedFeature) as NonRoot)
	}
	
	/**
	 * Change is not resolved.
	 */
	def private static void assertIsNotResolved(CreateAndReplaceNonRoot<Root, NonRoot> change, Root affectedEObject, NonRoot newValue) {
		Assert.assertFalse(change.isResolved)
		Assert.assertFalse(change.createChange.isResolved)
		Assert.assertFalse(change.insertChange.isResolved)
		Assert.assertNull(change.createChange.affectedEObject)
		Assert.assertNull(change.insertChange.newValue)
		Assert.assertNull(change.insertChange.affectedEObject);
		Assert.assertEquals(change.insertChange.newValueID, change.createChange.affectedEObjectID)
	}
	
	/**
	 * Change is resolved.
	 */
	def private static void assertIsResolved(CreateAndReplaceNonRoot<Root, NonRoot> change, Root affectedEObject, NonRoot newValue) {
		Assert.assertTrue(change.isResolved)
		change.insertChange.newValue.assertEqualsOrCopy(newValue)
		change.createChange.affectedEObject.assertEqualsOrCopy(newValue)
		Assert.assertSame(change.insertChange.affectedEObject, affectedEObject)
	}
	
	/**
	 * Creates new unresolved change.
	 */	
	def private CreateAndReplaceNonRoot<Root, NonRoot> createUnresolvedChange(NonRoot newNonRoot) {
		return compoundFactory.createCreateAndReplaceNonRootChange(affectedEObject, affectedFeature, newNonRoot)
	}
}