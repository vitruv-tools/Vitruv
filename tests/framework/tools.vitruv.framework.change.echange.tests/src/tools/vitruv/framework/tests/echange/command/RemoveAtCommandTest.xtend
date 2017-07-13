package tools.vitruv.framework.tests.echange.command

import allElementTypes.AllElementTypesFactory
import allElementTypes.AllElementTypesPackage
import org.eclipse.emf.common.command.Command
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.edit.domain.EditingDomain
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.command.RemoveAtCommand
import tools.vitruv.framework.change.echange.util.EChangeUtil

/**
 * Test class for the {@link RemoveAtCommand} which removes 
 * elements at a specific index in a EList.
 */
class RemoveAtCommandTest extends CommandTest {
	protected var EditingDomain editingDomain = null
	protected var EObject owner = null
	protected var EStructuralFeature feature = null
	protected var EList<Integer> list = null
	
	@Before
	def public void beforeTest() {
		owner = AllElementTypesFactory.eINSTANCE.createRoot
		feature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_EATTRIBUTE
		editingDomain = EChangeUtil.getEditingDomain(owner)
		list = owner.eGet(feature) as EList<Integer>
		for (var i = 0; i < 10; i++) {
			list.add(i)
		}
	}
	
	/**
	 * Tests both creation methods and constructors of the class.
	 */
	@Test
	def public void createCommandTest() {
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
	def public void prepareTest() {
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
	def public void executeTest() {
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
	def private static RemoveAtCommand assertIsRemoveAtCommand(Command command, EList<?> list, Object value, int index) {
		var RemoveAtCommand removeAtCommand = command.assertIsInstanceOf(RemoveAtCommand)
		Assert.assertSame(removeAtCommand.ownerList, list)
		Assert.assertTrue(removeAtCommand.collection.contains(value))
		Assert.assertEquals(removeAtCommand.collection.size, 1)
		Assert.assertEquals(removeAtCommand.index, index)
		return removeAtCommand
	}
	
	/**
	 * Executes command and checks if it removed the right value from the list.
	 */
	def private static void assertRemovedCorrectValueFrom(RemoveAtCommand command, EList<?> list) {
		var value = command.collection.get(0)
		var size = list.size
		Assert.assertEquals(list.get(command.index), value)
		command.assertExecuteCommand
		Assert.assertFalse(list.contains(value))
		Assert.assertEquals(list.size, size - 1)
	}
}