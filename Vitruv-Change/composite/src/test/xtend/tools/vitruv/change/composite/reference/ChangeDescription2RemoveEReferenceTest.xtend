package tools.vitruv.change.composite.reference

import org.junit.jupiter.api.Test
import tools.vitruv.change.atomic.eobject.DeleteEObject
import tools.vitruv.change.composite.ChangeDescription2ChangeTransformationTest

import static allElementTypes.AllElementTypesPackage.Literals.*
import static org.junit.jupiter.api.Assertions.assertTrue
import static tools.vitruv.change.composite.util.ChangeAssertHelper.*
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.*

import static extension tools.vitruv.change.composite.util.AtomicEChangeAssertHelper.*
import static extension tools.vitruv.change.composite.util.CompoundEChangeAssertHelper.*

class ChangeDescription2RemoveEReferenceTest extends ChangeDescription2ChangeTransformationTest {

	@Test
	def void testRemoveNonContainmentEReferenceFromList() {
		// prepare
		val nonRoot = aet.NonRoot
		uniquePersistedRoot => [
			singleValuedContainmentEReference = nonRoot
			multiValuedNonContainmentEReference += nonRoot
		]

		// test
		val result = uniquePersistedRoot.record [
			multiValuedNonContainmentEReference -= nonRoot
		]

		// assert
		result.assertChangeCount(1)
			.assertRemoveEReference(uniquePersistedRoot, ROOT__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE, nonRoot, 0, false)
			.assertEmpty
	}
	
	@Test
	def void testRemoveNonContainmentEReferenceFromListWithExplicitUnset() {
		// prepare
		val nonRoot = aet.NonRoot
		uniquePersistedRoot => [
			singleValuedContainmentEReference = nonRoot
			multiValuedUnsettableNonContainmentEReference += nonRoot
		]

		// test
		val result = uniquePersistedRoot.record [
			eUnset(ROOT__MULTI_VALUED_UNSETTABLE_NON_CONTAINMENT_EREFERENCE)
		]

		// assert
		result.assertChangeCount(2)
			.assertRemoveEReference(uniquePersistedRoot, ROOT__MULTI_VALUED_UNSETTABLE_NON_CONTAINMENT_EREFERENCE, nonRoot, 0, false)
			.assertUnsetFeature(uniquePersistedRoot, ROOT__MULTI_VALUED_UNSETTABLE_NON_CONTAINMENT_EREFERENCE)
			.assertEmpty
	}
	
	@Test
	def void testRemoveContainmentEReferenceFromList() {
		// prepare
		val nonRoot = aet.NonRoot
		uniquePersistedRoot => [
			singleValuedContainmentEReference = nonRoot
			multiValuedContainmentEReference += nonRoot
		]

		// test
		val result = uniquePersistedRoot.record [
			multiValuedContainmentEReference -= nonRoot
		]

		// assert
		result.assertChangeCount(2)
			.assertRemoveEReference(uniquePersistedRoot, ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE, nonRoot, 0, true)
			.assertDeleteEObject(nonRoot)
			.assertEmpty
	}

	@Test
	def void testRemoveContainmentEReferenceFromListWithExplicitUnset() {
		// prepare
		val nonRoot = aet.NonRoot
		uniquePersistedRoot => [
			singleValuedContainmentEReference = nonRoot
			multiValuedUnsettableContainmentEReference += nonRoot
		]

		// test
		val result = uniquePersistedRoot.record [
			eUnset(ROOT__MULTI_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE)
		]

		// assert
		result.assertChangeCount(3)
			.assertRemoveEReference(uniquePersistedRoot, ROOT__MULTI_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE, nonRoot, 0, true)
			.assertUnsetFeature(uniquePersistedRoot, ROOT__MULTI_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE)
			.assertDeleteEObject(nonRoot)
			.assertEmpty
	}
	
	@Test
	def void testRemoveElementAndReinsertContainedOne() {
		// prepare
		val containedRoot = aet.Root
		val nonRoot = aet.NonRoot
		uniquePersistedRoot => [
			recursiveRoot = containedRoot => [
				singleValuedContainmentEReference = nonRoot
			]
		]

		// test
		val result = uniquePersistedRoot.record [
			recursiveRoot = null
			singleValuedContainmentEReference = nonRoot
		]

		// assert
		result.assertChangeCount(4)
			.assertReplaceSingleValuedEReference(uniquePersistedRoot, ROOT__RECURSIVE_ROOT, containedRoot, null, true, false, false)
			.assertReplaceSingleValuedEReference(containedRoot, ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE, nonRoot, null, true, false, false)
			.assertReplaceSingleValuedEReference(uniquePersistedRoot, ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE, null, nonRoot, true, false, false)
			.assertDeleteEObjectAndContainedElements(containedRoot)
			.assertEmpty
	}
	
	@Test
	def void testRemoveElementAndReinsertContainedInContainedOne() {
		// prepare
		val containedRoot = aet.Root
		val innerContainedRoot = aet.Root
		val nonRoot = aet.NonRoot
		uniquePersistedRoot => [
			recursiveRoot = containedRoot => [
				recursiveRoot = innerContainedRoot => [
					singleValuedContainmentEReference = nonRoot
				]
			]
		]

		// test
		val result = uniquePersistedRoot.record [
			recursiveRoot = null
			singleValuedContainmentEReference = nonRoot
		]

		// assert
		result.assertChangeCount(5)
			.assertReplaceSingleValuedEReference(uniquePersistedRoot, ROOT__RECURSIVE_ROOT, containedRoot, null, true, false, false)
			.assertReplaceSingleValuedEReference(innerContainedRoot, ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE, nonRoot, null, true, false, false)
			.assertReplaceSingleValuedEReference(uniquePersistedRoot, ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE, null, nonRoot, true, false, false)
			.assertDeleteEObjectAndContainedElements(containedRoot)
			.assertEmpty
	}
	
	@Test
	def void testClearEReferences() {
		// prepare
		val nonRoot1 = aet.NonRoot
		val nonRoot2 = aet.NonRoot
		uniquePersistedRoot => [
			multiValuedContainmentEReference += nonRoot1
			multiValuedContainmentEReference += nonRoot2
		]

		// test
		val result = uniquePersistedRoot.record [
			multiValuedContainmentEReference.clear
		]

		// assert
		val deleteChanges = result.assertChangeCount(4)
			.assertRemoveEReference(uniquePersistedRoot, ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE, nonRoot2, 1, true)
			.assertRemoveEReference(uniquePersistedRoot, ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE, nonRoot1, 0, true)
			.assertChangeCount(2)
		// order of delete changes is random, thus use custom assertions
		deleteChanges.forEach[assertType(it, DeleteEObject)]
		val deletedObjects = deleteChanges.map[(it as DeleteEObject<?>).affectedElement]
		assertTrue(deletedObjects.contains(nonRoot1), "deleted objects are missing " + nonRoot1)
		assertTrue(deletedObjects.contains(nonRoot2), "deleted objects are missing " + nonRoot2)
	}
}
