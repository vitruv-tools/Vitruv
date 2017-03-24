package tools.vitruv.framework.tests.change.rootobject

import org.junit.Test
import org.junit.Ignore

class ChangeDescription2MoveRootTest extends ChangeDescription2RootChangeTest {
	
	@Ignore
	@Test
	def void moveRootEObjectBetweenResources(){
		val resource1 = this.rootElement.eResource;
		val resource2 = this.rootElement2.eResource;
		resource2.contents.clear();
		// prepare
		startRecording
		// test 
		insertRootEObjectInResource(resource2)
		// assert
		val isDelete = false
		assertRemoveRoot(0, isDelete, resource1.URI.toFileString)
		val isCreate = false
		assertInsertRoot(1, isCreate, resource2.URI.toFileString)
	}
}