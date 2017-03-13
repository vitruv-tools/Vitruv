package tools.vitruv.framework.tests.change.util

import tools.vitruv.framework.change.echange.AdditiveEChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.SubtractiveEChange
import tools.vitruv.framework.change.echange.compound.MoveEObject
import tools.vitruv.framework.util.datatypes.Quadruple
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.Assert

import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.feature.reference.UpdateReferenceEChange
import tools.vitruv.framework.change.echange.root.RootEChange
import tools.vitruv.framework.change.echange.feature.list.UpdateSingleListEntryEChange
import tools.vitruv.framework.change.echange.compound.CompoundEChange
import org.eclipse.emf.common.util.URI
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange

class ChangeAssertHelper {

	private new() {
	}

	public static def <T> T assertObjectInstanceOf(Object object, Class<T> type) {
		Assert.assertTrue("The object " + object.class.simpleName + " should be type of " + type.simpleName,
			type.isInstance(object))
		return type.cast(object)
	}

	public static def <T extends AdditiveEChange<?>, SubtractiveEChange> assertOldAndNewValue(T eChange,
		Object oldValue, Object newValue) {
		eChange.assertOldValue(oldValue)
		eChange.assertNewValue(newValue)
	}

	public static def assertOldValue(EChange eChange, Object oldValue) {
		Assert.assertEquals("old value must be the same than the given old value", oldValue,
			(eChange as SubtractiveEChange<?>).oldValue)
	}

	public static def assertNewValue(AdditiveEChange<?> eChange, Object newValue) {
		val newValueInChange = eChange.newValue
		var condition = newValue == null && newValueInChange == null;
		if (newValue instanceof EObject && newValueInChange instanceof EObject) {
			val newEObject = newValue as EObject
			var newEObjectInChange = newValueInChange as EObject
			condition = EcoreUtil.equals(newEObject, newEObjectInChange)
		} else if (!condition) {
			condition = newValue != null && newValue.equals(newValueInChange)
		}
		Assert.assertTrue(
			"new value in change ' " + newValueInChange + "' must be the same than the given new value '" + newValue +
				"'!", condition)
	}

	public static def void assertAffectedEObject(EChange eChange, EObject expectedAffectedEObject) {
		if (eChange instanceof FeatureEChange<?, ?>) {
			Assert.assertEquals("The actual affected EObject is a different one than the expected affected EObject",
				expectedAffectedEObject, (eChange as FeatureEChange<?, ?>).affectedEObject)
		} else if (eChange instanceof EObjectExistenceEChange<?>) {
			Assert.assertEquals("The actual affected EObject is a different one than the expected affected EObject",
				expectedAffectedEObject, (eChange as EObjectExistenceEChange<?>).affectedEObject)
		} else {
			throw new IllegalArgumentException();
		}
	}

	public static def assertAffectedEFeature(EChange eChange, EStructuralFeature expectedEFeature) {
		Assert.assertEquals(
			"The actual affected EStructuralFeature is a different one than the expected EStructuralFeature",
			expectedEFeature, (eChange as FeatureEChange<?, ?>).affectedFeature)
	}

	public static def getFeatureByName(EObject eObject, String name) {
		eObject.eClass.getEStructuralFeature(name)
	}

	def public static void assertPermuteAttributeListTest(List<?> changes, EObject rootElement,
		List<Integer> expectedIndicesForElementsAtOldIndices, EStructuralFeature affectedFeature) {
	}

	def static void assertContainment(UpdateReferenceEChange<?> updateEReference, boolean expectedValue) {
		Assert.assertEquals("The containment information of the change " + updateEReference + " is wrong",
			expectedValue, updateEReference.isContainment)
	}

	def static void assertUri(RootEChange rootChange, String expectedValue) {
		Assert.assertEquals("Change " + rootChange + " shall have the uri " + URI.createFileURI(expectedValue).toString,
			URI.createFileURI(expectedValue).toString, rootChange.uri)
	}

	def static void assertIndex(UpdateSingleListEntryEChange<?, ?> change, int expectedIndex) {
		Assert.assertEquals("The value is not at the correct index", expectedIndex, change.index)
	}

	def public static assertMoveEObject(EChange change, int atomicChanges) {
		val moveEObject = assertObjectInstanceOf(change, MoveEObject)
		moveEObject.assertAtomicChanges(atomicChanges)
		val subtractiveReferenceChange = moveEObject.subtractWhatChange
		val removeUpdateEReferenceChange = moveEObject.subtractWhereChange
		val addEReferenceChange = moveEObject.addWhatChange
		val addUpdateEReferenceChange = moveEObject.
			addWhereChange
		return new Quadruple<EObjectSubtractedEChange<?>, UpdateReferenceEChange<?>, EObjectAddedEChange<?>, UpdateReferenceEChange<?>>(
			subtractiveReferenceChange, removeUpdateEReferenceChange, addEReferenceChange, addUpdateEReferenceChange)

	}

	def public static assertAtomicChanges(CompoundEChange eCompoundChange, int atomicChanges) {
		Assert.assertEquals("Expected exactly " + atomicChanges + " changes in move EObject",
			eCompoundChange.atomicChanges.size, atomicChanges)
	}

}
