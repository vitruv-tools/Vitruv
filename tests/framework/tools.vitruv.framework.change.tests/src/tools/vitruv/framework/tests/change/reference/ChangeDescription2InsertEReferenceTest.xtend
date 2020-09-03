package tools.vitruv.framework.tests.change.reference

import allElementTypes.NonRoot
import org.eclipse.emf.ecore.EStructuralFeature
import org.junit.Test

import static allElementTypes.AllElementTypesPackage.Literals.*

import static extension tools.vitruv.framework.tests.change.util.AtomicEChangeAssertHelper.*
import static extension tools.vitruv.framework.tests.change.util.CompoundEChangeAssertHelper.*
import tools.vitruv.framework.change.echange.EChange

class ChangeDescription2InsertEReferenceTest extends ChangeDescription2EReferenceTest {

	@Test
	def void testInsertEReferenceNonContainment() {
		testInsertInEReference(0)
	}

	@Test
	def void testMultipleInsertEReferenceNonContainment() {
		testInsertInEReference(0)
		testInsertInEReference(1)
		testInsertInEReference(2)
		testInsertInEReference(1)
	}

	@Test
	def void testInsertEReferenceContainment() {
		testInsertInContainmentEReference(0)
	}

	@Test
	def void testMultipleInsertEReferenceContainment() {
		testInsertInContainmentEReference(0)
		testInsertInContainmentEReference(1)
		testInsertInContainmentEReference(2)
		testInsertInContainmentEReference(1)
	}

	def private testInsertInContainmentEReference(int expectedIndex) {
		// prepare
		startRecording
		// test
		val nonRoot = createAndAddNonRootToRootMultiReference(expectedIndex)
		// assert
		assertCreateAndInsertNonRoot(nonRoot, ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE, expectedIndex)
	}

	def private testInsertInEReference(int expectedIndex) {
		// prepare 
		startRecording
		val nonRoot = createAndAddNonRootToRootMultiReference(expectedIndex)
		startRecording
		// test
		this.rootElement.multiValuedNonContainmentEReference.add(expectedIndex, nonRoot)
		// assert
		val isContainment = false
		assertInsertEReference(nonRoot, ROOT__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE, expectedIndex, isContainment)
	}
	
	def private void assertInsertEReference(NonRoot nonRoot, EStructuralFeature feature, int expectedIndex, boolean isContainment) {
		var Iterable<? extends EChange> changes = changes;
		if (isContainment) {
			changes.assertChangeCount(2);
			changes = changes.assertReplaceSingleValuedEAttribute(nonRoot, IDENTIFIED__ID, null, nonRoot.id, false, false);
		} else {
			changes.assertChangeCount(1);
		}
		changes.assertInsertEReference(this.rootElement, feature, nonRoot, expectedIndex, isContainment, false)
			.assertEmpty;
	}
	
	def private void assertCreateAndInsertNonRoot(NonRoot nonRoot, EStructuralFeature feature, int expectedIndex) {
		changes.assertChangeCount(3);
		changes.assertCreateAndInsertNonRoot(this.rootElement, feature, nonRoot, expectedIndex, false)
			.assertReplaceSingleValuedEAttribute(nonRoot, IDENTIFIED__ID, null, nonRoot.id, false, false)
			.assertEmpty;
	}
}
