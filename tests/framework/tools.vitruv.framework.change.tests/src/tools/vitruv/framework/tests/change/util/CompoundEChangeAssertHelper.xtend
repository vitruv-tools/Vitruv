package tools.vitruv.framework.tests.change.util

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.echange.EChange

import static extension tools.vitruv.framework.tests.change.util.AtomicEChangeAssertHelper.*
import static extension tools.vitruv.framework.tests.change.util.ChangeAssertHelper.*
import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility
class CompoundEChangeAssertHelper {
	def static Iterable<? extends EChange> assertCreateAndInsertNonRoot(
			Iterable<? extends EChange> changes, EObject affectedEObject, EStructuralFeature affectedFeature, EObject expectedNewValue, int expectedIndex, boolean wasUnset) {
		changes.assertSizeGreaterEquals(2)
		return changes.assertCreateEObject(expectedNewValue)
			.assertInsertEReference(affectedEObject, affectedFeature, expectedNewValue,	expectedIndex, true, wasUnset)
	}

	def static Iterable<? extends EChange> assertRemoveAndDeleteNonRoot(
			Iterable<? extends EChange> changes, EObject affectedEObject, EStructuralFeature affectedFeature, EObject expectedOldValue, int expectedOldIndex) {
		changes.assertSizeGreaterEquals(2)
		return changes.assertRemoveEReference(affectedEObject, affectedFeature, expectedOldValue, expectedOldIndex, true)
			.assertDeleteEObject(expectedOldValue)
	}
	
	def static Iterable<? extends EChange> assertCreateAndReplaceAndDeleteNonRoot(Iterable<? extends EChange> changes, EObject expectedOldValue,
			EObject expectedNewValue, EStructuralFeature affectedFeature, EObject affectedEObject, boolean isContainment, boolean wasUnset, boolean isUnset) {
		changes.assertSizeGreaterEquals(3)
		return changes.assertCreateEObject(expectedNewValue)
			.assertReplaceSingleValuedEReference(affectedEObject, affectedFeature, expectedOldValue, expectedNewValue, isContainment, wasUnset, isUnset)
			.assertDeleteEObject(expectedOldValue)
	}
	
	def static Iterable<? extends EChange> assertCreateAndReplaceNonRoot(Iterable<? extends EChange> changes, EObject expectedNewValue,
		EObject affectedEObject, EStructuralFeature affectedFeature, boolean wasUnset) {
		changes.assertSizeGreaterEquals(2)
		return changes.assertCreateEObject(expectedNewValue)
			.assertReplaceSingleValuedEReference(affectedEObject, affectedFeature, null, expectedNewValue, true, wasUnset, false)
	}
	
	def static Iterable<? extends EChange> assertReplaceAndDeleteNonRoot(Iterable<? extends EChange> changes, EObject expectedOldValue,
		EObject affectedEObject, EStructuralFeature affectedFeature, boolean isUnset) {
		changes.assertSizeGreaterEquals(2)
		return changes.assertReplaceSingleValuedEReference(affectedEObject, affectedFeature, expectedOldValue, null, true, false, isUnset)
			.assertDeleteEObject(expectedOldValue)
	}

	def static Iterable<? extends EChange> assertCreateAndInsertRootEObject(Iterable<? extends EChange> changes, EObject expectedNewValue, String uri, Resource resource) {
		changes.assertSizeGreaterEquals(2)
		return changes.assertCreateEObject(expectedNewValue)
			.assertInsertRootEObject(expectedNewValue, uri, resource)
	}

	def static Iterable<? extends EChange> assertRemoveAndDeleteRootEObject(Iterable<? extends EChange> changes, EObject expectedOldValue, String uri, Resource resource) {
		changes.assertSizeGreaterEquals(2)
		return changes.assertRemoveRootEObject(expectedOldValue, uri, resource)
			.assertDeleteEObject(expectedOldValue)
	}
}
		