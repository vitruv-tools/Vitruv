package tools.vitruv.framework.tests.change.attribute

import static allElementTypes.AllElementTypesPackage.Literals.*
import static extension tools.vitruv.framework.tests.change.util.AtomicEChangeAssertHelper.*
import org.junit.jupiter.api.Test
import tools.vitruv.framework.tests.change.ChangeDescription2ChangeTransformationTest

class ChangeDescription2ReplaceSingleValuedEAttributeTest extends ChangeDescription2ChangeTransformationTest {
	
	/**
	 * Write value to non-unsettable EAttribute
	 */
	@Test
	def void testReplaceSingleValuedEAttributeValueFromDefault() {
		// prepare
		uniquePersistedRoot
		
		// test
		val result = uniquePersistedRoot.record [
			singleValuedEAttribute = 42
		]

		// assert
		result.assertChangeCount(1)
			.assertReplaceSingleValuedEAttribute(uniquePersistedRoot, ROOT__SINGLE_VALUED_EATTRIBUTE, 0, 42, false, false)
			.assertEmpty
	}

	/**
	 * Write default value to non-unsettable EAttribute
	 */
	@Test
	def void testReplaceSingleValuedEAttributeValueWithDefault() {
		// prepare
		uniquePersistedRoot => [
			singleValuedEAttribute = 42	
		]

		// test
		val result = uniquePersistedRoot.record [
			singleValuedEAttribute = 0	
		]

		// assert
		result.assertChangeCount(1)
			.assertReplaceSingleValuedEAttribute(uniquePersistedRoot, ROOT__SINGLE_VALUED_EATTRIBUTE, 42, 0, false, false)
			.assertEmpty
	}

	/**
	 * Explicitly unset non-unsettable EAttribute (should be same as writing default value to it)
	 */
	@Test
	def void testUnsetSingleValuedEAttributeValue() {
		// prepare
		uniquePersistedRoot => [
			singleValuedEAttribute = 42	
		]

		// test
		val result = uniquePersistedRoot.record [
			eUnset(ROOT__SINGLE_VALUED_EATTRIBUTE)
		]

		// assert
		result.assertChangeCount(1)
			.assertReplaceSingleValuedEAttribute(uniquePersistedRoot, ROOT__SINGLE_VALUED_EATTRIBUTE, 42, 0, false, false)
			.assertEmpty
	}

	/**
	 * Write value to unsettable EAttribute
	 */
	@Test
	def void testReplaceUnsettableSingleValuedEAttributeValueFromDefault() {
		// prepare
		uniquePersistedRoot
		
		// test
		val result = uniquePersistedRoot.record [
			singleValuedUnsettableEAttribute = 42	
		]

		// assert
		result.assertChangeCount(1)
			.assertReplaceSingleValuedEAttribute(uniquePersistedRoot, ROOT__SINGLE_VALUED_UNSETTABLE_EATTRIBUTE, 0, 42, false, false)
			.assertEmpty
	}

	/**
	 * Write default value to unsettable EAttribute
	 */
	@Test
	def void testReplaceUnsettableSingleValuedEAttributeValueWithDefault() {
		// prepare
		uniquePersistedRoot => [
			singleValuedUnsettableEAttribute = 42	
		]

		// test
		val result = uniquePersistedRoot.record [
			singleValuedUnsettableEAttribute = 0	
		]

		// assert
		result.assertChangeCount(1)
			.assertReplaceSingleValuedEAttribute(uniquePersistedRoot, ROOT__SINGLE_VALUED_UNSETTABLE_EATTRIBUTE, 42, 0, false, false)
			.assertEmpty
	}

	/**
	 * Unset unsettable EAttribute
	 */
	@Test
	def void testUnsetUnsettableSingleValuedEAttributeValue() {
		// prepare
		uniquePersistedRoot => [
			singleValuedUnsettableEAttribute = 42
		]

		// test
		val result = uniquePersistedRoot.record [
			eUnset(ROOT__SINGLE_VALUED_UNSETTABLE_EATTRIBUTE)
		]

		result.assertChangeCount(1)
			.assertReplaceSingleValuedEAttribute(uniquePersistedRoot, ROOT__SINGLE_VALUED_UNSETTABLE_EATTRIBUTE, 42, 0, false, true)
			.assertEmpty
	}

}
