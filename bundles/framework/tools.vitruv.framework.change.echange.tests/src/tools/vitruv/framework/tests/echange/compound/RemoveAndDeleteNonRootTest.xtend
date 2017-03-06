package tools.vitruv.framework.tests.echange.compound

import allElementTypes.AllElementTypesPackage
import allElementTypes.NonRoot
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EReference
import org.junit.Before
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot
import tools.vitruv.framework.tests.echange.feature.reference.ReferenceEChangeTest

/**
 * Test class for the concrete {@link RemoveAndDeleteNonRoot} EChange,
 * which removes a non root element reference from a reference list
 * and deletes it.
 */
class RemoveAndDeleteNonRootTest extends ReferenceEChangeTest {
	protected var EReference defaultAffectedFeature = null
	protected var EList<NonRoot> referenceContent = null
	
	protected static val Integer DEFAULT_INDEX = 0
	
	/**
	 * Calls setup of super class and sets the feature for the tests.
	 */
	@Before
	override public void beforeTest() {
		super.beforeTest()
		defaultAffectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE
		referenceContent = defaultAffectedEObject.eGet(defaultAffectedFeature) as EList<NonRoot>
	}
	
	
}