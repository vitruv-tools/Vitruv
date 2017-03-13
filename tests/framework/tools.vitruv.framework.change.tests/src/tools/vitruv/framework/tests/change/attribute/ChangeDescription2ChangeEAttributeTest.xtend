package tools.vitruv.framework.tests.change.attribute

import tools.vitruv.framework.tests.change.ChangeDescription2ChangeTransformationTest
import static extension tools.vitruv.framework.tests.change.util.AtomicEChangeAssertHelper.*
import static allElementTypes.AllElementTypesPackage.Literals.*;

abstract class ChangeDescription2ChangeEAttributeTest extends ChangeDescription2ChangeTransformationTest {
	def public void testInsertEAttributeValue(int expectedIndex, int expectedValue, int position) {
		// test
		var int index = expectedIndex
		startRecording
		if (-1 == position) {
			rootElement.multiValuedEAttribute.add(expectedValue)
			index = rootElement.multiValuedEAttribute.size - 1
		} else {
			rootElement.multiValuedEAttribute.add(position, expectedValue)
		}

		// assert
		changes.assertChangeCount(1);
		changes.claimChange(0).assertInsertEAttribute(rootElement, ROOT__MULTI_VALUED_EATTRIBUTE, expectedValue, index)
	}
}