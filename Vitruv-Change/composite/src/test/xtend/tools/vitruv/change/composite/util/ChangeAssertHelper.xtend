package tools.vitruv.change.composite.util

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.change.atomic.AdditiveEChange
import tools.vitruv.change.atomic.EChange
import tools.vitruv.change.atomic.SubtractiveEChange
import tools.vitruv.change.atomic.eobject.EObjectExistenceEChange
import tools.vitruv.change.atomic.feature.FeatureEChange
import tools.vitruv.change.atomic.feature.list.UpdateSingleListEntryEChange
import tools.vitruv.change.atomic.feature.reference.UpdateReferenceEChange
import tools.vitruv.change.atomic.root.RootEChange
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertEquals
import edu.kit.ipd.sdq.activextendannotations.Utility
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.core.IsInstanceOf.instanceOf
import static org.hamcrest.core.Is.is
import static tools.vitruv.change.testutils.matchers.ModelMatchers.equalsDeeply

@Utility
class ChangeAssertHelper {

	static def <T extends AdditiveEChange<?, ?>, SubtractiveEChange> assertOldAndNewValue(T eChange, Object oldValue,
		Object newValue) {
		eChange.assertOldValue(oldValue)
		eChange.assertNewValue(newValue)
	}

	static def assertOldValue(EChange<?> eChange, Object oldValue) {
		if (oldValue instanceof EObject) {
			assertThat("old value must be the same or a copy as the given old value", oldValue,
				equalsDeeply((eChange as SubtractiveEChange<?, ?>).oldValue as EObject))
		} else {
			assertEquals(oldValue, (eChange as SubtractiveEChange<?, ?>).oldValue,
				"old value must be the same as the given old value")
		}
	}

	static def assertNewValue(AdditiveEChange<?, ?> eChange, Object newValue) {
		val newValueInChange = eChange.newValue
		var condition = newValue === null && newValueInChange === null
		if (newValue instanceof EObject && newValueInChange instanceof EObject) {
			val newEObject = newValue as EObject
			var newEObjectInChange = newValueInChange as EObject
			assertThat(
				'''new value in change '«newValueInChange»' must be the same as the given new value '«newValue»!''',
				newEObject,
				equalsDeeply(newEObjectInChange)
			)
		} else if (!condition) {
			assertNotNull(newValue)
			assertEquals(newValue,
				newValueInChange, '''new value in change '«newValueInChange»' must be the same as the given new value '«newValue»!''')
		}
	}

	static def void assertAffectedEObject(EChange<EObject> eChange, EObject expectedAffectedEObject) {
		switch eChange {
			FeatureEChange<EObject, ?>:
				assertThat("The actual affected EObject is a different one than the expected affected EObject or its copy",
				expectedAffectedEObject, equalsDeeply(eChange.affectedElement))
			EObjectExistenceEChange<EObject>:
				assertThat("The actual affected EObject is a different one than the expected affected EObject or its copy",
				expectedAffectedEObject, equalsDeeply(eChange.affectedElement))
			default:
				throw new IllegalArgumentException()
		}
	}

	static def assertAffectedEFeature(EChange<?> eChange, EStructuralFeature expectedEFeature) {
		assertEquals(expectedEFeature, (eChange as FeatureEChange<?, ?>).affectedFeature,
			"The actual affected EStructuralFeature is a different one than the expected EStructuralFeature")
	}

	def static void assertContainment(UpdateReferenceEChange<?> updateEReference, boolean expectedValue) {
		assertEquals(expectedValue,
			updateEReference.isContainment, '''The containment information of the change «updateEReference» is wrong''')
	}

	def static void assertUri(RootEChange<?> rootChange, String expectedValue) {
		assertEquals(URI.createFileURI(expectedValue).toString,
			rootChange.uri, '''Change «rootChange» shall have the uri «URI.createFileURI(expectedValue)»''')
	}

	def static void assertResource(RootEChange<?> rootChange, Resource resource) {
		assertEquals(rootChange.resource, resource, '''Change «rootChange» shall have the resource «resource»''')
	}

	def static void assertIndex(UpdateSingleListEntryEChange<?, ?> change, int expectedIndex) {
		assertEquals(expectedIndex, change.index, "The value is not at the correct index")
	}

	static def <T> T assertType(Object original, Class<T> type) {
		assertThat(original, is(instanceOf(type)))
		return original as T
	}

	static def void assertSizeGreaterEquals(Iterable<?> iterable, int size) {
		assertTrue(iterable.size >= size)
	}

}
