package tools.vitruv.framework.tests.echange.feature.attribute

import allElementTypes.Root
import org.eclipse.emf.ecore.EAttribute
import tools.vitruv.framework.tests.echange.EChangeTest
import allElementTypes.AllElementTypesPackage
import org.eclipse.emf.common.util.EList
import org.junit.jupiter.api.BeforeEach

/**
 * Abstract class which is used by insert and remove attribute test classes.
 */
abstract class InsertRemoveEAttributeTest extends EChangeTest {
	protected var Root affectedEObject = null
	protected var EAttribute affectedFeature = null
	protected var EList<Integer> attributeContent = null
	
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