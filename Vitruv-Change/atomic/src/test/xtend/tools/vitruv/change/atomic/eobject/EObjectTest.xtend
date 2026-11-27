package tools.vitruv.change.atomic.eobject

import allElementTypes.Root
import tools.vitruv.change.atomic.EChangeTest
import org.junit.jupiter.api.BeforeEach
import org.eclipse.xtend.lib.annotations.Accessors
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.*

/**
 * Abstract class which is used by the EObject EChange test classes.
 */
abstract class EObjectTest extends EChangeTest {
	@Accessors(PROTECTED_GETTER, PROTECTED_SETTER)
	var Root createdObject = null
	@Accessors(PROTECTED_GETTER)
	var Root createdObject2 = null

	/**
	 * Calls setup of the superclass and creates two new root elements 
	 * which can be created or deleted in the tests.
	 */
	@BeforeEach
	def final void initializeRoots() {
		createdObject = aet.Root.withUuid
		createdObject2 = aet.Root.withUuid
	}
}
