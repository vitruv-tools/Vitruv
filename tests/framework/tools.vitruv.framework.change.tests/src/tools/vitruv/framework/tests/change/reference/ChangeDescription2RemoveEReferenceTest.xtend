package tools.vitruv.framework.tests.change.reference

import static extension tools.vitruv.framework.tests.change.util.AtomicEChangeAssertHelper.*
import static extension tools.vitruv.framework.tests.change.util.CompoundEChangeAssertHelper.*
import static allElementTypes.AllElementTypesPackage.Literals.*
import org.junit.jupiter.api.Test
import static extension tools.vitruv.testutils.metamodels.AllElementTypesCreators.*
import tools.vitruv.framework.tests.change.ChangeDescription2ChangeTransformationTest

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
			.assertRemoveAndDeleteNonRoot(uniquePersistedRoot, ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE, nonRoot, 0)
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
			.assertRemoveAndDeleteNonRoot(uniquePersistedRoot, ROOT__MULTI_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE, nonRoot, 0)
			.assertUnsetFeature(uniquePersistedRoot, ROOT__MULTI_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE)
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
			.assertReplaceAndDeleteNonRoot(containedRoot, uniquePersistedRoot, ROOT__RECURSIVE_ROOT, false)
			.assertReplaceSingleValuedEReference(containedRoot, ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE, nonRoot, null, true, false, false)
			.assertReplaceSingleValuedEReference(uniquePersistedRoot, ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE, null, nonRoot, true, false, false)
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
		result.assertChangeCount(4)
			.assertReplaceAndDeleteNonRoot(containedRoot, uniquePersistedRoot, ROOT__RECURSIVE_ROOT, false)
			.assertReplaceSingleValuedEReference(innerContainedRoot, ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE, nonRoot, null, true, false, false)
			.assertReplaceSingleValuedEReference(uniquePersistedRoot, ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE, null, nonRoot, true, false, false)
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
		result.assertChangeCount(4)
			.assertRemoveAndDeleteNonRoot(uniquePersistedRoot, ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE, nonRoot2, 1)
			.assertRemoveAndDeleteNonRoot(uniquePersistedRoot, ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE, nonRoot1, 0)
			.assertEmpty
	}
}
