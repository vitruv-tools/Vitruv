package tools.vitruv.framework.tests.change.reference

import allElementTypes.NonRoot
import allElementTypes.Root
import java.util.List
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.tests.change.ChangeDescription2ChangeTransformationTest

import static allElementTypes.AllElementTypesPackage.Literals.*
import static org.junit.jupiter.api.Assertions.assertEquals
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.*

import static extension tools.vitruv.framework.tests.change.util.AtomicEChangeAssertHelper.*
import static extension tools.vitruv.framework.tests.change.util.CompoundEChangeAssertHelper.*

class ChangeDescription2InsertEReferenceTest extends ChangeDescription2ChangeTransformationTest {

	private static Root UPR;
	
	@BeforeEach
	def void prepareComplexTest() {
		UPR = getUniquePersistedRoot()
	}
	
	private def Iterable<NonRoot> createNonRootElements(int count) {
		return (0 ..< count).map [
			val element = aet.NonRoot();
			element.id = it.toString()
			return element
		]
	} 

	def void assertFiveNonRootsNonContainment(List<EChange> actualResult, Pair<NonRoot, Integer>... expectedInsertions) {
		assertEquals(actualResult.size, expectedInsertions.size)
		var Iterable<? extends EChange> stepwiseConsumedResult = actualResult
		for (var int i = 0; i < expectedInsertions.size; i++) {
			val Pair<NonRoot, Integer> insertion = expectedInsertions.get(i)
			val nonRoot = insertion.key
			val insertAtIndex = insertion.value
			stepwiseConsumedResult = stepwiseConsumedResult
				.assertChangeCount(expectedInsertions.size-i)
				.assertInsertEReference(UPR, ROOT__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE, nonRoot, insertAtIndex, false, false)
		}
		stepwiseConsumedResult.assertChangeCount(0)
	}

	def void assertFiveNonRootsContainment(List<EChange> actualResult, Pair<NonRoot, Integer>... expectedInsertions) {
		assertEquals(actualResult.size, expectedInsertions.size*3)
		var Iterable<? extends EChange> stepwiseConsumedResult = actualResult
		for (var int i = 0; i < expectedInsertions.size; i++) {
			val Pair<NonRoot, Integer> insertion = expectedInsertions.get(i)
			val nonRoot = insertion.key
			val insertAtIndex = insertion.value
			stepwiseConsumedResult = stepwiseConsumedResult
				.assertChangeCount((expectedInsertions.size-i)*3)
				.assertCreateAndInsertNonRoot(UPR, ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE, nonRoot, insertAtIndex, false)
				.assertReplaceSingleValuedEAttribute(nonRoot, IDENTIFIED__ID, null, nonRoot.id, false, false)
		}
		stepwiseConsumedResult.assertChangeCount(0)
	}
	
	/** Elements which are to be inserted as NonContainment-EReferences have to be contained somewhere
	 *  so they are containment-added to the unique persisted root to then be available for non containment insertion.
	 */
	def void nonContainmentHelperAddAllAsContainment(Iterable<NonRoot> nonRootElementsToBeContained) {
		UPR => [ multiValuedContainmentEReference.addAll(nonRootElementsToBeContained) ]
	}
	
	@Test
	def void testInsertMultipleAtOnceContainment() {		
		val Iterable<NonRoot> nonRootElements = this.createNonRootElements(5)
		val List<EChange> result = UPR.record [
			multiValuedContainmentEReference.addAll(nonRootElements)
		]
		assertFiveNonRootsContainment(result, 
			new Pair(nonRootElements.get(0), 0), 
			new Pair(nonRootElements.get(1), 1), 
			new Pair(nonRootElements.get(2), 2),
			new Pair(nonRootElements.get(3), 3), 
			new Pair(nonRootElements.get(4), 4)
		)
	}
	
	@Test
	def void testInsertMultipleAtOnceNonContainment() {
		val Iterable<NonRoot> nonRootElements = this.createNonRootElements(5)
		nonContainmentHelperAddAllAsContainment(nonRootElements)
		val List<EChange> result = UPR.record [
			multiValuedNonContainmentEReference.addAll(nonRootElements)
		]
		assertFiveNonRootsNonContainment(result, 
			new Pair(nonRootElements.get(0), 0), 
			new Pair(nonRootElements.get(1), 1), 
			new Pair(nonRootElements.get(2), 2),
			new Pair(nonRootElements.get(3), 3), 
			new Pair(nonRootElements.get(4), 4)
		)
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

	def private insertAndAssertSingleContainment(int expectedIndex) {
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

	def private insertAndAssertSingleNonContainment(int expectedIndex) {
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
