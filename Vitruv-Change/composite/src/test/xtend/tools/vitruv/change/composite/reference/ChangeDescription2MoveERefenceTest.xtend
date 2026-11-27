package tools.vitruv.change.composite.reference

import tools.vitruv.change.composite.ChangeDescription2ChangeTransformationTest
import org.junit.jupiter.api.Test
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.*
import static extension tools.vitruv.change.composite.util.AtomicEChangeAssertHelper.*
import static allElementTypes.AllElementTypesPackage.Literals.*


class ChangeDescription2MoveERefenceTest extends ChangeDescription2ChangeTransformationTest {
	
	@Test
	def void moveMultiValuedNonContainmentEReference() {
		// prepare
		val nonRoot1 = aet.NonRoot
		val nonRoot2 = aet.NonRoot
		val nonRoot3 = aet.NonRoot
		uniquePersistedRoot => [
			multiValuedContainmentEReference += nonRoot1
			multiValuedContainmentEReference += nonRoot2
			multiValuedContainmentEReference += nonRoot3
		]
		uniquePersistedRoot => [
			multiValuedNonContainmentEReference += nonRoot1
			multiValuedNonContainmentEReference += nonRoot2
			multiValuedNonContainmentEReference += nonRoot3
		]
		
		// test
		val result = uniquePersistedRoot.record [
			multiValuedNonContainmentEReference.move(0, 1)
		]
		
		// assert
		result.assertChangeCount(2)
			.assertRemoveEReference(uniquePersistedRoot, ROOT__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE, nonRoot2, 1, false)
			.assertInsertEReference(uniquePersistedRoot, ROOT__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE, nonRoot2, 0, false, false)
			.assertEmpty
	}
	
	@Test
	def void moveMultiValuedUnsettableNonContainmentEReference() {
		// prepare
		val nonRoot1 = aet.NonRoot
		val nonRoot2 = aet.NonRoot
		val nonRoot3 = aet.NonRoot
		uniquePersistedRoot => [
			multiValuedContainmentEReference += nonRoot1
			multiValuedContainmentEReference += nonRoot2
			multiValuedContainmentEReference += nonRoot3
		]
		uniquePersistedRoot => [
			multiValuedUnsettableNonContainmentEReference += nonRoot1
			multiValuedUnsettableNonContainmentEReference += nonRoot2
			multiValuedUnsettableNonContainmentEReference += nonRoot3
		]
		
		// test
		val result = uniquePersistedRoot.record [
			multiValuedUnsettableNonContainmentEReference.move(0, 1)
		]
		
		// assert
		result.assertChangeCount(2)
			.assertRemoveEReference(uniquePersistedRoot, ROOT__MULTI_VALUED_UNSETTABLE_NON_CONTAINMENT_EREFERENCE, nonRoot2, 1, false)
			.assertInsertEReference(uniquePersistedRoot, ROOT__MULTI_VALUED_UNSETTABLE_NON_CONTAINMENT_EREFERENCE, nonRoot2, 0, false, false)
			.assertEmpty
	}
	
	@Test
	def void moveMultiValuedUnorderedNonContainmentEReference() {
		// prepare
		val nonRoot1 = aet.NonRoot
		val nonRoot2 = aet.NonRoot
		val nonRoot3 = aet.NonRoot
		uniquePersistedRoot => [
			multiValuedContainmentEReference += nonRoot1
			multiValuedContainmentEReference += nonRoot2
			multiValuedContainmentEReference += nonRoot3
		]
		uniquePersistedRoot => [
			multiValuedUnorderedNonContainmentEReference += nonRoot1
			multiValuedUnorderedNonContainmentEReference += nonRoot2
			multiValuedUnorderedNonContainmentEReference += nonRoot3
		]
		
		// test
		val result = uniquePersistedRoot.record [
			multiValuedUnorderedNonContainmentEReference.move(0, 1)
		]
		
		// assert
		result.assertChangeCount(2)
			.assertRemoveEReference(uniquePersistedRoot, ROOT__MULTI_VALUED_UNORDERED_NON_CONTAINMENT_EREFERENCE, nonRoot2, 1, false)
			.assertInsertEReference(uniquePersistedRoot, ROOT__MULTI_VALUED_UNORDERED_NON_CONTAINMENT_EREFERENCE, nonRoot2, 0, false, false)
			.assertEmpty
	}
	
	@Test
	def void moveMultiValuedContainmentEReference() {
		// prepare
		val nonRoot1 = aet.NonRoot
		val nonRoot2 = aet.NonRoot
		val nonRoot3 = aet.NonRoot
		uniquePersistedRoot => [
			multiValuedContainmentEReference += nonRoot1
			multiValuedContainmentEReference += nonRoot2
			multiValuedContainmentEReference += nonRoot3
		]
		
		// test
		val result = uniquePersistedRoot.record [
			multiValuedContainmentEReference.move(0, 1)
		]
		
		// assert
		result.assertChangeCount(2)
			.assertRemoveEReference(uniquePersistedRoot, ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE, nonRoot2, 1, true)
			.assertInsertEReference(uniquePersistedRoot, ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE, nonRoot2, 0, true, false)
			.assertEmpty
	}
	
	@Test
	def void moveMultiValuedUnsettableContainmentEReference() {
		// prepare
		val nonRoot1 = aet.NonRoot
		val nonRoot2 = aet.NonRoot
		val nonRoot3 = aet.NonRoot
		uniquePersistedRoot => [
			multiValuedUnsettableContainmentEReference += nonRoot1
			multiValuedUnsettableContainmentEReference += nonRoot2
			multiValuedUnsettableContainmentEReference += nonRoot3
		]
		
		// test
		val result = uniquePersistedRoot.record [
			multiValuedUnsettableContainmentEReference.move(0, 1)
		]
		
		// assert
		result.assertChangeCount(2)
			.assertRemoveEReference(uniquePersistedRoot, ROOT__MULTI_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE, nonRoot2, 1, true)
			.assertInsertEReference(uniquePersistedRoot, ROOT__MULTI_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE, nonRoot2, 0, true, false)
			.assertEmpty
	}
	
	@Test
	def void moveMultiValuedUnorderedContainmentEReference() {
		// prepare
		val nonRoot1 = aet.NonRoot
		val nonRoot2 = aet.NonRoot
		val nonRoot3 = aet.NonRoot
		uniquePersistedRoot => [
			multiValuedUnorderedContainmentEReference += nonRoot1
			multiValuedUnorderedContainmentEReference += nonRoot2
			multiValuedUnorderedContainmentEReference += nonRoot3
		]
		
		// test
		val result = uniquePersistedRoot.record [
			multiValuedUnorderedContainmentEReference.move(0, 1)
		]
		
		// assert
		result.assertChangeCount(2)
			.assertRemoveEReference(uniquePersistedRoot, ROOT__MULTI_VALUED_UNORDERED_CONTAINMENT_EREFERENCE, nonRoot2, 1, true)
			.assertInsertEReference(uniquePersistedRoot, ROOT__MULTI_VALUED_UNORDERED_CONTAINMENT_EREFERENCE, nonRoot2, 0, true, false)
			.assertEmpty
	}
}