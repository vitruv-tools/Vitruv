package tools.vitruv.framework.tests.change.compound

import tools.vitruv.framework.tests.change.ChangeDescription2ChangeTransformationTest
import org.junit.Test
import static extension tools.vitruv.framework.tests.change.util.AtomicEChangeAssertHelper.*
import static extension tools.vitruv.framework.tests.change.util.ChangeAssertHelper.*
import org.junit.Ignore
import static allElementTypes.AllElementTypesPackage.Literals.*;

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
		changes.assertChangeCount(1);
		val moveChanges = changes.claimChange(0).assertMoveEObject(2)
		moveChanges.first.assertRemoveEReference(this.rootElement, ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE,
			nonRoot, 0, true)
		moveChanges.second.assertAffectedEObject(this.rootElement)
		moveChanges.second.assertAffectedEFeature(
			this.rootElement.getFeatureByName(SINGLE_VALUED_CONTAINMENT_E_REFERENCE_NAME))
		moveChanges.third.assertInsertEReference(this.rootElement, ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE,
			nonRoot, 0, true)
		moveChanges.fourth.assertAffectedEObject(this.rootElement)
		moveChanges.fourth.assertAffectedEFeature(this.rootElement.getFeatureByName(MULTI_VALUED_CONTAINMENT_E_REFERENCE_NAME))
	}
}
