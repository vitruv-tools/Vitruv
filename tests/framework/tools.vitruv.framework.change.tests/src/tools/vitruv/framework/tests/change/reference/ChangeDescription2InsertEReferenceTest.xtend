package tools.vitruv.framework.tests.change.reference

import allElementTypes.NonRoot
import java.util.ArrayList
import java.util.List
import java.util.stream.Stream
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.tests.change.ChangeDescription2ChangeTransformationTest

import static allElementTypes.AllElementTypesPackage.Literals.*
import static org.junit.jupiter.api.Assertions.assertEquals
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.*

import static extension tools.vitruv.framework.tests.change.util.AtomicEChangeAssertHelper.*
import static extension tools.vitruv.framework.tests.change.util.CompoundEChangeAssertHelper.*

class ChangeDescription2InsertEReferenceTest extends ChangeDescription2ChangeTransformationTest { 

	def private void assertCorrectInsertionNonContainment(List<EChange> actualChanges, List<Pair<NonRoot, Integer>> expectedInsertions) {
		assertEquals(actualChanges.size, expectedInsertions.size)
		var Iterable<? extends EChange> stepwiseConsumedResult = actualChanges
		for (insertion : expectedInsertions) {
			val nonRoot = insertion.key
			val insertAtIndex = insertion.value
			stepwiseConsumedResult = stepwiseConsumedResult
				.assertInsertEReference(uniquePersistedRoot, ROOT__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE, nonRoot, insertAtIndex, false, false)
		}
		stepwiseConsumedResult.assertChangeCount(0)
	}

	def private void assertCorrectInsertionContainment(List<EChange> actualChanges, List<Pair<NonRoot, Integer>> expectedInsertions) {
		assertEquals(actualChanges.size, expectedInsertions.size*3)
		var Iterable<? extends EChange> stepwiseConsumedResult = actualChanges
		for (insertion : expectedInsertions) {
			val nonRoot = insertion.key
			val insertAtIndex = insertion.value
			stepwiseConsumedResult = stepwiseConsumedResult
				.assertCreateAndInsertNonRoot(uniquePersistedRoot, ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE, nonRoot, insertAtIndex, false)
				.assertReplaceSingleValuedEAttribute(nonRoot, IDENTIFIED__ID, null, nonRoot.id, false, false)
		}
		stepwiseConsumedResult.assertChangeCount(0)
	}

	@ParameterizedTest
	@MethodSource("provideParametersInsertMultipleAtOnceAtIndex")
	def void testInsertMultipleAtOnceContainment(int count, int insertAt) {
		//prepare
		val ids = #['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j']
		val List<NonRoot> preparationElements = ids.map[ 
			val element = aet.NonRoot()
			element.id = it.toString()
			return element
		].toList()
		uniquePersistedRoot.record [ multiValuedContainmentEReference.addAll(preparationElements) ]

	    //test
	    val List<NonRoot> nonRootElements = (0 ..< count).map[
			val element = aet.NonRoot()
			element.id = it.toString()
			return element
		].toList()
		val List<EChange> result = uniquePersistedRoot.record [
			multiValuedContainmentEReference.addAll(insertAt, nonRootElements)
		]

		//assert
		val expectedInsertions = nonRootElements.indexed().map[new Pair(value, key + insertAt)].toList
		assertCorrectInsertionContainment(result, expectedInsertions)
	}

	@ParameterizedTest
	@MethodSource("provideParametersInsertMultipleAtOnceAtIndex")
	def void testInsertMultipleAtOnceNonContainment(int count, int insertAt) {
		//prepare
		val List<NonRoot> preparationElements = (0 ..< 10).map[ aet.NonRoot() ].toList()
		uniquePersistedRoot.multiValuedContainmentEReference.addAll(preparationElements)
		uniquePersistedRoot.record [ multiValuedNonContainmentEReference.addAll(preparationElements) ]

		//test
		val List<NonRoot> nonRootElements = (0 ..< count).map[ aet.NonRoot() ].toList()
		uniquePersistedRoot.multiValuedContainmentEReference.addAll(nonRootElements)
		val List<EChange> result = uniquePersistedRoot.record [
			multiValuedNonContainmentEReference.addAll(insertAt, nonRootElements)
		]

		//assert
		val expectedInsertions = nonRootElements.indexed().map[new Pair(value, key + insertAt)].toList
		assertCorrectInsertionNonContainment(result, expectedInsertions)
	}

	def private static Stream<Arguments> provideParametersInsertMultipleAtOnceAtIndex() {
	    return Stream.of(
	            Arguments.of(2, 0),
	            Arguments.of(3, 0),
	            Arguments.of(5, 0),
	            Arguments.of(10, 0),
	            Arguments.of(2, 5),
	            Arguments.of(3, 5),
	            Arguments.of(5, 5),
	            Arguments.of(10, 5),
	            Arguments.of(2, 10),
	            Arguments.of(3, 10),
	            Arguments.of(5, 10),
	            Arguments.of(10, 10)
	    );
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
