package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.rootobject

import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.ChangeDescription2ChangeTransformationTest
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.junit.Before

class ChangeDescription2RootChangeTest extends ChangeDescription2ChangeTransformationTest{
	
	var protected Resource resource
	
	@Before
	def override beforeTest(){
		super.beforeTest
		val rs = new ResourceSetImpl
		resource = rs.createResource(URI.createFileURI("dummyURI"))
	}
	
	def protected startRecordingOnResource() {
		this.changeRecorder.startObserving(resource)
		this.changeRecorder.beginRec()
	}
}