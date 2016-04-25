package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.rootobject

import org.junit.Test

import static extension edu.kit.ipd.sdq.commons.util.java.util.ListUtil.*
import static extension edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeAssertHelper.*

class ChangeDescription2InsertRootEObjectTest extends ChangeDescription2RootChangeTest{
	
	@Test
	def void insertCreateRootEObjectInResource(){
		// prepare
		startRecordingOnResourceSet
		// test and assert
		insertRootEObjectInResource(true)
	}
	
	@Test
	def void insertNonCreateRootEObjectInResource(){
		// prepare
		this.resource1.contents.add(this.rootElement)
		startRecordingOnResourceSet
		// test and assert
		insertRootEObjectInResource(false)
	}
	
	def private void insertRootEObjectInResource(boolean isCreate){
		// test
		this.resource2.contents.add(this.rootElement)
		// assert
		assertInsertRoot(isCreate)
	}
	
	def private void assertInsertRoot(boolean isCreate) {
		val changes = getChanges()
		val insertChange = changes.claimElementAt(0)
		val setIDChange = changes.claimElementAt(1)
		insertChange.assertInsertRootEObject(this.rootElement, isCreate)
		setIDChange.assertReplaceSingleValueEAttribute(null,this.rootElement.id)
	}
	
}

