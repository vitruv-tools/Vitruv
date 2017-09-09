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
		val isCreate = false
		changes.assertRemoveRoot(isDelete, resource1)
			.assertInsertRoot(isCreate, resource2)
	}
}