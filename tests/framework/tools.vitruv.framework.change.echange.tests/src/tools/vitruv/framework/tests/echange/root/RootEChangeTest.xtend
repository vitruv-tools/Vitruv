package tools.vitruv.framework.tests.echange.root

import allElementTypes.AllElementTypesFactory
import allElementTypes.Root
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.junit.Before
import tools.vitruv.framework.tests.echange.EChangeTest

/**
 * Abstract class which is extended by the Root EChange test classes.
 */
public abstract class RootEChangeTest extends EChangeTest {
	protected var Root newRootObject = null;
	protected var Root newRootObject2 = null;
	protected var EList<EObject> resourceContent = null;
	protected var index = DEFAULT_INDEX
	
	protected static val DEFAULT_INDEX = 1
	
	/**
	 * Calls setup of superclass and creates two new root elements 
	 * which can be used in the tests.
	 */
	@Before
	override void beforeTest() {
		super.beforeTest()
		newRootObject = AllElementTypesFactory.eINSTANCE.createRoot()
		newRootObject2 = AllElementTypesFactory.eINSTANCE.createRoot()
		resourceContent = resourceSet.getResource(fileUri, false).contents
	}
	
	/**
	 * Prepares the staging area and the object which is in progress
	 * for a test. Inserts a new element to the staging area which will 
	 * be used in the test.
	 * @param object The EObject which will be inserted in the staging area. 
	 * 		Clears and sets the 0th element.
	 */
	protected def void prepareStagingArea(EObject object) {
		stagingArea.contents.clear
		stagingArea.contents.add(object)
	}
	
	/**
	 * Prepares the resource for a test. Inserts a new element
	 * to the resource which will be used in the test.
	 * @param object The EObject which will be inserted in the resource of the tests.
	 * @param index The index where the object will be inserted.
	 */
	protected def void prepareResource(EObject object, int index) {
		resourceContent.add(index, object)
	}
}