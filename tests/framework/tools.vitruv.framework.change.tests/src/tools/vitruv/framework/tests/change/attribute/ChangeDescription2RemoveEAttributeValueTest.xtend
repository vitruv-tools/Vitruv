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
	def public testRemoveEAttributeValue() {
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
		changes.claimChange(0).assertRemoveEAttribute(this.rootElement, ROOT__MULTI_VALUED_EATTRIBUTE, 66, 2)
		changes.claimChange(1).assertRemoveEAttribute(this.rootElement, ROOT__MULTI_VALUED_EATTRIBUTE, 42, 0)
		changes.claimChange(2).assertRemoveEAttribute(this.rootElement, ROOT__MULTI_VALUED_EATTRIBUTE, 55, 0)
	}
	
	/**
	 * Clear several attributes in a multi-valued EAttribute
	 */
	@Test
	def public testClearEAttributeValue() {
		this.rootElement.multiValuedEAttribute.add(42)
		this.rootElement.multiValuedEAttribute.add(55)
		// test
		startRecording

		// set to default/clear
		this.rootElement.multiValuedEAttribute.clear

		changes.assertChangeCount(2);
		changes.claimChange(0).assertRemoveEAttribute(this.rootElement, ROOT__MULTI_VALUED_EATTRIBUTE, 42, 0)
		changes.claimChange(1).assertRemoveEAttribute(this.rootElement, ROOT__MULTI_VALUED_EATTRIBUTE, 55, 1)
	}
	
	/**
	 * Remove attribute value from unsettable multi-valued EAttribute (should not be an unset)
	 */
	@Test
	def public testRemoveUnsettableEAttributeValue() {
		Assert.assertFalse(this.rootElement.eIsSet(ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE));
		this.rootElement.multiValuedUnsettableEAttribute.add(42)
		Assert.assertTrue(this.rootElement.eIsSet(ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE));
		// test
		startRecording

		// set to default/clear
		this.rootElement.multiValuedUnsettableEAttribute.remove(0)

		changes.assertChangeCount(1);
		changes.claimChange(0).assertRemoveEAttribute(this.rootElement, ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE, 42, 0)
		Assert.assertTrue(this.rootElement.eIsSet(ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE));
	}


	@Test
	def public testUnsetRemoveEAttributeValue() {
		this.rootElement.multiValuedUnsettableEAttribute.add(42)
		// test
		startRecording

		// unset 
		this.rootElement.eUnset(ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE)

		changes.assertChangeCount(1);
		val subtractiveChanges = changes.claimChange(0).assertExplicitUnset.subtractiveChanges
		subtractiveChanges.assertChangeCount(1);
		subtractiveChanges.get(0).assertRemoveEAttribute(this.rootElement, ROOT__MULTI_VALUED_UNSETTABLE_EATTRIBUTE, 42, 0)
	}
	
}
