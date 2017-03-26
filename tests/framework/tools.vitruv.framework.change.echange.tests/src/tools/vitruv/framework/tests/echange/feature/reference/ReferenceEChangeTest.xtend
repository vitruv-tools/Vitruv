package tools.vitruv.framework.tests.echange.feature.reference

import allElementTypes.AllElementTypesFactory
import allElementTypes.NonRoot
import allElementTypes.Root
import org.eclipse.emf.common.util.EList
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
	protected var EList<EObject> resourceContent = null
	
	/**
	 * Sets the default object and new value for tests.
	 */
	@Before
	override public void beforeTest() {
		super.beforeTest()
		affectedEObject = rootObject
		newValue = AllElementTypesFactory.eINSTANCE.createNonRoot()
		newValue2 = AllElementTypesFactory.eINSTANCE.createNonRoot()
		resourceContent = resource.contents
	}
	
	/**
	 * Prepares the resource and adds the new values.
	 */
	def protected void prepareResource() {
		resource.contents.add(newValue)
		resource.contents.add(newValue2)
	}
}