/**
 */
package tools.vitruv.framework.change.echange.feature.attribute.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.util.Switch;

import tools.vitruv.framework.change.echange.AdditiveEChange;
import tools.vitruv.framework.change.echange.AtomicEChange;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.SubtractiveEChange;

import tools.vitruv.framework.change.echange.feature.FeatureEChange;
import tools.vitruv.framework.change.echange.feature.UpdateMultiValuedFeatureEChange;
import tools.vitruv.framework.change.echange.feature.UpdateSingleValuedFeatureEChange;

import tools.vitruv.framework.change.echange.feature.attribute.*;

import tools.vitruv.framework.change.echange.feature.list.InsertInListEChange;
import tools.vitruv.framework.change.echange.feature.list.RemoveFromListEChange;
import tools.vitruv.framework.change.echange.feature.list.UpdateSingleListEntryEChange;

import tools.vitruv.framework.change.echange.feature.single.ReplaceSingleValuedFeatureEChange;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see tools.vitruv.framework.change.echange.feature.attribute.AttributePackage
 * @generated
 */
public class AttributeSwitch<T1> extends Switch<T1> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static AttributePackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeSwitch() {
		if (modelPackage == null) {
			modelPackage = AttributePackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T1 doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case AttributePackage.UPDATE_ATTRIBUTE_ECHANGE: {
				UpdateAttributeEChange<?> updateAttributeEChange = (UpdateAttributeEChange<?>)theEObject;
				T1 result = caseUpdateAttributeEChange(updateAttributeEChange);
				if (result == null) result = caseFeatureEChange(updateAttributeEChange);
				if (result == null) result = caseAtomicEChange(updateAttributeEChange);
				if (result == null) result = caseEChange(updateAttributeEChange);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AttributePackage.ADDITIVE_ATTRIBUTE_ECHANGE: {
				AdditiveAttributeEChange<?, ?> additiveAttributeEChange = (AdditiveAttributeEChange<?, ?>)theEObject;
				T1 result = caseAdditiveAttributeEChange(additiveAttributeEChange);
				if (result == null) result = caseAdditiveEChange(additiveAttributeEChange);
				if (result == null) result = caseUpdateAttributeEChange(additiveAttributeEChange);
				if (result == null) result = caseFeatureEChange(additiveAttributeEChange);
				if (result == null) result = caseAtomicEChange(additiveAttributeEChange);
				if (result == null) result = caseEChange(additiveAttributeEChange);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AttributePackage.SUBTRACTIVE_ATTRIBUTE_ECHANGE: {
				SubtractiveAttributeEChange<?, ?> subtractiveAttributeEChange = (SubtractiveAttributeEChange<?, ?>)theEObject;
				T1 result = caseSubtractiveAttributeEChange(subtractiveAttributeEChange);
				if (result == null) result = caseSubtractiveEChange(subtractiveAttributeEChange);
				if (result == null) result = caseUpdateAttributeEChange(subtractiveAttributeEChange);
				if (result == null) result = caseFeatureEChange(subtractiveAttributeEChange);
				if (result == null) result = caseAtomicEChange(subtractiveAttributeEChange);
				if (result == null) result = caseEChange(subtractiveAttributeEChange);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AttributePackage.INSERT_EATTRIBUTE_VALUE: {
				InsertEAttributeValue<?, ?> insertEAttributeValue = (InsertEAttributeValue<?, ?>)theEObject;
				T1 result = caseInsertEAttributeValue(insertEAttributeValue);
				if (result == null) result = caseInsertInListEChange(insertEAttributeValue);
				if (result == null) result = caseAdditiveAttributeEChange(insertEAttributeValue);
				if (result == null) result = caseUpdateSingleListEntryEChange(insertEAttributeValue);
				if (result == null) result = caseAdditiveEChange(insertEAttributeValue);
				if (result == null) result = caseUpdateAttributeEChange(insertEAttributeValue);
				if (result == null) result = caseUpdateMultiValuedFeatureEChange(insertEAttributeValue);
				if (result == null) result = caseFeatureEChange(insertEAttributeValue);
				if (result == null) result = caseAtomicEChange(insertEAttributeValue);
				if (result == null) result = caseEChange(insertEAttributeValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AttributePackage.REMOVE_EATTRIBUTE_VALUE: {
				RemoveEAttributeValue<?, ?> removeEAttributeValue = (RemoveEAttributeValue<?, ?>)theEObject;
				T1 result = caseRemoveEAttributeValue(removeEAttributeValue);
				if (result == null) result = caseRemoveFromListEChange(removeEAttributeValue);
				if (result == null) result = caseSubtractiveAttributeEChange(removeEAttributeValue);
				if (result == null) result = caseUpdateSingleListEntryEChange(removeEAttributeValue);
				if (result == null) result = caseSubtractiveEChange(removeEAttributeValue);
				if (result == null) result = caseUpdateAttributeEChange(removeEAttributeValue);
				if (result == null) result = caseUpdateMultiValuedFeatureEChange(removeEAttributeValue);
				if (result == null) result = caseFeatureEChange(removeEAttributeValue);
				if (result == null) result = caseAtomicEChange(removeEAttributeValue);
				if (result == null) result = caseEChange(removeEAttributeValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AttributePackage.REPLACE_SINGLE_VALUED_EATTRIBUTE: {
				ReplaceSingleValuedEAttribute<?, ?> replaceSingleValuedEAttribute = (ReplaceSingleValuedEAttribute<?, ?>)theEObject;
				T1 result = caseReplaceSingleValuedEAttribute(replaceSingleValuedEAttribute);
				if (result == null) result = caseAdditiveAttributeEChange(replaceSingleValuedEAttribute);
				if (result == null) result = caseSubtractiveAttributeEChange(replaceSingleValuedEAttribute);
				if (result == null) result = caseReplaceSingleValuedFeatureEChange(replaceSingleValuedEAttribute);
				if (result == null) result = caseAdditiveEChange(replaceSingleValuedEAttribute);
				if (result == null) result = caseUpdateAttributeEChange(replaceSingleValuedEAttribute);
				if (result == null) result = caseSubtractiveEChange(replaceSingleValuedEAttribute);
				if (result == null) result = caseUpdateSingleValuedFeatureEChange(replaceSingleValuedEAttribute);
				if (result == null) result = caseFeatureEChange(replaceSingleValuedEAttribute);
				if (result == null) result = caseAtomicEChange(replaceSingleValuedEAttribute);
				if (result == null) result = caseEChange(replaceSingleValuedEAttribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Update Attribute EChange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Update Attribute EChange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject> T1 caseUpdateAttributeEChange(UpdateAttributeEChange<A> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Additive Attribute EChange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Additive Attribute EChange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, T extends Object> T1 caseAdditiveAttributeEChange(AdditiveAttributeEChange<A, T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Subtractive Attribute EChange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Subtractive Attribute EChange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, T extends Object> T1 caseSubtractiveAttributeEChange(SubtractiveAttributeEChange<A, T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Insert EAttribute Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Insert EAttribute Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, T extends Object> T1 caseInsertEAttributeValue(InsertEAttributeValue<A, T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Remove EAttribute Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Remove EAttribute Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, T extends Object> T1 caseRemoveEAttributeValue(RemoveEAttributeValue<A, T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Replace Single Valued EAttribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Replace Single Valued EAttribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, T extends Object> T1 caseReplaceSingleValuedEAttribute(ReplaceSingleValuedEAttribute<A, T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EChange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EChange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseEChange(EChange object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Atomic EChange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Atomic EChange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseAtomicEChange(AtomicEChange object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EChange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EChange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, F extends EStructuralFeature> T1 caseFeatureEChange(FeatureEChange<A, F> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Additive EChange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Additive EChange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T extends Object> T1 caseAdditiveEChange(AdditiveEChange<T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Subtractive EChange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Subtractive EChange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T extends Object> T1 caseSubtractiveEChange(SubtractiveEChange<T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Update Multi Valued Feature EChange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Update Multi Valued Feature EChange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, F extends EStructuralFeature> T1 caseUpdateMultiValuedFeatureEChange(UpdateMultiValuedFeatureEChange<A, F> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Update Single List Entry EChange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Update Single List Entry EChange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, F extends EStructuralFeature> T1 caseUpdateSingleListEntryEChange(UpdateSingleListEntryEChange<A, F> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Insert In List EChange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Insert In List EChange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, F extends EStructuralFeature, T extends Object> T1 caseInsertInListEChange(InsertInListEChange<A, F, T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Remove From List EChange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Remove From List EChange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, F extends EStructuralFeature, T extends Object> T1 caseRemoveFromListEChange(RemoveFromListEChange<A, F, T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Update Single Valued Feature EChange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Update Single Valued Feature EChange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, F extends EStructuralFeature> T1 caseUpdateSingleValuedFeatureEChange(UpdateSingleValuedFeatureEChange<A, F> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Replace Single Valued Feature EChange</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Replace Single Valued Feature EChange</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <A extends EObject, F extends EStructuralFeature, T extends Object> T1 caseReplaceSingleValuedFeatureEChange(ReplaceSingleValuedFeatureEChange<A, F, T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T1 defaultCase(EObject object) {
		return null;
	}

} //AttributeSwitch
