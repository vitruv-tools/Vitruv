package tools.vitruv.framework.tests.change.attribute

import tools.vitruv.framework.tests.change.ChangeDescription2ChangeTransformationTest
import org.junit.Test

import static extension tools.vitruv.framework.tests.change.util.AtomicEChangeAssertHelper.*
import static extension tools.vitruv.framework.tests.change.util.CompoundEChangeAssertHelper.*
import static allElementTypes.AllElementTypesPackage.Literals.*
import org.junit.Assert

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
		changes.assertRemoveEAttribute(this.rootElement, ROOT__MULTI_VALUED_EATTRIBUTE, 66, 2)
			.assertRemoveEAttribute(this.rootElement, ROOT__MULTI_VALUED_EATTRIBUTE, 42, 0)
			.assertRemoveEAttribute(this.rootElement, ROOT__MULTI_VALUED_EATTRIBUTE, 55, 0)
			.assertEmpty;
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
		changes.assertRemoveEAttribute(this.rootElement, ROOT__MULTI_VALUED_EATTRIBUTE, 55, 1)
			.assertRemoveEAttribute(this.rootElement, ROOT__MULTI_VALUED_EATTRIBUTE, 42, 0)
			.assertEmpty;
	}
	
	/**
	 * Remove attribute value from unsettable multi-valued EAttribute (should not be an unset)
	 */
	@Test
	def void testRemoveUnsettableEAttributeValue() {
		Assert.assertFalse(this.rootElement.eIsSet(ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE));
		this.rootElement.multiValuedUnsettableEAttribute.add(42)
		Assert.assertTrue(this.rootElement.eIsSet(ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE));
		// test
		startRecording

		// set to default/clear
		this.rootElement.multiValuedUnsettableEAttribute.remove(0)

		changes.assertChangeCount(1);
		changes.assertRemoveEAttribute(this.rootElement, ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE, 42, 0)
			.assertEmpty;
		Assert.assertTrue(this.rootElement.eIsSet(ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE));
	}


	@Test
	def void testUnsetEAttributeValue() {
		this.rootElement.multiValuedUnsettableEAttribute.add(42)
		// test
		startRecording

		// unset 
		this.rootElement.eUnset(ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE)

		changes.assertChangeCount(1);
		val subtractiveChanges = changes.claimChange(0).assertExplicitUnsetEAttribute.subtractiveChanges
		subtractiveChanges.assertChangeCount(1);
		subtractiveChanges.assertRemoveEAttribute(this.rootElement, ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE, 42, 0)
			.assertEmpty;
	}
	
	@Test
	def void testUnsetEAttributeValuesWithSeveralValues() {
		Assert.assertFalse(rootElement.isSetMultiValuedUnsettableEAttribute);
		this.rootElement.multiValuedUnsettableEAttribute.add(42)
		Assert.assertTrue(rootElement.isSetMultiValuedUnsettableEAttribute);
		this.rootElement.multiValuedUnsettableEAttribute.add(22)
		// test
		startRecording

		// unset
		Assert.assertTrue(rootElement.isSetMultiValuedUnsettableEAttribute); 
		this.rootElement.eUnset(ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE)
		Assert.assertFalse(rootElement.isSetMultiValuedUnsettableEAttribute);
		changes.assertChangeCount(1);
		val subtractiveChanges = changes.claimChange(0).assertExplicitUnsetEAttribute.subtractiveChanges
		subtractiveChanges.assertChangeCount(2);
		subtractiveChanges.assertRemoveEAttribute(this.rootElement, ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE, 22, 1)
			.assertRemoveEAttribute(this.rootElement, ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE, 42, 0)
			.assertEmpty;
	}
	
}
