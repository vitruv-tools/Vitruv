package tools.vitruv.framework.tests.change.reference

import java.util.stream.Stream
import static java.util.stream.StreamSupport.stream
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.tests.change.ChangeDescription2ChangeTransformationTest

import static allElementTypes.AllElementTypesPackage.Literals.*
import static org.junit.jupiter.api.Assertions.assertEquals
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.*

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.mapFixed
import static extension tools.vitruv.framework.tests.change.util.AtomicEChangeAssertHelper.*
import static extension tools.vitruv.framework.tests.change.util.CompoundEChangeAssertHelper.*

class ChangeDescription2InsertEReferenceTest extends ChangeDescription2ChangeTransformationTest { 

	@ParameterizedTest
	@MethodSource("provideParametersInsertMultipleAtOnceAtIndex")
	def void testInsertMultipleAtOnceContainment(int count, int insertAt) {
		// prepare
		val preparationElements = (0 ..< 10).map[aet.NonRoot()]
		uniquePersistedRoot.record[multiValuedContainmentEReference.addAll(preparationElements)]

		// test
		val nonRootElements = (0 ..< count).mapFixed [
			val element = aet.NonRoot()
			element.id = it.toString()
			return element
		]
		var Iterable<? extends EChange> actualChanges = uniquePersistedRoot.record [
			multiValuedContainmentEReference.addAll(insertAt, nonRootElements)
		]

		// assert
		val expectedInsertions = nonRootElements.indexed().mapFixed[new Pair(value, key + insertAt)]
		assertEquals(actualChanges.size, expectedInsertions.size * 3)
		for (insertion : expectedInsertions) {
			val nonRoot = insertion.key
			val insertAtIndex = insertion.value
			actualChanges = actualChanges //
				.assertCreateAndInsertNonRoot(uniquePersistedRoot, ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE, nonRoot, insertAtIndex, false) //
				.assertReplaceSingleValuedEAttribute(nonRoot, IDENTIFIED__ID, null, nonRoot.id, false, false)
		}
	}

	@ParameterizedTest
	@MethodSource("provideParametersInsertMultipleAtOnceAtIndex")
	def void testInsertMultipleAtOnceNonContainment(int count, int insertAt) {
		// prepare
		val preparationElements = (0 ..< 10).mapFixed[aet.NonRoot()]
		uniquePersistedRoot.multiValuedContainmentEReference.addAll(preparationElements)
		uniquePersistedRoot.record[multiValuedNonContainmentEReference.addAll(preparationElements)]

		// test
		val nonRootElements = (0 ..< count).mapFixed[aet.NonRoot()]
		uniquePersistedRoot.multiValuedContainmentEReference.addAll(nonRootElements)
		var Iterable<? extends EChange> actualChanges = uniquePersistedRoot.record [
			multiValuedNonContainmentEReference.addAll(insertAt, nonRootElements)
		]

		// assert
		val expectedInsertions = nonRootElements.indexed().map[new Pair(value, key + insertAt)]
		assertEquals(actualChanges.size, expectedInsertions.size)
		for (insertion : expectedInsertions) {
			val nonRoot = insertion.key
			val insertAtIndex = insertion.value
			actualChanges = actualChanges //
				.assertInsertEReference(uniquePersistedRoot, ROOT__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE, nonRoot, insertAtIndex, false, false)
		}
	}

	def static Stream<Arguments> provideParametersInsertMultipleAtOnceAtIndex() {
		val counts = #[2, 3, 5, 10]
		val insertionIndexes = #[0, 5, 10]
		return stream(insertionIndexes.map[index|counts.map[Arguments.of(it, index)]].flatten.spliterator, false)
	}

	@Test
	def void testInsertSingleNonContainment() {
		insertAndAssertSingleNonContainment(0)
	}

	@Test
	def void testInsertMultipleIterativelyNonContainment() {
		insertAndAssertSingleNonContainment(0)
		insertAndAssertSingleNonContainment(1)
		insertAndAssertSingleNonContainment(2)
		insertAndAssertSingleNonContainment(1)
	}

	@Test
	def void testInsertSingleContainment() {
		insertAndAssertSingleContainment(0)
	}

	@Test
	def void testInsertMultipleIterativelyContainment() {
		insertAndAssertSingleContainment(0)
		insertAndAssertSingleContainment(1)
		insertAndAssertSingleContainment(2)
		insertAndAssertSingleContainment(1)
	}

	def private void insertAndAssertSingleContainment(int expectedIndex) {
		// prepare
		uniquePersistedRoot

		// test
		val nonRoot = aet.NonRoot
		val result = uniquePersistedRoot.record [
			multiValuedContainmentEReference.add(expectedIndex, nonRoot)
		]

		// assert
		result.assertChangeCount(3)
			.assertCreateAndInsertNonRoot(uniquePersistedRoot, ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE, nonRoot, expectedIndex, false)
			.assertReplaceSingleValuedEAttribute(nonRoot, IDENTIFIED__ID, null, nonRoot.id, false, false)
			.assertEmpty
	}

	def private void insertAndAssertSingleNonContainment(int expectedIndex) {
		// prepare
		val nonRoot = aet.NonRoot
		uniquePersistedRoot => [
			multiValuedContainmentEReference.add(expectedIndex, nonRoot)
		]

		// test
		val result = uniquePersistedRoot.record [
			multiValuedNonContainmentEReference.add(expectedIndex, nonRoot)
		]

		// assert
		result.assertChangeCount(1)
			.assertInsertEReference(uniquePersistedRoot, ROOT__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE, nonRoot, expectedIndex, false, false)
			.assertEmpty
	}

}
