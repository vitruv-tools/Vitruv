package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change

import static extension edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeAssertHelper.*
import static extension edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeDescription2ChangeTransformationTestUtil.*

import org.junit.Test
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.PermuteEAttributeValues

class ChangeDescription2PermuteEAttrbuteValueTest extends ChangeDescription2ChangeTransformationTest {

	@Test
	def public permuteAttributeTest() {
		// prepare test
		this.testInsertEAttributeValue(0, 42, -1)
		this.testInsertEAttributeValue(0, 21, -1)
		startRecording

		// test permutation
		this.rootElement.multiValuedEAttribute.add(this.rootElement.multiValuedEAttribute.remove(0))

		val changes = getChanges()

		changes.assertSingleChangeWithType(PermuteEAttributeValues)
		val permuteEAttributeValues = changes.get(0) as PermuteEAttributeValues<?>
		permuteEAttributeValues.assertAffectedEObject(this.rootElement)
		permuteEAttributeValues.assertAffectedEFeature(
			this.rootElement.eClass.getEStructuralFeature("multiValuedEAttribute"))

		permuteEAttributeValues.newIndex
		permuteEAttributeValues.oldIndex
	}
}
