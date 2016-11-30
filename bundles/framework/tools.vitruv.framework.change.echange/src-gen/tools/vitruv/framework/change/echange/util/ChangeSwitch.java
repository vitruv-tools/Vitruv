/**
 */
package tools.vitruv.framework.change.echange.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import tools.vitruv.framework.change.echange.*;

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
 * @see tools.vitruv.framework.change.echange.ChangePackage
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
            case ChangePackage.ATOMIC_ECHANGE: {
                AtomicEChange atomicEChange = (AtomicEChange)theEObject;
                T1 result = caseAtomicEChange(atomicEChange);
                if (result == null) result = caseEChange(atomicEChange);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.ADDITIVE_ECHANGE: {
                AdditiveEChange<?> additiveEChange = (AdditiveEChange<?>)theEObject;
                T1 result = caseAdditiveEChange(additiveEChange);
                if (result == null) result = caseAtomicEChange(additiveEChange);
                if (result == null) result = caseEChange(additiveEChange);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.SUBTRACTIVE_ECHANGE: {
                SubtractiveEChange<?> subtractiveEChange = (SubtractiveEChange<?>)theEObject;
                T1 result = caseSubtractiveEChange(subtractiveEChange);
                if (result == null) result = caseAtomicEChange(subtractiveEChange);
                if (result == null) result = caseEChange(subtractiveEChange);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.EOBJECT_ADDED_ECHANGE: {
                EObjectAddedEChange<?> eObjectAddedEChange = (EObjectAddedEChange<?>)theEObject;
                T1 result = caseEObjectAddedEChange(eObjectAddedEChange);
                if (result == null) result = caseAdditiveEChange(eObjectAddedEChange);
                if (result == null) result = caseAtomicEChange(eObjectAddedEChange);
                if (result == null) result = caseEChange(eObjectAddedEChange);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ChangePackage.EOBJECT_SUBTRACTED_ECHANGE: {
                EObjectSubtractedEChange<?> eObjectSubtractedEChange = (EObjectSubtractedEChange<?>)theEObject;
                T1 result = caseEObjectSubtractedEChange(eObjectSubtractedEChange);
                if (result == null) result = caseSubtractiveEChange(eObjectSubtractedEChange);
                if (result == null) result = caseAtomicEChange(eObjectSubtractedEChange);
                if (result == null) result = caseEChange(eObjectSubtractedEChange);
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
     * Returns the result of interpreting the object as an instance of '<em>EObject Added EChange</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EObject Added EChange</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends EObject> T1 caseEObjectAddedEChange(EObjectAddedEChange<T> object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>EObject Subtracted EChange</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EObject Subtracted EChange</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public <T extends EObject> T1 caseEObjectSubtractedEChange(EObjectSubtractedEChange<T> object) {
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
