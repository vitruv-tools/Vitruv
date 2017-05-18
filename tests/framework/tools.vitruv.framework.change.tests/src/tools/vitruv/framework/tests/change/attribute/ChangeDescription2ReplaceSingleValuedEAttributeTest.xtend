package tools.vitruv.framework.tests.change.attribute

import tools.vitruv.framework.tests.change.ChangeDescription2ChangeTransformationTest
import org.junit.Test
import static allElementTypes.AllElementTypesPackage.Literals.*
import static extension tools.vitruv.framework.tests.change.util.CompoundEChangeAssertHelper.*
import static extension tools.vitruv.framework.tests.change.util.AtomicEChangeAssertHelper.*

class ChangeDescription2ReplaceSingleValuedEAttributeTest extends ChangeDescription2ChangeTransformationTest {
	/**
	 * Write value to non-unsettable EAttribute
	 */
	@Test
	def testReplaceSingleValuedEAttributeValueFromDefault() {
		// test
		startRecording
		rootElement.singleValuedEAttribute = 42

		changes.assertChangeCount(1)
		changes.claimChange(0).assertReplaceSingleValuedEAttribute(rootElement, ROOT__SINGLE_VALUED_EATTRIBUTE, 0, 42)
	}

	/**
	 * Write default value to non-unsettable EAttribute
	 */
	@Test
	def testReplaceSingleValuedEAttributeValueWithDefault() {
		rootElement.singleValuedEAttribute = 42
		// test
		startRecording

		// set to default/clear
		rootElement.singleValuedEAttribute = 0

		changes.assertChangeCount(1)
		changes.claimChange(0).assertReplaceSingleValuedEAttribute(rootElement, ROOT__SINGLE_VALUED_EATTRIBUTE, 42, 0)
	}

	/**
	 * Explicitly unset non-unsettable EAttribute (should be same as writing default value to it)
	 */
	@Test
	def testUnsetSingleValuedEAttributeValue() {
		rootElement.singleValuedEAttribute = 42
		// test
		startRecording

		// unset 
		rootElement.eUnset(ROOT__SINGLE_VALUED_EATTRIBUTE)

		changes.assertChangeCount(1)
		changes.claimChange(0).assertReplaceSingleValuedEAttribute(rootElement, ROOT__SINGLE_VALUED_EATTRIBUTE, 42, 0)
	}

	/**
	 * Write value to unsettable EAttribute
	 */
	@Test
	def testReplaceUnsettableSingleValuedEAttributeValueFromDefault() {
		// test
		startRecording
		rootElement.singleValuedUnsettableEAttribute = 42

		changes.assertChangeCount(1)
		changes.claimChange(0).assertReplaceSingleValuedEAttribute(rootElement,
			ROOT__SINGLE_VALUED_UNSETTABLE_EATTRIBUTE, 0, 42)
	}

	/**
	 * Write default value to unsettable EAttribute
	 */
	@Test
	def testReplaceUnsettableSingleValuedEAttributeValueWithDefault() {
		rootElement.singleValuedUnsettableEAttribute = 42
		// test
		startRecording

		// set to default/clear
		rootElement.singleValuedUnsettableEAttribute = 0

		changes.assertChangeCount(1)
		changes.claimChange(0).assertReplaceSingleValuedEAttribute(rootElement,
			ROOT__SINGLE_VALUED_UNSETTABLE_EATTRIBUTE, 42, 0)
	}

	/**
	 * Unset unsettable EAttribute
	 */
	@Test
	def testUnsetUnsettableSingleValuedEAttributeValue() {
		rootElement.singleValuedUnsettableEAttribute = 42
		// test
		startRecording

		// unset 
		rootElement.eUnset(ROOT__SINGLE_VALUED_UNSETTABLE_EATTRIBUTE)

		changes.assertChangeCount(1)
		val subtractiveChanges = changes.claimChange(0).assertExplicitUnsetEAttribute.subtractiveChanges
		assertChangeCount(subtractiveChanges, 1)
		subtractiveChanges.get(0).assertReplaceSingleValuedEAttribute(rootElement,
			ROOT__SINGLE_VALUED_UNSETTABLE_EATTRIBUTE, 42, 0)
	}

}
