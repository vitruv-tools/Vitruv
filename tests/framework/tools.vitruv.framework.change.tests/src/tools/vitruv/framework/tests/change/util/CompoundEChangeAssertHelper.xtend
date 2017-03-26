package tools.vitruv.framework.tests.change.util

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.Resource
import org.junit.Assert
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot
import tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceAndDeleteNonRoot
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceNonRoot
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEAttribute
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEReference
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteRoot
import tools.vitruv.framework.change.echange.compound.ReplaceAndDeleteNonRoot
import tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange
import tools.vitruv.framework.change.echange.feature.reference.SubtractiveReferenceEChange
import tools.vitruv.framework.change.echange.util.StagingArea

import static extension tools.vitruv.framework.tests.change.util.AtomicEChangeAssertHelper.*
import static extension tools.vitruv.framework.tests.change.util.ChangeAssertHelper.*

class CompoundEChangeAssertHelper {
	def public static <A extends EObject, T extends EObject> CreateAndInsertNonRoot<A, T> assertCreateAndInsertNonRoot(
			EChange change, A affectedEObject, EStructuralFeature affectedFeature, T expectedNewValue, int expectedIndex) {
		val createAndInsert = change.assertObjectInstanceOf(CreateAndInsertNonRoot)
		createAndInsert.createChange.assertCreateEObject(expectedNewValue, StagingArea.getStagingArea(affectedEObject.eResource));
		createAndInsert.insertChange.assertInsertEReference(affectedEObject, affectedFeature, expectedNewValue,
			expectedIndex, true);
		return createAndInsert
	}

	def public static <A extends EObject, T extends EObject> RemoveAndDeleteNonRoot<A, T> assertRemoveAndDeleteNonRoot(
			EChange change, A affectedEObject, EStructuralFeature affectedFeature, T expectedOldValue, int expectedOldIndex) {
		val compositeChange = assertObjectInstanceOf(change, RemoveAndDeleteNonRoot)
		compositeChange.deleteChange.assertDeleteEObject(expectedOldValue, StagingArea.getStagingArea(affectedEObject.eResource));
		compositeChange.removeChange.assertRemoveEReference(affectedEObject, affectedFeature, expectedOldValue,
			expectedOldIndex, true);
		return compositeChange
	}
	
	def static void assertCreateAndReplaceAndDeleteNonRoot(EChange change, EObject expectedOldValue,
			EObject expectedNewValue, EStructuralFeature affectedFeature, EObject affectedEObject, boolean isContainment) {
		val compositeChange = change.assertObjectInstanceOf(CreateAndReplaceAndDeleteNonRoot)
		compositeChange.createChange.assertCreateEObject(expectedNewValue, StagingArea.getStagingArea(affectedEObject.eResource))
		compositeChange.deleteChange.assertDeleteEObject(expectedOldValue, StagingArea.getStagingArea(affectedEObject.eResource))
		compositeChange.replaceChange.assertReplaceSingleValuedEReference(affectedEObject, affectedFeature, 
			expectedOldValue, expectedNewValue, isContainment)
	}
	
	def static void assertCreateAndReplaceNonRoot(EChange change, EObject expectedNewValue,
		EObject affectedEObject, EStructuralFeature affectedFeature) {
		val compositeChange = assertObjectInstanceOf(change, CreateAndReplaceNonRoot)
		compositeChange.createChange.assertCreateEObject(expectedNewValue, StagingArea.getStagingArea(affectedEObject.eResource))
		compositeChange.insertChange.assertReplaceSingleValuedEReference(affectedEObject, affectedFeature,
			null, expectedNewValue, true)
	}
	
	def static void assertReplaceAndDeleteNonRoot(EChange change, EObject expectedOldValue,
		EObject affectedEObject, EStructuralFeature affectedFeature) {
		val compositeChange = assertObjectInstanceOf(change, ReplaceAndDeleteNonRoot)
		compositeChange.deleteChange.assertDeleteEObject(expectedOldValue, StagingArea.getStagingArea(affectedEObject.eResource))
		compositeChange.removeChange.assertReplaceSingleValuedEReference(affectedEObject, affectedFeature,
			expectedOldValue, null, true)
	}

	def public static void assertCreateAndInsertRootEObject(EChange change, EObject expectedNewValue, String uri, Resource resource) {
		val compositeChange = change.assertObjectInstanceOf(CreateAndInsertRoot)
		compositeChange.createChange.assertCreateEObject(expectedNewValue, StagingArea.getStagingArea(resource))
		compositeChange.insertChange.assertInsertRootEObject(expectedNewValue, uri, resource)
	}

	def public static void assertRemoveAndDeleteRootEObject(EChange change, EObject expectedOldValue, String uri, Resource resource) {
		val compositeChange = change.assertObjectInstanceOf(RemoveAndDeleteRoot)
		compositeChange.deleteChange.assertDeleteEObject(expectedOldValue, StagingArea.getStagingArea(resource))
		compositeChange.removeChange.assertRemoveRootEObject(expectedOldValue, uri, resource)
	}

	def public static <A extends EObject, T, S extends SubtractiveAttributeEChange<A, T>> ExplicitUnsetEAttribute<A, T> assertExplicitUnsetEAttribute(
			EChange change) {
		val unsetChange = change.assertObjectInstanceOf(ExplicitUnsetEAttribute)
		Assert.assertEquals("atomic changes should be the same than the subtractive changes",
			unsetChange.atomicChanges, unsetChange.subtractiveChanges)
		return unsetChange
	}
	
	def public static <A extends EObject, T extends EObject, S extends SubtractiveReferenceEChange<A, T>> ExplicitUnsetEReference<A> assertExplicitUnsetEReference(
			EChange change) {
		val unsetChange = change.assertObjectInstanceOf(ExplicitUnsetEReference)
		//Assert.assertEquals("atomic changes should be the same than the subtractive changes",
			//unsetChange.atomicChanges, unsetChange.changes)
		return unsetChange
	}
	
}
		