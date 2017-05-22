package tools.vitruv.framework.tests.echange.command

import org.eclipse.emf.ecore.EObject
import org.junit.Assert
import org.junit.Test
import tools.vitruv.framework.change.echange.command.RemoveFromStagingAreaCommand
import tools.vitruv.framework.change.echange.resolve.StagingArea

/**
 * Test class for the {@link RemoveFromStagingAreaCommand},
 * which removes an object from a staging area.
 */
class RemoveFromStagingAreaCommandTest extends StagingAreaCommandTest {
	/**
	 * Tests the preparation of the command, which decides
	 * whether the command can be executed or not.
	 */
	@Test
	def public void prepareTest() {
		// Valid commands
		stagingArea.add(object)
		createRemoveFromStagingAreaCommand(stagingArea, object).assertIsPreparable
		stagingArea.clear
		
		stagingArea.add(object2)
		createRemoveFromStagingAreaCommand(stagingArea, object2).assertIsPreparable
		stagingArea.clear
		
		// Invalid commands
		createRemoveFromStagingAreaCommand(stagingArea, object).assertIsNotPreparable // empty staging area
		stagingArea.add(object)
		createRemoveFromStagingAreaCommand(null, object).assertIsNotPreparable // staging area null
		createRemoveFromStagingAreaCommand(stagingArea, null).assertIsNotPreparable // object null
		createRemoveFromStagingAreaCommand(stagingArea, object2).assertIsNotPreparable // wrong object at head of staging area
	}
	
	/**
	 * Tests the execution of the command.
	 */
	@Test
	def public void executeTest() {
		createRemoveFromStagingAreaCommand(stagingArea, object).assertRemovedObjectFromStagingArea(stagingArea, object)
		createRemoveFromStagingAreaCommand(stagingArea, object2).assertRemovedObjectFromStagingArea(stagingArea, object2)
	}
	
	/**
	 * Creates a new command.
	 */
	def private RemoveFromStagingAreaCommand createRemoveFromStagingAreaCommand(StagingArea stagingArea, EObject object) {
		return new RemoveFromStagingAreaCommand(editingDomain, stagingArea, object)
	}
	
	/**
	 * The command removed the correct value from the correct staging area.
	 */
	def private assertRemovedObjectFromStagingArea(RemoveFromStagingAreaCommand command, StagingArea stagingArea, EObject object) {
		stagingArea.add(object)
		Assert.assertFalse(stagingArea.empty)
		command.assertExecuteCommand
		Assert.assertTrue(stagingArea.empty)
	}
}