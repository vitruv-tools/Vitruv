package tools.vitruvius.framework.tests.change.rootobject

import tools.vitruvius.framework.tests.change.ChangeDescription2ChangeTransformationTest
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.junit.Before

import static extension tools.vitruvius.framework.tests.change.util.ChangeAssertHelper.*


class ChangeDescription2RootChangeTest extends ChangeDescription2ChangeTransformationTest{
	
	var rs = new ResourceSetImpl
	var protected Resource resource1
	var protected Resource resource2
	var protected String uri1 = "/dummyURI1"
	var protected String uri2 = "/dummyURI2"
	
	@Before
	def override beforeTest(){
		super.beforeTest
		resource1 = rs.createResource(URI.createFileURI(uri1))
		resource2 = rs.createResource(URI.createFileURI(uri2))
	}
	
	def protected startRecordingOnResourceSet() {
		startRecording(#[rs])
	}
	
		
	def protected void assertInsertRoot(int index, boolean isCreate, String uri) {
		claimChange(index).assertInsertRootEObject(this.rootElement, isCreate, uri)
	}
	
	def protected void assertRemoveRoot(int index, boolean isDelete, String uri) {
		claimChange(index).assertRemoveRootEObject(this.rootElement, isDelete, uri)
	}
	
	def protected void insertRootEObjectInResource(Resource resource){
		resource.contents.add(this.rootElement)
	}
}