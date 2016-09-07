package tools.vitruvius.framework.tests.change.rootobject

import org.junit.Test

class ChangeDescription2MoveRootTest extends ChangeDescription2RootChangeTest {
	
	@Test
	def void moveRootEObjectBetweenResources(){
		// prepare
		insertRootEObjectInResource(this.resource1)
		startRecordingOnResourceSet
		// test 
		insertRootEObjectInResource(this.resource2)
		// assert
		val isDelete = false
		assertRemoveRoot(0, isDelete, this.uri1)
		val isCreate = false
		assertInsertRoot(1, isCreate, this.uri2)
	}
}