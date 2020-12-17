package tools.vitruv.framework.tests.change.reference

import static extension tools.vitruv.framework.tests.change.util.AtomicEChangeAssertHelper.*
import static extension tools.vitruv.framework.tests.change.util.CompoundEChangeAssertHelper.*
import static allElementTypes.AllElementTypesPackage.Literals.*;
import allElementTypes.NonRoot
import java.util.List
import org.junit.jupiter.api.Test

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
		// test
		this.rootElement.eUnset(feature);
		
		// assert 
		var remaining = if (isContainment) {
			changes.assertRemoveAndDeleteNonRoot(this.rootElement, feature, nonRoot, 0)
		} else {
			changes.assertRemoveEReference(this.rootElement, feature, nonRoot, 0, isContainment)
		}
		if (isExplicitUnset) {
			remaining = remaining.assertUnsetFeature(this.rootElement, feature);
		}
		remaining.assertEmpty;
	}
	
	@Test
	def void testRemoveNonContainmentEReferenceFromList() {
		val isContainment = false
		val isExplicitUnset = false
		testRemoveEReference(isContainment, isExplicitUnset)
	}
	
	@Test
	def void testRemoveNonContainmentEReferenceFromListWithExplicitUnset() {
		val isContainment = false
		val isExplicitUnset = true
		testRemoveEReference(isContainment, isExplicitUnset)
	}
	
	@Test
	def void testRemoveContainmentEReferenceFromList() {
		val isContainment = true
		val isExplicitUnset = false
		testRemoveEReference(isContainment, isExplicitUnset)
	}

	@Test
	def void testRemoveContainmentEReferenceFromListWithExplicitUnset() {
		val isContainment = true
		val isExplicitUnset = true
		testRemoveEReference(isContainment, isExplicitUnset)
	}

	@Test
	def void testClearEReferences() {
		val index0Element = createAndAddNonRootToRootMultiReference(0)
		val index1Element = createAndAddNonRootToRootMultiReference(1)
		// test
		startRecording

		// set to default/clear
		this.rootElement.multiValuedContainmentEReference.clear

		changes.assertChangeCount(4);
		changes.assertRemoveAndDeleteNonRoot(this.rootElement, ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE, index1Element, 1)
			.assertRemoveAndDeleteNonRoot(this.rootElement, ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE, index0Element, 0)
			.assertEmpty;
	}
}
