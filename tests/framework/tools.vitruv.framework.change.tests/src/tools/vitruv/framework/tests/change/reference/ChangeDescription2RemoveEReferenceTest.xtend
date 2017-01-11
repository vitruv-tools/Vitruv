package tools.vitruv.framework.tests.change.reference

import org.junit.Test

import static extension tools.vitruv.framework.tests.change.util.ChangeAssertHelper.*
import tools.vitruv.framework.change.echange.EChange
import org.junit.Ignore

class ChangeDescription2RemoveEReferenceTest extends ChangeDescription2EReferenceTest {


	def private void testRemoveEReference(boolean isContainment, boolean isExplicitUnset) {
		// prepare
		val nonRoot = createAndSetNonRootToRootSingleReference()
		// add element to right reference in order to be able to remove it later
		if (isContainment) {
			this.rootElement.multiValuedContainmentEReference.add(nonRoot)
		} else {
			this.rootElement.multiValuedNonContainmentEReference.add(nonRoot)
		}
		startRecording
		var EChange removeChange
		val featureName = if (isContainment) MULTI_VALUED_CONTAINMENT_E_REFERENCE_NAME else MULTI_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME
		// test
		if (isExplicitUnset) { 
			val feature = this.rootElement.getFeatureByName(featureName)
			this.rootElement.eUnset(feature)
			val unsetChange = claimChange(0).assertExplicitUnset()
			// TODO REvert
//			removeChange = unsetChange.subtractiveChanges?.get(0)
		} else {
			if (isContainment) {
				this.rootElement.multiValuedContainmentEReference.remove(nonRoot)
			} else {
				this.rootElement.multiValuedNonContainmentEReference.remove(nonRoot)
			}
			removeChange = claimChange(0)
		}
		// assert 
		if (isContainment) {
			removeChange.assertRemoveAndDeleteNonRoot(this.rootElement, MULTI_VALUED_CONTAINMENT_E_REFERENCE_NAME, nonRoot, 0)
		} else {
			removeChange.assertRemoveEReference(this.rootElement, MULTI_VALUED_NON_CONTAINMENT_E_REFERENCE_NAME, nonRoot, 0,
				isContainment)
		}
	}
	
	@Test
	def public void testRemoveNonContainmentEReferenceFromList() {
		val isContainment = false
		val isExplicitUnset = false
		testRemoveEReference(isContainment, isExplicitUnset)
	}
	
	@Ignore
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

	@Ignore
	@Test
	def public void testRemoveContainmentEReferenceFromListWithExplicitUnset() {
		val isContainment = true
		val isExplicitUnset = true
		testRemoveEReference(isContainment, isExplicitUnset)
	}
}
