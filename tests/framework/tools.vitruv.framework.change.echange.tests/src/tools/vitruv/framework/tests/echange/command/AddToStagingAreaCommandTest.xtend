package tools.vitruv.framework.tests.echange.command

import org.eclipse.emf.ecore.EObject
import org.junit.Assert
import org.junit.Test
import tools.vitruv.framework.change.echange.command.AddToStagingAreaCommand
import tools.vitruv.framework.change.echange.resolve.StagingArea

/**
 * Test class for the {@link AddToStagingAreaCommand}, which 
 * adds a new object the a staging area.
 */
class AddToStagingAreaCommandTest extends StagingAreaCommandTest {
	/**
	 * Tests the preparation of the command, which decides
	 * whether the command can be executed or not.
	 */
	@Test
	def public void prepareTest() {
		// Valid commands
		createAddToStagingAreaCommand(stagingArea, object).assertIsPreparable
		createAddToStagingAreaCommand(stagingArea, object2).assertIsPreparable
			
		// Invalid commands
		createAddToStagingAreaCommand(null, object).assertIsNotPreparable
		createAddToStagingAreaCommand(stagingArea, null).assertIsNotPreparable
	}
	
	/**
	 * Tests the execution of the command.
	 */
	@Test
	def public void executeTest() {
		createAddToStagingAreaCommand(stagingArea, object).assertAddedObjectToStagingArea(stagingArea, object)
		createAddToStagingAreaCommand(stagingArea, object2).assertAddedObjectToStagingArea(stagingArea, object2)
	}
	
	/**
	 * Creates a new command.
	 */
	def private AddToStagingAreaCommand createAddToStagingAreaCommand(StagingArea stagingArea, EObject object) {
		return new AddToStagingAreaCommand(editingDomain, stagingArea, object)
	}	
	
	/**
	 * The command added the correct value to the correct staging area.
	 */
	def private assertAddedObjectToStagingArea(AddToStagingAreaCommand command, StagingArea stagingArea, EObject object) {
		Assert.assertTrue(stagingArea.empty)
		command.assertExecuteCommand
		Assert.assertFalse(stagingArea.empty)
		Assert.assertSame(stagingArea.poll, object)
	}
}