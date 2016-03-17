package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.rootobject

import org.junit.Test

import static extension edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeAssertHelper.*

class ChangeDescription2InsertRootEObjectTest extends ChangeDescription2RootChangeTest{
	
	@Test
	def public void insertRootEObjectInResource(){
		// prepare
		startRecordingOnResource
		
		//test
		resource.contents.add(this.rootElement)
		
		//assert
		val changes = getChanges()
		changes.assertInsertRootEObject(this.rootElement, false)
	}
	
}

