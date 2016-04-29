package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.reference

import allElementTypes.AllElementTypesFactory
import allElementTypes.NonRoot
import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.ChangeDescription2ChangeTransformationTest
import org.junit.Test

import static extension edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeAssertHelper.*

class ChangeDescription2InsertEReferenceTest extends ChangeDescription2ChangeTransformationTest {

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
		val nonRoot = createAndAddNonRootToRootMultiReference()
		// assert
		val isContainment = true
		val isCreate = true
		claimChange(0).assertInsertEReference(this.rootElement, MULTI_VALUED_CONTAINMENT_E_REFERENCE_NAME, nonRoot,
			expectedIndex, isContainment, isCreate)
	}

	def private testInsertInEReference(int expectedIndex) {
		// prepare 
		val nonRoot = createAndAddNonRootToRootMultiReference()
		startRecording
		// test
		this.rootElement.multiValuedNonContainmentEReference.add(expectedIndex, nonRoot)
		// assert
		val isContainment = false
		val isCreate = false
		claimChange(0).assertInsertEReference(this.rootElement, MULTI_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME, nonRoot,
			expectedIndex, isContainment, isCreate)
	}
	
	def private NonRoot createAndAddNonRootToRootMultiReference() {
		val nonRoot = AllElementTypesFactory.eINSTANCE.createNonRoot
		this.rootElement.multiValuedContainmentEReference.add(nonRoot)
		return nonRoot
	}
}
