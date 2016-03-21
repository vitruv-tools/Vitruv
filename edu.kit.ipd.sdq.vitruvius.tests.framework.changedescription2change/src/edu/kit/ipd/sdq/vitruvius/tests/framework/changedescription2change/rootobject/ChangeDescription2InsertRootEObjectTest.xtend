package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.rootobject

import org.junit.Test

import static extension edu.kit.ipd.sdq.commons.util.java.util.ListUtil.*
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
		val insertChange = changes.claimElementAt(0)
		val setIDChange = changes.claimElementAt(1)
		// FIXME MK KEEP ON WORKING HERE resolve proxy given in change
		insertChange.assertInsertRootEObject(this.rootElement, false)
		setIDChange.assertReplaceSingleValueEAttribute("",this.rootElement.id)
	}
	
}

