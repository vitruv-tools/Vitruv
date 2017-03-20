package tools.vitruv.framework.tests.echange.feature.attribute

import allElementTypes.Root
import org.eclipse.emf.ecore.EAttribute
import org.junit.Before
import tools.vitruv.framework.tests.echange.EChangeTest
import allElementTypes.AllElementTypesPackage
import org.eclipse.emf.common.util.EList

/**
 * Abstract class which is used by insert and remove attribute test classes.
 */
public abstract class InsertRemoveEAttributeTest extends EChangeTest {
	protected var Root affectedEObject = null
	protected var EAttribute affectedFeature = null
	protected var EList<Integer> attributeContent = null
	protected var index = DEFAULT_INDEX
	
	protected static val Integer NEW_VALUE = 111
	protected static val Integer NEW_VALUE_2 = 222
	protected static val Integer NEW_VALUE_3 = 333
	protected static val Integer DEFAULT_INDEX = 0
	
	/**
	 * Sets the default object, feature and values for 
	 * the the insert and remove tests.
	 */
	@Before
	override public void beforeTest() {
		super.beforeTest()
		affectedEObject = rootObject
		affectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_EATTRIBUTE
		attributeContent = affectedEObject.eGet(affectedFeature) as EList<Integer>
	}
}