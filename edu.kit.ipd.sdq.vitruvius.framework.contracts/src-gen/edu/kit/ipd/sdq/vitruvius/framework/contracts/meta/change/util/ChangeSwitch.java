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
            case ChangePackage.ADDITIVE_CHANGE: {
                AdditiveChange<?> additiveChange = (AdditiveChange<?>)theEObject;
                T1 result = caseAdditiveChange(additiveChange);
                if (result == null) result = caseEChange(additiveChange);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.SUBTRACTIVE_CHANGE: {
                SubtractiveChange<?> subtractiveChange = (SubtractiveChange<?>)theEObject;
                T1 result = caseSubtractiveChange(subtractiveChange);
                if (result == null) result = caseEChange(subtractiveChange);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.REPLACIVE_CHANGE: {
                ReplaciveChange<?> replaciveChange = (ReplaciveChange<?>)theEObject;
                T1 result = caseReplaciveChange(replaciveChange);
                if (result == null) result = caseSubtractiveChange(replaciveChange);
                if (result == null) result = caseAdditiveChange(replaciveChange);
                if (result == null) result = caseEChange(replaciveChange);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.ADDITIVE_ATTRIBUTE_CHANGE: {
                AdditiveAttributeChange<?> additiveAttributeChange = (AdditiveAttributeChange<?>)theEObject;
                T1 result = caseAdditiveAttributeChange(additiveAttributeChange);
                if (result == null) result = caseAdditiveChange(additiveAttributeChange);
                if (result == null) result = caseEChange(additiveAttributeChange);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.SUBTRACTIVE_ATTRIBUTE_CHANGE: {
                SubtractiveAttributeChange<?> subtractiveAttributeChange = (SubtractiveAttributeChange<?>)theEObject;
                T1 result = caseSubtractiveAttributeChange(subtractiveAttributeChange);
                if (result == null) result = caseSubtractiveChange(subtractiveAttributeChange);
                if (result == null) result = caseEChange(subtractiveAttributeChange);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.REPLACIVE_ATTRIBUTE_CHANGE: {
                ReplaciveAttributeChange<?> replaciveAttributeChange = (ReplaciveAttributeChange<?>)theEObject;
                T1 result = caseReplaciveAttributeChange(replaciveAttributeChange);
                if (result == null) result = caseSubtractiveAttributeChange(replaciveAttributeChange);
                if (result == null) result = caseAdditiveAttributeChange(replaciveAttributeChange);
                if (result == null) result = caseSubtractiveChange(replaciveAttributeChange);
                if (result == null) result = caseAdditiveChange(replaciveAttributeChange);
                if (result == null) result = caseEChange(replaciveAttributeChange);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.ADDITIVE_REFERENCE_CHANGE: {
                AdditiveReferenceChange<?> additiveReferenceChange = (AdditiveReferenceChange<?>)theEObject;
                T1 result = caseAdditiveReferenceChange(additiveReferenceChange);
                if (result == null) result = caseAdditiveChange(additiveReferenceChange);
                if (result == null) result = caseEChange(additiveReferenceChange);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.SUBTRACTIVE_REFERENCE_CHANGE: {
                SubtractiveReferenceChange subtractiveReferenceChange = (SubtractiveReferenceChange)theEObject;
                T1 result = caseSubtractiveReferenceChange(subtractiveReferenceChange);
                if (result == null) result = caseSubtractiveChange(subtractiveReferenceChange);
                if (result == null) result = caseEChange(subtractiveReferenceChange);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.REPLACIVE_REFERENCE_CHANGE: {
                ReplaciveReferenceChange<?> replaciveReferenceChange = (ReplaciveReferenceChange<?>)theEObject;
                T1 result = caseReplaciveReferenceChange(replaciveReferenceChange);
                if (result == null) result = caseSubtractiveReferenceChange(replaciveReferenceChange);
                if (result == null) result = caseAdditiveReferenceChange(replaciveReferenceChange);
                if (result == null) result = caseSubtractiveChange(replaciveReferenceChange);
                if (result == null) result = caseAdditiveChange(replaciveReferenceChange);
                if (result == null) result = caseEChange(replaciveReferenceChange);
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
     * Returns the result of interpreting the object as an instance of '<em>Additive Change</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Additive Change</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends Object> T1 caseAdditiveChange(AdditiveChange<T> object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Subtractive Change</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Subtractive Change</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends Object> T1 caseSubtractiveChange(SubtractiveChange<T> object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Replacive Change</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Replacive Change</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends Object> T1 caseReplaciveChange(ReplaciveChange<T> object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Additive Attribute Change</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Additive Attribute Change</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends Object> T1 caseAdditiveAttributeChange(AdditiveAttributeChange<T> object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Subtractive Attribute Change</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Subtractive Attribute Change</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends Object> T1 caseSubtractiveAttributeChange(SubtractiveAttributeChange<T> object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Replacive Attribute Change</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Replacive Attribute Change</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends Object> T1 caseReplaciveAttributeChange(ReplaciveAttributeChange<T> object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Additive Reference Change</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Additive Reference Change</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends EObject> T1 caseAdditiveReferenceChange(AdditiveReferenceChange<T> object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Subtractive Reference Change</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Subtractive Reference Change</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T1 caseSubtractiveReferenceChange(SubtractiveReferenceChange object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Replacive Reference Change</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Replacive Reference Change</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends EObject> T1 caseReplaciveReferenceChange(ReplaciveReferenceChange<T> object) {
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
