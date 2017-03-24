package tools.vitruv.framework.tests.change.rootobject

import org.junit.Test
import org.junit.Ignore

class ChangeDescription2MoveRootTest extends ChangeDescription2RootChangeTest {
	
	@Ignore
	@Test
	def void moveRootEObjectBetweenResources(){
		// prepare
		insertRootEObjectInResource(this.resource)
		startRecordingOnResourceSet
		// test 
		insertRootEObjectInResource(this.resource2)
		// assert
		val isDelete = false
		assertRemoveRoot(0, isDelete, this.uri, this.resource)
		val isCreate = false
		assertInsertRoot(1, isCreate, this.uri2, this.resource2)
	}
}