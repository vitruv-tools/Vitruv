package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.reference

import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.ChangeDescription2ChangeTransformationTest
import org.junit.Test

import static extension edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeAssertHelper.*

class ChangeDescription2RemoveEReferenceTest extends ChangeDescription2ChangeTransformationTest {

	@Test
	def public void testRemoveNonContainmentEReferenceFromList() {
		val nonRoot = this.createAndAddNonRootToContainment(false)
		this.rootElement.multiValuedNonContainmentEReference.add(nonRoot)
		startRecording
		// insert nonRoot in non containment list
		this.rootElement.multiValuedNonContainmentEReference.clear

		// assert 
		val changes = getChanges()
		changes.assertSubtractiveChange(this.rootElement, MULTI_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME, nonRoot, 0,
			false, false)
	}
	
	def public void testRemoveNonContainmentEReferenceFromListWithExplicitUnset() {
		val nonRoot = this.createAndAddNonRootToContainment(false)
		this.rootElement.multiValuedNonContainmentEReference.add(nonRoot)
		startRecording

		this.rootElement.eUnset(this.rootElement.getFeautreByName(MULTI_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME))

		// assert 
		val changes = getChanges()
		val subtractiveChanges = changes.assertExplicitUnset()
		subtractiveChanges.assertSubtractiveChange( this.rootElement, MULTI_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME,
			nonRoot, 0, false, false)
	}
	

	@Test
	def public void testRemoveContainmentEReferenceFromList() {
		val nonRoot = this.createAndAddNonRootToFeature(
			this.rootElement.getFeautreByName(MULTI_VALUED_CONTAINMENT_E_REFERENCE_NAME), true)
		// insert nonRoot in non containment list
		this.rootElement.multiValuedContainmentEReference.clear

		// assert 
		val changes = getChanges()
		changes.assertSubtractiveChange(this.rootElement, MULTI_VALUED_CONTAINMENT_E_REFERENCE_NAME, nonRoot, 0, true,
			true)
	}

	@Test
	def public void testRemoveContainmentEReferenceFromListWithExplicitUnset() {
		val nonRoot = this.createAndAddNonRootToFeature(
			this.rootElement.getFeautreByName(MULTI_VALUED_CONTAINMENT_E_REFERENCE_NAME), true)

		this.rootElement.eUnset(this.rootElement.getFeautreByName(MULTI_VALUED_CONTAINMENT_E_REFERENCE_NAME))

		// assert 
		val changes = getChanges()
		val subtractiveChanges = changes.assertExplicitUnset()
		subtractiveChanges.assertReplaceSingleValuedEReference(nonRoot, null, MULTI_VALUED_CONTAINMENT_E_REFERENCE_NAME,
			this.rootElement, true, true, false)
	}

}
