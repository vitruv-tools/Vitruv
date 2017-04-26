package tools.vitruv.framework.tests.versioning

import allElementTypes.AllElementTypesFactory
import allElementTypes.Root
import java.io.IOException
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.common.util.EList
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.versioning.Author
import tools.vitruv.framework.versioning.commit.Commit
import tools.vitruv.framework.versioning.commit.InitialCommit
import tools.vitruv.framework.versioning.commit.impl.InitialCommitImpl
import tools.vitruv.framework.versioning.commit.impl.SimpleCommitImpl
import tools.vitruv.framework.versioning.impl.AuthorImpl

import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.hasItem
import static org.junit.Assert.assertThat

class BranchTest extends VersioningTest {
	protected var Root createdObject = null;
	protected var Root createdObject2 = null;
	
	@Test
	def public void branchTest() {
		val authorA = new AuthorImpl("a", "AuthorA") as Author
//		val authorB = new AuthorImpl("b", "AuthorB") as Author
		
		val initialCommit = new InitialCommitImpl(authorA) as InitialCommit
		assertThat(initialCommit.changes.length, equalTo(0))
		// Create change and resolve
		val resolvedChange = createUnresolvedChange(createdObject).resolveBefore(resourceSet) as CreateEObject<Root>
			
		val EList<EChange> changes = new BasicEList<EChange>()
		changes.add(resolvedChange)

		val commit = new SimpleCommitImpl(changes, "First commit", authorA, initialCommit) as Commit		
		
		assertThat(commit.changes, hasItem(resolvedChange))
		print(resourceSet)
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
	def private CreateEObject<Root> createUnresolvedChange(Root newObject) {
		return atomicFactory.createCreateEObjectChange(newObject, resource)
	}
		
}