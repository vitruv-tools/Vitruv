/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.util;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

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
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage
 * @generated
 */
public class ChangeSwitch<T1> extends Switch<T1> {
    /**
     * The cached model package
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static ChangePackage modelPackage;

    /**
     * Creates an instance of the switch.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ChangeSwitch() {
        if (modelPackage == null) {
            modelPackage = ChangePackage.eINSTANCE;
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
            case ChangePackage.ECHANGE: {
                EChange eChange = (EChange)theEObject;
                T1 result = caseEChange(eChange);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.EATOMIC_CHANGE: {
                EAtomicChange eAtomicChange = (EAtomicChange)theEObject;
                T1 result = caseEAtomicChange(eAtomicChange);
                if (result == null) result = caseEChange(eAtomicChange);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.ADDITIVE_ECHANGE: {
                AdditiveEChange<?> additiveEChange = (AdditiveEChange<?>)theEObject;
                T1 result = caseAdditiveEChange(additiveEChange);
                if (result == null) result = caseEAtomicChange(additiveEChange);
                if (result == null) result = caseEChange(additiveEChange);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.SUBTRACTIVE_ECHANGE: {
                SubtractiveEChange<?> subtractiveEChange = (SubtractiveEChange<?>)theEObject;
                T1 result = caseSubtractiveEChange(subtractiveEChange);
                if (result == null) result = caseEAtomicChange(subtractiveEChange);
                if (result == null) result = caseEChange(subtractiveEChange);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.ADDITIVE_EATTRIBUTE_CHANGE: {
                AdditiveEAttributeChange<?> additiveEAttributeChange = (AdditiveEAttributeChange<?>)theEObject;
                T1 result = caseAdditiveEAttributeChange(additiveEAttributeChange);
                if (result == null) result = caseAdditiveEChange(additiveEAttributeChange);
                if (result == null) result = caseEAtomicChange(additiveEAttributeChange);
                if (result == null) result = caseEChange(additiveEAttributeChange);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.SUBTRACTIVE_EATTRIBUTE_CHANGE: {
                SubtractiveEAttributeChange<?> subtractiveEAttributeChange = (SubtractiveEAttributeChange<?>)theEObject;
                T1 result = caseSubtractiveEAttributeChange(subtractiveEAttributeChange);
                if (result == null) result = caseSubtractiveEChange(subtractiveEAttributeChange);
                if (result == null) result = caseEAtomicChange(subtractiveEAttributeChange);
                if (result == null) result = caseEChange(subtractiveEAttributeChange);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.ADDITIVE_EREFERENCE_CHANGE: {
                AdditiveEReferenceChange<?> additiveEReferenceChange = (AdditiveEReferenceChange<?>)theEObject;
                T1 result = caseAdditiveEReferenceChange(additiveEReferenceChange);
                if (result == null) result = caseAdditiveEChange(additiveEReferenceChange);
                if (result == null) result = caseEAtomicChange(additiveEReferenceChange);
                if (result == null) result = caseEChange(additiveEReferenceChange);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.SUBTRACTIVE_EREFERENCE_CHANGE: {
                SubtractiveEReferenceChange subtractiveEReferenceChange = (SubtractiveEReferenceChange)theEObject;
                T1 result = caseSubtractiveEReferenceChange(subtractiveEReferenceChange);
                if (result == null) result = caseSubtractiveEChange(subtractiveEReferenceChange);
                if (result == null) result = caseEAtomicChange(subtractiveEReferenceChange);
                if (result == null) result = caseEChange(subtractiveEReferenceChange);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            default: return defaultCase(theEObject);
        }
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
    public T1 caseEAtomicChange(EAtomicChange object) {
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
     * Returns the result of interpreting the object as an instance of '<em>Additive EAttribute Change</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Additive EAttribute Change</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends Object> T1 caseAdditiveEAttributeChange(AdditiveEAttributeChange<T> object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Subtractive EAttribute Change</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Subtractive EAttribute Change</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends Object> T1 caseSubtractiveEAttributeChange(SubtractiveEAttributeChange<T> object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Additive EReference Change</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Additive EReference Change</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends EObject> T1 caseAdditiveEReferenceChange(AdditiveEReferenceChange<T> object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Subtractive EReference Change</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Subtractive EReference Change</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T1 caseSubtractiveEReferenceChange(SubtractiveEReferenceChange object) {
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

} //ChangeSwitch
