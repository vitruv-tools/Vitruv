package tools.vitruvius.framework.tests.change.attribute

import org.junit.Test

import static extension tools.vitruvius.framework.tests.change.util.ChangeAssertHelper.*
import static extension tools.vitruvius.framework.tests.change.util.ChangeDescription2ChangeTransformationTestUtil.*
import tools.vitruvius.framework.change.echange.feature.attribute.PermuteEAttributeValues

class ChangeDescription2PermuteEAttrbuteValueTest extends tools.vitruvius.framework.tests.change.ChangeDescription2ChangeTransformationTest {

	@Test
	def public permuteAttributeTest() {
		// prepare test
		this.testInsertEAttributeValue(0, 42, -1)
		this.testInsertEAttributeValue(0, 21, -1)
		startRecording

		// test permutation
		this.rootElement.multiValuedEAttribute.add(this.rootElement.multiValuedEAttribute.remove(0))

		val expectedIndicesForElementsAtOldIndices = #[1, 0]
		val changes = getChanges()
		changes.assertPermuteListTest(rootElement, expectedIndicesForElementsAtOldIndices, MULTI_VALUE_E_ATTRIBUTE_NAME, PermuteEAttributeValues)
	}

	@Test
	def public permuteAttributeTestComplex() {
		// prepare test
		this.testInsertEAttributeValue(0, 42, -1)
		this.testInsertEAttributeValue(0, 21, -1)
		this.testInsertEAttributeValue(0, 23, -1)
		startRecording

		// test permutation
		// change 2 and 3 --> expected list: 0, 2, 1
		this.rootElement.multiValuedEAttribute.add(1, this.rootElement.multiValuedEAttribute.remove(2))

		val expectedIndicesForElementsAtOldIndices = #[0, 2, 1]
		val changes = getChanges()
		changes.assertPermuteListTest(this.rootElement, expectedIndicesForElementsAtOldIndices, MULTI_VALUE_E_ATTRIBUTE_NAME, PermuteEAttributeValues)
	}

}
