package tools.vitruv.framework.tests.change.attribute

import tools.vitruv.framework.tests.change.ChangeDescription2ChangeTransformationTest
import org.junit.Test

import static extension tools.vitruv.framework.tests.change.util.ChangeAssertHelper.*

class ChangeDescription2RemoveEAttributeValueTest extends ChangeDescription2ChangeTransformationTest {

//	@Ignore
//	@Test
//	def public testUnsetRemoveEAttributeValue() {
//		this.rootElement.multiValuedEAttribute.add(42)
//		// test
//		startRecording
//
//		// unset 
//		this.rootElement.eUnset(this.rootElement.getFeatureByName(MULTI_VALUE_E_ATTRIBUTE_NAME))
//
//		val subtractiveChanges = claimChange(0).assertExplicitUnset.subtractiveChanges
//		subtractiveChanges.assertRemoveEAttribute(this.rootElement, MULTI_VALUE_E_ATTRIBUTE_NAME, 42, 0)
//	}

	@Test
	def public testRemoveEAttributeValue() {
		this.rootElement.multiValuedEAttribute.add(42)
		// test
		startRecording

		// set to default/clear
		this.rootElement.multiValuedEAttribute.remove(0)

		val changes = getChanges()
		changes.assertRemoveEAttribute(this.rootElement, MULTI_VALUE_E_ATTRIBUTE_NAME, 42, 0)
	}
	
	@Test
	def public testClearEAttributeValue() {
		this.rootElement.multiValuedEAttribute.add(42)
		// test
		startRecording

		// set to default/clear
		this.rootElement.multiValuedEAttribute.clear

		val changes = getChanges()
		changes.assertRemoveEAttribute(this.rootElement, MULTI_VALUE_E_ATTRIBUTE_NAME, 42, 0)
	}

}
