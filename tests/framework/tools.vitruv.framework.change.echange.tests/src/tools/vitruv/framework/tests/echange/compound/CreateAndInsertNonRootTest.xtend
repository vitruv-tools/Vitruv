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
import tools.vitruv.framework.change.echange.EChange
import java.util.List
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.tests.echange.EChangeTest

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
		unresolvedChange.assertIsNotResolved
			
		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(uuidGeneratorAndResolver) 
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
		unresolvedChange.assertIsNotResolved		
		
		// Set state after
		prepareStateAfter
			
		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(uuidGeneratorAndResolver) 
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
 		val resolvedChange = unresolvedChange.resolveBefore(uuidGeneratorAndResolver)
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
		val resolvedChange = createUnresolvedChange(affectedEObject, newValue, 0).resolveBefore(uuidGeneratorAndResolver)
		resolvedChange.assertApplyForward
		
		Assert.assertEquals(referenceContent.size, 1)
		val createChange = assertType(resolvedChange.get(0), CreateEObject)
		Assert.assertTrue(referenceContent.contains(createChange.affectedEObject))
		
		// Create and resolve and apply change 2	
		val resolvedChange2 = createUnresolvedChange(affectedEObject, newValue2, 1).resolveBefore(uuidGeneratorAndResolver)
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
		val resolvedChange = createUnresolvedChange(affectedEObject, newValue, 0).resolveBefore(uuidGeneratorAndResolver)
		resolvedChange.assertApplyForward
		
		// Create and resolve change 2
		val resolvedChange2 = createUnresolvedChange(affectedEObject, newValue2, 1).resolveBefore(uuidGeneratorAndResolver)
		resolvedChange2.assertApplyForward
				
		// State after
		assertIsStateAfter
					
		// Apply backward 2
		resolvedChange2.assertApplyBackward
		
		val createChange = assertType(resolvedChange.get(0), CreateEObject)
		val createChange2 = assertType(resolvedChange2.get(0), CreateEObject)
		Assert.assertTrue(referenceContent.contains(createChange.affectedEObject))
		Assert.assertFalse(referenceContent.contains(createChange2.affectedEObject))	
		Assert.assertEquals(referenceContent.size, 1)
			
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
	}
	
	/**
	 * Model is in state after the changes.
	 */
	def private void assertIsStateAfter() {
		Assert.assertEquals(referenceContent.size, 2)
		newValue.assertEqualsOrCopy(referenceContent.get(0))
		newValue2.assertEqualsOrCopy(referenceContent.get(1))
	}
	
	/**
	 * Change is not resolved.
	 */
	def protected static void assertIsNotResolved(List<EChange> changes) {
		EChangeTest.assertIsNotResolved(changes)
		Assert.assertEquals(2, changes.size);
		val createChange = assertType(changes.get(0), CreateEObject);
		val insertChange = assertType(changes.get(1), InsertEReference);
		Assert.assertEquals(insertChange.newValueID, createChange.affectedEObjectID)
	}
	
	/**
	 * Change is resolved.
	 */
	def private static void assertIsResolved(List<EChange> changes, Root affectedEObject,
		NonRoot newNonRoot) {
		changes.assertIsResolved;
		Assert.assertEquals(2, changes.size);
		val createChange = assertType(changes.get(0), CreateEObject);
		val insertChange = assertType(changes.get(1), InsertEReference);
		insertChange.newValue.assertEqualsOrCopy(newNonRoot)
		createChange.affectedEObject.assertEqualsOrCopy(newNonRoot)
		Assert.assertSame(insertChange.affectedEObject, affectedEObject)	
	}
		
	/**
	 * Creates new unresolved change.
	 */
	def private List<EChange> createUnresolvedChange(Root affectedRootObject, NonRoot newNonRoot, int index) {
		return compoundFactory.createCreateAndInsertNonRootChange(affectedRootObject, affectedFeature, newNonRoot, index)
	}
}