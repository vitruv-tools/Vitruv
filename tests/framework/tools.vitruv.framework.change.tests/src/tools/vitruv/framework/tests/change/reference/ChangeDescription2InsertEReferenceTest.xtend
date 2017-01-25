package tools.vitruv.framework.tests.change.reference

import allElementTypes.NonRoot
import org.junit.Test

import static extension tools.vitruv.framework.tests.change.util.AtomicEChangeAssertHelper.*
import static extension tools.vitruv.framework.tests.change.util.CompoundEChangeAssertHelper.*
import org.eclipse.emf.ecore.EStructuralFeature
import static allElementTypes.AllElementTypesPackage.Literals.*;

class ChangeDescription2InsertEReferenceTest extends ChangeDescription2EReferenceTest {

	@Test
	def public void testInsertEReferenceNonContainment() {
		testInsertInEReference(0)
	}

	@Test
	def public void testMultipleInsertEReferenceNonContainment() {
		testInsertInEReference(0)
		testInsertInEReference(1)
		testInsertInEReference(2)
		testInsertInEReference(1)
	}

	@Test
	def public void testInsertEReferenceContainment() {
		testInsertInContainmentEReference(0)
	}

	@Test
	def public void testMultipleInsertEReferenceContainment() {
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
		val nonRoot = createAndAddNonRootToRootMultiReference(expectedIndex)
		startRecording
		// test
		this.rootElement.multiValuedNonContainmentEReference.add(expectedIndex, nonRoot)
		// assert
		val isContainment = false
		assertInsertEReference(nonRoot, ROOT__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE, expectedIndex, isContainment)
	}
	
	def private void assertInsertEReference(NonRoot nonRoot, EStructuralFeature feature, int expectedIndex, boolean isContainment) {
		if (isContainment) {
			changes.assertChangeCount(2);
			changes.claimChange(1).assertReplaceSingleValuedEAttribute(nonRoot, IDENTIFIED__ID, null, nonRoot.id);
		} else {
			changes.assertChangeCount(1);
		}
		changes.claimChange(0).assertInsertEReference(this.rootElement, feature, nonRoot,
			expectedIndex, isContainment)
	}
	
	def private void assertCreateAndInsertNonRoot(NonRoot nonRoot, EStructuralFeature feature, int expectedIndex) {
		changes.assertChangeCount(2);
		changes.claimChange(0).assertCreateAndInsertNonRoot(this.rootElement, feature, nonRoot,
			expectedIndex)
		changes.claimChange(1).assertReplaceSingleValuedEAttribute(nonRoot, IDENTIFIED__ID, null, nonRoot.id);
	}
}
