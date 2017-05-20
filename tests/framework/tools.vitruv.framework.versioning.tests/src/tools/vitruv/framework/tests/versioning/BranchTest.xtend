package tools.vitruv.framework.tests.versioning

import allElementTypes.AllElementTypesFactory
import allElementTypes.Root
import java.io.IOException
//import org.eclipse.emf.common.util.BasicEList
//import org.eclipse.emf.common.util.EList
import org.junit.Assert
import org.junit.Before
import org.junit.Test
//import tools.vitruv.framework.change.echange.EChange
//import tools.vitruv.framework.change.echange.eobject.CreateEObject

//import static org.hamcrest.CoreMatchers.equalTo
//import static org.hamcrest.CoreMatchers.hasItem
//import static org.junit.Assert.assertThat
//import tools.vitruv.framework.versioning.repository.RepositoryFactory

class BranchTest extends VersioningTest {
	protected var Root createdObject = null;
	protected var Root createdObject2 = null;
	
	@Test
	def public void branchTest() {
//		val repo = RepositoryFactory.eINSTANCE.createRepository
//		
//		val authorA = repo.createAuthor("Name","Email")
//		val initialCommit = repo.initialCommit
//		
//		assertThat(initialCommit.changes.length, equalTo(0))
//		// Create change and resolve
//		val resolvedChange = createUnresolvedChange(createdObject).resolveBefore(resourceSet) as CreateEObject<Root>
//			
//		val EList<EChange> changes = new BasicEList<EChange>()
//		changes.add(resolvedChange)
//
//		val commit = authorA.createSimpleCommit("First commit", initialCommit,changes)		
//		
		
		
//	try {
//		resource.save(null)
//	} catch (IOException exc) {
//		throw new RuntimeException("auto-generated try/catch", exc)
//	}
//	assertThat(commit.changes, hasItem(resolvedChange))
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

	@Before
	override public void beforeTest() throws IOException {
		super.beforeTest
		createdObject = AllElementTypesFactory.eINSTANCE.createRoot()
		createdObject2 = AllElementTypesFactory.eINSTANCE.createRoot()
		prepareStateBefore
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
	
	/**
	 * Creates new unresolved change.
	 */
//	def private CreateEObject<Root> createUnresolvedChange(Root newObject) {
//		return atomicFactory.createCreateEObjectChange(newObject, resource)
//	}
		
}