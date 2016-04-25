package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.rootobject

import org.junit.Test
import org.junit.Before
import org.eclipse.emf.ecore.util.EcoreUtil

import static extension edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeAssertHelper.*

class ChangeDescription2RemoveRootEObject extends ChangeDescription2RootChangeTest{
	
	@Before
	override beforeTest(){
		super.beforeTest
		this.resource1.contents.add(this.rootElement)
		startRecordingOnResourceSet	
	}
	
	@Test
	def public void testRemoveRootEObject(){
		this.resource1.contents.clear
		assertRemoveEObject(false)		
	}
	
	@Test
	def public void testRemoveRootEObjectWithDelete(){
		EcoreUtil.delete(this.rootElement)
		assertRemoveEObject(true)	
	}
	
	def assertRemoveEObject(boolean isDelete) {
		val changes = getChanges()
		changes.assertRemoveRootEObject(this.rootElement, isDelete)
	}
		
}
