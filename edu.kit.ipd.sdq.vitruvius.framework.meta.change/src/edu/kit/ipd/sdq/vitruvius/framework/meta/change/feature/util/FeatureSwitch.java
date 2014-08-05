/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.util;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.util.Switch;

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
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.FeaturePackage
 * @generated
 */
public class FeatureSwitch<T1> extends Switch<T1> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static FeaturePackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureSwitch() {
		if (modelPackage == null) {
			modelPackage = FeaturePackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
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
			case FeaturePackage.EFEATURE_CHANGE: {
				EFeatureChange<?> eFeatureChange = (EFeatureChange<?>)theEObject;
				T1 result = caseEFeatureChange(eFeatureChange);
				if (result == null) result = caseEChange(eFeatureChange);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FeaturePackage.UPDATE_EFEATURE: {
				UpdateEFeature<?> updateEFeature = (UpdateEFeature<?>)theEObject;
				T1 result = caseUpdateEFeature(updateEFeature);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FeaturePackage.UPDATE_MULTI_VALUED_EFEATURE: {
				UpdateMultiValuedEFeature<?> updateMultiValuedEFeature = (UpdateMultiValuedEFeature<?>)theEObject;
				T1 result = caseUpdateMultiValuedEFeature(updateMultiValuedEFeature);
				if (result == null) result = caseUpdateEFeature(updateMultiValuedEFeature);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE: {
				UpdateSingleValuedEFeature<?> updateSingleValuedEFeature = (UpdateSingleValuedEFeature<?>)theEObject;
				T1 result = caseUpdateSingleValuedEFeature(updateSingleValuedEFeature);
				if (result == null) result = caseUpdateEFeature(updateSingleValuedEFeature);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FeaturePackage.UNSET_EFEATURE: {
				UnsetEFeature<?> unsetEFeature = (UnsetEFeature<?>)theEObject;
				T1 result = caseUnsetEFeature(unsetEFeature);
				if (result == null) result = caseEFeatureChange(unsetEFeature);
				if (result == null) result = caseEChange(unsetEFeature);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FeaturePackage.UNSET_EATTRIBUTE: {
				UnsetEAttribute<?> unsetEAttribute = (UnsetEAttribute<?>)theEObject;
				T1 result = caseUnsetEAttribute(unsetEAttribute);
				if (result == null) result = caseUnsetEFeature(unsetEAttribute);
				if (result == null) result = caseEFeatureChange(unsetEAttribute);
				if (result == null) result = caseEChange(unsetEAttribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FeaturePackage.UNSET_EREFERENCE: {
				UnsetEReference<?> unsetEReference = (UnsetEReference<?>)theEObject;
				T1 result = caseUnsetEReference(unsetEReference);
				if (result == null) result = caseUnsetEFeature(unsetEReference);
				if (result == null) result = caseEFeatureChange(unsetEReference);
				if (result == null) result = caseEChange(unsetEReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FeaturePackage.UNSET_NON_CONTAINMENT_EREFERENCE: {
				UnsetNonContainmentEReference<?> unsetNonContainmentEReference = (UnsetNonContainmentEReference<?>)theEObject;
				T1 result = caseUnsetNonContainmentEReference(unsetNonContainmentEReference);
				if (result == null) result = caseUnsetEReference(unsetNonContainmentEReference);
				if (result == null) result = caseUnsetEFeature(unsetNonContainmentEReference);
				if (result == null) result = caseEFeatureChange(unsetNonContainmentEReference);
				if (result == null) result = caseEChange(unsetNonContainmentEReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FeaturePackage.UNSET_CONTAINMENT_EREFERENCE: {
				UnsetContainmentEReference<?> unsetContainmentEReference = (UnsetContainmentEReference<?>)theEObject;
				T1 result = caseUnsetContainmentEReference(unsetContainmentEReference);
				if (result == null) result = caseUnsetEReference(unsetContainmentEReference);
				if (result == null) result = caseUnsetEFeature(unsetContainmentEReference);
				if (result == null) result = caseEFeatureChange(unsetContainmentEReference);
				if (result == null) result = caseEChange(unsetContainmentEReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EFeature Change</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EFeature Change</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T extends EStructuralFeature> T1 caseEFeatureChange(EFeatureChange<T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Update EFeature</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Update EFeature</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T extends Object> T1 caseUpdateEFeature(UpdateEFeature<T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Update Multi Valued EFeature</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Update Multi Valued EFeature</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T extends Object> T1 caseUpdateMultiValuedEFeature(UpdateMultiValuedEFeature<T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Update Single Valued EFeature</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Update Single Valued EFeature</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T extends Object> T1 caseUpdateSingleValuedEFeature(UpdateSingleValuedEFeature<T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Unset EFeature</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Unset EFeature</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T extends EStructuralFeature> T1 caseUnsetEFeature(UnsetEFeature<T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Unset EAttribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Unset EAttribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T extends Object> T1 caseUnsetEAttribute(UnsetEAttribute<T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Unset EReference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Unset EReference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T extends EObject> T1 caseUnsetEReference(UnsetEReference<T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Unset Non Containment EReference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Unset Non Containment EReference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T extends EObject> T1 caseUnsetNonContainmentEReference(UnsetNonContainmentEReference<T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Unset Containment EReference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Unset Containment EReference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T extends EObject> T1 caseUnsetContainmentEReference(UnsetContainmentEReference<T> object) {
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

} //FeatureSwitch
