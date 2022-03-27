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
	private static final NonRoot NR1 = aet.NonRoot();
	private static final NonRoot NR2 = aet.NonRoot();
	private static final NonRoot NR3 = aet.NonRoot();
	private static final NonRoot NR4 = aet.NonRoot();
	private static final NonRoot NR5 = aet.NonRoot();
	
	@BeforeEach
	def void prepareComplexTest() {
		UPR = getUniquePersistedRoot()
		NR1.id = 1.toString()
		NR2.id = 2.toString()
		NR3.id = 3.toString()
		NR4.id = 4.toString()
		NR5.id = 5.toString()
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
	def void nonContainmentHelperAddAllAsContainment() {
		UPR => [
			multiValuedContainmentEReference.add(0, NR1)
			multiValuedContainmentEReference.add(0, NR2)
			multiValuedContainmentEReference.add(0, NR3)
			multiValuedContainmentEReference.add(0, NR4)
			multiValuedContainmentEReference.add(0, NR5)
		]
	}
	
	@Test
	def void testMultipleAtOnceContainment() {
		val List<NonRoot> li = #[NR1, NR2, NR3, NR4, NR5]
		val List<EChange> result = UPR.record [
			multiValuedContainmentEReference.addAll(li)
		]
		assertFiveNonRootsContainment(result, new Pair(NR1, 0), new Pair(NR2, 1), new Pair(NR3, 2),
			new Pair(NR4, 3), new Pair(NR5, 4))
	}
	
	@Test
	def void testMultipleAtOnceNonContainment() {
		nonContainmentHelperAddAllAsContainment()
		val List<NonRoot> li = #[NR1, NR2, NR3, NR4, NR5]
		val List<EChange> result = UPR.record [
			multiValuedNonContainmentEReference.addAll(li)
		]
		assertFiveNonRootsNonContainment(result, new Pair(NR1, 0), new Pair(NR2, 1), new Pair(NR3, 2),
			new Pair(NR4, 3), new Pair(NR5, 4))
	}
	
	@Test
	def void testInsertSingleEReferenceNonContainment() {
		testInsertInNonContainmentEReference(0)
	}

	@Test
	def void testInsertMultipleEReferenceNonContainment() {
		testInsertInNonContainmentEReference(0)
		testInsertInNonContainmentEReference(1)
		testInsertInNonContainmentEReference(2)
		testInsertInNonContainmentEReference(1)
	}

	@Test
	def void testInsertSingleEReferenceContainment() {
		testInsertInContainmentEReference(0)
	}

	@Test
	def void testInsertMultipleEReferenceContainment() {
		testInsertInContainmentEReference(0)
		testInsertInContainmentEReference(1)
		testInsertInContainmentEReference(2)
		testInsertInContainmentEReference(1)
	}

	def private testInsertInContainmentEReference(int expectedIndex) {
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

	def private testInsertInNonContainmentEReference(int expectedIndex) {
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
