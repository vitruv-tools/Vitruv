package tools.vitruv.change.composite.util

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.change.atomic.EChange
import tools.vitruv.change.atomic.eobject.CreateEObject
import tools.vitruv.change.atomic.eobject.DeleteEObject
import tools.vitruv.change.atomic.eobject.EObjectExistenceEChange
import tools.vitruv.change.atomic.feature.UnsetFeature
import tools.vitruv.change.atomic.feature.attribute.InsertEAttributeValue
import tools.vitruv.change.atomic.feature.attribute.RemoveEAttributeValue
import tools.vitruv.change.atomic.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.change.atomic.feature.reference.InsertEReference
import tools.vitruv.change.atomic.feature.reference.RemoveEReference
import tools.vitruv.change.atomic.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.change.atomic.root.InsertRootEObject
import tools.vitruv.change.atomic.root.RemoveRootEObject
import tools.vitruv.change.atomic.root.RootEChange

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertTrue

import static extension tools.vitruv.change.composite.util.ChangeAssertHelper.*
import static extension tools.vitruv.change.composite.util.CompoundEChangeAssertHelper.*

@Utility
class AtomicEChangeAssertHelper {
	def static void assertEObjectExistenceChange(EObjectExistenceEChange<EObject> change, EObject affectedEObject) {
		change.assertAffectedEObject(affectedEObject)
	}

	def static Iterable<? extends EChange<EObject>> assertCreateEObject(Iterable<? extends EChange<EObject>> changes,
		EObject affectedEObject) {
		changes.assertSizeGreaterEquals(1)
		val createObject = assertType(changes.get(0), CreateEObject)
		createObject.assertEObjectExistenceChange(affectedEObject)
		return changes.tail
	}

	def static Iterable<? extends EChange<EObject>> assertDeleteEObject(Iterable<? extends EChange<EObject>> changes,
		EObject affectedEObject) {
		changes.assertSizeGreaterEquals(1)
		val deleteObject = assertType(changes.get(0), DeleteEObject)
		deleteObject.assertEObjectExistenceChange(affectedEObject)
		return changes.tail
	}

	def private static assertRootEChange(RootEChange<EObject> change, String uri, Resource resource) {
		change.assertUri(uri)
		change.assertResource(resource)
	}

	def static Iterable<? extends EChange<EObject>> assertInsertRootEObject(Iterable<? extends EChange<EObject>> changes,
		Object expectedNewValue, String uri, Resource resource) {
		changes.assertSizeGreaterEquals(1)
		val insertRopot = assertType(changes.get(0), InsertRootEObject)
		insertRopot.assertNewValue(expectedNewValue)
		insertRopot.assertRootEChange(uri, resource)
		return changes.tail
	}

	def static Iterable<? extends EChange<EObject>> assertRemoveRootEObject(Iterable<? extends EChange<EObject>> changes,
		Object expectedOldValue, String uri, Resource resource) {
		changes.assertSizeGreaterEquals(1)
		val removeRoot = assertType(changes.get(0), RemoveRootEObject)
		removeRoot.assertOldValue(expectedOldValue)
		removeRoot.assertRootEChange(uri, resource)
		return changes.tail
	}

	def static Iterable<? extends EChange<EObject>> assertReplaceSingleValuedEAttribute(Iterable<? extends EChange<EObject>> changes,
		EObject affectedEObject, EStructuralFeature affectedFeature, Object expectedOldValue, Object expectedNewValue,
		boolean wasUnset, boolean isUnset) {
		changes.assertSizeGreaterEquals(1)
		val removeEAttributeValue = assertType(changes.get(0), ReplaceSingleValuedEAttribute)
		removeEAttributeValue.assertAffectedEObject(affectedEObject)
		removeEAttributeValue.assertAffectedEFeature(affectedFeature)
		removeEAttributeValue.assertOldValue(expectedOldValue)
		removeEAttributeValue.assertNewValue(expectedNewValue)
		assertEquals(wasUnset, removeEAttributeValue.wasUnset)
		assertEquals(isUnset, removeEAttributeValue.isUnset)
		return changes.tail
	}

	def static Iterable<? extends EChange<EObject>> assertInsertEAttribute(Iterable<? extends EChange<EObject>> changes,
		EObject affectedEObject, EStructuralFeature affectedFeature, Object expectedNewValue, int expectedIndex,
		boolean wasUnset) {
		changes.assertSizeGreaterEquals(1)
		val InsertEAttributeValue<EObject, ?> insertEAttributValue = assertType(changes.get(0), InsertEAttributeValue)
		insertEAttributValue.assertAffectedEObject(insertEAttributValue.affectedElement)
		insertEAttributValue.assertNewValue(expectedNewValue)
		insertEAttributValue.assertIndex(expectedIndex)
		insertEAttributValue.assertAffectedEFeature(affectedFeature)
		assertEquals(wasUnset, insertEAttributValue.wasUnset)
		return changes.tail
	}

	def static Iterable<? extends EChange<EObject>> assertRemoveEAttribute(Iterable<? extends EChange<EObject>> changes,
		EObject affectedEObject, EStructuralFeature affectedFeature, Object expectedOldValue, int expectedOldIndex) {
		changes.assertSizeGreaterEquals(1)
		val removeEAttributeValue = assertType(changes.get(0), RemoveEAttributeValue)
		removeEAttributeValue.assertAffectedEObject(affectedEObject)
		removeEAttributeValue.assertAffectedEFeature(affectedFeature)
		removeEAttributeValue.assertOldValue(expectedOldValue)
		removeEAttributeValue.assertIndex(expectedOldIndex)
		return changes.tail
	}

	def static Iterable<? extends EChange<EObject>> assertReplaceSingleValuedEReference(Iterable<? extends EChange<EObject>> changes,
		EObject affectedEObject, EStructuralFeature affectedFeature, Object expectedOldValue, Object expectedNewValue,
		boolean isContainment, boolean wasUnset, boolean isUnset) {
		changes.assertSizeGreaterEquals(1)
		val replaceChange = assertType(changes.get(0), ReplaceSingleValuedEReference)
		replaceChange.assertOldAndNewValue(expectedOldValue, expectedNewValue)
		replaceChange.assertAffectedEFeature(affectedFeature)
		replaceChange.assertAffectedEObject(affectedEObject)
		replaceChange.assertContainment(isContainment)
		assertEquals(wasUnset, replaceChange.wasUnset)
		assertEquals(isUnset, replaceChange.isUnset)
		return changes.tail
	}

	def static Iterable<? extends EChange<EObject>> assertSetSingleValuedEReference(Iterable<? extends EChange<EObject>> changes,
		EObject affectedEObject, EStructuralFeature affectedFeature, EObject expectedNewValue, boolean isContainment,
		boolean isCreate, boolean wasUnset) {
		if (isContainment && isCreate) {
			return changes.assertCreateAndReplaceNonRoot(expectedNewValue, affectedEObject, affectedFeature, wasUnset)
		} else {
			changes.assertSizeGreaterEquals(1)
			val replaceChange = assertType(changes.get(0), ReplaceSingleValuedEReference)
			changes.assertReplaceSingleValuedEReference(affectedEObject, affectedFeature, null, expectedNewValue,
				isContainment, wasUnset, false)
			assertFalse(replaceChange.isFromNonDefaultValue)
			assertTrue(replaceChange.isToNonDefaultValue)
			return changes.tail
		}
	}

	def static Iterable<? extends EChange<EObject>> assertUnsetSingleValuedEReference(Iterable<? extends EChange<EObject>> changes,
		EObject affectedEObject, EStructuralFeature affectedFeature, EObject expectedOldValue, boolean isContainment,
		boolean isDelete, boolean isUnset) {
		if (isContainment && isDelete) {
			return changes.assertReplaceAndDeleteNonRoot(expectedOldValue, affectedEObject, affectedFeature, isUnset)
		} else {
			changes.assertSizeGreaterEquals(1)
			val replaceChange = assertType(changes.get(0), ReplaceSingleValuedEReference)
			changes.assertReplaceSingleValuedEReference(affectedEObject, affectedFeature, expectedOldValue, null,
				isContainment, false, isUnset)
			assertTrue(replaceChange.isFromNonDefaultValue)
			assertFalse(replaceChange.isToNonDefaultValue)
			return changes.tail
		}
	}

	def static Iterable<? extends EChange<EObject>> assertInsertEReference(Iterable<? extends EChange<EObject>> changes,
		EObject affectedEObject, EStructuralFeature affectedFeature, EObject expectedNewValue, int expectedIndex,
		boolean isContainment, boolean wasUnset) {
		changes.assertSizeGreaterEquals(1)
		val insertEReference = assertType(changes.get(0), InsertEReference)
		insertEReference.assertAffectedEObject(affectedEObject)
		insertEReference.assertAffectedEFeature(affectedFeature)
		insertEReference.assertNewValue(expectedNewValue)
		insertEReference.assertIndex(expectedIndex)
		insertEReference.assertContainment(isContainment)
		assertEquals(wasUnset, insertEReference.wasUnset)
		return changes.tail
	}

	def static Iterable<? extends EChange<EObject>> assertRemoveEReference(Iterable<? extends EChange<EObject>> changes,
		EObject affectedEObject, EStructuralFeature affectedFeature, EObject expectedOldValue, int expectedOldIndex,
		boolean isContainment) {
		changes.assertSizeGreaterEquals(1)
		val subtractiveChange = assertType(changes.get(0), RemoveEReference)
		subtractiveChange.assertAffectedEFeature(affectedFeature)
		subtractiveChange.assertAffectedEObject(affectedEObject)
		subtractiveChange.assertOldValue(expectedOldValue)
		subtractiveChange.assertIndex(expectedOldIndex)
		subtractiveChange.assertContainment(isContainment)
		return changes.tail
	}

	def static Iterable<? extends EChange<EObject>> assertUnsetFeature(Iterable<? extends EChange<EObject>> changes,
		EObject affectedEObject, EStructuralFeature affectedFeature) {
		changes.assertSizeGreaterEquals(1)
		val unsetChange = assertType(changes.get(0), UnsetFeature)
		unsetChange.assertAffectedEFeature(affectedFeature)
		unsetChange.assertAffectedEObject(affectedEObject)
		return changes.tail
	}

	def static Iterable<? extends EChange<EObject>> assertInsertRoot(Iterable<? extends EChange<EObject>> changes, EObject rootElement,
		boolean isCreate, Resource resource) {
		if (isCreate) {
			return changes.assertCreateAndInsertRootEObject(rootElement, resource.URI.toFileString, resource)
		} else {
			return changes.assertInsertRootEObject(rootElement, resource.URI.toFileString, resource)
		}
	}

	def static Iterable<? extends EChange<EObject>> assertRemoveRoot(Iterable<? extends EChange<EObject>> changes, EObject rootElement,
		boolean isDelete, Resource resource, URI uri) {
		if (isDelete) {
			changes.assertRemoveAndDeleteRootEObject(rootElement, uri.toFileString, resource)
		} else {
			changes.assertRemoveRootEObject(rootElement, uri.toFileString, resource)
		}
	}

	def static Iterable<? extends EChange<EObject>> assertRemoveRoot(Iterable<? extends EChange<EObject>> changes, EObject rootElement,
		boolean isDelete, Resource resource) {
		changes.assertRemoveRoot(rootElement, isDelete, resource, resource.URI)
	}

	def static assertEmpty(Iterable<? extends EChange<?>> changes) {
		assertEquals(0, changes.size)
	}

}
