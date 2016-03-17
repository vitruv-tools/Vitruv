package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.attribute

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.RemoveEAttributeValue
import org.junit.Test

import static extension edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeAssertHelper.*
import static extension edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeDescription2ChangeTransformationTestUtil.*

class ChangeDescription2ChangeRemoveEAttributeValueTest extends edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.ChangeDescription2ChangeTransformationTest{
	
	@Test
	def public testUnsetRemoveEAttributeValue(){
		this.testReplaceSingleValuedAttribute(0, 42)
		//test
		startRecording
		
		//unset 
		this.rootElement.eUnset(this.rootElement.getFeautreByName(SINGE_VALUE_E_ATTRIBUTE_NAME))
		
		val changes = getChanges()
		
		changes.assertSingleChangeWithType(RemoveEAttributeValue)
		val removeEAttributeValue = changes.get(0) as RemoveEAttributeValue<?, ?>
		removeEAttributeValue.assertAffectedEObject(this.rootElement)
		removeEAttributeValue.assertOldValue(42)
	} 
	
	@Test
	def public testRemoveEAttributeValue(){
		this.testReplaceSingleValuedAttribute(0, 42)
		//test
		startRecording
		
		//set to default
		this.testReplaceSingleValuedAttribute(42, 0)
		
		val changes = getChanges()
		
		changes.assertSingleChangeWithType(RemoveEAttributeValue)
		val removeEAttributeValue = changes.get(0) as RemoveEAttributeValue<?, ?>
		removeEAttributeValue.assertAffectedEObject(this.rootElement)
		removeEAttributeValue.assertOldValue(42)
	} 
}
