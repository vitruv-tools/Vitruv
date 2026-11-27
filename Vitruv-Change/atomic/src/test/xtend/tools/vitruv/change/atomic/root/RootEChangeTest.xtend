package tools.vitruv.change.atomic.root

import allElementTypes.Root
import tools.vitruv.change.atomic.EChangeTest
import org.junit.jupiter.api.BeforeEach
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.*
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
	 * Creates two new root elements which can be used in the tests.
	 */
	@BeforeEach
	def void beforeTest() {
		newRootObject = aet.Root.withUuid
		newRootObject2 = aet.Root.withUuid
	}
}
