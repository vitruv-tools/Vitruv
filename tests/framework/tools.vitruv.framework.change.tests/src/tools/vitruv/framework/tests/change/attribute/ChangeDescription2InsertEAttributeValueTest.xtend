package tools.vitruv.framework.tests.change.attribute

import static allElementTypes.AllElementTypesPackage.Literals.*

import static extension tools.vitruv.framework.tests.change.util.AtomicEChangeAssertHelper.*
import static extension tools.vitruv.framework.tests.change.util.CompoundEChangeAssertHelper.*
import org.junit.jupiter.api.Test
import static extension tools.vitruv.testutils.metamodels.AllElementTypesCreators.*
import tools.vitruv.framework.tests.change.ChangeDescription2ChangeTransformationTest

class ChangeDescription2InsertEAttributeValueTest extends ChangeDescription2ChangeTransformationTest {
	
	@Test
	def void testInsertEAttributeValue() {
		testInsertEAttributeValue(0, 42)
	}

	@Test
	def void testMultipleInsertEAttributeValue() {
		testInsertEAttributeValue(0, 42)
		testInsertEAttributeValue(1, 21)
		testInsertEAttributeValue(2, 10)
	}

	@Test
	def void testInsertAtPositionInsertEAttributeValue() {
		testInsertEAttributeValue(0, 42)
		testInsertEAttributeValue(0, 21, 0)
		testInsertEAttributeValue(1, 10, 1)
	}

	@Test
	def void testInsertEAttributeValueSequence() {
		// prepare
		uniquePersistedRoot
		
		// test
		val result = uniquePersistedRoot.record [
			multiValuedEAttribute += 42
			multiValuedEAttribute += 55
			multiValuedEAttribute += 66
		]

		// assert
		result.assertChangeCount(3)
			.assertInsertEAttribute(uniquePersistedRoot, ROOT__MULTI_VALUED_EATTRIBUTE, 42, 0, false)
			.assertInsertEAttribute(uniquePersistedRoot, ROOT__MULTI_VALUED_EATTRIBUTE, 55, 1, false)
			.assertInsertEAttribute(uniquePersistedRoot, ROOT__MULTI_VALUED_EATTRIBUTE, 66, 2, false)
			.assertEmpty
	}

	@Test
	def void testTreeInsertMultiValuedEAttribute() {
		// prepare
		uniquePersistedRoot
		val innerRoot = aet.Root
		innerRoot => [
			multiValuedEAttribute += 1
			multiValuedEAttribute += 2
		]

		// test
		val result = uniquePersistedRoot.record [
			recursiveRoot = innerRoot		
		]

		// assert
		result.assertChangeCount(5)
			.assertCreateAndReplaceNonRoot(innerRoot, uniquePersistedRoot, ROOT__RECURSIVE_ROOT, false)
			.assertReplaceSingleValuedEAttribute(innerRoot, IDENTIFIED__ID, null, innerRoot.id, false, false)
			.assertInsertEAttribute(innerRoot, ROOT__MULTI_VALUED_EATTRIBUTE, 1, 0, false)
			.assertInsertEAttribute(innerRoot, ROOT__MULTI_VALUED_EATTRIBUTE, 2, 1, false)
			.assertEmpty
	}

	def void testInsertEAttributeValue(int expectedIndex, int expectedValue, int position) {
		// prepare
		uniquePersistedRoot
		
		// test
		val result = uniquePersistedRoot.record [
			multiValuedEAttribute.add(position, expectedValue)
		]

		// assert
		result.assertChangeCount(1)
			.assertInsertEAttribute(uniquePersistedRoot, ROOT__MULTI_VALUED_EATTRIBUTE, expectedValue, expectedIndex, false)
			.assertEmpty
	}

	def void testInsertEAttributeValue(int expectedIndex, int expectedValue) {
		// prepare
		uniquePersistedRoot
		
		// test
		val result = uniquePersistedRoot.record  [
			multiValuedEAttribute += expectedValue
		]

		// assert
		val index = uniquePersistedRoot.multiValuedEAttribute.size - 1
		result.assertChangeCount(1)
			.assertInsertEAttribute(uniquePersistedRoot, ROOT__MULTI_VALUED_EATTRIBUTE, expectedValue, index, false)
			.assertEmpty
	}

}
