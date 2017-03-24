package tools.vitruv.framework.tests.change.rootobject

import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.Test

class ChangeDescription2RemoveRootEObjectTest extends ChangeDescription2RootChangeTest {
	
	@Test
	def void removeDeleteRootEObjectInResource(){
		val resource = this.rootElement.eResource;
		// prepare
		startRecording
		// test
		removeRootEObjectInResource()
		// assert
		val isDelete = true
		assertRemoveRoot(0, isDelete, resource.URI.toFileString)
	}
	
	def private void removeRootEObjectInResource(){
		EcoreUtil.delete(this.rootElement)
	}	
}
