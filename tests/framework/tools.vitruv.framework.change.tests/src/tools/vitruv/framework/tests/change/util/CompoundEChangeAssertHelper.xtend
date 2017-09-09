package tools.vitruv.framework.tests.change.util

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.Resource
import org.junit.Assert
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEAttribute
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEReference
import tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange
import tools.vitruv.framework.change.echange.feature.reference.SubtractiveReferenceEChange

import static extension tools.vitruv.framework.tests.change.util.AtomicEChangeAssertHelper.*
import static extension tools.vitruv.framework.tests.change.util.ChangeAssertHelper.*

class CompoundEChangeAssertHelper {
	def static Iterable<? extends EChange> assertCreateAndInsertNonRoot(
			Iterable<? extends EChange> changes, EObject affectedEObject, EStructuralFeature affectedFeature, EObject expectedNewValue, int expectedIndex) {
		changes.assertSizeGreaterEquals(2);
		return changes.assertCreateEObject(expectedNewValue)
			.assertInsertEReference(affectedEObject, affectedFeature, expectedNewValue,	expectedIndex, true);
	}

	def static Iterable<? extends EChange> assertRemoveAndDeleteNonRoot(
			Iterable<? extends EChange> changes, EObject affectedEObject, EStructuralFeature affectedFeature, EObject expectedOldValue, int expectedOldIndex) {
		changes.assertSizeGreaterEquals(2);
		return changes.assertRemoveEReference(affectedEObject, affectedFeature, expectedOldValue, expectedOldIndex, true)
			.assertDeleteEObject(expectedOldValue);
	}
	
	def static Iterable<? extends EChange> assertCreateAndReplaceAndDeleteNonRoot(Iterable<? extends EChange> changes, EObject expectedOldValue,
			EObject expectedNewValue, EStructuralFeature affectedFeature, EObject affectedEObject, boolean isContainment) {
		changes.assertSizeGreaterEquals(3);
		return changes.assertCreateEObject(expectedNewValue)
			.assertReplaceSingleValuedEReference(affectedEObject, affectedFeature, expectedOldValue, expectedNewValue, isContainment)
			.assertDeleteEObject(expectedOldValue)
	}
	
	def static Iterable<? extends EChange> assertCreateAndReplaceNonRoot(Iterable<? extends EChange> changes, EObject expectedNewValue,
		EObject affectedEObject, EStructuralFeature affectedFeature) {
		changes.assertSizeGreaterEquals(2);
		return changes.assertCreateEObject(expectedNewValue)
			.assertReplaceSingleValuedEReference(affectedEObject, affectedFeature, null, expectedNewValue, true);
	}
	
	def static Iterable<? extends EChange> assertReplaceAndDeleteNonRoot(Iterable<? extends EChange> changes, EObject expectedOldValue,
		EObject affectedEObject, EStructuralFeature affectedFeature) {
		changes.assertSizeGreaterEquals(2);
		return changes.assertReplaceSingleValuedEReference(affectedEObject, affectedFeature, expectedOldValue, null, true)
			.assertDeleteEObject(expectedOldValue);
	}

	def public static Iterable<? extends EChange> assertCreateAndInsertRootEObject(Iterable<? extends EChange> changes, EObject expectedNewValue, String uri, Resource resource) {
		changes.assertSizeGreaterEquals(2);
		return changes.assertCreateEObject(expectedNewValue)
			.assertInsertRootEObject(expectedNewValue, uri, resource)
	}

	def public static Iterable<? extends EChange> assertRemoveAndDeleteRootEObject(Iterable<? extends EChange> changes, EObject expectedOldValue, String uri, Resource resource) {
		changes.assertSizeGreaterEquals(2);
		return changes.assertRemoveRootEObject(expectedOldValue, uri, resource)
			.assertDeleteEObject(expectedOldValue);
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
		