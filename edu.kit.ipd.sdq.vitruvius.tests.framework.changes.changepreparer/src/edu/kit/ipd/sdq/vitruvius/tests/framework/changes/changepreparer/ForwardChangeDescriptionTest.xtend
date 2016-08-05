package edu.kit.ipd.sdq.vitruvius.tests.framework.changes.changepreparer

import edu.kit.ipd.sdq.vitruvius.tests.framework.changes.changepreparer.rootobject.ChangeDescription2RootChangeTest
import java.util.HashMap
import java.util.Map
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.xbase.lib.Functions.Function1
import org.junit.Assert
import org.junit.Test

class ForwardChangeDescriptionTest extends ChangeDescription2RootChangeTest {
//	@Test
//	def void insertRootEObjectInResource(){
//		// prepare
//		startRecordingOnResourceSet
//		
//		//test
//		this.resource1.contents.add(this.rootElement)
//		
//		//assert
//		val fcd = this.changeRecorder.endRecording()
//		val Map<EObject,EObject> attachContainersBefore = createMapForEachElement(fcd.objectsToAttach,[fcd.getNewContainer(it)])
//		val Map<EObject,EReference> attachReferencesBefore = createMapForEachElement(fcd.objectsToAttach,[fcd.getNewContainmentReference(it)])
//		val Map<EObject,EObject> detachContainersBefore = createMapForEachElement(fcd.objectsToDetach,[fcd.getNewContainer(it)])
//		val Map<EObject,EReference> detachReferencesBefore = createMapForEachElement(fcd.objectsToDetach,[fcd.getNewContainmentReference(it)])
//		
//		val cdi = fcd.changeDescription
//		val Map<EObject,EObject> attachContainersAfter = createMapForEachElement(fcd.objectsToAttach,[cdi.getOldContainer(it)])
//		val Map<EObject,EReference> attachReferencesAfter = createMapForEachElement(fcd.objectsToAttach,[cdi.getOldContainmentFeature(it)])
//		val Map<EObject,EObject> detachContainersAfter = createMapForEachElement(fcd.objectsToDetach,[cdi.getOldContainer(it)])
//		val Map<EObject,EReference> detachReferencesAfter = createMapForEachElement(fcd.objectsToDetach,[cdi.getOldContainmentFeature(it)])
//		
//		cdi.applyAndReverse()
//		val Map<EObject,EObject> attachContainersAfterAfter = createMapForEachElement(fcd.objectsToAttach,[cdi.getOldContainer(it)])
//		val Map<EObject,EReference> attachReferencesAfterAfter = createMapForEachElement(fcd.objectsToAttach,[cdi.getOldContainmentFeature(it)])
//		val Map<EObject,EObject> detachContainersAfterAfter = createMapForEachElement(fcd.objectsToDetach,[cdi.getOldContainer(it)])
//		val Map<EObject,EReference> detachReferencesAfterAfter = createMapForEachElement(fcd.objectsToDetach,[cdi.getOldContainmentFeature(it)])
//		
//		// assert status before revision
//		Assert.assertNull(attachContainersBefore.get(this.rootElement))
//		Assert.assertNull(attachReferencesBefore.get(this.rootElement))
//		Assert.assertNull(detachContainersBefore.get(this.rootElement))
//		Assert.assertNull(detachReferencesBefore.get(this.rootElement))
//		
//		// assert unwanted behaviour of ChangeDescriptionImpl after revision:
//		// Yes, before revision of the backward change, the root element was contained in the change description.
//		// But, we would like to know where it was contained before the change, not before the revision.
//		Assert.assertNotNull(attachContainersAfter.get(this.rootElement))
//		Assert.assertNotNull(attachReferencesAfter.get(this.rootElement))
//		Assert.assertNull(detachContainersAfter.get(this.rootElement))
//		Assert.assertNull(detachReferencesAfter.get(this.rootElement))
//		
//		// assert status before = status after two revisions
//		Assert.assertEquals(attachContainersBefore.get(this.rootElement), attachContainersAfterAfter.get(this.rootElement))
//		Assert.assertEquals(attachReferencesBefore.get(this.rootElement), attachReferencesAfterAfter.get(this.rootElement))
//		Assert.assertEquals(detachReferencesBefore.get(this.rootElement), detachReferencesAfterAfter.get(this.rootElement))
//		Assert.assertEquals(detachReferencesBefore.get(this.rootElement), detachContainersAfterAfter.get(this.rootElement))
//	}
	
	def private <T> Map<EObject,T> createMapForEachElement(Iterable<EObject> iterable, Function1<EObject,T> f) {
		val Map<EObject,T> map = new HashMap<EObject,T>()
		forEachPut(iterable, map, f)
		return map
	}

	def private <T> void forEachPut(Iterable<EObject> iterable, Map<EObject,T> map, Function1<EObject,T> f){
		iterable.forEach[map.put(it,f.apply(it))]
	}
}
