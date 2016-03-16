package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.InsertEAttributeValue
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.ReplaceSingleValuedEAttribute
import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.ChangeDescription2ChangeTransformationTest

import static extension edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeAssertHelper.*

class ChangeDescription2ChangeTransformationTestUtil {
	private new(){
		
	}
	
	def public static testReplaceSingleValuedAttribute(ChangeDescription2ChangeTransformationTest test, int oldValue, int newValue){
		// the test
		test.startRecording
		test.rootElement.setSingleValuedEAttribute(newValue)

		// get the changes
		val changes = test.getChanges()

		// assert the changes
		changes.assertSingleChangeWithType(ReplaceSingleValuedEAttribute)
		val replaceEAttribute = changes.get(0) as ReplaceSingleValuedEAttribute<?, ?>
		replaceEAttribute.assertOldAndNewValue(oldValue, newValue)
	}	
	
	def public static void testInsertEAttributeValue(ChangeDescription2ChangeTransformationTest changeDescription2Change, int expectedIndex, int expectedValue, int position) {
		// test
		changeDescription2Change.startRecording
		if (position == -1) {
			changeDescription2Change.rootElement.multiValuedEAttribute.add(expectedValue)
		}else{
			changeDescription2Change.rootElement.multiValuedEAttribute.add(position, expectedValue)
		}

		// get changes
		val changes = changeDescription2Change.getChanges()

		// assert
		changes.assertSingleChangeWithType(InsertEAttributeValue)
		val insertEAttributValue = changes.get(0) as InsertEAttributeValue<?, ?>
		insertEAttributValue.assertAffectedEObject(insertEAttributValue.affectedEObject)

		// insertEAttributValue.affectedFeature.assertAffectedFeature()
		insertEAttributValue.index
		insertEAttributValue.newValue
	}
}
