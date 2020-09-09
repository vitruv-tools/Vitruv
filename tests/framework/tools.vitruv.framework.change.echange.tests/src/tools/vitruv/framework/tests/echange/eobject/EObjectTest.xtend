package tools.vitruv.framework.tests.echange.eobject

import allElementTypes.AllElementTypesFactory
import allElementTypes.Root
import org.junit.Before
import tools.vitruv.framework.tests.echange.EChangeTest

/**
 * Abstract class which is used by the EObject EChange test classes.
 */
abstract class EObjectTest extends EChangeTest {
	protected var Root createdObject = null;
	protected var Root createdObject2 = null;
		
	/**
	 * Calls setup of the superclass and creates two new root elements 
	 * which can be created or deleted in the tests.
	 */
	@Before
	override void beforeTest() {
		super.beforeTest()
		createdObject = AllElementTypesFactory.eINSTANCE.createRoot()
		createdObject2 = AllElementTypesFactory.eINSTANCE.createRoot()
	}
}