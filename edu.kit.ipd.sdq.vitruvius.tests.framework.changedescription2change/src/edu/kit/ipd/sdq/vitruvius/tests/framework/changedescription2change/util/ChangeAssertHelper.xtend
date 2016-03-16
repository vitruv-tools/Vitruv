package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEChange
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.junit.Assert

import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge.*
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.EFeatureChange

class ChangeAssertHelper {

	private new() {
	}

	public static def assertSingleChangeWithType(List<?> changes, Class<?> type) {
		changes.claimOne
		assertObjectInstanceOf(changes.get(0), type)
	}

	public static def assertObjectInstanceOf(Object object, Class<?> type) {
		Assert.assertTrue("The object " + object.class.simpleName + " should be type of " + type.simpleName,
			type.isInstance(object))
	}

	public static def assertOldAndNewValue(EChange eChange, Object oldValue, Object newValue) {
		eChange.assertOldValue(oldValue)
		eChange.assertNewValue(newValue)
	}
	
	public static def assertOldValue(EChange eChange, Object oldValue){
		Assert.assertEquals("old value must be the same than the given old value", oldValue,
			(eChange as SubtractiveEChange<?>).oldValue)
	}
	
	public static def assertNewValue(EChange eChange, Object newValue){
		Assert.assertEquals("new value must be the same than the given new value", newValue,
			(eChange as AdditiveEChange<?>).newValue)
	}

	public static def void assertAffectedEObject(EChange eChange, EObject expectedAffectedEObject) {
		Assert.assertEquals("The actual affected EObject is a different one than the expected affected EObject",
			(eChange as EFeatureChange<?, ?>).affectedEObject, expectedAffectedEObject)
	}
	
	public static def assertAffectedEFeature(EChange eChange, EStructuralFeature expectedEFeature){
		Assert.assertEquals("The actual affected EStructuralFeature is a different one than the expected EStructuralFeature",
			(eChange as EFeatureChange<?, ?>).affectedFeature, expectedEFeature)
	} 

}
