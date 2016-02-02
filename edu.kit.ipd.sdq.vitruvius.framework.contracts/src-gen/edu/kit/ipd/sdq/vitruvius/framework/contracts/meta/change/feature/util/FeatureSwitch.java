/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.util;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EAtomicChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.*;

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
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeaturePackage
 * @generated
 */
public class FeatureSwitch<T> extends Switch<T> {
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
    protected T doSwitch(int classifierID, EObject theEObject) {
        switch (classifierID) {
            case FeaturePackage.EFEATURE_CHANGE: {
                EFeatureChange<?, ?> eFeatureChange = (EFeatureChange<?, ?>)theEObject;
                T result = caseEFeatureChange(eFeatureChange);
                if (result == null) result = caseEAtomicChange(eFeatureChange);
                if (result == null) result = caseEChange(eFeatureChange);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case FeaturePackage.UPDATE_EFEATURE: {
                UpdateEFeature updateEFeature = (UpdateEFeature)theEObject;
                T result = caseUpdateEFeature(updateEFeature);
                if (result == null) result = caseEAtomicChange(updateEFeature);
                if (result == null) result = caseEChange(updateEFeature);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case FeaturePackage.UPDATE_MULTI_VALUED_EFEATURE: {
                UpdateMultiValuedEFeature updateMultiValuedEFeature = (UpdateMultiValuedEFeature)theEObject;
                T result = caseUpdateMultiValuedEFeature(updateMultiValuedEFeature);
                if (result == null) result = caseUpdateEFeature(updateMultiValuedEFeature);
                if (result == null) result = caseEAtomicChange(updateMultiValuedEFeature);
                if (result == null) result = caseEChange(updateMultiValuedEFeature);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case FeaturePackage.UPDATE_SINGLE_VALUED_EFEATURE: {
                UpdateSingleValuedEFeature updateSingleValuedEFeature = (UpdateSingleValuedEFeature)theEObject;
                T result = caseUpdateSingleValuedEFeature(updateSingleValuedEFeature);
                if (result == null) result = caseUpdateEFeature(updateSingleValuedEFeature);
                if (result == null) result = caseEAtomicChange(updateSingleValuedEFeature);
                if (result == null) result = caseEChange(updateSingleValuedEFeature);
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
    public <A extends EObject, F extends EStructuralFeature> T caseEFeatureChange(EFeatureChange<A, F> object) {
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
    public T caseUpdateEFeature(UpdateEFeature object) {
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
    public T caseUpdateMultiValuedEFeature(UpdateMultiValuedEFeature object) {
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
    public T caseUpdateSingleValuedEFeature(UpdateSingleValuedEFeature object) {
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
    public T caseEChange(EChange object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>EAtomic Change</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EAtomic Change</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEAtomicChange(EAtomicChange object) {
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
    public T defaultCase(EObject object) {
        return null;
    }

} //FeatureSwitch
