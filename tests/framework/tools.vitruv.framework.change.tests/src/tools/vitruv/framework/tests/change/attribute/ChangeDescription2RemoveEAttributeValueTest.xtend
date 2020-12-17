package tools.vitruv.framework.tests.change.attribute

import tools.vitruv.framework.tests.change.ChangeDescription2ChangeTransformationTest

import static extension tools.vitruv.framework.tests.change.util.AtomicEChangeAssertHelper.*
import static allElementTypes.AllElementTypesPackage.Literals.*
import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertTrue

class ChangeDescription2RemoveEAttributeValueTest extends ChangeDescription2ChangeTransformationTest {

	/**
	 * Remove attribute value from multi-valued EAttribute
	 */
	@Test
	def void testRemoveEAttributeValue() {
		this.rootElement.multiValuedEAttribute.add(42)
		this.rootElement.multiValuedEAttribute.add(55)
		this.rootElement.multiValuedEAttribute.add(66)
		// test
		startRecording

		// set to default/clear
		this.rootElement.multiValuedEAttribute.remove(2)
		this.rootElement.multiValuedEAttribute.remove(0)
		this.rootElement.multiValuedEAttribute.remove(0)

		changes.assertChangeCount(3);
		changes.assertRemoveEAttribute(this.rootElement, ROOT__MULTI_VALUED_EATTRIBUTE, 66, 2).
			assertRemoveEAttribute(this.rootElement, ROOT__MULTI_VALUED_EATTRIBUTE, 42, 0).
			assertRemoveEAttribute(this.rootElement, ROOT__MULTI_VALUED_EATTRIBUTE, 55, 0).assertEmpty;
	}

	/**
	 * Clear several attributes in a multi-valued EAttribute
	 */
	@Test
	def void testClearEAttributeValue() {
		this.rootElement.multiValuedEAttribute.add(42)
		this.rootElement.multiValuedEAttribute.add(55)
		// test
		startRecording

		// set to default/clear
		this.rootElement.multiValuedEAttribute.clear

		changes.assertChangeCount(2);
		changes.assertRemoveEAttribute(this.rootElement, ROOT__MULTI_VALUED_EATTRIBUTE, 55, 1).
			assertRemoveEAttribute(this.rootElement, ROOT__MULTI_VALUED_EATTRIBUTE, 42, 0).assertEmpty;
	}

	/**
	 * Remove attribute value from unsettable multi-valued EAttribute (should not be an unset)
	 */
	@Test
	def void testRemoveUnsettableEAttributeValue() {
		assertFalse(this.rootElement.eIsSet(ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE));
		this.rootElement.multiValuedUnsettableEAttribute.add(42)
		assertTrue(this.rootElement.eIsSet(ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE));
		// test
		startRecording

		// set to default/clear
		this.rootElement.multiValuedUnsettableEAttribute.remove(0)

		changes.assertChangeCount(1);
		changes.assertRemoveEAttribute(this.rootElement, ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE, 42, 0).assertEmpty;
		assertTrue(this.rootElement.eIsSet(ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE));
	}

	@Test
	def void testUnsetEAttributeValue() {
		this.rootElement.multiValuedUnsettableEAttribute.add(42)
		// test
		startRecording

		// unset 
		this.rootElement.eUnset(ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE)

		changes.assertChangeCount(2);
		changes.assertRemoveEAttribute(this.rootElement, ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE, 42, 0).
			assertUnsetFeature(this.rootElement, ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE).assertEmpty;

	}

	@Test
	def void testUnsetEAttributeValuesWithSeveralValues() {
		assertFalse(rootElement.isSetMultiValuedUnsettableEAttribute);
		this.rootElement.multiValuedUnsettableEAttribute.add(42)
		assertTrue(rootElement.isSetMultiValuedUnsettableEAttribute);
		this.rootElement.multiValuedUnsettableEAttribute.add(22)
		// test
		startRecording

		// unset
		assertTrue(rootElement.isSetMultiValuedUnsettableEAttribute);
		this.rootElement.eUnset(ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE)
		assertFalse(rootElement.isSetMultiValuedUnsettableEAttribute);
		changes.assertChangeCount(3);
		changes.assertRemoveEAttribute(this.rootElement, ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE, 22, 1).
			assertRemoveEAttribute(this.rootElement, ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE, 42, 0).
			assertUnsetFeature(this.rootElement, ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE).assertEmpty;
	}

}
