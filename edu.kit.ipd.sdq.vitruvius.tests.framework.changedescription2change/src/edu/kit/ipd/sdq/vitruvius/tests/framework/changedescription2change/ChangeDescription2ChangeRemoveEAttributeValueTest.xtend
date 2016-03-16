package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change

import org.junit.Test

import static extension edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeAssertHelper.*
import static extension edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeDescription2ChangeTransformationTestUtil.*
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.RemoveEAttributeValue

class ChangeDescription2ChangeRemoveEAttributeValueTest extends ChangeDescription2ChangeTransformationTest{
	
	@Test
	def public testRemoveEAttributeValue(){
		this.testReplaceSingleValuedAttribute(0, 42)
		//test
		startRecording
		
		//set to default value
		this.testReplaceSingleValuedAttribute(42, 0)
		
		val changes = getChanges()
		
		changes.assertSingleChangeWithType(RemoveEAttributeValue)
		val removeEAttributeValue = changes.get(0) as RemoveEAttributeValue<?, ?>
		removeEAttributeValue.assertAffectedEObject(this.rootElement)
		removeEAttributeValue.assertOldValue(42)
	} 
}