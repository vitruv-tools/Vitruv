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

/**
 * Test class for the concrete {@link CreateAndInsertNonRoot} EChange,
 * which creates a new non root EObject and inserts it in containment reference.
 */
public class CreateAndInsertNonRootTest extends ReferenceEChangeTest {
	protected var EReference affectedFeature = null
	protected var EList<NonRoot> referenceContent = null
	protected var index = DEFAULT_INDEX
	
	protected static val Integer DEFAULT_INDEX = 0
	
	/**
	 * Calls setup of super class and sets the feature for the tests.
	 */
	@Before
	override public void beforeTest() {
		super.beforeTest
		affectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE
		referenceContent = affectedEObject.eGet(affectedFeature) as EList<NonRoot>
	}
	
	/**
	 * Resolves a {@link CreateAndInsertNonRoot} EChange. The model is in state
	 * before the change, so the new non root element will be created and inserted
	 * in a containment reference.
	 */
	@Test
	def public void resolveCreateAndInsertNonRootTest() {
		// State before
		val size = referenceContent.size
		Assert.assertTrue(stagingArea.contents.empty)	
		
		// Create change
		val unresolvedChange = createUnresolvedChange(affectedEObject, newValue)
		unresolvedChange.assertIsNotResolved(affectedEObject, newValue)
			
		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(resourceSet) 
			as CreateAndInsertNonRoot<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, newValue)	
			
		// Resolving applies all changes and reverts them, so the model should be unaffected.			
		Assert.assertEquals(referenceContent.size, size)
		Assert.assertTrue(stagingArea.contents.empty)			
	}
	
	/**
	 * Resolves a {@link CreateAndInsertNonRoot} EChange. The model is in state
	 * after the change, so the new non root element is in a containment reference.
	 */
	@Test
	def public void resolveCreateAndInsertNonRootTest2() {
		// State before
		Assert.assertTrue(stagingArea.contents.empty)			
		
		// Create change
		val unresolvedChange = createUnresolvedChange(affectedEObject, newValue)
		unresolvedChange.assertIsNotResolved(affectedEObject, newValue)		
		
		// Set state after
		prepareReference
		val size = referenceContent.size
			
		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(resourceSet) 
			as CreateAndInsertNonRoot<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, newValue)	
		
		// Resolving applies all changes and reverts them, so the model should be unaffected.		
		Assert.assertEquals(referenceContent.size, size)
		Assert.assertTrue(stagingArea.contents.empty)				
	}
	
	/**
	 * Tests whether resolving the {@link CreateAndInsertNonRoot} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange(affectedEObject, newValue)
		
		// Resolve		
 		val resolvedChange = unresolvedChange.resolveBefore(resourceSet)
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}
	
	/**
	 * Tests the {@link CreateAndInsertNonRoot} EChange by creating
	 * and inserting a new non root object into a multi valued 
	 * containment reference.
	 */
	@Test
	def public void createAndInsertNonRootApplyForwardTest() {
		// State before
		Assert.assertTrue(stagingArea.contents.empty)
		val oldSize = referenceContent.size	
		
		// Create and resolve and apply change
		val resolvedChange = createUnresolvedChange(affectedEObject, newValue).resolveBefore(resourceSet)
			as CreateAndInsertNonRoot<Root, NonRoot>
		Assert.assertTrue(resolvedChange.applyForward)
		
		Assert.assertEquals(referenceContent.size, oldSize + 1)
		Assert.assertTrue(referenceContent.contains(resolvedChange.createChange.affectedEObject))
		Assert.assertTrue(stagingArea.contents.empty)
		
		// Create and resolve and apply change 2	
		val resolvedChange2 = createUnresolvedChange(affectedEObject, newValue2).resolveBefore(resourceSet)
			as CreateAndInsertNonRoot<Root, NonRoot>
		Assert.assertTrue(resolvedChange2.applyForward)
		
		Assert.assertEquals(referenceContent.size, oldSize + 2)
		Assert.assertTrue(referenceContent.contains(resolvedChange2.createChange.affectedEObject))
		Assert.assertTrue(stagingArea.contents.empty)
	}
	
	/**
	 * Tests the {@link CreateAndInsertNonRoot} EChange by applying it backward.
	 * A non root object which was added to a containment reference will be removed and
	 * deleted.
	 */
	@Test
	def public void createAndInsertNonRootApplyBackwardTest() {
		// State before
		Assert.assertTrue(stagingArea.contents.empty)
		val index1 = referenceContent.indexOf(newValue)
		val index2 = referenceContent.indexOf(newValue2)
		
		// Create and resolve and apply change 1
		val resolvedChange = createUnresolvedChange(affectedEObject, newValue).resolveBefore(resourceSet)
			as CreateAndInsertNonRoot<Root, NonRoot>
		Assert.assertTrue(resolvedChange.applyForward)	
		
		// Create and resolve change 2
		val resolvedChange2 = createUnresolvedChange(affectedEObject, newValue2).resolveBefore(resourceSet)
			as CreateAndInsertNonRoot<Root, NonRoot>
		Assert.assertTrue(resolvedChange2.applyForward)	
				
		// State after
		val oldSize = referenceContent.size
		Assert.assertTrue(referenceContent.contains(resolvedChange.createChange.affectedEObject))
		Assert.assertTrue(referenceContent.contains(resolvedChange2.createChange.affectedEObject))	
		Assert.assertTrue(stagingArea.contents.empty)
					
		// Apply backward 2
		Assert.assertTrue(resolvedChange2.applyBackward)
		
		Assert.assertTrue(referenceContent.contains(resolvedChange.createChange.affectedEObject))
		Assert.assertFalse(referenceContent.contains(resolvedChange2.createChange.affectedEObject))	
		Assert.assertEquals(referenceContent.size, oldSize - 1)
		Assert.assertTrue(stagingArea.contents.empty)
			
		// Apply backward 1	
		Assert.assertTrue(resolvedChange.applyBackward)
		
		Assert.assertEquals(index1, referenceContent.indexOf(resolvedChange.createChange.affectedEObject))
		Assert.assertEquals(index2, referenceContent.indexOf(resolvedChange2.createChange.affectedEObject))
		Assert.assertEquals(referenceContent.size, oldSize - 2)
		Assert.assertTrue(stagingArea.contents.empty)		
	}
	

	/**
	 * Puts the newly created items in the reference to set
	 * the state after the change.
	 */
	def private void prepareReference() {
		referenceContent.add(newValue)
		referenceContent.add(newValue2)
	}
	
	/**
	 * Change is not resolved.
	 */
	def private static void assertIsNotResolved(CreateAndInsertNonRoot<Root, NonRoot> change, Root affectedEObject,
		NonRoot newNonRoot) {
		Assert.assertFalse(change.isResolved)
		Assert.assertFalse(change.createChange.isResolved)
		Assert.assertFalse(change.insertChange.isResolved)
		Assert.assertTrue(change.createChange.affectedEObject != newNonRoot)
		Assert.assertTrue(change.insertChange.newValue != newNonRoot)
		Assert.assertTrue(change.insertChange.affectedEObject != affectedEObject)
		Assert.assertTrue(change.createChange.affectedEObject != change.insertChange.newValue)
	}
	
	/**
	 * Change is resolved.
	 */
	def private static void assertIsResolved(CreateAndInsertNonRoot<Root, NonRoot> change, Root affectedEObject,
		NonRoot newNonRoot) {
		Assert.assertTrue(change.isResolved)
		change.insertChange.newValue.assertEqualsOrCopy(newNonRoot)
		change.createChange.affectedEObject.assertEqualsOrCopy(newNonRoot)
		Assert.assertTrue(change.insertChange.affectedEObject == affectedEObject)	
	}
		
	/**
	 * Creates new unresolved change.
	 */
	def private CreateAndInsertNonRoot<Root, NonRoot> createUnresolvedChange(Root affectedRootObject, NonRoot newNonRoot) {
		return compoundFactory.<Root, NonRoot>createCreateAndInsertNonRootChange(affectedRootObject, affectedFeature, newNonRoot, index)
	}
}