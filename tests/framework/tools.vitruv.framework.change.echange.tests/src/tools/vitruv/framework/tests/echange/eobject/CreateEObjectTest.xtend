package tools.vitruv.framework.tests.echange.eobject

import allElementTypes.Root
import org.junit.Assert
import org.junit.Test
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import org.junit.Before

import static extension tools.vitruv.framework.tests.echange.util.EChangeAssertHelper.*

/**
 * Test class for the concrete {@link CreateEObject} EChange,
 * which creates a new EObject and puts it in the staging area.
 */
class CreateEObjectTest extends EObjectTest {
	@Before
	override public void beforeTest() {
		super.beforeTest
		prepareStateBefore
	}
	
	/**
	 * Tests whether resolving the {@link CreateEObjectTest} EChange returns
	 * the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange(createdObject)
			
		// Resolve		
 		val resolvedChange = unresolvedChange.resolveBefore(resourceSet)
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}
	
	/**
	 * Tests applying a {@link CreateEObject} EChange forward by creating a
	 * new EObject and putting it in the staging area.
	 */
	@Test
	def public void applyForwardTest() {
		// Create change and resolve
		val resolvedChange = createUnresolvedChange(createdObject).resolveBefore(resourceSet)
			as CreateEObject<Root>
			
		// Apply forward
		resolvedChange.assertApplyForward
		
		// State after
		assertIsStateAfter(createdObject)
		
		// Now another change would take the object and inserts it in a resource
		prepareStateBefore
		
		// Create change and resolve 2
		val resolvedChange2 = createUnresolvedChange(createdObject2).resolveBefore(resourceSet)
			as CreateEObject<Root>
			
		// Apply forward 2
		resolvedChange2.assertApplyForward
		
		// State after
		assertIsStateAfter(createdObject2)
	}
	
	/**
	 * Tests applying a {@link CreateEObject} EChange backward 
	 * by removing a newly created object from the staging area.
	 */
	@Test
	def public void applyBackwardTest() {
		// Set state after
		prepareStateAfter(createdObject)

		// Create change and resolve
		val resolvedChange = createUnresolvedChange(createdObject).resolveAfter(resourceSet)
			as CreateEObject<Root>
		
		// Apply backward
		resolvedChange.assertApplyBackward
	}
	
	/**
	 * Sets the state of the model before the change.
	 */
	def private void prepareStateBefore() {
		stagingArea.clear
		assertIsStateBefore
	}
	
	/** 
	 * Sets the state of the model after the change.
	 */
	def private void prepareStateAfter(Root object) {
		stagingArea.clear
		stagingArea.add(object)
		assertIsStateAfter(object)
	}
	
	/**
	 * Model is in state before the change.
	 */
	def private void assertIsStateBefore() {
		Assert.assertTrue(stagingArea.empty)
	}
	
	/**
	 * Model is in state after the change.
	 */
	def private void assertIsStateAfter(Root object) {
		Assert.assertFalse(stagingArea.empty)
		object.assertEqualsOrCopy(stagingArea.peek)
	}
	
	/**
	 * Creates new unresolved change.
	 */
	def private CreateEObject<Root> createUnresolvedChange(Root newObject) {
		return atomicFactory.createCreateEObjectChange(newObject, resource, null)
	}
		
}