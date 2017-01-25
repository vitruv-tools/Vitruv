package tools.vitruv.framework.tests.change.reference

import org.junit.Test

import static extension tools.vitruv.framework.tests.change.util.AtomicEChangeAssertHelper.*
import static extension tools.vitruv.framework.tests.change.util.CompoundEChangeAssertHelper.*
import tools.vitruv.framework.change.echange.EChange
import static allElementTypes.AllElementTypesPackage.Literals.*;

class ChangeDescription2RemoveEReferenceTest extends ChangeDescription2EReferenceTest {

	def private void testRemoveEReference(boolean isContainment) {
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
		// test
		if (isContainment) {
			this.rootElement.multiValuedContainmentEReference.remove(nonRoot)
		} else {
			this.rootElement.multiValuedNonContainmentEReference.remove(nonRoot)
		}
		
		// assert 
		changes.assertChangeCount(1);
		removeChange = changes.claimChange(0)
		if (isContainment) {
			removeChange.assertRemoveAndDeleteNonRoot(this.rootElement, ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE, nonRoot, 0)
		} else {
			removeChange.assertRemoveEReference(this.rootElement, ROOT__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE, nonRoot, 0,
				isContainment)
		}
	}
	
	@Test
	def public void testRemoveNonContainmentEReferenceFromList() {
		val isContainment = false
		testRemoveEReference(isContainment)
	}
	
	@Test
	def public void testRemoveContainmentEReferenceFromList() {
		val isContainment = true
		testRemoveEReference(isContainment)
	}

}
