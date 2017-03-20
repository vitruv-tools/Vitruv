package tools.vitruv.framework.tests.echange.feature.reference

import allElementTypes.AllElementTypesFactory
import allElementTypes.NonRoot
import allElementTypes.Root
import org.eclipse.emf.ecore.EObject
import org.junit.Before
import tools.vitruv.framework.tests.echange.EChangeTest

/**
 * Abstract class which is used by all test classes for references.
 */
public abstract class ReferenceEChangeTest extends EChangeTest {
	protected var Root affectedEObject = null
	protected var NonRoot newValue = null
	protected var NonRoot newValue2 = null
	
	protected static val DEFAULT_NEW_NON_ROOT_NAME = "New Non Root Element"
	protected static val DEFAULT_NEW_NON_ROOT_NAME_2 = "New Non Root Element 2"
	
	/**
	 * Sets the default object and new value for tests.
	 */
	@Before
	override public void beforeTest() {
		super.beforeTest()
		affectedEObject = rootObject
		newValue = AllElementTypesFactory.eINSTANCE.createNonRoot()
		newValue.id = DEFAULT_NEW_NON_ROOT_NAME
		newValue2 = AllElementTypesFactory.eINSTANCE.createNonRoot()
		newValue2.id = DEFAULT_NEW_NON_ROOT_NAME_2
	}
	
	/**
	 * Prepares the resource and adds the new values.
	 */
	def protected void prepareResource() {
		resource.contents.add(newValue)
		resource.contents.add(newValue2)
	}
	
	/**
	 * Prepares the staging area for the tests and places
	 * a new object in it. Clears the old value.
	 * @param 	object The new object.
	 */
	def protected void prepareStagingArea(EObject object) {
		stagingArea.contents.clear
		if (object != null) {
			stagingArea.contents.add(object)
		}
	}
}