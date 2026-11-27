package tools.vitruv.change.atomic.feature.reference

import allElementTypes.NonRoot
import allElementTypes.Root
import tools.vitruv.change.atomic.EChangeTest
import org.junit.jupiter.api.BeforeEach
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.*
import org.eclipse.xtend.lib.annotations.Accessors

/**
 * 
 * Abstract class which is used by all test classes for references.
 */
abstract class ReferenceEChangeTest extends EChangeTest {
	@Accessors(PROTECTED_GETTER)
	var Root affectedEObject
	@Accessors(PROTECTED_GETTER, PROTECTED_SETTER)
	var NonRoot newValue
	@Accessors(PROTECTED_GETTER, PROTECTED_SETTER)
	var NonRoot newValue2

	/**
	 * Sets the default object and new value for tests.
	 */
	@BeforeEach
	def final void beforeTest() {
		affectedEObject = rootObject
		newValue = aet.NonRoot.withUuid
		newValue2 = aet.NonRoot.withUuid
	}

	/**
	 * Prepares the resource and adds the new values.
	 */
	def protected void prepareResource() {
		resource.contents.add(newValue)
		resource.contents.add(newValue2)
	}
}
