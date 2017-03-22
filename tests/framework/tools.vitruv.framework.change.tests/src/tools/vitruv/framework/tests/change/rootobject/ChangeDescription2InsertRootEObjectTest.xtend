package tools.vitruv.framework.tests.change.rootobject

import org.junit.Test

class ChangeDescription2InsertRootEObjectTest extends ChangeDescription2RootChangeTest {
	
	@Test
	def void insertCreateRootEObjectInResource(){
		// prepare
		startRecordingOnResourceSet
		// test
		insertRootEObjectInResource(this.resource1)
		// assert
		val isCreate = true
		assertInsertRoot(0, isCreate, this.uri1, this.resource1)
		//claimChange(1).assertReplaceSingleValueEAttribute(null, this.rootElement.id)
	}
	
	@Test
	def void insertCreateTwoRootEObjectsInResource(){
		// prepare
		startRecordingOnResourceSet
		// test
		insertRootEObjectInResource(this.resource1)
		// assert
		val isCreate = true
		assertInsertRoot(0, isCreate, this.uri1, this.resource1)
		
		startRecordingOnResourceSet
		// test
		insertRootEObjectInResource2(this.resource1)
		// assert
		assertInsertRoot2(0, isCreate, this.uri1, this.resource1)
		
		//claimChange(1).assertReplaceSingleValueEAttribute(null, this.rootElement.id)
	}
}

