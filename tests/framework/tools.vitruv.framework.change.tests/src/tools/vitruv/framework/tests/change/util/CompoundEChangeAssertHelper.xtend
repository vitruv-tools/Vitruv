package tools.vitruv.framework.tests.change.util

import static extension tools.vitruv.framework.tests.change.util.ChangeAssertHelper.*;
import static extension tools.vitruv.framework.tests.change.util.AtomicEChangeAssertHelper.*;
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot
import org.eclipse.emf.ecore.EStructuralFeature
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot
import org.junit.Assert
import tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceAndDeleteNonRoot
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteRoot
import tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot

class CompoundEChangeAssertHelper {
	def public static <A extends EObject, T extends EObject> CreateAndInsertNonRoot<A, T> assertCreateAndInsertNonRoot(
			EChange change, A affectedEObject, EStructuralFeature affectedFeature, T expectedNewValue, int expectedIndex) {
		val createAndInsert = change.assertObjectInstanceOf(CreateAndInsertNonRoot)
		createAndInsert.createChange.assertCreateEObject(expectedNewValue);
		createAndInsert.insertChange.assertInsertEReference(affectedEObject, affectedFeature, expectedNewValue,
			expectedIndex, true);
		return createAndInsert
	}

	def public static <A extends EObject, T extends EObject> RemoveAndDeleteNonRoot<A, T> assertRemoveAndDeleteNonRoot(
			EChange change, A affectedEObject, EStructuralFeature affectedFeature, T expectedOldValue, int expectedOldIndex) {
		val compositeChange = assertObjectInstanceOf(change, RemoveAndDeleteNonRoot)
		compositeChange.deleteChange.assertDeleteEObject(expectedOldValue);
		compositeChange.removeChange.assertRemoveEReference(affectedEObject, affectedFeature, expectedOldValue,
			expectedOldIndex, true);
		return compositeChange
	}
	
	def static void assertCreateAndReplaceAndDeleteNonRoot(EChange change, EObject expectedOldValue,
			EObject expectedNewValue, EStructuralFeature affectedFeature, EObject affectedEObject, boolean isContainment) {
		val compositeChange = change.assertObjectInstanceOf(CreateAndReplaceAndDeleteNonRoot)
		compositeChange.createChange.assertCreateEObject(expectedNewValue)
		compositeChange.deleteChange.assertDeleteEObject(expectedOldValue)
		compositeChange.replaceChange.assertReplaceSingleValuedEReference(affectedEObject, affectedFeature, 
			expectedOldValue, expectedNewValue, isContainment)
	}
	
	def public static void assertCreateAndInsertRootEObject(EChange change, EObject expectedNewValue, String uri) {
		val compositeChange = change.assertObjectInstanceOf(CreateAndInsertRoot)
		compositeChange.createChange.assertCreateEObject(expectedNewValue)
		compositeChange.insertChange.assertInsertRootEObject(expectedNewValue, uri)
	}
	
	def public static void assertRemoveAndDeleteRootEObject(EChange change, EObject expectedOldValue, String uri) {
		val compositeChange = change.assertObjectInstanceOf(RemoveAndDeleteRoot)
		compositeChange.deleteChange.assertDeleteEObject(expectedOldValue)
		compositeChange.removeChange.assertRemoveRootEObject(expectedOldValue, uri)
	}

	def public static <A extends EObject, T, S extends SubtractiveAttributeEChange<A, T>> ExplicitUnsetEFeature<A, T> assertExplicitUnset(
			EChange change) {
		val unsetChange = change.assertObjectInstanceOf(ExplicitUnsetEFeature)
		Assert.assertEquals("atomic changes should be the same than the subtractive changes",
			unsetChange.atomicChanges, unsetChange.subtractiveChanges)
		return unsetChange
	}
	
}
		