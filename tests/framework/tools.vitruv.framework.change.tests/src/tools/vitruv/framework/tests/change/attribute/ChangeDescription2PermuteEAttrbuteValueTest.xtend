package tools.vitruv.framework.tests.change.attribute

import org.junit.Test

import tools.vitruv.framework.change.echange.feature.attribute.PermuteEAttributeValues
import org.junit.Ignore
import static allElementTypes.AllElementTypesPackage.Literals.*;
import static extension tools.vitruv.framework.tests.change.util.ChangeAssertHelper.*

class ChangeDescription2PermuteEAttrbuteValueTest extends ChangeDescription2ChangeEAttributeTest {

	@Ignore
	@Test
	def public permuteAttributeTest() {
		// prepare test
		this.testInsertEAttributeValue(0, 42, -1)
		this.testInsertEAttributeValue(0, 21, -1)
		startRecording

		// test permutation
		this.rootElement.multiValuedEAttribute.add(this.rootElement.multiValuedEAttribute.remove(0))

		val expectedIndicesForElementsAtOldIndices = #[1, 0]
		changes.assertChangeCount(1);
		changes.claimChange(0).assertPermuteListTest(rootElement, expectedIndicesForElementsAtOldIndices, ROOT__MULTI_VALUED_EATTRIBUTE, PermuteEAttributeValues)
	}

	@Ignore
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
		changes.assertChangeCount(1);
		changes.claimChange(0).assertPermuteListTest(this.rootElement, expectedIndicesForElementsAtOldIndices, ROOT__MULTI_VALUED_EATTRIBUTE, PermuteEAttributeValues)
	}

}
