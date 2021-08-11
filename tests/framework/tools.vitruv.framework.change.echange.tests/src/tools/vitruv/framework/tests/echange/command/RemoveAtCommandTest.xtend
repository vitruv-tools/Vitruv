package tools.vitruv.framework.tests.echange.command

import allElementTypes.AllElementTypesPackage
import org.eclipse.emf.common.command.Command
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.edit.domain.EditingDomain
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertSame
import tools.vitruv.framework.change.echange.command.RemoveAtCommand
import static extension tools.vitruv.framework.change.echange.command.ChangeCommandUtil.getEditingDomain
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.*

/**
 * Test class for the {@link RemoveAtCommand} which removes 
 * elements at a specific index in a EList.
 */
class RemoveAtCommandTest extends CommandTest {
	var EditingDomain editingDomain
	var EObject owner
	var EStructuralFeature feature
	var EList<Integer> list

	@BeforeEach
	def final void beforeTest() {
		owner = aet.Root
		feature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_EATTRIBUTE
		editingDomain = owner.editingDomain
		list = owner.eGet(feature) as EList<Integer>
		for (var i = 0; i < 10; i++) {
			list.add(i)
		}
	}

	/**
	 * Tests both creation methods and constructors of the class.
	 */
	@Test
	def void createCommandTest() {
		// Test both create methods
		var command = new RemoveAtCommand(editingDomain, list, 2, 3)
		command.assertIsRemoveAtCommand(list, 2, 3)

		var command2 = new RemoveAtCommand(editingDomain, owner, feature, 4, 8)
		command2.assertIsRemoveAtCommand(list, 4, 8)
	}

	/**
	 * Tests the preparation of the command, which decides
	 * whether the command can be executed or not.
	 */
	@Test
	def void prepareTest() {
		// Valid commands
		createRemoveAtCommand(list, 0, 0).assertIsPreparable
		createRemoveAtCommand(list, 4, 4).assertIsPreparable
		createRemoveAtCommand(list, 9, 9).assertIsPreparable

		// Invalid commands
		createRemoveAtCommand(list, 0, 5).assertIsNotPreparable // value != value at index
		createRemoveAtCommand(list, 3, 6).assertIsNotPreparable // value != value at index
		createRemoveAtCommand(list, 0, -1).assertIsNotPreparable // Out of bounds
		createRemoveAtCommand(list, 0, 15).assertIsNotPreparable // Out of bounds
		createRemoveAtCommand(list, -1, 0).assertIsNotPreparable // List doesn't contain the value
		createRemoveAtCommand(list, 44, 0).assertIsNotPreparable // List doesn't contain the value
		createRemoveAtCommand(null, 0, 0).assertIsNotPreparable // List === null
	}

	/**
	 * Tests the execution of the command.
	 */
	@Test
	def void executeTest() {
		createRemoveAtCommand(list, 8, 8).assertRemovedCorrectValueFrom(list)
		createRemoveAtCommand(list, 0, 0).assertRemovedCorrectValueFrom(list)
		createRemoveAtCommand(list, 1, 0).assertRemovedCorrectValueFrom(list)
		createRemoveAtCommand(list, 5, 3).assertRemovedCorrectValueFrom(list)
	}

	/**
	 * Creates a new remove at command.
	 */
	def private RemoveAtCommand createRemoveAtCommand(EList<?> ownerList, Object value, int index) {
		return new RemoveAtCommand(editingDomain, ownerList, value, index)
	}

	/**
	 * Command is instance of RemoveAtCommand and contains the correct values.
	 */
	def private static RemoveAtCommand assertIsRemoveAtCommand(Command command, EList<?> list, Object value,
		int index) {
		var RemoveAtCommand removeAtCommand = command.assertIsInstanceOf(RemoveAtCommand)
		assertSame(removeAtCommand.ownerList, list)
		assertTrue(removeAtCommand.collection.contains(value))
		assertEquals(removeAtCommand.collection.size, 1)
		assertEquals(removeAtCommand.index, index)
		return removeAtCommand
	}

	/**
	 * Executes command and checks if it removed the right value from the list.
	 */
	def private static void assertRemovedCorrectValueFrom(RemoveAtCommand command, EList<?> list) {
		var value = command.collection.get(0)
		var size = list.size
		assertEquals(list.get(command.index), value)
		command.assertExecuteCommand
		assertFalse(list.contains(value))
		assertEquals(list.size, size - 1)
	}
}
