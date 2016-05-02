package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.reference

import org.junit.Test

import static extension edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeAssertHelper.*
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange

class ChangeDescription2RemoveEReferenceTest extends ChangeDescription2EReferenceTest {


	def private void testRemoveEReference(boolean isContainment, boolean isExplicitUnset) {
		// prepare
		val nonRoot = createAndSetNonRootToRootSingleReference()
		if (!isContainment) {
			// add element to non-containment reference before removing it
			this.rootElement.multiValuedNonContainmentEReference.add(nonRoot)
		}
		startRecording
		var EChange removeChange
		// test
		if (isExplicitUnset) {
			if (isContainment) {
				this.rootElement.multiValuedNonContainmentEReference.remove(nonRoot)
			} else {
				this.rootElement.multiValuedContainmentEReference.remove(nonRoot)
			}
			removeChange = claimChange(0)
		} else {
			val featureName = if (isContainment) MULTI_VALUED_CONTAINMENT_E_REFERENCE_NAME else MULTI_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME 
			val feature = this.rootElement.getFeautreByName(featureName)
			this.rootElement.eUnset(feature)
			val unsetChange = claimChange(0).assertExplicitUnset()
			removeChange = unsetChange.subtractiveChanges?.get(0)
		}
		// assert 
		val isDelete = isContainment
		removeChange.assertRemoveEReference(this.rootElement, MULTI_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME, nonRoot, 0,
			isContainment, isDelete)
	}
	
	@Test
	def public void testRemoveNonContainmentEReferenceFromList() {
		val isContainment = false
		val isExplicitUnset = false
		testRemoveEReference(isContainment, isExplicitUnset)
	}
	
	@Test
	def public void testRemoveNonContainmentEReferenceFromListWithExplicitUnset() {
		val isContainment = false
		val isExplicitUnset = true
		testRemoveEReference(isContainment, isExplicitUnset)
	}
	
	@Test
	def public void testRemoveContainmentEReferenceFromList() {
		val isContainment = true
		val isExplicitUnset = false
		testRemoveEReference(isContainment, isExplicitUnset)
	}

	@Test
	def public void testRemoveContainmentEReferenceFromListWithExplicitUnset() {
		val isContainment = true
		val isExplicitUnset = true
		testRemoveEReference(isContainment, isExplicitUnset)
	}
}
