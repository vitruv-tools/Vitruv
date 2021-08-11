package tools.vitruv.framework.tests.echange.feature.attribute

import allElementTypes.Root
import org.eclipse.emf.ecore.EAttribute
import tools.vitruv.framework.tests.echange.EChangeTest
import allElementTypes.AllElementTypesPackage
import org.eclipse.emf.common.util.EList
import org.junit.jupiter.api.BeforeEach
import org.eclipse.xtend.lib.annotations.Accessors

/**
 * Abstract class which is used by insert and remove attribute test classes.
 */
abstract class InsertRemoveEAttributeTest extends EChangeTest {
	@Accessors(PROTECTED_GETTER)
	var Root affectedEObject
	@Accessors(PROTECTED_GETTER)
	var EAttribute affectedFeature
	@Accessors(PROTECTED_GETTER)
	var EList<Integer> attributeContent

	protected static val Integer NEW_VALUE = 111
	protected static val Integer NEW_VALUE_2 = 222
	protected static val Integer NEW_VALUE_3 = 333

	@BeforeEach
	def void beforeTest() {
		affectedEObject = rootObject
		affectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_EATTRIBUTE
		attributeContent = affectedEObject.eGet(affectedFeature) as EList<Integer>
	}
}
