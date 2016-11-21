package tools.vitruv.framework.tests.change.compound

import tools.vitruv.framework.tests.change.ChangeDescription2ChangeTransformationTest
import org.junit.Test
import static extension tools.vitruv.framework.tests.change.util.ChangeAssertHelper.*
import org.junit.Ignore

class ChangeDescription2MoveEObjectTest extends ChangeDescription2ChangeTransformationTest {

	@Ignore
	@Test
	def public void testMoveEObjectFromContainmentSingleToContainmentList() {
		// prepare
		val nonRoot = this.createAndAddNonRootToContainment(true)

		// test
		this.rootElement.multiValuedContainmentEReference.add(this.rootElement.singleValuedContainmentEReference)
		this.rootElement.singleValuedContainmentEReference = null

		// assert
		val changes = getChanges()
		val moveChanges = changes.assertMoveEObject(2)
		moveChanges.first.assertRemoveEReference(this.rootElement, SINGLE_VALUED_CONTAINMENT_E_REFERENCE_NAME,
			nonRoot, 0, true, false)
		moveChanges.second.assertAffectedEObject(this.rootElement)
		moveChanges.second.assertAffectedEFeature(
			this.rootElement.getFeatureByName(SINGLE_VALUED_CONTAINMENT_E_REFERENCE_NAME))
		moveChanges.third.assertInsertEReference(this.rootElement, MULTI_VALUED_CONTAINMENT_E_REFERENCE_NAME,
			nonRoot, 0, true, false)
		moveChanges.fourth.assertAffectedEObject(this.rootElement)
		moveChanges.fourth.assertAffectedEFeature(this.rootElement.getFeatureByName(MULTI_VALUED_CONTAINMENT_E_REFERENCE_NAME))
	}
}
