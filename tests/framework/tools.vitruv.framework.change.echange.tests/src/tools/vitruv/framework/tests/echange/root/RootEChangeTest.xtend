package tools.vitruv.framework.tests.echange.root

import allElementTypes.Root
import tools.vitruv.framework.tests.echange.EChangeTest
import org.junit.jupiter.api.BeforeEach
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.*
import org.eclipse.xtend.lib.annotations.Accessors

/**
 * Abstract class which is extended by the Root EChange test classes.
 */
abstract class RootEChangeTest extends EChangeTest {
	@Accessors(PROTECTED_GETTER)
	var Root newRootObject = null
	@Accessors(PROTECTED_GETTER)
	var Root newRootObject2 = null

	/**
	 * Calls setup of superclass and creates two new root elements 
	 * which can be used in the tests.
	 */
	@BeforeEach
	def final void beforeTest() {
		newRootObject = aet.Root
		uuidGeneratorAndResolver.generateUuid(newRootObject)
		newRootObject2 = aet.Root
		uuidGeneratorAndResolver.generateUuid(newRootObject2)
	}
}
