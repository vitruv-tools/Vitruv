package tools.vitruv.framework.tests.echange.feature.attribute

import allElementTypes.Root
import org.eclipse.emf.ecore.EAttribute
import org.junit.Before
import tools.vitruv.framework.tests.echange.EChangeTest
import allElementTypes.AllElementTypesPackage

/**
 * Abstract class which is used by insert and remove attribute test classes.
 */
public abstract class InsertRemoveEAttributeTest extends EChangeTest {
	protected var Root defaultAffectedEObject = null
	protected var EAttribute defaultAffectedFeature = null
	
	protected static val Integer DEFAULT_NEW_VALUE = 111
	protected static val Integer DEFAULT_NEW_VALUE_2 = 222
	protected static val Integer DEFAULT_INDEX = 0
	
	/**
	 * Sets the default object, feature and values for 
	 * the the insert and remove tests.
	 */
	@Before
	override public void beforeTest() {
		super.beforeTest()
		defaultAffectedEObject = rootObject1
		defaultAffectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_EATTRIBUTE
	}
}