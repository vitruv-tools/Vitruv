package tools.vitruv.framework.tests.change.rootobject

import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.Test

class ChangeDescription2RemoveRootEObject extends ChangeDescription2RootChangeTest{
	
	@Test
	def void removeDeleteRootEObjectInResource(){
		// prepare
		insertRootEObjectInResource(this.resource)
		startRecordingOnResourceSet
		// test
		removeRootEObjectInResource()
		// assert
		val isDelete = true
		assertRemoveRoot(0, isDelete, this.uri, this.resource)
	}
	
	def private void removeRootEObjectInResource(){
		EcoreUtil.delete(this.rootElement)
	}	
}
