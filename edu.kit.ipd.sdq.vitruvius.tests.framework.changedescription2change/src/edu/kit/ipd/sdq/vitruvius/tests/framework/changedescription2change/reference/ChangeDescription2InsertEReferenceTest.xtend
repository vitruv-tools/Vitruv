package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.reference

import allElementTypes.NonRoot
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.InsertEReference
import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.ChangeDescription2ChangeTransformationTest
import java.util.List
import org.eclipse.emf.ecore.EObject
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
		startRecording
		val nonRoot = createNonRootInInsertEFerence(false)
		val changes = getChanges()
		changes.assertInsertEReference(this.rootElement, MULTI_VALUED_CONTAINMENT_E_REFERENCE_NAME, nonRoot,
			expectedIndex, true, true)
	}

	def private testInsertInEReference(int expectedIndex) {
		// prepare --> add nonRoot to 
		val nonRoot = createNonRootInInsertEFerence(true);
		// test
		this.rootElement.multiValuedNonContainmentEReference.add(expectedIndex, nonRoot)
		// assert	
		val changes = getChanges()
		changes.assertInsertEReference(this.rootElement, MULTI_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME, nonRoot,
			expectedIndex, false, false)
	}

	def private createNonRootInInsertEFerence(boolean startRecording) {
		val feature = this.rootElement.getFeautreByName(MULTI_VALUED_CONTAINMENT_E_REFERENCE_NAME)
		feature.createAndAddNonRootToFeature(startRecording)
	}
}
