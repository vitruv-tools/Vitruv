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

import static extension tools.vitruv.framework.tests.change.util.ChangeAssertHelper.*
import static extension tools.vitruv.framework.tests.change.util.CompoundEChangeAssertHelper.*
import tools.vitruv.framework.change.echange.feature.UnsetFeature
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertEquals
import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.common.util.URI

@Utility
class AtomicEChangeAssertHelper {
	def static void assertEObjectExistenceChange(EObjectExistenceEChange<?> change, EObject affectedEObject) {
		change.assertAffectedEObject(affectedEObject)
	}

	def static Iterable<? extends EChange> assertCreateEObject(Iterable<? extends EChange> changes,
		EObject affectedEObject) {
		changes.assertSizeGreaterEquals(1)
		val createObject = assertType(changes.get(0), CreateEObject)
		createObject.assertEObjectExistenceChange(affectedEObject)
		return changes.tail
	}

	def static Iterable<? extends EChange> assertDeleteEObject(Iterable<? extends EChange> changes,
		EObject affectedEObject) {
		changes.assertSizeGreaterEquals(1)
		val deleteObject = assertType(changes.get(0), DeleteEObject)
		deleteObject.assertEObjectExistenceChange(affectedEObject)
		return changes.tail
	}

	def private static assertRootEChange(RootEChange change, String uri, Resource resource) {
		change.assertUri(uri)
		change.assertResource(resource)
	}

	def static Iterable<? extends EChange> assertInsertRootEObject(Iterable<? extends EChange> changes,
		Object expectedNewValue, String uri, Resource resource) {
		changes.assertSizeGreaterEquals(1)
		val insertRopot = assertType(changes.get(0), InsertRootEObject)
		insertRopot.assertNewValue(expectedNewValue)
		insertRopot.assertRootEChange(uri, resource)
		return changes.tail
	}

	def static Iterable<? extends EChange> assertRemoveRootEObject(Iterable<? extends EChange> changes,
		Object expectedOldValue, String uri, Resource resource) {
		changes.assertSizeGreaterEquals(1)
		val removeRoot = assertType(changes.get(0), RemoveRootEObject)
		removeRoot.assertOldValue(expectedOldValue)
		removeRoot.assertRootEChange(uri, resource)
		return changes.tail
	}

	def static Iterable<? extends EChange> assertReplaceSingleValuedEAttribute(Iterable<? extends EChange> changes,
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

	def static Iterable<? extends EChange> assertInsertEAttribute(Iterable<? extends EChange> changes,
		EObject affectedEObject, EStructuralFeature affectedFeature, Object expectedNewValue, int expectedIndex,
		boolean wasUnset) {
		changes.assertSizeGreaterEquals(1)
		val insertEAttributValue = assertType(changes.get(0), InsertEAttributeValue)
		insertEAttributValue.assertAffectedEObject(insertEAttributValue.affectedEObject)
		insertEAttributValue.assertNewValue(expectedNewValue)
		insertEAttributValue.assertIndex(expectedIndex)
		insertEAttributValue.assertAffectedEFeature(affectedFeature)
		assertEquals(wasUnset, insertEAttributValue.wasUnset)
		return changes.tail
	}

	def static Iterable<? extends EChange> assertRemoveEAttribute(Iterable<? extends EChange> changes,
		EObject affectedEObject, EStructuralFeature affectedFeature, Object expectedOldValue, int expectedOldIndex) {
		changes.assertSizeGreaterEquals(1)
		val removeEAttributeValue = assertType(changes.get(0), RemoveEAttributeValue)
		removeEAttributeValue.assertAffectedEObject(affectedEObject)
		removeEAttributeValue.assertAffectedEFeature(affectedFeature)
		removeEAttributeValue.assertOldValue(expectedOldValue)
		removeEAttributeValue.assertIndex(expectedOldIndex)
		return changes.tail
	}

	def static Iterable<? extends EChange> assertReplaceSingleValuedEReference(Iterable<? extends EChange> changes,
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

	def static Iterable<? extends EChange> assertSetSingleValuedEReference(Iterable<? extends EChange> changes,
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

	def static Iterable<? extends EChange> assertUnsetSingleValuedEReference(Iterable<? extends EChange> changes,
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

	def static Iterable<? extends EChange> assertInsertEReference(Iterable<? extends EChange> changes,
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

	def static Iterable<? extends EChange> assertRemoveEReference(Iterable<? extends EChange> changes,
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

	def static Iterable<? extends EChange> assertUnsetFeature(Iterable<? extends EChange> changes,
		EObject affectedEObject, EStructuralFeature affectedFeature) {
		changes.assertSizeGreaterEquals(1)
		val unsetChange = assertType(changes.get(0), UnsetFeature)
		unsetChange.assertAffectedEFeature(affectedFeature)
		unsetChange.assertAffectedEObject(affectedEObject)
		return changes.tail
	}

	def static Iterable<? extends EChange> assertInsertRoot(Iterable<? extends EChange> changes, EObject rootElement,
		boolean isCreate, Resource resource) {
		if (isCreate) {
			return changes.assertCreateAndInsertRootEObject(rootElement, resource.URI.toFileString, resource)
		} else {
			return changes.assertInsertRootEObject(rootElement, resource.URI.toFileString, resource)
		}
	}

	def static Iterable<? extends EChange> assertRemoveRoot(Iterable<? extends EChange> changes, EObject rootElement,
		boolean isDelete, Resource resource, URI uri) {
		if (isDelete) {
			changes.assertRemoveAndDeleteRootEObject(rootElement, uri.toFileString, resource)
		} else {
			changes.assertRemoveRootEObject(rootElement, uri.toFileString, resource)
		}
	}

	def static Iterable<? extends EChange> assertRemoveRoot(Iterable<? extends EChange> changes, EObject rootElement,
		boolean isDelete, Resource resource) {
		changes.assertRemoveRoot(rootElement, isDelete, resource, resource.URI)
	}

	def static assertEmpty(Iterable<? extends EChange> changes) {
		assertEquals(0, changes.size)
	}

}
