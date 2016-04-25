package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.rootobject

import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.ChangeDescription2ChangeTransformationTest
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.junit.Before

class ChangeDescription2RootChangeTest extends ChangeDescription2ChangeTransformationTest{
	
	var rs = new ResourceSetImpl
	var protected Resource resource1
	var protected Resource resource2
	
	@Before
	def override beforeTest(){
		super.beforeTest
		resource1 = rs.createResource(URI.createFileURI("dummyURI"))
		resource2 = rs.createResource(URI.createFileURI("dummyURI2"))
	}
	
	def protected startRecordingOnResourceSet() {
		this.changeRecorder.startObserving(rs)
		this.changeRecorder.beginRec()
	}
}