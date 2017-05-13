package tools.vitruv.framework.tests.versioning
import allElementTypes.Root
import org.junit.Assert
import org.junit.Test
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import org.junit.Before
import tools.vitruv.framework.tests.versioning.VersioningTest
import java.io.IOException
import allElementTypes.AllElementTypesFactory

class CreateEObjectTest extends VersioningTest {
	protected var Root createdObject = null;
	protected var Root createdObject2 = null;
	@Before
	override public void beforeTest() throws IOException {
		super.beforeTest
		createdObject = AllElementTypesFactory.eINSTANCE.createRoot()
		createdObject2 = AllElementTypesFactory.eINSTANCE.createRoot()
		prepareStateBefore
	}
	
	/**
	 * Tests applying a {@link CreateEObject} EChange forward by creating a
	 * new EObject and putting it in the staging area.
	 */
	@Test
	def public void applyForwardTest() {
//		// Create change and resolve
//		val resolvedChange = createUnresolvedChange(createdObject).resolveBefore(resourceSet)
//			as CreateEObject<Root>
//			
//		val 
//		
//		// State after
//		assertIsStateAfter(createdObject)
//		
//		// Now another change would take the object and inserts it in a resource
//		prepareStateBefore
//		
//		// Create change and resolve 2
//		val resolvedChange2 = createUnresolvedChange(createdObject2).resolveBefore(resourceSet)
//			as CreateEObject<Root>
//			
//		// Apply forward 2
////		resolvedChange2.assertApplyForward
//		
//		// State after
//		assertIsStateAfter(createdObject2)
	}
	
	
	/**
	 * Sets the state of the model before the change.
	 */
	def private void prepareStateBefore() {
		stagingArea.clear
		assertIsStateBefore
	}
	
	/**
	 * Model is in state before the change.
	 */
	def private void assertIsStateBefore() {
		Assert.assertTrue(stagingArea.empty)
	}
	
//	/**
//	 * Model is in state after the change.
//	 */
//	def private void assertIsStateAfter(Root object) {
//		Assert.assertFalse(stagingArea.empty)
////		object.assertEqualsOrCopy(stagingArea.peek)
//	}
//	
//	/**
//	 * Creates new unresolved change.
//	 */
//	def private CreateEObject<Root> createUnresolvedChange(Root newObject) {
//		return atomicFactory.createCreateEObjectChange(newObject, resource)
//	}
		
}