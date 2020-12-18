package tools.vitruv.framework.tests.change.attribute


import static extension tools.vitruv.framework.tests.change.util.AtomicEChangeAssertHelper.*
import static allElementTypes.AllElementTypesPackage.Literals.*
import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertTrue
import tools.vitruv.framework.tests.change.ChangeDescription2ChangeTransformationTest

class ChangeDescription2RemoveEAttributeValueTest extends ChangeDescription2ChangeTransformationTest {

	/**
	 * Remove attribute value from multi-valued EAttribute
	 */
	@Test
	def void testRemoveEAttributeValue() {
		// prepare
		uniquePersistedRoot => [
			multiValuedEAttribute += 42
			multiValuedEAttribute += 55
			multiValuedEAttribute += 66	
		]

		// test
		val result = uniquePersistedRoot.record [
			multiValuedEAttribute.remove(2)
			multiValuedEAttribute.remove(0)
			multiValuedEAttribute.remove(0)
		]

		// assert
		result.assertChangeCount(3)
			.assertRemoveEAttribute(uniquePersistedRoot, ROOT__MULTI_VALUED_EATTRIBUTE, 66, 2)
			.assertRemoveEAttribute(uniquePersistedRoot, ROOT__MULTI_VALUED_EATTRIBUTE, 42, 0)
			.assertRemoveEAttribute(uniquePersistedRoot, ROOT__MULTI_VALUED_EATTRIBUTE, 55, 0)
			.assertEmpty
	}

	/**
	 * Clear several attributes in a multi-valued EAttribute
	 */
	@Test
	def void testClearEAttributeValue() {
		// prepare
		uniquePersistedRoot => [
			multiValuedEAttribute += 42
			multiValuedEAttribute += 55
		]

		// test
		val result = uniquePersistedRoot.record [
			multiValuedEAttribute.clear
		]

		// assert
		result.assertChangeCount(2)
			.assertRemoveEAttribute(uniquePersistedRoot, ROOT__MULTI_VALUED_EATTRIBUTE, 55, 1)
			.assertRemoveEAttribute(uniquePersistedRoot, ROOT__MULTI_VALUED_EATTRIBUTE, 42, 0)
			.assertEmpty
	}

	/**
	 * Remove attribute value from unsettable multi-valued EAttribute (should not be an unset)
	 */
	@Test
	def void testRemoveUnsettableEAttributeValue() {
		// prepare
		assertFalse(uniquePersistedRoot.eIsSet(ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE))
		uniquePersistedRoot => [
			multiValuedUnsettableEAttribute += 42
		]
		assertTrue(uniquePersistedRoot.eIsSet(ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE))

		// test
		val result = uniquePersistedRoot.record [
			multiValuedUnsettableEAttribute.remove(0)
		]

		// assert
		assertTrue(uniquePersistedRoot.eIsSet(ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE))
		result.assertChangeCount(1)
			.assertRemoveEAttribute(uniquePersistedRoot, ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE, 42, 0)
			.assertEmpty
	}

	@Test
	def void testUnsetEAttributeValue() {
		// prepare
		uniquePersistedRoot => [
			multiValuedUnsettableEAttribute += 42
		]

		// test
		val result = uniquePersistedRoot.record [
			eUnset(ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE)
		]

		// assert
		result.assertChangeCount(2)
			.assertRemoveEAttribute(uniquePersistedRoot, ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE, 42, 0)
			.assertUnsetFeature(uniquePersistedRoot, ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE)
			.assertEmpty
	}

	@Test
	def void testUnsetEAttributeValuesWithSeveralValues() {
		// prepare
		assertFalse(uniquePersistedRoot.isSetMultiValuedUnsettableEAttribute)
		uniquePersistedRoot => [
			multiValuedUnsettableEAttribute += 42
			multiValuedUnsettableEAttribute += 22
		]
		assertTrue(uniquePersistedRoot.isSetMultiValuedUnsettableEAttribute)

		// test
		val result = uniquePersistedRoot.record [
			eUnset(ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE)
		]

		// assert
		assertFalse(uniquePersistedRoot.isSetMultiValuedUnsettableEAttribute)
		result.assertChangeCount(3)
			.assertRemoveEAttribute(uniquePersistedRoot, ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE, 22, 1)
			.assertRemoveEAttribute(uniquePersistedRoot, ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE, 42, 0)
			.assertUnsetFeature(uniquePersistedRoot, ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE)
			.assertEmpty
	}

}
