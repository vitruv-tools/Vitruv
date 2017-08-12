package tools.vitruv.framework.tests.echange.compound

import allElementTypes.AllElementTypesPackage
import allElementTypes.NonRoot
import allElementTypes.Root
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EReference
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot
import tools.vitruv.framework.tests.echange.feature.reference.ReferenceEChangeTest

import static extension tools.vitruv.framework.tests.echange.util.EChangeAssertHelper.*

/**
 * Test class for the concrete {@link CreateAndInsertNonRoot} EChange,
 * which creates a new non root EObject and inserts it in containment reference.
 */
public class CreateAndInsertNonRootTest extends ReferenceEChangeTest {
	protected var EReference affectedFeature = null
	protected var EList<NonRoot> referenceContent = null
	
	@Before
	override public void beforeTest() {
		super.beforeTest
		affectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE
		referenceContent = affectedEObject.eGet(affectedFeature) as EList<NonRoot>
		assertIsStateBefore
	}
	
	/**
	 * Resolves a {@link CreateAndInsertNonRoot} EChange. The model is in state
	 * before the change, so the new non root element will be created and inserted
	 * into a containment reference.
	 */
	@Test
	def public void resolveBeforeTest() {
		// Create change
		val unresolvedChange = createUnresolvedChange(affectedEObject, newValue, 0)
		unresolvedChange.assertIsNotResolved(affectedEObject, newValue)
			
		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(resourceSet) 
			as CreateAndInsertNonRoot<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, newValue)	
			
		// Resolving applies all changes and reverts them, so the model should be unaffected.			
		assertIsStateBefore		
	}
	
	/**
	 * Resolves a {@link CreateAndInsertNonRoot} EChange. The model is in state
	 * after the change, so the new non root element is in a containment reference.
	 */
	@Test
	def public void resolveAfterTest() {
		// Create change
		val unresolvedChange = createUnresolvedChange(affectedEObject, newValue, 0)
		unresolvedChange.assertIsNotResolved(affectedEObject, newValue)		
		
		// Set state after
		prepareStateAfter
			
		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(resourceSet) 
			as CreateAndInsertNonRoot<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, newValue)	
		
		// Resolving applies all changes and reverts them, so the model should be unaffected.		
		assertIsStateAfter		
	}
	
	/**
	 * Tests whether resolving the {@link CreateAndInsertNonRoot} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange(affectedEObject, newValue, 0)
		
		// Resolve		
 		val resolvedChange = unresolvedChange.resolveBefore(resourceSet)
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}
	
	/**
	 * Tests the {@link CreateAndInsertNonRoot} EChange by applying it forward.
	 * Creates and inserts a new non root object into a multi valued 
	 * containment reference.
	 */
	@Test
	def public void applyForwardTest() {
		// Create and resolve and apply change
		val resolvedChange = createUnresolvedChange(affectedEObject, newValue, 0).resolveBefore(resourceSet)
			as CreateAndInsertNonRoot<Root, NonRoot>
		resolvedChange.assertApplyForward
		
		Assert.assertEquals(referenceContent.size, 1)
		Assert.assertTrue(referenceContent.contains(resolvedChange.createChange.affectedEObject))
		Assert.assertTrue(stagingArea.empty)
		
		// Create and resolve and apply change 2	
		val resolvedChange2 = createUnresolvedChange(affectedEObject, newValue2, 1).resolveBefore(resourceSet)
			as CreateAndInsertNonRoot<Root, NonRoot>
		resolvedChange2.assertApplyForward
		
		// State after
		assertIsStateAfter
	}
	
	/**
	 * Tests the {@link CreateAndInsertNonRoot} EChange by applying it backward.
	 * A non root object which was added to a containment reference will be removed and
	 * deleted.
	 */
	@Test
	def public void applyBackwardTest() {
		// Create and resolve and apply change 1
		val resolvedChange = createUnresolvedChange(affectedEObject, newValue, 0).resolveBefore(resourceSet)
			as CreateAndInsertNonRoot<Root, NonRoot>
		resolvedChange.assertApplyForward
		
		// Create and resolve change 2
		val resolvedChange2 = createUnresolvedChange(affectedEObject, newValue2, 1).resolveBefore(resourceSet)
			as CreateAndInsertNonRoot<Root, NonRoot>
		resolvedChange2.assertApplyForward
				
		// State after
		assertIsStateAfter
					
		// Apply backward 2
		resolvedChange2.assertApplyBackward
		
		Assert.assertTrue(referenceContent.contains(resolvedChange.createChange.affectedEObject))
		Assert.assertFalse(referenceContent.contains(resolvedChange2.createChange.affectedEObject))	
		Assert.assertEquals(referenceContent.size, 1)
		Assert.assertTrue(stagingArea.empty)
			
		// Apply backward 1	
		resolvedChange.assertApplyBackward
		
		// State after
		assertIsStateBefore	
	}
	
	/**
	 * Sets the state of the model after the changes.
	 */
	def private void prepareStateAfter() {
		referenceContent.add(newValue)
		referenceContent.add(newValue2)		
		assertIsStateAfter
	}
	
	/**
	 * Model is in state before the changes.
	 */
	def private void assertIsStateBefore() {
		Assert.assertEquals(referenceContent.size, 0)
		Assert.assertTrue(stagingArea.empty)
	}
	
	/**
	 * Model is in state after the changes.
	 */
	def private void assertIsStateAfter() {
		Assert.assertEquals(referenceContent.size, 2)
		newValue.assertEqualsOrCopy(referenceContent.get(0))
		newValue2.assertEqualsOrCopy(referenceContent.get(1))
		Assert.assertTrue(stagingArea.empty)
	}
	
	/**
	 * Change is not resolved.
	 */
	def private static void assertIsNotResolved(CreateAndInsertNonRoot<Root, NonRoot> change, Root affectedEObject,
		NonRoot newNonRoot) {
		Assert.assertFalse(change.isResolved)
		Assert.assertFalse(change.createChange.isResolved)
		Assert.assertFalse(change.insertChange.isResolved)
		Assert.assertNotSame(change.createChange.affectedEObject, newNonRoot)
		Assert.assertNotSame(change.insertChange.newValue, newNonRoot)
		Assert.assertNotSame(change.insertChange.affectedEObject, affectedEObject)
		Assert.assertNotSame(change.createChange.affectedEObject, change.insertChange.newValue)
	}
	
	/**
	 * Change is resolved.
	 */
	def private static void assertIsResolved(CreateAndInsertNonRoot<Root, NonRoot> change, Root affectedEObject,
		NonRoot newNonRoot) {
		Assert.assertTrue(change.isResolved)
		change.insertChange.newValue.assertEqualsOrCopy(newNonRoot)
		change.createChange.affectedEObject.assertEqualsOrCopy(newNonRoot)
		Assert.assertSame(change.insertChange.affectedEObject, affectedEObject)	
	}
		
	/**
	 * Creates new unresolved change.
	 */
	def private CreateAndInsertNonRoot<Root, NonRoot> createUnresolvedChange(Root affectedRootObject, NonRoot newNonRoot, int index) {
		return compoundFactory.createCreateAndInsertNonRootChange(affectedRootObject, affectedFeature, newNonRoot, index, null)
	}
}