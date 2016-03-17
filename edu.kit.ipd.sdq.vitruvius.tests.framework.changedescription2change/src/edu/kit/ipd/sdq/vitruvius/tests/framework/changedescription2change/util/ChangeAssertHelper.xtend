package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEReferenceChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEReferenceChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.EFeatureChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.PermuteEAttributeValues
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.InsertInEList
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.PermuteEList
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.ReplaceSingleValuedEReference
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.UpdateEReference
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.junit.Assert

import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge.*

class ChangeAssertHelper {

	private new() {
	}

	public static def <T> assertSingleChangeWithType(List<?> changes, Class<T> type) {
		changes.claimOne
		return assertObjectInstanceOf(changes.get(0), type)
	}

	public static def <T> assertObjectInstanceOf(Object object, Class<T> type) {
		Assert.assertTrue("The object " + object.class.simpleName + " should be type of " + type.simpleName,
			type.isInstance(object))
		return type.cast(object)
	}

	public static def assertOldAndNewValue(EChange eChange, Object oldValue, Object newValue) {
		eChange.assertOldValue(oldValue)
		eChange.assertNewValue(newValue)
	}

	public static def assertOldValue(EChange eChange, Object oldValue) {
		Assert.assertEquals("old value must be the same than the given old value", oldValue,
			(eChange as SubtractiveEChange<?>).oldValue)
	}

	public static def assertNewValue(EChange eChange, Object newValue) {
		Assert.assertEquals("new value must be the same than the given new value", newValue,
			(eChange as AdditiveEChange<?>).newValue)
	}

	public static def void assertAffectedEObject(EChange eChange, EObject expectedAffectedEObject) {
		Assert.assertEquals("The actual affected EObject is a different one than the expected affected EObject",
			(eChange as EFeatureChange<?, ?>).affectedEObject, expectedAffectedEObject)
	}

	public static def assertAffectedEFeature(EChange eChange, EStructuralFeature expectedEFeature) {
		Assert.assertEquals(
			"The actual affected EStructuralFeature is a different one than the expected EStructuralFeature",
			(eChange as EFeatureChange<?, ?>).affectedFeature, expectedEFeature)
	}

	public static def getFeautreByName(EObject eObject, String name) {
		eObject.eClass.getEStructuralFeature(name)
	}

	public static def assertIndices(PermuteEList permuteChange, List<Integer> expectedIndices) {
		Assert.assertEquals("The new indices for elements at old indices is wrong",
			permuteChange.newIndicesForElementsAtOldIndices, expectedIndices)
	}

	def public static void assertPermuteAttributeTest(List<?> changes, EObject rootElement,
		List<Integer> expectedIndicesForElementsAtOldIndices) {
		changes.assertSingleChangeWithType(PermuteEAttributeValues)
		val permuteEAttributeValues = changes.get(0) as PermuteEAttributeValues<?>
		permuteEAttributeValues.assertAffectedEObject(rootElement)
		permuteEAttributeValues.assertAffectedEFeature(rootElement.getFeautreByName("multiValuedEAttribute"))
		permuteEAttributeValues.assertIndices(expectedIndicesForElementsAtOldIndices)
	}

	def static void assertContainment(UpdateEReference<?> updateEReference, boolean expectedValue) {
		Assert.assertEquals("The containment information of the change " + updateEReference + " is wrong",
			updateEReference.isContainment, expectedValue)
	}

	def static void assertIsDelete(SubtractiveEReferenceChange<?> subtractiveReference, boolean expectedValue) {
		Assert.assertEquals("Change " + subtractiveReference + " shall not be a delete change",
			subtractiveReference.isIsDelete, expectedValue)
	}

	def static void assertIsCreate(AdditiveEReferenceChange<?> additiveReference, boolean expectedValue) {
		Assert.assertEquals("Change " + additiveReference + " shall not be a create change",
			additiveReference.isIsCreate, expectedValue)
	}
	
	def static void assertReplaceSingleValuedEReference(List<?> changes, Object expectedOldValue,
		Object expectedNewValue, String affectedEFeatureName, EObject affectedEObject, boolean isContainment) {
		val change = changes.assertSingleChangeWithType(ReplaceSingleValuedEReference)
		change.assertOldAndNewValue(expectedOldValue, expectedNewValue)
		change.assertAffectedEFeature(affectedEObject.getFeautreByName(affectedEFeatureName))
		change.assertAffectedEObject(affectedEObject)
		change.assertContainment(isContainment)
		change.assertIsCreate(isContainment)
		change.assertIsDelete(isContainment)
	}
	
	def static void assertIndex(InsertInEList change, int expectedIndex){
		Assert.assertEquals("The value is not at the correct index", change.index, expectedIndex)
	}
	

}
