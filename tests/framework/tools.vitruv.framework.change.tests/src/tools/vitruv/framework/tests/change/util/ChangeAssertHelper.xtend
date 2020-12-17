package tools.vitruv.framework.tests.change.util

import java.util.List
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.change.echange.AdditiveEChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.SubtractiveEChange
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.feature.list.UpdateSingleListEntryEChange
import tools.vitruv.framework.change.echange.feature.reference.UpdateReferenceEChange
import tools.vitruv.framework.change.echange.root.RootEChange
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.fail
import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility
class ChangeAssertHelper {

	static def <T> T assertObjectInstanceOf(Object object, Class<T> type) {
		assertTrue(
			type.isInstance(object), '''The object «object.class.simpleName» should be type of «type.simpleName»''')
		return type.cast(object)
	}

	static def <T extends AdditiveEChange<?>, SubtractiveEChange> assertOldAndNewValue(T eChange, Object oldValue,
		Object newValue) {
		eChange.assertOldValue(oldValue)
		eChange.assertNewValue(newValue)
	}

	static def assertOldValue(EChange eChange, Object oldValue) {
		if (oldValue instanceof EObject) {
			assertEqualsOrCopy(oldValue, (eChange as SubtractiveEChange<?>).oldValue as EObject,
				"old value must be the same or a copy than the given old value")
		} else {
			assertEquals(oldValue, (eChange as SubtractiveEChange<?>).oldValue,
				"old value must be the same than the given old value")
		}
	}

	static def assertNewValue(AdditiveEChange<?> eChange, Object newValue) {
		val newValueInChange = eChange.newValue
		var condition = newValue === null && newValueInChange === null;
		if (newValue instanceof EObject && newValueInChange instanceof EObject) {
			val newEObject = newValue as EObject
			var newEObjectInChange = newValueInChange as EObject
			condition = EcoreUtil.equals(newEObject, newEObjectInChange)
		} else if (!condition) {
			condition = newValue !== null && newValue.equals(newValueInChange)
		}
		assertTrue(
			condition, '''new value in change '«newValueInChange»' must be the same than the given new value '«newValue»!''')
	}

	static def void assertAffectedEObject(EChange eChange, EObject expectedAffectedEObject) {
		if (eChange instanceof FeatureEChange<?, ?>) {
			assertEqualsOrCopy(expectedAffectedEObject, eChange.affectedEObject,
				"The actual affected EObject is a different one than the expected affected EObject or its copy")
		} else if (eChange instanceof EObjectExistenceEChange<?>) {
			assertEqualsOrCopy(expectedAffectedEObject, eChange.affectedEObject,
				"The actual affected EObject is a different one than the expected affected EObject or its copy")
		} else {
			throw new IllegalArgumentException();
		}
	}

	static def assertAffectedEFeature(EChange eChange, EStructuralFeature expectedEFeature) {
		assertEquals(expectedEFeature, (eChange as FeatureEChange<?, ?>).affectedFeature,
			"The actual affected EStructuralFeature is a different one than the expected EStructuralFeature")
	}

	static def getFeatureByName(EObject eObject, String name) {
		eObject.eClass.getEStructuralFeature(name)
	}

	def static void assertPermuteAttributeListTest(List<?> changes, EObject rootElement,
		List<Integer> expectedIndicesForElementsAtOldIndices, EStructuralFeature affectedFeature) {
	}

	def static void assertContainment(UpdateReferenceEChange<?> updateEReference, boolean expectedValue) {
		assertEquals(expectedValue,
			updateEReference.isContainment, '''The containment information of the change «updateEReference» is wrong''')
	}

	def static void assertUri(RootEChange rootChange, String expectedValue) {
		assertEquals(URI.createFileURI(expectedValue).toString,
			rootChange.uri, '''Change «rootChange» shall have the uri «URI.createFileURI(expectedValue)»''')
	}

	def static void assertResource(RootEChange rootChange, Resource resource) {
		assertEquals(rootChange.resource, resource, '''Change «rootChange» shall have the resource «resource»''')
	}

	def static void assertIndex(UpdateSingleListEntryEChange<?, ?> change, int expectedIndex) {
		assertEquals(expectedIndex, change.index, "The value is not at the correct index")
	}

	def static assertEqualsOrCopy(EObject object1, EObject object2, String message) {
		assertTrue(EcoreUtil.equals(object1, object2), message)
	}

	static def <T> T assertType(Object original, Class<T> type) {
		if (type.isAssignableFrom(original.class)) {
			return original as T
		}
		fail('''Object «original» is not expected type «type»''');
		return null;
	}

	static def void assertSizeGreaterEquals(Iterable<?> iterable, int size) {
		assertTrue(iterable.size >= size)
	}

}
