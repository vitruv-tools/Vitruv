package tools.vitruv.change.composite.reference

import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.jupiter.api.Test
import tools.vitruv.change.composite.ChangeDescription2ChangeTransformationTest

import static allElementTypes.AllElementTypesPackage.Literals.*
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.*

import static extension tools.vitruv.change.composite.util.AtomicEChangeAssertHelper.*
import static extension tools.vitruv.change.composite.util.CompoundEChangeAssertHelper.*

class ChangeDescription2ReplaceSingleValuedEReferenceTest extends ChangeDescription2ChangeTransformationTest {

	@Test
	def void testReplaceSingleValuedEReferenceContainment() {
		// prepare
		uniquePersistedRoot
		
		// test
		val nonRoot = aet.NonRoot
		val result = uniquePersistedRoot.record [
			singleValuedContainmentEReference = nonRoot
		]

		// assert
		result.assertChangeCount(3)
			.assertSetSingleValuedEReference(uniquePersistedRoot, ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE, nonRoot, true, true, false)
			.assertReplaceSingleValuedEAttribute(nonRoot, IDENTIFIED__ID, null, nonRoot.id, false, false)
			.assertEmpty
	}

	@Test
	def void testReplaceExistingSingleValuedEReferenceContainment() {
		// prepare
		val nonRoot = aet.NonRoot
		val replaceNonRoot = aet.NonRoot
		uniquePersistedRoot => [
			singleValuedContainmentEReference = nonRoot
		]

		// test
		val result = uniquePersistedRoot.record [
			singleValuedContainmentEReference = replaceNonRoot
		]

		// assert
		result.assertChangeCount(4)
			.assertCreateAndReplaceNonRoot(replaceNonRoot, uniquePersistedRoot, nonRoot, ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE, false)
			.assertReplaceSingleValuedEAttribute(replaceNonRoot, IDENTIFIED__ID, null, replaceNonRoot.id, false, false)
			.assertDeleteEObjectAndContainedElements(nonRoot)
			.assertEmpty
	}
	
	@Test
	def void testReplaceExistingSingleValuedEReferenceContainmentWithDefault() {
		// prepare
		val nonRoot = aet.NonRoot
		uniquePersistedRoot => [
			singleValuedContainmentEReference = nonRoot
		]

		// test
		val result = uniquePersistedRoot.record [
			singleValuedContainmentEReference = null
		]

		// assert
		result.assertChangeCount(2)
			.assertUnsetSingleValuedEReference(uniquePersistedRoot, ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE, nonRoot, true, true, false)
			.assertEmpty
	}

	@Test
	def void testRemoveContainmentReferenceWithDelete() {
		// prepare
		val nonRoot = aet.NonRoot
		uniquePersistedRoot => [
			singleValuedContainmentEReference = nonRoot
		]
		
		// test
		val result = uniquePersistedRoot.record [
			EcoreUtil.delete(nonRoot)
		]

		// assert
		result.assertChangeCount(2)
			.assertUnsetSingleValuedEReference(uniquePersistedRoot, ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE,	nonRoot, true, true, false)
			.assertEmpty
	}

	@Test
	def void testSetSingleValuedEReferenceNonContainment() {
		// prepare
		val nonRoot = aet.NonRoot
		uniquePersistedRoot => [
			singleValuedContainmentEReference = nonRoot
		]
		
		// test
		val result = uniquePersistedRoot.record [
			singleValuedNonContainmentEReference = nonRoot
		]

		// assert
		result.assertChangeCount(1)
			.assertSetSingleValuedEReference(uniquePersistedRoot, ROOT__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE, nonRoot, false, false, false)
			.assertEmpty
	}

	@Test
	def void testReplaceExistingSingleValuedEReferenceNonContainment() {
		// prepare
		val nonRoot = aet.NonRoot
		val replaceNonRoot = aet.NonRoot
		uniquePersistedRoot => [			
			singleValuedContainmentEReference = nonRoot
			singleValuedNonContainmentEReference = nonRoot
			nonRootObjectContainerHelper = aet.NonRootObjectContainerHelper => [
				nonRootObjectsContainment += replaceNonRoot
			]
		]

		// test
		val result = uniquePersistedRoot.record [
			singleValuedNonContainmentEReference = replaceNonRoot
		]

		// assert
		result.assertChangeCount(1)
			.assertReplaceSingleValuedEReference(uniquePersistedRoot, ROOT__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE, nonRoot, replaceNonRoot, false, false, false)
			.assertEmpty
	}

	@Test
	def void testRemoveNonContainmentReferenceWithDelete() {
		// prepare
		val nonRoot = aet.NonRoot
		uniquePersistedRoot => [
			singleValuedContainmentEReference = nonRoot
			singleValuedNonContainmentEReference = nonRoot
		]

		// test
		val result = uniquePersistedRoot.record [
			EcoreUtil.delete(nonRoot)
		]

		// assert
		result.assertChangeCount(3)
			.assertUnsetSingleValuedEReference(uniquePersistedRoot, ROOT__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE, nonRoot, false, false, false)
			.assertUnsetSingleValuedEReference(uniquePersistedRoot, ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE, nonRoot, true, true, false)
			.assertEmpty
	}

	@Test
	def void testReplaceExistingSingleValuedEReferenceNonContainmentWithDefault() {
		// prepare
		val nonRoot = aet.NonRoot
		uniquePersistedRoot => [
			singleValuedContainmentEReference = nonRoot
			singleValuedNonContainmentEReference = nonRoot
		]

		// test
		val result = uniquePersistedRoot.record [
			singleValuedNonContainmentEReference = null
		]

		// assert
		result.assertChangeCount(1)
			.assertUnsetSingleValuedEReference(uniquePersistedRoot, ROOT__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE, nonRoot, false, false, false)
			.assertEmpty
	}
	
	@Test
	def void testUnsetExistingSingleValuedEReferenceContainment() {
		// prepare
		val nonRoot = aet.NonRoot
		uniquePersistedRoot => [
			singleValuedUnsettableContainmentEReference = nonRoot
		]

		// test
		val result = uniquePersistedRoot.record [
			eUnset(ROOT__SINGLE_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE)
		]

		// assert
		result.assertChangeCount(2)
			.assertReplaceAndDeleteNonRoot(nonRoot, uniquePersistedRoot, ROOT__SINGLE_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE, true)
			.assertEmpty
	}
	
	@Test
	def void testUnsetReplaceExistingSingleValuedEReferenceNonContainment() {
		// prepare
		val nonRoot = aet.NonRoot
		uniquePersistedRoot => [
			nonRootObjectContainerHelper = aet.NonRootObjectContainerHelper => [
				nonRootObjectsContainment += nonRoot
			]
			singleValuedUnsettableNonContainmentEReference = nonRoot
		]

		// test
		val result = uniquePersistedRoot.record [
			eUnset(ROOT__SINGLE_VALUED_UNSETTABLE_NON_CONTAINMENT_EREFERENCE)
		]

		// assert
		result.assertChangeCount(1)
			.assertReplaceSingleValuedEReference(uniquePersistedRoot, ROOT__SINGLE_VALUED_UNSETTABLE_NON_CONTAINMENT_EREFERENCE, nonRoot, null, false, false, true)
			.assertEmpty
	}

}
