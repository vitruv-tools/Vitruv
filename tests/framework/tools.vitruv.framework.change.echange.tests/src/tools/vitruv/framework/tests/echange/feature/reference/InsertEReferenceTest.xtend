package tools.vitruv.framework.tests.echange.feature.reference

import allElementTypes.AllElementTypesPackage
import allElementTypes.NonRoot
import allElementTypes.Root
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EReference
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference

import static extension tools.vitruv.framework.tests.echange.util.EChangeAssertHelper.*
import static extension tools.vitruv.framework.change.echange.resolve.EChangeResolverAndApplicator.*

/**
 * Test class for the concrete {@link InsertEReferenceValue} EChange,
 * which inserts a reference in a multivalued attribute.
 */
public class InsertEReferenceTest extends ReferenceEChangeTest {
	protected var EReference affectedFeature = null
	protected var EList<NonRoot> referenceContent = null
	
	@Before
	override public void beforeTest() {
		super.beforeTest()
		resourceContent = resource.contents
	}
			
	/**
	 * Test resolves a {@link InsertEReference} EChange with correct parameters on
	 * a model which is in state before the change.
	 * The affected feature is a non containment reference, so the new reference
	 * is in the resource already.
	 */
	@Test
	def public void resolveBeforeNonContainmentTest() {
		// Set state before
		isNonContainmentTest
		
		// Create change
		val unresolvedChange = createUnresolvedChange(newValue, 0)
		unresolvedChange.assertIsNotResolved(affectedEObject, newValue)
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(uuidGeneratorAndResolver) 
			as InsertEReference<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, newValue)
	}
	
	/**
	 * Test resolves a {@link InsertEReference} EChange with correct parameters on
	 * a model which is in state before the change.
	 * The affected feature is a containment reference, so the new reference 
	 * is in the staging area.
	 */
	@Test
	def public void resolveBeforeContainmentTest() {
		// Set state before
		isContainmentTest
		
		// Create change
		val unresolvedChange = createUnresolvedChange(newValue, 0)
		unresolvedChange.assertIsNotResolved(affectedEObject, newValue)
			
		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(uuidGeneratorAndResolver) as InsertEReference<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, newValue)				
	}
	
	/**
	 * Test resolves a {@link InsertEReference} EChange with correct parameters on 
	 * a model which is in state after the change.
	 * The affected feature is a non containment reference, so the inserted
	 * reference is already a root element.
	 */
	@Test
	def public void resolveAfterNonContainmentTest() {
		// Set state before
		isNonContainmentTest
		
		// Create change
		val unresolvedChange = createUnresolvedChange(newValue, 0)
		unresolvedChange.assertIsNotResolved(affectedEObject, newValue)
			
		// Set state after
		prepareReference(newValue)
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(uuidGeneratorAndResolver) as InsertEReference<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, newValue)			
	}
	
	/**
	 * Test resolves a {@link InsertEReference} EChange with correct parameters on
	 * a model which is in the state after the change.
	 * The affected feature is a containment reference, so the inserted 
	 * reference is in the resource after the change.
	 */
	@Test
	def public void resolveAfterContainmentTest() {
		// Set state before
		isContainmentTest
		
		// Create change
		val unresolvedChange = createUnresolvedChange(newValue, 0)
		unresolvedChange.assertIsNotResolved(affectedEObject, newValue)		
		
		// Set state after
		prepareReference(newValue)
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(uuidGeneratorAndResolver) as InsertEReference<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, newValue)						
	}
	
	/**
	 * Tests whether resolving the {@link InsertEReference} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		// Set state before
		isNonContainmentTest
		
		// Create change
		val unresolvedChange = createUnresolvedChange(newValue, 0)
			
		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(uuidGeneratorAndResolver)
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}
	
	 /**
	  * Tests applying the {@link InsertEReference} EChange forward by 
	  * inserting new values in a multivalued reference.
	  * The affected feature is a non containment reference, so the 
	  * new value is already in the resource.
	  */
	@Test
	def public void applyForwardNonContainmentTest() {
		// Set state before
		isNonContainmentTest
		
		// Create change (resolved)
		val resolvedChange = createUnresolvedChange(newValue, 0).resolveBefore(uuidGeneratorAndResolver)
	 	
	 	// Apply forward
	 	resolvedChange.assertApplyForward
	 	Assert.assertEquals(referenceContent.size, 1)
	 	Assert.assertSame(referenceContent.get(0), newValue)

	 	// Create change 2 (resolved)
		val resolvedChange2 = createUnresolvedChange(newValue2, 1).resolveBefore(uuidGeneratorAndResolver)
	 	
	 	// Apply forward 2
	 	resolvedChange2.assertApplyForward
	 	
	 	// State after
	 	assertIsStateAfter
	}
	
	/**
	 * Tests applying the {@link InsertEReference} EChange forward by 
	 * inserting new values from a multivalued reference.
	 * The affected feature is a containment reference, so the
	 * new value is from the staging area.
	 */
	@Test
	def public void applyForwardContainmentTest() {
		// Set state before
		isContainmentTest
		
		// Create change (resolved)		
		val resolvedChange = createUnresolvedChange(newValue, 0).resolveBefore(uuidGeneratorAndResolver)
		
		// Apply forward
	 	resolvedChange.assertApplyForward
	 	
		Assert.assertEquals(referenceContent.size, 1)
		Assert.assertSame(referenceContent.get(0), newValue)
		
		// Prepare and create change 2
		val resolvedChange2 = createUnresolvedChange(newValue2, 1).resolveBefore(uuidGeneratorAndResolver)
	 	
	 	// Apply forward 2
	 	resolvedChange2.assertApplyForward
	 	
		// State after
		assertIsStateAfter
	}
	
	/**
	 * Tests applying two {@link InsertEReference} EChanges backward by
	 * removing new added values from a multivalued reference.
	 * The affected feature is a non containment reference, so the
	 * removed values are already in the resource.
	 */
	@Test
	def public void applyBackwardNonContainmentTest() {
		// Set state before
		isNonContainmentTest
		
		// Create change and apply forward
		val resolvedChange = createUnresolvedChange(newValue, 0).resolveBefore(uuidGeneratorAndResolver)
	 	Assert.assertTrue(resolvedChange.applyForward)
	 	
	 	// Create change 2 and apply forward			
		val resolvedChange2 = createUnresolvedChange(newValue2, 1).resolveBefore(uuidGeneratorAndResolver)
	 	Assert.assertTrue(resolvedChange2.applyForward)			
		
		// State after
		assertIsStateAfter
		
		// Apply backward 2
		resolvedChange2.assertApplyBackward
		
		Assert.assertEquals(referenceContent.size, 1)
		Assert.assertSame(referenceContent.get(0), newValue)
		
		// Apply backward 1
		resolvedChange.assertApplyBackward
		
		// State before
		assertIsStateBefore
	}
	
	/**
	 * Tests applying two {@link InsertEReference} EChanges backward by 
	 * removing new added values from a multivalued reference.
	 * The affected feature is a containment reference, so the
	 * removed values will be placed in the staging area after removing them.
	 */
	@Test
	def public void applyBackwardContainmentTest() {
		// Set state before
		isContainmentTest

		// Create change and apply forward
		val resolvedChange = createUnresolvedChange(newValue, 0).resolveBefore(uuidGeneratorAndResolver)
	 	resolvedChange.assertApplyForward
	 	
	 	// Create change 2 and apply forward			
		val resolvedChange2 = createUnresolvedChange(newValue2, 1).resolveBefore(uuidGeneratorAndResolver)
	 	resolvedChange2.assertApplyForward	
	 	
	 	// State after
	 	assertIsStateAfter	
		
		// Apply backward 2
		resolvedChange2.assertApplyBackward
		
		Assert.assertEquals(referenceContent.size, 1)
		Assert.assertSame(referenceContent.get(0), newValue)		
		
		// Apply backward 1
		resolvedChange.assertApplyBackward
		
		// State before
		assertIsStateBefore	
	}
	
	/**
	 * Tests the {@link InsertEReference} EChange with invalid index.
	 */
	@Test
	def public void invalidIndexTest() {
		// Set state before
		isNonContainmentTest
		var index = 5 // Valid index in empty list is only 0
		Assert.assertEquals(referenceContent.size, 0)
		
		// Create and resolve
		val resolvedChange = createUnresolvedChange(newValue, index).resolveBefore(uuidGeneratorAndResolver)
			as InsertEReference<Root, NonRoot>		
		resolvedChange.assertIsResolved(affectedEObject, newValue)	
		
		// Apply	
	 	resolvedChange.assertCannotBeAppliedForward	 	
		resolvedChange.assertCannotBeAppliedBackward
	}
	
	/**
	 * Tests an affected object which has no such reference.
	 */
	@Test
	def public void invalidAttributeTest() {
		// Set state before
		isNonContainmentTest
		val invalidAffectedEObject = newValue2 // NonRoot element

		// Create and resolve change
		val resolvedChange = atomicFactory.<NonRoot, NonRoot>createInsertReferenceChange
			(invalidAffectedEObject, affectedFeature, newValue, 0).
			resolveBefore(uuidGeneratorAndResolver)
		Assert.assertTrue(resolvedChange.isResolved)
		
		// NonRoot has no such feature
		Assert.assertEquals(invalidAffectedEObject.eClass.getFeatureID(affectedFeature), -1)
		
		// Apply
	 	resolvedChange.assertCannotBeAppliedForward	 	
		resolvedChange.assertCannotBeAppliedBackward
	}
	
	/**
	 * Starts a test with a containment feature and sets state before.
	 */	
	def private void isContainmentTest() {
		affectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE
		referenceContent = affectedEObject.eGet(affectedFeature) as EList<NonRoot>
		assertIsStateBefore
	}
	
	/**
	 * Starts a test with a non containment feature and sets state before.
	 */	
	def private void isNonContainmentTest() {
		affectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE	
		referenceContent = affectedEObject.eGet(affectedFeature) as EList<NonRoot>
		prepareResource	
		assertIsStateBefore
	}
	
	/**
	 * Prepares the multivalued reference used in the tests 
	 * and fills it with a new value.
	 */
	def private void prepareReference(NonRoot object) {
		referenceContent.add(object)
	}
	
	/**
	 * Model is in state before the change.
	 */
	def private void assertIsStateBefore() {
		Assert.assertEquals(referenceContent.size, 0)
	}
	
	/**
	 * Model is in state after the change.
	 */
	def private void assertIsStateAfter() {
		Assert.assertEquals(referenceContent.size, 2)	
		newValue.assertEqualsOrCopy(referenceContent.get(0))
		newValue2.assertEqualsOrCopy(referenceContent.get(1))
	}
	
	/**
	 * Change is not resolved.
	 */
	def private static void assertIsNotResolved(InsertEReference<Root, NonRoot> change, 
		Root affectedEObject, NonRoot newValue) {
		Assert.assertFalse(change.isResolved)
		Assert.assertNotSame(change.affectedEObject, affectedEObject)
		Assert.assertNotSame(change.newValue, newValue)			
	}

	/**
	 * Change is resolved.
	 */
	def private static void assertIsResolved(InsertEReference<Root, NonRoot> change, 
		Root affectedEObject, NonRoot newValue) {
		Assert.assertTrue(change.isResolved)
		Assert.assertSame(change.affectedEObject, affectedEObject)
		Assert.assertSame(change.newValue, newValue)			
	}

	/**
	 * Creates new unresolved change.
	 */
	def private InsertEReference<Root, NonRoot> createUnresolvedChange(NonRoot newValue, int index) {
		return atomicFactory.createInsertReferenceChange(affectedEObject, affectedFeature, newValue, index)	
	}
}