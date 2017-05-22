package tools.vitruv.framework.tests.change.reference

import org.junit.Test

import static extension tools.vitruv.framework.tests.change.util.AtomicEChangeAssertHelper.*
import static extension tools.vitruv.framework.tests.change.util.CompoundEChangeAssertHelper.*
import tools.vitruv.framework.change.echange.EChange
import static allElementTypes.AllElementTypesPackage.Literals.*;
import allElementTypes.NonRoot
import java.util.List

class ChangeDescription2RemoveEReferenceTest extends ChangeDescription2EReferenceTest {

	def private void testRemoveEReference(boolean isContainment, boolean isExplicitUnset) {
		// prepare
		val nonRoot = createAndSetNonRootToRootSingleReference()
		val feature =
			if (isExplicitUnset) {
				if (isContainment) {
					ROOT__MULTI_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE
				} else {
					ROOT__MULTI_VALUED_UNSETTABLE_NON_CONTAINMENT_EREFERENCE
				}
			} else {
				if (isContainment) {
					ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE
				} else {
					ROOT__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE
				}
			} 
		
		// add element to right reference in order to be able to remove it later
		(this.rootElement.eGet(feature) as List<NonRoot>).add(nonRoot);
	
	
		startRecording
		var EChange removeChange
		// test
		this.rootElement.eUnset(feature);
		
		changes.assertChangeCount(1);
		if (isExplicitUnset) {
			val unsetChange = changes.claimChange(0).assertExplicitUnsetEReference()
			unsetChange.changes.assertChangeCount(1);
			unsetChange.atomicChanges.assertChangeCount(if (isContainment) 2 else 1);
			removeChange = unsetChange.changes?.get(0)
		} else {
			removeChange = changes.claimChange(0)	
		}
		
		// assert 
		if (isContainment) {
			removeChange.assertRemoveAndDeleteNonRoot(this.rootElement, feature, nonRoot, 0)
		} else {
			removeChange.assertRemoveEReference(this.rootElement, feature, nonRoot, 0,
				isContainment)
		}
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

	@Test
	def public void testClearEReferences() {
		val index0Element = createAndAddNonRootToRootMultiReference(0)
		val index1Element = createAndAddNonRootToRootMultiReference(1)
		// test
		startRecording

		// set to default/clear
		this.rootElement.multiValuedContainmentEReference.clear

		changes.assertChangeCount(2);
		changes.claimChange(0).assertRemoveAndDeleteNonRoot(this.rootElement, ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE, index1Element, 1)
		changes.claimChange(1).assertRemoveAndDeleteNonRoot(this.rootElement, ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE, index0Element, 0)
	}
}
