package tools.vitruv.framework.tests.change.rootobject

import org.junit.Test

class ChangeDescription2InsertRootEObjectTest extends ChangeDescription2RootChangeTest {
	
	override prepareRootElement() {
		this.rootElement = createRootInResource(1);
		this.rootElement2 = createRootInResource(2);
	}
	
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
		changes.assertInsertRoot(isCreate, resource);
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
		changes.assertInsertRoot(isCreate, resource1);
		
		startRecording
		// test
		insertRootEObjectInResource2(resource2)
		// assert
		changes.assertInsertRoot2(isCreate, resource2);
		
		//claimChange(1).assertReplaceSingleValueEAttribute(null, this.rootElement.id)
	}
}

