package tools.vitruv.framework.tests.change.reference

import allElementTypes.NonRoot
import java.util.ArrayList
import java.util.List
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.tests.change.ChangeDescription2ChangeTransformationTest

import static allElementTypes.AllElementTypesPackage.Literals.*
import static org.junit.jupiter.api.Assertions.assertEquals
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.*

import static extension tools.vitruv.framework.tests.change.util.AtomicEChangeAssertHelper.*
import static extension tools.vitruv.framework.tests.change.util.CompoundEChangeAssertHelper.*

class ChangeDescription2InsertEReferenceTest extends ChangeDescription2ChangeTransformationTest {

	def private ArrayList<NonRoot> createNonRootElements(int count) {
		val ArrayList<NonRoot> nonRootElements = new ArrayList<NonRoot>()
		(0 ..< count).forEach[
			val element = aet.NonRoot();
			element.id = it.toString()
			nonRootElements.add(element)
		]
		return nonRootElements
	} 

	def private void assertCorrectInsertionNonContainment(List<EChange> actualChanges, Pair<NonRoot, Integer>... expectedInsertions) {
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

	def private void assertCorrectInsertionContainment(List<EChange> actualChanges, Pair<NonRoot, Integer>... expectedInsertions) {
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

	/** Elements which are to be inserted as NonContainment-EReferences have to be contained somewhere
	 *  so they are containment-added to the unique persisted root to then be available for non containment insertion.
	 */
	def private void nonContainmentHelperAddAllAsContainment(Iterable<NonRoot> nonRootElementsToBeContained) {
		uniquePersistedRoot.multiValuedContainmentEReference.addAll(nonRootElementsToBeContained)
	}

	@ParameterizedTest
	@ValueSource(ints = #[2,3,5,10])
	def void testInsertMultipleAtOnceContainment(int count) {
		val Iterable<NonRoot> nonRootElements = this.createNonRootElements(5)
		val List<EChange> result = uniquePersistedRoot.record [
			multiValuedContainmentEReference.addAll(nonRootElements)
		]
		val expectedInsertions = this.zipNonRootsWithExpectedInsertionIndeces(nonRootElements, #[0,1,2,3,4])
		assertCorrectInsertionNonContainment(result, expectedInsertions)
	}

	@ParameterizedTest
	@ValueSource(ints = #[2,3,5,10])
	def void testInsertMultipleAtOnceNonContainment(int count) {
		val Iterable<NonRoot> nonRootElements = this.createNonRootElements(5)
		nonContainmentHelperAddAllAsContainment(nonRootElements)
		val List<EChange> result = uniquePersistedRoot.record [
			multiValuedNonContainmentEReference.addAll(nonRootElements)
		]
		val expectedInsertions = this.zipNonRootsWithExpectedInsertionIndeces(nonRootElements, #[0,1,2,3,4])
		assertCorrectInsertionNonContainment(result, expectedInsertions)
	}

	def private ArrayList<Pair<NonRoot, Integer>> zipNonRootsWithExpectedInsertionIndeces(Iterable<NonRoot> nonRoots, List<Integer> integers) {
		val ArrayList<Pair<NonRoot, Integer>> expectedInsertions = new ArrayList<Pair<NonRoot, Integer>>()
		integers.forEach[ value, index |
			expectedInsertions.add(new Pair(nonRoots.get(index), value))
		]
		return expectedInsertions
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
