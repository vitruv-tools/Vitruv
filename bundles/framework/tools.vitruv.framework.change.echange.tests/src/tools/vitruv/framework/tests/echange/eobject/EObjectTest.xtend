package tools.vitruv.framework.tests.echange.eobject

import allElementTypes.AllElementTypesFactory
import allElementTypes.Root
import org.eclipse.emf.ecore.EObject
import org.junit.Before
import tools.vitruv.framework.tests.echange.EChangeTest

/**
 * Abstract class which is used by the EObject EChange test classes.
 */
public abstract class EObjectTest extends EChangeTest {
	protected var Root createdObject = null;
	protected var Root createdObject2 = null;
	
	protected static val Integer DEFAULT_CREATED_OBJECT_1_INTEGER_VALUE = 111
	protected static val Integer DEFAULT_CREATED_OBJECT_2_INTEGER_VALUE = 222
		
	/**
	 * Calls setup of the superclass and creates two new root elements 
	 * which can be created or deleted in the tests.
	 */
	@Before
	override void beforeTest() {
		super.beforeTest()
		createdObject = AllElementTypesFactory.eINSTANCE.createRoot()
		createdObject.singleValuedEAttribute = DEFAULT_CREATED_OBJECT_1_INTEGER_VALUE
		createdObject2 = AllElementTypesFactory.eINSTANCE.createRoot()
		createdObject2.singleValuedEAttribute = DEFAULT_CREATED_OBJECT_2_INTEGER_VALUE
	}
	
	/**
	 * Prepares the staging area and object in progrss for a test. 
	 * Inserts a new element to the staging area which will be used in the test.
	 * @param object The EObject which will be inserted in the staging area. 
	 * 		Clears and sets the 0th element.
	 */	
	protected def void prepareStagingArea(EObject object) {
		stagingArea.contents.clear
		stagingArea.contents.add(object)
	}
}