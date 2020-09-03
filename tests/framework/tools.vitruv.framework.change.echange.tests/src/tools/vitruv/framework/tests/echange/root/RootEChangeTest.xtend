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
abstract class RootEChangeTest extends EChangeTest {
	protected var Root newRootObject = null;
	protected var Root newRootObject2 = null;
	protected var EList<EObject> resourceContent = null;
	
	/**
	 * Calls setup of superclass and creates two new root elements 
	 * which can be used in the tests.
	 */
	@Before
	override void beforeTest() {
		super.beforeTest()
		newRootObject = AllElementTypesFactory.eINSTANCE.createRoot()
		newRootObject2 = AllElementTypesFactory.eINSTANCE.createRoot()
		resourceContent = resource.contents
	}
}