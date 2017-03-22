package tools.vitruv.framework.tests.change.util

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.eobject.DeleteEObject
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import tools.vitruv.framework.change.echange.feature.attribute.InsertEAttributeValue
import tools.vitruv.framework.change.echange.feature.attribute.RemoveEAttributeValue
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.framework.change.echange.root.RemoveRootEObject
import tools.vitruv.framework.change.echange.root.RootEChange

import static org.junit.Assert.*

import static extension tools.vitruv.framework.tests.change.util.ChangeAssertHelper.*

class AtomicEChangeAssertHelper {
	public def static assertEObjectExistenceChange(EChange change, EObject affectedEObject, Resource stagingArea) {
		val eObjectExistingChange = assertObjectInstanceOf(change, EObjectExistenceEChange);
		eObjectExistingChange.assertAffectedEObject(affectedEObject)
		eObjectExistingChange.assertStagingArea(stagingArea)
	}
	public def static assertCreateEObject(EChange change, EObject affectedEObject, Resource stagingArea) {
		val createEObject = assertObjectInstanceOf(change, CreateEObject);
		createEObject.assertEObjectExistenceChange(affectedEObject, stagingArea);
	}
			
	public def static assertDeleteEObject(EChange change, EObject affectedEObject, Resource stagingArea) {
		val deleteEObject = assertObjectInstanceOf(change, DeleteEObject);
		deleteEObject.assertEObjectExistenceChange(affectedEObject, stagingArea);
	}
	
	def public static assertRootEChange(EChange change, String uri, Resource resource) {
		val rootChange = change.assertObjectInstanceOf(RootEChange)
		rootChange.assertUri(uri)
		rootChange.assertResource(resource)
	}
	
	def public static assertInsertRootEObject(EChange change, Object expectedNewValue, String uri, Resource resource) {
		val insertRoot = change.assertObjectInstanceOf(InsertRootEObject)
		insertRoot.assertNewValue(expectedNewValue)
		insertRoot.assertRootEChange(uri, resource)
	}

	def public static assertRemoveRootEObject(EChange change, Object expectedOldValue, String uri, Resource resource) {
		val removeRoot = change.assertObjectInstanceOf(RemoveRootEObject)
		removeRoot.assertOldValue(expectedOldValue)
		removeRoot.assertRootEChange(uri, resource)
	}
	
	
	def public static assertReplaceSingleValuedEAttribute(EChange change, EObject affectedEObject, EStructuralFeature affectedFeature,
			Object expectedOldValue, Object expectedNewValue) {
		val removeEAttributeValue = assertObjectInstanceOf(change, ReplaceSingleValuedEAttribute)
		removeEAttributeValue.assertAffectedEObject(affectedEObject)
		removeEAttributeValue.assertAffectedEFeature(affectedFeature)
		removeEAttributeValue.assertOldValue(expectedOldValue)
		removeEAttributeValue.assertNewValue(expectedNewValue)
	}
	
	def public static assertInsertEAttribute(EChange change, EObject affectedEObject, EStructuralFeature affectedFeature,
			Object expectedNewValue, int expectedIndex) {
		val insertEAttributValue = assertObjectInstanceOf(change, InsertEAttributeValue)
		insertEAttributValue.assertAffectedEObject(insertEAttributValue.affectedEObject as EObject)
		insertEAttributValue.assertNewValue(expectedNewValue)
		insertEAttributValue.assertIndex(expectedIndex)
		insertEAttributValue.assertAffectedEFeature(affectedFeature)
	}
	
	def public static assertRemoveEAttribute(EChange change, EObject affectedEObject, EStructuralFeature affectedFeature,
			Object expectedOldValue, int expectedOldIndex) {
		val removeEAttributeValue = assertObjectInstanceOf(change, RemoveEAttributeValue)
		removeEAttributeValue.assertAffectedEObject(affectedEObject)
		removeEAttributeValue.assertAffectedEFeature(affectedFeature)
		removeEAttributeValue.assertOldValue(expectedOldValue)
		removeEAttributeValue.assertIndex(expectedOldIndex)
	}
	
	
	def static void assertReplaceSingleValuedEReference(EChange change, EObject affectedEObject, EStructuralFeature affectedFeature,
			Object expectedOldValue, Object expectedNewValue, boolean isContainment) {
		val replaceChange = change.assertObjectInstanceOf(ReplaceSingleValuedEReference)
		replaceChange.assertOldAndNewValue(expectedOldValue, expectedNewValue)
		replaceChange.assertAffectedEFeature(affectedFeature)
		replaceChange.assertAffectedEObject(affectedEObject)
		replaceChange.assertContainment(isContainment)
	}
	
	def static void assertSetSingleValuedEReference(EChange change,	EObject affectedEObject, EStructuralFeature affectedFeature, 
			EObject expectedNewValue, boolean isContainment, boolean isCreate) {
		val replaceChange = change.assertObjectInstanceOf(ReplaceSingleValuedEReference)
		replaceChange.assertReplaceSingleValuedEReference(affectedEObject, affectedFeature, null, expectedNewValue, isContainment);
		assertFalse(replaceChange.isFromNonDefaultValue);
		assertTrue(replaceChange.isToNonDefaultValue);
	}
		
	def static void assertUnsetSingleValuedEReference(EChange change, EObject affectedEObject, EStructuralFeature affectedFeature,
			EObject expectedOldValue, boolean isContainment, boolean isDelete) {
		val replaceChange = change.assertObjectInstanceOf(ReplaceSingleValuedEReference)
		replaceChange.assertReplaceSingleValuedEReference(affectedEObject, affectedFeature, expectedOldValue, null, isContainment);
		assertTrue(replaceChange.isFromNonDefaultValue);
		assertFalse(replaceChange.isToNonDefaultValue);
	}
	
	// FIXME GENERICS
	def public static void assertInsertEReference(EChange change, EObject affectedEObject, EStructuralFeature affectedFeature, 
			EObject expectedNewValue, int expectedIndex, boolean isContainment) {
		val insertEReference = change.assertObjectInstanceOf(InsertEReference)
		insertEReference.assertAffectedEObject(affectedEObject)
		insertEReference.assertAffectedEFeature(affectedFeature)
		insertEReference.assertNewValue(expectedNewValue)
		insertEReference.assertIndex(expectedIndex)
		insertEReference.assertContainment(isContainment)
	}
	
	def public static void assertRemoveEReference(EChange change, EObject affectedEObject, EStructuralFeature affectedFeature,
			EObject expectedOldValue, int expectedOldIndex, boolean isContainment) {
		val subtractiveChange = assertObjectInstanceOf(change, RemoveEReference)
		subtractiveChange.assertAffectedEFeature(affectedFeature)
		subtractiveChange.assertAffectedEObject(affectedEObject)
		subtractiveChange.assertOldValue(expectedOldValue)
		subtractiveChange.assertIndex(expectedOldIndex)
		subtractiveChange.assertContainment(isContainment)
	}
			
}