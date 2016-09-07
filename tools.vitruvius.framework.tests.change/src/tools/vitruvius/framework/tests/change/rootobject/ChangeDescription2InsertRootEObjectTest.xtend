package tools.vitruvius.framework.tests.change.rootobject

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
		assertInsertRoot(0, isCreate, this.uri1)
		//claimChange(1).assertReplaceSingleValueEAttribute(null, this.rootElement.id)
	}
}

