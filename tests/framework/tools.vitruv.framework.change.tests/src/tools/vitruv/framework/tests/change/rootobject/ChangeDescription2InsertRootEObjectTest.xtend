package tools.vitruv.framework.tests.change.rootobject

import org.junit.Test

class ChangeDescription2InsertRootEObjectTest extends ChangeDescription2RootChangeTest {
	
	@Test
	def void insertCreateRootEObjectInResource(){
		val resource = this.rootElement.eResource;
		resource.contents.clear();
		// prepare
		startRecording
		// test
		insertRootEObjectInResource(resource)
		// assert
		val isCreate = true
		assertInsertRoot(0, isCreate, resource.URI.toFileString)
		//claimChange(1).assertReplaceSingleValueEAttribute(null, this.rootElement.id)
	}
	
	@Test
	def void insertCreateTwoRootEObjectsInResource(){
		val resource1 = this.rootElement.eResource;
		resource1.contents.clear();
		val resource2 = this.rootElement2.eResource;
		resource2.contents.clear();
		// prepare
		startRecording
		// test
		insertRootEObjectInResource(resource1)
		// assert
		val isCreate = true
		assertInsertRoot(0, isCreate, resource1.URI.toFileString)
		
		startRecording
		// test
		insertRootEObjectInResource2(resource2)
		// assert
		assertInsertRoot2(0, isCreate, resource2.URI.toFileString)
		
		//claimChange(1).assertReplaceSingleValueEAttribute(null, this.rootElement.id)
	}
}

